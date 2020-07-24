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

public class ChartTypeHorizontalBarBinded extends ChartType2D implements HorizontalChartType, FeedbackChartType{

  public ChartTypeHorizontalBarBinded() {
  }

  public void setGraphExtent(GraphExtent graphExtent){

     SpreadSheet sheet = graphExtent.getSpreadSheet();

     Rectangle area = graphExtent.getArea(); // graph area

     int x = area.x, y = area.y, w = area.width, h = area.height; // area coordinates

     int yaw = (int)(h*AppRegistry.VAULE_AXIS_WIDTH_RATIO); // y-axis width
     int xah =(int)(w*AppRegistry.ITEM_AXIS_HEIGHT_RATIO); // x-axis height

     Font font = FontManager.getDefaultFont(); // font for x axis text and y axis text

     // y-axis creation

     Rectangle itemAxisRect = new Rectangle(x, y, yaw, h - xah);

     ShapeStyle itemAxisStyle = ShapeStyle.getDefaultShapeStyle();

     ItemAxis itemAxis = new ItemAxis( sheet, graphExtent, itemAxisRect, itemAxisStyle );

     graphExtent.addChild( itemAxis );

     // end of y-axis creation

     // x-axis creation

     Rectangle valueAxisRect = new Rectangle(x + yaw, y + h - xah, w - yaw, xah );

     ShapeStyle valueAxisStyle = ShapeStyle.getDefaultShapeStyle();

     ValueAxis valueAxis = new ValueAxis( sheet, graphExtent, valueAxisRect, valueAxisStyle );

     graphExtent.addChild( valueAxis );

     // end of x-axis creation

     // picture area creation

     Rectangle pictureRect = new Rectangle( x + yaw + 1, y, w - yaw - 1, h - xah -1);

     ShapeStyle pictureStyle = ShapeStyle.getDefaultShapeStyle();

     pictureStyle.setFillColor( getPictureExtentBackground() );

     PictureExtent pictureExtent = new PictureExtent( sheet, graphExtent, pictureRect, pictureStyle );

     graphExtent.addChild( pictureExtent );

     // end of picture area creation
  }

    public void setPictureExtent(PictureExtent pictureExtent) {

	// adds horizontal grid lines
	GridLineGroup glg = new GridLineGroup( pictureExtent.getSheet(), pictureExtent, pictureExtent.getArea(), null, AppRegistry.MAJOR_GRID_LINE_OF_VALUE_AXIS );

	ChartElement ge = pictureExtent.getParent(); // graph extent
	ValueAxis valueAxis = (ValueAxis) ge.getChartElement( ValueAxis.class );
		ValueAxisOption vao = ( ValueAxisOption ) valueAxis.getAxisOption();

	Chart chart = pictureExtent.getChart();

	ChartData cd = chart.getChartData();

	Rectangle area = pictureExtent.getArea();

	int x0 = area.x, y0 = area.y, h = area.height, y1 = y0 + h, w = area.width;

	SpreadSheet sheet = pictureExtent.getSheet();

	double min = cd.getMinValue();
	double max = cd.getMaxValue();

	calibrateAxisMinMaxValueAndUnit( vao, min, max );

	min = vao.getMinScale();
	max = vao.getMaxScale();

	double unit = vao.getMajorUnit();

	int divNum = getNumberOfGridMarkOfValueAxis( min, max, unit ) + 1;

	int dx = w/divNum;

	for(int i = 0, len = divNum + 1; i < len; i ++ ) {

			int x = x0 + dx*i;

			x = ( i < len  - 1 ) ? x : x0 + w;

			ShapeStyle style = new ShapeStyle( null, Color.black );

			GridLine gl = new GridLine( sheet, glg, x, y0, x, y1, style );

			glg.addChild( gl );

	}

	pictureExtent.addChild( glg );

	// end of adding horizontal grid lines

	// adds data series views

	DataSeries [] ds = cd.getDataSeries();

	for(int i = 0, len = ds.length; i < len; i ++) {

			DataSeriesView dsv = new DataSeriesView( sheet, pictureExtent, null, null, this, ds[i] );

			pictureExtent.addChild( dsv );

	}

	// end of adding data series views

	DataSeriesView [] dsvs = pictureExtent.getDataSeriesViews();

	     if( dsvs.length < 1 ) {

	       return;

	     }

	     TendencyLineView tlv;

	     tlv = TendencyLineViewFactory.getLinearTendencyLineView(
				      pictureExtent.getArea(), null,
				      dsvs[0].getDataSeries(), pictureExtent, false
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
				      false
				    );

		 pictureExtent.addChild( tlv );

	     }

