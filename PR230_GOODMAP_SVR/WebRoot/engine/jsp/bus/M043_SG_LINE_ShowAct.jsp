<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.awt.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="sql" scope="request" > 
    SELECT T3.MAP_X AS X_POS ,T3.MAP_Y AS Y_POS,count(*) over() as CNT,'' AS POI_ID,'' AS poi_text, '#000000' as poi_color , '' AS poi_icon
    FROM     
            BIS_MST_ROUTESECTION T1,
            BIS_MST_SECTIONLINK T2,
            (
                SELECT A.LINK_ID,A.VERTEX_SEQ,A.MAP_X,A.MAP_Y
                FROM BIS_MST_VERTEX A,
                (
                    SELECT LINK_ID,MAX(VERTEX_SEQ) AS MAX_VERTEX_SEQ
                    FROM BIS_MST_VERTEX 
                    GROUP BY LINK_ID
                ) B
                WHERE A.LINK_ID=B.LINK_ID
            )T3
    WHERE T1.ROUTE_ID = '${ param.route_id }'
    AND T1.SECTION_ID = T2.SECTION_ID
    AND T2.LINK_ID = T3.LINK_ID
    ORDER BY T1.ROUTE_ID,T1.PRIORITY_SEQ,T2.PRIORITY_SEQ,T3.VERTEX_SEQ

</c:set>

<jsp:include flush="true" page="../M013_Nosun_ShowAct.jsp">
    <jsp:param name="sql" value="${ sql }" />
    <jsp:param name="layerName" value="bus_nosun" />
    <jsp:param name="lineWidth" value="2" />
    <jsp:param name="lineColor" value="#0000FF" />
</jsp:include>