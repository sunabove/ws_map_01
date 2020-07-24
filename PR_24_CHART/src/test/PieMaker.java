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
import java.io.*;

public class PieMaker extends Panel {

  double x = 250, y = 250, r = 200;

  double start = 30, extent = 120;

  private Label label = new Label( "coords" );
  private Button rot = new Button( "Rot" );
  private Button incAngle = new Button( "+" );
  private Button decAngle = new Button( "-" );

  private Shape getDonut() {

    return ShapeUtilities.getDonut( x, y, r, r*0.9, start, extent );

  }

  public PieMaker() {

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

	       start %= 360;

	       repaint();

	  }
       });

       incAngle.addActionListener( new ActionListener() {

	  public void actionPerformed(ActionEvent e) {

	       extent += 10;

	       extent %= 360;

	       repaint();

	  }
       });

       decAngle.addActionListener( new ActionListener() {

	  public void actionPerformed(ActionEvent e) {

	       extent -= 10;

	       extent %= 360;

	       repaint();

	  }
       });

  }

//  public void repaint() {
//
//    super.repaint();
//
//    Dimension size = getSize();
//
//    Image image = createImage( size.width, size.height );
//
//    paint( image.getGraphics() );
//
//    try {
//
//      FileOutputStream out = new FileOutputStream( new File( "C:\\aaa.gif" ) );
//
//      GIFOutputStream.writeGIF( out, image, GIFOutputStream.STANDARD_256_COLORS, Color.white );
//
//    } catch (Exception e) {
//
//      e.printStackTrace();
//
//    }
//
//  }

  public void paint(Graphics g) {

    Graphics2D g2 = (Graphics2D) g;

    Shape donut = getDonut();

    paintShape( g2, donut );

    Point2D [] ctrlPoints = ShapeUtilities.getControlPointsOfDonut( donut );

    g2.setColor( Color.red );

    for( int i = 0, len = ctrlPoints.length; i < len; i ++ ) {

      Point2D point = ctrlPoints[ i ] ;

      g2.fill( new Rectangle2D.Double( point.getX(), point.getY(), 4, 4 ) );

    }

  }

  private void paintShape(Graphics2D g2, Shape arc ) {


       g2.setColor( getForeground() );

       g2.draw( arc );

       g2.setColor( Color.red );

//       describeShape( g2, arc );

  }

  public void describeShape( Graphics2D g2, Shape shape ) {

      Path path = new Path( shape );

      PathElement [] elems = path.getPathElements();

      for(int i = 0, len = elems.length; i < len; i ++ ) {

	PathElement elem = elems[ i ];

	float [] p = elem.getPoint();

	int r = 2;

	int x = (int) p[0], y = (int) p[1];

	int type = elem.type;

	String typeText = "";

	switch( type ) {

	  case PathIterator.SEG_MOVETO:

		typeText = "MO";
		break;

	  case PathIterator.SEG_CUBICTO:

		typeText = "QU";
		break;

	  case PathIterator.SEG_LINETO:

		typeText = "LI";
		break;

	  case PathIterator.SEG_QUADTO:

		typeText = "QU";
		break;

	  case PathIterator.SEG_CLOSE:
		typeText = "CL";
		break;

	}

	g2.drawRect( x - r, y -r, 2*r, 2*r );

	g2.drawString( "[ " + i + ", " + typeText + " ] " + x +", " + y, x + 10, y + 10 );

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

    PieMaker PieMaker = new PieMaker();
    f.add( PieMaker, BorderLayout.CENTER );
    f.setVisible( true );

  }

}