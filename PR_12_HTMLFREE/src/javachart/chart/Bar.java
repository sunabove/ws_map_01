// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Bar.java

package javachart.chart;

import java.awt.*;
import java.util.Vector;

// Referenced classes of package javachart.chart:
//            Gc, Dataset, DisplayList, Datum, 
//            Transform, AxisInterface, Plotarea, Globals

public class Bar
{

    public Bar(Dataset adataset[], AxisInterface axisinterface, AxisInterface axisinterface1, Plotarea plotarea1)
    {
        labelsOn = false;
        clusterWidth = 0.80000000000000004D;
        doClip = false;
        useDisplayList = true;
        labelFormat = 1;
        labelPrecision = 2;
        datasets = adataset;
        xAxis = axisinterface;
        yAxis = axisinterface1;
        plotarea = plotarea1;
        globals = plotarea.globals;
    }

    public boolean getDoClip()
    {
        return doClip;
    }

    public void setDoClip(boolean flag)
    {
        doClip = flag;
    }

    public synchronized void draw(Graphics g)
    {
        if(g == null)
        {
            return;
        } else
        {
            buildDataXfm();
            doBars(g, false);
            return;
        }
    }

    public synchronized void drawInd(Graphics g)
    {
        if(g == null)
        {
            return;
        } else
        {
            buildDataXfm();
            doBars(g, true);
            return;
        }
    }

    public boolean getUseDisplayList()
    {
        return useDisplayList;
    }

    public void setUseDisplayList(boolean flag)
    {
        useDisplayList = flag;
    }

    public boolean getLabelsOn()
    {
        return labelsOn;
    }

    public void setLabelsOn(boolean flag)
    {
        labelsOn = flag;
    }

    public double getClusterWidth()
    {
        return clusterWidth;
    }

    public void setClusterWidth(double d)
    {
        clusterWidth = d;
    }

    public double getBaseline()
    {
        return baseline;
    }

    public void setBaseline(double d)
    {
        baseline = d;
    }

    public int getLabelAngle()
    {
        return labelAngle;
    }

