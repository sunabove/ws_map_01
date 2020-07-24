
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
import java.awt.geom.*;
import java.util.*;
import jcosmos.*;
import com.jcosmos.java3d.*;

public abstract class ChartType {

	private static int [][] chartColors = {

		{152, 156, 255}, {152,  52, 104}, {255, 255, 200}, {200, 255, 255}, { 95,   0,  95},
		{255, 128, 128}, {  0, 100, 200}, {208, 204, 255}, {  0,   0, 128}, {255,   0, 255},
		{255, 255,   0}, {  0, 255, 255}, {128,   0, 128}, {128,   0,   0}, {  0, 128, 128},
		{  0,   0, 255}, {  0, 204, 255}, {200, 255, 255}, {200, 255, 200}, {255, 255, 152}

	};

	private static Hashtable colorTable = new Hashtable();  // color hash table

	private static Hashtable chartTypeTable = new Hashtable(); // chart type hashtable

	public static final double logTen = Math.log( 10 );

	protected int minPixelBetweenGridLines = 50; // minimum gap between grid lines

	public static Color getChartColor(int index) {

		String id = "" + index;

		Color color = (Color) colorTable.get( id );

		if( color == null ) {

			int [] rgb = chartColors[ index ];

			color = new Color( rgb[0], rgb[1], rgb[2] );

			colorTable.put( id, color );
		}

		return color;

	}



