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
public class UndirectedGraphUtilTest extends GraphUtilTest {

	private static final String CONNECTED_COMPONENTS = "connected_components.test";
	private static final String CONNECTED_COMPONENTS_OUT = "connected_components.out";
	private static final String WRITE_INFO = "write_info1.out";

	@Override
	protected int getType() {
		return UndirectedGraph.TYPE;
	}

	public void testDetectConnectedComponents() throws Exception {
		UndirectedGraph graph;
		graph = GraphUtil.loadUndirectedGraph(getFilePath(CONNECTED_COMPONENTS),
											  DATA_STRUCTURE);
		
		GraphUtil.detectConnectedComponents(graph, getFilePath(CONNECTED_COMPONENTS_OUT));
		
		int[] expectedContent = {1,
								 5, 6, 7, 8, 9, 12,
								 2,
								 1, 2, 3, 4,
								 3,
								 10, 11};
		
		assertFileContent(getFilePath(CONNECTED_COMPONENTS_OUT), expectedContent);
	}	

	public void testWriteInfo() throws Exception {
		UndirectedGraph graph = new AsUndirectedGraph(8, DATA_STRUCTURE);
		graph.addEdge(1, 3);
		graph.addEdge(2, 3);
		graph.addEdge(3, 4);
		graph.addEdge(3, 5);
		graph.addEdge(4, 6);
		graph.addEdge(5, 6);
		graph.addEdge(5, 7);
		graph.addEdge(5, 8);
		graph.addEdge(6, 7);
		graph.addEdge(6, 8);
		graph.addEdge(7, 8);
		
		GraphUtil.writeGraphInfo(graph, getFilePath(WRITE_INFO));
		
		int[] expectedContent = {8, 
								 11,
								 3, 4,
								 5, 4,
								 6, 4,
								 7, 3,
								 8, 3,
								 4, 2,
								 1, 1,
								 2, 1};
		assertFileContent(getFilePath(WRITE_INFO), expectedContent);
	}

}
