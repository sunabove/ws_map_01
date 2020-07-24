package jchart;

import java.awt.*;

/**
 * Title:        java chart project
 * Description:  jchart v1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

public class PointedObject extends Point {

  private Object value;

  public PointedObject(int x, int y, Object value) {

     super(x, y);

     this.value = value;

  }

  public Object getValue() {

     return value;

  }

  public void setValue(Object value) {

     this.value = value;

  }

  public String getInteger() {

     java.lang.Double dbl = (java.lang.Double) value;

     return "" + dbl.intValue();

  }

  public String getDouble() {

     java.lang.Double dbl = (java.lang.Double) value;

     return "" + dbl.doubleValue();

  }

  public String getString() {

     return (String) value;

  }
}