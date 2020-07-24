package jchart;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import jcosmos.*;

public class LinearRegressionAnalysis extends RegressionAnalysis {

  public LinearRegressionAnalysis() {
  }

  public RegressionResult getRegressionAnalysisResult( double [] x, double y [] ) {

    return super.getLinearRegressionAnalysisResult( x, y );

  }

  public double getYHat( double x, RegressionResult result ) {

    double [] ab = result.ab;

    return ab[0]*x + ab[1];

  }

  public String getNumericExpression( RegressionResult result ) {

    double [] ab = result.ab;

    double a = ab[0];
    double b = ab[1];

    if( b == 0 ) {

	return "" + a + "x";

    } else {

	return "" + a + "x + " + b;

    }

  }

}