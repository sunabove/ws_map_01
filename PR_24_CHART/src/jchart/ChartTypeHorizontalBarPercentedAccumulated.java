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

public class ChartTypeHorizontalBarPercentedAccumulated extends ChartTypeHorizontalBarBinded implements FeedbackChartType {

  public ChartTypeHorizontalBarPercentedAccumulated() {
  }

  public Shape createGraphExtentShape(Chart chart) {

      chart.getChartData().setDivisionNumber( 9 );

      return super.createGraphExtentShape( chart );

   }

   /**
    * set shape and shape style of data series view
    */
   public void setShapeAndStyle(DataSeriesView dataSeriesView) {

      Chart chart = dataSeriesView.getChart();
      PictureExtent pa = chart.getPictureExtent();

      ChartData cd = chart.getChartData();  // cahrt data

      int bvTypeNum = pa.getDataSeriesViewNumberOfChartType( getClass() ); // binded vertical graph type data series view number

      int oci = pa.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

      Utility.debug( this, "bvTypeNum = " + bvTypeNum + ", occur order = " + oci );

      Color color = getChartColor( oci ); // DataElementView color

      ShapeStyle style = new ShapeStyle( color, Color.black ); // DataElementView Shape Style

      Rectangle paArea = pa.getArea();

      int pax = paArea.x, pay = paArea.y, paw = paArea.width, pah = paArea.height;

      DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

      int len = devs.length; // data element view number

      double dh = ( pah + 0.0 )/( len );

      DataSeries [] referenceSeries = cd.getReferenceSeries();

      for(int i = 0; i < len; i ++ ) {

          DataElementView dev = devs[ i ] ; // data element view

          dev.setShapeStyle( style );

          double currVal = dev.getDataElement().getValue();

          double baseVal = 0;

          DataSeries refSeries = referenceSeries[ i ];

          DataElement [] refDes = refSeries.getDataElements(); // reference data elements

          double sum = refSeries.getAbsoluteSum();

          for( int k = 0; k < oci; k ++ ) {

               baseVal += refDes[k].getValue();

          }

          double accuW = paw*baseVal/sum;

          double currW = paw*currVal/sum;

          double cy = pay + dh*(i + 0.5);

          double currH = dh/3.0;

          Shape shape = new Rectangle2D.Double( pax + accuW, cy - currH/2.0, currW, currH );

          dev.setShape( shape );
      }

   }

   public PointedObject [] getValueAxisMainGridPoints(ValueAxis valueAxis) {

     Chart chart = valueAxis.getChart();  // chart

     ChartData cd = chart.getChartData();  // chart data

     double min = 0;
     double max = 100;

     Rectangle area = valueAxis.getArea(); // area rectangle

     int ax = area.x, ay = area.y, aw = area.width, ah = area.height;

     int divNum = cd.getDivisionNumber();
//     int divNum = cd.getDivisionNumber();

     double dx = aw /(divNum + 1.0);   // vertical graphical gap

     double dv = (max - min)/(divNum + 1.0);   // vertical valude gap

     PointedObject [] points = new PointedObject[ divNum + 2 ];

     double x , y = ay + ah/2.0, val;

     int len = points.length; // points num

     for( int i = 0; i < len ; i ++ ) {

        x = ax + i*dx;
        val = min + i*dv;

        if( i == len -1 ) {
           val = max;
        }

        points[i] = new PointedObject((int)(x), (int)(y), new Double( val ) );

     }

     return points;
  }

  public String getValueAxisUnit() {

     return "%";

  }

}