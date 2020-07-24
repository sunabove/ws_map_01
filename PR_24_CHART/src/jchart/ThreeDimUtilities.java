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

public class ThreeDimUtilities {

//	public static Point2D getProjectedPoint( int z, double zxRate, double zyRate ) {
//
//		return new Point2D.Double( z*zxRate, z*zyRate );
//
//	}

	/**
	 * getProjectedPoint( .... ) 의 역함수
	 */
	public static double [] getProjectedZRates( int z, float zx, float zy ) {

		return new double [] { zx/(z + 0.0), zy/(z + 0.0) };

	}

	public static Shape [][] get3DHorizontalBarShapesGroup(Rectangle2D rect, float proX, float proY ) {

		Shape [][] shapess3D = new Shape[6][];

		shapess3D[0] = new Shape [] { rect };

		float x = (float) rect.getX(), y = (float) rect.getY(),
			w = (float) rect.getWidth(), h = (float)rect.getHeight();

		if( proY <= 0 ) { // 좌상단 꼭지점이 위로 갈 경우에

			GeneralPath gp = new GeneralPath();

			gp.moveTo( x, y );
			gp.lineTo( x + proX, y + proY );
			gp.lineTo( x + proX + w, y + proY );
			gp.lineTo( x + w, y );
			gp.closePath();

			shapess3D[4] = new Shape [] { gp };

			if( proX > 0 ) {

				gp = new GeneralPath();

				gp.moveTo( x + w, y );
				gp.lineTo( x + w + proX, y + proY );
				gp.lineTo( x + w + proX, y + proY + h );
				gp.lineTo( x + w, y + h );
				gp.closePath();

				shapess3D[ 1 ] = new Shape [] { gp };

			} else if( proX < 0 ) {

				gp = new GeneralPath();

				gp.moveTo( x, y );
				gp.lineTo( x + proX, y + proY );
				gp.lineTo( x + proX, y + proY + h );
				gp.lineTo( x, y + h );
				gp.closePath();

				shapess3D[ 3 ] = new Shape [] { gp };

			}

		} else { // 좌상단 꼭지점이 밑으로 갈 경우에

			GeneralPath gp = new GeneralPath();

			gp.moveTo( x, y + h);
			gp.lineTo( x + proX, y + h + proY );
			gp.lineTo( x + proX + w, y + h + proY );
			gp.lineTo( x + w, y + h );
			gp.closePath();

			shapess3D[5] = new Shape [] { gp };

			if( proX > 0 ) {

				gp = new GeneralPath();

				gp.moveTo( x + w, y );
				gp.lineTo( x + w + proX, y + proY );
				gp.lineTo( x + w + proX, y + proY + h );
				gp.lineTo( x + w, y + h );
				gp.closePath();

				shapess3D[ 1 ] = new Shape [] { gp };

			} else if( proX < 0 ) {

				gp = new GeneralPath();

				gp.moveTo( x, y );
				gp.lineTo( x + proX, y + proY );
				gp.lineTo( x + proX, y + proY + h );
				gp.lineTo( x, y + h );
				gp.closePath();

				shapess3D[ 3 ] = new Shape [] { gp };

			}

		}

		return shapess3D;

	}

	public static Shape [][] get3DPieShapesGroup(float tx, float ty, Shape arc, float rotRadian, float thickness, float top, float bottom ) {

		Shape [][] shapess3D = new Shape[6][];

		double sy = (bottom - thickness - top)/(bottom - top);

		Point2D [] ctrlPnts = ShapeUtilities.getPieControlPoints( arc );

		Point2D center = ctrlPnts[0];

		double cx = center.getX(); // center x
		double cy = center.getY() - thickness/2.0;

		AffineTransform at = AffineTransform.getTranslateInstance( cx, cy );

		at.scale( 1.0, sy );

		at.translate( - center.getX(), - center.getY() );

		arc = at.createTransformedShape( arc );

		shapess3D[ 4 ] = new Shape [] { arc };

		Shape bottomPie = ShapeUtilities.getShapeProcessedByVerticalBeam( arc );

		Path bottomPath = new Path( bottomPie );

		Shape [] bottomBanners = bottomPath.getElligentBanner( thickness );

		shapess3D[ 0 ] = ShapeUtilities.filterShapesNotInterSects( bottomPath.getGeneralPath(), bottomBanners );

		shapess3D[ 0 ] = ShapeUtilities.mergeShapesHavingQuadToOrCubicToPath( shapess3D[ 0 ] );

		return shapess3D;

	}

	public static Shape [][] get3DBarShapesGroup(float tx, float ty, Rectangle2D rect, float proX, float proY ) {

		float x = (float) rect.getX() + tx ;
		float y = (float) rect.getY() + ty ;
		float w = (float) rect.getWidth();
		float h = (float) rect.getHeight();

		return get3DBarShapesGroup( x, y, w, h, proX, proY );

	}

	public static Shape [][] get3DPlaneShapesGroup(float tx, float ty, Rectangle2D rect, float proX, float proY ) {

		float x = (float) rect.getX() + tx ;
		float y = (float) rect.getY() + ty ;
		float w = (float) rect.getWidth();
		float h = (float) rect.getHeight();

		return get3DPlaneShapesGroup( x, y, w, h, proX, proY );

	}

	private static Shape [][] get3DBarShapesGroup(float x, float y, float w, float h, float proX, float proY) {

		Shape [][] shapess3D = new Shape[6][];

		shapess3D[0] = new Shape [] { new Rectangle2D.Float( x, y, w, h ) };

		if( proY <= 0 ) { // 좌상단 꼭지점이 위로 갈 경우에

			GeneralPath gp = new GeneralPath();

			gp.moveTo( x, y );
			gp.lineTo( x + proX, y + proY );
			gp.lineTo( x + proX + w, y + proY );
			gp.lineTo( x + w, y );
			gp.closePath();

			shapess3D[4] = new Shape [] { gp };

			if( proX > 0 ) {

				gp = new GeneralPath();

				gp.moveTo( x + w, y );
				gp.lineTo( x + w + proX, y + proY );
				gp.lineTo( x + w + proX, y + proY + h );
				gp.lineTo( x + w, y + h );
				gp.closePath();

				shapess3D[ 1 ] = new Shape [] { gp };

			} else if( proX < 0 ) {

				gp = new GeneralPath();

				gp.moveTo( x, y );
				gp.lineTo( x + proX, y + proY );
				gp.lineTo( x + proX, y + proY + h );
				gp.lineTo( x, y + h );
				gp.closePath();

				shapess3D[ 3 ] = new Shape [] { gp };

			}

		} else { // 좌상단 꼭지점이 밑으로 갈 경우에

			GeneralPath gp = new GeneralPath();

			gp.moveTo( x, y + h);
			gp.lineTo( x + proX, y + h + proY );
			gp.lineTo( x + proX + w, y + h + proY );
			gp.lineTo( x + w, y + h );
			gp.closePath();

			shapess3D[5] = new Shape [] { gp };

			if( proX > 0 ) {

				gp = new GeneralPath();

				gp.moveTo( x + w, y );
				gp.lineTo( x + w + proX, y + proY );
				gp.lineTo( x + w + proX, y + proY + h );
				gp.lineTo( x + w, y + h );
				gp.closePath();

				shapess3D[ 1 ] = new Shape [] { gp };

			} else if( proX < 0 ) {

				gp = new GeneralPath();

				gp.moveTo( x, y );
				gp.lineTo( x + proX, y + proY );
				gp.lineTo( x + proX, y + proY + h );
				gp.lineTo( x, y + h );
				gp.closePath();

				shapess3D[ 3 ] = new Shape [] { gp };

			}

		}

		return shapess3D;

	}

