package jchart;

/**
 * Title:        java chart project
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 * @description     ChartElement 중 Text입력이 가능한 ChartElement들의 Parent Class로
 *                  Text의 글꼴과 Text의 회전 정도(orientation)등에 관한 정보들이 들어있다.
 */

import java.awt.*;
import java.awt.geom.*;
import jcosmos.Unit;

public class TextualChartElement extends ChartElement implements ParentToolTip {


    protected String text;
	protected Font font;

	// Text가 화면에 display될때 기울어진 정도를 나타내는 값으로 단위는 degree를 나타낸다.
    protected int orientation;
	protected Rectangle originalBounds; // orignal bounds for checking font size
    protected boolean verticalDrawing; // vertical Drawing setting



    public TextualChartElement(SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style, Font font, String text) {

		super(sheet, parent, shape, style);

		orientation = (verticalDrawing ? 90 : 0);

	this.font = font;

		this.text = text;


    }

	/**
	 * Area의 center point를 구하는 함수이다.
	 */
	protected Point getCenter() {

	Rectangle area = getArea();

	return new Point( area.x + area.width/2, area.y + area.height/2 );

    }


	public String getText() {

	return text;

    }

    public void setText(String text) {

	this.text = text;

    }

  // rational drawing font

    public Font getDrawingFont(Rectangle originalBounds) {

	Font font = this.font;

	Rectangle currentBounds = getArea();

	double sx = currentBounds.width / ( originalBounds.width + 0.0);
	double sy = currentBounds.height/ ( originalBounds.height + 0.0);

	double scale = ( sx < sy ) ? sx : sy;

	AffineTransform at = AffineTransform.getScaleInstance( scale, scale );

	return font.deriveFont( at );

    }


    public ChartElement cloneSelf(ChartElement parent, SpreadSheet sheet) {

	ShapeAndStyle shapeNstyle = cloneShapeAndShapeStyle();

	ChartElement element = new TextualChartElement( sheet, parent, shapeNstyle.getShape(), shapeNstyle.getStyle(), font, text );

	return element;

    }

    public void setFont(Font font) {

	this.font = font;

    }

    public Font getFont() {

	return this.font;

    }

	public int getOrientation() {

		return this.orientation;

	}

	public void setOrientation(int degrees) {

		this.orientation = degrees;

	}

	/**
	 *
	 */
	public void setGraphicsOrientation(Graphics2D g2, Point center) {

		if( orientation == 0) {

			return;

		}

		double radianValue = Unit.convertDegreeToRadian( this.orientation + 0.0);

	g2.rotate( - radianValue, center.x, center.y );


	}

	protected void reSetGraphicsOrientation(Graphics2D g2, Point center) {

		if( orientation == 0) {

			return;

		}

		double radianValue = Unit.convertDegreeToRadian( this.orientation + 0.0);

	g2.rotate( radianValue, center.x, center.y );


	}

	public void paintSelf(Graphics2D g2 ) {

	    if( orientation == 0 ) {

			super.paintSelf( g2 );

			return;

	    }

		shapeStyle.paint( g2, getRotatedShape().getBounds2D() );

	}

	private Shape getRotatedShape() {

		Point center= getCenter();

		double radianValue = Unit.convertDegreeToRadian( this.orientation + 0.0);

		AffineTransform at = AffineTransform.getRotateInstance( radianValue, center.x, center.y );

		return at.createTransformedShape( shape );

	}

    public Rectangle [] getSelectedMarks() {

	    if( orientation == 0 ) {

			return super.getSelectedMarks();

		}

		Rectangle bounds = getRotatedShape().getBounds();

		return getSelectedMarksMiddleOnBoundary( bounds );

	}

	public void paintMoveMode(Graphics2D g2, int x, int y) {

		if( orientation == 0 ) {

			super.paintMoveMode(g2, x, y);

			return;

		}

		paintMoveMode(g2, x, y, getRotatedShape().getBounds());

	}



	public void setTitleTextBounds(Graphics2D g2, int cenX, int cenY) {

	g2.setFont( font );

		FontMetrics fm = g2.getFontMetrics();
	int tw = fm.stringWidth( text ) + 6;
	int th = fm.getHeight();

	originalBounds = new Rectangle( cenX - tw/2, cenY - th/2 , tw, th );

	this.shape = originalBounds;

	if( orientation != 0 ) {

			double radianValue = Unit.convertDegreeToRadian( this.orientation + 0.0);
			AffineTransform at = AffineTransform.getRotateInstance( - radianValue, cenX, cenY );
			this.shape = at.createTransformedShape( super.shape );

	}

    }

}