package gmlviewer.gis.map;

import java.io.File;
import gmlviewer.gis.model.*;
import gmlviewer.gis.util.*;
import gmlviewer.gis.kernel.*;
import java.sql.SQLException;

public abstract class MapObject extends CommonLib
{

  public MapObject(String name, Integer index) {
    this( name, index.intValue() );
  }

  public MapObject(String name, int index) {
    this.name = name;
    this.index = index;
    this.isSelected = false;
  }

  public String getKind() {
    return ClassUtil.getSimplifiedName( this.getClass() );
  }

  public final String getName() {
    return this.name;
  }

  public int hashCode() {
    return this.index ;
  }

  public final int getIndex() {
    return index;
  }

  public void setSelected(boolean b) {
    this.isSelected = b;
  }

  public final void setSelectedSelfOnly(boolean b) {
    this.isSelected = b;
  }

  public boolean isSelected() {
    return this.isSelected;
  }

  public final void setMBR(MBR mbr) {
    this.mbr = mbr;
  }

  public final MBR getMBR() {
    if( this.mbr != null ) {
      return this.mbr;
    } else {
      this.mbr = this.calcMBR();
      return this.mbr;
    }
  }

  protected void checkKindAndMBR() {
    if( this.kind == null ) {
      this.kind = this.getKind();
    }
    if( this.mbr == null ) {
      this.mbr = this.calcMBR();
    }
  }


  public String toString() {
    String info = ClassUtil.getSimplifiedName( this.getClass() )
        + " : " + this.getName() + ", index = " + this.index ;
    return info;
  }

  // abstract member
  abstract protected MBR calcMBR() ;

  // member

  private String name;
  private int index;
  protected MBR mbr;
  protected String kind;

  private transient boolean isSelected;

  // end of member

  // static method
  protected static String getBBDLyrName(File file) {
    return getBBDLyrName( file.getName() );
  }
  protected static String getBBDLyrName( String name ) {
    int dotIdx = name.lastIndexOf('.');
    if( dotIdx < 0 ) {
      return name;
    } else {
      return name.substring(0, dotIdx);
    }
  }
  // end of static method

}
