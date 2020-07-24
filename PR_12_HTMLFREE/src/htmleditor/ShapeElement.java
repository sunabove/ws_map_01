package htmleditor;

/**
 * Title:        HTML FREE EDITOR
 * Description:  Html Free Editor v2.0
 * Copyright:    Copyright (c) 2001
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import jcosmos.*;

public class ShapeElement extends ImageElement {

	public static final int ZERO_ARROW_LINE = 0, FOREWARD_ONE_ARROW_LINE = 1,
			BACKWARD_ONE_ARROW_LINE = 2, TWO_ARROW_LINE = 3, MULTI_LINE = 4,
			FREE_LINE = 5, RECTANGLE = 6, CIRCLE = 7;

	private static ShapeElement MOUSE_SHAPE_ELEMENT;

	public static int MOUSE_PRESSED_POINT_INDEX = -1;

	public static int MOUSE_SHAPE_TYPE;

	private static final double TANGENT30 = Math.tan(Math.PI / 7.0);

	private int shapeType;

	private int thickness; // unit is pixel

	private Color fillColor = null;

	private Color lineColor = Color.black;

	private LinkedList pointList;

	private Shape shape;

	private File shapeFile;

	public ShapeElement(final HtmlDocument parentDoc,
			final LinkedList pointList, final int shapeType,
			final Color fillColor, final Color lineColor, final int thickness) {

		this.parentDoc = parentDoc;
		this.shapeType = shapeType;
		this.pointList = pointList;
		this.fillColor = fillColor;
		this.lineColor = lineColor;
		this.thickness = thickness;

		this.setStyle(ImageElement.FRONT_TEXT);

		this.shape = this.createShape(pointList, shapeType, thickness);

		Rectangle2D area = this.getArea();

		if (area != null) {

			// 생성자에서 영역 세팅시에는
			// setArea( ... ) 함수가 오버라이딩 되어 있으므로,
			// super.setArea( ... ) 함수를 사용한다.

			super.setArea(area);

			// 끝. super.setArea( ... ) 함수 사용하여 영역 설정하기

		}

	}

	@Override
	public void setArea(final Rectangle2D area) {

		final Rectangle2D preArea = this.getArea();

		final AffineTransform at = this.getResizeAffineTransform(area.getX()
				- preArea.getX(), area.getY() - preArea.getY(),
				area.getWidth(), area.getHeight());

		this.applyAffineTransform(at);

		super.setArea(this.getArea());

	}

	private void applyAffineTransform(final AffineTransform at) {

		final Iterator it = this.pointList.iterator();

		while (it.hasNext()) {

			final Point p = (Point) it.next();

			final Point2D q = at.transform(p, null);

			p.setLocation(q);

		}

		this.reSetShape();

	}

	public void reSetShape() {

		this.shape = null;

	}

	private Stroke getStroke() {

		if (this.isArrowShapeElement()) {

			return new BasicStroke(1);

		}

		return new BasicStroke(this.thickness);

	}

	public Shape getShape() {

		if (this.shape == null) {

			this.shape = this.createShape(this.pointList, this.shapeType, this.thickness);

		}

		return this.shape;

	}

	public Shape getBoundaryShape() {

		if (this.isArrowShapeElement()) {

			return this.createShape(this.pointList, this.shapeType, this.thickness + 3);

		}

		return this.getShape();

	}

	public void setFillColor(final Color fillColor) {

		this.fillColor = fillColor;

	}

	public void setLineColor(final Color lineColor) {

		this.lineColor = lineColor;

	}

	public Color getFillColor() {

		return this.fillColor;

	}

	public Color getLineColor() {

		return this.lineColor;

	}

	public int getThickness() {

		return this.thickness;

	}

	public void setThickness(int thickness) {

		this.thickness = thickness;

		this.reSetShape();

	}

	public void setShapeType(int shapeType) {
		this.shapeType = shapeType;
		this.reSetShape();
	}

	public void paint(final Graphics2D g2) {

		this.paint(g2, this.getShape());

	}

	private void paint(final Graphics2D g2, final Shape shape) {

		if (shape == null) {

			Utility.debug(this, "SHAPE IS NULL!!!!!");

			return;

		}

		if (this.isArrowShapeElement()) {

			this.paintArrowLine(g2, shape);

		} else {

			this.paintNonArrowLine(g2, shape);

		}

	}

	private void paintArrowLine(final Graphics2D g2, final Shape shape) {

		final Stroke preStroke = g2.getStroke();

		final Color lineColor = this.lineColor;

		if (lineColor == null) {

			return;

		}

		g2.setColor(lineColor);

		final Stroke stroke = this.getStroke();

		g2.setStroke(stroke);

		if (this.shapeType == ShapeElement.ZERO_ARROW_LINE && this.thickness == 1) {

			g2.draw(shape);

		} else {

			g2.fill(shape);

		}

		g2.setStroke(preStroke);

	}

	private void paintNonArrowLine(final Graphics2D g2, final Shape shape) {

		final Stroke preStroke = g2.getStroke();

		final Color fillColor = this.fillColor;

		if (fillColor != null) {

			g2.setColor(fillColor);

			g2.fill(shape);

		}

		final Color lineColor = this.lineColor;

		if (lineColor != null) {

			g2.setColor(lineColor);

		}

		final Stroke stroke = this.getStroke();

		g2.setStroke(stroke);

		g2.draw(shape);

		g2.setStroke(preStroke);

	}

	@Override
	public String getSourceName() {

		return this.getFile().getName();

	}

	@Override
	public File getFile() {

		if (this.shapeFile == null) {

			try {

				this.shapeFile = this.createShapeFile();

			} catch (Exception e) {

				Utility.debug(e);

			}
		}

		return this.shapeFile;

	}

	private Color getTransparentColor() {

		return Color.white;

	}

	public File createShapeFile() {

		Shape shape = this.getShape();

		final int th = this.getThickness();

		final Rectangle2D bounds = this.getArea();

		final int width = (int) (bounds.getWidth() + th);
		final int height = (int) (bounds.getHeight() + th);

		AffineTransform at = AffineTransform.getTranslateInstance(-bounds
				.getX()
				+ th / 2, -bounds.getY() + th / 2);

		shape = at.createTransformedShape(shape);

		final BufferedImage image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);

		final Graphics2D g2 = (Graphics2D) image.getGraphics();

		Utility.setHighQuality(g2);

		final Color transparentColor = this.getTransparentColor();

		g2.setColor(transparentColor);

		g2.fillRect(0, 0, width, height);

		this.paint(g2, shape);

		final HtmlEditorPane editor = this.parentDoc.getDocumentEditor();

		final String fileName = ""
				+ Math.abs((int) (System.currentTimeMillis())) + ".gif";

		try {

			final File file = new File(HtmlEditorPane.getTempDir(editor),
					fileName);

			GIFOutputStream.writeGIF(new FileOutputStream(file), image,
					GIFOutputStream.STANDARD_256_COLORS, transparentColor);

			return file;

		} catch (IOException e) {

			Utility.debug(e);

			return null;

		}

	}

	@Override
	public String getNameTag() {

		final int fillRGB = (this.fillColor == null) ? -1 : this.fillColor.getRGB();

		final int lineRGB = (this.lineColor == null) ? -1 : this.lineColor.getRGB();

		String tag = "name = \"shape\" type = \"" + this.shapeType + "\""
				+ " fill_color = \"" + fillRGB + "\"" + " line_color = \""
				+ lineRGB + "\"" + " thickness = \"" + this.thickness
				+ "\" data = \"";

		final Iterator it = this.pointList.iterator();

		while (it.hasNext()) {

			final Point point = (Point) it.next();

			tag += "" + point.x + "," + point.y + ",";

		}

		tag += "\" ";

		return tag;

	}

	@Override
	public Rectangle2D getArea() {

		final Shape shape = this.getShape();

		if (shape == null) {

			return null;

		}

		// final int shapeType = this.shapeType;
		//
		// if( isSimpleLinearShapeElement() ) {
		//
		// Point from = (Point) pointList.get( 0 );
		// Point to = (Point) pointList.get( 1 );
		//
		// final int x = (from.x < to.x ) ? from.x : to.x;
		// final int y = (from.y < to.y ) ? from.y : to.y;
		// final int w = Math.abs( to.x - from.x );
		// final int h = Math.abs( to.y - from.y );
		//
		// return new Rectangle( x, y, w, h );
		//
		// }

		final Rectangle2D area = shape.getBounds2D();

		return new Rectangle2D.Double(area.getX(), area.getY(),
				area.getWidth() + 1, area.getHeight() + 1);

	}

	private Shape createShape(final LinkedList pointList, final int shapeType,
			final int thickness) {

		// Utility.debug( ShapeElement.class, "POINTLIST SIZE = " +
		// pointList.size() );

		if (pointList.size() < 1) {

			return null;

		}

		if (this.isArrowShapeElement()) {

			return this.createArrowLine(shapeType, pointList, thickness);

		} else if (shapeType == RECTANGLE) {

			return this.createRectangle(pointList, thickness);

		} else if (shapeType == CIRCLE) {

			return this.createCircle(pointList, thickness);

		}

		return this.createMultiLine(pointList);

	}

	private Shape createRectangle(final LinkedList pointList,
			final int thickness) {

		if (pointList.size() < 2) {

			return null;

		}

		Point from = (Point) pointList.get(0);
		Point to = (Point) pointList.get(1);

		final int x = (from.x < to.x) ? from.x : to.x;
		final int y = (from.y < to.y) ? from.y : to.y;
		final int w = Math.abs(to.x - from.x);
		final int h = Math.abs(to.y - from.y);

		return new Rectangle(x, y, w, h);

	}

	private Shape createCircle(final LinkedList pointList, final int thickness) {

		if (pointList.size() < 2) {

			return null;

		}

		Point from = (Point) pointList.get(0);
		Point to = (Point) pointList.get(1);

		final int x = (from.x < to.x) ? from.x : to.x;
		final int y = (from.y < to.y) ? from.y : to.y;
		final int w = Math.abs(to.x - from.x);
		final int h = Math.abs(to.y - from.y);

		return new Ellipse2D.Double(x, y, w, h);

	}

	private Shape createArrowLine(final int arrowType,
			final LinkedList pointList, final int thickness) {

		if (pointList.size() < 2) {

			return null;

		}

		final boolean backwardOneArrowType = arrowType == ShapeElement.BACKWARD_ONE_ARROW_LINE;

		final Point p = backwardOneArrowType ? (Point) pointList.get(1)
				: (Point) pointList.get(0);
		final Point q = backwardOneArrowType ? (Point) pointList.get(0)
				: (Point) pointList.get(1);

		final int dx = q.x - p.x;
		final int dy = q.y - p.y;

		final int w = (int) Math.sqrt(dx * dx + dy * dy);

		final Shape arrow;

		if (arrowType == ShapeElement.ZERO_ARROW_LINE) {

			arrow = this.createZeroArrowLine(w, thickness);

		} else if (arrowType == ShapeElement.FOREWARD_ONE_ARROW_LINE) {

			arrow = this.createOneArrowLine(w, thickness);

		} else if (arrowType == ShapeElement.BACKWARD_ONE_ARROW_LINE) {

			arrow = this.createOneArrowLine(w, thickness);

		} else {

			arrow = this.createTwoArrowLine(w, thickness);
		}

		final double radian = Utility.getRadian(dx, dy);

		final AffineTransform at = AffineTransform.getTranslateInstance(p.x,
				p.y);

		at.rotate(radian);

		return at.createTransformedShape(arrow);

	}

	private Shape createZeroArrowLine(final double w, double t) {

		if (t == 1) {

			return new Line2D.Double(0, 0, w, 0);

		}

		t = t / 2.0; // 굵기를 반으로 줄인다. 위 아래로 트래블(travel) 하므로

		return new Rectangle2D.Double(0, -t, w, 2 * t);

	}

	private Shape createOneArrowLine(final double w, double t) {

		t = t / 2.0; // 굵기를 반으로 줄인다. 위 아래로 트래블(travel) 하므로

		final double bRatio = 2;

		final double b = ((bRatio * t) < 4) ? 4 : (bRatio * t);

		final double h = (b + t) / TANGENT30;

		final GeneralPath gp = new GeneralPath();

		gp.moveTo(0, (float) -t);

		gp.lineTo((float) (w - h), (float) -t);

		gp.lineTo((float) (w - h), (float) (-t - b));

		gp.lineTo((float) w, 0);

		gp.lineTo((float) (w - h), (float) (t + b));

		gp.lineTo((float) (w - h), (float) t);

		gp.lineTo(0, (float) t);

		gp.closePath();

		return gp;

	}

	private Shape createTwoArrowLine(final double w, double t) {

		t = t / 2.0; // 굵기를 반으로 줄인다. 위 아래로 트래블(travel) 하므로

		final double bRatio = 2;

		final double b = ((bRatio * t) < 4) ? 4 : (bRatio * t);

		final double h = (b + t) / TANGENT30;

		final GeneralPath gp = new GeneralPath();

		gp.moveTo(0, 0);

		gp.lineTo((float) (h), (float) (-t - b));

		gp.lineTo((float) (h), (float) (-t));

		gp.lineTo((float) (w - h), (float) -t);

		gp.lineTo((float) (w - h), (float) (-t - b));

		gp.lineTo((float) (w), 0);

		gp.lineTo((float) (w - h), (float) (t + b));

		gp.lineTo((float) (w - h), (float) (t));

		gp.lineTo((float) (h), (float) (t));

		gp.lineTo((float) (h), (float) (t + b));

		gp.closePath();

		return gp;

	}

	private GeneralPath createMultiLine(final LinkedList pointList) {

		final GeneralPath gp = new GeneralPath();

		final Iterator it = pointList.iterator();

		Point point = (Point) it.next();

		gp.moveTo(point.x, point.y);

		while (it.hasNext()) {

			point = (Point) it.next();

			gp.lineTo(point.x, point.y);

		}

		return gp;

	}

	public LinkedList getPointList() {

		return this.pointList;

	}

	public void addMousePoint(final MouseEvent e) {

		this.getPointList().addLast(new Point(e.getX(), e.getY()));

	}

	public static void removeAllMousePoints() {

		MOUSE_SHAPE_ELEMENT = new ShapeElement(null, new LinkedList(),
				MOUSE_SHAPE_TYPE, null, Color.black, 1);

	}

	public static ShapeElement getMouseShapeElement() {

		return MOUSE_SHAPE_ELEMENT;

	}

	public Point getLastPoint() {

		final LinkedList pointList = this.getPointList();

		return pointList.size() == 0 ? null : (Point) pointList.getLast();

	}

	public int getShapeType() {

		return this.shapeType;

	}

	public static void addMouseShapeElementToEditor(final HtmlEditorPane editor) {

		final ShapeElement shapeElement = MOUSE_SHAPE_ELEMENT;

		final LinkedList pointList = shapeElement.getPointList();

		Utility.debug(ShapeElement.class, "MOUSE POINT LIST SIZE = "
				+ pointList.size());

		filterPointList(pointList);

		Utility.debug(ShapeElement.class, "FILTERED POINT LIST SIZE = "
				+ pointList.size());

		if (pointList.size() < 2) {

			Utility.debug(ShapeElement.class, "CANCEL ADDING TO EDITOR.");
			return;

		}

		// shapeElement.setFillColor( shapeElement.isClosed() ? Color.cyan :
		// null );

		editor.addShapeElement(shapeElement);

	}

	public boolean isClosed() {

		final int shapeType = this.shapeType;

		if (shapeType == ShapeElement.RECTANGLE
				|| shapeType == ShapeElement.CIRCLE) {

			return true;

		}

		if (this.isTwoPointsComposedShapeElement()) {

			return false;

		}

		final LinkedList pointList = this.pointList;

		return pointList.getFirst().equals(pointList.getLast());

	}

	private static void filterPointList(final LinkedList pointList) {

		for (int i = 0; i < (pointList.size() - 1);) {

			final Point p = (Point) pointList.get(i);
			final Point q = (Point) pointList.get(i + 1);

			if (p.equals(q)) {

				pointList.remove(q);

			} else {

				i++;

			}

		}

	}

	public boolean isTwoPointsComposedShapeElement() {

		final int shapeType = this.shapeType;

		return shapeType == ShapeElement.ZERO_ARROW_LINE
				|| shapeType == ShapeElement.FOREWARD_ONE_ARROW_LINE
				|| shapeType == ShapeElement.BACKWARD_ONE_ARROW_LINE
				|| shapeType == ShapeElement.TWO_ARROW_LINE
				|| shapeType == ShapeElement.CIRCLE
				|| shapeType == ShapeElement.RECTANGLE;

	}

	public boolean isArrowShapeElement() {

		final int shapeType = this.shapeType;

		return shapeType == ShapeElement.ZERO_ARROW_LINE
				|| shapeType == ShapeElement.FOREWARD_ONE_ARROW_LINE
				|| shapeType == ShapeElement.BACKWARD_ONE_ARROW_LINE
				|| shapeType == ShapeElement.TWO_ARROW_LINE;

	}

	@Override
	public Shape[] getCornerRects() {

		if (!this.isArrowShapeElement()) {

			return super.getCornerRects();

		}

		final Shape[] rects = new Shape[8];

		final LinkedList pointList = this.pointList;

		final Point first = (Point) pointList.get(0);
		final Point last = (Point) pointList.get(1);

		final int crw = CORNER_RECT_WIDTH; // corner rect width

		rects[0] = new Rectangle(first.x - crw / 2, first.y - crw / 2, crw, crw);
		rects[7] = new Rectangle(last.x - crw / 2, last.y - crw / 2, crw, crw);

		return rects;

	}

	private LinkedList getClonePointList() {

		final Iterator it = this.pointList.iterator();

		final LinkedList clonePointList = new LinkedList();

		while (it.hasNext()) {

			clonePointList.addLast(((Point) it.next()).clone());

		}

		return clonePointList;

	}

	@Override
	public Object clone(final HtmlDocument parentDoc) {

		return new ShapeElement(parentDoc, this.getClonePointList(), this.shapeType,
				this.fillColor, this.lineColor, this.thickness);

	}

	public int getPointIndex(final int x, final int y) {

		final Iterator it = this.pointList.iterator();

		final Point p = new Point(x, y);

		int i = 0;

		while (it.hasNext()) {

			final Point q = (Point) it.next();

			if (this.isVeryNear(p, q)) {

				return i;

			}

			i++;

		}

		return -1;

	}

	@Override
	public Shape getDraggedShape(final int top, final MouseEvent from,
			final MouseEvent to) {

		final int mousePointIndex = ShapeElement.MOUSE_PRESSED_POINT_INDEX;

		if (this.isArrowShapeElement()) {

			if (mousePointIndex < 0) { // move mode 일 경우에.....

				final AffineTransform at = AffineTransform
						.getTranslateInstance(to.getX() - from.getX(), to
								.getY()
								- from.getY());

				return at.createTransformedShape(this.getShape());

			}

			final ShapeElement cloneShapeElement = (ShapeElement) this
					.clone(null);

			final LinkedList pointList = cloneShapeElement.getPointList();

			final Point point = (Point) pointList.get(mousePointIndex);

			point.setLocation(to.getX(), to.getY());

			cloneShapeElement.reSetShape();

			return cloneShapeElement.getShape();

		}

		return super.getDraggedShape(top, from, to);

	}

	@Override
	protected boolean hasReSetArea(final int top, final MouseEvent from,
			final MouseEvent to) {

		final int mousePointIndex = ShapeElement.MOUSE_PRESSED_POINT_INDEX;

		if (this.isArrowShapeElement()) {

			if (mousePointIndex < 0) { // move mode 일 경우에.....

				final AffineTransform at = AffineTransform
						.getTranslateInstance(to.getX() - from.getX(), to
								.getY()
								- from.getY());

				this.applyAffineTransform(at);

				return true;

			}

			final LinkedList pointList = this.getPointList();

			final Point point = (Point) pointList.get(mousePointIndex);

			point.setLocation(to.getX(), to.getY());

			this.reSetShape();

			return true;

		}

		return super.hasReSetArea(top, from, to);

	}

}