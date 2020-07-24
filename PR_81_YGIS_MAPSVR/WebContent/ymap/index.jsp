<%@ page contentType="text/html; charset=utf-8" %>

<%@page import="com.ynhenc.siteware.tag.*"%>
<%@page import="com.ynhenc.siteware.*"%>
<%@page import="com.ynhenc.gis.web.*"%>

<%
  JspMain jspMain = new JspMain();
  Request req = Request.createRequest(request, application);
  Service service = new MapRequestService();
  out.clearBuffer();
  jspMain.doService(service, req, response, out);
%>
