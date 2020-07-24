<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="./M020_MapControl_Act.jsp" />

<c:set var="margin" value="4" />
<c:set var="zoomLevelCurr" value="${ param.zoomLevelCurr != null ? param.zoomLevelCurr : gisProject.topLevel }" />

<jsp:include page="./C002_HtmlDocType.jsp"></jsp:include>

<html>
<head>

<jsp:include page="./C001_ComHeader.jsp"></jsp:include>

<title>MapControl V1.0.003 ${visitNo}</title>

<c:if test="${ false }" >
    <link rel="stylesheet" type="text/css" href="../css/css_001_MapControl.css">
</c:if> 

<!-- GMaps API Key that works for www.extjs.com -->
<script
 src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAUCG2rlIeVFJ07rCgVUYjhhSVbRpeAZk72H9nRSWIwLg0s1ul-BRlbCt360qbQumadan9ZlGxlCWzqg"
 type="text/javascript">
</script>
<!-- GMaps API Key that works for localhost --> 

<script type="text/javascript">
//<![CDATA[

var gmap;

function loadGoogleMap( paneId ) {
  if (GBrowserIsCompatible()) {
    gmap = new GMap2(document.getElementById( paneId ));

    var lonx = 126.50350074614;
    var laty = 33.500986544451;
    gmap.setCenter(new GLatLng( laty, lonx ), 14 );
    gmap.setMapType(G_SATELLITE_MAP);
    //map.setMapType(G_HYBRID_MAP); 
    //map.addControl(new GMapTypeControl());
    //map.addControl(new GSmallMapControl());
    //map.addControl(new GLargeMapControl()); 

    GEvent.addListener( gmap, "moveend",
             function() {  
                var level = gmap.getZoom();
                var scale = getGmapScale();
                //alert( "L = " + level + " : " + scale );
             }
    );
  }
}

function getGmapScale( ) {

    var DPI = 96 ; 

    var meterPerDot = ( 0.025399 / DPI )
    
	var scrSize = gmap.getSize();

	var distPixel = Math.sqrt( scrSize.width * scrSize.width + scrSize.height*scrSize.height );
	
	var bounds = gmap.getBounds();
	var min = bounds.getSouthWest();
	var max = bounds.getNorthEast();
	var distMeter = max.distanceFrom( min );

	var scale = distMeter / ( distPixel*meterPerDot ) ;
	
	return scale ;
}

//]]>
</script>

<script type="text/javascript">

function setLoadingLogoVisible( onOff ) {

    var loadingLogoPane = getObject( "loadingLogoPane" ); 
    
    var styleObj = loadingLogoPane.style;
    
    if( onOff ) {
        var parent = getObject( "mapImageTag" ) ; 
        
        var width = getNumber( "width" ) ;
        var height = getNumber( "height" ) ;
        
        styleObj.left = (width - 33) / 2  ;
        styleObj.top = (height - 33) / 2  ;
    }
    
    styleObj.visibility = onOff ? "visible" : "hidden" ;
} 

function setZoomLogo( vis , x , y , inOut ) {

	var paneZoomIn = getObject( "zoomInLogoPane" );

	var paneZoomOut = getObject( "zoomOutLogoPane" );
	
	var pane = ( inOut == "in" ) ? paneZoomIn : paneZoomOut ;

	if( vis == false ) {
		setVisible( paneZoomIn, vis );
		setVisible( paneZoomOut, vis );
	} else {	
		setVisible( pane, vis );
	}

	if( x != null && y != null ) {

		var style = pane.style;

		if( style != null ) {

			var w = pane.clientWidth;
	        var h = pane.clientHeight;

	        x = x - w/2;
	        y = y - h/2;

	        window.status = "x = " + x + ", y = " + y ;

	        style.left = x;
	        style.top = y;
		}
 
	}
	
}

function setVisible( obj , vis , x , y ) {
    if( obj != null ) {
        var style = obj.style;
        if( style != null ) {
            style.visibility = vis ? "visible" : "hidden" ;
        }
    }
}

function loadImage( img , src ) {
    if( img.alt != "" ) {
        img.alt = "";
        img.src = src ;
    }
}

// 시작. 지도 이미지 조작 함수들

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
    
var mapTileInfo = 
    {
        mapTileGroupList :
        [
            <c:forEach var="mapTileGroup" items="${gisProject.mapTileGroupList }" varStatus="status"  >
                {
                    rowNo : ${ mapTileGroup.rowNo },
                    colNo : ${ mapTileGroup.colNo },
                    pixelSize : { width : ${ mapTileGroup.pixelSize.width } , height :  ${ mapTileGroup.pixelSize.height } },
                    level : ${ mapTileGroup.levelScale.level }
                }
                ${ status.last ? '' : ', ' } 
            </c:forEach>
        ]
    } ; 
    
var initMapX = ${ cen.x } ;
var initMapY = ${ cen.y } ;

function mapRequest() {
    
    var event = window.event;   
    
    if( event.shiftKey ) {
       if( zoomOut() ) {
           mapRefresh();
       }
    } else if( event.ctrlKey ) {
        if( zoomIn() ) {
          mapRefresh();
        }
    } 
    
    if( true ) {
    } else if( ! drag ) {
        pan();
        mapRefresh();   
    } else if( drag ) {
        mapRefresh();   
    }
    
}

function getLevelDistCurrent() {
    return getLevelDist( getZoomLevelCurr() );
}

function getLevelDist( zoomLevel ) {
    var distList = panInfo.distList[ zoomLevel ]; 
    
    return distList;
} 

function getLevelScaleCurrent() {
    return getLevelScale( getZoomLevelCurr() );
}

function getLevelScale( zoomLevel ) {
    return 1.0/getLevelDist( zoomLevel );
} 

function getLevelScaleRelative( zoomLevel ) {

    var levelScale = panInfo.scaleList[ zoomLevel ]; 
    
    var levelScaleMax = panInfo.scalePhysScr ; 
    
    return ( levelScaleMax / levelScale ) ;
}

function getLevelScaleRelative_Old( zoomLevel ) {

    var levelScale = panInfo.scaleList[ zoomLevel ]; 
    
    var levelScaleMax = panInfo.scaleList[ ${ gisProject.zoomLevelMax } ]; 
    
    return ( levelScaleMax / levelScale ) ;
}