	public static ChartType getChartType(String chartGroupName, int chartSubIndex) {

	    String key = chartGroupName + chartSubIndex; // chart type key

	    Utility.debug(ChartType.class, key );

	    ChartType ct = (ChartType) chartTypeTable.get( key );  // chart type

	    if( ct == null ) {

		if( chartGroupName.equalsIgnoreCase( "verticalbar" ) ) {

		    if( chartSubIndex == 1 ) {

			ct = new ChartTypeVerticalBarAccumulated();

		    } else if( chartSubIndex == 2 ) {

			ct = new ChartTypeVerticalBarPercentedAccumulated();

		    } else if( chartSubIndex == 3 ) {

			ct = new ChartTypeVerticalBarBinded3D();

		    } else if( chartSubIndex == 4 ) {

			ct = new ChartTypeVerticalBarAccumulated3D();

		    } else if( chartSubIndex == 5 ) {

			ct = new ChartTypeVerticalBarPercentedAccumulated3D();

		    } else if( chartSubIndex == 6 ) {

			ct = new ChartTypeVerticalBar3D();

		    } else {  // chartSubIndex == 0

			ct = new ChartTypeVerticalBarBinded();

		    }

		} else if( chartGroupName.equalsIgnoreCase( "horizontalbar" ) ) {

		    if( chartSubIndex == 1 ) {

			ct = new ChartTypeHorizontalBarAccumulated();

		    } else if( chartSubIndex == 2 ) {

			ct = new ChartTypeHorizontalBarPercentedAccumulated();

		    } else if( chartSubIndex == 3 ) {

			ct = new ChartTypeHorizontalBarBinded3D();

		    } else if( chartSubIndex == 4 ) {

			ct = new ChartTypeHorizontalBarAccumulated3D();

		    } else if( chartSubIndex == 5 ) {

			ct = new ChartTypeHorizontalBarPercentedAccumulated3D();

		    } else {   // chartSubIndex == 0

			ct = new ChartTypeHorizontalBarBinded();

		    }

		} else if( chartGroupName.equalsIgnoreCase( "bendline" ) ) {

		    if( chartSubIndex == 1 ) {

		       ct = new ChartTypeBendLineAccumulated();

		    } else if( chartSubIndex == 2 ) {

		       ct = new ChartTypeBendLinePercentedAccumulated();

		    } else if( chartSubIndex == 3 ) {

		       ct = new ChartTypeBendLineSymbolic();

		    } else if( chartSubIndex == 4 ) {

		       ct = new ChartTypeBendLineAccumulatedSymbolic();

		    } else if( chartSubIndex == 5 ) {

		       ct = new ChartTypeBendLinePercentedAccumulatedSymbolic();

		    } else if( chartSubIndex == 6 ) {

		       ct = new ChartTypeBendLine3D();

		    } else {   // chartSubIndex == 0

		       ct = new ChartTypeBendLine();

		    }

		 } else if( chartGroupName.equalsIgnoreCase( "circle" ) ) {

		    if( chartSubIndex == 1 ) {

			ct = new ChartTypeCircle3D();

		    } else if( chartSubIndex == 2 ) {

			ct = new ChartTypeCircleVsCircle();

		    } else if( chartSubIndex == 3 ) {

			ct = new ChartTypeCircleDivided();

		    } else if( chartSubIndex == 4 ) {

			ct = new ChartTypeCircleDivided3D();

		    } else if( chartSubIndex == 5 ) {

			ct = new ChartTypeCircleVsBar();

		    } else {

			ct = new ChartTypeCircle();

		    }

		} else if( chartGroupName.equalsIgnoreCase( "donut" ) ) {

		    if( chartSubIndex == 1 ) {

		      ct = new ChartTypeDonutDivided();

		    } else {

		       ct = new ChartTypeDonut();

		    }

		} else if( chartGroupName.equalsIgnoreCase( "distribution" ) ) {

		    if( chartSubIndex == 1 ) {

		       ct = new ChartTypeDistributionSplinedSymbolic();

		    } else if( chartSubIndex == 2 ) {

		       ct = new ChartTypeDistributionSplined();

		    } else if( chartSubIndex == 3 ) {

		       ct = new ChartTypeDistributionBendedLineSymbolic();

		    } else if( chartSubIndex == 4 ) {

		      ct = new ChartTypeDistributionBendedLine();

		    } else {   // chartSubIndex == 0

		       ct = new ChartTypeDistribution();

		    }

		 } else if( chartGroupName.equalsIgnoreCase( "area" ) ) {

		    if( chartSubIndex == 1 ) {

		      ct = new ChartTypeAreaAccumulated();

		    } else if( chartSubIndex == 2 ) {

		       ct = new ChartTypeAreaPercentedAccumulated();

		    } else if( chartSubIndex == 3 ) {

		       ct = new ChartTypeArea3D();

		    } else if( chartSubIndex == 4 ) {

		       ct = new ChartTypeAreaAccumulated3D();

		    } else if( chartSubIndex == 5 ) {

		       ct = new ChartTypeAreaPercentedAccumulated3D();

		    } else {   // chartSubIndex == 0

		       ct = new ChartTypeArea();

		    }

		 } else if( chartGroupName.equalsIgnoreCase( "radiation" ) ) {

		    if( chartSubIndex == 1 ) {

		       ct = new ChartTypeRadiationSymbolic();

		    } else if( chartSubIndex == 2 ) {

		       ct = new ChartTypeRadiationFilled();

		    } else {   // chartSubIndex == 0

		       ct = new ChartTypeRadiation();

		    }

		 } else if ( chartGroupName.equalsIgnoreCase( "surface" ) ) {

		    ct = new BirdViewSurfaceChartType();

		 } else if( chartGroupName.equalsIgnoreCase( "bubble" ) ) {

		    if( chartSubIndex == 1 ) {

		      ct = new ChartTypeBubble3DEffect();

		    } else {

		      ct = new ChartTypeBubble();

		    }

		 } else if( chartGroupName.equalsIgnoreCase( "stock" ) ) {

		    if( chartSubIndex == 1 ) {

		       ct = new ChartTypeStockSHLE(); // start, high, low and end price stock type

		    } else if( chartSubIndex == 2 ) {

		       ct = new ChartTypeStockSHLEanother(); // start, high, low and end price stock type

		    } else if( chartSubIndex == 3 ) {

		       ct = new ChartTypeStockDSHLE();  // deal mount, start, high, low and end price stock type

		    } else {

		       ct = new ChartTypeStockHLE(); // high, low and end price stock type

		    }

		 } else if( chartGroupName.equalsIgnoreCase( "cylinder" ) ) {

		    if( chartSubIndex == 1 ) {

		       ct = new ChartTypeVerticalCylinderAccumulated3D();

		    } else if ( chartSubIndex == 2 ) {

		       ct = new ChartTypeVerticalCylinderPercentedAccumulated3D();

		    } else if( chartSubIndex == 3 ) {

		       ct = new ChartTypeHorizontalCylinderBinded3D();

		    } else if( chartSubIndex == 4 ) {

		       ct = new ChartTypeHorizontalCylinderAccumulated3D();

		    } else if( chartSubIndex == 5 ) {

		       ct = new ChartTypeHorizontalCylinderPercentedAccumulated3D();

		    } else if( chartSubIndex == 6 ) {

		       ct = new ChartTypeVerticalCylinder3D();

		    } else { // else if chartSubIndex == 0

		       ct = new ChartTypeVerticalCylinderBinded3D();
		    }

		 } else if( chartGroupName.equalsIgnoreCase( "cone" ) ) {

		    if( chartSubIndex == 1 ) {

		       ct = new ChartTypeVerticalConeAccumulated3D();

		    } else if( chartSubIndex == 2 ) {

		       ct = new ChartTypeVerticalConePercentedAccumulated3D();

		    } else if( chartSubIndex == 3 ) {

		       ct = new ChartTypeHorizontalSimpleConeBinded3D();

		    } else if( chartSubIndex == 4 ) {

		       ct = new ChartTypeHorizontalConeAccumulated3D();

		    } else if( chartSubIndex == 5 ) {

		       ct = new ChartTypeHorizontalConePercentedAccumulated3D();

		    } else if( chartSubIndex == 6 ) {

		       ct = new ChartTypeVerticalSimpleCone3D();

		    } else {   // chartSubIndex == 0

		       ct = new ChartTypeVerticalSimpleConeBinded3D();

		    }

		 } else if( chartGroupName.equalsIgnoreCase( "pyramid" ) ) {

		    if( chartSubIndex == 1 ) {

		       ct = new ChartTypeVerticalPyramidAccumulated3D();

		    } else if( chartSubIndex == 2 ) {

		       ct = new ChartTypeVerticalPyramidPercentedAccumulated3D();

		    } else if( chartSubIndex == 3 ) {

		       ct = new ChartTypeHorizontalSimplePyramidBinded3D();

		    } else if( chartSubIndex == 4 ) {

		       ct = new ChartTypeHorizontalPyramidAccumulated3D();

		    } else if( chartSubIndex == 5 ) {

		       ct = new ChartTypeHorizontalPyramidPercentedAccumulated3D();

		    } else if( chartSubIndex == 6 ) {

		       ct = new ChartTypeVerticalSimplePyramid3D();

		    } else {   // chartSubIndex == 0

		       ct = new ChartTypeVerticalSimplePyramidBinded3D();

		    }

		 } else {

		    ct = new ChartTypeVerticalBarBinded();

		  }

		   chartTypeTable.put( key, ct );

	    }

	     return ct;

	    }


