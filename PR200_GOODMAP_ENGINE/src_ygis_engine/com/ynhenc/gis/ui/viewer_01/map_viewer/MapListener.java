package com.ynhenc.gis.ui.viewer_01.map_viewer;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.util.*;
import com.ynhenc.gis.*;
import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.web.*;

public class MapListener extends GisComLib implements MouseListener, MouseMotionListener, MouseWheelListener,
		ComponentListener, KeyListener {

	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		if (e.isShiftDown() && (c == 'p' || c == 'P')) {
			MapViewer mapViewer = this.getMapViewer();
			mapViewer.print();
		}
	}

	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		MapViewer mapViewer = this.getMapViewer();
		if (mapViewer != null) {
			mapViewer.whenComponentResized();
		}
	}

	public MapViewer getMapViewer() {
		return mapViewer;
	};

	public Mode getMode() {
		return this.mode;
	}

	public void setMode(int modeNo) {
		Mode mode = this.getMode(modeNo);
		this.debug("Mode = " + mode.ordinal());
		this.setMode(mode);
	}

	private Mode getMode(int modeNo) {
		Mode modeList[] = Mode.values();
		for (Mode mode : modeList) {
			if (mode.ordinal() == modeNo) {
				return mode;
			}
		}
		return null;
	}

	private boolean isMode(Mode mode) {
		return this.getMode() == mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
		MapViewer mapViewer = this.getMapViewer();
		if (mapViewer != null) {
			// 모드가 바뀌면 세션 맵에 있는 기존의 모든 점,선,면 데이터를 초기화 시킨다.
			Request req = mapViewer.getRequest();
			mapViewer.getGisProject().getMapData_Session( req ).removeAllLayerList();
			mapViewer.repaint();
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		this.setCursor();
	}

	public void mouseExited(MouseEvent e) {
		MapViewer mapViewer = this.getMapViewer();
		if (mapViewer != null) {
			CursorManager.setCursor(mapViewer, CursorManager.def);
		}
		this.lastMoveME = null;
	}

	public void mouseMoved(MouseEvent e) {
		int x = e.getX(), y = e.getY();
		GeoPoint geoPoint = this.getSpatialPoint(x, y);
		this.setStatus(geoPoint);
		if (true) {
			this.lastMoveME = e;
		}
	}

	private void setStatus(GeoPoint geoPoint) {
		MapViewer mapViewer = this.getMapViewer();
		if (mapViewer != null) {
			mapViewer.setStatus(geoPoint);
		}
	}

	public void mousePressed(MouseEvent e) {
		this.setCursor();
		int x = e.getX(), y = e.getY();
		GeoPoint geoPoint = this.getSpatialPoint(x, y);
		this.setStatus(geoPoint);
		this.lastPressME = e;
		this.lastMoveME = null;
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		int wheelRotation = e.getWheelRotation();
		MapViewer mapViewer = this.mapViewer;
		MapController controller = mapViewer.getMapController();
		if (wheelRotation > 0 ) {
			controller.setZoomIn();
			mapViewer.repaint();
			mapViewer.layerStyleEditor_01_Repaint();
		} else if (true) {
			controller.setZoomOut();
			mapViewer.repaint();
			mapViewer.layerStyleEditor_01_Repaint();
		}
	}

	public void mouseDragged(MouseEvent e) {
		this.getMapAction().mouseDragged(e);

		if (false) {
			this.drawSelectAreaRectangle(e);
		}

		this.lastDragME = e;
	}

	public void mouseReleased(MouseEvent now) {
		this.getMapAction().mouseReleased(now);

		this.lastPressME = this.lastDragME = null;
	}

	protected double getDistance(MouseEvent now, MouseEvent pre) {
		int dx = now.getX() - pre.getX();
		int dy = now.getY() - pre.getY();
		return Math.sqrt(dx * dx + dy * dy);
	}

	protected void pan(MouseEvent now, MouseEvent pre) {
		int dx = now.getX() - pre.getX();
		int dy = now.getY() - pre.getY();
		this.mapViewer.getMapController().setPan(-dx, -dy);

	}

	public void setCursor() {
		int type = CursorManager.cross;
		MapViewer mapViewer = this.getMapViewer();
		if (mapViewer != null) {
			CursorManager.setCursor(mapViewer, type);
		}
	}

	public void setSelectViewExtent() {
	}

	private void drawSelectAreaRectangle(MouseEvent e) {
		MapViewer mapViewer = this.getMapViewer();
		if (mapViewer != null) {
			Graphics2D g2 = (Graphics2D) mapViewer.getGraphics();
			g2.setXORMode(Color.yellow);
			// last x and y
			int lx = this.lastPressME.getX(), ly = this.lastPressME.getY();

			if (this.lastDragME != null) {
				// clear old selection rectangle
				int x = this.lastDragME.getX(), y = this.lastDragME.getY();
				int tx = (lx < x) ? lx : x;
				int ty = (ly < y) ? ly : y;
				g2.draw3DRect(tx, ty, Math.abs(x - lx), Math.abs(y - ly), true);
			} // end of clearance old rectangle

			if (e != null) {
				// draw new selection rectangle
				int x = e.getX(), y = e.getY();
				int tx = (lx < x) ? lx : x;
				int ty = (ly < y) ? ly : y;
				g2.draw3DRect(tx, ty, Math.abs(x - lx), Math.abs(y - ly), true);
			} // end of drawing new selection rectangle

			g2.setPaintMode();
		}

	}

	protected void drawPanArrrow(MouseEvent e) {
		MapViewer mapViewer = this.getMapViewer();
		if (mapViewer != null) {
			Graphics2D g2 = (Graphics2D) mapViewer.getGraphics();
			g2.setXORMode(Color.yellow);
			int lx = this.lastPressME.getX(), ly = this.lastPressME.getY();

			if (this.lastDragME != null) {
				// clear old selection rectangle
				int x = this.lastDragME.getX(), y = this.lastDragME.getY();
				g2.drawLine(lx, ly, x, y);
			} // end of clearance old rectangle

			if (e != null) {
				// draw new selection rectangle
				int x = e.getX(), y = e.getY();
				g2.drawLine(lx, ly, x, y);
			} // end of drawing new selection rectangle
			g2.setPaintMode();
		}

	}

	public GeoPoint getSpatialPoint(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		return this.mapViewer.getGisProject().getProjection().toSpatial(x, y);
	}

	public GeoPoint getSpatialPoint(int x, int y) {
		return this.mapViewer.getGisProject().getProjection().toSpatial(x, y);
	}

	public PntShort toGraphics(double x, double y) {
		return this.mapViewer.getGisProject().getProjection().toGraphics(x, y);
	}

	private MapAction getMapAction() {
		if( this.isMode(Mode.MAP_CONTROL) ) {
			return this.actionMapControl;
		} else if( this.isMode(Mode.MEASURE_DIST)) {
			return this.actionMeasureDist;
		} else if( this.isMode(Mode.MEASURE_AREA)) {
			return this.actionMeasureArea;
		}

		return this.actionMapControl;
	}

	public MapListener(MapViewer mapViewer) {
		this.mapViewer = mapViewer;
	}

	private final MapAction actionMapControl = new MapActionMapControl( this );
	private final MapAction actionMeasureDist = new MapActionMeasureDist( this );
	private final MapAction actionMeasureArea = new MapActionMeasureArea( this );

	private Mode mode = Mode.MAP_CONTROL;

	private MapViewer mapViewer;

	protected MouseEvent lastPressME; // last pressed mouse event
	protected MouseEvent lastDragME; // last dragged mouse event
	protected MouseEvent lastMoveME; // last moved mouse event

	// 리스너 모드
	public enum Mode {
		MAP_CONTROL, MEASURE_DIST, MEASURE_AREA, ROUTE_SEARCH,
	}

	// 끝. 리스너 모

}
