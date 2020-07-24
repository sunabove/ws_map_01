
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart;



/**
 * ���� ��Ʈ�� �׸��ڸ� �����ϴ� �������̽��̴�.
 */

public interface WordArtShade {
    /**
     * ���� ��Ʈ�� �׸��ڿ� �ش��ϴ� �׸��� �����ϴ� �Լ��̴�.
     * @param wa ���� ��Ʈ
     */
    public void makeShadeGlyphs(WordArt wa);

    /**
     * ���� ��Ʈ�� �׸��ڸ� �׸��� �Լ��̴�.
     * @param wa ���� ��Ʈ
     */
    public void drawShadeGlyphs(WordArt wa);
}