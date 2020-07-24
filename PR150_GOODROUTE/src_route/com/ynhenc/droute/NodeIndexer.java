package com.ynhenc.droute;

public class NodeIndexer extends Indexer<Node> {

	public NodeIndexer( int size ) {
		super( size , Node.getNode( new IndexId( -1, -1L ) , null ) );
	}

}
