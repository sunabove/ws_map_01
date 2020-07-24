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
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import jcosmos.*;

public class PictureExtent extends ChartElement implements UnSelectable {

	// 삼차원 관련 변수들

	// 와이어 프레임 쉐잎 스타일
	public static ShapeStyle WIRE_FRAME_SHAPE_STYLE = ShapeStyle.getDefaultShapeStyle();

	public static final double DEF_PHI = Math.PI/6; // 15 도 아래로 기울어 짐.
	public static final double DEF_THETA = Unit.convertDegreeToRadian( 10 ); // 20 도

	// theta 와 phi 앵글의 정의는 엑셀 차트의 삼차원 보기의 각도 정의와 같다.

	private int depth3DRatio = 50; // 삼차원 차트 깊이
	private double theta = DEF_THETA;
	private double phi = DEF_PHI;

	private ShapeStyle bottomPlaneShapeStyle3D = new ShapeStyle( Color.yellow, Color.red );

	private boolean selectedBottomPlane3D;

	// 끝. 삼차원 관련 변수들

	private boolean dropLineShow = true;

	// data members for chart type circle vs circle

	public static final int SPLIT_BY_LOCATION = 0, SPLIT_BY_VALUE = 1,
				SPLIT_BY_PERCENTAGE = 2, SPLIT_BY_USER_DEFINED = 3;

	public int seriesSplitTypeOfChartTypeCircle = SPLIT_BY_PERCENTAGE ;
	public int secondDomainSplitValueOfChartTypeCircle = 50;
	public int secondDomainSizeOfChartTypeCircle = 75;
	public boolean hasSeriesLines = true;
	public boolean useDifferentColorToEachPieOfChartTypeCircle = true;
	public int gapWidthOfChartTypeCircle = 100;

	// end of data members for chart type circle vs circle

	// data members overlap and gap width

	public int overlapRatio = 50;
	public int gapWidthRatio = 150;

	// end of member data overlap and gap width

	// donut char type memeber data

	public int donutInnerRadiusRatio = 50;

	// end of donut chart type memeber data

	// member data for bubble char type

	public int bubbleSizeRatio = 100;
	public boolean showNegativeBubble = false;
	public boolean areaBubbleSize = true;

	// end of member data for bubble char type

	private double fixedDimensionRatio;

	public PictureExtent(SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style) {

		super(sheet, parent, shape, style );

		ChartType ct = getChartType();

		this.depth3DRatio = ct.get3DDepthRatio( this );

		this.theta = ct.get3DThetaRadian();

		ct.setPictureExtent( this );

		if( isFixedDimensionRatioChartElement() ) {

		  Rectangle2D bounds = this.shape.getBounds2D();

		  this.fixedDimensionRatio = bounds.getWidth()/bounds.getHeight();

		}

	}

	public double getFixedDimensionRatio() {

	    return this.fixedDimensionRatio;

	}

	public void setFixedDimensionRatio(double fdr) {
	    this.fixedDimensionRatio = fdr;
	}

	public boolean isSelectedBottomPlane3D() {

	  return selectedBottomPlane3D;

	}

	public ShapeStyle getBottomPlaneShapeStyle3D() {

	  return bottomPlaneShapeStyle3D;

	}

	public ChartElement cloneSelf(ChartElement parent, SpreadSheet sheet) {

		ShapeAndStyle sns = cloneShapeAndShapeStyle();

		PictureExtent element = new PictureExtent( sheet, parent, sns.getShape(), sns.getStyle() );

		return element;

	}

	/**
	 * PictureExtent안에 있는 ChartElement 중 같은 차트의 종류에 해당하는 DataSeriesView
	 * 의 개수를 리턴한다.
	 * returns the data series view number of the same chart type class
	 */
	public int getDataSeriesViewNumberOfChartType(Class chartKlass) {

		ChartElement [] childs = getChilds();

		int num = 0;

		for(int i = 0, len = childs.length; i < len; i ++ ) {
			if( childs[i] instanceof DataSeriesView ) {
				DataSeriesView dsv = (DataSeriesView) childs[i];

				if( dsv.getChartType().getClass() == chartKlass ) {
					num += 1;
				}
			}
		}

		return num;
	}