function pan() {  
    
    var e = window.event;
    
    if( e != null ) {
    
        var width = getNumber( "width" );
        var height = getNumber( "height" ); 
    
        var dx = e.offsetX - width/2.0 ;
        var dy = e.offsetY - height/2.0 ;
        
        setMapAreaMovedBy( dx , dy );
          
    }
      
}

function getZoomLevelMax() {
    return getNumber( "zoomLevelMax" );
}

function getZoomLevelCurr() { 
     return getNumber( "zoomLevelCurr" );
}

function getZoomLevelCurrent() { 
     return getZoomLevelCurr();
}

function setMapLevelListPane_Loc( x , y ) {

    var mapLevelListPane = getObject( "mapLevelListPane" );
        
    if( mapLevelListPane != null ) {
        var style = mapLevelListPane.style;
        style.top = 0;
        style.left = 0 ;
    }    
    
}

function zoomIn() {  // 확대 
    
    var zoomLevelCurr = getZoomLevelCurr() ;
    
    if( zoomLevelCurr > 0 ) {  
    
        setMapLevel( zoomLevelCurr - 1 );
        
        // setMapLevelListPane_Loc( 0, 0 );
        
        return true;
    } else {
       var msg = "더 이상 확대할 수 없습니다.";
       //showMessage( msg );
       return false;
    }
}

function zoomOut() { // 축소
    var zoomLevelCurr = getZoomLevelCurr() ;
    
    if( zoomLevelCurr < getZoomLevelMax() ) { 
    
        setMapLevel( zoomLevelCurr + 1 );
        
        // setMapLevelListPane_Loc( 0, 0 );
        
        return true;
    } else {
       var msg = "더 이상 축소할 수 없습니다." ;
       //showMessage( msg );
       return false;
    }
} 

function fullExtent() {   
     
    setMapLevel( getZoomLevelMax() );
    
    setMapCenter( initMapX , initMapY ) ;
    
    return true; 
    
} 

function setMapLocation( mapX, mapY ) {
    setMapCenter( mapX, mapY );
}

function setMapCenter( mapX, mapY ) {  

    setValue( "mapX", mapX );
    setValue( "mapY", mapY ); 
    
    setMapAreaMovedBy( 0, 0 );
        
}

function setMapLevel( mapLevel ) { 

    setValue( "zoomLevelCurr", mapLevel ); 
    
    setMapAreaMovedBy( 0, 0 );
        
}

function setMousePanValues() {
    var event = window.event;
    if( event != null ) {
        setValue( "pan.x" , event.offsetX );
        setValue( "pan.y" , event.offsetY );    
    }
}

function goBackward() {
    // 뒤로 가기
}

function goForeward() {
    // 앞으로 가
}

function toggleDebugOption() {

    var e = window.event;
            
    if( e != null && e.ctrlKey ) { 
        
        setValue( "debug" , -1 * getNumber( "debug" ) );
        
        window.status =  "debug=" + getValue( "debug" ) ;
        
    }
    
}

var mapReqNo = 0 ;

function getMapUrl() {

    if( true ) {    
        toggleDebugOption();
    }

    var url = "../.." ; 
    
    url += "/servlet/MapServlet?a=a" ;
    
    url += "&zoomLevelCurr=" + getValue( "zoomLevelCurr" ) ;
    url += "&format=" + getValue( "format" ) ;
    url += "&debug=" + getValue( "debug" ) ;
    
    if( true ) {    
        url += "&width=" + getValue( "width" ) + "&height=" + getValue( "height" ) ;
    }
    
    if( true ) {           
        url += "&mapX=" + getValue( "mapX" ) ;
        url += "&mapY=" + getValue( "mapY" ) ;
    } 

    if( false ) {      
        url += "&minX=" + getValue( "minX" ) ;
        url += "&minY=" + getValue( "minY" ) ;
        url += "&maxX=" + getValue( "maxX" ) ;
        url += "&maxY=" + getValue( "maxY" ) ;
    } 

    if( true ) {
        url += "&mapReqNo=" + ( mapReqNo ++ ) ;
    }
    
    return url;
}

function mapRefresh() {

    if( false ) {
    
        loadMapLevelPane(); 
        
    } else {
    
	    loadMapImage( true ); 
    } 
    
}

function getMapImageFormat() {
	return getValue( "format" );
}

function setMapImageFormat( format ) {
    setValue( "format", format );
}

function printMap() {
    //document.print();
    var mapImage = getObject( "mapImage" );
    if( mapImage != null ) {
        self.print();
    } else {
        window.print();
    }
}

// 끝. 지도 이미지 조작  함수들.

// 지도 정보 함수들.

function Pnt() { 
}

function Mbr() { 
}

function getMapLoc( x, y ) {
    return getMapLocation( x, y );
}

function getMapLocation( x, y ) {
    
    var mbr = getMbr();
    
    var width = getNumber( "width" ) + 0.0 ;
    var height = getNumber( "height" ) + 0.0 ; 
    
    var p = new Pnt();
    
    p.x = mbr.minX +  ( ( mbr.maxX - mbr.minX)*(x + 0.0)/width );
    p.y = mbr.minY +  ( ( mbr.maxY - mbr.minY)*(height - y + 0.0)/height );
    
    return p;  
}

function getMbr() {

    var mbr = new Mbr() ;
    
    mbr.minX = getNumber( "minX" );    
    mbr.minY = getNumber( "minY" );
    mbr.maxX = getNumber( "maxX" );
    mbr.maxY = getNumber( "maxY" );
    
    return mbr;
}

function getMapCenter() {

    var p = new Pnt();
    
    p.x = getNumber( "mapX" ) ;
    p.y = getNumber( "mapY" ) ;
    
    return p ;
}

function getMapDist( p, q ) {
    return getMapDistance( p, q );
}

function getMapDistance( p, q ) {
    
    var dx = p.x - q.x ;
    var dy = p.y - q.y ;
    
    return Math.sqrt( dx*dx + dy*dy );
    
}

