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

public class ChartTypeBubble3DEffect extends ChartTypeBubble {

   public ChartTypeBubble3DEffect() {
   }

   public void setShapeAndStyle(DataSeriesView dataSeriesView) {

        super.setShapeAndStyle( dataSeriesView );

        // data element views that is contained in data series view
	DataElementView devs [] = dataSeriesView.getDataElementViews();

	for(int i = 0, len = devs.length; i < len; i ++ ) {

	    DataElementView dev = devs[ i ];

	    ShapeStyle style = ShapeStyle.getDefaultShapeStyle();

	    FillEffect fe = new FillEffect();

	    fe.setFirstGradientColor( Color.white );
	    fe.setSecondGradientColor( Color.blue );
	    fe.setCyclic( true );
	    fe.setSymmetric( true );
	    fe.setType( FillEffect.ROUND );

	    Shape shape = dev.getShape();

	    fe.setBounds( shape.getBounds2D() );

	    style.setFillEffect( fe );

	    dev.setShapeStyle( style );

	}

   }

}