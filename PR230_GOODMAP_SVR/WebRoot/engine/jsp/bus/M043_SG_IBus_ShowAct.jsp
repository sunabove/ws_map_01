<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.awt.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="sql" scope="request" >
	SELECT rownum AS POI_ID,'' AS POI_TEXT ,  --substr(bus.plate_no,5,4)
			node.map_x AS X_POS, node.map_y Y_POS, count(*) over() as cnt,
			'#FF0000' as POI_COLOR 
			, '' as LINE_COLOR, '' as LINE_WIDTH, '' as LINE_ID 
			, 1 as POI_ONLY, --'busP.GIF' as poi_icon
			decode(br.route_di,2,'bus02_sa.gif','bus02_ha.gif') as poi_icon 
			, 4 as LAYER_NO, 'bus' as LAYER_NAME
	FROM BIS_PRC_BUSLOCATION bl, BIS_MST_NODE node, BIS_MST_BUS bus, bis_mst_route br
	WHERE bl.route_id = '${ param.route_id }' and bl.route_id = br.route_id
			AND bl.location_id = node.node_id
			AND bl.bus_id = bus.bus_id
			AND bl.collect_dt > SYSDATE - (SELECT default_val FROM BIS_RUN_CONTROLPARAMETER WHERE parameter_id = 60001) / 1440


</c:set>

<jsp:include flush="true" page="../M013_Nosun_ShowAct.jsp">
    <jsp:param name="sql" value="${ sql }" />
</jsp:include>