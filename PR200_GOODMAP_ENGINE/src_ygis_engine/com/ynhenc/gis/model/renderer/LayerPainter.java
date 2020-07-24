package com.ynhenc.gis.model.renderer;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.*;

import com.ynhenc.comm.util.*;
import com.ynhenc.gis.*;
import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.model.mapobj.*;
import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.model.style.*;
import com.ynhenc.gis.ui.resource.*;
import com.ynhenc.gis.web.*;

public class LayerPainter extends Painter {

	public void paint_01_GisProject(Graphics2D g2, GisProject gisProject, int zoomLevelCurr, Projection projection, Request req) {
		GisProjectOption option = gisProject.getGisProjectOption();
		RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		if (option != null && option.isGraphics_HighQuality()) {
			renderingHints = GraphicsUtil.getRenderingHints_High();
			// this.debug("USE RENDERING HINTS IS ON.");

		} else {
			renderingHints = GraphicsUtil.getRenderingHints_Normal();
			// this.debug("USE RENDERING HINTS IS OFF.");
		}

		if (true) {
			// 베이스 맵 페이팅.
			MapDataObject mapData = gisProject.getMapData_Base();
			if( true ) {
				LayerList allLayerList = mapData.getLayerList();
				for( Layer layer : allLayerList ) {
					layer.setShowingNow( false );
				}
			}
			LayerList layerList = mapData.getLayerListAtZoomLevel(zoomLevelCurr, projection);
			layerList.sortLayers();
			boolean displayShapeAndIconLabelTogether = option.isDisplayShapeAndIconLabelTogether();
			this.paint_02_LayerList(g2, projection, layerList, renderingHints, option, displayShapeAndIconLabelTogether );

		} // 끝. 베이스 맵 페인팅.

		if (true) {
			// 세션 맵 페인팅.
			MapDataObject mapData = gisProject.getMapData_Session(req);
			//LayerList layerList = mapData.getLayerList();
			LayerList layerList = mapData.getLayerListAtZoomLevel(zoomLevelCurr, projection);
			layerList.sortLayers();
			boolean displayShapeAndIconLabelTogether = option.isDisplayShapeAndIconLabelTogether();
			displayShapeAndIconLabelTogether = true;
			this.paint_02_LayerList(g2, projection, layerList, renderingHints, option , displayShapeAndIconLabelTogether );
		} // 끝. 세션 맵 페인팅.

	}

	public void paint_03_Option(Graphics2D g2, GisProject gisProject, Projection projection, int zoomLevelCurr, long then) {

		GisProjectOption option = gisProject.getGisProjectOption();

		if (option != null) {
			// 옵션 페인팅
			if (option.isRenderingTime_Show()) {
				// 렌더링 시간 표시.
				this.paint_07_RenderingTime(g2, projection, zoomLevelCurr, then);
			}

			if (option.isGlobalMbr_Show()) {
				// 전체 경계선 표시.
				MapDataObject mapDataBase = gisProject.getMapData_Base();
				this.paint_05_MbrBox(g2, projection, mapDataBase.getMbrTopLevel());
			}

			if (option.isCrossLine_Show()) {
				// 중앙십자선 표시.
				this.paint_06_CrossLine(g2, projection);
			}

			if (option.isLogo_Show()) { // 로고 표시.
				this.paint_09_Logo(g2);
			}
		} // 끝. 옵션 페인팅.

	}

	public void paint_07_RenderingTime(Graphics2D g2, Projection projection, int zoomLevelCurr, long then) {

		Dimension pixelSize = projection.getPixelSize();

		String labelText = "" + (zoomLevelCurr + 1) + " : " + ((System.currentTimeMillis() - then) / 1000.0);

		int x = 10;
		int y = pixelSize.height - 20;

		Font font = g2.getFont();

		FontRenderContext frc = g2.getFontRenderContext();
		Rectangle2D bounds = font.getStringBounds(labelText, frc);

		if (true) {
			x = (int) (pixelSize.getWidth() - bounds.getWidth() - 10);
		}

		bounds = new Rectangle2D.Double(x - 2, y - bounds.getHeight() + 2, bounds.getWidth() + 4, bounds.getHeight() + 4);

		g2.setRenderingHints(GraphicsUtil.getRenderingHints_Normal());

		g2.setColor(Color.white);
		g2.fill(bounds);
		g2.setColor(Color.black);
		g2.draw(bounds);
		g2.drawString(labelText, x, y);

	}

