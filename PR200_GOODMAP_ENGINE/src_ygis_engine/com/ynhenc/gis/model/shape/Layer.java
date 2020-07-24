package com.ynhenc.gis.model.shape;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;
import java.io.*;
import java.net.*;

import javax.swing.table.AbstractTableModel;

import com.ynhenc.comm.util.GraphicsUtil;
import com.ynhenc.gis.model.map.Code;
import com.ynhenc.gis.model.mapobj.*;
import com.ynhenc.gis.model.style.*;
import com.ynhenc.gis.ui.resource.IconImage;
import com.ynhenc.gis.ui.viewer_03.dbf_viewer.*;
import com.ynhenc.gis.web.Request;

public class Layer extends MapObject implements Comparable<Layer> {

	public boolean isShowingNow() {
		return showingNow;
	}

	public void setShowingNow(boolean showingNow) {
		this.showingNow = showingNow;
	}

	public final Integer getLayerNo() {
		return this.getIndex();
	}

	public final void setLayerNo(int layerNo) {
		this.setIndex(layerNo);
	}

	public boolean isMainDataLoaded() {
		return mainDataLoaded > 0;
	}

	public void setMainDataLoaded(boolean mainDataLoaded) {
		this.mainDataLoaded = mainDataLoaded ? 1 : 0;
	}

	public boolean isLayerEnabled() {
		return this.layerEnabled > 0;
	}

	public void setLayerEnabled(boolean layerEnabled) {
		this.layerEnabled = layerEnabled ? 1 : 0;
	}

	public void setDispLevel( MinMaxInt minMaxInt ) {
		this.setDispLevel( minMaxInt.getMin(), minMaxInt.getMax() );
	}

	private void setDispLevel( int min, int max ) {
		this.setDispLevelMin(min);
		this.setDispLevelMax(max);
	}

	public int getDispLevelMin() {
		return dispLevelMin;
	}

	public void setDispLevelMin(int dispLevelMin) {
		this.dispLevelMin = dispLevelMin;
	}

	public int getDispLevelMax() {
		return dispLevelMax;
	}

	public void setDispLevelMax(int dispLevelMax) {
		this.dispLevelMax = dispLevelMax;
	}

	public int getLableColumnIndex() {
		MetaDataLabel metaDataLabel = this.metaDataLabel;
		if (metaDataLabel != null) {
			return metaDataLabel.getColumnIndex();
		}
		return -1;
	}

	public MetaDataLabel getMetaDataLabel() {
		if (this.metaDataLabel == null) {
			this.metaDataLabel = new MetaDataLabel();
		}
		return this.metaDataLabel;
	}

	public URL getUrl() {
		URL urlParent = this.getUrlParent();

		String name = this.getName();
		if (!name.toUpperCase().endsWith(".SHP")) {
			// 확장자가 SHP으로 끝나지 않으면 SHP를 붙여준다.
			name += ".shp";
		}

		URL url = null;

		try {
			url = new URL("" + urlParent + "/" + name);
		} catch (MalformedURLException e) {
			this.debug.println(this, e);
		}
		return url;
	}

	public String[] getColNameList() {
		TableModelDbf tableModel = this.getTableModel();

		if (tableModel != null) {
			return tableModel.getColNameList();
		} else {
			return new String[] {};
		}

	}

	public TableModelDbf getTableModel() {
		if (this.tableModel == null) {
			File fileDbf = this.getFileShape_Dbf();

			if (fileDbf != null && fileDbf.exists()) {
				try {
					this.tableModel = new TableModelDbf(fileDbf);
				} catch (Exception e) {
					this.debug.println(this, e);
				}
			} else {
				this.debug.println(this, "FILE _DBF(" + fileDbf + ") is wrong!");
			}
		}

		return this.tableModel;
	}

