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

import br.mendonca.gmgraphlib.exception.InvalidEdgeException;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public interface WeightedGraph extends Graph {

	int TYPE = 1 << 2;

	/**
	 * Adds an edge to the graph.
	 * @param v1 first vertex connected to the edge.
	 * @param v2 second vertex connected to the edge.
	 * @param weight weight of the edge.
	 * @throws InvalidEdgeException 
	 */
	void addEdge(int v1, int v2, float weight) throws InvalidEdgeException;

	/**
	 * Returns the weight of the edge that connects the
	 * vertices <code>v1</code> and <code>v2</code>.
	 * @param v1 first vertex connected to the edge.
	 * @param v2 second vertex connected to the edge.
	 * @return the weight of the edge that connects v1 and v2
	 */
	Float getWeight(int v1, int v2);

	/**
	 * If there is an edge connecting the vertices <code>v1</code> and <code>v2</code>
	 * then changes its weight to <code>weight</code>.
	 * Else, throws an InvalidEdgeException.
	 * @param v1 first vertex connected to the edge.
	 * @param v2 second vertex connected to the edge.
	 * @param weight the new weight of the edge
	 * @throws InvalidEdgeException
	 */
	void setWeight(int v1, int v2, float weight) throws InvalidEdgeException;

	/**
	 * Returns the weight of the edge <code>edge</code>
	 * @param edge an edge
	 * @return the weight of the edge <code>edge</code>
	 */
	Float getWeight(Edge edge);

	/**
	 * Adds the edge <code>edge</code> to the graph.
	 * @param edge
	 * @param weight
	 * @throws InvalidEdgeException 
	 */
	void addEdge(Edge edge, Float weight) throws InvalidEdgeException;

}
