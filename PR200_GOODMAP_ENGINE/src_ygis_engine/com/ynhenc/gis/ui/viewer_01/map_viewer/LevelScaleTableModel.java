package com.ynhenc.gis.ui.viewer_01.map_viewer;

import javax.swing.table.*;

import com.ynhenc.comm.DebugInterface;
import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.model.map.*;

public class LevelScaleTableModel extends AbstractTableModel implements DebugInterface {

	public GisProject getGisProject() {
		return this.gisProject;
	}

	public void setGisProject(GisProject gisProject) {
		this.gisProject = gisProject;
	}

	public int getColumnCount() {
		return this.colNameList.length;
	}

	public int getRowCount() {
		return this.getZoomLevelList().getZoomLevelMax() + 1;
	}

	@Override
	public String getColumnName(int col) {
		return this.colNameList[col];
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return col == 1;
	}

	private LevelScale getLevelScale(int zoomLevel) {
		ZoomLevelList zoomLevelList = this.getZoomLevelList();
		if (zoomLevelList != null) {
			LevelScale levelScale = zoomLevelList.getLevelScale(zoomLevel);
			return levelScale;
		} else {
			return null;
		}
	}

	public Object getValueAt(int row, int col) {
		int zoomLevel = row;
		Number val ;
		if (col == 0) {
			val = zoomLevel;
		} else {
			LevelScale levelScale = this.getLevelScale(zoomLevel);
			if (levelScale != null) {
				val =  levelScale.getDist();
			} else {
				val = this.getScalePreferred().getDist(zoomLevel);
			}
		}

		if( true ) {
			return val;
		} else {
			return GisComLib.getFormat( val );
		}
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		double dist = -1;

		try {
			String valText = "" + value;
			valText = valText.replaceAll( ",", "" ).trim();
			dist = Double.parseDouble( valText );
		} catch (NumberFormatException e) {
			this.debug.println(e);
		}

		if (col == 1) {
			if (dist > 0) {
				int zoomLevel = row;
				LevelScale levelScale = this.getLevelScale(zoomLevel);
				if (levelScale != null) {
					levelScale.setDist(dist);
				}

				if (false) {
					ScalePreferred sp = this.getScalePreferred();
					sp.setDist(zoomLevel, dist);
					this.getZoomLevelList().initLevelScaleList();
				}
			}
		}
	}

	@Override
	public Class getColumnClass(int column) {
		Object obj = this.getValueAt(0, column);
		if (obj != null) {
			return obj.getClass();
		} else {
			return null;
		}
	}

	private int getZoomLevelMax() {
		return this.getZoomLevelList().getZoomLevelMax();
	}

	private ScalePreferred getScalePreferred() {
		return this.getZoomLevelList().getScalePreferred();
	}

	private ZoomLevelList getZoomLevelList() {
		return this.gisProject.getZoomLevelList();
	}

	public LevelScaleTableModel(GisProject gisProject) {
		super();
		this.gisProject = gisProject;
	}

	private String[] colNameList = { "∑π∫ß", "√‡√¥" };

	private GisProject gisProject;

}
