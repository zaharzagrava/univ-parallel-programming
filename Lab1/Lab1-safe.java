import java.util.Arrays;
import java.util.concurrent.Executor;

public class Lab1 {

  public static void main(final String[] arguments) throws InterruptedException {
    int[] intArr = new int[]{1,2,3,4,5,6,7,8,9,10, 11};

    int sum = 0;
    int chunkSize = 4;
    double as = Math.ceil((double)intArr.length / (double)chunkSize);
    int parts = (int)(as);

    System.out.println("chunkSize: " + chunkSize);
    System.out.println("parts: " + parts);

    Task[] sumTasks = new Task[0];
    Thread[] sumThreads = new Thread[0];
    for (int i = 0; i < parts; i++) {
      int[] sumPartsArr = Arrays.copyOfRange(intArr, chunkSize * i, Math.min(chunkSize * (i + 1), intArr.length));
      
      System.out.println(Arrays.toString(sumPartsArr));

      Task task = new Task(sumPartsArr);
      Thread th = new Thread(task);
      th.start();
      th.join();
      int partSum = task.getValue();

      sum += partSum;
    }

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
        for (int i = 0; i < sumArr.length; i++) {
          this.sum += sumArr[i];
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    public int getValue() {
      return this.sum;  
    }
  }
}