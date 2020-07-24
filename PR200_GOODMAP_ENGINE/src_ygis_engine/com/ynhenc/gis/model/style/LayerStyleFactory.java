package com.ynhenc.gis.model.style;

import java.awt.Color;
import java.awt.Font;

import com.jwordart.model.fillEffect.GraphicEffect;
import com.jwordart.util.UnitUtil_WordArt;
import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.util.ColorManager;
import com.ynhenc.comm.util.ColorUtil;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.model.shape.LayerType;

public class LayerStyleFactory extends GisComLib {

	public static void initLayerStyle(Layer layer, int index) {
		Style_05_General styleShape = LayerStyleFactory.getShapeStyle(layer, index);
		layer.setShapeStyle(styleShape);

		Style_01_Text styleText = LayerStyleFactory.getTextStyle(layer, index);
		layer.setTextStyle(styleText);
	}

	private static Style_05_General getShapeStyle(Layer layer, int index) {
		debug.println(LayerStyleFactory.class, "INDEX = " + index);

		Color colorLine;
		Color colorFill;

		if (true) {
			colorLine = Color.black;
			colorFill = Color.white;
		} else {
			colorLine = ColorManager.getColor(index);
			colorFill = ColorManager.getComplemetaryColor(colorLine);
		}

		if (LayerType.AREA == layer.getLyrType()) {
			// Area type
			Style_05_General styleGeneral = new Style_05_General();

			if (true) {
				styleGeneral.setLineColor(colorLine);
				styleGeneral.setFillColor(colorFill);
			} else {
				GraphicEffect graphicEffect_Fill = new GraphicEffect();

				graphicEffect_Fill.setFirstGradientColor(Color.gray);
				graphicEffect_Fill.setSecondGradientColor(Color.white);
				graphicEffect_Fill.setType(GraphicEffect.HORIZONTAL);
				graphicEffect_Fill.setBounds(100, 100);

				styleGeneral.setGraphicEffect_Fill(graphicEffect_Fill);
			}

			return styleGeneral;

		} else if (LayerType.LINE == layer.getLyrType()) {
			// 선 타입

			Style_05_General styleGeneral = new Style_05_General();

			if (true) {
				styleGeneral.setLineColor(colorLine);
				styleGeneral.setFillColor(null);
				styleGeneral.setLineWidth(1);
			}

			return styleGeneral;

		} else if (LayerType.POINT == layer.getLyrType()) {
			// 점 타입

			Style_05_General styleGeneral = new Style_05_General();

			if (true) {
				styleGeneral.setLineColor(colorLine);
				styleGeneral.setFillColor(colorFill);
			}

			return styleGeneral;

		} else {
			return null;
		}

	}

	private static Style_01_Text getTextStyle(Layer layer, int index) {

		String fontNameDef = "돋움";
		// String fontNameDef = "San Serif";
		// String fontNameDef = "Serif";

		Style_01_Text styleText = Style_01_Text.getStyle(fontNameDef, Font.PLAIN, Color.black, 11);

		return styleText;
	}

}
