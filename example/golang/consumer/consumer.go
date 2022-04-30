package main

import (
	"fmt"
	"log"
	"os"
	"os/signal"

	"github.com/Shopify/sarama"
)

func main() {
	consumer, err := sarama.NewConsumer(
		[]string{"localhost:9092"},
		sarama.NewConfig(),
	)
	if err != nil {
		log.Fatalln(err)
	}

	defer func() {
		if err := consumer.Close(); err != nil {
			log.Fatalln(err)
		}
	}()

	// list topics
	topics, err := consumer.Topics()
	if err != nil {
		log.Fatalln(err)
	}
	fmt.Println(topics)
	// ↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	// the topic `__consumer_offsets` is a built-in topic, which will record the
	// offset for every consumer, representing the current event the consumer is
	// consuming

	partitionConsumer, err := consumer.ConsumePartition(
		"axonboard",
		0,
		sarama.OffsetOldest,
	)

	signals := make(chan os.Signal, 1)
	signal.Notify(signals, os.Interrupt)

	consumed := 0
ConsumerLoop:
	for {
		select {
		case msg := <-partitionConsumer.Messages():
			log.Printf("consumed message [%s] at offset [%d]\n", string(msg.Value), msg.Offset)
			consumed++
		case <-signals:
			break ConsumerLoop
		}
	}
	// the `select` can only be used when fetching data from channels
	// it will block until one of its cases can run (one of the cases of channel
	// receive a message), then execute that case.

	log.Printf("Total message consumed: %d\n", consumed)
}
