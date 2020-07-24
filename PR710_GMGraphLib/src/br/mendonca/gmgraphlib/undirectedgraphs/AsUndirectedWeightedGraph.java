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

import java.util.Scanner;

import br.mendonca.gmgraphlib.Dijkstra;
import br.mendonca.gmgraphlib.Edge;
import br.mendonca.gmgraphlib.SearchAlgorithm;
import br.mendonca.gmgraphlib.exception.InvalidEdgeException;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class AsUndirectedWeightedGraph extends AsUndirectedGraph implements
		UndirectedWeightedGraph {

	private boolean negativeWeight = false;

	public AsUndirectedWeightedGraph(int numberOfVertices, int dataStructure) {
		super(numberOfVertices, dataStructure);
	}

	public void addEdge(int v1, int v2, float weight) throws InvalidEdgeException {
		if (isValidEdge(v1, v2)) {
			if (!edgeList.containsEdge(v1, v2)) {
				edgeList.addEdge(v1, v2, weight);
				edgeList.addEdge(v2, v1, weight);
				
				if (weight < 0f) {
					negativeWeight  = true;
				}
			}
		} else {
			throw new InvalidEdgeException();
		}
	}

	public Float getWeight(int v1, int v2) {
		return edgeList.getWeight(v1, v2);
	}

	public void setWeight(int v1, int v2, float weight) throws InvalidEdgeException {
		if (isValidEdge(v1, v2)) {
			edgeList.setWeight(v1, v2, weight);
		} else {
			throw new InvalidEdgeException();
		}
	}

	@Override
	protected SearchAlgorithm getDistanceCalculator(int from) {
		if (!negativeWeight) {
			return new Dijkstra(this, from);
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	public void addAllEdges(Scanner scanner) throws InvalidEdgeException {
		while (scanner.hasNext()) {
			addEdge(scanner.nextInt(), scanner.nextInt(), scanner.nextFloat());
		}
	}

	@Override
	public int getType() {
		return UndirectedWeightedGraph.TYPE;
	}

	public Float getWeight(Edge edge) {			
		return getWeight(edge.vertex1, edge.vertex2);
	}

	public void addEdge(Edge edge, Float weight) throws InvalidEdgeException {
		addEdge(edge.vertex1, edge.vertex2, weight);
	}
}
