package com.ynhenc.droute.map.link;

import java.awt.*;
import java.awt.geom.*;
import java.io.Writer;
import java.lang.Math;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.droute.*;
import com.ynhenc.droute.map.Projection;
import com.ynhenc.droute.render.*;
import com.ynhenc.kml.*;

public class GeoLink extends GeoObject {

	public RoadRank getRoadRank() {
		return roadRank;
	}

	public LinkShape getLinkShape() {
		if (this.linkShape == null) {
			this.linkShape = LinkShape.getLinkShape(this.getObjectId());
		}
		return this.linkShape;
	}

	@Override
	public Mbr getMbr() {
		return new Mbr(fromVert.getX(), fromVert.getY(), toVert.getX(), toVert.getY());
	}

	@Override
	public Vert getFrVert() {
		return fromVert;
	}

	@Override
	public Vert getToVert() {
		return toVert;
	}

	@Override
	public double getLength() {
		return length;
	}

	@Override
	public void paint(RenderInfo render, StyleLink styleLink, int index) {

		Graphics2D g = render.getGraphics();
		Color lineColor = styleLink.getLineColor();
		Dimension nodeSize = render.getNodeSize();

		Projection projection = render.getProjection();

		Vert frVert = this.getFrVert();
		Vert toVert = this.getToVert();

		PntShort a = projection.toGraphics(frVert.getX(), frVert.getY());
		PntShort b = projection.toGraphics(toVert.getX(), toVert.getY());

		PntShort normal = this.getNormalVector(a, b, 2);

		if (styleLink.isPath()) {
			// drawing road rank shape
			this.paintLinkShape(g, render, lineColor, styleLink.getStroke(), normal);
		} else {
			// set speed color
			Color speedColor = this.getSpeedColor(this.getRealTimeInfo());
			speedColor = lineColor;
			//speedColor = Color.DARK_GRAY;
			if (speedColor != null) {
				float lineWidth = 10 - this.getRoadRank().getRoadRank();
				lineWidth = 1;
				Stroke roadRankStroke = new BasicStroke(lineWidth);
				this.paintLinkShape(g, render, speedColor, roadRankStroke, normal);
			} else {
				this.paintLinkShape(g, render, lineColor, styleLink.getStroke(), normal);
			}
		} // end of setting speed color

		if (true) {
			// draw arrow
			g.setStroke( new BasicStroke( 1 ));
			SymbolArrow arrow = new SymbolArrow();
			Dimension arrowSize = new Dimension( 3, 3 );
			Shape shape = arrow.createArrowLine(a, b, arrowSize, normal );
			g.draw(shape);
			// end of drawing arrow
		} else if (true) {
			// drawing vertex
			g.setColor(Color.blue);
			Ellipse2D e1 = frVert.createNodeShape(projection, nodeSize, normal);
			g.fill(e1);
			g.setColor(Color.green);
			Ellipse2D e2 = toVert.createNodeShape(projection, nodeSize, normal);
			g.fill(e2);
		} // end of drawing vertex

		if (index > -1) { // draw index text
			PntShort cen = PntShort.pntShort((a.getX() + b.getX()) / 2.0, (a.getY() + b.getY()) / 2.0);
			if (false) {
				g.setColor(Color.black);
				g.drawString("" + index, (int) cen.getX(), (int) cen.getY());
			}
			if (false) {
				String linkDistance = "" + this.getLength();
				g.setColor(Color.black);
				g.drawString(linkDistance, (int) cen.getX(), (int) cen.getY());
			} else {
			}
		} // end of drawing index text

	}

	private void paintLinkShape(Graphics2D g, RenderInfo render, Color lineColor, Stroke stroke, PntShort normal) {
		g.setColor(lineColor);
		g.setStroke(stroke);

		boolean drawLinkShape = render.isDrawLinkShape();

		Projection projection = render.getProjection();

		if (drawLinkShape) {
			// draw link shape
			LinkShape linkShape = this.getLinkShape();
			if (linkShape != null) {
				double coords[] = linkShape.getCoords();
				PntShort a, b;
				Line2D line;
				if (coords != null) {
					for (int i = 0, iLen = (coords.length / 2) - 1; i < iLen; i++) {
						a = projection.toGraphics(coords[2 * i], coords[2 * i + 1]);
						b = projection.toGraphics(coords[2 * (i + 1)], coords[2 * (i + 1) + 1]);
						line = new Line2D.Double(a.getX(), a.getY(), b.getX(), b.getY());
						g.draw(line);
					}
				}
			}
			// end of drawing link shape
		} else {
			Vert frVert = this.getFrVert();
			Vert toVert = this.getToVert();

			PntShort a = projection.toGraphics(frVert.getX(), frVert.getY());
			PntShort b = projection.toGraphics(toVert.getX(), toVert.getY());

			Line2D line;
			if (normal != null) {
				line = new Line2D.Double(a.getX() + normal.getX(), a.getY() + normal.getY(), b.getX() + normal.getX(), b.getY()
						+ normal.getY());
			} else {
				line = new Line2D.Double(a.getX(), a.getY(), b.getX(), b.getY());
			}

			g.draw(line);

			if (false) {
				this.debug(String.format("Line p1(%s), p2(%s) ", line.getP1(), line.getP2()));
			}
		}

	}

