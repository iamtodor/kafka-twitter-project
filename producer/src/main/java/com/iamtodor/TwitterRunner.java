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
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static com.iamtodor.Producer.TOPIC;

public class TwitterClient {

    public static final String ACCESS_TOKEN = "1126052815854551040-GUZqycXGgem912tAWcwonus97tcynG";
    public static final String ACCESS_TOKEN_SECRET = "0a1cWzFsajOwHk41MoYtckHvog4fqlogVW4X2BdxDC1gd";
    public static final String CONSUMER_KEY = "ZKoDH5Jjq11LTUMHXofKrbXjI";
    public static final String CONSUMER_SECRET = "wINZGs9f3Bgmv8Ep9r3Myr1zwD42nabhMxT7mFNhU0qNy267HH";
    private static final List<String> TWITTER_TERMS = Arrays.asList("bitcoin", "usa", "politics", "sport", "soccer", "trump");

    private static final Logger logger = LoggerFactory.getLogger(TwitterClient.class.getName());

    public BasicClient setupClient(BlockingQueue<String> msgQueue) {
        /* Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
        Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();

        // Optional: set up some followings and track terms
        List<String> terms = Lists.newArrayList(TWITTER_TERMS);
        hosebirdEndpoint.trackTerms(terms);

        // These secrets should be read from a config file
        Authentication hosebirdAuth = new OAuth1(CONSUMER_KEY, CONSUMER_SECRET, ACCESS_TOKEN, ACCESS_TOKEN_SECRET);

        ClientBuilder builder = new ClientBuilder()
                .name("Hosebird-Client-01")                              // optional: mainly for the logs
                .hosts(hosebirdHosts)
                .authentication(hosebirdAuth)
                .endpoint(hosebirdEndpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        return builder.build();
    }

    public void processTweets(KafkaProducer<String, String> producer) {
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(100000);
        BasicClient client = setupClient(msgQueue);
        client.connect();

        while (!client.isDone()) {
            String tweet;

            try {
                tweet = msgQueue.poll(5, TimeUnit.SECONDS);
                if (tweet != null) {
                    logger.info(tweet);
                    String authorId = Utils.extractAuthorIdFromTweet(tweet);

                    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC, authorId, tweet);
                    producer.send(producerRecord, new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata metadata, Exception exception) {
                            if (exception != null) {
                                logger.error("Something bad happened" + exception.toString());
                            }
                        }
                    });
                }
            } catch (InterruptedException e) {
                logger.error(e.toString());
                producer.close();
                client.stop();
            }
            catch (NullPointerException e) {
                logger.warn("skipping bad data: ");
            }
        }
    }

}