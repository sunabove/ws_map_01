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
import jcosmos.*;

public class ItemAxis extends Axis {

    public ItemAxis(SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style) {

	super(sheet, parent, shape, style);

	Font font = FontManager.getDefaultFont();

		TickLabelGroup tlg = new TickLabelGroup( font, sheet, this, shape, style );

		addChild( tlg );

    }

	public ChartElement cloneSelf(ChartElement parent, SpreadSheet sheet) {

		ShapeAndStyle shapeNstyle = cloneShapeAndShapeStyle();

	ChartElement element = new ItemAxis( sheet, parent, shapeNstyle.getShape(), shapeNstyle.getStyle() );

	return element;

    }


    public Rectangle [] getSelectedMarks() {

	Rectangle bounds = getArea();

	if( bounds == null ) {

			return new Rectangle[ 8 ];

	}

	int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;

		Rectangle [] marks = new Rectangle[8];

		int WI = 6, HE = 6;
		int mx = x + (w - WI)/2, my = y + (h - HE)/2;
		int ex = x + w - WI/2, ey = y + h - HE/2;
		x -= WI/2; y -= HE/2;

		marks[0] = new Rectangle(x, y, WI, HE );
		marks[2] = new Rectangle(ex, y, WI, HE );

	return marks;
    }

	// 항목 축에 들어갈 눈금 레이블의 위치와 text의 내용을 가져온다.
	public PointedObject [] getTickLabelPoints() {

	    return getChart().getChartType().getTickLabelPointsOfItemAxis(this);

	}

	// 항목 축에 들어갈 주 눈금과 주 눈금선 들의 위치를 가져온다.
    public Point [] getMajorTickMarkPoints() {

	return getChart().getChartType().getTickMarkPointsOfItemAxis( this, AppRegistry.MAJOR_GRID_LINE_OF_ITEM_AXIS );

    }

	// 항목 축에 들어갈 보조 눈금과 보조 눈금선 들의 위치를 가져온다.
	public Point [] getMinorTickMarkPoints() {

		return getChart().getChartType().getTickMarkPointsOfItemAxis( this, AppRegistry.MINOR_GRID_LINE_OF_ITEM_AXIS );

	}

    public void paint(Graphics2D g2) {

	Chart chart = getChart();

	DataTable dataTable = (DataTable) chart.getChartElement( DataTable.class );

	ChartType ct = getChartType();

	if( dataTable != null && ! ( ct instanceof HorizontalChartType ) ) {
	    return;
	}

	super.paint( g2 );

    }

    public String toString() {

	return "항목 축";

    }

}