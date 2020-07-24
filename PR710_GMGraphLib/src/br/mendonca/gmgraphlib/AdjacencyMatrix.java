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
import java.util.LinkedList;
import java.util.List;

import br.mendonca.gmgraphlib.undirectedgraphs.UndirectedGraph;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class AdjacencyMatrix extends AbstractEdgeList<Float> {

	public static final int TYPE = 10;
	
	private static final long serialVersionUID = -3097041132947164746L;

	private int edgeIndex = 0;	
	
	public AdjacencyMatrix(int numberOfVertices) {
		super(numberOfVertices);
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.AbstractEdgeList#addNewVertex()
	 */
	@Override
	protected void addNewVertex(int numberOfVertices) {
		List<Float> nullList = new ArrayList<Float>(numberOfVertices);
		for (int j = 0; j < numberOfVertices; j++) {
			nullList.add(null);
		}
		add(nullList);
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.EdgeList#addEdge()
	 */
	public void addEdge(int v1, int v2, float weight) {
		List<Float> list = get(v1 - 1);
		list.set(v2 - 1, weight);
		numberOfEdges++;
		edgeIndex++;
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.EdgeList#getNeighborsIndex()
	 */
	public List<Integer> getNeighborsIndex(int vertexIndex) {
		List<Integer> neighbors = new LinkedList<Integer>();
				
		List<Float> edges = get(vertexIndex - 1);
		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i) != null) {
				neighbors.add(i + 1);
			}
		}
		
		return neighbors;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return edgeIndex > 0;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public Edge next() {
		edgeIndex--;
		
		while (get(v1Index).get(v2Index) == null) {
			updateIndexes();
		}
		
		Edge edge = new Edge(v1Index + 1, v2Index + 1);		
		updateIndexes();
		
		return edge;
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.EdgeList#getWeight()
	 */
	public Float getWeight(int v1, int v2) {
		return get(v1 - 1).get(v2 - 1);
	}

	/*
	 * (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.EdgeList#setWeight(int, int, float)
	 */
	public void setWeight(int v1, int v2, float weight) {
		get(v1 - 1).set(v2 - 1, weight);
	}

	public boolean containsEdge(int v1, int v2) {
		return get(v1 - 1).get(v2 - 1) != null;
	}

	public void removeEdge(int vertex1, int vertex2) {
		get(vertex1 - 1).set(vertex2 - 1, null);
		numberOfEdges--;
		edgeIndex--;
	}

	public ArrayList<Edge> createArray(int graphType) {
		ArrayList<Edge> edgeArray = new ArrayList<Edge>(size());
		
		for (int i = 0; i < size(); i++) {
			int ini = 0;
			if ((graphType & UndirectedGraph.TYPE) == UndirectedGraph.TYPE) {
				ini = i + 1;
			}
			
			for (int j = ini; j < size(); j++) {
				if (get(i).get(j) != null) {
					edgeArray.add(new Edge(i + 1, j + 1));
				}
			}
		}
		
		return edgeArray;
	}
	
	public int getFirstNeighbor(int index) {
		for (int i = 0; i < size(); i++) {
			if (get(index - 1).get(i) != null) {				
				return i + 1;
			}
		}
		
		return 0;
	}
}
