package gmlviewer.gis.style;

import java.lang.reflect.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;
import gmlviewer.gis.*;
import gmlviewer.gis.model.*;
import gmlviewer.gis.util.*;
import java.awt.font.GlyphVector;
import java.awt.font.FontRenderContext;

public abstract class Style extends gmlviewer.gis.kernel.CommonLib {

  public Style() {
  }

  public void paint(Graphics2D g2, java.awt.Shape shape) {
    fill(g2, shape);
    draw(g2, shape);
  }

  public String toString() {
    Class topKlass = Style.class;
    Object obj = this;
    Class klass = obj.getClass();
    Vector list = new Vector();
    StringBuffer styleValue;

    while( topKlass.isAssignableFrom( klass ) ) {
      styleValue = this.toStyleValue( obj, klass );
      list.add( 0, styleValue );
      klass = klass.getSuperclass();
    }

    StringBuffer bfr = new StringBuffer();
    bfr.append( "class: " + ClassUtil.getSimplifiedName( obj.getClass() ) + "; "  ) ;
    for( int i = 0, len = list.size() ; i < len ; i ++ ) {
      bfr.append( list.elementAt( i ) );
    }
    return bfr.toString();
  }

  private StringBuffer toStyleValue( Object obj, Class klass ) {

    StringBuffer bfr = new StringBuffer();
    Field [] flds = this.getStyleFields( klass );
    Field fld;
    Object value;
    String cssStyleValue ;
    for( int i = 0, len = flds.length ; i < len ; i ++ ) {
      fld = flds[i];
      try {
        fld.setAccessible( true );
        value = fld.get(obj);
        cssStyleValue = this.toStyleAttribute( fld, value );
        bfr.append( cssStyleValue );
      }
      catch ( Exception ex) {
        debug( ex );
      }
    }
    return bfr ;
  }

  private String toStyleAttribute( Field fld, Object value ) {
    String text = fld.getName() + ": " + toObjText( value ) + "; " ;
    return text;
  }

  private String toObjText( Object value ) {

    if( value instanceof Color ) {
      Color col = (Color) value;
      return HtmlUtil.toHtmlColor( col );
    } else if( value instanceof Font ) {
      return toFontText( (Font) value );
    }

    return "" + value;
  }

  private String toFontText( Font font ) {
    return  font.getName()
        + ", " + font.getStyle()
        + ", " + font.getSize()
        ;
  }

  private Field [] getStyleFields( Class klass ) {

    Field [] fieldList = klass.getDeclaredFields();

    Vector list = new Vector();

    Field field;

    for (int i = 0, len = fieldList.length; i < len; i++) {

      field = fieldList[i];

      if( check( field ) ) {
        list.add( field );
      }

    }

    int size = list.size();
    Field [] objs = new Field[ size ];
    list.toArray( objs );

    return objs;

  }


  public boolean check(Field field) {

    int mod = field.getModifiers();

    if ( Modifier.isStatic(mod) || Modifier.isTransient(mod) ) {
      return false;
    } else {
      return true;
    }

  }

  // abstract method

  public abstract void fill(Graphics2D g2, java.awt.Shape shape);

  public abstract void draw(Graphics2D g2, java.awt.Shape shape);

  public abstract void paint(Graphics2D g2, Shape shape, Project proj ) ;


  // end of abstract method

  // static method

  // end of static method

}
