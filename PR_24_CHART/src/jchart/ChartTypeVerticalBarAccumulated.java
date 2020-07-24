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
import java.awt.geom.*;
import jcosmos.*;

public class ChartTypeVerticalBarAccumulated extends ChartType2D implements FeedbackChartType{

  public ChartTypeVerticalBarAccumulated() {
  }

    /**
     * set shape and shape style of data series view
     */
    public void setShapeAndStyle(DataSeriesView dataSeriesView) {

		Chart chart = dataSeriesView.getChart();
        PictureExtent pa = chart.getPictureExtent();
		ValueAxisOption vao = chart.getValueAxisOption();


        ChartData cd = chart.getChartData();  // cahrt data

        DataSeries [] ds = cd.getDataSeries(); // data series

        int dsLen = ds.length;

        double accuMin = cd.getMinValue();  // accumulated minimum value
        double accuMax = accuMin;  // accumulated maximum value

        int bvTypeNum = pa.getDataSeriesViewNumberOfChartType( getClass() ); // binded vertical graph type data series view number

        int oci = pa.getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews( dataSeriesView ); // occurrence order index

        Utility.debug( this, "bvTypeNum = " + bvTypeNum + ", occur order = " + oci );

        DataSeries [] referenceSeries = cd.getReferenceSeries();

        for( int i = 0, refLen = referenceSeries.length; i < refLen ; i ++ ) {

            DataElement [] refDes = referenceSeries[ i ].getDataElements(); // reference data elements

            double lmax = 0;

            for( int k = 0, len = refDes.length; k < len; k ++ ) {

                lmax += refDes[k].getValue();

            }

            accuMax = ( accuMax > lmax ) ? accuMax : lmax ;

        }

        calibrateAxisMinMaxValueAndUnit( vao, accuMin, accuMax );

        accuMin = vao.getMinScale();
        accuMax = vao.getMaxScale();

        Color color = getChartColor( oci ); // DataElementView color

        ShapeStyle style = new ShapeStyle( color, Color.black ); // DataElementView Shape Style

        Rectangle paArea = pa.getArea();

        int pax = paArea.x, pay = paArea.y, paw = paArea.width, pah = paArea.height;

        int baseY = pay + pah; // data element view left botton vertical location ( y coordinate )

        DataElementView devs [] = dataSeriesView.getDataElementViews();  // data element views that is contained in data series view

        int len = devs.length; // data element view number

        double dw = ( paw + 0.0 )/( len );

        for(int i = 0; i < len; i ++ ) {

            DataElementView dev = devs[ i ] ; // data element view

            dev.setShapeStyle( style );

            double currVal = dev.getDataElement().getValue();

            double baseVal = accuMin;

            DataElement [] refDes = referenceSeries[ i ].getDataElements(); // reference data elements

            for( int k = 0; k < oci; k ++ ) {

                baseVal += refDes[k].getValue();

            }

            double accuH = pah*( baseVal - accuMin )/( accuMax - accuMin );

            double currH = pah*( currVal - accuMin )/( accuMax - accuMin );

            double cx = pax + dw*(i + 0.5);

            double currW = dw/3.0;

            double currY = baseY - accuH - currH;

            Shape shape = new Rectangle2D.Double( cx - currW/2, currY, currW, currH );

            dev.setShape( shape );
        }

    }

    public PointedObject [] getValueAxisMainGridPoints(ValueAxis valueAxis) {

		ValueAxisOption vao = ( ValueAxisOption ) valueAxis.getAxisOption();
        Chart chart = valueAxis.getChart();  // chart
		ChartData cd = chart.getChartData();  // chart data

        double min = cd.getMinValue();
        double max = min;

        DataSeries [] referenceSeries = cd.getReferenceSeries();

        for( int i = 0, refLen = referenceSeries.length; i < refLen ; i ++ ) {

            DataElement [] refDes = referenceSeries[ i ].getDataElements(); // reference data elements

            double lmax = 0;

            for( int k = 0, len = refDes.length; k < len; k ++ ) {

                lmax += refDes[k].getValue();

            }

            max = ( max > lmax ) ? max : lmax ;

        }

        calibrateAxisMinMaxValueAndUnit( vao, min, max );

        min = vao.getMinScale();
        max = vao.getMaxScale();

        Rectangle area = valueAxis.getArea(); // area rectangle

        int ax = area.x, ay = area.y, aw = area.width, ah = area.height;

        int divNum = cd.getDivisionNumber();

        double dy = ah /(divNum + 1.0);   // vertical graphical gap

        double dv = (max - min)/(divNum + 1.0);   // vertical valude gap

        PointedObject [] points = new PointedObject[ divNum + 2 ];

        double x = ax, y, val;

        int len = points.length; // points num

        for( int i = 0; i < len ; i ++ ) {
			y = ay + i*dy;
            val = max - i*dv;
            points[i] = new PointedObject((int)(x), (int)(y), new Double( val ) );
        }

        return points;
  }

}