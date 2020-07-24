
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
 * 워드 아트의 그림자를 지원하는 인터페이스이다.
 */

public interface WordArtShade {
    /**
     * 워드 아트의 그림자에 해당하는 그립을 생성하는 함수이다.
     * @param wa 워드 아트
     */
    public void makeShadeGlyphs(WordArt wa);

    /**
     * 워드 아트의 그림자를 그리는 함수이다.
     * @param wa 워드 아트
     */
    public void drawShadeGlyphs(WordArt wa);
}