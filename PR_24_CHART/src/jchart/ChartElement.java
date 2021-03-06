
/**
* Title:        java chart project<p>
* Description:  jchart v1.0<p>
* Copyright:    Copyright (c) Suhng ByuhngMunn<p>
* Company:      JCOSMOS DEVELOPMENT<p>
* @author Suhng ByuhngMunn
* @version 1.0
*/
package jchart;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import jcosmos.*;
import com.jcosmos.java3d.*;

/**
 * abstract class that represents chart element.
 * chart element makes an chart
 */
public abstract class ChartElement {

	/**
	 *  dragging stroke
	 */
	public static final BasicStroke DRAGGED_STROKE = new BasicStroke( 1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float [] { 4, 4 }, 0 );

	/**
	 *  affine transform to clone shape
	 */
	protected static final AffineTransform cloneAt = AffineTransform.getTranslateInstance( 0, 0 );

	/**
	 * ChartElement를 포함하는 SpreadSheet
	 */
	protected SpreadSheet sheet;

	// chart element shape for 2D
	protected Shape shape;

	// chart element shape group for 3D
	protected Shape [][] shapes3DGroup;

	// chart element shape style
	protected ShapeStyle shapeStyle;

	/**
	 * ChartElement를 포함하고 있는 ChartElement를 나타낸다.
	 */
	protected ChartElement parent;


	/**
	 * child ChartElement를 담고 있는 자료구조로 Vector를 사용하여 나타낸다.
	 */
	protected Vector childs = new Vector();

	/**
	 * Shape의 margin inset를 나타낸다.
	 */
	private Insets insets;

	/**
	 * flag that represents selected state
	 */
	protected boolean selected;


	// topology to an point(x, y)

	public static final int OUT_OF_AREA = -1, INSIDE_AREA = 8;

	// Xor painting shape
	private Shape xorShape;

	// constructor
	public ChartElement(SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style) {

		this.sheet = sheet;
		this.parent = parent;
		this.shape = shape;
		this.shapeStyle = style;
		this.insets = createInsets();

	}

	/**
	 * ChartElement를 포함하는 spreadSheet를 리턴한다.
	 */
	public SpreadSheet getSheet() {

		return sheet;

	}

	/**
	 * xorShape를 리턴한다.(근데 xorShape이 뭐지? 공부좀 해야겠군 쿠쿠쿠)
	 */
	public Shape getXorShape() {

		return xorShape;

	}

	/**
	 * xorShape를 shape으로 설정한다.
	 */
	public void setXorShape(Shape shape) {

		this.xorShape = shape;

	}

	/**
	 * ChartElement를 담고 있는 Chart를 리턴한다.
	 * return chart that this chart element are belong to
	 */
	public Chart getChart() {

		ChartElement ce = this;

		while( ce != null ) {

			if( ce instanceof Chart ) {

				return (Chart) ce;

			} else {

				ce = ce.getParent();

			}

		}

		return null;

	}

	/**
	 * ChartElement를 가지고 있는 Chart의 ChartType를 리턴한다.
	 * return chart type of this chart element's chart
	 */
	public ChartType getChartType() {

		Chart chart = getChart();

		if( chart == null ) {

			return null;

		} else {

			return chart.getChartType();

		}

	}

	/**
	 * ChartElement의 insets를 설정하여 리턴한다.
	 * returns chart element's insets
	 */
	private Insets createInsets() {

		if( this instanceof Axis || this instanceof PictureExtent ) {

			return new Insets(0, 0, 0, 0);

		} else {

			return new Insets(6, 6, 6, 6);

		}

	}

	/**
	 * child ChartElement 중 klass에 해당하는 ChartElement가 있으면 리턴한다.
	 * 재귀 호출로 구현되어 있다.
	 * returns chart element that has key among childs chart elements
	 */
	public ChartElement getChartElement(Class klass) {

		Enumeration enumIt = childs.elements();

		while( enumIt.hasMoreElements() ) {

			Object obj = enumIt.nextElement();

			if( obj.getClass() == klass ) {

				return (ChartElement) obj;
			}

		}

		ChartElement chartElement = null;

		if( chartElement == null ) {

			enumIt = childs.elements();

			while( enumIt.hasMoreElements() ) {

				ChartElement ce = (ChartElement) enumIt.nextElement();
				// recusive function
				chartElement = ce.getChartElement( klass );

				if( chartElement != null ) {
					break;
				}
			}
		}
		return chartElement;
	}

	/**
	 * affineTransform을 이용하여 ChartElement를 (x, y)만큼 이동시킨다.
	 * move shape by affine transform
	 */
	public void moveBy(int x, int y) {

		AffineTransform at = AffineTransform.getTranslateInstance( x, y );

		applyAffineTransform( at );

	}

	/**
	 * ChartElement와 그에 속하는 모든 child ChartElement들에 대하여 affineTransform을 적용시킨다.
	 * apply affine transform to this chart element and all child chart elements
	 *
	 * @param   at : 적용시킬 AffineTransform 개체
	 */
	public void applyAffineTransform(AffineTransform at) {

		this.shape = at.createTransformedShape( shape );

		this.shapes3DGroup = null;

		applyAffineTransformToChilds( at );

	}

