package gmlviewer.gis.map;

public class MapObjectIterator {

  public MapObjectIterator( MapFolder folder ) {
    this( folder, new AllMapObjectFilter() );
  }

  public MapObjectIterator( MapFolder folder, MapObjectFilter filter ) {
    super();
    this.folder = folder;
    this.filter = filter;
    this.objs = folder.toArrays();
    this.index = 0;
    this.len = this.objs.length;
    this.it = null;
  }

  public MapObject next() {
    if( it != null ) {
      while( ( found = it.next() ) != null ) {
        return found;
      }
      it = null;
      return next();
    } else {
      while( index < len ) {
        found = objs[index];
        index++;
        if( filter.accept(found) ) {
          return found;
        }
        else if( found instanceof MapFolder ) {
          MapFolder folder = (MapFolder) found;
          if( filter.acceptFolder( folder ) ) {
            it = new MapObjectIterator(folder, filter);
            return next();
          }
        }
      }

      return null;
    }

  }

  private MapObjectIterator it;
  private MapObject found;
  private MapFolder folder;
  private MapObject [] objs;
  private int index;
  private int len;
  private MapObjectFilter filter;

  private static class AllMapObjectFilter implements MapObjectFilter {
    public boolean accept( MapObject obj ) {
      return true;
    }

    public boolean acceptFolder( MapFolder folder ) {
      return true;
    }
  }

}