 /**
   * 3d frame setting value check
   */
   // 9/25�Ͽ� ChartType3D Ŭ�������� �������� �Լ��� �̵���.
  public void check3DRadians(double [] radians ) {

      if( this.isChartTypeCircle3D() ) {

	 checkCircleChartType3DRadians( radians );

      } else if( this instanceof HorizontalChartType ) {

	 checkHorizontalChartType3DRadians( radians );

      } else {

	 checkVerticalChartType3DRadians( radians );

      }

  }

  public int get3DDepthRatio(PictureExtent pictureExtent) {

      return 100;

  }

  public double get3DThetaRadian() {

      return PictureExtent.DEF_THETA;

  }

  public void checkCircleChartType3DRadians(double [] radians ) {

	double tenRadian = Unit.convertDegreeToRadian( 10 );

	radians[ 1 ] = radians[ 1 ] <  tenRadian ? tenRadian : radians[ 1 ] ;
	radians[ 1 ] = radians[ 1 ] >  (Math.PI/2.0 - tenRadian) ? (Math.PI/2.0 - tenRadian) : radians[ 1 ] ;

  }

  public void checkVerticalChartType3DRadians(double [] radians) {

     for(int i = 0, len = radians.length; i < len; i ++ ) {

	if( radians[i] > Math.PI/3 ) {

	   radians[i] = Math.PI/3;

	} else if( radians[i] < - Math.PI/3 ) {

	   radians[i] = - Math.PI/3;

	}

     }

  }

  public void checkHorizontalChartType3DRadians(double [] radians ) {

    radians[ 0 ] = radians[ 0 ] > Math.PI/3 ? Math.PI/3 : radians[ 0 ] ;
    radians[ 0 ] = radians[ 0 ] < 0 ? 0 : radians[ 0 ] ;

    radians[ 1 ] = radians[ 1 ] > Math.PI/3 ? Math.PI/3 : radians[ 1 ] ;
    radians[ 1 ] = radians[ 1 ] < 0 ? 0 : radians[ 1 ] ;

  }

  /**
   * ������ ���� �ͽ���Ʈ�� �׸��� �Լ�
   */
   // 9/25�Ͽ� ChartType3D Ŭ�������� �������� �Լ��� �̵���.

  public void paint3DPictureExtentOfNormal3DChartType(PictureExtent pictureExtent, Graphics2D g2, float proX, float proY ) {

      pictureExtent.paint3DSelfOnly(0, 0, g2, proX, proY );

      // put three dim obect pool

      ThreeDimObjectPool threeDimObjectPool = putThreeDimObjectPool( pictureExtent, g2, proX, proY );

      // sorts three dimensional object pool

      Object [] sortedChartElements = threeDimObjectPool.sort( );

      // end of sorting three dimensional object pool

      float tx = proX/4.0F;
      float ty = proY/4.0F;

      proX = proX/2.0F;
      proY = proY/2.0F;

      for(int i = 0, len = sortedChartElements.length; i < len; i ++ ) {

	  ChartElement ce = (ChartElement) sortedChartElements[ i ];

	  ce.paint3DSelfOnly( tx, ty, g2, proX, proY );

      }

  }

