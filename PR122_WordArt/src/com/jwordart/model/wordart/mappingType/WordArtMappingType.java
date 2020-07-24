
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart.mappingType;

import java.awt.*;
import java.awt.geom.*;

import com.jwordart.model.wordart.GeneralCurve;
import com.jwordart.model.wordart.WordArt;
import com.jwordart.model.wordart.WordArtComponent;

/**
 * 워드 아트 글립과 외곽선을 변형하는 클래스이다.
 */

public abstract class WordArtMappingType {
  private int type = 1; // 매핑 타입 아이디. 몇 몇 매핑 타입에서만 중요한 역할을 한다.

  /**
   * 생성자
   * @param type WordArtMappingType id
   */
  public WordArtMappingType(int type) {
    this.type = type;
  }

  /**
   * 요청한 매핑 타입 코드에 따라서
   * 적절한 매핑 타입을 리턴한다.
   * @param type WordArtMappingType id
   */
  public static WordArtMappingType getWordArtMappingType(int type) {
    WordArtMappingType value;

    switch( type ) {
        case 1:
           value = new HexagonMappingType( type );
           break;
        case 2:
           value = new TriangleMappingType( type );
           break;
        case 3:
           value = new ReverseTriangleMappingType( type );
           break;
        case 4:
           value = new BadgeMappingType( type );
           break;
        case 5:
           value = new ReverseBadgeMappingType( type );
           break;
        case 6:
           value = new RingMappingType( type );
           break;
        case 7:
           value = new RingMappingType( type );
           break;
        case 8:
           value = new SimpleTopArcMappingType( type );
           break;
        case 9:
           value = new SimpleBottomArcMappingType( type );
           break;
        case 10:
           value = new SimpleArcMappingType( type );
           break;
        case 11:
           value = new SimpleButtonMappingType( type );
           break;
        case 12:
           value = new TopArcMappingType( type );
           break;
        case 13:
           value = new BottomArcMappingType( type );
           break;
        case 14:
           value = new ArcMappingType( type );
           break;
        case 15:
           value = new ButtonMappingType( type );
           break;
        case 16:
           value = new TopCurveMappingType( type );
           break;
        case 17:
           value = new BottomCurveMappingType( type );
           break;
        case 18:
           value = new TopCylinderMappingType( type );
           break;
        case 19:
           value = new BottomCylinderMappingType( type );
           break;
        case 20:
           value = new WaveMappingType( type );
           break;
        case 21:
           value = new WaveMappingType( type );
           break;
        case 22:
           value = new WaveMappingType( type );
           break;
        case 23:
           value = new WaveMappingType( type );
           break;
        case 24:
           value = new ExpansionMappingType( type );
           break;
        case 25:
           value = new CompressionMappingType( type );
           break;
        case 26:
           value = new BottomExpansionMappingType( type );
           break;
        case 27:
           value = new BottomCompressionMappingType( type );
           break;
        case 28:
           value = new TopExpansionMappingType( type );
           break;
        case 29:
           value = new TopCompressionMappingType( type );
           break;
        case 30:
           value = new CompressionAndExpansionMappingType( type );
           break;
        case 31:
           value = new CompressionExpansionAndCompressionMappingType( type );
           break;
        case 32:
           value = new RightFadeOutMappingType( type );
           break;
        case 33:
           value = new LeftFadeOutMappingType( type );
           break;
        case 34:
           value = new TopFadeOutMappingType( type );
           break;
        case 35:
           value = new BottomFadeOutMappingType( type );
           break;
        case 36:
           value = new YLinearShearMappingType(type);
           break;
        case 37:
           value = new YLinearShearMappingType(type);
           break;
        case 38:
           value = new UpwardStairMappingType(type);
           break;
        case 39:
           value = new DownwardStairMappingType(type);
           break;
        default:
           value = new XLinearShearMappingType(type);
           break;
    }

    return value;
  }

  /**
   * 워드아트 매핑 타입의 클론(Clone) 매소드이다.
   */
  public WordArtMappingType create() {
    return getWordArtMappingType( this.getTypeCode() ) ;
  }

  /**
   * 매핑 타입을 나타내는 아이디를 리턴한다.
   * 몇 몇 매핑 타입에서는 타입 코드값에 따라서
   * 그 기능이 조금씩 달라진다.
   */
  public int getTypeCode() {
    return type;
  }

