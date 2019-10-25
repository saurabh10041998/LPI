#include<stdio.h>
#include<math.h>
#include<stdlib.h>
#include<time.h>
#include<iostream>
#define N 256*256
using namespace std;


__global__ void reduce(int *input, int *output) {
    __shared__ int shared_data[256];
    int i = blockIdx.x * blockDim.x + threadIdx.x;
    shared_data[threadIdx.x] = input[i];
    __syncthreads();
    for(int s = 1; s < blockDim.x; s*=2) {
        int index = 2 * s * threadIdx.x;
        if(index < blockDim.x){
            shared_data[index] += shared_data[index + s];
        }
        __syncthreads();
    }

    if(threadIdx.x == 0)
        atomicAdd(output, shared_data[0]);
    
}

int main() {

    int *hostA, *hostB, *dev_A, *dev_B;


    hostA = (int *)malloc(N * sizeof(int));

    hostB = (int *)malloc(N * sizeof(int));

    cudaMalloc(&dev_A, N*sizeof(int));
    cudaMalloc(&dev_B, N*sizeof(int));

    //initiallize host arrays
    for(int i=0;i<N;i++) {
        hostA[i] = i;
    }

    hostB[0] = 0;

    //copy on device..
    cudaMemcpy(dev_A, hostA, N*sizeof(int), cudaMemcpyHostToDevice);
    cudaMemcpy(dev_B, hostB, N*sizeof(int), cudaMemcpyHostToDevice);

    reduce<<<256, 256>>>(dev_A, dev_B);

    cudaMemcpy(hostB, dev_B, N*sizeof(int), cudaMemcpyDeviceToHost);
    cout<<(hostB[0]/N)<<endl;  
    return 0;     


}
