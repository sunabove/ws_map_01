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
import jcosmos.*;

public class TendencyLineViewFactory {

  public static TendencyLineView getLinearTendencyLineView( Rectangle2D area,
			  TendencyInformation tendencyInfo, DataSeries dataSeries,
			  PictureExtent pictureExtent, boolean vertical )
  {

       final double ax = area.getX();
       final double ay = area.getY();

       if( ! vertical ) {

	    area = ShapeUtilities.getRotatedShape( ax, ay, area, - Math.PI/2.0 ).getBounds2D();

       }

       RegressionAnalysis regressionAnalysis = new LinearRegressionAnalysis();

       double yArray [] = dataSeries.converToDoubleArray();

       int len = yArray.length;

       double xArray [] = getBasicXArray( len );

       RegressionResult regResult = regressionAnalysis.getRegressionAnalysisResult( xArray, yArray );

       double r2 = regressionAnalysis.getR2( xArray, yArray, regResult );

       Utility.debug( TendencyLineViewFactory.class , "R2 = " + r2 );

       Utility.debug( TendencyLineViewFactory.class , "NUMERIC EXPRESSION = " + regressionAnalysis.getNumericExpression( regResult ) );

       double [] ab = regResult.ab;
       double a = ab[ 0 ];
       double b = ab[ 1 ];

       double x = area.getX(), y = area.getY(), w = area.getWidth(), h = area.getHeight();

       Chart chart = pictureExtent.getChart();

       ValueAxisOption vao = chart.getValueAxisOption();

       double max = vao.getMaxScale();

       double dw = w/len;

       double yScale = h/( max - 0 );

       double x0 = x + dw/2.0, x1 = x + w - dw/2.0;

       double y0 = y + h - ( a*xArray[0] + b )*yScale , y1 = y + h - ( a*xArray[len -1] + b )*yScale ;

       Shape tendencyLine = makeTendencyLine(
					      new double [] { x0, x1 },
					      new double [] { y0, y1 }
					    );

       if( ! vertical ) {

	    tendencyLine = ShapeUtilities.getRotatedShape( ax, ay, tendencyLine, Math.PI/2.0 );

       }

       SpreadSheet sheet = pictureExtent.getSpreadSheet();
       ShapeStyle style = ShapeStyle.getDefaultShapeStyle();

       return new TendencyLineView( sheet, pictureExtent, tendencyLine, style );

  }

  public static TendencyLineView getLogTendencyLineView( Rectangle2D area,
			  TendencyInformation tendencyInfo, DataSeries dataSeries,
			  PictureExtent pictureExtent, boolean vertical )
  {

       final double ax = area.getX();
       final double ay = area.getY();

       if( ! vertical ) {

	    area = ShapeUtilities.getRotatedShape( ax, ay, area, - Math.PI/2.0 ).getBounds2D();

       }

       RegressionAnalysis regressionAnalysis = new LogRegressionAnalysis();

       double yArray [] = dataSeries.converToDoubleArray();

       int len = yArray.length;

       double xArray [] = getBasicXArray( len );

       RegressionResult regResult = regressionAnalysis.getRegressionAnalysisResult( xArray, yArray );

       double r2 = regressionAnalysis.getR2( xArray, yArray, regResult );

       Utility.debug( TendencyLineViewFactory.class , "R2 = " + r2 );

       Utility.debug( TendencyLineViewFactory.class , "NUMERIC EXPRESSION = " + regressionAnalysis.getNumericExpression( regResult ) );

       double [] ab = regResult.ab;
       double a = ab[ 0 ];
       double b = ab[ 1 ];

       double x = area.getX(), y = area.getY(), w = area.getWidth(), h = area.getHeight();

       Chart chart = pictureExtent.getChart();

       ValueAxisOption vao = chart.getValueAxisOption();

       double max = vao.getMaxScale();

       double dw = w/len;

       double yScale = h/( max - 0 );

       int divNum = 5;
       int pointsNum = divNum*(len -1) + 1;

       double xPoints [] = new double[ pointsNum ];

       for( int i = 0; i < pointsNum; i ++ ) {

	   xPoints[ i ] = x + dw/2.0 + (dw*i/divNum);

       }

       double yPoints [] = new double[ pointsNum ];

       for( int i = 0; i < pointsNum; i ++ ) {

	  int index = (int)(i/divNum);

	  double currX = xArray[ index ];

	  double mathX = currX + ( 1.0/divNum )*( i % divNum );

//	  Utility.debug( TendencyLineViewFactory.class, "Math X = " + mathX );

	  mathX = mathX <= 0 ? 0.00001 : mathX;

	  yPoints[ i ] = y + h - ( a*Math.log( mathX ) + b )*yScale;

       }

       Shape tendencyLine = makeTendencyLine(
					      xPoints,
					      yPoints
					    );

       if( ! vertical ) {

	    tendencyLine = ShapeUtilities.getRotatedShape( ax, ay, tendencyLine, Math.PI/2.0 );

       }

       SpreadSheet sheet = pictureExtent.getSpreadSheet();
       ShapeStyle style = new ShapeStyle( null, Color.yellow );

       return new TendencyLineView( sheet, pictureExtent, tendencyLine, style );

  }

