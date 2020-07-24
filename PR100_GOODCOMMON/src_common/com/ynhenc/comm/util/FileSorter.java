package com.ynhenc.comm.util;

import java.util.*;
import java.io.*;

public class FileSorter
    implements Comparator {

  private boolean folderFirst;

  public FileSorter( ) {
    this( true );
  }

  public FileSorter( boolean folderFirst ) {

    this.folderFirst = folderFirst;

  }

  public int compare(Object a, Object b) {

    File filea = (File) a;
    File fileb = (File) b;

    if ( folderFirst & ( filea.isDirectory() && ! fileb.isDirectory() ) ) {
      return -1;
    }
    else if ( folderFirst & ( ! filea.isDirectory() && fileb.isDirectory() ) ) {
      return 1;
    }
    else {
      return filea.getName().compareTo(fileb.getName());
      //return filea.getName().compareToIgnoreCase(fileb.getName());
    }

  }

  public File[] sort( File folder, FileFilter filter ) {
    if( folder == null ) {
        return null;
    }

    File[] files = folder.listFiles( filter );
    
    if( files != null ) {
    	Arrays.sort( files , this);
    }

    return files;

  }


}
