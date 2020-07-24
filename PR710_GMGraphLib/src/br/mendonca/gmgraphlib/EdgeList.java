package br.mendonca.gmgraphlib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface EdgeList extends Iterator<Edge> {

	public void addEdge(int v1, int v2, float weight);

	public void addEdge(int v1, int v2);

	public List<Integer> getNeighborsIndex(int vertexIndex);

	public int getNumberOfEdges();

	public Float getWeight(int v1, int v2);

	/**
	 * Updates the weight of the edge that connects <code>v1</code> to 
	 * <code>v2</code>.
	 * @param v1 first vertex of the edge
	 * @param v2 second vertex of the edge
	 * @param weight the new weight of the edge.
	 */
	public void setWeight(int v1, int v2, float weight);

	public boolean containsEdge(int v1, int v2);

	/**
	 * Removes the edge between vertex1 and vertex2 from the list, if it exists.
	 * @param vertex1
	 * @param vertex2
	 */
	public void removeEdge(int vertex1, int vertex2);

	/**
	 * Creates and returns an <code>ArrayList</code> containing all edges of the graph.
	 * @param graphType an int that represents the graph's type.
	 * @return an <code>ArrayList</code> containing all edges of the graph.
	 */
	public ArrayList<Edge> createArray(int graphType);

	/**
	 * Returns the index of the first neighbor of the vertex indicated by <code>index</code>. 
	 * @param index
	 * @return
	 */
	public int getFirstNeighbor(int index);

}