package com.iamtodor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.connect.json.JsonDeserializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

public class Consumer {

    static Logger logger = LoggerFactory.getLogger(Consumer.class.getName());

    public static void main(String[] args) {
        Consumer consumer = new Consumer();

        Serializer<JsonNode> jsonSerializer = new JsonSerializer();
        Deserializer<JsonNode> jsonDeserializer = new JsonDeserializer();
        Serde<JsonNode> jsonNodeSerde = Serdes.serdeFrom(jsonSerializer, jsonDeserializer);

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, JsonNode> tweetStream = builder.stream("twitter.raw_tweets",
                Consumed.with(Serdes.String(), jsonNodeSerde));

        KStream<String, JsonNode> tweetsFromInfluencers = tweetStream.filter((key, jsonNode) ->
                jsonNode.get("user").get("followers_count").asInt() > 500);

        tweetsFromInfluencers.to("twitter.important_tweets", Produced.with(Serdes.String(), jsonNodeSerde));

        consumer.countWordsInTweet(tweetsFromInfluencers);
        consumer.countTweetsFromAuthor(jsonNodeSerde, tweetsFromInfluencers);

        Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, consumer.getProperties());
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }

    private void countTweetsFromAuthor(Serde<JsonNode> jsonNodeSerde, KStream<String, JsonNode> tweetsFromInfluencers) {
        ObjectNode initialCount = JsonNodeFactory.instance.objectNode();
        initialCount.put("count", 0);

        KTable<String, JsonNode> authorTweetsCount = tweetsFromInfluencers
                .groupByKey(Serialized.with(Serdes.String(), jsonNodeSerde))
                .aggregate(
                        () -> initialCount,
                        (key, tweet, aggValue) -> newCount(tweet, aggValue),
                        Materialized.<String, JsonNode, KeyValueStore<Bytes, byte[]>>as("bank-balance-agg")
                                .withKeySerde(Serdes.String())
                                .withValueSerde(jsonNodeSerde)
                );

        authorTweetsCount.toStream().to("twitter.author_tweets_count", Produced.with(Serdes.String(), jsonNodeSerde));
    }

    private static JsonNode newCount(JsonNode tweet, JsonNode currentState) {
        ObjectNode newState = JsonNodeFactory.instance.objectNode();
        newState.put("count", currentState.get("count").asInt() + 1);
        newState.put("author_id", tweet.get("id_str").asText());
        return newState;
    }

    private void countWordsInTweet(KStream<String, JsonNode> tweetsFromInfluencers) {
        KStream<String, String> wordCountInTweet = tweetsFromInfluencers.mapValues(value -> {
            String loweredTweet = value.get("text").asText().toLowerCase();
            return String.valueOf(Arrays.asList(loweredTweet.split("\\W+")).size());
        });

        wordCountInTweet.to("twitter.test_word_count", Produced.with(Serdes.String(), Serdes.String()));
    }

    private Properties getProperties() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "twitter-streams-app");
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.PROCESSING_GUARANTEE_CONFIG, StreamsConfig.EXACTLY_ONCE);
        return properties;
    }

}