	private static Shape [][] get3DPlaneShapesGroup(float x, float y, float w, float h, float proX, float proY) {

		Shape [][] shapess3D = new Shape[6][];

		if( proY <= 0 ) { // 좌상단 꼭지점이 위로 갈 경우에

			GeneralPath gp = new GeneralPath();

			if( proX > 0 ) {

				gp = new GeneralPath();

				gp.moveTo( x + w, y );
				gp.lineTo( x + w + proX, y + proY );
				gp.lineTo( x + w + proX, y + proY + h );
				gp.lineTo( x + w, y + h );
				gp.closePath();

				shapess3D[ 1 ] = new Shape [] { gp };

			} else if( proX < 0 ) {

				gp = new GeneralPath();

				gp.moveTo( x, y );
				gp.lineTo( x + proX, y + proY );
				gp.lineTo( x + proX, y + proY + h );
				gp.lineTo( x, y + h );
				gp.closePath();

				shapess3D[ 3 ] = new Shape [] { gp };

			}

		} else { // 좌상단 꼭지점이 밑으로 갈 경우에

			GeneralPath gp = new GeneralPath();

			if( proX > 0 ) {

				gp = new GeneralPath();

				gp.moveTo( x + w, y );
				gp.lineTo( x + w + proX, y + proY );
				gp.lineTo( x + w + proX, y + proY + h );
				gp.lineTo( x + w, y + h );
				gp.closePath();

				shapess3D[ 1 ] = new Shape [] { gp };

			} else if( proX < 0 ) {

				gp = new GeneralPath();

				gp.moveTo( x, y );
				gp.lineTo( x + proX, y + proY );
				gp.lineTo( x + proX, y + proY + h );
				gp.lineTo( x, y + h );
				gp.closePath();

				shapess3D[ 3 ] = new Shape [] { gp };

			}

		}

		return shapess3D;

	}

	public static Shape [][] get3DBendLineShapesGroup(float tx, float ty, Shape shape, float proX, float proY ) {

		AffineTransform at = AffineTransform.getTranslateInstance( tx, ty );

		shape = at.createTransformedShape( shape );

		return get3DBendLineShapesGroup( shape, proX, proY );

	}

	private static Shape [][] get3DBendLineShapesGroup(Shape shape, float proX, float proY ) {

		Vector bendLineBandVec = new Vector();

		Path path = new Path( shape );

		PathElement pathElems [] = path.getPathElements();

		for( int i = 0, len = pathElems.length -1 ; i < len; i ++ ) {

		    float [] from = pathElems[ i ].getPoint();
		    float [] to = pathElems[ i + 1 ].getPoint();

		    GeneralPath gp = new GeneralPath();

		    gp.moveTo( from[0], from[1] );
		    gp.lineTo( to[0], to[1] );
		    gp.lineTo( to[0] + proX, to[1] + proY );
		    gp.lineTo( from[0] + proX, from[1] + proY );
		    gp.closePath();

		    bendLineBandVec.add( gp );

		}

		int len = bendLineBandVec.size();

		Shape [] shapes = new Shape[ len ];

		for(int i = 0; i < len; i ++ ) {

		    shapes[ i ] = (Shape) ( bendLineBandVec.elementAt( i ) );

		}

		Shape [][] shapess3D = new Shape [6][ ];

		shapess3D[ 4 ] = shapes;

		return shapess3D;

	}

	public static Shape [][] get3DArialShapesGroup(float tx, float ty, Shape shape, float proX, float proY ) {

		AffineTransform at = AffineTransform.getTranslateInstance( tx, ty );

		shape = at.createTransformedShape( shape );

		return get3DArialShapesGroup( shape, proX, proY );

	}

	private static Shape [][] get3DArialShapesGroup(Shape shape, float proX, float proY ) {

		Shape [][] shapess3D = new Shape[ 6 ] [ 0 ];

		shapess3D[0] = new Shape [] { shape };

		Path path = new Path( shape );

		Shape [] slashedShapes = path.getSlashedBanner( proX, proY );

		shapess3D[ 4 ] = ShapeUtilities.filterShapesNotInterSects( path.getGeneralPath(), slashedShapes );

		return shapess3D;

	}

	public static Shape [][] get3DVerticalCylindricShapesGroup(float tx, float ty, Rectangle2D rect, float proX, float proY ) {

		float x = (float) rect.getX() + tx ;
		float y = (float) rect.getY() + ty ;
		float w = (float) rect.getWidth();
		float h = (float) rect.getHeight();

		return get3DVerticalCylindricShapesGroup( x, y, w, h, proX, proY );

	}

