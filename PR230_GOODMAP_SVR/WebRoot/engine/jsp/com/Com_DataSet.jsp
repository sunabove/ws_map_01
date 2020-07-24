<%@page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:catch var="sqlError">
   
	<jsp:include flush="false" page="./Com_DataSource.jsp" />

	<c:set var="pageNo" value="${ (param.pageNo == null ) ? 1 : param.pageNo }" scope="page" />
	<c:set var="noOfRowsPerPage" value="${ param.noOfRowsPerPage == null ? 10 : param.noOfRowsPerPage }" scope="page" />
	<c:set var="noOfRowsPerPage" value="${ noOfRowsPerPage < 1 ? 10000 : noOfRowsPerPage }" />
	<c:set var="noOfRow_Start"	 value="${ noOfRowsPerPage*( pageNo - 1) + 1 }" scope="page" />
	<c:set var="noOfRow_End"	 value="${ noOfRow_Start + noOfRowsPerPage }" scope="page" />	
	
	<c:if test="${param.sql != null}">
		<c:set var="sql" value="${param.sql}" />
	</c:if>

	<c:if test="${param.sql == null && param.tabName != null}" >
		<c:set var="sql" value="select * from ${param.tabName}" />
	</c:if>
	
	<c:set var="sql">
	      select *
             from (
                 select rownum rno , count(*) over() cnt , a.*
                 from (
                     ${sql}
                 ) a
             ) a  where rno >= ? and rno < ?
       </c:set>

	<sql:query var="resultSet" dataSource="${vmsDataSource}" sql="${sql}" scope="request" >		
		<sql:param value="${ noOfRow_Start }"	/>
		<sql:param value="${ noOfRow_End }"		/>
	</sql:query>

	<c:set var="queryId" value="${ queryId + 1 }" scope="application" />


</c:catch>

<c:if test="${ resultSet != null }" >

<c:if test="${ param.dataType == null || param.dataType == '' || param.dataType == 'XML' }">
	<ResultSet>
		<MetaData>
			<VERSION><c:out value="${DataSerVer}" /></VERSION>
			<QUERYID><c:out value="${queryId}" /></QUERYID>
			<UseJndi><c:out value="${useJndi}" /></UseJndi>
            <SqlError><c:out value="${sqlError}" /></SqlError>
			<DbUrl><c:out value="${dbUrl}" /></DbUrl>
			<SQL><c:out value="${ true ? '' : sql}" /></SQL>
			<ColumnInfoList>
				<c:forEach var="columnName" items="${resultSet.columnNames}">
					<columnName><c:out value="${columnName}" /></columnName>
				</c:forEach>
			</ColumnInfoList>
			<RowCount><c:out value="${resultSet.rowCount}" /></RowCount>
			<PageNo>${pageNo}</PageNo>
			<NoOfRowsPerPage>${noOfRowsPerPage}</NoOfRowsPerPage>
			<NoOfRow_Start>${noOfRow_Start}</NoOfRow_Start>
			<NoOfRow_End>${noOfRow_End - 1}</NoOfRow_End>
		</MetaData>
		<RowList>
			<c:forEach var="row" items="${resultSet.rowsByIndex}" varStatus="rowStatus">
				<ROW>
					<c:forEach var="col" items="${row}" varStatus="colStatus">
					   <c:out escapeXml="false" value="<${resultSet.columnNames[colStatus.index]}>${col}</${resultSet.columnNames[colStatus.index]}>" />
					</c:forEach>
				</ROW>
			</c:forEach> 
		</RowList>
	</ResultSet>
</c:if>

<c:if test="${ param.dataType == 'JSON' ||  param.dataType == 'json' }" >
	{ 
		"metaData" :
		[
			{
				  "version" : "${ComDataSetXmlVersion}"
				, "queryId" : "<c:out value="${queryId}" />"
				, "sqlError" : "<c:out value="${sqlError}" />"
				, "rowCount" : ${resultSet.rowCount}
				, "pageNo" : ${pageNo}
				, "noOfRowsPerPage" : ${noOfRowsPerPage}
				, "noOfRow_Start" : ${noOfRow_Start}
				, "noOfRow_End" : ${noOfRow_End - 1}
			}
		]
		, 
		"rowList" :
		[
			<c:forEach var="row" items="${resultSet.rowsByIndex}" varStatus="rowStatus">					
				{
					<c:forEach var="col" items="${row}" varStatus="colStatus">
						"${resultSet.columnNames[colStatus.index]}" : "<c:out value="${col}" />" ${ colStatus.last ? '' : ', ' } 
					</c:forEach>
				} ${ rowStatus.last ? '' : ', ' } 					
			</c:forEach>
		]
	}
</c:if>

</c:if> 

<c:if test="${ resultSet == null }" >
	<ResultSet>
        <MetaData>
            <VERSION><c:out value="${DataSerVer}" /></VERSION>
            <QUERYID><c:out value="${queryId}" /></QUERYID>
            <UseJndi><c:out value="${useJndi}" /></UseJndi>
            <SqlError><c:out value="${sqlError}" /></SqlError>
            <DbUrl><c:out value="${dbUrl}" /></DbUrl>
            <SQL><c:out value="${sql}" /></SQL>
            <ColumnInfoList>
                <c:forEach var="columnName" items="${resultSet.columnNames}">
                    <columnName><c:out value="${columnName}" /></columnName>
                </c:forEach>
            </ColumnInfoList>
            <RowCount><c:out value="${resultSet.rowCount}" /></RowCount>
            <PageNo>${pageNo}</PageNo>
            <NoOfRowsPerPage>${noOfRowsPerPage}</NoOfRowsPerPage>
            <NoOfRow_Start>${noOfRow_Start}</NoOfRow_Start>
            <NoOfRow_End>${noOfRow_End - 1}</NoOfRow_End>
        </MetaData>
    </ResultSet>
</c:if>


