package jchartui;

/**
 * 	CLASS		: SICComponentValueEvent
 *	CREATE DATE	: 2000/9/28
 *	AUTHOR		: Won-Gun Han
 *	DESCRIPT	: SIC가 만든 Component의 value에 관한 event
 */
import java.awt.AWTEvent;

public class SICComponentValueEvent extends AWTEvent{
	// SICComponent의 Value가 change되었을 상황
	public static final int SICCOMPONENT_VALUE_CHANGED = 0;

	// value의 상태
	int state;

	public SICComponentValueEvent(Object source, int state){
		// RESERVED_ID_MAX는 event를 사용할수 있는 최대 ID
		super(source, RESERVED_ID_MAX);
		this.state = state;
	}

	public int getState(){
		return state;
	}
}
