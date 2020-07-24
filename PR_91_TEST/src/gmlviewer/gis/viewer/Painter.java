package gmlviewer.gis.viewer;

import gmlviewer.gis.kernel.KernelInterface;
import java.awt.*;

public abstract class Painter extends gmlviewer.gis.kernel.CommonLib
{
  public Painter() {
    super();
  }

  public void setRenderingHints(Graphics2D g) {
    RenderingHints hints = this.getRenderingHints();
    g.setRenderingHints(hints);
  }


  public RenderingHints getRenderingHints() {
    RenderingHints hints = new RenderingHints(
        RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

    hints.put(hints.KEY_RENDERING, hints.VALUE_RENDER_QUALITY);
    hints.put(hints.KEY_DITHERING, hints.VALUE_DITHER_ENABLE);
    hints.put(hints.KEY_COLOR_RENDERING, hints.VALUE_COLOR_RENDER_QUALITY);
    hints.put(hints.KEY_FRACTIONALMETRICS, hints.VALUE_FRACTIONALMETRICS_ON);
    hints.put(hints.KEY_INTERPOLATION, hints.VALUE_INTERPOLATION_BILINEAR);
    hints.put(hints.KEY_ALPHA_INTERPOLATION,
              hints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    return hints;
  }

}
