<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="./M020_MapControl_Act.jsp" />

<c:set var="visitNo" value="${visitNo != null ? visitNo + 1 : 0 }" scope="session" />

<jsp:include page="./C002_HtmlDocType.jsp"></jsp:include>

<html>
<head>
<jsp:include page="./C001_ComHeader.jsp"></jsp:include>
<title>MapControl ${visitNo}</title>

<script>

// 시작. 지도 이미지 조작 함수들

function mapRequest() {
    
    var event = window.event;   
    
    if( event.wheelDelta < 0 ) {
        if( zoomOut() ) { 
            mapRefresh();
        }
    } else if( event.wheelDelta > 0 ) {
        if( zoomIn() ) {
            mapRefresh();
        }
    } else if( event.shiftKey ) {
       if( zoomOut() ) {
           mapRefresh();
       }
    } else if( event.ctrlKey ) {
        if( zoomIn() ) {
          mapRefresh();
        }
    } else if( true ) {
        pan();
        mapRefresh();   
    } 
    
}

var panInfo = 
    {  scaleList :
        [
            <c:forEach var="levelScale" items="${gisProject.zoomLevelList.levelScaleList }" varStatus="levelScaleStatus"  >
                ${ levelScale.scale } ${ levelScaleStatus.last ? '' : ', ' } 
            </c:forEach>
        ] 
        ,        
        mbrTopLevel : 
            { 
                  minX : ${ min.x } 
                , minY : ${ min.y }
                , maxX : ${ max.x }
                , maxY : ${ max.y } 
            }  
        , mpd : ${mpd}
    };
    
function getLevelScale( zoomLevel ) {
    var levelScale = panInfo.scaleList[ zoomLevel ]; 
    
    return levelScale;
}

function getLevelScaleRelative( zoomLevel ) {

    var levelScale = panInfo.scaleList[ zoomLevel ]; 
    
    var levelScaleMax = panInfo.scaleList[ ${ gisProject.zoomLevelMax } ]; 
    
    return ( levelScaleMax / levelScale ) ;
}

function pan() {  
    
    var event = window.event;
    
    if( event != null ) {
    
        var width = getNumber( "width" );
        var height = getNumber( "height" ); 
    
        var dx = event.offsetX - width/2.0 ;
        var dy = event.offsetY - height/2.0 ;
        
        setMapAreaMovedBy( dx , dy );
          
    }
      
}

function getZoomLevelMax() {
    return getNumber( "zoomLevelMax" );
}

function getZoomLevelCurr() { 
     return getNumber( "zoomLevelCurr" );
}

function zoomIn() { 
    // 확대 
    
    var zoomLevelCurr = getZoomLevelCurr() ;
    
    if( zoomLevelCurr > 0 ) {  
    
        setMapLevel( zoomLevelCurr - 1 );
        
        return true;
    } else {
       var msg = "더 이상 확대할 수 없습니다.";
       //showMessage( msg );
       return false;
    }
}

function zoomOut() {
    // 축소
    var zoomLevelCurr = getZoomLevelCurr() ;
    
    if( zoomLevelCurr < getZoomLevelMax() ) { 
    
        setMapLevel( zoomLevelCurr + 1 );
        
        return true;
    } else {
       var msg = "더 이상 축소할 수 없습니다." ;
       //showMessage( msg );
       return false;
    }
}

