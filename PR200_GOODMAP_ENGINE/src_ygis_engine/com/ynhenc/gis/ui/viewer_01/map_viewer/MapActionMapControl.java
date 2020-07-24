package com.ynhenc.gis.ui.viewer_01.map_viewer;

import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

public class MapActionMapControl extends MapAction {

	public MapActionMapControl(MapListener mapListener) {
		super(mapListener);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		MapListener listener = this.getMapListener();
		listener.drawPanArrrow(e);
	}

	@Override
	public void mouseReleased(MouseEvent now) {
		MapListener listener = this.getMapListener();
		MapViewer mapViewer = listener.getMapViewer();
		if (listener.lastPressME != null && mapViewer != null) {
			double dist = listener.getDistance(now, listener.lastPressME);

			if (dist < 5) {
				// 마우스 드래깅 거리가 짧을 때.....
				boolean ctrlDown = now.isControlDown();
				MapController controller = mapViewer.getMapController();

				if (!ctrlDown) {
					// 중심점 이동.
					Dimension size = mapViewer.getSize();
					int dx = -(now.getX() - size.width / 2);
					int dy = -(now.getY() - size.height / 2);
					controller.setPan(-dx, -dy);
					// 끝. 중심점 이동.
				}

				if (!ctrlDown) {
					mapViewer.repaint();
				} else if (SwingUtilities.isLeftMouseButton(now)) {
					// 왼쪽 마우스 클릭시 확대.
					controller.setZoomIn();
					mapViewer.repaint();
				} else if (SwingUtilities.isRightMouseButton(now)) {
					// 오른 쪽 마우스 클릭시 축소.
					controller.setZoomOut();
					mapViewer.repaint();
				}
			} else {
				// 마우스 드래깅 거리가 길 때는 패닝을 한다.
				listener.pan(now, listener.lastPressME);
				mapViewer.repaint();
			}
		}
	}

}
