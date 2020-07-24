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

import java.util.PriorityQueue;

import br.mendonca.gmgraphlib.exception.InvalidEdgeException;
import br.mendonca.gmgraphlib.util.VertexComparator;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class Prim extends Dijkstra {

	public int size = 0;
	public MinimumSpanningTree mst;
	
	public Prim(WeightedGraph graph, int vertexIndex) {
		super(graph, vertexIndex);
		this.mst = new MinimumSpanningTree(graph);
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
			if (!vertex.visited) {
				vertex.visited = true;

				if (vertex.parent != vertex) {	                
					Float cost = distance.get(vertex.index);
					try {
						mst.addEdge(vertex.parent.index, vertex.index, cost);
		                mst.totalWeight += cost;
		                size++;
		                
					} catch (InvalidEdgeException e) {
						e.printStackTrace();
						
					}
				}

				for (Vertex child : graph.getNeighbors(vertex)) {
					if (!child.visited) {
						float edgeWeight = graph.getWeight(vertex.index, child.index);
							
						if (distance.get(child.index) > edgeWeight) {
							distance.set(child.index, edgeWeight);
							child.parent = vertex;
							vertices.add(child);
						}
					}
				}
			}
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
}
