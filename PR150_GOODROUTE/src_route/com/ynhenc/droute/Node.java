package com.ynhenc.droute;

import java.awt.Dimension;
import java.lang.Comparable;
import java.lang.Float;
import java.util.Vector;

import com.ynhenc.droute.render.*;

public class Node extends PathElement {

	@Override
	public Mbr getMbr() {
		return this.getGeoObject().getMbr();
	}

	public final boolean isIncluded( IncludeInfo inclInfo ) {
		return this.getGeoObject().isIncluded( inclInfo );
	}

	@Override
	public final GeoObject getGeoObject() {
		return geoObject;
	}

	@Override
	public final double getCostTo( PathElement to , SrchOption srchOption) {
		return this.getGeoObject().getCostTo( to.getGeoObject() , srchOption );
	}

	@Override
	public void paintPathElement(RenderInfo render, StyleLink style, int index ) {
		this.getGeoObject().paint( render, style, index );
	}

	@Override
	public Node getIndexNode() {
		return this;
	}

	public RealTimeInfo getRealTimeInfo() {
		return this.getGeoObject().getRealTimeInfo();
	}

	@Override
	public String toString() {
		return this.getGeoObject().toString();
	}

	private Node( IndexId idxId , GeoObject location ) {
		super( idxId );
		this.geoObject = location;
	}

	public static Node getNode( IndexId  idxId , GeoObject location ) {
		return new Node( idxId , location );
	}

	private GeoObject geoObject;

}
