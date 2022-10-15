import java.util.Arrays;
import java.util.concurrent.Executor;

public class Lab2 {

  public static void main(final String[] arguments) throws InterruptedException {
    int[] intArr = new int[]{1,2,3,4,5};

    System.out.println(Arrays.toString(intArr));

    while(intArr.length > 1) {
      Task[] sumTasks = new Task[intArr.length / 2];
      Thread[] sumThreads = new Thread[intArr.length / 2];
      for (int i = 0; i < intArr.length; i++) {
        // stop if we are at the middle
        if(i + 1 > (intArr.length / 2)) break;

        int begElemIndex = i;
        int endElemIndex = intArr.length - i - 1;

        int begElem = intArr[begElemIndex];
        int endElem = intArr[endElemIndex];

        Task task = new Task(begElem, endElem);
        Thread th = new Thread(task);
        sumTasks[i] = task;
        sumThreads[i] = th;

        th.start();
      }
      
      for (int i = 0; i < sumTasks.length; i++) {
        sumThreads[i].join();
        intArr[i] = sumTasks[i].getValue();
      }
  
      intArr = Arrays.copyOfRange(intArr, 0, intArr.length - 1);

      System.out.println(Arrays.toString(intArr));
    }
    
    System.out.println("The sum of the array is: " + intArr[0]);
  }

  class Invoker implements Executor {
    @Override
    public void execute(Runnable r) {
      r.run();
    }
  }

  static class Task implements Runnable {
    private int oneElem;
    private int twoElem;
    private int sum;

    public Task(int oneElem, int twoElem) {
      this.oneElem = oneElem;
      this.twoElem = twoElem;
    }

    public void run() {
      try {
        System.out.println("Thread " + Thread.currentThread().getId()+ " is running");
        this.sum = this.oneElem + this.twoElem;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public int getValue() {
      return this.sum;  
    }
  }
}