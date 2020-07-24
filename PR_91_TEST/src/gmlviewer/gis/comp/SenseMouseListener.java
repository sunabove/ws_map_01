package gmlviewer.gis.comp;

import java.awt.*;
import java.awt.event.*;

public class SenseMouseListener extends MouseAdapter implements MouseMotionListener {

  public SenseMouseListener(SenseComp component) {

     this.component = component;

     Component comp = (Component) component;

     comp.addMouseListener( this );
     comp.addMouseMotionListener( this );

  }

  public void mouseMoved(MouseEvent e) {

     int x = e.getX();
     int y = e.getY();

     Shape [] shapes = component.getSensitiveShapes();

     selShape = null;

     Component comp = (Component) component;

     int shapesLen = shapes.length;

     int preSelShapeId = selShapeId;

     for(int i = 0 ; i < shapesLen; i ++ ) {

	 if( shapes[ i ].contains( x, y ) ) {

	      selShapeId = i;

	      selShape = shapes[ i ];

	      break;

	 }

     }

     if( selShape == null ) {

	return;

     }

     Graphics2D g2 = (Graphics2D) comp.getGraphics();

     // clear previous rectangle

     if( preSelShapeId > -1 && preSelShapeId < shapesLen ) {

	g2.setColor( comp.getForeground() );

	g2.draw( shapes[ preSelShapeId ] );

     }

     // end of clearance previous rectangle

     // draw current rectange

     g2.setColor( selShapeColor );

     g2.draw( selShape );

     // end of drawing current rectangle

     // set tool tip text

     String toolTipText = component.getToolTipTexts()[ selShapeId ];

     if( toolTipText != null && toolTipText.length() > 0 ) {

	 component.setToolTipText( toolTipText );

     }

     // end of setting tool tip text

  }

  public void mouseDragged(MouseEvent e) {

  }

  public void mouseExited(MouseEvent e) {

     selShapeId = -1;

  }

  public Shape getSelectedShape() {

     return selShape;

  }

  public Color getSelectedShapeColor() {

     return selShapeColor;

  }

  public int getSelectedShapeId() {

     return selShapeId;

  }

  private Shape selShape;

  // ���õ� ������ �簢�� ������ �׸� ����
  private Color selShapeColor = Color.white;

  // ���õ� �簢�� ���̵�
  private int selShapeId = -1;

  private SenseComp component;

}
