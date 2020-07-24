// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LabelAxis.java

package javachart.chart;

import java.awt.Point;
import java.util.Vector;

// Referenced classes of package javachart.chart:
//            Axis, Dataset, Datum, AxisInterface, 
//            Plotarea

public class LabelAxis extends Axis
    implements AxisInterface
{

    LabelAxis(Dataset adataset[], boolean flag, Plotarea plotarea)
    {
        super(adataset, flag, plotarea);
    }

    public synchronized void addLabel(String s)
    {
        if(labelList == null)
            labelList = new Vector();
        labelList.addElement(s);
    }

    public synchronized void deleteLabel(int i)
    {
        if(labelList == null)
        {
            return;
        } else
        {
            labelList.removeElementAt(i);
            return;
        }
    }

    public void addLabels(String as[])
    {
        replaceLabels(as);
    }

    public synchronized void replaceLabels(String as[])
    {
        labelList = new Vector();
        for(int i = 0; i < as.length; i++)
            labelList.addElement(as[i]);

    }

    public void scale()
    {
        labelScale();
    }

    private synchronized boolean labelScale()
    {
        int k = 0;
        int i = datasetsInUse();
        if(i == 0)
            return false;
        for(int j = 0; j < i; j++)
            if(k < datasets[j].data.size())
                k = datasets[j].data.size();

        if(barScaling)
        {
            axisStart = -1D;
            axisEnd = k;
            numLabels = k;
            numMajTicks = numLabels + 1;
        } else
        {
            axisStart = 0.0D;
            axisEnd = (double)k - 1.0D;
            numLabels = k - 1;
            numMajTicks = numLabels;
        }
        numGrids = numMajTicks;
        numMinTicks = numMajTicks * 2;
        return true;
    }

    protected String getLabel(double d, int i)
    {
        if(labelList != null && labelList.size() > i)
            return (String)labelList.elementAt(i);
        try
        {
            if(((Datum)datasets[0].data.elementAt(i)).getString() != null)
                return ((Datum)datasets[0].data.elementAt(i)).getString();
        }
        catch(ArrayIndexOutOfBoundsException _ex)
        {
            return String.valueOf(i);
        }
        return null;
    }

    protected int whereOnAxis(int i, int j)
    {
        if(j == 4 || j == 3 || j == 2)
            return super.whereOnAxis(i, j);
        if(side == 0 || side == 2)
        {
            float f = getIncrement(endPoint.x, startPoint.x, numMajTicks);
            if(barScaling)
                return startPoint.x + (int)(f * (float)(i + 1));
            else
                return startPoint.x + (int)(f * (float)i);
        }
        float f1 = getIncrement(endPoint.y, startPoint.y, numMajTicks);
        if(barScaling)
            return startPoint.y + (int)(f1 * (float)(i + 1));
        else
            return startPoint.y + (int)(f1 * (float)i);
    }

    Vector labelList;
}
