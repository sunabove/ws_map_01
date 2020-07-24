package gmlviewer.gis.model;

import gmlviewer.gis.file.gml.*;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
import java.awt.geom.Rectangle2D;
import gmlviewer.gis.util.*;
import gmlviewer.gis.map.Code;
import gmlviewer.gis.GmlViewerRegistry;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class GmlArea extends Area {

  public GmlArea( GmlObject gmlObject ) {
    this( gmlObject.getRecNo(), gmlObject.getCoordinatesPnts() );
    this.gmlObject = gmlObject;
    this.setShapeAttribute( gmlObject );
  }

  private GmlArea( int recId, Pnt [] points ) {
     super( recId, points );
  }

  public GmlObject getGmlObject() {
    return gmlObject;
  }

  public void setGmlObject(GmlObject gmlObject) {
    this.gmlObject = gmlObject;
    super.setShapeAttribute( gmlObject );
  }

  public String getText() {
    if( this.getGmlObject() != null ) {
      return this.getGmlObject().getBldName();
    } else {
      return null;
    }
  }

  public Pnt getCenter() {
    if( this.getGmlObject() != null ) {
      return this.getGmlObject().getCenter();
    } else {
      return null;
    }
  }

  private boolean isTextPaintable() {

    GmlObject gmlObject = this.getGmlObject();

    if( gmlObject != null && gmlObject.isOx() && gmlObject.getCenter() != null ) {
      return true;
    } else {
      return false;
    }

  }

  public boolean isSelected() {
    return this.isSelectedLyr();
  }

  private boolean isSelectedLyr() {
    GmlObject gmlObject = this.getGmlObject();

    if( gmlObject != null && gmlObject.isOx() && gmlObject.getCenter() != null && GmlViewerRegistry.CURR_CODE != null ) {
      Code code = gmlObject.getCode();
      if( code.equals( GmlViewerRegistry.CURR_CODE ) ) {
        return true;
      } else {
        if( GmlViewerRegistry.CURR_CODE.getDai() < 0 ) {
          // 전체 보기 시에는 검색을 하지 않는다.
          return  false ;
        } else if( GmlViewerRegistry.CURR_CODE.getJung() < 1 ) {
          if( GmlViewerRegistry.CURR_CODE.getDai() == code.getDai()  ) {
            return true;
          }
        }
        return false;
      }
    } else {
      return false;
    }

  }

  public void paintTextOnly(Graphics2D g2, Project proj) {

    if( ! this.isTextPaintable() ) {
      return;
    }

    String text = this.getText();

    boolean isSelected = this.isSelectedLyr();

    Font font = this.normalFont;

    if( isSelected ) {
      font = this.selectFont ;
    }

    Pnt p = this.getCenter();

    //this.debug( "text = " + text + " p = " + p );

    if ( text != null && p != null && font != null) {

      g2.setFont(font);

      Color color = Color.black;

      if( isSelected ) {
        color = Color.red;
      }

      if (color != null) {
        g2.setColor(color);
      }

      p = proj.toGraphics(p);

      Rectangle2D trect = this.getTextBound(g2, font, text);

      double x = p.x - trect.getWidth()/2.0 ;
      double y = p.y + trect.getHeight()/2.0 ;

      g2.drawString(text, (float) x, (float) y);
    }

  }

  private static Font normalFont = FontManager.getFont( "Serif", Font.PLAIN, 12 );
  private static Font selectFont = FontManager.getFont( "Serif", Font.BOLD, 12 );

  private GmlObject gmlObject;

}