function fullExtent() {
    var zoomLevelMax = getZoomLevelMax();
    
    if( getZoomLevelCurr() < zoomLevelMax ) {   
     
        setMapLevel( zoomLevelMax );
        
        return true;
    } else {
        var msg = "현재 레벨이 최고 레벨입니다.";
        //showMessage( msg );
        
        return false;
    }
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

function getMapUrl() {

    if( true ) {    
        toggleDebugOption();
    }

    var url = "../.." ; 
    
    url += "/servlet/MapServlet?a=a" ;
    
    url += "&zoomLevelCurr=" + getValue( "zoomLevelCurr" ) ;
    url += "&format=" + getValue( "format" ) ;
    url += "&debug=" + getValue( "debug" ) ;
    
    if( false ) {    
        url += "&width=" + getValue( "width" ) + "&height=" + getValue( "height" ) ;
    }
    
    if( false ) {    
        url += "&minX=" + getValue( "minX" ) ;
        url += "&minY=" + getValue( "minY" ) ;
        url += "&maxX=" + getValue( "maxX" ) ;
        url += "&maxY=" + getValue( "maxY" ) ;
    } else {        
        url += "&mapX=" + getValue( "mapX" ) ;
        url += "&mapY=" + getValue( "mapY" ) ;
    } 
    
    return url;
}

function mapRefresh() {

    var mapUrl = getMapUrl();
    
    setValue( "mapImgUrl", mapUrl );
    
    loadMapImage(); 
}

// 끝. 지도 이미지 조작  함수들.

// 지도 정보 함수들.

function Pnt() { 
}

function Mbr() { 
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

function getMbrMovedBy( dx , dy ) {
    
    var width = getNumber( "width" );
    var height = getNumber( "height" ); 
        
    var zoomLevelCurr = getZoomLevelCurr();
    
    var scaleRelative = getLevelScaleRelative( zoomLevelCurr ); 
    
    //alert( "scaleRelative = " + scaleRelative );
    
    var mbrTopLevel = panInfo.mbrTopLevel ;
    
    var minX = mbrTopLevel.minX.toRad() ;
    var minY = mbrTopLevel.minY.toRad() ;
    
    var maxX = mbrTopLevel.maxX.toRad() ;
    var maxY = mbrTopLevel.maxY.toRad() ; 
    
    //alert( "minX = " + minX.toDeg() + ", maxX = " + maxX.toDeg() + " dTheta = " + ( maxX.toDeg() - minX.toDeg() ) );
    
    var distWidth = maxX - minX ;
    var distHeight = maxY - minY ; 
    
    var sx =  distWidth*scaleRelative*dx/width ;
    var sy = -distHeight*scaleRelative*dy/height ;
    
    // alert( "dw = " + distWidth.toDeg() + ", dh = " + distHeight.toDeg() + " sx " + sx + ", sy = " + sy );
    
    var mapX = getNumber( "mapX" ).toRad() ;
    var mapY = getNumber( "mapY" ).toRad() ;
    
    mapX += sx;
    mapY += sy; 
    
    var mbr = new Mbr() ;
    
    mbr.minX = ( mapX - distWidth*scaleRelative/2.0 ).toDeg() ;
    mbr.minY = ( mapY - distHeight*scaleRelative/2.0 ).toDeg() ;
    mbr.maxX = ( mapX + distWidth*scaleRelative/2.0 ).toDeg() ;
    mbr.maxY = ( mapY + distHeight*scaleRelative/2.0 ).toDeg() ;
    
    mbr.cenX = mapX.toDeg();
    mbr.cenY = mapY.toDeg();
    
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

function getDistanceWgs84( lon1, lat1, lon2, lat2 ) {

  lon1 = parseFloat( "" + lon1 );
  lat1 = parseFloat( "" + lat1 );
  lon2 = parseFloat( "" + lon2 );
  lat2 = parseFloat( "" + lat2 );
  
  var a = 6378137, b = 6356752.3142,  f = 1/298.257223563;  // WGS-84 ellipsiod
  var L = (lon2-lon1).toRad();
  var U1 = Math.atan((1-f) * Math.tan(lat1.toRad()));
  var U2 = Math.atan((1-f) * Math.tan(lat2.toRad()));
  var sinU1 = Math.sin(U1), cosU1 = Math.cos(U1);
  var sinU2 = Math.sin(U2), cosU2 = Math.cos(U2);
  
  var lambda = L, lambdaP = 2*Math.PI;
  var iterLimit = 20;
  while (Math.abs(lambda-lambdaP) > 1e-12 && --iterLimit>0) {
    var sinLambda = Math.sin(lambda), cosLambda = Math.cos(lambda);
    var sinSigma = Math.sqrt((cosU2*sinLambda) * (cosU2*sinLambda) + 
      (cosU1*sinU2-sinU1*cosU2*cosLambda) * (cosU1*sinU2-sinU1*cosU2*cosLambda));
    if (sinSigma==0) return 0;  // co-incident points
    var cosSigma = sinU1*sinU2 + cosU1*cosU2*cosLambda;
    var sigma = Math.atan2(sinSigma, cosSigma);
    var sinAlpha = cosU1 * cosU2 * sinLambda / sinSigma;
    var cosSqAlpha = 1 - sinAlpha*sinAlpha;
    var cos2SigmaM = cosSigma - 2*sinU1*sinU2/cosSqAlpha;
    if (isNaN(cos2SigmaM)) cos2SigmaM = 0;  // equatorial line: cosSqAlpha=0 (§6)
    var C = f/16*cosSqAlpha*(4+f*(4-3*cosSqAlpha));
    lambdaP = lambda;
    lambda = L + (1-C) * f * sinAlpha *
      (sigma + C*sinSigma*(cos2SigmaM+C*cosSigma*(-1+2*cos2SigmaM*cos2SigmaM)));
  }
  if (iterLimit==0) return NaN  // formula failed to converge

  var uSq = cosSqAlpha * (a*a - b*b) / (b*b);
  var A = 1 + uSq/16384*(4096+uSq*(-768+uSq*(320-175*uSq)));
  var B = uSq/1024 * (256+uSq*(-128+uSq*(74-47*uSq)));
  var deltaSigma = B*sinSigma*(cos2SigmaM+B/4*(cosSigma*(-1+2*cos2SigmaM*cos2SigmaM)-
    B/6*cos2SigmaM*(-3+4*sinSigma*sinSigma)*(-3+4*cos2SigmaM*cos2SigmaM)));
  var s = b*A*(sigma-deltaSigma);
  
  //s = s.toFixed(3); // round to 1mm precision
  return s;
}

Number.prototype.toRad = function() {  // convert degrees to radians
  return this * Math.PI / 180.0 ;
}

Number.prototype.toDeg = function() {  // convert radians to degrees
  return ( this / Math.PI ) * 180.0 ;
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

function setFormat( format ) {
    setValue( "format", format );
}

// 끝. 지도 정보 함수들.

// 시작. 데이터 관련 함수들.

function doSearch( srchLayerName, srchColName, srchColValue ,
         srchLayerLineColor, srchLayerFillColor , srchLayerTextColor , srchLayerLineWidth ) {
    
    setValue( "srchLayerName", srchLayerName );
    setValue( "srchColName", srchColName );
    setValue( "srchColValue", srchColValue );    
    setValue( "srchLayerLineColor", srchLayerLineColor );
    setValue( "srchLayerFillColor", srchLayerFillColor );
    setValue( "srchLayerTextColor", srchLayerTextColor );
    setValue( "srchLayerLineWidth", "" + srchLayerLineWidth );
    
    doSubmitTarget( "abc" );
    
    setValue( "srchLayerName", "" );
}

function doAddPoi( poiLayerName, poiId, poiMapX, poiMapY, poiLabel , poiIcon ) { 

    setValue( "poiLayerName", poiLayerName );
    setValue( "poiType", "0" );
    setValue( "poiId", poiId );
    setValue( "poiMapX", poiMapX );
    setValue( "poiMapY", poiMapY );
    setValue( "poiLabel", poiLabel );
    setValue( "poiIcon", poiIcon ); 
    
    doSubmitTarget( "abc" );
    
    setValue( "poiId", -1 );
}

function doAddLinePoi( poiLayerName, poiId, poiMapX, poiMapY, poiMapX1, poiMapY1, poiLineColor, poiLineWidth ) { 

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
}

function doClearSearch() {

    setValue( "clearSearch" , "true" ) ; 
    
    doSubmitTarget( "abc" ); 
    
    setValue( "clearSearch" , "" ) ;
    
}

function doRemoveAllPoiList() {

    setValue( "removeAllPoiList", "true" ) ;  
    
    doSubmitTarget( "abc" );
    
    setValue( "removeAllPoiList", "" ) ;  
    
} 

// 끝. 데이터 관련 함수들.

// 시작. 공통 함수들.

function setCmd( val ) {
    setValue( "cmd", val );
}

function setValue( id, val ) {
    var obj = getObject( id );
    
    if( obj != null ) {
        obj.value = val;    
    }             
} 

var continueWarn = true ; 

function getObject( id ) { 
    try
    {
        var obj = null ;
    
        if( document.getElementById ) { // this is the way the standards work
            obj = document.getElementById( id );
        } else if( document.all )  { // this is the way old msie versions work
            obj = document.all[ id ];
        } else if( document.layers ) { // this is the way nn4 works
            obj = document.layers[ id ];
        }
        
        if( obj == null && documenet.getElementsByName  ) {
           obj = document.getElementsByName( id ) ;
        }
    
        if( obj == null )
        {
            if( continueWarn ) {
                if( ! confirm( id + '에 해당하는 객체를 찾을 수 없습니다.\n\n관리자에게 문의하세요!' ) ) {
                    continueWarn = false;
                }
            }
            return null;
        }
    
        return obj;
    } catch ( exception ){
            return null;
    }

}

function getValue( id ) {
    var obj = getObject( id );
    
    if( obj != null ) { 
        return obj.value ;  
    } else {
        return "";
    }           
}

function getNumber( id ) {

    var val = getValue( id );
    
    try {
        val = parseFloat( val );
    } catch ( e ) {
        showMessage( e );
        
        val = 0 ;
    }
    
    return val;
}


function quote( text ) {
    return "\'" + text + "\'";
}

function showMessage( msg ) {
    if( false ) {
        window.status = msg;
    } else {
        alert( msg );
    }
}

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

    new Ajax.Request(
        
        url ,

        {
            asynchronous: false , 
            method: 'post', 
            encoding: 'UTF-8' ,
            parameters: 
             {
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
                  , "poiIcon" : getValue( "poiIcon" ) 
                  , "poiLineColor" : getValue( "poiLineColor" ) 
                  , "poiLineWidth" : getValue( "poiLineWidth" ) 
                  
                  , "clearSearch" : getValue( "clearSearch" ) 
                  
                  , "removeAllPoiList" : getValue( "removeAllPoiList" ) 
                  
                  , "debug" : getValue( "debug" ) 
                  
              } , 
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

function getPreLoadMapImage_Old() { 

    var mapImgUrl = getValue( "mapImgUrl" ) ;
    var width = getNumber( "width" ) ;
    var height = getNumber( "height" ) ;
    
    var img = new Image( width, height ) ;
    
    img.src = mapImgUrl ; 
    
    img.onload = function (evt) {
        var mapImage = getObject( "mapImage" );
        mapImage.src = mapImgUrl ;
        mapImage.width = width;
        mapImage.height = height;
        
        setLoadingLogoVisible( false );
    } ;
    
    img.onerror = function ( evt ) {
        
        setLoadingLogoVisible( false );
        
    } ;
    
    return img ;
    
}

function loadMapImage() {

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
    
    //setLoadingLogoVisible( false );
    
}

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

// 끝. 지도 로딩 함수들.

</script>

<script>

function bodyOnLoad() {

    //mapRefresh();
    
    //new Draggable('mapCellListPane');
    
}

</script>

<style>

body {
   background: black ;
}

.topPane {
   text-align:center; vertical-align: middle; background: black ;      
   background-image: url( "../image/worldDotMap.png" ) ;
}

.mapCellListPane {
    background: #8FBC8F ; 
    border: 0 solid black ;     
    clear: both;
}

.mapCellPane {
    background: #808000 ; 
    background-image: url( "../image/worldDotMap_gray.png" ) ;
    border: 1 solid red ;
    clear: both;
}

.mapImage {   
    cursor: hand; 
    border: 0px solid black;
}

</style>

<c:set var="margin" value="${ param.margin != null ? param.margin : 0 }" />
<c:set var="paneWidth" value="${ param.paneWidth != null ? param.paneWidth : width*4 }" />
<c:set var="paneHeight" value="${ param.paneHeight != null ? param.paneHeight : height*4 }" />

</head>

<body style="margin:0; padding:0; " onload="javascript: bodyOnLoad(); ">

<div class="topPane" style="margin: 0; padding: 0; width: 100%; height: 100%; ">
    <div id="mapCellListPane" class="mapCellListPane" align="center" style="padding: ${ margin} ; margin: 0 ;  width: ${ paneWidth + margin }; height : ${ paneHeight + margin };  " >
        <div id="mapCellPane" class="mapCellPane" align="center" style="margin: 0 ; padding: 0; width: ${ width }; height : ${ height }; " >
            <img id="mapImage" class="mapImage"  style=" margin: 0 ; padding: 0 px; "
                src="../../servlet/MapServlet"
                srca="../image/white.png" 
                border="0"
                width="${ width }" height="${ height }"
                align="absmiddle"
                onClick="javascript: mapRequest();" 
                onMouseWheel="javascript: mapRequest();"
                onload="javascript: setLoadingLogoVisible( false );"
                onerror="javascript: setLoadingLogoVisible( false );" 
            /></div>
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
    <input type="hidden" name="zoomLevelCurr"
        value="${ gisProject.zoomLevelMax }" />

    <input type="hidden" name="mapImgUrl" value="" />

    <input type="hidden" name="format" value="" />


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