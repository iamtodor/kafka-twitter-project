package com.iamtodor;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;

public class ElasticSearchClient {

    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchClient.class.getName());
    private static final String hostname = "kafka-course-4367221963.us-east-1.bonsaisearch.net";
    private static final String username = "pdr22x15j4";
    private static final String password = "wvb3g8c4xk";

    public RestHighLevelClient createRestClient() {
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

        RestClientBuilder builder = RestClient.builder(
                new HttpHost(hostname, 443, "https"))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                        return httpAsyncClientBuilder.setDefaultCredentialsProvider(provider);
                    }
                });
        return new RestHighLevelClient(builder);
    }

    public void proceedTweets(KafkaConsumer<String, String> consumer, RestHighLevelClient restClient) throws IOException {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            if (records.count() > 0) {
                BulkRequest bulkRequest = new BulkRequest();

                for (ConsumerRecord<String, String> record : records) {
                    String tweet = record.value();
                    logger.info(tweet);

                    String tweetId = Utils.extractTweetIdFromTweet(tweet);

                    if (tweetId.equals("")) {
                        logger.warn("skipping bad data");
                        continue;
                    }

                    IndexRequest indexRequest = new IndexRequest("twitter").source(record.value(), XContentType.JSON);
                    indexRequest.id(tweetId);
                    bulkRequest.add(indexRequest);
                }

                BulkResponse responses = restClient.bulk(bulkRequest, RequestOptions.DEFAULT);
                consumer.commitSync();
            }

        }
    }

}
