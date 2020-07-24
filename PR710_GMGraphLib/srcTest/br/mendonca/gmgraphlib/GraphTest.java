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

import junit.framework.TestCase;
import br.mendonca.gmgraphlib.exception.InvalidEdgeException;
import br.mendonca.gmgraphlib.exception.InvalidGraphTypeException;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public abstract class GraphTest extends TestCase implements GMGraphLibTest {

	protected abstract int getType();
	
	protected AbstractGraph createGraph(int numberOfVertices) {
		try {
			return AbstractGraph.getInstance(getType(), numberOfVertices, DATA_STRUCTURE);
		} catch (InvalidGraphTypeException e) {
			e.printStackTrace();
			return null;
		}
	}

	protected Graph graph;
	
	public void testNoVertices() {
		graph = createGraph(0);
		assertTrue(graph.hasNoVertices());
	}
	
	public void testOneVertex() {
		graph = createGraph(1);
		
		assertEquals(graph.getVertex(1).index, 1);
	}
	
	public void testInvalidEdge() {
		try {
			graph = createGraph(0);
			graph.addEdge(1, 2);
			fail("Deveria levantar uma InvalidEdgeException.");
			
		} catch (InvalidEdgeException e) {
			assertTrue(true);
		}
		
	}
	
	public void testGetNeighbors() throws Exception {
		graph = createGraph(2);
		graph.addEdge(1, 2);
		
		assertEquals(graph.getNeighbors(1).get(0).index, 2);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		graph = null;
	}

	public void testGetDistanceSameVertex() throws Exception {
		graph = createGraph(1);
		
		assertEquals(graph.getDistance(1, 1), 0f);
	}

	public void testGetDistance() throws Exception {
		graph = createGraph(3);
		graph.addEdge(1, 2);
		graph.addEdge(1, 3);
		graph.addEdge(2, 3);
		graph.addEdge(3, 1);
		
		assertEquals(graph.getDistance(1, 1), 0f);
		assertEquals(graph.getDistance(1, 3), 1f);
		assertEquals(graph.getDistance(2, 3), 1f);
	}

	public void testGetDistanceIsolatedVertex() throws Exception {
		graph = createGraph(2);
		
		assertEquals(graph.getDistance(1, 2), Float.POSITIVE_INFINITY);
	}
	
	public void testGetMinPathSameVertex() throws Exception {
		graph = createGraph(1);
		
		assertTrue(graph.getPath(1, 1).isEmpty());
	}

	public void testGetMinPath() throws Exception {
		graph = createGraph(6);
		graph.addEdge(1, 2);
		graph.addEdge(2, 3);
		graph.addEdge(2, 4);
		graph.addEdge(3, 5);
		graph.addEdge(5, 2);
		graph.addEdge(5, 6);
	}

	public void testAddSameEdge() throws Exception {
		graph = createGraph(2);
		graph.addEdge(1, 2);
		graph.addEdge(1, 2);
				
		assertEquals(graph.getNumberOfEdges(), 1);
	}
	
	public abstract void testGetAverageDistance() throws Exception;
	
	public abstract void testGetDiameter() throws Exception;
	
}
