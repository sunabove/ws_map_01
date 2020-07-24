package com.ynhenc.droute.render;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.*;

import com.ynhenc.comm.util.GraphicsUtil;
import com.ynhenc.comm.util.ImageUtil;
import com.ynhenc.droute.*;
import com.ynhenc.droute.map.Map;
import com.ynhenc.droute.map.PathFinder;
import com.ynhenc.droute.map.PathFinderDist;
import com.ynhenc.droute.map.Projection;
import com.ynhenc.droute.map.link.MapLink;
import com.ynhenc.droute.map.square.MapSquare;
import com.ynhenc.kml.KmlHandlerPath;

public class PathFinderPanel extends JPanel implements MouseMotionListener, MouseListener {

	public JTextArea getPathInfoComp(int srchType) {
		if (this.pathInfoComp != null && this.pathInfoComp.length > srchType) {
			return pathInfoComp[srchType];
		} else {
			return null;
		}
	}

	public JTextArea[] getPathInfoComp() {
		return pathInfoComp;
	}

	public void setPathInfoComp(JTextArea[] pathInfoDistComp) {
		this.pathInfoComp = pathInfoDistComp;
	}

	public JTextField getPathLenComp() {
		return pathLenComp;
	}

	public void setPathLenComp(JTextField pathLenComp) {
		this.pathLenComp = pathLenComp;
	}

	private static final long serialVersionUID = 1L;

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		MapAppData mapAppData = this.getMapAppData();

