package com.ynhenc.gis.model.map;

import java.awt.Dimension;
import java.util.*;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.gis.*;
import com.ynhenc.gis.model.shape.Mbr;
import com.ynhenc.gis.model.shape.PntShort;

public class MapTileGroup extends GisComLib {

	public Mbr getMbr() {
		return mbr;
	}

	public MbrUnit getMbrUnit() {
		return mbrUnit;
	}

	public Dimension getPixelSize() {
		return pixelSize;
	}

	public LevelScale getLevelScale() {
		return levelScale;
	}

	public int getRowNo() {

		Mbr mbr = this.getMbr();
		MbrUnit mbrUnit = this.getMbrUnit();

		double rowNo = mbr.getHeight() / mbrUnit.getHeight() ;

		return (int) Math.ceil( rowNo );

	}

	public int getColNo() {

		Mbr mbr = this.getMbr();
		MbrUnit mbrUnit = this.getMbrUnit();

		double rowNo = mbr.getWidth() / mbrUnit.getWidth() ;

		return (int) Math.ceil( rowNo );

	}

	public MapTile getMapTile( int mapTileNo ) {

		int rowNo = this.getRowNo();
		int colNo = this.getColNo();

		int row = mapTileNo/colNo ;

		row = rowNo - row - 1 ;

		int col = mapTileNo%colNo ;

		Mbr mbr = this.getMbr();
		MbrUnit mbrUnit = this.getMbrUnit();


		PntShort centroid = mbr.getCentroid();

		double dx = - ( mbrUnit.getWidth()*colNo - mbr.getWidth() )/2.0 ;
		double dy = - ( mbrUnit.getHeight()*rowNo - mbr.getHeight() ) /2.0 ;

		double minX = mbr.getMinX() + mbrUnit.getWidth()*col + dx ;
		double maxX = minX + mbrUnit.getWidth();
		double minY = mbr.getMinY() +  mbrUnit.getHeight()*row + dy ;
		double maxY = minY + mbrUnit.getHeight();

		Mbr mbrTile = new Mbr( minX, minY, maxX, maxY );

		MapTile mapTile = new MapTile( mapTileNo, mbrTile );

		return mapTile ;

	}

	public MapTileGroup(LevelScale levelScale, Dimension pixelSize, Mbr mbr  ) {
		this.levelScale = levelScale;
		this.pixelSize = pixelSize;
		this.mbr = mbr ;
		this.mbrUnit = MbrUnit.getMbrUnit(levelScale, pixelSize);
	}

	private Mbr mbr;
	private MbrUnit mbrUnit ;

	private LevelScale levelScale;

	private Dimension pixelSize;

}