	private File getFileTailored(String filePathShp, String tailNew) {

		boolean localDebug = false;

		final String head = "file:/";
		final String tailRef = ".shp";

		if (filePathShp.toUpperCase().startsWith(head.toUpperCase())) {
			filePathShp = filePathShp.substring(5);
		}

		if (filePathShp.toUpperCase().endsWith(tailRef.toUpperCase())) {

			String filePathNew = filePathShp.substring(0, filePathShp.length() - tailRef.length());
			filePathNew = filePathNew + tailNew;

			File fileNew = new File(filePathNew);

			if (localDebug) {
				this.debug("filePath = " + filePathNew);
			}

			if (!fileNew.exists()) {

				this.debug("Finding a file again = " + filePathNew);

				String fileNameNew = filePathNew.substring(filePathNew.lastIndexOf("/")).toUpperCase();

				File parent = fileNew.getParentFile();
				File fileList[] = parent.listFiles();
				String fileNameCompare;
				for (File fileCompare : fileList) {
					try {
						fileNameCompare = fileCompare.getCanonicalPath();
					} catch (IOException e) {
						this.debug(e);
						fileNameCompare = "/" + fileCompare.getName();
					}
					if (fileNameCompare.lastIndexOf("/") > -1) {
						fileNameCompare = fileNameCompare.substring(fileNameCompare.lastIndexOf("/")).toUpperCase();
					}
					if (fileNameCompare.equalsIgnoreCase(fileNameNew)) {
						return fileCompare;
					}
				}
			}

			if (fileNew.exists()) {
				return fileNew;
			} else {
				this.debug("Cannot find a file : " + filePathNew);
				return fileNew;
			}
		}

		return null;
	}

	private File getFileTailored_Old(String urlText, String tailDbf) {

		boolean localDebug = false;

		final String head = "file:/";
		final String tailShp = ".shp";

		if (urlText.toUpperCase().startsWith(head.toUpperCase())) {

			String filePath = urlText.substring(5);

			if (filePath.toUpperCase().endsWith(tailShp.toUpperCase())) {

				filePath = filePath.substring(0, filePath.length() - tailShp.length());
				filePath = filePath + tailDbf;

				File file = new File(filePath);

				if (localDebug) {
					this.debug.println(this, "filePath = " + filePath);
				}

				if (file.exists()) {
					return file;
				} else {
					return null;
				}
			}
		}

		return null;
	}

	public File getFileShape_Shp() {
		String filePath = "" + this.getUrl();
		return this.getFileTailored(filePath, ".shp");
	}

	public File getFileShape_Idx() {
		String filePath = "" + this.getUrl();
		return this.getFileTailored(filePath, ".shx");
	}

	public File getFileShape_Dbf() {
		String filePath = "" + this.getUrl();
		return this.getFileTailored(filePath, ".dbf");

	}

	public Style_01_Text getTextStyle() {
		return this.textStyle;
	}

	public void setTextStyle(Style_01_Text textStyle) {
		this.textStyle = textStyle;
	}

	public LayerType getLyrType() {
		return this.lyrType;
	}

	public void addSpatialShapeList(ArrayList<ShapeObject> shapeList) {

		for (ShapeObject shape : shapeList) {
			this.addSpatialShape(shape);
		}

	}

	private void addSpatialShapeWithMbrCheck(ShapeObject shape) {

		this.shapeObjectList.add(shape);

		Mbr mbr = this.getMbr();

		if (!Mbr.isIntersects(mbr, shape.getMbr())) {
			this.setMbr(Mbr.getMbrIntersected(mbr, shape.getMbr()));
		}

	}

	public void addSpatialShape(ShapeObject shape) {

		this.shapeObjectList.add(shape);

	}

	public void removeSpatialShape(int shapeId) {
		Iterator<ShapeObject> it = this.getShapeList().iterator();
		ShapeObject shape;
		while (it.hasNext()) {
			shape = it.next();
			if (shape != null && shape.getId() == shapeId) {
				it.remove();
			}
		}
	}

	private void removeSpatialShape(ShapeObject shape) {
		this.shapeObjectList.remove(shape);
	}

	public void removeAllShapes() {
		this.shapeObjectList = new ShapeObjectList();

		this.setMbr(null);
	}

	public Style_05_General getShapeStyle() {
		return this.shapeStyle;
	}

