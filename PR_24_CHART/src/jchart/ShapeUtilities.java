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
import java.util.*;
import jcosmos.*;
import com.jcosmos.java3d.*;

public class ShapeUtilities {

      /**
      * returns control points of a circle
      * control points are center point and two vertexes
      */
	public static Point2D [] getPieControlPoints(Shape pie) {

	    Vector vertexes = new Vector();

	    PathIterator pi = pie.getPathIterator( null ); // path iterator

	    while( ! pi.isDone() ) {

		double [] coords = new double[ 6 ];

		Point2D vertex = null;

		int type = pi.currentSegment( coords );

		switch( type ) {

		    case PathIterator.SEG_MOVETO :
			    vertex = new Point2D.Double( coords[0], coords[1] );
			  break;

		    case PathIterator.SEG_LINETO :
			    vertex = new Point2D.Double( coords[0], coords[1] );
			  break;

		    case PathIterator.SEG_QUADTO :
			    vertex = new Point2D.Double( coords[2], coords[3] );
			  break;

		    case PathIterator.SEG_CUBICTO :
			    vertex = new Point2D.Double( coords[4], coords[5] );
			  break;

		    case PathIterator.SEG_CLOSE :
			    vertex = null;
			 break;

		}

		if( vertex != null ) {

		    vertexes.addElement( vertex );

		}

		pi.next();

	    }

	    int size = vertexes.size();

	    Point2D points [] = new Point2D [3];

	    points[ 0 ] = (Point2D) vertexes.elementAt(size - 1);         // rootVertex
	    points[ 1 ] = (Point2D) vertexes.elementAt( size - 2 );      // otherVertex
	    points[ 2 ] = (Point2D) vertexes.elementAt( 0 );         // feedbackVertex

	    return points;

      }

      public static Shape getShapeProcessedByVerticalBeam(Shape shape) {

		shape = getShapeWithCorrectBounds( shape );

		Rectangle2D bounds = shape.getBounds2D();

		double x = bounds.getX();
		double y = bounds.getY();
		double w = bounds.getWidth();
		double h = bounds.getHeight();

		bounds = new Rectangle2D.Double( x, y - 10, w, h + 20 );

		Area exclusiveArea = new Area( bounds );
		Area shapeArea = new Area( shape );
		Area bottomArea = new Area( bounds );

		exclusiveArea.exclusiveOr( shapeArea );

		bottomArea.subtract( exclusiveArea );

		return bottomArea ;

	}

	public static Shape getShapeProcessedByHorizontalBeam(Shape shape) {

		shape = getShapeWithCorrectBounds( shape );

		Rectangle2D bounds = shape.getBounds2D();

		double x = bounds.getX();
		double y = bounds.getY();
		double w = bounds.getWidth();
		double h = bounds.getHeight();

		bounds = new Rectangle2D.Double( x - 10, y, w + 20, h );

		Area exclusiveArea = new Area( bounds );
		Area shapeArea = new Area( shape );
		Area bottomArea = new Area( bounds );

		exclusiveArea.exclusiveOr( shapeArea );

		bottomArea.subtract( exclusiveArea );

		return bottomArea ;

	}

	public static Shape getShapeWithCorrectBounds(Shape shape) {

		Rectangle2D bounds = shape.getBounds2D();

		double x = bounds.getX();
		double y = bounds.getY();
		double w = bounds.getWidth();
		double h = bounds.getHeight();

		bounds = new Rectangle2D.Double( x - 10, y - 10, w + 20, h + 20 );

		Area exclusiveArea = new Area( bounds );
		Area shapeArea = new Area( shape );
		Area bottomArea = new Area( bounds );

		exclusiveArea.exclusiveOr( shapeArea );

		bottomArea.subtract( exclusiveArea );

		return bottomArea ;

	}

