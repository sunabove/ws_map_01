package gmlviewer.gis.style;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.BasicStroke;
import gmlviewer.gis.model.Project;

public class StrokeStyle extends Style {

  protected StrokeStyle(Color lineColor, float width, float [] dash ) {
    this.lineColor = lineColor;
    this.stroke = new BasicStroke( width, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                                   0, dash, 0 );
  }

  public void draw(Graphics2D g2, Shape shape) {
    if ( lineColor != null ) {
      g2.setColor(lineColor);
      if( stroke != null ) {
        g2.setStroke( stroke );
      }
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
  protected Stroke stroke;

  // static method
  public static StrokeStyle getStyle(Color lineColor, float width, float [] dash ) {
    return new StrokeStyle(lineColor, width, dash );
  }

}
