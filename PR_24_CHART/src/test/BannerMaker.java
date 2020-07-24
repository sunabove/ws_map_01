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
import java.awt.event.*;
import java.awt.geom.*;

public class BannerMaker extends Panel {

  int x = 50, y = 50, w = 200, h = 200;

  double start = 30, extent = 120;

  Arc2D arc = getArc();

  private Label label = new Label( "coords" );
  private Button rot = new Button( "Rot" );
  private Button incAngle = new Button( "+" );
  private Button decAngle = new Button( "-" );

  private Arc2D getArc() {

       return new Arc2D.Double( x, y, w, h, start, extent, Arc2D.PIE );

  }

  public BannerMaker() {

       setLayout( new BorderLayout() );
       add( label, BorderLayout.SOUTH );
       add( rot, BorderLayout.EAST );
       add( incAngle, BorderLayout.WEST );
       add( decAngle, BorderLayout.NORTH );

       addMouseMotionListener( new MouseMotionAdapter() {

	   public void mouseMoved(MouseEvent e) {

	       Dimension size = getSize();

	       label.setText( "" + e.getX() + ", " + e.getY() );

	   }

       } );

       rot.addActionListener( new ActionListener() {

	  public void actionPerformed(ActionEvent e) {

	       start += 10;

	       arc = getArc();

	       repaint();

	  }
       });

       incAngle.addActionListener( new ActionListener() {

	  public void actionPerformed(ActionEvent e) {

	       extent += 10;

	       arc = getArc();

	       repaint();

	  }
       });

       decAngle.addActionListener( new ActionListener() {

	  public void actionPerformed(ActionEvent e) {

	       extent -= 10;

	       arc = getArc();

	       repaint();

	  }
       });

  }

  public void paint(Graphics g) {

       Rectangle2D arcBounds = arc.getBounds2D();

       double ax = arcBounds.getX(), ay = arcBounds.getY(),
	      aw = arcBounds.getWidth(), ah = arcBounds.getHeight();

       Rectangle2D rect = new Rectangle2D.Double( ax, ay - 4, aw, ah + 8);


       Area exclusiveArea = new Area( rect );
       exclusiveArea.exclusiveOr( new Area( arc ) );

       Area arcArea = new Area( rect );

       arcArea.subtract( exclusiveArea );

       Path newArcPath = new Path( arcArea );

       Path [] twoPath = newArcPath.getPathsSepratedByVerticalBeam();


       Graphics2D g2 = (Graphics2D) g;

       g2.draw( arc );
       g2.setColor( Color.blue );
       g2.draw( rect );

       g2.translate( w, h );
       g2.setColor( Color.red );
//       g2.draw(  twoPath[ 0 ].getGeneralPath() );

       g2.setColor( Color.green );

//       Shape s = ThreeDimUtilities.getBanner( twoPath[0].getGeneralPath(), 30.0F );
//
//       g2.draw( s );

       Shape [] shapes = twoPath[ 0 ].getElligentBanner( 30.0F );

       for(int i = 0, len = shapes.length; i < len; i ++ ) {

	   g2.draw( shapes[ i ] );

       }

//       shapes = twoPath[1].getBanner( 30 );
//
//       for(int i = 0, len = shapes.length; i < len; i ++ ) {
//
//           g2.draw( shapes[ i ] );
//
//       }

       g2.setColor( Color.orange );

//       g2.draw( twoPath[ 1 ].getGeneralPath() );

       g2.translate( -w, - h );

  }

  public void describeShape(Shape shape, String msg) {

      System.out.println( msg );

      Rectangle2D bounds = shape.getBounds2D();

      double x = bounds.getX();
      double w = bounds.getWidth();

      System.out.println( "Edge : " + x + ", " + ( x + w ) );

      PathIterator pi = shape.getPathIterator( null );

      while( ! pi.isDone() ) {

	   describePathIterator( pi );

	   pi.next();

      }

  }

  public void describePathIterator(PathIterator pi ) {

       double [] coords = new double[ 6 ];

       int type = pi.currentSegment( coords );

       switch( type ) {

	  case PathIterator.SEG_MOVETO :
	       print( "MOVE_TO", coords );
	       break;

	  case PathIterator.SEG_LINETO :
	       print( "LINE_TO", coords );
	       break;

	  case PathIterator.SEG_QUADTO :
	       print( "QUAD_TO", coords );
	       break;

	  case PathIterator.SEG_CUBICTO :
	       print( "CUBIC_TO", coords );
	       break;

	  case PathIterator.SEG_CLOSE :
	       print( "CLOSE", coords );
	       break;

       }

  }

  public void print(String type, double coords [] ) {
	 String msg = type + " : ";

	 for(int i = 0; i < 6; i ++ ) {

	     msg += coords[i] + ", " ;

	 }

	 System.out.println( msg );

  }

  public static void main(String[] args) {

    Frame f = new Frame();
    f.setSize( 500, 500 );
    f.setLayout( new BorderLayout( 1, 1) );

    BannerMaker BannerMaker = new BannerMaker();
    f.add( BannerMaker, BorderLayout.CENTER );
    f.setVisible( true );

  }

}