<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page import="com.ynhenc.gis.mapsvr.*"%>
<%@ page import="com.ynhenc.gis.web.*"%>
<%@ page import="com.ynhenc.gis.model.map.*"%>
<%@ page import="com.ynhenc.gis.model.shape.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="imagetoolbar" content="no"/>

<script type="text/javascript" src="../js/js_005_ext-2.2/adapter/prototype/prototype_1.6.1.js"></script>
<script type="text/javascript" src="../js/js_004_scriptaculous/scriptaculous.js"></script>

<% 
	MapRegistry mapRegistry = MapRegistry.getMapRegistry(); 

	GisProject gisProject = mapRegistry.getGisProject(); 
	
	Request req = Request.createRequest(request);
	
	Double mouseX = req.getDouble( "mouseX" );
	Double mouseY = req.getDouble( "mouseY" );
	
	if( mouseX != null && mouseY != null ) { 
	
		MousePnt mouse = new MousePnt( mouseX.doubleValue(), mouseY.doubleValue() ); 
		
		ShapePointPoiList shapePoiList = gisProject.getToolTipList( req, mouse );
		
		if( shapePoiList.getSize() > 0 ) {
		  ShapePointPoi shape = shapePoiList.getFirst();
		  request.setAttribute( "shape" , shape );
		}
		
		request.setAttribute( "shapePoiList" , shapePoiList );
	}
	
%> 

<script type="text/javascript">

var toolTipDs = {
    "mouseX" : "${ param.mouseX }" 
    ,
    "mouseY" : "${ param.mouseY }" 
    ,
    "toolTipList" :
    [
        <c:if test="${ shape != null }"> 
		  { "id" :  ${ shape.id }
		    ,
		    "cenX" : ${ shape.centroid.x }
		    ,
		    "cenY" : ${ shape.centroid.y }
		    ,
		    "type" : "${ shape.poi.toolTip.type }"
		    ,
		    "content" : "<c:out value="${ shape.poi.toolTip.content }" />"
		  } 
		</c:if>
	]
} ; 

function bodyOnLoad() {  
    
    var json = toolTipDs ; 
    var toolTipSize = json.toolTipList.size();

    if( toolTipSize > 0 ) {
        var index = 0 ;
        var toolTip = json.toolTipList[ index ];
        if( true ) {
            var x = json.mouseX;
            var y = json.mouseY;  
            
            var opener = window.parent; 
            if( opener != null ) { 
                opener.showToolTipFrameOnMap( x, y );
            }            
        }
    }
    
    var localDebug = false;
    if( localDebug ) { 
        alert( "x: " + json.mouseX +  ", y: " + json.mouseY );
        alert( "size: " + json.toolTipList.size() );
    }
    
}
     
</script>
 
</head>
<body onload="bodyOnLoad();" style="padding: 0; margin: 0; ">
	<c:if test="${ shape != null }">
	   <div style="border:1px solid black;  background-color: lightyellow; ">
	       <center>
	           <c:out value="${ shape.poi.toolTip.content }" escapeXml="false"/>
	       </center>
	   </div>
	</c:if>
</body>
</html>



