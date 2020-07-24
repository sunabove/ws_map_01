<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="./C002_HtmlDocType.jsp"></jsp:include> 

<html>
	<head>
		<title>이용자 데모 V1.0.0012 : ${visitNo}</title>

		<jsp:include page="./C001_ComHeader.jsp"></jsp:include>

	</head> 

	<body>
		<iframe id="map" 
			src="./M020_MapControl.jsp?a=1&width=600&height=400&mapX=156400&mapY=-13328" 
			frameborder="0"
			width="600" height="400" SCROLLING="no"
			style="border: 4 solid #919191; padding: 0; margin: 0;"
			 ></iframe>
		<br/>
		<span onClick="javascript: map.zoomIn(); map.mapRefresh(); "> 확대 c </span>
		<span onClick="javascript: map.zoomOut(); map.mapRefresh(); "> 축소 c </span>
	</body>
</html>


