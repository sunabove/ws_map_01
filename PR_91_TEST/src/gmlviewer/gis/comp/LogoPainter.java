package gmlviewer.gis.comp;

import java.awt.*;
import java.awt.geom.*;
import gmlviewer.gis.rsc.*;
import gmlviewer.gis.*;
import gmlviewer.gis.kernel.*;

public class LogoPainter implements DebugInterface {

  public static void paint(Component comp, final long duration) {

     Graphics2D g = (Graphics2D) comp.getGraphics();

     Dimension size = comp.getSize();

     Insets insets = GmlViewerRegistry.LOADING_BORDER_INSETS;

     // �̹��� ���� ����

     int iw = size.width - insets.left - insets.right;
     int ih = size.height - insets.top - insets.bottom;
     int ix = insets.left;
     int iy = insets.top;

     // ��. �̹��� ���� ����

     // �ε� �ΰ� ��輱 �׸���

     Area borderShape = new Area( new Rectangle( 0, 0, size.width, size.height ) );
     borderShape.subtract( new Area( new Rectangle( ix, iy, iw, ih ) ) );

     g.setColor( GmlViewerRegistry.LOADING_BORDER_COLOR );

     g.fill( borderShape );

     // ��. �ε� �ΰ� ��輱 �׸���

     // �ε� �ΰ� �̹��� �׸���

     g.drawImage( loadingImage, ix, iy, iw, ih, comp );

     // ��. �ε� �ΰ� �̹��� �׸���.

     // ��� ���� �ε� �ΰ� ���� ���� �ش�. (���� ������ ���ؼ�)
     if( duration > 0 ) {
        try {
          Thread.currentThread().sleep(duration);
        }
        catch (InterruptedException ex) {
          debug.debug( ex );
        }
     }
     // ��. �ε� �ΰ� ��� ���� �ϱ�.

  }

  /**
   * �ε� �ΰ� �̹���
   */

  public static Image loadingImage = Resource.getResourceImage( GmlViewerRegistry.GEN_RES_DIR, GmlViewerRegistry.LOADING_LOGO_FILE );

  /**
   * �ε� �ؽ�Ʈ
   */

}