	/**
	 * returns the occurrence order index of dataSeriesView among dataSeriesViews of the same chart Type
	 */
	public int getOccurenceOrderIndexAmongSameCharTypeDataSeriesViews(DataSeriesView dataSeriesView) {
		ChartElement [] childs = getChilds();

		int ocurrOrderIndex = -1 ; // will be increased by one at least

		Class refClass = dataSeriesView.getChartType().getClass();

		for(int i = 0, len = childs.length; i < len; i ++ ) {
			if( childs[i] instanceof DataSeriesView ) {
				DataSeriesView dsv = (DataSeriesView) childs[i];

				if( dsv.getChartType().getClass() == refClass ) {
					ocurrOrderIndex ++;
					if( dsv == dataSeriesView ) {
						return ocurrOrderIndex;
					}
				}
			}
		}

		return ocurrOrderIndex;
	}

	public void applyAffineTransform(AffineTransform at) {

		Utility.debug(this, "APPLY AFFINE TRASNFORM ....." );

		ChartElement sce = SpreadSheet.getCurrentSpreadSheet().getSelectedChartElement();

		if( sce instanceof Chart && isFixedDimensionRatioChartElement() ) {

		    this.applyAffineTransformToFixedDimensionRatioChartType( at );

		}  else {

		    super.applyAffineTransform( at );

		}

		applyAffineTransformToPictureExtentExtras( at );

	}

	private void applyAffineTransformToFixedDimensionRatioChartType(AffineTransform at) {

	  super.applyAffineTransform( at );

	}

   private void applyAffineTransformToPictureExtentExtras(AffineTransform at) {

		SpreadSheet sheet = SpreadSheet.getCurrentSpreadSheet();

		ChartElement element = sheet.getSelectedChartElement();

		if( element instanceof Chart ) {
			return;
		}

		Chart chart = getChart();

		XAxisTitle xAxisTitle = (XAxisTitle) chart.getChartElement( XAxisTitle.class );

		if( xAxisTitle != null ) {
			xAxisTitle.applyAffineTransform( at );
		}

		YAxisTitle yAxisTitle = (YAxisTitle) chart.getChartElement( YAxisTitle.class );

		if( yAxisTitle != null ) {
			yAxisTitle.applyAffineTransform( at );
		}

		DataTable dataTable = (DataTable) chart.getChartElement( DataTable.class );

		if( dataTable != null ) {
			dataTable.applyAffineTransform( at );
		}

	}

	public String toString() {

		return "그림 영역";

	}

	public Rectangle [] getSelectedMarks() {

		ChartType ct = getChartType();

		if( ct instanceof ChartTypeCircle ) {

			Rectangle [] selectedMarks = super.getSelectedMarks();

			selectedMarks[ 1 ] = null;
			selectedMarks[ 3 ] = null;
			selectedMarks[ 4 ] = null;
			selectedMarks[ 6 ] = null;

			return selectedMarks;

		} else if( ct instanceof ChartType3D ) {

			return getSelectedMarks3D();

		} else {

			return super.getSelectedMarks();

		}

	}

	private Rectangle [] getSelectedMarks3D() {

	  boolean selectedBottomPlane3D = this.selectedBottomPlane3D;

	  Shape [][] shapes3DGroup = super.shapes3DGroup;

	  if( shapes3DGroup == null ) {

	    return null ;

	  }

	  Shape [][] shapes3DForSelectedMarks;

	  if( selectedBottomPlane3D ) {

	    shapes3DForSelectedMarks = new Shape [ 1 ] [];

	    shapes3DForSelectedMarks[ 0 ] = shapes3DGroup[ 5 ];

	  } else {

	    shapes3DForSelectedMarks = new Shape [ 5 ] [];

	    System.arraycopy( shapes3DGroup, 0, shapes3DForSelectedMarks, 0, 5 );

	  }

	  return getSelectedMarks3D( shapes3DForSelectedMarks );

	}

	private Rectangle [] getSelectedMarks3D(Shape [][] shapes3D ) {

	  return getSelectedMarksLineType3D( shapes3D );

	}

	protected Shape [][] createShapes3DGroup(float tx, float ty, Shape shape, float proX, float proY) {

		return ThreeDimUtilities.get3DBarInsideShapesGroup( shape.getBounds2D(), proX, proY );

	}

