// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AxisInterface.java

package javachart.chart;

import java.awt.*;

// Referenced classes of package javachart.chart:
//            Dataset, Gc

public interface AxisInterface
{

    public abstract void draw(Graphics g);

    public abstract void addLabels(String as[]);

    public abstract void scale();

    public abstract Dataset[] getDatasets();

    public abstract void setDatasets(Dataset adataset[]);

    public abstract boolean getAutoScale();

    public abstract void setAutoScale(boolean flag);

    public abstract double getAxisStart();

    public abstract void setAxisStart(double d);

    public abstract double getAxisEnd();

    public abstract void setAxisEnd(double d);

    public abstract boolean getLineVis();

    public abstract void setLineVis(boolean flag);

    public abstract boolean getMajTickVis();

    public abstract void setMajTickVis(boolean flag);

    public abstract boolean getMinTickVis();

    public abstract void setMinTickVis(boolean flag);

    public abstract boolean getGridVis();

    public abstract void setGridVis(boolean flag);

    public abstract String getTitleString();

    public abstract void setTitleString(String s);

    public abstract Color getTitleColor();

    public abstract void setTitleColor(Color color);

    public abstract Font getTitleFont();

    public abstract void setTitleFont(Font font);

    public abstract boolean getLabelVis();

    public abstract void setLabelVis(boolean flag);

    public abstract Color getLabelColor();

    public abstract void setLabelColor(Color color);

    public abstract Font getLabelFont();

    public abstract void setLabelFont(Font font);

    public abstract int getLabelPrecision();

    public abstract void setLabelPrecision(int i);

    public abstract int getLabelFormat();

    public abstract void setLabelFormat(int i);

    public abstract int getLabelAngle();

    public abstract void setLabelAngle(int i);

    public abstract Gc getLineGc();

    public abstract void setLineGc(Gc gc);

    public abstract Gc getGridGc();

    public abstract void setGridGc(Gc gc);

    public abstract Gc getTickGc();

    public abstract void setTickGc(Gc gc);

    public abstract int getNumMajTicks();

    public abstract void setNumMajTicks(int i);

    public abstract int getNumGrids();

    public abstract void setNumGrids(int i);

    public abstract int getNumMinTicks();

    public abstract void setNumMinTicks(int i);

    public abstract int getNumLabels();

    public abstract void setNumLabels(int i);

    public abstract int getMajTickLength();

    public abstract void setMajTickLength(int i);

    public abstract int getMinTickLength();

    public abstract void setMinTickLength(int i);

    public abstract int getSide();

    public abstract void setSide(int i);

    public abstract boolean getBarScaling();

    public abstract void setBarScaling(boolean flag);

    public abstract boolean getUseDisplayList();

    public abstract void setUseDisplayList(boolean flag);
}
