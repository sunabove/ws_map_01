package jchartui;

import java.io.PrintStream;
import java.util.ResourceBundle;
import java.util.*;
//import jcalc.*;

public class JCalcResource {

/*
//	static String drawingMenuItemStr[][];
	static String menuItemStr[][];
	static String tooltipStr[][];
	static String toolBarStr[][];
	static String formatCellsStr[][];
	static String formatCellsFStyleStr[][];
	static String toolItemStr[][];
	//=============	COCOER (2000-10/-27)===============================================
	//	FunctionDialog
	static String itemsStr[];
	static String mostRecentlyUsedStr[];
	static String allStr[];
	static String financialStr[];
	static String dateTimeStr[];
	static String mathTrigStr[];
	static String statisticalStr[];
	static String lookupReferencesStr[];
	static String databaseStr[];
	static String textStr[];
	static String logicalStr[];
	static String informationStr[];
	static String engineeringStr[];

	//=======================	COCOER (2000-10/-27)===================================
	//	FunctionDialog
	public static final String getItemsString(int i) {
		if(itemsStr == null)
			return "";
		else
			return itemsStr[i];
	}
	public static final String getMostRecentlyUsedString(int i) {
		if(mostRecentlyUsedStr == null)
			return "";
		else
			return mostRecentlyUsedStr[i];
	}
	public static final String getAllString(int i) {
		if(allStr == null)
			return "";
		else
			return allStr[i];
	}
	public static final String getFinancialString(int i) {
		if(financialStr == null)
			return "";
		else
			return financialStr[i];
	}
	public static final String getDateTimeString(int i) {
		if(dateTimeStr == null)
			return "";
		else
			return dateTimeStr[i];
	}
	public static final String getMathTrigString(int i) {
		if(mathTrigStr == null)
			return "";
		else
			return mathTrigStr[i];
	}
	public static final String getStatisticalString(int i) {
		if(statisticalStr == null)
			return "";
		else
			return statisticalStr[i];
	}
	public static final String getLookupReferencesString(int i) {
		if(lookupReferencesStr == null)
			return "";
		else
			return lookupReferencesStr[i];
	}
	public static final String getDatabaseString(int i) {
		if(databaseStr == null)
			return "";
		else
			return databaseStr[i];
	}
	public static final String getTextString(int i) {
		if(textStr == null)
			return "";
		else
			return textStr[i];
	}
	public static final String getLogicalString(int i) {
		if(logicalStr == null)
			return "";
		else
			return logicalStr[i];
	}
	public static final String getInformationString(int i) {
		if(informationStr == null)
			return "";
		else
			return informationStr[i];
	}
	public static final String getEngineeringString(int i) {
		if(engineeringStr == null)
			return "";
		else
			return engineeringStr[i];
	}
	//===========================================================================
	public static final String getToolItemString(int row, int column) {
		if (toolItemStr == null) return "";
		else					 return toolItemStr[row][column];
	}

	public static final String getMenuItemString(int row, int column) {
		if(menuItemStr == null)
			return "";
		else
			return menuItemStr[row][column];
	}
/*
	public static final String getDrawingMenuItemString(int row, int column) {
		if(drawingMenuItemStr == null)
			return "";
		else
			return drawingMenuItemStr[row][column];
	}
*/
/*	public static final String getTooltipString(int row, int column) {
		if(tooltipStr == null)
			return "";
		else
			return tooltipStr[row][column];
	}

	public static final String getTooBarString(int row, int column) {
		if(toolBarStr == null)
			return "";
		else
			return toolBarStr[row][column];
	}

	public static final String getFormatCellsString(int row, int column) {
		if(formatCellsStr == null)
			return "";
		else
			return formatCellsStr[row][column];
	}

	public static final String getFormatCellsFStyleString(int row, int column) {
		if(formatCellsFStyleStr == null)
			return "";
		else
			return formatCellsFStyleStr[row][column];
	}

	public static final void init()	{
		try	{
			ResourceBundle resourcebundle = ResourceBundle.getBundle("jcalc/JCalcBundle", Locale.US);

			if(resourcebundle != null) {
				//===========	COCOER(2000-10-27)===========================================
				//	FunctionDialog
				itemsStr			= (String[])resourcebundle.getObject("Items");
				mostRecentlyUsedStr	= (String[])resourcebundle.getObject("MostRecentlyUsed");
				allStr				= (String[])resourcebundle.getObject("All");
				financialStr		= (String[])resourcebundle.getObject("Financial");
				dateTimeStr			= (String[])resourcebundle.getObject("DateTime");
				mathTrigStr			= (String[])resourcebundle.getObject("MathTrig");
				statisticalStr		= (String[])resourcebundle.getObject("Statistical");
				lookupReferencesStr = (String[])resourcebundle.getObject("LookupReferences");
				databaseStr			= (String[])resourcebundle.getObject("Database");
				textStr				= (String[])resourcebundle.getObject("Text");
				logicalStr			= (String[])resourcebundle.getObject("Logical");
				informationStr		= (String[])resourcebundle.getObject("Information");
				engineeringStr		= (String[])resourcebundle.getObject("Engineering");
				//===========================================================================
//				menuItemStr = (String[][])resourcebundle.getObject("MenuItems");
//				drawingMenuItemStr = (String[][])resourcebundle.getObject("DrawingMenuItems");
				tooltipStr = (String[][])resourcebundle.getObject("Tooltips");
				toolBarStr = (String[][])resourcebundle.getObject("ToolBars");
				toolItemStr	= (String[][])resourcebundle.getObject("ToolItems");
//				formatCellsStr =(String[][])resourcebundle.getObject("FormatCells");
				//formatCellsFStyleStr =(String[][])resourcebundle.getObject("FormatCellsFStyle");
				return;
			}
		} catch(Throwable throwable) {
			throwable.printStackTrace();
		}
	}

*/
	//============Multi Lang ฐüทร=====================
	public static boolean isKoreanLangMode=true;
	public static Locale locale = Locale.getDefault();//Locale.KOREA;
	public static byte currentLangIndex = 0;

	public static void setLocale(byte index) {
	    currentLangIndex = index;

		if ( index == 1 ) {
			locale = Locale.US;
			isKoreanLangMode = false;
	    }
		else if (index == 0) {
			locale = Locale.KOREA;
			isKoreanLangMode = true;
		}
	}
	public static Locale getLocale() {
		return Locale.KOREAN;
		//return Locale.KOREA;
	}

}
