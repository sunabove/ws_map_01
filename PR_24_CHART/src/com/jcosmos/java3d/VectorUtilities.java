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

public class VectorUtilities {

  public static Point2D getVectorFromTo(final Point2D p, final Point2D q) {

    return new Point2D.Double( q.getX() - p.getX(), q.getY() - p.getY() );

  }

  public static double getCrossProduct(final Point2D p, final Point2D q) {

    return ( ( p.getX()*q.getY() ) - ( p.getY()*q.getX() ) );

  }

  public static boolean isCounterClockWise( final Point2D p, final Point2D q, final Point2D r) {

    return ( getCrossProduct( getVectorFromTo( p, q ), getVectorFromTo( p, r ) ) > 0 ) ;

  }

  public static boolean isCounterClockWise( final Point2D [] points ) {

    final int len = points.length;

    if( len < 3 ) {

      return false;

    }

    double areaSum = 0;

    final Point2D p = points[ 0 ];

    for(int i = 1; i < len - 1 ; i ++ ) {

      final Point2D q = points[ i ];
      final Point2D r = points[ i + 1 ];

      areaSum += getCrossProduct( getVectorFromTo( p, q ), getVectorFromTo( p, r ) );

    }

    return ( areaSum > 0 );

  }

}