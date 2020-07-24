package com.ynhenc.comm.util;

public class Property {

  public Property( String key, String value ) {
    this.key = key;
    this.value = value;
  }

  public String getKey() {
    return key;
  }

  public String getValue() {
    return value;
  }

  public void setValue( String value ) {
    this.value = value;
  }

  public String toString() {
    return key + " = " + value;
  }

  private String key;
  private String value;

}
