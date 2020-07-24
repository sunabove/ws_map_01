package com.ynhenc.comm.util;

import java.awt.*;

import javax.swing.*;
import java.util.*;

public class ColorManager {

	public static final Color[] PREFERRED_COLOR_LIST = {

			new Color(132, 0, 0), // second row
			new Color(255, 99, 0),
			new Color(132, 132, 0),
			new Color(0, 132, 0),
			new Color(0, 132, 132),
			new Color(0, 0, 255),
			new Color(99, 99, 156),
			new Color(132, 132, 132),

			new Color(255, 0, 0), // third row
			new Color(255, 156, 0),
			new Color(156, 206, 0),
			new Color(49, 156, 99),
			new Color(49, 206, 206),
			new Color(49, 99, 255),
			new Color(132, 0, 132),
			new Color(148, 148, 148),

			new Color(255, 0, 255), // fouth row
			new Color(255, 206, 0),
			new Color(255, 255, 0),
			new Color(0, 255, 0),
			new Color(0, 255, 255),
			new Color(0, 206, 255),
			new Color(156, 49, 99),
			new Color(192, 192, 192),

			new Color(255, 156, 206), // fifth row
			new Color(255, 206, 156),
			new Color(255, 255, 156),
			new Color(206, 255, 206),
			new Color(206, 255, 255),
			new Color(156, 206, 255),
			new Color(206, 156, 255),
			new Color(255, 255, 255),

			// Color 배열 - 기본색지정
			new Color(0, 0, 0), // first row
			new Color(156, 49, 0), new Color(49, 49, 0), new Color(0, 49, 0),
			new Color(0, 49, 99), new Color(0, 0, 132), new Color(49, 49, 156),
			new Color(49, 49, 49),

	};

	private static Hashtable colorTable = new Hashtable();

	public static int getColorNum() {

		return PREFERRED_COLOR_LIST.length;

	}

	public static Color getColor(String colorName) {

		colorName = colorName.toUpperCase();
		Color color = (Color) colorTable.get(colorName);
		if (color != null) {
			return color;
		}

		if (colorName.equals("RED")) {
			color = Color.red;
		} else if (colorName.equals("BLUE")) {
			color = Color.blue;
		} else if (colorName.equals("GREEN")) {
			color = Color.green;
		} else if (colorName.equals("WHITE")) {
			color = Color.white;
		} else if (colorName.equals("BLACK")) {
			color = Color.black;
		} else if (colorName.equals("GRAY")) {
			color = Color.gray;
		} else if (colorName.equals("CYAN")) {
			color = Color.cyan;
		} else if (colorName.equals("ORANGE")) {
			color = Color.orange;
		} else if (colorName.equals("MAGENTA")) {
			color = Color.magenta;
		} else if (colorName.equals("VIOLET")) {
			color = Color.magenta;
		} else if (colorName.equals("PINK")) {
			color = Color.pink;
		} else if (colorName.equals("LIGHTGRAY")) {
			color = Color.lightGray;
		} else if (colorName.equals("DARKGRAY")) {
			color = Color.darkGray;
		} else if (colorName.equals("YELLOW")) {
			color = Color.yellow;
		}

		if (color == null) {
			return null;
		}

		colorTable.put(colorName, color);

		return color;

	}

	public static Color getColor(int index) { 
		
		return PREFERRED_COLOR_LIST[index%PREFERRED_COLOR_LIST.length]; 

	}

	public static Color getComplemetaryColor(Color c) {
		// 칼라 정수 값의 보수 값에 해당하는 칼라가 보색이 되어진다.
		return new Color( ~c.getRGB() );
	}

	public static Color getColorUsingColorChoooser(Component component,
			String title, Color initialColor) {

		return colorChooser.showDialog(component, title, initialColor);

	}

	private final static JColorChooser colorChooser = new JColorChooser();

}
