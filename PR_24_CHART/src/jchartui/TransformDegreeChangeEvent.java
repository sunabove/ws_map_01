package jchartui;

/******************************************************************************
 *	CLASS		: TransformDegreeChangeEvent
 *	AUTHOR		: WildRain
 *	CREATE DATE	: 2000-08-15
 *  DESCRIPT	: TransformPanel�� �̺�Ʈ
 ******************************************************************************/

import java.awt.*;

public class TransformDegreeChangeEvent extends AWTEvent {
	private double degree;

	// ������
	public TransformDegreeChangeEvent(TransformPanel source, double degree) {
		super(source, AWTEvent.RESERVED_ID_MAX + 1);
		this.degree = degree;
	}

	// ���� ���õǾ��ִ� ���� ��ȯ�ϴ� �޼ҵ�
	public double getDegree() {
		return this.degree * -1;
	}

}
