
package com.ynhenc.kmlexport;

import java.util.List;
import java.util.ArrayList;

import org.boehn.kmlframework.kml.Kml;
import org.boehn.kmlframework.kml.Document;
import org.boehn.kmlframework.kml.Style;
import org.boehn.kmlframework.kml.IconStyle;
import org.boehn.kmlframework.kml.LabelStyle;
import org.boehn.kmlframework.kml.LineStyle;
import org.boehn.kmlframework.kml.Folder;
import org.boehn.kmlframework.kml.Placemark;
import org.boehn.kmlframework.kml.LineString;
import org.boehn.kmlframework.kml.Point;

import com.ynhenc.comm.db.QueryResult;
import com.ynhenc.comm.db.SQL;


public class LinkListExporter extends ComLibRoute{
	
	Boolean 		checkRows = true;
	
	Integer			rowCount = 0;
	Integer			rowIndex = 0;
	
	Integer			preRoadRankCD = 0;
	Integer			preRoadNo = 0;
	
	// Folder for count 100
	Integer 		preCount100	= 0;
	Integer 		totalCount = 0;
	Integer 		folderNumber = 1;
	
	
	RecordLinkList 	linkList = null;
	
	public String getSqlText() throws Exception {

		String fileName = "sql_link_001.sql";

		SQL sql = this.getSQL(fileName);
		String sqlText = sql.getSqlText();

		// What purpose..?
		if (true) {
			sqlText = sqlText.replaceAll("&ROW_NUM", "" + 5);
		}

		return sqlText;
	}
	
	public QueryResult getQueryResult() throws Exception {
		
		// returns oracle sql query result
		String sqlText = this.getSqlText();
		QueryResult qr = this.getQueryResult(sqlText);

		return qr;
	}
	
	public void makeKML(String kmlFilePath) throws Exception {
		
		// Create a new KML Document
		Kml kml = new Kml ();
		
		// Add a Document to the KML
		Document document = new Document();
		kml.setFeature(document);

		// TOPIS Node list query result set
		QueryResult qr = this.getQueryResult();
		
		// Add RecordNodeLisst
		this.linkList = new RecordLinkList();

		while (qr.hasNext()) {
			RecordLink link = new RecordLink(qr);
			this.debug("Link = " + link);
			this.linkList.add(link);
		}
		
		this.rowCount = this.linkList.getSize();
		RecordLink link = this.linkList.get(this.rowIndex);
		
		while (true)
		{
			this.debug("test1");
			
			// Loop end condition.
			link = this.linkList.get(this.rowIndex);
			
			if (!this.checkRows || link == null) {
				break;
			}
						
			Integer roadRankCD = link.ROAD_ROADRANK_CD;
			if (this.preRoadRankCD != roadRankCD)
			{				
				this.preRoadRankCD = roadRankCD;
				
				// Make the style for ROADRANK$CD
				String roadRankStyle = makeStyleForRoadRank(roadRankCD, document);

				// Make the folder for ROADRANK#CD
				makeFolderForRoadRank(qr, document, roadRankStyle);
			}
			else 
			{
				
			}
			
		}
					
		// Generate the kml file;
		kml.createKml(kmlFilePath);	
	}
	
	public String makeStyleForRoadRank(Integer roadRankCD, Document document) throws Exception {
		
		// Make style
		String lineColor = "";
		String styleID = "";
		Double lineWidth = 2.0;
		Style roadRankStyle = new Style();
		LineStyle lineStyle = new LineStyle();
		
		lineColor = "ffffffff";
		
		lineStyle.setColor(lineColor);
	//	lineStyle.setColorMode(colorMode);
		lineStyle.setWidth(lineWidth);
		
		styleID = "road_rank_" + roadRankCD;
		
		
		roadRankStyle.setId(styleID);
		roadRankStyle.setLineStyle(lineStyle);
		
		document.addStyleSelector(roadRankStyle);
		
		return styleID; 
	} 
	
	public void makeFolderForRoadRank(QueryResult qr, Document document, String styleID) throws Exception {

		// Make folder
		RecordLink link = this.linkList.get(this.rowIndex);
		Integer roadRankCD = link.ROAD_ROADRANK_CD;
		String roadRankDesc = link.ROADRANK_DESC;
		
		// Make Folder According to ROAD#ROADRANK$CD
		String folderName = roadRankDesc + "(" + roadRankCD + ")";
	//	String folderDescription = roadRankDesc;
		
		Folder folderRoadRankCD = new Folder();
		folderRoadRankCD.setName(folderName);
	//	folderRoadRankCD.setDescription(folderDescription);
		
		while (true)
		{
			this.debug("tset2");

			link = this.linkList.get(this.rowIndex);	
			
			if (link == null) 
			{
				// Setting
				document.addFeature(folderRoadRankCD);
				return;				
			}
			
			roadRankCD = link.ROAD_ROADRANK_CD;
			
			// Condition of return
			if (this.preRoadRankCD != roadRankCD)
			{
				// Setting
				document.addFeature(folderRoadRankCD);
				return;
			}
			
			//
			Integer roadNo = link.ROAD_NO;
			if (this.preRoadNo != roadNo)
			{
				this.preRoadNo = roadNo;
				MakeFolderForRoadNo(qr, folderRoadRankCD, styleID);
			}
			else 
			{
				
			}
			
		}

	}
	
