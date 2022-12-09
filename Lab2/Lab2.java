import java.util.Arrays;
import java.util.concurrent.Executor;

public class Lab2 {

  private static int[] createLongMyArr() {
    int size = 10000;

    int[] mas = new int[size];

    for (int i = 0; i < size; i++)
        mas[i] = i;

    return mas;
  }

  private static int[] createMyArr() {
    return new int[]{1,2,3,4,5};
  }

  public static void main(final String[] arguments) throws InterruptedException {
    int[] intArr = Lab2.createMyArr().clone();

    int size = intArr.length;

    int threadsCount = 50;
    Task[] sumTasks = new Task[threadsCount];
    Thread[] sumThreads = new Thread[threadsCount];
    while(size > 1) {
      int summationIndex = 0;
      while(summationIndex < size / 2) {
        System.out.println("--- Thread run ---");

        int threadsIndex = 0;
        System.out.println(size / 2);
        System.out.println(summationIndex < size / 2);
        while(threadsIndex < threadsCount && summationIndex < size / 2) {
          int begElemIndex = summationIndex;
          int endElemIndex = size - summationIndex - 1;
  
          int begElem = intArr[begElemIndex];
          int endElem = intArr[endElemIndex];
  
          Task task = new Task(begElem, endElem);
          Thread th = new Thread(task);
          sumTasks[threadsIndex] = task;
          sumThreads[threadsIndex] = th;
  
          th.start();

          threadsIndex++; summationIndex++;
        }
        
        // Start counting from first index where we started creating threads
        for (int i = 0; i < threadsIndex; i++) {
          sumThreads[i].join();
          intArr[summationIndex - threadsIndex + i] = sumTasks[i].getValue();
        }

        System.out.println(Arrays.toString(intArr));
      }
  
      size = size / 2 + size % 2;

      System.out.println(size);
    }
    
    System.out.println("The sum of the array is: " + intArr[0]);

    // Calculate real sum for comparison
    long realSum = 0;
    int[] intArrBench = Lab2.createLongMyArr().clone();
    for (int i = 0; i < intArrBench.length; i++) {
      realSum += intArrBench[i];
    }

    System.out.println("The real sum of the array is: " + realSum);
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