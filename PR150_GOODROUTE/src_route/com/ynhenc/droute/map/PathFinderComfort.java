package com.ynhenc.droute.map;

import java.util.*;

import com.ynhenc.droute.*;

public class PathFinderComfort extends PathFinderDist {

	@Override
	public void setParent( Mark toNodeMark, Mark currNodeMark, Link link, Node endNode, SrchOption srchOption ) {
		double rotCost = link.getRotCost( srchOption );
		toNodeMark.setParent(currNodeMark, rotCost );
		toNodeMark.setF(endNode, srchOption);
	}

	@Override
	public boolean isBetterWay(Mark toNodeMark, Mark currNodeMark, Link link, SrchOption srchOption) {
		double rotCost = link.getRotCost( srchOption );
		return toNodeMark.getG() > rotCost + currNodeMark.getG() + currNodeMark.getCostTo(toNodeMark.getNode(), srchOption);
	}

	protected PathFinderComfort() {
	}

}
