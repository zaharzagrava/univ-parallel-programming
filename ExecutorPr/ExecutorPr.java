import java.util.concurrent.*;

class ExecutorPr {
  public static void main(String[] args)
  {
    int n = 8; // Number of threads
    for (int i = 0; i < n; i++) {
      Executor exe = new Invoker();
      exe.execute(() -> {
        // task to be performed
        System.out.println("T " + Thread.currentThread().getId() + " is running");
      });
      exe.execute(new MultithreadingDemo());
    }
  }
}

class Invoker implements Executor {
  @Override
  public void execute(Runnable r) {
      r.run();
  }
}

class MultithreadingDemo implements Runnable {
  public void run()
  {
      try {
          // Displaying the thread that is running
          System.out.println(
              "Thread  ss" + Thread.currentThread().getId()
              + " is running");
      }
      catch (Exception e) {
          // Throwing an exception
          System.out.println("Exception is caught");
      }
  }
}
