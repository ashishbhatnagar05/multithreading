package advance.exchangers;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Exchnager class could be used so that the thread could excahnge the object among themselves */
public class ExchangeExample {

  public static void main(String[] args) {
    Exchanger exchanger = new Exchanger();
    ExecutorService service = Executors.newFixedThreadPool(2);
    service.submit(new MyRunner(exchanger, "A"));
    service.submit(new MyRunner(exchanger, "B"));
    service.shutdown();
  }

  static class MyRunner implements Runnable {
    Exchanger exchanger = null;
    Object obj = null;

    public MyRunner(Exchanger exchanger, Object obj) {
      this.exchanger = exchanger;
      this.obj = obj;
    }

    @Override
    public void run() {
      System.out.println("MyRunner");
      Object previous = obj;
      try {
        obj = exchanger.exchange(obj);
        System.out.println(
            Thread.currentThread().getName() + " exchanged " + previous + " for " + obj);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}
