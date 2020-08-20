package com.iamtodor;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestUtils {

    @Test
    public void testValidExtractAuthorIdFromTweet() {
        String expectedUserId = "16259258";
        String data = "{\n" +
                "    \"created_at\": \"Thu Aug 20 08:23:33 +0000 2020\",\n" +
                "    \"id\": 1296362220729892865,\n" +
                "    \"id_str\": \"1296362220729892865\",\n" +
                "    \"text\": \"@savethekids1991 @DrGW0 @voltairesghost1 @lakersblake2 @real_defender @realDonaldTrump You are FAR from a free thin\\u2026 https:\\/\\/t.co\\/5RweY21wHE\",\n" +
                "    \"display_text_range\": [\n" +
                "        87,\n" +
                "        140\n" +
                "    ],\n" +
                "    \"source\": \"\\u003ca href=\\\"http:\\/\\/twitter.com\\/download\\/iphone\\\" rel=\\\"nofollow\\\"\\u003eTwitter for iPhone\\u003c\\/a\\u003e\",\n" +
                "    \"truncated\": true,\n" +
                "    \"in_reply_to_status_id\": 1296342119297163264,\n" +
                "    \"in_reply_to_status_id_str\": \"1296342119297163264\",\n" +
                "    \"in_reply_to_user_id\": 960912551113506816,\n" +
                "    \"in_reply_to_user_id_str\": \"960912551113506816\",\n" +
                "    \"in_reply_to_screen_name\": \"savethekids1991\",\n" +
                "    \"user\": {\n" +
                "        \"id\": 16259258,\n" +
                "        \"id_str\": \"16259258\",\n" +
                "        \"name\": \"TimeCr0ss\",\n" +
                "        \"screen_name\": \"Timecr0ss\",\n" +
                "        \"location\": \"Pale Blue Dot\",\n" +
                "        \"url\": null,\n" +
                "        \"description\": \"I have Castle Bravo as my cover because this is the mess we are in. Proudly not retweeted by Trump.\",\n" +
                "        \"translator_type\": \"none\",\n" +
                "        \"protected\": false,\n" +
                "        \"verified\": false,\n" +
                "        \"followers_count\": 132,\n" +
                "        \"friends_count\": 272,\n" +
                "        \"listed_count\": 0,\n" +
                "        \"favourites_count\": 9023,\n" +
                "        \"statuses_count\": 4035,\n" +
                "        \"created_at\": \"Fri Sep 12 17:19:12 +0000 2008\",\n" +
                "        \"utc_offset\": null,\n" +
                "        \"time_zone\": null,\n" +
                "        \"geo_enabled\": false,\n" +
                "        \"lang\": null,\n" +
                "        \"contributors_enabled\": false,\n" +
                "        \"is_translator\": false,\n" +
                "        \"profile_background_color\": \"C0DEED\",\n" +
                "        \"profile_background_image_url\": \"http:\\/\\/abs.twimg.com\\/images\\/themes\\/theme1\\/bg.png\",\n" +
                "        \"profile_background_image_url_https\": \"https:\\/\\/abs.twimg.com\\/images\\/themes\\/theme1\\/bg.png\",\n" +
                "        \"profile_background_tile\": false,\n" +
                "        \"profile_link_color\": \"0084B4\",\n" +
                "        \"profile_sidebar_border_color\": \"FFFFFF\",\n" +
                "        \"profile_sidebar_fill_color\": \"DDEEF6\",\n" +
                "        \"profile_text_color\": \"333333\",\n" +
                "        \"profile_use_background_image\": true,\n" +
                "        \"profile_image_url\": \"http:\\/\\/pbs.twimg.com\\/profile_images\\/1285516950450384897\\/8KqwGjNT_normal.jpg\",\n" +
                "        \"profile_image_url_https\": \"https:\\/\\/pbs.twimg.com\\/profile_images\\/1285516950450384897\\/8KqwGjNT_normal.jpg\",\n" +
                "        \"profile_banner_url\": \"https:\\/\\/pbs.twimg.com\\/profile_banners\\/16259258\\/1595326109\",\n" +
                "        \"default_profile\": false,\n" +
                "        \"default_profile_image\": false,\n" +
                "        \"following\": null,\n" +
                "        \"follow_request_sent\": null,\n" +
                "        \"notifications\": null\n" +
                "    },\n" +
                "    \"geo\": null,\n" +
                "    \"coordinates\": null,\n" +
                "    \"place\": null,\n" +
                "    \"contributors\": null,\n" +
                "    \"is_quote_status\": false,\n" +
                "    \"extended_tweet\": {\n" +
                "        \"full_text\": \"@savethekids1991 @DrGW0 @voltairesghost1 @lakersblake2 @real_defender @realDonaldTrump You are FAR from a free thinker. You repeat exactly what Q or Trump say.\",\n" +
                "        \"display_text_range\": [\n" +
                "            87,\n" +
                "            159\n" +
                "        ],\n" +
                "        \"entities\": {\n" +
                "            \"hashtags\": [],\n" +
                "            \"urls\": [],\n" +
                "            \"user_mentions\": [\n" +
                "                {\n" +
                "                    \"screen_name\": \"savethekids1991\",\n" +
                "                    \"name\": \"Cody\",\n" +
                "                    \"id\": 960912551113506816,\n" +
                "                    \"id_str\": \"960912551113506816\",\n" +
                "                    \"indices\": [\n" +
                "                        0,\n" +
                "                        16\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"screen_name\": \"DrGW0\",\n" +
                "                    \"name\": \"Graham Webb\",\n" +
                "                    \"id\": 94197182,\n" +
                "                    \"id_str\": \"94197182\",\n" +
                "                    \"indices\": [\n" +
                "                        17,\n" +
                "                        23\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"screen_name\": \"voltairesghost1\",\n" +
                "                    \"name\": \"voltairesghost\",\n" +
                "                    \"id\": 1254506116270219269,\n" +
                "                    \"id_str\": \"1254506116270219269\",\n" +
                "                    \"indices\": [\n" +
                "                        24,\n" +
                "                        40\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"screen_name\": \"lakersblake2\",\n" +
                "                    \"name\": \"Lakersblake2\",\n" +
                "                    \"id\": 1274699127167660032,\n" +
                "                    \"id_str\": \"1274699127167660032\",\n" +
                "                    \"indices\": [\n" +
                "                        41,\n" +
                "                        54\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"screen_name\": \"real_defender\",\n" +
                "                    \"name\": \"RD\",\n" +
                "                    \"id\": 49034507,\n" +
                "                    \"id_str\": \"49034507\",\n" +
                "                    \"indices\": [\n" +
                "                        55,\n" +
                "                        69\n" +
                "                    ]\n" +
                "                },\n" +
                "                {\n" +
                "                    \"screen_name\": \"realDonaldTrump\",\n" +
                "                    \"name\": \"Donald J. Trump\",\n" +
                "                    \"id\": 25073877,\n" +
                "                    \"id_str\": \"25073877\",\n" +
                "                    \"indices\": [\n" +
                "                        70,\n" +
                "                        86\n" +
                "                    ]\n" +
                "                }\n" +
                "            ],\n" +
                "            \"symbols\": []\n" +
                "        }\n" +
                "    },\n" +
                "    \"quote_count\": 0,\n" +
                "    \"reply_count\": 0,\n" +
                "    \"retweet_count\": 0,\n" +
                "    \"favorite_count\": 0,\n" +
                "    \"entities\": {\n" +
                "        \"hashtags\": [],\n" +
                "        \"urls\": [\n" +
                "            {\n" +
                "                \"url\": \"https:\\/\\/t.co\\/5RweY21wHE\",\n" +
                "                \"expanded_url\": \"https:\\/\\/twitter.com\\/i\\/web\\/status\\/1296362220729892865\",\n" +
                "                \"display_url\": \"twitter.com\\/i\\/web\\/status\\/1\\u2026\",\n" +
                "                \"indices\": [\n" +
                "                    117,\n" +
                "                    140\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"user_mentions\": [\n" +
                "            {\n" +
                "                \"screen_name\": \"savethekids1991\",\n" +
                "                \"name\": \"Cody\",\n" +
                "                \"id\": 960912551113506816,\n" +
                "                \"id_str\": \"960912551113506816\",\n" +
                "                \"indices\": [\n" +
                "                    0,\n" +
                "                    16\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"screen_name\": \"DrGW0\",\n" +
                "                \"name\": \"Graham Webb\",\n" +
                "                \"id\": 94197182,\n" +
                "                \"id_str\": \"94197182\",\n" +
                "                \"indices\": [\n" +
                "                    17,\n" +
                "                    23\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"screen_name\": \"voltairesghost1\",\n" +
                "                \"name\": \"voltairesghost\",\n" +
                "                \"id\": 1254506116270219269,\n" +
                "                \"id_str\": \"1254506116270219269\",\n" +
                "                \"indices\": [\n" +
                "                    24,\n" +
                "                    40\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"screen_name\": \"lakersblake2\",\n" +
                "                \"name\": \"Lakersblake2\",\n" +
                "                \"id\": 1274699127167660032,\n" +
                "                \"id_str\": \"1274699127167660032\",\n" +
                "                \"indices\": [\n" +
                "                    41,\n" +
                "                    54\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"screen_name\": \"real_defender\",\n" +
                "                \"name\": \"RD\",\n" +
                "                \"id\": 49034507,\n" +
                "                \"id_str\": \"49034507\",\n" +
                "                \"indices\": [\n" +
                "                    55,\n" +
                "                    69\n" +
                "                ]\n" +
                "            },\n" +
                "            {\n" +
                "                \"screen_name\": \"realDonaldTrump\",\n" +
                "                \"name\": \"Donald J. Trump\",\n" +
                "                \"id\": 25073877,\n" +
                "                \"id_str\": \"25073877\",\n" +
                "                \"indices\": [\n" +
                "                    70,\n" +
                "                    86\n" +
                "                ]\n" +
                "            }\n" +
                "        ],\n" +
                "        \"symbols\": []\n" +
                "    },\n" +
                "    \"favorited\": false,\n" +
                "    \"retweeted\": false,\n" +
                "    \"filter_level\": \"low\",\n" +
                "    \"lang\": \"en\",\n" +
                "    \"timestamp_ms\": \"1597911813324\"\n" +
                "}";
        String userId = Utils.extractAuthorIdFromTweet(data);
        assertEquals(userId, expectedUserId);
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
