package com.ynhenc.gis.ui.viewer_01.map_viewer;

import java.awt.event.MouseEvent;

import com.ynhenc.gis.model.shape.GeoPoint;
import com.ynhenc.gis.web.Request;

public class MapActionShortestPath extends MapAction {

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent now) {
		MapListener listener = this.getMapListener();
		MapViewer mapViewer = listener.getMapViewer();
		if (mapViewer != null) {
			Request req = mapViewer.getRequest() ;
			GeoPoint geoPoint = listener.getSpatialPoint(now);
			mapViewer.getGisProject().getAdd_04_PoiMeasureArea( req, geoPoint);
			mapViewer.createMapImageAgain(); // debug 09-08-05
			mapViewer.repaint();
		}
	}

	public MapActionShortestPath(MapListener mapListener) {
		super(mapListener);
	}

}
