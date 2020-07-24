// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Globals.java

package javachart.chart;

import java.awt.Image;

// Referenced classes of package javachart.chart:
//            DisplayList, RotateString

public class Globals
{

    public Globals()
    {
        xOffset = 15;
        yOffset = 15;
        threeD = false;
        useDisplayList = false;
    }

    public Globals(int i, int j, int k, boolean flag, RotateString rotatestring, Image image1)
    {
        xOffset = 15;
        yOffset = 15;
        threeD = false;
        useDisplayList = false;
        maxY = i;
        xOffset = j;
        yOffset = k;
        threeD = flag;
        stringRotator = rotatestring;
        image = image1;
    }

    public int getXOffset()
    {
        return xOffset;
    }

    public void setXOffset(int i)
    {
        xOffset = i;
    }

    public int getYOffset()
    {
        return yOffset;
    }

    public void setYOffset(int i)
    {
        yOffset = i;
    }

    public boolean isThreeD()
    {
        return threeD;
    }

    public void setThreeD(boolean flag)
    {
        threeD = flag;
    }

    public int getMaxY()
    {
        return maxY;
    }

    public void setMaxY(int i)
    {
        maxY = i;
    }

    public RotateString getStringRotator()
    {
        return stringRotator;
    }

    public void setStringRotator(RotateString rotatestring)
    {
        stringRotator = rotatestring;
    }

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image1)
    {
        image = image1;
    }

    public boolean getUseDisplayList()
    {
        return useDisplayList;
    }

    public void setUseDisplayList(boolean flag)
    {
        if(flag && displayList == null)
            displayList = new DisplayList(this);
        useDisplayList = flag;
    }

    public DisplayList getDisplayList()
    {
        return displayList;
    }

    public void setDisplayList(DisplayList displaylist)
    {
        displayList = displaylist;
    }

    int xOffset;
    int yOffset;
    boolean threeD;
    int maxY;
    int maxX;
    RotateString stringRotator;
    transient Image image;
    boolean useDisplayList;
    transient DisplayList displayList;
}
