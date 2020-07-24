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

	// ��Ʈ���� ���� ������
	public Metinfo (Font font,char ch) {
		met = f.getFontMetrics(font);
		w = met.charWidth(ch);
		h = met.getHeight();
		l = met.getLeading();
		a = met.getAscent();
		d = met.getDescent();
	}

	//-----------------------------------------------------
	// WildRain �߰�
	//-----------------------------------------------------
	public Metinfo (Font font) {
		met = f.getFontMetrics(font);
		h = met.getHeight();
		l = met.getLeading();
		a = met.getAscent();
		d = met.getDescent();
	}

	// �� ������
	public Metinfo() {}

	// ��Ʈ���� �ֿ����� ������ ����
	public void setMetinfo(Font font,char ch) {
		met = f.getFontMetrics(font);
		w = met.charWidth(ch);
		h = met.getHeight();
		l = met.getLeading();
		a = met.getAscent();
		d = met.getDescent();
	}

	// �� char�� ���̸� ���Ѵ�.
	public int getWidth() {
		return w;
	}

	// �� char�� ���̸� ���Ѵ�.
	public int getHeight() {
		return h;
	}

	// �� char�� ������ ���Ѵ�
	public int getLeading() {
		return l;
	}

	// �� char�� �Ʈ�� ���Ѵ�.
	public int getAscent() {
		return a;
	}

	// �� char�� ��Ʈ�� ���Ѵ�.
	public int getDescent() {
		return d;
	}
}