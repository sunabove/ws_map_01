package jchart;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author 1.0
 * @version 1.0
 */

public class AppRegistry {

	// Axis ���� ����
	public static final byte CATEGORY_SCALE = 0;
	public static final byte TIME_SCALE = 1;
	public static final byte AUTOMATIC_SCALE = 2;

	public static final byte PRIMARY_AXIS_GROUP = 0;
	public static final byte SECONDARY_AXIS_GROUP = 1;

	public static final byte ITEM_AXIS_TYPE = 0;
	public static final byte VALUE_AXIS_TYPE =1;

	// Axis ũ�� ���� ���
	public static final float VAULE_AXIS_WIDTH_RATIO = 0.1F;
	public static final float ITEM_AXIS_HEIGHT_RATIO = 0.1F;

	// DataLabel���� ���
	public static final byte DATA_LABEL_CANNOT_SHOW = -1;           // �����ͷ��̺� ���� ǥ���� �� ����
	public static final byte DATA_LABEL_SHOW_NONE = 0;              // ����
	public static final byte DATA_LABEL_SHOW_VALUE = 1;             // �� ǥ��
	public static final byte DATA_LABEL_SHOW_PERCENT = 2;           // ����� ǥ��
	public static final byte DATA_LABEL_SHOW_LABEL = 3;             // ���̺� ǥ��
	public static final byte DATA_LABEL_SHOW_LABEL_AND_PERCENT = 4; // ���̺�� ����� ǥ��
	public static final byte DATA_LABEL_SHOW_BUBBLE_SIZES = 5;      // ��ǰ ũ�� ǥ��

	// Legend ���� ���
	public static final float LEGEND_MARGIN_RATIO = 0.025F;
	public static final float LEGEND_WIDTH_RATIO = 0.12F, LEGEND_HEIGHT_RATIO = 0.3F;

	public static final int LEGEND_POSITION_NONE = -1, LEGEND_POSITION_BOTTOM = 0,
						    LEGEND_POSITION_CORNER = 1, LEGEND_POSITION_TOP = 2,
							LEGEND_POSITION_LEFT = 3, LEGEND_POSITION_RIGHT = 4;

	// TickMark ���� ���
	public static final byte MAJOR_TYPE = 0;
	public static final byte MINOR_TYPE = 1;

	public static final byte TICK_MARK_NONE = 0;
	public static final byte TICK_MARK_OUTSIDE = 1;
	public static final byte TICK_MARK_INSIDE = 2;
	public static final byte TICK_MARK_CROSS = 3;

	// TickLabel ���� ���
	public static final byte TICK_LABEL_POSITION_NONE = 0;
	public static final byte TICK_LABEL_POSITION_HIGH = 1;
	public static final byte TICK_LABEL_POSITION_LOW = 2;
	public static final byte TICK_LABEL_POSITION_NEXT_TO_AXIS = 3;

	// Axis Ŭ������ crossesAt ���� ���
	public static final byte AXIS_CROSSES_AUTOMATIC = 0;    // ���� �������� �ڵ����� ���
	public static final byte MINIMUM = 1;                   // ���� �ּҰ����� ����
	public static final byte MAXIMUM = 2;                   // ���� �ִ밪���� ����
	public static final byte AXIS_CROSSES_CUSTOM = 3;       // crossesAt�� ���ؼ� ����

	// Axis Ŭ������ displayUnit ���� ���
	public static final byte NONE = 0;
	public static final byte HUNDREDS = 1;
	public static final byte THOUSANDS = 2;
	public static final byte TEN_THOUSANDS = 3;
	public static final byte HUNDRED_THOUSANDS = 4;
	public static final byte MILLIONS = 5;
	public static final byte TEN_MILLIONS = 6;
	public static final byte HUNDRED_MILLIONS = 7;
	public static final byte THOUSAND_MILLIONS = 8;
	public static final byte MILLION_MILLIONS = 9;

	// Axis Ŭ������ scaleType ���� ���
	public static final byte SCALE_LINEAR = 0;
	public static final byte SCALE_LOGARITHMIC = 1;

	// TickMarkGroup Ŭ������ tickMarkType ���� ���
	public static final byte MAJOR_TICK_MARK = 0;      // �� ����
	public static final byte MINOR_TICK_MARK = 1;      // ���� ����

	// GridLineGroup Ŭ������ gridLineType ���� ���
	public static final byte MAJOR_GRID_LINE_OF_ITEM_AXIS = 0;      // X�� �� ���ݼ�
	public static final byte MAJOR_GRID_LINE_OF_VALUE_AXIS = 1;     // Y�� �� ���ݼ�
	public static final byte MINOR_GRID_LINE_OF_ITEM_AXIS = 2;      // X�� ���� ���ݼ�
	public static final byte MINOR_GRID_LINE_OF_VALUE_AXIS = 3;     // Y�� ���� ���ݼ�




    public AppRegistry() {
    }
}