
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
 * ���� ��Ʈ �۸��� �ܰ����� �����ϴ� Ŭ�����̴�.
 */

public abstract class WordArtMappingType {
  private int type = 1; // ���� Ÿ�� ���̵�. �� �� ���� Ÿ�Կ����� �߿��� ������ �Ѵ�.

  /**
   * ������
   * @param type WordArtMappingType id
   */
  public WordArtMappingType(int type) {
    this.type = type;
  }

  /**
   * ��û�� ���� Ÿ�� �ڵ忡 ����
   * ������ ���� Ÿ���� �����Ѵ�.
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
   * �����Ʈ ���� Ÿ���� Ŭ��(Clone) �żҵ��̴�.
   */
  public WordArtMappingType create() {
    return getWordArtMappingType( this.getTypeCode() ) ;
  }

  /**
   * ���� Ÿ���� ��Ÿ���� ���̵� �����Ѵ�.
   * �� �� ���� Ÿ�Կ����� Ÿ�� �ڵ尪�� ����
   * �� ����� ���ݾ� �޶�����.
   */
  public int getTypeCode() {
    return type;
  }

  /**
   * ���� ��Ʈ �۸��� ����(����)�Ѵ�.
   * ���� Ÿ�Կ� ���� ���������� ����������
   * �����Ͽ� �����Ѵ�.
   */
  public void transformGlyphs(WordArtComponent wac) {
      // ���Ͼ� ���� Ÿ���� ���
      if( this instanceof LinearMappingType ) {
          this.transformGlyphsLinearly( wac );
      } // �۸� �����̼� ���� Ÿ���� ���
      else if( this instanceof GlyphRotationMappingType ) {
          this.transformGlyphsRotatedAccordingToEllipse( wac );
      } // Ʈ���� �׸� �����̼� ���� Ÿ���� ���
      else if ( this instanceof TripleGlyphRotationMappingType ) {
          this.transformGlyphsTripleRotatedAccordingToEllipse( wac );
      } else { // �͸��Ͼ� ���� Ÿ���� ���
          this.transformGlyphsNonLinearly( wac );
      }
  }

  /**
   * ���� ��Ʈ�� ���������� �����Ѵ�.
   */
  public void transformGlyphsNonLinearly(WordArtComponent wac) {
      if( wac == null ) { return; }
      Shape [][] glyphs = wac.getGlyphs();
      NonLinearMappingType mt = (NonLinearMappingType) this;
      // ���ο� �ռ��� ���� �Ķ���͸� �����Ѵ�.
      mt.setParameters( wac.getWordArt() );
      for(int i = 0; i < glyphs.length; i++ ) {
         for(int j = 0; j < glyphs[i].length; j ++ ) {
            glyphs[i][j] = new GeneralCurve( glyphs[i][j] ).getShape( mt );
         }
      }
  }

  /**
   * ���� ��Ʈ ������Ʈ�� ���������� �����Ѵ�.
   */

  public void transformGlyphsLinearly(WordArtComponent wac) {
      if( wac == null ) { return; }
      WordArt wa = wac.getWordArt();
      if( wa == null ) { return; }
      // ���ο� �ռ��� ���� ��ä�� ���Ѵ�.
      AffineTransform at = ((LinearMappingType)this).getTransformInstance( wa );
      Shape [][] glyphs = wac.getGlyphs();
      for(int i = 0; i < glyphs.length; i++ ) {
         for(int j = 0; j < glyphs[i].length; j ++ ) {
            glyphs[i][j] = at.createTransformedShape( glyphs[i][j] );
         }
      }
  }

  /**
   * ���� ��Ʈ ������Ʈ�� �۸��� Ÿ���� ���� ȸ���Ѵ�.
   */

