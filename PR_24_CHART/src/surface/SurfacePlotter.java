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
import com.jcosmos.java3d.*;

public class SurfacePlotter {

  private Surface surface;

  public SurfacePlotter( Surface surface ) {

    this.surface = surface;

  }

  public void paint(Graphics2D g2, Rectangle2D area) {

    Color [] colors = {

      Color.red,
      Color.gray,
      Color.blue,

      Color.yellow,
      Color.green,
      Color.pink,

      Color.cyan,
      Color.magenta,
      Color.lightGray,

      Color.orange,

    };

    double ax = area.getX();
    double ay = area.getY();

    double aw = area.getWidth();
    double ah = area.getHeight();

    Surface surface = this.surface;

    SurfaceCell cellArray [][] = surface.getSurfaceCells( ax, ay, aw, ah );

    for( int i = 0, iLen = cellArray.length; i < iLen; i ++ ) {

      SurfaceCell cellRow [] = cellArray[ i ];

      for( int k = 0, kLen = cellRow.length; k < kLen; k ++ ) {

	SurfaceCell cell = cellRow[ k ];

	cell.paint( g2, colors, surface.min, surface.max, surface.level );

      }

    }

  }

}