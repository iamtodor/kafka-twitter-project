package com.iamtodor;

import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    static Logger logger = LoggerFactory.getLogger(Utils.class.getName());

    public static String extractTweetIdFromTweet(String tweet) {
        String tweetId = "";
        try {
            tweetId = JsonParser.parseString(tweet)
                    .getAsJsonObject()
                    .get("id_str")
                    .getAsString();
        } catch (NullPointerException | IllegalStateException e) {
            logger.error(e.toString());
        }
        return tweetId;
    }

}
