package br.mendonca.gmgraphlib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import br.mendonca.gmgraphlib.exception.InvalidEdgeException;

public interface Graph {

	/**
	 * Adds an edge to the graph.
	 * @param v1 first vertex connected to the edge.
	 * @param v2 second vertex connected to the edge.
	 */
	public void addEdge(int v1, int v2) throws InvalidEdgeException;

	/**
	 * When called, returns a list with the neighbors of the given vertex.
	 * 
	 * @param vertex a vertex of the graph.
	 * @return a list with the neighbors of the vertex.
	 */
	public List<Vertex> getNeighbors(Vertex vertex);

	/**
	 * When called, returns a list with the neighbors of the vertex with the given index.
	 * @param vertexIndex the index of a vertex in the graph.
	 * @return a list with the neighbors of the vertex.
	 */
	public List<Vertex> getNeighbors(int vertexIndex);
	
	/**
	 * If there is a vertex in this graph with the specified index,
	 * return it. Else returns <code>null</code>.
	 * 
	 * @param vertexIndex the index of a vertex.
	 * @return the vertex or <code>null</code> if it doesn't exists.
	 */
	public Vertex getVertex(int vertexIndex);

	/**
	 * Unmarks all vertices. 
	 * It must be called before a search, like BFS and DFS. 
	 */
	public void unmarkAll();
	
	/**
	 * Retrives the number of vertices in this graph.
	 * 
	 * @return the number of vertices in this graph.
	 */
	public int getNumberOfVertices();

	/**
	 * Returns <code>true</code> if there are no vertices in this graph.
	 *  
	 * @return <code>true</code> if there are no vertices in this graph.
	 */
	public boolean hasNoVertices();

	/**
	 * Returns an Iterator over all the edges in this graph.
	 * 
	 * @return an Iterator over all the edges in this graph.
	 */
	public Iterator<Edge> getEdgeIterator();

	public int getDataStructure();

	public int getNumberOfEdges();

	/**
	 * Mark all vertices in the connected component to which
	 * the vertex <code>vertex</code> belongs.
	 * @param vertex a vertex that belongs to the connected component.
	 */
	public void markVertices(Vertex vertex);
	
	public List<Vertex> getPath(int from, int to);

	/**
	 * Reads the scanner, adding all edges to the graph.
	 * @param scanner
	 * @throws InvalidEdgeException
	 */
	public void addAllEdges(Scanner scanner) throws InvalidEdgeException;

	/**
	 * Returns the average distance of the graph.
	 * @return the average distance of the graph.
	 */
	float getAverageDistance();

	public List<Float> getDistances(int root);

	/**
	 * Returns the distance from the vertex <code>from</code> to the vertex
	 * <code>to</code>.
	 * @param from
	 * @param to
	 * @return the distance from the vertex <code>from</code> to the vertex
	 * <code>to</code>.
	 */
	public Float getDistance(Vertex from, Vertex to);

	/**
	 * Returns the distance from the vertex with the index <code>fromIndex</code> 
	 * to the vertex with the index <code>toIndex</code>.
	 * @param fromIndex
	 * @param toIndex
	 * @return the distance from the vertex with the index <code>fromIndex</code> 
	 * to the vertex with the index <code>toIndex</code>.
	 */
	public Float getDistance(int fromIndex, int toIndex);

	public Float getDiameter();

	public void printAverageDistanceAndDiameter();

	/**
	 * Returns the <code>TYPE</code> of this instance.
	 * @return the <code>TYPE</code> of this instance.
	 */
	public int getType();

	/**
	 * Removes the specified edge from the graph. 
	 * @param edge the edge that will be removed.
	 */
	public void removeEdge(Edge edge);

	/**
	 * Returns <code>true</code> if the graph contains the specified edge.
	 * @param edge
	 * @return
	 */
	public boolean contains(Edge edge);

	/**
	 * Adds the edge <code>edge</code> to the graph.
	 * @param edge the edge that will be added.
	 * @throws InvalidEdgeException 
	 */
	public void addEdge(Edge edge) throws InvalidEdgeException;

	/**
	 * Returns a list with all edges of the graph.
	 * @return a list with all edges of the graph.
	 */
	public ArrayList<Edge> getEdgeArray();

	/**
	 * Returns the first neighbor of the vertex <code>vertex</code>
	 * @param vertex a vertex
	 * @return the first neighbor of the vertex <code>vertex</code>
	 */
	public Vertex getFirstNeighbor(Vertex vertex);

	/**
	 * Removes the edge between the vertices indicated by <code>v1</code> 
	 * and <code>v2</code>.
	 * @param v1
	 * @param v2
	 */
	void removeEdge(int v1, int v2);

}