function getMbrMovedBy( dx , dy ) {
    
    var width = getNumber( "width" );
    var height = getNumber( "height" ); 
        
    var zoomLevelCurr = getZoomLevelCurr();
    
    var scaleRelative = getLevelScaleRelative( zoomLevelCurr ); 
    
    //alert( "scaleRelative = " + scaleRelative );
    
    var mbrPhysScr = panInfo.mbrPhysScr ;
    
    var minX = mbrPhysScr.minX;
    var minY = mbrPhysScr.minY;
    
    var maxX = mbrPhysScr.maxX;
    var maxY = mbrPhysScr.maxY;  
    
    var distWidth = maxX - minX ;
    var distHeight = maxY - minY ; 
    
    var sx =  distWidth*scaleRelative*dx/width ;
    var sy = -distHeight*scaleRelative*dy/height ; 
    
    var mapX = getNumber( "mapX" );
    var mapY = getNumber( "mapY" );
    
    mapX += sx;
    mapY += sy; 
    
    var mbr = new Mbr() ;
    
    mbr.minX = ( mapX - distWidth*scaleRelative/2.0 );
    mbr.minY = ( mapY - distHeight*scaleRelative/2.0 );
    mbr.maxX = ( mapX + distWidth*scaleRelative/2.0 );
    mbr.maxY = ( mapY + distHeight*scaleRelative/2.0 );
    
    mbr.cenX = mapX;
    mbr.cenY = mapY;
    
    if( false ) {        
        alert( scale ); 
    }
        
    return mbr;
}

function setMapAreaMovedBy( dx , dy ) {

    var mbr = getMbrMovedBy( dx , dy );
        
    setValue( "mapX" , mbr.cenX );
    setValue( "mapY" , mbr.cenY );  
    
    setValue( "minX" , mbr.minX );
    setValue( "minY" , mbr.minY );
    
    setValue( "maxX" , mbr.maxX );
    setValue( "maxY" , mbr.maxY );
    
}

var jsonResult ; // TODO 2008.08.20.001 JSON 리턴 값 저장소 최적화 필요.

function doGetLayerCoord( srchLayerName  ) {

    var url = "./M011_MapLayerCoord_Act.jsp" ; 
    
    setValue( "srchLayerName" , srchLayerName );
    
    doSubmitUrlTarget( url, "abc" , doGetLayerCoord_Call );
    
    return jsonResult;
}

function doGetLayerCoord_Call( transport ) {

    var json = transport.responseText.evalJSON() ;   
    
    jsonResult = json; 
}

function doGetLayerSearchByCircle( srchLayerName , circleMapX , circleMapY, circleRadius  ) {

    var url = "./M012_MapLayerSearchByCircle_Act.jsp" ; 
    
    setValue( "srchLayerName" , srchLayerName );
    setValue( "circleMapX" , circleMapX );
    setValue( "circleMapY" , circleMapY );
    setValue( "circleRadius" , circleRadius );
    
    doSubmitUrlTarget( url, "abc" , doGetLayerSearchByCircle_Call );
    
    return jsonResult ;
}

function doGetLayerSearchByCircle_Call( transport ) {

    var json = transport.responseText.evalJSON() ;   
    
    jsonResult = json; 
}

function getCoordTplgToMapBoundary( mx , my ) {

    var mbr = getMbr();
    
    var vTop = 0;
    var hTop = 0;
    
    if( my > mbr.maxY ) {
        vTop = 0;
    } else if( my >= mbr.minY ) {
        vTop = 1;
    } else {
        vTop = 2;
    }
    
    if( mx < mbr.minX ) {
        hTop = 0;
    } else if( mx <= mbr.maxX ) {
        hTop = 1;
    } else {
        hTop = 2;
    }
    
    var top = 3*vTop + hTop ;
    
    return top ;
}

// 끝. 지도 정보 함수들.

// 시작. 데이터 관련 함수들.

function doSearch( srchLayerName, srchColName, srchColValue ,
         srchLayerLineColor, srchLayerFillColor , srchLayerTextColor , srchLayerLineWidth ) { // 레이어 속성 검색.
    
    setValue( "srchLayerName", srchLayerName );
    setValue( "srchColName", srchColName );
    setValue( "srchColValue", srchColValue );    
    setValue( "srchLayerLineColor", srchLayerLineColor );
    setValue( "srchLayerFillColor", srchLayerFillColor );
    setValue( "srchLayerTextColor", srchLayerTextColor );
    setValue( "srchLayerLineWidth", "" + srchLayerLineWidth );
    
    doSubmitTarget( "abc" );
    
    setValue( "srchLayerName", "" );
    
    return  true;
}

function doAddPoi_Old( poiLayerName, poiId, poiLabel , poiIcon, poiMapX, poiMapY ) { // POI 추가.

    setValue( "poiLayerName", poiLayerName );
    setValue( "poiType", "0" );
    setValue( "poiId", poiId );
    setValue( "poiMapX", poiMapX );
    setValue( "poiMapY", poiMapY );
    setValue( "poiLabel", poiLabel );
    setValue( "poiIcon", poiIcon ); 
    
    doSubmitTarget( "abc" );
    
    setValue( "poiId", -1 );
    
    return true;
}

// POI 추가.

function doAddPoi( poi ) { 

    var val = doAdd_UserObject( "0", poi );
    
    return val ;
}

function doAddLoi( poi ) { 
    
    var val = doAdd_UserObject( "1", poi );
    
    return val ;
    
}

function doAdd_UserObject( type, poi ) { 

    setValue( "poiType", type );
    setValue( "poiLayerName", poi.layerName );
    setValue( "poiId", poi.id );
    
    setValue( "poiMapX", poi.mapX );
    setValue( "poiMapY", poi.mapY );
    setValue( "poiMapX1", poi.mapX1 );
    setValue( "poiMapY1", poi.mapY1 );
    
    setValue( "poiLabel", poi.label );
    setValue( "poiTextColor", poi.textColor ); 
    setValue( "poiIcon", poi.icon ); 
    
    setValue( "poiLineColor", poi.lineColor );
    setValue( "poiLineWidth", poi.lineWidth );
    
    doSubmitTarget( "abc" );
    
    setValue( "poiId", -1 );
    
    return true;
}
// 끝. POI 추가