	public void applyAffineTransformToChilds(AffineTransform at) {

		Enumeration enumIt = childs.elements();

		while( enumIt.hasMoreElements() ) {

			ChartElement ce = (ChartElement) enumIt.nextElement();
			ce.applyAffineTransform( at );

		}

	}

	/**
	 * ChartElement에 원하는 ChartElement를 child ChartElement로 add한다.
	 * add an child chart element
	 */
	public void addChild(ChartElement element) {

		Vector childs = this.childs;

		if( !( element instanceof MultipleChild ) ){
			Class refKlass = element.getClass();

			Enumeration enumIt = childs.elements();

			while( enumIt.hasMoreElements() ) {
				Object obj = enumIt.nextElement();

				if( obj.getClass() == refKlass ) {
					childs.removeElement( obj );
					break;
				}
			}
		}
		childs.addElement( element );
	}

	/**
	 * 원하는 ChartElement를 child ChartElement에서 삭제시킨다.
	 */
	public void removeChild(ChartElement element) {

		if( childs.contains( element ) ) {

			childs.removeElement( element );
		}
	}

	/**
	 * 모든 child ChartElement를 삭제시킨다.
	 */
	public void removeAllChild() {

		childs.removeAllElements();
	}

	/**
	 * child ChartElement의 개수를 리턴한다.
	 * returns child chart element's number
	 */
	public int getChildsNumber() {

		return childs.size();
	}

	/**
	 * ChartElement를 포함하는 spreadSheet를 리턴한다.
	 * returns spread sheet that has the chart that this chart element is belong to
	 */
	public SpreadSheet getSpreadSheet() {

		return sheet;
	}

	/**
	 * parent ChartElement인 parent를 리턴한다.
	 * returns parent chart element
	 */
	public ChartElement getParent() {

		return parent;
	}

	/**
	 * parent ChartElement인 parent를 원하는 값으로 셋팅한다.
	 * sets parent chart element
	 */
	public void setParent(ChartElement parent) {

		this.parent = parent;

	}

	/**
	 * chartElements에 속하는 child ChartElement를 리턴한다.
	 */
	public ChartElement [] getChilds() {

		Vector childs = this.childs;

		ChartElement [] ces = new ChartElement[ childs.size() ];

		Enumeration enumIt = childs.elements();

		int len = ces.length;

		for(int i = 0; i < len; i ++ ) {

			ces[i] = (ChartElement) enumIt.nextElement();

		}

		return ces;

	}

	/**
	 * process mouse event to this chart element and all child chart elements
	 * skipChilds flag is meaningful when the class is instanceof ParentSelectable
	 */
	public boolean processMouseEvent(MouseEvent e, boolean skipChilds) {
		//Utility.debug(this, "Processing Mouse Event at " + this.getClass().getName() );

		int x = e.getX(), y = e.getY();		// 현재의 mouse의 위치를 얻어온다.

		if( contains( x, y ) ) {

			int id = e.getID();   // mouse event type

			if( id == e.MOUSE_MOVED ) { // to select tooltip chart element

				sheet.setSelectedChartElement( this, e );

			} else if( id == e.MOUSE_PRESSED ) {

				if( this instanceof UnSelectable ) {

					// Do nothing!

				} else if( this instanceof ParentSelectable ) {

					// 현재의 ChartElment개체를 담고 있는 Series 개체가 있으면

					sheet.setSelectedChartElement( this, e );

					if( skipChilds ) {
						//Utility.debug(this, "skipped processing child chart elements." );
						return true;
					}

				} else {

					sheet.setSelectedChartElement( this, e );

				}

			}

			ChartElement [] ces = getChilds(); // chart elements

			int len = ces.length;  // child elements length

			for(int i = len -1; i > -1 ; i -- ) {

				if( ces[i].processMouseEvent( e, true) ) {

					return true;

				}

			}

			return true;

		}

		return false;

	}

	/**
	 * 마우스 pressed event가 발생했을 때 현재 선택된 ChartElement를 selected mode로
	 * 그리기 위해 필요한 mark rectangles를 리턴한다.
	 */
	public Rectangle [] getSelectedMarks() {

		Rectangle [] marks = null;

		if( this instanceof Chart ) {
			// ChartElement가 Chart인 경우 marks를 boundary의 안쪽에 생성시킨다.
			// Utility.debug(this, "SEL MARKS CHART " + this.getClass() );
			marks = getSelectedMarksInsideBoundary();

		} else if( this instanceof DataElementView ) {
			// ChartElement가 DataElementView인 경우 marks를 ChartType에 의해 결정된다.
			// Utility.debug(this, "SEL MARKS DATEELEMENT " + this.getClass() );
			DataElementView dev = (DataElementView) this;

			ChartType type = dev.getChartType();

			marks = type.getSelectedMarks( dev );

		} else if( this instanceof LinearChartElement ) {
			// Utility.debug(this, "SEL MARKS LINE " + this.getClass() );
			// ChartElement가 LinearChartElement인 경우 marks를 선의 양쪽 끝에 생성시킨다.
			marks = getSelectedMarksLineType();

		} else {
			// Utility.debug(this, "SEL MARKS RECT " + this.getClass() );
			// ChartElement가 그 이외일때는 marks를 boundary 위에 생성시킨다.
			marks = getSelectedMarksMiddleOnBoundary();

		}

		return marks;

	}


