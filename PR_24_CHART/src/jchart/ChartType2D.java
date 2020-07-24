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
import jcosmos.*;

public abstract class ChartType2D extends ChartType {

	public ChartType2D() {
	}

	public void createLegendItems(Legend legend) {

		legend.removeAllChild();

		Chart chart = legend.getChart();

		DataSeries [] dss = getValidDataSeries( chart );

		Shape liShape = null; // legend item shape
		ShapeStyle liStyle = null; // legend item style
		String liString = null; // legend item string

		Rectangle area = legend.getArea(); // legend shape area

		final int len = dss.length; // dss length

		int lx = (int) area.getX(), ly = (int) area.getY(),
			lw = (int) area.getWidth(), lh = (int) area.getHeight(); // legend boundary values

		int h = lh/len,
			dh = h,
			x = lx + lw/10,
			y = ly,
			w = lw - lw/6;
		// legend item boundary values

		SpreadSheet sheet = legend.getSpreadSheet();

		for(int i = 0; i < len; i ++ ) {

			liString = dss[i].getSeriesName();

			liShape = new Rectangle( x, y, w, h );

			y += dh;

			liStyle = new ShapeStyle( ChartType.getChartColor( i ), Color.black );

			LegendItem li = new LegendItem( sheet, legend, dss[i], liString, liShape, liStyle);

			legend.addChild( li );

		}

	}


	/**
	 * ValueAxis���� GridMarkGroup�ȿ� ���ϴ� GridMark�� ������ ����Ͽ� �����Ѵ�.
	 *
	 * @param   min : ValueAxis������ �ּҰ��� ��Ÿ����.
	 * @param   max : ValueAxis������ �ִ밪�� ��Ÿ����.
	 * @param   unit : ValueAxis������ �� ������ ��Ÿ����.
	 * @see     createMajorLineGroupOfValueAxis(PictureExtent);
	 */
	public int getNumberOfGridMarkOfValueAxis(double min, double max, double unit) {

		int divNum = (int)( ( max - min ) / unit );

		return divNum + 1;

	}

//	public int getGridLineDivisionNumber(ChartData cd, Rectangle area ) {
//
//		double min = cd.getAxisMinValue();
//		double max = cd.getAxisMaxValue();
//
//		double [] minMaxAndUnit = calibrateAxisMinMaxValueAndUnit( min, max );
//
//		double divNum = (minMaxAndUnit[1] - minMaxAndUnit[0] )/minMaxAndUnit[2];
//
//		double minPBGL = super.minPixelBetweenGridLines;   // minimum pixel between grid lines
//
//		int h = ( this instanceof HorizontalChartType ) ? area.width : area.height;
//
//		divNum = ( divNum == 0 ) ? 1 : divNum;
//
//		int pixels =(int)( h/divNum );
//
//		pixels = ( pixels == 0 ) ? 1 : pixels;
//
//		if( pixels < minPBGL ) {
//
//			int cali = (int)( minPBGL/pixels );  // calibration
//
//			cali = ( cali == 0 ) ? 1 : cali;
//
//			divNum = divNum/cali;
//
//		}
//
//		return (int) divNum -1 ;
//
//	}

	public Color getPictureExtentBackground() {
		return Color.lightGray;
	}

	public Shape getDataElementIcon(int oci, int x, int y) { // oci means occurrence index

		oci = oci%9;

		if( oci == 0 ) {
			return IconManager.getDiaMond( x, y );
		} else if( oci == 1 ) {
			return IconManager.getRectangle( x, y );
		} else {
			return IconManager.getTriAngle( x, y );
		}
	}

