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

public class ChartTypeDistributionSplinedSymbolic extends ChartTypeDistribution implements LinearChartType, FeedbackChartType {

  public ChartTypeDistributionSplinedSymbolic() {
  }

  /**
    * set shape and shape style of data series view
    */
   public void setShapeAndStyle(DataSeriesView dataSeriesView) {
      Chart chart = dataSeriesView.getChart();
      PictureExtent pa = chart.getPictureExtent();

      ChartData cd = chart.getChartData();  // cahrt data

      double min = cd.getAxisMinValue();  // minimum value
      double max = cd.getAxisMaxValue();  // maximum value

      int bvTypeNum = pa.getDataSeriesViewNumberOfChartType( getClass() ); // binded vertical graph type data series view number

      int oci = pa.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

      Utility.debug( this, "bvTypeNum = " + bvTypeNum + ", occur order = " + oci );

      Color color = getChartColor( oci ); // DataElementView color

      ShapeStyle style = new ShapeStyle( color, color ); // DataElementView Shape Style

      Rectangle paArea = pa.getArea();

      int pax = paArea.x, pay = paArea.y, paw = paArea.width, pah = paArea.height;

      int baseY = pay + pah; // data element view left botton vertical location ( y coordinate )

      DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

      int len = devs.length; // data element view number

      double dw = ( paw + 0.0 )/( len + 1);

      double xpoints [] = new double[ len ];
      double ypoints [] = new double[ len ];

      for(int i = 0; i < len; i ++ ) {

          DataElementView currDev = devs[ i ] ; // current data element view

          double currX = pax + dw*(i + 1);

          double currY = baseY - pah*(currDev.getDataElement().getValue() - min)/(max - min);

          Shape icon = getDataElementIcon( oci, (int) currX, (int) currY );

          xpoints[ i ] = currX;
          ypoints[ i ] = currY;

          currDev.setShapeStyle( style );

          currDev.setShape( icon );
      }

      Spline spline = new Spline( xpoints, ypoints, paw, pah );

      Shape splineShape = spline.getSplineShape();

      dataSeriesView.setShape( splineShape );

      dataSeriesView.setShapeStyle( style );

   }

}