package gmlviewer.gis.style;

import java.awt.*;
import java.awt.Font;
import java.awt.Color;
import java.awt.image.*;
import java.awt.font.*;
import java.awt.geom.*;
import gmlviewer.gis.model.Project;
import gmlviewer.gis.model.ShapeText;
import gmlviewer.gis.model.Pnt;
import gmlviewer.gis.util.*;
import javax.swing.ImageIcon;
import gmlviewer.gis.GmlViewerRegistry;

public class TextStyle
    extends Style {

  private TextStyle( Color color) {
    this( FontManager.getDefaultFont() , color, null);
  }

  private TextStyle(Font font, Color color ) {
    this( font, color, null );
  }


  private TextStyle(Font font, Color color, String iconName) {
    this.font = font;
    this.color = color;
    this.iconName = iconName;
  }

  private BufferedImage getIconImage() {
    if (this.iconImage != null) {
      return this.iconImage;
    }
    else {
      String iconName = this.iconName;
      if (iconName != null && iconName.length() > 0) {
        // should be recoded
        BufferedImage iconImage = (BufferedImage) getImageIcon(iconName).getImage() ;
        this.iconImage = iconImage;
        return iconImage;
      }
      else {
        return null;
      }
    }

  }

  public void fill(Graphics2D g2, java.awt.Shape shape) {
  }

  public void draw(Graphics2D g2, java.awt.Shape shape) {
  }

  public void paint(Graphics2D g2, gmlviewer.gis.model.Shape shape, Project proj) {
    if (shape instanceof ShapeText) {
      ShapeText text = (ShapeText) shape;
      //debug( "TEXT = " + text.getString() );
      BufferedImage iconImage = this.getIconImage();
      if (iconImage != null) {
        this.paintIconText(g2, text, proj, iconImage);
      }
      else {
        this.paintTextOnly(g2, text, proj);
      }
    }
    else {
      debug("Wrong Shape at TextStyle !");
      //debug( "Wrong Shape at TextStyle = " + shape );
    }
  }

  public void paintTextOnly(Graphics2D g2, ShapeText textObj, Project proj) {

    Font font = this.font;

    if (font != null) {
      g2.setFont(font);
      Color color = this.color;
      if (color != null) {
        g2.setColor(color);
      }

      Pnt p = textObj.getPoint();
      p = proj.toGraphics(p);

      String text = textObj.getString();

      Rectangle2D trect = this.getTextBound(g2, font, text);

      double x = p.x - trect.getWidth()/2.0 ;
      double y = p.y + trect.getHeight()/2.0 ;

      g2.drawString(text, (float) x, (float) y);
    }

  }

  public void paintIconText(Graphics2D g2, ShapeText textObj, Project proj,
                            BufferedImage image) {

    String text = textObj.getString();
    Pnt p = textObj.getPoint();
    p = proj.toGraphics(p);

    double x = p.x;
    double y = p.y;

    double iw = image.getWidth();
    double ih = image.getHeight();
    double ix = x - iw / 2.0;
    double iy = y - ih / 2.0;

    g2.drawImage(image, (int) ix, (int) iy, null);

    Font font = this.font;

    if (font != null) {
      g2.setFont(font);

      Color color = this.color;
      if (color != null) {
        g2.setColor(color);
      }

      Rectangle2D trect = this.getTextBound(g2, font, text);

      float w = (float) (trect.getWidth());
      float a = (float) (trect.getHeight());

      float tx = (float) (x - w / 2.0);
      float ty = (float) (iy + ih + a + 2);

      String valign = this.valign;
      if (valign != null && valign.equalsIgnoreCase("middle")) {
        ty = (float) (iy + ih / 2.0 + a / 2.0);
      }

      g2.drawString(text, tx, ty);
    }

  }

  public String toString() {
    Font font = this.font;
    Color color = this.color;
    String info = "";
    if (font != null) {
      info += ("fontName:" + font.getName() + "; "
               + "fontSize:" + font.getSize() + "; "
               + "fontStyle:" + font.getStyle() + "; ");
    }
    else {
      info += "font: null; ";
    }
    if (color != null) {
      info += ("fontColor:" + HtmlUtil.toHtmlColor(color) + "; ");
    }
    else {
      info += "fontColor: null; ";
    }
    return info;
  }

  public ImageIcon getImageIcon(java.net.URL imageURL) {
    return new javax.swing.ImageIcon(imageURL);
  }

  public ImageIcon getImageIcon(String imageURL) {
    return new javax.swing.ImageIcon(imageURL);
  }


  // member
  private Font font;
  private Color color;
  private String iconName;
  private String valign;
  private BufferedImage iconImage;

  // static method

  public static TextStyle getStyle(String name, int style, Color color,
                                   int size) {
    return getStyle(name, style, color, size, null);
  }

  public static TextStyle getStyle(String name, int style, Color color,
                                   int size, String iconName) {
    Font font;
    if (name == null || name.equalsIgnoreCase("NULL")) {
      font = null;
    }
    else {
      font = new Font(name, style, size);
    }
    return new TextStyle(font, color, iconName);
  }

  public static TextStyle getStyle(String name, int style, Color color,
                                   int size, String iconName, String valign) {
    Font font;
    if (name == null || name.equalsIgnoreCase("NULL")) {
      font = null;
    }
    else {
      font = new Font(name, style, size);
    }
    TextStyle textStyle = new TextStyle(font, color, iconName);
    textStyle.valign = valign;

    return textStyle;
  }

}
