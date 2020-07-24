package com.ynhenc.droute;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.droute.render.*;

public class Link extends PathElement implements Comparable<Link> {

	@Override
	public Node getIndexNode() {
		return this.getToNode() ;
	}

	public Node getFromNode() {
		return fromNode;
	}

	public Node getToNode() {
		return toNode;
	}

	@Override
	public int compareTo(Link link) {
		int fromNodeIndex = this.getFromNode().getIndex();
		if(  fromNodeIndex != link.getFromNode().getIndex() ) {
			return GisComLib.compareToInteger( fromNodeIndex, link.getFromNode().getIndex() );
		} else {
			return GisComLib.compareToInteger( this.getToNode().getIndex(), link.getToNode().getIndex());
		}
	}

	public final double getLinkCost( SrchOption srchOption ) {
		return this.getFromNode().getCostTo( this.getToNode(), srchOption ) ;
	}

	public final double getRotCost( SrchOption srchOption ) {
		return this.rotCost.getRotTimeSec();
	}

	@Override
	public void paintPathElement(RenderInfo render, StyleLink style, int index ) {
		this.getFromNode().paintPathElement(render, style, index );
		this.getToNode().paintPathElement(render, style, index);
	}

	@Override
	public String toString() {
		return String.format( "Link: fnode(%d), tnode(%d), cost(%f)", this.getFromNode().getIndex(), this.getToNode().getIndex(), this.getLinkCost( null ) );
	}

	@Override
	public final double getCostTo( PathElement to, SrchOption srchOption ) {
		return this.getCostToLink( (Link) to, srchOption );
	}

	private double getCostToLink( Link toLink, SrchOption srchOption ) {
		if( this.getToNode() == toLink.getFromNode() ) {
			return toLink.getLinkCost( srchOption );
		} else {
			return  this.getToNode().getCostTo( toLink.getFromNode(), srchOption )
					+ toLink.getLinkCost( srchOption )
			;
		}
	}

	@Override
	public GeoObject getGeoObject() {
		return this.getFromNode().getGeoObject();
	}

	@Override
	public Mbr getMbr() {
		return Mbr.union( this.getFromNode().getMbr() , this.getToNode().getMbr() );
	}

	private Link( IndexId idxId , Node fromNode, Node toNode, RotCost rotCost ) {
		super( idxId );
		this.fromNode = fromNode;
		this.toNode = toNode;
		this.rotCost = rotCost;
	}

	public static Link getLink( IndexId idxId , Node fromNode, Node toNode, RotCost rotCost ) {
		return new Link(idxId, fromNode, toNode, rotCost );
	}

	private Node fromNode;
	private Node toNode;
	private RotCost rotCost ;
}