	public void paint(Graphics2D g2) {

		ChartType ct = getChartType();

		if( ct instanceof ChartType3D ) {

			Utility.debug(this, "DRAWING 3D SHAPE ....." );

			Point2D projectedPoint = getProjectionVector( depth3DRatio, this, theta, phi );

			float proX = (float) projectedPoint.getX();
			float proY = (float) projectedPoint.getY();

			if( ct.isChartTypeCircle3D() ) {

			    ct.paint3DPictureExtentOfCircle3DChartType(this, g2, proX, proY );

			} else if( ct instanceof Proportional3DChartType ) {

			   if( ct instanceof HorizontalChartType ) {

			      ct.paint3DPictureExtentOfHorizontalProportional3DChartType( this, g2, proX, proY );

			   } else {

			      ct.paint3DPictureExtentOfVerticalProportional3DChartType( this, g2, proX, proY );

			   }

			} else if( ct instanceof ZAxisChartType3D ) {

			    ct.paint3DPictureExtentOfZAxis3DChartType( this, g2, proX, proY );

			} else {

			    ct.paint3DPictureExtentOfNormal3DChartType( this, g2, proX, proY );

			}


		} else if( ct instanceof SurfaceChartType ) {

		    SurfaceChartType sct = (SurfaceChartType) ct;

		    sct.paint( g2, this );

		} else {

			super.paint( g2 );

		}

	}

	private void paint3DNormalWireFrame(Graphics2D g2, double proX, double proY ) {

		Utility.debug( this, "PROX = " + proX + ", PROY = " + proY );

		Chart chart = getChart();

		Shape chartShape = chart.getShape();

		WIRE_FRAME_SHAPE_STYLE.paint( g2, chartShape );

		Shape [] wireFrameLines = get3DWireFrameLines( ( float) proX, ( float) proY );

		WIRE_FRAME_SHAPE_STYLE.paintShapeArray( g2, wireFrameLines );

	}

	private void paint3DCircleWireFrame(Graphics2D g2, double proX, double proY ) {

		Chart chart = getChart();

		Shape chartShape = chart.getShape();

		ShapeStyle wireFrameShapeStyle = this.WIRE_FRAME_SHAPE_STYLE;

		wireFrameShapeStyle.paint( g2, chartShape );

		wireFrameShapeStyle.paint( g2, shape );

		Rectangle area = getArea(); // pictureExtent Area

		proY = Math.abs( proY );

		Shape ellipse = new Ellipse2D.Double( area.x, area.y, area.width, area.height - proY );

		wireFrameShapeStyle.paint( g2, ellipse );

		double cx = area.x + area.width/2.0;
		double cy = area.y + (area.height - proY )/2.0;

		double [] radians = get3DRadians( depth3DRatio, this, proX, proY );

		double rotAngle = Unit.convertRadianToDegree( radians[ 0 ] );

		double startAngle = 90 - rotAngle;

		ellipse = new Arc2D.Double( area.x, area.y, area.width, area.height - proY, startAngle, rotAngle, Arc2D.PIE );

		wireFrameShapeStyle.paint( g2, ellipse );

	}

	public boolean contains(double x, double y) {

		ChartType ct = getChartType();

		if( ct instanceof ChartType3D ) {

		      return get3DArea().contains( x, y );

		} else {

		      return super.contains( x, y );

		}

	}

	public Shape get3DArea() {

		Point2D projectedPoint = getProjectionVector( depth3DRatio, this, theta, phi );

		float proX = (float) projectedPoint.getX();
		float proY = (float) projectedPoint.getY();


		Shape [][] shapesGroup3D = ThreeDimUtilities.get3DBarShapesGroup( 0, 0, shape.getBounds2D(), proX, proY );

		// shapesGroup3D 는 Wire Frame 을 나타낸다.

		Area aread3D = new Area( getShape() );

		for(int i = 0, iLen = shapesGroup3D.length; i < iLen; i ++ ) {

		    Shape [] shapes = shapesGroup3D[ i ];

		    if( shapes == null ) {

			continue;

		    }

		    for(int k = 0, kLen = shapes.length; k < kLen; k ++ ) {

			Shape shape = shapes[ k ];

			if( shape == null ) {

			    continue;

			}

			aread3D.add( new Area( shape) );

		    }

		}

		return aread3D.getBounds2D();

	}

