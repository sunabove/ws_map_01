package jchart;

/**
 * Title:        java chart project
 * Description:  jchart v1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import jcosmos.*;

public class ThreeDimObjectPool extends LinkedList implements Comparator {

  private float dirX, dirY;

  private Hashtable compareKeys = new Hashtable();

  private ChartType chartType;

  private PictureExtent pictureExtent;

  private float proX;
  private float proY;

  public ThreeDimObjectPool(ChartType chartType, PictureExtent pictureExtent, float proX, float proY) {

    this.proX = proX;
    this.proY = proY;

    this.chartType = chartType;
    this.pictureExtent = pictureExtent;

  }

  public void add(ChartElement elem) {

    if( elem.getShape() == null ) {

      return;

    }

    super.add( elem );

    compareKeys.put( elem, chartType.get3DObjectCompareKey( elem, proX, proY ) );

  }

  public Object [] sort() {

    Object a[] = this.toArray();
    Arrays.sort(a, this );

    return a;

  }

  public int compare(Object o1, Object o2) {

    double [] first = (double [])compareKeys.get( o1 );
    double [] second = (double [])compareKeys.get( o2 );

    double xdiff = first[0] - second[0];
    double ydiff = first[1] - second[1];

    return (int) ( (xdiff == 0 ) ? ydiff: xdiff );

  }

}