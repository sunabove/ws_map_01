package com.ynhenc.droute;

public class RotCost {

	public double getRotTimeSec() {
		return this.rotTimeSec;
	}

	private double calcRotTimeSec( double rotDeg ) {
		double rotRad = Math.PI/180.0*Math.abs( rotDeg );
		double rotTimeSec = 5*Math.sin( 2*rotRad );
		if( rotDeg < 0 ) {
			rotTimeSec += 20 ;
		}

		if( true ) {
			rotTimeSec = rotTimeSec*( this.getRoadRankTurnTimeFactor(fromRoadRank) + this.getRoadRankTurnTimeFactor(toRoadRank))/2.0 ;
		}
		return rotTimeSec ;
	}

	private double getRoadRankTurnTimeFactor( int roadRank ) {
		return roadRankFactor.getRoadRankTurnTimeFactor(roadRank);
	}

	public RotCost(int fromRoadRank, int toRoadRank, double rotDeg) {
		this.fromRoadRank = fromRoadRank;
		this.toRoadRank = toRoadRank;
		this.rotDeg = rotDeg;
		this.rotTimeSec = this.calcRotTimeSec( rotDeg );
	}

	private int fromRoadRank;
	private int toRoadRank;

	private double rotDeg;

	private double rotTimeSec ;

	private final static RoadRankFactor roadRankFactor = new RoadRankFactor();

}