	public void setGraphExtent(GraphExtent graphExtent){

		Chart chart = graphExtent.getChart();
		ChartData cd = chart.getChartData();

		ValueAxisOption valueAxisOption = chart.getValueAxisOption();

		// TickMark�� �� �ִ밪�� �ּҰ� �׸��� ������ �����Ѵ�.
		valueAxisOption.calibrateAxisMinMaxValueAndUnit(cd.getMinValue(), cd.getMaxValue() );


		SpreadSheet sheet = graphExtent.getSpreadSheet();

		Rectangle area = graphExtent.getArea(); // graph area

		int x = area.x, y = area.y, w = area.width, h = area.height; // area coordinates

		int yaw = (int)(h*AppRegistry.VAULE_AXIS_WIDTH_RATIO); // y-axis width
		int xah =(int)(w*AppRegistry.ITEM_AXIS_HEIGHT_RATIO); // x-axis height

		Font font = FontManager.getDefaultFont(); // font for x axis text and y axis text

		// x-axis creation

		Rectangle itemAxisRect = new Rectangle(x + yaw, y + h - xah, w - yaw, xah );

		ShapeStyle itemAxisStyle = ShapeStyle.getDefaultShapeStyle();

		ItemAxis itemAxis = new ItemAxis( sheet, graphExtent, itemAxisRect, itemAxisStyle );

		graphExtent.addChild( itemAxis );

		// end of x-axis creation

		// y-axis creation

		Rectangle yAxisRect = new Rectangle(x, y, yaw, h - xah);

		ShapeStyle yAxisStyle = ShapeStyle.getDefaultShapeStyle();

		ValueAxis yAxis = new ValueAxis( sheet, graphExtent, yAxisRect, yAxisStyle );

		graphExtent.addChild( yAxis );

		// end of x-axis creation

		// picture area creation

		Rectangle pictureRect = new Rectangle( x + yaw + 1, y, w - yaw - 1, h - xah -1);

		ShapeStyle pictureStyle = ShapeStyle.getDefaultShapeStyle();

		pictureStyle.setFillColor( getPictureExtentBackground() );

		PictureExtent pictureExtent = new PictureExtent( sheet, graphExtent, pictureRect, pictureStyle );

		graphExtent.addChild( pictureExtent );

		// end of picture area creation
	}

	public void setPictureExtent(PictureExtent pictureExtent) {

		SpreadSheet sheet = pictureExtent.getSheet();
		ChartElement ge = pictureExtent.getParent(); // graph extent
		Rectangle area = pictureExtent.getArea();

		ValueAxis valueAxis = (ValueAxis) ge.getChartElement( ValueAxis.class );

		ValueAxisOption valueAxisOption = ( ValueAxisOption ) valueAxis.getAxisOption();

		ItemAxis itemAxis = (ItemAxis) ge.getChartElement( ItemAxis.class );

		ItemAxisOption itemAxisOption = ( ItemAxisOption ) itemAxis.getAxisOption();



		if ( valueAxisOption.getHasMajorGridLineGroup() ) {

			createMajorGridLineGroupOfValueAxis( pictureExtent );

		}

		if ( valueAxisOption.getHasMinorGridLineGroup() ) {

			createMinorGridLineGroupOfValueAxis( pictureExtent );

		}

		if ( itemAxisOption.getHasMajorGridLineGroup() ) {

			createMajorGridLineGroupOfItemAxis( pictureExtent );

		}

		if ( itemAxisOption.getHasMinorGridLineGroup() ) {

			createMinorGridLineGroupOfItemAxis( pictureExtent );

		}

		// adds data series views

		ChartData cd = pictureExtent.getChart().getChartData();

		DataSeries [] ds = cd.getDataSeries();

		for(int i = 0, len = ds.length; i < len; i ++) {

			DataSeriesView dsv = new DataSeriesView( sheet, pictureExtent, null, null, this, ds[i] );

			pictureExtent.addChild( dsv );

//			Utility.debug( this, "DataSeries Name " + ds[i].getSeriesName() );

		}

		// end of adding data series views

	}

	public Shape createGraphExtentShape(Chart chart) {

		Rectangle area = chart.getArea();  // chart area

		int x = area.x, y = area.y, w = area.width, h = area.height;

		x = (int)(x + w*chart.graphExtentLeftMarginRatio);
		y = (int)(y + w*chart.graphExtentTopMarginRatio);
		w = (int)(w*chart.graphExtentWidthRatio);
		h = (int)(h*chart.graphExtentHeightRatio);

		if( this instanceof FixedDimensionRatioChartType ) {
			int smallLen = ( w < h ) ? w : h;

			x += ( w - smallLen )/2;
			y += ( h - smallLen )/2;

			w = h = smallLen;
		}

		return new Rectangle( x, y, w, h );
	}

	public DataSeries [] getValidDataSeries(Chart chart) {

		return chart.getChartData().getDataSeries();

	}


