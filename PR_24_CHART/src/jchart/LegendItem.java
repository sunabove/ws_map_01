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
import jchart.DataSeries;

public class LegendItem extends TextualChartElement  implements MultipleChild, UnMovable {

    private DataSeries dataSeries;

	private Rectangle originalBounds; // orignal bounds for checking font size

	private boolean visible = true;

	public LegendItem(SpreadSheet sheet, ChartElement parent, DataSeries dataSeries, String legendString,   Shape shape, ShapeStyle style) {

	super(sheet, parent, shape, style, null, legendString );

	    setDataSeries(dataSeries);

	this.font = FontManager.getDefaultFont();

	super.setShapeStyle( null );

	Rectangle symbolRect = new Rectangle( 0, 0, 0, 0 );

	LegendItemSymbol lis = new LegendItemSymbol( sheet, this, symbolRect, style );

	addChild( lis );

	setFormValues();

    }

    public void setVisible(boolean b) {

      this.visible = b;

    }

	public DataSeries getDataSeries() {

		return dataSeries;

	}

	public void setDataSeries(DataSeries dataSeries) {

		this.dataSeries = dataSeries;
		dataSeries.setLegendItem(this);

	}

	public LegendItemSymbol getLegendItemSymbol() {

		return ( LegendItemSymbol ) this.getChartElement(LegendItemSymbol.class);

	}


    private void setFormValues() {

	Rectangle area = getArea();

	originalBounds = area;

	int x = (int) area.getX(), y = (int) area.getY(),
	    w = (int) area.getWidth(), h = (int) area.getHeight();

	    y = y + h/4;
	    w = w/5;
	    h = h/2;

	ChartElement lis = getChartElement( LegendItemSymbol.class );  // legend item symbol

	Rectangle shape = (Rectangle) lis.getShape();

	shape.setBounds( x, y, w, h);

    }

    public void paint(Graphics2D g2) {

       if( ! visible ) {

	return;

       }

	super.paint( g2 );

	Rectangle ta = getTextArea();  // text area

	Rectangle legendArea = getParent().getArea();

	Shape clipAreaOrg = g2.getClip();

	Font font = getDrawingFont( originalBounds );

	if( legendArea.intersects( ta ) && ( ! legendArea.contains( ta ) ) ) {

	  g2.setClip( legendArea.intersection( ta ) );

	}

	super.drawString(this, text, g2, font, ta );

	g2.setClip( clipAreaOrg );

    }

    private Rectangle getTextArea() {

	ChartElement lis = getChartElement( LegendItemSymbol.class );  // legend item symbol

	Rectangle lisArea = lis.getArea(); // legend item symbol area
	Rectangle area = getArea();

	int x = (int) lisArea.getX(),
	    y = (int) lisArea.getY(),
	    w = (int) (area.getWidth() - lisArea.getWidth()),
	    h = (int) lisArea.getHeight();

	    w = w - w/10;
	    x += lisArea.getWidth() + w/10;


	return new Rectangle( x, y, w, h );

    }

    public String toString() {

	return "\"" + text + "\" ¹ü·Ê Ç×¸ñ";

    }

}