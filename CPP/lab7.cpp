#include <iostream>
#include <vector>
#include <string>
#include <cmath>
#include <omp.h>
#include <unistd.h>

using namespace std;

int Lab1(int *sumArray, int size)
{
  const int partSize = 3;

  // cout << "Chunk size is: " << (int)ceil((double)size / (double)partSize) << endl;

  int partsCount = (int)ceil((double)size / (double)partSize);
  int chunkSums[partsCount] = {};
  int sum = 0;

  #pragma omp parallel for reduction (+:sum)
  for(int i = 0; i < partsCount; i++)
  {
    // usleep(5000 * omp_get_thread_num()); // do this to avoid race condition while printing
    // std::cout << "Num of thrs: " << omp_get_num_threads() << " Curr: " << omp_get_thread_num() << std::endl;
    int partFirstIndex = partSize * i;
    int partLastIndex = min(partSize * (i + 1), size);

    // cout << "I: [" << partFirstIndex << ", " << partLastIndex << "]" << endl;

    for (int j = partFirstIndex; j < partLastIndex; j++)
    {
      sum += sumArray[j];
      // cout << "Sum elem: " << sumArray[j] << " Sum: " << sum << endl;
    }
  }

  cout << "Lab1 | The sum of the array is: " << sum << endl;
}

int Lab2(int *sumArray, int argSize)
{
  int size = argSize;

  const int partSize = 3;
  const int threadsCount = 50;

  while(size > 1)
  {
    int summationIndex = 0;
    while(summationIndex < size / 2)
    {
      // cout << summationIndex << " " << size << " " << endl;
      int threadsIndex = 0;

      int taskData[threadsCount][2] = {};
      // cout << size << 2 << endl;
      while(threadsIndex < threadsCount && summationIndex < size / 2)
      {
        int begElemIndex = summationIndex;
        int endElemIndex = size - summationIndex - 1;

        taskData[threadsIndex][0] = sumArray[begElemIndex];
        taskData[threadsIndex][1] = sumArray[endElemIndex];

        threadsIndex++; summationIndex++;
      }

      // for (int i = 0; i < size && i < threadsCount; i++) 
      //   cout << taskData[i][0] << " " << taskData[i][1] << "|";
      // cout << endl;

      #pragma omp parallel for shared(sumArray)
      for(int i = 0; i < threadsIndex; i++)
      {
        sumArray[summationIndex - threadsIndex + i] = taskData[i][0] + taskData[i][1];
      }

      // for (int i = 0; i < size; i++) 
      //   cout << sumArray[i] << "|";
      // cout << endl;
    }

    size = size / 2 + size % 2;
  }

  cout << "Lab2 | The sum of the array is: " << sumArray[0] << endl;
}

int main()
{
  int sumArray14[14] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14};
  int sumArray5[5] = {1,2,3,4,5};
  int sumArrayLong[10000];
  for (int i = 0; i < 10000; i++)
      sumArrayLong[i] = i;

  // for (int i = 0; i < 10000; i++) 
  //   cout << sumArrayLong[i] << "|";
  // cout << endl;

  Lab1(sumArrayLong, sizeof(sumArrayLong) / sizeof(int));
  Lab2(sumArrayLong, sizeof(sumArrayLong) / sizeof(int));
}
