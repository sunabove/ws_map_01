package jchart;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.awt.*;
import java.awt.geom.*;

public class FromToChartElement extends ChartElement implements MultipleChild, ParentSelectable, LinearChartElement {

  private String toolTip;

  public FromToChartElement( SpreadSheet sheet, ChartElement parent, Point2D from, Point2D to, ShapeStyle style,
				 String toolTip ) {

	super( sheet, parent, new Line2D.Double( from, to ), style );

	this.toolTip = toolTip;

  }

  public ChartElement cloneSelf( ChartElement parent, SpreadSheet sheet ) {

    Shape shape = AffineTransform.getTranslateInstance( 0, 0 ).createTransformedShape( getShape() );

    FromToChartElement ce = new FromToChartElement( sheet, parent, null, null, getShapeStyle().create(), toolTip );

    ce.setShape( shape );

    return ce;

  }

  public String toString() {

    return toolTip;

  }

}