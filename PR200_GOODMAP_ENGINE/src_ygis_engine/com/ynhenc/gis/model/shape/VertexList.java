package com.ynhenc.gis.model.shape;

import java.util.*;

import com.ynhenc.comm.ArrayListEx;

public class VertexList extends ArrayListEx<Vertex> {

	public NodeList getNodeList() {

		NodeList nodeList = new NodeList();

		VertexList vertexList = this;

		for( Vertex vertex : vertexList ) {
			if( vertex instanceof Node ) {
				nodeList.add( (Node) vertex );
			}
		}

		return nodeList;

	}
}
