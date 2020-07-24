<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="./C002_HtmlDocType.jsp"></jsp:include>

<c:set var="currSessionId" value="${ pageContext.session.id }" />
<c:set var="prevSessionId" value="${ param.preSessionId }" />

<html > 

<body>
	Session id is ${ currSessionId == prevSessionId ? 'equal' : ' not equal ' } .
	<form method="post" >
		<input size="100" type="text" value="${ currSessionId }" /> 
		<input type="hidden" name="preSessionId" value= "${ currSessionId }" />
		<input type="submit" value="go" />
	</form> 
</body>
</html>