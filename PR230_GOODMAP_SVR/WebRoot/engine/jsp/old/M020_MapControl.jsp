<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="./M020_MapControl_Act.jsp" />

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%> 

<c:set var="zoomLevelCurr" value="${ param.zoomLevelCurr != null ? param.zoomLevelCurr : gisProject.topLevel }" scope="request" />

<% out.clearBuffer(); out.clear(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="imagetoolbar" content="no"/>
<LINK REL="SHORTCUT ICON" HREF="../../favicon.ico"/>

<title>MapControl V1.0.005 ${visitNo}</title>

<link rel="stylesheet" type="text/css" href="../css/css_003_GoodMapControl.css"></link>
<link rel="stylesheet" type="text/css" href="../js/js_007_dragresize/dragresize.css"></link>

<script type="text/javascript">

var mapSvrHostName = "<%= request.getServerName() %>" ;
var mapSvrPortNo   = "<%= request.getServerPort() %>" ; 

var initMapX = ${ cen.x } ;
var initMapY = ${ cen.y } ;

var initMapWidth = ${ width };
var initMapHeight = ${ height };

var initMapNo = ${ empty param.mapNo ? 0 : param.mapNo };

// background logo image url 
var initLogoImage = '${ empty param.logoImage ? 0 : param.logoImage }' ;
// map scale bar on off
var initScaleBar = ${ empty param.scaleBar ? 0 : param.scaleBar };
// map compass on off
var initCompass = ${ empty param.compass ? 0 : param.compass };

//var panInfo = null; 

var panInfo = 
    {   
        scaleList :
            [
                <c:forEach var="levelScale" items="${gisProject.zoomLevelList.levelScaleList }" varStatus="levelScaleStatus"  >
                    ${ levelScale.scale } ${ levelScaleStatus.last ? '' : ', ' } 
                </c:forEach>
            ]         
        , 
        distList :
            [
                <c:forEach var="levelScale" items="${gisProject.zoomLevelList.levelScaleList }" varStatus="levelScaleStatus"  >
                    ${ levelScale.dist } ${ levelScaleStatus.last ? '' : ', ' } 
                </c:forEach>
            ]         
        ,        
        mbrPhysScr : 
            { 
                  minX : ${ mbrPhysScr.min.x } 
                , minY : ${ mbrPhysScr.min.y }
                , maxX : ${ mbrPhysScr.max.x }
                , maxY : ${ mbrPhysScr.max.y } 
            } 
       ,
       scalePhysScr : ${ scalePhysScr }
    };  

function bodyOnLoad() {
    goodMapOnLoad();
}

</script>

<script type="text/javascript" src="../js/js_005_ext-2.2/adapter/prototype/prototype_1.6.1.js"></script>
<script type="text/javascript" src="../js/js_004_scriptaculous/scriptaculous.js"></script>
<script type="text/javascript" src="../js/js_007_dragresize/dragresize.js"></script> 

<script type="text/javascript" src="../js/js_003_map/js01_mapCommon.js"></script>
<script type="text/javascript" src="../js/js_003_map/js02_mapControl.js"></script> 
<script type="text/javascript" src="../js/js_003_map/js03_mapControl.js"></script>   

</head>

<body onload="javascript: bodyOnLoad(); " style="" >
<div id="topPaneMapControl" class="topPaneMapControl" style="position: absolute; top: 0 ; left: 0; width: ${width} ; height: ${ height };" 
    <c:if test="${ not empty param.logoImage }" >
        style="background-image: url( '${ param.logoImage }' );" 
    </c:if>
>
    <div id="mapPane" style="position: absolute; top: 0 ; left: 0; width: 100% ; height: 100%; " >
	    <div id="mapCellPane" class="mapCellPane"
	       style="position: absolute; top: 0 ; left: 0; width: 100% ; height: 100%; z-index: 10; " >
			<img id="mapImage" class="mapImage" style="cursor:hand; "
			    width = "${width}" height="${ height }"
	            border="0" 
	            align="absmiddle"
	            src="../image/map/white.png"
	            ondragend="" 
	            onload="javascript: mapImage_OnLoad(); "
	            onerror="javascript: setLoadingLogoVisible( false ); " 
			/>
		</div>
	</div>
	<div id="toolTipPane" style="position: absolute; top: 6 ; left: 6 ; z-index: 20; cursor:default; visibility: visible; " >
	   <iframe id="toolTipFrame" width="100" height="22" src="" frameborder="0" scrolling="no" ></iframe>
	   <div id="toolTipContent" style="background: #FFFFFF; border: 1px solid #272727; color: black; padding: 1px; font-size: 12px; visibility: hidden; ">
	   </div> 
    </div>
	<div id="mapCompassPane" style="position: absolute; top: 6 ; left: 6 ; z-index: 20; cursor:move; visibility:hidden; " >
        <img src="../image/map/mapCompass_05.gif" border="0" width="60" height="60" align="absmiddle" />
    </div>
    <div id="mapScalePane" class="mapScalePane" style="maring: 0; padding: 0; position: absolute; top: ${ height - 50 } ; left: 0 ; z-index: 20; cursor:hand; visibility:hidden; " >
        <li class="mapScale" ><span id="mapScaleVal" style="background: ThreeDFace; "></span></li>
        <li class="mapScale" ><img id="mapScaleBarImg" src="../image/map/mapScale_02.gif" border="0" align="absmiddle" /></li>
    </div>	
    <div id="zoomLogoPane" style="position: absolute; left: 0; top: 0; z-index: 22; visibility: hidden;">
	    <img src="../image/ajax-loader/grid-loading.gif" />
	</div>
	<div id="zoomInLogoPane" style="position: absolute; left: 0; top: 0; z-index: 22; visibility: hidden;">
        <img src="../image/map/mousewheel_zoomin.gif" />
    </div>
    <div id="zoomOutLogoPane" style="position: absolute; left: 0; top: 0; z-index: 22; visibility: hidden;">
        <img src="../image/map/mousewheel_zoomout.gif" />
    </div>
    
</div>
<div id="loadingLogoPane" style="position: absolute; left: 0; top: 0; z-index: 1; visibility: hidden;">
    <img src="../image/loading_map.gif" width="50" height="50"/>
</div>

<form id="mainForm">

    <input type="hidden" id="mapNo" value="${ param.mapNo }" />
    
    <input type="hidden" id="width" value="${ width }" />
    <input type="hidden" id="height" value="${ height }" />

    <input type="hidden" id="mapX" value="${ cen.x }" />
    <input type="hidden" id="mapY" value="${ cen.y }" />

    <input type="hidden" id="minX" value="${ min.x }" />
    <input type="hidden" id="minY" value="${ min.y }" />

    <input type="hidden" id="maxX" value="${ max.x }" />
    <input type="hidden" id="maxY" value="${ max.y }" />

    <input type="hidden" id="zoomLevelMax" value="${ gisProject.zoomLevelMax }" />
    <input type="hidden" id="zoomLevelCurr" value="${ zoomLevelCurr }" />

    <input type="hidden" id="mapImgUrl" value="" />

    <input type="hidden" id="format" value="${ param.format }" />

    <input type="hidden" id="circleMapX" value="" />
    <input type="hidden" id="circleMapY" value="" />
    <input type="hidden" id="circleRadius" value="" />

    <input type="hidden" id="srchLayerName" value="" />
    <input type="hidden" id="srchColName" value="" />
    <input type="hidden" id="srchColValue" value="" />
    <input type="hidden" id="srchLayerLineColor" value="" />
    <input type="hidden" id="srchLayerFillColor" value="" />
    <input type="hidden" id="srchLayerTextColor" value="" />
    <input type="hidden" id="srchLayerLineWidth" value="" />

    <input type="hidden" id="coordLayerName" value="" />

    <input type="hidden" id="poiLayerName" value="" />
    <input type="hidden" id="poiType" value="0" />
    <input type="hidden" id="poiId" value="" />
    <input type="hidden" id="poiMapX" value="" />
    <input type="hidden" id="poiMapY" value="" />
    <input type="hidden" id="poiMapX1" value="" />
    <input type="hidden" id="poiMapY1" value="" />
    <input type="hidden" id="poiLabel" value="" />
    <input type="hidden" id="poiTextColor" value="" />
    <input type="hidden" id="poiIcon" value="" />
    <input type="hidden" id="poiLineWidth" value="" />
    <input type="hidden" id="poiLineColor" value="" />

    <input type="hidden" id="clearSearch" value="" />
    <input type="hidden" id="removeAllPoiList" value="" />
    
    <input type="hidden" id="removeLayerName" value="" />
    <input type="hidden" id="removeShapeId" value="" />

    <input type="hidden" id="debug" value="${ param.debug }" />
    <input type="hidden" id="loadingLogo" value="${ param.loadingLogo }" />
</form>
</body>

</html>