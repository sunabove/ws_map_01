package jchart;

/**
* Title:        java chart project
* Description:  jchart v1.0
* Copyright:    Copyright (c) Suhng ByuhngMunn
* Company:      JCOSMOS DEVELOPMENT
* @author Suhng ByuhngMunn
* @version 1.0
*/
import jcosmos.Utility;
import java.awt.*;
import java.awt.geom.*;

public class Legend extends ChartElement implements ParentSelectable{



	private int locationType = AppRegistry.LEGEND_POSITION_NONE;

	private Chart chart;


	public Legend(SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style, Chart chart, int locationType) {

		super(sheet, parent, shape, style );

		this.chart = chart;

		this.locationType = locationType;

		getChartType().createLegendItems( this );

	}

	public ChartElement cloneSelf(ChartElement parent, SpreadSheet sheet) {

		ShapeAndStyle sns = cloneShapeAndShapeStyle();

		Legend element = new Legend( sheet, parent, shape, sns.getStyle(), chart, locationType );

		//     element.removeAllChild();

		return element;

	}

	//  public ChartElement clone(ChartElement parent) {
	//
	//       AffineTransform at = AffineTransform.getTranslateInstance( 0, 0 );
	//
	//       Shape cloneShape = at.createTransformedShape( shape );
	//
	//       ChartElement clone = new Legend( sheet, parent, cloneShape, shapeStyle.create(), locationType );
	//
	//       cloneChilds( clone );
	//
	//       return clone;
	//
	//  }

	public int getLocationType() {

		return locationType;

	}

	/**
	 * LegendFormatEditor의 Legend의 배치 탭에서 변경된 locationType에 따라 Legend의 위치를
	 * 변경해주고 locationType을 셋팅해주는 함수
	 */
	public void setLocationType(int locationType) {

		GraphExtent graphExtent = (GraphExtent) chart.getChartElement( GraphExtent.class );

		Rectangle parentArea = this.getParent().getArea(); // chart area
		Rectangle geArea = graphExtent.getArea();
		Rectangle area = this.getArea();

		int px = parentArea.x, py = parentArea.y;           // x, y of chart
		int pw = parentArea.width, ph = parentArea.height;  // width, height of chart
		int gex = geArea.x, gey = geArea.y;                 // x, y of graphExtent
		int gew = geArea.width, geh = geArea.height;        // width, height of graphExtent
		int x = area.x, y = area.y;                         // x, y of legend
		int w = area.width, h = area.height;                // width, height of legend
		int mgex = gex, mgey = gey;                         // x, y of modified graphExtent
		int mgew = gew, mgeh = geh;                         // width, height of modified graphExtent


		int mx = 0, my = 0;

		switch(locationType) {

			case AppRegistry.LEGEND_POSITION_BOTTOM :

				mx = (int)( ( px + pw * 0.5F - w * 0.5F ) - x );
				my = (int)( ( py + ph - ph * AppRegistry.LEGEND_MARGIN_RATIO - h ) - y );

				break;

			case AppRegistry.LEGEND_POSITION_CORNER :

			    mx = (int)( ( px + pw - pw * AppRegistry.LEGEND_MARGIN_RATIO - w ) - x );
				my = (int)( ( py + ph * AppRegistry.LEGEND_MARGIN_RATIO ) - y );

				break;

			case AppRegistry.LEGEND_POSITION_LEFT :

				mx = (int)( ( px + pw * AppRegistry.LEGEND_MARGIN_RATIO ) - x ) ;
				my = (int)( ( py + ph * 0.5F - h * 0.5F ) - y );

				break;

			case AppRegistry.LEGEND_POSITION_RIGHT :

			    mx = (int)( ( px + pw - pw * AppRegistry.LEGEND_MARGIN_RATIO - w ) - x ) ;
				my = (int)( ( py + ph * 0.5F - h * 0.5F ) - y );

				break;

			case AppRegistry.LEGEND_POSITION_TOP :

			    mx = (int)( ( px + pw * 0.5F - w * 0.5F ) - x );
				my = (int)( ( py + ph * AppRegistry.LEGEND_MARGIN_RATIO ) - y );

				break;

			case AppRegistry.LEGEND_POSITION_NONE :

				break;
		}

		this.moveBy( mx, my );

		this.locationType = locationType;

		reviseLegendShape();

		reviseGraphExtentPosition();    // GraphExtent의 위치를 보정시켜준다.

		chart.getSheet().repaint();

	}

