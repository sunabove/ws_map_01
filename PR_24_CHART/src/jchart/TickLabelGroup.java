package jchart;

/**
 * Title:           TickLabelGroup 클래스
 * Company:         Techdigm Corp.
 * @author          withhim
 * @version 1.0
 * @description     ChartElement 중 눈금에 해당하는 클래스
 *
 */

import java.awt.*;

public class TickLabelGroup extends TextualChartElement implements UnSelectable {

    public TickLabelGroup(Font font, SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style) {

		super(sheet, parent, shape, style, font, null );

		Axis axis = ( Axis ) parent;

		PointedObject [] coords = axis.getTickLabelPoints();

		Chart chart = getChart(); // chart

        ChartType chartType = chart.getChartType();

		String unit = chartType.getValueAxisUnit();

		boolean horizontalAlign = ( ( ( chartType instanceof HorizontalChartType ) && ( axis instanceof ValueAxis ) )
									|| ( axis instanceof ItemAxis ) )
									? false : true;

		for(int i = 0, len = coords.length; i < len; i ++ ) {

            PointedObject coord = coords[ i ];

			String text = ( axis instanceof ValueAxis ) ? coord.getInteger() + unit : coord.getString();

            TickLabel tl = new TickLabel( text, font, sheet, this, coord.x, coord.y );

            if( horizontalAlign ) {
                tl.alignTo( parent, Align.HOR_CENTER );  // align to center
            }

            addChild( tl );

        }

	}

	public void setOrientation( int orientation) {

		super.setOrientation( orientation );

		ChartElement [] ce = getChilds();

		for(int i = 0; i < ce.length; i++ ) {

			if( ce[i] instanceof TickLabel == false ) {
			    continue;
			}

			TickLabel tl = ( TickLabel ) ce[i];
		    tl.setOrientation( orientation );

		}

	}

}