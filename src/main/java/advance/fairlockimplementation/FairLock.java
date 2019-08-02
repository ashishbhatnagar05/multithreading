package advance.fairlockimplementation;

import java.util.ArrayList;
import java.util.List;

/** Bit complicated and slow but it kind of resolves the issue of old implementation */
public class FairLock {
  private boolean isLocked = false;
  private Thread lockingThread = null;
  private List<QueueObject> waitingThreads = new ArrayList<>();

  public void lock() throws InterruptedException {
    QueueObject queueObject = new QueueObject();
    synchronized (this) {
      waitingThreads.add(queueObject);
    }
    boolean isLockedForThisThread = true;
    while (isLockedForThisThread) {
      synchronized (this) {
        isLockedForThisThread = isLocked || waitingThreads.get(0) != queueObject;
        if (!isLockedForThisThread) {
          isLocked = true;
          waitingThreads.remove(queueObject);
          lockingThread = Thread.currentThread();
          return;
        }
      }
    }

    try {
      queueObject.wait();
    } catch (InterruptedException e) {
      synchronized (this) {
        waitingThreads.remove(queueObject);
      }
      throw e;
    }
  }

  public void unlock() {
    if (lockingThread != Thread.currentThread()) {
      throw new IllegalMonitorStateException();
    }
    isLocked = false;
    lockingThread = null;
    if (waitingThreads.size() > 0) {
      waitingThreads.get(0).notify();
    }
  }
}
