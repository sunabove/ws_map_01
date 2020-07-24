// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   PieChart.java

package javachart.chart;

import java.awt.Graphics;

// Referenced classes of package javachart.chart:
//            Chart, Pie, Plotarea, Dataset,
//            LegendInterface, Background, PieLegend

public class PieChart extends Chart
{

    public PieChart()
    {
    }

    public PieChart(String s)
    {
        super(s);
    }

    public PieChart(String s, Graphics g)
    {
        super(s, g);
    }

    public void drawGraph()
    {
        if(canvas == null)
        {
            return;
        } else
        {
            drawGraph(canvas);
            return;
        }
    }

    public void drawGraph(Graphics g)
    {
        if(g == null)
            return;
        super.drawGraph();
        background.draw(g);
        pie.draw(g);

        if(legendVisible)
            legend.draw(g);
    }

    public void addDataSet(String s, double ad[])
    {
        pie.dataset = new Dataset(s, ad, true, globals);
        datasets[0] = pie.dataset;
        PieLegend pielegend = (PieLegend)legend;
        pielegend.dataset = pie.dataset;
        numberOfDatasets = 1;
    }

    public void addDataSet(String s, double ad[], String as[])
    {
        pie.dataset = new Dataset(s, ad, as, true, globals);
        datasets[0] = pie.dataset;
        PieLegend pielegend = (PieLegend)legend;
        pielegend.dataset = pie.dataset;
        numberOfDatasets = 1;
    }

    public Pie getPie()
    {
        return pie;
    }

    public void setPie(Pie pie1)
    {
        pie = pie1;
    }

    void initChart()
    {
        initGlobals();
        background = new Background(globals);
        plotarea = new Plotarea(globals);
        initDataSets();
        pie = new Pie(null, plotarea, globals);
        legend = new PieLegend(datasets, globals);
        resize(640, 480);
    }

    Pie pie;
}
