
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
 * ���� ��Ʈ�� �۸��� ȸ���� �ϴ� ���� Ÿ����
 * �ݵ�� �� Ŭ������ ���(implements) �Ͽ��� �Ѵ�.
 */
public interface GlyphRotationMappingType {
   /**
   * ���� ���� Ÿ���� ���� ��ü�� ���Ѵ�.
   * @param wa ���� ��ü�� ���� ���� ��Ʈ
   *        glyph �����̼��� �۸�
   */
   public AffineTransform getTransformInstance(WordArt wa, Shape glyph);
}