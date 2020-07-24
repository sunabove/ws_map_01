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

public class ChartTypeBendLine extends ChartType2D implements LinearChartType, FeedbackChartType {

  public ChartTypeBendLine() {
  }

  public Color getPictureExtentBackground() {

     return Color.lightGray;

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

	  String value = "" + ( i + 1 );

	  cenX = area.x + (int)( pw*( i + 0.5) );

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

	int oci = pa.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

	Color fillColor = getChartColor( oci ); // DataElementView color

	Color lineColor = ( chart.getChartType() instanceof ChartType3D ) ? Color.black : null;

	ShapeStyle style = new ShapeStyle( fillColor, lineColor); // DataElementView Shape Style

	Point pal = pa.getLocation(); // paint area location
	int pah = pa.getHeight(); // paint area height

	int baseY = pal.y + pah; // data element view left botton vertical location ( y coordinate )

	DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

	int len = devs.length; // data element view number

	double width = pa.getWidth()/(len + 0.0); // DataElementView width

	for(int i = 0; i < len - 1; i ++ ) {

	    DataElementView currDev = devs[ i ] ; // current data element view
	    DataElementView nextDev = devs[ i + 1 ]; // next data element view

	    currDev.setShapeStyle( style );

	    double currX = pal.x + width*( i + 0.5 );

	    double currY = baseY - pah*(currDev.getDataElement().getValue() - min)/(max - min) ;

	    double nextX = pal.x + width*(i + 1.5 );

	    double nextY = baseY - pah*(nextDev.getDataElement().getValue() - min)/(max - min) ;

	    Line2D line = new Line2D.Double( currX, currY, nextX, nextY );

	    currDev.setShape( line );

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

    public void addDropLineView( PictureExtent pictureExtent ) {

	Chart chart = pictureExtent.getChart();

	ValueAxisOption vao = chart.getValueAxisOption();

	ChartData cd = chart.getChartData();  // cahrt data

	double min = cd.getMinValue();  // minimum value
	double max = cd.getMaxValue();  // maximum value

	calibrateAxisMinMaxValueAndUnit( vao, min, max );

	min = vao.getMinScale();
	max = vao.getMaxScale();

	ShapeStyle style = new ShapeStyle( null, Color.black ); // DataElementView Shape Style

	Point pal = pictureExtent.getLocation(); // paint area location
	int pah = pictureExtent.getHeight(); // paint area height

	int baseY = pal.y + pah; // data element view left botton vertical location ( y coordinate )

	double [][] data = convertToArray( cd.getDataSeries() );

	int dsNum = data.length; // data series number

	int colNum= data[ 0 ].length; // reference series number

	double width = pictureExtent.getWidth()/(dsNum + 0.0); // DataElementView width

	double [] minData = new double[ colNum ];
	double [] maxData = new double[ colNum ];

	for( int c = 0; c < colNum; c ++ ) {

	  for( int r = 0; r < dsNum; r ++ ) {

	    double value = data[ r ][ c ];

	    minData[ c ] = ( minData[c] < value ) ? minData[ c ] : value;

	    maxData[ c ] = ( maxData[c] > value ) ? maxData[ c ] : value;

	  }

	}

	Point2D minPoints [] = new Point2D[ colNum ];
	Point2D maxPoints [] = new Point2D[ colNum ];

	for(int i = 0; i < colNum; i ++ ) {

	    double currX = pal.x + width*( i + 0.5 );

	    double minY = baseY -  pah*(minData[i] - min)/(max - min);

	    double maxY = baseY -  pah*(maxData[i] - min)/(max - min);

	    minPoints[ i ] = new Point2D.Double( currX, minY );
	    maxPoints[ i ] = new Point2D.Double( currX, maxY );

	}

	pictureExtent.addChild( new DropLineView( minPoints, maxPoints, pictureExtent.getSpreadSheet(), pictureExtent, style ) );

    }

}