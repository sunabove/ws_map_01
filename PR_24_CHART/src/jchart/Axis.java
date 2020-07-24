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
import java.awt.font.*;
import jcosmos.FontManager;

public abstract class Axis extends ChartElement implements UnMovable{

    public Axis (SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style) {
//      super(sheet, parent, shape, style, font );
	    super(sheet, parent, shape, style);

		Font font = FontManager.getDefaultFont();

		// tickLabelGroup 생성
		TickLabelGroup tlg = new TickLabelGroup( font, sheet, this, shape, style );

		addChild( tlg );

		// tickMarkGroup 생성 -- Axis는 기본적으로 생성시 주 눈금을 가지고 있다. ( AxisOption 참조 )
		Chart chart = this.getChart();
		ChartType chartType = chart.getChartType();

		if ( this instanceof ValueAxis ) {

			chartType.createTickMarkGroupOfValueAxis( this, AppRegistry.MAJOR_TYPE, AppRegistry.TICK_MARK_CROSS  );

		} else {

   			chartType.createTickMarkGroupOfItemAxis( this, AppRegistry.MAJOR_TYPE, AppRegistry.TICK_MARK_CROSS );
		}


    }


	// 축에 들어갈 눈금 레이블의 위치와 text의 내용을 가져온다.
	public abstract PointedObject [] getTickLabelPoints();

	// 축에 들어갈 주 눈금과 주 눈금선 들의 위치를 가져온다.
    public abstract Point [] getMajorTickMarkPoints();

	// 축에 들어갈 보조 눈금과 보조 눈금선 들의 위치를 가져온다.
	public abstract Point [] getMinorTickMarkPoints();


//	public boolean getHasMajorGridLine() {
//
//		GraphExtent ge = (GraphExtent) this.getParent();
//		PictureExtent pe = (PictureExtent) ge.getChartElement( PictureExtent.class );
//		ChartElement [] ce = pe.getChilds();
//		if ( this instanceof ValueAxis ) {
//			for ( int i = 0; i < ce.length; i++ ) {
//				if ( ce[i] instanceof GridLineGroup ) {
//				    GridLineGroup glg = (GridLineGroup) ce[i];
//					if( glg.getGridLineType() == GridLineGroup.MAJOR_HORIZONTAL_GRID_LINE ) {
//						this.hasMajorGridLines = true;
//					}
//
//				}
//
//			}
//		    this.hasMajorGridLines = false;
//
//		}else {
//   			for ( int i = 0; i < ce.length; i++ ) {
//				if ( ce[i] instanceof GridLineGroup ) {
//				    GridLineGroup glg = (GridLineGroup) ce[i];
//					if( glg.getGridLineType() == GridLineGroup.MAJOR_VERTICAL_GRID_LINE ) {
//						this.hasMajorGridLines = true;
//					}
//
//				}
//
//			}
//		    this.hasMajorGridLines = false;
//
//		}
//
//		return this.hasMajorGridLines;
//
//	}

//	public boolean getHasMinorGridLine() {
//
//		GraphExtent ge = (GraphExtent) this.getParent();
//		PictureExtent pe = (PictureExtent) ge.getChartElement( PictureExtent.class );
//		ChartElement [] ce = pe.getChilds();
//		if ( this instanceof ValueAxis ) {
//			for ( int i = 0; i < ce.length; i++ ) {
//				if ( ce[i] instanceof GridLineGroup ) {
//				    GridLineGroup glg = (GridLineGroup) ce[i];
//					if( glg.getGridLineType() == GridLineGroup.MAJOR_HORIZONTAL_GRID_LINE ) {
//						this.hasMinorGridLines = true;
//					}
//
//				}
//
//			}
//		    this.hasMinorGridLines = false;
//
//		}else {
// 			for ( int i = 0; i < ce.length; i++ ) {
//				if ( ce[i] instanceof GridLineGroup ) {
//				    GridLineGroup glg = (GridLineGroup) ce[i];
//					if( glg.getGridLineType() == GridLineGroup.MAJOR_VERTICAL_GRID_LINE ) {
//						this.hasMinorGridLines = true;
//					}
//
//				}
//
//			}
//		    this.hasMinorGridLines = false;
//
//		}
//
//		return this.hasMinorGridLines;
//
//	}

	public AxisOption getAxisOption() {

		return this.getChart().getAxisOption(this);

	}


	public boolean getHasMajorGridLineGroup() {

	    return getAxisOption().getHasMajorGridLineGroup();

	}

	public boolean getHasMinorGridLineGroup() {

	    return getAxisOption().getHasMinorGridLineGroup();

	}

	public byte getTickLabelPosition() {

		return getAxisOption().getTickLabelPosition();

	}

	public void setTickLabelPosition(byte tickLabelPosition) {

		getAxisOption().setTickLabelPosition( tickLabelPosition );

	}

