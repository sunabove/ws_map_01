package jchart;

/**
 * Title:           TickMarkGroup 클래스
 * Company:         Techdigm Corp.
 * @author          withhim
 * @version 1.0
 * @description     ChartElement 중 눈금에 해당하는 클래스
 *
 * @Spect           1. 각각의 축(Axis)마다 존재하는 주 눈금, 보조 눈금에 해당하는 ChartElement 클래스로 Unselectable하다
 *                  2. Axis 를 parent로 가지며 여러개의 TickLabel 클래스를 가지고 있다.
 *                  3. 여기에 속한 TickLabel 클래스는 하나하나의 눈금을 나타내며 하나의
 *                     TickLabel을 선택시 TickMarkGroup이 선택된다.
 *                  4. tickMarkType에 따라 두개의 TickMarkGroup을 구별한다.
 *                  5. ShapeSytle은 Parent인 Axis의 ShapeStyle을 가져온다.
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