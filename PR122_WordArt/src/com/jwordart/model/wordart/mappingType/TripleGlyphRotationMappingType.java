
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
 * ����� SimpleTopArcMappingType, �ߴ��� Ȯ��,
 * �ϴ��� SimpleBottomArcMappingType���� �����ϴ�
 * ���� ��Ʈ�� �۸��� ȸ���� �ϴ� ���� Ÿ����
 * �ݵ�� �� Ŭ������ ���(implements) �Ͽ��� �Ѵ�.
 */

public interface TripleGlyphRotationMappingType {
   /**
   * ���� ���� Ÿ���� ���� ��ü�� ���Ѵ�.
   * @param wa ���� ��ü�� ���� ���� ��Ʈ
   *        glyph �����̼��� �۸�
   *        lineNum ���� �ѹ�
   */
   public AffineTransform getTransformInstance(WordArt wa, Shape glyph, int lineNum);

   /**
    * ���� ���� ���� ���� �������� �����Ѵ�.
    */
   public void setParamenters(WordArt wa);

   /**
    * ��� ������Ʈ ���� �ѹ��� �����Ѵ�.
    */
    public int getUpperLineNumber();

    /**
    * �ߴ� ������Ʈ ���� �ѹ��� �����Ѵ�.
    */
    public int getMiddleLineNumber();
}