	public byte getTickMark( byte tickMarkType ) {

		return getAxisOption().getTickMark( tickMarkType );

	}

	public void setTickMark(byte tickMarkType, byte tickMark) {

		AxisOption axisOption = getAxisOption();

	    // 현재 값과 같은 경우 아무것도 안해주고 나가도 된다.
		if ( axisOption.getTickMark( tickMarkType ) == tickMark ) {
		    return;
		}

		ChartElement [] ce = this.getChilds();

		if ( tickMark == AppRegistry.TICK_MARK_NONE ) {     // 있던 주 눈금선을 없애는 경우

			for ( int i = 0; i < ce.length; i++ ) {
			    if ( ce[i] instanceof TickMarkGroup ) {
				    TickMarkGroup tmg = (TickMarkGroup) ce[i];
					byte refTickMarkType = tmg.getTickMarkType();
				    if( refTickMarkType == tickMarkType ) {

						this.removeChild( ce[i] );
				    }
				}
			}


		} else {    // 주 눈금선을 새로 생성해야 하는 경우 차트 타입에서 생성시킨다.

			if( this instanceof ValueAxis) {

			    this.getChartType().createTickMarkGroupOfValueAxis(this, tickMarkType, tickMark);

			} else {

				this.getChartType().createTickMarkGroupOfItemAxis(this, tickMark, tickMark);

			}

	    }

		axisOption.setTickMark(tickMarkType, tickMark);


	}

	public void setHasMajorGridLineGroup(boolean hasMajorGridLineGroup) {

		AxisOption axisOption = getAxisOption();

	    // 똑같은 hasMajorGridLineGroup인 경우 아무것도 안해주고 나가도 된다.
		if ( axisOption.getHasMajorGridLineGroup() == hasMajorGridLineGroup ) {
		    return;
		}

	    GraphExtent ge = (GraphExtent) this.getParent();
		PictureExtent pe = (PictureExtent) ge.getChartElement( PictureExtent.class );
		ChartElement [] ce = pe.getChilds();

		if ( hasMajorGridLineGroup == false ) {     // 있던 주 눈금선을 없애는 경우
			for ( int i = 0; i < ce.length; i++ ) {
			    if ( ce[i] instanceof GridLineGroup ) {
				    GridLineGroup glg = (GridLineGroup) ce[i];
					byte gridLineType = glg.getGridLineType();
				    if( ( ( gridLineType == AppRegistry.MAJOR_GRID_LINE_OF_VALUE_AXIS ) && ( this instanceof ValueAxis ) ) ||
						( ( gridLineType == AppRegistry.MAJOR_GRID_LINE_OF_ITEM_AXIS ) && ( this instanceof ItemAxis ) ) ) {

						pe.removeChild( ce[i] );

					}
				}
			}


		} else {    // 주 눈금선을 새로 생성해야 하는 경우 차트 타입에서 생성시킨다.

			if( this instanceof ValueAxis) {

			    this.getChartType().createMajorGridLineGroupOfValueAxis(pe);

			} else {

				this.getChartType().createMajorGridLineGroupOfItemAxis(pe);

			}

	    }

		axisOption.hasMajorGridLineGroup = hasMajorGridLineGroup;

	}

	public void setHasMinorGridLineGroup(boolean hasMinorGridLineGroup) {

		AxisOption axisOption = getAxisOption();

	    // 똑같은 hasMinorGridLineGroup인 경우 아무것도 안해주고 나가도 된다.
		if ( axisOption.getHasMinorGridLineGroup() == hasMinorGridLineGroup ) {
		    return;
		}

	    GraphExtent ge = (GraphExtent) this.getParent();
		PictureExtent pe = (PictureExtent) ge.getChartElement( PictureExtent.class );
		ChartElement [] ce = pe.getChilds();

		if ( hasMinorGridLineGroup == false ) {     // 있던 주 눈금선을 없애는 경우
			for ( int i = 0; i < ce.length; i++ ) {
			    if ( ce[i] instanceof GridLineGroup ) {
				    GridLineGroup glg = (GridLineGroup) ce[i];
					byte gridLineType = glg.getGridLineType();
				    if( ( ( gridLineType == AppRegistry.MINOR_GRID_LINE_OF_VALUE_AXIS ) && ( this instanceof ValueAxis ) ) ||
						( ( gridLineType == AppRegistry.MINOR_GRID_LINE_OF_ITEM_AXIS ) && ( this instanceof ItemAxis ) ) ) {

						pe.removeChild( ce[i] );

					}
				}
			}


		} else {    // 주 눈금선을 새로 생성해야 하는 경우 차트 타입에서 생성시킨다.

			if( this instanceof ValueAxis) {

			    this.getChartType().createMinorGridLineGroupOfValueAxis(pe);

			} else {

				this.getChartType().createMinorGridLineGroupOfItemAxis(pe);

			}

	    }

		axisOption.hasMinorGridLineGroup = hasMinorGridLineGroup;

	}



}