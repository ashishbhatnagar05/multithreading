package advance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

// Below class shows difference between executor service and completion service. With completion
// service we could get the completed task
public class ExecutorCompletionServiceExample {

  public static void main(String[] args) {
    // usingExecutorService();
    // usingCompletionService();
  }

  private static void usingExecutorService() {
    ExecutorService service = Executors.newFixedThreadPool(10);
    List<Callable<String>> callables =
        Arrays.asList(
            new DelayedCallable("slow thread", 8000), new DelayedCallable("fast thread", 100));
    List<Future<String>> futures = new ArrayList<>();
    for (Callable<String> callable : callables) {
      futures.add(service.submit(callable));
    }
    try {
      for (Future<String> future : futures) {
        System.out.println(future.get()); // need to wait for longer thread to process
      }
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }

  private static void usingCompletionService() {
    ExecutorService WORKER_THREAD_POOL = Executors.newFixedThreadPool(10);
    ExecutorCompletionService<String> service = new ExecutorCompletionService<>(WORKER_THREAD_POOL);

    List<Callable<String>> callables =
        Arrays.asList(
            new DelayedCallable("slow thread", 8000), new DelayedCallable("fast thread", 100));

    for (Callable<String> callable : callables) {
      service.submit(callable);
    }
    try {
      System.out.println(
          service.take().get()); // we are getting response from the first executed thread.In normal
      // ExecutorService, we need to ait for one thread to complete, if it is slow, it will take lot
      // of tme
      System.out.println(service.take().get());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }

  static class DelayedCallable implements Callable {

    private String type;
    private int delay;

    public DelayedCallable(String type, int delay) {
      this.type = type;
      this.delay = delay;
    }

    @Override
    public String call() throws Exception {
      System.out.println(Thread.currentThread().getName() + " going to sleep");
      Thread.sleep(delay);
      return "Executed " + type + " thread with delay: " + delay;
    }
  }
}
