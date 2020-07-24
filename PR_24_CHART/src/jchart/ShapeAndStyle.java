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

public class ShapeAndStyle {

  private Shape shape;
  private ShapeStyle style;

  public ShapeAndStyle(Shape shape, ShapeStyle style) {

     this.shape = shape;
     this.style = style;

  }

  public Shape getShape() {

     return shape;

  }

  public ShapeStyle getStyle() {

    return style;

  }

}