	private static Shape [][] get3DVerticalCylindricShapesGroup(float x, float y,
		  float w, float h, float proX, float proY) {

		Shape [][] shapess3D = new Shape[6][];

		if( proY == 0 ) {

			double caliW = Math.abs( proX ); // width calibration

			shapess3D[ 0 ] = new Shape [ ] { new Rectangle2D.Double( x, y, w + caliW , h ) };

			return shapess3D;

		}

		double rx = x;
		double rw = w;
		double rh = Math.abs( proY );
		double ry = y - rh/2.0;

		double shx = proX/proY;

		Shape topEllipse = new Ellipse2D.Double( rx, ry, rw, rh );

		AffineTransform at = AffineTransform.getShearInstance( shx, 0.0 );

		topEllipse = at.createTransformedShape( topEllipse );

		topEllipse = ShapeUtilities.getShapeWithCorrectBounds( topEllipse );

		Rectangle2D topEllipseBounds = topEllipse.getBounds2D();

		double tx = x - topEllipseBounds.getX();

		double ty = y - ( topEllipseBounds.getY() + topEllipseBounds.getHeight() );

		at = AffineTransform.getTranslateInstance( tx, ty );

		topEllipse = at.createTransformedShape( topEllipse );

		topEllipse = ShapeUtilities.getShapeProcessedByVerticalBeam( topEllipse );

		Path topEllipsePath = new Path( topEllipse );

		PathElement edges [] = topEllipsePath.getVerticalEdgePathElements();

		PathElement leftEdge = edges[ 0 ];

		PathElement rightEdge = edges[ 1 ];

		float [] leftEdgePoint = leftEdge.getPoint();
		float [] rightEdgePoint = rightEdge.getPoint();

		GeneralPath frontShape = new GeneralPath();

		frontShape.moveTo( leftEdgePoint[0], leftEdgePoint[1] );
		frontShape.lineTo( rightEdgePoint[0], rightEdgePoint[1] );
		frontShape.lineTo( rightEdgePoint[0], rightEdgePoint[1] + h );
		frontShape.lineTo( leftEdgePoint[0], leftEdgePoint[1] + h );
		frontShape.closePath();

		Shape bottomEllipse = AffineTransform.getTranslateInstance( 0, h ).createTransformedShape( topEllipse );

		Area frontArea = new Area( frontShape );

		if( proY < 0 ) {

			Area topArea = new Area( topEllipse );
			Area bottomArea = new Area( bottomEllipse );

			frontArea.subtract( topArea );
			frontArea.add( bottomArea );

			shapess3D[ 0 ] = new Shape [] { frontArea };

			shapess3D[ 4 ] = new Shape [ ] { topEllipse };

		} else {

			Area topArea = new Area( topEllipse );
			Area bottomArea = new Area( bottomEllipse );

			frontArea.subtract( bottomArea );
			frontArea.add( topArea );

			shapess3D[ 0 ] = new Shape [] { frontArea };

			shapess3D[ 5 ] = new Shape [ ] { bottomEllipse };

		}

		return shapess3D;

	}

	public static Shape [][] get3DVerticalSimpleConicShapesGroup(float tx, float ty, Rectangle2D rect, float proX, float proY ) {

		float x = (float) rect.getX() + tx ;
		float y = (float) rect.getY() + ty ;
		float w = (float) rect.getWidth();
		float h = (float) rect.getHeight();

		return get3DVerticalSimpleConicShapesGroup( x, y, w, h, proX, proY );

	}

	private static Shape [][] get3DVerticalSimpleConicShapesGroup(float x, float y,
		  float w, float h, float proX, float proY) {

		Shape [][] shapess3D = new Shape[6][];

		if( proY == 0 ) {

			float conTopPointX = (float)( x + w/2.0 + proX/2.0 );

			float conTopPointY = y;

			GeneralPath coneFront = new GeneralPath();

			coneFront.moveTo( conTopPointX, conTopPointY );
			coneFront.lineTo( x, y + h );
			coneFront.lineTo( x + w + proX, y + h );
			coneFront.closePath();

			shapess3D[ 0 ] = new Shape [ ] { coneFront };

			return shapess3D;

		}

		double rx = x;
		double rw = w;
		double rh = Math.abs( proY );
		double ry = y + h - rh/2.0;

		double shx = proX/proY;

		Shape coneBase = new Ellipse2D.Double( rx, ry, rw, rh );

		AffineTransform at = AffineTransform.getShearInstance( shx, 0.0 );

		coneBase = at.createTransformedShape( coneBase );

		coneBase = ShapeUtilities.getShapeWithCorrectBounds( coneBase );

		Rectangle2D coneBaseBounds = coneBase.getBounds2D();

		double tx = x - coneBaseBounds.getX();

		double ty = y + h - ( coneBaseBounds.getY() + coneBaseBounds.getHeight() );

		at = AffineTransform.getTranslateInstance( tx, ty );

		coneBase = at.createTransformedShape( coneBase );

		coneBase = ShapeUtilities.getShapeProcessedByVerticalBeam( coneBase );

		Path coneBasePath = new Path( coneBase );

		PathElement edges [] = coneBasePath.getVerticalEdgePathElements();

		PathElement leftEdge = edges[ 0 ];

		PathElement rightEdge = edges[ 1 ];

		float [] leftEdgePoint = leftEdge.getPoint();
		float [] rightEdgePoint = rightEdge.getPoint();

		float conTopPointX = (float)( x + w/2.0 + proX/2.0 );
		float conTopPointY = (float)( y + proY/2.0 );

		GeneralPath coneFront = new GeneralPath();

		coneFront.moveTo( conTopPointX, conTopPointY );
		coneFront.lineTo( leftEdgePoint[0], leftEdgePoint[1] );
		coneFront.lineTo( rightEdgePoint[0], rightEdgePoint[1] );
		coneFront.closePath();

		if( proY < 0 ) {

			Area coneFrontArea = new Area( coneFront );
			Area coneBaseArea = new Area( coneBase );

			coneFrontArea.add( coneBaseArea );

			shapess3D[ 0 ] = new Shape [] { coneFrontArea };

		} else {

			Area coneFrontArea = new Area( coneFront );
			Area coneBaseArea = new Area( coneBase );

			coneFrontArea.subtract( coneBaseArea );

			shapess3D[ 0 ] = new Shape [] { coneFrontArea };

			shapess3D[ 5 ] = new Shape [ ] { coneBase };

		}

		return shapess3D;

	}

	public static Shape [][] get3DHorizontalSimpleConicShapesGroup(float tx, float ty, Rectangle2D rect, float proX, float proY ) {

		float x = (float) rect.getX() + tx ;
		float y = (float) rect.getY() + ty ;
		float w = (float) rect.getWidth();
		float h = (float) rect.getHeight();

		return get3DHorizontalSimpleConicShapesGroup( x, y, w, h, proX, proY );

	}