	public void setShapeStyle(Style_05_General style) {
		this.shapeStyle = style;
	}

	private void paintLayer(Graphics2D g2, Projection projection, RenderingHints renderingHints) {
		this.paintShapeList(g2, projection, this.getShapeListIntersects(projection), renderingHints, 3);
	}

	public ShapeObjectList getShapeListIntersects(Projection projection) {

		ShapeObjectList shapeListIntersects = new ShapeObjectList();

		ShapeObjectList shapeObjectList = this.getShapeList();

		Mbr mbrScreenProj = projection.getMbrScreen();

		int skipNo = 0, drawNo = 0;
		for (ShapeObject shape : shapeObjectList) {
			// TODO 2007.07.31.002 도형 교차 최적화 필요.
			if (shape.isIntersects(mbrScreenProj)) { // 도형이 교차 할 때....
				shapeListIntersects.add(shape);
				drawNo++;
			} else { // 건너 뛰기.
				skipNo++;
			}
		}

		// TODO 2008.08.27.001 레이어 페인팅 객체 갯수 디버

		boolean localDebug = false;

		if (localDebug) {
			this.debug.println(this, "SIZE = " + shapeObjectList.size() + ", DRAW NO = " + drawNo + ", SKIP NO = " + skipNo);
		}

		return shapeListIntersects;
	}

	public void paintShapeList(Graphics2D g2, Projection projection, ShapeObjectList shapeObjectList, RenderingHints renderingHints,
			int paintMode) {
		// 1. 도형을 먼저 그린다.
		// 2. 텍스트나 아이콘을 그린다.

		if (false) {
			String format = "layer(%s), paintMode(%d)";
			this.debug(String.format(format, this.getName(), paintMode), true);
		}

		if ((paintMode & 2) == 2) {
			Style_05_General shapeStyle = this.getShapeStyle();

			if (shapeStyle != null) {
				// 도형 드로잉시에는 힌트를 적용한다.
				g2.setRenderingHints(renderingHints);
				this.paintShapeListByStyle(g2, projection, shapeStyle, shapeObjectList);
			}
		}

		if ((paintMode & 1) == 1) {
			Style_01_Text textStyle = this.getTextStyle();
			if (textStyle != null) {

				MetaDataLabel metaDataLabel = textStyle.getMetaDataLabel();
				if (metaDataLabel == null) {
					metaDataLabel = this.getMetaDataLabel();
					int columnIndex = metaDataLabel.getColumnIndex();
					if (columnIndex > -1) {
						TableModelDbf tableModel = this.getTableModel();
						textStyle.setMetaDataLabel(metaDataLabel);
						textStyle.setTableModel(tableModel);
					}
				}

				IconImage iconImage = textStyle.getIconImage();

				if (iconImage != null || metaDataLabel != null || (this instanceof LayerPoi)) {
					// 아이콘 이미지가 있거나 , 레이블 칼럼이 있거나, POI 레이어 인 경우에만.... 텍스트스타일로
					// 그린다.
					// 텍스트 드로잉시에는 힌트를 적용하지 않는다.
					g2.setRenderingHints(GraphicsUtil.getRenderingHints_Normal());
					this.paintShapeListByStyle(g2, projection, textStyle, shapeObjectList);
				}
			}
		}

	}

	public void paintShapeListByStyle(Graphics2D g2, Projection projection, Style_00_Object style, ShapeObjectList shapeObjectList) {

		Stroke orgStroke = g2.getStroke();
		Paint orgPaint = g2.getPaint();
		Font orgFont = g2.getFont();
		Color orgColor = g2.getColor();

		LayerType layerType = this.getLyrType();

		if (LayerType.LINE != layerType) { // 레이어타입이 선 레이어타입이 아니면, 채워 넣기를 먼저함.
			if (style.setGraphics_01_InsideFill(g2, projection)) {
				// 그래픽 설정할 것이 있으면 채워 넣기를 한다.
				for (ShapeObject shape : shapeObjectList) {
					if (shape instanceof ShapeAreaHole) {
						this.debug("Hole Shape FID:" + shape.getFid());
					}
					style.paintShape_01_InsideFill(g2, shape, projection, shapeObjectList.getShapeObjHash());
				}
			}
		} // 끝. 채워 넣기.

		if (true) { // 외곽선 그리기. 레이어 타입에 상관없이 그림. 라인의 경우....반드시 그려져야 함.
			if (style.setGraphics_02_OutsideDraw(g2, projection)) {
				// 그래픽 설정할 것이 있으면 외곽선 그리기를 한다.
				for (ShapeObject shape : shapeObjectList) {
					style.paintShape_02_OutsideDraw(g2, shape, projection, shapeObjectList.getShapeObjHash());
				}
			}

		} // 끝. 외곽선 그리기.

		g2.setStroke(orgStroke);
		g2.setPaint(orgPaint);
		g2.setFont(orgFont);
		g2.setColor(orgColor);

	}

