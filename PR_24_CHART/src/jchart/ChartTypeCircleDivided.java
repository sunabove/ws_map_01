package jchart;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */
import java.awt.*;

public class ChartTypeCircleDivided extends ChartTypeCircle {

  public void setShapeAndStyle(DataSeriesView dataSeriesView) {

      super.setShapeAndStyle( dataSeriesView );

      Chart chart = dataSeriesView.getChart();

      PictureExtent pe = chart.getPictureExtent();

      Rectangle peArea = pe.getArea();

      double currRadius = peArea.width/2.0;

      double d = currRadius/1.0;

      scatterPiesBy( currRadius, dataSeriesView, d );

  }

}