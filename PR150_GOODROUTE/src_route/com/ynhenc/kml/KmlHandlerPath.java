package com.ynhenc.kml;

import java.awt.Component;
import java.io.*;
import java.util.Iterator;

import com.ynhenc.droute.*;
import com.ynhenc.droute.io.IoHandler;
import com.ynhenc.droute.map.link.LinkShape;
import com.ynhenc.droute.render.*;
import com.ynhenc.comm.file.*;
import com.ynhenc.comm.*;

public class KmlHandlerPath extends IoHandler {

	public void toKml(Component comp, Path path) throws Exception {

		if (path != null) {
			FileSystem fileSystem = new FileSystem();
			String title = "Save a search path to kml file ";
			File file = fileSystem.selectFile_Single(comp, this.getFileChooserSave(title));
			if (file != null) {
				Mode.GeoLinkCoordLevel coordLevel = Mode.GeoLinkCoordLevel.NODE;
				String fileName = file.getName();
				if( fileName.toUpperCase().indexOf( "LINK" ) > -1 ) {
					coordLevel = Mode.GeoLinkCoordLevel.LINKSHAPE ;
					int i = 0;
					i ++ ;
				}
				this.debug( "GeoLinkCoordLevel = " + coordLevel , true  );
				this.toKml(file, path, coordLevel );
			}
		}

	}

	private void toKml(File file, Path path, Mode.GeoLinkCoordLevel coordLevel ) throws Exception {

		FileOutputStream fos = new FileOutputStream(file);
		int buffSize = 1000000*10;
		BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"), buffSize);

		this.toKmlDocument(buff, path, coordLevel );

		// flush stream to physical file.
		buff.flush();
		fos.flush();
		fos.close();

	}

	public void toKmlDocument(Writer buff, Path path) throws Exception {
		this.toKmlDocument(buff, path, Mode.GeoLinkCoordLevel.NODE ) ;
	}

	public void toKmlDocument(Writer buff, Path path, Mode.GeoLinkCoordLevel coordLevel ) throws Exception {

		KmlKml kml = new KmlKml();

		KmlStyleContainer styleContainer = new KmlStyleContainer();

		KmlLineStyle kmlLineStyle = null;

		Style pathStyle = path.getStyle();
		if (pathStyle != null) {
			kmlLineStyle = new KmlLineStyle(pathStyle.getId(), pathStyle.getLineColor(), pathStyle.getLineWidth());
			styleContainer.addComponent(kmlLineStyle);
		}

		KmlDocument document = new KmlDocument(path.getKmlDocumentName(), path.getKmlDocumentDesc(), styleContainer);

		KmlPlacemarkIterator placemarkList = path.getPlacemarkIterable(kmlLineStyle , coordLevel );

		KmlFolder folder = new KmlFolder(path.getKmlFolderName(), path.getKmlFolderDesc(), placemarkList);

		kml.addComponent(document);

		document.addComponent(folder);

		kml.writeTag(buff);

	}

	@Override
	public FileFilter_01_Simple getFileFilter() {
		return new FileFilter_01_Simple(new String[] { ".KML" }, "KML for Google Earth");
	}

}
