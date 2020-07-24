<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 

<jsp:include page="./C001_ComHeader.jsp"></jsp:include>

<script type="text/javascript">

var widthPrev = -1 , heightPrev = -1 ;

function loadMapPage() {
    // load map page when iframe onload or resize

    var localDebug = false;

    if( ! bodyLoad ) { return ; }

    var zoomLevelCurr = ${ gisProject != null ? gisProject.topLevel : 8 };
    
    var mapX = 156400;
    var mapY = -13328.419; 
    
    if( false ) { // jeju 
        mapX = 156400 ;
        mapY = -13328.419 ;
    }else if( false ) { // seoul
	    mapX = 198459.0 ;
	    mapY = 448722.0 ;
    }else if( true ) { // suncheon
        mapX = 242145 ;
        mapY = 163015 ;
    }  
    
    var mapDispMode = 1;
    
    var format = "" + '${ param.format != null ? param.format : 'png' }' ;
 

    if( true ) {
        var mapFrame = getMap() ;
        if( mapFrame != null && mapFrame.getMapCenter != null ) {
        
            var mapCen = mapFrame.getMapCenter();
            mapX = mapCen.x;
            mapY = mapCen.y;
            zoomLevelCurr = mapFrame.getZoomLevelCurr();
            mapDispMode = mapFrame.getMapDispMode();
            format = mapFrame.getMapImageFormat();
            
            if( localDebug ) {
                alert( "zoomLevelCurr = " + zoomLevelCurr + ", mapCen = " + mapCen.x );
            } 
        } 
    } 
    
    //var map = getMap() ;
    var map = document.getElementById( "map" );  
    
    var margin = 0 ; 

    var paneWidth = map.clientWidth - margin ;
    var paneHeight = map.clientHeight - margin  ;
    
    if( paneWidth*paneHeight < 2 ) {
        paneWidth = 900;
        paneHeight = 700;
    }
    
    var width = paneWidth - margin;
    var height = paneHeight - margin;
    
    if( width != widthPrev || height != heightPrev ) {
    
        var msg  = "w=" + width + ", h=" + height ;
        
        //alert( msg  );
        
        var url = "./M020_MapControl.jsp?a=1" +"&paneWidth=" + paneWidth + "&paneHeight=" + paneHeight ; 
        url += "&debug=${param.debug -1}" ;
        url += "&loadingLogo=${param.loadingLogo}" ;
        url += "&margin=${param.margin}" ;
        url += "&width=" + width;
        url += "&height=" + height;
        url += "&mapX=" + mapX;
        url += "&mapY=" + mapY; 
        url += "&zoomLevelCurr=" + zoomLevelCurr ;  
        url += "&format=" + format ;
        url += "&mapDispMode=" + mapDispMode ;
    
        map.src = url ;  
        
        widthPrev = width;
        heightPrev = height; 
    } 
}

function makeToolTipList() { 
}

function buttonHandler( button, evt ) {
    var text = button.text;
    
    var map = getMap();
    
    var mapDispMode = 1 ;
    
    if( '지도' == text ) {
        mapDispMode = 1;
    } else {
        mapDispMode = 3;
    } 
    
    if( map != null ) {
        map.setMapDispMode( mapDispMode );
        map.loadMapImage( true );
    }
    
}

function makeButtonList() {  
    //var buttonObject = new Ext.Button({applyTo:'btn_MapOnly',text:'지도', handler:buttonHandler });
    //var buttonObject = new Ext.Button({applyTo:'btn_ImageOnly',text:'영상', handler:buttonHandler });
    //var buttonObject = new Ext.Button({applyTo:'btn_MapAndImage',text:'중첩', handler:buttonHandler });
}

function getMap() {
    return map; 
    //return getObject( "map" );
}

function high_01( obj ) {
    //new Effect.Highlight( obj );
}

function high_02( obj ) {

    if( true ) {
        return;
    }

    var style = obj.style; 

    var option = {
        "startcolor" : '#ffff99' ,
        "enccolor" : style.backgroundColor ,
        "restorecolor" : style.backgroundColor ,
        "keepBackgroundImage" :true ,
        "a" : ""
    };
    
    new Effect.Highlight( obj, option );
}

function high_03( obj, bgColor , borderColor ) {

    if( obj != null ) {
       if( bgColor == null || bgColor == "" ) { bgColor = "orange" ; }
       style = obj.style;
       if( style != null ) {
           //style.border = "1 red solid";
           if( borderColor != null ) {
               style.borderColor = borderColor ;
           }
           style.backgroundColor = bgColor ;
           style.color = "black";
           /*background-color: gray;*/
           /*background-color: #788D87;*/ 
       }
    }
}

