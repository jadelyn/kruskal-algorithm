/* Kruskal.java */


import graph.*;
import set.*;
import dict.*;
import list.*;
import java.util.Arrays;

/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   */
  public static WUGraph minSpanTree(WUGraph g) {
  	WUGraph t = new WUGraph();
	Object[] g_vertices = g.getVertices();
	DList elist = new DList();
	HashTableChained edgedict = new HashTableChained(g.vertexCount());
	for (Object o : g_vertices) {
		t.addVertex(o);						//Add in all the original vertices
		Neighbors nbr = g.getNeighbors(o);
		if (nbr != null) {
			for (int i = 0; i < nbr.neighborList.length; i++ ) {
				KruskalEdge e = new KruskalEdge(o, nbr.neighborList[i], nbr.weightList[i]);
				if (edgedict.find(e) == null){
					elist.insertFront(e);
					edgedict.insert(e, 1);
				}
			}
		}
	}

	//Sort the edgelist
	KruskalEdge[] edgelist = new KruskalEdge[elist.length()];
	ListNode curr = elist.front();
	int index = 0;
	while (curr.isValidNode()) {
		try {
			edgelist[index] = (KruskalEdge) curr.item();
			index++;
			curr = curr.next();
		} catch (InvalidNodeException e) {
			e.printStackTrace();
		}
	}
	Sort.quicksort(edgelist);
		

	//Construct the new graph
	DisjointSets v = new DisjointSets(g.vertexCount());
	for (KruskalEdge k : edgelist){
		int i1 = getIndex(g_vertices, k.o1);
		int i2 = getIndex(g_vertices, k.o2);
		if (v.find(i1) != v.find(i2)){
			v.union(v.find(i1), v.find(i2));
			t.addEdge(k.o1, k.o2, k.weight);
		}
	}
	return t;
  }
 
  /**
  * getIndex() returns the index of the Object obj in the Object[] array called array. If obj is not located in array, then -1 is returned.
  * @param array - the Object[] array in which Object obj's index is found, if obj is located in array
  * @param obj - the Object whose index is sought
  * @return - the integer index of obj in array, or -1 if obj does not exist in array.
  **/
  public static int getIndex(Object[] array, Object obj){
	  for (int i = 0; i < array.length; i ++){
		  if (array[i].equals(obj)){
			  return i;
		  }
	  }
	  return -1;
  }

}


/**
* The KruskalEdge class stores the vertices on both sides, as well as the weight.
**/
class KruskalEdge implements Comparable{
	Object o1;
	Object o2;
	int weight;

	/**
	* The constructor for KruskalEdge stores the two vertices on each side of the edge, as well as the weight of the edge.
	* @param u - The vertex Object on one side of the edge
	* @param v - The vertex Object on the other side of the edge
	* @param w - the weight, represented as an integer, of the edge
	**/
	public KruskalEdge(Object u, Object v, int w){
		o1 = u;
		o2 = v;
		weight = w;
	}
	
	/**
	* equals() determines whether or not "this" KruskalEdge equals Object e, by comparing to see if both vertices of each edge object equal each other. It returns true if the two KruskalEdges have the same two vertices, false otherwise.
	* @param e - the KruskalEdge Object being checked to see if it is equal to "this" KruskalEdge by seeing if both vertices are the same.
	* @return true if the two KruskalEdges have the same two vertices, false otherwise.
	**/
	public boolean equals(Object e){
		if ((this.o1.equals(((KruskalEdge)e).o1) && this.o2.equals(((KruskalEdge)e).o2)) || (this.o2.equals(((KruskalEdge)e).o1) && this.o1.equals(((KruskalEdge)e).o2))){
			return true;
		}
		return false;
	}


	/**
	* compareTo() compares the weight of "this" Kruskal edge to the weight of Object arg0. It returns -1 if "this" KruskalEdge's weight is smaller than the weight of arg0, 0 if they are the same, and 1 if "this" KruskalEdge's weight is larger.
	* @param arg0 - the KruskalEdge Object whose weight is being compared to the weight of "this" KruskalEdge
	* @return -1 if "this" KruskalEdge's weight is smaller than the weight of arg0, 0 if "this" KruskalEdge's weight is the same as arg0's weight, and 1 if "this" KruskalEdge's weight is larger than the weight of arg0.
	**/
	@Override
	public int compareTo(Object arg0) {
		if (this.weight < ((KruskalEdge)arg0).weight){
			return -1;
		}else if (this.weight == ((KruskalEdge)arg0).weight){
			return 0;
		}else{
			return 1;
		}
	}
	

	/**
	* toString() returns the String representation of "this" KruskalEdge by returning its weight, as well as both of the vertices located on each side of the edge.
	* @return the String representation of "this" KruskalEdge, including its weight and both vertices on each side of the edge.
	**/
	public String toString(){
		return String.valueOf(weight) + o1.toString() + o2.toString();
	}
}
