<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.awt.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="sql" scope="request" >
SELECT a. *
FROM (
	SELECT brs.station_seq, bs.station_id AS POI_ID,  
			bs.map_x AS X_POS, bs.map_y Y_POS, count(*) over() as cnt,
			bs.station_nm AS POI_TEXT 	,'#FF0000' as POI_COLOR ,-
			'' as LINE_COLOR, '' as LINE_WIDTH, '' as LINE_ID 
			, 1 as POI_ONLY , 'Bit_Red.gif' as poi_icon --poi_04.png
	FROM bis_mst_route br, BIS_MST_ROUTESTATION brs, BIS_MST_STATION bs , BIS_MST_BIT bit
	WHERE br.route_id = brs.route_id AND brs.station_id = bs.station_id
			and bs.station_id = bit.station_id
			and bit.use_yn='Y'
	ORDER BY brs.station_seq
) a
</c:set>

<jsp:include flush="true" page="../M013_Nosun_ShowAct.jsp">
    <jsp:param name="sql" value="${ sql }" />
    <jsp:param name="layerName" value="busStop" />
</jsp:include>