	public PntShort getNormalVector(PntShort a, PntShort b, int size) {

		double x = b.getX() - a.getX();
		double y = b.getY() - a.getY();

		if (false) {
			return PntShort.pntShort(x, y);
		} else {
			double len = Math.sqrt(x * x + y * y);

			if (len != 0) {
				return PntShort.pntShort(size * y / len, -size * x / len);
			} else {
				return null;
			}
		}
	}

	private Color getSpeedColor(RealTimeInfo realTimeInfo) {
		return SpeedColor.getSpeedColor(realTimeInfo);
	}

	@Override
	public boolean isIncluded(IncludeInfo inclInfo) {

		Point2D mid = this.getTmMiddlePoint();

		Point2D p = inclInfo.getVert().getTmPoint();

		return (Math.abs(mid.getX() - p.getX())) < 100 && (Math.abs(mid.getY() - p.getY())) < 100;

	}

	private Point2D getTmMiddlePoint() {
		Point2D from = this.getFrVert().getTmPoint();
		Point2D to = this.getToVert().getTmPoint();

		Point2D mid = new Point2D.Double((from.getX() + to.getX()) / 2.0, (from.getY() + to.getY()) / 2.0);
		return mid;
	}

	@Override
	public KmlGeometry getKmlGeometry(GeoLinkCoordLevel coordLevel) {
		if (coordLevel == GeoLinkCoordLevel.LINKSHAPE) {
			return this.getKmlGeometryLinkShape();
		} else {
			return this.getKmlGeometryNodeShape();
		}
	}

	private KmlGeometry getKmlGeometryNodeShape() {
		KmlLineString kmlLineString = new KmlLineString();
		KmlCoordinates coordinates = new KmlCoordinates();

		coordinates.addComponent(this.toKmlCoordinate(this.getFrVert()));
		coordinates.addComponent(new KmlCoord(","));
		coordinates.addComponent(this.toKmlCoordinate(this.getToVert()));

		kmlLineString.addComponent(coordinates);
		return kmlLineString;
	}

	private KmlGeometry getKmlGeometryLinkShape() {
		KmlLineString kmlLineString = new KmlLineString();
		KmlCoordinates coordinates = new KmlCoordinates();

		LinkShape linkShape = this.getLinkShape();

		KmlCoord coord = new KmlCoord(linkShape.toKmlCoords().toString());
		coordinates.addComponent(coord);
		kmlLineString.addComponent(coordinates);
		return kmlLineString;
	}

	private KmlCoord toKmlCoordinate(Vert vert) {
		return new KmlCoord(vert.getX() + "," + vert.getY() + ",0");
	}

	@Override
	public double getPhysicalDist(Vert toVert, Find.Mode findMode) {

		Point2D a = null;

		if (findMode == Find.Mode.START) {
			a = this.getFrVert().getTmPoint();
		} else if (findMode == Find.Mode.MIDDLE) {
			a = this.getTmMiddlePoint();
		} else if (findMode == Find.Mode.END) {
			a = this.getToVert().getTmPoint();
		}

		Point2D b = toVert.getTmPoint();

		return Math.sqrt((a.getX() - b.getX()) * (a.getX() - b.getX()) + (a.getY() - b.getY()) * (a.getY() - b.getY()));
	}

	@Override
	public double getSelfCost(SrchOption srchOption) {
		if (srchOption.getSrchType() == 0) {
			return this.getLength();
		}

		RealTimeInfo realTimeInfo = this.getRealTimeInfo();
		double speed_Kmph = realTimeInfo.getSpeed_Kmph();
		double cost = this.getLength() / speed_Kmph;
		return cost;
	}

	@Override
	public double getCostTo(GeoObject o, SrchOption srchOption) {
		GeoLink toLink = (GeoLink) o;
		int srchType = srchOption.getSrchType();
		if (srchType == 0) {
			// shortest distance
			return this.getCostTo_ShortestDist(toLink, srchOption);
		} else if (srchType == 1) {
			// shortest time
			return this.getCostTo_ShortestTime(toLink, srchOption);
		} else if (srchType == 2) {
			// optimal path
			return this.getCostTo_ShortestTime(toLink, srchOption);
		} else {
			return this.getCostTo_ShortestDist(toLink, srchOption);
		}
	}

	private double getCostTo_ShortestDist(GeoLink toLink, SrchOption srchOption) {
		double cost = toLink.getLength();

		if (this.getToVert() != toLink.getFrVert()) {
			cost += this.getToVert().getDistanceTo(toLink.getFrVert());
		}

		return cost;
	}

	private double getCostTo_ShortestTime(GeoLink toLink, SrchOption srchOption) {

		double cost = toLink.getSelfCost(srchOption);

		if (this.getToVert() != toLink.getFrVert()) {
			cost += (this.getToVert().getDistanceTo(toLink.getFrVert())) / RealTimeInfo.getAvgSpped_Kmph();
		}

		return cost;
	}

	private GeoLink(Long id, Vert fromVert, Vert toVert, double length, RoadRank roadRank) {
		super(id);
		this.fromVert = fromVert;
		this.toVert = toVert;
		this.length = length;
		this.roadRank = roadRank;
	}

	public static GeoLink getGeoLink(Long id, Vert fromVert, Vert toVert, double length, RoadRank roadRank) {
		return new GeoLink(id, fromVert, toVert, length, roadRank);
	}

	private Vert fromVert;
	private Vert toVert;
	private double length;
	private transient LinkShape linkShape;
	private RoadRank roadRank;

}