  /**
   * 워드 아트 글립을 변형(매핑)한다.
   * 매핑 타입에 따라서 선형적으로 비선형적으로
   * 구분하여 매핑한다.
   */
  public void transformGlyphs(WordArtComponent wac) {
      // 리니어 매핑 타입인 경우
      if( this instanceof LinearMappingType ) {
          this.transformGlyphsLinearly( wac );
      } // 글립 로테이션 매핑 타입인 경우
      else if( this instanceof GlyphRotationMappingType ) {
          this.transformGlyphsRotatedAccordingToEllipse( wac );
      } // 트리플 그립 로테이션 매핑 타입인 경우
      else if ( this instanceof TripleGlyphRotationMappingType ) {
          this.transformGlyphsTripleRotatedAccordingToEllipse( wac );
      } else { // 넌리니어 매핑 타입인 경우
          this.transformGlyphsNonLinearly( wac );
      }
  }

  /**
   * 워드 아트를 비선형적으로 매핑한다.
   */
  public void transformGlyphsNonLinearly(WordArtComponent wac) {
      if( wac == null ) { return; }
      Shape [][] glyphs = wac.getGlyphs();
      NonLinearMappingType mt = (NonLinearMappingType) this;
      // 매핑에 앞서서 각종 파라미터를 설정한다.
      mt.setParameters( wac.getWordArt() );
      for(int i = 0; i < glyphs.length; i++ ) {
         for(int j = 0; j < glyphs[i].length; j ++ ) {
            glyphs[i][j] = new GeneralCurve( glyphs[i][j] ).getShape( mt );
         }
      }
  }

  /**
   * 워드 아트 컴포넌트를 선형적으로 매핑한다.
   */

  public void transformGlyphsLinearly(WordArtComponent wac) {
      if( wac == null ) { return; }
      WordArt wa = wac.getWordArt();
      if( wa == null ) { return; }
      // 매핑에 앞서서 변형 객채를 구한다.
      AffineTransform at = ((LinearMappingType)this).getTransformInstance( wa );
      Shape [][] glyphs = wac.getGlyphs();
      for(int i = 0; i < glyphs.length; i++ ) {
         for(int j = 0; j < glyphs[i].length; j ++ ) {
            glyphs[i][j] = at.createTransformedShape( glyphs[i][j] );
         }
      }
  }

  /**
   * 워드 아트 컴포넌트의 글립을 타원을 따라서 회전한다.
   */

  public void transformGlyphsRotatedAccordingToEllipse(WordArtComponent wac) {
      if( wac == null ) { return; }
      WordArt wa = wac.getWordArt();
      if( wa == null ) { return; }
      // 매핑에 앞서서 변형 객채를 구한다.
      AffineTransform at;
      Shape [][] glyphs = wac.getGlyphs();
      GlyphRotationMappingType grmt = (GlyphRotationMappingType)this;
      for(int i = 0; i < glyphs.length; i++ ) {
         for(int j = 0; j < glyphs[i].length; j ++ ) {
            at = grmt.getTransformInstance( wa, glyphs[i][j] );
            glyphs[i][j] = at.createTransformedShape( glyphs[i][j] );
         }
      }
  }

  /**
   * 워드 아트 컴포넌트의 글립을 세가지 방식으로 타원을 따라서 회전한다.
   */

  public void transformGlyphsTripleRotatedAccordingToEllipse(WordArtComponent wac) {
      if( wac == null ) { return; }
      WordArt wa = wac.getWordArt();
      if( wa == null ) { return; }
      // 매핑에 앞서서 변형 객채를 구한다.
      AffineTransform at;
      TripleGlyphRotationMappingType tgrmt = (TripleGlyphRotationMappingType)this;
      tgrmt.setParamenters( wa );
      Shape [][] glyphs = wac.getGlyphs();
      for(int i = 0; i < glyphs.length; i++ ) {
         for(int j = 0; j < glyphs[i].length; j ++ ) {
            at = tgrmt.getTransformInstance( wa, glyphs[i][j], i );
            glyphs[i][j] = at.createTransformedShape( glyphs[i][j] );
         }
      }
  }

