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

public class ChartTypeCircle3D extends ChartTypeCircle implements ChartType3D {

	public Shape [][] createShapes3DGroup(float tx, float ty, Shape shape, float rotRadian, float thickness, float top, float bottom ) {

		return ThreeDimUtilities.get3DPieShapesGroup( tx, ty, shape, rotRadian, thickness, top, bottom );

	}

}