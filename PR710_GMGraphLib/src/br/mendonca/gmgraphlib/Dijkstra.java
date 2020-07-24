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
import java.util.List;
import java.util.PriorityQueue;

import br.mendonca.gmgraphlib.util.VertexComparator;


public class Dijkstra extends SearchAlgorithm {

	protected WeightedGraph graph;

	public Dijkstra(WeightedGraph graph, int vertexIndex) {
		this.graph = graph;
		this.root = graph.getVertex(vertexIndex);

		distance = this.createDistanceArray();
	}

	private List<Float> createDistanceArray() {
		List<Float> distance = new ArrayList<Float>(graph.getNumberOfVertices() + 1);

		for (int i = 0; i <= graph.getNumberOfVertices(); i++) {
			distance.add(Float.POSITIVE_INFINITY);
		}

		return distance;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		graph.unmarkAll();

		VertexComparator comparator = new VertexComparator(distance);
		PriorityQueue<Vertex> vertices =
			new PriorityQueue<Vertex>(graph.getNumberOfVertices(), comparator);

		distance.set(root.index, 0f);
		root.parent = root;
		vertices.add(root);

		while (!vertices.isEmpty()) {
			Vertex vertex = vertices.poll();

			for (Vertex child : graph.getNeighbors(vertex)) {
				float edgeWeight = graph.getWeight(vertex.index, child.index);

				if (distance.get(vertex.index) + edgeWeight < distance.get(child.index)) {
					distance.set(child.index,distance.get(vertex.index) + edgeWeight);
					child.parent = vertex;
					vertices.add(child);
				}
			}
		}
	}

}