	/**
	 * point p1, p2를 이은 line이 point p3를 포함하는지의 여부를 boolean형식으로 리턴한다.
	 */
	private boolean containsLine(Point2D p1, Point2D p2, Point2D p3) {

		double d12 = p1.distance( p2 ); // distance (p1, p2)
		double d13 = p1.distance( p3 );
		double d23 = p2.distance( p3 );

		if( Math.abs( d13 + d23 - d12 ) < 2 ) {
			return true;
		}

		return false;

	}

	/**
	 * ChartElement의 Shape이 position (x, y)를 포함하는지 여부를 리턴한다.
	 * represents if this chart element shape contains the point x, y
	 */
	public boolean contains(double x, double y) {

		Shape shape = this.shape;

		if( shapes3DGroup != null ) {  // in case of chart type 3d

			return contains3D( x, y );

		} else if( this instanceof ChildSelectable ) {

			ChartElement [] childs = getChilds();

			for(int i = 0, len = childs.length; i < len; i ++ ) {

				if( childs[i].contains( x, y ) ) {

					return true;

				}

			}

			return (shape != null) ? contains( shape, x, y ) : false;

		} else if( shape == null ) {

			ChartElement [] childs = getChilds();

			for(int i = 0, len = childs.length; i < len; i ++ ) {

				if( childs[i].contains( x, y ) ) {

					return true;

				}

			}

			return false;

		} else {

			return contains( shape, x, y );

		}

	}

	/**
	 *
	 */
	protected boolean contains3D( double x, double y ) {

		Shape [][] shapes3DGroup = this.shapes3DGroup;

		for(int i = 0, iLen = shapes3DGroup.length; i < iLen; i ++ ) {

			Shape [] shapes = shapes3DGroup[ i ] ;

			if( shapes == null ) {

			    continue;

			}

			for(int k = 0, kLen = shapes.length ; k < kLen; k ++ ) {

				Shape shape = shapes[ k ] ;

				if( shape != null && contains( shape, x, y ) ) {

					return true;

				}

			}

		}

		return false;

	}

	/**
	 * Shape shape이 point (x,y)를 포함하는지의 여부를 boolean 형식으로 리턴한다.
	 */

	private boolean contains(Shape shape, double x, double y) {

		if( shape instanceof Line2D ) {

			Line2D line = (Line2D) shape;

			Point2D p1 = line.getP1();
			Point2D p2 = line.getP2();

			Point2D p3 = new Point2D.Double(x, y);

			return containsLine(p1, p2, p3 );

		} else if( this instanceof LinearChartElement ) {

		       return containsLinearShape( shape, x, y );

		} else {

			return shape.contains( x, y );

		}

	}

	private boolean containsLinearShape(Shape shape, double x, double y ) {

	     Point2D points [] = ShapeUtilities.getVertexesPoint2D( shape );

	     Point2D p3 = new Point2D.Double(x, y);

	     for( int i = 0, len = points.length - 1; i < len; i ++ ) {

		if( containsLine( points[i], points[ i + 1 ], p3 ) ) {

		   return true;

		}

	     }

	     return false;

	}

	/**
	 * ChartElement obj를 child ChartElement로 포함하는지 여부를 리턴한다.
	 */
	public boolean contains(ChartElement obj) {

		// 바로 하위의 child ChartElement에 obj가 있는지 조사한다.
		if( childs.contains( obj )  ) {

			return true;
		}

		// 없을 때에는 child ChartElement를 하나씩 호출하여
		// contains(ChartElement)메서드를 제귀호출한다.
		ChartElement [] childs = getChilds();

		for(int i = 0, len = childs.length; i < len; i ++ ) {

			if( childs[i].contains( obj ) ) {

				return true;
			}
		}
		return false;
	}

	/**
	 *
	 * returns selected mark inside the chart element shape's boundary
	 */
	protected Rectangle [] getSelectedMarksInsideBoundary() {

		Rectangle bounds = getArea();

		int x = bounds.x, y = bounds.y, w = bounds.width, h = bounds.height;

		Rectangle [] marks = new Rectangle[8];

		int WI = 6, HE = 6;
		int mx = x + (w - WI)/2, my = y + (h - HE)/2;
		int ex = x + w - WI, ey = y + h - HE;

		marks[0] = new Rectangle(x, y, WI, HE );
		marks[1] = new Rectangle(mx, y, WI, HE );
		marks[2] = new Rectangle(ex, y, WI, HE );

		marks[3] = new Rectangle(x, my, WI, HE );
		marks[4] = new Rectangle(ex, my, WI, HE );

		marks[5] = new Rectangle(x, ey, WI, HE );
		marks[6] = new Rectangle(mx, ey, WI, HE );
		marks[7] = new Rectangle(ex, ey, WI, HE );

		return marks;

	}

