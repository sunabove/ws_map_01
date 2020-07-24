<%@page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${ param.class == 'nosun' }" >
    <c:set var="sql" scope="request" >  
        select 
        distinct NOSUN_NM , ( to_char( NOSUN_NM, '000' ) || ' 번' ) as NOSUN_DESC 
        from GM_02_JEJU_BUS 
        where 1 = 1 
        and GIJUM is not null
        and JONGJUM is not null
        order by 1         
    </c:set>
</c:if>

<c:if test="${ param.class == 'dir' }" >
    <c:set var="sql" scope="request" > 
        select 
            distinct NOSUN_NM 
            ,  DIR_ID
            , ( GT.TERM_NM || '->' || JT.TERM_NM ) as DIR_DESC 
            , avg( x_pos ) over( partition by NOSUN_NM , DIR_ID   ) as X_POS_CEN
            , avg( y_pos ) over( partition by NOSUN_NM , DIR_ID   ) as Y_POS_CEN
        from 
          GM_02_JEJU_BUS , GM_03_TERM GT , GM_03_TERM JT
          
        where 1 = 1 
        and NOSUN_NM = '${param.nosun_nm}'
        and GIJUM#TERM_ID != JONGJUM#TERM_ID -- 기점과 종점이 다른 방향만 검색
        and GIJUM#TERM_ID = GT.TERM_ID
        and JONGJUM#TERM_Id = JT.TERM_id
        order by NOSUN_NM , DIR_ID 
        
    </c:set>
</c:if>

<c:if test="${ param.class == 'jrj' }" >
    <c:set var="sql" scope="request" >  
        select 
        NOSUN_ID, NOSUN_NM , GIJUM , JONGJUM , DIR_ID, JRJ_ID, JRJ_NM, X_POS, Y_POS , SUNBUN  
        from GM_02_JEJU_BUS 
        
        where 1 = 1 
        and NOSUN_NM = '${param.nosun_nm}'
        and JRJ_ID is not null 
        and DIR_ID = '${ param.dir_id }'
        order by NOSUN_NM, SUNBUN
    </c:set>
</c:if>

<c:if test="${ param.class == 'vertex' }" >
    <c:set var="sql" scope="request" >  
        select 
        NOSUN_ID, NOSUN_NM , GIJUM , JONGJUM , DIR_ID, JRJ_ID, JRJ_NM, X_POS, Y_POS , SUNBUN
        from GM_02_JEJU_BUS 
        where 1 = 1 
        and NOSUN_NM = '${param.nosun_nm}'
        and ( GIJUM#TERM_ID || '.' || JONGJUM#TERM_ID ) = '${ param.dir_id }'
        order by NOSUN_NM, SUNBUN
    </c:set>
</c:if>

<jsp:include flush="true" page="./com/Com_DataSet.jsp" >
    <jsp:param name="sql" value="${ sql }" />
    <jsp:param name="noOfRowsPerPage" value="-1" />
    <jsp:param name="sqlParamList" value="${ sqlParamList }"  />    
</jsp:include> 