	/**
	 * 2D�� ��Ʈ Ÿ���� ��쿡 �� ���� ���� ���̺��� ��ġ�� ���̺� �� text��
	 * PointedObject �������� �����ϴ� �Լ��̴�.
	 *
	 * ����)  1. ���� ���̺��� ���� ���� �Ǵ� ���ݼ��� ���� �����ϴ�.
	 *       2. ���� ���̺��� ��ġ�� ���� �Ǵ� ���ݼ��� ��ġ�� �����ϴ�.
	 *       3. ù ���� ���̺�(���� �ؿ� �����ϴ� ���� ���̺�)�� text�� ������ �ּҰ��� text�� ������.
	 *       4. ������ ���� ���̺�(���� ���� �����ϴ� ���� ���̺�)�� text�� ������ �ִ밪�� text�� ������.
	 *       5. ó���� ������ ���� ���̺� ���̿� �����ϴ� ���̺��� text�� ���������� ù ���� ���̺��� ����
	 *          �� ���� �� ���� ��ŭ ������ ���� text�� ������.
	 *
	 *
	 * @see     getMajorTickMarkPointsOfValueAxis( ValueAxis);
	 */
	public PointedObject [] getTickLabelPointsOfValueAxis(ValueAxis valueAxis) {


		ValueAxisOption valueAxisOption = ( ValueAxisOption ) valueAxis.getAxisOption();

		double max = valueAxisOption.getMaxScale();
		double min = valueAxisOption.getMinScale();
		double unit = valueAxisOption.getMajorUnit();

		// ������ ��ġ�� �����´�.
	    Point [] refPoints = getTickMarkPointsOfValueAxis( valueAxis, AppRegistry.MAJOR_GRID_LINE_OF_VALUE_AXIS );

		int len = refPoints.length;

		// ���� 1
		PointedObject [] points = new PointedObject[ refPoints.length ];

		// ù��° ���ݷ��̺��� text�� �ּҰ����� �Ѵ�.(���� 3)
		double val = min;

		for( int i = 0; i < len ; i ++ ) {

			// ���� 5
			val = min + i * unit;

			// ���� 2
			points[i] = new PointedObject((int)(refPoints[i].x), (int)(refPoints[i].y), new Double( val ) );
		}

		return points;

	}

	/**
	 * 2D�� ��Ʈ Ÿ���� ��쿡 �׸� ���� ���� ���̺��� ��ġ�� ���̺� �� text��
	 * PointedObject �������� �����ϴ� �Լ��̴�.
	 *
	 * ����)  1. ���� ���̺��� ReferenceSeries�� Name�� ȭ�鿡 ��Ÿ���� ���̺��
	 *         ���� ���̺� ���̿� �� �׸� ��(tickLableSpacing)�� ȭ�鿡 ��Ÿ��
	 *         ���̺��� ���� �����Ѵ�.
	 *
	 *       2. tickLabelSpacing�� ���� 2 �̸� ȭ�鿡 ��Ÿ���� ReferenceSeries�� Name��
	 *         �Ѱ� �ǳʼ� ��Ÿ���� �ǹǷ� ���� ���̺��� ���� tickLabelSpacing
	 *         �� ���� ������ ���� �ȴ�.
	 *
	 *         (���� ���̺��� ��) = (RefSeries�� ��) / tickLabelSpacing
	 *
	 *       3. ���� ���̺��� ��ġ�� �ٸ� ������ ���� ������ �����Ƿ� ���������� ���� ��
	 *         �� �� �ִ�.
	 *
	 *         (���� ���̺� ������ �Ÿ�) = (�׸� ���� �ʺ�) / (���� ���̺��� ��)
	 *         (ù ���� ���̺��� ��ġ�� x ��ǥ) = (�׸� ���� x ��ǥ) + 1/2 * (���� ���̺� ������ �Ÿ�)
	 *
	 *
	 * @see     getMajorTickMarkPointsOfValueAxis( ValueAxis);
	 */
	public PointedObject [] getTickLabelPointsOfItemAxis(ItemAxis itemAxis) {

		Chart chart = itemAxis.getChart();

		ItemAxisOption itemAxisOption = ( ItemAxisOption ) itemAxis.getAxisOption();

		ChartData cd = chart.getChartData();  // chart data

		String [] refNames = cd.getReferenceNames(); // reference names

		// referenceSeries�� ����
		int numOfRefSeries = refNames.length;

		// ���ݷ��̺� ���̿� �� �׸� ��
		int tickLabelSpacing = itemAxisOption.getTickLabelSpacing();

		// ���� 2
		int numOfTickLabel = numOfRefSeries / tickLabelSpacing;

		PointedObject [] points = new PointedObject[ numOfTickLabel ];

		Rectangle area = itemAxis.getArea();

		int ax = area.x, ay = area.y, aw = area.width, ah = area.height;

		// ���� ���̺� ������ ����(���� 3)
		double dx = aw / numOfTickLabel;

		// ù MajorTickMark�� ��ġ(���� 3)
		double x1 = ax + (.5 * dx );
		double y1 = ay + (.5 * ah );
		double x;
		double y = y1;                  //��� ���� ���̺��� y ��ǥ�� ����.
		String text;

		for(int i = 0; i < numOfTickLabel; i ++ ) {

			// ù ���� ���̺��� ��ġ�� ���� ���� ���̺� ������ �Ÿ���ŭ �� x��ǥ��
			// i��°�� x ��ǥ�� �ȴ�.
			x = x1 + dx * i;

			text = refNames[ tickLabelSpacing * i ];

			// i��°�� majorTickMark �� ��ġ�� ��� �ִ� Point
			points[i] = new PointedObject( (int)(x), (int)(y), text );

		}

		return points;

	}


