package jchart;

/**
 * Title:        java chart project
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 * @description     ChartElement Сп
 *
 */

import java.awt.*;

public class TickLabel extends TextualChartElement implements MultipleChild, UnSelectable {

    public TickLabel(String text, Font font, SpreadSheet sheet, ChartElement parent, int cenX, int cenY) {

		super(sheet, parent, null, null, font, text );

		this.verticalDrawing = false;

		// Text Bound Rectangle Setting
	super.setTitleTextBounds( (Graphics2D) ( getChart().getSpreadSheet().getGraphics() ), cenX, cenY );
	// End of Setting Text Bound


	}

	public void paint(Graphics2D g2) {

	super.paint( g2 );

	Point center = getCenter();

		// set the rotatation angle

		setGraphicsOrientation( g2, center );

		// end of setting the rotation angle

	// Draw text

	Rectangle ta = getArea();   // text area,

	Font font = getDrawingFont( originalBounds ); // drawing font. It is different initial font

	super.drawString( this, text, g2, font, ta );

	// End of drawing text

	// restore the rotated angle

	reSetGraphicsOrientation( g2, center );

	// end of restoreation the rotated angle

    }


}