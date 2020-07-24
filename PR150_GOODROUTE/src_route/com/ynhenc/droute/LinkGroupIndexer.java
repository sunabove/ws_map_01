package com.ynhenc.droute;

import java.util.Iterator;

public class LinkGroupIndexer extends Indexer<LinkGroup> {

	public LinkGroupIndexer( int size  ) {
		super( size , new LinkGroup(null, -1, -1) );
	}
}
