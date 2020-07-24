<?xml version="1.0" encoding="EUC-KR" ?>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR" />
	<title>:: RouteTest</title>
	<link type="text/css" rel="stylesheet" href="../../../../static/css/v1.9.0/routetest.css"/>
</head>
<body>

	<div id="div_iframe">
		<iframe src="/PR170_ROUTE/index.jsp" name="result_frame" width="700" height="800"></iframe>
	</div>

	<div id="div_menu">
	
		<form action="../route/P001_SearchPath.jsp" method="get" target="result_frame">
		<h1>기본기능</h1><br/>
		<h2>1. 검색경로제공</h2><br/>
		sx: <input type="text" name="sx" value="127.061054"/> sy: <input type="text" name="sy" value="37.4392602"/><br/>
		ex: <input type="text" name="ex" value="127.046032"/> ey: <input type="text" name="ey" value="37.6528058"/><br/>
		<input class="btn" type="submit" value="확인" />
		</form>
		
		<form action="../info/P101_TurnInfo.jsp" method="get" target="result_frame">
		<h1>부가기능</h1><br/>
		<h2>1. 회전정보제공</h2><br/>
		s_link_id: <input type="text" name="s_link_id"/><br/>
		e_link_id: <input type="text" name="e_link_id"/><br/>
		<input class="btn" type="submit" value="확인" />
		</form>
		
		<form action="../info/P102_LinkInfo.jsp" method="get" target="result_frame">
		<h2>2. 링크정보제공</h2><br/>
		link_id: <input type="text" name="link_id"/><br/>
		<input class="btn" type="submit" value="확인" />
		</form>
		
		<form action="../info/P103_NodeInfo.jsp" method="get" target="result_frame">
		<h2>3. 노드정보제공</h2><br/>
		node_id: <input type="text" name="node_id"/><br/>
		<input class="btn" type="submit" value="확인" />
		</form>
		
		<form action="../info/P104_RoadInfo.jsp" method="get" target="result_frame">
		<h2>4. 도로정보제공</h2><br/>
		road_id: <input type="text" name="road_id"/><br/>
		<input class="btn" type="submit" value="확인" />
		</form>
		
		<form action="../route/P201_TurnShape.jsp" method="get" target="result_frame">
		<h1>형상기능</h1><br/>
		<h2>1. 회전형상제공</h2><br/>
		s_link_id: <input type="text" name="s_link_id"/><br/> e_link_id: <input type="text" name="e_link_id"/><br/>
		<input class="btn" type="submit" value="확인" />
		</form>
		
	</div>
	
</body>
</html>