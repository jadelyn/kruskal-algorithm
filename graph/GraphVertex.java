package graph;
import list.*;

/**
* The GraphVertex class represents a vertex in a graph. It stores the item and a list of edges.
**/
class GraphVertex{
	Object item;
	DList edges;
	ListNode mynode;
	
	/**
	* The GraphVertex() constructor sets the item of "this" GraphVertex to Object obj, and creates a new empty DList that is meant to store the edges of "this" GraphVertex.
	**/
	public GraphVertex(Object obj){
		item = obj;
		edges = new DList();
	}
	
	
}
