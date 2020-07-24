package com.ynhenc.droute;

import java.awt.*;

import com.ynhenc.droute.map.link.*;
import com.ynhenc.droute.render.*;
import com.ynhenc.kml.*;

public abstract class GeoObject extends ComLibRoute implements MbrInterface {

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	public RealTimeInfo getRealTimeInfo() {
		if( this.realTimeInfo == null ) {
			this.realTimeInfo = new RealTimeInfo();
		}
		return this.realTimeInfo;
	}

	public void setRealTimeInfo(RealTimeInfo realTimeInfo) {
		this.realTimeInfo = realTimeInfo;
	}

	public boolean isSameOrigin( GeoObject to ) {
		return this.getFrVert() == to.getFrVert() ;
	}

	public boolean isSameDest( GeoObject to ) {
		return this.getToVert() == to.getToVert();
	}

	public abstract double getLength() ;

	public abstract Vert getFrVert() ;

	public abstract Vert getToVert() ;

	public abstract double getSelfCost(SrchOption srchOption );

	public abstract double getCostTo(GeoObject b, SrchOption srchOption );

	public abstract void paint(RenderInfo render, StyleLink style , int index );

	public abstract boolean isIncluded(IncludeInfo inclInfo);

	public abstract double getPhysicalDist( Vert vert , Find.Mode findMode );

	public abstract KmlGeometry getKmlGeometry( GeoLinkCoordLevel coordLevel );

	public final KmlPlacemark getPlacemark( KmlStyle kmlStyle, GeoLinkCoordLevel coordLevel, int no ) {
		if( this.kmlPlacemark == null ) {
			this.kmlPlacemark = new KmlPlacemark( this.getKmlName() , kmlStyle, this.getKmlDesc( no ) ) ;
			this.kmlPlacemark.addComponent( this.getKmlGeometry( coordLevel ) );
		}
		return this.kmlPlacemark;
	}

	public final String getKmlName() {
		String name = "" + this.getObjectId();
		return name;
	}

	public final String getKmlDesc( int no ) {
		String desc = "link_id:" + this.getObjectId() + ", no:" + no ;
		return desc;
	}

	@Override
	public String toString() {
		return String.format( "class:%s, id:%d" , this.getClass().getSimpleName(), this.getObjectId() );
	}

	public long getObjectId() {
		return this.id;
	}

	public GeoObject(long id) {
		this.id = id;
		this.realTimeInfo = new RealTimeInfo();
		this.walkable = true;
	}

	private long id;
	private RealTimeInfo realTimeInfo ;
	private boolean walkable;

	private transient KmlPlacemark kmlPlacemark;

}