function doAddLinePoi_Old( poiLayerName, poiId, poiLineColor, poiLineWidth, poiMapX, poiMapY, poiMapX1, poiMapY1 ) { // LOI 추가.

    setValue( "poiLayerName", poiLayerName );
    setValue( "poiType", "1" );
    setValue( "poiId", poiId );
    setValue( "poiMapX", poiMapX );
    setValue( "poiMapY", poiMapY );
    setValue( "poiMapX1", poiMapX1 );
    setValue( "poiMapY1", poiMapY1 );
    setValue( "poiLineColor", poiLineColor );
    setValue( "poiLineWidth", poiLineWidth );
    
    doSubmitTarget( "abc" );
    
    setValue( "poiId", -1 );
    
    return true;
}

function doMapClear() { // 검색 레이어 삭제 및 POI 레이어 삭제.
    return doClearAll();
}

function doClear() { // 검색 레이어 삭제 및 POI 레이어 삭제.
    return doClearAll();
}

function doClearAll() { // 검색 레이어 삭제 및 POI 레이어 삭제.
    doClearSearch();
    doRemoveAllPoiList();
    
    return true;
}

function doClearSearch() { // 검색 레이어 삭제.

    setValue( "clearSearch" , "true" ) ; 
    
    doSubmitTarget( "abc" ); 
    
    setValue( "clearSearch" , "" ) ;
    
    return true;
    
}

function doRemoveAllPoiList() { // 추가 POI 레이어 삭제.

    setValue( "removeAllPoiList", "true" ) ;  
    
    doSubmitTarget( "abc" );
    
    setValue( "removeAllPoiList", "" ) ;  
    
    return true;
    
} 

// 끝. 데이터 관련 함수들.


function setTarget( target ) {
    var mainForm = getObject( "mainForm" ); 
    mainForm.target = target;
}

function getTarget( ) {
    var mainForm = getObject( "mainForm" ); 
    return mainForm.target;
}

function doSubmitTarget( target ) {

    var url = "./M010_MapModel.jsp" ;  
    var onSuccessCall = null ;
    doSubmitUrlTarget( url, target , onSuccessCall ); 
    
}

function doSubmitUrlTarget( url, target , onSuccessCall ) {
    setTarget( target ); 
    if( onSuccessCall == null || onSuccessCall == '' ) {
        onSuccessCall = function( transport ) {
            var target = getTarget();
            if( target == '' || target == null ) {
                loadMapPage( transport.responseText ); 
            }
        } ;
    }
    callAjax( url , onSuccessCall );
}

function callAjax( url , onSuccessCall ) {

    var parameters = {
           "a"            : ""            
        , "mapX" : getValue( "mapX" )
        , "mapY" : getValue( "mapY" )                  
        
        , "width" : getValue( "width" )
        , "height" : getValue( "height" )
        
        , "zoomLevelCurr" : getValue( "zoomLevelCurr" )
        
        , "srchLayerName" : getValue( "srchLayerName" )
        , "srchColName" : getValue( "srchColName" )
        , "srchColValue" : getValue( "srchColValue" )
        , "srchLayerLineColor" : getValue( "srchLayerLineColor" )
        , "srchLayerFillColor" : getValue( "srchLayerFillColor" ) 
        , "srchLayerTextColor" : getValue( "srchLayerTextColor" ) 
        , "srchLayerLineWidth" : getValue( "srchLayerLineWidth" )
        
        , "circleMapX" : getValue( "circleMapX" )
        , "circleMapY" : getValue( "circleMapY" )
        , "circleRadius" : getValue( "circleRadius" )
        
        , "poiLayerName" : getValue( "poiLayerName" )
        , "poiId" : getValue( "poiId" ) 
        , "poiType" : getValue( "poiType" ) 
        , "poiMapX" : getValue( "poiMapX" ) 
        , "poiMapY" : getValue( "poiMapY" ) 
        , "poiMapX1" : getValue( "poiMapX1" ) 
        , "poiMapY1" : getValue( "poiMapY1" ) 
        , "poiLabel" : getValue( "poiLabel" ) 
        , "poiTextColor" : getValue( "poiTextColor" ) 
        , "poiIcon" : getValue( "poiIcon" ) 
        , "poiLineColor" : getValue( "poiLineColor" ) 
        , "poiLineWidth" : getValue( "poiLineWidth" ) 
        
        , "clearSearch" : getValue( "clearSearch" ) 
        
        , "removeAllPoiList" : getValue( "removeAllPoiList" ) 
        
        , "debug" : getValue( "debug" ) 
        , "b" : ""
        
    };

    callAjaxUrlParams( url , parameters, onSuccessCall ); 
}

function callAjaxUrlParams( url , parameters, onSuccessCall ) {

    new Ajax.Request(
        
        url ,

        {
            asynchronous: false , 
            method: 'post', 
            encoding: 'UTF-8' ,
            "parameters" :  parameters , 
            onCreate: function() {  
            } , 
            onComplete: function() { 
            } , 
            onFailure: function() { 
                setLoadingLogoVisible( false );
            } ,
            onSuccess:  onSuccessCall
        }
    )
    
}

// 끝. 공통함수.

// 히스토리 관리 함수.

var mapHistList = new Array(); 
var mapHistId =  -1  ; 

function getMapHist( idx ) {
    if( idx > -1 && idx < mapHistList.length ) {
        return mapHistList[ idx ];
    } else {
        return null;
    }
}

function manageMapHistory() { 

    var size = mapHistList.length ;  

    var hn = { 
            "zoomLevelCurr" : getValue( "zoomLevelCurr" ) , 
            "mapX" : getValue( "mapX" ) , 
            "mapY" : getValue( "mapY" ) ,
            "a" : ""
        }  ;
        
    var hc = getMapHist( mapHistId ) ;    
    var isSameHist = false;
    
    if( hc != null ) { 
        isSameHist = ( hn.zoomLevelCurr == hc.zoomLevelCurr && hn.mapX == hc.mapX && hn.mapY == hc.mapY );
    }  
        
    if( isSameHist ) { // 같은  데이터 일 때....
        // do nothing !!! 
    } else if( hc == null  ) { // 처음 일 때....
        mapHistList.push( hn );
        mapHistId ++ ;
    } else {
        for(  ; mapHistId < mapHistList.length -1 ; ) {
            mapHistList.pop();
        }
        mapHistList.push( hn );    
        mapHistId ++ ;  
    }
    
    //alert( mapHistList.length );
    
}

