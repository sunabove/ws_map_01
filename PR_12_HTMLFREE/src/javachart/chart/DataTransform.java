// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DataTransform.java

package javachart.chart;

import java.awt.Point;

// Referenced classes of package javachart.chart:
//            AxisInterface, Datum, Plotarea, Globals, 
//            Chart, Transform

public class DataTransform
{

    public DataTransform(Chart chart)
    {
        myChart = chart;
        buildTransform();
    }

    public void resize()
    {
        buildTransform();
    }

    public Point datumToPoint(Datum datum)
    {
        return dataXfm.point(datum.x, datum.y);
    }

    public Datum pointToDatum(Point point)
    {
        return new Datum(dataXfm.xValue(point.x), dataXfm.yValue(myChart.globals.maxY - point.y), myChart.globals);
    }

    private void buildTransform()
    {
        double d = myChart.getXAxis().getAxisStart();
        double d1 = myChart.getXAxis().getAxisEnd();
        double d2 = myChart.getYAxis().getAxisStart();
        double d3 = myChart.getYAxis().getAxisEnd();
        dataXfm = new Transform(d, d2, d1, d3, myChart.plotarea.transform.point(myChart.plotarea.llX, myChart.plotarea.llY), myChart.plotarea.transform.point(myChart.plotarea.urX, myChart.plotarea.urY));
    }

    private Transform dataXfm;
    private Chart myChart;
}
