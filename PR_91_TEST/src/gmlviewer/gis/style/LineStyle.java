package gmlviewer.gis.style;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import gmlviewer.gis.model.Project;

public class LineStyle
    extends Style {

  protected LineStyle(Color lineColor) {
    this.lineColor = lineColor;
  }

  public void draw(Graphics2D g2, Shape shape) {
    if (lineColor != null) {
      g2.setColor(lineColor);
      g2.draw(shape);
    }
  }

  protected Color selectColor = Color.orange ;

  public void drawSelected(Graphics2D g2, Shape shape) {
    if (selectColor != null) {
      g2.setColor( selectColor );
      g2.draw(shape);
    }
  }


  public void fill(Graphics2D g2, Shape shape) {
  }

  public void paint(Graphics2D g2, gmlviewer.gis.model.Shape shape, Project proj ) {
    java.awt.Shape ashape = shape.getShape( proj );
    if( ashape != null ) {
      this.draw( g2, ashape );
    }
  }

  // member
  protected Color lineColor;

  // static method
  public static LineStyle getStyle(Color lineColor) {
    return new LineStyle(lineColor);
  }

}
