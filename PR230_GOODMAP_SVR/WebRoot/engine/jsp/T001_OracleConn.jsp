<%@page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 

<c:if test="${ param.sql == null || param.sql == ''}" >
	<c:set var="sql" scope="request" >  
	    select * from tab 
	    <c:if test="${ true }" >
	       where 1 =1  
	    </c:if>
	</c:set>
</c:if>

<jsp:include flush="true" page="./com/Com_DataSet.jsp" >
	<jsp:param name="sql" value="${ sql }" />
	<jsp:param name="noOfRowsPerPage" value="-1" />
	<jsp:param name="sqlParamList" value="${ sqlParamList }"  />	
</jsp:include> 
