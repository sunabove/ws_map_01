
/**
* Title:        java chart project<p>
* Description:  jchart v1.0<p>
* Copyright:    Copyright (c) Suhng ByuhngMunn<p>
* Company:      JCOSMOS DEVELOPMENT<p>
* @author Suhng ByuhngMunn
* @version 1.0
*/
package jchart;

public class ChartData {

	private SpreadSheet sheet;

	private int [] rows;
	private int [] cols;

	/**
	 * 계열 위치(행/열)를 나타내는 boolean 타입의 변수로
	 *	true이면 행 기준을 false이면 열 기준을 나타낸다.
	 */
	private boolean seriesLocationHorizontal = true;


	private DataSeries [] dataSeries;
	private DataSeries [] referenceSeries;

	/**
	 * Y축 눈금에 들어가는 가장 낮은 값
	 */
	private double axisMinValue = Double.MIN_VALUE;

	/**
	 * Y축 눈금에 들어가는 가장 큰 값
	 */
	private double axisMaxValue = Double.MAX_VALUE;

	/**
	 * 축 서식중 Y(축)눈금에서 주 단위에 해당하는 값
	 */
	private double mainAxisIncrement = Double.MIN_VALUE;

	/**
	 * divNumber가 왜 6일까?
	 */
	private int divNumber = 6;

	public ChartData(SpreadSheet sheet, int [] rows, int [] cols) {
		this.sheet = sheet;
		this.rows = rows;
		this.cols = cols;
	}

	public ChartData cloneChartData() {

		// copys rows and cols

		int rows [] = this.rows;
		int cols [] = this.cols;

		int rowLen = rows.length;
		int colLen = cols.length;

		int [] cloneRows = new int[ rowLen ];
		int [] cloneCols = new int[ colLen ];

		System.arraycopy( rows, 0, cloneRows, 0, rowLen );
		System.arraycopy( cols, 0, cloneCols, 0, colLen );

		ChartData cd = new ChartData(sheet, cloneRows, cloneCols );

		// end of copying rows and cols

		// copys another member data

		cd.divNumber = this.divNumber;
		cd.axisMaxValue = this.axisMaxValue;
		cd.axisMinValue = this.axisMinValue;
		cd.mainAxisIncrement = this.mainAxisIncrement;
		cd.seriesLocationHorizontal = this.seriesLocationHorizontal;

		// end of copying another member data

		return cd;

	}

	/**
	 * 계열 위치(행/열)를 설정한다.
	 */
	public void setDataSeriesLocationHorizontal(boolean horizontal) {

		this.seriesLocationHorizontal = horizontal;

	}

	/**
	 * 계열 위치에 따라서 DataSeries의 값들을 설정한다.
	 */
	public DataSeries [] getDataSeries() {

		if( dataSeries == null ) {

			if( seriesLocationHorizontal ) {

				dataSeries = getHorizontalDataSeries();

			} else {

				dataSeries = getVerticalDataSeries();

			}

		}

		return dataSeries;

	}

	/**
	 * 계열 위치에 따라서 ReferenceSeries의 값들을 설정한다.
	 */
	public DataSeries [] getReferenceSeries() {

		if( referenceSeries == null ) {

			if( seriesLocationHorizontal ) {

				referenceSeries = getVerticalDataSeries();

			} else {

				referenceSeries = getHorizontalDataSeries();

			}

		}

		return referenceSeries;

	}


	/**
	 * dataSeries에 있는 모든 DataElement의 내용중 가장 큰 값을 리턴한다.
	 * 이것은 Y축의 가장 큰 값을 결정하기 위해 필요하다.
	 */
	public double getMaxValue() {

		DataSeries [] dataSeries = getDataSeries();

		int len = dataSeries.length;

		double max = Double.MIN_VALUE;

		for(int i = 0; i < len; i ++ ) {
			DataSeries ds = dataSeries[i];
			double val = ds.getMaxValue();

			max = ( max > val ) ? max : val;
		}

		return max;

	}


