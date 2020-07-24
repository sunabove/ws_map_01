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
	return "\"" + ((LegendItem)parent).getText() + "\" ���� ǥ��";
    }


	/**
	 * ChartElement������ changeShapeStype���� �޸� LegendItemSymbol�� ShapeStyle��
	 * DataSeries�� ������ ShapeStyle�� ������ �ǹǷ� LegendItemSymbol�� ���̾�α�
	 * �ڽ��� ���� ShapeStyle�� �ٲ�� �Ǹ� DataSeries�� ShapeStyle�� �ٲ�� �ȴ�.
	 */
	public void changeShapeStyle( ShapeStyle style) {

	    super.setShapeStyle(style);

		LegendItem legendItem = (LegendItem) this.getParent();

		DataSeriesView dsv = legendItem.getDataSeries().getDataSeriesView();

	dsv.changeShapeStyleEvenChilds( style );

	}

	public void paint(Graphics2D g2) {

	  Rectangle area = getArea();

	  Rectangle legendArea = getParent().getParent().getArea(); // ����������� -> ������

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