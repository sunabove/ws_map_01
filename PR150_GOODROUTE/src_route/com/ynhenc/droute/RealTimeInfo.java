package com.ynhenc.droute;

import java.awt.Color;

import com.ynhenc.comm.*;
import com.ynhenc.comm.db.*;

public class RealTimeInfo extends GisComLib {

	public void setDataFromQueryResult(QueryResult qr) {
		this.speed_Kmph = qr.getDouble("link_spd_kmph", speed_Kmph_Default );
		this.link_idx = qr.getInteger( "link_idx");
	}

	public double getSpeed_Kmph() {
		return speed_Kmph;
	}

	public void setSpeed_Kmph(int speed_Kmph) {
		this.speed_Kmph = speed_Kmph;
	}

	public static double getAvgSpped_Kmph() {
		return speed_Kmph_Default;
	}

	@Override
	public String toString() {
		return String.format( "link_idx(%d), speed_Kmph(%f)", link_idx, speed_Kmph );
	}

	private int link_idx;
	private double speed_Kmph = speed_Kmph_Default ;

	private static final double speed_Kmph_Default = 40;

}
