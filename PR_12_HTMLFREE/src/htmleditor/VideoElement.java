package htmleditor;

/**
 * Title:        Test Publishing System
 * Description:  Internet Based Test Publishing System V1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;
import java.awt.geom.*;
import java.io.*;

public class VideoElement extends ImageElement {

  public VideoElement(HtmlDocument doc, File src ) {
      this( doc, src, new Point(0, 0) );
  }

  public VideoElement(HtmlDocument doc, File src, Point loc) {
      super( doc, src, loc );
  }

  public VideoElement(HtmlDocument doc, File src, Rectangle area ) {

     this( doc, src, area, RECT, null );

  }

  public VideoElement(HtmlDocument doc, File src, Rectangle area, int style, String href ) {
      super( doc, src, area, style, href );
  }

  public Object clone(HtmlDocument doc) {

      Rectangle2D area = this.getArea();

      Rectangle cloneArea = new Rectangle( (int) area.getX(), (int) area.getY(), (int) area.getWidth(), (int) area.getHeight() );

      return new VideoElement( doc, src, cloneArea, style, href );

  }

  public String tag() {

      String src = getSrcLocation();

      Rectangle2D area = getArea();

      int width = (int) area.getWidth();
      int height = (int) area.getHeight();

      return "<embed src = \"" + src + "\"" + " autostart = \"false\"" + " width = \"" + width + "\" height = \"" + height + "\"></embed>";

  }

}