	public void paint_02_LayerList(Graphics2D g2, Projection projection, LayerList layerList, RenderingHints renderingHints,
			GisProjectOption option , boolean displayShapeAndIconLabelTogether ) {

		boolean localDebug = false;

		int layerSize = layerList.getSize();

		Layer layer;
		ShapeObjectList[] shapeObjListIntersectsList = new ShapeObjectList[layerSize];
		for (int i = 0, iLen = layerList.size(); i < iLen; i++) {
			layer = layerList.get(i); // get layer in reversed order
			shapeObjListIntersectsList[i] = layer.getShapeListIntersects(projection);
			layer.setShowingNow( true );
		}

		int layerIndex;
		boolean paintAsc = true ;
		if (displayShapeAndIconLabelTogether) {
			int paintMode = 3;
			for (int i = 0, iLen = layerList.size(); i < iLen; i++) {
				// get layer in reversed order
				layerIndex = paintAsc ? i : iLen - i - 1;
				layer = layerList.get(layerIndex);
				this.debug("PAINT LAYER NAME = " + layer.getName(), localDebug);
				layer.paintShapeList(g2, projection, shapeObjListIntersectsList[layerIndex], renderingHints, paintMode);
			}
		} else {
			int paintMode = 2;
			for (int i = 0, iLen = layerList.size(); i < iLen; i++) {
				// get layer in reversed order
				layerIndex = paintAsc ? i : iLen - i - 1;
				layer = layerList.get(layerIndex);
				this.debug("PAINT LAYER NAME = " + layer.getName(), localDebug);
				layer.paintShapeList(g2, projection, shapeObjListIntersectsList[layerIndex], renderingHints, paintMode);
			}
			paintMode = 1;
			for (int i = 0, iLen = layerList.size(); i < iLen; i++) {
				// get layer in reversed order
				layerIndex = paintAsc ? i : iLen - i - 1;
				layer = layerList.get(layerIndex);
				this.debug("PAINT LAYER NAME = " + layer.getName(), localDebug);
				layer.paintShapeList(g2, projection, shapeObjListIntersectsList[layerIndex], renderingHints, paintMode);
			}
		}

	}

	public void paint_09_Logo(Graphics2D g2) {

		IconImage logoImage = this.logoImage;

		if (logoImage != null) {
			Component observer = null;
			g2.setPaintMode();
			BufferedImage bImage = logoImage.getBfrImage();
			if (bImage != null) {
				g2.drawImage(bImage, 10, 10, observer);
			}
		}

	}

	public void paint_06_CrossLine(Graphics2D g2, Projection projection) {

		int w = (int) projection.getPixelWidth();
		int h = (int) projection.getPixelHeight();

		int x = w / 2;
		int y = h / 2;

		int gap = 2;

		g2.setColor(Color.red);

		g2.drawLine(0, y, x - gap, y);
		g2.drawLine(x + gap, y, w, y);

		g2.drawLine(x, 0, x, y - gap);
		g2.drawLine(x, y + gap, x, h);

	}

	public void paint_05_MbrBox(Graphics2D g2, Projection projection, Mbr mbr) {

		if (mbr != null) {
			// top left graphical coordinate
			PntShort topLeftGraphical = projection.toGraphics(mbr.getMinX(), mbr.getMaxY());
			// bottom right graphical coordiante
			PntShort bottomRightGraphical = projection.toGraphics(mbr.getMaxX(), mbr.getMinY());

			Color c = g2.getColor(); // old color
			g2.setColor(Color.blue);

			Rectangle2D rect = new Rectangle2D.Double(topLeftGraphical.getX(), topLeftGraphical.getY(), bottomRightGraphical
					.getX()
					- topLeftGraphical.getX() - 1, bottomRightGraphical.getY() - topLeftGraphical.getY() - 1);

			g2.draw(rect);
			g2.setColor(c); // restore old color
		}
	}

	private LayerPainter() {
	}

	private static IconImage_Resource logoImage = IconImageFactory.getIconImage_Resource("gen", "logo.png");

	public static LayerPainter newLayerPainter() {
		return new LayerPainter();
	}

}
