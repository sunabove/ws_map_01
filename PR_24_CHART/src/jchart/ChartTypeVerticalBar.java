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

public abstract class ChartTypeVerticalBar extends ChartType2D implements FeedbackChartType {

       /**
	 * returns main grid points for vertical chart type
	 */
	public PointedObject [] getItemAxisMainGridPoints(ItemAxis itemAxis) {

		Chart chart = itemAxis.getChart();

		ChartData cd = chart.getChartData();  // cahrt data

		String [] refNames = cd.getReferenceNames(); // reference names

		int len = refNames.length;

		PointedObject [] points = new PointedObject[ len ];

		Rectangle area = itemAxis.getArea();

		int pw = (int)( area.width/(len + 0.0) );  // points width
		int cenX, cenY = area.y + area.height/2;

		for(int i = 0; i < len; i ++ ) {

			String value = refNames[ i ];

			cenX = area.x + (int)( pw*( i + 0.5 ) );

			points[i] = new PointedObject( cenX, cenY, value );

		}

		return points;

	}

	/**
	 * set shape and shape style of data series view
	 */
	public void setShapeAndStyle(DataSeriesView dataSeriesView) {

		Chart chart = dataSeriesView.getChart();
		ValueAxisOption vao = chart.getValueAxisOption();
		PictureExtent pa = chart.getPictureExtent();

		ChartData cd = chart.getChartData();  // cahrt data

		double min = cd.getMinValue();  // minimum value
		double max = cd.getMaxValue();  // maximum value

		calibrateAxisMinMaxValueAndUnit( vao, min, max );

		min = vao.getMinScale();
		max = vao.getMaxScale();

		// binded vertical graph type data series view number
		int bvTypeNum = pa.getDataSeriesViewNumberOfChartType( getClass() );

		int oci = pa.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

		Utility.debug( this, "bvTypeNum = " + bvTypeNum + ", occur order = " + oci );

		Color color = getChartColor( oci ); // DataElementView color

		ShapeStyle style = new ShapeStyle( color, Color.black ); // DataElementView Shape Style

		Rectangle paArea = pa.getArea();

		int pax = paArea.x, pay = paArea.y, paw = paArea.width, pah = paArea.height;

		int y = pay + pah; // data element view left botton vertical location ( y coordinate )

		DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

		int len = devs.length; // data element view number

		int w = (int) ( paw /(len + 0.0 ) ); // DataElementView width

		for(int i = 0; i < len; i ++ ) {

			DataElementView dev = devs[ i ] ; // data element view

			dev.setShapeStyle( style );

			int x = (int) (pax + w/3.0 + w*i);

			int h = (int)(pah*(dev.getDataElement().getValue() - min)/(max - min));

			Rectangle2D rect = new Rectangle2D.Double(x, y - h, w/3.0, h );

			dev.setShape( rect );

		}

	}

}