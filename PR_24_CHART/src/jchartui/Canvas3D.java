package jchartui;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;
import java.io.*;
import jcosmos.*;
import jchart.*;
import com.jcosmos.java3d.*;

public class Canvas3D extends JPanel {

   private static final int FIVE_DEGREE = 5;
   private static final int TEN_DEGREE = 10;

   private int phiAngle = 15;
   private int thetaAngle = 20;

   private ChartType chartType = new ChartTypeHorizontalBarBinded3D() ;

   public Canvas3D() {

   }

   public void setChartType(ChartType ct) {

    this.chartType = ct;

   }

   public void setThetaAndPhiAngle( int thetaAngle, int phiAngle) {

      this.thetaAngle = thetaAngle;
      this.phiAngle = phiAngle;

   }

   public void paint(Graphics g) {

      super.paint( g );

      Graphics2D g2 = (Graphics2D) g;

      ChartType ct = this.chartType;

      if( ct instanceof CircularChartType ) {

	paintCircle3D( g2 );

      } else if( ct instanceof HorizontalChartType ) {

	paintHorizontal3D( g2 );

      } else {

	paintVertical3D( g2 );

      }

   }

   private void paintHorizontal3D(Graphics2D g2) {

      Dimension size = getSize();

      double thetaAngle = getThetaAngle();
      double phiAngle = getPhiAngle();

      double zx = size.width/4.0;
      double theta = Unit.convertDegreeToRadian( thetaAngle );
      double phi = Unit.convertDegreeToRadian( phiAngle );

      Utility.debug( this, "ZX = " + zx + ", THETA = " + thetaAngle + ", PHI = " + phiAngle );

      Point2D projectionVector = PictureExtent.getProjectionVector( zx, theta, phi );

      int proX = (int) projectionVector.getX();
      int proY = (int) projectionVector.getY();

      int top = 5 + ( proY < 0 ? Math.abs( proY ) : 0 );
      int bottom = 5 + ( proY < 0 ? 0 : Math.abs( proY ) );

      int left = 5 + ( proX > 0 ? 0 : Math.abs( proX ) );
      int right = 5 + ( proX > 0 ? Math.abs( proX ) : 0 );

      double x = left;
      double y = top;

      double w = size.width - left - right;
      double h = size.height - top - bottom;

      ShapeStyle wireFrameShapeStyle = new ShapeStyle( null, Color.black );

      double mx = proX/4.0;
      double bh = h/4.0;
      double dby = bh/4.0;
      double dbw = w/2.0/3.0;

      double [][] barDatas = {
	{ x, y, w, h },
	{ x + mx, y + dby, w - 3*dbw, bh }, // 첫 번 째 삼차원 bar
	{ x + mx, y + 2*dby + bh, w - 2*dbw, bh },
	{ x + mx, y + 3*dby + 2*bh, w - 3*dbw, bh},
      };

      for( int i = 0, len = barDatas.length; i < len; i ++ ) {

	  double barData [] = barDatas[ i ];

	  Rectangle2D bar2D = new Rectangle2D.Double( barData[0], barData[1], barData[2], barData[3] );;

	  Shape [][] bar3D;

	  if( i == 0 ) {

	    bar3D = ThreeDimUtilities.get3DBarInsideShapesGroup( bar2D, proX, proY );

	  } else {

	    bar3D = ThreeDimUtilities.get3DHorizontalBarShapesGroup( bar2D, (int)(proX*2.0/3.0), (int)(proY*2.0/3.0) );

	  }

	  wireFrameShapeStyle.paint3DShapess( g2, bar3D );

      }

   }

   private void paintCircle3D(Graphics2D g2) {

      Dimension size = getSize();

      double theta = Unit.convertDegreeToRadian( getThetaAngle() );
      double phi = Unit.convertDegreeToRadian( getPhiAngle() );

      int top = 10;
      int bottom = 10;

      int left = 10;
      int right = 10;

      double x = left;
      double y = top;

      double w = size.width - left - right;
      double h = size.height - top - bottom;

      w = ( w < h ) ? w : h ;

      double r = w/2.0*Math.sin( phi );
      double d = w/5.0*Math.cos( phi );

      double cx = size.width/2.0;
      double cy = y + ( h - d )/2.0;

      Shape topEllipse = new Ellipse2D.Double( cx - w/2.0, cy - r, w, 2*r );

      Shape bottomEllipse = new Arc2D.Double( cx - w/2.0, cy -r + d, w, 2*r, 180, 180, Arc2D.OPEN );

      Point2D [] vertexes = ShapeUtilities.getVertexesPoint2D( bottomEllipse );

      Point2D leftEdge = vertexes[ 0 ];
      Point2D rightEdge = vertexes[ vertexes.length - 1 ];

      Line2D leftLine  = new Line2D.Double(  leftEdge, new Point2D.Double( cx - w/2.0, cy ) );
      Line2D rightLine = new Line2D.Double( rightEdge, new Point2D.Double( cx + w/2.0, cy ) );

      double rotAngle = Unit.convertRadianToDegree( theta );

      Shape rotatedLine = new Arc2D.Double( cx - w/2.0, cy - r, w, 2*r , 90 - rotAngle, 0, Arc2D.PIE );

      ShapeStyle wireFrameShapeStyle = new ShapeStyle( Color.white, Color.black );

      wireFrameShapeStyle.paint( g2, bottomEllipse );
      wireFrameShapeStyle.paint( g2, topEllipse );
      wireFrameShapeStyle.paint( g2, rotatedLine );
      wireFrameShapeStyle.paint( g2, leftLine );
      wireFrameShapeStyle.paint( g2, rightLine );

   }

