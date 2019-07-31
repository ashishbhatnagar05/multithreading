package advance.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomSemaphoreExample {
  public static void main(String[] args) {
    CustomSemaphore semaphore = new CustomSemaphore();

    ExecutorService service = Executors.newFixedThreadPool(2);
    Runnable command =
        () -> {
          System.out.println(Thread.currentThread().getName() + " Aquire lock");
          semaphore.aquire();
          try {
            Thread.sleep(5000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println(Thread.currentThread().getName() + " Release lock");
          semaphore.release();
        };
    service.execute(command);
    service.execute(command);
  }

  static class CustomSemaphore {
    private boolean isLocked = false;

    public synchronized void aquire() {
      while (isLocked) {
        try {
          wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      isLocked = true;
    }

    public synchronized void release() {
      isLocked = false;
      notify();
    }
  }
}
