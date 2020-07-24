package gmlviewer.gis.util;

import java.lang.reflect.*;

public class ClassUtil {

  public static final boolean isAnonymousClass( Class klass ) {

    return ( klass.getName().indexOf( '$' ) > -1 ) ;

  }

  public static final String getSimplifiedName( Object obj ) {

      return getSimplifiedName( obj.getClass() );
  }

  public static final String getSimplifiedName( Class klass ) {

    String name = klass.getName();

    int dotIdx = name.lastIndexOf( '.' );

    if( dotIdx < 0 ) {
      return name;
    } else {
      return name.substring( dotIdx + 1 );
    }

  }

}
