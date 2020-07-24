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

import java.util.Collections;
import java.util.LinkedList;

/** 
 * With a heuristic based on a Greedy Algorithm, gives an approximated 
 * answer to the Travelling Salesman Problem.<br>
 * First, a random solution is found.<br>
 * Then, between all pairs of edges in the solution given, select the one
 * which gives the shortest path when the two edges are swapped.<br>
 * Repeats the step above as long as there is a pair that satisfies 
 * the condition.<br>
 *
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 */
public class TravellingSalesman implements Runnable {
			
	private WeightedGraph originalGraph;
	private LinkedList<Integer> solution;
	private int n;

	public TravellingSalesman(WeightedGraph graph) {
		this.originalGraph = graph;
		this.n = originalGraph.getNumberOfVertices();
	}

	public void run() {
		solution = getRandomTour();

		int[] pair = getSwap();
		while (pair != null) {
			swapEdges(pair);
						
			pair = getSwap();
		}
			
	}

	/**
	 * @return a <code>LinkedList</code> with a random sequence of vertices, indicating a random tour.
	 */
	private LinkedList<Integer> getRandomTour() {
		LinkedList<Integer> randomTour = new LinkedList<Integer>();
	
		randomTour = createListOfVertices();
		Collections.shuffle(randomTour);
			
		return randomTour;
	}

	/**
	 * Returns an int array that represents the vertices that will be swapped into the tour.<br>
	 * The vertices chosen are the ones that will reduce more the cost of the tour. 
	 * @return an int array that represents the vertices that will be swapped into the tour.
	 */
	private int[] getSwap() {
		int[] result = null;
		float max = 0.0f;
		for (int i = 0; i < n; i++) {
			int limit = i > 0 ? n : n-1;
			for (int j = i + 2; j < limit; j++) {
				Edge[] oldPair = getOldPair(i, j);
				float oldPairCost = originalGraph.getWeight(oldPair[0]) + 
									originalGraph.getWeight(oldPair[1]);
				
				Edge[] newPair = getNewPair(i, j);
				float newPairCost = originalGraph.getWeight(newPair[0]) + 
									originalGraph.getWeight(newPair[1]);
	
				float delta = (oldPairCost - newPairCost);
				if (max < delta) {
					max = delta;
					result = new int[2];
					result[0] = i + 1;
					result[1] = j;
				}
			}
		}
		
		return result;
	}

	/**
	 * Swaps the edges, swapping the vertices into the tour.
	 * @param pair
	 */
	private void swapEdges(int[] pair) {
		int nSwaps = (int) (pair[1] - pair[0] + 1) / 2;
		for (int i = 0; i < nSwaps; i++) {
			Collections.swap(solution, pair[0] + i, pair[1] - i);
		}		
	}

	private LinkedList<Integer> createListOfVertices() {
		LinkedList<Integer> result = new LinkedList<Integer>();
		for (int i = 1; i <= n; i++) {
			result.add(i);
		}
		return result;
	}

	private Edge[] getOldPair(int i, int j) {
		Edge firstEdge = new Edge(solution.get(i), solution.get(i + 1));				
		Edge secondEdge = new Edge(solution.get(j), getLastIndex(j));
		
		Edge[] oldPair = {firstEdge, secondEdge};
		
		return oldPair;
	}

	private Edge[] getNewPair(int i, int j) {
		Edge firstEdge = new Edge(solution.get(i), solution.get(j));				
		Edge secondEdge = new Edge(solution.get(i + 1), getLastIndex(j));
		
		Edge[] newPair = {firstEdge, secondEdge};
		
		return newPair;
	}

	private int getLastIndex(int index) {		
		return (index + 1 < n) ? solution.get(index + 1) : solution.getFirst();
	}

	/**
	 * @return the tour found by this instance.
	 */
	public String getTour() {
		StringBuilder result = new StringBuilder();
		
		for (int index : solution) {
			result.append(index + GraphUtil.NEWLINE);
		}
		
		result.append(solution.getFirst());
		
		return result.toString();
	}

	/**
	 * @return the total cost of the solution found by this instance.
	 */
	public float getTotalCost() {
		float totalCost = 0.0f;
		
		for (int i = 0; i < n; i++) {
			int v1 = solution.get(i);
			int v2 = getLastIndex(i);
						
			totalCost += originalGraph.getWeight(v1, v2);
		}
		
		return totalCost;
	}

}