	public static Point [] getVertexes(Shape shape) {

		if( shape == null ) {

			return new Point [] { } ;

		}

		Vector vertexes = new Vector();

		PathIterator pi = shape.getPathIterator( null ); // path iterator

		while( ! pi.isDone() ) {

			double [] coords = new double[6];

			int type = pi.currentSegment( coords );

			Point vertex = null;

			switch( type ) {
			    case PathIterator.SEG_MOVETO:
				    vertex = new Point( (int) coords[0], (int) coords[1] );
				    break;
			    case PathIterator.SEG_LINETO:
				    vertex = new Point( (int) coords[0], (int) coords[1] );
				    break;
			    case PathIterator.SEG_QUADTO:
				    vertex = new Point( (int) coords[2], (int) coords[3] );
				    break;
			    case PathIterator.SEG_CUBICTO:
				    vertex = new Point( (int) coords[4], (int) coords[5] );
				    break;
			    case PathIterator.SEG_CLOSE :
				    vertex = null;
				    break;
			}

			if( vertex != null ) {

				vertexes.addElement( vertex );

			}

			pi.next();

		}

		int len = vertexes.size();

		Point [] points = new Point[ len ];

		Point p;

		Enumeration enumIt = vertexes.elements();

		int i = 0;

		while( enumIt.hasMoreElements() ) {

			points[ i ] = (Point) enumIt.nextElement();

			i ++;

		}

		return points;

	}

	public static Point2D [] getVertexesPoint2D(Shape shape) {

		if( shape == null ) {

			return new Point2D [] { } ;

		}

		Vector vertexes = new Vector();

		PathIterator pi = shape.getPathIterator( null ); // path iterator

		while( ! pi.isDone() ) {

			double [] coords = new double[6];

			int type = pi.currentSegment( coords );

			Point2D vertex = null;

			switch( type ) {

			    case PathIterator.SEG_MOVETO:
				    vertex = new Point2D.Double( coords[0], coords[1] );
				    break;
			    case PathIterator.SEG_LINETO:
				    vertex = new Point2D.Double( coords[0], coords[1] );
				    break;
			    case PathIterator.SEG_QUADTO:
				    vertex = new Point2D.Double( coords[2], coords[3] );
				    break;
			    case PathIterator.SEG_CUBICTO:
				    vertex = new Point2D.Double( coords[4], coords[5] );
				    break;
			    case PathIterator.SEG_CLOSE :
				    vertex = null;
				    break;
			}

			if( vertex != null ) {
				vertexes.addElement( vertex );
			}

			pi.next();

		}

		int len = vertexes.size();

		Point2D [] points = new Point2D[ len ];

		Point2D p;

		Enumeration enumIt = vertexes.elements();

		int i = 0;

		while( enumIt.hasMoreElements() ) {

			points[ i ] = (Point2D) enumIt.nextElement();

			i ++;

		}

		return points;

	}

	public static boolean hasAnyQuadToOrCubicToPath(Shape shape) {

	    PathIterator pi = shape.getPathIterator( null );

	    int type;

	    float [] coords = new float[ 6 ];

	    while( ! pi.isDone() ) {

	      type = pi.currentSegment( coords );

	      if( type == PathIterator.SEG_QUADTO || type == PathIterator.SEG_CUBICTO ) {

		return true;

	      }

	      pi.next();

	    }

	    return false;

	}

	public static Shape getShapeMerged(Shape one, Shape two) {

	  Area oneArea = new Area( one );

	  oneArea.add( new Area( two ) );

	  return oneArea;

	}

	public static Shape [] mergeShapesHavingQuadToOrCubicToPath(Shape [] shapes) {

	    // clone original shapes

	    Shape [] clonedShapes = new Shape[ shapes.length ];

	    AffineTransform at = AffineTransform.getTranslateInstance( 0, 0 );

	    for( int i = 0, len = shapes.length; i < len; i ++ ) {

	      Utility.debug( ShapeUtilities.class , "CLONING ......" );

	      clonedShapes[ i ] = at.createTransformedShape( shapes[ i ] );

	    }

	    Utility.debug( ShapeUtilities.class, "DONE CLONING!" );

	    shapes = clonedShapes;

	    // end of cloning original shapes

	    Vector shapeListMerged = new Vector();

	    Shape currShape, nextShape ;

	    for( int i = 0, len = shapes.length ; i < len ; i ++ ) {

	      Utility.debug( ShapeUtilities.class, "PROCESSING MERGING ....... " );

	      currShape = shapes[ i ];
	      nextShape = i < len -1 ? shapes[ i + 1 ] : null;

	      if( nextShape != null && hasAnyQuadToOrCubicToPath( currShape ) && hasAnyQuadToOrCubicToPath( nextShape ) ) {

		Shape shapeMerged = getShapeMerged( currShape, nextShape );

		shapes[ i + 1 ] = shapeMerged;

	      } else {

		shapeListMerged.addElement( currShape );

	      }

	    }

	    Shape [] shapesMerged = new Shape[ shapeListMerged.size() ];

	    for( int i = 0, len = shapesMerged.length; i < len; i ++ ) {

	      shapesMerged[ i ] = (Shape) shapeListMerged.get( i );

	    }

	    return shapesMerged;

	}

