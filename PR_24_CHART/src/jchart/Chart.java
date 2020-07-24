
/**
 * Title:        java chart project<p>
 * Description:  jchart v1.0<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package jchart;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import jcosmos.*;

public class Chart extends ChartElement {

	private ChartData chartData;
	private ChartType chartType;
	private ChartOption chartOption;
	private ItemAxisOption itemAxisOption;
	private ValueAxisOption valueAxisOption;

	protected static float graphExtentLeftMarginRatio = 0.05F, graphExtentTopMarginRatio = 0.05F;
	protected static float graphExtentWidthRatio = 0.78F, graphExtentHeightRatio = 0.85F;
//	protected static float legendRighttMarginRatio = 0.025F;
//	protected static float legendWidthRatio = 0.12F, legendHeightRatio = 0.3F;


	public Chart(SpreadSheet sheet, ChartData chartData, Rectangle area, ChartType chartType, ChartOption chartOption) {

		super( sheet, null, area, new ShapeStyle(Color.white, Color.black) );

		this.chartData = chartData;
		this.chartOption = chartOption;
		this.itemAxisOption = new ItemAxisOption();
		this.valueAxisOption = new ValueAxisOption();

		// chart type setting and graph extent

		GraphExtent graphExtent = getGraphExtent( chartType );

		addChild( graphExtent );

		// end of setting chart type and graph extent

		// adding legend

		Legend legend = createLegend();

		addChild( legend );

		legend.alignTo( graphExtent, Align.VER_CENTER );

		// end of legend addition

	}

	public ChartElement cloneSelf(ChartElement parent, SpreadSheet sheet) {

		Rectangle area = getArea();

		return new Chart( sheet, chartData.cloneChartData(), area, chartType, chartOption.cloneChartOption() );

	}

	public void applyChartType(ChartType chartType) {

		/**@todo check chart data validity
		 *
		 */

		if( ! chartType.isValidChartData( this.chartData, this ) ) {

		  return;

		}

		GraphExtent graphExtent = getGraphExtent( chartType );

		addChild( graphExtent );

		Legend legend = (Legend) getChartElement( Legend.class );

		if( legend == null ) {

			legend = createLegend();

			addChild( legend );

		} else {

			chartType.createLegendItems( legend );

		}

	}

	public void applyChartOption(ChartOption chartOption) {

		this.chartOption = chartOption;

		applyChartTitle( chartOption.getChartTitle() );

		applyXAxisTitle( chartOption.getXAxisTitle() );

		applyYAxisTitle( chartOption.getYAxisTitle() );

		applyDataTable( chartOption.getHasDataTable(), chartOption.getShowLegendItemSymbolInDataTable() );

	}

	public void applyDataTable(boolean hasDataTable , boolean showLegend ) {

		if( ( ! hasDataTable ) ) {

			Chart chart = this;

			DataTable dataTable = (DataTable) chart.getChartElement( DataTable.class );

			if( dataTable != null ) {

				GraphExtent ge = (GraphExtent) chart.getChartElement( GraphExtent.class );

				if( ge != null ) {

					Rectangle geArea = ge.getArea();

					Rectangle dtArea = dataTable.getArea();

					int nh = dtArea.y + dtArea.height - geArea.y; // new graph extent height

					int ph = geArea.height; // previous graph extent height

					double ratio = nh/ph;

					ge.resizeAndMoveBy( geArea.width, nh, 0, 0 );

					Legend legend = (Legend) chart.getChartElement( Legend.class );

					if( legend != null ) {
						Rectangle lgArea = legend.getArea(); // legend area
						legend.resizeAndMoveBy( lgArea.width, (int)(lgArea.height * ratio), 0, 0 );
						legend.alignTo( ge, Align.VER_CENTER );
					}

					YAxisTitle yAxisTitle = (YAxisTitle) chart.getChartElement( YAxisTitle.class );

					if( yAxisTitle != null ) {
						Rectangle yaArea = yAxisTitle.getArea();
						yAxisTitle.resizeAndMoveBy( yaArea.width, (int)(yaArea.height*ratio), 0, 0);
						yAxisTitle.alignTo( ge, Align.VER_CENTER );
					}

				}

				chart.removeChild( dataTable );

			}

		} else {

			Chart chart = this;

			DataSeries [] dss = chart.getChartData().getDataSeries();

			int dsNum = dss.length;

			ChartType ct = chart.getChartType();

			Rectangle area = chart.getArea();

			GraphExtent ge = (GraphExtent) chart.getChartElement( GraphExtent.class );

			Rectangle geArea = ge.getArea();

			int oldGeHeight = geArea.height;

			int baseY = geArea.y + geArea.height;

			XAxisTitle xAxisTitle = (XAxisTitle) chart.getChartElement( XAxisTitle.class );

			YAxisTitle yAxisTitle = (YAxisTitle) chart.getChartElement( YAxisTitle.class );

			boolean horizontalChartType = ct instanceof HorizontalChartType;

			ChartElement xAxis = ( horizontalChartType ? (ChartElement) ge.getValueAxis() : (ChartElement) ge.getItemAxis() );
			ChartElement yAxis = ( horizontalChartType ? (ChartElement) ge.getItemAxis() : (ChartElement) ge.getValueAxis() );

			Legend legend = (Legend) chart.getChartElement( Legend.class );

			LegendItem legendItem = null;

			if( legend != null ) {

			  legendItem = (LegendItem) legend.getChartElement( LegendItem.class );

			}

			//
			//         ------------------------
			//         |                       |
			//         |          PE           |
			//    x____|_______________________|
			//  y |    |                       |
			//  h |    |          DT           |
			//    |____|_______________________|
			//      w1             w2
			//

			int x = geArea.x, y, w1, w2, h;

			if( legendItem != null ) {

			  Rectangle liArea = legendItem.getArea();

			  h = liArea.height*(dsNum + 1 );
			  w1 = liArea.width;
			  w2 = geArea.width - w1;
			  y = baseY - h;

			} else {

			  int defaultLegendItemHeight = 20;
			  int defaultLegendItemWidth  = 40;

			  h = defaultLegendItemHeight*(dsNum + 1 );
			  w1 = defaultLegendItemWidth;
			  w2 = geArea.width - w1;
			  y = baseY - h;

			}

			if( xAxisTitle != null ) {

				// remove xAxisTitle temporary

				ge.removeChild( xAxisTitle );

			}

			int yAxisWidth = ( yAxis != null ? yAxis.getArea().width : 0 );

			if( horizontalChartType ) {

			    ge.resizeAndMoveBy( w2 + yAxisWidth, geArea.height - h , x + w1 - yAxisWidth - geArea.x, 0 );

			} else {

			    int ngh = (int)(oldGeHeight*(oldGeHeight - h + 0.0)/(oldGeHeight - xAxis.getArea().height + 0.0));

			    ge.resizeAndMoveBy( w2 + yAxisWidth, ngh, x + w1 - yAxisWidth - geArea.x, 0 );

			}

			geArea = ge.getArea();

			double geHeightRatio = geArea.height/(oldGeHeight + 0.0);

			if( yAxisTitle != null ) {

				Rectangle yAxisTitleArea = yAxisTitle.getArea();

				yAxisTitle.resizeAndMoveBy( yAxisTitleArea.width, (int)(yAxisTitleArea.height*geHeightRatio), 0, 0 );

				yAxisTitle.alignTo( ge, Align.VER_CENTER );

			}

			if( legend != null ) {

				Rectangle legendArea = legend.getArea();

				legend.resizeAndMoveBy( legendArea.width, (int)(legendArea.height*geHeightRatio), 0, 0);

				legend.alignTo( ge, Align.VER_CENTER );

			}

			Rectangle dataTableArea = new Rectangle( x, y, w1 + w2, h );

			ShapeStyle style = ShapeStyle.getDefaultShapeStyle();
			Font font = FontManager.getDefaultFont();

			DataTable dataTable = new DataTable( sheet, this, dataTableArea, style, font, showLegend, true, true, true );

			addChild( dataTable );

			if( xAxisTitle != null ) { // add xAxisTitle removed temporary

				ge.addChild( xAxisTitle );

			}

		}

	}

	public void applyYAxisTitle(String yAxisTitleText ) {

		YAxisTitle yAxisTitle = (YAxisTitle) getChartElement( YAxisTitle.class );

		chartOption.setYAxisTitle( yAxisTitleText );

		if( yAxisTitleText == null || yAxisTitleText.length() < 1 ) {

			if( yAxisTitle != null ) {
				this.removeChild( yAxisTitle );
			}

			return;
		}

		if( yAxisTitle == null ) {

			GraphExtent ge = (GraphExtent) getChartElement( GraphExtent.class ); // graph extent

			//Rectangle ca = getArea(); // chart area
			Rectangle gea = ge.getArea(); // graph extent area

			int gx = gea.x, gy = gea.y, gw = gea.width, gh = gea.height;
			//int cx = ca.x, cy = ca.y, cw = ca.width, ch = ca.height;

			// move and resize graph extent
			int left_margin = (int)( gw/8.0);  // bottom margin between graph extent underline and chart under line
			int ngw =gw - left_margin, ngh = gh; // new graph extent bounds

			ge.resizeAndMoveBy( ngw, ngh, left_margin, 0 );
			// end of graph extent reformation

			// set new applied value to graph extent information
			gea = ge.getArea();
			//gx = gea.x; gy = gea.y; gw = gea.width; gh = gea.height;
			// end fo setting new applied value to graph extent information

			int cen_x = (int)((gx + gea.getX())/2.0), cen_y = (int) (gea.getY() + gea.getHeight()/2 );

			ShapeStyle style = ShapeStyle.getDefaultShapeStyle();

			Font font = FontManager.getDefaultFont();

			yAxisTitle = new YAxisTitle( yAxisTitleText, font, sheet, this, cen_x, cen_y, style );

			addChild( yAxisTitle );

			// algin legend to graph extent

			Legend legend = (Legend) getChartElement( Legend.class );

			if ( legend != null ) {

				legend.alignTo( ge, Align.VER_CENTER );

			}


			legend.alignTo( ge, Align.VER_CENTER );

			// end of legend alignment

			// align chart title to graph extent

			ChartTitle chartTitle = (ChartTitle) getChartElement( ChartTitle.class );

			if (chartTitle != null ) {

			    chartTitle.alignTo( ge, Align.HOR_CENTER );

			}

			// end of chart title alignment

			// align x title to graph extent

			XAxisTitle xAxisTitle = (XAxisTitle) getChartElement( XAxisTitle.class );

			if ( xAxisTitle != null ) {

				xAxisTitle.alignTo( ge, Align.HOR_CENTER );

			}
			// end of x title alignment

		} else {
			yAxisTitle.setText( yAxisTitleText );
		}



	}

	public void applyXAxisTitle(String xAxisTitleText ) {
		XAxisTitle xAxisTitle = (XAxisTitle) getChartElement( XAxisTitle.class );

		chartOption.setXAxisTitle( xAxisTitleText );

		if( xAxisTitleText == null || xAxisTitleText.length() < 1  ) {

			if( xAxisTitle != null ) {
				this.removeChild( xAxisTitle );
			}

			return;
		}

		if( xAxisTitle == null ) {

			GraphExtent ge = (GraphExtent) getChartElement( GraphExtent.class ); // graph extent

			Rectangle ca = getArea(); // chart area
			Rectangle gea = ge.getArea(); // graph extent area

			int gx = gea.x, gy = gea.y, gw = gea.width, gh = gea.height;
			int cx = ca.x, cy = ca.y, cw = ca.width, ch = ca.height;

			// move and resize graph extent
			int bottom_margin = (int)( ch*1.5/15.0);  // bottom margin between graph extent underline and chart under line
			int ngw =gw, ngh = gh - bottom_margin; // new graph extent bounds

			ge.resizeAndMoveBy( ngw, ngh, 0, 0 );
			// end of graph extent reformation

			// set new applied value to graph extent information
			gea = ge.getArea();
			gx = gea.x; gy = gea.y; gw = gea.width; gh = gea.height;
			// end fo setting new applied value to graph extent information

			int cen_x = gx + gw/2, cen_y = gy + gh + bottom_margin/2;

			ShapeStyle style = ShapeStyle.getDefaultShapeStyle();

			Font font = FontManager.getDefaultFont();

			xAxisTitle = new XAxisTitle( xAxisTitleText, font, sheet, this, cen_x, cen_y, style );

			addChild( xAxisTitle );

			// algin legend to graph extent
			Legend legend = (Legend) getChartElement( Legend.class );

			if ( legend != null ) {

				legend.alignTo( ge, Align.VER_CENTER );

			}
			// end of legend alignment

		} else {
			xAxisTitle.setText( xAxisTitleText );
		}


	}

	/**
	 * 임의의 text를 ChartTitle에 적용시킨다. 만약 ChartTitle이 현재 Chart에 없을
	 * 때에는 새로 ChartTitle개체를 생성시킨다.
	 *
	 * @param   chartTitleText : 적용시킬 ChartTitle의 text 내용
	 */
	public void applyChartTitle(String chartTitleText) {

		ChartTitle chartTitle = (ChartTitle) getChartElement( ChartTitle.class );

		chartOption.setChartTitle( chartTitleText );

		if( chartTitleText == null || chartTitleText.length() < 1 ) {

			if( chartTitle != null ) {
				this.removeChild( chartTitle );
			}

			return;
		}

		if( chartTitle == null ) {

			GraphExtent ge = (GraphExtent) getChartElement( GraphExtent.class ); // graph extent

			Rectangle ca = getArea(); // chart area
			Rectangle gea = ge.getArea(); // graph extent area

			int gx = gea.x, gy = gea.y, gw = gea.width, gh = gea.height;
			int cx = ca.x, cy = ca.y, cw = ca.width, ch = ca.height;

			// move and resize graph extent
			int move_y = (int)(gh/7.0);
			int ngw =gw, ngh = gh - move_y; // new graph extent bounds

			ge.resizeAndMoveBy( ngw, ngh, 0, move_y );
			// end of graph extent reformation

			// set new applied value to graph extent information
			gea = ge.getArea();
			gx = gea.x; gy = gea.y; gw = gea.width; gh = gea.height;
			// end fo setting new applied value to graph extent information

			int m = (int)((gy - cy)/5); // gap between char top line and chart title and chart title and graph extent top line
			int h = gy - cy - m;

			int cen_x = gx + gw/2, cen_y = cy + m/2 + h/2;

			ShapeStyle style = ShapeStyle.getDefaultShapeStyle();

			Font font = FontManager.getDefaultFont();

			chartTitle = new ChartTitle( chartTitleText, font, sheet, this, cen_x, cen_y, style );

			addChild( chartTitle );

			// algin legend to graph extent

			Legend legend = (Legend) getChartElement( Legend.class );

			if ( legend != null ) {

				legend.alignTo( ge, Align.VER_CENTER );

			}

			// end of legend alignment

		} else {
			chartTitle.setText( chartTitleText );
		}



	}


	/**
	 * ValueAxisOption, ItemAxisOption을 참고하여 눈금선들을 Chart에 적용시킨다.
	 */
