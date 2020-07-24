package com.ynhenc.droute;

import java.awt.Dimension;

import com.ynhenc.droute.map.Map;
import com.ynhenc.droute.map.PathFinder;

public class MapAppData {

	public Vert getStartVert() {
		return startVert;
	}

	public void setStartVert(Vert startVert) {
		if (this.startVert != startVert) {
			this.startVert = startVert;
			this.initPathList();
		}
	}

	public Vert getEndVert() {
		return endVert;
	}

	public void setEndVert(Vert endVert) {
		if (this.endVert != endVert) {
			this.endVert = endVert;
			this.initPathList();
		}
	}

	public boolean isDrawLinkShape() {
		return drawLinkShape;
	}

	public void setDrawLinkShape(boolean drawLinkShape) {
		this.drawLinkShape = drawLinkShape;
	}

	public int getSrchTypeLatest() {
		return srchTypeLatest;
	}

	public SrchOption getSrchOption() {
		return srchOption;
	}

	public void setSrchOption(SrchOption srchOption) {
		this.srchOption = srchOption;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public Dimension getNodeSize() {
		Map map = this.getMap();
		if (map != null) {
			return map.getNodeSize();
		} else {
			return null;
		}
	}

	public void initPathList() {
		Path[] pathList = this.pathList;
		for (int i = 0, iLen = pathList.length; i < iLen; i++) {
			pathList[i] = null;
		}
	}

	private Node getStartNode() {
		return startNode;
	}

	private void setStartNode(Node start) {
		if (this.startNode != start) {
			this.startNode = start;
			this.initPathList();
		}
	}

	private Node getEndNode() {
		return endNode;
	}

	private void setEndNode(Node finish) {
		if (this.endNode != finish) {
			this.endNode = finish;
			this.initPathList();
		}
	}

	public Node getOverNode() {
		return overNode;
	}

	public void setOverNode(Node over) {
		this.overNode = over;
	}

	public Path getPath(int srchType) {
		return pathList[srchType];
	}

	public void setPath(SrchOption srchOption, Path path) {
		int srchType = srchOption.getSrchType();
		this.pathList[srchType] = path;
		this.srchTypeLatest = srchType;
	}

	public MapAppData() {
		this.srchOption = new SrchOption(0);
	}

	private Vert startVert;
	private Vert endVert;

	private Node startNode;
	private Node endNode;
	private SrchOption srchOption;
	private transient Node overNode;

	private transient Path[] pathList = new Path[3];
	private int srchTypeLatest = 0;
	boolean drawLinkShape = false;

	private Map map;

}
