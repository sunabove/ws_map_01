package com.jcosmos.java3d;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.util.*;
import java.awt.geom.*;

public class Polygon3D extends Vector {

  public double id;

  public Polygon3D( double id ) {

    this.id = id;

  }

  public GeneralPath getGeneralPath() {

    GeneralPath gp = new GeneralPath();

    Enumeration enumIt = elements();

    Point3D p = (Point3D) enumIt.nextElement();

    gp.moveTo( (float) p.x, (float) p.y );

    while( enumIt.hasMoreElements() ) {

      p = (Point3D) enumIt.nextElement();

      gp.lineTo( (float) p.x, (float) p.y );

    }

    gp.closePath();

    return gp;

  }

}
