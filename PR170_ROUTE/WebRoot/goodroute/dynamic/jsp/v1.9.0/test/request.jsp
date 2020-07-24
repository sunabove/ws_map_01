<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%
	Enumeration it = request.getParameterNames();
	String name = "";
	String value = "";
	
	while(it.hasMoreElements()) {
		name = (String)it.nextElement();
		value = request.getParameter(name);
		out.print(value);
	}
%>