	private static Shape [][] get3DHorizontalSimpleConicShapesGroup(float x, float y,
		  float w, float h, float proX, float proY) {

		Shape [][] shapess3D = new Shape[6][];

		if( proX == 0 ) {

			float conTopPointX = x + w;

			float conTopPointY = (float)( y + h/2.0 + proY/2.0 );

			GeneralPath coneFront = new GeneralPath();

			coneFront.moveTo( conTopPointX, conTopPointY );
			coneFront.lineTo( x, y );
			coneFront.lineTo( x, y + h + proY );
			coneFront.closePath();

			shapess3D[ 0 ] = new Shape [ ] { coneFront };

			return shapess3D;

		}

		double rw = Math.abs( proX );
		double rh = h;
		double ry = y;
		double rx = x + w - rw/2.0;

		double shy = proY/proX;

		Shape coneBase = new Ellipse2D.Double( rx, ry, rw, rh );

		AffineTransform at = AffineTransform.getShearInstance( 0.0, shy );

		coneBase = at.createTransformedShape( coneBase );

		coneBase = ShapeUtilities.getShapeWithCorrectBounds( coneBase );

		Rectangle2D coneBaseBounds = coneBase.getBounds2D();

		double tx = x - coneBaseBounds.getX();

		double ty = y - coneBaseBounds.getY();

		at = AffineTransform.getTranslateInstance( tx, ty );

		coneBase = at.createTransformedShape( coneBase );

		coneBase = ShapeUtilities.getShapeProcessedByHorizontalBeam( coneBase );

		Path coneBasePath = new Path( coneBase );

		PathElement edges [] = coneBasePath.getHorizontalEdgePathElements();

		PathElement topEdge = edges[ 0 ];

		PathElement bottomEdge = edges[ 1 ];

		float [] topEdgePoint = topEdge.getPoint();
		float [] bottomEdgePoint = bottomEdge.getPoint();

		float conTopPointX = (float)( x + w + proX/2.0 );
		float conTopPointY = (float)( y + h/2.0 + proY/2.0 );

		GeneralPath coneFront = new GeneralPath();

		coneFront.moveTo( conTopPointX, conTopPointY );
		coneFront.lineTo( topEdgePoint[0], topEdgePoint[1] );
		coneFront.lineTo( bottomEdgePoint[0], bottomEdgePoint[1] );
		coneFront.closePath();

		if( proX > 0 ) {

			Area coneFrontArea = new Area( coneFront );
			Area coneBaseArea = new Area( coneBase );

			coneFrontArea.add( coneBaseArea );

			shapess3D[ 0 ] = new Shape [] { coneFrontArea };

		} else {

			Area coneFrontArea = new Area( coneFront );
			Area coneBaseArea = new Area( coneBase );

			coneFrontArea.subtract( coneBaseArea );

			shapess3D[ 0 ] = new Shape [] { coneFrontArea };

			shapess3D[ 5 ] = new Shape [ ] { coneBase };

		}

		return shapess3D;

	}

	public static Shape [][] get3DVerticalSimplePyramidShapesGroup(float tx, float ty, Rectangle2D rect, float proX, float proY ) {

		float x = (float) rect.getX() + tx ;
		float y = (float) rect.getY() + ty ;
		float w = (float) rect.getWidth();
		float h = (float) rect.getHeight();

		return get3DVerticalSimplePyramidShapesGroup( x, y, w, h, proX, proY );

	}

	private static Shape [][] get3DVerticalSimplePyramidShapesGroup(float x, float y,
		  float w, float h, float proX, float proY) {

		Shape [][] shapess3D = new Shape[6][];

		float topPyramidX = (float)( x + w/2.0 + proX/2.0 );
		float topPyramidY = (float)( y + proY/2.0 );

		GeneralPath frontPyramid = new GeneralPath();

		frontPyramid.moveTo( topPyramidX, topPyramidY );
		frontPyramid.lineTo( x, y + h );
		frontPyramid.lineTo( x + w, y + h );
		frontPyramid.closePath();

		shapess3D[ 0 ] = new Shape [] { frontPyramid };

		if( proX > 0 ) {

		    GeneralPath rightPyramid = new GeneralPath();

		    rightPyramid.moveTo( topPyramidX, topPyramidY );
		    rightPyramid.lineTo( x + w, y + h );
		    rightPyramid.lineTo( x+ w + proX, y + h + proY );
		    rightPyramid.closePath();

		    shapess3D[ 1 ] = new Shape [] { rightPyramid };

		} else if( proX < 0 ) {

		    GeneralPath leftPyramid = new GeneralPath();

		    leftPyramid.moveTo( topPyramidX, topPyramidY );
		    leftPyramid.lineTo( x, y + h );
		    leftPyramid.lineTo( x + proX, y + h + proY );
		    leftPyramid.closePath();

		    shapess3D[ 3 ] = new Shape [] { leftPyramid };

		}

		if( proY > 0 ) {

		    GeneralPath bottomPyramid = new GeneralPath();

		    bottomPyramid.moveTo( x, y + h );
		    bottomPyramid.lineTo( x + w, y + h );
		    bottomPyramid.lineTo( x + w + proX, y + h + proY );
		    bottomPyramid.lineTo( x + proX, y + h + proY );
		    bottomPyramid.closePath();

		    shapess3D[ 5 ] = new Shape [] { bottomPyramid };

		}

		return shapess3D;

	}

	public static Shape [][] get3DVerticalProportionalPyramidShapesGroup(float tx, float ty, Rectangle2D rect, float proX, float proY,
	       float top, float bottom ) {

		float x = (float) rect.getX() + tx ;
		float y = (float) rect.getY() + ty ;
		float w = (float) rect.getWidth();
		float h = (float) rect.getHeight();

		return get3DVerticalProportionalPyramidShapesGroup( x, y, w, h, proX, proY, top + ty, bottom + ty );

	}

	/**
	 *             A
	 *            /\
	 *         E2/  \ \  D2
	 *        B2/____\/\C2
	 *         / E3   \ \ D3
	 *      B3/________\/\C3
	 *       /  E       \ \
	 *      /____________\/ D
	 *    B               C
	 */

	private static Shape [][] get3DVerticalProportionalPyramidShapesGroup(float x, float y,
		  float w, float h, float proX, float proY, float top, float bottom ) {

		Shape [][] shapess3D = new Shape[6][];

		float AX = (float) ( x + w/2.0 + proX/2.0 );
		float AY = (float) ( top + proY/2.0 );

		float BX = x, BY = bottom;
		float CX = BX + w, CY = BY;
		float DX = CX + proX, DY = CY + proY;
		float EX = BX + proX, EY = DY;

		float B2Ratio = (float)( ( y - top ) / (bottom - top) );
		float B3Ratio = (float) ( ( y + h - top ) / (bottom - top) );

		float B2X = AX + (BX - AX)*B2Ratio, B2Y = AY + (BY - AY)*B2Ratio;
		float C2X = AX + (CX - AX)*B2Ratio, C2Y = AY + (CY - AY)*B2Ratio;
		float D2X = AX + (DX - AX)*B2Ratio, D2Y = AY + (DY - AY)*B2Ratio;
		float E2X = AX + (EX - AX)*B2Ratio, E2Y = AY + (EY - AY)*B2Ratio;

		float B3X = AX + (BX - AX)*B3Ratio, B3Y = AY + (BY - AY)*B3Ratio;
		float C3X = AX + (CX - AX)*B3Ratio, C3Y = AY + (CY - AY)*B3Ratio;
		float D3X = AX + (DX - AX)*B3Ratio, D3Y = AY + (DY - AY)*B3Ratio;
		float E3X = AX + (EX - AX)*B3Ratio, E3Y = AY + (EY - AY)*B3Ratio;

		GeneralPath frontShape = new GeneralPath();

		frontShape.moveTo( B2X, B2Y );
		frontShape.lineTo( C2X, C2Y );
		frontShape.lineTo( C3X, C3Y );
		frontShape.lineTo( B3X, B3Y );
		frontShape.closePath();

		shapess3D[ 0 ] = new Shape [] { frontShape };

		if( proX > 0 ) {

		    GeneralPath rightShape = new GeneralPath();

		    rightShape.moveTo( C2X, C2Y );
		    rightShape.lineTo( D2X, D2Y );
		    rightShape.lineTo( D3X, D3Y );
		    rightShape.lineTo( C3X, C3Y );
		    rightShape.closePath();

		    shapess3D[ 1 ] = new Shape [] { rightShape };

		} else if( proX < 0 ) {

		    GeneralPath leftShape = new GeneralPath();

		    leftShape.moveTo( B2X, B2Y );
		    leftShape.lineTo( E2X, E2Y );
		    leftShape.lineTo( E3X, E3Y );
		    leftShape.lineTo( B3X, B3Y );
		    leftShape.closePath();

		    shapess3D[ 3 ] = new Shape [] { leftShape };

		}

		if( proY > 0 ) {

		    GeneralPath bottomShape = new GeneralPath();

		    bottomShape.moveTo( B3X, B3Y );
		    bottomShape.lineTo( C3X, C3Y );
		    bottomShape.lineTo( D3X, D3Y );
		    bottomShape.lineTo( E3X, E3Y );
		    bottomShape.closePath();

		    shapess3D[ 5 ] = new Shape [] { bottomShape };

		}

		return shapess3D;

	}

