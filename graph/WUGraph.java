/* WUGraph.java */

package graph;
import dict.*;
import list.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
	HashTableChained vertices;
	HashTableChained edges;
	DList verticeList;
	
  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph(){
	  vertices = new HashTableChained();
	  edges = new HashTableChained();
	  verticeList = new DList();
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount(){
	  return vertices.size();
  }

  /**
   * edgeCount() returns the number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount(){
	  return edges.size()/2;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices(){
	  int size = vertexCount();
	  Object[]  objArray = new Object[size];
	  try {
		  if (size > 0) {
			  ListNode node = verticeList.front();
			  for (int i = 0; i < size; i++){
				  objArray[i] = ((GraphVertex)node.item()).item;
				  node = node.next();
			  }
		  }
	  } catch (InvalidNodeException e){
		  e.printStackTrace();
	  }
	  return objArray;
  }

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.  The
   * vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex){
	  if  (!isVertex(vertex)) {
		  GraphVertex newvertex = new GraphVertex(vertex);
		  verticeList.insertFront(newvertex);
		  newvertex.mynode = verticeList.front();
		  vertices.insert(vertex, newvertex);		  
	  }
  }

  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex){
	  if (isVertex(vertex)) {
		  GraphVertex rm = (GraphVertex) vertices.find(vertex).value();
		  vertices.remove(vertex);
		  try {
			  ListNode node = rm.edges.front();
			  while (node.isValidNode()) {
				  Edge e = (Edge) node.item();
				  if (!e.o1.item.equals(e.o2.item)) {
					  e.partner.mynode.remove();
				  }
				  edges.remove(new VertexPair(e.o1.item, e.o2.item));
				  edges.remove(new VertexPair(e.o2.item, e.o1.item));
				  node = node.next();
			  }
			  rm.mynode.remove();
		  } catch(InvalidNodeException e) {
			  e.printStackTrace();
		  }
	  }
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex){
	  Entry find = vertices.find(vertex);
	  if (find == null) {
		  return false;
	  } else {
		  return true;
	  }
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex){
	  if (!isVertex(vertex)) {
		  return 0;
	  } else {
		  GraphVertex gvt = (GraphVertex) vertices.find(vertex).value();
		  return gvt.edges.length();
	  }
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex){
	  try{
		  if (degree(vertex) == 0) {
			  return null;
		  } else {
			GraphVertex gvt = (GraphVertex)vertices.find(vertex).value();
			Neighbors nbs = new Neighbors();
			nbs.neighborList = new Object[gvt.edges.length()];
			nbs.weightList = new int[gvt.edges.length()];
			ListNode node = gvt.edges.front();
	
			for (int i = 0; i < gvt.edges.length();i++){
				nbs.neighborList[i] = ((Edge) node.item()).o2.item;
				nbs.weightList[i] = ((Edge) node.item()).weight;
				node = node.next();
			}
			return nbs;
		  }
	  } catch(InvalidNodeException e) {
		  e.printStackTrace();
		  return null;
	  }
  }

  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the edge is already
   * contained in the graph, the weight is updated to reflect the new value.
   * Self-edges (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight){
	  if (isVertex(u) && isVertex(v)) {
		  if (!isEdge(u,v)) {
			  GraphVertex uVer = (GraphVertex) vertices.find(u).value();
			  GraphVertex vVer = (GraphVertex) vertices.find(v).value();
			  Edge uv = new Edge(uVer, vVer, weight);
			  Edge vu = new Edge(vVer, uVer, weight);
			  uv.partner = vu;
			  vu.partner = uv; 
			  uVer.edges.insertFront(uv);
			  uv.mynode = uVer.edges.front();
			  
			  if (! u.equals(v)){
				  vVer.edges.insertFront(vu);
			  }
			  
			  vu.mynode = vVer.edges.front();  
			  this.edges.insert(new VertexPair(u,v), uv);
			  this.edges.insert(new VertexPair(v,u), vu);
		  
			  
		  } else {
			  Edge uv = (Edge) edges.find(new VertexPair(u,v)).value();
			  uv.weight = weight;
			  uv.partner.weight = weight;
		  }
	  }
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v){
	  if (isEdge(u, v)) {
		  Edge rm = (Edge) edges.find(new VertexPair(u, v)).value();
		  try {
			  rm.mynode.remove();
			  if (u != v){
				  rm.partner.mynode.remove();
			  }
		  } catch (InvalidNodeException e) {
			  e.printStackTrace();
		  }
		  edges.remove(new VertexPair(u, v));
		  edges.remove(new VertexPair(v, u));
	  }
  }
  
  
  
  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v){
	  if (edges.find(new VertexPair(u, v)) != null) {
		  return true;
	  }
	  return false;
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but
   * also more annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v){
	  if (isEdge(u,v)) {
		  return ((Edge) edges.find(new VertexPair(u, v)).value()).weight;
	  } else {
		  return 0;
	  }
  }

}
