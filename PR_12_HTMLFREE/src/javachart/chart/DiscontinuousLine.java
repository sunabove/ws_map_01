// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DiscontinuousLine.java

package javachart.chart;

import java.awt.*;
import java.util.Vector;

// Referenced classes of package javachart.chart:
//            Line, Globals, Dataset, Datum, 
//            Gc, DisplayList, Transform, AxisInterface, 
//            Plotarea

public class DiscontinuousLine extends Line
{

    public DiscontinuousLine(Dataset adataset[], AxisInterface axisinterface, AxisInterface axisinterface1, Plotarea plotarea)
    {
        super(adataset, axisinterface, axisinterface1, plotarea);
        hasDiscontinuities = false;
    }

    protected void doLine(Graphics g, int i)
    {
        hasDiscontinuities = false;
        double ad[] = datasets[i].getY2Values();
        for(int j = 0; j < ad.length; j++)
            if(ad[j] == -1D)
                hasDiscontinuities = true;

        if(!hasDiscontinuities)
        {
            super.doLine(g, i);
            return;
        } else
        {
            doDiscontinuousLine(g, i, ad);
            return;
        }
    }

    private void doDiscontinuousLine(Graphics g, int i, double ad[])
    {
        double ad2[] = datasets[i].getXValues();
        double ad4[] = datasets[i].getYValues();
        Vector vector = new Vector();
        for(int j = 0; j < ad2.length; j++)
        {
            while(j < ad2.length && ad[j] != -1D) 
            {
                vector.addElement(datasets[i].getDataElementAt(j));
                j++;
            }
            double ad1[] = new double[vector.size()];
            double ad3[] = new double[vector.size()];
            for(int k = 0; k < ad1.length; k++)
            {
                Datum datum = (Datum)vector.elementAt(k);
                ad1[k] = datum.x;
                ad3[k] = datum.y;
            }

            doLineSegment(ad1, ad3, i, g);
            doMarkerSegment(ad1, ad3, i, vector, g);
            vector.removeAllElements();
        }

    }

    private void doLineSegment(double ad[], double ad1[], int i, Graphics g)
    {
        if(!globals.threeD)
        {
            datasets[i].gc.drawPolyline(g, dataXfm.pointList(ad, ad1));
            return;
        }
        int l = globals.xOffset / numDataSets;
        int i1 = globals.yOffset / numDataSets;
        int j = l * i;
        int k = i1 * i;
        Point apoint[] = new Point[4];
        for(int j1 = 0; j1 < ad.length - 1; j1++)
        {
            apoint[0] = dataXfm.point(ad[j1], ad1[j1]);
            apoint[0].translate(j, k);
            apoint[1] = new Point(apoint[0].x + l, apoint[0].y + i1);
            apoint[3] = dataXfm.point(ad[j1 + 1], ad1[j1 + 1]);
            apoint[3].translate(j, k);
            apoint[2] = new Point(apoint[3].x + l, apoint[3].y + i1);
            datasets[i].gc.drawPolygon(g, apoint);
            datasets[i].gc.drawPolyline(g, apoint);
        }

    }

    private void doMarkerSegment(double ad[], double ad1[], int i, Vector vector, Graphics g)
    {
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
                globals.displayList.addRectangle(vector.elementAt(j), point, point1);
                globals.displayList.addRectangle(datasets[i], point, point1);
                globals.displayList.addRectangle(this, point, point1);
            }
        }

    }

    protected void doMarkers(Graphics g, int i)
    {
        if(hasDiscontinuities)
        {
            return;
        } else
        {
            super.doMarkers(g, i);
            return;
        }
    }

    protected void doElementLabel(Graphics g, int i, int j)
    {
        Datum datum = datasets[i].getDataElementAt(j);
        if(datum.y2 == -1D)
        {
            return;
        } else
        {
            super.doElementLabel(g, i, j);
            return;
        }
    }

    static final double signalVal = -1D;
    boolean hasDiscontinuities;
}