	/**
	 * ValueAxis���� MajorTickMark�� MajorGridLine���� �������� ��ġ�� �����Ѵ�.
	 * �ִ밪�� �ּҰ����� ���ݼ��� �������� �����Ƿ� ������ ������ ���ݼ��� ���� ���� 2��
	 * ũ��. ���� ���ݼ��� ��� ���ϵ� PointedObject�� ù��°�� �������� ������ �ʴ´�.
	 *
	 * ù��° PointObject�� �ּҰ��� ��ġ�� ���� ��Ÿ����.
	 * ������ PointObject�� �ִ밪�� ��ġ�� ���� ��Ÿ����.
	 *
	 * @see     ValueAxisOption Ŭ������ calibrateAxisMinMaxValueAndUnit(int, int)
	 * @see     createMajorGridLineGroupOfValueAxis(PictureExtent)
	 */
	public Point [] getTickMarkPointsOfValueAxis(ValueAxis valueAxis, byte tickMarkType) {

		Chart chart = valueAxis.getChart();

		ValueAxisOption valueAxisOption = ( ValueAxisOption ) valueAxis.getAxisOption();

		double max = valueAxisOption.getMaxScale();
		double min = valueAxisOption.getMinScale();
		double unit;

		if ( tickMarkType == AppRegistry.MAJOR_GRID_LINE_OF_VALUE_AXIS ) {

			unit = valueAxisOption.getMajorUnit();

		} else {    // tickMarkType == AppRegistry.MINOR_GRID_LINE_OF_VALUE_AXIS

			unit = valueAxisOption.getMinorUnit();

		}

		Rectangle area = valueAxis.getArea(); // area rectangle

		int ax = area.x, ay = area.y, ah = area.height;

		PictureExtent pictureExtent = (PictureExtent) chart.getChartElement( PictureExtent.class );

		// GridLineGroup �ȿ� �� GridLine�� ������ ���Ѵ�.
		int divNum = getNumberOfGridMarkOfValueAxis( min, max, unit );

		double dy = ah /( divNum - 1.0 );   // vertical graphical gap

		Point [] points = new Point[ divNum ];

		double x1 = ax, y1= ay + ah;    // ù gridLineGroup�� ��ǥ

		double x = x1, y;   // ��� gridLine�� x��ǥ�� �Ȱ���.
		int len = points.length; // points num

		for( int i = 0; i < len ; i ++ ) {

			y = y1 - i * dy;

			points[i] = new Point( (int)(x), (int)(y) );
		}

		return points;

	}


//	/**
//	 * ValueAxis���� MinorTickMark�� MinorGridLine���� �������� ��ġ�� Point Array��
//	 * �����Ѵ�.( Point�� ���� ���� �ʿ����� �����Ƿ� PointedObject�� �ʿ����� �ʴ�.)
//	 * �ִ밪�� �ּҰ����� ���ݼ��� �������� �����Ƿ� ������ ������ ���ݼ��� ����(TickLabel�� ������ ����)
//	 * ���� 2�� ũ��. ���� ���ݼ��� ��� ���ϵ� PointedObject�� ù��°�� �������� ������ �ʴ´�.
//	 *
//	 * ù��° PointObject�� �ּҰ��� ��ġ�� ���� ��Ÿ����.
//	 * ������ PointObject�� �ִ밪�� ��ġ�� ���� ��Ÿ����.
//	 *
//	 * @see     ValueAxisOption�� calibrateAxisMinMaxValueAndUnit(int, int)
//	 * @see     createMajorGridLineGroupOfValueAxis(PictureExtent)
//	 * @see
//	 */
//	public Point [] getMinorTickMarkPointsOfValueAxis(ValueAxis valueAxis) {
//
//		Chart chart = valueAxis.getChart();
//		ValueAxisOption valueAxisOption = ( ValueAxisOption ) valueAxis.getAxisOption();
//
//		// �����Ͽ� �ִ밪, �ּҰ�, �� ����, ���� ������ ���´�.
//		double max = valueAxisOption.getMaxScale();
//		double min = valueAxisOption.getMinScale();
//		double majorUnit = valueAxisOption.getMajorUnit();
//		double minorUnit = valueAxisOption.getMinorUnit();
//
//		Rectangle area = valueAxis.getArea(); // area rectangle
//
//		int ax = area.x, ay = area.y, ah = area.height;
//
//		// ���� ���ݼ��� ������ �� ���ݼ��� ������ ������ �޴´�. ���� �ִ��ݰ� ��������
//		// ���� ��ġ�� �ְ� �Ǹ� �� ���ݼ��� ������ �ȴ�. ���� ������ ������ ���� ��
//		// ���ݼ��� �������ݼ��� unit�� �ּ� ������� Unit���� �̷�� ������ ������ŭ
//		// �������ݼ��� ������ �پ� ��� �ȴ�.
//
//		// �� ���ݰ� �������°� ���� ���� ���� ������ ����
//		int numOfOriginalMinorTickMark = getNumberOfGridMarkOfValueAxis( min, max, minorUnit );
//
//		// �� ���ݰ� ���� ������ �ּ� �����(LCM)�� Unit �϶��� ������ ����
//		/**@todo: ���� �ִ��ݰ� ���������� ������ ���� LCM�� ���� �� �ְ� �Ǿ� �ִ�.*/
//		int lcmUnit = Utility.lcm( (int) majorUnit, (int) minorUnit);
//		int numOfLCMMinorTickMark = getNumberOfGridMarkOfValueAxis( min, max, lcmUnit + 0.0 );
//
//		// ���� ���� ������ ����
//		int numOfMinorTickMark = numOfOriginalMinorTickMark - numOfLCMMinorTickMark;
//
//		Point [] points = new Point[ numOfMinorTickMark ];
//
//		double x = ax, y = ay + ah;
//
//		double dy = ah / ( numOfOriginalMinorTickMark - 1.0 );
//
//		int len = points.length; // points num
//		Utility.debug(this, " numOfMinorTickMark = " + numOfMinorTickMark );
//
//		for ( int i = 0, j = 0; i < numOfOriginalMinorTickMark; i ++ ) {
//
//			// ���� ������ �� ���ݰ� ��ĥ ������ ��ŵ�Ѵ�.
//			if( ( lcmUnit % ( i * minorUnit ) == 0 ) && ( i != 0 ) ){
//				continue;
//			}
//
//		    y = ay + ah - i * dy;
//		    Utility.debug(this, "j = " + j );
//			points[ j ] = new Point ((int)(x), (int)(y));
//		    j++;
//		}
//
//		return points;
//
//	}


