package advance.simplelockimplementation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Below is a simple lock implementation Below implementation is not fair as all the threads keeps
 * waiting till their turn. It could happeen that a thread never gets its turn. This needs
 * improvement
 */
public class LockImplementationExample {
  public static void main(String[] args) {
    ExecutorService service = Executors.newCachedThreadPool();
    Synchronizer synchronizer = new Synchronizer();
    service.submit(() -> synchronizer.method1());
    service.submit(() -> synchronizer.method1());
    service.shutdown();
  }

  static class Synchronizer {
    private Lock lock = new Lock();

    public void method1() {
      System.out.println("Run the method");

      lock.lock();
      System.out.println("This code is locked now");
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("This code is unlocked");
      lock.unlock();
    }
  }
}
