
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
	 * �迭 ��ġ(��/��)�� ��Ÿ���� boolean Ÿ���� ������
	 *	true�̸� �� ������ false�̸� �� ������ ��Ÿ����.
	 */
	private boolean seriesLocationHorizontal = true;


	private DataSeries [] dataSeries;
	private DataSeries [] referenceSeries;

	/**
	 * Y�� ���ݿ� ���� ���� ���� ��
	 */
	private double axisMinValue = Double.MIN_VALUE;

	/**
	 * Y�� ���ݿ� ���� ���� ū ��
	 */
	private double axisMaxValue = Double.MAX_VALUE;

	/**
	 * �� ������ Y(��)���ݿ��� �� ������ �ش��ϴ� ��
	 */
	private double mainAxisIncrement = Double.MIN_VALUE;

	/**
	 * divNumber�� �� 6�ϱ�?
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
	 * �迭 ��ġ(��/��)�� �����Ѵ�.
	 */
	public void setDataSeriesLocationHorizontal(boolean horizontal) {

		this.seriesLocationHorizontal = horizontal;

	}

	/**
	 * �迭 ��ġ�� ���� DataSeries�� ������ �����Ѵ�.
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
	 * �迭 ��ġ�� ���� ReferenceSeries�� ������ �����Ѵ�.
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
	 * dataSeries�� �ִ� ��� DataElement�� ������ ���� ū ���� �����Ѵ�.
	 * �̰��� Y���� ���� ū ���� �����ϱ� ���� �ʿ��ϴ�.
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
	 * dataSeries�� �ִ� ��� DataElement�� ������ ���� ���� ���� �����Ѵ�.
	 * �̰��� Y���� ���� ���� ���� �����ϱ� ���� �ʿ��ϴ�.
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
	 * ���� Row�� �ִ� DataElement���� DataSeries�� �ְ� �� DataSeries�� ��ȯ�Ѵ�.
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
	 * ���� Column�� �ִ� DataElement���� DataSeries�� �ְ� �� DataSeries�� ��ȯ�Ѵ�.
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
	 * ChartData�� ���� ��� �迭 �̸��� �����Ѵ�.
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
	 * ChartData���� (row, col)��ġ�� �ִ� DataElement�� ���� �迭�� �̸��� �����Ѵ�.
	 */
	public String getReferenceName(int row, int col) {
		if( seriesLocationHorizontal ) {
			return sheet.getDataAt( rows[0], col );
		} else {
			return sheet.getDataAt( row, cols[0] );
		}
	}

	/**
	 * �� ������ Y(��)���ݿ��� �� ������ �ش��ϴ� ������ ����Ʈ��
	 * (max - min)/ divNumber �Ǿ� �ִ�.
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
	 * divNumber�� �ʿ��� ������?
	 */
	public void setDivisionNumber(int divNumber) {
		this.divNumber = divNumber;
	}

	/**
	 * divNumber�� �ʿ��� ������?
	 */
	public int getDivisionNumber() {
		return divNumber;
	}

	/**
	 * axisMinValue�� �����Ѵ�.
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
	 * axisMaxValue�� �����Ѵ�.
	 */
	public double getAxisMaxValue( ) {

		if( axisMaxValue == Double.MAX_VALUE ) {

			return getMaxValue()*1.1;

		} else {

			return axisMaxValue;

		}

	}

}