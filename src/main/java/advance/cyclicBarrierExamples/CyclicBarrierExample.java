package advance.cyclicBarrierExamples;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This could be use to do some task defined in the cyclic barrier. When a thread calls await,
 * cyclic barrier executes the runnable defined(cyclicBarrier1,cyclicBarrier2)
 */
public class CyclicBarrierExample {
  public static void main(String[] args) {
    CyclicBarrier cyclicBarrier1 =
        new CyclicBarrier(1, () -> System.out.println("Executing action at cyclicBarrier1 "));
    CyclicBarrier cyclicBarrier2 =
        new CyclicBarrier(1, () -> System.out.println("Executing action at cyclicBarrier2 "));
    ExecutorService service = Executors.newFixedThreadPool(2);
    service.submit(new CyclicBarrierRunnable(cyclicBarrier1, cyclicBarrier2));
    service.submit(new CyclicBarrierRunnable(cyclicBarrier1, cyclicBarrier2));
    service.shutdown();
  }

  static class CyclicBarrierRunnable implements Runnable {
    CyclicBarrier cyclicBarrier1 = null;
    CyclicBarrier cyclicBarrier2 = null;

    public CyclicBarrierRunnable(CyclicBarrier cyclicBarrier1, CyclicBarrier cyclicBarrier2) {
      this.cyclicBarrier1 = cyclicBarrier1;
      this.cyclicBarrier2 = cyclicBarrier2;
    }

    @Override
    public void run() {
      System.out.println("Running the CyclicBarrierRunnable");
      await(" waiting at cyclicBarrier1", cyclicBarrier1);
      await(" waiting at cyclicBarrier2", cyclicBarrier2);
      System.out.println(Thread.currentThread().getName() + " done!");
    }

    private void await(String s, CyclicBarrier cyclicBarrier) {
      try {
        System.out.println(Thread.currentThread().getName() + s);
        cyclicBarrier.await();
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      } catch (BrokenBarrierException e) {
        e.printStackTrace();
      }
    }
  }
}
