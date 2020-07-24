// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Gc.java

package javachart.chart;

import java.awt.*;

// Referenced classes of package javachart.chart:
//            RotateString, Globals

public class Gc
{

    public Gc(Globals globals1)
    {
        markerStyle = 1;
        markerSize = 1;
        lineStyle = 1;
        lineWidth = 1;
        globals = globals1;
        fillColor = Color.black;
        markerColor = Color.black;
        lineColor = Color.black;
    }

    Gc(boolean flag, Globals globals1)
    {
        markerStyle = 1;
        markerSize = 1;
        lineStyle = 1;
        lineWidth = 1;
        globals = globals1;
        if(!flag)
            return;
        individualCount++;
        switch(individualCount)
        {
        case 0: // '\0'
            assignColors(Color.blue);
            return;

        case 1: // '\001'
            assignColors(Color.red);
            return;

        case 2: // '\002'
            assignColors(Color.green);
            return;

        case 3: // '\003'
            assignColors(Color.cyan);
            return;

        case 4: // '\004'
            assignColors(Color.orange);
            return;

        case 5: // '\005'
            assignColors(Color.pink);
            return;

        case 6: // '\006'
            assignColors(Color.yellow);
            return;

        case 7: // '\007'
            assignColors(Color.magenta);
            return;

        case 8: // '\b'
            assignColors(Color.lightGray);
            return;

        case 9: // '\t'
            assignColors(Color.darkGray);
            return;

        case 10: // '\n'
            assignColors(Color.blue.darker());
            return;

        case 11: // '\013'
            assignColors(Color.red.darker());
            return;

        case 12: // '\f'
            assignColors(Color.green.darker());
            return;

        case 13: // '\r'
            assignColors(Color.cyan.darker());
            return;

        case 14: // '\016'
            assignColors(Color.orange.darker());
            return;

        case 15: // '\017'
            assignColors(Color.pink.darker());
            return;

        case 16: // '\020'
            assignColors(Color.yellow.darker());
            return;

        case 17: // '\021'
            assignColors(Color.magenta.darker());
            return;

        case 18: // '\022'
            assignColors(Color.lightGray.darker());
            return;

        case 19: // '\023'
            assignColors(Color.darkGray.darker());
            individualCount = 0;
            return;
        }
        assignColors(randomColor());
    }

    Gc(int i, Globals globals1)
    {
        markerStyle = 1;
        markerSize = 1;
        lineStyle = 1;
        lineWidth = 1;
        globals = globals1;
        switch(i)
        {
        case 0: // '\0'
            assignColors(Color.blue);
            return;

        case 1: // '\001'
            assignColors(Color.red);
            return;

        case 2: // '\002'
            assignColors(Color.green);
            return;

        case 3: // '\003'
            assignColors(Color.cyan);
            return;

        case 4: // '\004'
            assignColors(Color.orange);
            return;

        case 5: // '\005'
            assignColors(Color.pink);
            return;

        case 6: // '\006'
            assignColors(Color.yellow);
            return;

        case 7: // '\007'
            assignColors(Color.magenta);
            return;

        case 8: // '\b'
            assignColors(Color.lightGray);
            return;

        case 9: // '\t'
            assignColors(Color.darkGray);
            return;

        case 10: // '\n'
            assignColors(Color.blue.darker());
            return;

        case 11: // '\013'
            assignColors(Color.red.darker());
            return;

        case 12: // '\f'
            assignColors(Color.green.darker());
            return;

        case 13: // '\r'
            assignColors(Color.cyan.darker());
            return;

        case 14: // '\016'
            assignColors(Color.orange.darker());
            return;

        case 15: // '\017'
            assignColors(Color.pink.darker());
            return;

        case 16: // '\020'
            assignColors(Color.yellow.darker());
            return;

        case 17: // '\021'
            assignColors(Color.magenta.darker());
            return;

        case 18: // '\022'
            assignColors(Color.lightGray.darker());
            return;

        case 19: // '\023'
            assignColors(Color.darkGray.darker());
            return;
        }
        assignColors(randomColor());
    }

