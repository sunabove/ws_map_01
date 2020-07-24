<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.awt.*"%>
<%@ page import="com.ynhenc.comm.*"%>
<%@ page import="com.ynhenc.comm.db.*"%>
<%@ page import="com.ynhenc.gis.model.map.*"%>
<%@ page import="com.ynhenc.gis.model.shape.*"%>
<%@ page import="com.ynhenc.gis.*"%>
<%@ page import="com.ynhenc.comm.db.*"%>
<%@ page import="com.ynhenc.gis.web.*"%> 

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  

<%
  	    boolean localDebug = true;
        
        GisComLib comLib = new GisComLib() ; 
        
        Request req = Request.createRequest( request ); 
        
        MapRegistry mapRegistry = MapRegistry.getMapRegistry();
        
        GisProject gisProject = mapRegistry.getGisProject();
        
        Session mapSession = SessionManager.getSession( session ); 
        
        String layerName = ""; 
        
        int layerNo = -1;
        
    	String sql = request.getParameter( "sql" ); 
    	
    	int lineId = 0; 
    	
    	int lineWidth = 1; 
    	
    	try {
    	   lineWidth = req.getDouble( "lineWidth" ).intValue();
    	} catch ( Exception e ) {
    	}
    	
    	Color lineColor = Color.blue;  
    	   
    	try {
    	   lineColor = comLib.getColor( req.getParameter( "lineColor" ) ) ;
    	} catch ( Exception e ) {
    	   //e.printStackTrace();
    	   lineColor = Color.blue;
    	}
    	
    	VertexList vertexList = new VertexList();
    	ToolTip loiToolTip = null;
    	AddObjectLoi loi = new AddObjectLoi( layerName , lineId, vertexList, lineColor, lineWidth, loiToolTip); 

    	Database db =com.ynhenc.goodmap.DbFactory_GoodMap.getDatabase(); 
    	
    	QueryResult qr = db.getQueryResult(sql, false ); 
    	
    	Vertex vertex = null; 
    	int vertextNo = 0;
    	
    	Integer poiId = null ;
    	
    	AddObjectPoi poi ;
    	Color poiTextColor = Color.green;  
    	String poiText = "";
        String poiIcon = "";
        Color poi_BgColor;
        Color poi_LineColor;
        Double poi_LineWidth;
        
        Node node;
        
        int nodeId = 0;
        
        int poiOnly = -1;
        
        int lineIdNew = lineId;
        
        double minX = Double.MAX_VALUE;
        double minY = Double.MAX_VALUE;
        double maxX = - Double.MAX_VALUE;
        double maxY = - Double.MAX_VALUE;
        
        double cenX = 0 ;
        double cenY = 0 ;
        
        double vX, vY;
        
        int pointNo = 0 ; 
        
        ToolTip poiToolTip; 
        
        MinMaxInt dispLevel = null;
    	
    	while( qr.next() ) {
    	
    	   poiToolTip = null;
    	
    	   layerNo = -1;
    	   
    	   dispLevel = new MinMaxInt( -1, -1 );
    	   
    	   try { 
               lineIdNew = qr.getInteger( "LINE_ID" ).intValue(); 
           } catch ( Exception e ) {
           }
           
           try {
    	       layerNo = qr.getInteger( "layer_No" ).intValue();  
    	   } catch ( Exception e ) {
    	   } 
    	   
    	   try {
    	       int layer_Level_St = qr.getInteger( "layer_Level_St" ).intValue();
    	       dispLevel.setMin( layer_Level_St );
    	   } catch ( Exception e ) {
    	   }
    	   
    	   try {
               int layer_Level_Ed = qr.getInteger( "layer_Level_Ed" ).intValue();
               dispLevel.setMax( layer_Level_Ed );
           } catch ( Exception e ) {
           }
    	   
    	   if( lineId != lineIdNew ) {
               if( vertexList.getSize() > 0 ) { 
                   gisProject.getAdd_02_Loi( req, loi , layerNo , dispLevel ); 
               }
               vertexList = new VertexList();
               loi = new AddObjectLoi( layerName , lineId, vertexList, lineColor, lineWidth, loiToolTip );
               lineId = lineIdNew;
           }  
    	
    	   if( true || pointNo < 1 || lineId != lineIdNew ) { // if new line object is occurred, 
               try { // set line color
                   lineColor = comLib.getColor( qr.getString( "LINE_COLOR" ) );
                   loi.setLineColor( lineColor );
               } catch( Exception e ) {
               }
               
               try { // set line width 
                   lineWidth = qr.getInteger( "LINE_WIDTH" ).intValue();
                   loi.setLineWidth( lineWidth );
               } catch( Exception e ) {
               }
           }
           
           try {
               String layerNameNew = qr.getString( "LAYER_NAME" ) ;        
               if( comLib.isGood( layerNameNew ) ) { 
                     layerName = layerNameNew;   
                     loi.setLayerName( layerName ); 
               }           
           } catch (Exception e ) {
           } 
           
           poiId = qr.getInteger( "POI_ID" );
    	   poiOnly = -1;
    	   
    	   if( poiId == null ) {
    	       vertex = new Vertex( qr.getDouble( "X_POS" ).doubleValue() , qr.getDouble( "Y_POS" ).doubleValue() );
    	   } else {
    	       try {
    	           poiOnly = qr.getInteger( "POI_ONLY" ).intValue() ;
    	       } catch (Exception e ) {
    	       }
    	       node = new Node( qr.getDouble( "X_POS" ).doubleValue() , qr.getDouble( "Y_POS" ).doubleValue() , poiId.intValue(), qr.getString( "POI_TEXT" ) );
    	       vertex = node;
    	       poiIcon = qr.getString( "POI_ICON" ) ;
    	       poiIcon = poiIcon == null ? "" : poiIcon;
    	       poiText = qr.getString( "POI_TEXT" );
    	       poiTextColor = comLib.getColor( qr.getString( "poi_color" ) );
    	       int toolTipType = 0;
    	       try {
    	            toolTipType = qr.getInteger( "toolTipType" ); 
    	       } catch ( Exception e ) {
    	       }
    	       try { 
                   String toolTipContent = qr.getString( "toolTipContent" );
                   poiToolTip = new ToolTip( toolTipType, toolTipContent );
               } catch ( Exception e ) {
               }
    	       poi = new AddObjectPoi(layerName + "_POI", nodeId, node, poiText, poiTextColor, poiIcon, poiToolTip ) ; 
    	       try {
    	            poi_BgColor = comLib.getColor( qr.getString( "poi_BgColor" ) );
    	            poi.setPoiBgColor( poi_BgColor );
               } catch( Exception e ) {
               }
               try {
                    poi_LineColor = comLib.getColor( qr.getString( "poi_LineColor" ) );
                    poi.setPoiLineColor( poi_LineColor );
                    poi_LineWidth = qr.getDouble( "poi_LineWidth" ).doubleValue();
                    poi.setPoiLineWidth( poi_LineWidth ); 
               } catch( Exception e ) {
               }  
    	       nodeId ++;
               gisProject.getAdd_01_Poi( req, poi , layerNo , dispLevel );  
    	   } 
    	   
    	   if( poiOnly < 1 ) { 
    		   vertexList.add( vertex ); 
    		   vertextNo ++;
    	   }
    	   
    	   vX = vertex.getX();
    	   vY = vertex.getY();
    	   
    	   cenX += vX;
    	   cenY += vY;
    	   
    	   if( pointNo < 1 ) {
    		   minX = maxX = vX;
    		   minY = maxY = vY; 
    	   } else {  
	    	   minX = minX < vX ? minX : vX;
	    	   minY = minY < vY ? minY : vY;
	    	   maxX = maxX > vX ? maxX : vX;
	    	   maxY = maxY > vY ? maxY : vY;
    	   }
    	   
    	   pointNo ++ ; 
    	}
    	
    	try {
    	   qr.close();
    	} catch ( Exception e ) {
    	   e.printStackTrace();
    	}
    	
    	Layer layerLoi = gisProject.getAdd_02_Loi( req, loi , layerNo , dispLevel ); // create line of interest 
    	
    	if( pointNo > 0 ) {
           cenX = cenX/pointNo;
           cenY = cenY/pointNo;
           
           Mbr mbr = Mbr.newMbr(  minX, minY, maxX, maxY );
           
           gisProject.getZoomLevelList(); 
           
           MapService mapService = new MapService();
           
           int zoomLevel = mapService.getOptimalZoomLevel( req, mbr );
           
           req.setAttribute( "cenX", cenX );
           req.setAttribute( "cenY", cenY );
           req.setAttribute( "zoomLevel", zoomLevel );
        }
     
%>

<% out.clearBuffer(); out.clear(); %>

{
    "cenX" : ${ cenX } ,
    "cenY" : ${ cenY } ,
    "zoomLevel" : ${ zoomLevel } ,
    "a" : ""
}

