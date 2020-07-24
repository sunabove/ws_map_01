
package com.ynhenc.kmlexport;

import java.util.List;

import org.boehn.kmlframework.kml.Feature;
import org.boehn.kmlframework.kml.Kml;
import org.boehn.kmlframework.kml.Document;
import org.boehn.kmlframework.kml.Style;
import org.boehn.kmlframework.kml.IconStyle;
import org.boehn.kmlframework.kml.LabelStyle;
import org.boehn.kmlframework.kml.Folder;
import org.boehn.kmlframework.kml.Placemark;

import com.ynhenc.comm.db.QueryResult;
import com.ynhenc.comm.db.SQL;

public class NodeListExporter extends ComLibRoute{

	Boolean checkRows = true;
	
	// Condition for folder making 
	Integer preRoadRankCD 	= -1;
	Integer preRoadNo 		= -1;
	Integer preNodeType 	= -1;
	Integer preCount100 	= 0;
	
	Integer totalCount = 0;
	
	Integer rowCount = 0;
	Integer rowIndex = 0;
	
	Integer folderNumber = 1;
	
	RecordNodeList nodeList = null;
	
	public String getSqlText() throws Exception {

		String fileName = "sql_node_001.sql";

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
		this.nodeList = new RecordNodeList();

		while (qr.hasNext()) {
			RecordNode node = new RecordNode(qr);
			this.debug("Node = " + node);
			this.nodeList.add(node);
		}
		
		this.rowCount = this.nodeList.getSize();
		RecordNode node = this.nodeList.get(this.rowIndex);
		
		while (true)
		{
			this.debug("test1");
			
			// Loop end condition.
			node = this.nodeList.get(this.rowIndex);
			
			if (!this.checkRows || node == null) {
				break;
			}
						
			Integer roadRankCD = node.ROAD_ROADRANK_CD;
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
		String iconPath = "";
		String styleID = "";
		LabelStyle labelStyle = new LabelStyle();
		IconStyle iconStyle = new IconStyle();
		Style roadRankStyle = new Style();
		
		iconPath = "./icon/road_rank_" + roadRankCD + ".png";
		styleID = "road_rank_" + roadRankCD;
		
		iconStyle.setId(styleID);
		iconStyle.setIconHref(iconPath);
		iconStyle.setScale(0.8);
		
		labelStyle.setId(styleID);
		labelStyle.setScale(0.8);
		
		roadRankStyle.setId(styleID);
		roadRankStyle.setIconStyle(iconStyle);
		roadRankStyle.setLabelStyle(labelStyle);
		
		document.addStyleSelector(roadRankStyle);
		
		return styleID; 
	} 
	
	public void makeFolderForRoadRank(QueryResult qr, Document document, String styleID) throws Exception {

		// Make folder
		RecordNode node = this.nodeList.get(this.rowIndex);
		Integer roadRankCD = node.ROAD_ROADRANK_CD;
		String roadRankDesc = node.ROADRANK_DESC;
		
		// Make Folder According to ROAD#ROADRANK$CD
		String folderName = roadRankDesc + "(" + roadRankCD + ")";
	//	String folderDescription = roadRankDesc;
		
		Folder folderRoadRankCD = new Folder();
		folderRoadRankCD.setName(folderName);
	//	folderRoadRankCD.setDescription(folderDescription);
		
		while (true)
		{
			this.debug("tset2");

			node = this.nodeList.get(this.rowIndex);	
			
			if (node == null) 
			{
				// Setting
				document.addFeature(folderRoadRankCD);
				return;				
			}
			
			roadRankCD = node.ROAD_ROADRANK_CD;
			
			// Condition of return
			if (this.preRoadRankCD != roadRankCD)
			{
				// Setting
				document.addFeature(folderRoadRankCD);
				return;
			}
			
			//
			Integer roadNo = node.ROAD_NO;
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
		RecordNode node = this.nodeList.get(this.rowIndex);
		Integer roadNo = node.ROAD_NO;
		String roadName = node.ROAD_NAME;
		
		// Make Folder According to ROAD_NO
		String folderName = roadName + "(" + roadNo + ")";
		
		Folder folderRoadNo = new Folder();
		folderRoadNo.setName(folderName);
		
		while (true) 
		{
			this.debug("test3");

			node = this.nodeList.get(this.rowIndex);
			
			if (node == null) 
			{
				// Setting
				folderRoadRankCD.addFeature(folderRoadNo);
				return;	
			}
			
			Integer roadRankCD = node.ROAD_ROADRANK_CD;
			roadNo = node.ROAD_NO;
			
			// condition of return
			if (this.preRoadNo != roadNo || this.preRoadRankCD != roadRankCD) {
				// Setting
				folderRoadRankCD.addFeature(folderRoadNo);
				this.preNodeType = 0;
				return;
			}
			
			//
			Integer nodeType = node.NODE_NODETYPE_CD;

			if (this.preNodeType != nodeType)
			{
				this.preNodeType = nodeType;
				MakeFolderForNodeType(qr,folderRoadNo, styleID);
			}
			else
			{
				
			}
		
		}
		
	}
	
	public void MakeFolderForNodeType(QueryResult qr, Folder folderRoadNo, String styleID) throws Exception {
		
		// Make Folder
		RecordNode node = this.nodeList.get(this.rowIndex);
		Integer nodeType = node.NODE_NODETYPE_CD;
		String nodeTypeDesc = node.NODETYPE_DESC;
		
		// Make folder according to ROAD#ROADTYPE$CD
		String folderName = nodeTypeDesc + "(" + nodeType + ")";
		
		Folder folderNodeType = new Folder();
		folderNodeType.setName(folderName);
	//	folderNodeType.setDescription(nodeTypeDesc);
		
		while (true) 
		{
			this.debug("test4");

			node = this.nodeList.get(this.rowIndex);
			
			if (node == null) 
			{
				// Setting
				folderRoadNo.addFeature(folderNodeType);
				return;				
			}
			
			Integer roadRankCD = node.ROAD_ROADRANK_CD;
			Integer roadNo = node.ROAD_NO;
			nodeType = node.NODE_NODETYPE_CD;
			
			
			if (this.preNodeType != nodeType || this.preRoadNo != roadNo || this.preRoadRankCD != roadRankCD)
			{
				// Setting
				folderRoadNo.addFeature(folderNodeType);
				this.preCount100 = 0;
				this.folderNumber = 1;
				return;
			}
			
			// count100  
			if (this.preCount100 % 100 == 0) 
			{
				MakeFolderForCount100(qr, folderNodeType, styleID);
			}
			else
			{
				
			}
			
			// Insert Placemark
//			this.insertPlacemark(qr, folderNodeType, styleID);
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
			RecordNode node = this.nodeList.get(this.rowIndex);
			
			if (node == null) 
			{
				// Setting
				this.preCount100 = 0;
				folderNodeType.addFeature(folderCount100);
				return;			
			}
			
			Integer roadRankCD = node.ROAD_ROADRANK_CD;
			Integer roadNo = node.ROAD_NO;
			Integer nodeType = node.NODE_NODETYPE_CD;
			
			if ( preCount100 >= 100 || this.preRoadNo != roadNo || this.preNodeType != nodeType || this.preRoadRankCD != roadRankCD)	
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
	
	public void insertPlacemark(QueryResult qr, Folder folder, String styleID) throws Exception {
		
		// Create a Placemark for the TOPIS node list
		RecordNode row 			= this.nodeList.get(this.rowIndex);
		
		String 	nodeName 		= row.NODE_NAME;
		Integer nodeID 			= row.NODE_ID;
		String 	turnP 			= row.TURNP_DESC;
		String 	nodeSign 		= row.NODESIGN_DESC;
		double 	pointX 			= row.NODE_GX;
		double 	pointY 			= row.NODE_GY;
		
		String placemarkName = nodeName + ":" + nodeID;
		String placemarkDescription = turnP + ":" + nodeSign;
		String styleUrl = "#" + styleID;
		
		Placemark node = new Placemark(placemarkName);		// Insert value of node_name:node_id for Placemark's name
		node.setDescription(placemarkDescription);			// Insert value of turnp_desc:nodesign_desc for Placemark's description
		node.setStyleUrl(styleUrl);							// Insert value of styleUrl
		node.setLocation(pointX, pointY);					// Insert value of geometry
	
		// Add the placemark to the Folder
		folder.addFeature(node);
	}

	public static void main(String[] args) throws Exception {
		
		String kmlFilePath = "c:\\road100_node.kml";
		NodeListExporter exporter = new NodeListExporter();
	
		// Make KML File 
		exporter.makeKML(kmlFilePath);
		
		// Done
		System.out.println( "Good Bye!" );
		
	}
	
}