  /**
   * 워드 아트 외곽선을 매핑 타입에 따라서
   * 변형한다.
   * 크게 선형적 매핑 방식과 비선형적 방식으로 변형한다.
   */
  public void transformMappingOutLines(WordArt wa) {
      // 리니어 매핑 타입인 경우
      if( this instanceof LinearMappingType ) {
          this.transformMappingOutLinesLinearly( wa );
      } // 글립 로테이션 매핑 타입인 경우
      else if( this instanceof GlyphRotationMappingType ) {
          this.transformMappingOutLinesRotatedAccordingToEllipse( wa );
      } // 단순 버튼 매핑 타입인 경우
      else if( this instanceof TripleGlyphRotationMappingType ) {
          this.transformMappingOutLinesTripleRotatedAccordingToEllipse( wa );
      }  // 넌리니어 매핑 타입인 경우
       else {
          this.transformMappingOutLinesNonLinearly( wa );
      }
  }

  /**
   * 워드 아트 외곽선을 비선형적으로 매핑한다.
   */
  public void transformMappingOutLinesLinearly(WordArt wa) {
     Shape [] value = wa.getMappingOutLines();

     if( value == null ) {
        return ;
     }

     AffineTransform at = ((LinearMappingType)this).getTransformInstance( wa );

     for(int i = 0; i < value.length; i ++ ) {
          value[i] = at.createTransformedShape( value[i] );
     }

     wa.setMappingOutLines( value );
  }

  /**
   * 글립 로테이션 매핑 타입 워드 아트의 매핑 외곽선을 설정한다.
   */
  public void transformMappingOutLinesRotatedAccordingToEllipse(WordArt wa) {
     Shape [] preMappingOutLines = wa.getMappingOutLines();

     if( preMappingOutLines == null ) {
        return ;
     }

     WordArtMappingType mt = wa.getMappingType();

     Shape [] mappingOutLines = new Shape[1];

     if( mt instanceof SimpleTopArcMappingType ) {
        TopArcMappingType tamt = new TopArcMappingType( 12 );

        tamt.setParameters( wa );

        mappingOutLines[0] = new GeneralCurve( preMappingOutLines[0] ).getShape( tamt );
     } else if( mt instanceof SimpleBottomArcMappingType ) {
        BottomArcMappingType bamt = new BottomArcMappingType( 13 );

        bamt.setParameters( wa );

        mappingOutLines[0] = new GeneralCurve( preMappingOutLines[0] ).getShape( bamt );
     } else if( mt instanceof SimpleArcMappingType ) {
        ArcMappingType amt = new ArcMappingType( 14 );

        amt.setParameters( wa );

        mappingOutLines[0] = new GeneralCurve( preMappingOutLines[0] ).getShape( amt );
     }

     wa.setMappingOutLines( mappingOutLines );
  }

   /**
   * 트리플 글립 로테이션 매핑 타입 워드 아트의 매핑 외곽선을 설정한다.
   */
  public void transformMappingOutLinesTripleRotatedAccordingToEllipse(WordArt wa) {
     Shape [] preMappingOutLines = wa.getMappingOutLines();

     if( preMappingOutLines == null ) {
        return ;
     }

     double w = wa.getGlobalDimension().getWidth(),
            h = wa.getGlobalDimension().getHeight();

     Shape [] mappingOutLines = new Shape[3];

     // 상단 매핑 외곽선 설정
     TopArcMappingType tamt = new TopArcMappingType( 12 );

     tamt.setParameters( wa );

     mappingOutLines[0] = new GeneralCurve( preMappingOutLines[0] ).getShape( tamt );
     // 끝. 상단 외곽선 설정

     // 하단 매핑 외곽선 설정
     AffineTransform at = AffineTransform.getTranslateInstance(0, h/2.0);
     at.scale(1.0, -1.0);
     at.translate(0, -h/2.0);

     mappingOutLines[2] = at.createTransformedShape( mappingOutLines[0] );
     // 중단 매핑 외곽선 설정

     Line2D middleLine = new Line2D.Double(0, h/2.0, w, h/2.0);

     mappingOutLines[1] = middleLine;
     // 끝. 중단 외곽선 설정

     wa.setMappingOutLines( mappingOutLines );
  }

