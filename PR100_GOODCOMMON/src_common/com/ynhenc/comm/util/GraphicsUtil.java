package com.ynhenc.comm.util;

import java.awt.*;

public class GraphicsUtil {

	public static void setHighQuality(final Graphics2D g2) {

		RenderingHints hints = GraphicsUtil.getRenderingHints_High();
		g2.setRenderingHints(hints);

	}

	private static RenderingHints hintsHigh, hintsNormal;

	public static RenderingHints getRenderingHints_High() {

		if( hintsHigh  != null ) {
			return hintsHigh;
		}

		RenderingHints hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (true) {
			hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		}

		if (true) {
			hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		}

		if (true) {
			hints.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		}

		if (true) {
			hints.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

		}

		if (true) {
			hints.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		}

		if (true) {
			hints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		}

		if (false) {
			hints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		}

		if (false) {
			hints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

		}

		hintsHigh = hints;

		return hints;
	}

	public static RenderingHints getRenderingHints_Normal() {

		if( hintsNormal != null ) {
			return hintsNormal ;
		}

		RenderingHints hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

		if (true) {
			hints.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		}

		hintsNormal = hints;

		return hints;

	}

}
