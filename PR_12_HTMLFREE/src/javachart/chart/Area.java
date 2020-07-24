// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Area.java

package javachart.chart;

import java.awt.*;
import java.util.Vector;

// Referenced classes of package javachart.chart:
//            Dataset, Globals, Transform, DisplayList, 
//            Gc, Plotarea, AxisInterface, Datum

public class Area
{

    public Area(Dataset adataset[], AxisInterface axisinterface, AxisInterface axisinterface1, Plotarea plotarea1)
    {
        doClip = false;
        useDisplayList = true;
        datasets = adataset;
        xAxis = axisinterface;
        yAxis = axisinterface1;
        plotarea = plotarea1;
        globals = plotarea.globals;
    }

    public void setDoClip(boolean flag)
    {
        doClip = flag;
    }

    public boolean getDoClip()
    {
        return doClip;
    }

    public boolean getUseDisplayList()
    {
        return useDisplayList;
    }

    public void setUseDisplayList(boolean flag)
    {
        useDisplayList = flag;
    }

    public void draw(Graphics g)
    {
        if(g == null)
        {
            return;
        } else
        {
            doAreas(g);
            return;
        }
    }

    private synchronized void doAreas(Graphics g)
    {
        numDataSets = datasetsInUse();
        xAxisStart = xAxis.getAxisStart();
        xAxisEnd = xAxis.getAxisEnd();
        yAxisStart = yAxis.getAxisStart();
        yAxisEnd = yAxis.getAxisEnd();
        dataXfm = new Transform(xAxisStart, yAxisStart, xAxisEnd, yAxisEnd, plotarea.transform.point(plotarea.llX, plotarea.llY), plotarea.transform.point(plotarea.urX, plotarea.urY));
        for(int i = 0; i < numDataSets; i++)
            doArea(g, numDataSets - i - 1);

    }

    private int datasetsInUse()
    {
        int i;
        for(i = 0; i < datasets.length; i++)
            if(datasets[i] == null)
                return i;

        return i;
    }

    protected synchronized double getBaseY(int i, int j)
    {
        double d = 0.0D;
        for(int k = 0; k < i; k++)
            d += datasets[k].getDataElementAt(j).y;

        return d;
    }

    private synchronized void doArea(Graphics g, int i)
    {
        double ad[] = datasets[i].getXValues();
        double ad1[] = datasets[i].getYValues();
        double ad3[] = new double[ad1.length + 2];
        double ad2[] = new double[ad.length + 2];
        int j;
        for(j = 0; j < ad1.length; j++)
        {
            ad3[j] = ad1[j] + getBaseY(i, j);
            ad2[j] = ad[j];
        }

        ad2[j] = ad2[j - 1];
        ad2[j + 1] = ad2[0];
        ad3[j] = yAxisStart;
        ad3[j + 1] = yAxisStart;
        if(globals.threeD)
        {
            if(i == numDataSets - 1)
                doTop(g, i, ad2, ad3);
            doSide(g, i, ad2[ad2.length - 3], ad3[ad3.length - 3]);
        }
        datasets[i].gc.drawPolygon(g, dataXfm.pointList(ad2, ad3));
        if(useDisplayList && globals.useDisplayList)
            globals.displayList.addPolygon(datasets[i], dataXfm.pointList(ad2, ad3));
    }

    private synchronized void doTop(Graphics g, int i, double ad[], double ad1[])
    {
        int k = globals.xOffset;
        int l = globals.yOffset;
        Point apoint[] = new Point[4];
        Color color = datasets[i].gc.fillColor;
        datasets[i].gc.fillColor = color.darker();
        for(int j = 0; j < ad.length - 3; j++)
        {
            apoint[0] = dataXfm.point(ad[j], ad1[j]);
            apoint[1] = new Point(apoint[0].x + k, apoint[0].y + l);
            apoint[3] = dataXfm.point(ad[j + 1], ad1[j + 1]);
            apoint[2] = new Point(apoint[3].x + k, apoint[3].y + l);
            datasets[i].gc.drawPolygon(g, apoint);
            datasets[i].gc.drawPolyline(g, apoint);
        }

        datasets[i].gc.fillColor = color;
    }

    private synchronized void doSide(Graphics g, int i, double d, double d1)
    {
        Point apoint[] = new Point[4];
        Color color = datasets[i].gc.fillColor;
        datasets[i].gc.fillColor = color.darker();
        apoint[0] = dataXfm.point(d, d1);
        double d2 = d;
        double d3 = getBaseY(i, datasets[i].data.size() - 1);
        d3 = yAxis.getAxisStart() <= d3 ? d3 : yAxis.getAxisStart();
        apoint[1] = dataXfm.point(d2, d3);
        apoint[2] = new Point(apoint[1].x + globals.xOffset, apoint[1].y + globals.yOffset);
        apoint[3] = new Point(apoint[0].x + globals.xOffset, apoint[0].y + globals.yOffset);
        datasets[i].gc.drawPolygon(g, apoint);
        datasets[i].gc.fillColor = color;
    }

    private AxisInterface yAxis;
    private AxisInterface xAxis;
    Plotarea plotarea;
    Dataset datasets[];
    double xAxisStart;
    double xAxisEnd;
    double yAxisStart;
    double yAxisEnd;
    boolean doClip;
    Transform dataXfm;
    boolean useDisplayList;
    protected Globals globals;
    private int numDataSets;
}
