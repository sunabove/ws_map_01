// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Datum.java

package javachart.chart;


// Referenced classes of package javachart.chart:
//            Gc, Globals

public class Datum
{

    public Datum(double d, double d1, Globals globals1)
    {
        globals = globals1;
        x = d;
        y = d1;
        gc = new Gc(globals);
    }

    public Datum(double d, double d1, int i, Globals globals1)
    {
        globals = globals1;
        x = d;
        y = d1;
        gc = new Gc(i, globals);
    }

    public Datum(double d, double d1, String s, boolean flag, Globals globals1)
    {
        globals = globals1;
        x = d;
        y = d1;
        label = s;
        gc = new Gc(flag, globals);
    }

    public Datum(double d, double d1, boolean flag, Globals globals1)
    {
        globals = globals1;
        x = d;
        y = d1;
        gc = new Gc(flag, globals);
    }

    public Datum(double d, String s, int i, Globals globals1)
    {
        globals = globals1;
        y = d;
        gc = new Gc(i, globals);
        label = s;
    }

    public Datum(double d, double d1, String s, int i, Globals globals1)
    {
        globals = globals1;
        x = d;
        y = d1;
        gc = new Gc(i, globals);
        label = s;
    }

    public Datum(double d, double d1, double d2, String s, 
            int i, Globals globals1)
    {
        globals = globals1;
        x = d;
        y = d1;
        y2 = d2;
        gc = new Gc(i, globals);
        label = s;
    }

    public Datum(double d, double d1, double d2, int i, 
            Globals globals1)
    {
        globals = globals1;
        x = d;
        y = d1;
        y2 = d2;
        gc = new Gc(i, globals);
    }

    public Datum(double d, double d1, double d2, double d3, Globals globals1)
    {
        globals = globals1;
        x = d;
        y = d1;
        y2 = d2;
        y3 = d3;
        gc = new Gc(globals);
    }

    public String getLabel()
    {
        return label;
    }

    public void setLabel(String s)
    {
        label = s;
    }

    public Gc getGc()
    {
        return gc;
    }

    public void setGc(Gc gc1)
    {
        gc = gc1;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double d)
    {
        x = d;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double d)
    {
        y = d;
    }

    public double getY2()
    {
        return y2;
    }

    public void setY2(double d)
    {
        y2 = d;
    }

    public Globals getGlobals()
    {
        return globals;
    }

    public void setGlobals(Globals globals1)
    {
        globals = globals1;
    }

    public String getString()
    {
        if(label != null)
            return label;
        String s = Double.toString(y);
        int i = s.indexOf(".");
        if(i != -1)
        {
            int j = s.length();
            if(i == j - 2 && s.charAt(j - 1) == '0')
                s = s.substring(0, i);
        }
        return s;
    }

    String label;
    Gc gc;
    double x;
    double y;
    double y2;
    double y3;
    Globals globals;
}