	public Shape [] get3DWireFrameLines( float proX, float proY ) {

		Rectangle2D bounds = shape.getBounds2D();

		double w = bounds.getWidth();
		double h = bounds.getHeight();

		Point2D projectionVector = getProjectionVector();

		double x = bounds.getX() + projectionVector.getX()/2.0 - proX/2.0;
		double y = bounds.getY() + projectionVector.getY()/2.0 - proY/2.0;

		Shape lines [] = new Shape[ 14 ];

		lines[  0 ] = new Line2D.Double( x, y, x + proX, y + proY );
		lines[  1 ] = new Line2D.Double( x + proX, y + proY, x + proX + w , y + proY );
		lines[  2 ] = new Line2D.Double( x + proX + w, y + proY, x + w, y );
		lines[  3 ] = new Line2D.Double( x + w, y, x, y );

		lines[  4 ] = new Line2D.Double( x, y, x, y + h );
		lines[  5 ] = new Line2D.Double( x + proX, y + proY, x + proX, y + proY + h );
		lines[  6 ] = new Line2D.Double( x + proX + w, y + proY, x + proX + w, y + proY + h );
		lines[  7 ] = new Line2D.Double( x + w, y, x + w, y + h );

		lines[  8 ] = new Line2D.Double( x, y + h, x + proX, y + h + proY );
		lines[  9 ] = new Line2D.Double( x + proX, y + proY + h, x + proX + w, y + proY + h );
		lines[ 10 ] = new Line2D.Double( x + proX + w, y + proY + h, x + w, y + h );
		lines[ 11 ] = new Line2D.Double( x + w, y + h, x, y + h );

		lines[ 12 ] = new Line2D.Double( x + proX/2.0, y + h + proY/2.0, x + proX/2.0 + w, y + h + proY/2.0 );
		lines[ 13 ] = new Line2D.Double( x + w/2.0, y + h, x + w/2.0 + proX, y + h + proY );

		return lines;

	}

	public void paintAndSet3DWireFrameMovedBy(Graphics2D g2, int x, int y, int dir) {

		double [] radians = paint3DWireFrameMovedBy( g2, x, y, dir );

		this.theta = radians[ 0 ];

		this.phi = radians[ 1 ];

	}

	public double [] paint3DWireFrameMovedBy(Graphics2D g2, int x, int y, int dir) {

	     return paint3DNormalWireFrameMovedBy( g2, x, y, dir );

	}

	public double [] paint3DNormalWireFrameMovedBy(Graphics2D g2, int x, int y, int dir ) {

		Point2D projectionVector = getProjectionVector();

		double proX = projectionVector.getX();
		double proY = projectionVector.getY();

		proX +=  dir*x;
		proY +=  dir*y;

		double [] radians = get3DRadians( depth3DRatio, this, proX, proY );

		// zRates validity check

		ChartType ct = getChartType();

		ct.check3DRadians( radians );

		// end of checking zRates validity

		projectionVector = getProjectionVector( depth3DRatio, this, radians[0], radians[1] );

		// paint 3d wire frame

		paint3DNormalWireFrame( g2, projectionVector.getX(), projectionVector.getY() );

		// end of painting 3d wire frame

		return radians;

	}

	public int[] getIndexOfSelectedDataElementView( DataElementView dataElementView ) {

		if (dataElementView == null) {

			return  null;
		}

		DataSeriesView dsv = (DataSeriesView) dataElementView.getParent();

		ChartElement [] childsOfPE = getChilds();       // childs of Picture extent

		int dsvIndex = 0;

		int firstIdx = -1, secondIdx = -1;

		for(int i = 0, len = childsOfPE.length; i < len; i ++ ) {

			if( childsOfPE[i] instanceof DataSeriesView ) {

				if( childsOfPE[i] == dsv ) {

				firstIdx = dsvIndex;

				break;

				}

				dsvIndex++;

			}

		}

		ChartElement [] childsOfDSV = dsv.getChilds();

		for(int i = 0, len = childsOfDSV.length; i < len; i ++ ) {

			if( childsOfDSV[i] == dataElementView) {

				secondIdx = i;

				break;

			    }


		}

		return new int [] { firstIdx, secondIdx };

	}


	public DataElementView getSelectedDataElementView(int [] indexs) {

		ChartElement [] childsOfPE = getChilds();       // childs of Picture extent

		DataSeriesView dsv = null;

		int dsvIndex =  0;

		int firstIdx = indexs[ 0 ], secondIdx = indexs[ 1 ];

		for(int i = 0, len = childsOfPE.length; i < len; i ++ ) {

			if( childsOfPE[i] instanceof DataSeriesView ) {

				if ( firstIdx == dsvIndex ) {

				    dsv = (DataSeriesView) childsOfPE[i];

					break;
				}

				dsvIndex++;

			}
		}

		return (DataElementView) dsv.childs.get( secondIdx );

	}

	// 데이터시리즈 뷰만 리턴한다.

