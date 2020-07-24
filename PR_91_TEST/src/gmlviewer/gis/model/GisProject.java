package gmlviewer.gis.model;

import gmlviewer.gis.kernel.*;
import gmlviewer.gis.viewer.PaintInfo;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */

public class GisProject extends CommonLib {
  private Lyr bgLyr;
  private Lyr srchLyr;

  public GisProject() {
  }

  public void setBgLyr(Lyr bgLyr) {
    this.bgLyr = bgLyr;
  }

  public void setSrchLyr(Lyr srchLyr) {
    this.srchLyr = srchLyr;
  }

  public Lyr getBgLyr() {
    return bgLyr;
  }

  public Lyr getSrchLyr() {
    return srchLyr;
  }

  public Lyr [] getLayers( Project proj, PaintInfo paintInfo ) {
    if( bgLyr != null && srchLyr != null ) {
      return new Lyr[] {
          bgLyr, srchLyr };
    } else if( bgLyr != null ) {
      return new Lyr[] {
          bgLyr};
    } else if( srchLyr != null ) {
      return new Lyr[] {
          srchLyr};
    } else {
      return new Lyr[] { };
    }
  }

}
