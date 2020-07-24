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

public class LegendItemSymbol extends ItemSymbol {

    public LegendItemSymbol(SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style) {

	super(sheet, parent, shape, style );

    }

    public String toString() {
	return "\"" + ((LegendItem)parent).getText() + "\" 범례 표식";
    }


	/**
	 * ChartElement에서의 changeShapeStype과는 달리 LegendItemSymbol의 ShapeStyle은
	 * DataSeries와 동일한 ShapeStyle을 가지게 되므로 LegendItemSymbol의 다이얼로그
	 * 박스에 의해 ShapeStyle이 바뀌게 되면 DataSeries의 ShapeStyle도 바뀌게 된다.
	 */
	public void changeShapeStyle( ShapeStyle style) {

	    super.setShapeStyle(style);

		LegendItem legendItem = (LegendItem) this.getParent();

		DataSeriesView dsv = legendItem.getDataSeries().getDataSeriesView();

	dsv.changeShapeStyleEvenChilds( style );

	}

	public void paint(Graphics2D g2) {

	  Rectangle area = getArea();

	  Rectangle legendArea = getParent().getParent().getArea(); // 레전드아이템 -> 레전드

	  if( legendArea.intersects( area ) && ! ( legendArea.contains( area ) ) ) {

	    Shape clipAreaOrg = g2.getClip();

	    g2.setClip( legendArea.intersection( area ) );

	    super.paint( g2 );

	    g2.setClip( clipAreaOrg );

	  } else {

	    super.paint( g2 );

	  }

	}

}