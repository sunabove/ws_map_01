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

		// �̹��� ���� ����

		int iw = size.width - insets.left - insets.right;
		int ih = size.height - insets.top - insets.bottom;
		int ix = insets.left;
		int iy = insets.top;

		// ��. �̹��� ���� ����

		// �ε� �ΰ� ��輱 �׸���

		Area borderShape = new Area(new Rectangle(0, 0, size.width, size.height));
		borderShape.subtract(new Area(new Rectangle(ix, iy, iw, ih)));

		g.setColor(Color.black);

		g.fill(borderShape);

		// ��. �ε� �ΰ� ��輱 �׸���

		// �ε� �ΰ� �̹��� �׸���

		if (true) {

			IconImage loadingImage = this.loadingImage;

			if (loadingImage != null) {

				BufferedImage bImage = loadingImage.getBfrImage();

				if (bImage != null) {

					g.drawImage(bImage, ix, iy, iw, ih, comp);

				}

			}

		}

		// ��. �ε� �ΰ� �̹��� �׸���.

		// ��� ���� �ε� �ΰ� ���� ���� �ش�. (���� ������ ���ؼ�)
		if (duration > 0) {
			try {
				Thread.currentThread().sleep(duration);
			} catch (InterruptedException e) {
				this.debug(e);
			}
		}
		// ��. �ε� �ΰ� ��� ���� �ϱ�.

	}

	public IconImage_Resource loadingImage = IconImageFactory.getIconImage_Resource("gen", "loading.jpg");

}
