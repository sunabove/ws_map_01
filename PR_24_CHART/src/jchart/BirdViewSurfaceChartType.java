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
import java.awt.geom.*;
import surface.*;

public class BirdViewSurfaceChartType extends ChartType2D implements SurfaceChartType {

  public BirdViewSurfaceChartType() {
  }

  public void setShapeAndStyle(DataSeriesView dataSeriesView) {

    // Do nothing!

  }

  public void paint(Graphics2D g2, PictureExtent pictureExtent ) {

    Chart chart = pictureExtent.getChart();

    ChartData cd = chart.getChartData();

    ValueAxisOption vao = chart.getValueAxisOption();

    DataSeries [] dss = cd.getDataSeries();

    double [][] data = convertToArray( dss );

    double min = vao.getMinScale(), max = vao.getMaxScale();
    int level = (int) ( (max - min)/vao.getMajorUnit() );

    Surface surface = new Surface( data, min, max, level );

    SurfacePlotter plotter = new SurfacePlotter( surface );

    Rectangle2D area = pictureExtent.getShape().getBounds2D();

    plotter.paint( g2, area );


  }

}