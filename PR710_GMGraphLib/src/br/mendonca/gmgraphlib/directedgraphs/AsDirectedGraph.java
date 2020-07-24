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
package br.mendonca.gmgraphlib.directedgraphs;

import java.util.Iterator;

import br.mendonca.gmgraphlib.AbstractGraph;
import br.mendonca.gmgraphlib.BreadthFirstSearch;
import br.mendonca.gmgraphlib.Edge;
import br.mendonca.gmgraphlib.Vertex;
import br.mendonca.gmgraphlib.exception.InvalidEdgeException;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class AsDirectedGraph extends AbstractGraph implements DirectedGraph {

	private DirectedGraph reverse;

	public AsDirectedGraph(int numberOfVertices, int dataStructure) {
		super(numberOfVertices, dataStructure);
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.Graph#addEdge(int, int)
	 */
	public void addEdge(int v1, int v2) throws InvalidEdgeException {
		if (isValidEdge(v1, v2)) {
			if (!edgeList.containsEdge(v1, v2)) {
				edgeList.addEdge(v1, v2);
				getVertex(v1).outDegree++;
				getVertex(v2).inDegree++;
			}
		} else {
			throw new InvalidEdgeException();
		}
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.DirectedGraph#getInDegree(int)
	 */
	public int getInDegree(int vertexIndex) {
		return getVertex(vertexIndex).inDegree;
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.DirectedGraph#getOutDegree(int)
	 */
	public int getOutDegree(int vertexIndex) {
		return getVertex(vertexIndex).outDegree;
	}

	/*
	 * (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.directedgraphs.DirectedGraph#getReverse()
	 */
	public DirectedGraph getReverse() {
		if (reverse == null) {
			AsDirectedGraph reverseGraph = new AsDirectedGraph(getNumberOfVertices(), 
														  	   getDataStructure());
	
			for (int i = 0; i < vertexList.size(); i++) {
				reverseGraph.vertexList.get(i).visited = vertexList.get(i).visited;			
			}
			
			Iterator<Edge> edgeIterator = getEdgeIterator();
			while (edgeIterator.hasNext()) {
				Edge edge = edgeIterator.next();
				
				try {
					reverseGraph.addEdge(edge.vertex2, edge.vertex1);
				} catch (InvalidEdgeException e) {
					e.printStackTrace();
				}
			}
			reverseGraph.isReverse = true;
			
			this.reverse = reverseGraph;
		}
		
		return reverse;
	}

	/*
	 * (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.Graph#getNumberOfEdges()
	 */
	public int getNumberOfEdges() {		
		return edgeList.getNumberOfEdges();		
	}
	
	/*
	 * (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.Graph#markVertices(int)
	 */
	public void markVertices(Vertex vertex) {
		Thread bfs = new Thread(new BreadthFirstSearch(this, vertex));
		Thread reverseBfs = new Thread(new BreadthFirstSearch(getReverse(), vertex));
		
		bfs.start();
		reverseBfs.start();
		
		try {
			bfs.join();
			reverseBfs.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		
		checkConnectivity();
	}	
	
	private void checkConnectivity() {
		for (int i = 1; i <= getNumberOfVertices(); i++) {
			Vertex vertex1 = getVertex(i);
			Vertex vertex2 = getReverse().getVertex(i);
			
			vertex1.visited = vertex1.visited && vertex2.visited;
		}
	}

	public int getType() {
		return TYPE;
	}
}
