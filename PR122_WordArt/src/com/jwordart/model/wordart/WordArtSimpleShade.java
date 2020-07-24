
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart;

import java.awt.*;
import java.awt.geom.*;


/**
 * 가장 단순한 형태의 그림자 생성 클래스
 * 수직, 수평으로 일정 간격 이동한 그림자를 그린다.
 */
public class WordArtSimpleShade implements WordArtShade {
  private int transX = 1, transY = 1;
  private Color shadeColor = Color.black;

  public WordArtSimpleShade(int transX, int transY, Color shadeColor) {
     this.transX = transX;
     this.transY = transY;
     this.shadeColor = shadeColor;
  }

  public void drawShadeGlyphs(WordArt wa) {
      Shape [][] shadeGlyphs = wa.getShadeGlyphs();
      Graphics2D g2 = wa.getGraphics2D();
      // wa.setTransferGraphics2DToLocation();

      WordArtComponent wac = wa.getWordArtComponent();
      String [] strings = wac.getStrings();

      char c;

      g2.setColor( this.shadeColor );

      for(int j = 0; j < shadeGlyphs.length ; j ++ ) {
        for(int i = 0; i < shadeGlyphs[j].length ; i ++ ) {
          c = strings[j].charAt(i);
          if( c == ' ' || c == '\t' ) {
          } else {
             g2.fill( shadeGlyphs[j][i] );
          }
          // 글립 바운더리를 그린다.
          // g2.setColor(Color.green );
          // g2.draw( this.glyphsBounds[j][i] );
        }
      }
  }

  public void makeShadeGlyphs(WordArt wa) {
      Shape [][] glyphs = wa.getGlyphs();
      Shape [][] shadeGlyphs = new Shape[ glyphs.length ][];
      AffineTransform at = AffineTransform.getTranslateInstance( transX, transY );
      for(int i = 0; i < glyphs.length; i ++ ) {
         shadeGlyphs[i] = new Shape[ glyphs[i].length ];
         for(int j = 0; j < glyphs[i].length; j ++ ) {
             shadeGlyphs[i][j] = at.createTransformedShape( glyphs[i][j] );
         }
      }
      wa.setShadeGlyphs( shadeGlyphs );
  }
}