## This repo is a pet project. 

### The purpose of the project is to prove Java, Kafka, Prometheus and Grafana knowledge. 

### The main components are:
- Twitter
- Kafka
- ElasticSearch
- Prometheus
- Grafana

### Overview:
- For the twitter, I used [Hosebird Client](https://github.com/twitter/hbc)
- Kafka 2.5.0 is running locally
- For ElasticSearch I took PaaS https://bonsai.io/ 
- Prometheus 2.20.1 is running locally
- Grafana 7.1.5 is running locally

### It has the following modules:
- vanilla twitter producer
- kafka connect twitter
- vanilla elastic search consumer
- kafka connect sink elasticsearch
- kstreams: 
    - it filters data by user_followers
    - it counts words in every tweet
    - it counts total tweets by terms from a certain author
    
### Vanilla producer and consumer are tested. 

### Notes:
* to run es_consumer_connector be sure to add guava.jar to classpath

### TODO:
* add how to page