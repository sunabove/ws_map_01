
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
import com.jwordart.model.wordart.ControlPointHorizontal;
import com.jwordart.model.wordart.WordArt;


public class XLinearShearMappingType extends WordArtMappingType implements LinearMappingType {

    public XLinearShearMappingType(int type) {
      super(type);
    }

    public AffineTransform getTransformInstance(WordArt wa) {

       double shear = this.getShearValue( wa ); 

       double w = wa.getGlobalDimension().getWidth(),
              h = wa.getGlobalDimension().getHeight();

       double sx = 1; // 이동 위치

       AffineTransform at = AffineTransform.getTranslateInstance(0, 0);

       if( shear > 0 ) {
           at.shear( shear, 0);

           sx = 1 - h/w*shear;

           at.scale( sx, 1.0 );
       } else if( shear < 0 ) {
           at.translate( -h*shear, 0 );

           at.shear( shear, 0);

           sx = 1 + h/w*shear;

           at.scale( sx, 1.0);
       }

       return at;
    }

   public double getShearValue(WordArt wa) {
      ControlPoint cp = wa.getControlPoint();
      if( cp == null ) {
         return 0;
      }
      if(  cp instanceof ControlPointHorizontal ) {

         double w = wa.getGlobalDimension().getWidth(),
                h = wa.getGlobalDimension().getHeight();

         double shear = 0,
            x = ( cp.getCurrentMoveRatio() - 0.5 )*w,
            y = h;

         if( y == 0 ) {
           shear = 0;
         } else {
           shear = x/y;
         }
         return shear;
      } else {
         return 0;
      }
   }

   @Override
public void setControlPoint(WordArt wa){
      wa.setControlPoint( new ControlPointHorizontal(wa, 0.25, 0.75, 0.5) );
   }
}