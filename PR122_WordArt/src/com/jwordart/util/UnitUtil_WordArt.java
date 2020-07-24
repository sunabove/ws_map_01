package com.jwordart.util;

import java.awt.Toolkit;

public class UnitUtil_WordArt {

	public static double getConvertPixelToMeter( double pxls ) {

		return  pxls * meterPerDot ;
	}

	public static double getConvertMeterToPixel( double meter ) {

		return  meter / meterPerDot ;
	}

	/**
	 * �ȼ��� ��Ƽ���ͷ� ��ȯ�Ѵ�.
	 *
	 * @param pxls
	 *            �ʼ� ��
	 */
	public static double convertPixelToCM(double pxls) {
		return ( pxls / DPI ) * CMsPerInch;
	}

	/**
	 * ��Ƽ���͸� �ȼ��� ��ȯ�Ѵ�.
	 *
	 * @param cms
	 *            ��Ƽ����
	 */
	public static double convertCMToPixel(double cms) {
		return ( cms / CMsPerInch ) * DPI ;
	}

	/**
	 * ����Ʈ�� �ȼ��� ��ȯ�Ѵ�.
	 *
	 * @param pnts
	 *            ����Ʈ
	 */
	public static double convertPointToPixel(double pnts) {
		return pnts * pixelsPerPoint;
	}

	/**
	 * �ȼ��� ����Ʈ�� ��ȯ�Ѵ�.
	 *
	 * @param pixels
	 *            �ȼ� ��
	 */
	public static double convertPixelToPoint(double pixels) {
		return pixels / pixelsPerPoint;
	}

	/**
	 * ������ �������� ��ȯ�Ѵ�.
	 *
	 * @param degree
	 *            ����
	 */
	public static double convertDegreeToRadian(double degree) {
		return degree * Math.PI / 180.0;
	}

	/**
	 * ������ ������ ��ȯ�Ѵ�.
	 *
	 * @param radian
	 *            ����
	 */
	public static double convertRadianToDegree(double radian) {
		return radian * 180.0 / Math.PI;
	}

	public static double getRadian(double x, double y) {

		double theta = Math.acos(x / Math.sqrt(x * x + y * y));

		if (x < 0 && y < 0) { // 3/4 �и�
			theta = 2.0 * Math.PI - theta;
		} else if (x > 0 && y < 0) { // 4/4 �и�
			theta = 2.0 * Math.PI - theta;
		}

		return theta;

	}

	public static void main( String [] args ) throws Exception {

		int dpi = Toolkit.getDefaultToolkit().getScreenResolution();

		System.out.println( "getScreenResolution DPI  = " + dpi );

		double width = 550 * 1000 * 100 ;
		double scale = 400000 ;

		double pixel = convertCMToPixel( width/scale );

		System.out.println( "pixel = " + pixel );

	}

	private static final double CMsPerInch = 2.54 ; // 1 ��ġ�� 2.5399 ��Ƽ���� �̴�.

	private static final int DPI = SystemUtil.getResolution();

	//private static final int DPI = 96; // windows

	//private static final int DPI = 98; // aix unix

	//private static final int DPI = Toolkit.getDefaultToolkit().getScreenResolution();

	private static final double meterPerDot = ( CMsPerInch / 100.0 / DPI ) ;

	private static final double pixelsPerCM = 72.0 / 2.54; // 1 ��Ƽ���ʹ� 72/2.54 �ȼ��� �ش��Ѵ�.

	private static final double pixelsPerPoint = (1.0 / 72.0) * CMsPerInch * pixelsPerCM; // ��  ����Ʈ�� 1/72 inch �̴�.




}