	/**
	 * locationType에 따라 legend의 모양이 가로형 또는 세로형으로 Shape의 모양을 결정하며
	 * 그 안에 들어가는 legendItem의 위치 또한 변경 시켜준다.
	 */
	public void reviseLegendShape() {

		Chart chart = getChart();

		GraphExtent graphExtent = (GraphExtent) chart.getChartElement( GraphExtent.class );

		Rectangle cArea = chart.getArea(); // chart area
		Rectangle area = this.getArea();

		int x = area.x;
		int y = area.y;
		int w = area.width;
		int h = area.height;

		if ( locationType == AppRegistry.LEGEND_POSITION_LEFT || locationType == AppRegistry.LEGEND_POSITION_RIGHT ) {






			this.alignTo( graphExtent, Align.VER_CENTER );

		} else if ( locationType == AppRegistry.LEGEND_POSITION_BOTTOM || locationType == AppRegistry.LEGEND_POSITION_TOP ) {

//			w = (int)( cArea.width * Legend.LEGEND_WIDTH_RATIO );
//			h = (int)( cArea.height * Legend.LEGEND_HEIGHT_RATIO );
//
//			Rectangle rect = new Rectangle( x, y, w, h );
//
//		    this.setShape( rect );
//


			this.alignTo( graphExtent, Align.HOR_CENTER );

		}



	}

	/**
	 * locationType에 따라 legend의 위치가 옮겨졌을때 GraphExtent의 위치를 보정해주는 함수이다
	 *
	 */
	 public void reviseGraphExtentPosition() {

		GraphExtent graphExtent = (GraphExtent) chart.getChartElement( GraphExtent.class );

		Rectangle parentArea = this.getParent().getArea(); // chart area
		Rectangle geArea = graphExtent.getArea();
		Rectangle area = this.getArea();

		int px = parentArea.x, py = parentArea.y;           // x, y of chart
		int pw = parentArea.width, ph = parentArea.height;  // width, height of chart
		int gex = geArea.x, gey = geArea.y;                 // x, y of graphExtent
		int gew = geArea.width, geh = geArea.height;        // width, height of graphExtent
		int x = area.x, y = area.y;                         // x, y of legend
		int w = area.width, h = area.height;                // width, height of legend
		int mgex = gex, mgey = gey;                         // x, y of modified graphExtent
		int mgew = gew, mgeh = geh;                         // width, height of modified graphExtent
		float verticalMargin =  AppRegistry.LEGEND_MARGIN_RATIO * ph;
		float horizontalMargin = AppRegistry.LEGEND_MARGIN_RATIO * pw;

//		Utility.debug(this, "vm = " + verticalMargin + " hm = " + horizontalMargin);

		int revisionDistance = 0;                           // 보정거리

		switch(locationType) {

			case AppRegistry.LEGEND_POSITION_BOTTOM :

				if ( graphExtent.contains( x + 0.0, y - verticalMargin ) ) {

					revisionDistance = (int)(gey + geh -  y + verticalMargin );
					mgeh = geh - revisionDistance ;

				}

				break;

			case AppRegistry.LEGEND_POSITION_CORNER :

				if( graphExtent.contains( x - horizontalMargin, y + h + verticalMargin ) ) {

				    revisionDistance = (int)( gex + gew - x + horizontalMargin );
					mgew = gew - revisionDistance;

					revisionDistance = (int)( y + h + verticalMargin - gey );

					mgeh = geh - revisionDistance;
					mgey = gey + revisionDistance;

				}

				break;

			case AppRegistry.LEGEND_POSITION_LEFT :

			    if ( graphExtent.contains( x + w + horizontalMargin, y + 0.0 ) ) {

					revisionDistance = (int)( x + w + horizontalMargin - gex );
					mgex = gex + revisionDistance;
					mgew = gew - revisionDistance;

				}

				break;

			case AppRegistry.LEGEND_POSITION_RIGHT :

				if ( graphExtent.contains( x - horizontalMargin, y + 0.0 ) ) {

					revisionDistance = (int)( gex + gew - x + horizontalMargin );
					mgew = gew - revisionDistance;

				}

				break;

			case AppRegistry.LEGEND_POSITION_TOP :

				if ( graphExtent.contains( x + 0.0, y + h + verticalMargin ) ) {

				    revisionDistance = (int)( y + h + verticalMargin - gey );

					mgeh = geh - revisionDistance;
					mgey = gey + revisionDistance;

				}

				break;

			case AppRegistry.LEGEND_POSITION_NONE :

				break;

		}

		graphExtent.resizeAndMoveBy( mgew, mgeh, mgex - gex, mgey - gey);

	}


	public String toString() {
		return "범례";
	}

