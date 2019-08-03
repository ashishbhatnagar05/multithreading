package advance.reentrantlocks;

public class ReEntrantLockExample {

  static class Lock {
    private boolean isLocked = false;
    private Thread lockedBy = null;
    private int lockCount = 0;

    public synchronized void lock() {
      while (isLocked && Thread.currentThread() != lockedBy) {
        try {
          wait();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      isLocked = true;
      lockCount++;
      lockedBy = Thread.currentThread();
    }

    public synchronized void unlock() {
      if (Thread.currentThread() == lockedBy) {
        lockCount--;
        if (lockCount == 0) {
          isLocked = false;
          notify();
        }
      }
    }
  }
}