	public void MakeFolderForRoadNo(QueryResult qr, Folder folderRoadRankCD, String styleID) throws Exception {
		
		// Make Folder
		RecordLink link = this.linkList.get(this.rowIndex);
		Integer roadNo = link.ROAD_NO;
		String roadName = link.ROAD_NAME;
		
		// Make Folder According to ROAD_NO
		String folderName = roadName + "(" + roadNo + ")";
		
		Folder folderRoadNo = new Folder();
		folderRoadNo.setName(folderName);
		
		while (true) 
		{
			this.debug("test3");

			link = this.linkList.get(this.rowIndex);
			
			if (link == null) 
			{
				// Setting
				folderRoadRankCD.addFeature(folderRoadNo);
				return;	
			}
			
			Integer roadRankCD = link.ROAD_ROADRANK_CD;
			roadNo = link.ROAD_NO;
			
			// condition of return
			if (this.preRoadNo != roadNo || this.preRoadRankCD != roadRankCD) {
				// Setting
				folderRoadRankCD.addFeature(folderRoadNo);
				this.preCount100 = 0;
				this.folderNumber = 1;
				return;
			}
			
			// count100  
			if (this.preCount100 % 100 == 0) 
			{
				MakeFolderForCount100(qr, folderRoadNo, styleID);
			}
			else
			{
				
			}
			
			// Insert Placemark
//			this.insertPlacemark(qr, folderRoadNo, styleID);
//
//			// Record index add
//			this.rowIndex++;
//			
//			this.debug("rowIndex = " + this.rowIndex + ", rowCount = " + this.rowCount);
//			
//			// loop end.
//			if (this.rowIndex == this.rowCount)
//			{
//				this.checkRows = false;
//			}
		
		}
		
	}
	

	public void MakeFolderForCount100(QueryResult qr, Folder folderNodeType, String styleID) throws Exception {

		String folderName = "";
		
		folderName = "" + this.folderNumber++;
		
		Folder folderCount100 = new Folder();
		folderCount100.setName(folderName);
		
		while (true)
		{
			this.debug("test5");
			//
			RecordLink link = this.linkList.get(this.rowIndex);
			
			if (link == null) 
			{
				// Setting
				this.preCount100 = 0;
				folderNodeType.addFeature(folderCount100);
				return;			
			}
			
			Integer roadRankCD = link.ROAD_ROADRANK_CD;
			Integer roadNo = link.ROAD_NO;
			
			if ( preCount100 >= 100 || this.preRoadNo != roadNo || this.preRoadRankCD != roadRankCD)	
			{
				this.preCount100 = 0;
				folderNodeType.addFeature(folderCount100);
				return;
			}
			
			// Insert Placemark
			this.insertPlacemark(qr, folderCount100, styleID);
			this.preCount100++;
			this.totalCount++;

			// Record index add
			this.rowIndex++;
			
			this.debug("rowIndex = " + this.rowIndex + ", rowCount = " + this.rowCount);
			
			// loop end.
			if (this.rowIndex == this.rowCount)
			{
				this.checkRows = false;
			}
		}
	}
	
	/*
	public void insertPlacemark(QueryResult qr, Folder folder, String styleID) throws Exception {
		
		// Create a Placemark for the TOPIS node list
		RecordLink row 			= this.linkList.get(this.rowIndex);
		
		// placemark name
		Integer linkID			= row.LINK_ID;
		Integer	link_f_node		= row.LINK_F_NODE_ID;
		Integer	link_t_node		= row.LINK_T_NODE_ID;
		
		// placemark description
		String 	linkUse 		= row.LINKUSE_DESC;
		Integer lane			= row.LINK_LANE_NO;
		String 	ramptype		= row.RAMPTYPE_DESC;
		Integer	ramptypeCd		= row.RAMPTYPE_CD;
		Integer	maxSpd			= row.LINK_SPD_MAX_KMPH;
		String	vhcltypeDesc	= row.VHCLTYPE_DESC;
		Integer	vhcltypeCd		= row.LINK_REST_01_VHCLTYPE_CD;
		Integer	restkg			= row.LINK_REST_W_KG;
		Integer	resth			= row.LINK_REST_H_M;
		
		// geometry
		double 	pointFX 		= row.FN_NODE_GX;
		double 	pointFY 		= row.FN_NODE_GY;
		double 	pointTX 		= row.TN_NODE_GX;
		double 	pointTY 		= row.TN_NODE_GY;
		
		Point pointF = new Point();
		pointF.setLongitude(pointFX);
		pointF.setLatitude(pointFY);

		Point pointT = new Point();
		pointT.setLongitude(pointTX);
		pointT.setLatitude(pointTY);
		
		LineString line = new LineString();
		
		ArrayList<Point> coord = new ArrayList<Point>();
		
		coord.add(pointF);
		coord.add(pointT);
		
		line.setCoordinates(coord);
		
		String placemarkName = "" + linkID + ":" + link_f_node + "-" + link_t_node;
		String placemarkDescription = linkUse + ", " + lane + ", " + ramptype 
									+ "(" + ramptypeCd + "), " + maxSpd + ", " 
									+ vhcltypeDesc + "(" + vhcltypeCd + "), " + restkg  + ", " + resth;
		String styleUrl = "#" + styleID;
		
		Placemark link = new Placemark(placemarkName);		// Insert value of node_name:node_id for Placemark's name
		link.setDescription(placemarkDescription);			// Insert value of turnp_desc:nodesign_desc for Placemark's description
		link.setStyleUrl(styleUrl);							// Insert value of styleUrl
		link.setGeometry(line);								// Insert value of geometry
	
		// Add the placemark to the Folder
		folder.addFeature(link);
	}
	*/
	
