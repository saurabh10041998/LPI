#include<iostream>
#include<omp.h>
using namespace std;

void binary_search(int a[],int l, int r, int key) {
    #pragma omp parallel sections
    {
        #pragma omp critical
        while(l <= r) {
            int mid = (l + r) /2;
            if(a[mid] == key) {
                cout<<"found at "<<key;
                break;
                
            }
            else if(a[mid] > key) {
                r = mid -1;
            }else {
                l = mid + 1;
            }
        }
    }
    cout<<"Not found"<<endl;
}

int main() {
    int a[] = {1,2,3,4,5,6};
    binary_search(a, 0, 6,5);
    return 0;
}