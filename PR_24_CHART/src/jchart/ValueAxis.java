package jchart;

/**
 * Title:        java chart project
 * Description:  jchart v1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import jcosmos.FontManager;

public class ValueAxis extends Axis {

    public ValueAxis( SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style ) {

        super(sheet, parent, shape, style );


    }

	public ChartElement cloneSelf(ChartElement parent, SpreadSheet sheet) {

		ShapeAndStyle shapeNstyle = cloneShapeAndShapeStyle();

        ChartElement element = new ItemAxis( sheet, parent, shapeNstyle.getShape(), shapeNstyle.getStyle() );

        return element;

    }

    public Rectangle [] getSelectedMarks() {

        Rectangle bounds = getArea();

		int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;

        Rectangle [] marks = new Rectangle[8];

        int WI = 6, HE = 6;
        int mx = x + (w - WI)/2, my = y + (h - HE)/2;
        int ex = x + w - WI/2, ey = y + h - HE/2;
        x -= WI/2; y -= HE/2;

        marks[2] = new Rectangle(ex, y, WI, HE );
        marks[7] = new Rectangle(ex, ey, WI, HE );

        return marks;

    }

	// 값 축에 들어갈 눈금 레이블의 위치와 text의 내용을 가져온다.
	public PointedObject [] getTickLabelPoints() {

	    return getChart().getChartType().getTickLabelPointsOfValueAxis(this);

	}


	// 값 축에 들어갈 주 눈금과 주 눈금선 들의 위치를 가져온다.
    public Point [] getMajorTickMarkPoints() {

		return getChart().getChartType().getTickMarkPointsOfValueAxis(this, AppRegistry.MAJOR_GRID_LINE_OF_VALUE_AXIS);

    }

	// 값 축에 들어갈 보조 눈금과 보조 눈금선 들의 위치를 가져온다.
    public Point [] getMinorTickMarkPoints() {

		return getChart().getChartType().getTickMarkPointsOfValueAxis(this, AppRegistry.MINOR_GRID_LINE_OF_VALUE_AXIS );

    }


    public String toString() {
        return "값 축";
    }

}