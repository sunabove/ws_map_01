// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Axis.java

package javachart.chart;

import java.awt.*;
import java.util.Vector;
//import htmleditor;

// Referenced classes of package javachart.chart:
//            AxisInterface, Plotarea, Gc, DisplayList,
//            Transform, Globals, Dataset

public class Axis
    implements AxisInterface
{

    public Axis(Dataset adataset[], boolean flag, Plotarea plotarea1)
    {
	autoScale = true;
	axisEnd = 10D;
	majTickVis = true;
	minTickVis = false;
	gridVis = false;
	labelVis = true;
	lineVis = true;
	useDisplayList = true;
	labelPrecision = 2;
	numMajTicks = 5;
	numGrids = 5;
	numMinTicks = 10;
	numLabels = 5;
	majTickLength = 5;
	minTickLength = 4;
	side = 1;
	isXAxis = false;
	barScaling = false;
	stepSize = 2D;
	isXAxis = flag;
	if(isXAxis)
	    side = 0;
	plotarea = plotarea1;
	datasets = adataset;
	globals = plotarea.globals;
	titleColor = Color.black;
	titleFont = htmleditor.AppRegistry.CHART_DEFAULT_FONT;
	labelColor = Color.black;
	labelFont = htmleditor.AppRegistry.CHART_DEFAULT_FONT;
	lineGc = new Gc(globals);
	gridGc = new Gc(globals);
	tickGc = new Gc(globals);
    }

    public synchronized void draw(Graphics g)
    {
	if(autoScale)
	    scale();
	if(lineVis)
	    drawLine(g);
	drawTicks(g);
	if(gridVis)
	    if(!globals.threeD)
		drawGrids(g);
	    else
		draw3Dgrids(g);
	if(labelVis)
	    drawLabels(g);
	if(useDisplayList && globals.useDisplayList)
	    buildDisplayList();
    }

    public String toString()
    {
	return getClass().getName() + "[" + " xAxis? " + isXAxis + " stepSize " + stepSize + " axisStart " + axisStart + " axisEnd " + axisEnd + " numMajTicks " + numMajTicks + " numMinTicks " + numMinTicks + " numLabels " + numLabels + " ]";
    }

    public synchronized void addLabels(String as[])
    {
    }

    public void scale()
    {
	linearScale();
    }

    public Dataset[] getDatasets()
    {
	return datasets;
    }

    public void setDatasets(Dataset adataset[])
    {
	datasets = adataset;
    }

    public boolean getAutoScale()
    {
	return autoScale;
    }

    public void setAutoScale(boolean flag)
    {
	autoScale = flag;
    }

    public double getAxisStart()
    {
	return axisStart;
    }

    public void setAxisStart(double d)
    {
	axisStart = d;
    }

    public double getAxisEnd()
    {
	return axisEnd;
    }

    public void setAxisEnd(double d)
    {
	axisEnd = d;
    }

    public boolean getLineVis()
    {
	return lineVis;
    }

    public void setLineVis(boolean flag)
    {
	lineVis = flag;
    }

    public boolean getMajTickVis()
    {
	return majTickVis;
    }

    public void setMajTickVis(boolean flag)
    {
	majTickVis = flag;
    }

    public boolean getMinTickVis()
    {
	return minTickVis;
    }

    public void setMinTickVis(boolean flag)
    {
	minTickVis = flag;
    }

    public String getTitleString()
    {
	return titleString;
    }

    public void setTitleString(String s)
    {
	titleString = s;
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

    public boolean getLabelVis()
    {
	return labelVis;
    }

    public void setLabelVis(boolean flag)
    {
	labelVis = flag;
    }

    public boolean getGridVis()
    {
	return gridVis;
    }

    public void setGridVis(boolean flag)
    {
	gridVis = flag;
    }

    public Color getLabelColor()
    {
	return labelColor;
    }

    public void setLabelColor(Color color)
    {
	labelColor = color;
    }

    public Font getLabelFont()
    {
	return labelFont;
    }

    public void setLabelFont(Font font)
    {
	labelFont = font;
    }

    public int getLabelPrecision()
    {
	return labelPrecision;
    }

    public void setLabelPrecision(int i)
    {
	labelPrecision = i;
    }

    public int getLabelAngle()
    {
	return labelAngle;
    }

    public void setLabelAngle(int i)
    {
	labelAngle = i;
    }

    public Gc getLineGc()
    {
	return lineGc;
    }

    public void setLineGc(Gc gc)
    {
	lineGc = gc;
    }

    public Gc getGridGc()
    {
	return gridGc;
    }

    public void setGridGc(Gc gc)
    {
	gridGc = gc;
    }

    public Gc getTickGc()
    {
	return tickGc;
    }

    public void setTickGc(Gc gc)
    {
	tickGc = gc;
    }

    public int getNumMajTicks()
    {
	return numMajTicks;
    }

    public void setNumMajTicks(int i)
    {
	numMajTicks = i;
    }

    public int getNumGrids()
    {
	return numGrids;
    }

    public void setNumGrids(int i)
    {
	numGrids = i;
    }

    public int getNumMinTicks()
    {
	return numMinTicks;
    }

    public void setNumMinTicks(int i)
    {
	numMinTicks = i;
    }

    public int getNumLabels()
    {
	return numLabels;
    }

    public void setNumLabels(int i)
    {
	numLabels = i;
    }

    public int getMajTickLength()
    {
	return majTickLength;
    }

    public void setMajTickLength(int i)
    {
	majTickLength = i;
    }

    public int getMinTickLength()
    {
	return minTickLength;
    }

    public void setMinTickLength(int i)
    {
	minTickLength = i;
    }

    public int getSide()
    {
	return side;
    }

    public void setSide(int i)
    {
	side = i;
    }

    public boolean getBarScaling()
    {
	return barScaling;
    }

    public void setBarScaling(boolean flag)
    {
	barScaling = flag;
    }

    public boolean getUseDisplayList()
    {
	return useDisplayList;
    }

    public void setUseDisplayList(boolean flag)
    {
	useDisplayList = flag;
    }

    public void setLabelFormat(int i)
    {
	if(i < 0 || i > 2)
	{
	    return;
	} else
	{
	    labelFormat = i;
	    return;
	}
    }

    public int getLabelFormat()
    {
	return labelFormat;
    }

    private synchronized boolean inRange(double d, double d1, double d2)
    {
	return d <= d2 && d >= d1;
    }

    private synchronized double getNormalizedIncrement(double d, double d1)
    {
	if(inRange(d, 0.0D, 0.5D))
	    return 0.5D * d1;
	if(inRange(d, 0.5D, 1.0D))
	    return d1;
	if(inRange(d, 1.0D, 9D))
	    return 2D * d1;
	else
	    return d1;
    }

    protected synchronized int datasetsInUse()
    {
	int i;
	for(i = 0; i < datasets.length; i++)
	    if(datasets[i] == null)
		return i;

	return i;
    }

    private int getMagnitude(double d)
    {
	if(d > 1.0D)
	{
	    double d1 = 10D;
	    int i = 1;
	    do
	    {
		if(d1 > d)
		    return i - 1;
		d1 *= 10D;
		i++;
	    } while(true);
	}
	if(d < 1.0D)
	{
	    double d2 = 0.10000000000000001D;
	    int j = -1;
	    do
	    {
		if(d2 < d)
		    return j + 1;
		d2 *= 0.10000000000000001D;
		j--;
	    } while(true);
	} else
	{
	    return 0;
	}
    }

    protected double getMaxValsFromData(int i)
    {
	double d = -8.9999999999999993E+035D;
	if(!isXAxis)
	{
	    for(int j = 0; j < i; j++)
		d = Math.max(d, datasets[j].maxY());

	} else
	{
	    for(int k = 0; k < i; k++)
		d = Math.max(d, datasets[k].maxX());

	}
	return d;
    }

    protected double getMinValsFromData(int i)
    {
	double d = 8.9999999999999993E+035D;
	if(!isXAxis)
	{
	    for(int j = 0; j < i; j++)
		d = Math.min(d, datasets[j].minY());

	} else
	{
	    for(int k = 0; k < i; k++)
		d = Math.min(d, datasets[k].minX());

	}
	return d;
    }

    private synchronized boolean linearScale()
    {
	double d6 = 8.9999999999999993E+035D;
	double d7 = -8.9999999999999993E+035D;
	boolean flag = true;
	int j = datasetsInUse();
	if(j == 0)
	    return false;
	double d1 = getMaxValsFromData(j);
	double d = getMinValsFromData(j);
	if(j > 1 || datasets[0].data.size() > 1)
	    flag = false;
	if(barScaling)
	{
	    if(d > 0.0D)
		d = 0.0D;
	    if(d1 < 0.0D)
		d1 = 0.0D;
	}
	double d2;
	if(flag || d1 == d)
	{
	    if(d != 0.0D)
		d2 = Math.abs(d * 2D);
	    else
	    if(d1 == 0.0D)
		d2 = 2D;
	    else
		d2 = d1;
	} else
	{
	    d2 = d1 - d;
	}
	int i = getMagnitude(d2);
	double d3 = Math.pow(10D, i);
	double d5 = d2 / (5D * d3);
	double d4 = getNormalizedIncrement(d5, d3);
	stepSize = d4;
	for(d6 = Math.floor(d / d4) * d4; (d6 += d4) <= d;);
	d6 -= d4;
	for(d7 = d6; (d7 += d4) < d1;);
	axisStart = d6;
	axisEnd = d7;
	numLabels = (int)((d7 - d6) / stepSize);
	numMajTicks = numLabels;
	numGrids = numLabels;
	numMinTicks = 2 * numMajTicks;
	return true;
    }

    protected void drawLine(Graphics g)
    {
	switch(side)
	{
	case 0: // '\0'
	    lineGc.drawLine(g, plotarea.transform.point(plotarea.llX, plotarea.llY), plotarea.transform.point(plotarea.urX, plotarea.llY));
	    return;

	case 1: // '\001'
	    lineGc.drawLine(g, plotarea.transform.point(plotarea.llX, plotarea.llY), plotarea.transform.point(plotarea.llX, plotarea.urY));
	    return;

	case 2: // '\002'
	    lineGc.drawLine(g, plotarea.transform.point(plotarea.llX, plotarea.urY), plotarea.transform.point(plotarea.urX, plotarea.urY));
	    return;

	case 3: // '\003'
	    lineGc.drawLine(g, plotarea.transform.point(plotarea.urX, plotarea.urY), plotarea.transform.point(plotarea.urX, plotarea.llY));
	    return;
	}
    }

    private void buildDisplayList()
    {
	switch(side)
	{
	case 0: // '\0'
	    globals.displayList.addRectangle(this, plotarea.transform.point(plotarea.llX, plotarea.llY), plotarea.transform.point(plotarea.urX, 0.0D));
	    return;

	case 1: // '\001'
	    globals.displayList.addRectangle(this, plotarea.transform.point(plotarea.llX, plotarea.llY), plotarea.transform.point(0.0D, plotarea.urY));
	    return;

	case 2: // '\002'
	    globals.displayList.addRectangle(this, plotarea.transform.point(plotarea.llX, plotarea.urY), plotarea.transform.point(plotarea.urX, 1.0D));
	    return;

	case 3: // '\003'
	    globals.displayList.addRectangle(this, plotarea.transform.point(plotarea.urX, plotarea.urY), plotarea.transform.point(1.0D, plotarea.llY));
	    return;
	}
    }

    float getIncrement(int i, int j, int k)
    {
	return ((float)i - (float)j) / (float)k;
    }

    protected int whereOnAxis(int i, int j)
    {
	if(side == 0 || side == 2)
	    return startPoint.x + (int)(increment * (float)i);
	else
	    return startPoint.y + (int)(increment * (float)i);
    }

    protected void drawTicks(Graphics g)
    {
	if(majTickVis)
	    switch(side)
	    {
	    default:
		break;

	    case 0: // '\0'
		startPoint = plotarea.transform.point(plotarea.llX, plotarea.llY);
		int i = startPoint.y;
		endPoint = plotarea.transform.point(plotarea.urX, plotarea.llY);
		increment = getIncrement(endPoint.x, startPoint.x, numMajTicks);
		for(int i2 = 0; i2 < numMajTicks; i2++)
		    tickGc.drawLine(g, whereOnAxis(i2, 4), i, whereOnAxis(i2, 4), i - majTickLength);

		tickGc.drawLine(g, endPoint.x, i, endPoint.x, i - majTickLength);
		break;

	    case 1: // '\001'
		startPoint = plotarea.transform.point(plotarea.llX, plotarea.llY);
		int j = startPoint.x;
		endPoint = plotarea.transform.point(plotarea.llX, plotarea.urY);
		increment = getIncrement(endPoint.y, startPoint.y, numMajTicks);
		for(int j2 = 0; j2 < numMajTicks; j2++)
		    tickGc.drawLine(g, j, whereOnAxis(j2, 4), j - majTickLength, whereOnAxis(j2, 4));

		tickGc.drawLine(g, j, endPoint.y, j - majTickLength, endPoint.y);
		break;

	    case 2: // '\002'
		startPoint = plotarea.transform.point(plotarea.llX, plotarea.urY);
		int k = startPoint.y;
		endPoint = plotarea.transform.point(plotarea.urX, plotarea.urY);
		increment = getIncrement(endPoint.x, startPoint.x, numMajTicks);
		for(int k2 = 0; k2 <= numMajTicks; k2++)
		    tickGc.drawLine(g, whereOnAxis(k2, 4), k, whereOnAxis(k2, 4), k + majTickLength);

		tickGc.drawLine(g, endPoint.x, k, endPoint.x, k + majTickLength);
		break;

	    case 3: // '\003'
		startPoint = plotarea.transform.point(plotarea.urX, plotarea.llY);
		int l = startPoint.x;
		endPoint = plotarea.transform.point(plotarea.urX, plotarea.urY);
		increment = getIncrement(endPoint.y, startPoint.y, numMajTicks);
		for(int l2 = 0; l2 <= numMajTicks; l2++)
		    tickGc.drawLine(g, l, whereOnAxis(l2, 4), l + majTickLength, whereOnAxis(l2, 4));

		tickGc.drawLine(g, l, endPoint.y, l + majTickLength, endPoint.y);
		break;
	    }
	if(minTickVis)
	    switch(side)
	    {
	    case 0: // '\0'
		startPoint = plotarea.transform.point(plotarea.llX, plotarea.llY);
		int i1 = startPoint.y;
		endPoint = plotarea.transform.point(plotarea.urX, plotarea.llY);
		increment = getIncrement(endPoint.x, startPoint.x, numMinTicks);
		for(int i3 = 0; i3 < numMinTicks; i3++)
		    tickGc.drawLine(g, whereOnAxis(i3, 3), i1, whereOnAxis(i3, 3), i1 - minTickLength);

		tickGc.drawLine(g, endPoint.x, i1, endPoint.x, i1 - minTickLength);
		return;

	    case 1: // '\001'
		startPoint = plotarea.transform.point(plotarea.llX, plotarea.llY);
		int j1 = startPoint.x;
		endPoint = plotarea.transform.point(plotarea.llX, plotarea.urY);
		increment = getIncrement(endPoint.y, startPoint.y, numMinTicks);
		for(int j3 = 0; j3 < numMinTicks; j3++)
		    tickGc.drawLine(g, j1, whereOnAxis(j3, 3), j1 - minTickLength, whereOnAxis(j3, 3));

		tickGc.drawLine(g, j1, endPoint.y, j1 - minTickLength, endPoint.y);
		return;

	    case 2: // '\002'
		startPoint = plotarea.transform.point(plotarea.llX, plotarea.urY);
		int k1 = startPoint.y;
		endPoint = plotarea.transform.point(plotarea.urX, plotarea.urY);
		increment = getIncrement(endPoint.x, startPoint.x, numMinTicks);
		for(int k3 = 0; k3 < numMinTicks; k3++)
		    tickGc.drawLine(g, whereOnAxis(k3, 3), k1, whereOnAxis(k3, 3), k1 + minTickLength);

		return;

	    case 3: // '\003'
		startPoint = plotarea.transform.point(plotarea.urX, plotarea.llY);
		int l1 = startPoint.x;
		endPoint = plotarea.transform.point(plotarea.urX, plotarea.urY);
		increment = getIncrement(endPoint.y, startPoint.y, numMinTicks);
		for(int l3 = 0; l3 < numMinTicks; l3++)
		    tickGc.drawLine(g, l1, whereOnAxis(l3, 3), l1 + minTickLength, whereOnAxis(l3, 3));

		tickGc.drawLine(g, l1, endPoint.y, l1 + minTickLength, endPoint.y);
		return;
	    }
    }

    protected void drawGrids(Graphics g)
    {
	startPoint = plotarea.transform.point(plotarea.llX, plotarea.llY);
	endPoint = plotarea.transform.point(plotarea.urX, plotarea.urY);
	switch(side)
	{
	case 0: // '\0'
	case 2: // '\002'
	    increment = getIncrement(endPoint.x, startPoint.x, numGrids);
	    for(int i = 0; i < numGrids; i++)
		gridGc.drawLine(g, whereOnAxis(i, 2), startPoint.y, whereOnAxis(i, 2), endPoint.y);

	    gridGc.drawLine(g, endPoint.x, startPoint.y, endPoint.x, endPoint.y);
	    return;

	case 1: // '\001'
	case 3: // '\003'
	    increment = getIncrement(endPoint.y, startPoint.y, numGrids);
	    for(int j = 0; j < numGrids; j++)
		gridGc.drawLine(g, startPoint.x, whereOnAxis(j, 2), endPoint.x, whereOnAxis(j, 2));

	    gridGc.drawLine(g, startPoint.x, endPoint.y, endPoint.x, endPoint.y);
	    return;
	}
    }

    protected void draw3Dgrids(Graphics g)
    {
	startPoint = plotarea.transform.point(plotarea.llX, plotarea.llY);
	endPoint = plotarea.transform.point(plotarea.urX, plotarea.urY);
	switch(side)
	{
	case 0: // '\0'
	case 2: // '\002'
	    increment = getIncrement(endPoint.x, startPoint.x, numGrids);
	    for(int i = 0; i < numGrids; i++)
		gridGc.drawLine(g, whereOnAxis(i, 2), startPoint.y, whereOnAxis(i, 2) + globals.xOffset, startPoint.y + globals.yOffset);

	    gridGc.drawLine(g, endPoint.x, startPoint.y, endPoint.x + globals.xOffset, startPoint.y + globals.yOffset);
	    for(int j = 0; j < numGrids; j++)
		gridGc.drawLine(g, whereOnAxis(j, 2) + globals.xOffset, startPoint.y + globals.yOffset, whereOnAxis(j, 2) + globals.xOffset, endPoint.y + globals.yOffset);

	    gridGc.drawLine(g, endPoint.x + globals.xOffset, startPoint.y + globals.yOffset, endPoint.x + globals.xOffset, endPoint.y + globals.yOffset);
	    return;

	case 1: // '\001'
	case 3: // '\003'
	    increment = getIncrement(endPoint.y, startPoint.y, numGrids);
	    for(int k = 0; k < numGrids; k++)
		gridGc.drawLine(g, startPoint.x, whereOnAxis(k, 2), startPoint.x + globals.xOffset, whereOnAxis(k, 2) + globals.yOffset);

	    gridGc.drawLine(g, startPoint.x, endPoint.y, startPoint.x + globals.xOffset, endPoint.y + globals.yOffset);
	    for(int l = 0; l < numGrids; l++)
		gridGc.drawLine(g, startPoint.x + globals.xOffset, whereOnAxis(l, 2) + globals.yOffset, endPoint.x + globals.xOffset, whereOnAxis(l, 2) + globals.yOffset);

	    gridGc.drawLine(g, startPoint.x + globals.xOffset, endPoint.y + globals.yOffset, endPoint.x + globals.xOffset, endPoint.y + globals.yOffset);
	    return;
	}
    }

    protected String getLabel(double d, int i)
    {
	String s = Double.toString(d);
	int j = s.indexOf(".");
	if(j != -1)
	{
	    int k = s.length();
	    if(j == k - 2 && s.charAt(k - 1) == '0')
		s = s.substring(0, j);
	}
	if(labelFormat == 0)
	    return s;
	j = s.indexOf("e");
	if(j != -1)
	    s = Gc.nonSciNumberStr(s, j);
	return Gc.formattedLabel(s, labelFormat, labelPrecision);
    }

    protected void drawLabels(Graphics g)
    {
	g.setFont(labelFont);
	FontMetrics fontmetrics = g.getFontMetrics();
	g.setColor(labelColor);
	switch(side)
	{
	case 0: // '\0'
	    startPoint = plotarea.transform.point(plotarea.llX, plotarea.llY);
	    int i1 = startPoint.y - majTickLength - 2;
	    endPoint = plotarea.transform.point(plotarea.urX, plotarea.llY);
	    increment = getIncrement(endPoint.x, startPoint.x, numLabels);
	    double d = (axisEnd - axisStart) / (double)numLabels;
	    int i;
	    for(i = 0; i < numLabels; i++)
	    {
		String s = getLabel(axisStart + d * (double)i, i);
		lineGc.drawSmartString(g, whereOnAxis(i, 1), i1, side, labelAngle, fontmetrics, s);
	    }

	    if(!barScaling || !isXAxis)
	    {
		String s1 = getLabel(axisEnd, i);
		lineGc.drawSmartString(g, whereOnAxis(i, 1), i1, side, labelAngle, fontmetrics, s1);
	    }
	    break;

	case 1: // '\001'
	    startPoint = plotarea.transform.point(plotarea.llX, plotarea.llY);
	    endPoint = plotarea.transform.point(plotarea.llX, plotarea.urY);
	    increment = getIncrement(endPoint.y, startPoint.y, numLabels);
	    double d1 = (axisEnd - axisStart) / (double)numLabels;
	    int j;
	    for(j = 0; j < numLabels; j++)
	    {
		String s2 = getLabel(axisStart + d1 * (double)j, j);
		int j1 = startPoint.x - majTickLength - 2;
		lineGc.drawSmartString(g, j1, whereOnAxis(j, 1), side, labelAngle, fontmetrics, s2);
	    }

	    if(!barScaling || !isXAxis)
	    {
		String s3 = getLabel(axisEnd, j);
		int k1 = startPoint.x - majTickLength - 2;
		lineGc.drawSmartString(g, k1, whereOnAxis(j, 1), side, labelAngle, fontmetrics, s3);
	    }
	    break;

	case 2: // '\002'
	    startPoint = plotarea.transform.point(plotarea.llX, plotarea.urY);
	    int l1 = startPoint.y + majTickLength + 2 + fontmetrics.getMaxDescent();
	    endPoint = plotarea.transform.point(plotarea.urX, plotarea.urY);
	    increment = getIncrement(endPoint.x, startPoint.x, numLabels);
	    double d2 = (axisEnd - axisStart) / (double)numLabels;
	    int k;
	    for(k = 0; k < numLabels; k++)
	    {
		String s4 = getLabel(axisStart + d2 * (double)k, k);
		lineGc.drawSmartString(g, whereOnAxis(k, 1), l1, side, labelAngle, fontmetrics, s4);
	    }

	    if(!barScaling || !isXAxis)
	    {
		String s5 = getLabel(axisEnd, k);
		lineGc.drawSmartString(g, whereOnAxis(k, 1), l1, side, labelAngle, fontmetrics, s5);
	    }
	    break;

	case 3: // '\003'
	    startPoint = plotarea.transform.point(plotarea.urX, plotarea.llY);
	    endPoint = plotarea.transform.point(plotarea.urX, plotarea.urY);
	    increment = getIncrement(endPoint.y, startPoint.y, numLabels);
	    double d3 = (axisEnd - axisStart) / (double)numLabels;
	    int i2 = startPoint.x + majTickLength + 2;
	    int l;
	    for(l = 0; l < numLabels; l++)
	    {
		String s6 = getLabel(axisStart + d3 * (double)l, l);
		lineGc.drawSmartString(g, i2, whereOnAxis(l, 1), side, labelAngle, fontmetrics, s6);
	    }

	    if(!barScaling || !isXAxis)
	    {
		String s7 = getLabel(axisEnd, l);
		int j2 = startPoint.x + majTickLength + 2;
		lineGc.drawSmartString(g, j2, whereOnAxis(l, 1), side, labelAngle, fontmetrics, s7);
	    }
	    break;
	}
	if(titleString != null)
	    drawTitle(g, majTickLength + fontmetrics.getHeight() + 4);
    }

    protected void drawTitle(Graphics g, int i)
    {
	g.setFont(titleFont);
	FontMetrics fontmetrics = g.getFontMetrics();
	g.setColor(titleColor);
	switch(side)
	{
	case 0: // '\0'
	    Point point = plotarea.transform.point(plotarea.llX, plotarea.llY);
	    Point point4 = plotarea.transform.point(plotarea.urX, plotarea.llY);
	    lineGc.drawString(g, (point.x + (point4.x - point.x) / 2) - fontmetrics.stringWidth(titleString) / 2, point.y - i - fontmetrics.getHeight(), titleString);
	    return;

	case 1: // '\001'
	    Point point1 = plotarea.transform.point(plotarea.llX, plotarea.llY);
	    Point point5 = plotarea.transform.point(plotarea.llX, plotarea.urY);
	    lineGc.drawString(g, point1.x - fontmetrics.stringWidth(titleString) / 2, point5.y + 4, titleString);
	    return;

	case 2: // '\002'
	    Point point2 = plotarea.transform.point(plotarea.llX, plotarea.urY);
	    Point point6 = plotarea.transform.point(plotarea.urX, plotarea.urY);
	    lineGc.drawString(g, (point2.x + (point6.x - point2.x) / 2) - fontmetrics.stringWidth(titleString) / 2, point2.y + i, titleString);
	    return;

	case 3: // '\003'
	    Point point3 = plotarea.transform.point(plotarea.urX, plotarea.llY);
	    Point point7 = plotarea.transform.point(plotarea.urX, plotarea.urY);
	    lineGc.drawString(g, point3.x - fontmetrics.stringWidth(titleString) / 2, point7.y + 4, titleString);
	    return;
	}
    }

    boolean autoScale;
    double axisStart;
    double axisEnd;
    boolean majTickVis;
    boolean minTickVis;
    boolean gridVis;
    boolean labelVis;
    boolean lineVis;
    String titleString;
    Color titleColor;
    Font titleFont;
    boolean useDisplayList;
    Color labelColor;
    Font labelFont;
    int labelPrecision;
    int labelAngle;
    int labelFormat;
    Gc lineGc;
    Gc gridGc;
    Gc tickGc;
    int numMajTicks;
    int numGrids;
    int numMinTicks;
    int numLabels;
    int majTickLength;
    int minTickLength;
    int side;
    protected Globals globals;
    boolean isXAxis;
    boolean barScaling;
    protected int numAxLabels;
    protected Dataset datasets[];
    protected double stepSize;
    protected Plotarea plotarea;
    Point startPoint;
    Point endPoint;
    float increment;
    protected static final int axisLabels = 1;
    protected static final int gridLines = 2;
    protected static final int minTicks = 3;
    protected static final int majTicks = 4;
    final double NODIV = 5D;
}
