package jchart;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;

public class ChartTypeHorizontalCylinderPercentedAccumulated3D extends ChartTypeHorizontalBarPercentedAccumulated implements ChartType3D {

	public Shape [][] createShapes3DGroup(float tx, float ty, Shape shape, float proX, float proY) {

		return ThreeDimUtilities.get3DHorizontalCylindricShapesGroup( tx, ty, shape.getBounds2D(), proX, proY );

	}

}