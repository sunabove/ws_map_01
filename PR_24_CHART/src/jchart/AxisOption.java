package jchart;

/**
 * Title:           AxisOption Ŭ����
 * Copyright:       Copyright (c) 2001
 * Company:         Techdigm Cop.
 * @author          withhim
 * @version 1.0
 * Description:     �ڷ��� Ŭ������ Axis�� ���õ� option���� �����ϰ� �ִ� Ŭ������ Axis��
 *                  �� �����Ǵ���� AxisOption�� data�� ������ �������� ���ʿ��� ����
 *                  ����ص� �ʿ䰡 ���� �ȴ�.
 *                  �ึ�� AxisOption�� �����Ƿ� 2D������ ItemAxisOption, ValueAxisOption
 *                  Ŭ������ �����ϸ�, �Ѵ� AxisOption�� ��ӹ޴´�.
 */

import java.lang.Math;

public abstract class AxisOption {

	// ���ݼ� ���� ����
	protected boolean hasMajorGridLineGroup;
	protected boolean hasMinorGridLineGroup;


	// ���� ���� ����
	protected byte majorTickMark, minorTickMark;

	protected byte tickLabelPosition;

	protected double maxScale;              // �� ���� �ִ밪
	protected double minScale;              // �� ���� �ּҰ�
	protected boolean maxScaleIsAuto;       // �� ���� �ִ밪 �ڵ�
	protected boolean minScaleIsAuto;       // �� ���� �ּҰ� �ڵ�

	protected double majorUnit;                 // �� ����
	protected double minorUnit;                 // ���� ����
	protected boolean majorUnitIsAuto;          // �� ���� �ڵ�
	protected boolean minorUnitIsAuto;          // ���� ���� �ڵ�

	protected byte crosses;                                 // �� ����

	protected double crossesAt;                 // ����

	protected byte displayUnit;                 // ǥ�� ����

	protected boolean hasDisplayUnitLabel;      // ���� ���̺��� ǥ�� ���θ� ��Ÿ��

	protected byte scaleType;                   // ���� ����

	protected boolean reversePlotOrder;         // �� ����

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