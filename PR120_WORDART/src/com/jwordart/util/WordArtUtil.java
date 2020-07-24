/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.util;

import java.awt.*;

public class WordArtUtil {

	public static double getAngle(double x, double y) {

		double theta = Math.acos(x / Math.sqrt(x * x + y * y));
		if (x < 0 && y < 0) { // 3/4 분면
			theta = 2 * Math.PI - theta;
		} else if (x > 0 && y < 0) { // 4/4 분면
			theta = 2 * Math.PI - theta;
		}
		return theta;

	}

}