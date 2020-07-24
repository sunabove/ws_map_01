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

public class ChartTypeRadiation extends ChartType2D implements FixedDimensionRatioChartType {

  public ChartTypeRadiation() {
  }

  public Shape createGraphExtentShape(Chart chart) {

      Rectangle area = chart.getArea();  // chart area

      int x = area.x, y = area.y, w = area.width, h = area.height;

      x = (int)(x + w*chart.graphExtentLeftMarginRatio);
      y = (int)(y + w*chart.graphExtentTopMarginRatio);
      w = (int)(w*chart.graphExtentWidthRatio);
      h = (int)(h*chart.graphExtentHeightRatio);

      if( this instanceof FixedDimensionRatioChartType ) {
	  int smallLen = ( w < h ) ? w : h;

	  x += ( w - smallLen )/2;
	  y += ( h - smallLen )/2;

	  w = h = smallLen;
      }

      int cx = x + w/2;
      int cy = y + h/2;

      ChartData cd = chart.getChartData();  // cahrt data

      String [] refNames = cd.getReferenceNames(); // reference names

      int len = refNames.length; // reference names length

      double radian = 2.0*Math.PI/len;

      int [] xpoints = new int[ len ];
      int [] ypoints = new int[ len ];

      Point2D topPoint = new Point2D.Double( 0, - h/2 );

      for( int i = 0; i < len; i ++ ) {

	  AffineTransform at = AffineTransform.getRotateInstance( radian*i );

	  Point2D p = at.transform( topPoint, null );

	  xpoints[i] = (int)(cx + p.getX());
	  ypoints[i] = (int)(cy + p.getY());
      }

      Polygon poly = new Polygon( xpoints, ypoints, len );

      Utility.debug(this, "RADIATION GRAPH EXTENT = " + poly );

      return poly;

  }

  public Shape createPictureExtentShape(GraphExtent graphExtent, Rectangle area) {

      int x = area.x, y = area.y, w = area.width, h = area.height;

      if( this instanceof FixedDimensionRatioChartType ) {

	  int smallLen = ( w < h ) ? w : h;

	  x += ( w - smallLen )/2;
	  y += ( h - smallLen )/2;

	  w = h = smallLen;

      }

      int cx = x + w/2;
      int cy = y + h/2;

      ChartData cd = graphExtent.getChart().getChartData();  // cahrt data

      String [] refNames = cd.getReferenceNames(); // reference names

      int len = refNames.length; // reference names length

      double radian = 2.0*Math.PI/len;

      int [] xpoints = new int[ len ];
      int [] ypoints = new int[ len ];

      Point2D topPoint = new Point2D.Double( 0, - h/2 );

      for( int i = 0; i < len; i ++ ) {

	  AffineTransform at = AffineTransform.getRotateInstance( radian*i );

	  Point2D p = at.transform( topPoint, null );

	  xpoints[i] = (int)(cx + p.getX());
	  ypoints[i] = (int)(cy + p.getY());
      }

      Polygon poly = new Polygon( xpoints, ypoints, len );

      Utility.debug(this, "RADIATION PICTURE EXTENT = " + poly );

      return poly;

  }

  public void setGraphExtent(GraphExtent graphExtent){

     SpreadSheet sheet = graphExtent.getSpreadSheet();

     graphExtent.setShapeStyle( null );

     Rectangle area = graphExtent.getArea(); // graph area

     int x = area.x, y = area.y, w = area.width, h = area.height; // area coordinates

     int margin =(int)(w*AppRegistry.ITEM_AXIS_HEIGHT_RATIO); // x-axis height

     // picture area creation

     Rectangle pictureExtentRect = new Rectangle( x + margin/2 + 1, y + margin/2 + 1
					    , w - margin - 1, h - margin -1);

     Shape pictureExtentShape = createPictureExtentShape( graphExtent, pictureExtentRect );

     ShapeStyle pictureExtentStyle = ShapeStyle.getDefaultShapeStyle();

     pictureExtentStyle.setFillColor( getPictureExtentBackground() );

     PictureExtent pictureExtent = new PictureExtent( sheet, graphExtent, pictureExtentShape, pictureExtentStyle );

     graphExtent.addChild( pictureExtent );

     // end of picture area creation

     Font font = FontManager.getDefaultFont(); // font for x axis text and y axis text

     // x-axis creation

     ShapeStyle itemAxisStyle = ShapeStyle.getDefaultShapeStyle();

     ItemAxis itemAxis = new ItemAxis( sheet, graphExtent, null, itemAxisStyle );

     graphExtent.addChild( itemAxis );

     // end of x-axis creation

     // y-axis creation

     int px = pictureExtentRect.x, py = pictureExtentRect.y,
	 pw = pictureExtentRect.width, ph = pictureExtentRect.height;

     Rectangle valueAxisRect = new Rectangle(px + pw/2 - margin, py, margin, ph/2 );

     ShapeStyle valueAxisStyle = ShapeStyle.getDefaultShapeStyle();

     ValueAxis valueAxis = new ValueAxis( sheet, graphExtent, valueAxisRect, valueAxisStyle );

     graphExtent.addChild( valueAxis );

     // end of x-axis creation

  }

