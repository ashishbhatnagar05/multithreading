package basics.waitnotify;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WaitUsingExecutors {
  public static void main(String[] args) {

    SharedObject sharedObject = new SharedObject(); // shared object
    Runnable produceRunnable1 =
        () -> {
          try {
            sharedObject.produce();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        };
    Runnable produceRunnable2 =
        () -> {
          try {
            sharedObject.produce();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        };
    Runnable consumeRunnable =
        () -> {
          try {
            sharedObject.consume();
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        };
    ExecutorService executorService = Executors.newFixedThreadPool(3);
    executorService.execute(produceRunnable1);
    executorService.execute(produceRunnable2);
    executorService.execute(consumeRunnable);
    shutdown(executorService);
  }

  private static void shutdown(ExecutorService executorService) {
    executorService.shutdown();
    try {
      if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
        executorService
            .shutdownNow(); // if code doesnt completes in 10 seconds we will inturrupt the code
        // flow and terminate all threads
      }
    } catch (InterruptedException e) {
      executorService.shutdownNow();
    }
  }

  static class SharedObject {

    public void produce() throws InterruptedException {
      System.out.println("Inside produce");
      synchronized (this) {
        System.out.println("producer is running");
        System.out.println("calling wait using thread: " + Thread.currentThread().getName());
        wait();
        System.out.println("executed code after wait");
      }
    }

    public void consume() throws InterruptedException {
      System.out.println("Inside consume");
      Scanner s = new Scanner(System.in);
      synchronized (this) {
        System.out.println("Enter any key: ");
        s.nextLine();
        System.out.println("return key pressed");
        System.out.println("calling notify using thread: " + Thread.currentThread().getName());
        notifyAll();
        System.out.println("Sleep for 3 seconds");
        Thread.sleep(3000);
      }
    }
  }
}
