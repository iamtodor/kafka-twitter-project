start zookeeper and kafka:
- bin/zookeeper-server-start.sh config/zookeeper.properties
- bin/kafka-server-start.sh config/server.properties

create all the topics:
- bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic twitter.raw_tweets --create --partitions 3 --replication-factor 1
- bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic twitter.important_tweets --create --partitions 3 --replication-factor 1
- bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic twitter.test_word_count --create --partitions 3 --replication-factor 1
- bin/kafka-topics.sh --bootstrap-server localhost:9092 --topic twitter.author_tweets_count --create --partitions 3 --replication-factor 1

read data from the topics:
- bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic twitter.raw_tweets --from-beginning
- bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic twitter.important_tweets --from-beginning
- bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic twitter.test_word_count --from-beginning
- bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic twitter.author_tweets_count --from-beginning

command to use twitter kafka connector and ES sink connector:
- kafka_2.13-2.5.0/bin/connect-standalone.sh connect-standalone.properties twitter.properties
- kafka_2.13-2.5.0/bin/connect-standalone.sh connect-standalone.properties es.properties