	/**
	 * ItemAxis���� MajorTickMark�� MajorGridLine���� �������� ��ġ�� ���ϵȴ�.
	 *
	 * @see     createMajorGridLineGroupOfItemAxis(PictureExtent)
	 */
	public Point [] getTickMarkPointsOfItemAxis(ItemAxis itemAxis, byte tickMarkType) {

		Chart chart = itemAxis.getChart();

		ItemAxisOption itemAxisOption = ( ItemAxisOption ) itemAxis.getAxisOption();

		ChartData cd = chart.getChartData();  // chart data

		String [] refNames = cd.getReferenceNames(); // reference names

		// referenceSeries�� ����
		int numOfRefSeries = refNames.length;

		// ���� ���̿� �� �׸� ��
		int tickMarkSpacing = itemAxisOption.getTickMarkSpacing();

		Rectangle area = itemAxis.getArea();

		int ax = area.x, ay = area.y, aw = area.width, ah = area.height;

		int numOfTickMarkPointsOfItemAxis;
		double dx;

		if ( tickMarkType == AppRegistry.MAJOR_GRID_LINE_OF_ITEM_AXIS ) {

			// MajorTickMark�� ����
			numOfTickMarkPointsOfItemAxis = ( numOfRefSeries / tickMarkSpacing ) + 1;


		} else {
		    // MinorTickMark�� ����
			numOfTickMarkPointsOfItemAxis = 2 * ( numOfRefSeries / tickMarkSpacing ) + 1;
			numOfRefSeries = 2 * numOfRefSeries;
		}

		Point [] points = new Point[ numOfTickMarkPointsOfItemAxis ];

		// ù TickMark�� ��ġ.
		double x = ax , y = area.y + ah;

		for(int i = 0; i < numOfTickMarkPointsOfItemAxis; i ++ ) {

			// ù TickMark�� ��ġ�� ���� ReferenceSeries�� ���ݸ�ŭ ����� ��ġ�Ѵ�.
			// ���⼭ tickMarkSpacing�� ���� ���� �ΰ�, ���� ���� ReferenceSeires ��ŭ ������
			// TickMark �� ������ �� �����Ƿ� dx * tickMarkSpacing�� �ؾ��Ѵ�.
			x = ax + ( aw * tickMarkSpacing * i ) / numOfRefSeries;

			// i��°�� majorTickMark �� ��ġ�� ��� �ִ� Point
			points[i] = new Point ((int)(x), (int)(y));

	    }

		return points;

	}

//	/**
//	 * ItemAxis���� MinorTickMark�� MinorGridLine���� �������� ��ġ�� ���ϵȴ�.
//	 *
//	 * @see     createMinorGridLineGroupOfItemAxis(PictureExtent)
//	 */
//	public Point [] getMinorTickMarkPointsOfItemAxis(ItemAxis itemAxis) {
//
//		Chart chart = itemAxis.getChart();
//
//		ItemAxisOption itemAxisOption = ( ItemAxisOption ) itemAxis.getAxisOption();
//
//		ChartData cd = chart.getChartData();  // chart data
//
//		String [] refNames = cd.getReferenceNames(); // reference names
//
//		// referenceSeries�� ����
//		int numOfRefSeries = refNames.length;
//
//		// ���� ���̿� �� �׸� ��
//		int tickMarkSpacing = itemAxisOption.getTickMarkSpacing();
//
//		// ���� ���̿� �� �׸���� tickMarkSpacing���� ���߱� ���ؼ� MinorTickMark
//		// �� ������ ������ ���ƾ� �Ѵ�.
//		// ������ ������ ����. ���� MajorTickMark ���̿� �ϳ��� MinorTickMark�� ������
//		// �Ƿ� MajorTickMark�� ��ġ�� MinorTickMark�� �����Ѵٰ� �����ϸ� minorTickMark��
//		// ������ major�� ������ ���� �ǹǷ�..
//		// numOfMinorTickMarkPointOfItemAxis = ( numOfRefSeries / tickMakrSpacing ) * 2 + 1
//		// ���⿡�� major�� minor�� ������ ���� minor�� ����( = major�� ����)�� ����
//		// ������ �������� �ȴ�.
//		int numOfMinorTickMarkPointsOfItemAxis = ( numOfRefSeries / tickMarkSpacing );
//
//		Point [] points = new Point [ numOfMinorTickMarkPointsOfItemAxis ];
//
//		Rectangle area = itemAxis.getArea();
//
//		int ax = area.x, ay = area.y, aw = area.width, ah = area.height;
//
//		// ReferenceSeries�� ����
//		double dx = aw / numOfRefSeries ;
//
//		// ù MinorTickMark�� ��ġ.
//		double x = ax + dx / 2 , y = area.y + ah;
//
//		for(int i = 0; i < numOfMinorTickMarkPointsOfItemAxis; i ++ ) {
//
//			// ù MinorTickMark�� ��ġ�� ���� ReferenceSeries�� ���ݸ�ŭ ����� ��ġ�Ѵ�.
//			// ���⼭ tickMarkSpacing�� ���� ���� �ΰ�, ���� ���� ReferenceSeires ��ŭ ������
//			// MajorTickMark �� ������ �� �����Ƿ� dx * tickMarkSpacing�� �ؾ��Ѵ�.
//			x = ax + dx * tickMarkSpacing * i;
//
//			// i��°�� majorTickMark �� ��ġ�� ��� �ִ� Point
//			points[i] = new Point ((int)(x), (int)(y));
//
//	    }
//
//		return points;
//
//	}





