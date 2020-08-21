package com.iamtodor;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;

public class ConsumerProcessor {

    final static Logger logger = LoggerFactory.getLogger(ConsumerProcessor.class.getName());

    public static void main(String[] args) {
        ConsumerComponent consumerComponent = new ConsumerComponent();
        KafkaConsumer<String, String> consumer = consumerComponent.createKafkaConsumer();
        consumer.subscribe(Collections.singletonList("twitter.raw_tweets"));

        ElasticSearchClient elasticSearchClient = new ElasticSearchClient();
        RestHighLevelClient restClient = elasticSearchClient.createRestClient();

        try {
            elasticSearchClient.proceedTweets(consumer, restClient);
        } catch (IOException e) {
            logger.error(e.toString());
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("stopping app");
            try {
                restClient.close();
            } catch (IOException e) {
                logger.error(e.toString());
            }
            consumer.close();
        }));
    }

}
