package advance.lockimplementation;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
