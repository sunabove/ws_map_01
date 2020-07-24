
/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package jcosmos;

/**
 * ���� ������ ��ȯ�ϴ� Ŭ���� �̴�.
 */
public class Unit {
  private static final double pixelsPerCM = 72.0/2.54;  // 1 ��Ƽ���ʹ� 72/2.54 �ȼ��� �ش��Ѵ�.
  private static final double CMsPerInch = 2.54 ;  // 1 ��ġ�� 2.54 ��Ƽ���� �̴�.
  private static final double pixelsPerPoint = (1.0/72.0)*CMsPerInch*pixelsPerCM; // �� ����Ʈ�� 1/72 inch �̴�.

  /**
   * �ȼ��� ��Ƽ���ͷ� ��ȯ�Ѵ�.
   * @param pxls �ʼ� ��
   */
  public static double convertPixelToCM(double pxls ) {
     double value = pxls/pixelsPerCM;
     value = ((int)(value*100) )/100.0;
     return value;
  }

  /**
   * ��Ƽ���͸� �ȼ��� ��ȯ�Ѵ�.
   * @param cms ��Ƽ����
   */
  public static double convertCMToPixel(double cms ) {
     return cms*pixelsPerCM;
  }

  /**
   * ����Ʈ�� �ȼ��� ��ȯ�Ѵ�.
   * @param pnts ����Ʈ
   */
  public static double convertPointToPixel(double pnts) {
    return pnts*pixelsPerPoint;
  }

  /**
   * �ȼ��� ����Ʈ�� ��ȯ�Ѵ�.
   * @param pixels �ȼ� ��
   */
  public static double convertPixelToPoint(double pixels ) {
    return pixels/pixelsPerPoint;
  }

  /**
   * ������ �������� ��ȯ�Ѵ�.
   * @param degree ����
   */
  public static double convertDegreeToRadian(double degree) {
    return degree*Math.PI/180.0;
  }

  /**
   * ������ ������ ��ȯ�Ѵ�.
   * @param radian ����
   */
  public static double convertRadianToDegree(double radian) {
    return radian*180/Math.PI;
  }
}