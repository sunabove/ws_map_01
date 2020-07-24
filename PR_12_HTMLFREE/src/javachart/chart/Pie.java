// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Pie.java

package javachart.chart;

import java.awt.*;
import java.util.Vector;

// Referenced classes of package javachart.chart:
//            Dataset, Globals, Transform, Plotarea,
//            Gc, Datum, DisplayList

public class Pie
{

    Pie(Dataset dataset1, Plotarea plotarea1, Globals globals1)
    {
	textLabelsOn = false;
	valueLabelsOn = false;
	percentLabelsOn = true;
	labelPosition = 2;
	xLoc = 0.5D;
	yLoc = 0.5D;
	width = 0.59999999999999998D;
	height = 0.59999999999999998D;
	useDisplayList = true;
	dataset = dataset1;
	plotarea = plotarea1;
	globals = globals1;
	labelFont = htmleditor.AppRegistry.CHART_DEFAULT_FONT;
	labelColor = Color.black;
	lineGc = new Gc(Color.black, globals);
    }

    public synchronized void draw(Graphics g)
    {
	if(g == null)
	    return;
	if(dataset == null)
	    return;
	if(!globals.threeD)
	{
	    doPie(g, xLoc, yLoc);
	    return;
	} else
	{
	    doDPie(g, xLoc, yLoc);
	    return;
	}
    }

    public synchronized void setExplosion(int i, double d)
    {
	try
	{
	    dataset.getDataElementAt(i).y2 = d;
	    return;
	}
	catch(NullPointerException _ex)
	{
	    return;
	}
	catch(ArrayIndexOutOfBoundsException _ex)
	{
	    return;
	}
    }

    public boolean getTextLabelsOn()
    {
	return textLabelsOn;
    }

    public void setTextLabelsOn(boolean flag)
    {
	textLabelsOn = flag;
    }

    public boolean getValueLabelsOn()
    {
	return valueLabelsOn;
    }

    public void setValueLabelsOn(boolean flag)
    {
	valueLabelsOn = flag;
    }

    public boolean getPercentLabelsOn()
    {
	return percentLabelsOn;
    }

    public void setPercentLabelsOn(boolean flag)
    {
	percentLabelsOn = flag;
    }

    public int getLabelPosition()
    {
	return labelPosition;
    }

    public void setLabelPosition(int i)
    {
	if(labelPosition < 0 || labelPosition > 2)
	{
	    labelPosition = 1;
	    return;
	} else
	{
	    labelPosition = i;
	    return;
	}
    }

    public int getLabelPrecision()
    {
	return labelPrecision;
    }

    public void setLabelPrecision(int i)
    {
	labelPrecision = i;
    }

    public int getLabelFormat()
    {
	return labelFormat;
    }

    public void setLabelFormat(int i)
    {
	if(labelFormat < 0 || labelFormat > 2)
	{
	    labelFormat = 0;
	    return;
	} else
	{
	    labelFormat = i;
	    return;
	}
    }

    public double getXLoc()
    {
	return xLoc;
    }

    public void setXLoc(double d)
    {
	if(d < 0.0D || d > 1.0D)
	{
	    xLoc = 0.5D;
	    return;
	} else
	{
	    xLoc = d;
	    return;
	}
    }

    public double getYLoc()
    {
	return yLoc;
    }

    public void setYLoc(double d)
    {
	if(d < 0.0D || d > 1.0D)
	{
	    yLoc = 0.5D;
	    return;
	} else
	{
	    yLoc = d;
	    return;
	}
    }

    public int getStartDegrees()
    {
	return startDegrees;
    }

    public void setStartDegrees(int i)
    {
	if(startDegrees >= 0 || startDegrees <= 360)
	    startDegrees = i;
	for(; startDegrees < 0; startDegrees += 360);
	for(; startDegrees > 360; startDegrees -= 360);
    }

    public double getWidth()
    {
	return width;
    }

    public void setWidth(double d)
    {
	if(d < 1.0D)
	    width = d;
    }

    public double getHeight()
    {
	return height;
    }

