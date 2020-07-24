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
package br.mendonca.gmgraphlib.util;

import java.util.Comparator;
import java.util.List;

import br.mendonca.gmgraphlib.Vertex;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class ConnectedComponentComparator implements Comparator<List<Vertex>> {

	public int compare(List<Vertex> o1, List<Vertex> o2) {		
		return o2.size() - o1.size();
	}

}
