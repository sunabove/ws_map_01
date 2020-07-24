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

public class ChartTypeCircleVsCircle extends ChartType2D implements FixedDimensionRatioChartType {

	public ChartTypeCircleVsCircle() {
	}

	public void setPictureExtent(PictureExtent pictureExtent) {

		// adds data series views

		SpreadSheet sheet = pictureExtent.getSheet();

		ChartData cd = pictureExtent.getChart().getChartData();

		DataSeries [] ds = cd.getDataSeries();

		// 하나의 dvs 만 그림 영역에 넣는다.

		DataSeriesView dsv = new DataSeriesView( sheet, pictureExtent, null, null, this, ds[ 0 ] );

		pictureExtent.addChild( dsv );

		// end of adding data series views

	}

	public Rectangle [] getSelectedMarks(DataElementView dataElementView) {
		return dataElementView.getSelectedMarksLineType();
	}

	public int getFeedbackTopology() {

	    return 0;

	}

	/**
	 * set shape and shape style of data series view
	 */
	public void setShapeAndStyle(DataSeriesView dataSeriesView) {

	     Chart chart = dataSeriesView.getChart();

	     PictureExtent pe = chart.getPictureExtent();

	     int seriesSplitType = pe.seriesSplitTypeOfChartTypeCircle;
	     int secondDomainSplitValue = pe.secondDomainSplitValueOfChartTypeCircle;
	     int secondDomainSize = pe.secondDomainSizeOfChartTypeCircle;
	     int gapWidth = pe.gapWidthOfChartTypeCircle;
	     boolean useDifferentColor = pe.useDifferentColorToEachPieOfChartTypeCircle;
	     boolean hasSeriesLines = pe.hasSeriesLines;

	     DataElementView [] devs = dataSeriesView.getDataElementViews();

	     Vector [] devsSplitVec = getDataElementViewGroupSplit( seriesSplitType, devs, secondDomainSplitValue );

	     DataElementView [] firstDevs = getDataElementViews( devsSplitVec[ 0 ] );
	     DataElementView [] secondDevs = getDataElementViews( devsSplitVec[ 1 ] );

	     Rectangle peArea = pe.getArea();

	     double px = peArea.x, py = peArea.y, pw = peArea.width, ph = peArea.height;

	     double ar = pw/( 1.0 + gapWidth/100.0 + secondDomainSize/100.0); // 첫번째 파이 영역 좌표
	     double ax = px;
	     double ay = ( secondDomainSize <= 100 ) ? py : py + ( ph - ar )/2.0;

	     Rectangle2D firstPieArea = new Rectangle2D.Double( ax, ay, ar, ar);

	     double br = ar*secondDomainSize/100.0; // 두번째 파이 영역 좌표
	     double bx = px + pw - br;
	     double by = (secondDomainSize <= 100 ) ? py + ( ph - br )/2.0 : py;

	     Rectangle2D secondPieArea = new Rectangle2D.Double( bx, by, br, br);

	     double totalSum = getAbsoluteSum( devs );

	     double firstDevSum = getAbsoluteSum( firstDevs );

	     double secondDevSum = getAbsoluteSum( secondDevs );

	     double auxRatio = secondDevSum/totalSum;

	     double auxAngle = 360.0 * auxRatio;

	     makeFirstPie( auxAngle/2.0, firstDevs, totalSum, firstPieArea, useDifferentColor );

	     makeSecondPie( 0, secondDevs, secondDevSum, secondPieArea, useDifferentColor );

	     // 설명 DEV 만들기

	     if( secondDevs.length > 0 ) {

		DataElementView auxDev = (DataElementView ) devs[0].clone( dataSeriesView, dataSeriesView.getSpreadSheet() );

		auxDev.setShape( new Arc2D.Double( firstPieArea, - auxAngle/2.0, auxAngle, Arc2D.PIE ) );

		Color auxColor = useDifferentColor ? getChartColor( firstDevs.length ) : Color.white;

		ShapeStyle auxStyle = new ShapeStyle( auxColor, Color.black );

		auxDev.setShapeStyle( auxStyle );

		String dataSeriesName = dataSeriesView.getDataSeries().getSeriesName();

		auxDev.setToolTip( "계열 \"" + dataSeriesName + "\" 참조 \"기타\" 갑: "
				  + secondDevSum + " (" + ((int)(auxRatio*100)) + "%)" );

		dataSeriesView.addChild( auxDev );

	     } else { // 옆에 까맣게 나타나도록 만들기

		DataElementView auxDev = (DataElementView ) devs[0].clone( dataSeriesView, dataSeriesView.getSpreadSheet() );

		Shape auxShape = null;

		if( this instanceof ChartTypeCircleVsBar ) {

		  auxShape = secondPieArea;

		} else {

		  auxShape = new Arc2D.Double( secondPieArea, 0, 360, Arc2D.OPEN );

		}

		auxDev.setShape( auxShape );

		Color auxColor = Color.black;

		ShapeStyle auxStyle = new ShapeStyle( auxColor, Color.black );

		auxDev.setShapeStyle( auxStyle );

		String dataSeriesName = dataSeriesView.getDataSeries().getSeriesName();

		auxDev.setToolTip( "계열 \"" + dataSeriesName + "\" 참조 \"기타\" 갑: "
				  + secondDevSum + " (" + ((int)(auxRatio*100)) + "%)" );

		dataSeriesView.addChild( auxDev );

	     }

	     // 끝. 설명 DEV 만들기

	     if( secondDevs.length > 0 && hasSeriesLines ) {

		  DataElementView seriesLineDev = ( DataElementView) ( devs[0].clone( dataSeriesView, dataSeriesView.getSpreadSheet() ) );

		  makeSeriesLines( seriesLineDev, auxAngle, firstPieArea, secondPieArea  );

		  dataSeriesView.addChild( seriesLineDev );

		  seriesLineDev.setToolTip( "계열선" );

	     }

	}

