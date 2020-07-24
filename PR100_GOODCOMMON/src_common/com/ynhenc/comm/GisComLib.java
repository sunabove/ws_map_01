package com.ynhenc.comm;

import java.io.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Image;
import java.awt.image.*;
import java.net.*;
import java.text.NumberFormat;

import javax.imageio.*;

import com.ynhenc.comm.file.FileManager;
import com.ynhenc.comm.util.*;

public class GisComLib implements KernelInterface {

	public final void debug(Exception e) {
		debug.println(this, e);
	}

	public final void debug(Exception e, boolean showMsg) {
		if( showMsg ) {
			debug.println(this, e);
		}
	}

	public final void debug(String msg, boolean showMsg) {
		if (showMsg) {
			debug.println(this, msg);
		}
	}

	public final void debug(String msg) {
		debug.println(this, msg);
	}

	public final void debug(StringBuffer msg) {
		debug.println(this, msg.toString());
	}

	public final void message(String message) {
		debug.println(this, message);
	}

	public Color getColor(String color) {
		return HtmlUtil.fromHtmlColor(color);
	}

	public Color getColor(String name, Color def) {
		Color color = this.getColor(name);
		return (color != null) ? color : def;
	}

	public String toColorText(Color color) {
		return HtmlUtil.toHtmlColor(color);
	}

	public final boolean isGood(Object obj) {
		return obj != null && obj.toString().length() > 0;
	}

	public final boolean isGood(String text) {
		return text != null && text.length() > 0;
	}

	public boolean isGood(Integer i) {
		return (i != null);
	}

	public boolean equalsStringTrimIgnoreCase(String a, String b) {
		if (a == b) {
			return true;
		} else if (a != null && b != null) {
			return a.trim().equalsIgnoreCase(b.trim());
		}
		return false;
	}

	public void messageDialog(Component comp, String message) {
		this.messageDialog(comp, message, message);
	}

	public void messageDialog(Component comp, String message, String title) {
		this.showOptionPaneDialog(comp, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public void warnDialog(Component comp, String message) {
		this.warnDialog(comp, message, message);
	}

	public void warnDialog(Component comp, String message, String title) {
		this.showOptionPaneDialog(comp, message, title, JOptionPane.WARNING_MESSAGE);
	}

	public void showOptionPaneDialog(Component comp, String message, String title, int type) {
		JOptionPane.showMessageDialog(comp, message, title, type);
	}

	public void invokeLater(Runnable runnable) {
		javax.swing.SwingUtilities.invokeLater(runnable);
	}

	private FileManager getFileManager() {
		return Server.getFileManager();
	}

	public File getRealPathFile(String fileName) {
		FileManager manager = this.getFileManager();
		return manager.getRealPathFile(fileName);
	}

	public BufferedImage getRealPathImage(String fileName) {
		try {
			File file = this.getRealPathFile(fileName);
			BufferedImage image = ImageIO.read(file);
			return image;
		} catch (IOException ex) {
			this.debug("CAN'T READ FILE = " + fileName + " : " + ex.getMessage());
			return null;
		}
	}

	public static String getFormat( Number number ) {
		return numberFormatter.format( number );
	}

	// static methods

	public static boolean isBetween(double a, double b, double c) {
		return a <= b && b <= c;
	}

	public static int compareToDouble(double d1, double d2) {
		if (d1 < d2) {
			return -1; // Neither val is NaN, thisVal is smaller
		}
		if (d1 > d2) {
			return 1; // Neither val is NaN, thisVal is larger
		}

		long thisBits = Double.doubleToLongBits(d1);
		long anotherBits = Double.doubleToLongBits(d2);

		return (thisBits == anotherBits ? 0 : // Values are equal
				(thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
						1)); // (0.0, -0.0) or (NaN, !NaN)

	}

	public static int compareToInteger(int thisVal, int anotherVal) {
		return (thisVal < anotherVal ? -1 : (thisVal == anotherVal ? 0 : 1));
	}

	public static BufferedImage convertToBufferedImage(Image image) {

		if (image == null) {
			return null;
		} else {
			Frame f = null;
			if (false) {
				f = GisComLib.getFrame();
			}

			int w = image.getWidth(f), h = image.getHeight(f);
			BufferedImage bImage;

			bImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			bImage.getGraphics().drawImage(image, 0, 0, f);

			return bImage;
		}
	}

	private static Frame getFrame() {
		if (FRAME_GLOBAL == null) {
			FRAME_GLOBAL = new Frame();
			FRAME_GLOBAL.addNotify();
		}
		return FRAME_GLOBAL;
	}

	private static Frame FRAME_GLOBAL;

	private static final NumberFormat numberFormatter = NumberFormat.getInstance();

	public static final String NEWLINE = System.getProperty("line.separator");

	public static final String NL = System.getProperty("line.separator");

}
