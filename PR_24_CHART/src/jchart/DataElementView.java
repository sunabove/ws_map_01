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

import jcosmos.*;


public class DataElementView extends ChartElement implements UnResizable, MultipleChild, DataListener  {

	private ChartType chartType;

	private DataElement dataElement;

	private String toolTip;

	public DataElementView(SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style,
						   ChartType chartType, DataElement dataElement) {
		super(sheet, parent, shape, style );

		this.chartType = chartType;

		this.dataElement = dataElement;

	}

	public ChartElement cloneSelf(ChartElement parent, SpreadSheet sheet) {

		ShapeAndStyle sns = cloneShapeAndShapeStyle();

		ChartElement element = new DataElementView( sheet, parent, sns.getShape(), sns.getStyle(), chartType, dataElement );

		return element;

	}

	/**
	 * DataLabelView를 반환한다.
	 */
	 public DataLabelView getDataLabelView(){

		return ( DataLabelView ) this.getChartElement(DataLabelView.class);

	 }

	/**
	 * dataElement를 반환한다.
	 */
	public DataElement getDataElement() {

		return dataElement;

	}

	/**
	 * dataElement의 toString()을 반환한다.
	 */

	public String toString() {

		return (toolTip == null ) ? dataElement.toString() : toolTip ;

	}

	public void setToolTip(String toolTip ) {

		this.toolTip = toolTip;

	}

	/**
	 * DataElementView에 해당하는 DataLabelView에 dataLabelType을 적용하는 함수이다.
	 */
	public void applyDataLabelView( byte dataLabelType, boolean showDataLabelSymbol) {


		DataLabelView dataLabelView = this.getDataLabelView();


		// Show None이면 DataLabelView를 삭제 한다.
		if ( dataLabelType == AppRegistry.DATA_LABEL_SHOW_NONE ) {

			this.removeChild( dataLabelView );
			return;
		}

		// 이전에 DataLabelView가 존재 하였을 때
		if ( dataLabelView != null ) {

			DataLabel dataLabel = dataLabelView.getDataLabel();

			String text = DataLabel.getTextOfDataLabelViewAt( dataElement, dataLabelType );

			dataLabelView.setText(text);
			dataLabel.setDataLabelType( dataLabelType );
			dataLabelView.setShowDataLabelSymbol( showDataLabelSymbol );

			return;

		// 이전에 DataLabelView가 존재 하지 않았을 때
		}else {

			String text = DataLabel.getTextOfDataLabelViewAt( dataElement, dataLabelType );

			Utility.debug(this, text);

			// 새로 만들어질 DataLabelView의 폰트를 정한다.
			Font font = FontManager.getDefaultFont();

			// 새로 만들어질 DataLabelView의 위치를 정한다.
		    Rectangle area = getArea();
			int cenX = area.x + area.width/2;
			int cenY = area.y + area.height/2;

			dataLabelView = new DataLabelView(text, font, this.sheet, this, dataElement, cenX, cenY
								    , null, dataLabelType, false, showDataLabelSymbol);

		    this.addChild(dataLabelView);

		}

	}

	/**
	 * 현재 담고 있는 Datalabel의 Text의 종류를 나타낸다.
	 * 담고 있는 DataLabel이 없으면 DATA_LABEL_SHOW_NONE를 리턴한다.
	 */
	public byte getDataLabelType() {

		DataLabelView dlv = this.getDataLabelView();

		if ( dlv == null ) {

			return AppRegistry.DATA_LABEL_SHOW_NONE;

		}

		return dlv.getDataLabel().getDataLabelType();

	}



}