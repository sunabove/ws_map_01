// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Plotarea.java

package javachart.chart;

import java.awt.*;

// Referenced classes of package javachart.chart:
//            Globals, Transform, DisplayList, Gc

public class Plotarea
{

    public Plotarea()
    {
	this(new Globals());
    }

    public Plotarea(Globals globals1)
    {
	urX = 0.97000000000000004D;
	urY = 0.80000000000000004D;
	llX = 0.10000000000000001D;
	llY = 0.230000000000000001D;
	useDisplayList = true;
	gWidth = 640;
	gHeight = 480;
	globals = globals1;
	gc = new Gc(Color.white, globals);
	transform = new Transform(0.0D, 0.0D, 1.0D, 1.0D, 0, 0, gWidth, gHeight);
    }

    public synchronized void draw(Graphics g)
    {
	if(globals.threeD)
	    draw3d(g);
	else
	    gc.fillRect(g, transform.point(llX, llY), transform.point(urX, urY));
	if(useDisplayList && globals.useDisplayList)
	    globals.displayList.addRectangle(this, transform.point(llX, llY), transform.point(urX, urY));
    }

    public synchronized void draw3d(Graphics g)
    {
	Point apoint[] = new Point[4];
	if(g == null)
	    return;
	apoint[0] = transform.point(llX, llY);
	apoint[1] = new Point(apoint[0].x + globals.xOffset, apoint[0].y + globals.yOffset);
	apoint[2] = transform.point(urX, urY);
	apoint[3] = new Point(apoint[2].x + globals.xOffset, apoint[2].y + globals.yOffset);
	gc.fillRect(g, apoint[1], apoint[3]);
	if(useDisplayList && globals.useDisplayList)
	    globals.displayList.addRectangle(this, apoint[1], apoint[3]);
	Color color = gc.fillColor;
	gc.fillColor = color.darker();
	int i = apoint[2].x;
	int j = apoint[2].y;
	apoint[2].x = apoint[1].x;
	apoint[2].y = apoint[3].y;
	apoint[3].x = apoint[0].x;
	apoint[3].y = j;
	gc.drawPolygon(g, apoint);
	apoint[2].x = i + globals.xOffset;
	apoint[2].y = apoint[1].y;
	apoint[3].x = i;
	apoint[3].y = apoint[0].y;
	gc.drawPolygon(g, apoint);
	gc.fillColor = color;
    }

    public synchronized void resize(int i, int j)
    {
	gWidth = i;
	gHeight = j;
	transform = new Transform(0.0D, 0.0D, 1.0D, 1.0D, 0, 0, gWidth, gHeight);
    }

    public Gc getGc()
    {
	return gc;
    }

    public void setGc(Gc gc1)
    {
	gc = gc1;
    }

    public double getUrX()
    {
	return urX;
    }

    public void setUrX(double d)
    {
	urX = d;
    }

    public double getUrY()
    {
	return urY;
    }

    public void setUrY(double d)
    {
	urY = d;
    }

    public double getLlX()
    {
	return llX;
    }

    public void setLlX(double d)
    {
	llX = d;
    }

    public double getLlY()
    {
	return llY;
    }

    public void setLlY(double d)
    {
	llY = d;
    }

    public boolean getUseDisplayList()
    {
	return useDisplayList;
    }

    public void setUseDisplayList(boolean flag)
    {
	useDisplayList = flag;
    }

    public String toString()
    {
	return getClass().getName() + "[" + "urX " + urX + "urY " + urY + "llX " + llX + "llY " + llY + "]";
    }

    Gc gc;
    double urX;
    double urY;
    double llX;
    double llY;
    boolean useDisplayList;
    Globals globals;
    Transform transform;
    private int gWidth;
    private int gHeight;
}
