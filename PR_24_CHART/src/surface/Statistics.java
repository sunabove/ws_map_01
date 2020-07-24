package surface;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class Statistics {

  private double [][] data;

  public double num;
  public double sum;
  public double avg;
  public double variation;
  public double deviation;

  public Statistics( double [][] data ) {

    this.data = data;

    this.num = data.length*data[ 0 ].length;

    this.sum = getSum();

    this.avg = sum / num ;

    this.variation = getVariation();

    this.deviation = Math.sqrt( variation );

  }

  private double getSum() {

    double sum = 0;

    double [][] data = this.data;

    for( int i = 0, iLen = data.length; i < iLen; i ++ ) {

      double [] rowData = data[ i ];

      for( int k = 0, kLen = rowData.length; k < kLen; k ++ ) {

	sum += rowData[ k ];

      }

    }

    return sum;

  }

  private double getVariation() {

    double variation = 0;

    double [][] data = this.data;

    for( int i = 0, iLen = data.length; i < iLen; i ++ ) {

      double [] rowData = data[ i ];

      for( int k = 0, kLen = rowData.length; k < kLen; k ++ ) {

	variation += ( rowData[ k ]*rowData[k] ) ;

      }

    }

    return (variation/num) - (avg*avg);

  }

}