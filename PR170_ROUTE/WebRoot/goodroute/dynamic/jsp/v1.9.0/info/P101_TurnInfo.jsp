<%@page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${ param.s_link_id != null && param.e_link_id != null }" >
    <c:set var="sql" scope="request" >  
        select  turn$id             turn_id,
        		turn#node$id        turn_node_id,
        		turn_st#link$id     turn_st_link_id,
        		turn_ed#link$id     turn_ed_link_id,
        		turn#turntype$cd    turn_turntype_cd,
        		turn#turnuse$cd     turn_turnuse_cd,
        		turn#opertime$cd    turn_opertime_cd,
        		turn_rot_deg		turn_rot_deg,
        		turn_remark
		from    tr002_turn
        where 	turn_st#link$id = ${ param.s_link_id }
        and   	turn_ed#link$id = ${ param.e_link_id }
    </c:set>
</c:if> 

<jsp:include flush="true" page="../com/Com_DataSet.jsp" >
	<jsp:param name="sql" value="${ sql }" />
	<jsp:param name="noOfRowsPerPage" value="-1" />
	<jsp:param name="sqlParamList" value="${ sqlParamList }"  />	
</jsp:include>
	 