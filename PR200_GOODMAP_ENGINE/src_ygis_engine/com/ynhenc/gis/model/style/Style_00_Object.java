package com.ynhenc.gis.model.style;

import java.lang.reflect.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.comm.util.*;
import com.ynhenc.gis.*;
import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.model.shape.*;

import java.util.*;

public abstract class Style_00_Object extends GisComLib {

	public Style_00_Object() {
	}

	@Override
	public String toString() {
		Class topKlass = Style_00_Object.class;
		Object obj = this;
		Class klass = obj.getClass();
		Vector list = new Vector();
		StringBuffer styleValue;

		while (topKlass.isAssignableFrom(klass)) {
			styleValue = this.toStyleValue(obj, klass);
			list.add(0, styleValue);
			klass = klass.getSuperclass();
		}

		StringBuffer bfr = new StringBuffer();
		bfr.append("class: " + ClassUtil.getSimplifiedName(obj.getClass()) + "; ");
		for (int i = 0, len = list.size(); i < len; i++) {
			bfr.append(list.elementAt(i));
		}
		return bfr.toString();
	}

	private StringBuffer toStyleValue(Object obj, Class klass) {

		StringBuffer bfr = new StringBuffer();
		Field[] flds = this.getStyleFields(klass);
		Field fld;
		Object value;
		String cssStyleValue;
		for (int i = 0, len = flds.length; i < len; i++) {
			fld = flds[i];
			try {
				fld.setAccessible(true);
				value = fld.get(obj);
				cssStyleValue = this.toStyleAttribute(fld, value);
				bfr.append(cssStyleValue);
			} catch (Exception ex) {
				this.debug(ex);
			}
		}
		return bfr;
	}

	private String toStyleAttribute(Field fld, Object value) {
		String text = fld.getName() + ": " + this.toObjText(value) + "; ";
		return text;
	}

	private String toObjText(Object value) {

		if (value instanceof Color) {
			Color col = (Color) value;
			return HtmlUtil.toHtmlColor(col);
		} else if (value instanceof Font) {
			return this.toFontText((Font) value);
		}

		return "" + value;
	}

	private String toFontText(Font font) {
		return font.getName() + ", " + font.getStyle() + ", " + font.getSize();
	}

	private Field[] getStyleFields(Class klass) {

		Field[] fieldList = klass.getDeclaredFields();

		Vector<Field> list = new Vector<Field>();

		Field field;

		for (int i = 0, len = fieldList.length; i < len; i++) {

			field = fieldList[i];

			if (this.checkField(field)) {
				list.add(field);
			}

		}

		int size = list.size();
		Field[] objs = new Field[size];
		list.toArray(objs);

		return objs;

	}

	public boolean checkField(Field field) {

		int mod = field.getModifiers();

		if (Modifier.isStatic(mod) || Modifier.isTransient(mod)) {
			return false;
		} else {
			return true;
		}

	}

	// abstract method

	public abstract boolean setGraphics_02_OutsideDraw(Graphics2D g2, Projection projection);

	public abstract boolean setGraphics_01_InsideFill(Graphics2D g2, Projection projection);

	public abstract void paintShape_01_InsideFill(Graphics2D g2, ShapeObject shapeObj, Projection projection, ShapeHash shapeHash );

	public abstract void paintShape_02_OutsideDraw(Graphics2D g2, ShapeObject shape, Projection projection, ShapeHash shapeHash);

	// end of abstract method

	// static method

	// end of static method

}