  public void paint3DPictureExtentOfZAxis3DChartType(PictureExtent pictureExtent, Graphics2D g2, float proX, float proY ) {

      pictureExtent.paint3DSelfOnly(0, 0, g2, proX, proY );

      ChartElement [] childs = pictureExtent.getChilds();

      Vector dsvVec = new Vector();

      for(int i = 0, len = childs.length; i < len; i ++ ) {

	 ChartElement child = childs[ i ];

	 if( child instanceof DataSeriesView ) {

	    DataSeriesView dsv = (DataSeriesView) child;

	    if( ! dsv.isInitedDataElementViews() ) {

		dsv.setShapeAndStyle();

	    }

	    dsvVec.add( dsv );

	 } else {

	    child.paint3D( 0, 0, g2, proX, proY );

	 }

      }

      int len = dsvVec.size();

      if( len == 0 ) {

	  return;

      }

      float proX2 = proX/len/2.0F;
      float proY2 = proY/len/2.0F;

      for(int i = len -1; i > -1 ; i -- ) {

	  DataSeriesView dsv = (DataSeriesView) ( dsvVec.elementAt( i ) );

	  float tx = proX/len*i + proX2/2.0F;
	  float ty = proY/len*i + proY2/2.0F;

	  dsv.paint3D( tx, ty, g2, proX2, proY2 );

      }

  }

  public double [] get3DObjectCompareKey(ChartElement elem, float proX, float proY) {

    Rectangle2D bounds = elem.getShape().getBounds2D();

    float dirX = proX < 0 ? - 1.0F : 1.0F ;
    float dirY = proY < 0 ? - 1.0F : 1.0F ;

    return new double[] { dirX*bounds.getX(), dirY*bounds.getY() };

  }

  private ThreeDimObjectPool putThreeDimObjectPool(PictureExtent pictureExtent, Graphics2D g2, float proX, float proY ) {

      ChartElement [] childs = pictureExtent.getChilds();

      ThreeDimObjectPool threeDimObjectPool = new ThreeDimObjectPool( this, pictureExtent, proX, proY );

      for(int i = 0, len = childs.length; i < len; i ++ ) {

	 ChartElement child = childs[ i ];

	 if( child instanceof DataSeriesView ) {

	    DataSeriesView dsv = (DataSeriesView) child;

	    if( ! dsv.isInitedDataElementViews() ) {

		dsv.setShapeAndStyle();

	    }

	    child.addElementToThreeDimObjectPool( threeDimObjectPool );

	 } else {

	    child.paint3D( 0, 0, g2, proX, proY );

	 }

      }

      return threeDimObjectPool;

  }

  public void paint3DPictureExtentOfVerticalProportional3DChartType(PictureExtent pictureExtent, Graphics2D g2, float proX, float proY ) {

      pictureExtent.paint3DSelfOnly(0, 0, g2, proX, proY );

      // put three dim obect pool

      ThreeDimObjectPool threeDimObjectPool = putThreeDimObjectPool( pictureExtent, g2, proX, proY );

      // sorts three dimensional object pool

      Object [] sortedChartElements = threeDimObjectPool.sort( );

      // end of sorting three dimensional object pool

      float tx = proX/4.0F;
      float ty = proY/4.0F;

      proX = proX/2.0F;
      proY = proY/2.0F;

      Rectangle pictureExtentArea = pictureExtent.getArea();

      float bottom = (float) ( pictureExtentArea.getY() + pictureExtentArea.getHeight() ) ;

      Hashtable topLocationHashtable = new Hashtable();

      for(int i = 0, len = sortedChartElements.length; i < len; i ++ ) {

	  ChartElement ce = (ChartElement) sortedChartElements[ i ];

	  float top = ( float ) getTopLoctionAmongChartElements( topLocationHashtable, sortedChartElements, ce );

	  ce.paint3DSelfOnly( tx, ty, g2, proX, proY, top, bottom );

      }

  }

  public void paint3DPictureExtentOfHorizontalProportional3DChartType(PictureExtent pictureExtent, Graphics2D g2, float proX, float proY ) {

      pictureExtent.paint3DSelfOnly(0, 0, g2, proX, proY );

      // put three dim obect pool

      ThreeDimObjectPool threeDimObjectPool = putThreeDimObjectPool( pictureExtent, g2, proX, proY );

      // sorts three dimensional object pool

      Object [] sortedChartElements = threeDimObjectPool.sort( );

      // end of sorting three dimensional object pool

      float tx = proX/4.0F;
      float ty = proY/4.0F;

      proX = proX/2.0F;
      proY = proY/2.0F;

      Rectangle pictureExtentArea = pictureExtent.getArea();

      float left = (float) ( pictureExtentArea.getX() ) ;

      Hashtable topLocationHashtable = new Hashtable();

      for(int i = 0, len = sortedChartElements.length; i < len; i ++ ) {

	  ChartElement ce = (ChartElement) sortedChartElements[ i ];

	  float right = ( float ) getRightLoctionAmongChartElements( topLocationHashtable, sortedChartElements, ce );

	  ce.paint3DSelfOnly( tx, ty, g2, proX, proY, right, left );

      }

  }

  public double [] getRadiusAndThicknessOf3DCircle(PictureExtent pictureExtent) {

      double w = pictureExtent.getArea().width;

      double phiRadian = pictureExtent.getPhiRadian();

      double radius = w/2.0*Math.abs( Math.sin( phiRadian ) );
      double thickness = w/5.0*Math.abs( Math.cos( phiRadian ) );

      return new double [] { radius, thickness };

  }

