package advance.deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * http://tutorials.jenkov.com/java-concurrency/deadlock-prevention.html .Below is example of Deadlock
 * and how it occurs in multithreading. Shared is a class having two sharable instance variable a and
 * b both thread have access to these two variables Thread 1 access a and try to access b, but
 * thread a already has access to b,hence the deadlock.This could be prevented: 1- make method take
 * lock in same order 2--Lock timeout 3-- deadlock detection
 */
public class DeadLockExample {

  public static void main(String[] args) {
    Shared shared = new Shared();
    ExecutorService service = Executors.newCachedThreadPool();
    service.execute(() -> shared.method1());
    service.execute(() -> shared.method2());
    service.shutdown();
  }

  static class Shared {
    A a = new A();
    B b = new B();

    public void method1() {
      System.out.println(Thread.currentThread().getName() + " is inside method1");
      synchronized (a) {
        // sleeping(" is sleeping with A lock", 3000);
      }
      System.out.println(Thread.currentThread().getName() + " trying to lock B");
      synchronized (b) {
        System.out.println("locked B");
      }
    }

    public void method2() {
      System.out.println(Thread.currentThread().getName() + " is inside method1");
      synchronized (b) {
        sleeping(" is sleeping with B lock", 6000);
      }
      System.out.println(Thread.currentThread().getName() + " trying to lock A");
      synchronized (a) {
        System.out.println("locked B");
      }
    }

    private void sleeping(String s, int i) {
      System.out.println(Thread.currentThread().getName() + s);
      try {
        Thread.sleep(i);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
}

class B {}

class A {}
