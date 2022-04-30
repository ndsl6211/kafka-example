# Kafka

## What is Kafka?

Kafka is an implementation of message queue, which can be applied on event
streaming, any application that rely heavily on event driven design, or event
sourcing of Domain Driven Design.

[introduction to kafka from official website](https://kafka.apache.org/intro)

## Basic Concepts of Kafka
- a **broker** represent a server node in Kafka cluster
- **producer** can produce an event to a **topic**
- **consumer** can consume an event from a **topic**
- a **topic** can have multiple **partitions** located in different **broker**
- every **partition** will have a leader and some followers
  - the leader is responsible for handling provider's and consumer's request
  - the follower is responsible for backup data from the leader
  - the number of follower depends on the number of broker in Kafka cluster

### Basic APIs and Functionality Provided by Kafka
- Admin API
  - topic management
  - broker management
- Producer API
  - publish events to Kafka topics
- Consumer API
  - subscribe and consume events from Kafka topics
- Streams API
  - process events on Kafka server directly without consuming it
- Connect API
  - (to be updated..)


## To Run Kafka Broker and Zookeeper
```
  docker-compose up -d
```
the docker-compose file will start a single node Kafka broker and a zookeeper
server

### Kafka Broker

the server to handle incoming producer and consumer

### Zookeeper Server

- broker detection
- consumer addition and removal
- maintain the leader-follower relationship of partition, that is, if a leader
  of a partition dies, it will hold an election to select a new follower as
  leader
- maintain metadata for topic and partition
  
## Example from CLI

### Producer

to produce event to a topic:
```
  $ kafka-console-producer --bootstrap-server localhost:9092 --topic mashu
```
`--bootstrap-server`: the server wanna connect to
`--topic`: the topic wanna product to

the command above will enter an interaction mode to let us keep producing event
to the topic

### Consumer

to consume event from a topic:
```
  $ kafka-console-consumer --bootstrap-server localhost:9092 --topic mashu
```
`--bootstrap-server`: the server wanna connect to
`--topic`: the topic wanna consume from

the command above will enter an interaction mode to let us keep consuming event
to the topic, but only the message received after start consuming (no history)

if want to consume the history, we can add flag `--from-beginning`
```
  $ kafka-console-consumer \
    --bootstrap-server localhost:9092 \
    --topic mashu \
    --from-beginning
```

## Kafka Language Client

### Golang
- sarama
  - [doc](https://pkg.go.dev/github.com/Shopify/sarama#section-readme)
  - [github](https://github.com/Shopify/sarama)

to install it:
```
  $ go get -u github.com/Shopify/sarama
```

### Java

### Python