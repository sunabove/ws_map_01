// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   RotateString.java

package javachart.chart;

import java.awt.*;
import java.awt.image.*;

public class RotateString
{

    public RotateString(Component component)
    {
        parent = component;
        c = null;
        f = null;
    }

    void setFont(Font font)
    {
        f = font;
    }

    void setColor(Color color)
    {
        c = color;
    }

    private Rectangle rotateRectangle(Rectangle rectangle, int i, int j, double d)
    {
        double d3;
        double d1 = d3 = (double)(rectangle.x - i) * Math.cos(d) - (double)(rectangle.y - j) * Math.sin(d);
        double d4;
        double d2 = d4 = (double)(rectangle.x - i) * Math.sin(d) + (double)(rectangle.y - j) * Math.cos(d);
        double d5 = (double)((rectangle.x + rectangle.width) - 1 - i) * Math.cos(d) - (double)(rectangle.y - j) * Math.sin(d);
        double d6 = (double)((rectangle.x + rectangle.width) - 1 - i) * Math.sin(d) + (double)(rectangle.y - j) * Math.cos(d);
        if(d5 < d1)
            d1 = d5;
        if(d5 > d3)
            d3 = d5;
        if(d6 < d2)
            d2 = d6;
        if(d6 > d4)
            d4 = d6;
        d5 = (double)(rectangle.x - i) * Math.cos(d) - (double)((rectangle.y + rectangle.height) - 1 - j) * Math.sin(d);
        d6 = (double)(rectangle.x - i) * Math.sin(d) + (double)((rectangle.y + rectangle.height) - 1 - j) * Math.cos(d);
        if(d5 < d1)
            d1 = d5;
        if(d5 > d3)
            d3 = d5;
        if(d6 < d2)
            d2 = d6;
        if(d6 > d4)
            d4 = d6;
        d5 = (double)((rectangle.x + rectangle.width) - 1 - i) * Math.cos(d) - (double)((rectangle.y + rectangle.height) - 1 - j) * Math.sin(d);
        d6 = (double)((rectangle.x + rectangle.width) - 1 - i) * Math.sin(d) + (double)((rectangle.y + rectangle.height) - 1 - j) * Math.cos(d);
        if(d5 < d1)
            d1 = d5;
        if(d5 > d3)
            d3 = d5;
        if(d6 < d2)
            d2 = d6;
        if(d6 > d4)
            d4 = d6;
        rectangle.x = (int)d1 + i;
        rectangle.y = (int)d2 + j;
        rectangle.width = (int)((d3 - d1) + 1.0D);
        rectangle.height = (int)((d4 - d2) + 1.0D);
        return rectangle;
    }

    private Rectangle getExtent(String s, int i, int j, int k, int l, int i1, Font font)
    {
        Rectangle rectangle = new Rectangle();
        FontMetrics fontmetrics = parent.getFontMetrics(font);
        int j1 = fontmetrics.getMaxDescent();
        int k1 = fontmetrics.getMaxAscent();
        rectangle.x = i;
        rectangle.y = (j - k1) + j1;
        rectangle.width = fontmetrics.stringWidth(s);
        rectangle.height = k1;
        if(i1 != 0)
            rectangle = rotateRectangle(rectangle, k, l, (-3.1415926535897931D * (double)i1) / 180D);
        return rectangle;
    }

    private Rectangle getExtent(String s, int i, int j, int k, int l, int i1, FontMetrics fontmetrics)
    {
        Rectangle rectangle = new Rectangle();
        int j1 = fontmetrics.getMaxDescent();
        int k1 = fontmetrics.getMaxAscent();
        rectangle.x = i;
        rectangle.y = (j - k1) + j1;
        rectangle.width = fontmetrics.stringWidth(s);
        rectangle.height = k1;
        if(i1 != 0)
            rectangle = rotateRectangle(rectangle, k, l, (-3.1415926535897931D * (double)i1) / 180D);
        return rectangle;
    }

    protected Rectangle getExtent(String s, int i, int j, int k, FontMetrics fontmetrics)
    {
        return getExtent(s, i, j, i, j, k, fontmetrics);
    }

    private Rectangle getExtent(String s, int i, int j, int k, Font font)
    {
        return getExtent(s, i, j, i, j, k, font);
    }

    private Rectangle getExtent(String s, int i, int j, int k, int l, int i1)
    {
        return getExtent(s, i, j, k, l, i1, f);
    }

    private Rectangle getExtent(String s, int i, int j, int k)
    {
        return getExtent(s, i, j, i, j, k, f);
    }

    protected void drawString(String s, int i, int j, int k, Image image)
    {
        drawString(s, i, j, i, j, k, image);
    }

    private void drawString(String s, int i, int j, Image image)
    {
        drawString(s, i, j, i, j, 0, image);
    }

    private void drawString(String s, int i, int j, int k, int l, int i1, Image image)
    {
        if(s != null)
        {
            double d = (-3.1415926535897931D * (double)i1) / 180D;
            Graphics g1 = image.getGraphics();
            if(f == null)
                f = g1.getFont();
            if(c == null)
                c = g1.getColor();
            if(c == null)
                c = Color.black;
            Rectangle rectangle = getExtent(s, i, j, 0, f);
            int j2 = rectangle.x;
            int k2 = rectangle.y;
            int l2 = rectangle.width;
            int i3 = rectangle.height;
            int j1;
            int k1;
            int l1;
            int i2;
            if(i1 == 0)
            {
                j1 = j2;
                k1 = k2;
                l1 = l2;
                i2 = i3;
            } else
            {
                rectangle = rotateRectangle(rectangle, k, l, d);
                j1 = rectangle.x;
                k1 = rectangle.y;
                l1 = rectangle.width;
                i2 = rectangle.height;
            }
            tmp = parent.createImage(l2, i3);
            Graphics g = tmp.getGraphics();
            int j3;
            int k3 = j3 = c.getRGB();
            if(j3 == Color.white.getRGB())
            {
                g.setColor(Color.black);
                j3 = Color.black.getRGB();
            } else
            {
                g.setColor(c);
            }
            g.setFont(f);
            g.drawString(s, i - j2, j - k2);
            int ai[] = new int[l2 * i3];
            PixelGrabber pixelgrabber = new PixelGrabber(tmp, 0, 0, l2, i3, ai, 0, l2);
            try
            {
                pixelgrabber.grabPixels();
            }
            catch(InterruptedException _ex) { }
            int ai1[] = new int[l1 * i2];
            int l3 = k1;
            int i4 = 0;
            for(; l3 < i2 + k1; l3++)
            {
                for(int i5 = j1; i5 < l1 + j1; i5++)
                {
                    int k4 = (int)(((0.5D + (double)(i5 - k) * Math.cos(-d)) - (double)(l3 - l) * Math.sin(-d)) + (double)k);
                    int l4 = (int)(0.5D + (double)(i5 - k) * Math.sin(-d) + (double)(l3 - l) * Math.cos(-d) + (double)l);
                    k4 -= j2;
                    l4 -= k2;
                    if(k4 >= 0 && l4 >= 0 && k4 < l2 && l4 < i3)
                    {
                        int j4 = l4 * l2 + k4;
                        if(ai[j4] == j3)
                            ai1[i4] = k3;
                    }
                    i4++;
                }

            }

            tmp = parent.createImage(new MemoryImageSource(l1, i2, ColorModel.getRGBdefault(), ai1, 0, l1));
            g1.drawImage(tmp, j1, k1, null);
        }
    }

    private Component parent;
    private Color c;
    private Font f;
    private transient Image tmp;
}
