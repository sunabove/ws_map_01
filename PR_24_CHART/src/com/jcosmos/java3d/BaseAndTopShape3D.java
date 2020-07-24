package com.jcosmos.java3d;

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
import jchart.*;
import java.util.*;
import jcosmos.*;

public abstract class BaseAndTopShape3D extends Shape3D {

  private Rectangle2D base2DShapeBounds;

  private double height;
  private double baseZ;
  private double topShapeRatio;

  private Point3DGroup [] sideShapesPoint3DGroups;
  private Point3DGroup    baseShapePoint3DGroup;
  private Point3DGroup    topShapePoint3DGroup;

  public BaseAndTopShape3D( final ShapeStyle style,
		  final double baseZ, final Rectangle2D base2DShapeBounds,
		  final double topShapeRatio, final double height ) {

    super( style );

    this.baseZ = baseZ;
    this.height = height;

    this.topShapeRatio = topShapeRatio;

    this.base2DShapeBounds = base2DShapeBounds;

  }

  public Rectangle2D getBase2DShapeBounds() {

    return base2DShapeBounds;

  }

  public Cube3D getBoundsCube3D() {

    // base shape bounds
    final Rectangle2D bsb = get2DBaseShape().getBounds2D();

    final double baseZ = this.baseZ;

    return

	 new Cube3D(
	     new Point3D( bsb.getX(), bsb.getY(), baseZ ),
	     new Point3D( bsb.getX() + bsb.getWidth(), bsb.getY() + bsb.getHeight(), baseZ + height )
	 );

  }

  abstract public Shape get2DBaseShape() ;

  private Point2D getCenter(final Shape shape) {

    final Rectangle2D bounds = shape.getBounds2D();

    return new Point2D.Double( bounds.getX() + bounds.getWidth()/2.0, bounds.getY() + bounds.getHeight()/2.0 );

  }

  private Point3DGroup buildPoint3DGroup( final Point2D [] points, final double z,
					  final Point3D normalVector) {

    final int len = points.length;

    final Point3D [] point3Ds = new Point3D[ len ];

    for(int i = 0; i < len; i ++ ) {

      point3Ds[ i ] = new Point3D( points[ i ], z );

    }

    return new Point3DGroup( point3Ds, normalVector );

  }

  private void build3DShapes() {

    final double h = getHeight();

    final double baseZ = this.baseZ;

    final double topZ = baseZ + h;

    final Point2D [] baseVertexesClockWise = getBaseVertexesPoint2DClockWise();

    this.baseShapePoint3DGroup = buildPoint3DGroup( baseVertexesClockWise, baseZ, K_MINUS_CARET );

    final int len = baseVertexesClockWise.length;

    // 탑 버텍스는 베이스 버텍스를 기준으로 하기 때문에, CW로 정렬되어 있다.
    final Point2D [] topVertexesCockWise = getTopVertexesPoint2D( baseVertexesClockWise );

    final Point3DGroup point3DGroups [] = new Point3DGroup[ len ];

    for(int i = 0; i < len; i ++ ) {

       //          k
       //                   i
       //          |
       //          |        |
       //          |        |
       //          |        |  v
       //          |        |
       //                   |
       //              u
       //          k        i
       //
       //
       //

       final int k = ( i + 1 > len -1 ) ? 0 : i + 1 ;

       final Point3D [] points = new Point3D [] {

		    new Point3D( baseVertexesClockWise[ k ], baseZ ),
		    new Point3D( baseVertexesClockWise[ i ], baseZ ),
		    new Point3D( topVertexesCockWise[ i ], topZ ),
		    new Point3D( topVertexesCockWise[ k ], topZ )

	       };

       final Point3D u = points[0].getVectorTo( points[1] );
       final Point3D v = points[1].getVectorTo( points[2] );

       final Point3D normalVector = u.getCrossProduct( v ).getUnitVector();

       point3DGroups[ i ] = new Point3DGroup( points, normalVector );

    }

    this.sideShapesPoint3DGroups = point3DGroups;

    // 탑 쉐입 삼차원 포인트 그룹 생성시에
    // 탑 버텍스를 CCW로 다시 정렬하여 생성한다.
    this.topShapePoint3DGroup = buildPoint3DGroup(
				  ShapeUtilities.getPointsCounterClockWise( topVertexesCockWise ),
				  topZ,
				  K_CARET
				);


  }

  public Point2D [] getBaseVertexesPoint2DClockWise() {

    return new GeneralCurve( get2DBaseShape() ).getPoint2DsClockWise();

  }

  private Point2D [] getTopVertexesPoint2D( final Point2D [] base2DShapeVertexes) {

    final Point2D center = getCenter( get2DBaseShape() );

    AffineTransform at = AffineTransform.getTranslateInstance( center.getX(), center.getY() );

    final double topShapeRatio = this.topShapeRatio;

    at.scale( topShapeRatio, topShapeRatio );

    at.translate( -center.getX(), - center.getY() );

    final int len = base2DShapeVertexes.length;

    final Point2D [] top2DShapeVertexes = new Point2D[ len ];

    for(int i = 0; i < len ; i ++ ) {

       top2DShapeVertexes[ i ] = at.transform( base2DShapeVertexes[ i], null );

    }

    return top2DShapeVertexes;

  }

  private double getHeight() {

    return height;

  }

  private Point3DGroup [] getSideShapesPoint3DGroups() {

    if( sideShapesPoint3DGroups == null ) {

       build3DShapes();

    }

    return sideShapesPoint3DGroups;

  }

  public Point3DGroup [] getPoint3DGroups( ) {

    final Point3DGroup [] sideShapesPoint3DGroups = getSideShapesPoint3DGroups();

    final int len = sideShapesPoint3DGroups.length;

    final Point3DGroup [] point3DGroups = new Point3DGroup[ len + 2 ];

    point3DGroups[ 0 ] = topShapePoint3DGroup;
    point3DGroups[ 1 ] = baseShapePoint3DGroup;

    System.arraycopy( sideShapesPoint3DGroups, 0, point3DGroups, 2, len );

    return point3DGroups;

  }

}