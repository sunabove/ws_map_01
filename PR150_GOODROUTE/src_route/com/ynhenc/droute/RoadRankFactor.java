package com.ynhenc.droute;

public class RoadRankFactor {

	public double getRoadRankTurnTimeFactor( int roadRank ) {
		/*
		1	고속국도
		2	도시고속국도
		3	일반국도
		4	특별광역시도
		5	국가지원지방도
		6	지방도	지방도
		7	시군도	시군도
		*/

		double [] factors = { 0.1, 0.15, 1, 1.1 , 1, 0.8, 1.1 };
		return factors[ roadRank - 1 ];
	}

}