	public static Shape [][] get3DVerticalProportionalConicShapesGroup(float tx, float ty, Rectangle2D rect, float proX, float proY,
	       float top, float bottom ) {

		float x = (float) rect.getX() + tx ;
		float y = (float) rect.getY() + ty ;
		float w = (float) rect.getWidth();
		float h = (float) rect.getHeight();

		return get3DVerticalProportionalConicShapesGroup( x, y, w, h, proX, proY, top + ty, bottom + ty );

	}

	/**
	 *             A
	 *            /\
	 *         E2/  \ \  D2
	 *        B2/____\/\C2
	 *         / E3   \ \ D3
	 *      B3/________\/\C3
	 *       /  E       \ \
	 *      /____________\/ D
	 *    B               C
	 */

	private static Shape [][] get3DVerticalProportionalConicShapesGroup(float x, float y,
		  float w, float h, float proX, float proY, float top, float bottom ) {

		Shape [][] shapess3D = new Shape[6][];

		float AX = (float) ( x + w/2.0 + proX/2.0 );
		float AY = (float) ( top + proY/2.0 );

		float BX = x, BY = bottom;
		float CX = BX + w, CY = BY;
		float DX = CX + proX, DY = CY + proY;
		float EX = BX + proX, EY = DY;

		float B2Ratio = (float)( ( y - top ) / (bottom - top) );
		float B3Ratio = (float) ( ( y + h - top ) / (bottom - top) );

		float B2X = AX + (BX - AX)*B2Ratio, B2Y = AY + (BY - AY)*B2Ratio;
		float C2X = AX + (CX - AX)*B2Ratio, C2Y = AY + (CY - AY)*B2Ratio;
		float D2X = AX + (DX - AX)*B2Ratio, D2Y = AY + (DY - AY)*B2Ratio;
		float E2X = AX + (EX - AX)*B2Ratio, E2Y = AY + (EY - AY)*B2Ratio;

		float B3X = AX + (BX - AX)*B3Ratio, B3Y = AY + (BY - AY)*B3Ratio;
		float C3X = AX + (CX - AX)*B3Ratio, C3Y = AY + (CY - AY)*B3Ratio;
		float D3X = AX + (DX - AX)*B3Ratio, D3Y = AY + (DY - AY)*B3Ratio;
		float E3X = AX + (EX - AX)*B3Ratio, E3Y = AY + (EY - AY)*B3Ratio;

		if( proY == 0 ) {

		    GeneralPath frontShape = new GeneralPath();

		    if( proX > 0 ) {

			frontShape.moveTo( B2X, B2Y );
			frontShape.lineTo( D2X, D2Y );
			frontShape.lineTo( D3X, D3Y );
			frontShape.lineTo( B3X, B3Y );
			frontShape.closePath();

		    } else {

			frontShape.moveTo( E2X, E2Y );
			frontShape.lineTo( C2X, C2Y );
			frontShape.lineTo( C3X, C3Y );
			frontShape.lineTo( E3X, E3Y );
			frontShape.closePath();

		    }

		    shapess3D[ 0 ] = new Shape [] { frontShape };

		    return shapess3D;

		}

		float shx = proX/proY;

		double rw = C2X - B2X;
		double rh = Math.abs( B2Y - E2Y );

		double tx = B2X;
		double ty = proY < 0 ? E2Y : B2Y ;

		// creates top cone shape

		Shape topConeShape = new Ellipse2D.Double( 0, -rh, rw, rh );

		AffineTransform at = AffineTransform.getTranslateInstance( tx, ty );

		at.shear( shx, 0.0 );

		topConeShape = at.createTransformedShape( topConeShape );

		topConeShape = ShapeUtilities.getShapeProcessedByVerticalBeam( topConeShape );

		// end of creating top cone shape

		// creates bottom cone shape

		rw = C3X - B3X;
		rh = Math.abs( B3Y - E3Y );

		tx = B3X;
		ty = proY < 0 ? E3Y : B3Y ;

		Shape bottomConeShape = new Ellipse2D.Double( 0, -rh, rw, rh );

		at = AffineTransform.getTranslateInstance( tx, ty );

		at.shear( shx, 0.0 );

		bottomConeShape = at.createTransformedShape( bottomConeShape );

		bottomConeShape = ShapeUtilities.getShapeProcessedByVerticalBeam( bottomConeShape );

		// end of creating bottom cone shape

		// creates front shape

		Path topConeShapePath = new Path( topConeShape );

		float [] topConeLeftEdge;
		float [] topConeRightEdge;

		int pathNum = topConeShapePath.getPathElements().length;

		Utility.debug(ThreeDimUtilities.class , "PATH NUM = " + pathNum );

		if( pathNum == 0 ) {

		    topConeLeftEdge = topConeRightEdge = new float [] { AX, AY };

		} else {

		    PathElement topConeShapeEdges [] = topConeShapePath.getVerticalEdgePathElements();

		    topConeLeftEdge = topConeShapeEdges[ 0 ].getPoint();
		    topConeRightEdge = topConeShapeEdges[ 1 ].getPoint();

		}

		Path bottomConeShapePath = new Path( bottomConeShape );

		PathElement bottomConeShapeEdges [] = bottomConeShapePath.getVerticalEdgePathElements();

		float [] bottomConeLeftEdge = bottomConeShapeEdges[ 0 ].getPoint();
		float [] bottomConeRightEdge = bottomConeShapeEdges[ 1 ].getPoint();

		GeneralPath frontShape = new GeneralPath();

		frontShape.moveTo( topConeLeftEdge[0], topConeLeftEdge[1] );
		frontShape.lineTo( topConeRightEdge[0], topConeRightEdge[1] );
		frontShape.lineTo( bottomConeRightEdge[0], bottomConeRightEdge[1] );
		frontShape.lineTo( bottomConeLeftEdge[0], bottomConeLeftEdge[1] );
		frontShape.closePath();

		// end of creating front shape

		if( proY < 0 ) {

		     Area frontShapeArea = new Area( frontShape );

		     Area topConeShapeArea = new Area( topConeShape );

		     Area bottomConeShapeArea = new Area( bottomConeShape );

		     frontShapeArea.subtract( topConeShapeArea );
		     frontShapeArea.add( bottomConeShapeArea );

		     shapess3D[ 0 ] = new Shape [] { frontShapeArea };

		} else if( proY > 0 ) {

		     Area frontShapeArea = new Area( frontShape );

		     Area topConeShapeArea = new Area( topConeShape );

		     Area bottomConeShapeArea = new Area( bottomConeShape );

		     frontShapeArea.add( topConeShapeArea );
		     frontShapeArea.subtract( bottomConeShapeArea );

		     shapess3D[ 0 ] = new Shape [] { frontShapeArea };

		     shapess3D[ 5 ] = new Shape [] { bottomConeShape };

		}

		return shapess3D;

	}

