package advance.nestedMonitorLock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Below is Nested Monitor lock The first thread calls lock, second one holds the this lock Hence
 * first one cant call unlock method. a nested monitor lockout occurs exactly by two threads taking
 * the locks in the same order. this could happen in our fairlock implementation
 */
public class NestedLockExample {

  public static void main(String[] args) {
    Testing testing = new Testing();
    ExecutorService service = Executors.newCachedThreadPool();
    service.submit(() -> testing.test());
    service.submit(() -> testing.test());
    service.shutdown();
  }

  static class Testing {
    Lock lock = new Lock();

    public void test() {
      System.out.println("Inside Method");
      lock.lock();
      try {
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("Trying to leave lock");
      lock.unlock();
      System.out.println("Left lock");
    }
  }

  static class Lock {
    private MonitorObject monitorObject = new MonitorObject();
    private boolean isLocked = false;

    public void lock() {
      synchronized (this) {
        while (isLocked) {
          synchronized (monitorObject) {
            try {
              monitorObject.wait();
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
          }
        }
        isLocked = true;
      }
    }

    public void unlock() {
      synchronized (this) {
        isLocked = false;
        synchronized (monitorObject) {
          monitorObject.notify();
        }
      }
    }
  }
}

class MonitorObject {}
