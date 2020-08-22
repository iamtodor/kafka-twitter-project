package com.iamtodor;

import org.junit.Test;

import static com.iamtodor.MockedDataUtils.EXPECTED_TWEET_ID;
import static com.iamtodor.MockedDataUtils.loadJsonRecord;
import static org.junit.Assert.assertEquals;

public class TestUtils {

    @Test
    public void testValidExtractTweetIdFromTweet() {
        String data = loadJsonRecord();
        String userId = Utils.extractTweetIdFromTweet(data);
        assertEquals(userId, EXPECTED_TWEET_ID);
    }

    @Test
    public void testExtractAuthorIdFromTweetFromLimitJson() {
        String expectedTweetId = "";
        String data = "{\"limit\":{\"track\":232,\"timestamp_ms\":\"1597938751762\"}}";
        String tweetId = Utils.extractTweetIdFromTweet(data);
        assertEquals(tweetId, expectedTweetId);
    }

    @Test
    public void testExtractAuthorIdFromTweetFromEmptyString() {
        String expectedTweetId = "";
        String data = "";
        String tweetId = Utils.extractTweetIdFromTweet(data);
        assertEquals(tweetId, expectedTweetId);
    }

}