	/**
	 *
	 */
	private Rectangle getRectForSelectedMarks() {

		return getArea();

	}

	/**
	 * boundary 위에 위치하는 marks를 생성하고 그 marks를 리턴한다.
	 * returns the selected marks rectangles on the middle boundary
	 */
	protected Rectangle [] getSelectedMarksMiddleOnBoundary() {

		return getSelectedMarksMiddleOnBoundary( getRectForSelectedMarks() );

	}

	protected Rectangle [] getSelectedMarksMiddleOnBoundary(Rectangle area) {

		if( area == null ) {

		   return null;

		}

		int x = area.x, y = area.y, w = area.width, h = area.height;

		Rectangle [] marks = new Rectangle[8];

		int WI = 6, HE = 6;

		int mx = x + (w - WI)/2, my = y + (h - HE)/2;
		int ex = x + w - WI/2, ey = y + h - HE/2;

		x -= WI/2; y -= HE/2;

		marks[0] = new Rectangle(x, y, WI, HE );
		marks[1] = new Rectangle(mx, y, WI, HE );
		marks[2] = new Rectangle(ex, y, WI, HE );

		marks[3] = new Rectangle(x, my, WI, HE );
		marks[4] = new Rectangle(ex, my, WI, HE );

		marks[5] = new Rectangle(x, ey, WI, HE );
		marks[6] = new Rectangle(mx, ey, WI, HE );
		marks[7] = new Rectangle(ex, ey, WI, HE );

		// 만약 ChartElement가 DataElementView가 아니면서 ChartElement의 Area가
		// 작을 경우 불필요한 marks를 제거한다.
		// width가 작을 경우

		if ( ! (this instanceof DataElementView ) ) {

			if( w < WI*3.3 ) {

			  marks[1] = marks[6] = null;

			}

			// heigth가 작을 경우
			if( h < HE*3.3 ) {

			    marks[3] = marks[4] = null;

			}
		}

		return marks;

	}

	/**
	 * PictureExtent 위에 존재할 marks를 생성하고 그 marks를 리턴한다.
	 */
	protected Rectangle [] getSimpleSelectedMarksMiddleOnBoundary() {

		Rectangle area = getArea();

		int x = area.x, y = area.y, w = area.width, h = area.height;

		Rectangle [] marks = new Rectangle[4];

		int WI = 6, HE = 6;

		int ex = x + w - WI/2, ey = y + h - HE/2;

		x -= WI/2; y -= HE/2;

		marks[0] = new Rectangle(x, y, WI, HE );
		marks[1] = new Rectangle(ex, y, WI, HE );

		marks[2] = new Rectangle(x, ey, WI, HE );
		marks[3] = new Rectangle(ex, ey, WI, HE );

		return marks;

	}

	/**
	 * returns the selected marks rectangles on the middle boundary
	 */

	protected Rectangle [] getSelectedMarksLineType() {

		ChartType ct = getChartType();

		if( ct.isChartTypeCircle3D() ) {

			int index = 4;

			if( shapes3DGroup == null ) {

			      return null;

			} else if( shapes3DGroup[ index ] == null ) {

			      return null;

			}

			return getSelectedMarksLineType(  shapes3DGroup[index ][0] );

		} else if( ct instanceof ChartType3D ) {

		    return getSelectedMarksLineType3D();

		} else {

		    return getSelectedMarksLineType( shape );

		}

	}

	protected Rectangle [] getSelectedMarksLineType3D() {

		Shape [][] shapes3DGroup = this.shapes3DGroup;

		if( shapes3DGroup == null ) {

		    return null;

		}

		return getSelectedMarksLineType3D( shapes3DGroup );

	}

	protected Rectangle [] getSelectedMarksLineType3D(Shape [][] shapes3DGroup ) {

		Vector marksVec = new Vector();

		for(int i = 0, iLen = shapes3DGroup.length; i < iLen; i ++ ) {

			Shape [] shapes = shapes3DGroup[ i ] ;

			for(int k = 0, kLen = ( shapes == null ? 0 : shapes.length ); k < kLen; k ++ ) {

				Shape shape = shapes[ k ] ;

				Rectangle marks [] = getSelectedMarksLineType( shape );

				for(int m = 0, mLen = marks.length; m < mLen; m ++ ) {

					if( marks[m] != null ) {

						marksVec.add( marks[ m ] );

					}

				}

			}

		}

		int len = marksVec.size();

		Rectangle marks [] = new Rectangle[ len ];

		Enumeration enumIt = marksVec.elements();

		for(int i = 0; i < len; i ++ ) {

			marks[ i ] = (Rectangle) enumIt.nextElement();

		}

		return marks;

	}

