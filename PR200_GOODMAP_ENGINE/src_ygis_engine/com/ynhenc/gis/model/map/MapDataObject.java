package com.ynhenc.gis.model.map;

import java.awt.*;
import java.util.*;

import com.ynhenc.comm.ArrayListString;
import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.*;
import com.ynhenc.gis.model.mapobj.*;
import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.model.style.Style_00_Object;

public abstract class MapDataObject extends GisComLib {

	public int getLayerSize() {
		return this.getLayerList().size();
	}

	public final void addLayer(final Layer layer) {
		this.getLayerList().add(layer);
	}

	public boolean moveLayerTop_Old(Layer layer) {
		return this.moveLayer(layer, -this.getLayerSize());
	}

	public boolean moveLayerUp_Old(Layer layer) {
		return this.moveLayer(layer, +1);
	}

	public boolean moveLayerDown_Old(Layer layer) {
		return this.moveLayer(layer, -1);
	}

	public boolean moveLayerBottom_Old(Layer layer) {
		return this.moveLayer(layer, this.getLayerSize());
	}

	private boolean moveLayerOld(Layer layer, int increment) {
		LayerList layerList = this.getLayerList();
		final int size = layerList.size();
		final int index = layerList.indexOf(layer);
		if (index > -1) {
			layerList.remove(index);
			int indexNew = index + increment;
			indexNew = indexNew < 0 ? 0 : indexNew;
			indexNew = indexNew > size - 1 ? size - 1 : indexNew;
			layerList.add(indexNew, layer);
			return true;
		}
		return false;
	}

	public boolean moveLayerUp(Layer layer) {
		int newLayerNo = layer.getLayerNo() + 1;
		return this.moveLayer(layer, newLayerNo );
	}

	public boolean moveLayerDown(Layer layer) {
		int newLayerNo = layer.getLayerNo() - 1;
		return this.moveLayer(layer, newLayerNo );
	}

	public boolean moveLayerTop(Layer layer) {
		int newLayerNo = this.getLayerList().getMinLayerNo() - 1 ;
		if( newLayerNo < 0 ) {
			newLayerNo = 0;
		}
		return this.moveLayer(layer, newLayerNo );
	}

	public boolean moveLayerBottom(Layer layer) {
		int newLayerNo = this.getLayerList().getMaxLayerNo() + 1;
		if( newLayerNo < 0 ) {
			newLayerNo = 0;
		}
		return this.moveLayer(layer, newLayerNo );
	}

	private boolean moveLayer(Layer moveLayer, int newLayerNo) {
		if (moveLayer.getLayerNo() != newLayerNo) {
			moveLayer.setLayerNo(newLayerNo);
			LayerList layerList = this.getLayerList();
			layerList.remove( moveLayer );
			int layerNo = 0;
			for( Layer layer: layerList ) {
				if( layerNo == newLayerNo ) {
					layerNo ++;
				}
				layer.setLayerNo(layerNo);
				layerNo ++;
			}
			layerList.add(moveLayer);
			this.sortLayers();
			return true;
		} else {
			return false;
		}
	}

	public boolean removeLayer(Layer layer) {
		return this.getLayerList().remove(layer);
	}

	public Mbr getMbrPhys() {
		return this.getMbrTopLevel();
	}

	public Mbr getMbrPhysScr(Dimension pixelSize) {
		Mbr mbrPhys = this.getMbrPhys();
		Projection projection = Projection.getProject(mbrPhys, pixelSize);
		return projection.getMbrScreen();
	}

	public Mbr getMbrTopLevel() {
		return this.getMbrTopLevelWithMargin(0.06F);
	}

	public Mbr getMbrTopLevelWithMargin(double marginRatio) {
		Mbr mbr = null;
		LayerList layerList = this.getLayerList();
		for (Layer layer : layerList) {
			mbr = Mbr.union(mbr, layer.getMbr());
		}

		if (mbr != null && marginRatio > 0 && marginRatio < 1) {
			return Mbr.getMbrWithMargin(mbr, marginRatio);
		}

		if (mbr == null) {
			mbr = new Mbr(0, 0, 1, 1);
		}

		return mbr;
	}

	public Layer getLayer(int index) {
		if (index > -1) {
			LayerList layerList = this.getLayerList();

			if (index < layerList.size()) {
				return layerList.get(index);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public ArrayListString getLayerNameList() {
		ArrayListString nameList = new ArrayListString();
		LayerList layerList = this.getLayerList();
		for (Layer layer : layerList) {
			nameList.add(layer.getName());
		}
		return nameList;
	}

	public void sortLayers() {
		this.getLayerList().sortLayers();
	}

	public LayerList getLayerList() {
		if (this.layerListNew == null) {
			this.layerListNew = new LayerList();
		}
		return this.layerListNew;
	}

	public Layer getLayer(String layerName) {
		LayerList layerList = this.getLayerList();

		for (Layer layer : layerList) {
			if (layer.getName().equalsIgnoreCase(layerName)) {
				return layer;
			}
		}

		return null;
	}

	public LayerList getLayerListAtZoomLevel(int zoomLevel, Projection projection) {
		boolean localDebug = false;

		LayerList layerList_Clone = new LayerList();

		LayerList layerList = this.getLayerList();

		Mbr projectionMbr = projection.getMbr();
		Mbr projectionMbrScr = projection.getMbrScreen();
		Mbr layerMbr;

		for (Layer layer : layerList) {
			if (layer.isLayerEnabled() && layer.isSelectedAtZoomLevel(zoomLevel)) {
				if (projection != null) {
					layerMbr = layer.getMbr();
					if (Mbr.isIntersects(projectionMbr, layerMbr) || Mbr.isIntersects(projectionMbrScr, layerMbr)) {
						// 레이어와 프로젝션 영역이 겹칠 때만 드로잉 한다.
						layerList_Clone.add(layer);
					} else {
						this.debug("LAYER[" + layer.getName() + " ] is out of the projection MBR!", localDebug);
					}
				} else {
					layerList_Clone.add(layer);
				}
			}
		}

		return layerList_Clone;
	}

	public void removeAllLayerList() {
		this.layerListNew = new LayerList();
	}

	private void init() {
		this.layerListNew = new LayerList();
	}

	protected MapDataObject() {
		this.init();
	}

	private LayerList layerListNew;

}
