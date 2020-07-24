package com.ynhenc.gis.ui.viewer_01.map_viewer;

import java.awt.event.MouseEvent;

import com.ynhenc.comm.GisComLib;

public abstract class MapAction extends GisComLib {

	public MapListener getMapListener() {
		return mapListener;
	}

	public abstract void mouseDragged(MouseEvent e) ;

	public abstract void mouseReleased(MouseEvent now) ;

	public MapAction(MapListener mapListener) {
		super();
		this.mapListener = mapListener;
	}

	private MapListener mapListener ;

}
