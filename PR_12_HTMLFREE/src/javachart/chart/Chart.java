// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Chart.java

package javachart.chart;

import java.awt.Graphics;
import java.awt.Image;
import java.io.PrintStream;

// Referenced classes of package javachart.chart:
//            DisplayList, Globals, Dataset, ChartInterface, 
//            LegendInterface, Plotarea, Background, AxisInterface, 
//            RotateString

public abstract class Chart
    implements ChartInterface
{

    public Chart()
    {
        name = "New Chart";
        legendVisible = false;
        width = 640;
        height = 480;
        xAxisVisible = true;
        yAxisVisible = true;
        routineName = "Initialization Value";
        initChart();
    }

    public Chart(String s)
    {
        name = "New Chart";
        legendVisible = false;
        width = 640;
        height = 480;
        xAxisVisible = true;
        yAxisVisible = true;
        routineName = "Initialization Value";
        name = s;
        initChart();
    }

    public Chart(String s, Graphics g)
    {
        name = "New Chart";
        legendVisible = false;
        width = 640;
        height = 480;
        xAxisVisible = true;
        yAxisVisible = true;
        routineName = "Initialization Value";
        name = s;
        if(g == null)
        {
            return;
        } else
        {
            canvas = g;
            initChart();
            return;
        }
    }

    public void paint()
    {
        drawGraph();
    }

    public void paint(Graphics g)
    {
        drawGraph(g);
    }

    public void drawGraph()
    {
        if(globals.useDisplayList)
            globals.displayList.clear();
    }

    public void drawGraph(Graphics g)
    {
        if(globals.useDisplayList)
            globals.displayList.clear();
    }

    public void addDataSet(String s, double ad[])
    {
        if(numberOfDatasets < 40)
        {
            datasets[numberOfDatasets] = new Dataset(s, ad, numberOfDatasets, globals);
            numberOfDatasets++;
            return;
        } else
        {
            System.out.println("max datasets is " + 40);
            return;
        }
    }

    public void addDataSet(String s, double ad[], double ad1[])
    {
        if(numberOfDatasets < 40)
        {
            datasets[numberOfDatasets] = new Dataset(s, ad, ad1, numberOfDatasets, globals);
            numberOfDatasets++;
            return;
        } else
        {
            System.out.println("max datasets is " + 40);
            return;
        }
    }

    public void addDataSet(String s, double ad[], double ad1[], double ad2[], double ad3[])
    {
        if(numberOfDatasets < 40)
        {
            datasets[numberOfDatasets] = new Dataset(s, ad, ad1, ad2, ad3, numberOfDatasets, globals);
            numberOfDatasets++;
            return;
        } else
        {
            System.out.println("maximum number of datasets is " + 40);
            return;
        }
    }

    public void addDataSet(String s, double ad[], String as[])
    {
        if(numberOfDatasets < 40)
        {
            datasets[numberOfDatasets] = new Dataset(s, ad, as, numberOfDatasets, globals);
            numberOfDatasets++;
            return;
        } else
        {
            System.out.println("maximum number of datasets is " + 40);
            return;
        }
    }

    public void addDataSet(String s, double ad[], double ad1[], String as[])
    {
        addDataSet(s, ad, as);
    }

    public void deleteDataSet(String s)
    {
        for(int i = 0; i < numberOfDatasets; i++)
            if(datasets[i].setName == s)
            {
                datasets[i] = null;
                for(int j = i + 1; j < numberOfDatasets; j++)
                    datasets[j - 1] = datasets[j];

            }

        numberOfDatasets--;
    }

    public int getNumDatasets()
    {
        return numberOfDatasets;
    }

    public void resize(int i, int j)
    {
        globals.maxY = j;
        plotarea.resize(i, j);
        background.resize(i, j);
        legend.resize(i, j);
        globals.maxX = i;
        globals.maxY = j;
    }

    protected void initGlobals()
    {
        globals = new Globals();
    }

    protected void initDataSets()
    {
        datasets = new Dataset[40];
    }

    public String getName()
    {
        return name;
    }

    public void setName(String s)
    {
        name = s;
    }

    public Graphics getCanvas()
    {
        return canvas;
    }

    public void setCanvas(Graphics g)
    {
        canvas = g;
    }

    public boolean isLegendVisible()
    {
        return legendVisible;
    }

    public void setLegendVisible(boolean flag)
    {
        legendVisible = flag;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int i)
    {
        width = i;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int i)
    {
        height = i;
    }

    public AxisInterface getXAxis()
    {
        return null;
    }

    public void setXAxis(AxisInterface axisinterface)
    {
    }

    public AxisInterface getYAxis()
    {
        return null;
    }

    public void setYAxis(AxisInterface axisinterface)
    {
    }

    public boolean isXAxisVisible()
    {
        return xAxisVisible;
    }

    public void setXAxisVisible(boolean flag)
    {
        xAxisVisible = flag;
    }

    public boolean isYAxisVisible()
    {
        return yAxisVisible;
    }

    public void setYAxisVisible(boolean flag)
    {
        yAxisVisible = flag;
    }

    public Dataset[] getDatasets()
    {
        return datasets;
    }

    public void setDatasets(Dataset adataset[])
    {
        datasets = adataset;
    }

    public Plotarea getPlotarea()
    {
        return plotarea;
    }

    public void setPlotarea(Plotarea plotarea1)
    {
        plotarea = plotarea1;
    }

    public Background getBackground()
    {
        return background;
    }

    public void setBackground(Background background1)
    {
        background = background1;
    }

    public LegendInterface getLegend()
    {
        return legend;
    }

    public void setLegend(LegendInterface legendinterface)
    {
        legend = legendinterface;
    }

    public Globals getGlobals()
    {
        return globals;
    }

    public void setGlobals(Globals globals1)
    {
        globals = globals1;
    }

    public boolean isThreeD()
    {
        return globals.threeD;
    }

    public void setThreeD(boolean flag)
    {
        globals.threeD = flag;
    }

    public int getXOffset()
    {
        return globals.xOffset;
    }

    public void setXOffset(int i)
    {
        globals.xOffset = i;
    }

    public int getYOffset()
    {
        return globals.yOffset;
    }

    public void setYOffset(int i)
    {
        globals.yOffset = i;
    }

    public RotateString getStringRotator()
    {
        return globals.stringRotator;
    }

    public void setStringRotator(RotateString rotatestring)
    {
        globals.stringRotator = rotatestring;
    }

    public Image getImage()
    {
        return globals.getImage();
    }

    public void setImage(Image image)
    {
        globals.setImage(image);
    }

    public DisplayList getDisplayList()
    {
        return globals.getDisplayList();
    }

    public void setDisplayList(DisplayList displaylist)
    {
        globals.setDisplayList(displaylist);
    }

    public boolean getUseDisplayList()
    {
        return globals.getUseDisplayList();
    }

    public void setUseDisplayList(boolean flag)
    {
        globals.setUseDisplayList(flag);
    }

    void initChart()
    {
    }

    String name;
    Graphics canvas;
    boolean legendVisible;
    int width;
    int height;
    boolean xAxisVisible;
    boolean yAxisVisible;
    Globals globals;
    Dataset datasets[];
    Plotarea plotarea;
    Background background;
    LegendInterface legend;
    int numberOfDatasets;
    String routineName;
    int errNo;
    static final int MAX_DATASETS = 40;
}