	/**
	 * set shape and shape style of data series view
	 */

	public abstract void setShapeAndStyle(DataSeriesView dataSeriesView);



	// �׸� �࿡ �ش��ϴ� ������ tickMark�� ���� pictureExtent�� �����ϴ� �Լ�
	public void createTickMarkGroupOfItemAxis(Axis axis, byte tickMarkType, byte tickMark) {

		SpreadSheet sheet = axis.getSheet();
		Rectangle area = axis.getArea();

		ItemAxis itemAxis = (ItemAxis) axis;

		TickMarkGroup tmg = new TickMarkGroup( sheet,  axis, area, null, tickMarkType );

		Point [] points;

		if ( tickMarkType == AppRegistry.MAJOR_TICK_MARK ) {

			points = itemAxis.getMajorTickMarkPoints();

		} else {

		    points = itemAxis.getMinorTickMarkPoints();

		}

		int y0 = 0 , y1 = 0, x;
		int distance = (int)( .3 * area.height );
		switch( tickMark ) {

		// ������ ������ ��
		case AppRegistry.TICK_MARK_INSIDE :
			y0 = area.y;
			y1 = y0 - distance;
			break;

		// ������ �ٱ����� ��
		case AppRegistry.TICK_MARK_OUTSIDE :
		    y0 = area.y;
			y1 = y0 + distance;
			break;

		// ������ ������ ��
		case AppRegistry.TICK_MARK_CROSS :
		    y0 = area.y - distance;
			y1 = y0 + 2 * distance;
			break;
		// ������ ���� ��( �̻�Ȳ�� ���� �ʴ´�... �ֳĸ� ������ ������ �� �Լ��� ȣ�� ���� �ʴ´�)
		case AppRegistry.TICK_MARK_NONE :

		    return;

		}


		// TickMark�� Style�� Area�� Style�� �����ϴ�
		ShapeStyle tickMarkStyle = axis.getShapeStyle();


		for(int i = 0, len = points.length; i < len; i ++ ) {

			x = points[i].x;

			TickMark tm = new TickMark( sheet, tmg, x, y0, x, y1, tickMarkStyle );

			tmg.addChild( tm );

		}

		itemAxis.addChild( tmg );



	}

