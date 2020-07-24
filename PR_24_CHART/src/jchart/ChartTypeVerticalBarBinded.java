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
import java.awt.geom.*;
import jcosmos.*;
import java.lang.Double;

public class ChartTypeVerticalBarBinded extends ChartType2D implements FeedbackChartType {

	/**
	 * set shape and shape style of data series view
	 */
	public void setShapeAndStyle(DataSeriesView dataSeriesView) {

		Chart chart = dataSeriesView.getChart();
		ValueAxisOption vao = chart.getValueAxisOption();
		PictureExtent pa = chart.getPictureExtent();

		ChartData cd = chart.getChartData();  // cahrt data

		DataSeries [] ds = cd.getDataSeries(); // data series

		final int dsNum = ds.length; // data series number

		double min = cd.getMinValue();  // minimum value
		double max = cd.getMaxValue();  // maximum value

		calibrateAxisMinMaxValueAndUnit( vao, min, max );

		min = vao.getMinScale();
		max = vao.getMaxScale();

		int oci = pa.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

		Color color = getChartColor( oci ); // DataElementView color

		ShapeStyle style = new ShapeStyle( color, Color.black ); // DataElementView Shape Style

		Rectangle paArea = pa.getArea();

		int pax = paArea.x, pay = paArea.y, paw = paArea.width, pah = paArea.height;

		int y = pay + pah; // data element view left botton vertical location ( y coordinate )

		DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

		final int rsNum = devs.length; // reference series number == data element view number

		double or = pa.overlapRatio/100.0; // overlap ratio
		double gwr = pa.gapWidthRatio/100.0; // gap width ratio
		double w = paw/(dsNum*rsNum + gwr*rsNum - or*(dsNum -1)*rsNum); // bar width
		double gapWidth = w*gwr;

		Utility.debug(this, "OCI = " + oci );

		for(int i = 0; i < rsNum; i ++ ) {

			DataElementView dev = devs[ i ] ; // data element view

			dev.setShapeStyle( style );

			double x = pax + gapWidth*(i + 0.5) +
				  ( w*dsNum - w*or*(dsNum - 1) )*i +
				  w*oci - w*or*( oci );

			double h = pah*(dev.getDataElement().getValue() - min)/(max - min);

			Rectangle rect = new Rectangle( (int)(x), (int)( y - h), (int)w, (int)(h) );

			dev.setShape( rect );

		}

	}

    public void setPictureExtent(PictureExtent pictureExtent) {

       super.setPictureExtent( pictureExtent );

//       if( pictureExtent.isDropLineShow() ) {
//
//	 addDropLineView( pictureExtent );
//
//       }

	     DataSeriesView [] dsvs = pictureExtent.getDataSeriesViews();

	     if( dsvs.length < 1 ) {

	       return;

	     }

	     if( true ) {

	      return;

	     }

	     TendencyLineView tlv;

	     tlv = TendencyLineViewFactory.getLinearTendencyLineView(
				      pictureExtent.getArea(), null,
				      dsvs[0].getDataSeries(), pictureExtent, true
				    );

	     pictureExtent.addChild( tlv );

//	     if( true ) {
//
//		return;
//
//	     }

	     Color colors [] = { Color.cyan, Color.blue, Color.yellow,
				 Color.green, Color.orange, Color.red,
				 Color.pink, Color.magenta, Color.lightGray };

	     for( int i = 3; i < 5; i ++ ) {

		 tlv = TendencyLineViewFactory.getPolynomialTendencyLineView(
				      pictureExtent.getArea(), null,
				      dsvs[0].getDataSeries(), pictureExtent, i, colors[i -1],
				      true
				    );

		 pictureExtent.addChild( tlv );

	     }

	     tlv = TendencyLineViewFactory.getLogTendencyLineView(
				      pictureExtent.getArea(), null,
				      dsvs[0].getDataSeries(), pictureExtent, true
				    );

	     pictureExtent.addChild( tlv );

	     tlv = TendencyLineViewFactory.getExponentialTendencyLineView(
				      pictureExtent.getArea(), null,
				      dsvs[0].getDataSeries(), pictureExtent,
				      true
				    );

	     pictureExtent.addChild( tlv );

	     tlv = TendencyLineViewFactory.getRadicalRootTendencyLineView(
				      pictureExtent.getArea(), null,
				      dsvs[0].getDataSeries(), pictureExtent,
				      true
				    );

	     pictureExtent.addChild( tlv );

    }

}