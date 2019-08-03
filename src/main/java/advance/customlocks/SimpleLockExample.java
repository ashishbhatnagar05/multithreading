package advance.customlocks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Simple lock implementation Wont work if their are nested method calls. Check Reentrant lock for
 * that case
 */
public class SimpleLockExample {
  public static void main(String[] args) {
    Tester tester = new Tester();
    ExecutorService service = Executors.newCachedThreadPool();
    service.execute(() -> tester.method());
    service.execute(() -> tester.method());
    service.shutdown();
  }

  static class Tester {
    Lock lock = new Lock();

    public void method() {
      System.out.println("Inside method");
      lock.lock();
      try {
        System.out.println("Processing ...");
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Unlocking ...");
      lock.unlock();
    }
  }

  static class Lock {
    private boolean isLocked = false;

    public synchronized void lock() {
      while (isLocked) {
        try {
          wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      isLocked = true;
    }

    public synchronized void unlock() {
      isLocked = false;
      notify();
    }
  }
}