function goMapPrev() {

    return goMapHistroy( -1 );
    
}

function goMapNext() {

    return goMapHistroy( 1 );
    
}

function goMapHistroy( inc ) {

    var h = getMapHist( mapHistId + inc ) ; 
    
    if( h != null ) {
         mapHistId += inc ;
         
         setMapLevel( h.zoomLevelCurr );
         setMapCenter( h.mapX, h.mapY );
    
        loadMapImage( false );
        
        return true;
    }
    
    return false;
    
}

// 끝. 히스토리 관리 함수.

// 시작. 지도 로딩 함수.

function loadMapPage( htmlText ) {
    if( htmlText.indexOf( "<sucess/>" ) > -1 ) {
        $("mapDataTag").update( htmlText ) ; 
        loadMapImage();
    } else {
        var msg = "지도 로딩 실패. 잠시후 다시 시도해 주세요!";
        showMessage( msg );
    }
}

function getPreLoadMapImage() { 

    var mapImgUrl = getValue( "mapImgUrl" ) ;
    var width = getNumber( "width" ) ;
    var height = getNumber( "height" ) ; 
    
    var mapImage = getObject( "mapImage" );
    
    mapImage.src = mapImgUrl ;
    mapImage.width = width;
    mapImage.height = height; 
    
    return mapImage;
    
}

function loadMapImage( history ) {

    if( history ) {
        manageMapHistory();
    }

    var mapUrl = getMapUrl();
        
    setValue( "mapImgUrl", mapUrl );

    var loadingLogo = getNumber( "loadingLogo" );
    
    if( loadingLogo > 0 ) {

        setLoadingLogoVisible( true );
    
    }
    
    try {    
        var image = getPreLoadMapImage();
    } catch ( e ) {        
        setLoadingLogoVisible( false );
    }
    
    if( false ) {
        var mapImageTag = getObject( "mapImageTag" );       
        mapImage.style.visibility = "visible"; 
    }
    
}

function loadMapLevelPane() { 

    var zoomLevelCurr = getZoomLevelCurr(); 
    
    var mapTileGroup = mapTileInfo.mapTileGroupList[ zoomLevelCurr ]; 
    
    var mapLevelPane = getObject( "mapLevelPane_" + zoomLevelCurr );
    
    var table = getObject( "mapLevelPane_" + zoomLevelCurr + "_table" ); 
    
    if( table.rows.length < 1 ) {
    
        var width = getNumber( "width" );
        var height = getNumber( "height" ); 
        
        var tabRow,  tabCell ;
        
        var rowNo = mapTileGroup.rowNo , colNo = mapTileGroup.colNo ;
        
        //alert( "rowNo = " + rowNo + ", colNo = " + colNo );
        
        var mapTileNo ;
        
        var mapUrl = "" ; 
        
        for( var row = 0 ; row < rowNo ; row ++ ) { 
          
           tabRow = table.insertRow(-1);     
                      
           for( var col = 0 ; col < colNo ; col ++ ) {
           
                mapTileNo = row*colNo + col ; 
                
                mapUrl = "../../servlet/MapServlet?a=a";
                mapUrl += "&zoomLevelCurr=" + zoomLevelCurr ;
                mapUrl += "&mapTileNo=" + mapTileNo ; 
                
                if( col == 0 && row == 0 ) {
                   mapUrl += "&debug=1" ;               
                }
                
                tabCell = tabRow.insertCell(-1);
                tabCell.innerHTML = "<img src='" + mapUrl + "' width=${ width } height=${ height } border=0 align=absmiddle />" ;
                
                //alert( tabCell.innerHTML );
                tabCell.bgColor = "lemonchiffon";
                
           }
        }
         
    }
    
    if( mapLevelPane != null ) {
    
        var mapLevelListPane = getObject( "mapLevelListPane" );
    
        for( var i = 0 , iLen = mapLevelListPane.childNodes.length ; i < iLen ; i ++ ) {
            setVisible( mapLevelListPane.childNodes( i ), false ); 
        } 
        
        setVisible( mapLevelPane, true ); 
        
    } 
    
}

// 끝. 지도 로딩 함수들.

</script>

<script type="text/javascript">

function checkMapDimension() {

    var width = getValue( "width" )
    var height = getValue( "height" ); 
    
    var mapImage = document.getElementById( "mapImage" );
    
    if( mapImage != null ) {
	    var margin = 0 ; 
	
	    width = mapImage.clientWidth;
	    height = mapImage.clientHeight;
	    
	    setValue( "width" , width );
	    setValue( "height", height ); 
    } else {
        alert( "mapImage is null!" );
    }
    
}

function effectFunction(element){
   new Effect.Opacity(element, {from:1.0, to:1.0, duration: 0.0 });
}

function bodyOnLoad() {

    if( false ) {
        checkMapDimension();
    }

    mapRefresh();
    
    initMouseEvent();
    
    if(  true ) {    
	    new Draggable( dragObjId , { starteffect: '', endeffect: '', revert: false , zindex : 10 } );
	    
	    new Draggable( "mapCompassPane" , { starteffect: '', endeffect: '', revert: false , zindex : 10 } );
	    
	    new Draggable( "mapScalePane" , { starteffect: '', endeffect: '', revert: false , zindex : 10 } );	    
    } 

    loadGoogleMap( 'googleMapPane' ); 
    
} 

// 마우스 드래그 처리.

function getMapImage() {
    return getObject( "mapImage" );
}

function getStyle( id ) {
    var obj = getObject( id );
    return  ( obj != null ? obj.style : null ) ;
}

function getMapCursor() {
    var style = getStyle( "mapImage" );
    return ( ( style != null ) ? style.cursor : null );
}

function setMapCursor( cursor ) {
    var style = getStyle( "mapImage" );
    if( style != null ) { 
        style.cursor = cursor ; 
        mapCursor = cursor;
    }
}

var dragObjId = "mapPane" ; // "mapCellPane";     //var dragObjId = "mapImage" ; 
        
var drag = false;
var down = false;
var sx = -1, sy = -1 , ex = -1, ey = -1;

var mapCursorPrev = "" ;
var mapCursor ;