	/**
	 * 2D���� �� �࿡ �ش��ϴ� ������ tickMark�� ���� pictureExtent�� �����ϴ� �Լ�
	 */
	public void createTickMarkGroupOfValueAxis(Axis axis, byte tickMarkType, byte tickMark){

		SpreadSheet sheet = axis.getSheet();
		Rectangle area = axis.getArea();

		ValueAxis valueAxis = (ValueAxis) axis;

		TickMarkGroup tmg = new TickMarkGroup( sheet,  axis, area, null, tickMarkType );

		Point [] points;

		if ( tickMarkType == AppRegistry.MAJOR_TICK_MARK ) {

			points = valueAxis.getMajorTickMarkPoints();

		} else {

		    points = valueAxis.getMinorTickMarkPoints();

		}

		int x0 = 0 , x1 = 0, y;
		int distance = (int)( .3 * area.width );
		switch( tickMark ) {

		// ������ ������ ��
		case AppRegistry.TICK_MARK_INSIDE :
			x0 = area.x + area.width;
			x1 = x0 + distance;
			break;

		// ������ �ٱ����� ��
		case AppRegistry.TICK_MARK_OUTSIDE :
		    x0 = area.x + area.width;
			x1 = x0 - distance;
			break;

		// ������ ������ ��
		case AppRegistry.TICK_MARK_CROSS :
		    x0 = area.x + area.width - distance;
			x1 = x0 + 2 * distance;
			break;
		// ������ ���� ��( �̻�Ȳ�� ���� �ʴ´�... �ֳĸ� ������ ������ �� �Լ��� ȣ�� ���� �ʴ´�)
		case AppRegistry.TICK_MARK_NONE :

		    return;

		}


		// TickMark�� Style�� Area�� Style�� �����ϴ�
		ShapeStyle tickMarkStyle = axis.getShapeStyle();


		for(int i = 0, len = points.length; i < len; i ++ ) {

			y = points[i].y;

			TickMark tm = new TickMark( sheet, tmg, x0, y, x1, y, tickMarkStyle );

			tmg.addChild( tm );

		}

		valueAxis.addChild( tmg );


	}



