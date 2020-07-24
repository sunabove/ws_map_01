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

public class ChartTypeBubble extends ChartType2D implements FeedbackChartType {

  public ChartTypeBubble() {
  }

  public void setPictureExtent(PictureExtent pictureExtent) {

     // adds horizontal grid lines
     GridLineGroup glg = new GridLineGroup( pictureExtent.getSheet(), pictureExtent, pictureExtent.getArea(), null, AppRegistry.MAJOR_GRID_LINE_OF_VALUE_AXIS );

     ChartElement ge = pictureExtent.getParent(); // graph extent
     ValueAxis valueAxis = (ValueAxis) ge.getChartElement( ValueAxis.class );

     Point [] points = valueAxis.getMajorTickMarkPoints();

     Rectangle area = pictureExtent.getArea();

     int x0 = area.x, w = area.width, x1 = x0 + w;

     SpreadSheet sheet = pictureExtent.getSheet();

     for(int i = 0, len = points.length; i < len; i ++ ) {
	 int y = points[i].y;

	 ShapeStyle style = new ShapeStyle(Color.green, Color.green );

	 GridLine gl = new GridLine( sheet, glg, x0, y, x1, y, style );

	 glg.addChild( gl );
     }

     pictureExtent.addChild( glg );

     // end of adding horizontal grid lines

     // adds data series views

     DataSeries [] ds = getValidDataSeries( pictureExtent.getChart() );

     for(int i = 0, len = ds.length ; i < len ; i ++ ) {

	DataSeriesView dsv = new DataSeriesView( sheet, pictureExtent, null, null, this, ds[i] );

	pictureExtent.addChild( dsv );

     }

     // end of adding data series views
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

      DataSeries [] dss = cd.getDataSeries(); // data series

      int dssLen = dss.length;  // data series length

      // 최대 버블 사이즈 조사

      double maxBubbleSize = 0;

      int offIndex = dssLen%2;

      for( int i = 1 - offIndex; i < dssLen ; i += 2 ) {

	DataElement des [] = dss[ i ].getDataElements();

	for( int k = 0, kLen = des.length; k < kLen; k ++ ) {

	  maxBubbleSize = ( maxBubbleSize > des[k].getValue() ) ? maxBubbleSize : des[ k ].getValue();

	}

      }

      Utility.debug(this, "MAX BUBBLE SIZE = " + maxBubbleSize );

      // 끝. 최대 버블 사이즈 조사

      int bubbleIndex = oci + 1 - offIndex;

      DataSeries bubbleDs =  dss[ bubbleIndex ];

      DataElement [] bubbleDes = bubbleDs.getDataElements(); // bubble size data elements

      Rectangle paArea = pa.getArea();

      int pax = paArea.x, pay = paArea.y, paw = paArea.width, pah = paArea.height;

      int baseY = pay + pah; // data element view left botton vertical location ( y coordinate )

      DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

      int len = devs.length; // data element view number

      double dw = ( paw + 0.0 )/( len );

      double bubbleSizeRatio = pa.bubbleSizeRatio/100.0;
      boolean showNegativeBubble = pa.showNegativeBubble;
      boolean areaBubbleSize = pa.areaBubbleSize;

      for(int i = 0; i < len; i ++ ) {

	  DataElementView dev = devs[ i ] ; // data element view

	  int cx = pax + (int)( dw*(i + 0.5) );

	  int cy = baseY - (int)(pah*( dev.getDataElement().getValue() - min )/( max - min ) );

	  if( offIndex == 1 && oci == 0 ) {

	    cy = baseY;

	  }

	  double bubbleSize = bubbleDes[i].getValue();

	  Color color = getChartColor( oci ); // DataElementView color

	  if( bubbleSize < 0 ) {

	    if( showNegativeBubble ) {

	      bubbleSize = Math.abs( bubbleSize );

	      color = getChartColor( dssLen + oci );

	    } else {

	      bubbleSize = 0;

	    }

	  }

	  double radius = dw*0.9*bubbleSizeRatio/2.0;

	  if( areaBubbleSize ) {

	    radius *= ( (bubbleSize/maxBubbleSize)*(bubbleSize/maxBubbleSize) );

	  } else {

	    radius *= bubbleSize/maxBubbleSize;

	  }

	  ShapeStyle style = new ShapeStyle( color, Color.black ); // DataElementView Shape Style

	  dev.setShapeStyle( style );

	  Shape arc = new Ellipse2D.Double( cx - radius, cy - radius, radius*2, radius*2 );

	  dev.setShape( arc );

      }

   }

   public DataSeries [] getValidDataSeries(Chart chart) {

      ChartData cd = chart.getChartData();

      DataSeries [] dss = cd.getDataSeries();

      int len = ( dss.length + 1 )/2;

      DataSeries [] validDs = new DataSeries[ len ];

      int offIndex = dss.length%2;

      int i = 0;

      if( offIndex == 1 ) {

	 // data series of data element zero length

	 validDs[ i ] = dss[ 0 ];

	 i ++;

      }

      for(  ; i < len; i ++ ) {

	 validDs[i] = dss[ i * 2 + ( offIndex == 0 ? 0 : - offIndex ) ];

      }

      return validDs;

   }

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

     int pw = area.width/len;  // points width
     int cenX, cenY = area.y + area.height/2;

     for(int i = 0; i < len; i ++ ) {
	  String value = "" + ( i + 1) ;
	  cenX = area.x + (int)( pw*( i + 0.5) );
	  points[i] = new PointedObject( cenX, cenY, value );
     }

     return points;
  }

}