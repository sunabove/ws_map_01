package com.ynhenc.gis.model.map;

import java.io.*;
import java.net.URL;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.file.FileSystem;
import com.ynhenc.comm.util.WinUtil;
import com.ynhenc.comm.util.XMLUtil;
import com.ynhenc.gis.*;
import com.ynhenc.gis.file.esri_shape.EsriReaderShpSingleLayer;
import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.model.style.Style_01_Text;
import com.ynhenc.gis.projection.CoordinateConversion;
import com.ynhenc.gis.ui.resource.IconImage;
import com.ynhenc.gis.ui.viewer_01.map_viewer.LayerFontStyleShower;
import com.ynhenc.gis.ui.viewer_01.map_viewer.MapViewer;

public class GisProjectOpener extends GisComLib {

	public MapViewer getMapViewer() {
		return mapViewer;
	}

	public void gisProject_01_OpenFile() {
		MapViewer mapViewer = this.getMapViewer();

		FileSystem fs = new FileSystem();

		JFileChooser fileChooser = this.mapViewer.getFileChooser_02_GisProject();

		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChooser.setDialogTitle("프로젝트  열기");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

		final File file = fs.selectFile_Single(mapViewer, fileChooser);

		if (file != null && file.exists()) {
			// TODO 파일 다이얼로그 선택후 까맣게 화면이 유지되는 것을 방지.

			this.invokeLater(new Runnable() {
				public void run() {
					try {
						GisProjectOpener.this.gisProject_01_OpenFile_Runnable(file);
					} catch (Exception e) {
						GisProjectOpener.this.debug(e);
					}
				}
			});

			GisRegistry gisRegistry = GisRegistry.getGisRegistry();

			try {
				gisRegistry.writeRegistry_GisProjectFilePath(file);
			} catch (Exception e) {
				this.debug(e);
			}

		}

	}

	public void gisProject_01_OpenFile_RecentProject(String projectFilePath) {

		final File file = new File(projectFilePath);

		if (file != null && file.exists()) {
			// TODO 파일 다이얼로그 선택후 까맣게 화면이 유지되는 것을 방지.
			// this.updateUI();

			this.invokeLater(new Runnable() {
				public void run() {
					try {
						GisProjectOpener.this.gisProject_01_OpenFile_Runnable(file);
					} catch (Exception e) {
						GisProjectOpener.this.debug(e);
					}
				}
			});

		}

	}

	private void gisProject_01_OpenFile_Runnable(File file) throws Exception {

		GisProjectFileFormat gisProjectFileFormat = this.openGisProjectFile(file);

		if (gisProjectFileFormat != null) {
			MapViewer mapViewer = this.getMapViewer();
			this.init_GisProjectOpenFile(gisProjectFileFormat );
			GisProject gisProject = gisProjectFileFormat.getGisProject();
			if (mapViewer != null) {
				mapViewer.setGisProject(gisProject);
				if( true ) {
					// JFrame 타이틀 변경
					JFrame jframe = WinUtil.getJFrame( mapViewer );
					if( jframe != null ) {
						jframe.setTitle( GisRegistry.getGisRegistry().getAppFullName() + " : " + gisProject.getProjectFilePath() );
					}
				}
			}
		} else {
			this.message("Wrong GisProject Type!");
		}

	}

	public void init_GisProjectOpenFile(GisProjectFileFormat gisProjectFileFormat ) throws Exception {
		GisProject gisProject = gisProjectFileFormat.getGisProject();

		CoordinateConversion coordConv = gisProject.getCoordinateConversion();

		File fileOpened = gisProjectFileFormat.getFileOpened();

		gisProject.setProjectFilePath( fileOpened );

		gisProject.setProjectFolderPath(fileOpened.getParentFile());

		MapDataBase mapData_Base = gisProject.getMapData_Base();

		Projection projection = gisProject.getProjection();

		final String urlParentText = fileUrlHead + gisProject.getFolderMapLayer().getCanonicalPath();
		final URL urlParent = new URL(urlParentText);

		boolean localDebug = true;

		if (localDebug) {
			this.debug("URL PARENT = " + urlParent);
		}

		final String folderPath_MapIcon = gisProject.getFolderMapIcon().getCanonicalPath();

		LayerList layerList = mapData_Base.getLayerList();
		int index = 0;
		boolean needStyling = false;
		int layerListSize = layerList.getSize();

		MapViewer mapViewer = this.getMapViewer();

		for (Layer layer : layerList) {
			layer.setUrlParent(urlParent);
			layer.initLayerOpen();

			if (true) {
				Style_01_Text textStyle = layer.getTextStyle();
				if (textStyle != null) {
					IconImage imageIcon = textStyle.getIconImage();
					if (imageIcon != null) {
						imageIcon.setFolder(folderPath_MapIcon);
					}
				}
			}

			if ( mapViewer != null) {
				mapViewer.layer_02_OpenSingleUrl(layer, index, needStyling, coordConv);
			} else {
				this.getReadLayerData(layer, coordConv);
			}

			if( layer.getLayerNo() < 0 ) {
				layer.setLayerNo( layerListSize - index ) ;
			}

			index++;
		}

		mapData_Base.sortLayers();

		this.debug("Global Mbr = " + gisProject.getMbrTopLevel());
	}

	private void getReadLayerData(Layer layer, CoordinateConversion coordinateConversion) throws Exception {

		EsriReaderShpSingleLayer esriReader = EsriReaderShpSingleLayer.getEsriShapeReader(layer, coordinateConversion);

		this.debug("Reading Main File ......");

		try {
			esriReader.readMainFileHeader();
			esriReader.readMainFileRecordContents();
		} catch (RuntimeException e) {
			this.debug("Error: failed reading a layer : " + layer.getName());

			throw e;
		}

		this.debug("Done reading main file.");

	}

	public GisProjectFileFormat openGisProjectFile(File file) throws Exception {

		this.message("GisProject File Open = " + file.getCanonicalPath());

		FileInputStream fis = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(fis, "UTF-8");

		Object obj = XMLUtil.fromXml(reader);

		if (obj instanceof GisProjectFileFormat) {

			GisProjectFileFormat fileFormat = (GisProjectFileFormat) obj;

			fileFormat.setFileOpened(file);

			return fileFormat;

		} else {
			this.message("Wrong GisProject Type!");
			return null;
		}

	}

	public GisProjectOpener(MapViewer mapViewer) {
		this.mapViewer = mapViewer;
	}

	private String fileUrlHead = "file:/";
	private MapViewer mapViewer;

}