  public static TendencyLineView getExponentialTendencyLineView( Rectangle2D area,
			  TendencyInformation tendencyInfo, DataSeries dataSeries,
			  PictureExtent pictureExtent, boolean vertical )
  {
       final double ax = area.getX();
       final double ay = area.getY();

       if( ! vertical ) {

	    area = ShapeUtilities.getRotatedShape( ax, ay, area, - Math.PI/2.0 ).getBounds2D();

       }

       RegressionAnalysis regressionAnalysis = new ExponentialRegressionAnalysis();

       double yArray [] = dataSeries.converToDoubleArray();

       int len = yArray.length;

       double xArray [] = getBasicXArray( len );

       RegressionResult regResult = regressionAnalysis.getRegressionAnalysisResult( xArray, yArray );

       double r2 = regressionAnalysis.getR2( xArray, yArray, regResult );

       Utility.debug( TendencyLineViewFactory.class , "R2 = " + r2 );

       Utility.debug( TendencyLineViewFactory.class , "NUMERIC EXPRESSION = " + regressionAnalysis.getNumericExpression( regResult ) );

       double [] ab = regResult.ab;
       double a = ab[ 0 ];
       double b = ab[ 1 ];

       double x = area.getX(), y = area.getY(), w = area.getWidth(), h = area.getHeight();

       Chart chart = pictureExtent.getChart();

       ValueAxisOption vao = chart.getValueAxisOption();

       double max = vao.getMaxScale();

       double dw = w/len;

       double yScale = h/( max - 0 );

       int divNum = 5;
       int pointsNum = divNum*(len -1) + 1;

       double xPoints [] = new double[ pointsNum ];

       for( int i = 0; i < pointsNum; i ++ ) {

	   xPoints[ i ] = x + dw/2.0 + (dw*i/divNum);

       }

       double yPoints [] = new double[ pointsNum ];

       for( int i = 0; i < pointsNum; i ++ ) {

	  int index = (int)(i/divNum);

	  double currX = xArray[ index ];

	  double mathX = currX + ( 1.0/divNum )*( i % divNum );

//	  Utility.debug( TendencyLineViewFactory.class, "Math X = " + mathX );

	  mathX = mathX <= 0 ? 0.00001 : mathX;

	  yPoints[ i ] = y + h - ( a*Math.exp( b* mathX ) )*yScale;

       }

       Shape tendencyLine = makeTendencyLine(
					      xPoints,
					      yPoints
					    );

       if( ! vertical ) {

	    tendencyLine = ShapeUtilities.getRotatedShape( ax, ay, tendencyLine, Math.PI/2.0 );

       }

       SpreadSheet sheet = pictureExtent.getSpreadSheet();
       ShapeStyle style = new ShapeStyle( null, Color.red );

       return new TendencyLineView( sheet, pictureExtent, tendencyLine, style );

  }

  public static TendencyLineView getPolynomialTendencyLineView( Rectangle2D area,
			  TendencyInformation tendencyInfo, DataSeries dataSeries,
			  PictureExtent pictureExtent, int n, Color color, boolean vertical )
  {

       final double ax = area.getX();
       final double ay = area.getY();

       if( ! vertical ) {

	    area = ShapeUtilities.getRotatedShape( ax, ay, area, - Math.PI/2.0 ).getBounds2D();

       }

       PolynomialRegressionAnalysis regressionAnalysis = new PolynomialRegressionAnalysis();

       double yArray [] = dataSeries.converToDoubleArray();

       int len = yArray.length;

       double xArray [] = getBasicXArray( len );

       RegressionResult regResult = regressionAnalysis.getRegressionAnalysisResult( xArray, yArray, n );

       double r2 = regressionAnalysis.getR2( xArray, yArray, regResult );

       Utility.debug( TendencyLineViewFactory.class , "R2 = " + r2 );

       Utility.debug( TendencyLineViewFactory.class , "NUMERIC EXPRESSION = " + regressionAnalysis.getNumericExpression( regResult ) );

       double [] coeff = regResult.ab;

       String coeffText = "";

       for( int i = 0; i < coeff.length; i ++ ) {

	  coeffText += "a[" + i + "] = " + coeff[i] + ", ";

       }

       Utility.debug( TendencyLineViewFactory.class, "COEFF = " + coeffText );

       double x = area.getX(), y = area.getY(), w = area.getWidth(), h = area.getHeight();

       Chart chart = pictureExtent.getChart();

       ValueAxisOption vao = chart.getValueAxisOption();

       double max = vao.getMaxScale();

       double dw = w/len;

       double yScale = h/( max - 0 );

       int divNum = 5;
       int pointsNum = divNum*(len -1) + 1;

       double xPoints [] = new double[ pointsNum ];

       for( int i = 0; i < pointsNum; i ++ ) {

	   xPoints[ i ] = x + dw/2.0 + (dw*i/divNum);

       }

       double yPoints [] = new double[ pointsNum ];

       for( int i = 0; i < pointsNum; i ++ ) {

	  int index = (int)(i/divNum);

	  double currX = xArray[ index ];

	  double mathX = currX + ( 1.0/divNum )*( i % divNum );

//	  Utility.debug( TendencyLineViewFactory.class, "Math X = " + mathX );

	  yPoints[ i ] = y + h - ( getPolynomialValue( coeff, mathX ) )*yScale;

       }

       Shape tendencyLine = makeTendencyLine(
					      xPoints,
					      yPoints
					    );

       if( ! vertical ) {

	    tendencyLine = ShapeUtilities.getRotatedShape( ax, ay, tendencyLine, Math.PI/2.0 );

       }

       SpreadSheet sheet = pictureExtent.getSpreadSheet();
       ShapeStyle style = new ShapeStyle( null, color );

       return new TendencyLineView( sheet, pictureExtent, tendencyLine, style );

  }

