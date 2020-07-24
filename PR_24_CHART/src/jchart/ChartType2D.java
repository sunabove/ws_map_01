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
	 * ValueAxis에서 GridMarkGroup안에 속하는 GridMark의 개수를 계산하여 리턴한다.
	 *
	 * @param   min : ValueAxis에서의 최소값을 나타낸다.
	 * @param   max : ValueAxis에서의 최대값을 나타낸다.
	 * @param   unit : ValueAxis에서의 주 단위를 나타낸다.
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

		// TickMark에 들어갈 최대값과 최소값 그리고 단위를 재계산한다.
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
	 * 2D의 차트 타입일 경우에 값 축의 눈금 레이블의 위치와 레이블에 들어갈 text를
	 * PointedObject 형식으로 리턴하는 함수이다.
	 *
	 * 스팩)  1. 눈금 레이블의 수는 눈금 또는 눈금선의 수와 동일하다.
	 *       2. 눈금 레이블의 위치는 눈금 또는 눈금선의 위치와 동일하다.
	 *       3. 첫 눈금 레이블(가장 밑에 존재하는 눈금 레이블)의 text는 값축의 최소값을 text로 가진다.
	 *       4. 마지막 눈금 레이블(가장 위에 존재하는 눈금 레이블)의 text는 값축의 최대값을 text로 가진다.
	 *       5. 처음과 마지막 눈금 레이블 사이에 존재하는 레이블의 text는 순차적으로 첫 눈금 레이블의 값에
	 *          값 축의 주 단위 만큼 증가된 값을 text로 가진다.
	 *
	 *
	 * @see     getMajorTickMarkPointsOfValueAxis( ValueAxis);
	 */
	public PointedObject [] getTickLabelPointsOfValueAxis(ValueAxis valueAxis) {


		ValueAxisOption valueAxisOption = ( ValueAxisOption ) valueAxis.getAxisOption();

		double max = valueAxisOption.getMaxScale();
		double min = valueAxisOption.getMinScale();
		double unit = valueAxisOption.getMajorUnit();

		// 눈금의 위치를 가져온다.
	    Point [] refPoints = getTickMarkPointsOfValueAxis( valueAxis, AppRegistry.MAJOR_GRID_LINE_OF_VALUE_AXIS );

		int len = refPoints.length;

		// 스팩 1
		PointedObject [] points = new PointedObject[ refPoints.length ];

		// 첫번째 눈금레이블의 text는 최소값으로 한다.(스팩 3)
		double val = min;

		for( int i = 0; i < len ; i ++ ) {

			// 스팩 5
			val = min + i * unit;

			// 스팩 2
			points[i] = new PointedObject((int)(refPoints[i].x), (int)(refPoints[i].y), new Double( val ) );
		}

		return points;

	}

	/**
	 * 2D의 차트 타입일 경우에 항목 축의 눈금 레이블의 위치와 레이블에 들어갈 text를
	 * PointedObject 형식으로 리턴하는 함수이다.
	 *
	 * 스팩)  1. 눈금 레이블은 ReferenceSeries의 Name을 화면에 나타내는 레이블로
	 *         눈금 레이블 사이에 들어갈 항목 수(tickLableSpacing)로 화면에 나타낼
	 *         레이블의 수를 결정한다.
	 *
	 *       2. tickLabelSpacing이 만약 2 이면 화면에 나타나는 ReferenceSeries의 Name은
	 *         한개 건너서 나타나게 되므로 눈금 레이블의 수는 tickLabelSpacing
	 *         에 의해 다음과 같게 된다.
	 *
	 *         (눈금 레이블의 수) = (RefSeries의 수) / tickLabelSpacing
	 *
	 *       3. 눈금 레이블의 위치는 다른 변수에 의해 변동이 없으므로 직관적으로 쉽게 구
	 *         할 수 있다.
	 *
	 *         (눈금 레이블 사이의 거리) = (항목 축의 너비) / (눈금 레이블의 수)
	 *         (첫 눈금 레이블의 위치의 x 좌표) = (항목 축의 x 좌표) + 1/2 * (눈금 레이블 사이의 거리)
	 *
	 *
	 * @see     getMajorTickMarkPointsOfValueAxis( ValueAxis);
	 */
	public PointedObject [] getTickLabelPointsOfItemAxis(ItemAxis itemAxis) {

		Chart chart = itemAxis.getChart();

		ItemAxisOption itemAxisOption = ( ItemAxisOption ) itemAxis.getAxisOption();

		ChartData cd = chart.getChartData();  // chart data

		String [] refNames = cd.getReferenceNames(); // reference names

		// referenceSeries의 개수
		int numOfRefSeries = refNames.length;

		// 눈금레이블 사이에 들어갈 항목 수
		int tickLabelSpacing = itemAxisOption.getTickLabelSpacing();

		// 스팩 2
		int numOfTickLabel = numOfRefSeries / tickLabelSpacing;

		PointedObject [] points = new PointedObject[ numOfTickLabel ];

		Rectangle area = itemAxis.getArea();

		int ax = area.x, ay = area.y, aw = area.width, ah = area.height;

		// 눈금 레이블 사이의 간격(스팩 3)
		double dx = aw / numOfTickLabel;

		// 첫 MajorTickMark의 위치(스팩 3)
		double x1 = ax + (.5 * dx );
		double y1 = ay + (.5 * ah );
		double x;
		double y = y1;                  //모든 눈금 레이블의 y 좌표는 같다.
		String text;

		for(int i = 0; i < numOfTickLabel; i ++ ) {

			// 첫 눈금 레이블의 위치로 부터 눈금 레이블 사이의 거리만큼 간 x좌표가
			// i번째의 x 좌표가 된다.
			x = x1 + dx * i;

			text = refNames[ tickLabelSpacing * i ];

			// i번째의 majorTickMark 의 위치를 담고 있는 Point
			points[i] = new PointedObject( (int)(x), (int)(y), text );

		}

		return points;

	}


	/**
	 * ValueAxis안의 MajorTickMark과 MajorGridLine들의 시작점의 위치를 리턴한다.
	 * 최대값과 최소값에는 눈금선이 존재하지 않으므로 눈금의 개수가 눈금선의 개수 보다 2개
	 * 크다. 따라서 눈금선의 경우 리턴된 PointedObject의 첫번째와 마지막은 사용되지 않는다.
	 *
	 * 첫번째 PointObject는 최소값의 위치와 값을 나타낸다.
	 * 마지막 PointObject는 최대값의 위치와 값을 나타낸다.
	 *
	 * @see     ValueAxisOption 클래스의 calibrateAxisMinMaxValueAndUnit(int, int)
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

		// GridLineGroup 안에 들어갈 GridLine의 개수를 구한다.
		int divNum = getNumberOfGridMarkOfValueAxis( min, max, unit );

		double dy = ah /( divNum - 1.0 );   // vertical graphical gap

		Point [] points = new Point[ divNum ];

		double x1 = ax, y1= ay + ah;    // 첫 gridLineGroup의 좌표

		double x = x1, y;   // 모든 gridLine의 x좌표는 똑같다.
		int len = points.length; // points num

		for( int i = 0; i < len ; i ++ ) {

			y = y1 - i * dy;

			points[i] = new Point( (int)(x), (int)(y) );
		}

		return points;

	}


//	/**
//	 * ValueAxis안의 MinorTickMark과 MinorGridLine들의 시작점의 위치를 Point Array로
//	 * 리턴한다.( Point에 따라 값이 필요하지 않으므로 PointedObject가 필요하지 않다.)
//	 * 최대값과 최소값에는 눈금선이 존재하지 않으므로 눈금의 개수가 눈금선의 개수(TickLabel의 개수와 동일)
//	 * 보다 2개 크다. 따라서 눈금선의 경우 리턴된 PointedObject의 첫번째와 마지막은 사용되지 않는다.
//	 *
//	 * 첫번째 PointObject는 최소값의 위치와 값을 나타낸다.
//	 * 마지막 PointObject는 최대값의 위치와 값을 나타낸다.
//	 *
//	 * @see     ValueAxisOption의 calibrateAxisMinMaxValueAndUnit(int, int)
//	 * @see     createMajorGridLineGroupOfValueAxis(PictureExtent)
//	 * @see
//	 */
//	public Point [] getMinorTickMarkPointsOfValueAxis(ValueAxis valueAxis) {
//
//		Chart chart = valueAxis.getChart();
//		ValueAxisOption valueAxisOption = ( ValueAxisOption ) valueAxis.getAxisOption();
//
//		// 재계산하여 최대값, 최소값, 주 단위, 보조 단위를 얻어온다.
//		double max = valueAxisOption.getMaxScale();
//		double min = valueAxisOption.getMinScale();
//		double majorUnit = valueAxisOption.getMajorUnit();
//		double minorUnit = valueAxisOption.getMinorUnit();
//
//		Rectangle area = valueAxis.getArea(); // area rectangle
//
//		int ax = area.x, ay = area.y, ah = area.height;
//
//		// 보조 눈금선의 개수는 주 눈금선의 개수에 영향을 받는다. 만약 주눈금과 보조눈금
//		// 같은 위치에 있게 되면 주 눈금선만 생성이 된다. 보조 눈금의 개수는 따라서 주
//		// 눈금선과 보조눈금선의 unit의 최소 공배수를 Unit으로 이루는 눈금의 개수만큼
//		// 보조눈금선의 개수가 줄어 들게 된다.
//
//		// 주 눈금과 겹쳐지는걸 무시 했을 때의 눈금의 개수
//		int numOfOriginalMinorTickMark = getNumberOfGridMarkOfValueAxis( min, max, minorUnit );
//
//		// 주 눈금과 보조 눈금의 최소 공배수(LCM)가 Unit 일때의 눈금의 개수
//		/**@todo: 현재 주눈금과 보조눈금이 정수일 때만 LCM을 구할 수 있게 되어 있다.*/
//		int lcmUnit = Utility.lcm( (int) majorUnit, (int) minorUnit);
//		int numOfLCMMinorTickMark = getNumberOfGridMarkOfValueAxis( min, max, lcmUnit + 0.0 );
//
//		// 실제 보조 눈금의 개수
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
//			// 보조 눈금이 주 눈금과 겹칠 때에는 스킵한다.
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
	 * ItemAxis안의 MajorTickMark과 MajorGridLine들의 시작점의 위치를 리턴된다.
	 *
	 * @see     createMajorGridLineGroupOfItemAxis(PictureExtent)
	 */
	public Point [] getTickMarkPointsOfItemAxis(ItemAxis itemAxis, byte tickMarkType) {

		Chart chart = itemAxis.getChart();

		ItemAxisOption itemAxisOption = ( ItemAxisOption ) itemAxis.getAxisOption();

		ChartData cd = chart.getChartData();  // chart data

		String [] refNames = cd.getReferenceNames(); // reference names

		// referenceSeries의 개수
		int numOfRefSeries = refNames.length;

		// 눈금 사이에 들어갈 항목 수
		int tickMarkSpacing = itemAxisOption.getTickMarkSpacing();

		Rectangle area = itemAxis.getArea();

		int ax = area.x, ay = area.y, aw = area.width, ah = area.height;

		int numOfTickMarkPointsOfItemAxis;
		double dx;

		if ( tickMarkType == AppRegistry.MAJOR_GRID_LINE_OF_ITEM_AXIS ) {

			// MajorTickMark의 개수
			numOfTickMarkPointsOfItemAxis = ( numOfRefSeries / tickMarkSpacing ) + 1;


		} else {
		    // MinorTickMark의 개수
			numOfTickMarkPointsOfItemAxis = 2 * ( numOfRefSeries / tickMarkSpacing ) + 1;
			numOfRefSeries = 2 * numOfRefSeries;
		}

		Point [] points = new Point[ numOfTickMarkPointsOfItemAxis ];

		// 첫 TickMark의 위치.
		double x = ax , y = area.y + ah;

		for(int i = 0; i < numOfTickMarkPointsOfItemAxis; i ++ ) {

			// 첫 TickMark의 위치로 부터 ReferenceSeries의 간격만큼 띄어져 위치한다.
			// 여기서 tickMarkSpacing의 값에 의해 두개, 세개 등의 ReferenceSeires 만큼 떨어져
			// TickMark 가 존재할 수 있으므로 dx * tickMarkSpacing을 해야한다.
			x = ax + ( aw * tickMarkSpacing * i ) / numOfRefSeries;

			// i번째의 majorTickMark 의 위치를 담고 있는 Point
			points[i] = new Point ((int)(x), (int)(y));

	    }

		return points;

	}

