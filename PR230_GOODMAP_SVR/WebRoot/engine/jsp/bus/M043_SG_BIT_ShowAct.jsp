<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="sql" scope="request" >   
SELECT S2.MAP_X AS X_POS,S2.MAP_Y AS Y_POS,count(*) over() as CNT,
ROWNUM AS POI_ID,'' AS POI_TEXT,'#000000' AS POI_COLOR,
DECODE(NVL(S1.STATE,'0'),'0','sbit.gif','1','ebit.gif','2','ebit2.gif') AS poi_icon,
1 as POI_ONLY,NVL (S1.STATE, '0') AS STATE,S2.LOCAL_NO,S2.LOCATION_NM
FROM
(
    (
        SELECT FACILITY_ID,STATE
        FROM (
            SELECT FACILITY_ID,STATE,ROW_NUMBER() OVER(PARTITION BY FACILITY_ID ORDER BY COLLECT_DT DESC ) AS CNT 
            FROM BIS_COL_FACILITYSTATUS
            WHERE COLLECT_DT > (SYSDATE - 25 / 24)
            AND FACILITY_TY='5'
            AND STATE_TY='1'
        )
        WHERE CNT=1 AND STATE='1'
    )
    UNION
    (
        SELECT T1.FACILITY_ID,'2' AS STATE
        FROM (
            SELECT FACILITY_ID
            FROM (
                SELECT FACILITY_ID,STATE,ROW_NUMBER() OVER(PARTITION BY FACILITY_ID ORDER BY COLLECT_DT DESC ) AS CNT 
                FROM BIS_COL_FACILITYSTATUS
                WHERE COLLECT_DT > (SYSDATE - 25 / 24)
                AND FACILITY_TY='5'
                AND STATE_TY='1'
            )
            WHERE CNT=1 AND STATE='0'
        ) T1,(
            SELECT FACILITY_ID
            FROM (
                SELECT FACILITY_ID,STATE,ROW_NUMBER() OVER(PARTITION BY FACILITY_ID ORDER BY COLLECT_DT DESC ) AS CNT 
                FROM BIS_COL_FACILITYSTATUS
                WHERE COLLECT_DT > (SYSDATE - 25 / 24)
                AND FACILITY_TY='5'
                AND STATE_TY='2'
            )
            WHERE CNT=1 AND STATE='1'
        ) T2
        WHERE T1.FACILITY_ID=T2.FACILITY_ID
    )
    UNION
    (
        SELECT T3.FACILITY_ID,'0' AS STATE
        FROM (
            SELECT FACILITY_ID,STATE
            FROM (
                SELECT FACILITY_ID,STATE,ROW_NUMBER() OVER(PARTITION BY FACILITY_ID ORDER BY COLLECT_DT DESC ) AS CNT 
                FROM BIS_COL_FACILITYSTATUS
                WHERE COLLECT_DT > (SYSDATE - 25 / 24)
                AND FACILITY_TY='5'
                AND STATE_TY='1'
            )
            WHERE CNT=1 AND STATE='0'
        ) T3,( SELECT FACILITY_ID,STATE
            FROM (
                SELECT FACILITY_ID,STATE,ROW_NUMBER() OVER(PARTITION BY FACILITY_ID ORDER BY COLLECT_DT DESC ) AS CNT 
                FROM BIS_COL_FACILITYSTATUS
                WHERE COLLECT_DT > (SYSDATE - 25 / 24)
                AND FACILITY_TY='5'
                AND STATE_TY='2'
            )
            WHERE CNT=1 AND STATE='0' 
         ) T4
         WHERE T3.FACILITY_ID=T4.FACILITY_ID
    )
) S1,(
    SELECT A.BIT_ID,A.LOCAL_NO,A.LOCATION_NM,B.MAP_X,B.MAP_Y
    FROM BIS_MST_BIT A,BIS_MST_STATION B
    WHERE A.STATION_ID=B.STATION_ID
) S2
WHERE S1.FACILITY_ID(+)=S2.BIT_ID
ORDER BY S2.LOCAL_NO
		
</c:set>

<jsp:include flush="true" page="../M013_Nosun_ShowAct.jsp">
    <jsp:param name="sql" value="${ sql }" />
    <jsp:param name="layerName" value="bus_nosun" />
    <jsp:param name="lineWidth" value="2" />
    <jsp:param name="lineColor" value="#0000FF" />
</jsp:include>