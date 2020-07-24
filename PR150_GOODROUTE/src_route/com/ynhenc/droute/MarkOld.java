package com.ynhenc.droute;

public abstract class MarkOld implements Comparable<MarkOld> {

	public int compareTo(MarkOld n) {
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

	public MarkOld getParent() {
		return parent;
	}

	public void setParent(MarkOld parent) {
		this.parent = parent;
	}

	public void setParent(MarkOld parent, double rotCost  ) {
		this.parent = parent;
		this.rotCost = rotCost ;
	}

	public void setF(MarkOld finish, SrchOption srchOption ) {
		double hr = srchOption.getHeuristicFactor();
		this.setG( srchOption );
		if( hr > 0 ) {
			this.setH( finish , srchOption );
			f = g + hr*h;
		} else {
			this.h = 0;
			f = g ;
		}
	}

	public void setG( SrchOption srchOption ) {
		g = parent.getG() + this.getCostTo(parent , srchOption ) + rotCost ;
	}

	public void setH(MarkOld finish, SrchOption srchOption ) {
		h = this.getCostTo(finish, srchOption );
	}

	public double getCostTo(MarkOld toPathElement, SrchOption srchOption) {
		return this.getPathElement().getCostTo(toPathElement.getPathElement() , srchOption );
	}

	public PathElement getPathElement() {
		return this.pathElement;
	}

	public void initNodeMark() {
		parent = null;
		f = g = h = 0;
		rotCost = 0;
	}

	public MarkOld( PathElement pathElement) {
		this.pathElement = pathElement ;
	}

	private PathElement pathElement;
	private MarkOld parent = null;

	private double f;
	private double g;
	private double h;
	private double rotCost ;
}