  public void paint3DPictureExtentOfCircle3DChartType(PictureExtent pictureExtent, Graphics2D g2, float proX, float proY ) {

	pictureExtent.paintSelf( g2 );

	// put three dim obect pool

	float rotRadian = ( float) ( pictureExtent.getThetaRadian() );

	double [] radiusAndThickness = getRadiusAndThicknessOf3DCircle( pictureExtent );

	double thickness = radiusAndThickness[ 1 ];

	ThreeDimObjectPool threeDimObjectPool = putThreeDimObjectPool( pictureExtent, g2, rotRadian, (float) thickness );

	// sorts three dimensional object pool

	Object [] sortedChartElements = threeDimObjectPool.sort( );

	// end of sorting three dimensional object pool

	Rectangle pictureExtentArea = pictureExtent.getArea();

	float top = (float) pictureExtentArea.getY();
	float bottom = top + (float) pictureExtentArea.getHeight();

	for(int i = 0, len = sortedChartElements.length; i < len; i ++ ) {

		ChartElement ce = (ChartElement) sortedChartElements[ i ];

		ce.paint3DSelfOnly( 0, 0, g2, rotRadian, (float) thickness, top, bottom );

	}

  }

  private double getTopLoctionAmongChartElements(Hashtable topLocationHashtable,
	  Object sortedElements [], ChartElement element) {

      Shape shape = element.getShape();

      double x = shape.getBounds2D().getX();

      String topText = (String) ( topLocationHashtable.get( "" + x ) );

      if( topText != null ) {

	  return ( new Double( topText ) ).doubleValue();

      }

      double top = Double.MAX_VALUE;

      for(int i = 0, len = sortedElements.length; i < len; i ++ ) {

	  ChartElement ce = (ChartElement) sortedElements[ i ];

	  Rectangle2D bounds = ce.getShape().getBounds2D();

	  if( bounds.getX() == x ) {

	     double y = bounds.getY();

	     if( y < top ) {

		 top = y;

	     }

	  }

      }

      topLocationHashtable.put( "" + x, "" + top );

      return top;

  }

  private double getRightLoctionAmongChartElements(Hashtable topLocationHashtable,
	  Object sortedElements [], ChartElement element) {

      Shape shape = element.getShape();

      double y = shape.getBounds2D().getY();

      String rightText = (String) ( topLocationHashtable.get( "" + y ) );

      if( rightText != null ) {

	  return ( new Double( rightText ) ).doubleValue();

      }

      double right = Double.MIN_VALUE;

      for(int i = 0, len = sortedElements.length; i < len; i ++ ) {

	  ChartElement ce = (ChartElement) sortedElements[ i ];

	  Rectangle2D bounds = ce.getShape().getBounds2D();

	  if( bounds.getY() == y ) {

	     double x = bounds.getX() + bounds.getWidth();

	     if( x > right ) {

		 right = x;

	     }

	  }

      }

      topLocationHashtable.put( "" + y, "" + right );

      return right;

  }

  /**
   * ������ ������ �����ϴ� �Լ�
   */
   // 9/25�Ͽ� ChartType3D Ŭ�������� �������� �Լ��� �̵���.

  public Shape [][] createShapes3DGroup(float tx, float ty, Shape shape, float proX, float proY) {

      return ThreeDimUtilities.get3DBarShapesGroup( tx, ty, shape.getBounds2D(), proX, proY );

  }

  public Shape [][] createShapes3DGroup(float tx, float ty, Shape shape, float proX, float proY,
	 float top, float bottom ) {

      return ThreeDimUtilities.get3DBarShapesGroup( tx, ty, shape.getBounds2D(), proX, proY );

  }

	/**
	 *
	 */
    public void calibrateAxisMinMaxValueAndUnit( ValueAxisOption valueAxisOption, double min, double max ) {

		System.out.println(" starting calibrate");

		valueAxisOption.calibrateAxisMinMaxValueAndUnit ( min, max );

		System.out.println(" ending calibrate");

    }

	public abstract Color getPictureExtentBackground();

	public abstract void setGraphExtent(GraphExtent graphExtent);

	public abstract void setPictureExtent(PictureExtent pictureExtent);

	public abstract void setShapeAndStyle(DataSeriesView dataSeriesView);

	public abstract Shape createGraphExtentShape(Chart chart);

	public abstract DataSeries [] getValidDataSeries(Chart chart);




	// ��Ʈ Ÿ�Կ� ���� �׸� ���� ���� ���̺��� ��ġ�� ���̺� �� text�� PointedObject �������� �����ϴ� �Լ�
	public abstract PointedObject [] getTickLabelPointsOfItemAxis(ItemAxis itemAxis);

