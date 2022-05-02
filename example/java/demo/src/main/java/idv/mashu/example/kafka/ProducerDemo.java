package idv.mashu.example.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProducerDemo {
  private final Logger logger = LoggerFactory.getLogger(ProducerDemo.class);
  private Properties p;

  public ProducerDemo() {
    String bootstrapServer = "localhost:9092";

    Properties p = new Properties();
    p.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
    p.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    p.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    p.setProperty(ProducerConfig.METRICS_RECORDING_LEVEL_CONFIG, "INFO");

    this.p = p;
  }

  public void startWithKey() {
    this.logger.info("Simple Kafka Producer with Key");

    KafkaProducer<String, String> producer = new KafkaProducer<>(p);

    this.logger.info("prepare record with topic \"java-example\" and \"message1\"");
    ProducerRecord<String, String> record = new ProducerRecord<>(
      "java-example",
      "key_1",
      "message1"
    );

    producer.send(record, new Callback() {
      public void onCompletion(RecordMetadata metadata, Exception e) {
        if (e == null) {
          System.out.println(
            "Received new metadata. \n" +
            "Topic: " + metadata.topic() + "\n" +
            "Key: " + record.key() + "\n" +
            "Partition: " + metadata.partition() + "\n" +
            "Offset: " + metadata.offset() + "\n" +
            "Timestamp: " + metadata.timestamp()
          );
        } else {
          System.out.println("Error while producing event");
        }
      } 
    });

    producer.close();
  }

  public void start() {
    this.logger.info("Simple kafka producer");

    KafkaProducer<String, String> producer = new KafkaProducer<>(p);

    this.logger.info("prepare record with topic \"java-example\" and \"message1\"");
    ProducerRecord<String, String> record = new ProducerRecord<>("java-example", "message1");

    // send data asynchronously 
    producer.send(record, new Callback() {
      public void onCompletion(RecordMetadata metadata, Exception e) {
        if (e == null) {
          System.out.println(
            "Received new metadata. \n" +
            "Topic:" + metadata.topic() + "\n" +
            "Partition: " + metadata.partition() + "\n" +
            "Offset: " + metadata.offset() + "\n" +
            "Timestamp: " + metadata.timestamp()
          );
        } else {
          System.out.println("Error while producing event"); 
        }
      }
    });

    // send data synchronously
    // producer.flush();

    // flush and close producer
    producer.close();
  }
}
