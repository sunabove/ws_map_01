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

import br.mendonca.gmgraphlib.GraphTest;
import br.mendonca.gmgraphlib.Vertex;


/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class UndirectedGraphTest extends GraphTest {

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.GraphTest#getType()
	 */
	@Override
	protected int getType() {
		return UndirectedGraph.TYPE;
	}

	public void testUndirectedEdge() throws Exception {
		graph = createGraph(2);
		graph.addEdge(1, 2);
		
		assertEquals(graph.getNeighbors(1).get(0).index, 2);
		assertEquals(graph.getNeighbors(2).get(0).index, 1);
	}
	
	@Override
	public void testGetDistance() throws Exception {
		super.testGetDistance();
		assertEquals(graph.getDistance(3, 2), 1f);
	}
	
	@Override
	public void testGetMinPath() throws Exception {
		super.testGetMinPath();
		
		List<Vertex> path = graph.getPath(1, 6);
		
		assertEquals(path.get(0).index, 1);
		assertEquals(path.get(1).index, 2);
		assertEquals(path.get(2).index, 5);
		assertEquals(path.get(3).index, 6);
	}

	@Override
	public void testGetAverageDistance() throws Exception {
		graph = createGraph(6);
		graph.addEdge(1, 2);
		graph.addEdge(1, 3);
		graph.addEdge(2, 4);
		graph.addEdge(3, 5);
	
		assertEquals(graph.getAverageDistance(), 2.0f, 0.01f);
	}

	@Override
	public void testGetDiameter() throws Exception {
		graph = createGraph(7);
		graph.addEdge(1, 2);
		graph.addEdge(1, 3);
		graph.addEdge(2, 3);
		graph.addEdge(2, 5);
		graph.addEdge(3, 4);
		graph.addEdge(4, 6);
		
		assertEquals(graph.getDiameter(), 4.0f);
	}
	
}
