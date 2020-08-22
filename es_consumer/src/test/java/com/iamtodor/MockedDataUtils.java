package com.iamtodor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockedDataUtils {

    public static final String EXPECTED_TWEET_ID = "1296362220729892865";

    protected static String loadJsonRecord() {
        byte[] encoded = new byte[0];
        try {
            encoded = Files.readAllBytes(Paths.get("src/test/resources/record.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(encoded, StandardCharsets.UTF_8);
    }

     protected static ConsumerRecords<String, String> getConsumerRecords(String topic, int partition, long offset,
                                                                 String key, String value) {
        ConsumerRecord<String, String> record = new ConsumerRecord<>(topic, partition, offset, key, value);
        Map<TopicPartition, List<ConsumerRecord<String, String>>> map = new HashMap<>();
        TopicPartition topicPartition = new TopicPartition(topic, partition);

        List<ConsumerRecord<String, String>> recordsContainer = new ArrayList<>();
        recordsContainer.add(record);

        map.put(topicPartition, recordsContainer);
        return new ConsumerRecords<>(map);
    }

}
