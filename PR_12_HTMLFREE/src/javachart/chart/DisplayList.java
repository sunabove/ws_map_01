// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DisplayList.java

package javachart.chart;

import java.awt.FontMetrics;
import java.awt.Point;
import java.util.Vector;

// Referenced classes of package javachart.chart:
//            Globals

public class DisplayList
{

    public DisplayList(Globals globals1)
    {
        globals = globals1;
        primitiveList = new Vector();
        objectList = new Vector();
    }

    protected void addLine(Object obj, Point point, Point point1)
    {
        Point point2 = new Point(point.x - 2, point.y);
        Point point3 = new Point(point1.x + 2, point1.y);
        addRectangle(obj, point2, point3);
    }

    protected synchronized void addRectangle(Object obj, Point point, Point point1)
    {
        Point apoint[] = new Point[4];
        apoint[0] = new Point(point.x, point.y);
        apoint[1] = new Point(point.x, point1.y);
        apoint[2] = new Point(point1.x, point1.y);
        apoint[3] = new Point(point1.x, point.y);
        primitiveList.addElement(apoint);
        objectList.addElement(obj);
    }

    protected synchronized void addTextString(Object obj, int i, int j, String s, FontMetrics fontmetrics)
    {
        int k = fontmetrics.stringWidth(s);
        int l = fontmetrics.getHeight();
        Point apoint[] = new Point[4];
        apoint[0] = new Point(i, j);
        apoint[1] = new Point(i, j + l);
        apoint[2] = new Point(i + k, j + l);
        apoint[3] = new Point(i + k, j);
        primitiveList.addElement(apoint);
        objectList.addElement(obj);
    }

    protected synchronized void addPolygon(Object obj, Point apoint[])
    {
        primitiveList.addElement(apoint);
        objectList.addElement(obj);
    }

    protected synchronized void addPolyline(Object obj, Point apoint[])
    {
        Point apoint1[] = new Point[apoint.length * 2];
        for(int i = 0; i < apoint.length; i++)
        {
            apoint1[i] = apoint[i];
            apoint1[i].translate(0, 2);
            apoint1[apoint1.length - i - 1] = new Point(apoint[i].x, apoint[i].y - 4);
        }

        primitiveList.addElement(apoint1);
        objectList.addElement(obj);
    }

    protected synchronized void addArc(Object obj, Point point, Point point1, int i, int j)
    {
        double d2 = 0.062831853071795868D;
        double d = ((double)i / 180D) * 3.1415926535897931D;
        double d1 = ((double)j / 180D) * 3.1415926535897931D;
        int k = (int)(d1 / d2);
        Point apoint[] = new Point[k + 1];
        apoint[k] = point;
        for(int j1 = 0; j1 < k; j1++)
        {
            int l = point.x + (int)(Math.cos(d) * (double)(point1.x / 2));
            int i1 = point.y + (int)(Math.sin(d) * (double)(point1.y / 2));
            apoint[j1] = new Point(l, i1);
            d += d2;
        }

        primitiveList.addElement(apoint);
        objectList.addElement(obj);
    }

    public synchronized boolean contains(Point point, Vector vector)
    {
        return traverseList(point, vector);
    }

    public synchronized void clear()
    {
        primitiveList = new Vector();
        objectList = new Vector();
    }

    private boolean traverseList(Point point, Vector vector)
    {
        boolean flag = false;
        Point point1 = new Point(point.x, globals.maxY - point.y);
        for(int i = 0; i < primitiveList.size(); i++)
        {
            Point apoint[] = (Point[])primitiveList.elementAt(i);
            if(inPolygon(point1, apoint))
            {
                vector.addElement(objectList.elementAt(i));
                flag = true;
            }
        }

        return flag;
    }

    private boolean inPolygon(Point point, Point apoint[])
    {
        int j = 0;
        int k = 0;
        int i = apoint.length;
        for(int l = 0; l < i; l++)
        {
            int i1 = l + 1;
            if(i1 == i)
                i1 = 0;
            int j1 = apoint[l].x - point.x;
            int k1 = apoint[l].y - point.y;
            int l1 = apoint[i1].x - point.x;
            int i2 = apoint[i1].y - point.y;
            long l2;
            if((j1 > 0 || l1 > 0) && (k1 > 0 || i2 > 0) && (j1 <= 0 || l1 <= 0) && (l2 = j1 * i2 - k1 * l1) != 0L && (l2 > 0L) == (j1 - l1 > 0))
            {
                j++;
                if(l2 < 0L)
                    k--;
                if(l2 > 0L)
                    k++;
            }
        }

        j &= 0x1;
        if(j == 0 && k != 0)
            j = 1;
        return j == 1;
    }

    Vector primitiveList;
    Vector objectList;
    Globals globals;
}
