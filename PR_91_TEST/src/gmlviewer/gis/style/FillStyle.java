package gmlviewer.gis.style;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Graphics2D;
import gmlviewer.gis.model.Project;

public class FillStyle extends LineStyle {

  private FillStyle(Color lineColor, Color fillColor ) {
    super( lineColor );
    this.fillColor = fillColor;
  }

  public void fill(Graphics2D g2, Shape shape) {

    if (fillColor != null) {
      g2.setColor(fillColor);
      g2.fill(shape);
    }

  }

  public void fillSelected(Graphics2D g2, Shape shape) {

    if ( this.selectColor != null) {
      g2.setColor(selectColor);
      g2.fill(shape);
    }

  }


  public void paint(Graphics2D g2, gmlviewer.gis.model.Shape shape, Project proj ) {
    java.awt.Shape ashape = shape.getShape( proj );
    if( ashape != null ) {
      if( shape.isSelected() ) {
        this.fillSelected( g2, ashape );
        this.drawSelected(g2, ashape);
      } else {
        this.fill( g2, ashape );
        this.draw(g2, ashape);
      }
    }
  }



  // member
  private Color fillColor;

  // static method

  public static FillStyle getStyle( Color lineColor, Color fillColor ) {
    return new FillStyle( lineColor, fillColor );
  }

  public static void main( String [] args ) {
    Style s = FillStyle.getStyle( Color.red, Color.cyan );
    Style t = LineStyle.getStyle( Color.blue );
    Style u = TextStyle.getStyle( "Serif", 1, Color.red, 12 );
    System.out.println( s );
    System.out.println( t );
    System.out.println( u );
  }

}
