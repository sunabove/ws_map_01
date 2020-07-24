package gmlviewer.gis.model;

import java.lang.reflect.*;
import gmlviewer.gis.model.*;

public class Geometry {

  public Geometry() {
  }

  public Geometry( Shape shape ) {
      this();
      this.shape = shape;
  }

  public String toString() {
      return toSqlValue( shape );
  }

  public String getSqlType( Field field ) {
    return "GEOMETRY" ;
  }

  public String toSqlValue( Object value ) {

    if( value == null ) {
      return "NULL";
    } else {
      if( value instanceof Shape ) {
          Shape shape = (Shape) value;
          return "GeomFromText(\'" + shape.toGeomText() + "\')" ;
      } else {
          return value.toString();
      }
    }

  }

  // member
  private Shape shape;
  // end of member

}