	public static Shape [] filterShapesNotInterSects(Shape refShape, Shape [] shapes ) {

		Vector filteredShapes = new Vector();

		Area refArea = new Area( refShape );

		for(int i = 0, len = shapes.length; i < len; i ++ ) {

		  Area area =  new Area( shapes[i] );

		  area.intersect( refArea );

		  Rectangle2D bounds = area.getBounds2D();

		  if( bounds.getWidth() == 0 && bounds.getHeight() == 0 ) {

		    filteredShapes.add( shapes[i] );

		  }

		}

		shapes = new Shape [ filteredShapes.size() ];

		Enumeration enumIt = filteredShapes.elements();

		for(int i = 0, len = shapes.length; i < len; i ++ ) {

		   shapes[ i ] = (Shape) ( enumIt.nextElement() );

		}

		return shapes;

	}

	public static Shape getArcRotatedBy(double radian, Shape arc) {

	    Point2D [] ctrlPnts = ShapeUtilities.getPieControlPoints( arc );

	    Point2D center = ctrlPnts[0];

	    AffineTransform at = AffineTransform.getRotateInstance( radian, center.getX(), center.getY() );

	    return at.createTransformedShape( arc );

	}

//	public static Point2D [] getDonutControlPoints(Shape donut, double cx, double cy) {
//
//	    donut = getShapeWithCorrectBounds( donut );
//
//	    Vector pntsVec = null;
//
//	    float theta = 0;
//
//	    double PI = Math.PI;
//
//	    double incRad = PI/11.0;
//
//	    for( float i = 0; i <= 2.0*PI; i += incRad ) {
//
//	       AffineTransform at = AffineTransform.getRotateInstance( i, cx, cy );
//
//	       Shape donutRotated = at.createTransformedShape( donut );
//
//	       pntsVec = getLinePoints( new Path( donutRotated ).getPathElements() ) ;
//
//	       Utility.debug(ShapeUtilities.class, "PNTS VEC NUM = " + pntsVec.size() );
//
//	       if( pntsVec.size() == 4 ) {
//
//		  theta = i;
//
//		  break;
//
//	       }
//
//	       pntsVec = null;
//
//	    }
//
//	    if( pntsVec == null ) {
//
//	      return null;
//
//	    }
//
//	    Point2D points [] = new Point2D [ 4 ];
//
//	    AffineTransform at = AffineTransform.getRotateInstance( - theta, cx, cy );
//
//	    for(int i = 0, len = points.length; i < len ; i ++ ) {
//
//	      float [] point = ( float [] ) ( pntsVec.elementAt( i ) );
//
//	      points[ i ] = at.transform( new Point2D.Float( point[0], point[1] ), null );
//
//	    }
//
//	    Utility.debug(ShapeUtilities.class, "PNTS ARRAY NUM = " + points.length );
//
//	    return points;
//
//	}

	private static Vector getLinePoints(PathElement [] elems) {

	    Vector points = new Vector();

	    for( int i = 0, len = elems.length; i < len ; i ++ ) {

	      PathElement elem = elems[ i ];

	      if( elem.type == PathIterator.SEG_LINETO ) {

		points.add( elems[ i - 1].getPoint() );
		points.add( elem.getPoint() );

	      }

	    }

	    return points;

	}

