package idv.mashu.example.kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.InterruptException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsumerDemo {
  private final Logger logger = LoggerFactory.getLogger(ProducerDemo.class);
  private Properties p;

  public ConsumerDemo() {
    String server = "localhost:9092";
    String groupId = "java-consumer";

    Properties p = new Properties();
    p.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, server);
    p.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    p.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    p.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    p.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

    this.p = p;
  } 

  public void start() {
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(p);

    final Thread mainThread = Thread.currentThread();
    Runtime.getRuntime().addShutdownHook(new Thread() {
      public void run() {
        // trigger exception `WakeupException`
        consumer.wakeup();

        try {
          // wait the main thread to finish
          mainThread.join();
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          System.out.println("program finished");
        }
      }
    });

    String topic = "java-example";
    consumer.subscribe(Arrays.asList(topic));

    try {
      while (true) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

        for (ConsumerRecord<String, String> record: records) {
          this.logger.info("Key: {}, Value: {}", record.key(), record.value());
          this.logger.info("Partition: {}, Offset: {}", record.partition(), record.offset());
        }
      }
    // } catch (WakeupException e) {
    //   this.logger.info("wakeup exception detected");
    } catch (Exception e) {
      this.logger.error("unexpected exception", e);
    } finally {
      consumer.close();
      this.logger.info("main thread finished");
    }
  }
}