		if (mapAppData != null) {
			Find.Mode findMode = Find.Mode.START;
			Node over = this.getNodeAt(e, findMode);
			if (over != null) {
				if (over != null && mapAppData.getOverNode() != over) {
					mapAppData.setOverNode(over);
					this.update(this.getGraphics());
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		final MapAppData mapAppData = this.getMapAppData();
		if (mapAppData != null) {
			SrchOption srchOption = mapAppData.getSrchOption();
			this.searchPathAgain(e, srchOption);
			this.update(this.getGraphics());
		}
	}

	public void searchPathAgain(MouseEvent e, SrchOption srchOption) {
		final MapAppData mapAppData = this.getMapAppData();
		if (mapAppData != null) {
			Map map = mapAppData.getMap();
			if (map == null || e == null) {
				// do nothing !!!!
			} else {
				Vert startVert = mapAppData.getStartVert();
				Vert clickVert = null;
				if ( true ) {
					Dimension panelSize = this.getSize();
					Projection projection = map.getProjection( panelSize);
					Dimension nodeSize = mapAppData.getNodeSize();
					int x = e.getX();
					int y = e.getY();
					PntShort point = projection.toSpatial(x, y);
					clickVert = Vert.getVert(point.getX(), point.getY());
				}

				if (e.isShiftDown() || e.isAltDown() || startVert == null) {
					mapAppData.setStartVert(clickVert);
				} else {
					mapAppData.setEndVert(clickVert);
				}
			}

			this.searchPath_Impl(mapAppData, srchOption);

		}
	}

	private Node getNodeAt(MouseEvent e, Find.Mode findMode) {

		int mouseX = e.getX();
		int mouseY = e.getY();

		if (mapAppData != null) {
			Map map = mapAppData.getMap();
			if (map != null) {
				Dimension panelSize = this.getSize();
				Projection projection = map.getProjection(panelSize);
				if (projection != null) {
					Dimension nodeSize = mapAppData.getNodeSize();
					int x = e.getX();
					int y = e.getY();
					PntShort point = projection.toSpatial(x, y);
					Vert vert = Vert.getVert(point.getX(), point.getY());
					Node clickNode = map.getNodeNearest(vert, findMode);
					if (false) {
						IncludeInfo inclInfo = new IncludeInfo(vert, nodeSize);
						clickNode = map.getNodeAt(inclInfo);
					}
					if (false) {
						this.debug("Sel Node = " + clickNode, false);
					}
					return clickNode;
				}
			}
		}

		return null;
	}

	public void setPath(SrchOption srchOption, Path path) {
		final MapAppData mapAppData = this.getMapAppData();
		if (mapAppData != null) {
			mapAppData.setPath(srchOption, path);

			this.setPathInfoToConsole(srchOption, path);
		}
	}

	private Path searchPath_Impl(MapAppData mapAppData, SrchOption srchOption) {

		Path path = PathFinder.searchPath(mapAppData, srchOption);

		this.setPathInfoToConsole(srchOption, path);

		return path;
	}

	private void setPathInfoToConsole(SrchOption srchOption, Path path) {

		String info = null;
		int srchType = srchOption.getSrchType();
		if (path != null) {
			String nl = ComLibRoute.NL;
			info = "no:" + path.getQueryNo();
			info += ", " + path.getSrchOption();
			info += nl + ", len:" + path.getLength();
		} else {
			info = "";
		}

		if (info != null) {
			JTextArea textArea = this.getPathInfoComp(srchType);
			if (textArea != null) {
				Color bg = Color.blue;
				if (srchType > 0) {
					Path distPath = mapAppData.getPath(0);
					if (path != null && distPath != null && distPath.getLength() > path.getLength()) {
						bg = Color.red;
					}
				}
				textArea.setBackground(bg);
				textArea.setForeground(new Color(~bg.getRGB()));
				textArea.setText(info);
			}
		}

		if (info != null) {
			JTextField textField = this.getPathLenComp();
			if (textField != null) {
				textField.setText(info);
			}
		}
	}

	public void debug(Object msg, boolean b) {
		if (b) {
			System.out.println(msg);
		}
	}

	private BufferedImage createImage(Dimension size) {

		BufferedImage image = this.image;

		if (image != null && image.getWidth() == size.width && image.getHeight() == size.height) {
			return image;
		} else {
			image = ImageUtil.createImage(size.width, size.height);
			this.image = image;
		}

		return image;
	}

	@Override
	public void update(Graphics g) {
		this.paint(g);
	}

	@Override
	public void paint(Graphics g1) {

		Dimension size = this.getSize();

		BufferedImage image = this.createImage(size);

		this.paintPanel(image.getGraphics());

		g1.drawImage(image, 0, 0, this);

	}

	private void paintPanel(Graphics g1) {

		boolean localDebug = false;

		Dimension panelSize = this.getSize();

		MapAppData mapAppData = this.getMapAppData();

		if (true || mapAppData == null) {
			g1.setColor(Color.white);
			g1.fillRect(0, 0, panelSize.width, panelSize.height);
			// super.paint(g1);
		}

		this.debug("paint panel", localDebug);

		Graphics2D g = (Graphics2D) g1;

		GraphicsUtil.setHighQuality(g);

		if (mapAppData != null) {
			Dimension nodeSize = mapAppData.getNodeSize();
			Map map = mapAppData.getMap();
			Projection projection = map.getProjection(panelSize);
			boolean drawLinkShape = false;
			RenderInfo render = new RenderInfo(g, projection, panelSize, nodeSize, drawLinkShape);
			MapPainter mapPainter = this.getMapPainter();

			if (map != null) { // draw base map
				int indexSize = map.getIndexSize();
				this.debug("indexSize = " + indexSize, localDebug);
				this.debug(projection, localDebug);
				int lineWidth = 4;
				int alpha = 200;
				mapPainter.paintMap(render, map, lineWidth, alpha);
			}

			if (true) { // draw shortest path
				for (int i = 0, iLen = 3; i < iLen; i++) {
					int srchType = i;
					Path path = mapAppData.getPath(srchType);
					if (path != null) {
						SrchOption srchOption = path.getSrchOption();
						StyleLink style = new StyleLink(Color.red, Color.red, 6, 255, false );
						if (srchOption != null) {
							style = srchOption.getDefStyle();
						}
						path.setStyle(style);
						boolean drawLinkShapeOnPath = mapAppData.isDrawLinkShape();
						render.setDrawLinkShape(drawLinkShapeOnPath);
						mapPainter.paintPath(render, path, style);
					}
				}
			}

			Vert start = mapAppData.getStartVert();
			Vert finish = mapAppData.getEndVert();

			Dimension vertSize = new Dimension( 10, 10 );

			if (start != null) {
				Vert vert = start;
				StyleVert style = new StyleVert(Color.red, Color.red, vertSize );
				mapPainter.paintVert(render, vert, style, -1);
			}

			if (finish != null) {
				Vert vert = finish;
				StyleVert style = new StyleVert(Color.blue, Color.blue, vertSize );
				mapPainter.paintVert(render, vert, style, -1);
			}

			if (true) {
				// draw over node
				Node over = mapAppData.getOverNode();
				if (over != null) {
					StyleLink styleOver = new StyleLink(Color.magenta, Color.magenta, 4, 255, false );
					mapPainter.paintMouseOver(render, over, styleOver);
				}
			}
		}
	}

	public MapPainter getMapPainter() {
		if (this.mapPainter == null) {
			this.mapPainter = new MapPainter();
		}
		return this.mapPainter;
	}

	public void init() {
		this.initPanel(0);
	}

	public void initPanel(int initMode) {

		this.setBackground(Color.white);

		Dimension size = this.getSize();
		super.setPreferredSize(size);

		MapAppData mapAppData = new MapAppData();
		Dimension nodeSize = mapAppData.getNodeSize();

		Map map = null;

		try {
			if (initMode == 0) {
				map = MapSquare.newMapSquare(this);
			} else {
				map = MapLink.getNewMapLink();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (map != null) {
			mapAppData.setMap(map);

			mapAppData.setStartVert(null);
			mapAppData.setEndVert(null);
			mapAppData.setOverNode(null);

			mapAppData.initPathList();

			this.mapAppData = mapAppData;
		}

	}

	public void exportToKml() throws Exception {
		MapAppData mapAppData = this.getMapAppData();
		if (mapAppData != null) {
			int srchTypeLatest = mapAppData.getSrchTypeLatest();
			Path path = mapAppData.getPath(srchTypeLatest);
			if (path != null) {
				KmlHandlerPath kmlHandler = new KmlHandlerPath();
				kmlHandler.toKml(this, path);
			}
		}
	}

	public MapAppData getMapAppData() {
		return this.mapAppData;
	}

	public void setMapAppData(MapAppData mapAppData) {
		this.mapAppData = mapAppData;

		if (mapAppData != null) {
			this.repaint();
		}
	}

	private void setSrchType(int srchType) {
		MapAppData mapAppData = this.getMapAppData();
		if (mapAppData != null) {
			SrchOption srchOption = new SrchOption(srchType);
			mapAppData.setSrchOption(srchOption);
		}
	}

	private Double convertDouble(String val) {
		Double d = null;
		try {
			d = Double.valueOf(val.trim());
		} catch (Exception e) {

		}
		return d;
	}

	public void setOrgDesLocation(String sxt, String syt, String ext, String eyt) {
		this
				.setOrgDesLocation(this.convertDouble(sxt), this.convertDouble(syt), this.convertDouble(ext), this
						.convertDouble(eyt));
	}

	public void setOrgDesLocation(Double sx, Double sy, Double ex, Double ey) {

		MapAppData mapAppData = this.getMapAppData();
		if (mapAppData != null) {
			Map map = mapAppData.getMap();
			if (map != null && sx != null && sy != null) {
				Vert vert = Vert.getVert(sx, sy);
				mapAppData.setStartVert( vert );
			}
			if (map != null && ex != null && ey != null) {
				Vert vert = Vert.getVert(ex, ey);
				mapAppData.setEndVert( vert );
			}
			MouseEvent e = null;
			SrchOption srchOption = mapAppData.getSrchOption();
			this.searchPathAgain(e, srchOption);
		}

	}

	public void setDrawLinkShape(boolean drawLinkShape) {
		MapAppData mapAppData = this.getMapAppData();
		if (mapAppData != null) {
			mapAppData.setDrawLinkShape(drawLinkShape);
		}
	}

	public PathFinderPanel() {
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.setDoubleBuffered(true);
	}

	private BufferedImage image;

	private MapAppData mapAppData;
	private MapPainter mapPainter;
	private JTextArea[] pathInfoComp;
	private JTextField pathLenComp;

}
