package gmlviewer.gis.viewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import gmlviewer.gis.comp.*;

public class PanButton extends JButton implements SenseComp {

  public PanButton() {
      super( " " ); // ��ư�� ũ�⸦ ������ �����ϱ� ���ؼ� ��ư �ؽ�Ʈ�� " " �� ������.
		    // �����δ� ������ ����
  }

   public String [] getToolTipTexts() {
     return nineDirTexts;
  }

  /**
   * ��ư ��ü�� 9����Ѵ�.
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

  // ������Ʈ ����
  private Insets insets = new Insets( 4, 4, 4, 4 );

  // ���콺 ������ ����
  private SenseMouseListener sensitiveMouseListener = new SenseMouseListener( this );

  // ��ȩ ������ ��Ÿ���� ����
  private final static String[] nineDirTexts  = { "NW", "N", "NE", "W", "", "E", "SW", "S", "SE" };

}
