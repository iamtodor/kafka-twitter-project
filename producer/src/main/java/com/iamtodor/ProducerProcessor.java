package com.iamtodor;

import com.twitter.hbc.httpclient.BasicClient;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProducerProcessor {

    private static final Logger logger = LoggerFactory.getLogger(ProducerProcessor.class.getName());

    public static void main(String[] args) {
        ProducerComponent producerRunner = new ProducerComponent();
        KafkaProducer<String, String> kafkaProducer = producerRunner.createKafkaProducer();

        TwitterRunner twitterRunner = new TwitterRunner();

        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<>(100000);
        BasicClient twitterClient = twitterRunner.setupClient(msgQueue);
        twitterRunner.processTweets(twitterClient, kafkaProducer, msgQueue);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("stopping app");
            twitterClient.stop();
            kafkaProducer.close();
        }));
    }

}
