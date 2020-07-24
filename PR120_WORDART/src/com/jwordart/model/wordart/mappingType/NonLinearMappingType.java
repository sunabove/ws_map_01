
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
 * 워드 아트의 글립을 비선형적으로 매핑하는 클래스들의
 * 시조 클래스이다.
 */
public abstract class NonLinearMappingType extends WordArtMappingType {
    /**
     * 생성자
     * @param type 매핑 클래스 아이디이다.
     */
    public NonLinearMappingType(int type) {
      super(type);
    }

    /**
     * 비선형 매핑의 핵심이 되는 함수이다.
     * 입력된 좌표를 변형하여 새로운 좌표를 리턴한다.
     *     (x', y') = f(x, y)
     * @param point 매핑할 좌표
     *
     * 결과값은 매핑된 좌표이다.
     */
    public abstract float [] f(float [] point); // 점 매핑 함수

    /**
     * 비선형 매핑을 적용할 워드 아트의 콘트롤 포인트를 설정한다.
     * 비선형 매핑 클래스에 따라서 각기 다른 종류의 콘트롤 포인트가 설정된다.
     * 수직, 수평, 아크, 단순 아크 콘트롤 포인트가 사용된다.
     */
    public abstract void setParameters(WordArt wa); // 매핑 변수 설정
}