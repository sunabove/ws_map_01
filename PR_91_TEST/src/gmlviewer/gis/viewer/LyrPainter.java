package gmlviewer.gis.viewer;

import java.util.*;

import java.awt.*;
import java.awt.geom.*;

import gmlviewer.gis.model.*;

public class LyrPainter
    extends Painter {

  public LyrPainter() {
    this(true);
  }

  public LyrPainter(boolean showTextLayer) {
    this.showTextLayer = showTextLayer;
  }

  public int paintLayers(Lyr[] lyrs, Project proj,
                         Graphics2D g, PaintInfo pi,
                         double tx, double ty
      ) {

    int lyrNo = 0;
    if (lyrs != null) {

      g.translate(tx, ty);

      Rectangle2D pixelRect = proj.getPixelRect();

      Color lyrBg = null;

      if (lyrBg != null) {
        g.setColor(lyrBg);
        g.fill(pixelRect);
      }

      Lyr lyr;

      for (int i = 0, len = lyrs.length; i < len; i++) {
        lyr = lyrs[i];
        if (lyr != null) {
          //debug( "DISPLAYING NON TEXT " + lyr.getCode().getDisOrd() + "......" );
          lyr.paintNonTextOnly(g, proj);
          lyrNo += 1;
        }
      }

      boolean showTextLayer = this.isShowTextLayer();

      if (showTextLayer) {
        for (int i = 0, len = lyrs.length; i < len; i++) {
          lyr = lyrs[i];
          if (lyr != null) {
            //debug( "DISPLAYING ONLY TEXT " + lyr.getName() + "......" );
            lyr.paintTextOnly(g, proj );
            lyrNo += 1;
          }
        }
      }

      g.translate( -tx, -ty);

      //debug.debug(this, "Layer no = " + lyrNo);
    }

    return lyrNo;

  }

  public boolean isShowTextLayer() {
    return showTextLayer;
  }

  public void setShowTextLayer(boolean showTextLayer) {
    this.showTextLayer = showTextLayer;
  }

  // member

  private boolean showTextLayer = true ;

}
