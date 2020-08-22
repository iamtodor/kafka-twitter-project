package com.iamtodor;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.iamtodor.MockedDataUtils.EXPECTED_USER_ID;
import static com.iamtodor.MockedDataUtils.loadJsonRecord;
import static org.junit.Assert.*;

public class TestProducer {

    @Test
    public void testProducer() throws ExecutionException, InterruptedException, TimeoutException {
        String data = loadJsonRecord();

        ProducerComponent producerComponent = new ProducerComponent();
        KafkaProducer<String, String> producer = producerComponent.createKafkaProducer();
        RecordMetadata recordMetadata = producer.send(new ProducerRecord<>(ProducerComponent.TOPIC, EXPECTED_USER_ID, data),
                (metadata, exception) -> {
                    assertNull(exception);
                    assertEquals(ProducerComponent.TOPIC, metadata.topic());
                }).get(3, TimeUnit.SECONDS);

        assertTrue(recordMetadata.hasOffset());
        assertTrue(recordMetadata.hasTimestamp());
    }

}
