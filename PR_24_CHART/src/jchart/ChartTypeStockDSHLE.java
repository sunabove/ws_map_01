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

      String warnMsg = "�ֽ� ��Ʈ�� ������� �ֽ��� ���� ���� �ְ� ����, ���� ����, ���� ����, ���� �ŷ��� ������� ��Ʈ�� �����͸� �����ؾ� �մϴ�. ��¥�� �ֽ� �̸��� ���̺�� ����մϴ�.";

      if( dss.length != 5 ) {

	 Utility.warn( warnMsg, SpreadSheet.getCurrentSpreadSheet() );

	 return false;

      }

      if( dss[0].getSeriesName().equals( "���� �ŷ���")
	  && dss[1].getSeriesName().equals( "���� ����")
	  && dss[2].getSeriesName().equals( "�ְ� ����")
	  && dss[3].getSeriesName().equals( "���� ����")
	  && dss[4].getSeriesName().equals( "���� ����")
      )
      {

	return true;

      }

      Utility.warn( warnMsg, SpreadSheet.getCurrentSpreadSheet() );

      return false;

   }

}