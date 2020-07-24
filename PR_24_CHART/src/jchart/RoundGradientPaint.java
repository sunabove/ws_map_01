
/**
 * Title:        First JAVA 2D<p>
 * Description:  First JAVA 2D  Drawing<p>
 * Copyright:    Copyright (c) sbmoon<p>
 * Company:      jcosmos development<p>
 * @author sbmoon
 * @version 1.0
 */

package jchart;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.ColorModel;

public class RoundGradientPaint implements Paint {
  private FillEffect fe;
  protected Point2D mPoint;
  protected Point2D mRadius;
  protected Color mPointColor, mBackgroundColor;

  public RoundGradientPaint(FillEffect fe, Point2D center, Color pointColor,
      Point2D radius, Color backgroundColor) {
    if( radius.distance(center.getX(), center.getY()) <= 0 )
      throw new IllegalArgumentException("Radius must be greater than 0." );
    this.fe = fe;
    mPoint = center;
    mPointColor = pointColor;
    mRadius = radius;
    mBackgroundColor = backgroundColor;
  }

  public PaintContext createContext(ColorModel cm, Rectangle deviceBounds,
      Rectangle2D userBounds, AffineTransform xform, RenderingHints hints) {
    Point2D transformedPoint = xform.transform(mPoint, null);
    Point2D transformedRadius = xform.deltaTransform( mRadius, null );
    return new RoundGradientContext(this.fe, transformedPoint, mPointColor,
        transformedRadius, mBackgroundColor);
  }

  public int getTransparency() {
    int a1 = mPointColor.getAlpha();
    int a2 = mBackgroundColor.getAlpha();
    return (((a1 & a2) == 0xff) ? OPAQUE : TRANSLUCENT );
  }
}
