# Kafka

## What is Kafka?

Kafka is an implementation of message queue, which can be applied on event
streaming, any application that rely heavily on event driven design, or event
sourcing of Domain Driven Design.

[introduction to kafka from official website](https://kafka.apache.org/intro)

## Why Kafka?

### Compare to HTTP Request
- a http request is synchronous, that is, the sender have to wait for the
  response sent back from the server.
- If service B, C have to send their log to service A (maybe A is a log
  warehouse), and if A provide http interface, then B, C should send request to
  A through HTTP. It's not a big problem, but if there are lots of services like
  B and C want to store their log into A, it will be a huge burden if the all
  the clients send request to A through HTTP.

  Solution:
    We can use Kafka to **decouple** the log storage server and client. When a
    client want to use service A to store its log, it just push the log message
    (event) to a Kafka topic, and service A will go to consume (handle) it as
    soon as possible. In addition, if there are large numbers of service that
    want to use service A, A will not be exhausted by the large inbound traffic.

- In contrast, consider a scenario that if a service A have to constantly send
  message to a number of different services, say service B, C, D...
  In the case of using HTTP, service A must know the detail of each service that
  event will be sent to, it's a nightmare for management and maintenance.
  However, if we choose Kafka in this scenario, everything will be better.

- The use of message queue can let the consumer to decide the **consume rate**
  to data itself, the client just have to send the message to the messaging
  server, and then it doesn't need to care about anything about that. The rest
  is the work of the consumer

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
```
  <dependency>
    <groupId>org.apache.kafka</groupId>
    <artifactId>kafka-clients</artifactId>
    <version>3.0.0</version>
  </dependency>
```

### Python


## Connect to Kafka Broker in Docker

[Baeldung](https://www.baeldung.com/kafka-docker-connection)
[Confluent](https://www.confluent.io/blog/kafka-client-cannot-connect-to-broker-on-aws-on-docker-etc/)