package com.ynhenc.droute.map;

import java.util.*;

import com.ynhenc.droute.*;

public class PathFinderDist extends PathFinder {

	public final void initPathFinder() {
		this.pathElemMarkList = new Hashtable<Integer, MarkNode>();
	}

	@Override
	public Path getPathImpl(final Map map, final Node startNode, final Node endNode, SrchOption srchOption) {

		this.initPathFinder();

		LinkList mapLinkList = map.getLinkList();

		final MarkNode startNodeMark = this.getNodeMark(startNode);
		// final MarkNode endNodeMark = this.getNodeMark(endNode);

		MarkList openList = new MarkList(); // open list
		MarkList closeList = new MarkList(); // close list
		// add start node to open Vector
		openList.add(startNodeMark);

		Mark currNodeMark = null;

		int lookupNo = 0;

		LinkGroup linkGroup = null;

		while (lookupNo < 1 || openList.getSize() > 0) {
			// loop until no open nodes left

			if (lookupNo > 0) {
				Collections.sort(openList);
				// nodes are comparable, using f values
				currNodeMark = openList.remove(0);
				// take the open node with lowest f
				closeList.add(currNodeMark);
				// move it to the closed Vector
				if ( currNodeMark.getNode() == endNode || currNodeMark.getNode().isSameDest(endNode)) {
					// if we've found the end, quit!
					break;
				}
				linkGroup = map.getLinkGroup(currNodeMark.getNode().getIndexNode());
			} else {
				linkGroup = map.getLinkGroup(startNode.getIndexNode());
				Mark toNodeMark;
				for (Link link : linkGroup.getGroupLinkList(mapLinkList)) {
					toNodeMark = this.getNodeMark(link.getToNode());
					toNodeMark.setG( toNodeMark.getNode().getSelfCost(srchOption) );
				}
			}

			// otherwise, loop through neighbours
			Mark toNodeMark;
			for (Link link : linkGroup.getGroupLinkList(mapLinkList)) {
				toNodeMark = this.getNodeMark(link.getToNode());
				if ( ! closeList.contains(toNodeMark) ) {
					if (!openList.contains(toNodeMark)) {
						openList.add(toNodeMark);
						if (lookupNo > 0 ) {
							this.setParent(toNodeMark, currNodeMark, link, endNode, srchOption);
						}
					} else if (this.isBetterWay(toNodeMark, currNodeMark, link, srchOption)) {
						// if this is a better way to get there than last time,
						// we looked at it .
						this.setParent(toNodeMark, currNodeMark, link, endNode, srchOption);
					}
				}
			}

			lookupNo++;
		}

		return this.getMakePath(currNodeMark, srchOption , startNode );
	}

	public void setParent(Mark toNodeMark, Mark currNodeMark, Link link, Node endNode, SrchOption srchOption) {
		toNodeMark.setParent(currNodeMark);
		toNodeMark.setF(endNode, srchOption);
	}

	public boolean isBetterWay(Mark toNodeMark, Mark currNodeMark, Link link, SrchOption srchOption) {
		return toNodeMark.getG() > currNodeMark.getG() + currNodeMark.getCostTo(toNodeMark.getNode(), srchOption);
	}

	public final MarkNode getNodeMark(Node node) {
		Integer index = node.getIndex();
		MarkNode mark = this.pathElemMarkList.get(index);

		if (mark == null) {
			mark = MarkNode.getNewMark(node);
			this.pathElemMarkList.put(index, mark);
		}

		return mark;
	}

	protected PathFinderDist() {
	}

	private Hashtable<Integer, MarkNode> pathElemMarkList;

}
