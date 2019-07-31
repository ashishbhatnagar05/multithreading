package advance.threadLocals;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// The ThreadLocal class in Java enables you to create variables that can only be read and written
// by the same thread. Thus, even if two threads are executing the same code, and the code has a
// reference to a ThreadLocal variable, then the two threads cannot see each other's ThreadLocal
// variables.
public class ThreadLocalExample {

  public static void main(String[] args) {
    ThreadLocal<String> threadLocal = new ThreadLocal();
    ExecutorService service = Executors.newFixedThreadPool(5);
    Runnable command =
        () -> {
          System.out.println(Thread.currentThread().getName() + " Updating the value");
          threadLocal.set(String.valueOf(Math.random() * 1000));
          try {
            Thread.sleep(3000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          System.out.println(Thread.currentThread().getName() + " value: " + threadLocal.get());
        };
    service.execute(command);
    service.execute(command);
    service.execute(command);
    service.execute(command);
    service.execute(command);
    service.shutdown();
  }
}

//        pool-1-thread-1 Updating the value
//        pool-1-thread-2 Updating the value
//        pool-1-thread-3 Updating the value
//        pool-1-thread-4 Updating the value
//        pool-1-thread-5 Updating the value
//        pool-1-thread-2 value: 776.537793960497
//        pool-1-thread-1 value: 610.2801756996528
//        pool-1-thread-5 value: 654.2906060479489
//        pool-1-thread-4 value: 204.14129880933763
//        pool-1-thread-3 value: 641.5392027205442
