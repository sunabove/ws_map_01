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
package br.mendonca.gmgraphlib.directedgraphs;

import br.mendonca.gmgraphlib.GraphUtil;
import br.mendonca.gmgraphlib.GraphUtilTest;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class DirectedGraphUtilTest extends GraphUtilTest {

	private static final String STRONGLY_CONNECTED_COMPONENTS = "strongly_connected_components.test";
	private static final String STRONGLY_CONNECTED_COMPONENTS_OUT = "strongly_connected_components.out";
	private static final String WRITE_INFO = "write_info2.out";

	@Override
	protected int getType() {
		return DirectedGraph.TYPE;
	}	
	
	public void testDetectStronglyConnectedComponents() throws Exception {
		DirectedGraph graph;
		graph = GraphUtil.loadDirectedGraph(getFilePath(STRONGLY_CONNECTED_COMPONENTS), 
											DATA_STRUCTURE);
		
		GraphUtil.detectConnectedComponents(graph, getFilePath(STRONGLY_CONNECTED_COMPONENTS_OUT));
		
		int[] expectedContent = {1,
								 1, 2, 3,
								 2,
								 6, 7, 8,
								 3,
								 4, 5};
		
		assertFileContent(getFilePath(STRONGLY_CONNECTED_COMPONENTS_OUT), expectedContent);
	}
	
	@Override
	public void testWriteInfo() throws Exception {
		DirectedGraph graph = new AsDirectedGraph(8, DATA_STRUCTURE);
		graph.addEdge(1, 2);
		graph.addEdge(1, 3);
		graph.addEdge(1, 4);
		graph.addEdge(3, 5);
		graph.addEdge(4, 2);
		graph.addEdge(4, 5);
		graph.addEdge(4, 6);
		graph.addEdge(5, 3);
		graph.addEdge(6, 5);
		graph.addEdge(6, 8);
		graph.addEdge(8, 6);
		
		GraphUtil.writeGraphInfo(graph, getFilePath(WRITE_INFO));
		
		int[] expectedContent = {8, 
								 11,
								 5, 3, 1,
								 2, 2, 0,
								 3, 2, 1,
								 6, 2, 2,
								 4, 1, 3,
								 8, 1, 1,
								 1, 0, 3,
								 7, 0, 0};
		assertFileContent(getFilePath(WRITE_INFO), expectedContent);
	}

}