  /**
   * 워드 아트 외곽선을 비선형적으로 매핑한다.
   */
  public void transformMappingOutLinesNonLinearly(WordArt wa) {
     Shape [] value = wa.getMappingOutLines();


     if( value == null ) {
        return ;
     }

     NonLinearMappingType mt = (NonLinearMappingType) this;

     // 움쳐렸다 부풀리기 매핑 타입일 경우
     if( mt instanceof CompressionAndExpansionMappingType ) {
         CompressionAndExpansionMappingType cemt = (CompressionAndExpansionMappingType) mt;
         Shape [] newOutLines = new Shape[2];

         double upBottomY = cemt.getUpperComponentBottomY( wa ),
                downTopY = cemt.getDownComponentTopY( wa ),
                w = wa.getGlobalDimension().getWidth(),
                h = wa.getGlobalDimension().getHeight(),
                middleGap = cemt.getMiddleGap();

         cemt.setControledParameters( wa );

         if( upBottomY == downTopY ) { // 워드 아트의 라인 수가 하나일 때,
             newOutLines[0] = new Line2D.Double(0, h, w, h);
             newOutLines[0] = new GeneralCurve( newOutLines[0] ).getShape( mt );
             AffineTransform at = AffineTransform.getTranslateInstance( 0, middleGap );
             newOutLines[1] = at.createTransformedShape( newOutLines[0] );
         }else {
             newOutLines[0] = new Line2D.Double(0, upBottomY, w, upBottomY);
             newOutLines[1] = new Line2D.Double(0, downTopY, w, downTopY);
             for(int i = 0; i < newOutLines.length; i ++ ) {
                newOutLines[i] = new GeneralCurve( newOutLines[i] ).getShape( mt );
             }
         }
         value = new Shape[4];
         value[0] = new Line2D.Double(0, 0, w, 0);
         value[1] = newOutLines[0];
         value[2] = newOutLines[1];
         value[3] = new Line2D.Double(0, h, w, h);
    } // 움쳐렸다 부풀렸다 다시 움츠리기 매핑 타입인 경우
    else if( mt instanceof CompressionExpansionAndCompressionMappingType ) {
         CompressionExpansionAndCompressionMappingType cecmt
             = (CompressionExpansionAndCompressionMappingType) mt;

         Shape [] newOutLines = new Shape[4];
         // CompressionExpansionAndCompressionMappingType 의 외곽선은 4개이고
         // 위쪽으로 위치한 순서대로 인덱스를 정한다.
         // 인덱스가 낮을 수록 위쪽에 위치한 외곽선이다.

         double upBottomY = cecmt.getUpperComponentBottomY( wa ),
                midTopY = cecmt.getMiddleComponentTopY( wa ),
                midBottomY = cecmt.getMiddleComponentBottomY( wa ),
                downTopY = cecmt.getDownComponentTopY( wa ),
                w = wa.getGlobalDimension().getWidth(),
                h = wa.getGlobalDimension().getHeight(),
                middleGap = cecmt.getMiddleGap(),
                divisionHeight = cecmt.getDivisionHeight();

         int lineNum = cecmt.getLineNumber(); // 워드 아트 라인 갯수

         cecmt.setControledParameters( wa );

         if( lineNum == 1 ) { // 워드 아트의 라인 수가 하나일 때,
             newOutLines[0] = new Line2D.Double(0, h, w, h);
             newOutLines[0] = new GeneralCurve( newOutLines[0] ).getShape( mt );

             // 중간 갭 만틈 이동하는 변형 객체
             AffineTransform atTrans = AffineTransform.getTranslateInstance( 0, middleGap );

             newOutLines[1] = atTrans.createTransformedShape( newOutLines[0] );

             // 원점 이동후, y 축으로 대칭 변환 한 다음, 다시 원점 + 중간 갭 만큼 이동하는 변형 객체
             AffineTransform atScale = AffineTransform.getTranslateInstance(0, 2*divisionHeight + middleGap);
             atScale.scale(1.0, - 1.0); // y 축으로 대칭 변환
             atScale.translate(0, - divisionHeight); // 원점으로 이동

             newOutLines[2] = atScale.createTransformedShape( newOutLines[1] );

             newOutLines[3] = atTrans.createTransformedShape( newOutLines[2] );
         } else if(lineNum == 2 ){ // 워드 아트의 라인 수가 2개일 경우
             newOutLines[0] = new Line2D.Double(0, upBottomY, w, upBottomY);
             newOutLines[1] = new Line2D.Double(0, midTopY, w, midTopY);
             newOutLines[2] = new Line2D.Double(0, midBottomY, w, midBottomY);

             for(int i = 0; i < 3 ; i ++ ) {
                newOutLines[i] = new GeneralCurve( newOutLines[i] ).getShape( mt );
             }

             // 중간 갭 만틈 이동하는 변형 객체
             AffineTransform atTrans = AffineTransform.getTranslateInstance( 0, middleGap );

             newOutLines[3] = atTrans.createTransformedShape( newOutLines[2] );
         } else {
             newOutLines[0] = new Line2D.Double(0, upBottomY, w, upBottomY);
             newOutLines[1] = new Line2D.Double(0, midTopY, w, midTopY);
             newOutLines[2] = new Line2D.Double(0, midBottomY, w, midBottomY);
             newOutLines[3] = new Line2D.Double(0, downTopY, w, downTopY);

             for(int i = 0; i < 4 ; i ++ ) {
                newOutLines[i] = new GeneralCurve( newOutLines[i] ).getShape( mt );
             }
         }
         value = new Shape[6];
         value[0] = new Line2D.Double(0, 0, w, 0);
         value[1] = newOutLines[0];
         value[2] = newOutLines[1];
         value[3] = newOutLines[2];
         value[4] = newOutLines[3];
         value[5] = new Line2D.Double(0, h, w, h);
    } // ButtonMappingType 인 경우
    else if( mt instanceof ButtonMappingType ) {
         ButtonMappingType bmt
             = (ButtonMappingType) mt;

         Shape [] newOutLines = new Shape[6];
         // ButtonMappingType의 외곽선은 6개이고 위에서 위치한 순서대로
         // 외곽선의 인덱스를 정한다. 즉, 인덱스가 적을 수로 위쪽으로
         // 위치하고 있는 외곽선이다.

         double upBottomY = bmt.getUpperComponentBottomY( wa ),
                //midTopY = bmt.getMiddleComponentTopY( wa ),
                //midBottomY = bmt.getMiddleComponentBottomY( wa ),
                //downTopY = bmt.getDownComponentTopY( wa ),
                w = wa.getGlobalDimension().getWidth(),
                h = wa.getGlobalDimension().getHeight();

         bmt.setControledParameters( wa );

         // 상단 컴포넌트 외곽선
         newOutLines[0] = new GeneralCurve( value[0] ).getShape( mt );
         double halfLineGap = bmt.getLineGap()/2.0; // 워드 아트 줄간 간격의 반 값
         // 워드 아트의 상단 컴포넌트의 최하단 좌표는 실제 좌표에서 반 줄간 간격 만큼
         // 더 해져있으므로 이를 다시 보정한다.
         Line2D upBottomLine = new Line2D.Double(0, upBottomY - halfLineGap, w, upBottomY - halfLineGap);
         newOutLines[1] = new GeneralCurve( upBottomLine ).getShape( mt );
         // 끝. 상단 외곽선

         // 하단 컴포넌트 외곽선
         AffineTransform at = AffineTransform.getTranslateInstance(0, h/2.0);
         at.scale(1, -1);
         at.translate(0, -h/2.0);

         newOutLines[4] = at.createTransformedShape( newOutLines[1] );
         newOutLines[5] = at.createTransformedShape( newOutLines[0] );
         // 끝. 하단 외곽선

         // 중단 컴포넌트 외곽선
         Shape [] middleLines = bmt.getMiddleComponentLines();

         newOutLines[2] = middleLines[0];
         newOutLines[3] = middleLines[1];
         // 끝. 중단 외곽선

         value = newOutLines;
    } else {
         mt.setParameters( wa );
         for(int i = 0; i < value.length; i ++ ) {
             value[i] = new GeneralCurve( value[i] ).getShape( mt );
         }
     }

     wa.setMappingOutLines( value );
  }


  /**
   * 콘트롤 포인트를 워드 아트에 설정한다.
   * @param wa 콘트롤 포인트를 설정한 워드 아트
   */
  public abstract void setControlPoint(WordArt wa); // 콘트롤 포인트를 설정한다.
}