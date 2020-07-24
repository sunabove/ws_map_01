package jchartui;
/******************************************************************************
 *	CLASS		: TransformDegreeChangeListener
 *	AUTHOR		: WildRain
 *	CREATE DATE	: 2000-08-12
 *  DESCRIPT	: BorderStylePanel용 이벤트 리스너
 ******************************************************************************/

public interface TransformDegreeChangeListener extends java.util.EventListener {

	// BorderStylePanel이 클릭되면 발생...
	public void transformDegreeChanged(TransformDegreeChangeEvent event);

}


