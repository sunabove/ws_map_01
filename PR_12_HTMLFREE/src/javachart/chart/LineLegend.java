// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LineLegend.java

package javachart.chart;

import java.awt.*;

// Referenced classes of package javachart.chart:
//            Legend, Gc, Dataset, LegendInterface, 
//            Transform, Globals

public class LineLegend extends Legend
    implements LegendInterface
{

    public LineLegend(Dataset adataset[], Globals globals)
    {
        super(adataset, globals);
    }

    protected synchronized void doVerticalIcons(Graphics g)
    {
        g.setFont(labelFont);
        int j = datasetsInUse();
        double d = llX + 0.02D;
        double d1 = llY + 0.02D + iconHeight / 2D;
        double d2 = d + iconWidth;
        double d3 = d1;
        for(int i = j; i > 0; i--)
        {
            datasets[i - 1].gc.drawLine(g, transform.point(d, d1), transform.point(d2, d3));
            datasets[i - 1].gc.drawImage(g, transform.point(d + iconWidth / 2D, d1));
            Point point = transform.point(d2, d1);
            g.setColor(labelColor);
            backgroundGc.drawString(g, point.x + 4, point.y - 4, datasets[i - 1].setName);
            d1 += iconHeight + iconGap;
            d3 = d1;
        }

    }

    protected synchronized void doHorizontalIcons(Graphics g)
    {
        g.setFont(labelFont);
        int j = datasetsInUse();
        double d = llX + 0.02D;
        double d1 = llY + 0.02D + iconHeight / 2D;
        double d2 = d + iconWidth;
        double d3 = d1;
        for(int i = 0; i < j; i++)
        {
            datasets[i].gc.drawLine(g, transform.point(d, d1), transform.point(d2, d3));
            datasets[i].gc.drawImage(g, transform.point(d + iconWidth / 2D, d1));
            Point point = transform.point(d2, d1);
            g.setColor(labelColor);
            backgroundGc.drawString(g, point.x + 3, point.y - 3, datasets[i].setName);
            FontMetrics fontmetrics = g.getFontMetrics();
            d += iconWidth + iconGap + iconGap + iconGap + (double)fontmetrics.stringWidth(datasets[i].setName) / (double)gWidth;
            d2 = d + iconWidth;
        }

    }
}
