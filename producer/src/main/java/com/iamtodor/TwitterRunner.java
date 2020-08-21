package com.iamtodor;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.BasicClient;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.iamtodor.ProducerComponent.TOPIC;

public class TwitterRunner {

    public static final String ACCESS_TOKEN = "1126052815854551040-GUZqycXGgem912tAWcwonus97tcynG";
    public static final String ACCESS_TOKEN_SECRET = "0a1cWzFsajOwHk41MoYtckHvog4fqlogVW4X2BdxDC1gd";
    public static final String CONSUMER_KEY = "ZKoDH5Jjq11LTUMHXofKrbXjI";
    public static final String CONSUMER_SECRET = "wINZGs9f3Bgmv8Ep9r3Myr1zwD42nabhMxT7mFNhU0qNy267HH";
    private static final List<String> TWITTER_TERMS = Arrays.asList("trump");

    private static final Logger logger = LoggerFactory.getLogger(TwitterRunner.class.getName());

    public BasicClient setupClient(BlockingQueue<String> msgQueue) {
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        List<String> terms = Lists.newArrayList(TWITTER_TERMS);
        hosebirdEndpoint.trackTerms(terms);

        Authentication hosebirdAuth = new OAuth1(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, ACCESS_TOKEN_SECRET);

        ClientBuilder builder = new ClientBuilder()
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        return builder.build();
    }

    public void processTweets(BasicClient client, KafkaProducer<String, String> producer, BlockingQueue<String> msgQueue) {
        client.connect();

        while (!client.isDone()) {
            String tweet;

            try {
                tweet = msgQueue.poll(5, TimeUnit.SECONDS);
                if (tweet != null) {
                    logger.info(tweet);
                    String authorId = Utils.extractAuthorIdFromTweet(tweet);

                    if (authorId.equals("")) {
                        logger.warn("skipping bad data");
                        continue;
                    }

                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, authorId, tweet);
                    producer.send(producerRecord, (metadata, exception) -> {
                        if (exception != null) {
                            logger.error("Something bad happened" + exception.toString());
                        }
                    });
                }
            } catch (InterruptedException e) {
                logger.error(e.toString());
                producer.close();
                client.stop();
            }
        }
    }

}