    public void setLabelAngle(int i)
    {
        labelAngle = i;
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

    public int getLabelFormat()
    {
        return labelFormat;
    }

    public int getLabelPrecision()
    {
        return labelPrecision;
    }

    public void setLabelPrecision(int i)
    {
        labelPrecision = i;
    }

    protected void buildDataXfm()
    {
        xAxisStart = xAxis.getAxisStart();
        xAxisEnd = xAxis.getAxisEnd();
        yAxisStart = yAxis.getAxisStart();
        yAxisEnd = yAxis.getAxisEnd();
        dataXfm = new Transform(xAxisStart, yAxisStart, xAxisEnd, yAxisEnd, plotarea.transform.point(plotarea.llX, plotarea.llY), plotarea.transform.point(plotarea.urX, plotarea.urY));
    }

    protected void doBars(Graphics g, boolean flag)
    {
        int i2 = 0;
        int l1 = datasetsInUse();
        for(int i = 0; i < l1; i++)
            if(i2 < datasets[i].data.size())
                i2 = datasets[i].data.size();

        double d = clusterWidth / (double)l1;
        if(!globals.threeD)
        {
            for(int j = 0; j < l1; j++)
            {
                for(int i1 = 0; i1 < datasets[j].data.size(); i1++)
                    doBar(g, d, j, i1, flag);

            }

        } else
        {
            for(int j1 = 0; j1 < i2; j1++)
            {
                for(int k = 0; k < l1; k++)
                    doDBar(g, d, k, j1, flag);

            }

        }
        if(labelsOn)
        {
            for(int l = 0; l < l1; l++)
            {
                for(int k1 = 0; k1 < datasets[l].data.size(); k1++)
                    doBarLabel(g, d, l, k1);

            }

        }
    }

    protected synchronized void drawSet(Graphics g, int i, boolean flag)
    {
        if(!globals.threeD)
        {
            for(int j = 0; j < datasets[i].data.size(); j++)
                doBar(g, clusterWidth, i, j, flag);

        } else
        {
            for(int k = 0; k < datasets[i].data.size(); k++)
                doDBar(g, clusterWidth, i, k, flag);

        }
        if(labelsOn)
        {
            for(int l = 0; l < datasets[i].data.size(); l++)
                doBarLabel(g, clusterWidth, i, l);

        }
    }

    protected int datasetsInUse()
    {
        int i;
        for(i = 0; i < datasets.length; i++)
            if(datasets[i] == null)
                return i;

        return i;
    }

    protected void doBar(Graphics g, double d, int i, int j, boolean flag)
    {
        double d5 = 0.5D * clusterWidth;
        double d1 = ((double)j - d5) + d * (double)i;
        double d3 = d1 + d;
        double d4;
        try
        {
            d4 = datasets[i].getDataElementAt(j).y;
        }
        catch(ArrayIndexOutOfBoundsException _ex)
        {
            return;
        }
        double d2 = baseline;
        if(!flag)
            datasets[i].gc.fillRect(g, dataXfm.point(d1, d2), dataXfm.point(d3, d4));
        else
            datasets[i].getDataElementAt(j).gc.fillRect(g, dataXfm.point(d1, d2), dataXfm.point(d3, d4));
        if(useDisplayList && globals.useDisplayList)
        {
            if(flag)
            {
                globals.displayList.addRectangle(datasets[i].getDataElementAt(j), dataXfm.point(d1, d2), dataXfm.point(d3, d4));
                globals.displayList.addRectangle(datasets[i], dataXfm.point(d1, d2), dataXfm.point(d3, d4));
            } else
            {
                globals.displayList.addRectangle(datasets[i], dataXfm.point(d1, d2), dataXfm.point(d3, d4));
                globals.displayList.addRectangle(datasets[i].getDataElementAt(j), dataXfm.point(d1, d2), dataXfm.point(d3, d4));
            }
            globals.displayList.addRectangle(this, dataXfm.point(d1, d2), dataXfm.point(d3, d4));
        }
    }

    protected void doBarLabel(Graphics g, double d, int i, int j)
    {
        double d4 = 0.5D * clusterWidth;
        double d1 = ((double)j - d4) + d * (double)i;
        double d2 = d1 + d;
        double d3 = datasets[i].getDataElementAt(j).y;
        String s;
        if(datasets[i].getDataElementAt(j).label != null)
        {
            s = datasets[i].getDataElementAt(j).label;
        } else
        {
            s = Double.toString(datasets[i].getDataElementAt(j).y);
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
        Point point = dataXfm.point(d1 + (d2 - d1) / 2D, d3);
        datasets[i].gc.drawSmartString(g, point.x, point.y + 2, 2, labelAngle, fontmetrics, s);
    }

    protected void doDBar(Graphics g, double d, int i, int j, boolean flag)
    {
        double d5 = 0.5D * clusterWidth;
        Point apoint[] = new Point[4];
        double d1 = ((double)j - d5) + d * (double)i;
        double d3 = d1 + d;
        double d4 = datasets[i].getDataElementAt(j).y;
        double d2 = baseline;
        apoint[0] = dataXfm.point(d1, d2);
        apoint[1] = dataXfm.point(d3, d4);
        Gc gc;
        try
        {
            if(!flag)
                gc = datasets[i].gc;
            else
                gc = datasets[i].getDataElementAt(j).gc;
        }
        catch(ArrayIndexOutOfBoundsException _ex)
        {
            gc = datasets[i].gc;
        }
        gc.fillRect(g, apoint[0], apoint[1]);
        if(useDisplayList && globals.useDisplayList)
        {
            if(flag)
            {
                globals.displayList.addRectangle(datasets[i].getDataElementAt(j), apoint[0], apoint[1]);
                globals.displayList.addRectangle(datasets[i], apoint[0], apoint[1]);
            } else
            {
                globals.displayList.addRectangle(datasets[i], apoint[0], apoint[1]);
                globals.displayList.addRectangle(datasets[i].getDataElementAt(j), apoint[0], apoint[1]);
            }
            globals.displayList.addRectangle(this, dataXfm.point(d1, d2), dataXfm.point(d3, d4));
        }
        Color color = gc.fillColor;
        gc.fillColor = color.darker();
        apoint[0] = dataXfm.point(d3, d2);
        apoint[2] = new Point(apoint[1].x + globals.xOffset, apoint[1].y + globals.yOffset);
        apoint[3] = new Point(apoint[0].x + globals.xOffset, apoint[0].y + globals.yOffset);
        gc.drawPolygon(g, apoint);
        if(d4 > d2)
        {
            apoint[0] = dataXfm.point(d1, d4);
            apoint[3].x = apoint[0].x + globals.xOffset;
            apoint[3].y = apoint[0].y + globals.yOffset;
            gc.drawPolygon(g, apoint);
        } else
        {
            apoint[0] = dataXfm.point(d1, d2);
            apoint[1] = dataXfm.point(d3, d2);
            apoint[2].x = apoint[1].x + globals.xOffset;
            apoint[2].y = apoint[1].y + globals.yOffset;
            apoint[3].x = apoint[0].x + globals.xOffset;
            apoint[3].y = apoint[0].y + globals.yOffset;
            gc.drawPolygon(g, apoint);
        }
        gc.fillColor = color;
    }

    boolean labelsOn;
    double clusterWidth;
    double baseline;
    boolean doClip;
    boolean useDisplayList;
    Globals globals;
    int labelAngle;
    int labelFormat;
    int labelPrecision;
    AxisInterface yAxis;
    AxisInterface xAxis;
    Plotarea plotarea;
    Dataset datasets[];
    double xAxisStart;
    double xAxisEnd;
    double yAxisStart;
    double yAxisEnd;
    protected Transform dataXfm;
}
