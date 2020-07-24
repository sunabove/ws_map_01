
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
	 * DataElement�� �����ϰ� �ִ� SpreadSheet ��ü
	 * getValue()�޼��忡�� �ʿ�
	 */
	private SpreadSheet spreadSheet;

	/**
	 * ��� ���⵵ ���ȣ���� �ϴ±�
	 * ���� �߰��� ������(�� �������� ����)
	 * ������ �޼����� getString() ������ �ʿ�
	 */
	private DataSeries dataSeries;

	/**
	 * SpreadSheet���� DataElemnt�� ��ġ�� ��Ÿ���� integer ��
	 */
	private int row, col;

	/**
	 * ������ :
	 */
	public DataElement(SpreadSheet sheet, int row, int col) {
		this.spreadSheet = sheet;
		this.row = row;
		this.col = col;
	}

    /**
     * row�� �����Ѵ�.
     */
    public int getRow() {
        return row;
    }

    /**
     * col�� �����Ѵ�.
     */
    public int getColumn() {
        return col;
    }

	/**
	 * DataSeries�� series�� �����Ѵ�.
	 */
	public void setDataSeries(DataSeries series) {
		this.dataSeries = series;
	}

	/**
	 * DataElement�� �����ϰ� �ִ� ReferenceSeries�� �̸��� ��ȯ�Ѵ�.
	 */
	public String getReferenceName() {
		ChartData cd = dataSeries.getChartData();

		return cd.getReferenceName( row, col );
	}

	public double getPercentedValue() {

		return getValue() / dataSeries.getSum() * 100;

	}

	/**
	 * DataElenment�� �ش��ϴ� ���� ���� �����Ѵ�.
	 */
	public double getValue() {
		return Double.valueOf( spreadSheet.getDataAt( row, col ) ).doubleValue();
	}

	/**
	 * dataSeries�� �����Ѵ�.
	 */
	public DataSeries getDataSeries() {
		return dataSeries;
	}

	/**
	 * Chart���� �ش� DataElement�� ���õǾ����� �� DataElement�� �迭�� ����, �׸��� ���� �����Ѵ�.
	 */
	public String toString() {
		return   "�迭 \"" + dataSeries.getSeriesName()
				 + "\" ���� \"" + getReferenceName() + "\" ��:" + getValue() ;
	}
}