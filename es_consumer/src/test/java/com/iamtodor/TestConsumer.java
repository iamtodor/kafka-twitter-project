package com.iamtodor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;

import java.time.Duration;
import java.util.Collections;

import static org.junit.Assert.*;

public class TestConsumer {

    public static final String TOPIC_TWITTER_RAW_TWEETS = "twitter.raw_tweets";

    @Test
    public void testConsumer() {
        ConsumerComponent consumerComponent = new ConsumerComponent();
        KafkaConsumer<String, String> consumer = consumerComponent.createKafkaConsumer();
        consumer.subscribe(Collections.singletonList(TOPIC_TWITTER_RAW_TWEETS));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

            if (records.count() > 0) {
                ConsumerRecord<String, String> record = records.iterator().next();

                assertEquals(record.topic(), TOPIC_TWITTER_RAW_TWEETS);
                assertTrue(record.offset() > 0);
                assertTrue(record.partition() > 0);

                String tweetId = Utils.extractTweetIdFromTweet(record.value());
                assertNotEquals(tweetId, "");

                break;
            }
        }
    }

}
