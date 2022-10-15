package Multithread;
// // By extending Thread
// class MultithreadingDemo extends Thread {
//   public void run() {
//     try {
//       System.out.println("Thread" + Thread.currentThread().getId() + " is running");
//     } catch (Exception e) {
//       System.out.println("Exception is caught");
//     }
//   }
// }

// public class Multithread {
//   public static void main(String[] args) {
//     int n = 8;
//     for (int i = 0; i < n; i++) {
//       MultithreadingDemo object = new MultithreadingDemo();
//       object.start();
//     }
//   }
// }

// By extending Runnable
class MultithreadingDemo implements Runnable {
  public void run()
  {
      try {
          // Displaying the thread that is running
          System.out.println(
              "Thread " + Thread.currentThread().getId()
              + " is running");
      }
      catch (Exception e) {
          // Throwing an exception
          System.out.println("Exception is caught");
      }
  }
}

class Multithread {
  public static void main(String[] args)
  {
      int n = 8; // Number of threads
      for (int i = 0; i < n; i++) {
          Thread object
              = new Thread(new MultithreadingDemo());
          object.start();
      }
  }
}
