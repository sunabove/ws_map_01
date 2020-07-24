// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChartInterface.java

package javachart.chart;

import java.awt.Graphics;
import java.awt.Image;

// Referenced classes of package javachart.chart:
//            Dataset, Plotarea, Background, AxisInterface, 
//            LegendInterface, Globals, RotateString, DisplayList

public interface ChartInterface
{

    public abstract void drawGraph();

    public abstract void drawGraph(Graphics g);

    public abstract void addDataSet(String s, double ad[]);

    public abstract void addDataSet(String s, double ad[], String as[]);

    public abstract void addDataSet(String s, double ad[], double ad1[]);

    public abstract void addDataSet(String s, double ad[], double ad1[], String as[]);

    public abstract void addDataSet(String s, double ad[], double ad1[], double ad2[], double ad3[]);

    public abstract void deleteDataSet(String s);

    public abstract int getNumDatasets();

    public abstract void resize(int i, int j);

    public abstract String getName();

    public abstract void setName(String s);

    public abstract Graphics getCanvas();

    public abstract void setCanvas(Graphics g);

    public abstract boolean isLegendVisible();

    public abstract void setLegendVisible(boolean flag);

    public abstract int getWidth();

    public abstract void setWidth(int i);

    public abstract int getHeight();

    public abstract void setHeight(int i);

    public abstract boolean isXAxisVisible();

    public abstract void setXAxisVisible(boolean flag);

    public abstract boolean isYAxisVisible();

    public abstract void setYAxisVisible(boolean flag);

    public abstract Dataset[] getDatasets();

    public abstract void setDatasets(Dataset adataset[]);

    public abstract Plotarea getPlotarea();

    public abstract void setPlotarea(Plotarea plotarea);

    public abstract Background getBackground();

    public abstract void setBackground(Background background);

    public abstract AxisInterface getXAxis();

    public abstract void setXAxis(AxisInterface axisinterface);

    public abstract AxisInterface getYAxis();

    public abstract void setYAxis(AxisInterface axisinterface);

    public abstract LegendInterface getLegend();

    public abstract void setLegend(LegendInterface legendinterface);

    public abstract Globals getGlobals();

    public abstract void setGlobals(Globals globals);

    public abstract boolean isThreeD();

    public abstract void setThreeD(boolean flag);

    public abstract int getXOffset();

    public abstract void setXOffset(int i);

    public abstract int getYOffset();

    public abstract void setYOffset(int i);

    public abstract RotateString getStringRotator();

    public abstract void setStringRotator(RotateString rotatestring);

    public abstract Image getImage();

    public abstract void setImage(Image image);

    public abstract DisplayList getDisplayList();

    public abstract void setDisplayList(DisplayList displaylist);

    public abstract boolean getUseDisplayList();

    public abstract void setUseDisplayList(boolean flag);
}
