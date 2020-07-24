package gmlviewer.gis.map;

public interface MapObjectFilter {
  public boolean accept(MapObject obj);
  public boolean acceptFolder( MapFolder folder ) ;
}
