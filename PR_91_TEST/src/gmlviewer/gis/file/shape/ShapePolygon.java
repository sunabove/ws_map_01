package gmlviewer.gis.file.shape;

import gmlviewer.gis.model.*;

public class ShapePolygon extends EsriShape {

  private double [] box = new double[4];
  private int numParts;
  private int numPoints;
  private int [] parts;
  private Pnt [] points;

  public ShapePolygon( int recordNumber, double [] box, int numParts, int numPoints,
                   int [] parts, Pnt [] points) {
      super( recordNumber );

      this.box = box;
      this.numParts = numParts;
      this.numPoints = numPoints;
      this.parts = parts;
      this.points = points;
  }

  public String toString() {
      return "Polygon recNum = " + getRecordNumber() + ", xmin = " + box[0] + ", ymin = " + box[1] +
      ", xmax = " + box[2] + ", ymax = " + box[3] + ", numParts = " + numParts +
      ", numPoints = " + numPoints;
  }

  public int getNumParts() {
      return numParts;
  }

  public int getNumPoints() {
      return numPoints;
  }

  public int [] getParts() {
      return parts;
  }

  public Pnt [] getPoints() {
      return points;
  }

  public double [] getBox() {
      return box;
  }

}
