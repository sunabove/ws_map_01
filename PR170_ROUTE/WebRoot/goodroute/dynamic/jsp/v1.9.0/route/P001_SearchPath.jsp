<%@page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.ynhenc.comm.*"%>
<%@ page import="com.ynhenc.routeWeb.*"%>
<%@ page import="com.ynhenc.droute.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:useBean id="routeRequest" class="com.ynhenc.routeWeb.RouteRequest" scope="request">
    <jsp:setProperty name="routeRequest" property="*" /> 
</jsp:useBean>

<%
	out.clearBuffer();
    WebRoute route = new WebRoute(); 
	Path path = route.getPath( routeRequest ) ;
	route.toKml( out, routeRequest, path  );
%>