	/**
	 * �ش� PictureExtent�� ValueAxis�� ���ϴ� MajorGridLine�� ����� �Լ�
	 */
	public void createMajorGridLineGroupOfValueAxis(PictureExtent pictureExtent) {

		SpreadSheet sheet = pictureExtent.getSheet();
		ChartElement ge = pictureExtent.getParent(); // graph extent
		Rectangle area = pictureExtent.getArea();

		ValueAxis valueAxis = (ValueAxis) ge.getChartElement( ValueAxis.class );

		GridLineGroup glg = new GridLineGroup( sheet, pictureExtent, null, null, AppRegistry.MAJOR_GRID_LINE_OF_VALUE_AXIS );

		Point [] points = valueAxis.getMajorTickMarkPoints();

		int x0 = area.x, w = area.width, x1 = x0 + w;

		ShapeStyle glStyle = new ShapeStyle( null, Color.black );  // grid line shape style


		// ���ϵ� getMajorTickMarkPoints���� ù��° Point�� �ּҰ� ��Ÿ����
		// TickMark�� ���Ǵ� ���̹Ƿ� GridLine���� �ʿ���� Point�̴�.
		for(int i = 1, len = points.length; i < len; i ++ ) {

			int y = points[i].y;

			GridLine gl = new GridLine( sheet, glg, x0, y, x1, y, glStyle );

			glg.addChild( gl );

		}

		pictureExtent.addChild( glg );

	}

	/**
	 * �ش� PictureExtent�� ValueAxis�� ���ϴ� MinorGridLine�� ����� �Լ�
	 */
	public void createMinorGridLineGroupOfValueAxis(PictureExtent pictureExtent) {

		SpreadSheet sheet = pictureExtent.getSheet();
		ChartElement ge = pictureExtent.getParent(); // graph extent
		Rectangle area = pictureExtent.getArea();

		ValueAxis valueAxis = (ValueAxis) ge.getChartElement( ValueAxis.class );

		GridLineGroup glg = new GridLineGroup( sheet, pictureExtent, null, null, AppRegistry.MINOR_GRID_LINE_OF_VALUE_AXIS );

		Point [] points = valueAxis.getMinorTickMarkPoints();

		int x0 = area.x, w = area.width, x1 = x0 + w;

		ShapeStyle glStyle = new ShapeStyle( null, Color.black );  // grid line shape style

		for(int i = 1, len = points.length; i < len; i ++ ) {

			int y = points[i].y;

			GridLine gl = new GridLine( sheet, glg, x0, y, x1, y, glStyle );

			glg.addChild( gl );

		}

		pictureExtent.addChild( glg );

	}

	/**
	 * �ش� PictureExtent�� ItemAxis�� ���ϴ� MajorGridLine�� ����� �Լ�
	 */
	public void createMajorGridLineGroupOfItemAxis(PictureExtent pictureExtent) {

	    SpreadSheet sheet = pictureExtent.getSheet();
		ChartElement ge = pictureExtent.getParent(); // graph extent
		Rectangle area = pictureExtent.getArea();

		ItemAxis itemAxis = (ItemAxis) ge.getChartElement( ItemAxis.class );

		GridLineGroup glg = new GridLineGroup( sheet, pictureExtent, null, null, AppRegistry.MAJOR_GRID_LINE_OF_ITEM_AXIS );

		Point [] points = itemAxis.getMajorTickMarkPoints();

		int y0 = area.y, h = area.height, y1 = y0 + h;

		ShapeStyle glStyle = new ShapeStyle( null, Color.black );  // grid line shape style

		for(int i = 0, len = points.length; i < len; i ++ ) {

			int x = points[i].x;

			GridLine gl = new GridLine( sheet, glg, x, y0, x, y1, glStyle );

			glg.addChild( gl );

		}

		pictureExtent.addChild( glg );


	}

	/**
	 * �ش� PictureExtent�� ItemAxis�� ���ϴ� MinorGridLine�� ����� �Լ�
	 */
	public void createMinorGridLineGroupOfItemAxis(PictureExtent pictureExtent) {

	    SpreadSheet sheet = pictureExtent.getSheet();
		ChartElement ge = pictureExtent.getParent(); // graph extent
		Rectangle area = pictureExtent.getArea();

		ItemAxis itemAxis = (ItemAxis) ge.getChartElement( ItemAxis.class );

		GridLineGroup glg = new GridLineGroup( sheet, pictureExtent, null, null, AppRegistry.MINOR_GRID_LINE_OF_ITEM_AXIS );

		Point [] points = itemAxis.getMinorTickMarkPoints();

		int y0 = area.y, h = area.height, y1 = y0 + h;

		ShapeStyle glStyle = new ShapeStyle( null, Color.black );  // grid line shape style

		for(int i = 0, len = points.length; i < len; i ++ ) {

			int x = points[i].x;

			GridLine gl = new GridLine( sheet, glg, x, y0, x, y1, glStyle );

			glg.addChild( gl );

		}

		pictureExtent.addChild( glg );


	}



	public String getValueAxisUnit() {

		return "";

	}

}