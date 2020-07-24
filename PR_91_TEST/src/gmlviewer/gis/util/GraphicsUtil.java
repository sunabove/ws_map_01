package gmlviewer.gis.util;

import java.awt.*;

public class GraphicsUtil {

  public static void setHighQuality( final Graphics2D g2 ) {

      g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

  }

  public static Color getComplemetaryColor(Color c) {
    // Į�� ���� ���� ���� ���� �ش��ϴ� Į�� ������ �Ǿ�����.
    return new Color( ~ c.getRGB() );
  }

}
