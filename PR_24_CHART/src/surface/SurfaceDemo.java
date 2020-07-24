package surface;

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

public class SurfaceDemo extends Canvas {

  double [][] data	=
	       {
		   { 50, 100, 100, 45 },
		   { 50, 38.6, 40, 30 },
		   { 20, 27.4, 90, 40 }

	       };

//  double [][] data	=
//	       {
//		   { 200, 100, 10 },
//		   { 200, 70, 10 },
//		   {  50, 40, 5 }
//
//	       };

//   double [][] data	=
//	       {
//		   { 200, 2 },
//		   { 1, 1 }
//
//	       };

  Surface surface = new Surface( data, 0, 100, 5 );

  public SurfaceDemo() {
  }

  public void paint(Graphics g) {

    Graphics2D g2 = (Graphics2D) g;

    SurfacePlotter surfPlotter = new SurfacePlotter( surface );

    Dimension size = getSize();

    Rectangle2D area = new Rectangle2D.Double( 10, 10, size.width - 20, size.height - 20 );

    surfPlotter.paint( g2, area );

  }

  public static void main(String [] args) {

    Frame f = new Frame();

    f.setLayout( new BorderLayout() );

    SurfaceDemo demo = new SurfaceDemo();

    f.add( demo, BorderLayout.CENTER );

    f.setSize( 300, 400 );

    f.setVisible( true );

  }

}