   private void paintVertical3D(Graphics2D g2) {

      Dimension size = getSize();

      double thetaAngle = getThetaAngle();
      double phiAngle = getPhiAngle();

      double zx = size.width/4.0;
      double theta = Unit.convertDegreeToRadian( thetaAngle );
      double phi = Unit.convertDegreeToRadian( phiAngle );

      Utility.debug( this, "ZX = " + zx + ", THETA = " + thetaAngle + ", PHI = " + phiAngle );

      Point2D projectionVector = PictureExtent.getProjectionVector( zx, theta, phi );

      int proX = (int) projectionVector.getX();
      int proY = (int) projectionVector.getY();

      int top = 5 + ( proY < 0 ? Math.abs( proY ) : 0 );
      int bottom = 5 + ( proY < 0 ? 0 : Math.abs( proY ) );

      int left = 5 + ( proX > 0 ? 0 : Math.abs( proX ) );
      int right = 5 + ( proX > 0 ? Math.abs( proX ) : 0 );

      double x = left;
      double y = top;

      double w = size.width - left - right;
      double h = size.height - top - bottom;

      ShapeStyle wireFrameShapeStyle = new ShapeStyle( null, Color.black );

      double bw = w/4.0; // bar width
      double mx = bw/4.0; // x margin of bar
      double dbh = h/2.0/3.0; // bar height decrement

      double [][] barDatas = {
	{ x, y, w, h },
	{ x + mx, y + dbh, bw, h - dbh }, // 첫 번 째 삼차원 bar
	{ x + mx*2.0 + bw, y + 2*dbh, bw, h - 2*dbh },
	{ x + mx*3.0 + 2*bw, y + 3*dbh, bw, h - 3*dbh },
      };

      for( int i = 0, len = barDatas.length; i < len; i ++ ) {

	  double barData [] = barDatas[ i ];

	  Rectangle2D bar2D = new Rectangle2D.Double( barData[0], barData[1], barData[2], barData[3] );;

	  Shape [][] bar3D;

	  if( i == 0 ) {

	    bar3D = ThreeDimUtilities.get3DBarInsideShapesGroup( bar2D, proX, proY );

	  } else {

	    bar3D = ThreeDimUtilities.get3DBarShapesGroup( (int)(proX*1.0/3.0), (int)(proY*1.0/3.0),
							   bar2D,
							   (int)(proX*2.0/3.0), (int)(proY*2.0/3.0) );

	  }

	  wireFrameShapeStyle.paint3DShapess( g2, bar3D );

      }

   }

   private void controlViewPoint(final int dThetaAngle, final int dPhiAngle ) {

      thetaAngle += dThetaAngle;

      phiAngle += dPhiAngle;

      check3DAngles();

      repaint();

   }

   private void check3DAngles() {

     ChartType ct = this.chartType;

     if( ct instanceof CircularChartType ) {

	checkCircular3DAngles();

     } else if( ct instanceof HorizontalChartType ) {

	checkHorizontal3DAngles();

     } else {

	checkVertical3DAngles();

     }

   }

   private void checkVertical3DAngles() {

      thetaAngle = ( thetaAngle > 45 ) ? 45 : thetaAngle;
      thetaAngle = ( thetaAngle < - 45 ) ? - 45 : thetaAngle;

      phiAngle = ( phiAngle > 90 ) ? 90 : phiAngle;
      phiAngle = ( phiAngle < - 90 ) ? -90 : phiAngle;

   }

   private void checkHorizontal3DAngles() {

      thetaAngle = ( thetaAngle > 45 ) ? 45 : thetaAngle;
      thetaAngle = ( thetaAngle <  0 ) ?  0 : thetaAngle;

      phiAngle = ( phiAngle > 45 ) ? 45 : phiAngle;
      phiAngle = ( phiAngle <  0 ) ?  0 : phiAngle;

   }

   private void checkCircular3DAngles() {

      thetaAngle %= 360;

      phiAngle = ( phiAngle > 80 ) ? 80 : phiAngle;
      phiAngle = ( phiAngle < 10 ) ? 10 : phiAngle;

   }

   public int getThetaAngle() {

     if( thetaAngle < 0 ) {

       return 360 + thetaAngle;

     }

      return this.thetaAngle;

   }

   public int getPhiAngle() {

      return this.phiAngle;

   }

   public void setEyeDown() {

       controlViewPoint( 0, FIVE_DEGREE );

   }

   public void setEyeUp() {

       controlViewPoint( 0, - FIVE_DEGREE );

   }

   public void setEyeLeft() {

       controlViewPoint( TEN_DEGREE, 0 );

   }

   public void setEyeRight() {

       controlViewPoint( - TEN_DEGREE, 0 );

   }

}
