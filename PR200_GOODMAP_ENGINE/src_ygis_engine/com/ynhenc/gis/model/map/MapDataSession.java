package com.ynhenc.gis.model.map;

import java.awt.*;
import java.util.*;

import com.ynhenc.gis.model.shape.*;
import com.ynhenc.gis.model.style.*;

public class MapDataSession extends MapDataObject {

	public Layer getLayerAddPoi( String layerName ) {

		Layer layer = this.getLayer( layerName );

		if( layer == null ) {
			LayerType layerType = LayerType.POINT ;
			int index = 0;
			layer = this.getLayerAfterStyling( layerName, layerType, index);
		}

		return layer;

	}

	public Layer getLayerAddLine( String layerName ) {

		Layer layer = this.getLayer( layerName );

		if( layer == null ) {
			LayerType layerType = LayerType.LINE ;
			int index = 0;
			layer = this.getLayerAfterStyling( layerName, layerType, index);
		}

		return layer;

	}

	public Layer getLayerAddArea( String layerName ) {

		Layer layer = this.getLayer( layerName );

		if( layer == null ) {
			LayerType layerType = LayerType.AREA ;
			int index = 0;
			layer = this.getLayerAfterStyling( layerName, layerType, index);
		}

		return layer;

	}

	private Layer getLayerAfterStyling( String layerName, LayerType layerType , int index ) {

		Layer layer = new LayerPoi();

		layer.setName( layerName );

		layer.setLyrType( layerType  );

		LayerStyleFactory.initLayerStyle( layer, index);

		this.addLayer( layer );

		return layer ;
	}

	@Override
	public void removeAllLayerList() {
		super.removeAllLayerList();
	}

	private void initMapData() {
	}

	public MapDataSession() {
		super();
		this.initMapData();
	}
}
