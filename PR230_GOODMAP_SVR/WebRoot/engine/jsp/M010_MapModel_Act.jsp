<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.ynhenc.gis.mapsvr.*"%> 

<%

	JspService jspService = new JspService_002_MapModel( request );

	jspService.getService(); 
	
%>