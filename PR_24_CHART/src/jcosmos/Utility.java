
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
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.text.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class Utility {
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	public static int warnNum = 0, messageNum;
	public static String latestMessage;
	public static JLabel statusBar;
	public static String defaultStatus = "";
	public static Frame frame = new Frame();
	public static boolean debugFlag = true;  // debuggig flag

	static {

		frame.addNotify();

	}

	private static long lastMsgTime = System.currentTimeMillis();

	public static void warn(String message, Component comp) {
		long now = System.currentTimeMillis();

		if( now - lastMsgTime < 1000 ) {
			waitForSeconds( 2 );
		}

		lastMsgTime = System.currentTimeMillis();

		JOptionPane.showMessageDialog( comp, message, "에러!", JOptionPane.ERROR_MESSAGE );
	}

	public static void debug(String message, Component comp) {
		long now = System.currentTimeMillis();

		if( now - lastMsgTime < 1000 ) {
			waitForSeconds( 2 );
		}

		lastMsgTime = System.currentTimeMillis();

		JOptionPane.showMessageDialog( comp, message, "메시지!", JOptionPane.INFORMATION_MESSAGE );
	}

	public static String toHtmlColor(Color color) {
		String [] rgb = {
			Integer.toHexString( color.getRed() ),
			Integer.toHexString( color.getGreen() ),
			Integer.toHexString( color.getBlue() )
		};

		for( int i = 0; i < 3; i ++ ) {
			if( rgb[i].length() == 1 ) {
				rgb[i] = "0" + rgb[i];
			}
		}

		return "#" + rgb[0] + rgb[1] + rgb[2] ;
	}

	public static int getWidth(Image img) {
		return img.getWidth( frame );
	}

	public static int getHeight(Image img) {
		return img.getHeight( frame );
	}

	public static void showStatus(String status) {
		if( statusBar != null ) {
			statusBar.setText( status );
		}
	}

	public static void showAttributes(AttributeSet attr) {
		Enumeration enumIt = attr.getAttributeNames();
		String contents = "";
		Object elem;
		while( enumIt.hasMoreElements() ) {
			elem = enumIt.nextElement();
			contents += elem + " = " + attr.getAttribute( elem ) + " ";
		}
		Utility.debug(Utility.class, contents );
	}

	public static Object getAttribute(AttributeSet attr, String key) {
		Enumeration enumIt = attr.getAttributeNames();

		Object elem;

		while( enumIt.hasMoreElements() ) {
			elem = enumIt.nextElement();

			if( ("" + elem).equals( "" + key ) ) {
				return attr.getAttribute( elem );
			}
		}
		return null;
	}

	public static void showDefaultStatus() {
		showStatus( defaultStatus );
	}

	public static double getAngle(double x, double y) {

		return Unit.convertRadianToDegree( getRadian(x,y) );

	}

	public static double getRadian(double x, double y) {

		if( x == 0 && y == 0 ) {

		    return 0.0;

		}

		double theta = Math.acos( x/Math.sqrt(x*x + y*y));

		if( y < 0 ) { // 3/4 분면 과 4/4 분면

			theta = 2*Math.PI - theta;

		}

		return theta%(2*Math.PI);

	}

	public static double getAngle(double x1, double y1, double x2, double y2) {
		return getAngle(x2 - x1, y2 - y1);
	}

	public static double getAngleForArc(double x1, double y1, double x2, double y2) {
		return 360 - getAngle(x2 - x1, y2 - y1);
	}


	public static double getAngleForArc(double x1, double y1, double x2, double y2, double x3, double y3) {

		double startAngle = getAngleForArc(x1, y1, x2, y2);
		double endAngle = getAngleForArc(x1, y1, x3, y3);
		double withinAngle = startAngle - endAngle;

		if (startAngle - endAngle < 0 ) {
		    withinAngle = 360 + withinAngle;
		}

		return withinAngle;

	}

	public static double getDistance( double x1, double y1, double x2, double y2 ) {

		return Math.sqrt( Math.pow( ( x2 - x1 ), 2 ) + Math.pow(( y2 - y1 ), 2 ) );

	}

	public static double getDistance( Point2D p1, Point2D p2 ) {

		return getDistance( p1.getX(), p1.getY(), p2.getX(), p2.getY() );

	}

	public static Rectangle2D.Double getBoundOfArc(Point rootVertex, Point feedbackVertex, Point otherVertex) {

		int mvX = rootVertex.x, mvY = rootVertex.y;     // movingPoint

		Point mfv = new Point( feedbackVertex.x - mvX, feedbackVertex.y - mvY );
		Point mov = new Point( otherVertex.x - mvX, otherVertex.y - mvY );

		int x1sq = mfv.x * mfv.x, y1sq = mfv.y * mfv.y;
		int x2sq = mov.x * mov.x, y2sq = mov.y * mov.y;

		double a = Math.sqrt( ( double) ( y1sq * x2sq - y2sq * x1sq ) / ( y1sq - y2sq ) );
		double b = Math.sqrt( ( double) ( x1sq * y2sq - x2sq * y1sq ) / ( x1sq - x2sq ) );

		double width = 2 * a, height = 2 * b;
		double x = -a + mvX, y = -b + mvY;

		return new Rectangle2D.Double( x, y, width, height );

	}

	public static void waitForSeconds(double sec) {
		sec = Math.abs( sec );
		long start = System.currentTimeMillis(), now = start;
		while( Math.abs(now - start) < sec*1000 ) {
			try {
				// message( cls, "Waiting now ......." );
				Thread.currentThread().sleep( 100 );
				now = System.currentTimeMillis();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// message( cls, "Done waiting.");
	}

	public static void println(String str ) {
		if( debugFlag ) {
			System.out.println(str);
		}
	}

	public static void debug(Exception e) {
		e.printStackTrace();
	}

	public static void debug(Class cls, String str) {
		latestMessage = str;
		println( " DEBUG: (" + (messageNum++) + ") [" + cls.getName() + "] : " + str );
	}

	public static void done() {
		println( "\tDone: " + latestMessage );
	}

	public static void debug(Object obj, String str) {
		debug( obj.getClass(), str );
	}

	public static void locateCenterOnScreen(Window comp) {
		Dimension frameSize = comp.getSize();
		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}
		comp.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
	}

	public static BufferedImage getBufferedImage(String imageURL ) {
		try {
			return getBufferedImage( new java.net.URL(imageURL ) );
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static BufferedImage getBufferedImage(java.net.URL imageURL ) {

		Frame f = new Frame();
		f.addNotify();

		Image image = null;

		try {
			image = new javax.swing.ImageIcon( imageURL ).getImage();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if( image == null ) {
			return null;
		}

		int w = image.getWidth(f), h = image.getHeight(f);

		BufferedImage bImage;

		bImage = new BufferedImage( w, h, BufferedImage.TYPE_INT_RGB );
		bImage.getGraphics().drawImage( image, 0, 0, f );

		return bImage;
	}

	static GraphicsEnvironment local = GraphicsEnvironment.getLocalGraphicsEnvironment();
	static GraphicsDevice screen = local.getDefaultScreenDevice();
	static GraphicsConfiguration configuration = screen.getDefaultConfiguration();

	public static BufferedImage createBufferedImage(int width, int height) {
		if( width > 0 && height > 0 ) {
			return configuration.createCompatibleImage( width, height );
		} else {
			return null;
		}
	}

	public static JFrame getJFrame(Component comp) {
		while( comp != null ) {
			if( comp instanceof JFrame ) {
				return (JFrame) comp;
			}
			comp = comp.getParent();
		}
		return null;
	}

	public static Component getComponent(Component comp, Class klass ) {

		while( comp != null ) {
			if( comp.getClass() == klass ) {
				return comp;
			}
			comp = comp.getParent();
		}

		return null;
	}

	public static URL getResource(String src) {
		return Utility.class.getResource( "resource/" + src);
	}

	public static Image getResourceImage( String src ) {
		return new ImageIcon( getResource( src ) ).getImage();
	}

	public static BufferedImage getResourceBufferedImage( String src ) {
		return getBufferedImage( getResource( src ) );
		//return new ImageIcon( getResource( src ) ).getImage();
	}

	/**
	 * 최대 공약수 구하기
	 */
	public static int gcd( int x1, int x2 ) {
	    if ( x2 == 0 ) return x1;
		else return gcd(x2, x1 % x2);
	}

	/**
	 * 최소 공배수 구하기
	 */
	public static int lcm( int x1, int x2 ) {
	    return Math.abs( x1 * x2 ) / gcd(x1, x2);
	}
}
