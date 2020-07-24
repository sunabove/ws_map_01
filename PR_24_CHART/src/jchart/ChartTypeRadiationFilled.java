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

public class ChartTypeRadiationFilled extends ChartTypeRadiation {

  public ChartTypeRadiationFilled() {
  }

   /**
    * set shape and shape style of data series view
    */
   public void setShapeAndStyle(DataSeriesView dataSeriesView) {

      Chart chart = dataSeriesView.getChart();

      PictureExtent pe = chart.getPictureExtent(); // pictureExtent

      ChartData cd = chart.getChartData();  // cahrt data

      double min = cd.getAxisMinValue();  // minimum value
      double max = cd.getAxisMaxValue();  // maximum value

      int bvTypeNum = pe.getDataSeriesViewNumberOfChartType( getClass() ); // binded vertical graph type data series view number

      int oci = pe.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

      Utility.debug( this, "bvTypeNum = " + bvTypeNum + ", occur order = " + oci );

      Color color = getChartColor( oci ); // DataElementView color

      ShapeStyle style = new ShapeStyle( color, Color.black ); // DataElementView Shape Style

      Point pel = pe.getLocation(); // picture extent location

      int pex = pel.x; // picture extent x coordinate
      int pey = pel.y; // picture extent y coordinate
      int pew = pe.getWidth(); // picture extent width
      int peh = pe.getHeight(); // picture extent height

      int cx = pex + pew/2; // picture extent center x coordinate
      int cy = pey + peh/2; // picture extent center y coordinate

      DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

      int len = devs.length; // data element view number

      double radian = 2.0*Math.PI/len;

      int xpoints [] = new int[ len ];
      int ypoints [] = new int[ len ];

      for(int i = 0; i < len; i ++ ) {

          DataElementView currDev = devs[ i ] ; // current data element view
          DataElementView nextDev = ( i < len -1 ) ? devs[ i + 1 ] : devs[0]; // next data element view

          double currVal = currDev.getDataElement().getValue();
          double nextVal = nextDev.getDataElement().getValue();

          double currX = 0;

          double currY = - peh/2.0*(currVal - min)/(max - min);

          double nextX = 0;

          double nextY = - peh/2.0*(nextVal -min)/(max - min);

          AffineTransform at = AffineTransform.getRotateInstance( radian*( i ) ); // affine transform

          Point2D currPoint = at.transform( new Point2D.Double( currX, currY ), null );

          at = AffineTransform.getRotateInstance( radian*( i + 1 ) );

          Point2D nextPoint = at.transform( new Point2D.Double( nextX, nextY ), null );

          currX = currPoint.getX();
          currY = currPoint.getY();

          nextX = nextPoint.getX();
          nextY = nextPoint.getY();

          currX += cx;
          currY += cy;

          nextX += cx;
          nextY += cy;

          Line2D line = new Line2D.Double( currX, currY, nextX, nextY );

          currDev.setShapeStyle( null );

          currDev.setShape( line );

          // sets data series view polygon

          xpoints[ i ] = (int) currX;
          ypoints[ i ] = (int) currY;

          // end of setting data series view polygon

      }

      Polygon dsvPoly = new Polygon( xpoints, ypoints, xpoints.length );  // data series view polygon

      dataSeriesView.setShapeStyle( style );
      dataSeriesView.setShape( dsvPoly );

   }

}