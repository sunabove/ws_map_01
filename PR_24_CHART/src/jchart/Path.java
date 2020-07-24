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

public class Path {

  public Path(Shape shape) {

      PathIterator pi = shape.getPathIterator( null );

      Vector pathList = new Vector();

      float [] moveCoords = null;

      while( ! pi.isDone() ) {

	  float [] coords = new float[ 6 ];

	  int type = pi.currentSegment( coords );

	  if( type == PathIterator.SEG_MOVETO ) {

	      moveCoords = coords;

	  } else if( type == PathIterator.SEG_CLOSE ) {

	      coords = moveCoords;

	  }

	  pathList.add( new PathElement( type, coords ) );

	  pi.next();

      }

      int len = pathList.size();

      PathElement [] pathElems = new PathElement[ len ];

      for(int i = 0; i < len; i ++ ) {

	   pathElems[ i ] = (PathElement) pathList.elementAt( i );

      }

      this.pathElems = pathElems;

  }

  public Path(PathElement [] pathElems) {

      this.pathElems = pathElems;

  }

  public static Shape getBottomShape(Shape one, Shape two) {

      Rectangle2D oneBounds = one.getBounds2D();
      Rectangle2D twoBounds = two.getBounds2D();

      double oneY = oneBounds.getY();
      double oneH = oneBounds.getHeight();

      double twoY = twoBounds.getY();
      double twoH = twoBounds.getHeight();

      if( twoY > oneY ) {

	  return two;

      } else if( twoY + twoH > oneY + oneH ) {

	  return two;

      } else {

	  return one;

      }

  }

  public static Shape getTopShape(Shape one, Shape two) {

      Shape bottomShape = getBottomShape( one, two );

      return ( bottomShape == one ? two : one );

  }

  public Path [] getPathsSepratedByVerticalBeam() {

     PathElement [] verticalEdges = getVerticalEdgePathElements();

     PathElement leftEdge = verticalEdges[ 0 ];
     PathElement rightEdge = verticalEdges[ 1 ];

     PathElement [] pathElems = this.pathElems;

     Vector onePathList = new Vector(), twoPathList = new Vector();

     int i = 0;

     PathElement elem;

     // search left edge

     for(   ; ; ) {

	 elem = pathElems[ i ];

	 if( elem == leftEdge ) {
	      break;
	 }

	 i ++;

     }

     // end of searching left edge

     // construct one path list from left to right edge

     int elemLen = pathElems.length;

     for( ; ; ) {

	 onePathList.add( elem );

	 if( elem == rightEdge ) {

	    break;

	 }

	 if( i == elemLen - 1 ) {

	     i = 0;

	 } else {

	     i ++;

	 }

	 elem = pathElems[ i ];

     }

     // end of construction one path list

     // construct two path list from right to left edge

     for( ; ; ) {

	 twoPathList.add( elem );

	 if( elem == leftEdge ) {

	    break;

	 }

	 if( i == elemLen - 1 ) {

	     i = 0;

	 } else {

	     i ++;

	 }

	 elem = pathElems[ i ];

     }

     // end of construction two path list

     int onePathElemsSize = onePathList.size();

     PathElement [] onePathElems = new PathElement [ onePathElemsSize ];

     for( i = 0; i < onePathElemsSize ; i ++ ) {

	onePathElems[ i ] = (PathElement) onePathList.elementAt( i );

     }

     int twoPathElemsSize = twoPathList.size();

     PathElement [] twoPathElems = new PathElement[ twoPathElemsSize ];

     for( i = 0; i < twoPathElemsSize ; i ++ ) {

	 twoPathElems[ i ] = (PathElement) twoPathList.elementAt( i );

     }

     return new Path [] { new Path( onePathElems), new Path( twoPathElems) };

  }

  public GeneralPath getGeneralPath() {

     return constructGeneralPath( pathElems );

  }

