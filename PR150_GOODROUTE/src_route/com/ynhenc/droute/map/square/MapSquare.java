package com.ynhenc.droute.map.square;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import javax.swing.JPanel;

import com.ynhenc.droute.*;
import com.ynhenc.droute.map.Map;

public class MapSquare extends Map {

	public static MapSquare newMapSquare(JPanel comp) throws Exception {

		MapSquare map = new MapSquare();

		Dimension size = comp.getSize();
		if (true) {
			map.debug("Pane size = " + size, true);
			Insets insets = comp.getInsets();
			size.width = size.width - insets.left - insets.right - 0;
			size.height = size.height - insets.top - insets.bottom - 0;
		}

		Dimension nodeSize = map.getNodeSize();

		int colNo = size.width / nodeSize.width;
		int rowNo = size.height / nodeSize.height;

		map.rowNo = rowNo;
		map.colNo = colNo;

		int indexSize = rowNo * colNo;

		map.createWorld(indexSize);

		return map;

	}

	@Override
	public NodeIndexer createNodeIndexer() {

		Dimension nodeSize = this.getNodeSize();
		int rowNo = this.rowNo;
		int colNo = this.colNo;

		NodeIndexer nodeIndexer = new NodeIndexer(rowNo * colNo);

		int nodeWidth = nodeSize.width;
		int nodeHeight = nodeSize.height;

		int index = 0;
		long id;

		for (int i = 0; i < rowNo; i++) {
			for (int k = 0; k < colNo; k++) {
				double x = k * nodeWidth + nodeWidth * 0.5F;
				double y = i * nodeHeight + nodeHeight * 0.5F;
				boolean walkable = Math.random() < 0.8F;
				id = index;
				nodeIndexer.add(Node.getNode(new IndexId(index, id), new GeoSquare((long) index, x, y) ));
				index++;
			}
		}

		return nodeIndexer;
	}

	private Link createOneLink(IndexId idxId, Node fromNode, Node toNode) {

		RotCost rotCost = new RotCost(0, 0, 0);

		return Link.getLink(idxId, fromNode, toNode, rotCost );

	}

	private void addLink(LinkList linkList, Node fromNode, Node toNode) {
		int index = linkList.getSize();
		long linkId = linkList.getSize();
		linkList.add(this.createOneLink(new IndexId(index, linkId), fromNode, toNode));
	}

	@Override
	public void createLinkList() {

		LinkList linkList = new LinkList();

		final int rowNo = this.rowNo;
		final int colNo = this.colNo;

		NodeIndexer nodeIndexer = this.getNodeIndexer();

		for (int row = 0, rLen = rowNo; row < rLen; row++) {
			for (int col = 0, cLen = colNo; col < cLen; col++) {
				this.createNeighborLink(row, col, linkList, rowNo, colNo, nodeIndexer);
			}
		}

		Collections.sort(linkList);

		if (false) {
			for (Link link : linkList) {
				this.debug(link.toString());
			}
		}

		this.linkList = linkList;

	}

	private void createNeighborLink(final int row, final int col, LinkList linkList, final int rowNo, final int colNo,
			NodeIndexer nodeIndexer) {
		int fromNodeIndex = row * colNo + col;
		Node fromNode = nodeIndexer.get(fromNodeIndex);
		int toNodeIndex = 0;
		Node toNode;
		for (int i = row - 1, iLen = i + 3; i < iLen; i++) {
			for (int k = col - 1, kLen = k + 3; k < kLen; k++) {
				if (-1 < i && i < rowNo && -1 < k && k < colNo) {
					toNodeIndex = i * colNo + k;
					if (fromNodeIndex != toNodeIndex) {
						toNode = nodeIndexer.get(toNodeIndex);
						if (toNode != null && toNode.getGeoObject().isWalkable()) {
							this.addLink(linkList, fromNode, toNode);
						}
					}
				}
			}
		}
	}

	@Override
	public Dimension getNodeSize() {
		if (this.nodeSize == null) {
			int scale = 3;
			this.nodeSize = new Dimension(4 * scale, 4 * scale);
		}
		return this.nodeSize;
	}

	@Override
	public void updateLinkRealTimeInfo() throws Exception {

	}

	private int rowNo;
	private int colNo;

}
