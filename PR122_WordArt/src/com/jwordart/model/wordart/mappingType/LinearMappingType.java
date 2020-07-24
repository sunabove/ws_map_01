
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

import com.jwordart.model.wordart.WordArt;


/**
 * ���� ��Ʈ�� �۸��� ���������� �����ϴ� Ŭ������ �ݵ��
 * �� Ŭ������ ���(implements) �Ͽ����Ѵ�.
 */
public interface LinearMappingType {
   /**
   * ���� ���� Ÿ���� ���� ��ü�� ���Ѵ�.
   * @param wa ���� ��ü�� ���� ���� ��Ʈ
   */
  public abstract AffineTransform getTransformInstance(WordArt wa);
}