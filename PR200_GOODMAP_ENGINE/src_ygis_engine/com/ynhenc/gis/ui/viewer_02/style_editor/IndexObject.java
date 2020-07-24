package com.ynhenc.gis.ui.viewer_02.style_editor;

public class IndexObject {

	public int getIndex() {
		return this.index;
	}

	public Object getValue() {
		return this.value;
	}

	@Override
	public String toString() {
		return "" + this.value;
	}

	public IndexObject(int index, Object value) {
		this.index = index;
		this.value = value;
	}

	private int index;
	private Object value;

	public static IndexObject [] getIndexObjectList( Object [] objList ) {
		IndexObject [] indexObjList  = new IndexObject[ objList.length ];

		int index = 0;
		for( Object obj : objList ) {
			indexObjList[ index ] = new IndexObject( index , obj );
			index ++;
		}

		return indexObjList ;
	}
}