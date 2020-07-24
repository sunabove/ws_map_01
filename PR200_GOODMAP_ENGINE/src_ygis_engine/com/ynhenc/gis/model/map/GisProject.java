package com.ynhenc.gis.model.map;

import java.awt.*;
import java.io.*;
import java.util.*;

import com.ynhenc.comm.ArrayListString;
import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.file.FileFilter_01_Simple;
import com.ynhenc.comm.util.UnitUtil;
import com.ynhenc.gis.*;
import com.ynhenc.gis.file.*;
import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.model.style.*;
import com.ynhenc.gis.projection.*;
import com.ynhenc.gis.ui.resource.*;
import com.ynhenc.gis.ui.viewer_03.dbf_viewer.*;
import com.ynhenc.gis.web.*;

public class GisProject extends GisComLib {

	public String getProjectFolderPath() {
		return projectFolderPath;
	}

	public Layer getTargetLayer() {
		return targetLayer;
	}

	public void setTargetLayer(Layer targetLayer) {
		this.targetLayer = targetLayer;
	}

	public File getMapMetaDataFile() {
		if (this.mapMetaDataFile == null) {
			File folder = this.getFolderProject();
			if (folder != null) {
				File fileList[] = folder.listFiles();
				for( File file : fileList ) {
					if( file.getName().equalsIgnoreCase( "MAP.DBF")) {
						this.mapMetaDataFile = file;
						break;
					}
				}
			}
		}

		this.debug( "MapMetaFile:" + this.mapMetaDataFile );

		return this.mapMetaDataFile;
	}

	public void initMapMetaData() {

	}

	public String getProjectFilePath() {
		return projectFilePath;
	}

	public void setProjectFilePath(File file) {
		try {
			this.projectFilePath = file.getCanonicalPath();
		} catch (Exception e) {
			this.debug(e);
		}
	}

	public MapDataSessionHash getMapDataSessionList() {
		if (this.mapDataSessionList == null) {
			this.mapDataSessionList = new MapDataSessionHash();
		} else {
			this.mapDataSessionList.getClearOutOldSession();
		}
		return this.mapDataSessionList;
	}

	public CoordinateConversion getCoordinateConversion() {
		return this.getGisProjectOption().getCoordinateConversion();
	}

