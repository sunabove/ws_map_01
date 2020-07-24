<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="sql" scope="request" >   
SELECT C.MAP_X AS X_POS,C.MAP_Y AS Y_POS,count(*) over() as CNT,
ROWNUM AS POI_ID,'' AS POI_TEXT,'#000000' AS POI_COLOR,
'BusStop_Blue.gif' AS poi_icon,
1 as POI_ONLY,'0' AS STATE,B.STATION_SEQ,C.STATION_NM    
	FROM BIS_MST_ROUTE A,BIS_MST_ROUTESTATION B,BIS_MST_STATION C,(SELECT CODE_ID,CODE_NM FROM BIS_RUN_CODE WHERE UPPERCODE_SEQ='22') D        
	WHERE A.ROUTE_ID=B.ROUTE_ID        
	AND A.ROUTE_TY='13' 
	AND B.STATION_ID=C.STATION_ID        
	AND A.ROUTE_DI=D.CODE_ID(+)        
	AND A.ROUTE_ID='${param.route_id}'	
</c:set>

<jsp:include flush="true" page="../M013_Nosun_ShowAct.jsp">
    <jsp:param name="sql" value="${ sql }" />
    <jsp:param name="layerName" value="bus_nosun" />
    <jsp:param name="lineWidth" value="2" />
    <jsp:param name="lineColor" value="#0000FF" />
</jsp:include>