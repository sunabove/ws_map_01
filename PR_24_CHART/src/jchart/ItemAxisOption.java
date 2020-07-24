package jchart;

/**
 * Title:           ItemAxisOption 클래스
 * Copyright:       Copyright (c) 2001
 * Company:         Techdigm corp.
 * @author          withhim
 * @version         1.0
 * Description:     ItemAxis 클래스의 자료를 담고 있는 클래스로 AxisOption을 상속받는다.
 */

public class ItemAxisOption extends AxisOption{

	// 눈금 레이블 사이에 들어갈 항목 수
	protected int tickLableSpacing;

	// 눈금 사이에 들어갈 항목 수
	protected int tickMarkSpacing;


    public ItemAxisOption() {
		super();

		this.hasMajorGridLineGroup = false;
		this.hasMinorGridLineGroup = false;
		this.tickLableSpacing = 1;
		this.tickMarkSpacing = 1;

    }

	/***************************************************************************
	 * Getter & Setter
	 **************************************************************************/

	public int getTickLabelSpacing() {

	    return this.tickLableSpacing;

	}

	public void setTickLabelSpacing( int tickLabelSpacing ) {

		this.tickLableSpacing = tickLabelSpacing;

	}

	public int getTickMarkSpacing() {

	    return this.tickMarkSpacing;

	}

	public void setTickMarkSpacing( int tickMarkSpacing ) {

		this.tickMarkSpacing = tickMarkSpacing;

	}

}