  public void transformGlyphsRotatedAccordingToEllipse(WordArtComponent wac) {
      if( wac == null ) { return; }
      WordArt wa = wac.getWordArt();
      if( wa == null ) { return; }
      // ���ο� �ռ��� ���� ��ä�� ���Ѵ�.
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
   * ���� ��Ʈ ������Ʈ�� �۸��� ������ ������� Ÿ���� ���� ȸ���Ѵ�.
   */

  public void transformGlyphsTripleRotatedAccordingToEllipse(WordArtComponent wac) {
      if( wac == null ) { return; }
      WordArt wa = wac.getWordArt();
      if( wa == null ) { return; }
      // ���ο� �ռ��� ���� ��ä�� ���Ѵ�.
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
   * ���� ��Ʈ �ܰ����� ���� Ÿ�Կ� ����
   * �����Ѵ�.
   * ũ�� ������ ���� ��İ� ������ ������� �����Ѵ�.
   */
  public void transformMappingOutLines(WordArt wa) {
      // ���Ͼ� ���� Ÿ���� ���
      if( this instanceof LinearMappingType ) {
          this.transformMappingOutLinesLinearly( wa );
      } // �۸� �����̼� ���� Ÿ���� ���
      else if( this instanceof GlyphRotationMappingType ) {
          this.transformMappingOutLinesRotatedAccordingToEllipse( wa );
      } // �ܼ� ��ư ���� Ÿ���� ���
      else if( this instanceof TripleGlyphRotationMappingType ) {
          this.transformMappingOutLinesTripleRotatedAccordingToEllipse( wa );
      }  // �͸��Ͼ� ���� Ÿ���� ���
       else {
          this.transformMappingOutLinesNonLinearly( wa );
      }
  }

  /**
   * ���� ��Ʈ �ܰ����� ���������� �����Ѵ�.
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
   * �۸� �����̼� ���� Ÿ�� ���� ��Ʈ�� ���� �ܰ����� �����Ѵ�.
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
   * Ʈ���� �۸� �����̼� ���� Ÿ�� ���� ��Ʈ�� ���� �ܰ����� �����Ѵ�.
   */
  public void transformMappingOutLinesTripleRotatedAccordingToEllipse(WordArt wa) {
     Shape [] preMappingOutLines = wa.getMappingOutLines();

     if( preMappingOutLines == null ) {
        return ;
     }

     double w = wa.getGlobalDimension().getWidth(),
            h = wa.getGlobalDimension().getHeight();

     Shape [] mappingOutLines = new Shape[3];

     // ��� ���� �ܰ��� ����
     TopArcMappingType tamt = new TopArcMappingType( 12 );

     tamt.setParameters( wa );

     mappingOutLines[0] = new GeneralCurve( preMappingOutLines[0] ).getShape( tamt );
     // ��. ��� �ܰ��� ����

     // �ϴ� ���� �ܰ��� ����
     AffineTransform at = AffineTransform.getTranslateInstance(0, h/2.0);
     at.scale(1.0, -1.0);
     at.translate(0, -h/2.0);

     mappingOutLines[2] = at.createTransformedShape( mappingOutLines[0] );
     // �ߴ� ���� �ܰ��� ����

     Line2D middleLine = new Line2D.Double(0, h/2.0, w, h/2.0);

     mappingOutLines[1] = middleLine;
     // ��. �ߴ� �ܰ��� ����

     wa.setMappingOutLines( mappingOutLines );
  }

  /**
   * ���� ��Ʈ �ܰ����� ���������� �����Ѵ�.
   */
  public void transformMappingOutLinesNonLinearly(WordArt wa) {
     Shape [] value = wa.getMappingOutLines();


     if( value == null ) {
        return ;
     }

     NonLinearMappingType mt = (NonLinearMappingType) this;

     // ���ķȴ� ��Ǯ���� ���� Ÿ���� ���
     if( mt instanceof CompressionAndExpansionMappingType ) {
         CompressionAndExpansionMappingType cemt = (CompressionAndExpansionMappingType) mt;
         Shape [] newOutLines = new Shape[2];

         double upBottomY = cemt.getUpperComponentBottomY( wa ),
                downTopY = cemt.getDownComponentTopY( wa ),
                w = wa.getGlobalDimension().getWidth(),
                h = wa.getGlobalDimension().getHeight(),
                middleGap = cemt.getMiddleGap();

         cemt.setControledParameters( wa );

         if( upBottomY == downTopY ) { // ���� ��Ʈ�� ���� ���� �ϳ��� ��,
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
    } // ���ķȴ� ��Ǯ�ȴ� �ٽ� �������� ���� Ÿ���� ���
    else if( mt instanceof CompressionExpansionAndCompressionMappingType ) {
         CompressionExpansionAndCompressionMappingType cecmt
             = (CompressionExpansionAndCompressionMappingType) mt;

         Shape [] newOutLines = new Shape[4];
         // CompressionExpansionAndCompressionMappingType �� �ܰ����� 4���̰�
         // �������� ��ġ�� ������� �ε����� ���Ѵ�.
         // �ε����� ���� ���� ���ʿ� ��ġ�� �ܰ����̴�.

         double upBottomY = cecmt.getUpperComponentBottomY( wa ),
                midTopY = cecmt.getMiddleComponentTopY( wa ),
                midBottomY = cecmt.getMiddleComponentBottomY( wa ),
                downTopY = cecmt.getDownComponentTopY( wa ),
                w = wa.getGlobalDimension().getWidth(),
                h = wa.getGlobalDimension().getHeight(),
                middleGap = cecmt.getMiddleGap(),
                divisionHeight = cecmt.getDivisionHeight();

         int lineNum = cecmt.getLineNumber(); // ���� ��Ʈ ���� ����

         cecmt.setControledParameters( wa );

         if( lineNum == 1 ) { // ���� ��Ʈ�� ���� ���� �ϳ��� ��,
             newOutLines[0] = new Line2D.Double(0, h, w, h);
             newOutLines[0] = new GeneralCurve( newOutLines[0] ).getShape( mt );

             // �߰� �� ��ƴ �̵��ϴ� ���� ��ü
             AffineTransform atTrans = AffineTransform.getTranslateInstance( 0, middleGap );

             newOutLines[1] = atTrans.createTransformedShape( newOutLines[0] );

             // ���� �̵���, y ������ ��Ī ��ȯ �� ����, �ٽ� ���� + �߰� �� ��ŭ �̵��ϴ� ���� ��ü
             AffineTransform atScale = AffineTransform.getTranslateInstance(0, 2*divisionHeight + middleGap);
             atScale.scale(1.0, - 1.0); // y ������ ��Ī ��ȯ
             atScale.translate(0, - divisionHeight); // �������� �̵�

             newOutLines[2] = atScale.createTransformedShape( newOutLines[1] );

             newOutLines[3] = atTrans.createTransformedShape( newOutLines[2] );
         } else if(lineNum == 2 ){ // ���� ��Ʈ�� ���� ���� 2���� ���
             newOutLines[0] = new Line2D.Double(0, upBottomY, w, upBottomY);
             newOutLines[1] = new Line2D.Double(0, midTopY, w, midTopY);
             newOutLines[2] = new Line2D.Double(0, midBottomY, w, midBottomY);

             for(int i = 0; i < 3 ; i ++ ) {
                newOutLines[i] = new GeneralCurve( newOutLines[i] ).getShape( mt );
             }

             // �߰� �� ��ƴ �̵��ϴ� ���� ��ü
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
    } // ButtonMappingType �� ���
    else if( mt instanceof ButtonMappingType ) {
         ButtonMappingType bmt
             = (ButtonMappingType) mt;

         Shape [] newOutLines = new Shape[6];
         // ButtonMappingType�� �ܰ����� 6���̰� ������ ��ġ�� �������
         // �ܰ����� �ε����� ���Ѵ�. ��, �ε����� ���� ���� ��������
         // ��ġ�ϰ� �ִ� �ܰ����̴�.

         double upBottomY = bmt.getUpperComponentBottomY( wa ),
                //midTopY = bmt.getMiddleComponentTopY( wa ),
                //midBottomY = bmt.getMiddleComponentBottomY( wa ),
                //downTopY = bmt.getDownComponentTopY( wa ),
                w = wa.getGlobalDimension().getWidth(),
                h = wa.getGlobalDimension().getHeight();

         bmt.setControledParameters( wa );

         // ��� ������Ʈ �ܰ���
         newOutLines[0] = new GeneralCurve( value[0] ).getShape( mt );
         double halfLineGap = bmt.getLineGap()/2.0; // ���� ��Ʈ �ٰ� ������ �� ��
         // ���� ��Ʈ�� ��� ������Ʈ�� ���ϴ� ��ǥ�� ���� ��ǥ���� �� �ٰ� ���� ��ŭ
         // �� ���������Ƿ� �̸� �ٽ� �����Ѵ�.
         Line2D upBottomLine = new Line2D.Double(0, upBottomY - halfLineGap, w, upBottomY - halfLineGap);
         newOutLines[1] = new GeneralCurve( upBottomLine ).getShape( mt );
         // ��. ��� �ܰ���

         // �ϴ� ������Ʈ �ܰ���
         AffineTransform at = AffineTransform.getTranslateInstance(0, h/2.0);
         at.scale(1, -1);
         at.translate(0, -h/2.0);

         newOutLines[4] = at.createTransformedShape( newOutLines[1] );
         newOutLines[5] = at.createTransformedShape( newOutLines[0] );
         // ��. �ϴ� �ܰ���

         // �ߴ� ������Ʈ �ܰ���
         Shape [] middleLines = bmt.getMiddleComponentLines();

         newOutLines[2] = middleLines[0];
         newOutLines[3] = middleLines[1];
         // ��. �ߴ� �ܰ���

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
   * ��Ʈ�� ����Ʈ�� ���� ��Ʈ�� �����Ѵ�.
   * @param wa ��Ʈ�� ����Ʈ�� ������ ���� ��Ʈ
   */
  public abstract void setControlPoint(WordArt wa); // ��Ʈ�� ����Ʈ�� �����Ѵ�.
}