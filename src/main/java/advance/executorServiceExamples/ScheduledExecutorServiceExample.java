package advance.executorServiceExamples;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** Run a task at particular delay/period */
public class ScheduledExecutorServiceExample {

  public static void main(String[] args) {
    ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
    System.out.println("Running ScheduledExecutorService");
    service.schedule(() -> System.out.println("Welcome to the World"), 5, TimeUnit.SECONDS);
    // service.shutdown(); // since above task is submitted already the service didnt shut down
    service.scheduleAtFixedRate(
        () -> System.out.println("Running every 3 seconds"),
        5,
        5,
        TimeUnit.SECONDS); // this task execute every 3 seconds interval

    try {
      Thread.sleep(20000);
      service.shutdown(); // shutdown main thread after 20 seconds
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
