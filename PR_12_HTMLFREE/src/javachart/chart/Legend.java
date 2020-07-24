// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Legend.java

package javachart.chart;

import java.awt.*;

// Referenced classes of package javachart.chart:
//            DisplayList, Transform, Globals, Dataset,
//            LegendInterface, Gc

public class Legend
    implements LegendInterface
{

    public Legend(Dataset adataset[], Globals globals1)
    {
	verticalLayout = true;
	backgroundVisible = true;
	iconHeight = 0.050000000000000003D;
	iconWidth = 0.070000000000000007D;
	iconGap = 0.01D;
	useDisplayList = true;
	gWidth = 640;
	gHeight = 480;
	globals = globals1;
	backgroundGc = new Gc(Color.white, globals1);
	labelFont = htmleditor.AppRegistry.CHART_DEFAULT_FONT;
	labelColor = Color.black;
	datasets = adataset;
	llX = 0.20000000000000001D;
	llY = 0.59999999999999998D;
    }

    public void draw(Graphics g)
    {
	if(g == null)
	    return;
	recalculateSize(g);
	if(backgroundVisible)
	{
	    backgroundGc.fillRect(g, transform.point(llX, llY), transform.point(urX, urY));
	    if(useDisplayList && globals.useDisplayList)
		globals.displayList.addRectangle(this, transform.point(llX, llY), transform.point(urX, urY));
	}
	if(verticalLayout)
	{
	    doVerticalIcons(g);
	    return;
	} else
	{
	    doHorizontalIcons(g);
	    return;
	}
    }

    public void resize(int i, int j)
    {
	gWidth = i;
	gHeight = j;
	transform = new Transform(0.0D, 0.0D, 1.0D, 1.0D, 0, 0, gWidth, gHeight);
    }

    public boolean getUseDisplayList()
    {
	return useDisplayList;
    }

    public void setUseDisplayList(boolean flag)
    {
	useDisplayList = flag;
    }

    public Gc getBackgroundGc()
    {
	return backgroundGc;
    }

    public void setBackgroundGC(Gc gc)
    {
	backgroundGc = gc;
    }

    public Font getLabelFont()
    {
	return labelFont;
    }

    public void setLabelFont(Font font)
    {
	labelFont = font;
    }

    public Color getLabelColor()
    {
	return labelColor;
    }

    public void setLabelColor(Color color)
    {
	labelColor = color;
    }

    public boolean getVerticalLayout()
    {
	return verticalLayout;
    }

    public void setVerticalLayout(boolean flag)
    {
	verticalLayout = flag;
    }

    public boolean getBackgroundVisible()
    {
	return backgroundVisible;
    }

    public void setBackgroundVisible(boolean flag)
    {
	backgroundVisible = flag;
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

    public double getUrX()
    {
	return urX;
    }

    public double setUrY()
    {
	return urY;
    }

    public double getIconWidth()
    {
	return iconWidth;
    }

    public void setIconWidth(double d)
    {
	iconWidth = d;
    }

    public double getIconHeight()
    {
	return iconHeight;
    }

    public void setIconHeight(double d)
    {
	iconHeight = d;
    }

    public double getIconGap()
    {
	return iconGap;
    }

    public void setIconGap(double d)
    {
	iconGap = d;
    }

    public String toString()
    {
	return getClass().getName() + "[" + "urX " + urX + "urY " + urY + "llX " + llX + "llY " + llY + "]";
    }

    synchronized int datasetsInUse()
    {
	int i;
	for(i = 0; i < datasets.length; i++)
	    if(datasets[i] == null)
		return i;

	return i;
    }

    protected synchronized void doVerticalIcons(Graphics g)
    {
	g.setFont(labelFont);
	int j = datasetsInUse();
	double d = llX + 0.02D;
	double d1 = llY + 0.02D;
	double d2 = d + iconWidth;
	double d3 = d1 + iconHeight;
	for(int i = 0; i < j; i++)
	{
	    datasets[i].gc.fillRect(g, transform.point(d, d1), transform.point(d2, d3));
	    if(useDisplayList && globals.useDisplayList)
		globals.displayList.addRectangle(datasets[i], transform.point(d, d1), transform.point(d2, d3));
	    Point point = transform.point(d2, d1);
	    g.setColor(labelColor);
	    backgroundGc.drawString(g, point.x + 2, point.y, datasets[i].setName);
	    d1 += iconHeight + iconGap;
	    d3 = d1 + iconHeight;
	}

    }

    protected synchronized void doHorizontalIcons(Graphics g)
    {
	g.setFont(labelFont);
	int j = datasetsInUse();
	double d = llX + 0.02D;
	double d1 = llY + 0.02D;
	double d2 = d + iconWidth;
	double d3 = d1 + iconHeight;
	for(int i = 0; i < j; i++)
	{
	    datasets[i].gc.fillRect(g, transform.point(d, d1), transform.point(d2, d3));
	    if(useDisplayList && globals.useDisplayList)
		globals.displayList.addRectangle(datasets[i], transform.point(d, d1), transform.point(d2, d3));
	    Point point = transform.point(d2, d1);
	    g.setColor(labelColor);
	    backgroundGc.drawString(g, point.x + 2, point.y, datasets[i].setName);
	    FontMetrics fontmetrics = g.getFontMetrics();
	    d += iconWidth + iconGap + iconGap + (double)fontmetrics.stringWidth(datasets[i].setName) / (double)gWidth;
	    d2 = d + iconWidth;
	}

    }

    public synchronized void recalculateSize(Graphics g)
    {
	int l = 0;
	double d = 0.0D;
	int i1 = 0;
	g.setFont(labelFont);
	FontMetrics fontmetrics = g.getFontMetrics();
	int k = datasetsInUse();
	if(verticalLayout)
	{
	    for(int i = 0; i < k; i++)
	    {
		l = fontmetrics.stringWidth(datasets[i].setName);
		if(l > i1)
		    i1 = l;
		d += iconGap + iconHeight;
	    }

	    urX = llX + iconWidth + 0.040000000000000001D + (double)l / (double)gWidth;
	    urY = llY + d + 0.040000000000000001D + d / (double)gHeight;
	    return;
	}
	for(int j = 0; j < k; j++)
	    i1 += fontmetrics.stringWidth(datasets[j].setName);

	urX = llX + (double)l + 0.040000000000000001D + (double)k * (iconWidth + iconGap) + iconWidth + iconGap + (double)i1 / (double)gWidth;
	urY = llY + d + 0.040000000000000001D + iconHeight;
    }

    Globals globals;
    Gc backgroundGc;
    Font labelFont;
    Color labelColor;
    boolean verticalLayout;
    boolean backgroundVisible;
    double llX;
    double llY;
    double urX;
    double urY;
    double iconHeight;
    double iconWidth;
    double iconGap;
    boolean useDisplayList;
    Dataset datasets[];
    Transform transform;
    int gWidth;
    int gHeight;
    static final double backgroundFudge = 0.02D;
}