    public void setHeight(double d)
    {
	if(d < 1.0D)
	    height = d;
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

    public boolean getUseDisplayList()
    {
	return useDisplayList;
    }

    public void setUseDisplayList(boolean flag)
    {
	useDisplayList = flag;
    }

    protected void doPie(Graphics g, double d, double d1)
    {
	int k = startDegrees;
	for(int i = 0; i < dataset.data.size(); i++)
	    total += dataset.getDataElementAt(i).y;

	for(int j = 0; j < dataset.data.size(); j++)
	{
	    int l;
	    if(j == dataset.data.size() - 1)
		l = (360 - k) + startDegrees;
	    else
		l = (int)((360D * dataset.getDataElementAt(j).y) / total);
	    double d4 = ((double)(l / 2 + k) / 180D) * 3.1415926535897931D;
	    double d2 = xLoc + dataset.getDataElementAt(j).y2 * Math.cos(d4);
	    double d3 = yLoc + dataset.getDataElementAt(j).y2 * Math.sin(d4);
	    dataset.getDataElementAt(j).gc.fillArc(g, plotarea.transform.point(d2, d3), plotarea.transform.point(width, height), k, l);
	    if(useDisplayList && globals.useDisplayList)
		globals.displayList.addArc(dataset.getDataElementAt(j), plotarea.transform.point(d2, d3), plotarea.transform.point(width, height), k, l);
	    if(textLabelsOn || valueLabelsOn || percentLabelsOn)
		doPieLabel(g, d2, d3, d4, dataset.getDataElementAt(j));
	    k += l;
	}

	total = 0.0D;
    }

    protected void doDPie(Graphics g, double d, double d1)
    {
	int i1 = startDegrees;
	widthHeight = plotarea.transform.point(width, height);
	Point apoint[] = new Point[4];
	for(int i = 0; i < dataset.data.size(); i++)
	    total += dataset.getDataElementAt(i).y;

	for(int j = 0; j < dataset.data.size(); j++)
	{
	    int j1;
	    if(j == dataset.data.size() - 1)
		j1 = (360 - i1) + startDegrees;
	    else
		j1 = (int)((360D * dataset.getDataElementAt(j).y) / total);
	    double d2 = xLoc;
	    double d5 = yLoc;
	    Point point = plotarea.transform.point(d2, d5);
	    point.y -= globals.yOffset;
	    Color color = dataset.getDataElementAt(j).gc.fillColor;
	    dataset.getDataElementAt(j).gc.fillColor = color.darker();
	    dataset.getDataElementAt(j).gc.fillArc(g, point, widthHeight, i1, j1);
	    dataset.getDataElementAt(j).gc.fillColor = color;
	    i1 += j1;
	    if(useDisplayList && globals.useDisplayList)
		globals.displayList.addArc(dataset.getDataElementAt(j), plotarea.transform.point(d2, d5), plotarea.transform.point(width, height), i1, j1);
	}

	i1 = startDegrees;
	for(int k = 0; k < dataset.data.size(); k++)
	{
	    int k1;
	    if(k == dataset.data.size() - 1)
		k1 = (360 - i1) + startDegrees;
	    else
		k1 = (int)((360D * dataset.getDataElementAt(k).y) / total);
	    double d3 = xLoc;
	    double d6 = yLoc;
	    Point point1 = plotarea.transform.point(d3, d6);
	    point1.y -= globals.yOffset;
	    if(i1 < 180 && i1 + k1 > 180)
	    {
		k1 -= 180 - i1;
		i1 = 180;
	    }
	    apoint[1] = getArcPoint(point1, i1);
	    apoint[0] = new Point(apoint[1].x, apoint[1].y + globals.yOffset);
	    apoint[2] = getArcPoint(point1, i1 + k1);
	    apoint[3] = new Point(apoint[2].x, apoint[2].y + globals.yOffset);
	    Color color1 = dataset.getDataElementAt(k).gc.fillColor;
	    dataset.getDataElementAt(k).gc.fillColor = color1.darker();
	    dataset.getDataElementAt(k).gc.drawPolygon(g, apoint);
	    dataset.getDataElementAt(k).gc.fillColor = color1;
	    i1 += k1;
	}

	i1 = startDegrees;
	for(int l = 0; l < dataset.data.size(); l++)
	{
	    int l1;
	    if(l == dataset.data.size() - 1)
		l1 = (360 - i1) + startDegrees;
	    else
		l1 = (int)((360D * dataset.getDataElementAt(l).y) / total);
	    double d8 = ((double)(l1 / 2 + i1) / 180D) * 3.1415926535897931D;
	    double d4 = xLoc;
	    double d7 = yLoc;
	    dataset.getDataElementAt(l).gc.fillArc(g, plotarea.transform.point(d4, d7), widthHeight, i1, l1);
	    if(textLabelsOn || valueLabelsOn || percentLabelsOn)
		doPieLabel(g, d4, d7, d8, dataset.getDataElementAt(l));
	    i1 += l1;
	}

	total = 0.0D;
    }

    private Point getArcPoint(Point point, int i)
    {
	double d = ((double)i / 180D) * 3.1415926535897931D;
	int j = (int)((double)(widthHeight.x / 2) * Math.cos(d)) + point.x;
	int k = (int)((double)(widthHeight.y / 2) * Math.sin(d)) + point.y;
	return new Point(j, k);
    }

    protected String pctStr(double d)
    {
	if(d >= 1.0D)
	{
	    return "100%";
	} else
	{
	    String s = Double.toString(d * 100D);
	    s = Gc.formattedLabel(s, labelFormat, labelPrecision);
	    return s + "%";
	}
    }

    private void doPieLabel(Graphics g, double d, double d1, double d2,
	    Datum datum)
    {
	String s = null;
	double d11;
	for(d11 = 6.2831853071795862D; d2 > d11; d2 -= d11);
	if(valueLabelsOn)
	{
	    s = Double.toString(datum.y);
	    int k = s.indexOf(101);
	    if(k != -1)
		s = Gc.nonSciNumberStr(s, k);
	    s = Gc.formattedLabel(s, labelFormat, labelPrecision);
	}
	g.setFont(labelFont);
	g.setColor(labelColor);
	FontMetrics fontmetrics = g.getFontMetrics();
	int i;
	int j;
	if(labelPosition == 1)
	{
	    double d3 = d + (width / 2D) * Math.cos(d2);
	    double d6 = d1 + (height / 2D) * Math.sin(d2);
	    Point point = plotarea.transform.point(d3, d6);
	    i = point.x;
	    j = point.y;
	} else
	if(labelPosition == 0)
	{
	    double d4 = d + (width / 4D) * Math.cos(d2);
	    double d7 = d1 + (height / 4D) * Math.sin(d2);
	    Point point1 = plotarea.transform.point(d4, d7);
	    i = point1.x;
	    j = point1.y;
	} else
	if(labelPosition == 2)
	{
	    double d5 = d + (width / 2D) * Math.cos(d2);
	    double d8 = d1 + (height / 2D) * Math.sin(d2);
	    double d9 = d + (width / 1.8D) * Math.cos(d2);
	    double d10 = d1 + (height / 1.8D) * Math.sin(d2);
	    lineGc.drawLine(g, plotarea.transform.point(d5, d8), plotarea.transform.point(d9, d10));
	    g.setColor(labelColor);
	    d9 = d + (width / 1.79D) * Math.cos(d2);
	    d10 = d1 + (height / 1.79D) * Math.sin(d2);
	    Point point2 = plotarea.transform.point(d9, d10);
	    i = point2.x;
	    j = point2.y;
	} else
	{
	    return;
	}
	if(d2 >= 4.7123889803846897D && d2 <= d11)
	{
	    j -= fontmetrics.getAscent();
	    if(textLabelsOn)
	    {
		lineGc.drawString(g, i, j, datum.label);
		j -= fontmetrics.getAscent();
	    }
	    if(valueLabelsOn)
	    {
		lineGc.drawString(g, i, j, s);
		j -= fontmetrics.getAscent();
	    }
	    if(percentLabelsOn)
		lineGc.drawString(g, i, j, pctStr(datum.y / total));
	    return;
	}
	if(d2 >= 0.0D && d2 < 1.5707963267948966D)
	{
	    if(percentLabelsOn)
	    {
		lineGc.drawString(g, i, j, pctStr(datum.y / total));
		j += fontmetrics.getAscent();
	    }
	    if(valueLabelsOn)
	    {
		lineGc.drawString(g, i, j, s);
		j += fontmetrics.getAscent();
	    }
	    if(textLabelsOn)
		lineGc.drawString(g, i, j, datum.label);
	    return;
	}
	if(d2 >= 1.5707963267948966D && d2 < 3.1415926535897931D)
	{
	    if(textLabelsOn && datum.label != null)
		i -= fontmetrics.stringWidth(datum.label);
	    else
	    if(valueLabelsOn)
		i -= fontmetrics.stringWidth(s);
	    else
		i -= fontmetrics.stringWidth(pctStr(datum.y / total));
	    if(percentLabelsOn)
	    {
		lineGc.drawString(g, i, j, pctStr(datum.y / total));
		j += fontmetrics.getAscent();
	    }
	    if(valueLabelsOn)
	    {
		lineGc.drawString(g, i, j, s);
		j += fontmetrics.getAscent();
	    }
	    if(textLabelsOn)
		lineGc.drawString(g, i, j, datum.label);
	    return;
	}
	if(d2 >= 3.1415926535897931D && d2 < 4.7123889803846897D)
	{
	    if(textLabelsOn && datum.label != null)
		i -= fontmetrics.stringWidth(datum.label);
	    else
	    if(valueLabelsOn)
		i -= fontmetrics.stringWidth(s);
	    else
		i -= fontmetrics.stringWidth(pctStr(datum.y / total));
	    j -= fontmetrics.getAscent();
	    if(textLabelsOn)
	    {
		lineGc.drawString(g, i, j, datum.label);
		j -= fontmetrics.getAscent();
	    }
	    if(valueLabelsOn)
	    {
		lineGc.drawString(g, i, j, s);
		j -= fontmetrics.getAscent();
	    }
	    if(percentLabelsOn)
	    {
		lineGc.drawString(g, i, j, pctStr(datum.y / total));
		j -= fontmetrics.getAscent();
	    }
	}
    }

    boolean textLabelsOn;
    boolean valueLabelsOn;
    boolean percentLabelsOn;
    int labelPosition;
    int labelFormat;
    int labelPrecision;
    double xLoc;
    double yLoc;
    int startDegrees;
    double width;
    double height;
    Font labelFont;
    Color labelColor;
    boolean useDisplayList;
    Globals globals;
    Dataset dataset;
    Plotarea plotarea;
    double total;
    Gc lineGc;
    private Point widthHeight;
}
