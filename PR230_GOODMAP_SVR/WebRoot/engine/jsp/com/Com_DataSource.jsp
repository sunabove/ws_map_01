<%@page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.ynhenc.comm.*"%>
<%@ page import="com.ynhenc.comm.db.*"%>
<%@ page import="oracle.jdbc.pool.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="DataSerVer" value="DataSerVer 1.9.7"   scope="application" />

<% 
    String dataSourceParamName = "vmsDataSource";
    Object dataSource = application.getAttribute( dataSourceParamName ); 
    if ( dataSource == null ) {  
        OracleDataSource ods = com.ynhenc.goodmap.DbFactory_GoodMap.getOracleDataSource(); 
        application.setAttribute( dataSourceParamName, ods ); 
        System.out.println( "DataSource for Oracle is initialized." ); 
    }    
%>