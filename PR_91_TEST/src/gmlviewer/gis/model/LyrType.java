package gmlviewer.gis.model;

public class LyrType {

  private LyrType( int type ) {
    this.type = type;
  }

  // member
  private int type;
  // end of member

  // static member
  public static final LyrType
      POINT = new LyrType( Shape.POINT ),
      LINE = new LyrType( Shape.POLY_LINE ),
      AREA = new LyrType( Shape.POLYGON ),
      TEXT = new LyrType( Shape.TEXT )
      ;
  // end of static member

}
