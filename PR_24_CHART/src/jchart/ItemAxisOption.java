package jchart;

/**
 * Title:           ItemAxisOption Ŭ����
 * Copyright:       Copyright (c) 2001
 * Company:         Techdigm corp.
 * @author          withhim
 * @version         1.0
 * Description:     ItemAxis Ŭ������ �ڷḦ ��� �ִ� Ŭ������ AxisOption�� ��ӹ޴´�.
 */

public class ItemAxisOption extends AxisOption{

	// ���� ���̺� ���̿� �� �׸� ��
	protected int tickLableSpacing;

	// ���� ���̿� �� �׸� ��
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
