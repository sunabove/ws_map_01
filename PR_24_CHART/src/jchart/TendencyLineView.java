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

public class TendencyLineView extends ChartElement implements MultipleChild, LinearChartElement, UnResizable, UnMovable {

  public TendencyLineView (SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style) {

    super( sheet, parent, shape, style );

  }

  public ChartElement cloneSelf( ChartElement parent, SpreadSheet sheet ) {

    Shape shape = AffineTransform.getTranslateInstance( 0, 0 ).createTransformedShape( getShape() );

    return new TendencyLineView( sheet, parent, shape, getShapeStyle().create() );

  }

  public String toString() {

    return "Ãß¼¼¼±";

  }

  public void paintSelf(Graphics2D g2) {

		if( isPaintable() ) {

			shapeStyle.drawShape( g2, shape );

			if( selected ) {

				paintSelected( g2 );

			}

		}

 }

}