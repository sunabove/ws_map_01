package com.ynhenc.gis.model.mapobj;


import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.util.*;
import com.ynhenc.gis.*;
import com.ynhenc.gis.model.shape.*;

public abstract class MapObject extends GisComLib implements MbrInterface {

	public void setName(String name) {
		this.name = name;
	}

	public MapObject(String name, int index) {
		this.name = name;
		this.index = index;
		this.isSelected = false;
	}

	private String getKind() {
		return ClassUtil.getSimplifiedName(this.getClass());
	}

	public final String getName() {
		return this.name;
	}

	@Override
	public int hashCode() {
		return this.index;
	}

	public final void setIndex(int index) {
		this.index = index;
	}

	public final int getIndex() {
		return this.index;
	}

	public void setSelected(boolean b) {
		this.isSelected = b;
	}


	public void setSelected(Boolean b) {
		this.setSelected( b.booleanValue() );
	}

	public boolean isSelected() {
		return this.isSelected;
	}

	public final void setMbr( Mbr mbr) {
		this.mbr = mbr ;
	}

	public final Mbr getMbr() {
		if (this.mbr != null) {
			return this.mbr;
		} else {
			this.mbr = this.calcMbr();
			return this.mbr;
		}
	}

	@Override
	public String toString() {
		String info = ClassUtil.getSimplifiedName(this.getClass()) + " : "
				+ this.getName() + ", index = " + this.index;
		return info;
	}

	// abstract member

	abstract protected Mbr calcMbr();

	// member

	private String name;
	private int index;
	protected transient Mbr mbr;

	private boolean isSelected;

	// end of member

}
