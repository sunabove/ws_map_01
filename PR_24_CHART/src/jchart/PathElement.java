package jchart;

/**
 * Title:        java chart project
 * Description:  jchart v1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.geom.*;

public class PathElement {

  public PathElement(int type, float [] coords) {

      this.type = type;
      this.coords = coords;

  }

  public float [] getPoint() {

      int type = this.type;

      float [] coords = this.coords;

      switch( type ) {

          case PathIterator.SEG_MOVETO :

               return new float [] { coords[ 0 ], coords[ 1 ] };

          case PathIterator.SEG_LINETO :

               return new float [] { coords[ 0 ], coords[ 1 ] };

          case PathIterator.SEG_QUADTO :

               return new float [] { coords[ 2 ], coords[ 3 ] };

          case PathIterator.SEG_CUBICTO :

               return new float [] { coords[ 4 ], coords[ 5 ] };

          default :

               return new float [] { coords[ 0 ], coords[ 1 ] };

       }

  }

  public PathElement getCloneMovedBy( float tx, float ty ) {

      float [] coords = new float[6];

      System.arraycopy( this.coords, 0, coords, 0, 6 );

      int type = this.type;

      int len = 2;

      switch( type ) {

          case PathIterator.SEG_QUADTO :

               len = 4;

          case PathIterator.SEG_CUBICTO :

               len = 6;

       }

      for(int i = 0; i < len; i += 2 ) {

          coords[i] += tx;
          coords[ i + 1 ] += ty;

      }

      return new PathElement( type, coords );

  }

  public float [] coords;
  public int type;

}