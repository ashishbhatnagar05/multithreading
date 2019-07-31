package basics.synchronization;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SynchronizationExample {

  public static void main(String[] args) {
    Shared shared = new Shared();
    ExecutorService service = Executors.newFixedThreadPool(2);
    // executeStaticMethodsConcurrently(service);
    // executeInstanceMethodsConcurrently(service, shared);
    executeEitherMethodsConcurrently(service, shared);
    service.shutdown();
    System.out.println("Completed");
  }

  private static void executeInstanceMethodsConcurrently(ExecutorService service, Shared shared) {
    service.execute(() -> shared.instanceMethod());
    service.execute(() -> shared.instanceMethod());
  }

  private static void executeStaticMethodsConcurrently(ExecutorService service) {
    service.execute(() -> Shared.staticMethod());
    service.execute(() -> Shared.staticMethod());
  }

  private static void executeEitherMethodsConcurrently(ExecutorService service, Shared shared) {
    service.execute(() -> Shared.staticMethod());
    service.execute(() -> shared.instanceMethod());
  }

  static class Shared {
    private int count = 0;

    public static synchronized void staticMethod() {
      System.out.println("Inside static method");
      try {
        System.out.println(Thread.currentThread().getName() + " is sleeping");
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    public synchronized void instanceMethod() {
      System.out.println("Inside instance method");
      try {
        System.out.println(Thread.currentThread().getName() + " is sleeping");
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
