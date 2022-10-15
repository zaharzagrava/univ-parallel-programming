import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.CyclicBarrier;

public class Lab31 {
  static private CyclicBarrier cyclicBarrier;
  static private boolean isAggregatorRun;

  public static void main(final String[] arguments) throws InterruptedException {
    int[] intArr = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

    System.out.println(Arrays.toString(intArr));

    int sum = 0;
    int chunkSize = 4;
    double as = Math.ceil((double) intArr.length / (double) chunkSize);
    int parts = (int) (as);

    System.out.println("chunkSize: " + chunkSize);
    System.out.println("parts: " + parts);

    Task[] sumTasks = new Task[parts];
    Thread[] sumThreads = new Thread[parts];
    AggregatorTask aggregatorTask = new AggregatorTask(sumTasks);
    cyclicBarrier = new CyclicBarrier(parts, aggregatorTask);
    for (int i = 0; i < parts; i++) {
      int[] sumPartsArr = Arrays.copyOfRange(intArr, chunkSize * i, Math.min(chunkSize * (i + 1), intArr.length));

      System.out.println(Arrays.toString(sumPartsArr));

      Task task = new Task(sumPartsArr);
      Thread th = new Thread(task);
      sumTasks[i] = task;
      sumThreads[i] = th;

      th.start();
    }

    while (isAggregatorRun == false) {}

    sum = aggregatorTask.getValue();

    System.out.println("The sum of the array is: " + sum);

  }

  class Invoker implements Executor {
    @Override
    public void execute(Runnable r) {
      r.run();
    }
  }

  static class Task implements Runnable {
    private int[] sumArr;
    private int sum;

    public Task(int[] sumArr) {
      this.sumArr = sumArr;
    }

    public void run() {
      try {
        System.out.println(Thread.currentThread().getName() + " is running");
        for (int i = 0; i < sumArr.length; i++) {
          this.sum += sumArr[i];
        }
        System.out.println(Thread.currentThread().getName() + " waiting for the rest");
        cyclicBarrier.await();

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
    private int sum;

    public AggregatorTask(Task[] tasks) {
      this.tasks = tasks;
    }

    public void run() {
      try {
        System.out.println("Aggregator task");
        for (int i = 0; i < this.tasks.length; i++) {
          sum += this.tasks[i].getValue();
        }

        isAggregatorRun = true;
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public int getValue() {
      return this.sum;
    }
  }
}