	public static Shape [][] get3DHorizontalProportionalPyramidShapesGroup(float tx, float ty, Rectangle2D rect, float proX, float proY,
	       float right, float left ) {

		float x = (float) rect.getX() + tx ;
		float y = (float) rect.getY() + ty ;
		float w = (float) rect.getWidth();
		float h = (float) rect.getHeight();

		return get3DHorizontalProportionalPyramidShapesGroup( x, y, w, h, proX, proY, right + tx, left + tx );

	}

	/**
	 *             A
	 *            /\
	 *         E2/  \ \  D2
	 *        B2/____\/\C2
	 *         / E3   \ \ D3
	 *      B3/________\/\C3
	 *       /  E       \ \
	 *      /____________\/ D
	 *    B               C
	 */

	private static Shape [][] get3DHorizontalProportionalPyramidShapesGroup(float x, float y,
		  float w, float h, float proX, float proY, float right, float left ) {

		Shape [][] shapess3D = new Shape[6][];

		float AX = (float) ( right + proX/2.0 );
		float AY = (float) ( y + h/2.0 + proY/2.0 );

		float BX = left, BY = y;
		float CX = BX, CY = BY + h;
		float DX = CX + proX, DY = CY + proY;
		float EX = DX, EY = BY + proY;

		float B2Ratio = (float)( ( right - ( x + w ) ) / (right - left) );
		float B3Ratio = (float) ( ( right - x ) / (right - left) );

		float B2X = AX + (BX - AX)*B2Ratio, B2Y = AY + (BY - AY)*B2Ratio;
		float C2X = AX + (CX - AX)*B2Ratio, C2Y = AY + (CY - AY)*B2Ratio;
		float D2X = AX + (DX - AX)*B2Ratio, D2Y = AY + (DY - AY)*B2Ratio;
		float E2X = AX + (EX - AX)*B2Ratio, E2Y = AY + (EY - AY)*B2Ratio;

		float B3X = AX + (BX - AX)*B3Ratio, B3Y = AY + (BY - AY)*B3Ratio;
		float C3X = AX + (CX - AX)*B3Ratio, C3Y = AY + (CY - AY)*B3Ratio;
		float D3X = AX + (DX - AX)*B3Ratio, D3Y = AY + (DY - AY)*B3Ratio;
		float E3X = AX + (EX - AX)*B3Ratio, E3Y = AY + (EY - AY)*B3Ratio;

		GeneralPath frontShape = new GeneralPath();

		frontShape.moveTo( B2X, B2Y );
		frontShape.lineTo( C2X, C2Y );
		frontShape.lineTo( C3X, C3Y );
		frontShape.lineTo( B3X, B3Y );
		frontShape.closePath();

		shapess3D[ 0 ] = new Shape [] { frontShape };

		if( proY > 0 ) {

		    GeneralPath rightShape = new GeneralPath();

		    rightShape.moveTo( C2X, C2Y );
		    rightShape.lineTo( D2X, D2Y );
		    rightShape.lineTo( D3X, D3Y );
		    rightShape.lineTo( C3X, C3Y );
		    rightShape.closePath();

		    shapess3D[ 5 ] = new Shape [] { rightShape };

		} else if( proY < 0 ) {

		    GeneralPath leftShape = new GeneralPath();

		    leftShape.moveTo( B2X, B2Y );
		    leftShape.lineTo( E2X, E2Y );
		    leftShape.lineTo( E3X, E3Y );
		    leftShape.lineTo( B3X, B3Y );
		    leftShape.closePath();

		    shapess3D[ 4 ] = new Shape [] { leftShape };

		}

		if( proX < 0 ) {

		    GeneralPath bottomShape = new GeneralPath();

		    bottomShape.moveTo( B3X, B3Y );
		    bottomShape.lineTo( C3X, C3Y );
		    bottomShape.lineTo( D3X, D3Y );
		    bottomShape.lineTo( E3X, E3Y );
		    bottomShape.closePath();

		    shapess3D[ 3 ] = new Shape [] { bottomShape };

		}

		return shapess3D;

	}

	public static Shape [][] get3DHorizontalProportionalConicShapesGroup(float tx, float ty, Rectangle2D rect, float proX, float proY,
	       float right, float left ) {

		float x = (float) rect.getX() + tx ;
		float y = (float) rect.getY() + ty ;
		float w = (float) rect.getWidth();
		float h = (float) rect.getHeight();

		return get3DHorizontalProportionalConicShapesGroup( x, y, w, h, proX, proY, right + tx, left + tx );

	}

	/**
	 *             A
	 *            /\
	 *         E2/  \ \  D2
	 *        B2/____\/\C2
	 *         / E3   \ \ D3
	 *      B3/________\/\C3
	 *       /  E       \ \
	 *      /____________\/ D
	 *    B               C
	 */

