package com.ynhenc.droute.render;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Iterator;

import com.ynhenc.droute.*;
import com.ynhenc.droute.map.Map;
import com.ynhenc.droute.map.Projection;

public class MapPainter extends ComLibRoute {

	public void paintMap(RenderInfo render, Map map, int lineWidth, int alpha) {
		this.map = map;
		NodeIndexer nodeIndexer = map.getNodeIndexer();
		boolean isPath = false;
		StyleLink styleWhite = new StyleLink(Color.white, Color.black, lineWidth, alpha, isPath );
		StyleLink styleGray = new StyleLink(Color.gray, Color.black, lineWidth, alpha, isPath );
		StyleLink style = styleWhite;
		Graphics2D g = render.getGraphics();
		Dimension size = render.getPanelSize();
		for (Node node : nodeIndexer.getAllIndexElments()) {
			this.paintNode(render, node, style, -1);
		}
	}

	public void paintNode(RenderInfo render, PathElement node, StyleLink style, int index) {
		node.paintPathElement(render, style, index);
	}

	public void paintVert(RenderInfo render, Vert vert, StyleVert style, int index) {
		vert.paintVert(render, style, index);
	}

	public void paintPath(RenderInfo render, Path path, StyleLink stylePath) {
		boolean localDebug = false;
		this.debug("srchType:" + path.getSrchOption().getSrchType(), localDebug);
		int nodeIndex = 0;
		for (PathElement node : path) {
			this.paintNode(render, node, stylePath, nodeIndex);
			this.debug("path: no(" + nodeIndex + "):" + node.toString(), localDebug);

			nodeIndex++;
		}
	}

	public void paintMouseOver(RenderInfo render, Node over, StyleLink styleOver) {
		// draw over node
		Map map = this.getMap();
		if (over != null) {
			StyleLink styleOverGray = new StyleLink(Color.gray, Color.black, 3, 255, false);
			this.paintNode(render, over, styleOver, -1);
			if (false) {
				StyleLink styleNear = new StyleLink(Color.pink, Color.pink, 1, 255, false );
				LinkList linkList = map.getLinkList();
				LinkGroup linkGroup = map.getLinkGroup(over);
				if (linkGroup != null) {
					Node toNode;
					for (Link link : linkGroup.getGroupLinkList(linkList)) {
						toNode = link.getToNode();
						this.paintNode(render, toNode, styleNear, -1);
					}
				}
			}
		}
	}

	private Map getMap() {
		return this.map;
	}

	private Map map;

}
