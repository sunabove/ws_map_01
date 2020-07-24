package com.ynhenc.gis.ui.viewer_01.map_viewer;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.*;

import javax.swing.*;

import com.ynhenc.gis.model.map.*;

public class LevelScaleViewer extends JPanel {

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2 = (Graphics2D) g;

		this.paintLevelScaleList(g2);
	}

	private void paintLevelScaleList(Graphics2D g2) {

		MapViewer mapViewer = this.mapViewer;

		if (mapViewer != null) {
			GisProject gisProject = mapViewer.getGisProject();
			if (gisProject != null) {
				ZoomLevelList zoomLevelList = gisProject.getZoomLevelList();
				if (zoomLevelList != null) {

					Dimension size = this.getSize();

					int m = 2;

					Rectangle2D rect = new Rectangle2D.Double(m, m, size.width
							- 2 * m, size.height - 2 * m);

					this.paintZoomLevel(g2, zoomLevelList, rect);

				}
			}
		}
	}

	private void paintZoomLevel(Graphics2D g2, ZoomLevelList zoomLevelList,
			Rectangle2D rect) {

		g2.setColor(Color.white);

		g2.fill(rect);

		g2.setColor(Color.black);

		g2.draw(rect);

		double mx = rect.getWidth() / 10;
		double my = rect.getWidth() / 20;
		double x = rect.getX() + mx;
		double y = rect.getY() + my;
		double width = rect.getWidth() - mx - 6;
		double height = rect.getHeight() - 2 * my - 6;

		Rectangle2D chartArea = new Rectangle2D.Double(x, y, width, height);

		g2.setColor(Color.black);

		g2.draw(chartArea);

		final int zoomLevelMax = zoomLevelList.getZoomLevelMax();
		final int tickNumber = zoomLevelMax + 1;

		LevelScale levelScale;
		for (int i = 0, iLen = zoomLevelMax + 1; i < iLen; i++) {
			levelScale = zoomLevelList.getLevelScale(i);
			this.paintLevelScale(g2, levelScale, i, tickNumber, chartArea);
		}

	}

	private void paintLevelScale(final Graphics2D g2,
			final LevelScale levelScale, final int index, final int tickNumber,
			final Rectangle2D rect) {

		double x0 = rect.getX();
		double y0 = rect.getY();
		double w = rect.getWidth();
		double h = rect.getHeight();

		double x2 = x0 + w;
		double y2 = y0 + h;

		final double tickX = x0 + ((index + 1) * w / (tickNumber + 1));

		g2.setColor(Color.black);

		Line2D tickLineX = new Line2D.Double(tickX, y2 - 3, tickX, y2 + 3);

		g2.draw(tickLineX);

		if (true) {

			Font font = g2.getFont();
			FontRenderContext frc = g2.getFontRenderContext();
			String s = "" + index;
			Rectangle2D bounds = font.getStringBounds(s, frc);
			LineMetrics lm = font.getLineMetrics(s, frc);

			double tw = bounds.getWidth();
			double th = bounds.getHeight();

			double tx = tickLineX.getX1() - tw / 2.0;
			double ty = tickLineX.getY2() + th - lm.getDescent();

			g2.drawString(s, (float) tx, (float) ty);
		}

		final double tickY = y2 - (h * 0.9) * levelScale.getScale();

		Line2D tickLineY = new Line2D.Double(x0 - 3, tickY, x0 + 3, tickY);

		g2.setColor(Color.blue);
		g2.draw(tickLineY);

		if (true) {

			Font font = g2.getFont();
			FontRenderContext frc = g2.getFontRenderContext();
			String s = "" + levelScale.getScale();
			Rectangle2D bounds = font.getStringBounds(s, frc);
			LineMetrics lm = font.getLineMetrics(s, frc);

			double tw = bounds.getWidth();
			double th = bounds.getHeight();

			double tx = x0 + 4 ;
			double ty = tickLineY.getY2() + th - lm.getDescent();

			g2.drawString(s, (float) tx, (float) ty);
		}

		Rectangle2D diamond = new Rectangle2D.Double(tickX - 2, tickY - 2, 4, 4);

		g2.setColor(Color.red);

		g2.draw(diamond);

	}

	public void setMapViewer(MapViewer mapViewer) {
		this.mapViewer = mapViewer;
	}

	private MapViewer mapViewer;

}