  private GeneralPath constructGeneralPath(PathElement [] pathElems) {

     GeneralPath gp = new GeneralPath();

     int pathElemsLen = pathElems.length;

     if( pathElemsLen < 1 ) {

	 return null;

     }

     PathElement elem = (PathElement) pathElems[ 0 ];

     float [] point = elem.getPoint();

     gp.moveTo( point[0], point[1] );

     for( int i = 1; i < pathElemsLen ; i ++ ) {

	 elem = pathElems[ i ];

	 makeForwardPath( gp, elem );

     }

     return gp;

  }

  public static void makeForwardPath(GeneralPath gp, PathElement elem) {

    int type = elem.type;

    float [] coords = elem.coords;

    switch( type ) {

	case PathIterator.SEG_CUBICTO :

	     gp.curveTo( coords[0], coords[1], coords[2], coords[3], coords[4], coords[5] );

	     break;

	case PathIterator.SEG_QUADTO :

	     gp.quadTo( coords[0], coords[1], coords[2], coords[3] );

	     break;

	case PathIterator.SEG_LINETO :

	     gp.lineTo( coords[0], coords[1] );

	     break;

	case PathIterator.SEG_MOVETO :

	     gp.moveTo( coords[0], coords[1] );

	     break;

	case PathIterator.SEG_CLOSE :

	     gp.lineTo( coords[0], coords[1] );

	     break;

    }

  }

 private void makeSlashedBackwardPath(GeneralPath gp, PathElement elem, float [] nextPoint, float proX, float proY ) {

    int type = elem.type;

    // 주어진 좌표를 z 만큼씩 이동한다.

    nextPoint = new float [] { nextPoint[0] + proX, nextPoint[1] + proY };

    int len = 6;

    float [] coords = new float [ len ];

    System.arraycopy( elem.coords, 0, coords, 0, len );

    for(int i = 0; i < len ; i ++ ) {

	coords[ i ] += ( ( i%2 == 0 ) ? proX : proY ) ;

    }

    // 끝. 좌표 이동

    switch( type ) {

	case PathIterator.SEG_CUBICTO :

	     gp.curveTo( coords[2], coords[3], coords[0], coords[1], nextPoint[0], nextPoint[1] );

	     break;

	case PathIterator.SEG_QUADTO :

	     gp.quadTo( coords[0], coords[1], nextPoint[0], nextPoint[1] );

	     break;

	case PathIterator.SEG_LINETO :

	     gp.lineTo( nextPoint[0], nextPoint[1] );

	     break;

	case PathIterator.SEG_MOVETO :

	     gp.moveTo( nextPoint[0], nextPoint[1] );

	     break;

	case PathIterator.SEG_CLOSE :

	     gp.lineTo( nextPoint[0], nextPoint[1] );

	     break;

    }

  }

  public void makeBackwardPath(GeneralPath gp, PathElement elem, float [] nextPoint, float z ) {

    int type = elem.type;

    // 주어진 좌표를 z 만큼씩 이동한다.

    nextPoint = new float [] { nextPoint[0], nextPoint[1] + z };

    int len = 6;

    float [] coords = new float [ len ];

    System.arraycopy( elem.coords, 0, coords, 0, len );

    for(int i = 1; z != 0 && i < len ; i += 2 ) {

	coords[ i ] += z;

    }

    // 끝. 좌표 이동

    switch( type ) {

	case PathIterator.SEG_CUBICTO :

	     gp.curveTo( coords[2], coords[3], coords[0], coords[1], nextPoint[0], nextPoint[1] );

	     break;

	case PathIterator.SEG_QUADTO :

	     gp.quadTo( coords[0], coords[1], nextPoint[0], nextPoint[1] );

	     break;

	case PathIterator.SEG_LINETO :

	     gp.lineTo( nextPoint[0], nextPoint[1] );

	     break;

	case PathIterator.SEG_MOVETO :

	     gp.moveTo( nextPoint[0], nextPoint[1] );

	     break;

	case PathIterator.SEG_CLOSE :

	     gp.lineTo( nextPoint[0], nextPoint[1] );

	     break;

    }

  }

