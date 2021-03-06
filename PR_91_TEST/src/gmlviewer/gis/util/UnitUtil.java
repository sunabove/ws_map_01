package gmlviewer.gis.util;

public class UnitUtil {
  private static final double pixelsPerCM = 72.0/2.54;  // 1 센티미터는 72/2.54 픽셀에 해당한다.
  private static final double CMsPerInch = 2.54 ;  // 1 인치는 2.54 센티미터 이다.
  private static final double pixelsPerPoint = (1.0/72.0)*CMsPerInch*pixelsPerCM; // 한 포인트는 1/72 inch 이다.

  /**
   * 픽셀을 센티미터로 변환한다.
   * @param pxls 필셀 값
   */
  public static double convertPixelToCM(double pxls ) {
     double value = pxls/pixelsPerCM;
     value = ((int)(value*100) )/100.0;
     return value;
  }

  /**
   * 센티미터를 픽셀로 변환한다.
   * @param cms 센티미터
   */
  public static double convertCMToPixel(double cms ) {
     return cms*pixelsPerCM;
  }

  /**
   * 포인트를 픽셀로 변환한다.
   * @param pnts 포인트
   */
  public static double convertPointToPixel(double pnts) {
    return pnts*pixelsPerPoint;
  }

  /**
   * 픽셀을 포인트로 변환한다.
   * @param pixels 픽셀 값
   */
  public static double convertPixelToPoint(double pixels ) {
    return pixels/pixelsPerPoint;
  }

  /**
   * 각도를 라디안으로 변환한다.
   * @param degree 각도
   */
  public static double convertDegreeToRadian(double degree) {
    return degree*Math.PI/180.0;
  }

  /**
   * 라디안을 각도로 변환한다.
   * @param radian 라디안
   */
  public static double convertRadianToDegree(double radian) {
    return radian*180/Math.PI;
  }

  public static double getAngle(double x, double y) {

     double theta = Math.acos( x/Math.sqrt(x*x + y*y));

     if( x < 0 && y < 0 ) { // 3/4 분면
        theta = 2*Math.PI - theta;
     } else if( x > 0 && y < 0 ) { // 4/4 분면
        theta = 2*Math.PI - theta;
     }

     return theta;

  }

}
