package com.ynhenc.droute;

import java.util.Hashtable;

public class RoadRank {

	public int getRoadRank() {
		return roadRank;
	}

	private RoadRank(int roadRank) {
		super();
		this.roadRank = roadRank;
	}

	public static RoadRank getRoadRank( int code ) {
		RoadRank roadRank = roadRankList.get( code );

		if( roadRank == null) {
			roadRank = new RoadRank( code );
			roadRankList.put( code, roadRank);
		}

		return roadRank;
	}

	private int roadRank;

	private final static Hashtable< Integer, RoadRank > roadRankList = new Hashtable< Integer, RoadRank >();

}
