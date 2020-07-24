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

public class ChartTypeCircleDivided3D extends ChartTypeCircleDivided implements ChartType3D {

	public Shape [][] createShapes3DGroup(float tx, float ty, Shape shape, float proX, float proY, float top, float bottom ) {

		return ThreeDimUtilities.get3DPieShapesGroup( tx, ty, shape, proX, proY, top, bottom );

	}

}