  public static double getPolynomialValue( double [] coeff, double x) {

      double y = 0;

      for( int i = 0, len = coeff.length ; i < len; i ++ ) {

	y += ( coeff[i]*Math.pow( x, i ) );

      }

      return y;

  }

  public static TendencyLineView getRadicalRootTendencyLineView( Rectangle2D area,
			  TendencyInformation tendencyInfo, DataSeries dataSeries,
			  PictureExtent pictureExtent, boolean vertical )
  {
       final double ax = area.getX();
       final double ay = area.getY();

       if( ! vertical ) {

	    area = ShapeUtilities.getRotatedShape( ax, ay, area, - Math.PI/2.0 ).getBounds2D();

       }

       RegressionAnalysis regressionAnalysis = new RadicalRootRegressionAnalysis();

       double yArray [] = dataSeries.converToDoubleArray();

       int len = yArray.length;

       double xArray [] = getBasicXArray( len );

       RegressionResult regResult = regressionAnalysis.getRegressionAnalysisResult( xArray, yArray );

       double r2 = regressionAnalysis.getR2( xArray, yArray, regResult );

       Utility.debug( TendencyLineViewFactory.class , "R2 = " + r2 );

       Utility.debug( TendencyLineViewFactory.class , "NUMERIC EXPRESSION = " + regressionAnalysis.getNumericExpression( regResult ) );

       double [] ab = regResult.ab;
       double a = ab[ 0 ];
       double b = ab[ 1 ];

       double x = area.getX(), y = area.getY(), w = area.getWidth(), h = area.getHeight();

       Chart chart = pictureExtent.getChart();

       ValueAxisOption vao = chart.getValueAxisOption();

       double max = vao.getMaxScale();

       double dw = w/len;

       double yScale = h/( max - 0 );

       int divNum = 5;
       int pointsNum = divNum*(len -1) + 1;

       double xPoints [] = new double[ pointsNum ];

       for( int i = 0; i < pointsNum; i ++ ) {

	   xPoints[ i ] = x + dw/2.0 + (dw*i/divNum);

       }

       double yPoints [] = new double[ pointsNum ];

       for( int i = 0; i < pointsNum; i ++ ) {

	  int index = (int)(i/divNum);

	  double currX = xArray[ index ];

	  double mathX = currX + ( 1.0/divNum )*( i % divNum );

//	  Utility.debug( TendencyLineViewFactory.class, "Math X = " + mathX );

	  mathX = mathX <= 0 ? 0.00001 : mathX;

	  yPoints[ i ] = y + h - ( a*Math.pow(mathX, b) )*yScale;

       }

       Shape tendencyLine = makeTendencyLine(
					      xPoints,
					      yPoints
					    );

       if( ! vertical ) {

	    tendencyLine = ShapeUtilities.getRotatedShape( ax, ay, tendencyLine, Math.PI/2.0 );

       }

       SpreadSheet sheet = pictureExtent.getSpreadSheet();
       ShapeStyle style = new ShapeStyle( null, Color.blue );

       return new TendencyLineView( sheet, pictureExtent, tendencyLine, style );

  }

  private static Shape makeTendencyLine( double [] x, double y [] ) {

     GeneralPath gp = new GeneralPath();

     gp.moveTo( (float) x[0], (float) y[0] );

     for( int i = 1, len = x.length; i < len; i ++ ) {

       gp.lineTo( (float) x[i], (float) y[i] );

     }

     return gp;

  }

  private static double [] getBasicXArray(int len) {

      double x [] = new double[ len ] ;

      for( int i = 0; i < len; i ++ ) {

	 x[ i ] = ( i + 1 );

      }

      return x;

  }

}