	private static Shape [][] get3DHorizontalProportionalConicShapesGroup(float x, float y,
		  float w, float h, float proX, float proY, float right, float left ) {

		Shape [][] shapess3D = new Shape[6][];

		float AX = (float) ( right + proX/2.0 );
		float AY = (float) ( y + h/2.0 + proY/2.0 );

		float BX = left, BY = y;
		float CX = BX, CY = BY + h;
		float DX = CX + proX, DY = CY + proY;
		float EX = DX, EY = BY + proY;

		float B2Ratio = (float)( ( right - ( x + w ) ) / (right - left) );
		float B3Ratio = (float) ( ( right - x ) / (right - left) );

		float B2X = AX + (BX - AX)*B2Ratio, B2Y = AY + (BY - AY)*B2Ratio;
		float C2X = AX + (CX - AX)*B2Ratio, C2Y = AY + (CY - AY)*B2Ratio;
		float D2X = AX + (DX - AX)*B2Ratio, D2Y = AY + (DY - AY)*B2Ratio;
		float E2X = AX + (EX - AX)*B2Ratio, E2Y = AY + (EY - AY)*B2Ratio;

		float B3X = AX + (BX - AX)*B3Ratio, B3Y = AY + (BY - AY)*B3Ratio;
		float C3X = AX + (CX - AX)*B3Ratio, C3Y = AY + (CY - AY)*B3Ratio;
		float D3X = AX + (DX - AX)*B3Ratio, D3Y = AY + (DY - AY)*B3Ratio;
		float E3X = AX + (EX - AX)*B3Ratio, E3Y = AY + (EY - AY)*B3Ratio;

		if( proX == 0 ) {

		    GeneralPath frontShape = new GeneralPath();

		    if( proY > 0 ) {

			frontShape.moveTo( E2X, E2Y );
			frontShape.lineTo( E3X, E3Y );
			frontShape.lineTo( C3X, C3Y );
			frontShape.lineTo( C2X, C2Y );
			frontShape.closePath();

		    } else {

			frontShape.moveTo( B2X, B2Y );
			frontShape.lineTo( B3X, B3Y );
			frontShape.lineTo( D3X, D3Y );
			frontShape.lineTo( D2X, D2Y );
			frontShape.closePath();

		    }

		    shapess3D[ 0 ] = new Shape [] { frontShape };

		    return shapess3D;

		}

		// creates right cone shape

		double rw = Math.abs( E2X - B2X );
		double rh = C2Y - B2Y;

		double tx = B2X;
		double ty = B2Y;

		double shy = proY/proX;

		Shape rightConeShape = new Ellipse2D.Double( 0, 0, rw, rh );

		AffineTransform at = AffineTransform.getTranslateInstance( tx, ty );

		at.shear( 0.0, shy );

		rightConeShape = at.createTransformedShape( rightConeShape );

		rightConeShape = ShapeUtilities.getShapeProcessedByHorizontalBeam( rightConeShape );

		// end of creating right cone shape

		// creates left cone shape

		rw = Math.abs( E3X - B3X );
		rh = C3Y - B3Y;

		Shape leftConeShape = new Ellipse2D.Double( 0, 0, rw, rh );

		tx = B3X;
		ty = B3Y;

		at = AffineTransform.getTranslateInstance( tx, ty );

		at.shear( 0.0, shy );

		leftConeShape = at.createTransformedShape( leftConeShape );

		leftConeShape = ShapeUtilities.getShapeProcessedByHorizontalBeam( leftConeShape );

		// end of creating left cone shape

		// creates front shape

		Path rightConeShapePath = new Path( rightConeShape );

		int pathNum = rightConeShapePath.getPathElements().length;

		float [] rightConeTopEdge;
		float [] rightConeBottomEdge;

		if( pathNum == 0 ) {

		    rightConeTopEdge = rightConeBottomEdge = new float [] { AX, AY };

		} else {

		    PathElement [] rightConeEdges = rightConeShapePath.getHorizontalEdgePathElements();

		    rightConeTopEdge = rightConeEdges[ 0 ].getPoint();
		    rightConeBottomEdge = rightConeEdges[ 1 ].getPoint();

		}

		Path leftConeShapePath = new Path( leftConeShape );

		PathElement [] leftConeEdges = leftConeShapePath.getHorizontalEdgePathElements();

		float [] leftConeTopEdge = leftConeEdges[ 0 ].getPoint();
		float [] leftConeBottomEdge = leftConeEdges[ 1 ].getPoint();

		GeneralPath frontShape = new GeneralPath();

		frontShape.moveTo( rightConeTopEdge[0], rightConeTopEdge[1] );
		frontShape.lineTo( rightConeBottomEdge[0], rightConeBottomEdge[1] );
		frontShape.lineTo( leftConeBottomEdge[0], leftConeBottomEdge[1] );
		frontShape.lineTo( leftConeTopEdge[0], leftConeTopEdge[1] );
		frontShape.closePath();

		if( proX > 0 ) {

		     Area frontShapeArea = new Area( frontShape );
		     Area rightConeShapeArea = new Area( rightConeShape );
		     Area leftConeShapeArea = new Area( leftConeShape );

		     frontShapeArea.subtract( rightConeShapeArea );
		     frontShapeArea.add( leftConeShapeArea );

		     shapess3D[ 0 ] = new Shape [] { frontShapeArea };

		} else if( proX < 0 ) {

		     Area frontShapeArea = new Area( frontShape );
		     Area rightConeShapeArea = new Area( rightConeShape );
		     Area leftConeShapeArea = new Area( leftConeShape );

		     frontShapeArea.add( rightConeShapeArea );
		     frontShapeArea.subtract( leftConeShapeArea );

		     shapess3D[ 0 ] = new Shape [] { frontShapeArea };

		     shapess3D[ 3 ] = new Shape [] { leftConeShape };

		}

		return shapess3D;

	}

	public static Shape [][] get3DHorizontalSimplePyramidShapesGroup(float tx, float ty, Rectangle2D rect, float proX, float proY ) {

		float x = (float) rect.getX() + tx ;
		float y = (float) rect.getY() + ty ;
		float w = (float) rect.getWidth();
		float h = (float) rect.getHeight();

		return get3DHorizontalSimplePyramidShapesGroup( x, y, w, h, proX, proY );

	}