function mapMouseDown() { 
	var e = window.event;
    var button = e.button ;

    if( down ) {
        return ;
    } else {
    
	    down = true ;
	    
	    mapCursorPrev = getMapCursor(); 
	    
	    //alert( mapCursorPrev ); 
	    
	    if( e!= null ) {
	        sx = e.screenX ;
	        sy = e.screenY ;
	        //window.status = ( "down x = " +  sx + ", y = " + sy ); 
	    } 
    }  
}

function mapMouseMove() {

    if( down ) {
        if( "move" != mapCursor ) {
            setMapCursor( "move" );
        }        
    }

    var e = window.event;
    
    if( e != null ) {
    
        var p = getMapLoc( e.x, e.y ); 
        
        window.status = ( "TM : X = " + p.x + ", Y = " + p.y );
    }
}

var mapDispMode = ${ param.mapDispMode != null ? param.mapDispMode : 1 } ; // 1 : map, 2 : image , 3 : overlay

function getMapDispMode() {
	return mapDispMode;
}

function getMapDisplayMode() {
	return getMapDispMode();
}

function setMapDispMode( dispMode ) {
    setMapDisplayMode( dispMode );
}

function setMapDisplayMode( dispMode ) {

	mapDispMode = dispMode ;
	
	var format = "JPG" ;

	if( mapDispMode > 1 ) {
		 format = "GIF";
	}

	setMapImageFormat( format );
	
}

var mode = 0 ; // 0 : normal, 1 : distance, 2 : area

var clickPrev = null , clickCurr = null ;
var clickTimePrev = -1 ; clickTimeCurr = -1 ;
var clickNo = 0;
var lineClickNo = 0;
var lineObjNo = -1 ;
var distTotal = 0;
var distPrev = 0;

function setMapMode( m ) {

    mode = m ; 
    
    if( true || m == 0 ) {
        clickPrev = null;
        clickCurr = null;
        lineClickNo = 0;
        distTotal = 0;
        distPrev = 0;
        lineObjNo ++ ;
    }
    
    var cursorName = "hand";
    
    cursorName = m === 1 ? "crosshair" : "hand" ;
    
    var mapImage = getObject( "mapImage" );
    
    if( mapImage != null ) {
        var style = mapImage.style;
        if( style != null ) {
            style.cursor = cursorName ;
        }
    }
    
}

function formatDist( dist_Meter ) {

       var unit = "M" ;
        
       if( dist_Meter > 1000 ) { 
        
           dist_Meter = ( dist_Meter/1000 );
            
           unit = "KM";
        
       }
        
       var label = formatNumber( dist_Meter, 2, false,true ) + unit;
       
       return label;
}

function getDistPoiIconName( kind , id ) {

    kind = kind % 3;
    
    if( kind == 0 ) {
        kind = "ball" ;
    } else if( kind == 1 ) {
        kind = "dia";
    } else {
        kind = "rect";
    }
    
    var no =  ( lineClickNo % 5 ) + 1  ;
    no = "00" + no;
    
    var iconName = "" + kind + "_10x10_" + no;
    
    return iconName;
}

function doAdd_MeasureDist( e0, e1 ) {

    var id = lineClickNo ;
    
    var layerName = "MEASURE_DIST_POI_" + lineObjNo ;  
    var label = lineClickNo + 1 ;
    var iconName = "";
    var textColor = "#000000";
    
    var loc = null;
    
    var poi; 
    
    var headShow = false;
    
    if( e0 != null ) {  
    
	    loc = e0 ;
	    
	    var idPrev = id - 1;
	    
	    idPrev = ( idPrev < 0 ) ? 0 : idPrev ;
	    
	    label = idPrev ;
	    
	    var dist = distPrev;
	    
	    var labelHead = headShow ? "(" + idPrev + ") " : "" ;
	    
	    iconName = getDistPoiIconName( lineObjNo, id );
	    
	    if( dist > 0 ) {    
	       label = labelHead + formatDist( dist );
	    } else {
	       label = labelHead ;
	    }
	    
	    poi = {
	                   "id" : idPrev ,
	                   "layerName" : layerName ,
	                   "label" : label ,
	                   "icon" : iconName ,
	                   "mapX" : loc.x ,
	                   "mapY" : loc.y ,
	                   "textColor" : textColor ,
	                   "a" : ""
	               }; 
	               
	   doAddPoi( poi );
   
   }
   
   if( e1 != null ) { 
   
       textColor = "#FF0000";
       
       iconName = getDistPoiIconName( lineObjNo, id );
   
       locPrev = e0; 
        
       locCurr = e1;
        
       var dist = getMapDist( locPrev, locCurr ); 
       
       distTotal += dist;
       
       var labelHead = headShow ? "(" + id + ") " : "" ;
       
       label = labelHead + "총: " + formatDist( distTotal );
   
	   poi = {
	                   "id" : id ,
	                   "layerName" : layerName ,
	                   "label" : label ,
	                   "icon" : iconName ,
	                   "mapX" : locCurr.x ,
	                   "mapY" : locCurr.y ,
	                   "textColor" : textColor ,
	                   "a" : ""
	               }; 
	               
	   doAddPoi( poi );
	   
	   distPrev = dist;        
	   
   }
   
   if( e0 != null && e1 != null ) {
   
        var layerName = "MEASURE_DIST_LOI_" + lineObjNo ;   
   
        var lineColor = "#FF0000";
        var lineWidth = "2";
        
        id = lineClickNo ;
    
        locPrev = e0;
        
        locCurr = e1;

        label = "";
        
        poi = {
                       "id" : id ,
                       "layerName" : layerName ,
                       "label" : label ,
                       "icon" : iconName ,
                       "mapX" : locPrev.x ,
                       "mapY" : locPrev.y ,
                       "mapX1" : locCurr.x ,
                       "mapY1" : locCurr.y ,
                       "textColor" : textColor ,
                       "lineColor" : lineColor ,
                       "lineWidth" : lineWidth ,
                       "a" : ""
                   }; 
                   
       doAddLoi( poi );
       
   }
   
   lineClickNo ++ ; 
   
}

