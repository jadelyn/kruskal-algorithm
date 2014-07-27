package graph;
import list.*;

/**
* The Edge class represents an edge, which stores the partner edge, which is the corresponding edge adjacent to the other vertex, the two vertices on both sides of the edge, and the weight of the edge.
**/

class Edge{
	Edge partner;
	GraphVertex o1;
	GraphVertex o2;
	int weight;
	ListNode mynode;
	
	/**
	* The Edge() constructor stores the two vertices on both sides of the edge, and the weight of the edge.
	**/
	public Edge(GraphVertex u, GraphVertex v, int w){
		o1 = u;
		o2 = v;
		weight = w;
	}
}