	private static Shape [][] get3DHorizontalSimplePyramidShapesGroup(float x, float y,
		  float w, float h, float proX, float proY) {

		Shape [][] shapess3D = new Shape[6][];

		float topPyramidX = (float)( x + w + proX/2.0 );
		float topPyramidY = (float)( y + h/2.0 + proY/2.0 );

		GeneralPath frontPyramid = new GeneralPath();

		frontPyramid.moveTo( topPyramidX, topPyramidY );
		frontPyramid.lineTo( x, y );
		frontPyramid.lineTo( x, y + h );
		frontPyramid.closePath();

		shapess3D[ 0 ] = new Shape [] { frontPyramid };

		if( proY > 0 ) {

		    GeneralPath rightPyramid = new GeneralPath();

		    rightPyramid.moveTo( topPyramidX, topPyramidY );
		    rightPyramid.lineTo( x, y + h );
		    rightPyramid.lineTo( x + proX, y + h + proY );
		    rightPyramid.closePath();

		    shapess3D[ 5 ] = new Shape [] { rightPyramid };

		} else if( proY < 0 ) {

		    GeneralPath leftPyramid = new GeneralPath();

		    leftPyramid.moveTo( topPyramidX, topPyramidY );
		    leftPyramid.lineTo( x, y );
		    leftPyramid.lineTo( x + proX, y + proY );
		    leftPyramid.closePath();

		    shapess3D[ 4 ] = new Shape [] { leftPyramid };

		}

		if( proX < 0 ) {

		    GeneralPath bottomPyramid = new GeneralPath();

		    bottomPyramid.moveTo( x, y );
		    bottomPyramid.lineTo( x, y + h );
		    bottomPyramid.lineTo( x + proX, y + h + proY );
		    bottomPyramid.lineTo( x + proX, y + proY );
		    bottomPyramid.closePath();

		    shapess3D[ 3 ] = new Shape [] { bottomPyramid };

		}

		return shapess3D;

	}

	public static Shape [][] get3DHorizontalCylindricShapesGroup(float tx, float ty, Rectangle2D rect, float proX, float proY ) {

		float x = (float) rect.getX() + tx ;
		float y = (float) rect.getY() + ty ;
		float w = (float) rect.getWidth();
		float h = (float) rect.getHeight();

		return get3DHorizontalCylindricShapesGroup( x, y, w, h, proX, proY );

	}

	private static Shape [][] get3DHorizontalCylindricShapesGroup(float x, float y,
		  float w, float h, float proX, float proY) {

		Shape [][] shapess3D = new Shape[6][];

		if( proX == 0 ) {

			double caliH = Math.abs( proY ); // calibration height

			shapess3D[ 0 ] = new Shape [ ] { new Rectangle2D.Double( x, y, w, h + caliH ) };

			return shapess3D;

		}

		double rw = Math.abs( proX );
		double rh = h;
		double ry = y;
		double rx = x + w - rw/2.0;

		double shy = proY/proX;

		Shape topEllipse = new Ellipse2D.Double( rx, ry, rw, rh );

		AffineTransform at = AffineTransform.getShearInstance( 0.0, shy );

		topEllipse = at.createTransformedShape( topEllipse );

		topEllipse = ShapeUtilities.getShapeWithCorrectBounds( topEllipse );

		Rectangle2D topEllipseBounds = topEllipse.getBounds2D();

		double tx = x + w - topEllipseBounds.getX();

		double ty = y - topEllipseBounds.getY();

		at = AffineTransform.getTranslateInstance( tx, ty );

		topEllipse = at.createTransformedShape( topEllipse );

		topEllipse = ShapeUtilities.getShapeProcessedByHorizontalBeam( topEllipse );

		Path topEllipsePath = new Path( topEllipse );

		PathElement edges [] = topEllipsePath.getHorizontalEdgePathElements();

		PathElement topEdge = edges[ 0 ];

		PathElement bottomEdge = edges[ 1 ];

		float [] topEdgePoint = topEdge.getPoint();
		float [] bottomEdgePoint = bottomEdge.getPoint();

		GeneralPath frontShape = new GeneralPath();

		frontShape.moveTo( topEdgePoint[0], topEdgePoint[1] );
		frontShape.lineTo( bottomEdgePoint[0], bottomEdgePoint[1] );
		frontShape.lineTo( bottomEdgePoint[0] - w, bottomEdgePoint[1] );
		frontShape.lineTo( topEdgePoint[0] - w, topEdgePoint[1] );
		frontShape.closePath();

		Shape bottomEllipse = AffineTransform.getTranslateInstance( - w,  0 ).createTransformedShape( topEllipse );

		Area frontArea = new Area( frontShape );

		if( proX > 0 ) {

			Area topArea = new Area( topEllipse );
			Area bottomArea = new Area( bottomEllipse );

			frontArea.subtract( topArea );
			frontArea.add( bottomArea );

			shapess3D[ 0 ] = new Shape [] { frontArea };

			shapess3D[ 4 ] = new Shape [ ] { topEllipse };

		} else {

			Area topArea = new Area( topEllipse );
			Area bottomArea = new Area( bottomEllipse );

			frontArea.subtract( bottomArea );
			frontArea.add( topArea );

			shapess3D[ 0 ] = new Shape [] { frontArea };

			shapess3D[ 5 ] = new Shape [ ] { bottomEllipse };

		}

		return shapess3D;

	}

	public static void applyAffineTransform(AffineTransform at, Shape [][] shapesGroup) {

		for(int i = 0, iLen = shapesGroup.length; i < iLen; i ++ ) {

			Shape [] shapes = shapesGroup[ i ];

			if( shapes == null ) {

				continue;

			}

			for( int k = 0, kLen = shapes.length; k < kLen; k ++ ) {

				if( shapes[ k ] == null ) {

					continue;

				}

				shapes[ k ] = at.createTransformedShape( shapes[ k ] );

			}

		}

	}

	public static Shape [][] get3DBarInsideShapesGroup(Rectangle2D rect, float proX, float proY ) {

		rect.setRect( rect.getX() + proX, rect.getY() + proY, rect.getWidth(), rect.getHeight() );

		Shape [][] shapess3D = get3DBarShapesGroup( 0, 0, rect, - proX, - proY );

		if( proY >= 0 ) {

			shapess3D[4 ] = shapess3D[5] = null;

		}

		return shapess3D;

	}

	public static Shape [][] get3DHorizontalLinearInsideShapesGroup(Rectangle2D rect, float proX, float proY ) {

		Shape [][] shapes3D = new Shape[6][];

		float x = (float) rect.getX(), y = (float) rect.getY(),
			w = (float) rect.getWidth();

		if( proX >= 0 ) {

			shapes3D[ 4 ] = new Shape [] {
							   new Line2D.Double( x, y, x + proX, y + proY ),
							   new Line2D.Double( x + proX, y + proY, x + proX + w, y + proY )
							   };

		} else {

			shapes3D[ 4 ] = new Shape [] {
							   new Line2D.Double( x + proX, y + proY, x + proX + w, y + proY ),
							   new Line2D.Double( x + proX + w, y + proY, x + w, y )
							   };

		}

		return shapes3D;

	}

	public static Shape [][] get3DVerticalLinearInsideShapesGroup(Rectangle2D rect, float proX, float proY ) {

		Shape [][] shapes3D = new Shape[6][];

		float x = (float) rect.getX(), y = (float) rect.getY(),
			h = (float) rect.getHeight();

		shapes3D[ 4 ] = new Shape [] {
						   new Line2D.Double( x, y + h, x + proX, y + h + proY ),
						   new Line2D.Double( x + proX, y + h + proY, x + proX, y + proY )
						   };

		return shapes3D;

	}
}