//	public void applyGridLineGroup( boolean hasItemMajorGridLineGroup
//								    , boolean hasItemMinorGridLineGroup
//									, boolean hasValueMajorGridLineGroup
//									, boolean hasValueMinorGridLineGroup ) {
//
//		if( hasItemMajorGridLineGroup != itemAxisOption.getHasMajorGridLineGroup() ) {
//			if ( hasItemMajorGridLineGroup == true ) {
//
//				this.chartType.createItemMajorGridLineGroup();
//
//			}else {
//
//
//
//			}
//		}
//
//		if( hasItemMajorGridLineGroup != itemAxisOption.getHasMajorGridLineGroup() ) {
//			if ( hasItemMajorGridLineGroup == true ) {
//
//				this.chartType.createItemMajorGridLineGroup();
//
//			}
//		}
//
//		if( hasItemMajorGridLineGroup != itemAxisOption.getHasMajorGridLineGroup() ) {
//			if ( hasItemMajorGridLineGroup == true ) {
//
//				this.chartType.createItemMajorGridLineGroup();
//
//			}
//		}
//
//		if( hasItemMajorGridLineGroup != itemAxisOption.getHasMajorGridLineGroup() ) {
//			if ( hasItemMajorGridLineGroup == true ) {
//
//				this.chartType.createItemMajorGridLineGroup();
//
//			}
//		}
//
//	}

	public GraphExtent getGraphExtent(ChartType chartType) {

		this.chartType = chartType;

		Utility.debug(this, "setting chart type " + chartType );

		// The previous graph extent is to be removed automatically
		// if new graph extent is added

		// adding new graph extent

		GraphExtent graphExtent = createGraphExtent( chartType );

		return graphExtent;

	}

	public ChartData getChartData() {
		return chartData;
	}

	public ChartType getChartType() {
		return chartType;
	}

	public ChartOption getChartOption() {
		return chartOption;
	}

