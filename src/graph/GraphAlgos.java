package graph;

import graph.WeightedGraph.Edge;

import java.util.ArrayList;
import java.util.List;

public class GraphAlgos{
	
	List<Integer>[] ls;
	boolean[] isVisited;
	@SuppressWarnings("unchecked")
	public GraphAlgos(int n){
		ls = new ArrayList[n];
		isVisited = new boolean[n];
		for(int i = 0;i < n;i++)ls[i] = new ArrayList<Integer>();
	}
	public void addEdge(int u,int v){
		ls[u-1].add(v-1);
		ls[v-1].add(u-1);
	}
	public void dfsTravel(){
		int comp = 0;
		for(int i = 0;i < ls.length;i++){
			if(isVisited[i] == false){
				comp++;
				dfs(i);}
			
		}
		System.out.println("\nConnected Components = "+comp);
		for(int i = 0;i < ls.length;i++)isVisited[i] = false;
	}
	public void dfs(int i){
		isVisited[i] = true;
		int comp = 0;
		System.out.print((i+1)+" ");
		for(int x:ls[i])
			if(isVisited[x] == false)
				dfs(x);
	}
	public void bfsTravel(int i){
		isVisited[i] = true;
		MyQueue<Integer> q = new MyQueue<>();
		q.enq(i);
		while(!q.isEmpty()){
			int tmp = q.deq();
			System.out.print((tmp+1)+" ");
			for(int x:ls[tmp]){
				if(isVisited[x] == false){
					q.enq(x);
					isVisited[x] = true;
				}
			}
		}
		for(int j = 0;j < ls.length;j++)isVisited[j] = false;
	}
	class MyQueue<E>{
		private List<E> ls;
		public MyQueue(){
			ls = new ArrayList<E>();
		}
		public void enq(E item){
			ls.add(item);
		}
		public E deq(){
			E item = ls.remove(0);
			return item;
		}
		public boolean isEmpty(){
			return ls.isEmpty();
		}
		public void printQ(){
			for(E item : ls)System.out.print(item+" ");
			System.out.println();
		}
		public void clear(){
			ls.clear();
		}
	}
	public static void main(String[] args){
		/*GraphAlgos g = new GraphAlgos(4);
		g.addEdge(1, 3);
		g.addEdge(1, 2);
		g.addEdge(3, 4);
		g.addEdge(3, 4);
		g.addEdge(3, 6);
		g.addEdge(5, 6);
		g.addEdge(6, 7);
		g.addEdge(6, 8);
		g.dfsTravel();
		g.bfsTravel(0);*/
		/*int[] a = {50,35,30,29,25,21,20,16,12,12,12,8,7,6,5,3,1,-1,-20};
		MinHeap h = new MinHeap();
		a = h.heapSort(a);
		System.out.println();
		for(int x:a)System.out.print(x+" ");*/
		WeightedGraph g = new WeightedGraph(7);
		g.add(1,2,2);
		g.add(1,3,3);
		g.add(1,4,5);
		g.add(2,4,6);
		g.add(2,5,7);
		g.add(2,6,4);
		g.add(3,7,8);
		g.add(4,7,9);
		g.add(5,7,10);
		g.add(6,5,6);
		g.mst(1);
	}
}
class MinHeap<Edge>{
	private List<Edge> ls = new ArrayList<Edge>();
	//private int size = 0;
	public MinHeap(){
		
	}
	public void heapify(int x){
		//System.out.println("X = "+x+" ");
		Edge tmp = ls.get(x);
		int left = left(x);
		int right = right(x);
		int min = x;
		if(left >=0 && left < ls.size() && (((Comparable<Edge>) ls.get(left)).compareTo(tmp) == 1))min = left;
		if(right >=0 && right < ls.size() && (((Comparable<Edge>) ls.get(right)).compareTo(ls.get(min)) == 1))min = right;
		if(min != x){
			Edge temp = ls.get(min);
			ls.set(min,tmp);
			ls.set(x, temp);
			heapify(min);
		}
	}
	public void insert(Edge x){
		ls.add(x);
		//size++;
		int cur = ls.size()-1;
		//System.out.println("Cur = "+ls.get(ls.size()-1)+" ");
		Edge tmp = x;
		/*System.out.println("cur = "+cur);
		System.out.println(parent(cur));
		if(parent(cur) >= 0)System.out.println("Parent = "+ls.get(parent(cur)));*/
		while(parent(cur)>=0 && ((Comparable<Edge>) tmp).compareTo(ls.get(parent(cur))) == 1){
			ls.set(cur, ls.get(parent(cur)));
			ls.set(parent(cur), tmp);
			cur = parent(cur);
		}
		/*System.out.println("Current State ");
		for(int i:ls)System.out.print(i+" ");
		System.out.println();*/
	}
	public Edge deleteMin(){
		Edge res = ls.get(0);
		int temp = ls.size()-1;
		ls.set(0,ls.get(temp));
		ls.remove(temp);
		if(ls.size() > 0)heapify(0);
		return res;
	}
	public Edge showMin(){
		return ls.get(0);
	}
	public Edge[] heapSort(Edge[] a){
		for(int i = 0;i < a.length;i++)
			insert(a[i]);
		//for(int x:ls)System.out.print(x+" ");
		for(int i = 0;i < a.length;i++)
			a[i] = deleteMin();
		return a;
	}
	public int left(int i){
		return 2*i+1;
	}
	public int right(int i){
		return 2*i+2;
	}
	public int parent(int i){
		return (int)(Math.ceil((double)(i)/2.0))-1;
	}
	public void showHeap(){
		System.out.println("##################");
		for(int i = 0;i < ls.size();i++){
			graph.WeightedGraph.Edge e = (graph.WeightedGraph.Edge) ls.get(i);
			System.out.println(e.getFrom()+" -> "+e.getTo()+" "+e.getWt());
		}
		System.out.println("##################");
	}
}
class WeightedGraph{
	private List[] ls;
	boolean[] isVisited;
	public WeightedGraph(int n){
		ls = new ArrayList[n];
		isVisited = new boolean[n];
		for(int i = 0;i < n;i++)ls[i] = new ArrayList<Edge>();
	}
	public void add(int from,int to,int wt){
		Edge e = new Edge(from-1,to-1,wt);
		Edge e2 = new Edge(to-1,from-1,wt); 
		//int wt = e.getWt();
		ls[from-1].add(e);
		ls[to-1].add(e2);
	}
	/*public void printGraph(){
		for(int i = 0;i < ls.length;i++)
			for(int j = 0;j < ls[i].size();j++)
				
				
	}*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void mst(int i){
		MinHeap<Edge> h = new MinHeap();
		int count = 0;
		int totWt = 0;
		for(int x = 0;x < ls[i-1].size();x++){
			Edge e = (Edge) ls[i-1].get(x);
			h.insert(e);
		}
		isVisited[i-1] = true;
		count++;
		//h.showHeap();
		while(count != isVisited.length){
			Edge e = h.deleteMin();
			//System.out.println("MIN EDGE INFO -> From = "+e.from+" To = "+e.to+" Weight = "+e.wt);
			if(isVisited[e.getTo()] == false){
				count++;
				//System.out.println("Count = "+count);
				totWt += e.getWt();
				int to = e.getTo();
				isVisited[to] = true;
				for(int x = 0;x < ls[to].size();x++){
					Edge tmp = (Edge) ls[to].get(x);
					//System.out.println("TMP>TO = "+tmp.to+" TMP>FROM = "+tmp.from);
					if(!isVisited[tmp.to])
						h.insert(tmp);
				}
				//h.showHeap();
			}
		}
		System.out.println("MST Weight = "+totWt);
	}
	class Edge implements Comparable<Edge>{
		public Edge(){}
		public int getFrom() {
			return from;
		}
		public int getTo() {
			return to;
		}
		public int getWt() {
			return wt;
		}
		private int from,to,wt;
		public Edge(int from,int to,int wt){
			this.from = from;
			this.to = to;
			this.wt = wt;
		}
		public int compareTo(Edge e){
			if(this.wt > e.wt)return -1;
			if(this.wt < e.wt)return 1;
			return 0;
		}
		public void printE(){
			System.out.println(this.from+" "+this.to+" "+this.wt);
		}
	}
}




