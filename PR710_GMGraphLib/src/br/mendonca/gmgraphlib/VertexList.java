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

import java.util.ArrayList;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class VertexList extends ArrayList<Vertex> {

	private static final long serialVersionUID = 5595471693655512453L;
	
	public VertexList(int numberOfVertices) {
		super(numberOfVertices);
		
		for (int i = 0; i < numberOfVertices; i++) {
			add(new Vertex(i + 1));
		}
	}
	
	@Override
	public boolean add(Vertex e) {
		if (e.index == 0) {
			e.index = size() + 1;
		}
		
		return super.add(e);
	}
	
}
