// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Background.java

package javachart.chart;

import java.awt.*;
import java.io.PrintStream;

// Referenced classes of package javachart.chart:
//            DisplayList, Globals, Gc

public class Background
{

    public Background(Globals globals1)
    {
	useDisplayList = true;
	gWidth = 640;
	gHeight = 480;
	globals = globals1;
	gc = new Gc(Color.white, globals);
	startPoint = new Point(0, 0);
	titleColor = Color.black;
	titleFont = htmleditor.AppRegistry.CHART_DEFAULT_FONT;
	titleString = null;
    }

    public Gc getGc()
    {
	return gc;
    }

    public void setGc(Gc gc1)
    {
	gc = gc1;
    }

    public Color getTitleColor()
    {
	return titleColor;
    }

    public void setTitleColor(Color color)
    {
	titleColor = color;
    }

    public Font getTitleFont()
    {
	return titleFont;
    }

    public void setTitleFont(Font font)
    {
	titleFont = font;
    }

    public String getTitleString()
    {
	return titleString;
    }

    public void setTitleString(String s)
    {
	titleString = s;
    }

    public boolean getUseDisplayList()
    {
	return useDisplayList;
    }

    public void setUseDisplayList(boolean flag)
    {
	useDisplayList = flag;
    }

    public synchronized void draw(Graphics g)
    {
	if(g == null)
	{
	    System.out.println("null graphics in background");
	    return;
	}
	gc.fillRect(g, startPoint, gWidth, gHeight);
	if(useDisplayList && globals.useDisplayList)
	    globals.displayList.addRectangle(this, startPoint, new Point(gWidth, gHeight));
	if(titleString == null)
	    return;
	g.setFont(titleFont);
	g.setColor(titleColor);
	FontMetrics fontmetrics = g.getFontMetrics();
	int i = (gWidth - startPoint.x) / 2 - fontmetrics.stringWidth(titleString) / 2;
	int j = fontmetrics.getMaxAscent() + 10;
	g.drawString(titleString, i, j);
	if(useDisplayList && globals.useDisplayList)
	    globals.displayList.addTextString(this, i, j, titleString, fontmetrics);
    }

    protected synchronized void resize(int i, int j)
    {
	gWidth = i;
	gHeight = j;
    }

    Gc gc;
    Color titleColor;
    Font titleFont;
    String titleString;
    boolean useDisplayList;
    Globals globals;
    int gWidth;
    int gHeight;
    Point startPoint;
}
