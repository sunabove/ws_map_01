package jchart;

/**
 * Title:        java chart project
 * Description:  jchart v1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
import java.awt.*;

public class XAxisTitle extends Title {

  public XAxisTitle(String title, Font font, SpreadSheet sheet, ChartElement parent, int cenX, int cenY, ShapeStyle style) {
      super(title, font, sheet, parent, cenX, cenY, style, false );
  }

  public String toString() {
     return "X(√‡) ¡¶∏Ò";
  }

}