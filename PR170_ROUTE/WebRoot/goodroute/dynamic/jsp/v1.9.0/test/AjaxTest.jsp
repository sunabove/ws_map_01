<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	
	Enumeration en = request.getParameterNames();
	
	String name = "";
	String value = "";
	
	while(en.hasMoreElements()) {
		name = (String)en.nextElement();
		value = request.getParameter(name);
		out.print(value);
	}
%>