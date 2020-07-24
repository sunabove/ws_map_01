// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Transform.java

package javachart.chart;

import java.awt.Point;

public class Transform
{

    public Transform(double d, double d1, double d2, double d3, int i, int j, int k, int l)
    {
        scaleX = (double)(k - i) / (d2 - d);
        scaleY = (double)(l - j) / (d3 - d1);
        shiftX = (double)i - scaleX * d;
        shiftY = (double)j - scaleY * d1;
    }

    public Transform(double d, double d1, double d2, double d3, Point point1, Point point2)
    {
        scaleX = (double)(point2.x - point1.x) / (d2 - d);
        scaleY = (double)(point2.y - point1.y) / (d3 - d1);
        shiftX = (double)point1.x - scaleX * d;
        shiftY = (double)point1.y - scaleY * d1;
    }

    public Point point(double d, double d1)
    {
        int i = (int)(d * scaleX + shiftX);
        int j = (int)(d1 * scaleY + shiftY);
        return new Point(i, j);
    }

    public Point[] pointList(double ad[], double ad1[])
    {
        Point apoint[] = new Point[ad.length];
        for(int i = 0; i < ad.length; i++)
            apoint[i] = point(ad[i], ad1[i]);

        return apoint;
    }

    public double xValue(int i)
    {
        double d = i;
        d = (d - shiftX) / scaleX;
        return d;
    }

    public double yValue(int i)
    {
        double d = i;
        d = (d - shiftY) / scaleY;
        return d;
    }

    public String toString()
    {
        return getClass().getName() + "[" + " shiftX " + shiftX + " shiftY " + shiftY + " scaleX " + scaleX + " scaleY " + scaleY + " ]";
    }

    private double scaleX;
    private double scaleY;
    private double shiftX;
    private double shiftY;
}
