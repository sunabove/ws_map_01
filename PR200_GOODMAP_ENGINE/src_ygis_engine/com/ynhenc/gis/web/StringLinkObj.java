package com.ynhenc.gis.web;

public abstract class StringLinkObj {

  public StringLinkObj( Object obj ) {
    this.obj = obj;
  }

  public String toString() {
    return obj.toString();
  }

  private Object obj;

}
