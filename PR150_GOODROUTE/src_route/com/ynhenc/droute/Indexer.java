package com.ynhenc.droute;

import java.lang.reflect.Array;
import java.util.Iterator;

import com.ynhenc.comm.ArrayListEx;

public class Indexer<Type> extends ComLibRoute {

	public final Type [] getAllIndexElments() {
		return this.indexList;
	}

	public final void add( Type obj ) {
		//this.debug( "node index = " + node.getIndex() , true );
		if( this.indexList == null ) {
			this.initIndexer(obj);
		}
		this.indexList[ ((IndexInterface)obj).getIndex() ] = obj ;
	}


	public final void remove( int index ) {
		this.indexList[ index ] = null;
	}

	public final Type get( int index ) {
		return this.indexList[ index ];
	}

	public final int getSize() {
		return this.size;
	}

	private final void initIndexer(Type obj) {
		this.indexList = (Type[]) Array.newInstance( obj.getClass(), this.getSize() );
		//this.indexList = new Type[ this.getSize() ] ;
		//this.indexList = (Type[]) new Object[ this.getSize() ];
	}

	public Indexer( int size , Type sample ) {
		//this.debug( "node index size = " + size , true );
		this.size = size;
		this.initIndexer( sample );
	}

	private int size = 0;
	protected Type [] indexList ;

}
