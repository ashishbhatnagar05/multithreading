package advance.lockExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Simple lock implementation. the counter increments with each lock.lock() and decrements with
 * lock.unlock()
 */
public class SimpleLockExample {
  public static void main(String[] args) {
    Shared shared = new Shared();
    ExecutorService service = Executors.newCachedThreadPool();
    service.submit(() -> shared.method1());
    service.submit(() -> shared.method1());
    service.shutdown();
  }

  static class Shared {
    private Lock lock = new ReentrantLock(true); // give access to longest waiting thread

    public void method1() {
      System.out.println("method1");
      lock.lock();
      System.out.println("Processing for 2 sec");
      process();
      System.out.println("Processed");
    }

    private void process() {
      try {
        Thread.sleep(2000);
        lock.lock(); // take lock again
        System.out.println("processing for 3 sec again");
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } finally {
        lock.unlock();
        lock.unlock();
      }
    }
  }
}
