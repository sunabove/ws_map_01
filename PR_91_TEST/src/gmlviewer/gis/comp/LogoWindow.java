package gmlviewer.gis.comp;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;
import gmlviewer.gis.util.*;

public class LogoWindow extends JWindow {

  public LogoWindow( ) {

     Container con = getContentPane();

     Image loadingImage = LogoPainter.loadingImage;

     int iw = loadingImage.getWidth( this ) ;
     int ih = loadingImage.getHeight( this ) ;

     setSize( iw, ih );

     WinUtil.locateOnTheScreenCenter( this );

     addFocusListener( new FocusAdapter() {

	 public void focusLost(FocusEvent e) {
	 }

     });

  }

  public void paint(Graphics g) {
     LogoPainter.paint( this, -1 );
  }

}
