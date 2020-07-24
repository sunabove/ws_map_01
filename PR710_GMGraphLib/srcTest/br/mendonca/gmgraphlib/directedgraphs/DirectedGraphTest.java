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

import java.util.List;

import br.mendonca.gmgraphlib.GraphTest;
import br.mendonca.gmgraphlib.Vertex;
import br.mendonca.gmgraphlib.directedgraphs.AsDirectedGraph;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class DirectedGraphTest extends GraphTest {

	@Override
	protected int getType() {
		return AsDirectedGraph.TYPE;
	}
	
	@Override
	public void testGetDistance() throws Exception {
		super.testGetDistance();
		assertEquals(graph.getDistance(3, 2), 2f);
	}
	
	@Override
	public void testGetMinPath() throws Exception {
		super.testGetMinPath();

		List<Vertex> path = graph.getPath(1, 6);
		
		assertEquals(path.get(0).index, 1);
		assertEquals(path.get(1).index, 2);
		assertEquals(path.get(2).index, 3);
		assertEquals(path.get(3).index, 5);
		assertEquals(path.get(4).index, 6);
	}

	@Override
	public void testGetAverageDistance() throws Exception {
		// TODO Auto-generated method stub		
	}

	@Override
	public void testGetDiameter() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
