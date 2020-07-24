<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.awt.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="sql" scope="request" >
	select rownum as rno, a.* 
    from    (
        SELECT
            T3.MAP_X AS X_POS , T3.MAP_Y AS Y_POS,
              count(*) over() as cnt,rownum AS POI_ID,'' AS poi_text
            ,'' as poi_color
            , decode(T1.route_id, '326000174','bus_nosun','bus_nosun2') as LAYER_NAME
            , T1.route_id as LINE_ID
            , decode(T1.route_id, '326000113', '#CC0000','#0000FF') as LINE_COLOR
            , 6 as LINE_WIDTH, '' as poi_icon  
            , decode(T1.route_id, '326000174', 15, 10 ) as LAYER_NO  
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
    WHERE T1.SECTION_ID = T2.SECTION_ID
        AND T2.LINK_ID = T3.LINK_ID
        and (
             ( T1.ROUTE_ID = '326000174' AND T1.PRIORITY_SEQ BETWEEN 13 and 17 )
            or
            ( T1.ROUTE_ID = '326000113'  AND T1.PRIORITY_SEQ BETWEEN 23 and 29 )
        )
        --AND T1.ROUTE_ID <> '326000113'
    ORDER BY LINE_ID asc ,
            T1.ROUTE_ID desc ,
     T1.PRIORITY_SEQ, T2.PRIORITY_SEQ, T3.VERTEX_SEQ
    ) a

</c:set>

<jsp:include flush="true" page="../M013_Nosun_ShowAct.jsp">
    <jsp:param name="sql" value="${ sql }" />
	<jsp:param name="lineWidth" value="2" />
	<jsp:param name="layerName" value="bus_nosun_it_transfer" /> 
</jsp:include>