// package advance.phasers;
//
// import java.util.concurrent.Phaser;
//
// public class PhasersExample {
//
//  public static void main(String[] args) {
//    Phaser phaser = new Phaser();
//    new Thread(new MyRunner(phaser)).start();
//    new Thread(new MyRunner(phaser)).start();
//  }
//
//  static class MyRunner implements Runnable {
//    Phaser phaser = null;
//
//    public MyRunner(Phaser phaser) {
//      this.phaser = phaser;
//      phaser.register();
//    }
//
//    @Override
//    public void run() {
//      System.out.println("MyRunner");
//      phaser.arriveAndAwaitAdvance();
//      try {
//        System.out.println("sleeping");
//        Thread.sleep(2000);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//      phaser.arriveAndAwaitAdvance();
//      phaser.arriveAndDeregister();
//    }
//  }
// }
