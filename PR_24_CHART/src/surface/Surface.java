package surface;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.util.*;
import com.jcosmos.java3d.*;
import java.awt.*;
import java.awt.geom.*;
import jcosmos.*;

public class Surface {

  double [][] data;
  double min;
  double max;
  int level;

  public Surface(double [][] data, double min, double max, int level ) {

     this.data = data;
     this.min = min;
     this.max = max;
     this.level = level;

  }

  private double [][] getReverseArray( double [][] data ) {

    int len = data.length;

    double [][] dataReversed = new double [len][];

    for( int i = 0; i < len; i ++ ) {

      dataReversed[ i ] = data[ len - i - 1 ];

    }

    return dataReversed;

  }

  public SurfaceCell [][] getSurfaceCells( double x, double y, double w, double h ) {

    double [][] data = this.data;
    double  min    =   this.min;
    double  max    =   this.max;
    int     level  =   this.level;

    int row = data.length;
    int col = data[ 0 ].length;

    int cellRow = row - 1;
    int cellCol = col - 1;

    final double dw = h/cellRow < w/cellCol ? h/cellRow : w/cellCol ;

    SurfaceCell cell [][] = new SurfaceCell[ cellRow ] [ cellCol ];

    for( int r = 0; r < cellRow; r ++ ) {

      for( int c = 0; c < cellCol; c ++ ) {

	int top = getCellTopology( data, r, c );

	double cellData [] = getCellData(  new double [] { data[r][c], data[r][c+1], data[ r + 1 ][c + 1], data[ r + 1 ][ c ] }, top );

	cell[ r ][ c ] = new SurfaceCell( top, new Rectangle2D.Double( x + c*dw, y + r*dw, dw, dw ),
					  cellData[0], cellData[1], cellData[2], cellData[3] );

      }

    }

    return cell;

  }

  private int getCellTopology( double [][] data, int r, int c ) {

    double pp = data[r][c], qq = data[r][c+1], rr = data[ r + 1 ][c + 1], ss = data[ r + 1 ][ c ] ;

    double max = pp > qq ? pp : qq;
    max = max > rr ? max : rr;
    max = max > ss ? max : ss;

    double min = pp < qq ? pp : qq;
    min = min < rr ? min : rr;
    min = min < ss ? min : ss;

    if( pp == max && rr == min ) {

      return 0;

    } else if ( qq == max && ss == min ) {

      return 1;

    } else if( rr == max && pp == min ) {

      return 2;

    } else {

      return 3;

    }


  }

  private double [] getCellData( double [] data, int top ) {

    if( top == 0 ) {

      return data;

    } else if( top == 1 ) {

      return new double [] { data[1], data[0], data[3], data[2] };

    } else if( top == 2 ) {

      return new double [] { data[2], data[1], data[0], data[3] };

    } else { // top == 3

      return new double [] { data[3], data[0], data[1], data[2] };

    }

  }

}