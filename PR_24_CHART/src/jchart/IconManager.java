package jchart;

/**
 * Title:        java chart project
 * Description:  jchart v1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
import java.awt.*;
import java.awt.geom.*;

public class IconManager {
      private static int w = 6, h = 6;

      public static Shape getDiaMond(int x, int y) {
          int mx = x - w/2, my = y + h/2;

          GeneralPath diaMond = new GeneralPath(GeneralPath.WIND_EVEN_ODD);

          diaMond.moveTo( w/2 + mx, 0 + my);
          diaMond.lineTo( w + mx, - h/2 + my );
          diaMond.lineTo( w/2 + mx, - h + my );
          diaMond.lineTo( 0 + mx, -h/2 + my );

          diaMond.closePath();

          return diaMond;
      }

      public static Shape getRectangle(int x, int y) {
          Rectangle rect = new Rectangle( x - w/2, y - h/2, w, h );
          return rect;
      }

      public static Shape getTriAngle(int x, int y) {
          GeneralPath triAngle = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
          int mx = x - w/2, my = y + h/2;

          triAngle.moveTo( 0 + mx, 0 + my);
          triAngle.lineTo( w + mx, my);
          triAngle.lineTo( w/2 + mx, - h + my);
          triAngle.closePath();

          return triAngle;
      }
}