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

import br.mendonca.gmgraphlib.GraphUtil;
import br.mendonca.gmgraphlib.GraphUtilTest;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class WeightedUndirectedGraphUtilTest extends GraphUtilTest {

	protected static final String ONE_EDGE = "one_edge_weight.test";
	protected static final String EUCLIDEAN = "euclidean.test";
	protected static final String EUCLIDEAN_OUT = "euclidean.out";

	@Override
	protected int getType() {
		return UndirectedWeightedGraph.TYPE;
	}

	@Override
	public void testWriteInfo() throws Exception {}

	@Override
	public void testBFS1() throws Exception {}
	
	@Override
	public void testBFS2() throws Exception {}
	
	@Override
	public void testDFS() throws Exception {}
	
	@Override
	public void testCreateGraphOneEdge() throws Exception {
		UndirectedWeightedGraph graph = 
			GraphUtil.loadUndirectedWeightedGraph(getFilePath(ONE_EDGE), DATA_STRUCTURE);

		assertEquals(graph.getWeight(1, 2), -1.5001f);
	}
	
	public void testLoadEuclideanGraph() throws Exception {
		UndirectedWeightedGraph graph = 
			GraphUtil.loadEuclideanGraph(getFilePath(EUCLIDEAN));
		
		assertEquals(graph.getWeight(1, 2), 1.414f, 0.001f);
		assertEquals(graph.getWeight(2, 3), 2.236f, 0.001f);
		assertEquals(graph.getWeight(3, 4), 1.414f, 0.001f);
		assertEquals(graph.getWeight(4, 1), 1.000f, 0.001f);
	}
	
	public void testEuclideanTravelingSalesman() throws Exception {
		EuclideanGraph graph = GraphUtil.loadEuclideanGraph(getFilePath(EUCLIDEAN));
		
		float totalCost = GraphUtil.travellingSalesman(graph, getFilePath(EUCLIDEAN_OUT));
		
		assertEquals(totalCost, 4.82f, 0.01f);
	}
	
	/*
	public void testTravellingSalesman() throws Exception {
		UndirectedWeightedGraph graph = 
			GraphUtil.loadUndirectedWeightedGraph(getFilePath(TSP), AdjacencyMatrix.TYPE);
		
		
	}
	*/

}
