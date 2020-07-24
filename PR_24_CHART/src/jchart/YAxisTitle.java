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

public class YAxisTitle extends Title {

  public YAxisTitle(String title, Font font, SpreadSheet sheet, ChartElement parent, int cenX, int cenY, ShapeStyle style) {
      super(title, font, sheet, parent, cenX, cenY, style, true );
  }

  public String toString() {
     return "Y(√‡) ¡¶∏Ò";
  }

}