package com.ynhenc.droute;

import com.ynhenc.droute.render.*;

public abstract class PathElement implements IndexInterface, MbrInterface {

	@Override
	public final int hashCode() {
		return this.getIndex();
	}

	public final int getIndex() {
		return this.index;
	}

	public final long getObjectId() {
		return this.id;
	}

	public double getSelfCost(SrchOption srchOption) {
		return this.getGeoObject().getSelfCost(srchOption);
	}

	public boolean isSameOrigin( PathElement toPathElement ) {
		return this.getGeoObject().isSameOrigin( toPathElement.getGeoObject());
	}

	public boolean isSameDest( PathElement toPathElement ) {
		return this.getGeoObject().isSameDest( toPathElement.getGeoObject());
	}

	public abstract Node getIndexNode();

	public abstract double getCostTo(PathElement to, SrchOption srchOption);

	public abstract GeoObject getGeoObject();

	public abstract void paintPathElement(RenderInfo render, StyleLink style, int index);

	public PathElement(IndexId idxId) {
		super();
		this.index = idxId.getIndex();
		this.id = idxId.getId();
	}

	private int index;
	private long id;

}