	// ��Ʈ Ÿ�Կ� ���� �� ���� ���� ���̺��� ��ġ�� ���̺� �� text�� PointedObject �������� �����ϴ� �Լ�
	public abstract PointedObject [] getTickLabelPointsOfValueAxis(ValueAxis valueAxis);

	// ��Ʈ Ÿ�Կ� ���� �׸� ���� �� ���ݵ��� ��ġ�� PointedObject �������� �����ϴ� �Լ�
	public abstract Point [] getTickMarkPointsOfItemAxis(ItemAxis itemAxis, byte tickMarkType);

	// ��Ʈ Ÿ�Կ� ���� �� ���� ���ݵ�� ���� ���̺��� ��ġ���� PointedObject �������� �����ϴ� �Լ�
	public abstract Point [] getTickMarkPointsOfValueAxis(ValueAxis valueAxis, byte tickMarkType);


	public abstract String getValueAxisUnit();

	public abstract int getNumberOfGridMarkOfValueAxis(double min, double max, double unit);

	public abstract void createLegendItems(Legend legend);

	// �׸� �࿡ �ش��ϴ� ������ tickMark�� ���� pictureExtent�� �����ϴ� �Լ�
	public abstract void createTickMarkGroupOfItemAxis(Axis axis, byte tickMarkType, byte tickMark);

	// �� �࿡ �ش��ϴ� ������ tickMark�� ���� pictureExtent�� �����ϴ� �Լ�
	public abstract void createTickMarkGroupOfValueAxis(Axis axis, byte tickMarkType, byte tickMark);

	// �׸� �࿡ �ش��ϴ�  �� ���ݼ��� pictureExtent�� �����ϴ� �Լ�
	public abstract void createMajorGridLineGroupOfItemAxis(PictureExtent pictureExtent);

	// �׸� �࿡ �ش��ϴ� ���� ���ݼ��� pictureExtent�� �����ϴ� �Լ�
	public abstract void createMinorGridLineGroupOfItemAxis(PictureExtent pictureExtent);

	// �� �࿡ �ش��ϴ� �� ���ݼ��� pictureExtent�� �����ϴ� �Լ�
	public abstract void createMajorGridLineGroupOfValueAxis(PictureExtent pictureExtent);

	// �� �࿡ �ش��ϴ� ���� ���ݼ��� pictureExtent�� �����ϴ� �Լ�
	public abstract void createMinorGridLineGroupOfValueAxis(PictureExtent pictureExtent);



	/**
	 * Feedback Interface�� ��ӹ޴� ChartType�� ���ؼ� feedbackEvent�� ���� Handling�� �����ϴ� �Լ���
	 * ���õ� DataElementView�� (tx, ty)��ŭ ũ�⸦ �������� �� �׿� ������ SpreadSheet��
	 * Cell�� ���� ���� ��Ű�� Cell�� ������ �׷��� ��� Chart���� ������Ʈ �ϴ� �Լ��̴�.
	 *
	 * @param   dataElementView : feedbackEvent�� ���õ� DataElementView ��ü
	 * @param   pmx : �ֱ� ���콺�� pressed�Ǿ��� ��ġ�� X��ǥ
	 * @param   pmy : �ֱ� ���콺�� pressed�Ǿ��� ��ġ�� Y��ǥ
	 * @param   cmx : ���� ���콺 ��ġ�� �ش��ϴ� X��ǥ(trace X position)
	 * @param   cmy : ���� ���콺 ��ġ�� �ش��ϴ� Y��ǥ(trace Y position)
	 * @see     SpreadSheet Class�� mouseReleased(MouseEvent e)
	 * @see     getModifiedValue(DataElementView, int, int, int, int)
	 */
	public void	feedbackEventHandling( Graphics2D g2, DataElementView dataElementView, int pmx, int pmy, int cmx, int cmy ) {

		SpreadSheet sheet = dataElementView.getSheet();
		Chart chart = dataElementView.getChart();

		double modifiedValue = getModifiedValue( dataElementView, pmx, pmy, cmx, cmy );

		// SpreadSheet�� ����� ���� �����Ͽ� �ش�.
		sheet.setValueAtDataElementView( dataElementView, new Double( modifiedValue ) );

		chart.reCreateGraphExtentWhenModifiedValue(this, dataElementView);

	}



