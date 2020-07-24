package com.ynhenc.comm;

import java.util.ArrayList;

/**
 * @author sbmoon
 *
 * @param <Type>
 */
public class ArrayListEx<Type> extends ArrayList<Type> {

	@Override
	public Type get(int index) {
		if( super.size() < 1 ) {
			return null;
		} else if( index == -1 ) { // returns the last element
			return super.get( super.size() - 1 );
		} else if( index < 0 ) {
			return null;
		} else if( index < super.size()) {
			return super.get(index);
		} else {
			return null;
		}
	}

	public void addList(ArrayListEx<Type> list) {
		Type o;
		for (int i = 0, iLen = list.size(); i < iLen; i++) {
			o = list.get(i);
			if (o != null) {
				this.add(o);
			}
		}
	}

	public void addList(ArrayListEx<Type> list, int pos) {
		if (this.size() < 1) {
			pos = 0;
		} else {
			pos = pos < 0 ? this.size() : pos;
			pos = pos > this.size() - 1 ? this.size() : pos;
		}

		Type o;
		for (int i = 0, iLen = list.size(); i < iLen; i++) {
			o = list.get( iLen - i - 1 );
			if (o != null) {
				this.add(pos, o);
			}
		}
	}

	public final Iterable<Type> getIterable() {
		return this;
	}

	public Type getFirst() {
		return this.get( 0 );
	}

	public Type getLast() {
		return this.get( - 1 );
	}

	@Override
	public boolean add(Type o) {
		if (null == o) {
			return false;
		} else {
			return super.add(o);
		}
	}

	public final int getSize() {
		return super.size();
	}

	public ArrayListEx() {
		super();
	}

	public ArrayListEx( int size ) {
		super( size );
	}


	/**
	 *
	 */
	private static final long serialVersionUID = -8205099199490314061L;

}
