package com.ynhenc.droute.map.link;

import java.awt.geom.Point2D;

import com.ynhenc.comm.db.QueryResult;
import com.ynhenc.droute.GeoObject;
import com.ynhenc.droute.IndexId;
import com.ynhenc.droute.Node;
import com.ynhenc.droute.RoadRank;
import com.ynhenc.droute.Vert;

public class GeoLinkRecord {

	public void setDataFromQueryResult(QueryResult qr) {
		this.link_idx = qr.getInteger("link_idx");
		this.link$id = qr.getLong("link$id");
		this.road_roadrank$cd = qr.getInteger("road#roadrank$cd");
		this.road_no = qr.getInteger("road_no");

		this.fn_node = Vert.getVert(qr.getDouble("fn_node_gx"), qr.getDouble("fn_node_gy"));
		this.tn_node = Vert.getVert(qr.getDouble("tn_node_gx"), qr.getDouble("tn_node_gy"));

		this.link_length_m = qr.getDouble("link_length_m");
		this.link_spd_max_kmph = qr.getDouble("link_spd_max_kmph");
	}

	public Node toNode( int index ) {
		RoadRank roadRank = RoadRank.getRoadRank( this.road_roadrank$cd );
		GeoLink geoObject = GeoLink.getGeoLink( this.link$id, this.fn_node, this.tn_node , this.link_length_m, roadRank );
		return Node.getNode( new IndexId( index, link_idx ) , geoObject );
	}

	@Override
	public String toString() {
		String format = "GeoLink[%d]: idx(%d), id(%d), road_rank(%d), road_no(%d)";
		format += ", fn_gx(%f), fn_gy(%f), tn_gx(%f), tn_gy(%f), len_m(%f), spd_max_kmph(%f)";
		return String.format(format, link_idx, link_idx, link$id, road_roadrank$cd, road_no, fn_node.getX(), fn_node.getY(),
				tn_node.getX(), tn_node.getY(), link_length_m, link_spd_max_kmph);
	}

	Integer link_idx;
	Long link$id;
	Integer road$id;
	Integer road_roadrank$cd;
	Integer road_no;
	Vert fn_node ;
	Vert tn_node ;
	Double link_length_m;
	Double link_spd_max_kmph;

}