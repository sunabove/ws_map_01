// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Line.java

package javachart.chart;

import java.awt.*;
import java.util.Vector;

// Referenced classes of package javachart.chart:
//            Datum, Dataset, Globals, DisplayList, 
//            Gc, Plotarea, AxisInterface, Transform

public class Line
{

    public Line(Dataset adataset[], AxisInterface axisinterface, AxisInterface axisinterface1, Plotarea plotarea1)
    {
        doClip = false;
        useDisplayList = true;
        labelFormat = 1;
        labelsOn = false;
        labelPrecision = 2;
        datasets = adataset;
        xAxis = axisinterface;
        yAxis = axisinterface1;
        plotarea = plotarea1;
        globals = plotarea.globals;
    }

    public void setYAxis(AxisInterface axisinterface)
    {
        yAxis = axisinterface;
    }

    public AxisInterface getYAxis()
    {
        return yAxis;
    }

    public void setXAxis(AxisInterface axisinterface)
    {
        xAxis = axisinterface;
    }

    public AxisInterface getXAxis()
    {
        return xAxis;
    }

    public void setPlotarea(Plotarea plotarea1)
    {
        plotarea = plotarea1;
    }

    public Plotarea getPlotarea()
    {
        return plotarea;
    }

    public void setDatasets(Dataset adataset[])
    {
        datasets = adataset;
    }

    public Dataset[] getDatasets()
    {
        return datasets;
    }

    public void setScatterPlot(boolean flag)
    {
        scatterPlot = flag;
    }

    public boolean isScatterPlot()
    {
        return scatterPlot;
    }

    public boolean getUseDisplayList()
    {
        return useDisplayList;
    }

    public void setUseDisplayList(boolean flag)
    {
        useDisplayList = flag;
    }

    public boolean getClip()
    {
        return doClip;
    }

    public void setClip(boolean flag)
    {
        doClip = flag;
    }

    public boolean getLabelsOn()
    {
        return labelsOn;
    }

    public void setLabelsOn(boolean flag)
    {
        labelsOn = flag;
    }

    public int getLabelAngle()
    {
        return labelAngle;
    }

    public void setLabelAngle(int i)
    {
        labelAngle = i;
    }

    public int getLabelPrecision()
    {
        return labelPrecision;
    }

    public void setLabelPrecision(int i)
    {
        labelPrecision = i;
    }

    public int getLabelFormat()
    {
        return labelFormat;
    }

    public void setLabelFormat(int i)
    {
        if(i < 0 || i > 2)
        {
            return;
        } else
        {
            labelFormat = i;
            return;
        }
    }

    public void draw(Graphics g)
    {
        if(g == null)
        {
            return;
        } else
        {
            doLines(g, scatterPlot);
            return;
        }
    }

    protected synchronized void doLines(Graphics g, boolean flag)
    {
        numDataSets = datasetsInUse();
        xAxisStart = xAxis.getAxisStart();
        xAxisEnd = xAxis.getAxisEnd();
        yAxisStart = yAxis.getAxisStart();
        yAxisEnd = yAxis.getAxisEnd();
        if(doClip)
        {
            Point point = plotarea.transform.point(plotarea.llX, plotarea.llY);
            Point point1 = plotarea.transform.point(plotarea.urX, plotarea.urY);
            g.clipRect(point.x, globals.maxY - point1.y, point1.x - point.x, point1.y - point.y);
        }
        dataXfm = new Transform(xAxisStart, yAxisStart, xAxisEnd, yAxisEnd, plotarea.transform.point(plotarea.llX, plotarea.llY), plotarea.transform.point(plotarea.urX, plotarea.urY));
        for(int i = 0; i < numDataSets; i++)
        {
            if(flag)
            {
                doMarkers(g, numDataSets - 1 - i);
            } else
            {
                doLine(g, numDataSets - 1 - i);
                doMarkers(g, numDataSets - 1 - i);
            }
            if(labelsOn)
                doLabels(g);
        }

        if(doClip)
            g.clipRect(0, 0, globals.maxX, globals.maxY);
    }

    synchronized void drawSet(Graphics g, int i, boolean flag)
    {
        xAxisStart = xAxis.getAxisStart();
        xAxisEnd = xAxis.getAxisEnd();
        yAxisStart = yAxis.getAxisStart();
        yAxisEnd = yAxis.getAxisEnd();
        numDataSets = 1;
        if(doClip)
        {
            Point point = plotarea.transform.point(plotarea.llX, plotarea.llY);
            Point point1 = plotarea.transform.point(plotarea.urX, plotarea.urY);
            g.clipRect(point.x, globals.maxY - point1.y, point1.x - point.x, point1.y - point.y);
        }
        dataXfm = new Transform(xAxisStart, yAxisStart, xAxisEnd, yAxisEnd, plotarea.transform.point(plotarea.llX, plotarea.llY), plotarea.transform.point(plotarea.urX, plotarea.urY));
        if(flag)
        {
            doMarkers(g, i);
        } else
        {
            doLine(g, i);
            doMarkers(g, i);
        }
        if(labelsOn)
            doLabels(g);
        if(doClip)
            g.clipRect(0, 0, globals.maxX, globals.maxY);
    }

