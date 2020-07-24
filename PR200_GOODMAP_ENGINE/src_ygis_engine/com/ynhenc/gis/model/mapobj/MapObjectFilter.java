package com.ynhenc.gis.model.mapobj;


public interface MapObjectFilter {
  public boolean accept(MapObject obj);
  public boolean acceptFolder( MapFolder folder ) ;
}
