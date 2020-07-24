// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3)
// Source File Name:   Dataset.java

package javachart.chart;

import java.awt.Color;
import java.awt.Font;
import java.io.PrintStream;
import java.util.Vector;

// Referenced classes of package javachart.chart:
//            Globals, Gc, Datum

public class Dataset
{

    public Dataset()
    {
	this("new", xarr, yarr, new Globals());
    }

    public Dataset(String s, double ad[], double ad1[], Globals globals1)
    {
	labelColor = Color.black;
	init(s, -1, globals1);
	for(int i = 0; i < ad.length; i++)
	    data.addElement(new Datum(ad[i], ad1[i], globals1));

    }

    public Dataset(String s, double ad[], double ad1[], int i, Globals globals1)
    {
	labelColor = Color.black;
	init(s, i, globals1);
	for(int j = 0; j < ad.length; j++)
	    data.addElement(new Datum(ad[j], ad1[j], i, globals1));

    }

    public Dataset(String s, double ad[], int i, Globals globals1)
    {
	labelColor = Color.black;
	init(s, i, globals1);
	for(int j = 0; j < ad.length; j++)
	    data.addElement(new Datum(j, ad[j], i, globals1));

    }

    public Dataset(String s, double ad[], String as[], int i, Globals globals1)
    {
	labelColor = Color.black;
	init(s, i, globals1);
	for(int j = 0; j < ad.length; j++)
	    try
	    {
		data.addElement(new Datum(j, ad[j], as[j], i, globals1));
	    }
	    catch(ArrayIndexOutOfBoundsException _ex)
	    {
		data.addElement(new Datum(j, ad[j], "", i, globals1));
	    }

    }

    public Dataset(String s, double ad[], double ad1[], String as[], int i, Globals globals1)
    {
	labelColor = Color.black;
	init(s, i, globals1);
	for(int j = 0; j < ad1.length; j++)
	    data.addElement(new Datum(ad[j], ad1[j], as[j], i, globals1));

    }

    public Dataset(String s, double ad[], double ad1[], double ad2[], String as[], int i, Globals globals1)
    {
	labelColor = Color.black;
	init(s, i, globals1);
	for(int j = 0; j < ad1.length; j++)
	    try
	    {
		data.addElement(new Datum(ad[j], ad1[j], ad2[j], as[j], i, globals1));
	    }
	    catch(ArrayIndexOutOfBoundsException _ex)
	    {
		return;
	    }

    }

    public Dataset(String s, double ad[], double ad1[], double ad2[], int i, Globals globals1)
    {
	labelColor = Color.black;
	init(s, i, globals1);
	for(int j = 0; j < ad1.length; j++)
	    data.addElement(new Datum(ad[j], ad1[j], ad2[j], i, globals1));

    }

    public Dataset(String s, double ad[], double ad1[], double ad2[], double ad3[], int i, Globals globals1)
    {
	labelColor = Color.black;
	init(s, i, globals1);
	for(int j = 0; j < ad1.length; j++)
	    data.addElement(new Datum(ad[j], ad1[j], ad2[j], ad3[j], globals1));

    }

    public Dataset(String s, double ad[], String as[], boolean flag, Globals globals1)
    {
	labelColor = Color.black;
	init(s, 1, globals1);
	for(int i = 0; i < ad.length; i++)
	    try
	    {
		data.addElement(new Datum(i, ad[i], as[i], flag, globals1));
	    }
	    catch(ArrayIndexOutOfBoundsException _ex)
	    {
		data.addElement(new Datum(i, ad[i], flag, globals1));
	    }

    }

    public Dataset(String s, double ad[], boolean flag, Globals globals1)
    {
	labelColor = Color.black;
	init(s, 1, globals1);
	for(int i = 0; i < ad.length; i++)
	    data.addElement(new Datum(i, ad[i], flag, globals1));

    }

    public synchronized void replaceYData(double ad[])
    {
	int l = data.size();
	if(ad.length > l)
	{
	    for(int i = 0; i < l; i++)
		((Datum)data.elementAt(i)).y = ad[i];

	    for(int j = l; j < ad.length; j++)
		data.addElement(new Datum(0.0D, ad[j], 0.0D, 0.0D, globals));

	    return;
	}
	for(int k = 0; k < ad.length; k++)
	    ((Datum)data.elementAt(k)).y = ad[k];

    }

    public synchronized void replaceY2Data(double ad[])
    {
	int l = data.size();
	if(ad.length > l)
	{
	    for(int i = 0; i < l; i++)
		((Datum)data.elementAt(i)).y2 = ad[i];

	    for(int j = l; j < ad.length; j++)
		data.addElement(new Datum(0.0D, 0.0D, ad[j], 0.0D, globals));

	    return;
	}
	for(int k = 0; k < ad.length; k++)
	    ((Datum)data.elementAt(k)).y2 = ad[k];

    }

    public synchronized void replaceY3Data(double ad[])
    {
	int l = data.size();
	if(ad.length > l)
	{
	    for(int i = 0; i < l; i++)
		((Datum)data.elementAt(i)).y3 = ad[i];

	    for(int j = l; j < ad.length; j++)
		data.addElement(new Datum(0.0D, 0.0D, 0.0D, ad[j], globals));

	    return;
	}
	for(int k = 0; k < ad.length; k++)
	    ((Datum)data.elementAt(k)).y3 = ad[k];

    }

