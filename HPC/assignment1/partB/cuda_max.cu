#include<iostream>
#include<time.h>
#include<climits>
#include<stdlib.h>
using namespace std;

__global__ void minimum(int *a, int *b, int n) {
    int tid = threadIdx.x;
    int min_limit = INT_MAX;
    for(int i=tid; i<min(tid+256, n); i++) {
        if(min_limit > a[i])
            min_limit = a[i];
    }
    b[tid] = min_limit;
}

int main(){
    int n = 128;
    int *a = (int *)malloc(n * sizeof(int));
    srand(time(0));
    for(int i=0;i<n;i++) {
        a[i] = rand();
    }

    for(int i=0; i<n; i++){
        cout<<a[i]<<" ";
    }
    cout<<endl;

    //creating the device array
    int *dev_a, *dev_b;
    int size = n * sizeof(int);
    float total_elapsed_time;
    cudaEvent_t start, stop;
    cudaEventCreate(&start);
    cudaEventCreate(&stop);    
    cudaMalloc(&dev_a, size);
    cudaMalloc(&dev_b, sizeof(int));
    int * ans;
    ans = (int *)malloc(sizeof(int));
    cudaMemcpy(dev_a, a, size, cudaMemcpyHostToDevice);
    cudaEventRecord(start, 0);
    minimum<<<1, n>>>(dev_a, dev_b, n);
    cudaDeviceSynchronize();    
    cudaMemcpy(ans, dev_b, sizeof(int), cudaMemcpyDeviceToHost);
    cudaEventRecord(stop, 0);
    cudaEventElapsedTime(&total_elapsed_time, start, stop);
    cudaEventDestroy(start);
    cudaEventDestroy(stop);
    cout<<"Minimum of the array is "<<ans[0]<<endl;   
    cout<<"total elapsed time "<<total_elapsed_time<<"ms"<<endl;

    //calculating the serial way
    clock_t  start_cpu = clock();
    int min  = INT_MAX;
    for(int i=0;i<n;i++) {
        if(a[i] < min)
            min = a[i];
    }
    clock_t stop_cpu = clock();
    cout<<"Min by CPU "<<min<<endl;
    clock_t total_time = (stop_cpu - start_cpu) * 1000 / CLOCKS_PER_SEC;
    cout<<total_time<<endl;
    return 0;    
}
