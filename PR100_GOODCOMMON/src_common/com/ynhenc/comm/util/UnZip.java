package com.ynhenc.comm.util;

import java.io.*;
import java.util.zip.*;
import java.util.*;
import javax.swing.*;

public class UnZip {

  private ProgressInterface progressBar;

  public UnZip( ProgressInterface progressBar) {

    this.progressBar = progressBar;

  }

  public Vector unzip(File file, File destDir ) throws Exception {

     double src = 0;

     if( progressBar != null ) {

	ZipInputStream zis = new ZipInputStream(new FileInputStream( file ));

	for( ; zis.getNextEntry() != null ; src ++ ) {
	}

	zis.close();

     }

     return unzip( new FileInputStream( file ), destDir, src );

  }

  private Vector unzip( InputStream in, File destDir, double src ) throws Exception {

	Vector fileList = new Vector();

	ZipInputStream zis = new ZipInputStream( in );

	double sink = 0;

	ProgressInterface progressBar = this.progressBar;

	for (ZipEntry e; (e=zis.getNextEntry())!=null; )
	{
	    sink ++;

	    File file= new File( destDir, e.getName());

	    fileList.addElement( file );

	    FileOutputStream fos = new FileOutputStream(file);

	    byte[] buf = new byte[1024];

	    for (int i; (i=zis.read(buf))!=-1; )
	    {

		fos.write(buf, 0, i);

	    }

	    fos.close();

	    zis.closeEntry();

	    if( progressBar != null ) {

	       progressBar.setValue( (int) (100.0*sink/src) );

	    }

	}

	zis.close();

	if( progressBar != null ) {

	  progressBar.setValue( -1 );

	}

	return fileList;

  }

}
