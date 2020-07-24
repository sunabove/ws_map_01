package com.jwordart.model.wordart;

import java.awt.Color;
import java.awt.image.BufferedImage;

import com.jwordart.model.fillEffect.GraphicEffect;
import com.jwordart.ui.comp.WordArtFillEffectEditor;
import com.jwordart.ui.resource.Resource_WordArt;
import com.jwordart.util.UnitUtil_WordArt;

public class WordArtStyleFactory {
	
	public static GraphicEffect getFillEffect( int fillEffectType ) {
		WordArtStyle wordArtStyle = getWordArtStyle( fillEffectType );
		return wordArtStyle.getFillEffect();
	}
	
	public static WordArtStyle getWordArtStyle(int wordArtType) {
		
		WordArtStyle wordArtStyle = new WordArtStyle();

		if (wordArtType > 25) {
			wordArtStyle.setVertical(true);
		}

		GraphicEffect fe;
		BufferedImage image;

		switch (wordArtType) {
		case 1:
			wordArtStyle.setFillColor(Color.white);
			break;
		case 6:
			wordArtStyle.setFillColor(Color.white);
			wordArtStyle.setFontItalic();
			wordArtStyle.setSimpleShade(Color.black);
			break;
		case 7:
			wordArtStyle.setFillColor(new Color(51, 102, 153));
			wordArtStyle.setLineColor(null);
			wordArtStyle.setSimpleShade(Color.gray);
			break;
		case 8:
			fe = new GraphicEffect();
			fe.setHalfTransparent(Color.gray);
			wordArtStyle.setFillEffect(fe);
			wordArtStyle.setLineColor(new Color(51, 51, 204));
			wordArtStyle.setSimpleShade(new Color(99, 61, 195));
			break;
		case 9:
			fe = new GraphicEffect();
			fe.setFirstGradientColor(Color.gray);
			fe.setSecondGradientColor(Color.white);
			fe.setType(GraphicEffect.HORIZONTAL);
			wordArtStyle.setFillEffect(fe);
			wordArtStyle.setLineColor(null);
			wordArtStyle.setSimpleShade(Color.black);
			break;
		case 10:
			wordArtStyle.setFillColor(new Color(0, 102, 204));
			wordArtStyle.setLineColor(new Color(153, 204, 255));
			wordArtStyle.setSimpleShade(new Color(128, 0, 0));
			break;
		case 11:
			fe = new GraphicEffect();
			fe.setFirstGradientColor(Color.yellow);
			fe.setSecondGradientColor(Color.orange);
			fe.setType(GraphicEffect.FROM_CORNER);
			fe.setDirection(GraphicEffect.DOWN);
			wordArtStyle.setFillEffect(fe);
			wordArtStyle.setLineColor(null);
			wordArtStyle.setSimpleShade(Color.lightGray);
			break;
		case 12:
			fe = new GraphicEffect();
			fe.setFirstGradientColor(new Color(102, 0, 204));
			fe.setSecondGradientColor(new Color(204, 0, 204));
			fe.setType(GraphicEffect.HORIZONTAL);
			wordArtStyle.setFillEffect(fe);
			wordArtStyle.setLineColor(new Color(204, 153, 255));
			wordArtStyle.setLineWidth(UnitUtil_WordArt.convertPointToPixel(0.75));
			wordArtStyle.setSimpleShade(new Color(204, 153, 255));
			break;
		case 13:
			fe = new GraphicEffect();
			image = Resource_WordArt.getBufferedImage("wordart", "texture_05.gif");
			if (image != null) {
				fe.setTexturePaint(image);
			}
			wordArtStyle.setFillEffect(fe);
			wordArtStyle.setLineColor(new Color(0, 128, 0));
			wordArtStyle.setLineWidth(UnitUtil_WordArt.convertPointToPixel(0.75));
			wordArtStyle.setSimpleShade(new Color(116, 167, 111));
			break;
		case 14:
			fe = new GraphicEffect();
			fe.setFirstGradientColor(Color.red);
			fe.setSecondGradientColor(Color.yellow);
			fe.setType(GraphicEffect.VERTICAL);
			wordArtStyle.setFillEffect(fe);
			wordArtStyle.setLineColor(new Color(234, 234, 234));
			wordArtStyle.setSimpleShade(Color.lightGray);
			break;
		case 15:
			fe = new GraphicEffect();
			fe.setFirstGradientColor(new Color(153, 153, 255));
			fe.setSecondGradientColor(new Color(0, 153, 153));
			fe.setType(GraphicEffect.HORIZONTAL);
			wordArtStyle.setFillEffect(fe);
			wordArtStyle.setLineColor(null);
			wordArtStyle.setSimpleShade(Color.lightGray);
			break;
		case 16:
			fe = new GraphicEffect();
			image = Resource_WordArt.getBufferedImage("wordart", "texture_18.gif");
			if (image != null) {
				fe.setTexturePaint(image);
			}
			wordArtStyle.setFillEffect(fe);
			wordArtStyle.setLineColor(null);
			wordArtStyle.setSimpleShade(4, 0, Color.black);
			break;
		case 17:
			fe = new GraphicEffect();
			fe.setFirstGradientColor(new Color(255, 255, 204));
			fe.setSecondGradientColor(new Color(255, 153, 153));
			fe.setType(GraphicEffect.HORIZONTAL);
			wordArtStyle.setFillEffect(fe);
			wordArtStyle.setLineColor(null);
			break;
		case 18:
			fe = new GraphicEffect();
			fe.setFirstGradientColor(new Color(82, 4, 2));
			fe.setSecondGradientColor(new Color(255, 204, 0));
			fe.setType(GraphicEffect.HORIZONTAL);
			wordArtStyle.setFillEffect(fe);
			wordArtStyle.setLineColor(Color.gray);
			break;
		case 19:
			wordArtStyle.setFillColor(new Color(51, 204, 255));
			wordArtStyle.setLineColor(new Color(0, 0, 153));
			wordArtStyle.setSimpleShade(4, -4, new Color(0, 0, 153));
			break;
		case 20:
			fe = new GraphicEffect();
			image = Resource_WordArt.getBufferedImage("wordart", "tile_21.gif");
			image = WordArtFillEffectEditor.getImageChangedColor(image,
					Color.yellow, Color.black);
			fe.setBfrImage(image);
			wordArtStyle.setFillEffect(fe);
			wordArtStyle.setSimpleShade(Color.darkGray);
			break;
		case 27:
			wordArtStyle.setFillColor(new Color(128, 0, 0));
			wordArtStyle.setLineColor(new Color(128, 0, 0));
			wordArtStyle.setSimpleShade(2, 2, Color.lightGray);
			break;
		case 28:
			fe = new GraphicEffect();
			image = Resource_WordArt.getBufferedImage("wordart", "texture_20.gif");
			if (image != null) {
				fe.setTexturePaint(image);
			}
			wordArtStyle.setFillEffect(fe);
			wordArtStyle.setLineColor(null);
			wordArtStyle.setSimpleShade(Color.lightGray);
			break;
		case 29:
			fe = new GraphicEffect();
			fe.setFirstGradientColor(Color.green);
			fe.setSecondGradientColor(Color.blue);
			fe.setDirection(GraphicEffect.VERTICAL);
			wordArtStyle.setFillEffect(fe);
			wordArtStyle.setLineColor(null);
			wordArtStyle.setSimpleShade(-4, 4, Color.blue);
			break;
		case 30:
			wordArtStyle.setFillColor(new Color(204, 0, 0));
			wordArtStyle.setLineColor(null);
			break;
		}
		
		return wordArtStyle;
	}

}
