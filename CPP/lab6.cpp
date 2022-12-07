#include <iostream>
#include <vector>
#include <string>
#include <cmath>

using namespace std;

int Lab1(int *sumArray, int size)
{
  const int partSize = 3;

  // cout << "Chunk size is: " << (int)ceil((double)size / (double)partSize) << endl;

  int partsCount = (int)ceil((double)size / (double)partSize);
  int chunkSums[partsCount] = {};
  int sum = 0;

  for(int i = 0; i < partsCount; i++)
  {
    int partFirstIndex = partSize * i;
    int partLastIndex = min(partSize * (i + 1), size);
    for (int j = partFirstIndex; j < partLastIndex; j++)
    {
      sum += sumArray[j];
    }
  }

  cout << "Lab1 | The sum of the array is: " << sum << endl;
}

int Lab2(int *sumArray, int size)
{
  const int partSize = 3;

  int localSize = size;
  while(localSize > 1)
  {
    int summationIndex = 0;
    while(summationIndex < localSize / 2)
    {
      sumArray[summationIndex] = sumArray[summationIndex] + sumArray[localSize - summationIndex - 1];
      summationIndex++;
    }

    // for (int i = 0; i < size; i++) 
    //   cout << sumArray[i] << "|";
    // cout << endl;

    localSize = localSize / 2 + localSize % 2;
  }

  cout << "Lab2 | The sum of the array is: " << sumArray[0] << endl;
}

int main()
{
  int sumArray[5] = {1,2,3,4,5};

  Lab1(sumArray, sizeof(sumArray) / sizeof(int));
  Lab2(sumArray, sizeof(sumArray) / sizeof(int));
}
