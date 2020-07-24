package test;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.awt.*;
import java.awt.geom.*;

public class ComplexArea extends Panel {

  public ComplexArea() {

  }

  public void paint( Graphics g ) {

    super.paint( g );

    Shape arc1 = new Ellipse2D.Double( 10, 10, 30, 30 );

    Graphics2D g2 = (Graphics2D) g;

//    paint( g2, arc1, Color.black );

    Shape arc2 = new Ellipse2D.Double( 50, 50, 20, 20 );

//    paint( g2, arc2, Color.red );

    Shape rect = new Rectangle2D.Double( 5, 5, 100, 100 );

//    paint( g2, rect, Color.blue );

    Area rectArea = new Area( rect );

    Area arcArea = new Area( arc1 );
    arcArea.add( new Area(arc2) );

    rectArea.subtract( arcArea );

    paint( g2, rectArea, Color.cyan );

  }

  public void paint(Graphics2D g2, Shape shape, Color color ) {

    g2.setColor( color );

    g2.fill( shape );

  }

  public static void main(String [] args) {

    Frame f = new Frame();

    Panel p = new ComplexArea();

    f.setLayout( new BorderLayout() );

    f.add( BorderLayout.CENTER, p );

    f.setSize( 300, 300 );

    f.setVisible( true );

  }

}