package com.ynhenc.droute.map.link;

import java.awt.geom.Point2D;

import com.ynhenc.comm.db.QueryResult;
import com.ynhenc.droute.*;

public class GeoTurnnRecord extends ComLibRoute {

	public void setDataFromQueryResult(QueryResult qr) {
		this.turn$id = qr.getLong("turn$id") ;
		this.turn_cost = qr.getDouble("turn_cost");
		this.turn_rot_deg = qr.getDouble("turn_rot_deg");
		this.from_link_idx = qr.getInteger("from_link_idx");
		this.to_link_idx = qr.getInteger("to_link_idx");
		this.from_link$id = qr.getInteger("from_link$id");
		this.to_link$id = qr.getInteger("to_link$id");
		this.fr_roadrank$cd  = qr.getInteger( "fr_roadrank$cd" ) ;
		this.tr_roadrank$cd  = qr.getInteger( "tr_roadrank$cd" ) ;
	}

	public Link toLink( int index, NodeIndexer nodeIndexer ) {

		Node fromNode = nodeIndexer.get( this.from_link_idx );
		Node toNode = nodeIndexer.get( this.to_link_idx );

		if( fromNode == null || toNode == null ) {
			this.debug( "fromNode is null!!!" );
		}

		RotCost rotCost = new RotCost( this.fr_roadrank$cd, this.tr_roadrank$cd, this.turn_rot_deg );

		return Link.getLink( new IndexId( index, turn$id ) , fromNode, toNode, rotCost );
	}

	@Override
	public String toString() {
		String format = "GeoTurn:id(%d), from_link(%d, %s), to_link(%d, %s)";
		return String.format(format,  turn$id, from_link_idx , from_link$id, to_link_idx, to_link$id );
	}

	Long turn$id;

	Integer from_link_idx;
	Integer to_link_idx;
	Double turn_cost;
	Double turn_rot_deg;
	Integer turn_flag;
	Integer turn_angle;
	Integer from_link$id;
	Integer to_link$id;
	Integer fr_roadrank$cd ;
	Integer tr_roadrank$cd ;
}