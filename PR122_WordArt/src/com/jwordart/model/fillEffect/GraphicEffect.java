package com.jwordart.model.fillEffect;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;

import javax.swing.ImageIcon;

import com.jwordart.ui.resource.Resource_WordArt;
import com.jwordart.util.WordArtUtil;
//import com.ynhenc.comm.GisComLib;

public class GraphicEffect {

	public GraphicEffect create() {

		GraphicEffect value = new GraphicEffect();

		if (bounds != null) {
			value.bounds = new Rectangle2D.Double(bounds.getX(), bounds.getY(),
					bounds.getWidth(), bounds.getHeight());
		}

		value.halfTransparent = this.halfTransparent;

		value.firstGradientColor = this.firstGradientColor;

		value.secondGradientColor = this.secondGradientColor;

		value.cyclic = this.cyclic;
		value.symmetric = this.symmetric;
		value.type = this.type;
		value.direction = this.direction;
		value.bfrImage = this.bfrImage;

		return value;
	}

	public void setSymmetric(boolean b) {
		this.symmetric = b;
	}

	public void setBounds(Rectangle2D bounds) {
		this.bounds = bounds;
	}

	public void setDirection(int dir) {
		this.direction = dir;
	}

	public int getDirection() {
		return this.direction;
	}

	public void setBfrImage(BufferedImage bfrImage) {
		this.bfrImage = bfrImage;
		this.firstGradientColor = null;
		this.secondGradientColor = null;
		this.halfTransparent = false;
	}

	public void setBounds(double w, double h) {
		if (this.bounds == null) {
			this.bounds = new Rectangle2D.Double(0, 0, w, h);
		} else {
			this.bounds.setRect(0, 0, w, h);
		}
	}

	public void setFirstGradientColor(Color c) {
		this.firstGradientColor = c;
	}

	public void setSecondGradientColor(Color c) {
		this.secondGradientColor = c;
	}

