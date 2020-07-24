<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="sql" scope="request" >
                SELECT T1.MAP_X AS X_POS,T1.MAP_Y AS Y_POS,count(*) over() as CNT
                ,rownum AS POI_ID,T1.PLATE_NO AS POI_TEXT,'#000000' AS POI_COLOR,'BusSa.gif' AS poi_icon,1 as POI_ONLY,
                T1.ROUTE_NO,NVL (T2.STATE, '0') AS STATE,T1.LOCAL_NO,T1.ROUTE_DI,T1.ROUTE_ID    ,T1.BUS_ID,T1.PLATE_NO,
                 '#FFCC00' AS POI_BGCOLOR , '#3322CC' AS POI_LINECOLOR , 2 AS POI_LINEWIDTH ,  'M043_SG_BUS_ShowAct' AS LAYER_NAME , 3 AS LAYER_NO 
            FROM (     
                SELECT A.BUS_ID,C.PLATE_NO,B.MAP_X,B.MAP_Y,D.ROUTE_NO,C.LOCAL_NO,    
                    D.ROUTE_DI,D.ROUTE_ID    ,B.NODE_ID,B.NODE_NM
                FROM BIS_PRC_BUSLOCATION A,BIS_MST_NODE B ,BIS_MST_BUS C, BIS_MST_ROUTE D    
                WHERE A.LOCATION_ID=B.NODE_ID    
                AND A.BUS_ID=C.BUS_ID    
                AND A.ROUTE_ID=D.ROUTE_ID  
				AND A.LASTCOLLECT_DT>SYSDATE-(SELECT DEFAULT_VAL FROM BIS_RUN_CONTROLPARAMETER WHERE PARAMETER_ID = 60001 AND PROCESS_ID = 60101) / 1440 	
				<c:if test="${ param.busDirRouteId != null && param.busDirRouteId != ''}" > 
				AND D.ROUTE_ID='${param.busDirRouteId}'	
				</c:if> 
				<c:if test="${ param.busNosunVal != null && param.busNosunVal != ''}" > 
				AND D.UPPER_ID='${param.busNosunVal}'
				</c:if>
				<c:if test="${ param.busKindVal != null && param.busKindVal != ''}" > 
				AND D.ROUTE_TY='${param.busKindVal}'	
				</c:if>
				<c:if test="${ param.busId != null && param.busId != ''}" > 
				AND C.BUS_ID='${param.busId}'	
				</c:if> 
				<c:if test="${ param.busComVal != null && param.busComVal != ''}" > 
				AND C.COMPANY_ID='${param.busComVal}'			
				</c:if>                
                ) T1,    
                (    
                SELECT A.BUS_ID,B.STATE    
                FROM     
                    BIS_MST_OBE A,    
                    (SELECT FACILITY_ID,STATE    
                    FROM (    
                        SELECT FACILITY_ID,COLLECT_DT,STATE,ROW_NUMBER() OVER(PARTITION BY FACILITY_ID ORDER BY COLLECT_DT DESC) AS ORDERED    
                        FROM BIS_COL_FACILITYSTATUS    
                        WHERE FACILITY_TY='4'    
                        AND STATE_TY='2'    
                        )     
                    WHERE ORDERED=1    
                    ) B    
                WHERE A.OBE_ID=B.FACILITY_ID    
                ) T2    
            WHERE T1.BUS_ID = T2.BUS_ID(+) 
</c:set>

<jsp:include flush="true" page="../M013_Nosun_ShowAct.jsp">
    <jsp:param name="sql" value="${ sql }" />
    <jsp:param name="layerName" value="bus_nosun" />
    <jsp:param name="lineWidth" value="2" />
    <jsp:param name="lineColor" value="#0000FF" />
</jsp:include>