	protected static Rectangle [] getSelectedMarksLineType(Shape shape) {

		Point [] vertexes = ShapeUtilities.getVertexes( shape );

		int WI = 6, HE = 6;

		int len = vertexes.length;

		Rectangle marks [] = new Rectangle[ len ];

		for(int i = 0; i < len; i ++ ) {

			marks[i] = new Rectangle( vertexes[i].x - WI/2, vertexes[i].y - HE/2, WI, HE );

		}

		return marks;

	}

	/**
	 * set's selected state
	 */
	public void setSelected(boolean selected) {

		this.selected = selected;

	}

	/**
	 * returns whether or not selected
	 */
	public boolean isSelected() {

		return selected;

	}

	/**
	 * paint chart element and child chart elements graphically
	 */

	public void paint(Graphics2D g2) {

		paintSelf( g2 );

		paintChilds( g2 );

	}

	public void paintSelf(Graphics2D g2) {

		if( isPaintable() ) {

			shapeStyle.paint( g2, shape );

			if( selected ) {

				paintSelected( g2 );

			}

		}

	}

	public void paintChilds(Graphics2D g2) {

		Enumeration enumIt = childs.elements();

		while( enumIt.hasMoreElements() ) {
			((ChartElement) enumIt.nextElement()).paint( g2 );
		}

	}

	public boolean isPaintable() {

		return shapeStyle != null && shape != null;

	}

	public void paint3DSelfOnly(float tx, float ty, Graphics2D g2, float proX, float proY ) {

		if( isPaintable() ) {

			Shape [][] shapess = getShapes3DGroup( tx, ty, shape, proX, proY );

			shapeStyle.paint3DShapess( g2, shapess );

			if( selected ) {

				paintSelected( g2 );

			}

		}

	}

	public void paint3DSelfOnly(float tx, float ty, Graphics2D g2, float proX, float proY, float top, float bottom ) {

		if( isPaintable() ) {

			Shape [][] shapess = getShapes3DGroup( tx, ty, shape, proX, proY, top, bottom );

			shapeStyle.paint3DShapess( g2, shapess );

			if( selected ) {

				paintSelected( g2 );

			}

		}

	}

	/**
	 * 삼차원을 구성하는 도형 그룹
	 * 가비지의 생성을 최소화 하기 위한 알고리즘임.
	 * 넘 힘들다. (사랑은 아무나 하나)(내게도 사랑이 사랑이 있었다면)
	 */

	protected Shape [][] getShapes3DGroup(float tx, float ty, Shape shape, float proX, float proY) {

		Shape [][] shapes3DGroup = this.shapes3DGroup;

		if( shapes3DGroup == null ) {

			shapes3DGroup = createShapes3DGroup( tx, ty, shape, proX, proY);

			this.shapes3DGroup = shapes3DGroup;

		}

		return shapes3DGroup;

	}

	protected Shape [][] getShapes3DGroup(float tx, float ty, Shape shape, float proX, float proY,
		  float top, float bottom) {

		Shape [][] shapes3DGroup = this.shapes3DGroup;

		if( shapes3DGroup == null ) {

			shapes3DGroup = createShapes3DGroup( tx, ty, shape, proX, proY, top, bottom );

			this.shapes3DGroup = shapes3DGroup;

		}

		return shapes3DGroup;

	}

	protected Shape [][] createShapes3DGroup(float tx, float ty, Shape shape, float proX, float proY) {

		return getChartType().createShapes3DGroup( tx, ty, shape, proX, proY );

	}

	protected Shape [][] createShapes3DGroup(float tx, float ty, Shape shape, float proX, float proY,
		  float top, float bottom ) {

		return getChartType().createShapes3DGroup( tx, ty, shape, proX, proY, top, bottom );

	}

	/**
	 * paint chart element and child chart elements graphically
	 */

	public void paint3D(float tx, float ty, Graphics2D g2, float proX, float proY ) {

		paint3DSelfOnly( tx, ty, g2, proX, proY );

		Enumeration enumIt = childs.elements();

		while( enumIt.hasMoreElements() ) {

			((ChartElement) enumIt.nextElement()).paint3D( tx, ty, g2, proX, proY );

		}

	}

	public void addElementToThreeDimObjectPool(ThreeDimObjectPool pool) {

		pool.add( this );

		Enumeration enumIt = childs.elements();

		while( enumIt.hasMoreElements() ) {

			((ChartElement) enumIt.nextElement()).addElementToThreeDimObjectPool( pool );

		}

	}

	/**
	 * 마우스 click 이벤트에 의해 선택된 ChartElement를 paint 한다.
	 */
	public void paintSelected(Graphics2D g2) {

		if( this instanceof ChildSelectable ) {		// 만약 child가 존재하면 child ChartElement도 리페인트한다.

			ChartElement [] childs = getChilds();

			for(int i = 0, len = childs.length; i < len; i ++ ) {

				childs[i].paintSelected( g2 );

			}

		} else {

			// 선택된 ChartElement에 selected mode를 나타내기 위한 marks를 설정한다.

			Rectangle [] marks = getSelectedMarks();

			if( marks == null ) {

			    return;

			}

			int len = marks.length;

			g2.setColor( Color.black );

			if( this instanceof PictureExtent ) {
				// 선택된 ChartElement가 PictureExtent인 경우
				g2.setStroke( getDraggedStroke() );
				g2.draw( shape );

			}

			g2.setStroke( new BasicStroke(1) );

			for(int i = 0; i < len; i ++ ) {

				ChartType ct = this.getChartType();

				if( marks[i] != null ) {

					if ( ct instanceof FeedbackChartType
						&& this instanceof DataElementView
						&& i == ct.getFeedbackTopology() ) {

					g2.setColor( Color.blue );
					    g2.fill( marks[i] );
						g2.setColor( Color.black );

					} else {

					    g2.fill( marks[i] );

				    }

				}


			}

		}

	}

