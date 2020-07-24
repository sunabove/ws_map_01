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

public class ChartTypeVerticalSimplePyramid3D extends ChartTypeVerticalBar
       implements ChartType3D, ZAxisChartType3D {

        public Shape [][] createShapes3DGroup(float tx, float ty, Shape shape, float proX, float proY) {

		return ThreeDimUtilities.get3DVerticalSimplePyramidShapesGroup( tx, ty, shape.getBounds2D(), proX, proY );

	}

}