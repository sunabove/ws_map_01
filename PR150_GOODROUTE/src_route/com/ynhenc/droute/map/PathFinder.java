package com.ynhenc.droute.map;

import com.ynhenc.comm.*;
import com.ynhenc.comm.db.*;
import com.ynhenc.droute.*;

public abstract class PathFinder extends GisComLib {

	public abstract Path getPathImpl(final Map map, final Node startNode, final Node endNode, SrchOption srchOption);

	public static PathFinder getNewPathFinder(SrchOption srchOption) {
		PathFinder pathFinder = null;
		if (srchOption.getSrchType() == 2) {
			pathFinder = new PathFinderComfort();
		} else {
			pathFinder = new PathFinderDist();
		}
		pathFinder.debug("PathFinder = " + pathFinder.getClass().getSimpleName(), true);
		return pathFinder;
	}

	public Path getPath(final Map map, final Node startNode, final Node endNode, SrchOption srchOption) {
		long start = System.currentTimeMillis();

		Path path = this.getPathImpl(map, startNode, endNode, srchOption);

		path.setQueryTime_Sec((System.currentTimeMillis() - start) / 1000.0);
		path.setQueryNo(QUERY_NO++);

		this.debug("Path Query Time = " + path.getQueryTime_Sec(), true);

		return path;
	}

	public static Path searchPath(MapAppData mapAppData, SrchOption srchOption) {

		Vert startVert = mapAppData.getStartVert();
		Vert endVert = mapAppData.getEndVert();
		Map map = mapAppData.getMap();

		if (map != null) {
			if (startVert != null && endVert != null && (startVert != endVert)) {
				Node startNode = map.getNodeNearest(startVert, Find.Mode.START);
				Node endNode = map.getNodeNearest(endVert, Find.Mode.END);
				PathFinder pathFinder = map.getPathFinder(srchOption);
				Path path = pathFinder.getPath(map, startNode, endNode, srchOption);
				mapAppData.setPath(srchOption, path);
				return path;
			} else {
				mapAppData.initPathList();
				return null;
			}
		} else {
			return null;
		}

	}

	protected Path getMakePath(Mark mark, SrchOption srchOption, Node startNode) {
		Path pathReversed = new Path(srchOption);
		while (mark != null) { // trace parents to get path
			pathReversed.add(mark.getNode());
			mark = mark.getParent();
		}

		if (!pathReversed.contains(startNode)) {
			pathReversed.add(startNode);
		}

		if (true) { // correct path element order
			Path path = new Path(srchOption);
			Node node;
			for (int i = 0, iLen = pathReversed.getSize(); i < iLen; i++) {
				node = pathReversed.get(iLen - i - 1);
				path.add(node);
			}

			int sameIndex = -1;

			for (int i = 1, iLen = path.getSize(); i < iLen; i++) {
				node = path.get(i);
				if (node.isSameOrigin(startNode)) {
					sameIndex = i;
				}
			}

			if (sameIndex > -1) {
				int index;
				for (int i = 0, iLen = sameIndex; i < iLen; i++) {
					index = sameIndex - i - 1;
					if (index > -1) {
						path.remove(index);
					}
				}
			}

			if( false && ! path.contains( startNode)) {
				path.add( 0, startNode );
			}

			return path;
		} else {
			return pathReversed;
		}
	}

	private static int QUERY_NO = 0;

}