	/**
	 * returns calibrated move point of x and y
	 */
	public Point getMovePoint(int x, int y) {

		Rectangle bounds = getMovableBounds();

		Rectangle area = getArea();

		if( x < 0 ) {
			int mx = bounds.x - area.x;   // maximum x range ( negative )

			x = ( x > mx ) ? x : mx;
		} else if( x > 0 ) {
			int mx = bounds.x + bounds.width - (area.x + area.width );  // maximum x range ( positive )

			x = ( x < mx ) ? x : mx;
		}

		if( y < 0 ) {
			int my = bounds.y - area.y; //  maximum y range ( negative )

			y = ( y > my ) ? y : my;
		} else if( y > 0 ) {
			int my = bounds.y + bounds.height - ( area.y + area.height );  // maximum y range ( positive )

			y = ( y < my ) ? y : my;
		}

		return new Point( x, y );

	}

	/**
	 * paint graphically when move by mouse dragging
	 */
	public void paintMoveMode(Graphics2D g2, int x, int y) {

	    paintMoveMode(g2, x, y, this.shape );

	}

	/**
	 * paint graphically when move by mouse dragging using the specific Shape size
	 */

	public void paintMoveMode(Graphics2D g2, int x, int y, Shape shape ) {

		Point mp = getMovePoint( x, y ) ; // move point

		AffineTransform at = AffineTransform.getTranslateInstance( mp.x, mp.y ); // translate affinte transform

		shape = at.createTransformedShape( shape );

		g2.setColor( Color.black );

		g2.setXORMode( Color.yellow );

		g2.setStroke( getDraggedStroke() );

		Shape xs = this.xorShape;  // xor Shape

		if( xs != null ) {

			g2.draw( xs );

		}

		g2.draw( shape );

		this.setXorShape( shape );

		g2.setPaintMode();

	}

	public AffineTransform getResizeAffineTransform(int x, int y, int w, int h) {

		Point loc = getLocation();

		int newLocX = ( w >= 0 ) ? loc.x + x : loc.x + x + w;
		int newLocY = ( h >= 0 ) ? loc.y + y : loc.y + y + h;

		Rectangle area = getArea();

		AffineTransform at = AffineTransform.getTranslateInstance( newLocX, newLocY );

		at.scale(
			  ( Math.abs( w ) + 0.0)/(area.width + 0.0),
			  ( Math.abs( h ) + 0.0)/(area.height + 0.0)
			);

		at.translate( - loc.x, - loc.y );

		return at;

	}

	public void resizeAndMoveBy(MouseEvent from, MouseEvent to, int topology) {

//	    Point p = getResizeVector( from, to );
//
//	    Rectangle rv = getResizeValue( p.x, p.y, topology );
//
//	    resizeAndMoveBy( rv.width, rv.height, rv.x, rv.y );

	}

	public void resizeAndMoveBy(int width, int height, int x, int y) {

	    applyAffineTransform( getResizeAffineTransform( x, y, width, height ) );

	}

	public void paintResizeMode(Graphics2D g2, MouseEvent from, MouseEvent to, int topology) {



	}

	public void paintResizeMode(Graphics2D g2, int mx, int my){

	    // clear old arc

		g2.setXORMode( Color.yellow );

		Shape xs = this.getXorShape();

		if( xs != null ) {

		    g2.draw( xs );

		}

		Point2D [] vp = ShapeUtilities.getPieControlPoints( shape );

		Point2D rootVertex = vp[ 0 ];
		Point2D otherVertex = vp[ 1 ];
		Point2D feedbackVertex = vp[ 2 ];

		double startAngle = Utility.getAngleForArc(  rootVertex.getX()
											    ,  rootVertex.getY()
											    , (double) mx
												, (double) my );

		double extentAngle = Utility.getAngleForArc( rootVertex.getX()
											    , rootVertex.getY()
											    , otherVertex.getX()
												, otherVertex.getY()
												, (double) mx
												, (double) my );


		Shape shape = this.shape;




		double radius = Utility.getDistance( rootVertex, feedbackVertex );
		double x = rootVertex.getX() - radius, y = rootVertex.getY() - radius;

		int type = Arc2D.PIE; // arc type

		Arc2D arc = new Arc2D.Double( x, y, radius * 2, radius * 2, startAngle, extentAngle, type );

		Utility.debug(this, "x = " + x + ", y = " + y + ", r = " + radius
					+ ", startAngle = " + startAngle + ", extentAngle = " + extentAngle);

		g2.setColor(Color.red);
		g2.draw( arc );

		this.setXorShape( arc );

		g2.setPaintMode();

	}


