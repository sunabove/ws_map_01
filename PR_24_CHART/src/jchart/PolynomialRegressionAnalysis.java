package jchart;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import math.*;

public class PolynomialRegressionAnalysis extends RegressionAnalysis {

  public PolynomialRegressionAnalysis() {
  }

  public RegressionResult getRegressionAnalysisResult( double [] x, double y [] ) {

    return getRegressionAnalysisResult( x, y, 2 );

  }

  public double getYHat( double x, RegressionResult result ) {

    double [] coeff = result.ab;

    double v = 0;

    for( int i = 0, len = coeff.length; i < len; i ++ ) {

      v += ( coeff[i]*Math.pow( x, i ) );

    }

    return v;

  }

  public RegressionResult getRegressionAnalysisResult( double [] xArray, double yArray [], int n ) {

    int m = xArray.length;

    if( m < n ) {

      // n ������ ���� ȸ�� �м��� �ҷ��� �������� �ּ� ������ n ���� �Ǿ�� �Ѵ�.
      // �������� ������ n ������ ���� m �̸�
      // m ������ ���� ȸ�� �м��� �ǽ��Ѵ�.

      n = m;

    }

    n = n + 1; // 2 ���� ȸ�� �м��̸� ������ �� ��,
	       // 3 ���� ȸ�� �м��̸� ������ �� �� �̹Ƿ�.....
	       // n ���� ȸ�� �м��̸� ������ n+1 �� �̴�.

	       // ���� ���� ���� n �� ���� ���� ������ �ǹ��Ѵ�.

    double XX [][] = new double[ m ] [ n ];

    for( int r = 0; r < m ; r ++ ) {

      double XXRow [] = XX[ r ];

      double x = xArray[ r ];

      for( int k = 0; k < n ; k ++ ) {

	XXRow[ k ] = Math.pow( x, k );

      }

    }

    double YY [][] = new double[ m ] [ 1 ];

    for( int r = 0; r < m ; r ++ ) {

      YY[ r ][ 0 ] = yArray[ r ];

    }

    double [][] xxT = Matrix.getTransposeMatrix( XX );

    double [][] xxTxx = Matrix.getMultipliedMatrix( xxT, XX );

    double [][] xxTxxInvXXt = Matrix.getMultipliedMatrix( Matrix.getInverseMatrix( xxTxx ), xxT );

    double [][] AA = Matrix.getMultipliedMatrix( xxTxxInvXXt, YY );

    double [] coeff = new double[ n ];

    for( int i = 0; i < n ; i ++ ) {

      coeff[ i ] = AA[i][0];

    }

    return new RegressionResult( coeff );

  }

  public String getNumericExpression( RegressionResult result ) {

    double [] coeff = result.ab;

    String numericExpression = "";

    for( int i = 0, len = coeff.length; i < len; i ++ ) {

      if( i == 0 ) {

	  numericExpression += "(" + coeff[i] + ")";

      } else {

	  numericExpression += " + (" + coeff[i] + ")(x)^(" + i + ")";

      }

    }

    return numericExpression;

  }

}