	public void insertPlacemark(QueryResult qr, Folder folder, String styleID) throws Exception {
		
		// Create a Placemark for the TOPIS node list
		RecordLink row 			= this.linkList.get(this.rowIndex);
		
		// placemark name
		Integer linkID			= row.LINK_ID;
		Integer	link_f_node		= row.LINK_F_NODE_ID;
		Integer	link_t_node		= row.LINK_T_NODE_ID;
		
		// placemark description
		String 	linkUse 		= row.LINKUSE_DESC;
		Integer lane			= row.LINK_LANE_NO;
		String 	ramptype		= row.RAMPTYPE_DESC;
		Integer	ramptypeCd		= row.RAMPTYPE_CD;
		Integer	maxSpd			= row.LINK_SPD_MAX_KMPH;
		String	vhcltypeDesc	= row.VHCLTYPE_DESC;
		Integer	vhcltypeCd		= row.LINK_REST_01_VHCLTYPE_CD;
		Integer	restkg			= row.LINK_REST_W_KG;
		Integer	resth			= row.LINK_REST_H_M;
		
		// geometry		
		String linkshpGeom		= row.LINKSHP_GEOM;
		LineString line 		= new LineString();
		
		
		
		
		ArrayList<Point> coord = parseGeom(linkshpGeom);
		line.setCoordinates(coord);
		
		String placemarkName = "" + linkID + ":" + link_f_node + "-" + link_t_node;
		String placemarkDescription = linkUse + ", " + lane + ", " + ramptype 
									+ "(" + ramptypeCd + "), " + maxSpd + ", " 
									+ vhcltypeDesc + "(" + vhcltypeCd + "), " + restkg  + ", " + resth;
		String styleUrl = "#" + styleID;
		
		Placemark link = new Placemark(placemarkName);		// Insert value of node_name:node_id for Placemark's name
		link.setDescription(placemarkDescription);			// Insert value of turnp_desc:nodesign_desc for Placemark's description
		link.setStyleUrl(styleUrl);							// Insert value of styleUrl
		link.setGeometry(line);								// Insert value of geometry
	
		// Add the placemark to the Folder
		folder.addFeature(link);
	}
	
	public ArrayList<Point> parseGeom(String linkshpGeom) throws Exception {

		// LINESTRING (127.016055 37.520922, 127.016223 37.520803, 127.016628 37.520639, 127.017084 37.520504, 127.017292 37.52052, 127.017443 37.520569, 127.017582 37.520634, 127.017689 37.520714, 127.017896 37.52087, 127.018074 37.521057)
		
		ArrayList<Point> 	coord = new ArrayList<Point>();
		String 				temp = "";
		int 				startNum = linkshpGeom.indexOf("(");
		int 				endNum = linkshpGeom.length() -1; 
		
		if (startNum != -1) {
			temp = linkshpGeom.substring(startNum + 1, endNum);
			
			if (temp == null) {
				return null;
			}
			
			// x, y coordinates pair split
			String[] values = temp.split(",");
			Double pointx = 0.0;
			Double pointy = 0.0;
			String value = "";
			
			for (int i = 0; i < values.length; i++) {
				pointx = 0.0;
				pointy = 0.0;
				
				value = values[i];
				
				if (value != null)
				{
					// x, y point value split
					value = value.trim();
					String points[] = value.split(" ");
					
					if (points != null) {
						pointx = pointx.parseDouble(points[0]);
						pointy = pointy.parseDouble(points[1]);
												
						Point point = new Point();
						point.setLongitude(pointx);
						point.setLatitude(pointy);
						
						coord.add(point);
					}
				}
			}
		}
		
		return coord;
		
	}
	
	public static void main(String[] args) throws Exception {
		
		String kmlFilePath = "c:\\road200_link.kml";
		LinkListExporter exporter = new LinkListExporter();
	
		// Make KML File 
		exporter.makeKML(kmlFilePath);
		
		// Done
		System.out.println( "Good Bye!" );
		
	}
	
}
