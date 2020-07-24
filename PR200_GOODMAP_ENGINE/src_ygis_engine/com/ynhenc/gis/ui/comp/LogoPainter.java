package com.ynhenc.gis.ui.comp;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.*;
import com.ynhenc.gis.ui.resource.*;

public class LogoPainter extends GisComLib {

	public void paint(Component comp, final long duration) {

		Graphics2D g = (Graphics2D) comp.getGraphics();

		Dimension size = comp.getSize();

		Insets insets = new Insets(1, 1, 1, 1);
		;

		// 이미지 영역 설정

		int iw = size.width - insets.left - insets.right;
		int ih = size.height - insets.top - insets.bottom;
		int ix = insets.left;
		int iy = insets.top;

		// 끝. 이미지 영역 설정

		// 로딩 로고 경계선 그리기

		Area borderShape = new Area(new Rectangle(0, 0, size.width, size.height));
		borderShape.subtract(new Area(new Rectangle(ix, iy, iw, ih)));

		g.setColor(Color.black);

		g.fill(borderShape);

		// 끝. 로딩 로고 경계선 그리기

		// 로딩 로고 이미지 그리기

		if (true) {

			IconImage loadingImage = this.loadingImage;

			if (loadingImage != null) {

				BufferedImage bImage = loadingImage.getBfrImage();

				if (bImage != null) {

					g.drawImage(bImage, ix, iy, iw, ih, comp);

				}

			}

		}

		// 끝. 로딩 로고 이미지 그리기.

		// 잠시 동안 로딩 로고 만을 보여 준다. (고객의 여유을 위해서)
		if (duration > 0) {
			try {
				Thread.currentThread().sleep(duration);
			} catch (InterruptedException e) {
				this.debug(e);
			}
		}
		// 끝. 로딩 로고 출력 유지 하기.

	}

	public IconImage_Resource loadingImage = IconImageFactory.getIconImage_Resource("gen", "loading.jpg");

}
