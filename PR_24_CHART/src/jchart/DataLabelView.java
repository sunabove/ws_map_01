package jchart;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.awt.*;
import java.awt.geom.*;
import jcosmos.Unit;
import jcosmos.Utility;


public class DataLabelView extends TextualChartElement implements UnResizable {

	private DataLabel dataLabel;

	// 레이블 옆에 범례 표식 표시여부를 나타내는 변수
	private boolean showDataLabelSymbol = false;

	public DataLabelView(String text, Font font, SpreadSheet sheet, ChartElement parent, DataElement dataElement,
				int cenX, int cenY, ShapeStyle style, byte dataLabelType, boolean verticalDrawing, boolean showDataLabelSymbol) {

		super(sheet, parent, null, style, font, text );

		dataLabel = new DataLabel( dataElement, dataLabelType);

		orientation = (verticalDrawing ? 90 : 0);

		this.text = text;

	this.verticalDrawing = verticalDrawing;

		// Text Bound Rectangle Setting
	super.setTitleTextBounds( (Graphics2D) ( getChart().getSpreadSheet().getGraphics() ), cenX, cenY );
	// End of Setting Text Bound

		setShowDataLabelSymbol( showDataLabelSymbol );


  }

    public ChartElement clone(ChartElement parent, SpreadSheet sheet) {

	ShapeAndStyle sns = cloneShapeAndShapeStyle();

		Point center = this.getCenter();

	    ChartElement clone = new DataLabelView( "" + text, font, sheet, parent, dataLabel.getDataElement(), center.x, center.y, sns.getStyle(), dataLabel.getDataLabelType(), verticalDrawing, showDataLabelSymbol );

	cloneChilds( clone, sheet );

	return clone;

    }


	/**************************************************************************
	 * setter & getter
	 **************************************************************************/

	public DataLabelSymbol getDataLabelSymbol() {

		return (DataLabelSymbol) getChartElement( DataLabelSymbol.class );  // DataLabelSymbol

	}


	public void setText(String text) {

		super.setText( text );

		Point center = this.getCenter();

	setTitleTextBounds( (Graphics2D) ( getChart().getSpreadSheet().getGraphics() ), center.x, center.y );

    }



	public void setShowDataLabelSymbol( boolean showDataLabelSymbol ) {

		DataLabelSymbol dls = getDataLabelSymbol();

		this.showDataLabelSymbol = showDataLabelSymbol;

		if ( showDataLabelSymbol == false ) {

			this.removeChild( dls ) ;
			return;

		} else if ( this.showDataLabelSymbol == false ) {

			Rectangle symbolRect = new Rectangle( 0, 0, 0, 0 );
			ShapeStyle dlsStyle = dataLabel.getDataElement().getDataSeries()
								    .getDataSeriesView().getShapeStyle();

			dls = new DataLabelSymbol(sheet, this, symbolRect, dlsStyle );

			this.addChild( dls );

			setFormValues();

		}



	}

	public boolean getShowDataLabelSymbol() {

		return this.showDataLabelSymbol;

	}

	public DataLabel getDataLabel() {

		return this.dataLabel;

	}

	private void setFormValues() {

	Rectangle area = getArea();

	originalBounds = area;

	int x = (int) area.getX(), y = (int) area.getY(),
	    w = (int) area.getWidth(), h = (int) area.getHeight();

	    y = y + h/4;
	    w = w/5;
	    h = h/2;

	Utility.debug(this, "x = " + x + ", y = " + y);
	Utility.debug(this, "w = " + w + ", h = " + h);

	ChartElement dls = getDataLabelSymbol();

	Rectangle shape = (Rectangle) dls.getShape();

	shape.setBounds( x, y, w, h);

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