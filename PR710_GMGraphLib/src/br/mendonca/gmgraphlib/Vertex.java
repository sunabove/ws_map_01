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
public class Vertex {

	public int index;
	public boolean visited = false;
	public boolean connected = false;
	public Vertex parent = null;
	public Vertex cycleParent = null;
	public int inDegree = 0;
	public int outDegree = 0;
	
	public Vertex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return String.valueOf(index);
	}

	public int compareTo(Vertex o2) {
		Integer index = this.index;
		return index.compareTo(o2.index);
	}
}
