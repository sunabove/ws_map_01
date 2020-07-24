package test;

/**
 * Title:        java chart project
 * Description:  jchart v1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import jchart.*;
import java.awt.*;
import java.awt.geom.*;

public class Test2 extends Frame {

  double x = 100, y = 100, w = 80, h = 50;

  Shape ellipse =  ShapeUtilities.getShapeProcessedByVerticalBeam ( new Ellipse2D.Double( x, y, w, h ) );

  double shx = - .5 ;

  AffineTransform at = AffineTransform.getShearInstance( shx, 0 );

  Shape shearedEllipse = at.createTransformedShape( ellipse );

  public Test2() {

     setSize( 300, 300 );

  }

  public void paint(Graphics g) {

     super.paint( g );

     Graphics2D g2 = (Graphics2D) g;

     g2.setColor( getForeground() );
     g2.draw( ellipse );

     Rectangle2D ellipseBounds =  ellipse.getBounds2D();

     System.out.println( "" + ellipseBounds );

     g2.draw( ellipseBounds );

     g2.translate( 0, 2*h );

     g2.setColor( Color.red );
     g2.draw( shearedEllipse );

     Rectangle2D shearedEllipseBounds = ShapeUtilities.getShapeProcessedByVerticalBeam( shearedEllipse ).getBounds2D() ;

     System.out.println( shearedEllipseBounds );

     g2.draw( shearedEllipseBounds );

     g2.translate( 0, -2*h );

  }

  public static void main(String [] args) {

     new Test2().show();

  }

}