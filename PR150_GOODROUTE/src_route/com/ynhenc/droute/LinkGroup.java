package com.ynhenc.droute;

import java.util.*;

public class LinkGroup extends ComLibRoute implements IndexInterface {

	public Node getFromNode() {
		return fromNode;
	}

	public int getIndex() {
		return this.getFromNode().getIndex();
	}

	public Link[] getGroupLinkList(LinkList linkList) {
		if( this.linkArray != null) {
			return this.linkArray;
		} else {
			Link [] linkArray = new Link[ to - from ];
			for( int i = 0, iLen = to - from ; i < iLen ; i ++ ) {
				linkArray[ i ] = linkList.get( from + i );
			}
			this.linkArray = linkArray;
		}
		return this.linkArray;
	}

	public LinkGroup( Node fromNode, int from, int to) {
		super();
		this.fromNode = fromNode;
		this.from = from;
		this.to = to;
	}

	private Node fromNode;
	private int from;
	private int to;

	private transient Link [] linkArray ;
}