   public void setPictureExtent(PictureExtent pictureExtent) {

     // adds horizontal grid lines

     GridLineGroup glg = new GridLineGroup( pictureExtent.getSheet(), pictureExtent, pictureExtent.getArea(), null, AppRegistry.MAJOR_GRID_LINE_OF_VALUE_AXIS );

     ChartElement ge = pictureExtent.getParent(); // graph extent

     Chart chart = pictureExtent.getChart(); // chart
     ChartData cd = chart.getChartData();  // cahrt data
     String [] refNames = cd.getReferenceNames(); // reference names

     double min = cd.getAxisMinValue();  // minimum value
     double max = cd.getAxisMaxValue();  // maximum value

     Point pel = pictureExtent.getLocation(); // paint extent location

     int pex = pel.x; // paint extent x coordinate
     int pey = pel.y; // parint extent y coordinate
     int pew = pictureExtent.getWidth(); // paint extent width
     int peh = pictureExtent.getHeight(); // paint extent height

     int cx = pex + pew/2; // paint extent center x coordinate
     int cy = pey + peh/2; // paint extent center y coordinate

     int refNamesLen = refNames.length; // reference names length

     double radian = 2.0*Math.PI/refNamesLen;

     SpreadSheet sheet = pictureExtent.getSheet();

     int divNum = getNumberOfGridMarkOfValueAxis( cd, pictureExtent ) + 1;

     double dh = (peh/2.0 + 0.0)/(divNum + 0.0);

     for(int i = 0; i < divNum; i ++ ) {

	for(int k = 0; k < refNamesLen; k ++ ) {

	   double currX = 0;

	   double currY = ( i < divNum -1 ) ? - dh*(i + 1) : - peh/2.0;

	   double nextX = 0;

	   double nextY = ( i < divNum -1 ) ? - dh*(i + 1) : - peh/2.0;

	   AffineTransform at = AffineTransform.getRotateInstance( radian*( k ) ); // affine transform

	   Point2D currPoint = at.transform( new Point2D.Double( currX, currY ), null );

	   at = AffineTransform.getRotateInstance( radian*( k + 1 ) );

	   Point2D nextPoint = at.transform( new Point2D.Double( nextX, nextY ), null );

	   currX = currPoint.getX();
	   currY = currPoint.getY();

	   nextX = nextPoint.getX();
	   nextY = nextPoint.getY();

	   currX += cx;
	   currY += cy;

	   nextX += cx;
	   nextY += cy;

	   ShapeStyle style = new ShapeStyle(Color.green, Color.green );

	   GridLine gl = new GridLine( sheet, glg, (int) currX, (int) currY, (int) nextX, (int) nextY, style );

	   glg.addChild( gl );

	   if( i == divNum - 1 ) {

	       gl = new GridLine( sheet, glg, cx, cy, (int) currX, (int) currY, style );

	       glg.addChild( gl );

	   }

	}

     }

     pictureExtent.addChild( glg );

     // end of adding horizontal grid lines

     // adds data series views

     DataSeries [] ds = cd.getDataSeries();  // data series

     for(int i = 0, len = ds.length; i < len; i ++) {
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
      PictureExtent pe = chart.getPictureExtent(); // pictureExtent

      ChartData cd = chart.getChartData();  // cahrt data

      double min = cd.getAxisMinValue();  // minimum value
      double max = cd.getAxisMaxValue();  // maximum value

      int bvTypeNum = pe.getDataSeriesViewNumberOfChartType( getClass() ); // binded vertical graph type data series view number

      int oci = pe.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

      Utility.debug( this, "bvTypeNum = " + bvTypeNum + ", occur order = " + oci );

      Color color = getChartColor( oci ); // DataElementView color

      ShapeStyle style = new ShapeStyle( color, color ); // DataElementView Shape Style

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

	  currDev.setShapeStyle( style );
	  currDev.setShape( line );

      }
   }

   public Rectangle [] getSelectedMarks(DataElementView dataElementView) {
      return dataElementView.getSelectedMarksLineType();
   }

   public int getNumberOfGridMarkOfValueAxis(ChartData cd, PictureExtent pictureExtent ) {
      return 1;
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

     PictureExtent pe = (PictureExtent) chart.getChartElement( PictureExtent.class );

     Point [] vertexes = ShapeUtilities.getVertexes( pe.getShape() );

//     GraphExtent ge = (GraphExtent) chart.getChartElement( GraphExtent.class );
//
//     Point [] vertexes = ge.getVertexes( ge.getShape() );

     for(int i = 0; i < len; i ++ ) {

	  String value = refNames[ i ];

	  Point vertex = vertexes[i];

	  Utility.debug(this, "VERTEX = " + vertex );

	  points[i] = new PointedObject( vertex.x, vertex.y, value );

     }

     return points;

  }
}