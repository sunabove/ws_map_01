package jchartui;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class TextRotatePanel extends JPanel {

	private int orientation = 0;

    public TextRotatePanel(int degrees) {

		setOrientation(degrees);

    }

	public int getOrientation() {

		return orientation;

	}

	public void setOrientation(int degrees) {

		this.orientation = degrees;

	}


	public void paint(Graphics g) {

		super.paint( g );

		Graphics2D g2 = (Graphics2D) g;

		Dimension size = getSize();

		int w = size.width, h = size.height;

		g2.setColor( Color.white );

		g2.fillRect( 0, 0, w, h );

		g2.setColor( Color.black );

		Shape ellipse = new Ellipse2D.Double( - w, 0, 2*w - 1, h );

		g2.draw( ellipse );

		Shape line = new Line2D.Double( 0, h/2.0, w, h/2.0 );

		AffineTransform at = AffineTransform.getRotateInstance( orientation, 0, h/2.0  );

		line = at.createTransformedShape( line );

		g2.setColor( Color.red );

		g2.draw( line );

	}
}