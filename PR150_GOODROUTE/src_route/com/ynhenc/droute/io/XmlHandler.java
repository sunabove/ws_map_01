package com.ynhenc.droute.io;

import java.awt.*;
import java.io.*;

import javax.swing.JFileChooser;

import com.ynhenc.comm.*;
import com.ynhenc.comm.file.*;
import com.ynhenc.comm.util.XMLUtil;
import com.ynhenc.droute.*;
import com.ynhenc.droute.map.*;
import com.ynhenc.droute.render.*;


public class XmlHandler extends IoHandler {

	public void write(PathFinderPanel pathFinderPanel) {
		Component comp = pathFinderPanel;
		String title = "맵 데이터 저장";
		FileSystem fs = new FileSystem();
		JFileChooser fileChooser = this.getFileChooserSave( title );

		final File file = fs.selectFile_Single( comp, fileChooser );
		final MapAppData mapAppData = pathFinderPanel.getMapAppData();

		if (file != null && mapAppData != null ) {
			// 파일 다이얼로그 선택후 까맣게 화면이 유지되는 것을 방지하기 위한 조치임.

			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						XmlHandler.this.write( file , mapAppData );
					} catch (Exception e) {
						XmlHandler.this.debug(e);
					}
				}
			});

		}

	}

	public void write( File file, MapAppData mapAppData ) throws Exception {

		if( ! file.getName().toUpperCase().endsWith( ".XML") ) {
			file = new File( file.getCanonicalPath() + ".xml" );
		}

		this.message("Save File = " + file.getCanonicalPath());

		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter writer = new OutputStreamWriter(fos, "UTF-8");

		XMLUtil.toXml( mapAppData, writer);

		this.message( "Save Done GisProject File = " + file.getCanonicalPath() );


	}

	public void open(final PathFinderPanel pathFinderPanel) {
		Component comp = pathFinderPanel;
		String title = "맵 데이터 열기";
		FileSystem fs = new FileSystem();
		JFileChooser fileChooser = this.getFileChooserOpen( title );

		final File file = fs.selectFile_Single( comp, fileChooser );

		if (file != null ) {
			// 파일 다이얼로그 선택후 까맣게 화면이 유지되는 것을 방지하기 위한 조치임.
			javax.swing.SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					try {
						XmlHandler.this.open( pathFinderPanel, file  );
					} catch (Exception e) {
						XmlHandler.this.debug(e);
					}
				}
			});

		}

	}

	public void open( PathFinderPanel panel, File file ) throws Exception {

		this.message("Open File = " + file.getCanonicalPath());

		FileInputStream fos = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(fos, "UTF-8");

		Object obj = XMLUtil.fromXml( reader );

		MapAppData mapAppData = (MapAppData) obj;

		if( mapAppData != null ) {
			Map map = mapAppData.getMap();
			if( map != null ) {
				map.buildLink( );
				SrchOption srchOption = mapAppData.getSrchOption();
				PathFinder.searchPath(mapAppData, srchOption);
			}
		}

		this.message( "Open Done GisProject File = " + file.getCanonicalPath() );

		panel.setMapAppData(mapAppData);

		this.message( "Set MapAppData Done GisProject File = " + file.getCanonicalPath() );

	}

	@Override
	public FileFilter_01_Simple getFileFilter() {
		return new FileFilter_01_Simple(new String[] { ".XML" }, "XML (Y&H E&C Gis Project)");
	}
}
