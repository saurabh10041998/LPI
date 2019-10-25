#include<iostream>
#include<climits>
#include<time.h>
#include<omp.h>
#include<vector>
#include<stdlib.h>
using namespace std;

vector<int> initialize(vector<int>, int);

int main(){
    int SIZE = 1 << 12;
    int arr[SIZE];
    vector<int> v;
    v = initialize(v, SIZE);
    int x = 0 ;
    vector<int>::iterator it;
    for(it = v.begin(); it != v.end(); it++) {
        arr[x] =  *it;
        x++;
    }
    double start,end; 
    start = omp_get_wtime();
    cout<<"\n PARALLEL REDUCTION OPERATION = max \n"; 
    cout<<"==================================\n";
    int max_val = 0;
    int j = 0;
    #pragma omp parallel for reduction(max: max_val)
    for(j = 0; j<SIZE; j++) {
        if(arr[j] > max_val) {
            max_val = arr[j];
        }
    }

    cout<<"maximum of the array "<<max_val<<endl;

    cout<<"\n PARALLEL REDUCTION OPERATION = min\n";
    cout<<"========================================\n";
    int min_val = INT_MAX;
    #pragma omp parallel for reduction(min : min_val)
    for(j = 0 ; j < SIZE; j++) {
        if(arr[j] < min_val) {
            min_val = arr[j];
        }
    }

    cout<<"minimum of array "<<min_val<<endl;

    cout<<"\n PARALLEL REDUCTION OPERATION = sum\n";
    cout<<"======================================\n";
    long long int sum = 0;
    #pragma omp parallel for reduction(+: sum) 
    for(j = 0; j < SIZE; j++) {
        sum += arr[j];
    }
    cout<<"sum of the array element is "<<sum<<endl;
    cout<<"PARALLEL REDUCTION OPERATION = average\n";
    cout<<"=======================================\n";
    sum = 0;
    #pragma omp parallel for reduction(+: sum)
    for(j = 0; j<SIZE; j++) 
        sum += arr[j];
    long double average = sum / SIZE;
    cout<<"Average of the array elements "<<average<<endl;
    end = omp_get_wtime();
    cout<<"\nProgram took time for excution "<<(end - start)<<" seconds"<<endl;
    return 0;

}

vector<int>initialize(vector<int> v, int n) {
    srand(time(0));
    for(int i=0; i<n; i++)
        v.push_back(rand());
    return v;
}