package com.iamtodor;

import org.junit.Test;

import static com.iamtodor.MockedDataUtils.EXPECTED_USER_ID;
import static com.iamtodor.MockedDataUtils.loadJsonRecord;
import static org.junit.Assert.assertEquals;

public class TestUtils {

    @Test
    public void testValidExtractAuthorIdFromTweet() {
        String data = loadJsonRecord();
        String userId = Utils.extractAuthorIdFromTweet(data);
        assertEquals(userId, EXPECTED_USER_ID);
    }

    @Test
    public void testExtractAuthorIdFromTweetFromLimitJson() {
        String expectedUserId = "";
        String data = "{\"limit\":{\"track\":232,\"timestamp_ms\":\"1597938751762\"}}";
        String userId = Utils.extractAuthorIdFromTweet(data);
        assertEquals(userId, expectedUserId);
    }

    @Test
    public void testExtractAuthorIdFromTweetFromEmptyString() {
        String expectedUserId = "";
        String data = "";
        String userId = Utils.extractAuthorIdFromTweet(data);
        assertEquals(userId, expectedUserId);
    }

}
