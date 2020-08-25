This repo is a pet project. 

The purpose of the project is to prove Kafka and Java knowledge. 

The main components are:
- Twitter
- Kafka
- ElasticSearch

Overview:
- For the twitter, I used [Hosebird Client](https://github.com/twitter/hbc)
- Kafka 2.5.0 is running locally
- For ElasticSearch I took PaaS https://bonsai.io/ 

It has the following modules:
- vanilla twitter producer
- kafka connect twitter
- vanilla elastic search consumer
- kafka connect sink elasticsearch
- kstreams: 
    - it filters data by user_followers
    - it counts words in every tweet
    - it counts total tweets by terms from a certain author

Notes:
* to run es_consumer_connector be sure to add guava.jar to classpath