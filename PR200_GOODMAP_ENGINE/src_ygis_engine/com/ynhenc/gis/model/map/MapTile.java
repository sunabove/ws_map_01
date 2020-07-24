package com.ynhenc.gis.model.map;

import java.awt.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.*;
import com.ynhenc.gis.model.shape.*;

public class MapTile extends GisComLib {

	public Mbr getMbr() {
		return mbr;
	}
	public int getIndex() {
		return index;
	}

	public MapTile( int index, Mbr mbr ) {
		this.index = index;
		this.mbr = mbr;
	}

	public PntShort getCentroid() {
		if( this.mbr != null ) {
			return this.mbr.getCentroid();
		}

		return null;
	}

	private Mbr mbr;

	private int index;

}
