package advance.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CountDownLatch object could be used by multiple Threads to contact each other In Below example,
 * the latch is initialized to 3, and we had the waiter waiting for signal.Once the latch count
 * decrements to 0, waiter will be released b
 */
public class CountDownLatchExample {

  public static void main(String[] args) {
    CountDownLatch countDownLatch = new CountDownLatch(3);
    ExecutorService service = Executors.newFixedThreadPool(2);
    service.submit(new Waiter(countDownLatch));
    service.submit(new Decrementer(countDownLatch));
    service.shutdown();
  }

  static class Decrementer implements Runnable {

    CountDownLatch latch = null;

    public Decrementer(CountDownLatch latch) {
      this.latch = latch;
    }

    @Override
    public void run() {
      countDown();
      countDown();
      countDown();
    }

    private void countDown() {
      try {
        Thread.sleep(2000);
        System.out.println("Counting down the latch");
        latch.countDown();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  static class Waiter implements Runnable {

    CountDownLatch latch = null;

    public Waiter(CountDownLatch latch) {
      this.latch = latch;
    }

    @Override
    public void run() {
      System.out.println("Waiter is waiting");
      try {
        latch.await();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Waiter released");
    }
  }
}
