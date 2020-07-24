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


/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class AdjacencyList extends AbstractEdgeList<Object[]> {

	public static final int TYPE = 5;

	private static final long serialVersionUID = 2962480744980492420L;

	public AdjacencyList(int numberOfVertices) {
		super(numberOfVertices);
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.AbstractEdgeList#addNewVertex()
	 */
	@Override
	public void addNewVertex(int numberOfVertices) {
		add(new LinkedList<Object[]>());
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.EdgeList#addEdge()
	 */
	public void addEdge(int v1, int v2, float weight) {
		Object[] item = {v2, weight};
		get(v1 - 1).add(item);
		numberOfEdges++;
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.EdgeList#getNeighborsIndex()
	 */
	public List<Integer> getNeighborsIndex(int vertexIndex) {		
		List<Integer> output = new LinkedList<Integer>();
		for (Object[] item : get(vertexIndex - 1)) {
			output.add((Integer) item[0]);
		}
		return output;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {		
		return v1Index < size();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public Edge next() {
		
		Integer index = (Integer) get(v1Index).get(v2Index)[0];
		Edge result = new Edge(v1Index + 1, index);
		updateIndexes();
		while (v1Index < size() && get(v1Index).isEmpty()) {
			v1Index++;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.EdgeList#getWeight()
	 */
	public Float getWeight(int v1, int v2) {
		Iterable<Object[]> neighborhood = get(v1 - 1);
		Float result = null;
		
		for (Object[] item : neighborhood) {
			if (item[0].equals(v2)) {
				result = (Float) item[1];
			}
		}
		
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.EdgeList#setWeight(int, int, float)
	 */
	public void setWeight(int v1, int v2, float weight) {
		Iterable<Object[]> neighborhood = get(v1 - 1);
		
		for (Object[] item : neighborhood) {
			if (item[0].equals(v2)) {
				item[1] = weight;
				break;
			}
		}
	}

	public boolean containsEdge(int v1, int v2) {
		for (Object[] tuple : get(v1 - 1)) {
			if (tuple[0].equals(v2)) {
				return true;
			}
		}
		return false;
	}

	public void removeEdge(int vertex1, int vertex2) {
		throw new UnsupportedOperationException();
	}
	
	public ArrayList<Edge> createArray(int graphType) {
		throw new UnsupportedOperationException();
	}
	
	public int getFirstNeighbor(int index) {
		//return (Integer) get(index - 1).get(0)[0];
		throw new UnsupportedOperationException();
	}
}
