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
package br.mendonca.gmgraphlib.undirectedgraphs;

import java.util.List;

import br.mendonca.gmgraphlib.AbstractGraph;
import br.mendonca.gmgraphlib.BreadthFirstSearch;
import br.mendonca.gmgraphlib.Edge;
import br.mendonca.gmgraphlib.Vertex;
import br.mendonca.gmgraphlib.exception.InvalidEdgeException;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class AsUndirectedGraph extends AbstractGraph implements UndirectedGraph {

	public AsUndirectedGraph(int numberOfVertices, int dataStructure) {
		super(numberOfVertices, dataStructure);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.Graph#addEdge(int, int)
	 */
	public void addEdge(int v1, int v2) throws InvalidEdgeException {
		if (isValidEdge(v1, v2)) {
			if (!edgeList.containsEdge(v1, v2)) {
				edgeList.addEdge(v1, v2);
				edgeList.addEdge(v2, v1);
			}
		} else {
			throw new InvalidEdgeException();
		}
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.UndirectedGraph#getDegree(int)
	 */
	public int getDegree(int vertexIndex) {
		List<Vertex> neighborhood = getNeighbors(vertexIndex);
		
		return neighborhood.size();
	}

	/*
	 * (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.Graph#getNumberOfEdges()
	 */
	public int getNumberOfEdges() {		
		return edgeList.getNumberOfEdges() / 2;		
	}

	/*
	 * (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.Graph#markVertices(int)
	 */
	public void markVertices(Vertex vertex) {
		Runnable bfs = new BreadthFirstSearch(this, vertex);
		bfs.run();
	}

	public int getType() {		
		return TYPE;
	}
	
	@Override
	public void removeEdge(int v1, int v2) {
		super.removeEdge(v1, v2);
		super.removeEdge(v2, v1);
	}
}