//	public boolean getHasAxis( byte axisType, byte axisGroup ) {
//
//		if( axisType == AxisOption.ITEM_AXIS_TYPE ) {
//
//			return itemAxisOption.getHasAxis(axisGroup);
//
//		} else {
//
//		    return valueAxisOption.getHasAxis(axisGroup);
//
//		}
//
//	}

	public void setHasAxis( byte axisType, byte axisGroup, boolean hasAxis) {



	}
	//
	// Axis Option 에 관련된 메서드 - 시작
	//
	public AxisOption getAxisOption(Axis axis) {

		if ( axis instanceof ItemAxis ) {

		    return this.itemAxisOption;

		} else {

			return this.valueAxisOption;

		}

	}

	public ValueAxisOption getValueAxisOption() {

		return this.valueAxisOption;

	}

	public ItemAxisOption getItemAxisOption() {

		return this.itemAxisOption;

	}
	//
	// Axis Option 에 관련된 메서드 - 끝
	//


	public byte getDataLabelType() {

		return chartOption.getDataLabelType();


	}

	public void applyDataLabelView( byte dataLabelType, boolean showDataLabelSymbol ) {

		ChartElement [] childs = getPictureExtent().getChilds();

		for ( int i = 0; i < childs.length; i++ ) {

			ChartElement ce = childs[i];

			if ( ce instanceof DataSeriesView ) {

				( ( DataSeriesView ) ce ).applyDataLabelView( dataLabelType, showDataLabelSymbol );
			}

		}

		chartOption.setDataLabelType( dataLabelType );
	}


	public PictureExtent getPictureExtent() {

		return (PictureExtent) getChartElement( PictureExtent.class );

	}


	public GraphExtent createGraphExtent(ChartType chartType) {

		Shape geShape = chartType.createGraphExtentShape( this );

		ShapeStyle geStyle = ShapeStyle.getDefaultShapeStyle();

		return new GraphExtent( sheet, this, geShape, geStyle );

	}

	public void reCreateGraphExtentWhenModifiedValue(ChartType chartType, DataElementView dataElementView) {

		GraphExtent preGe = (GraphExtent) this.getChartElement( GraphExtent.class );   // previous graph extent
		PictureExtent prePe = this.getPictureExtent();                                 // previous picture extent

		Rectangle areaOfPreGe = preGe.getArea();

		int px = (int) areaOfPreGe.getX(), py = (int) areaOfPreGe.getY();
		int pw = (int) areaOfPreGe.getWidth(), ph = (int) areaOfPreGe.getHeight();


		int [] indexs = prePe.getIndexOfSelectedDataElementView(dataElementView);

		GraphExtent curGe = this.getGraphExtent(chartType);     // current graph extent

		this.addChild(curGe);

		Rectangle areaOfCurGe = curGe.getArea();

		int cx = (int) areaOfCurGe.getX(), cy = (int) areaOfCurGe.getY();

		curGe.resizeAndMoveBy(pw, ph, px - cx, py - cy);

		PictureExtent curPe = this.getPictureExtent();

		if ( indexs == null ) {
			return;
		}
		DataElementView curDataElementView = curPe.getSelectedDataElementView( indexs );

		sheet.setSelectedChartElementWhenMousePressed(curDataElementView);

		//		sheet.drawSelectedChartElement(g2);
		sheet.repaint();

	}

	public Legend createLegend() {

		Rectangle area = this.getArea(); // chart area

		int w = (int)( area.width * AppRegistry.LEGEND_WIDTH_RATIO );
		int h = (int)( area.height * AppRegistry.LEGEND_HEIGHT_RATIO );
		int x = (int)( area.x + area.width - area.width * AppRegistry.LEGEND_MARGIN_RATIO - w );
		int y = (int)( area.y + area.height * 0.5F - h * 0.5F );

		Rectangle rect = new Rectangle( x, y, w, h );
		ShapeStyle style = ShapeStyle.getDefaultShapeStyle();

		Legend legend = new Legend( sheet, this, rect, style, this, AppRegistry.LEGEND_POSITION_RIGHT );

		return legend;

	}

	public String toString() {

		return "차트 영역";

	}

	public Chart cloneChart(SpreadSheet sheet) {

		ChartElement cloneChart = clone( null, sheet );

		return (Chart) cloneChart;

	}


}