<%@page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="com.ynhenc.comm.*"%>
<%@ page import="com.ynhenc.comm.db.*"%>
<%@ page import="oracle.jdbc.pool.*"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<% 
    final String dataSourceParamName = "vmsDataSource"; 
    Object dataSource = application.getAttribute( dataSourceParamName ); 
    if ( dataSource == null ) {  
        OracleDataSource ods = com.ynhenc.goodmap.DbFactory_GoodMap.getOracleDataSource(); 
        application.setAttribute( dataSourceParamName, ods ); 
        System.out.println( "DataSource for Oracle is initialized." ); 
    }
%> 

<c:set var="DataSerVer" value="DataSerVer 1.9.7"   scope="application" />
    
<c:set var="dbServer" value="220.90.136.132"   scope="application" />
<c:set var="dbPort"   value="1521"              scope="application" />
<c:set var="dbSid"    value="yhenc"             scope="application" />  
<c:set var="dbUser"   value="ynhenc"               scope="application" />
<c:set var="dbPasswd"   value="yhec0070"               scope="application" />
<c:set var="driver"   value="oracle.jdbc.driver.OracleDriver"   scope="application" />

<c:if test="${ false && vmsDataSource == null }" >

    <c:set var="dbUrl"    value="jdbc:oracle:thin:${dbUser}/${dbPasswd}@${dbServer}:${dbPort}:${dbSid}" scope="application" />
    
    <c:if test="${ false }">    
        <sql:setDataSource
            var="vmsDataSource"
            dataSource="jdbc/ynhenc"
            scope="request"     
        />	 
        <c:set var="useJndi" value="use" scope="application"/>   
    </c:if>
    
    <c:set var="sqlError_Ds" value="${sqlError}" scope="application" />
    
    <c:if test="${ true }">    
	    <sql:setDataSource 
            var="vmsDataSource"
            url="${dbUrl}"
            user="${dbUser}"
            password="${dbPasswd}"
            scope="request"     
        />  
        <c:set var="useJndi" value="no" scope="application"/>     
    </c:if>

</c:if>
