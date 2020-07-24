package gmlviewer.gis.kernel;

import gmlviewer.gis.util.ClassUtil;
import java.awt.font.GlyphVector;
import java.awt.font.FontRenderContext;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

public abstract class CommonLib
    implements KernelInterface {

  public CommonLib() {
    super();
  }

  public void debug(Exception e) {
    debug.debug(this, e);
  }

  public void debug(String msg) {
    debug.debug(this, msg);
  }

  public void debug(Object obj) {
    this.debug("" + obj);
  }

  public void debug(String[] texts) {

    if (texts != null) {
      String msg = "";
      for (int i = 0, len = texts.length; i < len; i++) {
        msg += texts[i] + (i < len - 1 ? ", " : "");
      }
      this.debug(msg);
    }
    else {
      this.debug(texts);
    }

  }

  public String getSimpleClassName() {
    return ClassUtil.getSimplifiedName(this.getClass());
  }

  public String quote(boolean b) {

    return quote(b ? "Y" : "N");

  }

  public String quote(String text) {
    if (text != null) {
      return '\'' + text + '\'';
    }
    else {
      return "''";
    }
  }

  public String quoteYmd(String text) {
    if (text != null) {
      text = text.replaceAll("-| |:", "");
      return quote(text);
    }
    else {
      return quote(text);
    }
  }

  public String toInStringValues(String[] valueList) {

    StringBuffer bfr = new StringBuffer();

    for (int i = 0, len = valueList.length; i < len; i++) {
      bfr.append(quote(valueList[i]) + (i < len - 1 ? "," : ""));
    }

    return bfr.toString();

  }

  public String quote(float f) {
    return quote("" + f);
  }

  public String quote(int i) {
    return quote("" + i);
  }

  public String quote(double d) {
    return quote("" + d);
  }

  public String number(int i) {
    return "" + i;
  }

  public String number(float f) {
    return "" + f;
  }

  public String number(double f) {
    return "" + f;
  }

  public String number(String t) {

    if (t == null) {
      return "";
    }
    else {
      return t;
    }

  }

  public static final int toInt(String txt) {

    if (txt == null || txt.length() < 1) {
      return 0;
    }

    txt = txt.replaceAll(",", "");

    try {
      return Integer.parseInt(txt);
    }
    catch (NumberFormatException ex) {
      return 0;
    }
  }

  public static final double toDouble(String txt) {

    if (txt == null || txt.length() < 1) {
      return 0;
    }

    txt = txt.replaceAll(",", "");

    try {
      return Double.parseDouble(txt);
    }
    catch (NumberFormatException ex) {
      return 0;
    }
  }


  public final double toNumber(String txt) {

    if (txt == null || txt.length() < 1) {
      return 0;
    }

    txt = txt.replaceAll(",", "");

    return Double.parseDouble(txt);
  }

  public Rectangle2D getTextBound(Graphics2D g2, Font font, String text) {
    FontRenderContext frc = g2.getFontRenderContext();
    GlyphVector gv = font.createGlyphVector(frc, text);
    return gv.getVisualBounds();
  }

}
