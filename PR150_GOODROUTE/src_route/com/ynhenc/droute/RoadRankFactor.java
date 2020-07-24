package com.ynhenc.droute;

public class RoadRankFactor {

	public double getRoadRankTurnTimeFactor( int roadRank ) {
		/*
		1	��ӱ���
		2	���ð�ӱ���
		3	�Ϲݱ���
		4	Ư�������õ�
		5	�����������浵
		6	���浵	���浵
		7	�ñ���	�ñ���
		*/

		double [] factors = { 0.1, 0.15, 1, 1.1 , 1, 0.8, 1.1 };
		return factors[ roadRank - 1 ];
	}

}
