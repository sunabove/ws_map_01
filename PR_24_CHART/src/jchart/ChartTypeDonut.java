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
import java.util.*;
import jcosmos.*;
import com.jcosmos.java3d.*;

public class ChartTypeDonut extends ChartTypeCircle implements CircularChartType, FixedDimensionRatioChartType, FeedbackChartType{

  public ChartTypeDonut() {
  }

  public void setPictureExtent(PictureExtent pictureExtent) {

		// adds data series views

		SpreadSheet sheet = pictureExtent.getSheet();

		ChartData cd = pictureExtent.getChart().getChartData();

		DataSeries [] ds = cd.getDataSeries();

		for(int i = 0, len = ds.length; i < len; i ++ ) {

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

      DataSeries [] dss = cd.getDataSeries();

      int dsLen = dss.length;

      Point pal = pa.getLocation(); // paint area location

      int oci = pa.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

      DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

      int len = devs.length; // data element view number

      double x = pal.getX(), y = pal.getY(); // paint area location x and y
      double w = pa.getWidth(), h = pa.getHeight(); // paint area width and height

      double donutInnerRadiusRatio = pa.donutInnerRadiusRatio/100.0; // 도넛 내부 반경 비율

      double outerRadius = w/2.0;
      double innerRadius = outerRadius*donutInnerRadiusRatio;

      double cx = x + w/2.0;
      double cy = y + h/2.0;

      double sum = dataSeriesView.getDataSeries().getAbsoluteSum(); // data series absolute sum value

      double start = 90; // last degree angle

      for(int i = 0; i < len; i ++ ) {

	  DataElementView dev = devs[ i ] ; // data element view

	  double data = Math.abs( dev.getDataElement().getValue() ); // data element value

	  double ratio = data/sum; // ratio to absolute summation

	  double extent = 360*ratio; // angle of data element

	  Color color = getChartColor( i ); // DataElementView color

	  //Utility.debug(this, "DataElementView Color = " + color + ", start = " + start + ", extent = " + extent);

	  ShapeStyle style = new ShapeStyle( color, Color.black ); // DataElementView Shape Style

	  dev.setShapeStyle( style );

	  double r0 = innerRadius + (outerRadius - innerRadius)*(oci + 1)/(dsLen + 0.0);
	  double r1 = innerRadius + (outerRadius - innerRadius)*(oci)/(dsLen + 0.0);

	  Shape donut = ShapeUtilities.getDonut( cx, cy, r0, r1, start - extent, extent );

	  dev.setShape( donut  );

	  start -= extent;

      }

   }

   public Rectangle [] getSelectedMarks(DataElementView dataElementView) {

       Shape donut = dataElementView.getShape();

       if( donut == null ) {

	  return null;

       }

       return ChartElement.getSelectedMarksLineType( donut );

   }

   /**
   * scatter pies by distance d
   * @param currentRadius : current Radius
   * @param d : distance
   */

  public void scatterPiesBy(double currentRadius, DataSeriesView dataSeriesView, double d) {

      // data element views that is contained in data series view
      DataElementView devs [] = dataSeriesView.getDataElementViews();

      for(int i = 0, len = devs.length; i < len; i ++ ) {

	  scatterPieBy( currentRadius, devs[ i ], d );

      }

  }

  public void scatterPieBy( final double currentRadius, DataElementView dev, final double d ) {

	  Utility.debug(this, "SCATTERING A DONUT ......" );

	  Shape donut = dev.getShape();

	  Point2D ctrlPnts [] = ShapeUtilities.getControlPointsOfDonut( donut );

	  /**
	   *         _____------____
	   *       /   __________    \
	   *      /   /          \    \
	   *     /   /     u      \    \
	   *    q   r             s    p
	   *      a                  b
	   *            v      w
	   *
	   */

	  Point2D p = ctrlPnts[ 0 ];
	  Point2D q = ctrlPnts[ 1 ];
	  Point2D r = ctrlPnts[ 2 ];
	  Point2D s = ctrlPnts[ 3 ];

	  Point2D a = new Point2D.Double( ( q.getX() + r.getX() )/2.0, ( q.getY() + r.getY() )/2.0 );
	  Point2D b = new Point2D.Double( ( p.getX() + s.getX() )/2.0, ( p.getY() + s.getY() )/2.0 );

	  Point2D center = null;

	  if( center == null ) {

	    PictureExtent pe = dev.getChart().getPictureExtent();

	    Rectangle area = pe.getArea();

	    center = new Point2D.Double( area.x + area.width/2.0, area.y + area.height/2.0 );

	  }

	  Point2D v = VectorUtilities.getVectorFromTo( center, a );
	  Point2D w = VectorUtilities.getVectorFromTo( center, b );

	  double ux = ( v.getX() + w.getX() )/2.0;
	  double uy = ( v.getY() + w.getY() )/2.0;

	  // ux, uy 검정

	  double cux = center.getX() + ux/2.0;
	  double cuy = center.getY() + uy/2.0;

	  if( ! donut.contains( cux, cuy ) ) {

	    ux = - ux;
	    uy = - uy;

	  }

	  // 끝. ux, uy 검정

	  double phi = jcosmos.Utility.getRadian( ux, uy );

	  AffineTransform at = AffineTransform.getTranslateInstance( center.getX(), center.getY() );
	  at.rotate( phi );

//	  double scale = currentRadius/(currentRadius + d + 0.0);
//
//	  at.scale( scale, scale );
	  at.translate( d, 0 );
	  at.rotate( - phi );
	  at.translate( - center.getX(), - center.getY() );

	  dev.setShape( at.createTransformedShape( donut ) );

  }

//  private Point2D getCenterOfDonut(Point2D p, Point2D q, Point2D r, Point2D s ) {
//
//    double px = p.getX(), py = p.getY(), qx = q.getX(), qy = q.getY(),
//	   rx = r.getX(), ry = r.getY(), sx = s.getX(), sy = s.getY();
//
//    double bunMo = 2*( ( qy - py )*(rx - sx ) + ( px - qx )*( ry - sy ) );
//
//    if( bunMo == 0 ) {
//
//      return null;
//
//    }
//
//    double xBunJa = py*py*(ry - sy) + (px*px - qx*qx)*(ry - sy) + qy*qy*( - ry + sy ) +
//		    qy*(rx*rx + ry*ry - sx*sx - sy*sy) + py*(- rx*rx - ry*ry + sx*sx + sy*sy );
//    double yBunJa = - (px*px + py*py - qx*qx - qy*qy)*(rx - sx) +
//		    (px - qx)*(rx*rx + ry*ry - sx*sx - sy*sy);
//
//    return new Point2D.Double( xBunJa/bunMo, yBunJa/bunMo );
//
//  }

}