  public Shape [] getElligentBanner(float z) {

     PathElement [] pathElems = this.pathElems;

     int len = pathElems.length - 1;

     Shape shapes [] = new Shape [ len ];


     for(int i = 0; i < len ; i ++ ) {

	  shapes[ i ] = getElligentBanner( pathElems[ i ], pathElems[ i + 1 ], z );

     }

     return shapes;

  }

  public Shape [] getSlashedBanner(float proX, float proY) {

     PathElement [] pathElems = this.pathElems;

     int len = pathElems.length - 1;

     Shape shapes [] = new Shape [ len ];


     for(int i = 0; i < len ; i ++ ) {

	  shapes[ i ] = getSlashedBanner( pathElems[ i ], pathElems[ i + 1 ], proX, proY );

     }

     return shapes;

  }

  private Shape getSlashedBanner(PathElement fromElem, PathElement toElem, float proX, float proY) {

     GeneralPath gp = new GeneralPath();

     float [] fromPoint = fromElem.getPoint();

     gp.moveTo(  fromPoint[0], fromPoint[1] );

     makeForwardPath( gp, toElem );

     float [] toPoint = toElem.getPoint();

     gp.lineTo( toPoint[0] + proX, (float) ( toPoint[1] + proY ) );

     makeSlashedBackwardPath( gp, toElem, fromPoint, proX, proY );

     gp.closePath();

     return gp;

  }

  private Shape getElligentBanner(PathElement fromElem, PathElement toElem, float z) {

     GeneralPath gp = new GeneralPath();

     float [] fromPoint = fromElem.getPoint();

     gp.moveTo(  fromPoint[0], fromPoint[1] );

     makeForwardPath( gp, toElem );

     float [] toPoint = toElem.getPoint();

     gp.lineTo( toPoint[0], (float) ( toPoint[1] + z ) );

     makeBackwardPath( gp, toElem, fromPoint, z );

     gp.closePath();

     return gp;

  }

  public PathElement [] getVerticalEdgePathElements() {

     PathElement left = null, right = null;

     PathElement [] pathElems = this.pathElems;

     int len = pathElems.length;

     for(int i = 0; i < len; i ++ ) {

	 PathElement elem = pathElems[ i ];

	 if( left == null ) {

	     left = right = elem;

	     continue;

	 }

	 float [] point = elem.getPoint();
	 float [] leftPoint = left.getPoint();
	 float [] rightPoint = right.getPoint();

	 if( point[ 0] < leftPoint[ 0 ] ) {

	     left = elem;

	 } else if( point[ 0 ] == leftPoint[ 0 ] ) {

	     left = ( point[ 1 ] < leftPoint[ 1 ] ) ? elem : left ;

	 }

	 if( point[ 0 ] > rightPoint[ 0 ] ) {

	     right = elem;

	 } else if( point[ 0 ] == rightPoint[ 0 ] ) {

	     right = ( point[ 1 ] < rightPoint[ 1 ] ) ? elem : right;

	 }

     }

     return new PathElement [] { left, right };

  }

  public PathElement [] getHorizontalEdgePathElements() {

     PathElement top = null, bottom = null;

     PathElement [] pathElems = this.pathElems;

     for(int i = 0, len = pathElems.length; i < len; i ++ ) {

	 PathElement elem = pathElems[ i ];

	 if( top == null ) {

	     top = bottom = elem;

	     continue;

	 }

	 float [] point = elem.getPoint();
	 float [] topPoint = top.getPoint();
	 float [] bottomPoint = bottom.getPoint();

	 if( point[ 1 ] < topPoint[ 1 ] ) {

	     top = elem;

	 } else if( point[ 1 ] == topPoint[ 1 ] ) {

	     top = ( point[ 0 ] < topPoint[ 0 ] ) ? elem : top ;

	 }

	 if( point[ 1 ] > bottomPoint[ 1 ] ) {

	     bottom = elem;

	 } else if( point[ 1 ] == bottomPoint[ 1 ] ) {

	     bottom = ( point[ 0 ] < bottomPoint[ 0 ] ) ? elem : bottom;

	 }

     }

     return new PathElement [] { top, bottom };

  }

  public PathElement [] getPathElements() {

    return pathElems;

  }

  private PathElement [] pathElems;

}