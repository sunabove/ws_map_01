//package com.techdigm.chart.editor;
package jchartui;

import java.util.*;


/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class TextureTabBundle_en extends ListResourceBundle {

    public TextureTabBundle_en() {
    }
    protected Object[][] getContents() {
        /**@todo: implement this java.util.ListResourceBundle abstract method*/
		return contents;
    }

	String[] textureString = {
		 "Newsprint", "Recycled paper", "Parchment", "Stationery", "Green marble", "White marble", "Brown marble",
		 "Granite", "Blue tissue paper", "Pink tissue paper", "Purple mesh", "Bouquet", "Papyrus", "Canvas",
		 "Denim", "Woven mat", "Water droplets", "Paper bag", "Fish fossil", "Sand", "Cork", "Walnut",
		 "Oak", "Medium wood", ""
	};

	private Object[][] contents = {
		{"textureStirng", textureString},
		{"selectTexture", "Select Texture"},
		{"otherTexture", "Other Texture..."},
		{"texture", "Texture:"},
		{"textureString", textureString}
	};

}