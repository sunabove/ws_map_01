package gmlviewer.gis.util;

import java.awt.*;

public class GraphicsUtil {

  public static void setHighQuality( final Graphics2D g2 ) {

      g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

  }

  public static Color getComplemetaryColor(Color c) {
    // 칼라 정수 값의 보수 값에 해당하는 칼라가 보색이 되어진다.
    return new Color( ~ c.getRGB() );
  }

}
