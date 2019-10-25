#include<iostream>
#include<list>
#include<omp.h>
using namespace std;

class Graph{
	int V;
	list<int> *adj;
public:
	Graph(int v);
	void adde(int v,int w);
	void bfs(int s);
};

Graph::Graph(int V){
	this->V=V;
	adj=new list<int>[V];
}
void Graph::adde(int v,int w){
	adj[v].push_back(w);
}

void Graph::bfs(int s){
	bool *visited=new bool[V];
	for(int i=0;i<V;i++){
		visited[i]=false;
	}
	list<int>queue;
	visited[s]=true;
	queue.push_back(s);

	list<int>::iterator i;
    double start, end;
	while(!queue.empty()){
		s=queue.front();
		cout<<s<<" ";

		queue.pop_front();

		start=omp_get_wtime();
		#pragma omp parallel 
		for(i=adj[s].begin();i!=adj[s].end();++i){

			#pragma omp single
			if(!visited[*i]){
				visited[*i]=true;
				queue.push_back(*i);
			}
		}
		end=omp_get_wtime();


	}
		cout<<"\n Execution time is:"<<(end-start);
	
}

int main(){
	Graph g(4);
	g.adde(0,1);
	g.adde(0,2);
	g.adde(1,2);
	g.adde(2,3);
	g.adde(3,3);
	g.adde(2,0);

	g.bfs(2);
}
