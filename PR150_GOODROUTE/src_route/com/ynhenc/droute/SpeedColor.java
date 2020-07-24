package com.ynhenc.droute;

import java.awt.Color;

public class SpeedColor {

	public static Color getSpeedColor( RealTimeInfo realTimeInfo ) {
		double spd_Kmph = realTimeInfo.getSpeed_Kmph();
		if( spd_Kmph < 11 ) {
			return colorList[0];
		} else if( spd_Kmph < 21 ) {
			return colorList[1];
		} else if( spd_Kmph < 31 ) {
			return colorList[2];
		} else if( spd_Kmph < 51 ) {
			return colorList[3];
		}
		return colorList[ 4 ];
	}

	private static Color [] colorList = {
		new Color( 255*1/5, 255*1/5, 255*1/5 ),
		new Color( 255*2/5, 255*2/5, 255*2/5 ),
		new Color( 255*3/5, 255*3/5, 255*3/5 ),
		new Color( 255*4/5, 255*4/5, 255*4/5 ),
		new Color( 255*5/5, 255*5/5, 255*5/5 ),
	};

	private static Color [] colorListOld = {
		new Color( 248, 23, 18 ),
		new Color( 186, 85, 211 ),
		new Color( 255, 242, 30 ),
		new Color( 22, 141, 6 ),
		new Color( 10, 62, 255 ),
};

}
