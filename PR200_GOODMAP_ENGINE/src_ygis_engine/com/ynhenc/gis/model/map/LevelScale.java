package com.ynhenc.gis.model.map;

import com.ynhenc.comm.GisComLib;

public class LevelScale extends GisComLib {

	public void setDist(double dist) {
		this.dist = dist;
	}

	public double getDist() {
		return this.dist;
	}

	public int getLevel() {
		return this.level;
	}

	public double getScale() {
		return 1.0/this.getDist() ;
	}

	public LevelScale(int level, double dist ) {
		super();
		this.level = level;
		this.dist = dist;
	}

	private int level;
	private double dist;

}
