
/**
 * Title:        java chart project<p>
 * Description:  jchart v1.0<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package jchart;

public class DataSeries {

  private ChartData chartData;
  private DataElement [] dataElement;
  private String seriesName;
  private LegendItem legendItem;
  private DataSeriesView dataSeriesView;

  public DataSeries(ChartData chartData, String seriesName, DataElement [] dataElement) {

     this.chartData = chartData;
     this.seriesName = seriesName;
     this.dataElement = dataElement;

     int size = dataElement.length;

     for( int i = 0; i < size; i ++ ) {
	 dataElement[ i ].setDataSeries( this );
     }
  }

  public DataElement [] getDataElements() {
     return dataElement;
  }

  public ChartData getChartData() {
    return chartData;
  }

  public String getSeriesName() {
     return seriesName;
  }

  public double getMaxValue() {
     DataElement [] dataElement = this.dataElement;

     int len = dataElement.length;

     double max = Double.MIN_VALUE;

     for(int i = 0; i < len; i ++ ) {
	double val = dataElement[i].getValue();
	max = ( max > val ) ? max : val;
     }

     return max;
  }

  public double getMinValue() {
     DataElement [] dataElement = this.dataElement;

     int len = dataElement.length;

     double min = Double.MAX_VALUE;

     for(int i = 0; i < len; i ++ ) {
	double val = dataElement[i].getValue();
	min = ( min < val ) ? min : val;
     }

     return min;
  }

  public double getAbsoluteSum() {
     DataElement [] des = this.dataElement;

     double sum = 0;

     for(int i = 0, len = des.length; i < len; i ++ ) {
	sum += Math.abs( des[i].getValue() );
     }

     return sum;
  }

  public double getSum() {
     DataElement [] des = this.dataElement;

     double sum = 0;

     for(int i = 0, len = des.length; i < len; i ++ ) {
	sum += des[i].getValue() ;
     }

     return sum;
  }

	public void setLegendItem(LegendItem legendItem) {

	    this.legendItem = legendItem;

	}

	public LegendItem getLegendItem() {

		return this.legendItem;

	}


	public void setDataSeriesView(DataSeriesView dataSeriesView) {

		this.dataSeriesView = dataSeriesView;
	}

	public DataSeriesView getDataSeriesView() {

		return dataSeriesView;

	}

	public double [] converToDoubleArray( ) {

	  DataElement [] des = getDataElements();

	  int len = des.length;

	  double array [] = new double[ len ];

	  for( int i = 0; i < len; i ++ ) {

	      array[ i ] = des[i].getValue();

	  }

	  return array;

	}

}