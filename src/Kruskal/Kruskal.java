package Kruskal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

class Node implements Comparable<Node>{
	int n1; // 연결 할 노드
	int n2; // 연결 할 노드
	int v; //간선
	
	public Node(int n1, int n2, int v) {
		this.n1 = n1;
		this.n2 = n2;
		this.v = v;
	}

	@Override
	public int compareTo(Node n) {
		return n.v >=this.v ? -1:1; // 작은 수 반환
	}
	
}

/*1. 연결되어 있는 간선들 중 제일 작은 수 찾기
 * 2. 두 개의 노드가 연결되어 있다면 두 개의 노드 연결
 * 3. 이미 연결되어 있다면 사이클이 형성된 것이기 때문에 pass
 * */
public class Kruskal {
	static int N; //정점 개수
	static int E; // 간선 개수
	static PriorityQueue<Node> pq; //간선이 제일 작은 것부터 찾기 위한 우선순위 큐
	static int[] parent; //부모 노드
	static int result; // 결과
	
	//path compression 기법
	//연결되어있는 부모 노드 찾기
	public static int find(int a) {
		if(parent[a]!=a) {
			parent[a] = find(parent[a]);
		}
		return parent[a];
	}
	
	//union-by-rank 기법
	//부모 노드로 연결
	public static void union(int a, int b) {
		int root1 = find(a);
		int root2 = find(b);
		
		if(root1 != root2) {
			parent[root1] = b;
		}else {
			return;
		}
	}
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.valueOf(br.readLine()); //정점 개수
		E = Integer.valueOf(br.readLine()); //간선 개수
		
		parent = new int[N+1];
		
		result = 0;
		
		pq = new PriorityQueue<Node>();
		String[] tempStr;
		for(int i=0; i<E; i++) {
			tempStr = br.readLine().split(" ");
			pq.add(new Node(Integer.valueOf(tempStr[0]), Integer.valueOf(tempStr[1]), Integer.valueOf(tempStr[2])));
		}
		
		//각 노드의 부모 노드를 자기 자신으로 초기화
		for(int i=1; i<=N; i++) {
			parent[i]=i;
		}
		
		List<int[]> list = new ArrayList<int[]>();
		
		//간선의 수만큼 반복
		for(int i=0; i<E; i++) {
			Node small = pq.poll(); //간선의 수가 가장 작은 노드 선택
			int start = small.n1;
			int end = small.n2;
			int a = find(start);
			int b = find(end);
			if(a==b) continue;
			
			union(start, end);
			result += small.v;
			int[] r= {start, end, small.v};
			list.add(r);
			
		}
		
		System.out.println("MST : "+result);
		
		for(int i=0; i<list.size(); i++) {
			int[] aa = list.get(i);
			System.out.print(aa[0]);
			System.out.print(" ");
			System.out.print(aa[1]);
			System.out.print(" : ");
			System.out.println(aa[2]);
		}
	}

}
