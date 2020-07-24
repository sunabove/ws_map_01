package com.ynhenc.gis.model.map;

import java.util.*;

import com.ynhenc.comm.GisComLib;

public class ZoomLevelList extends GisComLib {

	public double getScale(int zoomLevel) {
		return this.getLevelScale(zoomLevel).getScale();
	}

	public double getScaleRelative(int zoomLevel) {
		return this.getScale(zoomLevel) / this.getScale(this.getZoomLevelMax());
	}

	public ScalePreferred getScalePreferred() {
		if (this.scalePreferred == null) {
			this.scalePreferred = new ScalePreferred();
		}
		return this.scalePreferred;
	}

	public void setScalePreferred(ScalePreferred scalePreferred) {
		this.scalePreferred = scalePreferred;
	}

	public ArrayList<LevelScale> getLevelScaleList() {

		if (this.levelScaleList == null) {
			this.initLevelScaleList();
		}

		return this.levelScaleList;
	}

	public int getZoomLevelMax() {
		return this.zoomLevelMax;
	}

	public void setZoomLevelMax(int zoomLevelMax) {
		this.zoomLevelMax = zoomLevelMax;
		this.setZoomLevelCurr(this.zoomLevelCurr);
	}

	public int getZoomLevelCurr() {
		return this.zoomLevelCurr;
	}

	public void setZoomLevelCurr(int zoomLevelCurr) {
		int zoomLevelMax = this.zoomLevelMax;
		this.zoomLevelCurr = zoomLevelCurr < zoomLevelMax ? zoomLevelCurr : zoomLevelMax ;
	}

	public LevelScale getLevelScale(Integer zoomLevel) {

		ArrayList<LevelScale> levelScaleList = this.getLevelScaleList();

		if (levelScaleList != null) {

			LevelScale levelScale = null;

			if (zoomLevel < levelScaleList.size()) {
				levelScale = levelScaleList.get(zoomLevel);
			}

			if (levelScale == null) {
				levelScale = this.getPreferredLevelScale(zoomLevel);
				if (zoomLevel > levelScaleList.size() -1 ) {
					levelScaleList.add(zoomLevel, levelScale);
				} else {
					levelScaleList.set(zoomLevel, levelScale);
				}
			}

			return levelScale;

		} else {

			return null;

		}

	}

	private LevelScale getPreferredLevelScale(Integer zoomLevel) {
		ScalePreferred sp = this.getScalePreferred();
		LevelScale levelScale = new LevelScale(zoomLevel, sp.getDist(zoomLevel));
		return levelScale;
	}

	public void initLevelScaleList() {

		int zoomLevelMax = this.getZoomLevelMax();

		this.debug("zoomLevelMax = " + zoomLevelMax);

		this.levelScaleList = new ArrayList<LevelScale>(zoomLevelMax + 1);

		for (int i = 0, iLen = zoomLevelMax + 1; i < iLen; i++) {
			this.levelScaleList.add(null);
			this.getLevelScale(i);
		}

	}

	public ZoomLevelList() {
		this( ZOOM_LEVEL_MAX_INIT );
	}

	public ZoomLevelList(int zoomLevelMax) {
		this.zoomLevelMax = zoomLevelMax;
		this.zoomLevelCurr = zoomLevelMax;
		this.scalePreferred = new ScalePreferred();

		this.initLevelScaleList();
	}

	private int zoomLevelMax = 11;
	private int zoomLevelCurr = 0;
	private transient ScalePreferred scalePreferred;

	private ArrayList<LevelScale> levelScaleList;

	private static final int ZOOM_LEVEL_MAX_INIT = 11;

}
