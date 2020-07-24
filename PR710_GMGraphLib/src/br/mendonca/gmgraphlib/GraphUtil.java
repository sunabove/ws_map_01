package br.mendonca.gmgraphlib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import br.mendonca.gmgraphlib.directedgraphs.DirectedGraph;
import br.mendonca.gmgraphlib.exception.EmptyFileException;
import br.mendonca.gmgraphlib.exception.InvalidEdgeException;
import br.mendonca.gmgraphlib.exception.InvalidGraphTypeException;
import br.mendonca.gmgraphlib.undirectedgraphs.EuclideanGraph;
import br.mendonca.gmgraphlib.undirectedgraphs.UndirectedGraph;
import br.mendonca.gmgraphlib.undirectedgraphs.UndirectedWeightedGraph;
import br.mendonca.gmgraphlib.util.ConnectedComponentComparator;
import br.mendonca.gmgraphlib.util.DegreeListComparator;

/**
 * @author Gabriel Mendonça - gabrielgmendonca@gmail.com
 *
 */
public class GraphUtil {	
	
	static final String NEWLINE = System.getProperty("line.separator");
	private static List<Edge> writedEdges = new LinkedList<Edge>();
	
	/**
	 * Creates a graph from a source file.
	 * @param filePath name of the file that will be readed.
	 * @param graphType TODO
	 * @throws InvalidEdgeException 
	 */
	public static Graph loadGraph(String filePath, int graphType, int dataStructure) throws EmptyFileException, InvalidEdgeException {
		Scanner scanner = null;
		
		try {
			scanner = new Scanner(new File(filePath));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return loadGraph(scanner, graphType, dataStructure);
	}
	
	/**
	 * Creates a graph from a source file.
	 * @param graphType TODO
	 * @param dataStructure TODO
	 * @param reader
	 */
	public static Graph loadGraph(Scanner scanner, int graphType, int dataStructure) 
								  throws EmptyFileException, InvalidEdgeException {		
		try {
			Integer numberOfVertices;			
			if (scanner.hasNext()) {
				numberOfVertices = new Integer(scanner.nextInt());
			} else {
				throw new EmptyFileException();
			}
			
			Graph graph = AbstractGraph.getInstance(graphType, numberOfVertices, dataStructure);
			
			graph.addAllEdges(scanner);
			
			return graph;	
			
		} catch (InvalidGraphTypeException e) {
			e.printStackTrace();
			return null;
		} finally {
			scanner.close();
		}
	}

	public static DirectedGraph loadDirectedGraph(String filePath, int dataStructure) throws EmptyFileException, InvalidEdgeException {
		return (DirectedGraph) loadGraph(filePath, DirectedGraph.TYPE, dataStructure);
	}

	public static UndirectedGraph loadUndirectedGraph(String filePath, int dataStructure) throws EmptyFileException, InvalidEdgeException {
		return (UndirectedGraph) loadGraph(filePath, UndirectedGraph.TYPE, 
										   dataStructure);
	}

	public static UndirectedWeightedGraph loadUndirectedWeightedGraph(String filePath, int data_structure) throws EmptyFileException, InvalidEdgeException {
		return (UndirectedWeightedGraph) loadGraph(filePath, 
												   UndirectedWeightedGraph.TYPE,
										 		   data_structure);
	}

	public static EuclideanGraph loadEuclideanGraph(String filePath) throws EmptyFileException, InvalidEdgeException {
		return (EuclideanGraph) loadGraph(filePath,
										  EuclideanGraph.TYPE,
										  AdjacencyMatrix.TYPE);
	}

	/**
	 * Writes the basic informations of a graph in a file.
	 * @param graph the graph that will be saved.
	 * @param filePath
	 * @throws IOException 
	 */
	public static void saveGraph(Graph graph, String filePath) throws IOException {		
		StringBuilder output = new StringBuilder();
		output.append(graph.getNumberOfVertices());
		
		Iterator<Edge> edgeIterator = graph.getEdgeIterator();
		while (edgeIterator.hasNext()) {
			Edge edge = edgeIterator.next();
			
			writeEdge(edge, output);
		}
		
		write(filePath, output.toString());
		writedEdges.clear();
	}


	public static void breadthFirstSearch(Graph graph, int vertexIndex, String filePath) throws IOException {
		StringBuilder output = new StringBuilder();
		Vertex root = graph.getVertex(vertexIndex);
		Runnable bfs = new BreadthFirstSearch(graph, root, output);
		
		bfs.run();
		
		write(filePath, output.toString());
	}

	public static void depthFirstSearch(Graph graph, int vertexIndex, String filePath) throws IOException {
		graph.unmarkAll();
		
		Stack<Vertex> stack = new Stack<Vertex>();
		StringBuilder output = new StringBuilder("root " + vertexIndex + NEWLINE);
		
		Vertex initialVertex = graph.getVertex(vertexIndex);
		stack.push(initialVertex);
		
		while (!stack.empty()) {
			Vertex vertex = stack.pop();
			
			if (!vertex.visited) {
				vertex.visited = true;
				
				if (vertex.parent != null) {
					output.append(vertex.parent.index + " <= " + vertex.index + NEWLINE);
				}

				List<Vertex> neighborhood = graph.getNeighbors(vertex);				
				for (int i = neighborhood.size() - 1; i >= 0; i--) {
					Vertex child = neighborhood.get(i);
					stack.push(child);
					child.parent = vertex;
				}
			}
		}	
		write(filePath, output.toString());
	}

	public static void detectCycles(UndirectedGraph graph, String filePath) throws IOException {
		graph.unmarkAll();
		
		StringBuilder output = new StringBuilder();
		
		Vertex cycleVertex = graph.getVertex(1);
		cycleVertex = visit(graph, cycleVertex);
		
		if (cycleVertex != null) {
			Vertex parent = cycleVertex;
			while (parent != cycleVertex.cycleParent) {
				output.append(parent + NEWLINE);
				parent = parent.parent;
			}
			output.append(cycleVertex.cycleParent + NEWLINE);
			
		} else {
			output.append("Acíclico");
		}
		write(filePath, output.toString());
	}

	
	
	private static Vertex visit(Graph graph, Vertex cycleVertex) {
		cycleVertex.visited = true;
		for (Vertex child : graph.getNeighbors(cycleVertex)) {
			if (!child.visited) {
				child.visited = true;
				child.parent = cycleVertex;
				
				Vertex childVisit = visit(graph, child);
				if (childVisit != null) {
					return childVisit;
				}
			} else if (child != cycleVertex.parent) {
				cycleVertex.cycleParent = child;
				return cycleVertex;
			}
		}
		return null;
	}

	public static void detectCycles(DirectedGraph graph, String filePath) throws IOException {		
		List<List<Vertex>> connectedComponentList = 
			getConnectedComponents(graph, filePath, DirectedGraph.TYPE);
		
		StringBuilder output = new StringBuilder();
		
		if (!connectedComponentList.isEmpty()) {
			for (List<Vertex> connectedComponent : connectedComponentList) {
				if (connectedComponent.size() > 1) {
					for (Vertex vertex : connectedComponent) {
						output.append(vertex + NEWLINE);
					}
					break;
				}
			}
			
		}
		if (output.length() == 0) {
			output.append("Acyclic");
		}
		
		write(filePath, output.toString());
	}

	public static void detectConnectedComponents(UndirectedGraph graph, String filePath) throws IOException {
		List<List<Vertex>> connectedComponentList = 
					getConnectedComponents(graph, filePath, UndirectedGraph.TYPE);
		
		StringBuilder output = createOutput(connectedComponentList);
		write(filePath, output.toString());
	}

	public static void detectConnectedComponents(DirectedGraph graph, String filePath) throws IOException {
		List<List<Vertex>> connectedComponentList = 
			getConnectedComponents(graph, filePath, DirectedGraph.TYPE);

		StringBuilder output = createOutput(connectedComponentList);
		write(filePath, output.toString());
	}
	
	public static void writeGraphInfo(UndirectedGraph graph, String filePath) throws IOException {
		StringBuilder output = new StringBuilder();
		int numberOfVertices = graph.getNumberOfVertices();
		
		output.append("n = " + numberOfVertices + NEWLINE);
		output.append("m = " + graph.getNumberOfEdges() + NEWLINE);
		
		List<int[]> list = new ArrayList<int[]>(numberOfVertices);
		for (int i = 1; i <= numberOfVertices; i++) {
			int[] vertexDegree = {i, graph.getDegree(i)};
			list.add(vertexDegree);
		}
		
		Collections.sort(list, new DegreeListComparator());
		
		for (int[] vertexDegree : list) {
			output.append(vertexDegree[0] + " : " + vertexDegree[1] + NEWLINE);
		}
		
		write(filePath, output.toString());
	}

	public static void writeGraphInfo(DirectedGraph graph, String filePath) throws IOException {
		StringBuilder output = new StringBuilder();
		int numberOfVertices = graph.getNumberOfVertices();
		
		output.append("n = " + numberOfVertices + NEWLINE);
		output.append("m = " + graph.getNumberOfEdges() + NEWLINE);
		
		List<int[]> list = new ArrayList<int[]>(numberOfVertices);
		for (int i = 1; i <= numberOfVertices; i++) {
			Vertex vertex = graph.getVertex(i);
						
			int[] vertexDegree = {vertex.index, graph.getInDegree(vertex.index), graph.getOutDegree(vertex.index)};
			list.add(vertexDegree);
		}
		
		Collections.sort(list, new DegreeListComparator());
		
		for (int[] vertexDegree : list) {
			output.append(vertexDegree[0] + " : in " + vertexDegree[1] + " out " + vertexDegree[2] + NEWLINE);
		}
		
		write(filePath, output.toString());
	}

	private static List<List<Vertex>> getConnectedComponents(Graph graph, String filePath, int type) throws IOException {
		graph.unmarkAll();
		
		List<List<Vertex>> connectedComponentList = new LinkedList<List<Vertex>>();
		Vertex root = graph.getVertex(1);
		
		while (root != null) {
			if (!root.connected) {
				graph.markVertices(root);
	
				List<Vertex> connectedComponent = new LinkedList<Vertex>();
				
				for (Vertex vertex = root; vertex != null; 
							vertex = graph.getVertex(vertex.index + 1)) { 
					if (vertex.visited && !vertex.connected) {
						vertex.connected = true;
						connectedComponent.add(vertex);
					}
					
				}
				
				if (!connectedComponent.isEmpty()) {
					connectedComponentList.add(connectedComponent);
				}
			}
			
			root = graph.getVertex(root.index + 1);
		}
		
		Collections.sort(connectedComponentList, new ConnectedComponentComparator());
		
		return connectedComponentList;
	}

	private static StringBuilder createOutput(List<List<Vertex>> connectedComponentList) {
		StringBuilder output = new StringBuilder();
		
		int i = 1;
		for (List<Vertex> connectedComponent : connectedComponentList) {
			output.append("Connected Component N° " + i++ + NEWLINE);
			
			for (Vertex vertex : connectedComponent) {
				output.append(vertex.index + NEWLINE);
			}
		}
		
		return output;
	}

	/**
	 * Writes the specified StringBuilder to a file.
	 * @param filePath the path of the output file
	 * @param output the StringBuilder with the content that will be saved
	 * @throws IOException
	 */
	private static void write(String filePath, String output) throws IOException {
		File file = new File(filePath);
		FileWriter fileWriter = new FileWriter(file);
		BufferedWriter writer = new BufferedWriter(fileWriter);
		
		try {
			writer.write(output);
			
		} finally {
			writer.close();			
		}
	}

	private static void writeEdge(Edge edge, StringBuilder output) throws IOException {
		if (!writedEdges.contains(edge)) {
			output.append(NEWLINE + edge.toString());
			writedEdges.add(edge);
		}
	}

	public static MinimumSpanningTree minimumSpanningTree(WeightedGraph graph) {		
		Prim prim = new Prim(graph, 1);		
		prim.run();
		
		return prim.mst;
	}
	
	public static void minimumSpanningTree(WeightedGraph graph, String filePath) throws IOException {
		MinimumSpanningTree mst = minimumSpanningTree(graph);
		write(filePath, mst.toString());
	}

	/**
	 * Calculates an approximated solution of the Travelling Salesman Problem and
	 * writes it to the file indicated by <code>filePath</code>.
	 * To optimize the execution, two solutions are found in parallel for each iteration.
	 * For more details, look at the class <code>TravellingSalesman</code>.
	 * 
	 * @param graph 
	 * @param filePath 
	 * @return the tour's total cost.
	 * @throws IOException 
	 * @see TravellingSalesman
	 */
	public static float travellingSalesman(WeightedGraph graph, String filePath) throws IOException {
		return travellingSalesman(graph, filePath, null);
	}

	/**
	 * Calculates an approximated solution of the Travelling Salesman Problem and
	 * writes it to the file indicated by <code>filePath</code>.
	 * To obtain a better result, the algorithm is executed until <code>time</code>.
	 * To optimize the execution, two solutions are found in parallel for each iteration.
	 * For more details, look at the class <code>TravellingSalesman</code>.
	 * 
	 * @param graph
	 * @param filePath
	 * @return the tour's total cost.
	 * @throws IOException 
	 * @see TravellingSalesman
	 */
	public static float travellingSalesman(WeightedGraph graph, String filePath, Calendar time) throws IOException {
		TravellingSalesman tsp = null;
		
		do {
			tsp = runTSPThreads(graph, tsp);
			
		} while (time != null && Calendar.getInstance().before(time));
		
		String tour = tsp.getTour();
		write(filePath, tour);
		
		return tsp.getTotalCost();
	}

	/**
	 * Starts two threads that execute the method <code>run()</code> 
	 * of <code>TravellingSalesman</code>. <br>
	 * Returns the instance that obtained a better result.
	 * @param graph
	 * @param tsp 
	 * @return the instance that obtained a better result.
	 */
	private static TravellingSalesman runTSPThreads(WeightedGraph graph, TravellingSalesman tsp) {
		try {
			TravellingSalesman threadedTsp1 = new TravellingSalesman(graph);
			TravellingSalesman threadedTsp2 = new TravellingSalesman(graph);
			
			Thread thread1 = new Thread(threadedTsp1);
			Thread thread2 = new Thread(threadedTsp2);
			
			thread1.start();
			thread2.start();

			thread1.join();
			thread2.join();

			float threadCost1 = threadedTsp1.getTotalCost();
			float threadCost2 = threadedTsp2.getTotalCost();
			float actualCost = (tsp != null) ? tsp.getTotalCost() : Float.POSITIVE_INFINITY; 				
			if (threadCost1 < actualCost || threadCost2 < actualCost) {
				if (threadCost1 < threadCost2) {
					tsp = threadedTsp1;
				} else {
					tsp = threadedTsp2;
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return tsp;
	}
	
}
