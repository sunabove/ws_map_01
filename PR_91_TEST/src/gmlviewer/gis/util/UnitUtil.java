package gmlviewer.gis.util;

public class UnitUtil {
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

  public static double getAngle(double x, double y) {

     double theta = Math.acos( x/Math.sqrt(x*x + y*y));

     if( x < 0 && y < 0 ) { // 3/4 �и�
        theta = 2*Math.PI - theta;
     } else if( x > 0 && y < 0 ) { // 4/4 �и�
        theta = 2*Math.PI - theta;
     }

     return theta;

  }

}
