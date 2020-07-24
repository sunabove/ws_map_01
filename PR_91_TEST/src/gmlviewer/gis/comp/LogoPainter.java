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

     // 이미지 영역 설정

     int iw = size.width - insets.left - insets.right;
     int ih = size.height - insets.top - insets.bottom;
     int ix = insets.left;
     int iy = insets.top;

     // 끝. 이미지 영역 설정

     // 로딩 로고 경계선 그리기

     Area borderShape = new Area( new Rectangle( 0, 0, size.width, size.height ) );
     borderShape.subtract( new Area( new Rectangle( ix, iy, iw, ih ) ) );

     g.setColor( GmlViewerRegistry.LOADING_BORDER_COLOR );

     g.fill( borderShape );

     // 끝. 로딩 로고 경계선 그리기

     // 로딩 로고 이미지 그리기

     g.drawImage( loadingImage, ix, iy, iw, ih, comp );

     // 끝. 로딩 로고 이미지 그리기.

     // 잠시 동안 로딩 로고 만을 보여 준다. (고객의 여유을 위해서)
     if( duration > 0 ) {
        try {
          Thread.currentThread().sleep(duration);
        }
        catch (InterruptedException ex) {
          debug.debug( ex );
        }
     }
     // 끝. 로딩 로고 출력 유지 하기.

  }

  /**
   * 로딩 로고 이미지
   */

  public static Image loadingImage = Resource.getResourceImage( GmlViewerRegistry.GEN_RES_DIR, GmlViewerRegistry.LOADING_LOGO_FILE );

  /**
   * 로딩 텍스트
   */

}
