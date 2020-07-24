package com.ynhenc.kml;

import java.awt.Color;
import java.io.Writer;

import com.ynhenc.comm.util.*;

public abstract class KmlStyle extends KmlObject {

	public KmlStyleUrl getStyleUrl() {
		if( this.styleUrl == null ) {
			this.styleUrl = new KmlStyleUrl( this.getId() );
		}
		return this.styleUrl;
	}

	public int getId() {
		return id;
	}

	@Override
	public String getTag() {
		return "Style";
	}

	public KmlStyle( int id ) {
		super( null, null );
		this.id = id;
	}

	private int id;

	private transient KmlStyleUrl styleUrl;

}