function mapZoomLevelListener( zoomLevel, bgColor ) {

    if( bodyOnLoad ) { 
        var map = getMap();
    
        var zoomLevelMax = map.getZoomLevelMax();
        var zoomLevelCurr = map.getZoomLevelCurr();
        
        for( var i = 0, iLen = zoomLevelMax + 1 ; i < iLen ; i ++ ) {
            if( i == zoomLevelCurr ) {
            } else {
                var id = "mapLevel_" + i  ; 
                var obj = getObject( id );
                if( obj != null ) {
                    var style = obj.style;
                    if( style != null ) {
                        style.border = "1px solid #99bbe8";
                        style.backgroundColor = "#3978BF";
                        style.color = "white";  
                    }
                }
            }   
        }
        
        var id = "mapLevel_" + zoomLevel ;    
        var obj = getObject( id );
        high_03( obj, bgColor );
    }
    
}

</script>

<table id="mapSize" class="mapControl" style="padding:0; margin:0; " cellspacing="0" width="100%" height="100%">
	   <tr>
	       <td colspan="3">
	              <iframe id="map" class="map" src="about:blank" 
	                   width="100%" height="100%" scrolling="no" frameborder="0" 
	                   style="border: 4 solid #919191; padding: 0; margin: 0; "
	                   onresize="javascript: loadMapPage();"  
	               ></iframe>                      
	       </td>
	   </tr>
	   <tr style="height:50;vertical-align:middle; align:left;">
	       <td style="background-color: #C8D0D4;" >
	            <div style="padding:0; margin:0; border: 0 solid red;" > 
	                <ol class="mapController" >
	                    <li class="mapController">
	                        <image src="../image/jeju/separator.gif" align="absmiddle" />
	                    </li>
	                    <li class="mapController">
	                        <image id="btn_All" src="../image/jeju/all.gif" class="mapController" align="absmiddle" 
	                            onClick="javascript: if( map.fullExtent() ) { map.mapRefresh() ; };"  
	                     />
	                     <script> makeToolTip( 'btn_All', '지도 전체 영역을 조회합니다.' , '전체보기' ); </script>
	                 </li>
	                 <li class="mapController">
	                     <image src="../image/jeju/separator.gif" align="absmiddle" />
	                 </li>
	                 <li class="mapController">
	                     <image id="btn_ZoomIn" src="../image/jeju/zoomIn.gif" class="mapController" align="absmiddle" 
	                         onClick="javascript: if( map.zoomIn() ) { map.mapRefresh() ; };" 
	                     />
	                     <script> makeToolTip( 'btn_ZoomIn', '지도를 확대합니다. <br/>지도 위에서 마우스 휠을 위로 돌리면 지도가 확대됩니다.' , '확대' ); </script>
	                 </li> 
	                 <li class="mapController">
	                     <image id="btn_ZoomOut" src="../image/jeju/zoomOut.gif" class="mapController"  align="absmiddle" 
	                         onClick="javascript: if( map.zoomOut() ) { map.mapRefresh() ; };" 
	                     />
	                     <script> makeToolTip( 'btn_ZoomOut', '지도를 축소합니다. <br/>지도 위에서 마우스 휠을 아래로 돌리면 지도가 축소됩니다.' , '촉소' ); </script>
	                 </li> 
	                 <li class="mapController">
	                     <image id="btn_Pan" src="../image/jeju/pan.gif" class="mapController" align="absmiddle"
	                         onMouseOver="high_01( this );" 
	                         onClick=" map.setMapMode( 0 );"
	                     />
	                     <script> makeToolTip( 'btn_Pan', '지도를 드래그하면 지도가 이동됩니다.' , '이동' ); </script>                                 
	                 </li> 
	                 <li class="mapController">
	                     <image src="../image/jeju/separator.gif" align="absmiddle" />
	                 </li>
	                 <li class="mapController" >
	                     <image id="btn_Prev" src="../image/jeju/previous.gif" align="absmiddle"
	                        onClick="javascript: if( ! map.goMapPrev() ) {   } ;" 
	                     />
	                     <script> makeToolTip( 'btn_Prev', '이전 지점으로 지도를 이동합니다.' , '이전' ); </script>
	                 </li>
	                 <li class="mapController">
	                     <image id="btn_Next" src="../image/jeju/next.gif" align="absmiddle"
	                         onClick="javascript: if( ! map.goMapNext() ) { } ;" 
	                     />
	                     <script> makeToolTip( 'btn_Next', '다음 지점으로 지도를 이동합니다.' , '다음' ); </script>
	                 </li> 
	                 <li class="mapController">
	                     <image src="../image/jeju/separator.gif" align="absmiddle" />
	                 </li>
	                 <li class="mapController">
	                     <image id="btn_Dist" src="../image/jeju/distance.gif" align="absmiddle"
	                         onClick=" map.setMapMode( 1 );"
	                     />
	                     <script> makeToolTip( 'btn_Dist', '지도상에거 거리를 측정합니다.' , '거리 재기' ); </script>
	                 </li> 
	                 <li class="mapController">
	                     <image id="btn_Cls" src="../image/jeju/clear.gif" align="absmiddle" 
	                         onClick="javascript: if( map.doRemoveAllPoiList() ) { map.mapRefresh() ; };" 
	                     />
	                     <script> makeToolTip( 'btn_Cls', '사용자가 추가한 노선 및 POI들을 모두 삭제합니다.' , '지우기' ); </script>
	                 </li> 
	                 <li class="mapController">
	                     <image id="btn_Print" src="../image/jeju/print.gif" align="absmiddle" 
	                         onClick="javascript: map.focus(); map.printMap(); " 
	                     />
	                     <script> makeToolTip( 'btn_Print', '지도를 프린터로 출력합니다. 프린터 옵션을 설정을 통하여 사용자가 원하는 지도를 출력할 수 있습니다.' , '프린트' ); </script>
	                 </li> 
	                 <li class="mapController">
	                     <image src="../image/jeju/separator.gif" align="absmiddle" />
	                 </li>
	                 <li class="mapController">
	                     <image id="btn_Help" src="../image/jeju/help.gif" align="absmiddle" 
	                         onClick="javascript: ;"
	                     />
	                     <script> makeToolTip( 'btn_Help', '도움말은 현재 지원되지 않습니다.' , '도움말' ); </script>
	                 </li>
	                 <li class="mapController">
	                     <image src="../image/jeju/separator.gif" align="absmiddle" />
	                 </li> 
	             </ol>
	         </div>
	     </td>
	     <td style="background-color: #C8D0D4; ">
	           <div style="padding:0; margin:0; border: 0 solid red; align:center; " > 
		           <ol class="mapController"> 
                       <li class="mapController" > 
                            <div id="btn_MapOnly" >
                            </div>                     
                       </li>
                       <li class="mapController" > 
                            <div id="btn_ImageOnly" >
                            </div> 
                       </li>
                       <li class="mapController" > 
                            <div id="btn_MapAndImage" >
                            </div> 
                       </li> 
                   </ol>
	           </div>
	     </td>
	     <td style="background-color: #C8D0D4; align:right;">
	         <div style="align:left; padding:0; margin:0; border: 0; align:right; " >	             
	             <ol class="mapController" >
	                 <li class="mapController">
                         <image src="../image/jeju/separator.gif" align="absmiddle" />
                     </li> 
	                 <li class="mapController">
	                      <image src="../image/jeju/zoomIn.gif" class="mapController" align="absmiddle" alt="확대" 
	                            onClick="javascript: if( map.zoomIn() ) { map.mapRefresh() ; };"
	                      />
	                 </li> 
	             </ol>
	             <ol class="mapLevel">
	                 <c:forEach var="mapLevel" begin="0" end="${ gisProject.topLevel }">
	                     <li id="mapLevel_${mapLevel}" class="mapLevel" 
	                         onMouseOver="javascript: mapZoomLevelListener( ${mapLevel}, '#77C347' );" 
	                         onMouseOut="javascript: mapZoomLevelListener( map.getZoomLevelCurr()  );" 
	                         onClick="javascript: map.setMapLevel( ${mapLevel} );map.mapRefresh();" 
	                     >
	                         ${mapLevel + 1} 
	                     </li>
	                  </c:forEach>
	             </ol>
	             <ol class="mapController" >
	                 <li class="mapController">
	                     <image src="../image/jeju/zoomOut.gif" class="mapController" align="absmiddle" alt="축소"
	                         onClick="javascript: if( map.zoomOut() ) { map.mapRefresh() ; };" 
	                     /> 
	                 </li>
	                 <li class="mapController">
                         <image src="../image/jeju/separator.gif" align="absmiddle" />
                     </li>                   
	             </ol>
	         </div>
	    </td>
	</tr>
</table>  