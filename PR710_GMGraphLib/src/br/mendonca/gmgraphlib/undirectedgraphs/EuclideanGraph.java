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

import java.util.Scanner;

import br.mendonca.gmgraphlib.exception.InvalidEdgeException;


/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class EuclideanGraph extends AsUndirectedWeightedGraph {
	
	public static final int TYPE = UndirectedWeightedGraph.TYPE | 1 << 3;
	
	/**
	 * @param numberOfVertices
	 * @param dataStructure 
	 */
	public EuclideanGraph(int numberOfVertices, int dataStructure) {
		super(numberOfVertices, dataStructure);
	}
	
	@Override
	public void addAllEdges(Scanner scanner) throws InvalidEdgeException {
		int[] x = new int[getNumberOfVertices()];
		int[] y = new int[getNumberOfVertices()];
		
		for (int k = 0; scanner.hasNext(); k++) {
			x[k] = scanner.nextInt();
			y[k] = scanner.nextInt();
		}
		
		for (int i = 0; i < getNumberOfVertices(); i++) {
			for (int j = i + 1; j < getNumberOfVertices(); j++) {				
				int dx = x[i] - x[j];
				int dy = y[i] - y[j];			
				float distance = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
				addEdge(i + 1, j + 1, distance);
			}
		}
	}
	
	@Override
	public int getType() {
		return TYPE;
	}
}
