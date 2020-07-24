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
import jcosmos.*;

/**
 * stock chart type of start, high, low, end price
 */
public class ChartTypeStockSHLE extends ChartTypeStockHLE {

  public ChartTypeStockSHLE() {
  }

   /**
    * set shape and shape style of data series view
    */
   public void setShapeAndStyle(DataSeriesView dataSeriesView) {

      Chart chart = dataSeriesView.getChart();
      PictureExtent pa = chart.getPictureExtent();

      int oci = pa.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

      if( oci == 0 ) { // 최저, 최고가 연결선이 밑 바닥에 나타나도록 하기 위해서 제일 처음에 넣어준다.

	setMinMaxStockDataSeriesViewShapeAndStyle( dataSeriesView, 1 ); // oci는 최고 가 데이터 시리즈 인덱스를 넣어 준다.

      } else if( oci == 1 ) {

	setPositiveLineStockDataSeriesViewShapeAndStyle( dataSeriesView );

      } else if( oci == 2 ) {

	setNegativeLineStockDataSeriesViewShapeAndStyle( dataSeriesView );

      }


   }

   protected void setPositiveLineStockDataSeriesViewShapeAndStyle(DataSeriesView dataSeriesView) {

      setTrendStockDataSeriesViewShapeAndStyle( dataSeriesView, true,  "양선 1" );

   }

   protected void setNegativeLineStockDataSeriesViewShapeAndStyle(DataSeriesView dataSeriesView) {

      setTrendStockDataSeriesViewShapeAndStyle( dataSeriesView, false, "음선 1" );

   }

    protected void setTrendStockDataSeriesViewShapeAndStyle(DataSeriesView dataSeriesView, boolean positiveLine, String toolTip) {

        Chart chart = dataSeriesView.getChart();

		ValueAxisOption vao = chart.getValueAxisOption();       // valueAxis Option

        PictureExtent pa = chart.getPictureExtent();

        ChartData cd = chart.getChartData();  // cahrt data

        DataSeries [] ds = cd.getDataSeries(); // data series



        int dsLen = ds.length;

        double min = cd.getMinValue();  // minimum value
        double max = cd.getMaxValue();  // maximum value

        super.calibrateAxisMinMaxValueAndUnit( vao, min, max ); // calibrated min, max and unit

        min = vao.getMinScale();
        max = vao.getMaxScale();

        Color fillColor = positiveLine ? Color.white : Color.black;

        ShapeStyle style = new ShapeStyle( fillColor, Color.black ); // DataElementView Shape Style

        Rectangle paArea = pa.getArea();

        int pax = paArea.x, pay = paArea.y, paw = paArea.width, pah = paArea.height;

        int baseY = pay + pah; // data element view left botton vertical location ( y coordinate )

        DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

        int len = devs.length; // data element view number

        double dw = ( paw + 0.0 )/( len );

        DataElement [] initialStockPriceDEs = ds[0].getDataElements();
        DataElement [] finalStockPriceDEs = ds[3].getDataElements();

        for(int i = 0; i < len; i ++ ) {

			double initialPrice = initialStockPriceDEs[i].getValue(); // max stock price
			double finalPrice = finalStockPriceDEs[i].getValue(); // min stock price

			boolean positive = ( finalPrice > initialPrice );

			if( positive != positiveLine ) {

				continue;

			}

			DataElementView dev = devs[ i ] ; // data element view

			dev.setToolTip( toolTip );

			dev.setShapeStyle( style );

			int x = pax + (int)( dw*(i + 0.5) );

			double ty = baseY - pah*( initialPrice - min )/( max - min );  // top y

			double by = baseY - pah*( finalPrice - min )/( max - min );   // bottom y

			double y = ( ty < by ) ? ty : by;

			double h = Math.abs( ty - by );

			double w = dw/2.0;

			Shape shape = new Rectangle2D.Double( x - w/2.0, y, w, h );

			dev.setShape( shape );

        }

    }

   public boolean isValidChartData(ChartData cd, Object source) {

      boolean result = super.isValidChartDataValues( cd, source );

      if( result == false ) {

	return false;

      }

      DataSeries [] dss = cd.getDataSeries();

      String warnMsg = "주식 차트를 만들려면 주식의 개시 가격, 최고 가격, 최저 가격, 최종 가격 순서대로 시트에 데이터를 정렬해야 합니다. 날짜나 주식 이름을 레이블로 사용합니다.";

      if( dss.length !=4 ) {

	 Utility.warn( warnMsg, SpreadSheet.getCurrentSpreadSheet() );

	 return false;

      }

      if( dss[0].getSeriesName().equals( "개시 가격")
	  && dss[1].getSeriesName().equals( "최고 가격")
	  && dss[2].getSeriesName().equals( "최저 가격")
	  && dss[3].getSeriesName().equals( "최종 가격")
      )
      {

	return true;

      }

      Utility.warn( warnMsg, SpreadSheet.getCurrentSpreadSheet() );

      return false;

   }

}