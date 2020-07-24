
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart.mappingType;

import java.awt.geom.*;
import java.awt.*;

import com.jwordart.model.wordart.WordArt;


/**
 * 워드 아트의 글립을 회전만 하는 매핑 타입은
 * 반드시 본 클래스를 상속(implements) 하여야 한다.
 */
public interface GlyphRotationMappingType {
   /**
   * 선형 매핑 타입의 변형 객체를 구한다.
   * @param wa 변형 객체를 구할 워드 아트
   *        glyph 로테이션할 글립
   */
   public AffineTransform getTransformInstance(WordArt wa, Shape glyph);
}