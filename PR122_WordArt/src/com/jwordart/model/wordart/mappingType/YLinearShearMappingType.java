
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p> 
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.model.wordart.mappingType;

import java.awt.geom.*;

import com.jwordart.model.wordart.ControlPoint;
import com.jwordart.model.wordart.ControlPointVertical;
import com.jwordart.model.wordart.WordArt;


public class YLinearShearMappingType extends WordArtMappingType implements LinearMappingType {

  public YLinearShearMappingType(int type) {
     super(type);
  }

  public AffineTransform getTransformInstance(WordArt wa) {

       double shear = this.getShearValue( wa ); 

       double w = wa.getGlobalDimension().getWidth(),
              h = wa.getGlobalDimension().getHeight();

       AffineTransform at = AffineTransform.getTranslateInstance( 0, 0 );

       double sy;

       if( shear > 0 ) { // 타입이 36 일 때,
          at.shear( 0, shear );
          sy = 1 - w/h*shear;
          at.scale( 1, sy );
       } else if( shear < 0 ) {
          at.translate( 0, - w*shear );
          at.shear( 0, shear );
          sy = 1 + w/h*shear;
          at.scale( 1, sy );
       }

       return at;
    }


  public double getShearValue(WordArt wa) {
      ControlPoint cp = wa.getControlPoint();
      if( cp == null ) {
         return 0;
      }
      if(  cp instanceof ControlPointVertical ) {
         double w = wa.getGlobalDimension().getWidth(),
                h = wa.getGlobalDimension().getHeight();
         double shear = 0,
                y = cp.getCurrentMoveRatio()*h;

          int typeCode = super.getTypeCode();

         if( typeCode == 37 ) {
            y = h - y;
         }

         shear = y/w;

         if( typeCode == 36 ) {
            shear *= - 1;
         }

         return shear;
      } else {
         return 0;
      }
  }

  @Override
public void setControlPoint(WordArt wa){
     ControlPoint cp = null;
     if( super.getTypeCode() == 37 ) {
       cp = new ControlPointVertical(wa, 0.25, 1.0, 0.45);
     } else {
       cp = new ControlPointVertical(wa, 0, 0.75, 0.55);
     }
     wa.setControlPoint( cp );
  }
}