	public void applyAffineTransform(AffineTransform at) {

	  Shape shape = getShape();

	  this.shape = at.createTransformedShape( shape );

	  shape = this.shape;

	  Rectangle legendArea = shape.getBounds();

	  int ax = legendArea.x;
	  int ay = legendArea.y;

	  Rectangle cellRects [] = getCellRects( shape );

	  ChartElement [] childs = getChilds();

	  int maxWH [] = getMaxWidthHeightOfLegendItems();

	  int maxW = maxWH[ 0 ];
	  int maxH = maxWH[ 1 ];

	  int i = 0, cellRectsLen = cellRects.length;

	  int childsLen = childs.length;

	  for( ; i < cellRectsLen ; i ++ ) {

	    if( i > childsLen -1 ) {

	      break;

	    }

	    Rectangle cellRect = cellRects[ i ];

	    LegendItem legendItem = (LegendItem ) childs[ i ];

	    legendItem.setVisible( true );

	    Rectangle liArea = legendItem.getArea();

	    double x = cellRect.x + cellRect.width/2.0 - maxW/2.0;
	    double y = cellRect.y + cellRect.height/2.0 - maxH/2.0;

	    Insets insets = legendItem.getInsets();

	    x = ( x < ax + insets.left ) ? ax + insets.left : x;
	    y = ( y < ay + insets.top ) ? ay + insets.top : y;

	    legendItem.resizeAndMoveBy( liArea.width, liArea.height, (int)( x - liArea.x ), (int) ( y - liArea.y ) );

	  }

	  for(  ; i < childsLen; i ++ ) {

	    LegendItem legendItem = (LegendItem ) childs[ i ];

	    legendItem.setVisible( false );

	  }

	}

	public Rectangle [] getCellRects( Shape shape ) {

	  Rectangle area = shape.getBounds();

	  int ax = area.x, ay = area.y, aw = area.width, ah = area.height;

	  int [] maxWH = getMaxWidthHeightOfLegendItems();

	  int maxW = maxWH[ 0];
	  int maxH = maxWH[ 1 ];

	  int horiNum = aw/maxW;
	  int vertNum = ah/maxH;

	  horiNum = ( horiNum < 1 ) ? 1 : horiNum;
	  vertNum = ( vertNum < 1 ) ? 1 : vertNum;

	  int legendItemsNum = getChilds().length;

	  if( horiNum*vertNum > legendItemsNum ) {

	    vertNum = legendItemsNum/horiNum;

	    vertNum = ( vertNum < 1 ) ? 1 : vertNum;

	  }

//	  if( horiNum*vertNum < legendItemsNum && ah >= 2*maxH ) {
//
//	    vertNum += 1;
//
//	  }

	  if( vertNum == 1 && horiNum*vertNum > legendItemsNum ) {

	    horiNum = legendItemsNum;

	  }

	  Rectangle [] cellRects = new Rectangle [ horiNum*vertNum ];

	  int cw = aw/horiNum;
	  int ch = ah/vertNum;

	  for( int r = 0; r < vertNum; r ++ ) {

	    for( int c = 0; c < horiNum; c ++ ) {

	      cellRects[ r*horiNum + c ] = new Rectangle( ax + c*cw, ay + r*ch, cw, ch );

	    }

	  }

	  return cellRects;

	}

	public int [] getMaxWidthHeightOfLegendItems() {

	  ChartElement childs [] = getChilds(); // legend items

	  int maxW = 0, maxH = 0;

	  for( int i = 0, len = childs.length; i < len; i ++ ) {

	    Rectangle area = childs[ i ].getArea();

	    maxW = ( maxW > area.width  ) ? maxW : area.width;
	    maxH = ( maxH > area.height ) ? maxH : area.height;

	  }

	  return new int [] { maxW, maxH };

	}

	public void paintResizeMode(Graphics2D g2, int x, int y, int w, int h) {

		g2.setXORMode( Color.yellow );

		g2.setStroke( getDraggedStroke() );

		Shape xs = this.getXorShape();  // xor Shape

		if( xs != null ) {

			g2.draw( xs );

		}

		Rectangle [] innerDraggedRects = this.innerDraggedRects;

		if( innerDraggedRects != null ) {

		  for( int i = 0, len = innerDraggedRects.length; i < len; i ++ ) {

		    g2.draw( innerDraggedRects[ i ] );

		  }

		}

		Shape shape = getResizeShape();

		AffineTransform at = getResizeAffineTransform( x, y, w, h ); // resize affine transform

		shape = at.createTransformedShape( shape );

		g2.setColor( Color.black );

		g2.draw( shape );

		innerDraggedRects = getCellRects( shape );

		if( innerDraggedRects != null ) {

		  for( int i = 0, len = innerDraggedRects.length; i < len; i ++ ) {

		    g2.draw( innerDraggedRects[ i ] );

		  }

		}

		this.innerDraggedRects = innerDraggedRects;

		this.setXorShape( shape );

		g2.setPaintMode();

	}

	private Rectangle [] innerDraggedRects;

}