function mapMouseUp() {

	down = false; 
    drag = false; 

    var isDblClick = false ;

    if( true ) {

	    clickTimeCurr = getCurrentTimeMillis();
	
	    var clickIntv = clickTimeCurr - clickTimePrev ;
	
	    if( clickIntv > 0 && clickIntv < 300 ) {
	        isDblClick = true;

	        //window.status = ( "clickIntv = " + clickIntv ) ;
	    }

	    clickTimePrev = clickTimeCurr ;

    }
    
     var e = window.event;
     var button = e.button ;
     
     if( e == null ) { // 이벤트가 없거나 
        window.status = ( "wong event" );
     } else if( true ) { 

         var isLeftClick = Event.isLeftClick( e ) ; 
         var isRightClick = ( ! isLeftClick ) ;

         var x = e.x;
         var y = e.y;
              
	     ex = e.screenX ; 
	     ey = e.screenY;
	     
	     var dx = ex - sx , dy = ey - sy ;
	     
	     var distum = Math.abs( dx*dy ) ;
	     var mouseSense = ( distum >= 3 ) ;

	     if( isDblClick ) {

		     var mapLoc = getMapLocation( x, y );

		     setMapLocation( mapLoc.x, mapLoc.y ); 
		     
             if( e.shiftKey || e.ctrlKey || isRightClick ) { 
           	     setZoomLogo( true, x, y, "out" );
           	     zoomOut();
                 loadMapImage( true ); 
		     } else { 
		    	 setZoomLogo( true, x, y, "in" );
                 zoomIn();
                 loadMapImage( true ); 
		     } 
	    	 
	     } else if( isRightClick ) { // 오른 쪽 버튼 클릭시 ....
		     // do nothing ...
	     } else if( mouseSense ) { // 이동 모드.
	         down = false; 
             drag = false;   
     	     setMapAreaMovedBy( -dx, -dy );
		     loadMapImage( true );
		     //window.status = ( "drag dx = " + dx + ", dy = " + dy );
	     } else if( mode == 1 ) { // 거리 재기 모드.
		     
	       if( clickPrev == null ) {
	       
	           clickPrev = getMapLocation( e.x, e.y );
	           
	           doAdd_MeasureDist( clickPrev, clickCurr );

	           loadMapImage( true );
	           
	       } else if( true ) {
		       
	           clickCurr = getMapLocation( e.x, e.y );
	           
	           doAdd_MeasureDist( clickPrev, clickCurr );

	           window.status = ( "dist : dx = " + dx + ", dy = " + dy );
	           	           
	           clickPrev = clickCurr ; 

	           loadMapImage( true ); 
               
	       }
	       
	     } else if( false ) {
	         pan();
	         loadMapImage( true );
	     }   
     } else if( false ) { // 클릭 시에 .... 
	     pan();
	     loadMapImage( true ); 
     }; 
     
     if( true ) {
        setMapCursor( mapCursorPrev );
     }     
     
}

function mapMouseWheel() {
	
    var e = window.event; 

    if( e != null ) {

        var x = e.x , y = e.y ;

	    var wheelDelta = e.wheelDelta ;  
	
	    var mapLoc = getMapLocation( x, y );
	
	    setMapLocation( mapLoc.x, mapLoc.y ); 
	    
	    if( wheelDelta < 0 ) {
	    	setZoomLogo( true, x, y , "out" );
            zoomOut();
            loadMapImage( true ); 
	    } else if( wheelDelta > 0 ) {
	    	setZoomLogo( true, x, y , "in" );
            zoomIn();
            loadMapImage( true ); 
	    }

    }
    
}

function initMouseEvent() {
    var mapImage = getObject( "mapImage" );
    
    mapImage.onmousedown = mapMouseDown ;
    mapImage.onmousemove = mapMouseMove ;
    mapImage.onmouseup = mapMouseUp ;
    mapImage.onmousewheel = mapMouseWheel; 
}

function restoreMapImagePaneLocation() {
    var dragObj = getObject( dragObjId );
    if( dragObj != null && dragObj.style.top != 0 ) {
        dragObj.style.top = 0;
        dragObj.style.left = 0 ;
    }  
}

function showMapScale() {

    var mapScaleBarImg = getObject( "mapScaleBarImg" );
    
    var levelDist = getLevelDistCurrent(); 
    
    var dist_Meter = levelDist*3/100;
    
    var dist_Meter_Format = formatNumber( dist_Meter, 2, false,true ) ; 
    
    var text = "&nbsp;" + dist_Meter_Format + " m" + "&nbsp;" ;
    
    var mapScaleVal = getObject( "mapScaleVal" );
    
    mapScaleVal.innerHTML = text ;
}

function setZoomLevelListener( listener ) {
    alert( "here" );
    document.zoomLevelListener = listener ;
}

function mapImage_OnLoad() { // 지도 이미지 로딩후 콜되는 함수.
     
     setLoadingLogoVisible( false );
     setZoomLogo( false );
     restoreMapImagePaneLocation();

     showMapScale();

     if( mapDispMode > 1 ) {
    	 var mapLoc = getMapCenter();
    	 var mapLocWgs84 = getTmToWgs84( mapLoc.x, mapLoc.y );
    	 if( gmap != null ) {
        	 var gmapLevel = 19 - getZoomLevelCurr();
    		 gmap.setCenter( new GLatLng( mapLocWgs84.y, mapLocWgs84.x ), gmapLevel );
    	 }    	 
     }
     
     if( self.parent.mapZoomLevelListener != null ) {
        var zoomLevelCurr = getZoomLevelCurr(); 
        self.parent.mapZoomLevelListener( zoomLevelCurr );
     }
     
}

function toDecDeg( d , m , c ) {
	return ( d + (m/60.0) + (c/3600.0) ) ;
}

function getTmToWgs84( x_tm , y_tm ) {  

    var x0_tm = 130622.8958 ; 
    var y0_tm = -31813.4394 ; 

    var x0_wgs = toDecDeg( 126, 15, 26.80 ) ;
    var y0_wgs = toDecDeg(  33, 12, 23.82 ) ;

    var x1_tm = 180999.7194 ;
    var y1_tm = 6853.79475

    var x1_wgs = toDecDeg( 126, 47, 47.45 ) ;
    var y1_wgs = toDecDeg(  33, 33, 28.05 ) ;

    var dx = x_tm - x0_tm ;
    var dy = y_tm - y0_tm ;

    var x_wgs = x0_wgs + (x1_wgs - x0_wgs)*dx/(x1_tm - x0_tm );
    var y_wgs = y0_wgs + (y1_wgs - y0_wgs)*dy/(y1_tm - y0_tm );

    var wgs = new Pnt();

    wgs.x = x_wgs - 6.7/3600.0 ; 
    wgs.y = y_wgs - .2/3600.0 ;

    return wgs;
    
} 

