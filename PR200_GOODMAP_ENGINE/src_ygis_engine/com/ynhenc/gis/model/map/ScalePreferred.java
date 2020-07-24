package com.ynhenc.gis.model.map;

import java.util.Hashtable;

import com.ynhenc.comm.GisComLib;

public class ScalePreferred extends GisComLib {

	private Hashtable< Integer, Double> getDistList() {
		if( this.distList == null ) {
			this.distList = new Hashtable<Integer, Double> ();

			 double dist [] = {
					   1000 , // 0
					   3000 , // 1
					   5000 , // 2
					  10000 , // 3
					  20000 , // 4
					  25000 , // 5
					  50000 , // 6
					 100000 , // 7
					 200000 , // 8
					 400000 , // 9
					 800000 , // 10
					2000000 , // 11 ³²ÇÑ
					4000000 , // 12 ³²ºÏ
			};

			int index = 0;
			for( double d : dist ) {
				this.distList.put( index, d);
				index ++ ;
			}
		}
		return this.distList;
	}

	public double getDist( int zoomLevel ) {
		Hashtable< Integer, Double> distList = this.getDistList() ;
		if( zoomLevel < distList.size() ) {
			return distList.get( zoomLevel );
		} else {
			double scale = distList.get( distList.size() - 1 );
			return scale*Math.pow( 2, zoomLevel + 1 - distList.size() );
		}
	}

	public void setDist( int zoomLevel , double dist ) {
		Hashtable< Integer, Double> distList = this.getDistList() ;

		distList.put( zoomLevel, dist);
	}

	public ScalePreferred() {
	}

	private Hashtable< Integer, Double>  distList ;

}
