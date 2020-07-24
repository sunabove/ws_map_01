package com.ynhenc.droute;

public abstract class Mark implements Comparable<Mark> {

	public int compareTo(Mark n) {
		double d1 = this.f;
		double d2 = n.f;

		if (d1 < d2) {
			return -1; // Neither val is NaN, thisVal is smaller
		} else if (d1 > d2) {
			return 1; // Neither val is NaN, thisVal is larger
		}

		long thisBits = Double.doubleToLongBits(d1);
		long anotherBits = Double.doubleToLongBits(d2);

		return (thisBits == anotherBits ? 0 : // Values are equal
				(thisBits < anotherBits ? -1 : // (-0.0, 0.0) or (!NaN, NaN)
						1)); // (0.0, -0.0) or (NaN, !NaN)

	}

	public double getG() {
		return this.g;
	}

	public Mark getParent() {
		return parent;
	}

	public void setParent(Mark parent) {
		this.parent = parent;
	}

	public void setParent(Mark parent, double rotCost) {
		this.parent = parent;
		this.rotCost = rotCost;
	}

	public void setF(Node endNode, SrchOption srchOption) {
		double hr = srchOption.getHeuristicFactor();
		this.setG(srchOption);
		if (hr > 0) {
			this.setH(endNode, srchOption);
			f = g + hr * h;
		} else {
			this.h = 0;
			f = g;
		}
	}

	public void setG( double g ) {
		this.g = g;
		this.f = g;
	}

	private void setG(SrchOption srchOption) {
		g = parent.getG() + this.getCostTo(parent.getNode(), srchOption) + rotCost;
	}

	private void setH(Node endNode, SrchOption srchOption) {
		h = this.getCostTo(endNode, srchOption);
	}

	public double getCostTo(PathElement toNode, SrchOption srchOption) {
		return this.getNode().getCostTo(toNode, srchOption);
	}

	public Node getNode() {
		return this.node;
	}

	public void initNodeMark() {
		parent = null;
		f = g = h = 0;
		rotCost = 0;
	}

	public Mark(Node node) {
		this.node = node;
	}

	private Node node;
	private Mark parent = null;

	private double f;
	private double g;
	private double h;
	private double rotCost;
}