    Gc(Color color, Globals globals1)
    {
        markerStyle = 1;
        markerSize = 1;
        lineStyle = 1;
        lineWidth = 1;
        globals = globals1;
        assignColors(color);
    }

    private void assignColors(Color color)
    {
        markerColor = color;
        fillColor = color;
        lineColor = color;
    }

    private Color randomColor()
    {
        float f = (float)Math.random();
        float f1 = (float)Math.random();
        float f2 = (float)Math.random();
        return new Color(f, f1, f2);
    }

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image1)
    {
        image = image1;
    }

    public Color getMarkerColor()
    {
        return markerColor;
    }

    public void setMarkerColor(Color color)
    {
        markerColor = color;
    }

    public int getMarkerStyle()
    {
        return markerStyle;
    }

    public void setMarkerStyle(int i)
    {
        markerStyle = i;
    }

    public int getMarkerSize()
    {
        return markerSize;
    }

    public void setMarkerSize(int i)
    {
        markerSize = i;
    }

    public Color getFillColor()
    {
        return fillColor;
    }

    public void setFillColor(Color color)
    {
        fillColor = color;
    }

    public Color getLineColor()
    {
        return lineColor;
    }

    public void setLineColor(Color color)
    {
        lineColor = color;
    }

    public int getLineStyle()
    {
        return lineStyle;
    }

    public void setLineStyle(int i)
    {
        lineStyle = i;
    }

    public int getLineWidth()
    {
        return lineWidth;
    }

    public void setLineWidth(int i)
    {
        lineWidth = i;
    }

    void fillRect(Graphics g, Point point, Point point1)
    {
        int i;
        int j;
        if(point.x > point1.x)
        {
            i = point1.x;
            j = point.x;
        } else
        {
            i = point.x;
            j = point1.x;
        }
        g.setColor(fillColor);
        if(point1.y > point.y)
        {
            g.fillRect(i, globals.maxY - point1.y, j - i, point1.y - point.y);
            return;
        } else
        {
            g.fillRect(i, globals.maxY - point.y, j - i, point.y - point1.y);
            return;
        }
    }

    void fillRect(Graphics g, Point point, int i, int j)
    {
        g.setColor(fillColor);
        g.fillRect(point.x, point.y, i, j);
    }

    public void drawLine(Graphics g, Point point, Point point1)
    {
        g.setColor(lineColor);
        g.drawLine(point.x, globals.maxY - point.y, point1.x, globals.maxY - point1.y);
    }

    public void drawLine(Graphics g, int i, int j, int k, int l)
    {
        g.setColor(lineColor);
        g.drawLine(i, globals.maxY - j, k, globals.maxY - l);
    }

    public void drawPolyline(Graphics g, Point apoint[])
    {
        g.setColor(lineColor);
        if(apoint.length == 1)
            return;
        for(int i = 1; i < apoint.length; i++)
            g.drawLine(apoint[i - 1].x, globals.maxY - apoint[i - 1].y, apoint[i].x, globals.maxY - apoint[i].y);

    }

    public void drawImage(Graphics g, Point point)
    {
        if(image != null)
            g.drawImage(image, point.x - image.getWidth(null) / 2, globals.maxY - point.y - image.getHeight(null) / 2, null);
    }

    public void drawPolygon(Graphics g, Point apoint[])
    {
        g.setColor(fillColor);
        int ai[] = new int[apoint.length];
        int ai1[] = new int[apoint.length];
        for(int i = 0; i < apoint.length; i++)
        {
            ai[i] = apoint[i].x;
            ai1[i] = globals.maxY - apoint[i].y;
        }

        g.fillPolygon(ai, ai1, apoint.length);
    }

    void fillArc(Graphics g, Point point, Point point1, int i, int j)
    {
        int k = point.x - point1.x / 2;
        int l = globals.maxY - point.y - point1.y / 2;
        g.setColor(fillColor);
        g.fillArc(k, l, point1.x, point1.y, i, j);
    }

    public void drawArc(Graphics g, Point point, Point point1, int i, int j)
    {
        int k = point.x - point1.x / 2;
        int l = globals.maxY - point.y - point1.y / 2;
        g.setColor(fillColor);
        g.drawArc(k, l, point1.x, point1.y, i, j);
    }

    public void drawSmartString(Graphics g, int i, int j, int k, int l, FontMetrics fontmetrics, String s)
    {
        int i1 = 0;
        int j1 = 0;
        int k1 = 0;
        if(globals.stringRotator != null)
        {
            globals.stringRotator.setFont(g.getFont());
            globals.stringRotator.setColor(g.getColor());
        } else
        {
            l = 0;
        }
        switch(k)
        {
        case 0: // '\0'
            if(l == 0)
            {
                drawString(g, i - fontmetrics.stringWidth(s) / 2, j - fontmetrics.getMaxAscent(), s);
                return;
            }
            if(l < -90 || l > 90)
                k1 = 90;
            else
                k1 = l;
            Rectangle rectangle = globals.stringRotator.getExtent(s, i, globals.maxY - j, k1, fontmetrics);
            if(k1 == 90)
            {
                i1 = i + fontmetrics.getMaxAscent() / 2;
                j1 = j - rectangle.height;
            } else
            if(k1 > 0)
            {
                i1 = (i - rectangle.width) + fontmetrics.getMaxAscent();
                j1 = j - rectangle.height;
            } else
            {
                i1 = i - fontmetrics.getMaxAscent() / 2;
                j1 = j - fontmetrics.getMaxAscent() / 2;
            }
            break;

        case 2: // '\002'
            if(l == 0)
            {
                drawString(g, i - fontmetrics.stringWidth(s) / 2, j, s);
                return;
            }
            if(l < -90 || l > 90)
                k1 = 90;
            else
                k1 = l;
            Rectangle rectangle1 = globals.stringRotator.getExtent(s, i, globals.maxY - j, k1, fontmetrics);
            if(k1 == 90)
            {
                i1 = i + fontmetrics.getMaxAscent() / 2;
                j1 = j;
            }
            if(k1 == -90)
            {
                i1 = i - fontmetrics.getMaxAscent() / 2;
                j1 = j + rectangle1.height;
                break;
            }
            if(l > 0)
            {
                i1 = i;
                j1 = j;
            } else
            {
                i1 = (i - rectangle1.width) + fontmetrics.getMaxAscent() / 2;
                j1 = (j + rectangle1.height) - fontmetrics.getMaxAscent() / 2;
            }
            break;

        case 3: // '\003'
            if(l == 0)
            {
                drawString(g, i, j - fontmetrics.getHeight() / 2, s);
                return;
            }
            if(l < -90 || l > 90)
                k1 = 90;
            else
                k1 = l;
            Rectangle rectangle2 = globals.stringRotator.getExtent(s, i, globals.maxY - j, k1, fontmetrics);
            if(l > 0)
                i1 = i + fontmetrics.getMaxAscent();
            else
                i1 = i;
            if(l == 90)
            {
                j1 = j - fontmetrics.stringWidth(s) / 2;
                break;
            }
            if(l == -90)
                j1 = j + fontmetrics.stringWidth(s) / 2;
            else
                j1 = j;
            break;

        case 1: // '\001'
            if(l == 0)
            {
                drawString(g, i - fontmetrics.stringWidth(s), j - fontmetrics.getHeight() / 2, s);
                return;
            }
            if(l < -90 || l > 90)
                k1 = 90;
            else
                k1 = l;
            Rectangle rectangle3 = globals.stringRotator.getExtent(s, i, globals.maxY - j, k1, fontmetrics);
            i1 = i - rectangle3.width;
            if(l == 90)
            {
                j1 = j - fontmetrics.stringWidth(s) / 2;
                break;
            }
            if(l == -90)
            {
                j1 = j + fontmetrics.stringWidth(s) / 2;
                break;
            }
            if(l > 0)
                j1 = (j - rectangle3.height) + fontmetrics.getMaxAscent() / 2;
            else
                j1 = (j + rectangle3.height) - fontmetrics.getMaxAscent() / 2;
            break;

        default:
            return;
        }
        globals.stringRotator.drawString(s, i1, globals.maxY - j1, k1, globals.image);
    }

    public void drawString(Graphics g, int i, int j, String s)
    {
        try
        {
            g.drawString(s, i, globals.maxY - j);
            return;
        }
        catch(NullPointerException _ex)
        {
            return;
        }
    }

    public static String nonSciNumberStr(String s, int i)
    {
        int j = s.indexOf(43);
        int l;
        if(j == -1)
            l = Integer.parseInt(s.substring(i + 1));
        else
            l = Integer.parseInt(s.substring(j + 1));
        String s1 = s.substring(0, i).trim();
        int i1 = s1.indexOf(46);
        if(i1 == -1)
        {
            if(l > 0)
                j = l;
            else
                j = -l;
        } else
        if(l > 0)
            j = (l - (s1.length() - i1)) + 1;
        else
            j = -l - i1;
        char ac[] = new char[j];
        for(int k = 0; k < ac.length; k++)
            ac[k] = '0';

        if(i1 == -1)
            if(l < 0)
                return "0." + ac + s1;
            else
                return s1 + ac;
        if(l < 0)
            return "0." + ac + s1.substring(0, i1) + s1.substring(i1 + 1);
        else
            return s1.substring(0, i1) + s1.substring(i1 + 1) + ac;
    }

    public static String formattedLabel(String s, int i, int j)
    {
        int i1 = s.lastIndexOf(".");
        if(i1 != -1)
        {
            int l1 = s.length();
            if(j == 0 || i1 == l1 - 2 && s.charAt(l1 - 1) == '0')
                s = s.substring(0, i1);
            else
            if(l1 > 1 + i1 + j)
                s = s.substring(0, i1 + 1 + j);
        }
        if(i == 0)
            return s;
        char c;
        if(i == 1)
        {
            c = ',';
            byte byte0 = 46;
        } else
        {
            c = '.';
            byte byte1 = 44;
        }
        int k = s.indexOf(46);
        if(k == -1)
            k = s.length();
        else
        if(i == 2)
            s = s.replace('.', ',');
        char ac[];
        if(k % 3 != 0)
            ac = new char[s.length() + k / 3];
        else
            ac = new char[(s.length() + k / 3) - 1];
        char ac1[] = s.toCharArray();
        i1 = ac1.length - 1;
        int k1 = ac.length - 1;
        for(; i1 >= k; i1--)
        {
            ac[k1] = ac1[i1];
            k1--;
        }

        int l;
        if(ac1[0] == '-')
            l = 1;
        else
            l = 0;
        k1 = 0;
        for(int j1 = 0; j1 < k;)
        {
            if((k - j1) % 3 == 0 && k1 > l && j1 < k)
            {
                ac[k1] = c;
                k1++;
            }
            ac[k1] = ac1[j1];
            j1++;
            k1++;
        }

        return new String(ac);
    }

    Color markerColor;
    int markerStyle;
    int markerSize;
    Color fillColor;
    transient Image image;
    Globals globals;
    Color lineColor;
    int lineStyle;
    int lineWidth;
    public static final int DEFAULT_FORMAT = 0;
    public static final int COMMA_FORMAT = 1;
    public static final int EURO_FORMAT = 2;
    static int individualCount;
    static final int keepBELOW = 0;
    static final int keepLEFT = 1;
    static final int keepABOVE = 2;
    static final int keepRIGHT = 3;
}
