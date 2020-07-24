<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.awt.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="sql" scope="request" >
SELECT a. *,  decode( img, 1 , 'Bit_Red.gif' , 'BusStop_Red_14x18.gif' ) as poi_icon
FROM (
	SELECT brs.station_seq, bs.station_id AS POI_ID,  --'routestation_poi' AS LAYER_NAME,
			bs.map_x AS X_POS, bs.map_y Y_POS, count(*) over() as cnt,
			'' AS POI_TEXT 	,'' as POI_COLOR ,
			decode(bs.station_id, 
				(select MAX(station_id) from BIS_MST_BIT where bs.station_id = station_id),'1') img
			, '' as LINE_COLOR, '' as LINE_WIDTH, '' as LINE_ID 
			, 1 as POI_ONLY
	FROM bis_mst_route br, BIS_MST_ROUTESTATION brs, BIS_MST_STATION bs 
	WHERE br.route_id = brs.route_id AND brs.station_id = bs.station_id
			--AND br.upper_id = '${ param.upper_id }' AND br.route_id = '${ param.route_id }' 
	ORDER BY brs.station_seq
) a
</c:set>

<jsp:include flush="true" page="../M013_Nosun_ShowAct.jsp">
    <jsp:param name="sql" value="${ sql }" />
    <jsp:param name="layerName" value="busStop" />
</jsp:include>