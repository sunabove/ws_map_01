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
 * stock chart type of high, low, end price
 */
public class ChartTypeStockHLE extends ChartType2D {

      protected int finalPriceSymbolWidth = 4, finalPriceSymbolHeight = 2;

      public ChartTypeStockHLE() {
      }

      public void createLegendItems(Legend legend) {

	  super.createLegendItems( legend );

	  ChartElement legendItems [] = legend.getChilds();

	  int len = legendItems.length;

	  for(int i = 0; i < len - 1; i ++ ) {

	    LegendItem li = (LegendItem) (legendItems[i]);

	    LegendItemSymbol lis = (LegendItemSymbol) li.getChartElement( LegendItemSymbol.class );

	    lis.setShapeStyle( null );

	  }

//	  LegendItem li = (LegendItem) (legendItems[ len - 1 ]);
//
//	  LegendItemSymbol lis = (LegendItemSymbol) li.getChartElement( LegendItemSymbol.class );
//
//	  Rectangle lisArea = lis.getArea();
//
//	  lisArea = new Rectangle( lisArea.x + lisArea.width/2, lisArea.y + lisArea.height/2, finalPriceSymbolWidth, finalPriceSymbolHeight );
//
//	  lis.setShape( lisArea );

      }

   /**
    * set shape and shape style of data series view
    */
   public void setShapeAndStyle(DataSeriesView dataSeriesView) {

      Chart chart = dataSeriesView.getChart();
      PictureExtent pa = chart.getPictureExtent();

      int oci = pa.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

      if( oci == 0 ) {

	  setMinMaxStockDataSeriesViewShapeAndStyle( dataSeriesView, oci );

	  return;

      } else if( oci == 2 ) {

	  setFinalStockDataSeriesViewShapeAndStyle( dataSeriesView, oci );

	  return;
      }

   }

   /**
    * @param oci : occurence order index of data series view
    */

   protected void setMinMaxStockDataSeriesViewShapeAndStyle(DataSeriesView dataSeriesView, int oci ) {

      Chart chart = dataSeriesView.getChart();

	  ValueAxisOption vao = chart.getValueAxisOption();

      PictureExtent pa = chart.getPictureExtent();

      ChartData cd = chart.getChartData();  // cahrt data

      DataSeries [] ds = cd.getDataSeries(); // data series

      int dsLen = ds.length;

      double min = cd.getMinValue();  // minimum value
      double max = cd.getMaxValue();  // maximum value

      calibrateAxisMinMaxValueAndUnit( vao, min, max );

      min = vao.getMinScale();
      max = vao.getMaxScale();

      Color color = getChartColor( oci ); // DataElementView color

      ShapeStyle style = new ShapeStyle( Color.black, Color.black ); // DataElementView Shape Style

      Rectangle paArea = pa.getArea();

      int pax = paArea.x, pay = paArea.y, paw = paArea.width, pah = paArea.height;

      int baseY = pay + pah; // data element view left botton vertical location ( y coordinate )

      DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

      int len = devs.length; // data element view number

      double dw = ( paw + 0.0 )/( len );

      DataElement [] maxStockPriceDEs = ds[oci].getDataElements();
      DataElement [] minStockPriceDEs = ds[oci + 1].getDataElements();

      for(int i = 0; i < len; i ++ ) {

	  DataElementView dev = devs[ i ] ; // data element view

	  dev.setToolTip( "최저/최고값 연결선 1" );

	  dev.setShapeStyle( style );

	  int x = pax + (int)( dw*(i + 0.5) );

	  double maxPrice = maxStockPriceDEs[i].getValue(); // max stock price
	  double minPrice = minStockPriceDEs[i].getValue(); // min stock price

	  double ty = baseY - pah*( maxPrice - min )/( max - min );  // top y

	  double by = baseY - pah*( minPrice - min )/( max - min );   // bottom y

	  Shape shape = new Line2D.Double( x, ty, x, by );

	  dev.setShape( shape );

      }

   }

   /**
    * @param oci : occurence order index of data series view
    */

    protected void setFinalStockDataSeriesViewShapeAndStyle(DataSeriesView dataSeriesView, int oci ) {

        Chart chart = dataSeriesView.getChart();

		ValueAxisOption vao = chart.getValueAxisOption();

        PictureExtent pa = chart.getPictureExtent();

        ChartData cd = chart.getChartData();  // cahrt data

        DataSeries [] ds = cd.getDataSeries(); // data series

        int dsLen = ds.length;

        double min = cd.getMinValue();  // minimum value
        double max = cd.getMaxValue();  // maximum value

        calibrateAxisMinMaxValueAndUnit( vao, min, max );

        min = vao.getMinScale();
        max = vao.getMaxScale();

        Color color = getChartColor( oci ); // DataElementView color

        ShapeStyle style = new ShapeStyle( Color.black, Color.black ); // DataElementView Shape Style

        Rectangle paArea = pa.getArea();

        int pax = paArea.x, pay = paArea.y, paw = paArea.width, pah = paArea.height;

        int baseY = pay + pah; // data element view left botton vertical location ( y coordinate )

        DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

        int len = devs.length; // data element view number

        double dw = ( paw + 0.0 )/( len );

        DataElement [] finalStockPriceDEs = ds[oci].getDataElements();

        for(int i = 0; i < len; i ++ ) {

	        DataElementView dev = devs[ i ] ; // data element view

	        dev.setShapeStyle( style );

	        int x = pax + (int)( dw*(i + 0.5) );

	        double finalPrice = finalStockPriceDEs[i].getValue(); // final stock price

			double y = baseY - pah*( finalPrice - min )/( max - min );   // bottom y

			Shape shape = new Rectangle2D.Double( x, y - finalPriceSymbolHeight, finalPriceSymbolWidth, finalPriceSymbolHeight );

			dev.setShape( shape );

        }

   }

    public boolean isValidChartData(ChartData cd, Object source) {

        boolean result = super.isValidChartDataValues( cd, source );

        if( result == false ) {

			return false;

        }

        DataSeries [] dss = cd.getDataSeries();

        String warnMsg = "주식 차트를 만들려면 주식의 최고 가격, 최저 가격, 최종 가격 순서대로 시트에 데이터를 정렬해야 합니다. 날짜나 주식 이름을 레이블로 사용합니다.";

        if( dss.length !=3 ) {

			Utility.warn( warnMsg, SpreadSheet.getCurrentSpreadSheet() );

			return false;

        }

        if( dss[0].getSeriesName().equals( "최고 가격")
			&& dss[1].getSeriesName().equals( "최저 가격")
			&& dss[2].getSeriesName().equals( "최종 가격") ) {

			return true;

        }

        Utility.warn( warnMsg, SpreadSheet.getCurrentSpreadSheet() );
        return false;

    }

}