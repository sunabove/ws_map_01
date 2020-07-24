<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="sql" scope="request" >          

	SELECT B.MAP_X AS X_POS,B.MAP_Y AS Y_POS,count(*) over() as CNT,
        ROWNUM AS POI_ID,A.PLATE_NO AS POI_TEXT,'#000000' AS POI_COLOR,'BusSa.gif' AS poi_icon,1 as POI_ONLY
             FROM BIS_MST_BUS A,BIS_PRC_INTERVALSERVICE B,BIS_MST_ROUTE C        
             WHERE A.BUS_ID=B.BUS_ID        
             AND B.ROUTE_ID=C.ROUTE_ID  
		<c:if test="${param.busComVal!=''}">
		AND A.COMPANY_ID='${param.busComVal}'
		</c:if>
		<c:if test="${param.busNosunVal!=''}">
		AND C.UPPER_ID='${param.busNosunVal}'
		</c:if>
		<c:if test="${param.busDirRouteId!=''}">
		AND C.ROUTE_ID='${param.busDirRouteId}'
		</c:if>
             AND (B.ANALYSIS_DT,C.ROUTE_ID,A.BUS_ID) IN (        
                 SELECT MAX(ANALYSIS_DT),SA.ROUTE_ID,SA.BUS_ID        
                 FROM BIS_PRC_INTERVALSERVICE SA,BIS_MST_ROUTE SB        
                 WHERE SA.ROUTE_ID=SB.ROUTE_ID        
                 AND SA.ANALYSIS_DT>SYSDATE- 10/1440         
                 GROUP BY SA.ROUTE_ID,SA.BUS_ID        
             )    
		
</c:set>

<jsp:include flush="true" page="../M013_Nosun_ShowAct.jsp">
    <jsp:param name="sql" value="${ sql }" />
    <jsp:param name="layerName" value="bus_nosun" />
    <jsp:param name="lineWidth" value="2" />
    <jsp:param name="lineColor" value="#0000FF" />
</jsp:include>