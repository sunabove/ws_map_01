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

		// tickLabelGroup ����
		TickLabelGroup tlg = new TickLabelGroup( font, sheet, this, shape, style );

		addChild( tlg );

		// tickMarkGroup ���� -- Axis�� �⺻������ ������ �� ������ ������ �ִ�. ( AxisOption ���� )
		Chart chart = this.getChart();
		ChartType chartType = chart.getChartType();

		if ( this instanceof ValueAxis ) {

			chartType.createTickMarkGroupOfValueAxis( this, AppRegistry.MAJOR_TYPE, AppRegistry.TICK_MARK_CROSS  );

		} else {

   			chartType.createTickMarkGroupOfItemAxis( this, AppRegistry.MAJOR_TYPE, AppRegistry.TICK_MARK_CROSS );
		}


    }


	// �࿡ �� ���� ���̺��� ��ġ�� text�� ������ �����´�.
	public abstract PointedObject [] getTickLabelPoints();

	// �࿡ �� �� ���ݰ� �� ���ݼ� ���� ��ġ�� �����´�.
    public abstract Point [] getMajorTickMarkPoints();

	// �࿡ �� ���� ���ݰ� ���� ���ݼ� ���� ��ġ�� �����´�.
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

	    // ���� ���� ���� ��� �ƹ��͵� �����ְ� ������ �ȴ�.
		if ( axisOption.getTickMark( tickMarkType ) == tickMark ) {
		    return;
		}

		ChartElement [] ce = this.getChilds();

		if ( tickMark == AppRegistry.TICK_MARK_NONE ) {     // �ִ� �� ���ݼ��� ���ִ� ���

			for ( int i = 0; i < ce.length; i++ ) {
			    if ( ce[i] instanceof TickMarkGroup ) {
				    TickMarkGroup tmg = (TickMarkGroup) ce[i];
					byte refTickMarkType = tmg.getTickMarkType();
				    if( refTickMarkType == tickMarkType ) {

						this.removeChild( ce[i] );
				    }
				}
			}


		} else {    // �� ���ݼ��� ���� �����ؾ� �ϴ� ��� ��Ʈ Ÿ�Կ��� ������Ų��.

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

	    // �Ȱ��� hasMajorGridLineGroup�� ��� �ƹ��͵� �����ְ� ������ �ȴ�.
		if ( axisOption.getHasMajorGridLineGroup() == hasMajorGridLineGroup ) {
		    return;
		}

	    GraphExtent ge = (GraphExtent) this.getParent();
		PictureExtent pe = (PictureExtent) ge.getChartElement( PictureExtent.class );
		ChartElement [] ce = pe.getChilds();

		if ( hasMajorGridLineGroup == false ) {     // �ִ� �� ���ݼ��� ���ִ� ���
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


		} else {    // �� ���ݼ��� ���� �����ؾ� �ϴ� ��� ��Ʈ Ÿ�Կ��� ������Ų��.

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

	    // �Ȱ��� hasMinorGridLineGroup�� ��� �ƹ��͵� �����ְ� ������ �ȴ�.
		if ( axisOption.getHasMinorGridLineGroup() == hasMinorGridLineGroup ) {
		    return;
		}

	    GraphExtent ge = (GraphExtent) this.getParent();
		PictureExtent pe = (PictureExtent) ge.getChartElement( PictureExtent.class );
		ChartElement [] ce = pe.getChilds();

		if ( hasMinorGridLineGroup == false ) {     // �ִ� �� ���ݼ��� ���ִ� ���
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


		} else {    // �� ���ݼ��� ���� �����ؾ� �ϴ� ��� ��Ʈ Ÿ�Կ��� ������Ų��.

			if( this instanceof ValueAxis) {

			    this.getChartType().createMinorGridLineGroupOfValueAxis(pe);

			} else {

				this.getChartType().createMinorGridLineGroupOfItemAxis(pe);

			}

	    }

		axisOption.hasMinorGridLineGroup = hasMinorGridLineGroup;

	}



}