	/**
	 * xorShape을 페이트 해주는 함수이다.
	 * paint graphically resize outer line
	 *
	 * @param   g2 : 페인트 해줄 Graphic2D 개체
	 * @param   x  : xorShape의 x 좌표
	 * @param   y  : xorShape의 y 좌표
	 * @param   w  : xorShape의 너비
	 * @param   h  : xorShape의 높이
	 * @see getResizeAffineTransform(int, int, int, int)
	 */

	private void paintResizeMode(Graphics2D g2, int x, int y, int w, int h) {

		g2.setXORMode( Color.yellow );

		g2.setStroke( getDraggedStroke() );

		Shape xs = this.getXorShape();  // xor Shape

		if( xs != null ) {

			g2.draw( xs );

		}

		Shape shape = getResizeShape();

		AffineTransform at = getResizeAffineTransform( x, y, w, h ); // resize affine transform

		shape = at.createTransformedShape( shape );

		g2.setColor( Color.black );

		g2.draw( shape );

		this.setXorShape( shape );

		g2.setPaintMode();

	}

	public Shape getResizeShape(){

	  return this.shape;

	}

	/**
	 * returns dragging stoke
	 */
	public BasicStroke getDraggedStroke() {

		return DRAGGED_STROKE;

	}

	/**
	 * prints chart element information like location and size
	 */
	public void printChartElement() {

		Point loc = getLocation();

		Dimension size = getSize();

		//Utility.debug(this, "" + loc);
		//Utility.debug(this, "" + size);

	}

	/**
	 * returns chart element shape
	 */
	public Shape getShape() {

		return shape;

	}

	/**
	 * sets chart element shape
	 */
	public void setShape(Shape shape) {
//		Utility.debug(this, "SET SHAPE = " + shape);

		this.shape = shape;

	}

	/**
	 * sets chart element shape style
	 */
	public void setShapeStyle(ShapeStyle style) {

		this.shapeStyle = style;

	}

	/**
	 * returns chart element shape style
	 */
	public ShapeStyle getShapeStyle() {

		return this.shapeStyle;

	}

	/**
	 * ChartElement의 위치를 Point형식으로 리턴한다.
	 * returns chartElement location
	 */
	public Point getLocation() {

		if( shape == null ) {
			return null;
		}

		return getArea().getLocation();

	}



	/**
	 * returns chart element dimension size
	 */
	public Dimension getSize() {

		if( shape == null ) {

			return null;

		}

		return shape.getBounds().getSize();

	}

	/**
	 * returns chart element bounds that are movable and resizable
	 */
	public Rectangle getBounds() {

		if( this instanceof Chart ) {

			return sheet.getBounds();

		} else {

			if( parent == null ) {
				return null;
			}

			return parent.getShape().getBounds();

		}

	}

	/**
	 * 마우스 위치에 따라서 ChartElemnt의 topology 값을 결정한다
	 * returns topology of mouse point and chart element area
	 */

	public int getTopology(MouseEvent e) {

		if( shape == null ) {

			return OUT_OF_AREA; // out of area

		}

		int x = e.getX(), y = e.getY();

		Rectangle [] marks = getSelectedMarks();

		if( marks == null ) {

			return OUT_OF_AREA; // out of area

		}

		int len = marks.length;

		for(int i = 0; i < len; i ++ ) {

			Rectangle rect = marks[i];

			if( rect != null && rect.contains(x, y) ) {

				return i;

			}

		}

		Rectangle area = getArea();

		if( ! area.contains(x, y) ) {

			return OUT_OF_AREA;   // out of area

		}

		return INSIDE_AREA; // inside

	}

	/**
	 * returns chart element move bounds except insets
	 */
	private Rectangle getMovableBounds() {

		Rectangle bounds = null;

		if( this instanceof Chart ) {

			bounds = sheet.getBounds();

		} else {

			if( parent == null ) {

				return null;

			}

			Shape ps = parent.getShape(); // parent shape

			if( ps == null ) {

				return null;

			}

			if ( this instanceof DataLabelView ) {


				PictureExtent pe = this.getChart().getPictureExtent();

			    return pe.getShape().getBounds();

			}

			bounds = ps.getBounds();

		}

		Insets insets = getInsets();

		bounds.x += insets.left;
		bounds.y += insets.top;

		bounds.width -= (insets.left + insets.right );
		bounds.height -= (insets.top + insets.bottom );

		return bounds;

	}

	public Insets getInsets() {

	      return insets;

	}

	/**
	 * shape을 담을 수있는 Rectagle 영역을 Rectangle 형식으로 리턴한다.
	 * return shape area rectangle
	 */
	public Rectangle getArea() {

		if( shape == null ) {

			return null;

		} else {

			return shape.getBounds();

		}

	}

	/**
	 * shape을 담을 수있는 Rectagle 영역의 width를 리턴한다.
	 * returns area width
	 */
	public int getWidth() {

		return getArea().width;

	}

