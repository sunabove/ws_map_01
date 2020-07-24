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

public class ChartTypeDonutDivided extends ChartTypeDonut {

  public ChartTypeDonutDivided() {
  }

  public void setShapeAndStyle(DataSeriesView dataSeriesView) {

    super.setShapeAndStyle( dataSeriesView );

    Chart chart = dataSeriesView.getChart();
    ChartData cd = chart.getChartData();
    PictureExtent pe = chart.getPictureExtent();


    DataSeries [] dss = cd.getDataSeries();

    int dssLen = dss.length;

    int oci = pe.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

    double donutInnerRadiusRatio = pe.donutInnerRadiusRatio/100.0; // 도넛 내부 반경 비율
    double outerRadius = pe.getWidth()/2.0;
    double innerRadius = outerRadius*donutInnerRadiusRatio;

    double dr = (outerRadius - innerRadius)/( dssLen + 0.0);  // unit radius of a donuts

    if( oci == dssLen - 1 ) {

      scatterPiesBy( innerRadius + dr*oci, dataSeriesView, outerRadius/5.0 );

    }

  }

}