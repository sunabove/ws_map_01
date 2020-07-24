/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package jcosmos;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.*;
import java.net.*;
import java.io.*;
import com.sun.image.codec.jpeg.*;
import htmleditor.*;

public class Utility {

	final public static Dimension screenSize = Toolkit.getDefaultToolkit()
			.getScreenSize();
	public static int messageNum;
	public static String defaultStatus = "";

	// final private static java.awt.Frame frame = new Frame();
	//
	// static {
	// frame.addNotify();
	// }

	final public static String[] parseString(String text, String delim) {

		LinkedList tknList = new LinkedList();

		for (;;) {

			int i = text.indexOf(delim);

			if (i < 0) {

				if (text.length() > 0) {

					tknList.add(text);

				}

				break;

			}

			tknList.add(text.substring(0, i));

			if (i < text.length() - 1) {

				text = text.substring(i + delim.length());

			} else {

				break;

			}

		}

		Iterator it = tknList.iterator();

		int len = tknList.size();

		if (len < 1) {

			return null;

		}

		String[] tokens = new String[len];

		for (int i = 0; i < len; i++) {

			tokens[i] = (String) (it.next());

		}

		return tokens;

	}

	public static void messageDialog(Component comp, String message,
			String title) {

		showOptionPaneDialog(comp, message, title,
				JOptionPane.INFORMATION_MESSAGE);

	}

	public static void warningDialog(Component comp, String message,
			String title) {

		showOptionPaneDialog(comp, message, title, JOptionPane.WARNING_MESSAGE);

	}

	public static void showOptionPaneDialog(Component comp, String message,
			String title, int type) {

		JOptionPane.showMessageDialog(comp, message, title, type);

	}

	// public static int getWidth(Image img) {
	//
	// return img.getWidth( frame );
	//
	// }
	//
	// public static int getHeight(Image img) {
	//
	// return img.getHeight( frame );
	//
	// }

	// public static void showStatus(String status) {
	//
	// if( statusBar != null ) {
	//
	// statusBar.setText( status );
	//
	// statusBar.repaint();
	//
	// }
	//
	// }

	private static void println(String str) {

		if (AppRegistry.DEBUG_FLAG) {
			System.out.println(str);
		}

	}

	public static void debug(Exception e) {

		e.printStackTrace();

	}

	public static void debug(Error e) {

		e.printStackTrace();

	}

	public static void debug(Class cls, String str) {

		println(" DEBUG: (" + (messageNum++) + ") [" + cls.getName() + "] : "
				+ str);

	}

	public static void debug(Object obj, String str) {

		debug(obj.getClass(), str);

	}

	public static boolean isRightMouseButton(MouseEvent anEvent) {

		return ((anEvent.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK);

	}

	// inserting image file

	public static JFrame getJFrame(Component comp) {

		return (JFrame) Utility.getAncestorOfClass(JFrame.class, comp);

	}

	public static Component getAncestorOfClass(Class klass, Component comp) {

		return SwingUtilities.getAncestorOfClass(klass, comp);

	}

	public static URL getResource(String src) {

		// if( AppRegistry.ICON_RESOURCE_URL != null ) {
		//
		// try {
		//
		// URL url = new URL( AppRegistry.ICON_RESOURCE_URL + src );
		//
		// Utility.debug( Utility.class, "URL = " + url );
		//
		// return url;
		//
		// } catch (Exception e) {
		//
		// debug( e );
		//
		// return null;
		// }
		//
		// }

		// System.out.println( "" + Utility.class.getResource( "resource/" +
		// src) );

		return Utility.class.getResource("resource/" + src);

	}

	public static Image getResourceImage(String src) {

		try {

			return new ImageIcon(getResource(src)).getImage();

		} catch (Exception e) {

			return getImageNotFound();

		}

	}

	public static Image getUrlImage(URL url) {

		try {

			Image image = new ImageIcon(url).getImage();

			Utility.debug(Utility.class, "IMG = " + image);

			return image;

		} catch (Exception e) {

			return getImageNotFound();

		}

	}

	public static Image getImageNotFound() {

		return new ImageIcon(Utility.class
				.getResource("resource/image_not_found.gif")).getImage();

	}

	public static ImageIcon getResourceImageIcon(String src) {

		return new ImageIcon(Utility.getResourceImage(src));

	}

	public static Color getComplemetaryColor(Color c) {

		// 칼라 정수 값의 보수 값에 해당하는 칼라가 보색이 되어진다.
		return new Color(~c.getRGB());

	}

	final public static Paint createTexturePaint(final int[][] pixels,
			final Color refColor) {

		final int h = pixels.length;
		final int w = pixels[0].length;

		final BufferedImage image = new BufferedImage(w, h,
				BufferedImage.TYPE_INT_RGB);

		final Graphics g = image.getGraphics();

		for (int i = 0; i < h; i++) {

			for (int k = 0; k < w; k++) {

				g.setColor((pixels[i][k] == 0) ? Color.white : refColor);
				g.drawRect(k, i, 1, 1);

			}

		}

		final Rectangle2D tr = new Rectangle2D.Double(0, 0, w, h);

		return new TexturePaint(image, tr);

	}

	public static void removeAllListElements(final LinkedList list) {

		for (int i = 0, len = list.size(); i < len; i++) {

			list.remove(i);

		}

	}

	public static double getRadian(double x, double y) {

		if (x == 0 && y == 0) {

			return 0;

		}

		double theta = Math.acos(x / Math.sqrt(x * x + y * y));

		if (y < 0) { // 3/4 분면 과 4/4 분면

			theta = 2 * Math.PI - theta;

		}

		return theta % (2 * Math.PI);

	}

	public static void locateOnTheScreenCenter(Window comp) {

		Dimension frameSize = comp.getSize();

		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}

		comp.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);

	}

	public static void setHighQuality(final Graphics2D g2) {

		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

	}

}