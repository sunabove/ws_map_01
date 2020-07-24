package br.mendonca.gmgraphlib;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEdgeList<T> extends ArrayList<List<T>> implements EdgeList {

	private static final long serialVersionUID = -1602314549480567773L;
	protected int v1Index = 0;
	protected int v2Index = 0;
	protected int numberOfEdges = 0;

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.EdgeList#addEdge(int, int)
	 */
	public void addEdge(int v1, int v2) {
		addEdge(v1, v2, 1f);
	}

	public AbstractEdgeList(int numberOfVertices) {
		super(numberOfVertices);

		for (int i = 0; i < numberOfVertices; i++) {
			addNewVertex(numberOfVertices);
		}
	}

	protected abstract void addNewVertex(int numberOfVertices);

	protected void updateIndexes() {
		v2Index++;
		if (v2Index == get(v1Index).size()) {
			v1Index++;
			v2Index = 0;
		}
	}

	public AbstractEdgeList() {
		super();
	}

	/* (non-Javadoc)
	 * @see br.mendonca.gmgraphlib.EdgeList#getNumberOfEdges()
	 */
	public int getNumberOfEdges() {
		return numberOfEdges;
	}
	
	@Override
	public String toString() {
		StringBuilder output = new StringBuilder(); 
		
		for (List<T> line : this) {
			output.append(line);
			output.append(GraphUtil.NEWLINE);
		}
		
		return output.toString();
	}

	public void remove() {
		throw new UnsupportedOperationException();		
	}

}