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
public class ChartTypeStockDSHLE extends ChartTypeStockSHLEanother {

  public ChartTypeStockDSHLE() {
  }

   /**
    * set shape and shape style of data series view
    */
   public void setShapeAndStyle(DataSeriesView dataSeriesView) {

   }

   public boolean isValidChartData(ChartData cd, Object source) {

      boolean result = super.isValidChartDataValues( cd, source );

      if( result == false ) {

	return false;

      }

      DataSeries [] dss = cd.getDataSeries();

      String warnMsg = "주식 차트를 만들려면 주식의 개시 가격 최고 가격, 최저 가격, 최종 가격, 일일 거래량 순서대로 시트에 데이터를 정렬해야 합니다. 날짜나 주식 이름을 레이블로 사용합니다.";

      if( dss.length != 5 ) {

	 Utility.warn( warnMsg, SpreadSheet.getCurrentSpreadSheet() );

	 return false;

      }

      if( dss[0].getSeriesName().equals( "일일 거래량")
	  && dss[1].getSeriesName().equals( "개시 가격")
	  && dss[2].getSeriesName().equals( "최고 가격")
	  && dss[3].getSeriesName().equals( "최저 가격")
	  && dss[4].getSeriesName().equals( "최종 가격")
      )
      {

	return true;

      }

      Utility.warn( warnMsg, SpreadSheet.getCurrentSpreadSheet() );

      return false;

   }

}