function getTmToWgs84_Old( x_tm , y_tm ) {  

    var x0_tm = 132071.10546875 ;
    var y0_tm = -42586.9294 ;

    var x0_wgs = toDecDeg( 126 , 16 ,  5.28 ).toRad();
    var y0_wgs = toDecDeg(  33 ,  6 , 47.23 ).toRad();

    var x1_tm = 183888.17578125 ;
    var y1_tm = 6991.0181640625 ;

    var x1_wgs = toDecDeg( 126, 49, 37.25 ).toRad();
    var y1_wgs = toDecDeg(  33, 33, 31.40 ).toRad();

    var dx = x_tm - x0_tm ;
    var dy = y_tm - y0_tm ;

    var x_wgs = x0_wgs + (x1_wgs - x0_wgs)*dx/(x1_tm - x0_tm );
    var y_wgs = y0_wgs + (y1_wgs - y0_wgs)*dy/(y1_tm - y0_tm );

    var wgs = new Pnt();

    wgs.x = x_wgs.toDeg() + 3.95/3600.0 ; 
    wgs.y = y_wgs.toDeg() - 1.6/3600.0 ;  

    return wgs;
    
} 

// 끝. 마우스 드래그 처리.

</script>

<style >   

body {
    padding: 0;
    margin: 0;
    /*background-image: url( '../image/map/worldDotMap.png' ) ;*/
    background-image: url( '../image/bg/body_bg_tile.png' ) ;
} 
    
div {
    padding:0;
    margin:0; 
    background: transparent;
    text-align:center;
    vertical-align:middle;
    border: 0 red solid;
    clear: both;   
}

.topPaneMapControl {
   /*background: gold ;*/
   background-color: red ;
   background-image: url( '../image/bg/body_bg_tile.png' ) ;
}

.mapCellPane {
    /*background-color: gold ;*/
    /*background-image: url( '../image/bg/body_bg_tile.png' ) ;*/
}

.mapImage {   
    cursor: hand;
}

.mapScale {
    list-style: none ;
    list-style-type:none; 
    align: center;
    text-align: right;
    vertical-align: middle;
    /*float: left;*/
    margin:  0 ;
    padding: 0 ;
    border: 0 solid #99bbe8; 
    /*background-color: gray;*/
    /*background-color: #788D87;*/ 
    font-size: 10pt;
    font-family: tahoma,arial,sans-serif 궁서체 ;
    cursor: move;
}
</style>

</head>

<body onload="javascript: bodyOnLoad(); " style="width: 100%; height: 100%; " >

<div id="topPaneMapControl" class="topPaneMapControl" style="width: 100% ; height: 100%;" >
    <div id="mapPane" style="position: absolute; top: 0 ; left: 0; width: 100% ; height: 100%; " >
	    <div id="googleMapPane" class="mapCellPane" style="position: absolute; top: 0 ; left: 0; width: 100% ; height: 100%; z-index: 5; " >
	    </div>  
		<div id="mapCellPane" class="mapCellPane" style="position: absolute; top: 0 ; left: 0; width: 100% ; height: 100%; z-index: 10; " >
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
	<div id="mapCompassPane" style="position: absolute; top: 6 ; left: 6 ; z-index: 20; cursor:move; " >
        <img src="../image/map/mapCompass_05.gif" border="0" width="60" height="60" align="absmiddle" />
    </div>
    <div id="mapScalePane" class="mapScalePane" style="maring: 0; padding: 0; position: absolute; top: ${ height - 50 } ; left: 0 ; z-index: 20; cursor:hand; "  >
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

    <input type="hidden" name="width" value="${ width }" />
    <input type="hidden" name="height" value="${ height }" />

    <input type="hidden" name="mapX" value="${ cen.x }" />
    <input type="hidden" name="mapY" value="${ cen.y }" />

    <input type="hidden" name="minX" value="${ min.x }" />
    <input type="hidden" name="minY" value="${ min.y }" />

    <input type="hidden" name="maxX" value="${ max.x }" />
    <input type="hidden" name="maxY" value="${ max.y }" />

    <input type="hidden" name="zoomLevelMax" value="${ gisProject.zoomLevelMax }" />
    <input type="hidden" name="zoomLevelCurr" value="${ zoomLevelCurr }" />

    <input type="hidden" name="mapImgUrl" value="" />

    <input type="hidden" name="format" value="${ param.format }" />


    <input type="hidden" name="circleMapX" value="" />
    <input type="hidden" name="circleMapY" value="" />
    <input type="hidden" name="circleRadius" value="" />

    <input type="hidden" name="srchLayerName" value="" />
    <input type="hidden" name="srchColName" value="" />
    <input type="hidden" name="srchColValue" value="" />
    <input type="hidden" name="srchLayerLineColor" value="" />
    <input type="hidden" name="srchLayerFillColor" value="" />
    <input type="hidden" name="srchLayerTextColor" value="" />
    <input type="hidden" name="srchLayerLineWidth" value="" />

    <input type="hidden" name="coordLayerName" value="" />

    <input type="hidden" name="poiLayerName" value="" />
    <input type="hidden" name="poiType" value="0" />
    <input type="hidden" name="poiId" value="" />
    <input type="hidden" name="poiMapX" value="" />
    <input type="hidden" name="poiMapY" value="" />
    <input type="hidden" name="poiMapX1" value="" />
    <input type="hidden" name="poiMapY1" value="" />
    <input type="hidden" name="poiLabel" value="" />
    <input type="hidden" name="poiTextColor" value="" />
    <input type="hidden" name="poiIcon" value="" />
    <input type="hidden" name="poiLineWidth" value="" />
    <input type="hidden" name="poiLineColor" value="" />

    <input type="hidden" name="clearSearch" value="" />
    <input type="hidden" name="removeAllPoiList" value="" />

    <input type="hidden" name="debug" value="${ param.debug }" />
    <input type="hidden" name="loadingLogo" value="${ param.loadingLogo }" />
</form>
</body>

</html>