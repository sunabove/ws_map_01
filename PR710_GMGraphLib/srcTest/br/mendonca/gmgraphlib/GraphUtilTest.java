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

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

import junit.framework.TestCase;
import br.mendonca.gmgraphlib.exception.EmptyFileException;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public abstract class GraphUtilTest extends TestCase implements GMGraphLibTest {

	private static final String PATH = "srcTest/testFiles/";
	private static final String EMPTY_FILE = "empty_file.test";
	private static final String ONE_VERTEX = "one_vertex.test";
	private static final String ONE_EDGE = "one_edge.test";
	private static final String WRITE_GRAPH = "write_graph.out";
	private static final String BFS_IN_1 = "bfs_in_1.test";
	private static final String BFS_IN_2 = "bfs_in_2.test";
	private static final String BFS = "bfs.out";
	private static final String DFS_IN = "dfs_in.test";
	private static final String DFS = "dfs.out";

	protected abstract int getType();
	
	private Graph graph;
	
	public abstract void testWriteInfo() throws Exception;

	public void testCreateGraphEmptyFile() throws Exception {
		try {
			GraphUtil.loadGraph(getFilePath(EMPTY_FILE), getType(), DATA_STRUCTURE);
			fail("Deveria levantar uma EmptyFileException.");
			
		} catch (EmptyFileException e) {
			assertTrue(true);
			
		}
	}

	public void testCreateGraphOneVertex() throws Exception {
		graph = GraphUtil.loadGraph(getFilePath(ONE_VERTEX), getType(), DATA_STRUCTURE);
		assertEquals(graph.getVertex(1).index, 1);
	}
	
	public void testCreateGraphOneEdge() throws Exception {
		graph = GraphUtil.loadGraph(getFilePath(ONE_EDGE), getType(), DATA_STRUCTURE);

		assertEquals(graph.getNeighbors(1).get(0).index, 3);
	}

	public void testWriteGraph() throws Exception {
		graph = AbstractGraph.getInstance(getType(), 3, DATA_STRUCTURE);
		graph.addEdge(1, 2);
		graph.addEdge(1, 3);
		
		GraphUtil.saveGraph(graph, getFilePath(WRITE_GRAPH));
		
		int[] expectedContent = {3, 1, 2, 1, 3};
		assertFileContent(getFilePath(WRITE_GRAPH), expectedContent);
	}
	
	public void testBFS1() throws Exception {
		graph = GraphUtil.loadGraph(getFilePath(BFS_IN_1), getType(), DATA_STRUCTURE);
		
		GraphUtil.breadthFirstSearch(graph, 2, getFilePath(BFS));
		
		int[] expectedContent = {2, 2, 1, 2, 4, 1, 3, 4, 5, 4, 6};
		assertFileContent(getFilePath(BFS), expectedContent);
	}

	public void testBFS2() throws Exception {
		graph = GraphUtil.loadGraph(getFilePath(BFS_IN_2), getType(), DATA_STRUCTURE);

		GraphUtil.breadthFirstSearch(graph, 6, getFilePath(BFS));
		
		int[] expectedContent = {6,
								 6, 2,
								 6, 5,
								 6, 7,
								 2, 1,
								 2, 4,
								 1, 3};
		assertFileContent(getFilePath(BFS), expectedContent);
	}
	
	public void testDFS() throws Exception {
		graph = GraphUtil.loadGraph(getFilePath(DFS_IN), getType(), DATA_STRUCTURE);
		
		GraphUtil.depthFirstSearch(graph, 4, getFilePath(DFS));
		
		int[] expectedContent = {4, 4, 1, 1, 3, 3, 2, 3, 8, 4, 6, 6, 5, 5, 7};
		assertFileContent(getFilePath(DFS), expectedContent);
	}
	
	protected void assertFileContent(String fileName, int[] expectedContent) throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(fileName));
		for (int number : expectedContent) {
			int readedNumber = readInt(scanner);
			assertEquals(readedNumber, number);
		}
		assertTrue(!scanner.hasNext());
	}

	private int readInt(Scanner scanner) {
		int readedNumber;		
		try {
			readedNumber = scanner.nextInt();
			return readedNumber;
		} catch (InputMismatchException e) {
			scanner.next();
			return readInt(scanner);
		}		
	}

	protected String getFilePath(String fileName) {		
		return PATH + fileName;
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		graph = null;
	}
	
}
