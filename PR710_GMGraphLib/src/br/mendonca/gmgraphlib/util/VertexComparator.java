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
public class VertexComparator implements Comparator<Vertex> {

	private List<Float> distance;

	public VertexComparator(List<Float> distance) {
		this.distance = distance;
	}

	public int compare(Vertex o1, Vertex o2) {
		float d1 = distance.get(o1.index);
        float d2 = distance.get(o2.index);

        if (d1 > d2)
        {
            return +1;
        }
        else if (d1 < d2)
        {
            return -1;
        }
        else
        {
            return o1.compareTo(o2);
        }

	}
	
}
