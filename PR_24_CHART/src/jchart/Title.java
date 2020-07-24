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
import java.awt.geom.*;
import jcosmos.Unit;

public class Title extends TextualChartElement implements UnResizable {


	public Title(String text, Font font, SpreadSheet sheet, ChartElement parent,
				int cenX, int cenY, ShapeStyle style, boolean verticalDrawing) {

		super(sheet, parent, null, style, font, text );

		orientation = (verticalDrawing ? 90 : 0);


	this.verticalDrawing = verticalDrawing;

		// Text Bound Rectangle Setting
	super.setTitleTextBounds( (Graphics2D) ( getChart().getSpreadSheet().getGraphics() ), cenX, cenY );
	// End of Setting Text Bound

  }

    public ChartElement clone(ChartElement parent, SpreadSheet sheet) {

	ShapeAndStyle sns = cloneShapeAndShapeStyle();

		Point center = this.getCenter();

	ChartElement clone = new Title( "" + text, font, sheet, parent, center.x, center.y, sns.getStyle(), verticalDrawing );

	cloneChilds( clone, sheet );

	return clone;

    }

	public void setText(String text) {

		super.setText( text );

		Point center = this.getCenter();

	setTitleTextBounds( (Graphics2D) ( getChart().getSpreadSheet().getGraphics() ), center.x, center.y );

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