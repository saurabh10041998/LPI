#include<iostream>
#include<omp.h>
#include<time.h>
#include<stdlib.h>
using namespace std;

int *initialize(int *arr ,int size) {
    srand(time(0));
    for(int i=0;i<size; i++) 
        arr[i] = rand();
    return arr;
}


int main() {
    int size = 1000;
    int *arr = (int *)malloc(size * sizeof(int));
    arr = initialize(arr, size);

    double start, end;
    start = omp_get_wtime();
    int i, j;
    int x = size;
    for(i=0;i<x-1; i++) {
        int first = i % 2;
        #pragma omp parallel for default(none), shared(arr,i,x)
        for(j = 0; j<(x-i-1); j+=2) {
            if(arr[j+1] < arr[j]) {
                int temp = arr[j+1];
                arr[j+1] = arr[j];
                arr[j] = temp;
            }
        }
    }
    double time = omp_get_wtime() - start;
    cout<< time<<endl;



}