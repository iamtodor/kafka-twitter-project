package com.iamtodor;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.elasticsearch.action.bulk.BulkRequest;
import org.junit.Test;

import static com.iamtodor.MockedDataUtils.getConsumerRecords;
import static com.iamtodor.MockedDataUtils.loadJsonRecord;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestElasticClient {

    @Test
    public void testClientWithBadData() {
        ElasticSearchClient elasticSearchClient = new ElasticSearchClient();

        ConsumerRecords<String, String> consumerRecords = getConsumerRecords("topic", 1, 123L,
                "key", "");

        BulkRequest bulkRequest = elasticSearchClient.getBulkRequest(consumerRecords);
        assertTrue(bulkRequest.requests().isEmpty());
    }

    @Test
    public void testClientWithGoodData() {
        ElasticSearchClient elasticSearchClient = new ElasticSearchClient();
        String data = loadJsonRecord();

        ConsumerRecords<String, String> consumerRecords = getConsumerRecords("topic", 1, 123L,
                "key", data);

        BulkRequest bulkRequest = elasticSearchClient.getBulkRequest(consumerRecords);
        assertEquals(1, bulkRequest.requests().size());
    }

}
