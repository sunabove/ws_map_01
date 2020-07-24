// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LegendInterface.java

package javachart.chart;

import java.awt.*;

// Referenced classes of package javachart.chart:
//            Gc

public interface LegendInterface
{

    public abstract void draw(Graphics g);

    public abstract void resize(int i, int j);

    public abstract Gc getBackgroundGc();

    public abstract void setBackgroundGC(Gc gc);

    public abstract Font getLabelFont();

    public abstract void setLabelFont(Font font);

    public abstract Color getLabelColor();

    public abstract void setLabelColor(Color color);

    public abstract boolean getVerticalLayout();

    public abstract void setVerticalLayout(boolean flag);

    public abstract boolean getBackgroundVisible();

    public abstract void setBackgroundVisible(boolean flag);

    public abstract double getLlX();

    public abstract void setLlX(double d);

    public abstract double getLlY();

    public abstract void setLlY(double d);

    public abstract double getUrX();

    public abstract double setUrY();

    public abstract String toString();

    public abstract double getIconWidth();

    public abstract void setIconWidth(double d);

    public abstract double getIconHeight();

    public abstract void setIconHeight(double d);

    public abstract double getIconGap();

    public abstract void setIconGap(double d);

    public abstract boolean getUseDisplayList();

    public abstract void setUseDisplayList(boolean flag);
}
