package com.ynhenc.gis.model.style;

import java.awt.*;

import com.jwordart.model.fillEffect.*;
import com.ynhenc.gis.model.shape.*;

public class Style_05_General extends Style_00_Object {

	public SymbolArrow getSymbolArrow() {
		if (this.symbolArrow == null) {
			this.symbolArrow = new SymbolArrow();
		}
		return this.symbolArrow;
	}

	public void setSymbolArrow(SymbolArrow symbolArrow) {
		this.symbolArrow = symbolArrow;
	}

	private ShapeEffect getShapeEffect() {
		return this.shapeEffect;
	}

	public void setShapeEffect(ShapeEffect shapeEffect) {
		this.shapeEffect = shapeEffect;
	}

	@Override
	public void paintShape_01_InsideFill(Graphics2D g2, ShapeObject shapeObj, Projection projection, ShapeHash shapeHash) {

		g2.fill(shapeHash.getShape(shapeObj, projection));

		Shape[] shapeAccessoryList = this.getShapeAccessoryList(shapeObj, projection);

		if (shapeAccessoryList != null) {

			for (Shape shapeAccessory : shapeAccessoryList) {
				if (shapeAccessory != null) {
					g2.fill(shapeAccessory);
				}
			}

		}

	}

	@Override
	public void paintShape_02_OutsideDraw(Graphics2D g2, ShapeObject shape, Projection projection,
			ShapeHash shapeHash) {

		g2.draw(shapeHash.getShape(shape, projection));

		Shape[] shapeAccessoryList = this.getShapeAccessoryList(shape, projection);

		if (shapeAccessoryList != null) {

			for (Shape shapeAccessory : shapeAccessoryList) {
				if (shapeAccessory != null && shapeAccessory.intersects(projection.getPixelRect())) {
					g2.draw(shapeAccessory);
				}
			}

		}

	}

	private Shape[] getShapeAccessoryList(ShapeObject shapeObj, Projection projection) {

		if (shapeObj instanceof ShapeLine) {
			// TODO 2008/06/24/화 라인 끝 점 그리기.

			ShapeLine shapeLine = (ShapeLine) shapeObj;

			SymbolArrow symbolArrow = this.getSymbolArrow();

			if (symbolArrow != null) {

				return shapeLine.getShapeArrowList(symbolArrow, projection);

			} else {
				return null;
			}

		} else {

			return null;

		}

	}

	@Override
	public boolean setGraphics_01_InsideFill(Graphics2D g2, Projection projection) {
		ShapeEffect shapeEffect = this.shapeEffect;

		Color fillColor = shapeEffect.getFillColor();

		if (fillColor != null) {
			g2.setColor(fillColor);
			return true;
		} else {
			GraphicEffect graphicEffect_Fill = shapeEffect.getGraphicEffect_Fill();

			if (graphicEffect_Fill != null) {

				graphicEffect_Fill.setBounds(projection.getPixelWidth(), projection.getPixelHeight());

				Paint gradientPaint = graphicEffect_Fill.getGradientPaint();

				if (gradientPaint != null) {
					g2.setPaint(gradientPaint);
					return true;
				} else {
					Paint texturePaint = graphicEffect_Fill.getTexturePaint();
					if (texturePaint != null) {
						g2.setPaint(texturePaint);
						return true;
					} else {
						return false; // 페인팅 할 것이 없음.
					}
				}
			} else {
				return false;
			}
		}
	}

	@Override
	public boolean setGraphics_02_OutsideDraw(Graphics2D g2, Projection projection) {
		// TODO 201 라인 효과로 도형 그리기.
		ShapeEffect shapeEffect = this.shapeEffect;

		Color lineColor = shapeEffect.getLineColor();
		BasicStroke lineStroke = shapeEffect.getLineStroke();

		if (lineColor != null) {
			g2.setColor(lineColor);
			if (lineStroke != null) {
				g2.setStroke(lineStroke);
			}
			return true;
		} else {
			if (lineStroke != null) {
				g2.setStroke(lineStroke);

				GraphicEffect graphicEffect_Line = shapeEffect.getGraphicEffect_Line();

				if (graphicEffect_Line != null) {
					Paint texturePaint = graphicEffect_Line.getTexturePaint();
					if (texturePaint != null) {
						g2.setPaint(texturePaint);
					}
				}

				return true;
			} else {
				GraphicEffect graphicEffect_Line = shapeEffect.getGraphicEffect_Line();

				if (graphicEffect_Line != null) {
					Paint texturePaint = graphicEffect_Line.getTexturePaint();
					if (texturePaint != null) {
						g2.setPaint(texturePaint);
						return true;
					}
				} else {
					return false; // 드로잉 할 것이 없음.
				}
			}

		}

		return false;
	}

	public void setLineColor(Color colorLine) {
		this.shapeEffect.setLineColor(colorLine);
	}

	public Color getLineColor() {
		return this.shapeEffect.getLineColor();
	}

	public void setFillColor(Color colorFill) {
		this.shapeEffect.setFillColor(colorFill);
	}

	public Color getFillColor() {
		return this.shapeEffect.getFillColor();
	}

	public GraphicEffect getGraphicEffect_Fill() {
		return this.shapeEffect.getGraphicEffect_Fill();
	}

	public void setGraphicEffect_Fill(GraphicEffect graphicEffect_Fill) {
		this.shapeEffect.setGraphicEffect_Fill(graphicEffect_Fill);
	}

	public void setGraphicEffect_Line(GraphicEffect graphicEffect_Line) {
		this.shapeEffect.setGraphicEffect_Line(graphicEffect_Line);
	}

	public GraphicEffect getGraphicEffect_Line() {
		return this.shapeEffect.getGraphicEffect_Line();
	}

	public BasicStroke getLineStroke() {
		return this.shapeEffect.getLineStroke();
	}

	public BasicStroke getOrgLineStroke() {
		return this.shapeEffect.getOrgLineStroke();
	}

	public double getLineWidth() {
		return this.shapeEffect.getLineWidth();
	}

	public void setLineWidth(double lineWidth) {
		this.shapeEffect.setLineWidth(lineWidth);
	}

	public void setLineStroke(BasicStroke lineStroke) {
		this.shapeEffect.setLineStroke(lineStroke);
	}

	public Style_05_General create() {

		ShapeEffect cloneShapeEffect = this.getShapeEffect().create();

		Style_05_General clone = new Style_05_General(cloneShapeEffect);

		clone.setSymbolArrow(this.getSymbolArrow().create());

		return clone;
	}

	public Style_05_General() {
		this(new ShapeEffect());
	}

	public Style_05_General(ShapeEffect shapeEffect) {
		this.shapeEffect = shapeEffect;
		this.symbolArrow = new SymbolArrow();
	}

	private ShapeEffect shapeEffect;
	private SymbolArrow symbolArrow;

	public Style_05_General getStyle_50_General(int type) {
		Style_05_General styleGeneral = new Style_05_General();
		return styleGeneral;
	}

}