    private synchronized int datasetsInUse()
    {
        int i;
        for(i = 0; i < datasets.length; i++)
            if(datasets[i] == null)
                return i;

        return i;
    }

    protected void doLine(Graphics g, int i)
    {
        double ad[] = datasets[i].getXValues();
        double ad1[] = datasets[i].getYValues();
        if(!globals.threeD)
        {
            datasets[i].gc.drawPolyline(g, dataXfm.pointList(ad, ad1));
            if(useDisplayList && globals.useDisplayList)
            {
                globals.displayList.addPolyline(datasets[i], dataXfm.pointList(ad, ad1));
                globals.displayList.addPolyline(this, dataXfm.pointList(ad, ad1));
                return;
            }
        } else
        {
            int i1 = globals.xOffset / numDataSets;
            int j1 = globals.yOffset / numDataSets;
            int k = i1 * i;
            int l = j1 * i;
            Point apoint[] = new Point[4];
            for(int j = 0; j < ad.length - 1; j++)
            {
                apoint[0] = dataXfm.point(ad[j], ad1[j]);
                apoint[0].translate(k, l);
                apoint[1] = new Point(apoint[0].x + i1, apoint[0].y + j1);
                apoint[3] = dataXfm.point(ad[j + 1], ad1[j + 1]);
                apoint[3].translate(k, l);
                apoint[2] = new Point(apoint[3].x + i1, apoint[3].y + j1);
                datasets[i].gc.drawPolygon(g, apoint);
                datasets[i].gc.drawPolyline(g, apoint);
                if(useDisplayList && globals.useDisplayList)
                {
                    globals.displayList.addPolygon(datasets[i], apoint);
                    globals.displayList.addPolyline(this, apoint);
                }
            }

        }
    }

    protected void doMarkers(Graphics g, int i)
    {
        if(datasets[i].gc.image == null)
            return;
        double ad[] = datasets[i].getXValues();
        double ad1[] = datasets[i].getYValues();
        for(int j = 0; j < ad1.length; j++)
        {
            datasets[i].gc.drawImage(g, dataXfm.point(ad[j], ad1[j]));
            if(useDisplayList && globals.useDisplayList)
            {
                Point point1 = dataXfm.point(ad[j], ad1[j]);
                int k = datasets[i].gc.image.getWidth(null);
                int l = datasets[i].gc.image.getHeight(null);
                point1.translate(k / 2, l / 2);
                Point point = new Point(point1.x - k, point1.y - l);
                globals.displayList.addRectangle(datasets[i].getDataElementAt(j), point, point1);
                globals.displayList.addRectangle(datasets[i], point, point1);
                globals.displayList.addRectangle(this, point, point1);
            }
        }

    }

    protected void doLabels(Graphics g)
    {
        for(int i = 0; datasets[i] != null; i++)
        {
            for(int j = 0; j < datasets[i].data.size(); j++)
                doElementLabel(g, i, j);

        }

    }

    protected void doElementLabel(Graphics g, int i, int j)
    {
        Datum datum = datasets[i].getDataElementAt(j);
        double d = datum.x;
        double d1 = datum.y;
        String s;
        if(datum.label != null)
        {
            s = datum.label;
        } else
        {
            s = Double.toString(datum.y);
            int k;
            if((k = s.indexOf("e")) == -1)
            {
                s = Gc.formattedLabel(s, labelFormat, labelPrecision);
            } else
            {
                s = Gc.nonSciNumberStr(s, k);
                s = Gc.formattedLabel(s, labelFormat, labelPrecision);
            }
        }
        g.setFont(datasets[i].labelFont);
        g.setColor(datasets[i].labelColor);
        java.awt.FontMetrics fontmetrics = g.getFontMetrics();
        Point point = dataXfm.point(d, d1);
        datasets[i].gc.drawSmartString(g, point.x, point.y + 2, 2, labelAngle, fontmetrics, s);
    }

    AxisInterface yAxis;
    AxisInterface xAxis;
    Plotarea plotarea;
    boolean scatterPlot;
    boolean doClip;
    boolean useDisplayList;
    Globals globals;
    Dataset datasets[];
    int labelAngle;
    int labelFormat;
    boolean labelsOn;
    int labelPrecision;
    Transform dataXfm;
    double xAxisStart;
    double xAxisEnd;
    double yAxisStart;
    double yAxisEnd;
    protected int numDataSets;
}