	public void makeSeriesLines( DataElementView dev, double auxAngle,
				      Rectangle2D firstArea, Rectangle2D secondArea ) {

	      double px, py, qx, qy, rx, ry, sx, sy;

	      // 계열선 포인트 정의
	      //       p -_
	      //            ` q
	      //       r -_
	      //            ` t
	      //

	      double fax = firstArea.getX(), fay = firstArea.getY(),
		     faw = firstArea.getWidth(), fah = firstArea.getHeight();

	      if( auxAngle > 180 ) {

		  px = fax + faw/2.0;
		  py = fay;

		  rx = fax + faw/2.0;
		  ry = fay + fah;

	      } else {

		  double v = faw/2.0;

		  double theta = Unit.convertDegreeToRadian( auxAngle/2.0 );

		  double vx = v*Math.cos( theta );
		  double vy = v*Math.sin( theta );

		  px = fax + faw/2.0 + vx;
		  py = fay + fah/2.0 - vy;

		  rx = px;
		  ry = fay + fah/2.0 + vy;

	      }

	      double sax = secondArea.getX(), say = secondArea.getY(),
		     saw = secondArea.getWidth(), sah = secondArea.getHeight();

	      qx = sax + saw/2.0;
	      qy = say;

	      sx = sax + saw/2.0;
	      sy = say + sah;

	      GeneralPath seriesLine = new GeneralPath();

	      seriesLine.moveTo( (float) px, (float) py );
	      seriesLine.lineTo( (float) qx, (float) qy );

	      seriesLine.moveTo( (float) rx, (float) ry );
	      seriesLine.lineTo( (float) sx, (float) sy );

	      dev.setShape( seriesLine );
	      dev.setShapeStyle( ShapeStyle.getDefaultShapeStyle() );

	}

	public void makeSecondPie( double startAngle, DataElementView [] devs, double sum, Rectangle2D area, boolean useDifferentColor ) {

	      makeFirstPie( startAngle, devs, sum, area, useDifferentColor );

	}

	public void makeFirstPie( double startAngle, DataElementView [] devs, double sum, Rectangle2D area, boolean useDifferentColor ) {

	     int type = Arc2D.PIE; // arc type

	     for( int i = 0, len = devs.length; i < len; i ++ ) {

		 DataElementView dev = devs[ i ];

		 double extent = 360.0* ( Math.abs ( dev.getDataElement().getValue() )/sum );

		 Color color = useDifferentColor ? getChartColor( i ) : Color.white ; // DataElementView color

		 ShapeStyle style = new ShapeStyle( color, Color.black ); // DataElementView Shape Style

		 dev.setShapeStyle( style );

		 Arc2D arc = new Arc2D.Double( area, startAngle, extent, type );

		 dev.setShape( arc );

		 startAngle += extent;

	     }

	}

	private double getAbsoluteSum(DataElementView [] devs) {

	    double sum = 0;

	    for( int i = 0, len = devs.length; i < len; i ++ ) {

	      sum += Math.abs( devs[i].getDataElement().getValue() );

	    }

	    return sum;

	}

