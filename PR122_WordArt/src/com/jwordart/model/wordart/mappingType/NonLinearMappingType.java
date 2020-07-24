
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart.mappingType;

import com.jwordart.model.wordart.WordArt;


/**
 * ���� ��Ʈ�� �۸��� ���������� �����ϴ� Ŭ��������
 * ���� Ŭ�����̴�.
 */
public abstract class NonLinearMappingType extends WordArtMappingType {
    /**
     * ������
     * @param type ���� Ŭ���� ���̵��̴�.
     */
    public NonLinearMappingType(int type) {
      super(type);
    }

    /**
     * ���� ������ �ٽ��� �Ǵ� �Լ��̴�.
     * �Էµ� ��ǥ�� �����Ͽ� ���ο� ��ǥ�� �����Ѵ�.
     *     (x', y') = f(x, y)
     * @param point ������ ��ǥ
     *
     * ������� ���ε� ��ǥ�̴�.
     */
    public abstract float [] f(float [] point); // �� ���� �Լ�

    /**
     * ���� ������ ������ ���� ��Ʈ�� ��Ʈ�� ����Ʈ�� �����Ѵ�.
     * ���� ���� Ŭ������ ���� ���� �ٸ� ������ ��Ʈ�� ����Ʈ�� �����ȴ�.
     * ����, ����, ��ũ, �ܼ� ��ũ ��Ʈ�� ����Ʈ�� ���ȴ�.
     */
    public abstract void setParameters(WordArt wa); // ���� ���� ����
}