	public Layer getShapesIncludes(int x, int y, Projection proj) {

		Layer layer = new Layer(this.lyrType, this.getName() + " selected");
		ArrayList<ShapeObject> shapeList = this.shapeObjectList;
		ShapeObject shape;
		java.awt.Shape shp;
		for (int i = 0, len = shapeList.size(); i < len; i++) {
			shape = shapeList.get(i);
			shp = shape.getShape(proj);
			if (shp.contains(x, y)) {
				layer.addSpatialShape(shape);
			}
		}
		return layer;
	}

	public Layer getLayerSearchByCircle(GeoPoint center, Double radius) {

		Layer layer = new Layer(this.lyrType, this.getName() + " search by circle");
		ArrayList<ShapeObject> shapeList = this.shapeObjectList;
		ShapeObject shape;
		for (int i = 0, len = shapeList.size(); i < len; i++) {
			shape = shapeList.get(i);
			if (shape.isContainedByCircle(center, radius)) {
				layer.addSpatialShape(shape);
			}
		}
		return layer;
	}

	public int compareTo(Layer layer) {
		if (layer != null) {
			return  ( this.getLayerNo().compareTo( layer.getLayerNo() ) ) ;
		} else {
			return Integer.MAX_VALUE;
		}

	}

	public int compareTo_Old(Layer layer) {
		if (layer != null) {
			String aCode = this.getName();
			String bCode = layer.getName();
			if (aCode != null && bCode != null) {
				return aCode.compareTo(bCode);
			} else {
				return -Integer.MAX_VALUE;
			}
		} else {
			return -Integer.MAX_VALUE;
		}

	}

	@Override
	protected final Mbr calcMbr() {

		Mbr mbr = null;
		ArrayList<ShapeObject> shapeList = this.shapeObjectList;

		for (ShapeObject shape : shapeList) {
			mbr = Mbr.union(mbr, shape.getMbr());
		}
		return mbr;

	}

	public int getSize() {
		return this.shapeObjectList.size();
	}

	public ShapeObject getShape(int shapeId) {
		ArrayList<ShapeObject> shapeList = this.getShapeList();

		for (ShapeObject shape : shapeList) {
			if (shapeId == shape.getId()) {
				return shape;
			}
		}
		return null;
	}

	public ShapeObjectList getShapeList() {
		return this.shapeObjectList;
	}

	public void setShapeList(ShapeObjectList shapeObjectList) {
		this.shapeObjectList = shapeObjectList;
	}

	public void setLyrType(LayerType lyrType) {
		this.lyrType = lyrType;
	}

	public boolean isSelectedAtZoomLevel(int zoomLevel) {

		 boolean isBetweenMinMaxLevel =  this.getDispLevelMin() <= zoomLevel && ( this.getDispLevelMax() < 0 || zoomLevel <= this.getDispLevelMax() );

		if (isBetweenMinMaxLevel) {
			return true;
		} else {
			return this.isSelectedAtZoomLevel_Old(zoomLevel);
		}

	}

