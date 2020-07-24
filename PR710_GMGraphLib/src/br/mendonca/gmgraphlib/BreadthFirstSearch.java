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
import java.util.Queue;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class BreadthFirstSearch extends SearchAlgorithm {

	private Graph graph;
	private StringBuilder output;
	
	public BreadthFirstSearch(Graph graph, Vertex root, StringBuilder output) {
        this.graph = graph;
        this.root = root;
        this.output = output;
		this.distance = new ArrayList<Float>(graph.getNumberOfVertices() + 1);
		
		for (int i = 0; i <= graph.getNumberOfVertices(); i++) {
			this.distance.add(Float.POSITIVE_INFINITY);
		}
	}
	
	public BreadthFirstSearch(Graph graph, Vertex root) {
		this(graph, root, null);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		graph.unmarkAll();
		
		Queue<Vertex> queue = new LinkedList<Vertex>();

		if (output != null) {
            output.append("root " + root + GraphUtil.NEWLINE);
		}
		
		distance.set(root.index, 0f);
		root.parent = root;
		root.visited = true;
		queue.add(root);		
		
		while (!queue.isEmpty()) {
			Vertex vertex = queue.poll();
			
			for (Vertex child : graph.getNeighbors(vertex)) {
				if (!child.visited) {
					distance.set(child.index, distance.get(vertex.index) + 1);
					child.parent = vertex;
					child.visited = true;
					queue.add(child);
		
					if (output != null) {
                        output.append(vertex.index + " <= " + child.index + GraphUtil.NEWLINE);
					}
				}
			}
		}
		
	}

}