//	/**
//	 * ItemAxis안의 MinorTickMark과 MinorGridLine들의 시작점의 위치를 리턴된다.
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
//		// referenceSeries의 개수
//		int numOfRefSeries = refNames.length;
//
//		// 눈금 사이에 들어갈 항목 수
//		int tickMarkSpacing = itemAxisOption.getTickMarkSpacing();
//
//		// 눈금 사이에 들어갈 항목수를 tickMarkSpacing으로 맞추기 위해선 MinorTickMark
//		// 의 개수는 다음과 같아야 한다.
//		// 이유는 다음과 같다. 먼저 MajorTickMark 사이에 하나의 MinorTickMark가 존재하
//		// 므로 MajorTickMark의 위치에 MinorTickMark가 존재한다고 생각하면 minorTickMark의
//		// 간격이 major의 간격의 반이 되므로..
//		// numOfMinorTickMarkPointOfItemAxis = ( numOfRefSeries / tickMakrSpacing ) * 2 + 1
//		// 여기에서 major와 minor가 겹쳐진 곳의 minor의 개수( = major의 개수)를 빼면
//		// 다음과 같아지게 된다.
//		int numOfMinorTickMarkPointsOfItemAxis = ( numOfRefSeries / tickMarkSpacing );
//
//		Point [] points = new Point [ numOfMinorTickMarkPointsOfItemAxis ];
//
//		Rectangle area = itemAxis.getArea();
//
//		int ax = area.x, ay = area.y, aw = area.width, ah = area.height;
//
//		// ReferenceSeries의 간격
//		double dx = aw / numOfRefSeries ;
//
//		// 첫 MinorTickMark의 위치.
//		double x = ax + dx / 2 , y = area.y + ah;
//
//		for(int i = 0; i < numOfMinorTickMarkPointsOfItemAxis; i ++ ) {
//
//			// 첫 MinorTickMark의 위치로 부터 ReferenceSeries의 간격만큼 띄어져 위치한다.
//			// 여기서 tickMarkSpacing의 값에 의해 두개, 세개 등의 ReferenceSeires 만큼 떨어져
//			// MajorTickMark 가 존재할 수 있으므로 dx * tickMarkSpacing을 해야한다.
//			x = ax + dx * tickMarkSpacing * i;
//
//			// i번째의 majorTickMark 의 위치를 담고 있는 Point
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



	// 항목 축에 해당하는 눈금을 tickMark에 따라 pictureExtent에 생성하는 함수
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

		// 눈금이 안쪽일 때
		case AppRegistry.TICK_MARK_INSIDE :
			y0 = area.y;
			y1 = y0 - distance;
			break;

		// 눈금이 바깥쪽일 때
		case AppRegistry.TICK_MARK_OUTSIDE :
		    y0 = area.y;
			y1 = y0 + distance;
			break;

		// 눈금이 교차할 때
		case AppRegistry.TICK_MARK_CROSS :
		    y0 = area.y - distance;
			y1 = y0 + 2 * distance;
			break;
		// 눈금이 없을 때( 이상황은 오지 않는다... 왜냐면 눈금이 없으면 이 함수가 호출 되지 않는다)
		case AppRegistry.TICK_MARK_NONE :

		    return;

		}


		// TickMark의 Style은 Area의 Style과 동일하다
		ShapeStyle tickMarkStyle = axis.getShapeStyle();


		for(int i = 0, len = points.length; i < len; i ++ ) {

			x = points[i].x;

			TickMark tm = new TickMark( sheet, tmg, x, y0, x, y1, tickMarkStyle );

			tmg.addChild( tm );

		}

		itemAxis.addChild( tmg );



	}

	/**
	 * 2D에서 값 축에 해당하는 눈금을 tickMark에 따라 pictureExtent에 생성하는 함수
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

		// 눈금이 안쪽일 때
		case AppRegistry.TICK_MARK_INSIDE :
			x0 = area.x + area.width;
			x1 = x0 + distance;
			break;

		// 눈금이 바깥쪽일 때
		case AppRegistry.TICK_MARK_OUTSIDE :
		    x0 = area.x + area.width;
			x1 = x0 - distance;
			break;

		// 눈금이 교차할 때
		case AppRegistry.TICK_MARK_CROSS :
		    x0 = area.x + area.width - distance;
			x1 = x0 + 2 * distance;
			break;
		// 눈금이 없을 때( 이상황은 오지 않는다... 왜냐면 눈금이 없으면 이 함수가 호출 되지 않는다)
		case AppRegistry.TICK_MARK_NONE :

		    return;

		}


		// TickMark의 Style은 Area의 Style과 동일하다
		ShapeStyle tickMarkStyle = axis.getShapeStyle();


		for(int i = 0, len = points.length; i < len; i ++ ) {

			y = points[i].y;

			TickMark tm = new TickMark( sheet, tmg, x0, y, x1, y, tickMarkStyle );

			tmg.addChild( tm );

		}

		valueAxis.addChild( tmg );


	}



	/**
	 * 해당 PictureExtent에 ValueAxis에 속하는 MajorGridLine을 만드는 함수
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


		// 리턴된 getMajorTickMarkPoints에서 첫번째 Point는 최소값 나타내는
		// TickMark에 사용되는 항이므로 GridLine에는 필요없는 Point이다.
		for(int i = 1, len = points.length; i < len; i ++ ) {

			int y = points[i].y;

			GridLine gl = new GridLine( sheet, glg, x0, y, x1, y, glStyle );

			glg.addChild( gl );

		}

		pictureExtent.addChild( glg );

	}

	/**
	 * 해당 PictureExtent에 ValueAxis에 속하는 MinorGridLine을 만드는 함수
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
	 * 해당 PictureExtent에 ItemAxis에 속하는 MajorGridLine을 만드는 함수
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
	 * 해당 PictureExtent에 ItemAxis에 속하는 MinorGridLine을 만드는 함수
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