/**
 * 	This file is part of GMGraphLib.
 * 
 * 	GMGraphLib is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *  
 *  GMGraphLib is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with GMGraphLib.  If not, see <http://www.gnu.org/licenses/>.
*/
package br.mendonca.gmgraphlib;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import br.mendonca.gmgraphlib.directedgraphs.AsDirectedGraph;
import br.mendonca.gmgraphlib.directedgraphs.DirectedGraph;
import br.mendonca.gmgraphlib.exception.InvalidEdgeException;
import br.mendonca.gmgraphlib.exception.InvalidGraphTypeException;
import br.mendonca.gmgraphlib.undirectedgraphs.AsUndirectedGraph;
import br.mendonca.gmgraphlib.undirectedgraphs.AsUndirectedWeightedGraph;
import br.mendonca.gmgraphlib.undirectedgraphs.EuclideanGraph;
import br.mendonca.gmgraphlib.undirectedgraphs.UndirectedGraph;
import br.mendonca.gmgraphlib.undirectedgraphs.UndirectedWeightedGraph;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public abstract class AbstractGraph implements Graph {

	protected VertexList vertexList;	
	protected EdgeList edgeList;
	protected boolean isReverse = false;
	
	private int dataStructure;

	public static AbstractGraph getInstance(int type, int numberOfVertices, 
											int dataStructure) 
											throws InvalidGraphTypeException {
		switch (type) {
		case DirectedGraph.TYPE:
			return new AsDirectedGraph(numberOfVertices, dataStructure);
		
		case UndirectedGraph.TYPE:
			return new AsUndirectedGraph(numberOfVertices, dataStructure);

		case UndirectedWeightedGraph.TYPE:			
			return new AsUndirectedWeightedGraph(numberOfVertices, dataStructure);
			
		case EuclideanGraph.TYPE:
			return new EuclideanGraph(numberOfVertices, dataStructure);
		}
		
		throw new InvalidGraphTypeException();
	}

	public AbstractGraph(int numberOfVertices, int dataStructure) {
		createVertexList(numberOfVertices);
		this.dataStructure = dataStructure;
		createEdgeList(numberOfVertices);
	}

	/*
	 * (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.Graph#getNeighbors(int)
	 */
	public List<Vertex> getNeighbors(int vertexIndex) {
		List<Vertex> neighbors = new LinkedList<Vertex>();
		List<Integer> neighborsIndex = edgeList.getNeighborsIndex(vertexIndex);
		for (Integer neighborIndex : neighborsIndex) {
			neighbors.add(vertexList.get(neighborIndex - 1));
		}
		
		return neighbors;		
	}

	/*
	 * (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.Graph#getNeighbors(br.mendonca.gmgraphlib.Vertex)
	 */
	public List<Vertex> getNeighbors(Vertex vertex) {
		return getNeighbors(vertex.index);
	}
	
	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.Graph#getVertex(int)
	 */
	public Vertex getVertex(int vertexIndex) {
		try {
			return vertexList.get(vertexIndex - 1);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}		
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.Graph#unmarkAll()
	 */
	public void unmarkAll() {		
		for (Vertex vertex : vertexList) {
			vertex.visited = false;
			vertex.parent = null;
		}
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.Graph#getNumberOfVertices()
	 */
	public int getNumberOfVertices() {
		return vertexList.size();
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.Graph#hasNoVertices()
	 */
	public boolean hasNoVertices() {
		return vertexList.isEmpty();
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.Graph#getEdgeIterator()
	 */
	public Iterator<Edge> getEdgeIterator() {		
		return edgeList;
	}

	private void createEdgeList(int numberOfVertices) {
		switch (dataStructure) {
		case AdjacencyList.TYPE:
			edgeList = new AdjacencyList(numberOfVertices);
			break;
			
		case AdjacencyMatrix.TYPE:
			edgeList = new AdjacencyMatrix(numberOfVertices);
			break;
		}
	}

	protected void createVertexList(int numberOfVertices) {
		vertexList = new VertexList(numberOfVertices);
	}

	public int getDataStructure() {
		return dataStructure;
	}
	
	/**
	 * Adds a vertex to the graph.
	 */
	@Deprecated
	public void addVertex() {
		//vertexList.add(new Vertex(vertexList.size()));
		//edgeList.addNewVertex(vertexList.size());
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns <code>true</code> if exists an edge between <code>v1</code>
	 * and <code>v2</code>
	 * @param v1
	 * @param v2
	 * @return <code>true</code> if exists an edge between <code>v1</code> 
	 * and <code>v2</code>
	 */
	protected boolean isValidEdge(int v1, int v2) {
		return v1 != v2 && v1 <= vertexList.size() && v2 <= vertexList.size();
	}
	
	protected SearchAlgorithm getDistanceCalculator(int from) {
		return new BreadthFirstSearch(this, getVertex(from));
	}

	/*
	 * (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.Graph#getPath(int, int)
	 */
	public List<Vertex> getPath(int from, int to) {
		LinkedList<Vertex> result = new LinkedList<Vertex>();
		if (from != to) {
			Runnable distanceCalculator = getDistanceCalculator(from);			
			
			distanceCalculator.run();
			
			Vertex vertex = getVertex(to);
			while (vertex.parent != vertex){
				 result.addFirst(vertex);
				 vertex = vertex.parent;
				 if (vertex == null) return null;
			}
			result.addFirst(vertex);
		}
		return result;
	}
	
	public void addAllEdges(Scanner scanner) throws InvalidEdgeException {
		while (scanner.hasNext()) {
			addEdge(scanner.nextInt(), scanner.nextInt());
		}
	}

	public float getAverageDistance() {
		int n = getNumberOfVertices();
		double sum = 0.0d;
		long quantity = 0l;
		
		for (int i = 1; i <= n; i++) {
			List<Float> distances = getDistances(i);
			for (int j = i + 1; j <= n; j++) {
				float distance = distances.get(j);
				if (distance != Float.POSITIVE_INFINITY) {
					sum += distance;
					quantity++;
				}
			}
		}
		
		return (float) sum / quantity;
	}

	public List<Float> getDistances(int fromIndex) {
		SearchAlgorithm search = getDistanceCalculator(fromIndex);		
		return search.getDistances();
	}
	
	public Float getDistance(int fromIndex, int toIndex) {
		return getDistances(fromIndex).get(toIndex);
	}
	
	public Float getDistance(Vertex from, Vertex to) {
		return getDistance(from.index, to.index);
	}
	
	public Float getDiameter() {
		int n = getNumberOfVertices();
		float diameter = Float.NEGATIVE_INFINITY;
		
		for (int i = 1; i <= n; i++) {
			List<Float> distances = getDistances(i);
			for (int j = i + 1; j <= n; j++) {
				float distance = distances.get(j);
				if (distance != Float.POSITIVE_INFINITY &&
					distance > diameter) {
					diameter = distance;
				}
			}
		}
		
		return diameter;
	}
	
	public void printAverageDistanceAndDiameter() {
		int n = getNumberOfVertices();
		double sum = 0.0d;
		long quantity = 0l;
		float diameter = Float.NEGATIVE_INFINITY;
		
		for (int i = 1; i <= n; i++) {
			List<Float> distances = getDistances(i);
			for (int j = i + 1; j <= n; j++) {
				float distance = distances.get(j);
				if (distance != Float.POSITIVE_INFINITY) {
					if (distance > diameter) {
						diameter = distance;
					}
					sum += distance;
					quantity++;
				}
			}
		}
		
		System.out.println("Average Distance: " + (sum/quantity));
		System.out.println("Diameter: " + diameter);
	}
	
	public void removeEdge(Edge edge) {
		removeEdge(edge.vertex1, edge.vertex2);
	}
	
	public boolean contains(Edge edge) {		
		return edgeList.containsEdge(edge.vertex1, edge.vertex2);
	}
	
	public void addEdge(Edge edge) throws InvalidEdgeException {
		addEdge(edge.vertex1, edge.vertex2);
	}
	
	public ArrayList<Edge> getEdgeArray() {
		return edgeList.createArray(getType());
	}
	
	public Vertex getFirstNeighbor(Vertex vertex) {
		int index = edgeList.getFirstNeighbor(vertex.index);
		return getVertex(index);
	}
	
	public void removeEdge(int v1, int v2) {
		edgeList.removeEdge(v1, v2);
	}
}
