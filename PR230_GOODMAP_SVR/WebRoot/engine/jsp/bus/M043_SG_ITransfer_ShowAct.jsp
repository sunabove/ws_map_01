<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.awt.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="sql" scope="request" >
	select rownum as rno, a.* , '' as poi_icon
			--, NVL2(    POI_ID ,
	       --          decode( rownum
	       --             , 1 , 'start_01.png'
	       --             , cnt, 'end_01.png'
	       --             , ''
	       --           )
	        --          , ''
            --    ) as poi_icon

    from 	(
		SELECT
			T3.MAP_X AS X_POS , T3.MAP_Y AS Y_POS
			--, ( T3.MAP_Y + decode( t1.route_id, 326000127, 1000, 0 ) ) AS Y_POS 
			,count(*) over() as cnt,rownum AS POI_ID,'' AS poi_text
	   	   	,'' as poi_color 
	   	   	, decode(T1.route_id, '${ param.route_id1 }','bus_nosun','bus_nosun2') as LAYER_NAME
	   	   	, T1.route_id as LINE_ID
	   		, decode(T1.route_id, '${ param.route_id2 }', '#CC0000','#0000FF') as LINE_COLOR
	   		, 2 as LINE_WIDTH
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
	    <c:if test="${ !empty param.route_id1 && !empty param.from1_seq && !empty param.to1_seq }" >
	    	 ( T1.ROUTE_ID = '${param.route_id1}' AND T1.PRIORITY_SEQ BETWEEN ${param.from1_seq} and ${param.to1_seq} )
	   	</c:if>

	   	<c:if test="${ !empty param.route_id1 && !empty param.route_id2 }" >
	    	or
	    </c:if>
		<c:if test="${ !empty param.route_id2 && !empty param.from2_seq && !empty param.to2_seq }">
			( T1.ROUTE_ID = '${param.route_id2}'  AND T1.PRIORITY_SEQ BETWEEN ${param.from2_seq} and ${param.to2_seq} )
		</c:if> 
   		)
   		--and t1.route_id = '326000127' 
    ORDER BY LINE_ID asc , 

    <c:choose>
	    <c:when test="${ param.route_id1 > param.route_id2 }">
	    	T1.ROUTE_ID desc ,
	    </c:when>
	    <c:otherwise>
	   	 	T1.ROUTE_ID ,
	    </c:otherwise>
    </c:choose>

	 T1.PRIORITY_SEQ, T2.PRIORITY_SEQ, T3.VERTEX_SEQ
    ) a
    --where rownum < 100 

</c:set>

<jsp:include flush="true" page="../M013_Nosun_ShowAct.jsp">
    <jsp:param name="sql" value="${ sql }" />
	 <jsp:param name="lineWidth" value="2" />
</jsp:include>