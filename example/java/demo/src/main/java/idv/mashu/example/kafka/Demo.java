package idv.mashu.example.kafka;

public class Demo {

  public static void main(String[] args) {
    // demo for producer
    // ProducerDemo producerDemo = new ProducerDemo();
    // producerDemo.start();

    // demo for consumer
    ConsumerDemo consumerDemo = new ConsumerDemo();    
    consumerDemo.start();

    // final Thread thread = Thread.currentThread();
    // try {
    //   thread.join();
    // } catch (InterruptedException e) {
    //   System.out.println("exception");
    // }
  }
}
