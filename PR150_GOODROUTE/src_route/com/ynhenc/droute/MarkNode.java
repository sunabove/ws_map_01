package com.ynhenc.droute;

public class MarkNode extends Mark {

	private MarkNode( Node node ) {
		super( node );
	}

	public static MarkNode getNewMark( Node node ) {
		return new MarkNode( node );
	}

}
