package jchartui;
/**
 * 	INTERFACE	: SICComponentValueListener
 *	CREATE DATE	: 2000/9/28
 *	AUTHOR		: Won-Gun Han
 *	DESCRIPT	: SIC가 만든 Component의 value에 관한 Listener
 */

public interface SICComponentValueListener extends java.util.EventListener{
	public void sicComponentValueChanged(SICComponentValueEvent event);
}
