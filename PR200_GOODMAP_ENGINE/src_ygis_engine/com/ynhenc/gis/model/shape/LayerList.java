package com.ynhenc.gis.model.shape;

import java.util.*;

import com.ynhenc.comm.ArrayListEx;

public class LayerList extends ArrayListEx<Layer> {

	@Override
	public boolean add(Layer layer) {
		if (!this.isInsertedLayer(layer)) {
			int layerNo = layer.getLayerNo();
			if (layerNo < 0) {
				layer.setLayerNo(this.getMaxLayerNo() + 1);
			}
			return super.add(layer);
		} else {
			return false;
		}
	}

	public boolean isInsertedLayer(final Layer layer) {
		LayerList layerList = this;
		for (int i = 0; i < layerList.size(); i++) {
			if (layerList.get(i) == layer) {
				return true;
			}
		}
		return false;
	}

	public void sortLayers() {
		Collections.sort(this);
	}

	public int getMaxLayerNo() {
		if (this.getSize() > 0) {
			int max = -Integer.MAX_VALUE;
			for (Layer layer : this) {
				max = max > layer.getLayerNo() ? max : layer.getLayerNo();
			}
			return max;
		} else {
			return -1;
		}
	}

	public int getMinLayerNo() {
		if (this.getSize() > 0) {
			int min = + Integer.MAX_VALUE;
			for (Layer layer : this) {
				min = min < layer.getLayerNo() ? min : layer.getLayerNo();
			}
			return min;
		} else {
			return -1;
		}
	}

}
