// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   PieLegend.java

package javachart.chart;

import java.awt.*;
import java.util.Vector;

// Referenced classes of package javachart.chart:
//            Legend, Gc, Datum, LegendInterface,
//            Transform, Dataset, Globals

public class PieLegend extends Legend
    implements LegendInterface
{

    public PieLegend(Dataset adataset[], Globals globals)
    {
        super(adataset, globals);
        datasets = adataset;
    }

    protected synchronized void doVerticalIcons(Graphics g)
    {
        g.setFont(labelFont);
        int j = dataset.data.size();
        double d = llX + 0.02D;
        double d1 = llY + 0.02D;
        double d2 = d + iconWidth;
        double d3 = d1 + iconHeight;
        for(int i = j - 1; i >= 0; i--)
        {
            dataset.getDataElementAt(i).gc.fillRect(g, transform.point(d, d1), transform.point(d2, d3));
            Point point = transform.point(d2, d1);
            g.setColor(labelColor);
            backgroundGc.drawString(g, point.x + 2, point.y + 2, dataset.getDataElementAt(i).label);
            d1 += iconHeight + iconGap;
            d3 = d1 + iconHeight;
        }

    }

    protected synchronized void doHorizontalIcons(Graphics g)
    {
        g.setFont(labelFont);
        int j = dataset.data.size();
        double d = llX + 0.02D;
        double d1 = llY + 0.02D;
        double d2 = d + iconWidth;
        double d3 = d1 + iconHeight;
        for(int i = 0; i < j; i++)
        {
            dataset.getDataElementAt(i).gc.fillRect(g, transform.point(d, d1), transform.point(d2, d3));
            Point point = transform.point(d2, d1);
            g.setColor(labelColor);
            backgroundGc.drawString(g, point.x + 2, point.y, dataset.getDataElementAt(i).label);
            FontMetrics fontmetrics = g.getFontMetrics();
            d += iconWidth + iconGap + iconGap + (double)fontmetrics.stringWidth( dataset.getDataElementAt(i).label) / (double)gWidth;
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
        dataset = datasets[0];
        int k = dataset.data.size();
        if(verticalLayout)
        {
            for(int i = 0; i < k; i++)
            {
                try
                {
                    l = fontmetrics.stringWidth(dataset.getDataElementAt(i).label);
                }
                catch(NullPointerException _ex)
                {
                    l = 0;
                }
                if(l > i1)
                    i1 = l;
                d += iconGap + iconHeight;
            }

            urX = llX + iconWidth + 0.040000000000000001D + (double)l / (double)gWidth;
            urY = llY + d + 0.040000000000000001D + d / (double)gHeight;
            return;
        }
        for(int j = 0; j < k; j++)
            try
            {
                i1 += fontmetrics.stringWidth(dataset.getDataElementAt(j).label);
            }
            catch(NullPointerException _ex) { }

        urX = llX + (double)l + 0.040000000000000001D + (double)k * (iconWidth + iconGap) + iconWidth + iconGap + (double)i1 / (double)gWidth;
        urY = llY + d + 0.040000000000000001D + iconHeight;
    }

    Dataset dataset;
}
