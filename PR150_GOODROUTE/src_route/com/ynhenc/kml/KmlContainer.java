package com.ynhenc.kml;

import java.io.Writer;

import com.ynhenc.comm.ArrayListEx;

public abstract class KmlContainer<Type> extends KmlObject {

	public Iterable<Type> getCompList() {
		return compList;
	}

	public void addComponent( Type type ) {
		this.compList.add(type);
	}

	public KmlContainer(String name, String desc) {
		super(name, desc);
		this.compList = new ArrayListEx< Type >();
	}

	private ArrayListEx< Type > compList;

}
