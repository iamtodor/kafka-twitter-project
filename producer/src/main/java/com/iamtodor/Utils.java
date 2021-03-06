package com.iamtodor;

import com.google.gson.JsonParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

    static Logger logger = LoggerFactory.getLogger(Utils.class.getName());

    public static String extractAuthorIdFromTweet(String tweet) {
        String userId = "";
        try {
            userId = JsonParser.parseString(tweet)
                    .getAsJsonObject()
                    .get("user")
                    .getAsJsonObject()
                    .get("id_str")
                    .getAsString();
        } catch (NullPointerException | IllegalStateException e) {
            logger.error(e.toString());
        }
        return userId;
    }
}
