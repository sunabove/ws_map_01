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

public class ChartTypeCircle extends ChartType2D implements CircularChartType, FixedDimensionRatioChartType, FeedbackChartType, PercentedChartType {

	public ChartTypeCircle() {
	}

	public void setGraphExtent(GraphExtent graphExtent){

		SpreadSheet sheet = graphExtent.getSpreadSheet();

		Rectangle area = graphExtent.getArea(); // graph area

		int x = area.x, y = area.y, w = area.width, h = area.height; // area coordinates

		int distance = (w <= h ) ? w : h ;  	// picture area creation

		Rectangle pictureRect = new Rectangle( x, y, distance, distance);

		graphExtent.setShape( (Shape) ( pictureRect.clone() ) );

		ShapeStyle pictureStyle = ShapeStyle.getDefaultShapeStyle();
		pictureStyle.setFillColor( getPictureExtentBackground() );

		PictureExtent pictureExtent = new PictureExtent( sheet, graphExtent, pictureRect, pictureStyle );

		graphExtent.addChild( pictureExtent );

		graphExtent.synchronizeToPictureExtentShape();

		// end of picture area creation
	}

	public void setPictureExtent(PictureExtent pictureExtent) {

		// adds data series views

		SpreadSheet sheet = pictureExtent.getSheet();

		ChartData cd = pictureExtent.getChart().getChartData();

		DataSeries [] ds = cd.getDataSeries();

		//     for(int i = ds.length - 1; i > -1; i --) {
		for(int i = ds.length - 1; i == ds.length -1; i --) {  // 단, 하나의 DSV만 삽입한다.

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

		Point pal = pa.getLocation(); // paint area location

		DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

		int len = devs.length; // data element view number

		double x = pal.getX(), y = pal.getY(); // paint area location x and y

		double w = pa.getWidth(), h = pa.getHeight(); // paint area width and height

		double sum = dataSeriesView.getDataSeries().getAbsoluteSum(); // data series absolute sum value

		double start = 90; // last degree angle

		int type = Arc2D.PIE; // arc type

		for(int i = 0; i < len; i ++ ) {

			DataElementView dev = devs[ i ] ; // data element view

			double data = Math.abs( dev.getDataElement().getValue() ); // data element value

			double ratio = data/sum; // ratio to absolute summation

			double extent = 360*ratio; // angle of data element

			Color color = getChartColor( i ); // DataElementView color

			//Utility.debug(this, "DataElementView Color = " + color + ", start = " + start + ", extent = " + extent);

			ShapeStyle style = new ShapeStyle( color, Color.black ); // DataElementView Shape Style

			dev.setShapeStyle( style );

			Arc2D arc = new Arc2D.Double( x, y, w, h, start - extent, extent, type );

			dev.setShape( arc );

			start -= extent;

		}

	}

	public Rectangle [] getSelectedMarks(DataElementView dataElementView) {

		return dataElementView.getSelectedMarksLineType();

	}

	public int getFeedbackTopology() {

	    return 0;

	}

  public double get3DThetaRadian() {

       return 0;

   }

   public double [] get3DObjectCompareKey(ChartElement elem, float rotRadian, float proY) {

      Rectangle2D bounds = elem.getShape().getBounds2D();

      return new double[] { - bounds.getX(), - bounds.getY() };

  }

  /**
   * scatter pies by distance d
   * @param currentRadius : current Radius
   * @param d : distance
   */

  public void scatterPiesBy( final double currentRadius, DataSeriesView dataSeriesView, final double d) {

      // data element views that is contained in data series view

      DataElementView devs [] = dataSeriesView.getDataElementViews();

      for(int i = 0, len = devs.length; i < len; i ++ ) {

	scatterPieBy( currentRadius, devs[ i ], d );

      }

  }

  public void scatterPieBy( final double currentRadius, DataElementView dev, final double d ) {

	  Shape pie = dev.getShape();

	  Point2D ctrlPnts [] = ShapeUtilities.getPieControlPoints( pie );

	  /**
	   *       p
	   *       / `
	   *   v  /    \
	   *     /    /  q
	   *    /  /  w
	   *   //
	   *  center
	   *
	   */

	  Point2D center = ctrlPnts[0];

	  Point2D p = ctrlPnts[1], q = ctrlPnts[2];

	  double vx = p.getX() - center.getX();
	  double vy = p.getY() - center.getY();
	  double wx = q.getX() - center.getX();
	  double wy = q.getY() - center.getY();

	  double ux = (vx + wx)/2.0;
	  double uy = (vy + wy)/2.0;

	  // ux, uy 검정

	  double cux = center.getX() + ux/2.0;
	  double cuy = center.getY() + uy/2.0;

	  if( ! pie.contains( cux, cuy ) ) {

	    ux = - ux;
	    uy = - uy;

	  }

	  // 끝. ux, uy 검정

	  double phi = jcosmos.Utility.getRadian( ux, uy );

	  AffineTransform at = AffineTransform.getTranslateInstance( center.getX(), center.getY() );
	  at.rotate( phi );
	  double scale = currentRadius/(currentRadius + d + 0.0);
	  at.scale( scale, scale );
	  at.translate( d, 0 );
	  at.rotate( - phi );
	  at.translate( - center.getX(), - center.getY() );

	  dev.setShape( at.createTransformedShape( pie ) );

  }

}