	public static GeneralPath createGeneralPath(final Point2D [] points) {

	    GeneralPath gp = new GeneralPath();

	    final int len = points.length;

	    if( len > 0 ) {

	      gp.moveTo( (float) points[0].getX(), (float) points[0].getY() );

	    }

	    for( int i = 1; i < len; i ++ ) {

	      Point2D p = points[ i ];

	      gp.lineTo( (float) p.getX(), (float) p.getY() );

	    }

	    if( len > 0 ) {

	      gp.closePath();

	    }

	    return gp;

	}

	// counter clock wise points

	public static Point2D [] getPointsCounterClockWise(final Point2D [] points) {

	   if( VectorUtilities.isCounterClockWise( points ) ) {

	      return points;

	   }

	   return getPointsReversed( points );

	}

	public static Point2D [] getPointsClockWise(final Point2D [] points) {

	   if( VectorUtilities.isCounterClockWise( points ) ) {

	      return getPointsReversed( points );

	   }

	   return points;

	}

	private static Point2D [] getPointsReversed(final Point2D [] points ) {

	   final int len = points.length;

	   final Point2D [] reversedPoints = new Point2D[ len ];

	   for(int i = 0; i < len; i ++ ) {

	      reversedPoints[ i ] = points[ len - i - 1];

	   }

	   return reversedPoints;

	}

  public static Arc2D getArc( double cx, double cy, double r, double start, double extent, int type ) {

       return new Arc2D.Double( cx - r, cy -r, 2*r, 2*r, start, extent, type);

  }

  public static Shape getDonut( double cx, double cy, double r0, double r1,
				 double start, double extent ) {

    final Shape outerArc = getArc( cx, cy, r0, start, extent, Arc2D.OPEN );
    final Shape innerArc = getArc( cx, cy, r1, start, extent, Arc2D.OPEN );

    final Path outerArcPath = new Path( outerArc );
    final Path innerArcPath = new Path( innerArc );

    final GeneralPath donut = outerArcPath.getGeneralPath();

    final PathElement [] innerArcPathElems = innerArcPath.getPathElements();

    final int innerPathElemsLen = innerArcPathElems.length;

    final float [] lastPathElemPointOfInnerArc =
			       innerArcPathElems[ innerPathElemsLen - 1 ].getPoint();

    donut.lineTo( lastPathElemPointOfInnerArc[ 0 ], lastPathElemPointOfInnerArc[ 1 ] );

    for( int i = innerPathElemsLen - 1; i > 0 ; i -- ) {

       float [] nextPoint = innerArcPathElems[ i - 1 ].getPoint();

       innerArcPath.makeBackwardPath( donut, innerArcPathElems[ i ], nextPoint, 0 );

    }

    donut.closePath();

    return donut;

  }

  public static Point2D [] getControlPointsOfDonut( Shape donut ) {

    Point2D points [] = new Point2D[ 4 ];

    PathElement [] elems = new Path( donut ).getPathElements();

    PathElement elem;

    int len = elems.length;

    float [] onePoint = elems[ 0 ].getPoint();

    points[ 0 ] = new Point2D.Double( onePoint[ 0 ], onePoint[ 1 ] );

    for( int i = 0; i < len; i ++ ) {

      elem = elems[ i ];

      if( elem.type == PathIterator.SEG_LINETO ) {

	float [] twoPoint = elems[ i - 1 ].getPoint();

	points[ 1 ] = new Point2D.Double( twoPoint[ 0 ], twoPoint[ 1 ] );

	float [] threePoint = elem.getPoint();

	points[ 2 ] = new Point2D.Double( threePoint[ 0 ], threePoint[ 1 ] );

	break;

      }

    }

    float [] fourPoint = elems[ len - 2 ].getPoint();

    points[ 3 ] = new Point2D.Double( fourPoint[ 0 ], fourPoint[ 1 ] );

    return points;

  }

  public static Shape getRotatedShape( double x, double y, Shape shape, double radian ) {

     return AffineTransform.getRotateInstance( radian, x, y ).createTransformedShape( shape );

  }

  private static AffineTransform atClone = AffineTransform.getTranslateInstance( 0, 0 );

  public static Shape getShapeCloned(Shape shape) {

    return atClone.createTransformedShape( shape );

  }

}