	public File getFolderProject() {
		String projectFolderPath = this.projectFolderPath;

		if (this.isGood(projectFolderPath)) {
			File folder = null;
			try {
				folder = new File(projectFolderPath);
			} catch (Exception e) {
				this.debug(e);
			}
			if (folder != null && folder.exists()) {
				return folder;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public File getFolderMapLayer() {
		File projectFolder = this.getFolderProject();

		File file = new File(projectFolder, "mapLayer");

		return file;
	}

	public File getFolderMapIcon() {
		File projectFolder = this.getFolderProject();

		File file = new File(projectFolder, "mapIcon");

		return file;
	}

	public void setProjectFolderPath(File projectFolder) {
		try {
			this.projectFolderPath = projectFolder.getCanonicalPath();
		} catch (IOException e) {
			this.debug(e);
		}
	}

	public int getTopLevel() {
		return this.getZoomLevelMax();
	}

	public int getMinLevel() {
		return 0;
	}

	public int getZoomLevelMax() {
		return this.getZoomLevelList().getZoomLevelMax();
	}

	public int getZoomLevelCurr() {
		return this.getZoomLevelList().getZoomLevelCurr();
	}

	public double getScale(int zoomLevel) {
		return this.getZoomLevelList().getScale(zoomLevel);
	}

	public double getScaleRelative(int zoomLevel) {
		return this.getZoomLevelList().getScaleRelative(zoomLevel);
	}

	public LevelScale getLevelScale(int zoomLevel) {
		return this.getZoomLevelList().getLevelScale(zoomLevel);
	}

	public ZoomLevelList getZoomLevelList() {
		if (this.zoomLevelList == null) {
			this.zoomLevelList = new ZoomLevelList();
		}

		return this.zoomLevelList;
	}

	public GisProjectOption getGisProjectOption() {

		if (this.gisProjectOption == null) {
			this.gisProjectOption = new GisProjectOption();
		}
		return this.gisProjectOption;
	}

	public void setGisProjectOption(GisProjectOption gisProjectOption) {
		this.gisProjectOption = gisProjectOption;
	}

	public Projection getProjectionPrev() {
		return this.projectionPrev;
	}

	public void setProjectionPrev(Projection projectionPrev) {
		this.projectionPrev = projectionPrev;
	}

	public Projection getProjection() {
		if (this.projection == null) {
			Mbr mbr = new Mbr(0, 0, 1, 1);
			Projection projection = Projection.getProject(mbr, 200, 200);

			this.setProjection(projection);
		}
		return this.projection;
	}

	public void setProjection(Projection projection) {
		this.projection = projection;
	}

	public MapDataSession getMapData_Session(Request req) {

		MapDataSessionHash mapDataSessionList = this.getMapDataSessionList();

		MapSession mapSession = req.getMapSeesion();

		// this.debug("MapSession No = " + mapSession.getNo());

		MapDataSession mapData = mapDataSessionList.get(mapSession);

		if (mapData == null) {
			mapData = new MapDataSession();
			mapDataSessionList.put(mapSession, mapData);
			return mapData;
		} else {
			return mapData;
		}

	}

	public MapDataBase getMapData_Base() {
		return this.mapData_Base;
	}

	public Mbr getMbrPhys() {
		return this.getMapData_Base().getMbrPhys();
	}

	public Mbr getMbrPhysScr(Dimension pixelSize) {
		return this.getMapData_Base().getMbrPhysScr(pixelSize);
	}

	public Mbr getMbrTopLevel() {
		return this.getMapData_Base().getMbrTopLevel();
	}

	public LayerList getLayerList() {
		return this.getMapData_Base().getLayerList();
	}

	public ArrayListString getLayerNameList() {
		return this.getMapData_Base().getLayerNameList();
	}

	public Layer getLayer(String layerName) {
		return this.getMapData_Base().getLayer(layerName);
	}

	public Layer getLayer(Integer index) {
		return this.getMapData_Base().getLayer(index);
	}

	public Layer getLayerSearchByAttribute(Request req, String layerName, String colName, String colValue, Color lineColor,
			Color fillColor, Color textColor, int lineWidth) {

		Layer layerSearched = this.getLayerSearchedByAttribute_Local(layerName, colName, colValue, lineColor, fillColor,
				textColor, lineWidth);

		MapDataSession mapData = this.getMapData_Session(req);

		if (layerSearched != null) {
			mapData.addLayer(layerSearched);
		}

		return layerSearched;

	}

	public void getClearLayerSearch(Request req) {

		MapDataSession mapData = this.getMapData_Session(req);

		if (mapData != null) {
			mapData.removeAllLayerList();
		}

	}

	public ShapePointPoiList getToolTipList(Request req, MousePnt mouse) {
		boolean localDebug = false;

		MapDataSession mapData = this.getMapData_Session(req);

		ShapePointPoiList allPoiList = new ShapePointPoiList();

		Projection projection;

		if (true) {
			GisProject gisProject = this;
			MapService mapService = new MapService();
			Dimension mapImageSize = mapService.getPixelSize(req, gisProject);
			int zoomLevelCurr = mapService.getZoomLevelCurr(req, gisProject);
			projection = mapService.getProjection(req, mapImageSize, gisProject, zoomLevelCurr);
		}

		if (mapData != null) {
			LayerList layerList = mapData.getLayerList();
			for (Layer layer : layerList) {
				if (layer != null) {
					allPoiList.addList(layer.getToolTipList(projection, mouse));
				}
			}
		}

		return allPoiList;
	}

	public Layer getAdd_01_Poi(Request req, AddObjectPoi poiData, int layerNo, MinMaxInt dispLevel ) {
		boolean localDebug = false;

		MapDataSession mapData = this.getMapData_Session(req);

		Layer layer = null;

		if (mapData != null) {
			layer = mapData.getLayerAddPoi(poiData.getPoiLayerName());

			if (layerNo > -1) {
				layer.setLayerNo(layerNo);
			} else {
				layer.setLayerNo(Integer.MAX_VALUE);
			}

			layer.setLayerEnabled(  true );

			if( dispLevel != null) {
				layer.setDispLevel(dispLevel);
			}

			ShapeObject shape = layer.getShape(poiData.getPoiId());

			ShapePointPoi shapePoi;

			if (shape != null) {
				shapePoi = (ShapePointPoi) shape;
				shapePoi.setLocation(poiData.getPoiPoint());
				this.debug("POI : " + poiData.getPoiId() + " is updated!", localDebug);
			} else {
				shapePoi = new ShapePointPoi(poiData.getPoiId(), -1, poiData.getPoiPoint().getX(), poiData.getPoiPoint()
						.getY());
				layer.addSpatialShape(shapePoi);
				this.debug("POI : " + poiData.getPoiId() + " is appended!", localDebug);
			}

			if (shapePoi != null) {
				Poi poi = shapePoi.getPoi();
				poi.setTextLabel(poiData.getPoiLabel());
				poi.setTextColor(poiData.getPoiTextColor());
				IconImage iconImage = this.getIconImage(poiData.getPoiIcon());
				poi.setIconImage(iconImage);
				poi.setToolTip(poiData.getToolTip());
				poi.setBgColor(poiData.getPoiBgColor());
				poi.setLineColor(poiData.getPoiLineColor());
				poi.setLineWidth(poiData.getPoiLineWidth());
			}

			layer.setMbr(null);
		}

		return layer;
	}

	public Layer getAdd_02_Loi(Request req, AddObjectLoi loiData, int layerNo , MinMaxInt dispLevel ) {

		MapDataSession mapData = this.getMapData_Session(req);
		Layer layer = null;
		if (mapData != null) {
			layer = mapData.getLayerAddLine(loiData.getLayerName());

			if (layerNo > -1) {
				layer.setLayerNo(layerNo);
			} else {
				layer.setLayerNo(Integer.MAX_VALUE);
			}

			layer.setLayerEnabled(  true );

			if( dispLevel != null) {
				layer.setDispLevel(dispLevel);
			}

			GeoPolyLine polyLine = loiData.getPolyLine();
			ShapeLinePoi shapeLoi = null;
			if (polyLine != null) {
				ShapeObject shape = layer.getShape(loiData.getId());
				if (shape != null) {
					shapeLoi = (ShapeLinePoi) shape;
					shapeLoi.setGeoObject(polyLine);
				}
			}

			if (shapeLoi == null && polyLine != null) {
				shapeLoi = new ShapeLinePoi(loiData.getId(), -1, polyLine);
				layer.addSpatialShape(shapeLoi);
			}

			if (loiData.getLineColor() != null) {
				layer.getShapeStyle().setLineColor(loiData.getLineColor());
			}

			if (loiData.getLineWidth() > 0) {
				layer.getShapeStyle().setLineWidth(loiData.getLineWidth());
			}

			layer.setMbr(null);

		}

		return layer;
	}

	public ShapeObject getAdd_03_PoiMeasureDistance(Request req, GeoPoint geoPoint) {
		String layerName = "MEARSURE_DIST";
		return this.getAddMeasurePoint(req, layerName, LayerType.LINE, geoPoint);

	}

	public ShapeObject getAdd_04_PoiMeasureArea(Request req, GeoPoint geoPoint) {
		String layerName = "MEASURE_AREA";
		return this.getAddMeasurePoint(req, layerName, LayerType.AREA, geoPoint);

	}

	private ShapeObject getAddMeasurePoint(Request req, String layerName, LayerType layerType, GeoPoint geoPoint) {
		Layer layer;

		if (layerType == LayerType.LINE) {
			layer = this.getLayerAddLine(req, layerName);
		} else {
			layer = this.getLayerAddArea(req, layerName);
		}

		if (layer != null) {
			int id = 0;
			ShapeObject shape = layer.getShape(id);

			if (shape == null) {
				GeoPoint[] pointList = {};
				if (layerType == LayerType.LINE) {
					GeoPolyLine polyLine = new GeoPolyLine(pointList);
					shape = new ShapeLinePoi(id, -1, polyLine);
				} else {
					GeoPolygon polyLine = new GeoPolygon(pointList);
					shape = new ShapeAreaPoi(id, -1, polyLine);
				}
				layer.addSpatialShape(shape);
			}

			shape.addGeoPoint(geoPoint);
			shape.setCentroid(null); // 중심정 재계산을 위해 초기화함.

			if (shape instanceof PoiInterface) {
				PoiInterface poiInterface = (PoiInterface) shape;
				Poi poi = poiInterface.getPoi();
				String textLabel;
				if (layerType == LayerType.LINE) {
					textLabel = "길이: " + shape.getGeoObject().getLength();
				} else {
					textLabel = "면적: " + shape.getGeoObject().getArea();
				}
				poi.setTextLabel(textLabel);
			}

			layer.setMbr(null); // Mbr 재계산을 위해 초기화 함.

			return shape;

		} else {
			return null;
		}
	}

	public Layer getLayerAddLine(Request req, String layerName) {
		MapDataSession mapData = this.getMapData_Session(req);

		if (mapData != null) {
			return mapData.getLayerAddLine(layerName);
		} else {
			return null;
		}
	}

	public Layer getLayerAddArea(Request req, String layerName) {
		MapDataSession mapData = this.getMapData_Session(req);

		if (mapData != null) {
			return mapData.getLayerAddArea(layerName);
		} else {
			return null;
		}
	}

	public void getRemoveAllPoiList(Request req) {

		MapDataSession mapData = this.getMapData_Session(req);

		if (mapData != null) {
			mapData.removeAllLayerList();
		}

	}

	public void getRemoveSessionLayer(Request req, String removeLayerName, int removeShapeId) {

		MapDataSession mapData = this.getMapData_Session(req);

		if (mapData != null) {
			Layer layer = mapData.getLayer(removeLayerName);
			if (layer != null) {
				if (removeShapeId > -1) {
					layer.removeSpatialShape(removeShapeId);
				} else {
					mapData.removeLayer(layer);
				}
			}
		}

	}

	private Layer getLayerSearchedByAttribute_Local(String layerName, String colName, String colValue, Color lineColor,
			Color fillColor, Color textColor, int lineWidth) {

		if (!this.isGood(colName) || !this.isGood(colValue)) {
			return null;
		}

		final Layer layer = this.getLayer(layerName);

		if (layer != null) {
			TableModelDbf tableModel = layer.getTableModel();

			if (tableModel != null) {

				Integer colIdx = tableModel.getColumnIndex(colName);

				if (colIdx != null) {

					Hashtable<Integer, Integer> idList = new Hashtable<Integer, Integer>();

					int rowCount = tableModel.getRowCount();

					for (int rowNo = 0, iLen = rowCount; rowNo < iLen; rowNo++) {
						if (colValue.equals("" + tableModel.getValueAt(rowNo, colIdx))) {
							idList.put(rowNo + 1, rowNo + 1); // shape id's
							// initial index
							// is 1 .
						}
					}

					if (idList.size() > 0) {
						final ArrayList<ShapeObject> shapeListSearched = new ArrayList<ShapeObject>();

						if (true) {
							final ArrayList<ShapeObject> shapeList = layer.getShapeList();
							Integer shapeId;
							for (ShapeObject shape : shapeList) {
								shapeId = shape.getRecordNo();
								if (idList.get(shapeId) != null) {
									shapeListSearched.add(shape);
								}
							}
						}

						if (shapeListSearched.size() > 0) {
							Layer layerSearched = new Layer();
							layerSearched.setLyrType(layer.getLyrType());
							if (true) {
								Style_05_General shapeStyle = layer.getShapeStyle();

								if (shapeStyle != null) {
									Style_05_General shapeStyleClone = shapeStyle.create();
									if (lineColor != null) {
										shapeStyleClone.setLineColor(lineColor);
									}
									if (fillColor != null) {
										shapeStyleClone.setFillColor(fillColor);
									}
									if (lineWidth > 0) {
										this.debug("lineWidth=" + lineWidth);
										shapeStyleClone.setLineWidth(lineWidth);
									}
									layerSearched.setShapeStyle(shapeStyleClone);
								}

								Style_01_Text textStyle = layer.getTextStyle();
								if (textStyle != null) {
									Style_01_Text textStyleClone = textStyle.create();
									if (textColor != null) {
										textStyleClone.setColor(textColor);
									}
									layerSearched.setTextStyle(textStyleClone);
								}
							}

							layerSearched.addSpatialShapeList(shapeListSearched);
							return layerSearched;
						}
					}
				}
			}
		}

		return null;
	}

	public IconImage getIconImage(String iconName) {

		iconName = iconName.toUpperCase();

		if (this.isGood(iconName)) {
			Vector<IconImage> iconImageList = this.getIconImageList();

			if (iconImageList != null) {
				String imageName;
				for (IconImage iconImage : iconImageList) {
					imageName = iconImage.getImageName().toUpperCase();
					if (iconName.equalsIgnoreCase(imageName)) {
						return iconImage;
					} else if (imageName.startsWith(iconName)) {
						return iconImage;
					} else if (iconName.startsWith(imageName)) {
						return iconImage;
					}
				}
			}
		}

		return null;
	}

	public Vector<IconImage> getIconImageList() {
		if (this.iconImageList == null) {
			this.iconImageList = this.createIconImageList();
		}
		return this.iconImageList;
	}

	private Vector<IconImage> createIconImageList() {

		GisProject gisProject = this;

		File folder = gisProject.getFolderMapIcon();

		String[] extList = { ".png", ".jpg", ".jpeg", ".gif" };

		FileFilter filter = new FileFilter_01_Simple(extList, "");

		File[] fileList = folder.listFiles(filter);

		String folderPath = null;

		try {
			folderPath = folder.getCanonicalPath();
		} catch (IOException e) {
			this.debug(e);
		}

		Vector<IconImage> iconImageList = new Vector<IconImage>();

		if (folderPath != null) {
			IconImage iconImage = null;
			for (File file : fileList) {
				String iconName = file.getName();
				iconImage = IconImageFactory.getIconImage(folderPath, iconName);
				if (iconImage.getBfrImage() != null) {
					iconImageList.add(iconImage);
				} else {
					this.debug("NULL IMAGE NAME = " + iconName);
				}
			}
		}

		return iconImageList;

	}

	public Dimension getMapPixelSizeDefault() {

		return new Dimension(256, 256);
	}

	public MapTileGroup getMapTileGroup(int zoomLevel) {

		LevelScale leveScale = this.getLevelScale(zoomLevel);

		Dimension pixelSize = this.getMapPixelSizeDefault();

		Mbr mbrTopLevel = this.getMbrTopLevel();

		LevelScale levelScale = this.getLevelScale(zoomLevel);

		MapTileGroup mapTileGroup = new MapTileGroup(levelScale, pixelSize, mbrTopLevel);

		return mapTileGroup;
	}

	public MapTileGroup[] getMapTileGroupList() {

		int zoomLevelMax = this.getZoomLevelMax();

		MapTileGroup[] mapTileGroupList = new MapTileGroup[zoomLevelMax + 1];

		for (int zoomLevel = 0; zoomLevel <= zoomLevelMax; zoomLevel++) {
			mapTileGroupList[zoomLevel] = this.getMapTileGroup(zoomLevel);
		}

		return mapTileGroupList;

	}

	public GisProject() {

		this.mapData_Base = new MapDataBase();
		this.mapDataSessionList = new MapDataSessionHash();
		this.gisProjectOption = new GisProjectOption();

		this.zoomLevelList = new ZoomLevelList();

	}

	private Projection projection;
	private transient Projection projectionPrev;

	private ZoomLevelList zoomLevelList;

	private MapDataBase mapData_Base;
	private transient MapDataSessionHash mapDataSessionList;

	private String projectFolderPath;
	private transient String projectFilePath;
	private transient Vector<IconImage> iconImageList;
	private transient File mapMetaDataFile;
	private GisProjectOption gisProjectOption;
	private transient Layer targetLayer;

}
