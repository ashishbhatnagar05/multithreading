package advance.fairlockimplementation;

public class QueueObject {

  private boolean isNotified = false;

  public synchronized void doWait() {
    while (!isNotified) {
      try {
        wait();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    isNotified = false;
  }

  public synchronized void doNotify() {
    isNotified = true;
    notify();
  }
}
