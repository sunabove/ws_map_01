package jchartui;

/******************************************************************************
 *	CLASS		: TransformDegreeChangeEvent
 *	AUTHOR		: WildRain
 *	CREATE DATE	: 2000-08-15
 *  DESCRIPT	: TransformPanel용 이벤트
 ******************************************************************************/

import java.awt.*;

public class TransformDegreeChangeEvent extends AWTEvent {
	private double degree;

	// 생성자
	public TransformDegreeChangeEvent(TransformPanel source, double degree) {
		super(source, AWTEvent.RESERVED_ID_MAX + 1);
		this.degree = degree;
	}

	// 현재 선택되어있는 값을 반환하는 메소드
	public double getDegree() {
		return this.degree * -1;
	}

}
