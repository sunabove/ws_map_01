//package jcalc;
package jcosmos;

import java.applet.*;
import java.awt.*;
import java.awt.image.*;
import java.util.*;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Graphics;
import java.net.*;
import java.io.*;
import java.awt.event.*;
import java.awt.PrintGraphics;
import java.awt.Dialog;
import java.util.zip.*;


public class Metinfo {
	FontMetrics met;
	int w,h,l,a,d;

	Frame f = new Frame();

	// 메트릭스 정보 생성자
	public Metinfo (Font font,char ch) {
		met = f.getFontMetrics(font);
		w = met.charWidth(ch);
		h = met.getHeight();
		l = met.getLeading();
		a = met.getAscent();
		d = met.getDescent();
	}

	//-----------------------------------------------------
	// WildRain 추가
	//-----------------------------------------------------
	public Metinfo (Font font) {
		met = f.getFontMetrics(font);
		h = met.getHeight();
		l = met.getLeading();
		a = met.getAscent();
		d = met.getDescent();
	}

	// 빈 생성자
	public Metinfo() {}

	// 메트릭스 주요정보 변경후 재계산
	public void setMetinfo(Font font,char ch) {
		met = f.getFontMetrics(font);
		w = met.charWidth(ch);
		h = met.getHeight();
		l = met.getLeading();
		a = met.getAscent();
		d = met.getDescent();
	}

	// 한 char의 넓이를 구한다.
	public int getWidth() {
		return w;
	}

	// 한 char의 높이를 구한다.
	public int getHeight() {
		return h;
	}

	// 한 char의 리딩을 구한다
	public int getLeading() {
		return l;
	}

	// 한 char의 어센트을 구한다.
	public int getAscent() {
		return a;
	}

	// 한 char의 디센트를 구한다.
	public int getDescent() {
		return d;
	}
}