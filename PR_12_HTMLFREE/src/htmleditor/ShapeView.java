package htmleditor;

/**
 * Title:        HTML FREE EDITOR
 * Description:  Html Free Editor v2.0
 * Copyright:    Copyright (c) 2001
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;

public class ShapeView extends ImageView {

  public ShapeView( ShapeElement shapeElement ) {

    super( shapeElement );

  }

  public void paint(Graphics2D g2, int x, int y) {

     final ShapeElement shapeElement = (ShapeElement) getImageElement();

     shapeElement.paint( g2 );

     if( isSelected() ) {

	ImageView.SEL_IMG_VIEW = this;

     }

  }

}