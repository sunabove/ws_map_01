package gmlviewer.gis.util;

import java.util.*;

public class ArrayUtil {

  /**
   * 어레이 중에 오브젝트와 컨텐트가 일치하는 객체의 인덱스를 반환한다.
   * @param array
   * @param obj
   * @return
   */
  public static int indexOfWithEquals( Object [] array, Object obj ) {

    for( int i = 0, len = array.length; i < len ; i ++ ) {

      if( array[i] == obj || array[i].equals( obj ) ) {
	return i;
      }

    }

    return -1;

  }

  public static ColRow getArrayDim( Object [][] objs ) {

    int col = 0;

    Object [] rowList;

    int c;
    for( int i = 0, len = objs.length; i < len; i ++ ) {
      rowList = objs[ i ];
      c = ( rowList == null ) ? 0 : rowList.length;
      col = ( c > col ) ? c : col;
    }

    return new ColRow( col, objs.length );

  }

  public static void removeAllListElements(final LinkedList list) {
      for(int i = 0, len = list.size(); i < len; i ++ ) {
         list.remove( i );
      }
  }

  public static Object a() {
    return new Object[] {};
  }

  public static Object a(Object a) {
    return new Object[] {
        a};
  }

  public static Object a(Object a, Object b) {
    return new Object[] {
        a, b};
  }

  public static Object a(Object a, Object b, Object c) {
    return new Object[] {
        a, b, c};
  }

  public static Object a(Object a, Object b, Object c, Object d) {
    return new Object[] {
        a, b, c, d};
  }

  public static Object a(Object a, Object b, Object c, Object d, Object e) {
    return new Object[] {
        a, b, c, d, e};
  }

  public static Object a(Object a, Object b, Object c, Object d, Object e
                         , Object f) {
    return new Object[] {
        a, b, c, d, e, f};
  }

  public static Object a(Object a, Object b, Object c, Object d, Object e
                         , Object f, Object g) {
    return new Object[] {
        a, b, c, d, e, f, g};
  }

  public static Object a(Object a, Object b, Object c, Object d, Object e
                         , Object f, Object g, Object h) {
    return new Object[] {
        a, b, c, d, e, f, g, h};
  }

  public static Object a(Object a, Object b, Object c, Object d, Object e
                         , Object f, Object g, Object h, Object i) {
    return new Object[] {
        a, b, c, d, e, f, g, h, i};
  }

  public static Object a(Object a, Object b, Object c, Object d, Object e
                         , Object f, Object g, Object h, Object i, Object j) {
    return new Object[] {
        a, b, c, d, e, f, g, h, i, j};
  }




}
