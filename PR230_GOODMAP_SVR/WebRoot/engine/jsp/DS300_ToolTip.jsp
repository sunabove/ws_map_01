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

<% 
	MapRegistry mapRegistry = MapRegistry.getMapRegistry(); 

	GisProject gisProject = mapRegistry.getGisProject(); 
	
	Request req = Request.createRequest(request);
	
	Double mouseX = req.getDouble( "mouseX" );
	Double mouseY = req.getDouble( "mouseY" );
	
	if( mouseX != null && mouseY != null ) { 
	
		MousePnt mouse = new MousePnt( mouseX.doubleValue(), mouseY.doubleValue() ); 
		
		ShapePointPoiList shapePoiList = gisProject.getToolTipList( req, mouse );
		
		request.setAttribute( "shapePoiList" , shapePoiList );
	}
	
%> 

{
    "mouseX" : "${ param.mouseX }" 
    ,
    "mouseY" : "${ param.mouseY }" 
    ,
    "toolTipList" :
    [
        <c:if test="${ shapePoiList != null }"> 
			<c:forEach var="shape" items="${ shapePoiList }" varStatus="shapeStatus" >
			  { "id" :  ${ shape.id }
			    ,
			    "cenX" : ${ shape.centroid.x }
			    ,
			    "cenY" : ${ shape.centroid.y }
			    ,
			    "type" : "${ shape.poi.toolTip.type }"
			    ,
			    "content" : "<c:out value="${ shape.poi.toolTip.content }" />"
			  } ${ shapeStatus.last ? '' : ', ' } 
			</c:forEach>
		</c:if>
	]
}

