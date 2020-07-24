package jchartui;

/**
 * 	CLASS		: SICComponentValueEvent
 *	CREATE DATE	: 2000/9/28
 *	AUTHOR		: Won-Gun Han
 *	DESCRIPT	: SIC�� ���� Component�� value�� ���� event
 */
import java.awt.AWTEvent;

public class SICComponentValueEvent extends AWTEvent{
	// SICComponent�� Value�� change�Ǿ��� ��Ȳ
	public static final int SICCOMPONENT_VALUE_CHANGED = 0;

	// value�� ����
	int state;

	public SICComponentValueEvent(Object source, int state){
		// RESERVED_ID_MAX�� event�� ����Ҽ� �ִ� �ִ� ID
		super(source, RESERVED_ID_MAX);
		this.state = state;
	}

	public int getState(){
		return state;
	}
}
