package gmlviewer.gis.map;

import java.io.*;
import java.util.*;
import gmlviewer.gis.model.*;
import gmlviewer.gis.kernel.*;
import java.sql.SQLException;

public class MapFolder
    extends MapObject implements DebugInterface {

  public MapFolder(String name, int index) {
    this(name, new Integer(index));
  }

  public MapFolder(String name, Integer index) {
    super(name, index);
    this.objList = new Vector();
    this.nameList = new Hashtable();
    this.folderName = this.getName();
    this.folderKind = this.getKind();
  }

  public int getSize() {
    return this.objList.size();
  }

  public final void add(MapObject obj) {
    this.objList.add(obj);
    this.nameList.put(obj.getName(), obj);
    super.setMBR(null);

    //debug( this, "Add Obj = " + obj );
  }

  public final MapObject remove(MapObject obj ) {
    this.objList.remove( obj );
    this.nameList.remove( obj.getName() );
    return obj;
  }

  public final void removeAll() {
    this.objList.removeAllElements();
    this.nameList = new Hashtable();
  }

  public void setSelectedSelf( boolean b ) {
    super.setSelected( b );
  }

  public void setSelected( boolean b ) {
    this.setSelectedRecursive( b );
  }

  public final void setSelectedRecursive(boolean b) {
    this.setSelectedSelf(b);
    MapObject[] objs = this.toArrays();
    MapObject obj;
    MapFolder folder;
    for (int i = 0, len = objs.length; i < len; i++) {
      obj = objs[i];
      if( obj instanceof MapFolder ) {
        folder = (MapFolder) obj;
        folder.setSelectedRecursive(b);
      } else {
        obj.setSelected( b );
      }
    }
  }

  public final MapObject get(String name) {
    return (MapObject) (nameList.get(name));
  }

  public final MapObject get(int i) {
    if( i < objList.size() ) {
      return (MapObject) (objList.get(i));
    } else {
      return null;
    }
  }

  public MapObject[] toArrays() {
    int size = this.objList.size();
    MapObject[] objs = new MapObject[size];
    this.objList.toArray(objs);
    return objs;
  }

  public Object[] toArrays(Object[] objs) {
    return this.objList.toArray(objs);
  }

  protected final MBR calcMBR() {
    MBR mbr = null;
    MapObject[] objs = this.toArrays();
    for (int i = 0, len = objs.length; i < len; i++) {
      mbr = MBR.union(mbr, objs[i].getMBR());
    }
    return mbr;
  }

  private Vector objList;
  private Hashtable nameList;
  private String folderName;
  private String folderKind;

}
