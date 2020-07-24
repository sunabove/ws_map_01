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
import com.jcosmos.java3d.*;

public class GeneralCurveTest extends Panel {

  double x = 250, y = 250, r = 200;

  double start = 30, extent = 120;

  private Label label = new Label( "coords" );
  private Button rot = new Button( "Rot" );
  private Button incAngle = new Button( "+" );
  private Button decAngle = new Button( "-" );

  private Shape getShape() {

//    Shape shape = new Arc2D.Double( x - r, y -r, 2*r, 2*r, 0, extent, Arc2D.PIE );

    Shape shape = new Ellipse2D.Double( x - r, y -r, 2*r, 2*r );

    double theta = Unit.convertDegreeToRadian( start );

    AffineTransform at = AffineTransform.getRotateInstance( theta, x, y );

    return at.createTransformedShape( shape );

  }

  public GeneralCurveTest() {

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

       GeneralCurve gc = new GeneralCurve( shape );

       GeneralPath gp = gc.getGeneralPath();

       g2.translate( r/2, r/2 );

       g2.draw( gp );

       g2.translate( - r/2, -r/2 );

  }

  public static void main(String[] args) {

    Frame f = new Frame();
    f.setSize( 500, 500 );
    f.setLayout( new BorderLayout( 1, 1) );

    GeneralCurveTest GeneralCurveTest = new GeneralCurveTest();
    f.add( GeneralCurveTest, BorderLayout.CENTER );
    f.setVisible( true );

  }

}