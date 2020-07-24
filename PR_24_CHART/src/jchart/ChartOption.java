package jchart;

/**
 * Title:           ChartOption 클래스
 * Company:         Techdigm Corp.
 * @author          withhim
 * @version         1.1 (modified by withhim)
 * Description:     Chart의 Option에 해당하는 자료들을 모아놓은 클래스
 */

public class ChartOption {


	private String chartTitle;			// 차트 제목을 나타내는 String
	private String xAxisTitle;			// X(항목) 제목을 나타내는 String
	private String yAxisTitle ;			// Y(항목) 제목을 나타내는 String

	// 축 표시하기의 디폴트 값은 자동으로 한다.
	private byte xAxis = AUTO_X_AXIS;	// X 축 표시하기는 보이기 옵션 중 자동으로 셋팅
	private byte yAxis = SHOW_Y_AXIS;	// Y 축 표시하기는 show로 한다.

	// 축 표시하기에 사용되는 static 상수
	public static final byte NULL_X_AXIS = 0, AUTO_X_AXIS = 1, ITEM_X_AXIS = 2, TIME_UNIT_X_AXIS = 3;
	public static final byte NULL_Y_AXIS = 0, SHOW_Y_AXIS = 1;

	private byte legendLocation = RIGHT_LEGEND;

	public static final byte BOTTOM_LEGEND = 0, CORNER_LEGEND = 1, TOP_LEGEND = 2, LEFT_LEGEND = 3, RIGHT_LEGEND = 4, NULL_LEGEND = 5;

	private byte dataLabelType = NULL_DATA_LABEL;

	public static final byte NULL_DATA_LABEL = 0, SHOW_VALUE = 1, SHOW_LABEL = 2;

	private boolean hasDataTable = false;

	private boolean showLegendItemSymbolInDataTable = false;



	// 생성자1 :
	public ChartOption() {
      this(null, null, null);
	}

	// 생성자2 :
	public ChartOption(String chartTitle, String xAxisTitle, String yAxisTitle) {
		this.chartTitle = chartTitle;
		this.xAxisTitle = xAxisTitle;
		this.yAxisTitle = yAxisTitle;
	}

    public ChartOption cloneChartOption() {

        // copys chart title, x axis title and y axis title
        ChartOption co = new ChartOption( chartTitle, xAxisTitle, yAxisTitle );
        // end of copying chart title, x axis title and y axis title

        // copys another memeber data

        co.xAxis = this.xAxis;
        co.yAxis = this.yAxis;
        co.legendLocation = this.legendLocation;
        co.dataLabelType = this.dataLabelType;
        co.hasDataTable = this.hasDataTable;
        co.showLegendItemSymbolInDataTable = this.showLegendItemSymbolInDataTable;

        // end of copying another memeber data

        return co;

    }


	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		if( chartTitle != null && chartTitle.length() == 0 ) {
			chartTitle = null;
		}
		this.chartTitle = chartTitle;
	}

	public String getXAxisTitle() {
		return xAxisTitle;
	}

	public void setXAxisTitle(String xAxisTitle) {
		if( xAxisTitle != null && xAxisTitle.length() == 0 ) {
			xAxisTitle = null;
	}
		this.xAxisTitle = xAxisTitle;
	}

	public String getYAxisTitle() {
		return yAxisTitle;
	}

	public void setYAxisTitle(String yAxisTitle) {
		if( yAxisTitle != null && yAxisTitle.length() == 0 ) {
			yAxisTitle = null;
		}
		this.yAxisTitle = yAxisTitle;
	}


	public byte getLegendLocation() {
		return legendLocation;
	}

	public void setLegendLocation(byte legendLocation) {
		this.legendLocation = legendLocation;
	}

	// withhim makes
	public byte getDataLabelType() {
		return dataLabelType;
	}
	// withhim makes
	public void setDataLabelType(byte dataLabelType) {
		this.dataLabelType = dataLabelType;
	}

	public boolean getHasDataTable() {
	    return hasDataTable;
	}

	public void getHasDataTable(boolean hasDataTable) {
		this.hasDataTable = hasDataTable;
	}

	public void setShowLegendItemSymbolInDataTable(boolean showLegendItemSymbolInDataTable) {
		this.showLegendItemSymbolInDataTable = showLegendItemSymbolInDataTable;
	}

	public boolean getShowLegendItemSymbolInDataTable() {
		return showLegendItemSymbolInDataTable;
	}

}