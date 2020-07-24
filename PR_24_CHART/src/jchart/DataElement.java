
/**
* Title:        java chart project<p>
* Description:  jchart v1.0<p>
* Copyright:    Copyright (c) Suhng ByuhngMunn<p>
* Company:      JCOSMOS DEVELOPMENT<p>
* @author Suhng ByuhngMunn
* @version 1.0
*/
package jchart;

public class DataElement {

	/**
	 * DataElement를 포함하고 있는 SpreadSheet 개체
	 * getValue()메서드에서 필요
	 */
	private SpreadSheet spreadSheet;

	/**
	 * 어라 여기도 재귀호출을 하는군
	 * 내가 발견한 이유는(더 있을수도 있음)
	 * 마지막 메서드인 getString() 때문에 필요
	 */
	private DataSeries dataSeries;

	/**
	 * SpreadSheet에서 DataElemnt의 위치를 나타내는 integer 값
	 */
	private int row, col;

	/**
	 * 생성자 :
	 */
	public DataElement(SpreadSheet sheet, int row, int col) {
		this.spreadSheet = sheet;
		this.row = row;
		this.col = col;
	}

    /**
     * row를 리턴한다.
     */
    public int getRow() {
        return row;
    }

    /**
     * col를 리턴한다.
     */
    public int getColumn() {
        return col;
    }

	/**
	 * DataSeries를 series로 설정한다.
	 */
	public void setDataSeries(DataSeries series) {
		this.dataSeries = series;
	}

	/**
	 * DataElement를 포함하고 있는 ReferenceSeries의 이름을 반환한다.
	 */
	public String getReferenceName() {
		ChartData cd = dataSeries.getChartData();

		return cd.getReferenceName( row, col );
	}

	public double getPercentedValue() {

		return getValue() / dataSeries.getSum() * 100;

	}

	/**
	 * DataElenment에 해당하는 셀의 값을 리턴한다.
	 */
	public double getValue() {
		return Double.valueOf( spreadSheet.getDataAt( row, col ) ).doubleValue();
	}

	/**
	 * dataSeries를 리턴한다.
	 */
	public DataSeries getDataSeries() {
		return dataSeries;
	}

	/**
	 * Chart에서 해당 DataElement가 선택되었을때 그 DataElement의 계열과 참조, 그리고 값을 리턴한다.
	 */
	public String toString() {
		return   "계열 \"" + dataSeries.getSeriesName()
				 + "\" 참조 \"" + getReferenceName() + "\" 값:" + getValue() ;
	}
}