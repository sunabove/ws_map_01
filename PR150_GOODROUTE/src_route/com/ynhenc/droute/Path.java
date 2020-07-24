package com.ynhenc.droute;

import java.util.*;

import com.ynhenc.comm.*;
import com.ynhenc.droute.render.*;
import com.ynhenc.kml.*;

public class Path extends ArrayListEx< Node >  {

	public Style getStyle() {
		return style;
	}

	public void setStyle(Style style) {
		this.style = style;
	}

	public SrchOption getSrchOption() {
		return srchOption;
	}

	public void setSrchOption(SrchOption srchOption) {
		this.srchOption = srchOption;
	}

	public int getQueryNo() {
		return queryNo;
	}

	public void setQueryNo(int queryNo) {
		this.queryNo = queryNo;
	}

	public double getQueryTime_Sec() {
		return queryTime_Sec;
	}

	public void setQueryTime_Sec(double queryTime) {
		this.queryTime_Sec = queryTime;
	}

	public double getLength() {
		double dist = 0;

		for( Node node : this.getIterable() ) {
			dist += node.getGeoObject().getLength();
		}

		return dist;
	}

	@Override
	public String toString() {
		return this.getKmlDocumentName() + "," + this.getKmlDocumentDesc();
	}

	public final String getKmlDocumentName() {
		Path path = this;
		String name = "srchType:" + this.getSrchOption().getSrchType() + ", queryNo:" + path.getQueryNo() ;
		name += ", heuristic:" + this.getSrchOption().getHeuristicFactor();
		return name;
	}

	public final String getKmlDocumentDesc() {
		Path path = this;
		String desc = "queryTime:" + path.getQueryTime_Sec();
		desc += ", ver:" + ComLibRoute.getAppName();
		desc += ", length:" + this.getLength();
		return desc;
	}

	public final String getKmlFolderName() {
		return "path";
	}
	public final String getKmlFolderDesc() {
		return "path desc";
	}

	public KmlPlacemarkIterator getPlacemarkIterable( final KmlLineStyle kmlLineStyle , final Mode.GeoLinkCoordLevel coordLevel ) {

		final Iterator<Node> pathElemIt = this.iterator();

		KmlPlacemarkIterator it = new KmlPlacemarkIterator() {

			@Override
			public boolean hasNext() {
				return pathElemIt.hasNext();
			}

			@Override
			public KmlPlacemark next() {
				PathElement pathElement = pathElemIt.next();
				GeoObject geo = pathElement.getGeoObject();
				KmlPlacemark pm = geo.getPlacemark( kmlLineStyle, coordLevel, no );
				no ++ ;
				return pm ;
			}

			@Override
			public void remove() {
				pathElemIt.remove();
			}

			private int no  = 0;

		} ;

		return it ;
	}

	public Path( SrchOption srchOption ) {
		this( srchOption, null );
	}

	public Path( SrchOption srchOption, Style style ) {
		this.setSrchOption(srchOption);
		this.setStyle( style );
	}

	private double queryTime_Sec;
	private int queryNo;
	private SrchOption srchOption;
	private Style style;

}
