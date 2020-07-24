package gmlviewer.gis.rsc;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.net.*;

public class Resource {

  public static URL getResource(String src) {

//      Utilities.debug(Resource.class, "" + src );

      return klass.getResource( src );

  }

  public static Image getResourceImage( String dir, String src ) {

     return getResourceImageIcon( dir, src ).getImage();

  }

  public static ImageIcon getResourceImageIcon( String dir, String src ) {

     return getImageIcon( getResource( dir + "/" + src ) );

  }

  private static ImageIcon getImageIcon(java.net.URL imageURL ) {

      return new javax.swing.ImageIcon( imageURL );

  }

  private static final Class klass = new Resource().getClass();

}
