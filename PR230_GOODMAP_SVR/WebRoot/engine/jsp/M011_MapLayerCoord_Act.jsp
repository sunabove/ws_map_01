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
	String layerName = request.getParameter("srchLayerName");

	if (layerName != null && layerName.trim().length() > 0) {

		MapRegistry mapRegistry = MapRegistry.getMapRegistry(); 

		GisProject gisProject = mapRegistry.getGisProject();
		
		layerName = layerName.trim();
		
		Layer layer = gisProject.getLayer( layerName );  
		
		if( layer != null ) {
			request.setAttribute( "srchLayer" , layer );
		}

	}
%> 

{
    "layerName" : "${ param.srchLayerName }" 
    ,
    "shapeList" :
    [
		<c:forEach var="shape" items="${srchLayer.shapeList}" varStatus="shapeStatus" >
		  { "id" :  ${ shape.id }
		    ,
		    "pointList" :
		      [
		          <c:forEach var="point" items="${ shape.pointList }" varStatus="pointStatus" >
		              <c:set var="wgs" value="${point.utmToWgs}" />
		              {
		                  "x" : ${ wgs.x }
		                  ,
		                  "y" : ${ wgs.y}
		              } ${ pointStatus.last ? '' : ', ' } 
		          </c:forEach>
		      ]
		  } ${ shapeStatus.last ? '' : ', ' } 
		</c:forEach>
	]
}

