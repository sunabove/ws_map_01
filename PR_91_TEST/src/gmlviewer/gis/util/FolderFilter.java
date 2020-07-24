package gmlviewer.gis.util;

import java.io.*;

public class FolderFilter extends javax.swing.filechooser.FileFilter
implements java.io.FileFilter
{

  public FolderFilter() {
    super();
  }

  public boolean accept( File file ) {
    return file.isDirectory() ;
  }

  public String getDescription() {
    return "Folders Only";
  }

}