	public void setCyclic(boolean b) {
		this.cyclic = b;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getType() {
		return this.type;
	}

	public Rectangle2D getBounds() {
		return this.bounds;
	}

	public Point2D getFromPoint() {
		if (bounds == null) {
			return null;
		}

		double w = bounds.getWidth(), h = bounds.getHeight(), x = 0, y = 0;

		switch (type) {
		case HORIZONTAL:
			x = w / 2;
			y = 0;
			if (symmetric) {
				x = w / 2;
				y = h / 2;
			}
			break;
		case VERTICAL:
			x = 0;
			y = h / 2;
			if (symmetric) {
				x = w / 2;
				y = h / 2;
			}
			break;
		case RIGHT_DIAGONAL:
			x = 0;
			y = 0;
			if (symmetric) {
				x = w / 2;
				y = h / 2;
			}
			break;
		case LEFT_DIAGONAL:
			x = 0;
			y = h;
			if (symmetric) {
				x = w / 2;
				y = h / 2;
			}
			break;
		case FROM_CORNER:
			x = 0;
			y = 0;
			if (direction == UP) {
				x = 0;
				y = h;
			}
			break;
		case FROM_CENTER:
			x = w / 2;
			y = h / 2;
			break;
		case ROUND:
			x = w / 2;
			y = h / 2;
			break;
		}

		return new Point2D.Double(x, y);
	}

	public Point2D getToPoint() {
		if (bounds == null) {
			return null;
		}

		double x = 0, y = 0, w = bounds.getWidth(), h = bounds.getHeight();

		switch (type) {
		case HORIZONTAL:
			x = w / 2;
			y = h;
			break;
		case VERTICAL:
			x = w;
			y = h / 2;
			break;
		case RIGHT_DIAGONAL:
			x = w;
			y = h;
			break;
		case LEFT_DIAGONAL:
			x = w;
			y = 0;
			break;
		case FROM_CORNER:
			if (direction == DOWN) {
				x = w;
				y = h;
			} else {
				x = w;
				y = 0;
			}
			break;
		case FROM_CENTER:
			x = w;
			y = h;
			break;
		case ROUND:
			x = w;
			y = w;
			break;
		}

		return new Point2D.Double(x, y);
	}

	public void setTexturePaint(BufferedImage bfrImage) {
		this.bfrImage = bfrImage;
		if (bfrImage != null) {
			this.halfTransparent = false;
			this.firstGradientColor = null;
			this.secondGradientColor = null;
		}
	}

	public Paint getGradientPaint() {

		if (this.bfrImage != null) {
			return null;
		}

		if (firstGradientColor != null && secondGradientColor != null
				&& bounds != null) {

			Point2D fromPoint = this.getFromPoint();
			Point2D toPoint = this.getToPoint();
			int type = this.type;
			if (type == ROUND) {
				return new RoundGradientPaint(fromPoint, firstGradientColor,
						toPoint, secondGradientColor);
			} else if (type == FROM_CENTER) {
				return new RectangleGradientPaint(fromPoint,
						firstGradientColor, toPoint, secondGradientColor);
			} else if (type == FROM_CORNER) {
				return new CornerGradientPaint(this, fromPoint,
						firstGradientColor, toPoint, secondGradientColor);
			} else {
				return new GradientPaint(fromPoint, firstGradientColor,
						toPoint, secondGradientColor, cyclic);
			}
		} else {
			return null;
		}
	}

	public void setHalfTransparent(Color c) {
		this.firstGradientColor = c;

		if (c != null) {
			this.halfTransparent = true;
			this.bfrImage = null;
		}
	}

	public TexturePaint getHalfTransparentTexturePaint(Color c) {
		if (c == null) {
			return null;
		}

		// 기준 버퍼드 이미지 생성
		BufferedImage refBImage = Resource_WordArt.getBufferedImage("wordart",
				"half_transparent.gif");
		int rw = refBImage.getWidth(), rh = refBImage.getHeight();
		// 끝. 기준 버퍼드 이미지 생성

		// 기준 이미지 프러세싱
		BufferedImage processedImage = new BufferedImage(rw, rh,
				BufferedImage.TYPE_INT_RGB);

		int srcPixel, dstPixel, blackPixel = Color.black.getRGB(), firstTileColorPixel = c
				.getRGB();

		for (int y = 0; y < refBImage.getHeight(); y++) {
			for (int x = 0; x < refBImage.getWidth(); x++) {
				srcPixel = refBImage.getRGB(x, y);
				if (srcPixel == blackPixel) {
					dstPixel = firstTileColorPixel;
				} else {
					dstPixel = srcPixel;
				}
				processedImage.setRGB(x, y, dstPixel);
			}
		}
		// 끝. 기준 이미지 프러세싱

		// 텍스쳐 페인트 생성
		Rectangle2D tr = new Rectangle2D.Double(0, 0, rw, rh);

		TexturePaint tp = new TexturePaint(processedImage, tr);
		// 끝. 텍스쳐 페인트 생성
		return tp;
	}

	public boolean isHalfTransparent() {
		return this.halfTransparent;
	}

	public TexturePaint getTexturePaint() {

		if (this.halfTransparent && this.firstGradientColor != null) {
			return this.getHalfTransparentTexturePaint(this.firstGradientColor);
		}

		BufferedImage bfrImage = this.bfrImage ;
		if ( bfrImage == null) {
			return null;
		} else {
			Rectangle2D tr = new Rectangle2D.Double(0, 0, bfrImage.getWidth() , bfrImage.getHeight() );

			return new TexturePaint( bfrImage, tr);
		}
	}

	public Color getFirstGradientColor() {
		return this.firstGradientColor;
	}

	public Color getSecondGradientColor() {
		return this.secondGradientColor;
	}

	public static final int HORIZONTAL = 0, VERTICAL = 1, RIGHT_DIAGONAL = 2,
			LEFT_DIAGONAL = 3, FROM_CORNER = 4, FROM_CENTER = 5, ROUND = 6;
	public static final int DOWN = 0, UP = 1;

	private Rectangle2D bounds;
	private Color firstGradientColor = Color.white;
	private Color secondGradientColor = null;
	private boolean halfTransparent = false;
	private boolean cyclic = true;
	private boolean symmetric = false;
	private int type = HORIZONTAL, direction = DOWN;

	private transient BufferedImage bfrImage;

	public GraphicEffect() {
	}

}