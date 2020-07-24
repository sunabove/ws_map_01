package jchart;

/**
 * Title:           AxisOption 클래스
 * Copyright:       Copyright (c) 2001
 * Company:         Techdigm Cop.
 * @author          withhim
 * @version 1.0
 * Description:     자료형 클래스로 Axis와 관련된 option값을 저장하고 있는 클래스로 Axis가
 *                  재 생성되더라두 AxisOption이 data를 가지고 있음으로 불필요한 값을
 *                  백업해둘 필요가 없게 된다.
 *                  축마다 AxisOption을 가지므로 2D에서는 ItemAxisOption, ValueAxisOption
 *                  클래스가 존재하며, 둘다 AxisOption을 상속받는다.
 */

import java.lang.Math;

public abstract class AxisOption {

	// 눈금선 관련 변수
	protected boolean hasMajorGridLineGroup;
	protected boolean hasMinorGridLineGroup;


	// 눈금 관련 변수
	protected byte majorTickMark, minorTickMark;

	protected byte tickLabelPosition;

	protected double maxScale;              // 축 눈금 최대값
	protected double minScale;              // 축 눈금 최소값
	protected boolean maxScaleIsAuto;       // 축 눈금 최대값 자동
	protected boolean minScaleIsAuto;       // 축 눈금 최소값 자동

	protected double majorUnit;                 // 주 단위
	protected double minorUnit;                 // 보조 단위
	protected boolean majorUnitIsAuto;          // 주 단위 자동
	protected boolean minorUnitIsAuto;          // 보조 단위 자동

	protected byte crosses;                                 // 축 교차

	protected double crossesAt;                 // 교점

	protected byte displayUnit;                 // 표시 형식

	protected boolean hasDisplayUnitLabel;      // 단위 레이블의 표시 여부를 나타냄

	protected byte scaleType;                   // 단위 형식

	protected boolean reversePlotOrder;         // 축 반전

    public AxisOption() {

		this.majorTickMark = AppRegistry.TICK_MARK_INSIDE;
		this.minorTickMark = AppRegistry.TICK_MARK_NONE;
		this.tickLabelPosition = AppRegistry.TICK_LABEL_POSITION_NEXT_TO_AXIS;

		this.maxScaleIsAuto = true;
		this.minScaleIsAuto = true;
		this.majorUnitIsAuto = true;
		this.minorUnitIsAuto = true;
		this.crosses = AppRegistry.AXIS_CROSSES_AUTOMATIC;

		this.displayUnit = AppRegistry.NONE;
		this.hasDisplayUnitLabel = true;

		this.scaleType = AppRegistry.SCALE_LINEAR;
		this.reversePlotOrder = false;


    }


	//*************************************************************************
	// setter & getter
	// ************************************************************************

	public boolean getHasMajorGridLineGroup() {
		return this.hasMajorGridLineGroup;
	}

	public boolean getHasMinorGridLineGroup() {
	    return this.hasMinorGridLineGroup;
	}

	public void setHasMajorGridLineGroup( boolean hasMajorGridLineGroup ) {
	    this.hasMajorGridLineGroup = hasMajorGridLineGroup;
	}

	public void setHasMinorGridLineGroup( boolean hasMinorGridLineGroup ) {
	    this.hasMinorGridLineGroup = hasMinorGridLineGroup;
	}

	public byte getTickMark(byte tickMarkType) {

		if( tickMarkType == AppRegistry.MAJOR_TYPE ) {

			return majorTickMark;

		}else {     // tickMarkType == this.MINOR_TYPE

			return minorTickMark;

		}

	}

	public void setTickMark(byte tickMarkType, byte tickMark) {

		if( tickMarkType == AppRegistry.MAJOR_TYPE ) {

		    this.majorTickMark = tickMark;

		}else {     // tickMarkType == this.MINOR_TYPE

			this.minorTickMark = tickMark;

		}

	}

	public byte getTickLabelPosition() {

		return this.tickLabelPosition;

	}

	public void setTickLabelPosition( byte tickLabelPosition ) {

		this.tickLabelPosition = tickLabelPosition;

	}

	public double getMaxScale() {

		return this.maxScale;

	}

	public double getMinScale() {

		return this.minScale;

	}

	public boolean getMaxScaleIsAuto() {

		return this.maxScaleIsAuto;
	}

	public boolean getMinScaleIsAuto() {

	    return this.minScaleIsAuto;

	}

	public void setMaxScale( double maxScale ) {

	    this.maxScale = maxScale;

	}

	public void setMinScale( double minScale ) {

		this.minScale = minScale;

	}

	public void setMaxScaleIsAuto( boolean maxSacleIsAuto ) {

	    this.maxScaleIsAuto = maxSacleIsAuto;

	}

	public void setMinScaleIsAuto( boolean minScaleIsAuto ) {

	    this.minScaleIsAuto = minScaleIsAuto;

	}

	public double getMajorUnit() {

		return this.majorUnit;

	}

	public double getMinorUnit() {

		return this.minorUnit;

	}

	public boolean getMajorUnitIsAuto() {

		return this.majorUnitIsAuto;
	}

	public boolean getMinorUnitIsAuto() {

	    return this.minorUnitIsAuto;

	}

	public void setMajorUnit( double majorUnit ) {

		this.majorUnit = majorUnit;

	}

	public void setMinorUnit( double minorUnit ) {

		this.minorUnit = minorUnit;

	}

	public void setMajorUnitIsAuto( boolean majorUnitIsAuto ) {

	    this.majorUnitIsAuto = majorUnitIsAuto;

	}

	public void setMinorUnitIsAuto( boolean minorUnitIsAuto ) {

	    this.minorUnitIsAuto = minorUnitIsAuto;

	}

	public byte getCrosses() {

		return this.crosses;

	}

	public double getCroessesAt() {

	    return this.crossesAt;
	}

	public void setCrosses( byte crosses ) {

		this.crosses = crosses;

	}

	public void setCroessAt( double crossesAt ) {

	    this.crossesAt = crossesAt;

	}

	public byte getDisplayUnit() {

	    return this.displayUnit;
	}

	public void setDisplayUnit( byte displayUnit ) {

		this.displayUnit = displayUnit;

	}

	public boolean getHasDisplayUnitLabel() {

		return this.hasDisplayUnitLabel;

	}

	public void setHasDisplayUnitLabel( boolean hasDisplayUnitLabel ) {

	    this.hasDisplayUnitLabel = hasDisplayUnitLabel;

	}

	public byte getScaleType() {

		return this.scaleType;

	}

	public void setScaleType( byte scaleType ) {

		this.scaleType = scaleType;

	}

	public boolean getReversePlotOrder() {

		return this.reversePlotOrder;

	}

	public void setReversePlotOrder( boolean reversePlotOrder ) {

		this.reversePlotOrder = reversePlotOrder;

	}




}