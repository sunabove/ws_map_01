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

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class Edge {
	
	public int vertex1;
	public int vertex2;
	
	/**
	 * Creates a new Edge.
	 * 
	 * @param v1 first vertex connected to the edge
	 * @param v2 second vertex connected to the edge
	 */
	public Edge(int v1, int v2) {
		this.vertex1 = v1;
		this.vertex2 = v2;
	}

	public String toString() {
		return vertex1 + " " + vertex2;
	}
	
	@Override
	public boolean equals(Object obj) {
		try {
			Edge otherEdge = (Edge) obj;
			boolean result = (this.vertex1 == otherEdge.vertex1 && 
							  this.vertex2 == otherEdge.vertex2);
			result = result || (this.vertex1 == otherEdge.vertex2 && 
					  			this.vertex2 == otherEdge.vertex1);
			return result;
		} catch (Exception e) {
			return super.equals(obj);
		}
	}

	public Edge revert() {
		return new Edge(vertex2, vertex1);
	}
}
