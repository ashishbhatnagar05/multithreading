package advance.lockExample;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/** Read Write Lock example */
public class ReadWriteLockExample {
  public static void main(String[] args) {
    Shared shared = new Shared();
    ExecutorService service = Executors.newCachedThreadPool();
    service.submit(() -> shared.reading());
    service.submit(() -> shared.reading());
    service.submit(() -> shared.writing());
    service.submit(() -> shared.reading());
    service.shutdown();
  }

  static class Shared {

    ReadWriteLock lock = new ReentrantReadWriteLock();

    public void reading() {
      System.out.println("method1");

      lock.readLock().lock();
      // since their is no write lock , first two threads will access this part concurrently
      System.out.println("Inside Read lock");
      try {
        System.out.println("Sleeping for 3 sec with read lock");
        Thread.sleep(3000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      lock.readLock().unlock();

      System.out.println("Read Lock released");
    }

    public void writing() {
      // this write thread will wait for 2 reading threads to complete reading
      lock.writeLock().lock();
      // now 3 thread has write lock, hence 4 thread needs to wait for 5 seconds to read
      System.out.println("Inside Write lock");
      try {
        System.out.println("Sleeping for 5 sec with write lock");
        Thread.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      lock.writeLock().unlock();

      System.out.println("Write Lock released");
    }
  }
}
