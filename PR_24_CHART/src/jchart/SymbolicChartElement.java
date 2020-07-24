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

public class SymbolicChartElement extends ChartElement implements MultipleChild, UnSelectable, UnMovable, UnResizable  {

  public SymbolicChartElement(SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style) {

      super(sheet, parent, shape, style );

  }

  public ChartElement cloneSelf(ChartElement parent, SpreadSheet sheet) {

     ShapeAndStyle sns = cloneShapeAndShapeStyle();

     SymbolicChartElement element = new SymbolicChartElement( sheet, parent, sns.getShape(), sns.getStyle() );

     return element;

  }

  public String toString() {

      return "";

  }

}