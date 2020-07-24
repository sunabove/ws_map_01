<%@page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.ynhenc.comm.*"%>
<%@ page import="com.ynhenc.droute.map.link.*"%>
<%@ page import="com.ynhenc.routeWeb.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:useBean id="turnShapeRequest" class="com.ynhenc.routeWeb.TurnShapeRequest" scope="request">
    <jsp:setProperty name="turnShapeRequest" property="*" /> 
</jsp:useBean>

<%
	out.clearBuffer();
	WebTurnShape turnShape = new WebTurnShape();  
	LinkShapeList linkShapeList = turnShape.getLinkShapeList( turnShapeRequest ); 
	turnShape.toKml( out, turnShapeRequest, linkShapeList );
%>

