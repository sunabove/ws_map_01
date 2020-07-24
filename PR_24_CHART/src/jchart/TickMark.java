package jchart;

/**
 * Title:        java chart project
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 * @description     ChartElement Сп
 *
 */

import java.awt.*;
import java.awt.geom.*;


public class TickMark extends ChartElement implements ParentSelectable, LinearChartElement, UnMovable, UnSelectable, MultipleChild {

  public TickMark(SpreadSheet sheet, ChartElement parent, int x0, int y0, int x1, int y1, ShapeStyle style) {
      super(sheet, parent, new Line2D.Double(x0, y0, x1, y1), style );
  }

  public ChartElement cloneSelf(ChartElement parent, SpreadSheet sheet) {

      ShapeAndStyle sns = cloneShapeAndShapeStyle();

      ChartElement element = new TickMark( sheet, parent, 0, 0, 0, 0, sns.getStyle() );

      element.setShape( sns.getShape() );

      return element;
  }

  protected Shape [][] createShapes3DGroup(float tx, float ty, Shape shape, float proX, float proY) {

       ChartType ct = getChartType();

       if( ct instanceof HorizontalChartType ) {

           return ThreeDimUtilities.get3DVerticalLinearInsideShapesGroup( shape.getBounds2D(), proX, proY );
       }

       return ThreeDimUtilities.get3DHorizontalLinearInsideShapesGroup( shape.getBounds2D(), proX, proY );

   }



}