// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LineChart.java

package javachart.chart;

import java.awt.Graphics;

// Referenced classes of package javachart.chart:
//            Chart, Background, AxisInterface, Axis, 
//            Line, LineLegend, Plotarea, LegendInterface

public class LineChart extends Chart
{

    public LineChart()
    {
        lineVisible = true;
    }

    public LineChart(String s)
    {
        super(s);
        lineVisible = true;
    }

    public LineChart(String s, Graphics g)
    {
        super(s, g);
        lineVisible = true;
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
        super.drawGraph(g);
        background.draw(g);
        plotarea.draw(g);
        if(xAxisVisible)
            xAxis.draw(g);
        if(yAxisVisible)
            yAxis.draw(g);
        line.draw(g);
        if(legendVisible)
            legend.draw(g);
    }

    public AxisInterface getXAxis()
    {
        return xAxis;
    }

    public AxisInterface getYAxis()
    {
        return yAxis;
    }

    public void setXAxis(AxisInterface axisinterface)
    {
        xAxis = axisinterface;
    }

    public void setYAxis(AxisInterface axisinterface)
    {
        yAxis = axisinterface;
    }

    public void setLineVisible(boolean flag)
    {
        lineVisible = flag;
        if(flag)
        {
            line.scatterPlot = false;
            return;
        } else
        {
            line.scatterPlot = true;
            return;
        }
    }

    public boolean getLineVisible()
    {
        return lineVisible;
    }

    public Line getLine()
    {
        return line;
    }

    public void setLine(Line line1)
    {
        line = line1;
    }

    protected void initChart()
    {
        initGlobals();
        plotarea = new Plotarea(globals);
        background = new Background(globals);
        initDataSets();
        initAxes();
        line = new Line(datasets, xAxis, yAxis, plotarea);
        legend = new LineLegend(datasets, globals);
        resize(640, 480);
    }

    protected void initAxes()
    {
        xAxis = new Axis(datasets, true, plotarea);
        yAxis = new Axis(datasets, false, plotarea);
    }

    Line line;
    AxisInterface xAxis;
    AxisInterface yAxis;
    boolean lineVisible;
}