	public DataSeriesView [] getDataSeriesViews() {

	  Vector dsvList = new Vector();

	  ChartElement childs [] = getChilds();

	  for( int i = 0, iLen = childs.length; i < iLen; i ++ ) {

	    ChartElement ce = childs[ i ];

	    if( ce instanceof DataSeriesView ) {

	      dsvList.add( ce );

	    }

	  }

	  int len = dsvList.size();

	  DataSeriesView [] dsvs = new DataSeriesView[ len ];

	  Enumeration enumIt = dsvList.elements();

	  for( int i = 0; i < len; i ++ ) {

	    dsvs[ i ] = (DataSeriesView) enumIt.nextElement();

	  }

	  return dsvs;

	}

	public void paint3DSelfOnly(float tx, float ty, Graphics2D g2, float proX, float proY ) {

		if( isPaintable() ) {

			Shape [][] shapess = getShapes3DGroup( tx, ty, shape, proX, proY );

			shapeStyle.paint3DShapess( g2, shapess, bottomPlaneShapeStyle3D );

			if( selected ) {

				paintSelected( g2 );

			}

		}

	}

	private boolean isSelectedBottomPlane3D(double x, double y) {

	   Shape [][] shape3DGroup = super.shapes3DGroup;

	   if( shape3DGroup == null ) {

	       return false;

	   }

	   Shape [] bottomShapes3D = shape3DGroup[ 5 ];

	   if( bottomShapes3D == null ) {

	       return false;

	   }

	   for( int i = 0, len = bottomShapes3D.length; i < len; i ++ ) {

	       Shape shape = bottomShapes3D[ i ];

	       if( shape.contains( x, y ) ) {

		   return true;

	       }

	   }

	   return false;

	}

	public boolean processMouseEvent(MouseEvent e, boolean skipChilds) {

		if( e.getID() == e.MOUSE_PRESSED || e.getID() == e.MOUSE_RELEASED ) {

		   ChartType ct = getChartType();

		   if( ct instanceof ChartType3D ) {

			selectedBottomPlane3D = isSelectedBottomPlane3D( e.getX(), e.getY() );

		   }

		}

		return super.processMouseEvent( e, skipChilds );

	}

	public boolean isDropLineShow() {

	  return dropLineShow;

	}

	public Point2D getProjectionVector() {

	  return getProjectionVector( depth3DRatio, this, theta, phi );

	}

	public static Point2D getProjectionVector( int depth3DRatio, PictureExtent pictureExtent, double theta, double phi ) {

	   return getProjectionVector( getZ3D( depth3DRatio, pictureExtent ), theta, phi );

	}

	public static Point2D getProjectionVector( double z, double theta, double phi ) {

	   return new Point2D.Double( z*Math.sin( theta ), - z*Math.sin( phi ) );

	}

	public static double getZ3D( int depth3DRatio, PictureExtent pictureExtent ) {

	   Rectangle area = pictureExtent.getArea();

	   Chart chart = pictureExtent.getChart();

	   ChartData cd = chart.getChartData();

	   DataSeries [] dss = cd.getDataSeries();

	   int dsNum = dss.length;

	   dsNum = ( dsNum < 1 ) ? 1 : dsNum;

	   ChartType ct = chart.getChartType();

	   double w = ( ct instanceof HorizontalChartType ) ? area.height : area.width;

	   return (w/( dsNum + 0.0 ))*( depth3DRatio/100.0 );

	}

	public static double [] get3DRadians(int depth3DRatio, PictureExtent pictureExtent, double px, double py) {

	      double z = getZ3D( depth3DRatio, pictureExtent );

	      double xRatio = px/z;
	      double yRatio = py/z;

	      xRatio = xRatio > 1  ?    1 : xRatio;
	      xRatio = xRatio < -1 ?  - 1 : xRatio;

	      yRatio = yRatio > 1  ?    1 : yRatio;
	      yRatio = yRatio < -1 ?   -1 : yRatio;

	      double theta = Math.abs( Math.asin( xRatio ) );

	      double phi   = Math.abs( Math.asin( yRatio ) );

	      theta = px > 0 ? theta : - theta;

	      phi = py < 0 ? phi : - phi;

	      return new double [] { theta, phi };

	}

	public double getThetaRadian() {

		return this.theta;

	}

	public double getPhiRadian() {

		return this.phi;

	}

	public void setThetaAndPhiRadian(double theta, double phi) {

	       this.theta = theta;
	       this.phi = phi;

	       reSetShape3DData();

	}

	public void reSetShape3DData() {

	      super.applyAffineTransform( AffineTransform.getTranslateInstance( 0, 0) );

	}

	public int getDepth3DRatio () {

	      return depth3DRatio;

	}

}
