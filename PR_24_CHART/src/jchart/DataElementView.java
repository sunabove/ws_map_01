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
	 * DataLabelView�� ��ȯ�Ѵ�.
	 */
	 public DataLabelView getDataLabelView(){

		return ( DataLabelView ) this.getChartElement(DataLabelView.class);

	 }

	/**
	 * dataElement�� ��ȯ�Ѵ�.
	 */
	public DataElement getDataElement() {

		return dataElement;

	}

	/**
	 * dataElement�� toString()�� ��ȯ�Ѵ�.
	 */

	public String toString() {

		return (toolTip == null ) ? dataElement.toString() : toolTip ;

	}

	public void setToolTip(String toolTip ) {

		this.toolTip = toolTip;

	}

	/**
	 * DataElementView�� �ش��ϴ� DataLabelView�� dataLabelType�� �����ϴ� �Լ��̴�.
	 */
	public void applyDataLabelView( byte dataLabelType, boolean showDataLabelSymbol) {


		DataLabelView dataLabelView = this.getDataLabelView();


		// Show None�̸� DataLabelView�� ���� �Ѵ�.
		if ( dataLabelType == AppRegistry.DATA_LABEL_SHOW_NONE ) {

			this.removeChild( dataLabelView );
			return;
		}

		// ������ DataLabelView�� ���� �Ͽ��� ��
		if ( dataLabelView != null ) {

			DataLabel dataLabel = dataLabelView.getDataLabel();

			String text = DataLabel.getTextOfDataLabelViewAt( dataElement, dataLabelType );

			dataLabelView.setText(text);
			dataLabel.setDataLabelType( dataLabelType );
			dataLabelView.setShowDataLabelSymbol( showDataLabelSymbol );

			return;

		// ������ DataLabelView�� ���� ���� �ʾ��� ��
		}else {

			String text = DataLabel.getTextOfDataLabelViewAt( dataElement, dataLabelType );

			Utility.debug(this, text);

			// ���� ������� DataLabelView�� ��Ʈ�� ���Ѵ�.
			Font font = FontManager.getDefaultFont();

			// ���� ������� DataLabelView�� ��ġ�� ���Ѵ�.
		    Rectangle area = getArea();
			int cenX = area.x + area.width/2;
			int cenY = area.y + area.height/2;

			dataLabelView = new DataLabelView(text, font, this.sheet, this, dataElement, cenX, cenY
								    , null, dataLabelType, false, showDataLabelSymbol);

		    this.addChild(dataLabelView);

		}

	}

	/**
	 * ���� ��� �ִ� Datalabel�� Text�� ������ ��Ÿ����.
	 * ��� �ִ� DataLabel�� ������ DATA_LABEL_SHOW_NONE�� �����Ѵ�.
	 */
	public byte getDataLabelType() {

		DataLabelView dlv = this.getDataLabelView();

		if ( dlv == null ) {

			return AppRegistry.DATA_LABEL_SHOW_NONE;

		}

		return dlv.getDataLabel().getDataLabelType();

	}



}