<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.awt.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<c:set var="sql" scope="request" >

SELECT b.* , 
	decode( img2 , '1' , 'Bit_Red.gif' ,'2','start_02.gif', '3','transfer_02.gif','4','end_02.gif','BusStop_Green_14x18.gif' ) as poi_icon
FROM(
	select rownum AS rno, station_id AS POI_ID, a.*,
		DECODE(rownum,1,'2',cnt,'4',img) img2
	FROM (
		SELECT bs.station_id, 
                    bs.map_x AS X_POS , bs.map_y AS Y_POS , count(*) over() as cnt,
                    '' AS poi_text 	,'' as poi_color ,
                    decode(bs.station_id,  
                   (select a.station_id from bis_mst_station a, bis_mst_routestation b 
					where a.station_id=b.station_id and b.station_seq= ${param.from2_seq} AND b.route_id='${param.route_id2}' ) ,
					'3', (select MAX(station_id) from BIS_MST_BIT  where bs.station_id = station_id),'1'
					 ) img ,
					'' as LINE_COLOR, '' as LINE_WIDTH, '' as LINE_ID 
					, 1 as POI_ONLY
		FROM BIS_MST_ROUTESTATION brs, BIS_MST_STATION bs	         
        WHERE
        	( 
        	<c:if test="${ !empty param.route_id1 && !empty param.from1_seq && !empty param.to1_seq }" >
	    	 ( brs.ROUTE_ID = '${param.route_id1}' AND brs.station_seq BETWEEN ${param.from1_seq} and ${param.to1_seq} )
	   		</c:if>

	   		<c:if test="${ !empty param.route_id1 && !empty param.route_id2 }" >
	    		or
	    	</c:if>
			
			<c:if test="${ !empty param.route_id2 && !empty param.from2_seq && !empty param.to2_seq }">
				( brs.ROUTE_ID = '${param.route_id2}'  AND brs.station_seq BETWEEN ${param.from2_seq} and ${param.to2_seq} )
			</c:if>
			) 
            AND brs.station_id = bs.station_id		   				     
        ORDER BY 
			<c:choose>
				<c:when test="${ param.route_id1 > param.route_id2 }">
	    			brs.route_id desc 
				</c:when>
				<c:otherwise>
	   	 			brs.route_id 
				</c:otherwise>
			</c:choose>
			, brs.STATION_SEQ
	) a
	) b
</c:set>

<jsp:include flush="true" page="../M013_Nosun_ShowAct.jsp">
    <jsp:param name="sql" value="${ sql }" />
    <jsp:param name="layerName" value="bus_nosun" />
</jsp:include>