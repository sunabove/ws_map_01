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

public class LinearDataElementView extends DataElementView implements LinearChartElement {

  public LinearDataElementView(SpreadSheet sheet, ChartElement parent, Shape shape,
              ShapeStyle style, ChartType chartType, DataElement dataElement) {

      super(sheet, parent, shape, style, chartType, dataElement );

  }

}