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

public class DropLineView extends ChartElement implements MultipleChild {

  public DropLineView( Point2D [] minPoints, Point2D [] maxPoints,
		       SpreadSheet sheet, ChartElement parent, ShapeStyle style) {

    super( sheet, parent, null, style );

    if( minPoints == null ) {

      return;

    }

    for( int i = 0, len = minPoints.length ; i < len; i ++ ) {

      addChild( new FromToChartElement( sheet, this, minPoints[i], maxPoints[i], style, "ÇÏ°­¼±" ) );

    }

  }

  public ChartElement cloneSelf( ChartElement parent, SpreadSheet sheet ) {

    return new DropLineView( null, null, sheet, parent, getShapeStyle().create() );

  }

}