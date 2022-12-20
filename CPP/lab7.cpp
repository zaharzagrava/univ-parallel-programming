#include <iostream>
#include <vector>
#include <string>
#include <cmath>
#include <omp.h>
#include <unistd.h>

using namespace std;

int Lab1(int *sumArray, int size)
{
  int sum = 0;
  #pragma omp parallel for reduction (+:sum)
  for(int i = 0; i < size; i++)
  {
    sum += sumArray[i];
  }

  cout << "Lab1 | The sum of the array is: " << sum << endl;
}

int Lab2(int *sumArray, int argSize)
{
  int localSize = argSize;

  const int partSize = 3;
  while(localSize > 1)
  {
    #pragma omp parallel for shared(sumArray)
    for (int summationIndex = 0; summationIndex < localSize / 2; summationIndex++)
    {
      sumArray[summationIndex] = sumArray[summationIndex] + sumArray[localSize - summationIndex - 1];
    }

    // for (int i = 0; i < localSize; i++) 
    //   cout << sumArray[i] << "|";
    // cout << endl;

    localSize = localSize / 2 + localSize % 2;
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
