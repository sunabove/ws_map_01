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
				// ���콺 �巡�� �Ÿ��� ª�� ��.....
				boolean ctrlDown = now.isControlDown();
				MapController controller = mapViewer.getMapController();

				if (!ctrlDown) {
					// �߽��� �̵�.
					Dimension size = mapViewer.getSize();
					int dx = -(now.getX() - size.width / 2);
					int dy = -(now.getY() - size.height / 2);
					controller.setPan(-dx, -dy);
					// ��. �߽��� �̵�.
				}

				if (!ctrlDown) {
					mapViewer.repaint();
				} else if (SwingUtilities.isLeftMouseButton(now)) {
					// ���� ���콺 Ŭ���� Ȯ��.
					controller.setZoomIn();
					mapViewer.repaint();
				} else if (SwingUtilities.isRightMouseButton(now)) {
					// ���� �� ���콺 Ŭ���� ���.
					controller.setZoomOut();
					mapViewer.repaint();
				}
			} else {
				// ���콺 �巡�� �Ÿ��� �� ���� �д��� �Ѵ�.
				listener.pan(now, listener.lastPressME);
				mapViewer.repaint();
			}
		}
	}

}
