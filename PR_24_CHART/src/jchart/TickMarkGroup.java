package jchart;

/**
 * Title:           TickMarkGroup Ŭ����
 * Company:         Techdigm Corp.
 * @author          withhim
 * @version 1.0
 * @description     ChartElement �� ���ݿ� �ش��ϴ� Ŭ����
 *
 * @Spect           1. ������ ��(Axis)���� �����ϴ� �� ����, ���� ���ݿ� �ش��ϴ� ChartElement Ŭ������ Unselectable�ϴ�
 *                  2. Axis �� parent�� ������ �������� TickLabel Ŭ������ ������ �ִ�.
 *                  3. ���⿡ ���� TickLabel Ŭ������ �ϳ��ϳ��� ������ ��Ÿ���� �ϳ���
 *                     TickLabel�� ���ý� TickMarkGroup�� ���õȴ�.
 *                  4. tickMarkType�� ���� �ΰ��� TickMarkGroup�� �����Ѵ�.
 *                  5. ShapeSytle�� Parent�� Axis�� ShapeStyle�� �����´�.
 *
 */


import java.awt.*;

public class TickMarkGroup extends ChartElement implements ChildSelectable, UnSelectable {

	private byte tickMarkType;


    public TickMarkGroup(SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style, byte tickMarkType) {
        super(sheet, parent, shape, style );

	    this.tickMarkType = tickMarkType;
    }

    public ChartElement cloneSelf(ChartElement parent, SpreadSheet sheet) {

        ShapeAndStyle sns = cloneShapeAndShapeStyle();

        TickMarkGroup element = new TickMarkGroup( sheet, parent, sns.getShape(), sns.getStyle(), this.tickMarkType );

        return element;

    }

	public byte getTickMarkType() {

		return this.tickMarkType;

    }



}