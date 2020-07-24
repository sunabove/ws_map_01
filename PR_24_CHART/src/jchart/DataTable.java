package jchart;

/**
 * Title:        java chart project
 * Description:  jchart v1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;
import java.awt.geom.*;

public class DataTable extends TextualChartElement implements UnResizable, UnMovable{

  private boolean legendShow;
  private boolean horizontalLineShow;
  private boolean verticalLineShow;
  private boolean outerLineShow;

  public DataTable(SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style, Font font,
		   boolean legendShow, boolean horizontalLineShow,
		   boolean verticalLineShow, boolean outerLineShow ) {

      super(sheet, parent, shape, style, font, null);
      this.legendShow = legendShow;
      this.horizontalLineShow = horizontalLineShow;
      this.verticalLineShow = verticalLineShow;
      this.outerLineShow = outerLineShow;

  }

  public void paint(Graphics2D g2) {

      Chart chart = getChart();

      ChartData cd = chart.getChartData();

      DataSeries [] dss = cd.getDataSeries();

      Rectangle dataArea = getDataArea();

      String refNames [] = cd.getReferenceNames();

      int x = dataArea.x, y = dataArea.y, w = dataArea.width, h = dataArea.height;

      // Draw the reference names

      int row = dss.length + 1;
      int col = refNames.length + 1;

      int refLen = refNames.length;

      int dw = w/refLen, dh = h/row;

      Rectangle legendArea = getLegendArea( );

      ShapeStyle shapeStyle = getShapeStyle();

//      윤곽선 변수 정의
//
//      --------------------     <- upperLine
//      |                   |
//   -----------------------     <- middleLine
//   |  |                   |
//   ------------------------    <- bottomLine
//
//      leftLine           rightLine
//
//  legendLeftLine
//

      int lax = legendArea.x;

      if( outerLineShow ) {

	Line2D upperLine  = new Line2D.Double( x, y, x + w, y );
	Line2D leftLine   = new Line2D.Double( x, y, x, y + h );
	Line2D rightLine   = new Line2D.Double( x + w, y, x + w, y + h );

	Line2D middleLine = new Line2D.Double( lax, y + dh, x + w, y + dh );
	Line2D bottomLine = new Line2D.Double( lax, y + h,  x + w, y + h );
	Line2D legendLeftLine = new Line2D.Double( lax, y + dh, lax, y + h );

	shapeStyle.paint( g2, upperLine );
	shapeStyle.paint( g2, leftLine );
	shapeStyle.paint( g2, rightLine );
	shapeStyle.paint( g2, middleLine );
	shapeStyle.paint( g2, bottomLine );
	shapeStyle.paint( g2, legendLeftLine );

      }

      if( verticalLineShow ) {

	for( int i = 0; i < refLen - 1 ; i ++ ) {

	  Line2D vertLine = new Line2D.Double( x + dw*( i + 1 ), y, x + dw*( i + 1 ), y + h );

	  shapeStyle.paint( g2, vertLine );

	}

      }

      int dssLen = dss.length;

      if( horizontalLineShow ) {

	 for( int i = 0; i < dssLen - 1 ; i ++ ) {

	    Line2D horiLine = new Line2D.Double( lax, y + dh*( i + 2 ), x + w, y + dh*( i + 2 ) );

	    shapeStyle.paint( g2, horiLine );

	 }

      }

      for(int i = 0; i < refLen; i ++ ) {

	   Rectangle textArea = new Rectangle( x + i*dw, y, dw, dh );

	   String text = refNames[i];

	   super.drawString( this, text, g2, font, textArea );

      }

      // End of drawing the reference names

      // Draws the data value

      int dsNum = dss.length;

      y += dh; // skips one row

      for(int i = 0; i < dsNum ; i ++ ) {

	  DataSeries ds = dss[ i ];

	  DataElement des [] =  ds.getDataElements();

	  for(int k = 0; k < refLen; k ++ ) {

	     Rectangle textArea = new Rectangle( x + k*dw, y + i*dh, dw, dh );
	     String text = "" + des[k].getValue();
//	     shapeStyle.paint( g2, textArea );
	     super.drawString( this, text, g2, font, textArea );

	  }

      }

      // End of drawing the data values

      // Draws the legend items

      x = legendArea.x;
      w = legendArea.width;
      y = legendArea.y;

      PictureExtent pe = chart.getPictureExtent();

      boolean legendShow = this.legendShow;

      DataSeriesView dsvs [] = legendShow ? pe.getDataSeriesViews() : null ;

      for(int i = 0; i < dssLen; i ++ ) {

	  if( legendShow ) {

	    ShapeStyle legendSymbolStyle = ShapeStyle.getDefaultShapeStyle();

	    if( legendSymbolStyle != null ) {

	      Rectangle legendSymbolArea = new Rectangle( x + dh/4, y + ( i + 1)*dh + dh/4, dh/2, dh/2 );

	      legendSymbolStyle.paint( g2, legendSymbolArea );

	    }

	  }

	  int tx = legendShow ? x + dh : x;

	  int tw = legendShow ? w - dh : w;

	  Rectangle textArea = new Rectangle( tx, y + (i + 1)*dh, tw, dh );

	  String text = dss[i].getSeriesName();

	  super.drawString( this, text, g2, font, textArea );

      }

      // End of drawing the legend items

  }

  private Rectangle getDataArea() {

      Chart chart = getChart();

      PictureExtent pe = (PictureExtent) chart.getChartElement( PictureExtent.class );

      Rectangle peArea = pe.getArea();

      Rectangle area = getArea();

      return new Rectangle( peArea.x, area.y, peArea.width, area.height );

  }

  private Rectangle getLegendArea( ) {

      Chart chart = getChart();

      PictureExtent pe = (PictureExtent) chart.getChartElement( PictureExtent.class );

      Rectangle peArea = pe.getArea();

      Rectangle area = getArea();

      return new Rectangle( area.x, area.y, peArea.x - area.x, area.height );

  }

  public String toString() {

     return "데이터 테이블";

  }

  public ChartElement cloneSelf(ChartElement parent, SpreadSheet sheet) {

    AffineTransform at = AffineTransform.getTranslateInstance( 0, 0 );

    Shape shape = at.createTransformedShape( getShape() );

    return new DataTable( sheet, parent, shape, getShapeStyle().create(), getFont(), legendShow,
				     horizontalLineShow, verticalLineShow, outerLineShow );

  }

  public Rectangle [] getSelectedMarks() {

    return getSelectedMarksLineType( getDataTableShape() );

  }

  public boolean contains(double x, double y) {

    return getDataTableShape().contains( x, y );

  }

  public Shape getDataTableShape() {

    Polygon polygon = new Polygon();

    Chart chart = getChart();

    ChartData cd = chart.getChartData();

    DataSeries [] dss = cd.getDataSeries();

    String refNames [] = cd.getReferenceNames();

    Rectangle dataArea = getDataArea();

    Rectangle legendArea = getLegendArea( );

    int h = dataArea.height;

      // Draw the reference names

    int row = dss.length + 1;

    int refLen = refNames.length;

    int dh = h/row;

    polygon.addPoint( dataArea.x, dataArea.y );

    polygon.addPoint( dataArea.x + dataArea.width, dataArea.y );


    polygon.addPoint( legendArea.x, dataArea.y + dh );

    polygon.addPoint( dataArea.x, dataArea.y + dh );


    polygon.addPoint( legendArea.x,  dataArea.y + dataArea.height );

    polygon.addPoint( dataArea.x + dataArea.width, dataArea.y + dataArea.height );

    return polygon;

  }

}