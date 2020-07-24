package com.ynhenc.droute;

import com.ynhenc.comm.ArrayListEx;

public class LinkList extends ArrayListEx<Link > {

	/**
	 *
	 */
	private static final long serialVersionUID = -2609146767525711910L;

	public ArrayListEx<Link> getFromLinkList( Node node ) {
		ArrayListEx<Link> nodeLinkList = new ArrayListEx<Link>();
		for( Link link : this ) {
			if( node == link.getFromNode() ) {
				nodeLinkList.add(link);
			}
		}
		return nodeLinkList;
	}

	public ArrayListEx<Link> getToLinkList( Node node ) {
		ArrayListEx<Link> nodeLinkList = new ArrayListEx<Link>();
		for( Link link : this ) {
			if( node == link.getToNode() ) {
				nodeLinkList.add(link);
			}
		}
		return nodeLinkList;
	}
}
