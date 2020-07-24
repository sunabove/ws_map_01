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
package br.mendonca.gmgraphlib.casestudies;

import java.io.IOException;
import java.util.Calendar;

import junit.framework.TestCase;
import br.mendonca.gmgraphlib.GraphUtil;
import br.mendonca.gmgraphlib.WeightedGraph;
import br.mendonca.gmgraphlib.undirectedgraphs.EuclideanGraph;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class CaseStudies extends TestCase {

	private static final String PATH = "srcTest/testFiles/casestudies/";
	
	private static final String POINTS = "points-";

	public void testPoints() throws Exception {
		testEuclideanGraph(POINTS + 100 + "_new.txt", POINTS + 100 + "_new.out", 0.0f);
	}	
	
	private void testPoints(int n) throws Exception {
		testEuclideanGraph(POINTS + n + ".txt", POINTS + n + ".out", 0.0f);
	}

	private void testEuclideanGraph(String inFile, String outFile, float expectedCost) throws Exception {
		EuclideanGraph graph = GraphUtil.loadEuclideanGraph(getFilePath(inFile));
		
		testTSP(graph, expectedCost, outFile);
	}

	private void testTSP(WeightedGraph graph, float expectedCost, String outFile) throws IOException {
		Calendar time = Calendar.getInstance();
		time.add(Calendar.MINUTE, 1);
		
		float totalCost = GraphUtil.travellingSalesman(graph, getFilePath(outFile), time);
		
		assertEquals(totalCost, expectedCost, 0.01f);
	}

	protected String getFilePath(String fileName) {		
		return PATH + fileName;
	}
	
}
