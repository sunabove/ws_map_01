<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  

<c:if test="${ empty param.mapSvrHostName }">
    <c:set var="pageError"> mapSvrHostName is null. </c:set>
    <c:set var="errorCode"> 100 </c:set>
</c:if>

<c:if test="${ not empty param.mapSvrHostName }">

    <c:catch var="pageError">
    
        <c:set var="errorCode"> 200 </c:set>
        
        <c:set var="mapSvrPortNo" value="${ (empty param.mapSvrPortNo ) ? 80 : param.mapSvrPortNo }" /> 

	    <c:set var="redirectUrl">
	        http://${ param.mapSvrHostName }:${ mapSvrPortNo }/PR230_GOODMAP_SVR/engine/jsp/M020_MapControl.jsp
	    </c:set>
	
	    <c:import url="${ redirectUrl }">
	       <c:param name="width">${param.width}</c:param>
	       <c:param name="height">${param.height}</c:param>
	       <c:param name="mapX">${param.mapX}</c:param>
	       <c:param name="mapY">${param.mapY}</c:param>
	       <c:param name="zoomLevelCurr">${param.zoomLevelCurr}</c:param>
	       <c:param name="logoImage">${param.logoImage}</c:param>
	       <c:param name="compass">${param.compass}</c:param>
	       <c:param name="scaleBar">${param.scaleBar}</c:param>
	       <c:param name="mapNo">${param.mapNo}</c:param>
		   <c:param name="a">a</c:param>
		</c:import>
	
	</c:catch>
    
</c:if>

<c:if test="${ not empty pageError }">
    {
        "erroCode" : "${ errorCode }" ,
        "errorCause": "${ pageError }" , 
        "a", ""
    }
</c:if>