	/**
	 * dataSeries에 있는 모든 DataElement의 내용중 가장 작은 값을 리턴한다.
	 * 이것은 Y축의 가장 작은 값을 결정하기 위해 필요하다.
	 */
	public double getMinValue() {

		DataSeries [] dataSeries = getDataSeries();

		int len = dataSeries.length;

		double min = Double.MAX_VALUE;

		for(int i = 0; i < len; i ++ ) {
			DataSeries ds = dataSeries[i];
			double val = ds.getMinValue();

			min = ( min < val ) ? min : val;
		}

		return min;

	}

	/**
	 * 같은 Row에 있는 DataElement들을 DataSeries에 넣고 그 DataSeries를 반환한다.
	 */
	private DataSeries [] getHorizontalDataSeries() {

		int [] rows = this.rows, cols = this.cols;

		int  rl = rows.length - 1 ; // row length except header

		DataSeries [] series = new DataSeries[ rl ];

		for( int i = 0; i < rl; i ++ ) {
			int cl = cols.length - 1; // col length except header

			DataElement [] des = new DataElement[  cl ]; // data elements

			for( int j = 0; j < cl; j ++ ) {
				des[ j ] = new DataElement( sheet, rows[ i + 1 ], cols[ j + 1 ] );
			}

			series[ i ] = new DataSeries( this, sheet.getDataAt(rows[ i + 1 ], cols[0]), des );
		}

		return series;

	}


	/**
	 * 같은 Column에 있는 DataElement들을 DataSeries에 넣고 그 DataSeries를 반환한다.
	 */
	private DataSeries [] getVerticalDataSeries() {

		int [] rows = this.rows, cols = this.cols;

		int cl = cols.length - 1; // col length except header

		DataSeries [] series = new DataSeries[ cl ];

		for( int i = 0; i < cl; i ++ ) {

			int  rl = rows.length - 1 ; // row length except header

			DataElement [] des = new DataElement[  rl ]; // data elements

			for( int j = 0; j < rl; j ++ ) {

				des[ j ] = new DataElement( sheet, rows[ j + 1 ], cols[ i + 1 ] );

			}

			series[ i ] = new DataSeries( this, sheet.getDataAt(rows[0], cols[ i + 1 ] ), des );

		}

		return series;
	}

	/**
	 * ChartData에 속한 모든 계열 이름을 리턴한다.
	 */
	public String [] getReferenceNames() {

		DataSeries series = this.getDataSeries()[ 0 ];
		DataElement dataElements [] = series.getDataElements();

		int len = dataElements.length;

		String refNames [] = new String[ len ];

		for(int i = 0; i < len; i ++ ) {
			refNames[ i ] = dataElements[i].getReferenceName();
		}

		return refNames;
	}

	/**
	 * ChartData에서 (row, col)위치에 있는 DataElement가 속한 계열의 이름을 리턴한다.
	 */
	public String getReferenceName(int row, int col) {
		if( seriesLocationHorizontal ) {
			return sheet.getDataAt( rows[0], col );
		} else {
			return sheet.getDataAt( row, cols[0] );
		}
	}

	/**
	 * 축 서식중 Y(축)눈금에서 주 단위에 해당하는 것으로 디폴트로
	 * (max - min)/ divNumber 되어 있다.
	 */
	public double getAxisMainIncrement() {
		if( mainAxisIncrement == Double.MIN_VALUE ) {

			double min = getAxisMinValue();
			double max = getAxisMaxValue();

			return (max - min)/divNumber;

		} else {

			return mainAxisIncrement;
		}
	}


	/**
	 * divNumber가 필요한 이유는?
	 */
	public void setDivisionNumber(int divNumber) {
		this.divNumber = divNumber;
	}

	/**
	 * divNumber가 필요한 이유는?
	 */
	public int getDivisionNumber() {
		return divNumber;
	}

	/**
	 * axisMinValue를 리턴한다.
	 */
	public double getAxisMinValue( ) {

		if( axisMinValue == Double.MIN_VALUE ) {

			double min = getMinValue( );

			return ( min < 0 ) ? min : 0 ;

		} else {

			return axisMinValue;

		}
	}

	/**
	 * axisMaxValue를 리턴한다.
	 */
	public double getAxisMaxValue( ) {

		if( axisMaxValue == Double.MAX_VALUE ) {

			return getMaxValue()*1.1;

		} else {

			return axisMaxValue;

		}

	}

}