	/**
	 * FeedBackToolTip�� �� ����� ���� ����Ͽ� �����ϴ� �Լ��̴�.
	 *
	 * @param   dataElementView : feedbackEvent�� ���õ� DataElementView ��ü
	 * @param   pmx : �ֱ� ���콺�� pressed�Ǿ��� ��ġ�� X��ǥ
	 * @param   pmy : �ֱ� ���콺�� pressed�Ǿ��� ��ġ�� Y��ǥ
	 * @param   cmx : ���� ���콺 ��ġ�� �ش��ϴ� X��ǥ(trace X position)
	 * @param   cmy : ���� ���콺 ��ġ�� �ش��ϴ� Y��ǥ(trace Y position)
	 * @see     getFeedbackTooltipsString(DataElementView, int, int)
	 */
	public double getModifiedValue(DataElementView dataElementView, int pmx, int pmy, int cmx, int cmy) {

		double currentValue = (int)dataElementView.getDataElement().getValue();
		double currentSum = dataElementView.getDataElement().getDataSeries().getSum();

		if (dataElementView.getChartType() instanceof PercentedChartType) {


			double percentedValue = getModifiedPercentedValue(dataElementView, pmx, pmy, cmx, cmy);

			Utility.debug(this, "previousSum = " + currentSum);
			Utility.debug(this, "percentedValue = " + percentedValue);

			Utility.debug(this, "modifiedValue = " + currentSum * percentedValue / ( 100 - percentedValue ));

			return (currentSum - currentValue) * percentedValue / ( 100 - percentedValue ) ;

		} else {

			int currentHeight = (int)dataElementView.getArea().getHeight();
			int modifiedHeight = currentHeight - (cmy - pmy) ;

			return (double) currentValue * modifiedHeight / currentHeight;
		}

	}

	/**
	 * feedBack Event���� mouse drag�Ǵ� ���� ���� ���� ���÷��� �޼����
	 * tooltip�� �������� ��Ÿ����.
	 *
	 * @param   g2 : ���õ� Gragphics2D ��ü
	 * @param   dataElementView : feedbackEvent�� ���õ� DataElementView ��ü
	 * @param   pmx : mouse pressed Event ���� X��ǥ(previous mouse X position)
	 * @param   pmy : mouse pressed event ���� Y��ǥ(previous mouse Y position)
	 * @param   tx : mouse drag �Ǽ� ������ X��ǥ(trace X position)
	 * @param   ty : mouse drag �Ǽ� ������ Y��ǥ(trace Y position)
	 * @see     getFeedbackTooltipsString(DataElementView, int, int)
	 */
	public void displayFeedbackTooltips(Graphics2D g2, DataElementView dataElementView, int pmx, int pmy, int cmx, int cmy) {

		int tx = cmx - pmx, ty = cmy - pmy;

		FontMetrics fm = g2.getFontMetrics();

		// drag�Ǵ� ������ ����� ���� feedbackTooltip�� �����´�.
		String feedbackToolTip = getFeedbackTooltipsString(dataElementView, pmx, pmy, cmx, cmy) ;

		int w = fm.stringWidth( feedbackToolTip ) + 6;  // tooltip rectangle width
		int h = fm.getHeight(); // tooltip rectangle height

		ShapeStyle style = dataElementView.getShapeStyle().getDefaultShapeStyle();

		Rectangle textArea = new Rectangle( pmx, pmy - 40, w, h );

		style.paint( g2, textArea );

		dataElementView.drawString( null, feedbackToolTip, g2, g2.getFont(), textArea );

	}

	/**
	 * FeedBackToolTip�� �� String�� ChartTyep�� �°� �����ϴ� �Լ��̴�.
	 *
	 * @param   dataElementView : feedbackEvent�� ���õ� DataElementView ��ü
	 * @param   tx : mouse drag �Ǽ� ������ X��ǥ(trace X position)
	 * @param   ty : mouse drag �Ǽ� ������ Y��ǥ(trace Y position)
	 * @see     displayFeedbackTooltips(Graphics, DataElementView, int, int)
	 * @see     getModifiedPercentedValue(DataElementView, int, int)
	 * @see     getModifiedValue(DataElementView, int, int)
	 */
	public String  getFeedbackTooltipsString(DataElementView dataElementView, int pmx, int pmy, int cmx, int cmy) {


		// ChartType�� 100% ���� ������Ʈ�̰ų� Circle�� ��쿡�� PercentedValue�� ToolTip Stirng�� ����.
		if (dataElementView.getChartType() instanceof PercentedChartType) {

			return getModifiedPercentedValue(dataElementView, pmx, pmy, cmx, cmy) + "%";

		// �ƴ� ��쿡�� dataElement�� ���� Tooltip String�� ����.
		} else {

		    return getModifiedValue(dataElementView, pmx, pmy, cmx, cmy) + "";
		}
	}

