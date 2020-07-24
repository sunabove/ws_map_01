<%@page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${ param.node_id != null }" >
    <c:set var="sql" scope="request" >  
		select 	node$id             node_id,
		        node#nodetype$cd    node_nodetype_cd,
		        node_name,
		        node#turnp$cd       node_turnp_cd,
		        node_remark,
		        node#nodesign$cd    node_nodesign_cd,
		        node_gx,
		        node_gy,
		        node_tx,
		        node_ty 
		from 	tr001_node
        where 	node$id = ${ param.node_id }
    </c:set>
</c:if> 

<jsp:include flush="true" page="../com/Com_DataSet.jsp" >
	<jsp:param name="sql" value="${ sql }" />
	<jsp:param name="noOfRowsPerPage" value="-1" />
	<jsp:param name="sqlParamList" value="${ sqlParamList }"  />	
</jsp:include>
	 