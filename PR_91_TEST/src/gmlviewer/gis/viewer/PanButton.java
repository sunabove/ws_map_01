package gmlviewer.gis.viewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import gmlviewer.gis.comp.*;

public class PanButton extends JButton implements SenseComp {

  public PanButton() {
      super( " " ); // 버튼의 크기를 적당히 설정하기 위해서 버튼 텍스트를 " " 로 설정함.
		    // 실제로는 보이지 않음
  }

   public String [] getToolTipTexts() {
     return nineDirTexts;
  }

  /**
   * 버튼 전체를 9등분한다.
   */
  public Shape [] getSensitiveShapes() {

      Dimension size = getSize();

      Insets insets = this.insets;

      int dw = (size.width - insets.left - insets.right)/3;
      int dh = (size.height - insets.top - insets.bottom)/3;

      int x = insets.left;
      int y = insets.top;

      Shape rects [] = new Shape[ 9 ];

      for(int i = 0; i < 3 ; i ++ ) { // row

	  for(int k = 0; k < 3 ; k ++ ) { // coloumn

	      rects[ i*3 + k ] = new Rectangle( x + k*dw, y + i*dh, dw, dh );

	  }

      }

      return rects;

  }

  public void paint(Graphics g) {

     super.paint( g );

     Shape [] rects = getSensitiveShapes();

     Graphics2D g2 = (Graphics2D) g;

     Color fg = getForeground();
     Color bg = getBackground();

     String [] dirTexts = this.nineDirTexts;

     int rectsLen = rects.length;

     for(int i = 0; i < rectsLen; i ++ ) {

	 Shape rect = rects[ i ];
	 g2.setColor( bg );
	 g2.fill( rect );
	 g2.setColor( fg );
	 g2.draw( rect );

     }

     Shape selShape = sensitiveMouseListener.getSelectedShape();

     if ( selShape != null ) {

	 g2.setColor( sensitiveMouseListener.getSelectedShapeColor() );
	 g2.draw( selShape );

     }

  }

  public String getActionCommand() {

      int selRectId = sensitiveMouseListener.getSelectedShapeId();

      if( selRectId < 0 ) {

	 return "";

      }

      return nineDirTexts[ selRectId ];

  }

  // 컴포넌트 마진
  private Insets insets = new Insets( 4, 4, 4, 4 );

  // 마우스 리스너 설정
  private SenseMouseListener sensitiveMouseListener = new SenseMouseListener( this );

  // 아홉 방향을 나타내는 문자
  private final static String[] nineDirTexts  = { "NW", "N", "NE", "W", "", "E", "SW", "S", "SE" };

}