	     tlv = TendencyLineViewFactory.getLogTendencyLineView(
				      pictureExtent.getArea(), null,
				      dsvs[0].getDataSeries(), pictureExtent, false
				    );

	     pictureExtent.addChild( tlv );

	     tlv = TendencyLineViewFactory.getExponentialTendencyLineView(
				      pictureExtent.getArea(), null,
				      dsvs[0].getDataSeries(), pictureExtent,
				      false
				    );

	     pictureExtent.addChild( tlv );

	     tlv = TendencyLineViewFactory.getRadicalRootTendencyLineView(
				      pictureExtent.getArea(), null,
				      dsvs[0].getDataSeries(), pictureExtent,
				      false
				    );

	     pictureExtent.addChild( tlv );

    }

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

		DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

		final int rsNum = devs.length; // reference series number == data element view number

		double or = pa.overlapRatio/100.0; // overlap ratio
		double ghr = pa.gapWidthRatio/100.0; // gap height ratio
		double h = pah/(dsNum*rsNum + ghr*rsNum - or*(dsNum -1)*rsNum); // bar width
		double gapHeight = h*ghr;

		Utility.debug(this, "OCI = " + oci );

		for(int i = 0; i < rsNum; i ++ ) {

			DataElementView dev = devs[ i ] ; // data element view

			dev.setShapeStyle( style );

			double y = pay + gapHeight*(i + 0.5) +
				  ( h*dsNum - h*or*(dsNum - 1) )*i +
				  h*oci - h*or*( oci );

			double w = pah*(dev.getDataElement().getValue() - min)/(max - min);

			Rectangle rect = new Rectangle( pax, (int) y, (int) w, (int) h );

			dev.setShape( rect );

		}

	}

   /**
   * returns main grid points for horizontal chart type
   */
  public PointedObject [] getItemAxisMainGridPoints(ItemAxis itemAxis) {

     Chart chart = itemAxis.getChart();

     ChartData cd = chart.getChartData();  // cahrt data

     String [] refNames = cd.getReferenceNames(); // reference names

     int len = refNames.length;

     PointedObject [] points = new PointedObject[ len ];

     Rectangle area = itemAxis.getArea();

     int ph = area.height/len;  // points width
     int cenY, cenX = area.x + area.width/2;

     for(int i = 0; i < len; i ++ ) {
	  String value = refNames[ i ];
	  cenY = area.y + (int)( ph*( i + 0.5) );
	  points[i] = new PointedObject( cenX, cenY, value );
     }

     return points;
  }

    public PointedObject [] getValueAxisMainGridPoints(ValueAxis valueAxis) {

	Chart chart = valueAxis.getChart();  // chart

		ValueAxisOption vao = chart.getValueAxisOption();

	ChartData cd = chart.getChartData();  // chart data

	double min = cd.getAxisMinValue();
	double max = cd.getAxisMaxValue();

	calibrateAxisMinMaxValueAndUnit( vao, min, max );

	min = vao.getMinScale();
	max = vao.getMaxScale();

	double unit = vao.getMajorUnit();

	Rectangle area = valueAxis.getArea(); // area rectangle

	int ax = area.x, ay = area.y, aw = area.width, ah = area.height;

	int divNum = getNumberOfGridMarkOfValueAxis( min, max, unit );

	double dx = aw /(divNum + 1.0);   // vertical graphical gap

	double dv = (max - min)/(divNum + 1.0);   // vertical valude gap

	PointedObject [] points = new PointedObject[ divNum + 2 ];

	double x, y = ay + ah/2, val;

	int len = points.length; // points num

	for( int i = 0; i < len ; i ++ ) {
		x = ax + i*dx;
		    val = min + i*dv;
		points[i] = new PointedObject((int)(x), (int)(y), new Double( val ) );
	}

	return points;
  }

}