    public synchronized void replaceXData(double ad[])
    {
	int l = data.size();
	if(ad.length > l)
	{
	    for(int i = 0; i < l; i++)
		((Datum)data.elementAt(i)).x = ad[i];

	    for(int j = l; j < ad.length; j++)
		data.addElement(new Datum(ad[j], 0.0D, globals));

	    return;
	}
	for(int k = 0; k < ad.length; k++)
	    ((Datum)data.elementAt(k)).x = ad[k];

    }

    public Vector getData()
    {
	return data;
    }

    public void setData(Vector vector)
    {
	data = vector;
    }

    public String getName()
    {
	return setName;
    }

    public void setName(String s)
    {
	setName = s;
    }

    public Gc getGc()
    {
	return gc;
    }

    public void setGc(Gc gc1)
    {
	gc = gc1;
    }

    public Color getLabelColor()
    {
	return labelColor;
    }

    public void setLabelColor(Color color)
    {
	labelColor = color;
    }

    public Font getLabelFont()
    {
	return labelFont;
    }

    public void setLabelFont(Font font)
    {
	labelFont = font;
    }

    Globals getGlobals()
    {
	return globals;
    }

    public void setGlobals(Globals globals1)
    {
	globals = globals1;
    }

    public synchronized double minX()
    {
	double d = ((Datum)data.elementAt(0)).x;
	int j = data.size();
	for(int i = 1; i < j; i++)
	    if(d > ((Datum)data.elementAt(i)).x)
		d = ((Datum)data.elementAt(i)).x;

	return d;
    }

    public synchronized double minY()
    {
	double d = ((Datum)data.elementAt(0)).y;
	int j = data.size();
	for(int i = 1; i < j; i++)
	    if(d > ((Datum)data.elementAt(i)).y)
		d = ((Datum)data.elementAt(i)).y;

	return d;
    }

    public synchronized double minY2()
    {
	double d = ((Datum)data.elementAt(0)).y2;
	int j = data.size();
	for(int i = 1; i < j; i++)
	    if(d > ((Datum)data.elementAt(i)).y2)
		d = ((Datum)data.elementAt(i)).y2;

	return d;
    }

    public synchronized double maxX()
    {
	double d = ((Datum)data.elementAt(0)).x;
	int j = data.size();
	for(int i = 1; i < j; i++)
	    if(d < ((Datum)data.elementAt(i)).x)
		d = ((Datum)data.elementAt(i)).x;

	return d;
    }

    public synchronized double maxY()
    {
	double d = ((Datum)data.elementAt(0)).y;
	int j = data.size();
	for(int i = 1; i < j; i++)
	    if(d < ((Datum)data.elementAt(i)).y)
		d = ((Datum)data.elementAt(i)).y;

	return d;
    }

    public synchronized double maxY2()
    {
	double d = ((Datum)data.elementAt(0)).y2;
	int j = data.size();
	for(int i = 1; i < j; i++)
	    if(d < ((Datum)data.elementAt(i)).y2)
		d = ((Datum)data.elementAt(i)).y2;

	return d;
    }

    public synchronized double[] getXValues()
    {
	double ad[] = new double[data.size()];
	for(int i = 0; i < data.size(); i++)
	    ad[i] = ((Datum)data.elementAt(i)).x;

	return ad;
    }

    public synchronized double[] getYValues()
    {
	double ad[] = new double[data.size()];
	for(int i = 0; i < data.size(); i++)
	    ad[i] = ((Datum)data.elementAt(i)).y;

	return ad;
    }

    public synchronized double[] getY2Values()
    {
	double ad[] = new double[data.size()];
	for(int i = 0; i < data.size(); i++)
	    ad[i] = ((Datum)data.elementAt(i)).y2;

	return ad;
    }

    public synchronized double[] getY3Values()
    {
	double ad[] = new double[data.size()];
	for(int i = 0; i < data.size(); i++)
	    ad[i] = ((Datum)data.elementAt(i)).y3;

	return ad;
    }

    public Datum getDataElementAt(int i)
    {
	return (Datum)data.elementAt(i);
    }

    public void dump()
    {
	for(int i = 0; i < data.size(); i++)
	    System.out.println("X " + ((Datum)data.elementAt(i)).x + " Y " + ((Datum)data.elementAt(i)).y + " Y2 " + ((Datum)data.elementAt(i)).y2);

    }

    private void init(String s, int i, Globals globals1)
    {
	setName = s;
	data = new Vector();
	globals = globals1;
	if(i == -1)
	    gc = new Gc(globals1);
	else
	    gc = new Gc(i, globals1);
	labelFont = htmleditor.AppRegistry.CHART_DEFAULT_FONT;
	labelColor = Color.black;
    }

    Vector data;
    String setName;
    Gc gc;
    Color labelColor;
    Font labelFont;
    private Globals globals;
    static double xarr[] = {
	1.0D
    };
    static double yarr[] = {
	1.0D
    };

}
