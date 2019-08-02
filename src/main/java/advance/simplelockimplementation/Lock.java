package advance.simplelockimplementation;

/** Below is an implementation of a simple lock */
public class Lock {

  private boolean isLocked = false;
  private Thread lockingThread = null;

  public synchronized void lock() {
    while (isLocked) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    isLocked = true;
    lockingThread = Thread.currentThread();
  }

  public synchronized void unlock() {
    if (lockingThread != Thread.currentThread()) {
      throw new IllegalMonitorStateException();
    }
    isLocked = false;
    lockingThread = null;
    notify();
  }
}
