package jchartui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;


import jchart.ShapeStyle;
import jchart.FillEffect;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class PreviewPanelInPatternTab extends JPanel {

	private Color backColor = Color.white;
	private Color borderColor = Color.black;
	private float lineWidth = 1;
	private float[] dashArray = null;
	private boolean enableShadow = false;
	private boolean enableborder = true;

	FillEffect fillEffect=null;

	private byte mode;

	public PreviewPanelInPatternTab(byte mode) {

		this.mode = mode;

	}

	public void setBackColor(Color color) {

		this.backColor = color;

	}
	public Color getBackColor() {

		return this.backColor;

	}

	public void setBorderColor(Color color) {

		this.borderColor = color;

	}
	public Color getBorderColor() {

		return this.borderColor;

	}

	public void setLineWidth(float width) {

		this.lineWidth = width;

	}
	public float getLineWidth() {

		return this.lineWidth;

	}

	public void setDashStyle(float[] style) {

		this.dashArray = style;

	}
	public float[] getDashStyle() {

		return this.dashArray;

	}

	public void setShadow(boolean isEnable) {

		this.enableShadow = isEnable;

	}

	public boolean getShadow() {

		return this.enableborder;

	}

	public void setEnableBorder(boolean isEnable) {

		this.enableborder = isEnable;

	}

	public void setFillEffect(FillEffect fillEffect) {

		this.fillEffect = fillEffect;

	}
	public FillEffect getFillEffect() {
		return this.fillEffect;
	}


	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int x;

		BasicStroke stroke;
		//dashArray가 널인지 확인... null인면 실선.. null이 아니면 점선...
		if ( dashArray == null )    stroke = new BasicStroke(this.lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
		else stroke = new BasicStroke(this.lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, this.dashArray, 0);

		if (this.mode == PatternTab.AREA_MODE ) drawAreaMode(g2d, stroke);
		else                                    drawLineMode(g2d, stroke);

	}


	private void drawAreaMode(Graphics2D g2d, Stroke stroke) {
		int x = (int)(this.lineWidth/2);
		//그림자가 있는지 없는지 확인해서 그림자를 그린다..
		if (enableShadow) {
			//배경색으로 먼저 색칠을 한다... 왜냐면.. 그림자 때문에 오른쪽 위 모퉁이 문제..
			g2d.setColor(this.getBackground());
			g2d.fillRect(0,0, this.getWidth(), this.getHeight());
			//그림자...
			g2d.setColor(Color.black);
			g2d.fillRect(4,4, this.getWidth()-4, this.getHeight()-4);

			if (this.fillEffect == null) {
				//실제 그리기...
//					System.out.println("Paint..... 채우기 효과 없음...");
				g2d.setColor(this.backColor);
				g2d.fillRect(0,0, this.getWidth()-4, this.getHeight()-4);
			}else {
//					System.out.println("Paint..... 채우기 효과 적용.....");

				this.drawFillEffect(g2d, this.getWidth()-4, this.getHeight()-4, this.fillEffect);

				//this.fillEffect = fillEffect;
			}

			if (this.enableborder) {
				//Border그리기
				g2d.setColor(this.borderColor);
				g2d.setStroke(stroke);
				g2d.drawRect(x,x, (int)(this.getWidth()-4-this.lineWidth), (int)(this.getHeight()-4-this.lineWidth));
			}
		}
		else {
			//실제 그리기...
			if (this.fillEffect == null) {
				//실제 그리기...
//					System.out.println("Paint..... 채우기 효과 없음...");
				g2d.setColor(this.backColor);
				g2d.fillRect(0,0, this.getWidth(), this.getHeight());
			}else {
//					System.out.println("Paint..... 채우기 효과 적용.....");

				this.drawFillEffect(g2d, this.getWidth(), this.getHeight(), this.fillEffect);

				//this.fillEffect = fillEffect;
			}
			if (this.enableborder) {
				//Border그리기
				g2d.setColor(this.borderColor);
				g2d.setStroke(stroke);
				g2d.drawRect(x,x, (int)(this.getWidth()-this.lineWidth), (int)(this.getHeight()-this.lineWidth));
			}
		}
	}

	private void drawLineMode(Graphics2D g2d, Stroke stroke) {
		int middle = (int)(this.getHeight()/2);

		//배경색으로 먼저 색칠을 한다...
		g2d.setColor(this.getBackground());
		g2d.fillRect(0,0, this.getWidth(), this.getHeight());

		g2d.setColor(this.borderColor);
		g2d.setStroke(stroke);
		g2d.drawLine(5, middle, this.getWidth()-5, middle);

	}

	private void drawFillEffect(Graphics2D g2d, double w, double h, FillEffect fillEffect) {
//		    double  w = this.getWidth(),
//			h = this.getHeight();

		fillEffect.setBounds(w, h);

		Paint gp = fillEffect.getGradientPaint();
		TexturePaint tp = fillEffect.getTexturePaint();

		Rectangle2D rect = new Rectangle2D.Double( 1, 1, w -2, h -2 );
		if( gp != null ) {
//				System.out.println("Gradient 로 그리기.....");
			g2d.setPaint( gp );
			g2d.fill( rect );
		} else if( tp != null ) {
//				System.out.println("Texture 로 그리기.....");
			g2d.setPaint( tp );
			g2d.fill( rect );
		} else {

		}
	}

	public ShapeStyle getShapeStyle(ShapeStyle refShapeStyle) {

		Color color;
		FillEffect fillEffect = this.getFillEffect();

		ShapeStyle shapeStyle = refShapeStyle.create();

		if (shapeStyle == null ) shapeStyle = new ShapeStyle();

		if ( this.mode == PatternTab.AREA_MODE) {

			color = this.getBackColor();

			if (color == null) {

				if (fillEffect==null) shapeStyle.setFillEffect(fillEffect);

				else shapeStyle.setFillEffect(fillEffect.create());

			}else {

				shapeStyle.setFillColor(color);

			}

		}

		//테두리 정보를 shapeStyle에 적용 시킨다..
		//채우기 색상정보는 계속해서 shapeStyle에 저장되어 있기 때문에 테두리 정보만 수집해서 저장한다.
		color = this.getBorderColor();
		float lineWidth = this.getLineWidth();

		shapeStyle.setLineColor(color);
		shapeStyle.setLineWidth((double)lineWidth);

		//Stroke정보...
		BasicStroke stroke;
		float[] dash = this.getDashStyle();

		if ( dash == null )    stroke = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
		else stroke = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dash, 0);

		shapeStyle.setLineStroke(stroke);

	    return shapeStyle;
	}
}