	public boolean isSelectedAtZoomLevel_Old(int zoomLevel) {

		ArrayList<Boolean> zoomLevelList = this.zoomLevelList;

		if (zoomLevelList.size() < 1 || zoomLevel < 0) {
			// 기본적으로는 모든 레이어에 안 선택되어 지도록 설정한다.
			return false;
		} else if (zoomLevel < zoomLevelList.size()) {
			Boolean selected = zoomLevelList.get(zoomLevel);
			return (selected != null ? selected.booleanValue() : true);
		} else {
			return false;
		}

	}

	public void setSelectedAtZoomLevel(int zoomLevel, boolean selected) {
		this.setSelectedAtZoomLevel_Old(zoomLevel, selected);
	}

	public void setSelectedAtZoomLevel_Old(int zoomLevel, boolean selected) {

		ArrayList<Boolean> zoomLevelList = this.zoomLevelList;

		if (zoomLevelList.size() <= zoomLevel) {
			for (int i = zoomLevelList.size(), iLen = zoomLevel + 1; i < iLen; i++) {
				zoomLevelList.add(new Boolean(false));
			}
		}

		this.zoomLevelList.set(zoomLevel, selected);

	}

	public URL getUrlParent() {
		return this.urlParent;
	}

	public void setUrlParent(URL urlParent) {
		this.urlParent = urlParent;
	}

	public void initLayerOpen() {
		this.shapeObjectList = new ShapeObjectList();
	}

	public ShapePointPoiList getToolTipList(Projection projection, MousePnt mouse) {
		if (this.getSize() < 1) {
			return null;
		} else {
			ShapeObjectList shapeObjectList = this.getShapeList();
			if (shapeObjectList != null && shapeObjectList.getSize() > 0) {
				ShapeObject shapeObjFirst = shapeObjectList.get(0);
				if (shapeObjFirst instanceof ShapePointPoi) {
					ShapePointPoiList shapePoiList = new ShapePointPoiList();
					ShapePointPoi shapePoi;
					for( ShapeObject shapeObj : shapeObjectList ) {
						if( shapeObj instanceof ShapePointPoi ) {
							shapePoi = (ShapePointPoi) shapeObj;
							Poi poi = shapePoi.getPoi();
							ToolTip toolTip = poi.getToolTip();
							if( toolTip != null && this.isGood( toolTip.getContent() ) &&  shapePoi.isContains(projection, mouse)) {
								shapePoiList.add(shapePoi);
							}
						}
					}
					return shapePoiList;
				} else {
					return null;
				}
			} else {
				return null;
			}
		}
	}

	@Override
	public String toString() {
		return this.getName();
	}

	public Code getCode() {
		if( this.codeValue < 1 ) {
			return null;
		} else {
			return Code.getCode( this.codeValue );
		}
	}

	public Layer() {
		this(LayerType.LINE, "");
		this.debug.println(this, "LAYER DEFAULT 생성자 호출");
	}

	public Layer(LayerType lyrType, String name) {
		this(lyrType, name, -1, null);
	}

	public Layer(String name, URL urlParent) {
		this(LayerType.LINE, name, -1, urlParent);
	}

	public Layer(LayerType lyrType, String name, URL urlParent) {
		this(lyrType, name, -1, urlParent);
	}

	public Layer(LayerType lyrType, String name, Integer index, URL urlParent) {
		super(name, index);
		this.lyrType = lyrType;
		this.mbr = null;
		this.urlParent = urlParent;

		this.zoomLevelList = new ArrayList<Boolean>();

		this.dispLevelMin = -1;
		this.dispLevelMax = 13;

		this.layerEnabled = 1;

		this.initLayerOpen();
	}

	// abstract method

	// end of abstract method

	// member

	private LayerType lyrType;
	private URL urlParent;
	private transient TableModelDbf tableModel;
	private MetaDataLabel metaDataLabel;

	private Style_05_General shapeStyle;
	private Style_01_Text textStyle;

	private ArrayList<Boolean> zoomLevelList; // deprecated

	private int dispLevelMin = -1;
	private int dispLevelMax = 13;

	private int layerEnabled = 1; // layer enabled
	private int codeValue = - 1 ;

	private transient int mainDataLoaded = 0;
	private transient boolean showingNow;

	private transient ShapeObjectList shapeObjectList;

	// end of member

}
