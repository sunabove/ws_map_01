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
import br.mendonca.gmgraphlib.Prim;
import br.mendonca.gmgraphlib.Vertex;
import br.mendonca.gmgraphlib.WeightedGraph;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class WeightedUndirectedGraphTest extends GraphTest {

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.GraphTest#getType()
	 */
	@Override
	protected int getType() {
		return UndirectedGraph.TYPE | WeightedGraph.TYPE;
	}

	public void testWeightedUndirectedEdge() throws Exception {
		WeightedGraph weighted = (WeightedGraph) graph;
		weighted = (WeightedGraph) createGraph(2);
		weighted.addEdge(1, 2, 50f);
		
		assertEquals(weighted.getNeighbors(1).get(0).index, 2);
		assertEquals(weighted.getNeighbors(2).get(0).index, 1);
		assertEquals(weighted.getWeight(1, 2), 50f);
		assertEquals(weighted.getWeight(2, 1), 50f);
	}

	public void testSetEdgeWeight() throws Exception {
		WeightedGraph weighted = (WeightedGraph) createGraph(2);
		
		weighted.addEdge(1, 2, -50f);
		weighted.setWeight(1, 2, 50f);
		
		assertEquals(weighted.getWeight(1, 2), 50f);
	}
	
	public void testGetDistanceNegativeWeight() throws Exception {
		WeightedGraph weighted = (WeightedGraph) createGraph(2);
		
		weighted.addEdge(1, 2, -1f);
		
		try {
			weighted.getDistance(1, 2);
			fail("Deveria lançar UsupportedOperationException");
			
		} catch (UnsupportedOperationException e) {
			assertTrue(true);
		}
	}
	
	@Override
	public void testGetDistance() throws Exception {
		WeightedGraph weighted = (WeightedGraph) createGraph(4);
		weighted.addEdge(1, 2, 4f);
		weighted.addEdge(1, 3, 1.4f);
		weighted.addEdge(2, 3, 2.4f);
		
		assertEquals(weighted.getDistance(1, 1), 0f);
		assertEquals(weighted.getDistance(1, 2), 3.8f, 0.1f);
		assertEquals(weighted.getDistance(1, 3), 1.4f, 0.1f);
		assertEquals(weighted.getDistance(2, 3), 2.4f, 0.1f);
		assertEquals(weighted.getDistance(1, 4), Float.POSITIVE_INFINITY);
	}
	
	@Override
	public void testGetMinPath() throws Exception {
		WeightedGraph weighted = (WeightedGraph) createGraph(5);
		weighted.addEdge(1, 2, 0f);
		weighted.addEdge(1, 3, 2f);
		weighted.addEdge(1, 4, 10f);
		weighted.addEdge(2, 3, 1.8f);
		weighted.addEdge(3, 4, 7.8f);
		
		List<Vertex> path3 = weighted.getPath(1, 3);
		assertEquals(path3.get(0).index, 1);
		assertEquals(path3.get(1).index, 2);
		assertEquals(path3.get(2).index, 3);
		assertEquals(weighted.getDistance(1, 3), 1.8f);

		List<Vertex> path4 = weighted.getPath(1, 4);
		assertEquals(path4.get(0).index, 1);
		assertEquals(path4.get(1).index, 2);
		assertEquals(path4.get(2).index, 3);
		assertEquals(path4.get(3).index, 4);
		assertEquals(weighted.getDistance(1, 4), 9.6f);
		
		assertNull(weighted.getPath(1, 5));
	}
	
	public void testGetAverageDistance() throws Exception {
		WeightedGraph weighted = (WeightedGraph) createGraph(6);
		weighted.addEdge(1, 2, 0f);
		weighted.addEdge(1, 3, 2f);
		weighted.addEdge(1, 5, 1.4f);
		weighted.addEdge(2, 4, 0.5f);
		weighted.addEdge(2, 5, 1.3f);
		weighted.addEdge(3, 5, 10.02f);

		assertEquals(weighted.getAverageDistance(), 1.52f, 0.01f);
	}

	@Override
	public void testGetDiameter() throws Exception {
		WeightedGraph weighted = (WeightedGraph) createGraph(5);
		weighted.addEdge(1, 2, 5.0f);
		weighted.addEdge(1, 3, 2.5f);
		weighted.addEdge(1, 4, 5.001f);
		weighted.addEdge(3, 4, 2.4f);
		
		assertEquals(weighted.getDiameter(), 9.9f);
	}
	
	@Override
	public void testAddSameEdge() throws Exception {
		WeightedGraph weighted = (WeightedGraph) createGraph(2);
		weighted.addEdge(1, 2, 4.0f);
		weighted.addEdge(1, 2, 0f);
				
		assertEquals(weighted.getNumberOfEdges(), 1);
	}
	
	public void testPrim() throws Exception {
		WeightedGraph graph = (WeightedGraph) createGraph(5);
		graph.addEdge(1, 2, 3.0f);
		graph.addEdge(2, 3, 1.0f);
		graph.addEdge(2, 4, 2.0f);
		graph.addEdge(2, 5, 1.5f);
		graph.addEdge(3, 4, 1.0f);
		
		Prim prim = new Prim(graph, 1);		
		//prim.run();
		
		//assertEquals(prim.mst.totalWeight, 6.5f);
	}
}