	/**
	 * FeedBackToolTip�� �� ����� ���� �������� �迭(series)������ �ۼ�Ʈ���� ����Ͽ� �����ϴ� �Լ��̴�.
	 *
	 * @param   dataElementView : feedbackEvent�� ���õ� DataElementView ��ü
	 * @param   tx : mouse drag �Ǽ� ������ X��ǥ(trace X position)
	 * @param   ty : mouse drag �Ǽ� ������ Y��ǥ(trace Y position)
	 * @see     getFeedbackTooltipsString(DataElementView, int, int)
	 */
	public double getModifiedPercentedValue(DataElementView dataElementView, int pmx, int pmy, int cmx, int cmy) {

		float modifiedPercentedValue = 0;

	    if( dataElementView.getChartType() instanceof ChartTypeCircle ) {

		    Point2D [] vp = ShapeUtilities.getPieControlPoints( dataElementView.getShape() );

		    Point2D rootVertex = vp[ 0 ];
		    Point2D otherVertex = vp[ 1 ];
		    Point2D feedbackVertex = vp[ 2 ];


			Utility.debug(this, "rootVertex = " + rootVertex);
			Utility.debug(this, "feedbackVertex = " + feedbackVertex);
			Utility.debug(this, "otherVertex = " + otherVertex);
			Utility.debug(this, "mx = " + cmx + ", my = " + cmy);

			double currentAngle = Utility.getAngleForArc( rootVertex.getX()
											    , rootVertex.getY()
											    , otherVertex.getX()
												, otherVertex.getY()
												, feedbackVertex.getX()
												, feedbackVertex.getY());

			double modifiedAngle = Utility.getAngleForArc( rootVertex.getX()
											    , rootVertex.getY()
											    , otherVertex.getX()
												, otherVertex.getY()
												, (double) cmx
												, (double) cmy );

//			Utility.debug(this, "curAngle = " + currentAngle);
			Utility.debug(this, "modifiedAngle = " + modifiedAngle);


			modifiedPercentedValue = (float) ((modifiedAngle / 360) * 100);

//			Utility.debug(this, "" + modifiedPercentedValue);
		} else {

			double previousValue = dataElementView.getDataElement().getValue();
			double previousSum = dataElementView.getDataElement().getDataSeries().getSum();

			double modifiedValue = getModifiedValue(dataElementView, pmx, pmy, cmx, cmy);
			double modifiedSum = previousSum + modifiedValue - previousValue;

			modifiedPercentedValue = (float) (modifiedValue / modifiedSum * 100);
		}

			return modifiedPercentedValue;

	}

	public boolean isLinearChartType() {

	      return ( this instanceof LinearChartType ) && ( ! ( this instanceof ChartType3D ) );
	}

	/**
	 * ChartType�� 2D�� ����� SelectedMarks�� �����Ͽ� �����Ѵ�.
	 */
	public Rectangle [] getSelectedMarks(DataElementView dataElementView) {

		if( this instanceof ChartType3D ) {

			return dataElementView.getSelectedMarksLineType3D();

		} else if( dataElementView instanceof LinearDataElementView ) {

			return dataElementView.getSelectedMarksLineType();

		} else {

			ChartElement sce = SpreadSheet.getCurrentSpreadSheet().getSelectedChartElement();

			if( sce != null && sce == dataElementView.getParent() ) {

			    Rectangle area = dataElementView.getArea();

			    int cx = area.x + area.width/2;
			    int cy = area.y + area.height/2;

			    int WI = 6;
			    int HE = 6;

			    return new Rectangle [] { new Rectangle( cx - WI/2, cy - HE/2, WI, HE ) };

			}

			return dataElementView.getSelectedMarksMiddleOnBoundary();

		}

	}

	public int getFeedbackTopology() {

	    return 1;

	}

	public boolean isChartTypeCircle3D() {

	    return (this instanceof ChartTypeCircle3D ) || ( this instanceof ChartTypeCircleDivided3D );

	}

	 /**
	 * checks if valid chart data elements composition
	 * @param source is event source that calls this funcion
	 */
	public boolean isValidChartData(ChartData cd, Object source) {

	  return isValidChartDataValues( cd , source );

	}

	 /**
	 * checks if valid chart data elements composition
	 * @param source is event source that calls this funcion
	 */

	public boolean isValidChartDataValues(ChartData cd, Object source) {

	    try {

		DataSeries [] dss = cd.getDataSeries();

		for(int i = 0, iLen = dss.length; i < iLen; i ++ ) {

		    DataElement [] des = dss[i].getDataElements();

		    for( int k = 0, kLen = des.length; k < kLen; k ++ ) {

		      des[k].getValue();

		    }
		}

	    } catch (Exception e) {


	      String warnMsg = null; // warning message

	      if( source == null ) {

		  warnMsg = "íƮ �����͸� �����ϰ� �����Ͽ� �ּ���!";

	      } else {

		  warnMsg = "������ íƮ Ÿ���� �ƴմϴ�.";

	      }

	      Utility.warn( warnMsg, SpreadSheet.getCurrentSpreadSheet() );

	      return false;

	    }

	    return true;

	}

   public double [][] convertToArray(DataSeries [] dss) {

    int len = dss.length;

    double [][] data = new double[ len ][];

    for( int i = len - 1 ; i > - 1 ; i -- ) {

      DataSeries ds = dss[ i ];

      DataElement [] des = ds.getDataElements();

      int kLen = des.length;

      double [] dataRow = new double[ kLen ];

      for( int k = 0; k < kLen; k ++ ) {

	dataRow[ k ] = des[ k ].getValue();

      }

      data[ len - i - 1 ] = dataRow;

    }

    return data;

  }

}






