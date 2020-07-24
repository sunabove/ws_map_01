package com.ynhenc.kmlexport;

import com.ynhenc.comm.db.*;

public class RecordLink {

	public RecordLink (QueryResult qr) {
		
		this.LINK_ID					= qr.getInteger("LINK$ID");
		this.ROAD_ROADRANK_CD			= qr.getInteger("ROAD#ROADRANK$CD");
		this.ROADRANK_DESC				= qr.getString("ROADRANK_DESC");
		this.ROAD_ID					= qr.getInteger("ROAD$ID");
		this.ROAD_NO					= qr.getInteger("ROAD_NO");
		this.ROAD_NAME					= qr.getString("ROAD_NAME");
		this.LINK_F_NODE_ID 			= qr.getInteger("LINK_F#NODE$ID");
		this.LINK_T_NODE_ID 			= qr.getInteger("LINK_T#NODE$ID");
		this.LINKUSE_CD 				= qr.getInteger("LINKUSE$CD");
		this.LINKUSE_DESC 				= qr.getString("LINKUSE_DESC");
		this.LINK_LANE_NO 				= qr.getInteger("LINK_LANE_NO");
		this.RAMPTYPE_CD 				= qr.getInteger("RAMPTYPE$CD");
		this.RAMPTYPE_DESC 				= qr.getString("RAMPTYPE_DESC");
		this.LINK_SPD_MAX_KMPH 			= qr.getInteger("LINK_SPD_MAX_KMPH");
		this.LINK_REST_01_VHCLTYPE_CD 	= qr.getInteger("LINK_REST_01#VHCLTYPE$CD");
		this.VHCLTYPE_DESC 				= qr.getString("VHCLTYPE_DESC");
		this.LINK_REST_W_KG 			= qr.getInteger("LINK_REST_W_KG");
		this.LINK_REST_H_M 				= qr.getInteger("LINK_REST_H_M");
		this.FN_NODE_GX					= qr.getDouble("FN_NODE_GX");
		this.FN_NODE_GY					= qr.getDouble("FN_NODE_GY");
		this.TN_NODE_GX					= qr.getDouble("TN_NODE_GX");
		this.TN_NODE_GY					= qr.getDouble("TN_NODE_GY");
		this.LINKSHP_GEOM				= qr.getString("LINKSHP_GEOM");
		
		
		
	}
	
	Integer		LINK_ID = 0;
	Integer		ROAD_ROADRANK_CD = 0;
	String		ROADRANK_DESC = "";
	Integer		ROAD_ID = 0;
	Integer		ROAD_NO = 0;
	String		ROAD_NAME = "";
	Integer		LINK_F_NODE_ID = 0;
	Integer		LINK_T_NODE_ID = 0;
	Integer		LINKUSE_CD = 0;
	String		LINKUSE_DESC = "";
	Integer		LINK_LANE_NO = 0;
	Integer		RAMPTYPE_CD = 0;
	String		RAMPTYPE_DESC = "";
	Integer		LINK_SPD_MAX_KMPH = 0;
	Integer		LINK_REST_01_VHCLTYPE_CD = 0;
	String		VHCLTYPE_DESC = "";
	Integer		LINK_REST_W_KG = 0;
	Integer		LINK_REST_H_M = 0;
	Double		FN_NODE_GX = 0.0;
	Double		FN_NODE_GY = 0.0;
	Double		TN_NODE_GX = 0.0;
	Double		TN_NODE_GY = 0.0;
	String		LINKSHP_GEOM = "";
}
