import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Lab32 {
  static private CyclicBarrier cyclicBarrier;
  static private boolean isAggregatorRun;

  public static void main(final String[] arguments) throws InterruptedException {
    int[] intArr = new int[]{1,2,3,4,5};

    System.out.println(Arrays.toString(intArr));

    while(intArr.length > 1) {
      System.out.println("-------------------------");
      isAggregatorRun = false;

      int numOfThreads = intArr.length / 2;

      Task[] sumTasks = new Task[numOfThreads];
      Thread[] sumThreads = new Thread[numOfThreads];

      AggregatorTask aggregatorTask = new AggregatorTask(sumTasks, intArr);
      cyclicBarrier = new CyclicBarrier(numOfThreads, aggregatorTask);
      for (int i = 0; i < intArr.length; i++) {
        // stop if we are at the middle
        if(i + 1 > (intArr.length / 2)) break;

        int begElemIndex = i;
        int endElemIndex = intArr.length - i - 1;

        int begElem = intArr[begElemIndex];
        int endElem = intArr[endElemIndex];

        Task task = new Task(begElem, endElem, cyclicBarrier);
        Thread th = new Thread(task);
        th.setName("Thread " + i);
        sumTasks[i] = task;
        sumThreads[i] = th;

        th.start();
      }

      while(isAggregatorRun == false) {}
      
      intArr = aggregatorTask.getValue();

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
    private CyclicBarrier cyclicBarrier;
    private int oneElem;
    private int twoElem;
    private int sum;

    public Task(int oneElem, int twoElem, CyclicBarrier cyclicBarrier) {
      this.oneElem = oneElem;
      this.twoElem = twoElem;
      this.cyclicBarrier = cyclicBarrier;
    }

    public void run() {
      try {
        System.out.println(Thread.currentThread().getName()+ " is running");
        this.sum = this.oneElem + this.twoElem;
        System.out.println(Thread.currentThread().getName()+ " waiting for the rest");
        cyclicBarrier.await();
        // System.out.println(Thread.currentThread().getName()+ " has finished waiting for the rest");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public int getValue() {
      return this.sum;  
    }
  }

  static class AggregatorTask implements Runnable {
    private Task[] tasks;
    private int[] intArr;

    public AggregatorTask(Task[] tasks, int[] intArr) {
      this.tasks = tasks;
      this.intArr = intArr;
    }

    public void run() {
      try {
        System.out.println("Aggregator task");
        for (int i = 0; i < this.tasks.length; i++) {
          intArr[i] = this.tasks[i].getValue();
        }
        
        intArr = Arrays.copyOfRange(intArr, 0, intArr.length - 1);
        isAggregatorRun = true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public int[] getValue() {
      return this.intArr;  
    }
  }
}