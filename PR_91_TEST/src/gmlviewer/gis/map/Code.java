package gmlviewer.gis.map;

import gmlviewer.gis.kernel.*;
import java.util.ArrayList;

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
public class Code extends CommonLib {

  public Code( String name, int dai, int jung ) {
    this.setName( name );
    this.setDai( dai );
    this.setJung( jung );
  }

  public int getDai() {
    return dai;
  }

  public int getJung() {
    return jung;
  }

  public boolean isSelected() {
    return selected;
  }

  public String getName() {
    return name;
  }

  public ArrayList getSubCodes() {
    return subCodes;
  }

  public void setDai(int dai) {
    this.dai = dai;
  }

  public void setJung(int jung) {
    this.jung = jung;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Code addSubCode( Code subCode ) {
    this.subCodes.add( subCode );
    return this;
  }

  public int getCodeValue() {
    return this.dai*10 + this.jung;
  }

  public String toString() {
    if( this.getCodeValue() < 0 ) {
      return this.name ;
    } else {
      return this.name + " ( " + this.getCodeValue() + " )";
    }
  }

  public boolean equals( Object obj ) {
    if( obj instanceof Code ) {
      Code that = (Code) obj;
      return that.getCodeValue() == this.getCodeValue() ;
    }
    return false;
  }

  private ArrayList subCodes = new ArrayList();

  private int dai;
  private int jung;
  private boolean selected;
  private String name;

}
