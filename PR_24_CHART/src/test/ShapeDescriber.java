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
import jcosmos.*;

public class ShapeDescriber extends Panel {

  double x = 250, y = 250, r = 200;

  double start = 30, extent = 120;

  private Label label = new Label( "coords" );
  private Button rot = new Button( "Rot" );
  private Button incAngle = new Button( "+" );
  private Button decAngle = new Button( "-" );

  private Shape getShape() {

//    Shape shape = new Rectangle2D.Double( x - r, y -r, r, r );

    Shape shape = new Arc2D.Double( x - r, y -r, 2*r, 2*r, 0, extent, Arc2D.PIE );

    double theta = Unit.convertDegreeToRadian( start );

    AffineTransform at = AffineTransform.getRotateInstance( theta, x, y );

//    return at.createTransformedShape( shape );

    return new Rectangle2D.Double( 50, 50, 100, 100 );

  }

  public ShapeDescriber() {

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

  public void paint(Graphics g) {

       Graphics2D g2 = (Graphics2D) g;

       Shape shape = getShape();

       g2.setColor( getForeground() );

       g2.draw( shape );

       g2.setColor( Color.blue );

       describeShape( g2, shape );

  }

  public void describeShape( Graphics2D g2, Shape shape ) {

      Path path = new Path( shape );

      PathElement [] elems = path.getPathElements();

      for(int i = 0, len = elems.length - 1; i < len; i ++ ) {

	float [] p = elems[i].getPoint();

	int r = 2;

	int x = (int) p[0], y = (int) p[1];

	g2.drawRect( x - r, y -r, 2*r, 2*r );

	g2.drawString( "[ " + i + " ] " + x +", " + y, x + 10, y + 10 );

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

    ShapeDescriber ShapeDescriber = new ShapeDescriber();
    f.add( ShapeDescriber, BorderLayout.CENTER );
    f.setVisible( true );

  }

}