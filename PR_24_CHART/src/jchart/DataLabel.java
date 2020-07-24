package jchart;

/**
 * Title:           DataLabel Ŭ����
 * Copyright:       Copyright (c) 2001
 * Company:         techdigm corp.
 * @author          withthim
 * @version 1.0
 * Description:     ������ ���̺��� Text�� ��� �ִ� �ڷ��� Ŭ������ dataLableType��
 *                  ���� Text�� DataElement�� ���� �� �ϳ��� �����ϰ� �ȴ�.
 */

public class DataLabel {

	private DataElement dataElement;

	// DataLabel�� Text�� ������ ��Ÿ���� ����
	private byte dataLabelType;





    public DataLabel( DataElement dataElement, byte dataLabelType ) {

		this.dataElement = dataElement;
		this.dataLabelType = dataLabelType;

    }

	public void setDataLabelType(byte dataLabelType) {

		this.dataLabelType = dataLabelType;

	}

	public byte getDataLabelType() {

		return this.dataLabelType;

	}

	public DataElement getDataElement() {

		return this.dataElement;

	}


	public static String getTextOfDataLabelViewAt( DataElement dataElement, byte dataLabelType ) {

		String text = null;

		switch(dataLabelType) {
		case AppRegistry.DATA_LABEL_SHOW_NONE :
			text = null;
			break;
		case AppRegistry.DATA_LABEL_SHOW_VALUE  :
//			text = ( dataElement.getValue() ).toString();
			text = dataElement.getValue() + "";
			break;
		case AppRegistry.DATA_LABEL_SHOW_PERCENT :
			text = dataElement.getPercentedValue() + "";
			break;
		case AppRegistry.DATA_LABEL_SHOW_LABEL  :
			text = dataElement.getReferenceName();
			break;
		case AppRegistry.DATA_LABEL_SHOW_LABEL_AND_PERCENT :
			text = dataElement.getReferenceName()  ;
			break;
//		case DataLabelView.DATA_LABEL_SHOW_BUBBLE_SIZES :
//			text = ;
//			break;

		}

		return text;

	}




}