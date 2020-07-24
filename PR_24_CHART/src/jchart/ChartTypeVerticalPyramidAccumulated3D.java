package jchart;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

import java.awt.*;

public class ChartTypeVerticalPyramidAccumulated3D extends ChartTypeVerticalBarAccumulated
	implements ChartType3D, Proportional3DChartType {

	public Shape [][] createShapes3DGroup(float tx, float ty, Shape shape, float proX, float proY,
	        float top, float bottom ) {

		return ThreeDimUtilities.get3DVerticalProportionalPyramidShapesGroup( tx, ty, shape.getBounds2D(), proX, proY, top, bottom );

	}

}