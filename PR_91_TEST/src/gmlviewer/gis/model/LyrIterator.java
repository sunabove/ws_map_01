package gmlviewer.gis.model;

import gmlviewer.gis.map.*;
import gmlviewer.gis.model.*;

public class LyrIterator {

  public LyrIterator( MapFolder folder, MapObjectFilter filter ) {
    super();
    this.folder = folder;
    this.objIt = new MapObjectIterator( folder, filter );
  }

  public Lyr next() {
    return (Lyr) (objIt.next());
  }

  // member
  private MapFolder folder;
  private MapObjectIterator objIt;
  // end of member


}