	private DataElementView [] getDataElementViews(Vector devList) {

	     int len = devList.size();

	     DataElementView [] devs = new DataElementView[ len ];

	     for( int i = 0; i < len; i ++ ) {

		devs[ i ] = (DataElementView) devList.elementAt( i );

	     }

	     return devs;

	}

	public Vector [] getDataElementViewGroupSplit( int splitType,
				    DataElementView [] devs, int splitValue ) {

	     if( splitType == PictureExtent.SPLIT_BY_LOCATION ) {

		return getDataElementViewGroupListSplitByLocation( devs, splitValue );

	     } else if( splitType == PictureExtent.SPLIT_BY_PERCENTAGE ) {

		return getDataElementViewGroupListSplitByPercentage( devs, splitValue );

	     } else if( splitType == PictureExtent.SPLIT_BY_VALUE ) {

		return getDataElementViewGroupListSplitByValue( devs, splitValue );

	     }

	     return getDataElementViewGroupListSplitByLocation( devs, splitValue );

	}

	public Vector [] getDataElementViewGroupListSplitByLocation(DataElementView [] devs, int splitValue ) {

	     Vector firstDevList = new Vector();
	     Vector secondDevList = new Vector();

	     final int devsLen = devs.length;

	     int firstRange = devsLen - splitValue;

	     firstRange = firstRange < -1 ? 0 : firstRange;
	     firstRange = firstRange > devsLen ? devsLen : firstRange;

	     int i = 0;

	     for( ; i < firstRange; i ++ ) {

		firstDevList.add( devs[i] );

	     }

	     for( ; i < devsLen ; i ++ ) {

		secondDevList.add( devs[i] );

	     }

	     return new Vector [] { firstDevList, secondDevList };

	}

	public Vector [] getDataElementViewGroupListSplitByPercentage(DataElementView [] devs, int splitValue ) {

	    double maxValue = 0;

	    for( int i = 0, len = devs.length; i < len; i ++ ) {

	      double value = devs[i].getDataElement().getValue();

	      maxValue = ( maxValue > value ) ? maxValue : value;

	    }

	    double absSplitValue = maxValue*(splitValue/100.0);

	    return getDataElementViewGroupListSplitByValue( devs, absSplitValue );

	}

	public Vector [] getDataElementViewGroupListSplitByValue(DataElementView [] devs, double splitValue ) {

	     Vector firstDevList = new Vector();
	     Vector secondDevList = new Vector();

	     final int devsLen = devs.length;

	     DataElementView dev;

	     for( int i = 0; i < devsLen ; i ++ ) {

		dev = devs[i];

		if( dev.getDataElement().getValue() <= splitValue ) {

		  secondDevList.add( dev );

		} else {

		  firstDevList.add( dev );

		}

	     }

	     return new Vector [] { firstDevList, secondDevList };

	}

	public void setGraphExtent(GraphExtent graphExtent){

		SpreadSheet sheet = graphExtent.getSpreadSheet();

		Rectangle area = graphExtent.getArea(); // graph area

		double x = area.x, y = area.y, w = area.width, h = area.height; // area coordinates

		Rectangle2D pictureRect = new Rectangle2D.Double( x, y, w, h);

		ShapeStyle pictureStyle = ShapeStyle.getDefaultShapeStyle();

		pictureStyle.setFillColor( getPictureExtentBackground() );

		PictureExtent pe = new PictureExtent( sheet, graphExtent, pictureRect, pictureStyle );

		graphExtent.addChild( pe );

		int secondDomainSize = pe.secondDomainSizeOfChartTypeCircle;
		int gapWidth = pe.gapWidthOfChartTypeCircle;

		Rectangle peArea = pe.getArea();

		double px = peArea.x, py = peArea.y, pw = peArea.width, ph = area.height;

		double ar = pw/( 1.0 + gapWidth/100.0 + secondDomainSize/100.0); // 첫번째 파이 영역 좌표

		double nph = ( secondDomainSize <= 100 ) ? ar : ar* ( secondDomainSize/100.0) ; // 새 PE 높이

		pe.setShape( new Rectangle2D.Double( px, py + ( ph - nph )/2.0, pw, nph ) );

		pe.setFixedDimensionRatio( pw/(nph + 0.0) );

		graphExtent.synchronizeToPictureExtentShape();

	}

}