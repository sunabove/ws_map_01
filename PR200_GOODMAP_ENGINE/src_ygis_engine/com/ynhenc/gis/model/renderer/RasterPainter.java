package com.ynhenc.gis.model.renderer;

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;

import com.ynhenc.comm.util.*;
import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.model.style.*;

public class RasterPainter extends Painter {

	public RasterPainter() {
		super();
		this.observer = new Frame();
		this.observer.addNotify();
	}

	public void paintBgRasterLyr(Projection proj, Graphics2D g2 ) {

		String rasterName = "BG_IMG_NAME";
		BufferedImage image = this.getRealPathImage(rasterName);

		Rectangle2D rect = proj.getPixelRect();

		Component observer = this.observer;

		int iw = image.getWidth(observer);
		int ih = image.getHeight(observer);

		double rx0 = 0;
		double ry0 = 18000;
		double rx1 = 22000;
		double ry1 = 0;
		double rw = rx1 - rx0;
		double rh = ry0 - ry1;

		PntShort p = proj.toGraphics(rx0, ry0);
		PntShort q = proj.toGraphics(rx1, ry1);

		double w = q.getX() - p.getX();
		double h = q.getY() - p.getY();

		double f = 4;
		if (w < rect.getWidth() * f && h < rect.getHeight() * f) {
			if ( true ) {
				this.debug("DRAWING RASTER IMAGE AFTER IMAGE PROCESSING ......");

				AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
				at.scale(w / iw, h / ih);
				// at.translate( p.x, p.y );

				RenderingHints hints = GraphicsUtil.getRenderingHints_High();

				BufferedImageOp op = new AffineTransformOp(at, hints);
				g2.drawImage(image, op, (int) p.getX(), (int) p.getY());

			} else {
				g2.drawImage(image, (int) p.getX(), (int) p.getY(), (int) w, (int) h,
						observer);
			}
		}

	}

	public Image cropImage(Image image, int x, int y, int w, int h) {
		this.loadImageUsingMediaTracker(new Image[] { image });
		CropImageFilter tCropImageFilter = new CropImageFilter(x, y, w, h);
		Image value = Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(image.getSource(), tCropImageFilter));
		this.loadImageUsingMediaTracker(new Image[] { value });
		return value;
	}

	public void loadImageUsingMediaTracker(Image[] images) {
		try {
			// debug("Loading image ....");
			MediaTracker tMediaTracker = new MediaTracker(this.observer);
			int tImageId = 0;
			for (int i = 0; i < images.length; i++) {
				if (images[i] != null) {
					tMediaTracker.addImage(images[i], tImageId);
					tImageId++;
				} else {
					// debug("Null Image. Skipped from image loading.");
				}
			}
			tMediaTracker.waitForAll();
			// debug("Done loading image well.");
		} catch (InterruptedException pE) {
			pE.printStackTrace();
		}
	}

	private Component observer;

}
