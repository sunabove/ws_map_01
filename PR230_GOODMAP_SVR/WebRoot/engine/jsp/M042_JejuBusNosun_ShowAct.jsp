<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="sql" scope="request" >
     
     select rownum as rno , a.*
        ,  NVL2(    POI_ID ,
	                 decode( rownum   
	                    , 1 , 'start_01.png' 
	                    , cnt, 'end_01.png'
	                    , 'BusStop_Green_High_30X27.png' 
	                  )
	                  , ''
                ) as poi_icon
        
     from ( 
	     select 
	          X_POS, Y_POS -- required field
	        , count(*) over() as CNT -- required field
	        , JRJ_ID as POI_ID
	        , JRJ_NM as poi_text -- required field
	        , '#00000' as poi_color -- required field
	        ,  NOSUN_ID, NOSUN_NM , GIJUM , JONGJUM , SUNBUN -- debug field
	     from GM_02_JEJU_BUS
	     where 1 = 1 
	     and NOSUN_NM = '${ param.nosun_nm }'
	     and ( GIJUM#TERM_ID || '.' || JONGJUM#TERM_ID ) = '${param.dir_id}'
	     order by SUNBUN  
     ) a
     
</c:set>

<jsp:include flush="true" page="./M013_Nosun_ShowAct.jsp">
    <jsp:param name="sql" value="${ sql }" />
    <jsp:param name="layerName" value="bus_nosun" />
    <jsp:param name="lineWidth" value="2" />
    <jsp:param name="lineColor" value="#0000FF" />
</jsp:include>