package com.ynhenc.kml;

import java.awt.Color;
import java.awt.Component;
import java.io.*;
import java.util.Iterator;

import com.ynhenc.droute.*;
import com.ynhenc.droute.io.IoHandler;
import com.ynhenc.droute.map.link.*;
import com.ynhenc.droute.render.*;
import com.ynhenc.comm.file.*;
import com.ynhenc.comm.*;

public class KmlHandlerLinkShape extends IoHandler {

	public void toKml(Component comp, LinkShapeList linkShapeList ) throws Exception {

		if (linkShapeList != null) {
			FileSystem fileSystem = new FileSystem();
			String title = "Save a search path to kml file ";
			File file = fileSystem.selectFile_Single(comp, this.getFileChooserSave(title));
			if (file != null) {
				this.toKml(file, linkShapeList);
			}
		}

	}

	public void toKml(File file, LinkShapeList linkShapeList) throws Exception {

		FileOutputStream fos = new FileOutputStream(file);
		BufferedWriter buff = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));

		KmlStyle kmlStyle = new KmlLineStyle( 0, Color.yellow, 1 );

		this.toKmlDocument(buff, linkShapeList, kmlStyle );

		// flush stream to physical file.
		buff.flush();
		fos.flush();
		fos.close();

	}

	public void toKmlDocument(Writer buff, LinkShapeList linkShapeList, KmlStyle kmlStyle ) throws Exception {

		KmlKml kml = new KmlKml();

		String docName = "linkShape doc";
		String docDesc = "";

		KmlStyleContainer styleContainer = new KmlStyleContainer();
		styleContainer.addComponent(kmlStyle);

		KmlDocument document = new KmlDocument(docName, docDesc, styleContainer );

		ArrayListEx<KmlPlacemark> pmList = new ArrayListEx<KmlPlacemark>();

		for( LinkShape linkShape: linkShapeList ) {
			String name = "link_name " + linkShape.getLinkId() ;
			String desc = "link_id:" + linkShape.getLinkId();
			KmlPlacemark pm = new KmlPlacemark( name, kmlStyle, desc );
			KmlGeometry gm = new KmlLineString();
			KmlCoordinates coordList = new KmlCoordinates();
			KmlCoord coord = new KmlCoord(linkShape.toKmlCoords().toString());

			pm.addComponent(gm);
			gm.addComponent(coordList);
			coordList.addComponent(coord);

			pmList.add( pm );
		}

		String folderName = "linkShape folder";
		String folderDesc = "";

		KmlFolder folder = new KmlFolder(folderName, folderDesc, pmList );

		kml.addComponent(document);

		document.addComponent(folder);

		kml.writeTag(buff);

	}

	@Override
	public FileFilter_01_Simple getFileFilter() {
		return new FileFilter_01_Simple(new String[] { ".KML" }, "KML for Google Earth");
	}

}
