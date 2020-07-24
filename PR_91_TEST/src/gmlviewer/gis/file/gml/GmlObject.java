package gmlviewer.gis.file.gml;

import gmlviewer.gis.kernel.*;
import gmlviewer.gis.sbmoon.Property;
import gmlviewer.gis.model.*;
import gmlviewer.gis.map.*;

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
public class GmlObject extends CommonLib implements ShapeAttribute {

  public GmlObject() {
  }

  public String getAddress1() {
    return address1;
  }

  public String getAddress2() {
    return address2;
  }

  public String getBldName() {
    return bldName;
  }

  public String getEx() {
    return ex;
  }

  public String getHomepage() {
    return homepage;
  }

  public int getKey() {
    return key;
  }

  public Code getCode() {
    return CodeTable.code( key, "" );
  }

  public boolean isOx() {
    return ox;
  }

  public String getTelephoneNo() {
    return telephoneNo;
  }

  public int getRecNo() {
    return recNo;
  }

  public String getCoordinates() {
    return coordinates;
  }

  public Pnt [] getCoordinatesPnts() {

    String coordinates = this.getCoordinates();

    if( coordinates == null ) {

      return new Pnt [] {};

    } else {

      String [] pairs = coordinates.split( " " );

      int size = pairs.length;

      Pnt [] points = new Pnt[ size ];
      String pair[];
      for( int i = 0, len = size; i < len ; i ++ ) {
        pair = pairs[i].split( ",");
        points[i] = new Pnt();
        points[i].x = Double.parseDouble( pair[0] );
        points[i].y = Double.parseDouble( pair[1] );
      }

      return points;

    }

  }


  public int getObjectId() {
    return objectId;
  }

  public void setTelephoneNo(String telephoneNo) {
    this.telephoneNo = telephoneNo;
  }

  public void setOx(boolean ox) {
    this.ox = ox;
  }

  public void setOx(String ox) {
    if( "1".equals( ox ) ) {
      this.setOx( true );
    } else {
      this.setOx( false );
    }
  }

  public void setKey(int key) {
    this.key = key;
  }

  public void setKey(String key) {
    if( key == null || key.equalsIgnoreCase( "null" ) ) {
      this.setKey( -1 );
    }
    this.setKey( this.toInt( key ) );
  }

  public void setHomepage(String homepage) {
    this.homepage = homepage;
  }

  public void setEx(String ex) {
    this.ex = ex;
  }

  public void setBldName(String bldName) {
    this.bldName = bldName;
  }

  public void setAddress2(String address2) {
    this.address2 = address2;
  }

  public void setAddress1(String address1) {
    this.address1 = address1;
  }

  public void setRecNo(int recNo) {
    this.recNo = recNo;
  }

  public void setCoordinates(String coordinates) {
    this.coordinates = coordinates;
  }

  public void setObjectId( String objectId) {
    this.setObjectId( this.toInt( objectId ));
  }

  public void setObjectId(int objectId) {
    this.objectId = objectId;
  }

  public String toString() {

    return "recNo: " + recNo + ";"
        + " objectId: " + objectId + ";"
        + " key: " + key + ";"
        + " ox: " + ox + ";"
        + " bldName: " + bldName + ";"
        + " tel: " + telephoneNo + ";"
        + " add1: " + address1 + ";"
        + " add2: " + address2 + ";"
        + " ex: " + ex + ";"
        + " homepage: " + homepage + ";"
        + " cneter: " + center +  ";"
        + " coordinates: " + coordinates + ";"
        ;

  }

  public void setCenterX( String x ) {

    if( this.center == null ) {
      this.center = new Pnt();
    }

    this.center.x = this.toDouble( x );

    if( this.center.x == 0 ) {
      this.center = null;
    }

  }

  public void setCenterY( String y ) {

    if( this.center == null ) {
      this.center = new Pnt();
    }

    this.center.y = this.toDouble( y );

    if( this.center.y == 0 ) {
      this.center = null;
    }


  }

  public Pnt getCenter() {
    return this.center;
  }

  private String getAddress() {
    String address = "";
    if( this.getAddress1() != null ) {
      address += this.getAddress1();
    }

    if( this.getAddress2() != null ) {
      address += " " + this.getAddress2() ;
    }

    return address;
  }

  public Property [] getProperties() {
    if( this.pros != null ) {
      return this.pros;
    } else {
      // 상호명/전화/주소/특징/홈페이지
      this.pros = new Property[] {
          prop( "상호명", this.getBldName() ),
          prop( "전화", this.getTelephoneNo() ),
          prop( "주소", this.getAddress() ) ,
          prop( "특징", this.getEx() ),
          prop( "홈페이지", this.getHomepage() ),
      };
      return this.pros;
    }
  }

  private Property prop( String name, String value ) {
    return new Property( name, value != null ? value : ""  );
  }

  private Property [] pros;

  private int recNo;
  private int objectId;
  private String coordinates;
  private int key; // 레이어 번호
  private boolean ox; // 검색 여부
  private Pnt center; // 중앙 지점....

  // 상호명/전화/주소/특징/홈페이지
  private String bldName; // 상호명
  private String telephoneNo; // 전화 번호
  private String address1;
  private String address2;
  private String ex; // 특징
  private String homepage; // 홈페이지

}
