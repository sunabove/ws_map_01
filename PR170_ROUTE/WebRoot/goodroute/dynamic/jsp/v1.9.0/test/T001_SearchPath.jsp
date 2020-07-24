<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title> 경로 검색 </title>
</head>
<body onload=""> 
<h3>경로 검색 v1.0.08</h3>
<hr/>
<table>
	<tr>
		<td valign="top">
		      <form id="pathLoader" target="aaa" action="../route/P001_SearchPath.jsp" method="get">
		          sx: <input type="text" name="sx" value="126.924110" />
		          sy: <input type="text" name="sy" value="37.521641" />
		          <br/>
		          mx: <input type="text" name="mx" value="126.979255" />
                  my: <input type="text" name="my" value="37.572461" />
                  <br/>
                  ex: <input type="text" name="ex" value="126.998330" />
                  ey: <input type="text" name="ey" value="37.564243" />
                  <br/>
		          <input type="submit" value="go" />  
		      </form>		  
		</td>
		<td>
			<iframe width="400" height="400" frameborder="1" name="searchResult" src="about:blank">
	        </iframe> 
		</td>
	</tr>
</table>

<hr/>
</body>
</html>