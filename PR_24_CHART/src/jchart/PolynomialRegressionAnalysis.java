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

      // n 차수의 다항 회귀 분석을 할려면 데이터의 최소 갯수가 n 개가 되어야 한다.
      // 데이터의 갯수가 n 개보다 적은 m 이면
      // m 차수의 다항 회귀 분석을 실시한다.

      n = m;

    }

    n = n + 1; // 2 차항 회귀 분석이면 변수가 세 개,
	       // 3 차항 회귀 분석이면 변수가 네 개 이므로.....
	       // n 차항 회귀 분석이면 변수가 n+1 개 이다.

	       // 이후 라인 부터 n 은 변량 인자 갯수를 의미한다.

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