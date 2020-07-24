// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   BarChart.java

package javachart.chart;

import java.awt.Graphics;

// Referenced classes of package javachart.chart:
//            Chart, LabelAxis, Plotarea, AxisInterface, 
//            LegendInterface, Legend, Bar, Axis, 
//            Background

public class BarChart extends Chart
{

    public BarChart()
    {
    }

    public BarChart(String s)
    {
        super(s);
    }

    public BarChart(String s, Graphics g)
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
        plotarea.draw(g);
        if(xAxisVisible)
            xAxis.draw(g);
        else
            xAxis.scale();
        if(yAxisVisible)
            yAxis.draw(g);
        else
            yAxis.scale();
        bar.draw(g);
        if(legendVisible)
            legend.draw(g);
    }

    public Bar getBar()
    {
        return bar;
    }

    public void setBar(Bar bar1)
    {
        bar = bar1;
    }

    public AxisInterface getXAxis()
    {
        return xAxis;
    }

    public void setXAxis(LabelAxis labelaxis)
    {
        xAxis = labelaxis;
    }

    public AxisInterface getYAxis()
    {
        return yAxis;
    }

    public void setYAxis(Axis axis)
    {
        yAxis = axis;
    }

    void initChart()
    {
        initGlobals();
        plotarea = new Plotarea(globals);
        background = new Background(globals);
        initDataSets();
        initAxes();
        bar = new Bar(datasets, xAxis, yAxis, plotarea);
        legend = new Legend(datasets, globals);
        resize(640, 480);
    }

    void initAxes()
    {
        xAxis = new LabelAxis(datasets, true, plotarea);
        xAxis.setBarScaling(true);
        yAxis = new Axis(datasets, false, plotarea);
        yAxis.setBarScaling(true);
    }

    Bar bar;
    AxisInterface xAxis;
    AxisInterface yAxis;
}