	/**
	 * shape을 담을 수있는 Rectagle 영역이 height를 리턴한다.
	 * returns area height
	 */
	public int getHeight() {

		return getArea().height;

	}

	/**
	 * draw text on the rectangle area vertically or horizontally
	 */
	public static void drawString(ChartElement element, String text, Graphics2D g2, Font font, Rectangle rect ) {

		g2.setFont( font );

		FontMetrics fm = g2.getFontMetrics();

		int sw = fm.stringWidth( text ); // string width

		int  x = rect.x + rect.width/2 - sw/2;

		int  y = rect.y + rect.height/2 + fm.getAscent()/2 ;

		if( element == null || element instanceof TickLabel || element instanceof LegendItem
			|| element instanceof DataTable ) {
		} else {
			y -= fm.getDescent();
		}

		g2.drawString( text, x, y );

	}

	/**
	 * align this chart element to element by the align type
	 */

	public void alignTo(ChartElement element, int align) {

		Point p = getDisplacementTo( element, align );

		moveBy(p.x, p.y);

	}

	private Point getDisplacementTo(ChartElement element, int align) {

		Rectangle bounds = getArea();

		Rectangle refBounds = element.getArea();

		Point p = new Point();

		if( align == Align.VER_CENTER ) {
			int cy = bounds.y + bounds.height/2;
			int rcy = refBounds.y + refBounds.height/2;
			p.y = rcy - cy;
		} else if( align == Align.TOP ) {
			p.y = refBounds.y - bounds.y;
		} else if( align == Align.BOTTOM ) {
			p.y = refBounds.y + refBounds.height - bounds.y - bounds.height;
		} else if( align == Align.HOR_CENTER ) {
			int cx = bounds.x + bounds.width/2;
			int rcx = refBounds.x + refBounds.width/2;
			p.x = rcx - cx;
		}

		return p;

	}

	public String toString() {

		if( this instanceof ParentToolTip ) {

			return parent.toString();

		} else {

			return super.toString();

		}

	}

	public ShapeAndStyle cloneShapeAndShapeStyle () {

		AffineTransform at = cloneAt;

		Shape cloneShape = ( shape == null ) ? null : at.createTransformedShape( shape );

		ShapeStyle cloneStyle = shapeStyle == null ? null : shapeStyle.create();

		return new ShapeAndStyle( cloneShape, cloneStyle );

	}

	public abstract ChartElement cloneSelf(ChartElement parent, SpreadSheet sheet);

	protected void cloneChilds(ChartElement clone, SpreadSheet sheet) {

		ChartElement childs [] = getChilds();

		for(int i = 0, len = childs.length; i < len; i ++ ) {

			clone.addChild( childs[i].cloneSelf( clone, sheet ) );

		}

	}

	public ChartElement clone(ChartElement parent, SpreadSheet sheet) {

		ChartElement clone = cloneSelf( parent, sheet );

		cloneChilds( clone, sheet );

		return clone;

	}

	public void changeFillEffect(FillEffect fillEffect) {

		ShapeStyle style = this.shapeStyle;

		if ( style == null || fillEffect == null ) {

			return;

		}

		fillEffect.setBounds(this.getArea());

		style.setFillEffect( fillEffect );

	}

	public void changeFillEffectEvenChilds(FillEffect fillEffect) {

		if ( fillEffect == null ) {

			return;

		}

		changeFillEffect( fillEffect );

		ChartElement childs [] = getChilds();

		for(int i = 0, len = childs.length; i < len; i ++ ) {

			childs[i].changeFillEffect( fillEffect );

		}

	}

	public void changeFillColor( Color fillColor ) {

	    ShapeStyle style = this.getShapeStyle();

		if( style.getFillColor() == fillColor ) {

		    return;

	    }

		style.setFillColor( fillColor );

	}

	public void changeLineColor( Color lineColor ) {

		ShapeStyle style = this.getShapeStyle();

		if( style.getLineColor() == lineColor) {

			return;
		}

		style.setLineColor( lineColor );

	}

//	public void chang( float dashStyle) {
//
//	    ShapeStyle style = this.getShapeStyle();
//
//		if( style.getLineStroke() == lineColor) {
//
//			return;
//		}
//
//		style.setLineColor( lineColor );

	public void changeShapeStyle(ShapeStyle style) {

	    setShapeStyle(style);

	}

	public void changeShapeStyleEvenChilds(ShapeStyle style) {

		if ( style == null ) {

			return;

		}

		changeShapeStyle(style);

		ChartElement childs [] = getChilds();

		for(int i = 0, len = childs.length; i < len; i ++ ) {

			childs[i].changeShapeStyle( style );

			System.out.println("style = " + style);

		}

	}

	public boolean isFixedDimensionRatioChartElement() {

	    if( ! ( this instanceof GraphExtent ) ) {

	      return false;

	    }

	    ChartType ct = getChartType();

	    return ct instanceof FixedDimensionRatioChartType && ! ( ct instanceof ChartType3D ) ;

	}

}
