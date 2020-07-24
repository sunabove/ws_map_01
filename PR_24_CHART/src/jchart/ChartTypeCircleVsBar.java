package jchart;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.awt.*;
import java.awt.geom.*;
import jcosmos.*;

public class ChartTypeCircleVsBar extends ChartTypeCircleVsCircle {

	public ChartTypeCircleVsBar() {
	}

	public void makeSecondPie( double startAngle, DataElementView [] devs, double sum, Rectangle2D area, boolean useDifferentColor ) {

	     double x = area.getX(), y = area.getY(), w = area.getWidth(), h = area.getHeight();

	     for( int i = 0, len = devs.length; i < len; i ++ ) {

		 DataElementView dev = devs[ i ];

		 double dy = h * ( Math.abs ( dev.getDataElement().getValue() )/sum );

		 Color color = useDifferentColor ? getChartColor( i ) : Color.white ; // DataElementView color

		 ShapeStyle style = new ShapeStyle( color, Color.black ); // DataElementView Shape Style

		 dev.setShapeStyle( style );

		 Shape rect = new Rectangle2D.Double( x, y, w, dy );

		 dev.setShape( rect );

		 y += dy;

	     }

	}

	public void makeSeriesLines( DataElementView dev, double auxAngle,
				      Rectangle2D firstArea, Rectangle2D secondArea ) {

	      double px, py, qx, qy, rx, ry, sx, sy;

	      // 계열선 포인트 정의
	      //       p -_
	      //            ` q
	      //       r -_
	      //            ` t
	      //

	      double fax = firstArea.getX(), fay = firstArea.getY(),
		     faw = firstArea.getWidth(), fah = firstArea.getHeight();

	      if( auxAngle > 180 ) {

		  px = fax + faw/2.0;
		  py = fay;

		  rx = fax + faw/2.0;
		  ry = fay + fah;

	      } else {

		  double v = faw/2.0;

		  double theta = Unit.convertDegreeToRadian( auxAngle/2.0 );

		  double vx = v*Math.cos( theta );
		  double vy = v*Math.sin( theta );

		  px = fax + faw/2.0 + vx;
		  py = fay + fah/2.0 - vy;

		  rx = px;
		  ry = fay + fah/2.0 + vy;

	      }

	      double sax = secondArea.getX(), say = secondArea.getY(),
		     saw = secondArea.getWidth(), sah = secondArea.getHeight();

	      qx = sax;
	      qy = say;

	      sx = sax;
	      sy = say + sah;

	      GeneralPath seriesLine = new GeneralPath();

	      seriesLine.moveTo( (float) px, (float) py );
	      seriesLine.lineTo( (float) qx, (float) qy );

	      seriesLine.moveTo( (float) rx, (float) ry );
	      seriesLine.lineTo( (float) sx, (float) sy );

	      dev.setShape( seriesLine );
	      dev.setShapeStyle( ShapeStyle.getDefaultShapeStyle() );

	}

}