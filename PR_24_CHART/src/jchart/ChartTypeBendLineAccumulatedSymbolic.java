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

public class ChartTypeBendLineAccumulatedSymbolic extends ChartTypeBendLineAccumulated implements FeedbackChartType {

  public ChartTypeBendLineAccumulatedSymbolic() {
  }

  /**
    * set shape and shape style of data series view
    */

   public void setShapeAndStyle(DataSeriesView dataSeriesView) {

      Chart chart = dataSeriesView.getChart();
      PictureExtent pa = chart.getPictureExtent();

      ChartData cd = chart.getChartData();  // cahrt data

      DataSeries [] ds = cd.getDataSeries(); // data series

      int dsLen = ds.length;

      double accuMin = cd.getAxisMinValue();  // accumulated minimum value
      double accuMax = accuMin;  // accumulated maximum value

      int bvTypeNum = pa.getDataSeriesViewNumberOfChartType( getClass() ); // binded vertical graph type data series view number

      int oci = pa.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

      Utility.debug( this, "bvTypeNum = " + bvTypeNum + ", occur order = " + oci );

      DataSeries [] referenceSeries = cd.getReferenceSeries();

      for( int i = 0, refLen = referenceSeries.length; i < refLen ; i ++ ) {

          DataElement [] refDes = referenceSeries[ i ].getDataElements(); // reference data elements

          double lmax = 0;

          for( int k = 0, len = refDes.length; k < len; k ++ ) {

              lmax += refDes[k].getValue();

          }

          accuMax = ( accuMax > lmax ) ? accuMax : lmax ;

      }

      Color color = getChartColor( oci ); // DataElementView color

      ShapeStyle style = new ShapeStyle( color, color ); // DataElementView Shape Style

      Rectangle paArea = pa.getArea();

      int pax = paArea.x, pay = paArea.y, paw = paArea.width, pah = paArea.height;

      int baseY = pay + pah; // data element view left botton vertical location ( y coordinate )

      DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

      int len = devs.length; // data element view number

      double dw = ( paw + 0.0 )/( len );

      for(int i = 0; i < len - 1 ; i ++ ) {

          DataElementView currDev = devs[ i ] ; // data element view

          currDev.setShapeStyle( style );

          double currVal = 0;

          DataElement [] currRefDes = referenceSeries[ i ].getDataElements(); // current reference data elements

          for( int k = 0; k < oci + 1 ; k ++ ) {

               currVal += currRefDes[k].getValue();

          }

          double nextVal = 0;

          DataElement [] nextRefDes = referenceSeries[ i + 1 ].getDataElements(); // next reference data elements

          for( int k = 0; k < oci + 1 ; k ++ ) {

               nextVal += nextRefDes[k].getValue();

          }

          double currY = baseY - pah*( currVal - accuMin )/( accuMax - accuMin );

          double nextY = baseY - pah*( nextVal - accuMin )/( accuMax - accuMin );

          double currX = pax + dw*(i + 0.5);

          double nextX = pax + dw*(i + 1.5);

          Shape shape = new Line2D.Double( currX, currY, nextX, nextY );

          currDev.setShape( shape );

          if( i == 0 ) {

             Shape currSymbolShape = getDataElementIcon( oci, (int) currX, (int) currY );

             SymbolicChartElement currSymbol = new SymbolicChartElement( currDev.getSheet(), currDev, currSymbolShape, style );

             currDev.addChild( currSymbol );


          } else {

             Shape currSymbolShape = getDataElementIcon( oci, (int) currX, (int) currY );

             SymbolicChartElement currSymbol = new SymbolicChartElement( currDev.getSheet(), currDev, currSymbolShape, style );

             Shape nextSymbolShape = getDataElementIcon( oci, (int) nextX, (int) nextY );

             SymbolicChartElement nextSymbol = new SymbolicChartElement( currDev.getSheet(), currDev, nextSymbolShape, style );

             currDev.addChild( currSymbol );

             currDev.addChild( nextSymbol );

          }
      }

   }

}