package idv.mashu.example.kafka;

public class Demo {

  public static void main(String[] args) {
    String server = "192.168.1.114:9092";
    String topic = "java-example";

    // demo for producer
    ProducerDemo producerDemo = new ProducerDemo(server, topic);
    producerDemo.start();

    // demo for consumer
    // ConsumerDemo consumerDemo = new ConsumerDemo(server, topic);
    // consumerDemo.start();
  }
}
