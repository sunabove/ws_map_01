// pan info 

var mapControlVersion = "GooMap Control V1.0.140";;

var mapReqNo = 0 ;

var mapDispMode = 1 ; // 1 : map, 2 : image , 3 : overlay  

// TODO 2008.08.20.001 JSON RETURN VALUE REPOSITORY OPTIMIZATION NEEDED
var jsonResult ;  

function getPanInfoFromServer() {  
    
	var width = getNumber( "width" ) ;
	var height = getNumber( "height" ) ;  
	var zoomLevelCurr = getZoomLevelCurr(); 
	
	var url = "./DS200_PanInfo.jsp" ; 
	url += "?";
	url += "width=" + width;
	url += "&height=" + height;
	url += "&zoomLevelCurr=" + zoomLevelCurr;
	
	doSubmitUrlTarget( url, "abc" , setPanInfoFromServer ); 
}

function setPanInfoFromServer( transport ) {
    var json = transport.responseText.evalJSON() ; 
    panInfo = json; 
} 
    
function getPanInfo() { 
    if( panInfo == null ) { 
	    getPanInfoFromServer();
    }
    
    return panInfo;
}

// end of pan info

// map pint function

var mapPrintWindow__;

function doMapPrint() { 
	var margin = 20;
	var width = getNumber( "width") + margin ;
	var height = getNumber( "height" ) + margin ;
	var winOption__ = "width=" + width + ",height=" + height + ",status=no,toolbar=no,menubar=no,location=no";
	mapPrintWindow__ = window.open( document.getElementById("mapImage").src,"_blank", winOption__ );
	var jsCommandText = "mapPrintWindow__.print(); mapPrintWindow__.close();" ;
	setTimeout( jsCommandText,1000);  
}

// end of printing map

// tooltip function

var toolTipThreadFlag = -1;
var toolTipThreadMiliSec = -1;

function fireToolTipThread() {
	var runTimeOut = false ;
	var currTimeMiliSec = getCurrTimeMiliSec();
	if( toolTipThreadFlag < 0 ) {
		toolTipThreadFlag = 0 ;
		toolTipThreadMiliSec = currTimeMiliSec;
		runTimeOut = false ;
	} else if( toolTipThreadFlag == 0 ) {
		if( currTimeMiliSec - toolTipThreadMiliSec < 2000 ) { 
			runTimeOut = true ;
		} else {
			toolTipThreadFlag = 1 ; 
			toolTipThreadMiliSec = currTimeMiliSec; 
		}
	} else if( toolTipThreadFlag == 1 ) {
		toolTipThreadFlag = -1;
		toolTipThreadMiliSec = currTimeMiliSec;  
		if( ! isToolTipShowing ) { 
			getToolTipFromServer_Forced2(); 
		}
	}
	
	var sameMousePos = false;
	if( mouseMoveX == preMouseMoveX && mouseMoveY == preMouseMoveY ) {
		sameMousePos = true;
	}
	
	if( ! sameMousePos && ! isToolTipShowing && runTimeOut ) {
		var expression = "fireToolTipThread()";
		var timeout = 1000;
		setTimeout ( expression, timeout ); 
	}
}

var toolTipNo = 0;

function getToolTipFromServer_Forced2() {  
	
	preMouseMoveX = mouseMoveX;
	preMouseMoveY = mouseMoveY; 
	
	if( false ) { 
		window.status = " getting tool tip ...." + toolTipNo ; 
	}
	toolTipNo ++; 
	
	//isToolTipShowing = true;
	
	//setToolTipPaneVisible( 1 );  
	
	if( false ) {
		return;
	}
	
	var mx = mouseMoveX;
	var my = mouseMoveY; 
	
	if( mx >= 0 && my >= 0 ) {
		var w = getMapWidth();
		var h = getMapHeight();
		var mapNo = getMapNo();
		var zoomLevelCurr = getZoomLevelCurr(); 
		var mapX = getNumber( "mapX" );
		var mapY = getNumber( "mapY" );
		
		var url = "./M032_ToolTipView.jsp" ; 
		url += "?";
		url += "mouseX=" + mx;
		url += "&mouseY=" + my;   
		url += "&mapX=" + mapX;
		url += "&mapY=" + mapY;
		url += "&width=" + w;
		url += "&height=" + h;
		url += "&mapNo=" + mapNo;
		url += "&zoomLevelCurr=" + zoomLevelCurr;
		url += "&toolTipNo=" + toolTipNo;
		
		//alert( url ); 
		
		var frame = toolTipFrame ;
		if( frame != null ) { 
			frame.location = url;
		}
	}
}

function getToolTipFromServer_Forced() {  
	
	if( true ) { 
		window.status = toolTipNo + " getting tool tip ....";
		toolTipNo ++; 
	}
	
	var mx = mouseMoveX;
	var my = mouseMoveY; 
	
	if( mx >= 0 && my >= 0 ) {
		var url = "./DS300_ToolTip.jsp" ; 
		url += "?";
		url += "mouseX=" + mx;
		url += "&mouseY=" + my;  
		doSubmitUrlTarget( url, "abc" , getToolTipFromServer_Listener ); 
	} else {
		//alert( "wrong mouse position");
	}
}

function getToolTipFromServer_Listener( transport ) {
    var json = transport.responseText.evalJSON() ;
    var toolTipSize = json.toolTipList.size();
    if( toolTipSize > 0 ) {
    	var index = 0 ;
    	var toolTip = json.toolTipList[ index ];
    	if( true ) {
	    	var x = json.mouseX;
	    	var y = json.mouseY;
	    	showToolTipOnMap( toolTip , x, y );
	    	//alert( "x: " + json.mouseX +  ", y: " + json.mouseY );
	    	//alert( "type: " + toolTip.type + ", content: " + toolTip.content ); 
    	}
    }
    var localDebug = false;
    if( localDebug ) { 
	    alert( "x: " + json.mouseX +  ", y: " + json.mouseY );
	    alert( "size: " + json.toolTipList.size() );
    }
}

function showToolTipFrameOnMap( x, y ) { 
	//alert( "x:" + x + ",y:" + y );
	var toolTipPaneId = "toolTipPane";
	setObjectLocation( toolTipPaneId, x - 5, y - 5 );
	setToolTipPaneVisible( 1 ); 
}

function showToolTipOnMap( toolTip , x, y ) {
	var toolTipContent = getObject( "toolTipContent" );
	if( toolTipContent != null ) { 
		if( true ) {  
			var text = "<nobr>" + toolTip.content + "</nobr>" ; 
			//text = unescape( text );
			alert( text );
			$( "toolTipContent" ).update( text ); 
		} else if( true ) {
			var text = "<nobr>" + toolTip.content + "</nobr>" ;
			$( "toolTipContent" ).update( text );
		} else {
			toolTipContent.innerHTML = "<nobr>" + toolTip.content + "</nobr>";
		}
		var toolTipPaneId = "toolTipPane";
		setObjectLocation( toolTipPaneId, x - 5, y - 5 );
		setToolTipPaneVisible( 1 );
	}
}

// enf of tooltip functon

// start of javascript file

function getMapControlVersion() {
    return mapControlVersion;
}

function getMapControlVer() {
    return getMapControlVersion();
}

function getGisEngine_MapUrlHead() {
    return "http://" + mapSvrHostName + ":" + mapSvrPortNo + "/PR230_GOODMAP_SVR/engine/jsp/" ;    
}

function goodMapOnLoad() { 
    if( false ) {
        checkMapDimension();
    }
    
    if( initMapWidth > 0 && initMapHeight > 0 ) {
        setValue( "width", initMapWidth );
        setValue( "height", initMapHeight );
    }
    
    setValue( "mapNo", initMapNo ); 
    
    if( initLogoImage != '0' ) { 
    	var topPane = getTopPaneMapControl();
    	if( topPane != null ) {
    		var style = topPane.style;
    		if( style != null ) {
    			var newImageUrl = "url(\'" + initLogoImage + "\')" ; 
    			style.backgroundImage = newImageUrl ;
    		}
    	}
    }

    setObjectVisibility( "mapScalePane" , initScaleBar );
    
    setObjectVisibility( "mapCompassPane" , initCompass ); 
    
    setToolTipPaneVisible( 0 );
    
    mapRefresh();
    
    initMouseEvent();
    
    if( true ) { 
    	makeDivDraggable( mapPaneId );
    	makeDivDraggable( "mapCompassPane" );
    	makeDivDraggable( "mapScalePane" );     
    }    
}

function makeDivDraggable( objId ) {
	new Draggable( objId , { starteffect: '', endeffect: '', revert: false , zindex : 10 } ); 
}

function setObjectVisibility( objId, onOff ) {
    var pane = getObject( objId ); 
    
    var styleObj = pane.style; 
    if( styleObj != null ) {    
    	styleObj.visibility = onOff > 0 ? "visible" : "hidden" ;
    }
}

function setObjectLocation( objId, x , y ) {
    var pane = getObject( objId ); 
    
    var styleObj = pane.style; 
    if( styleObj != null ) {    
    	styleObj.left = x;
    	styleObj.top = y; 
    }
} 

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

var isToolTipShowing = false;

function setToolTipPaneVisible( onOff ) {  
	isToolTipShowing = onOff > 0 ? true : false;
	setObjectVisibility( "toolTipPane", onOff ); 
}

function setLoadingLogoVisible( onOff ) {
    var loadingLogoPane = getObject( "loadingLogoPane" ); 
    
    try {  
	    var styleObj = loadingLogoPane.style;
	    
	    if( styleObj != null ) { 
		    if( onOff ) {
		        var parent = getObject( "mapImageTag" ) ; 
		        
		        var width = getNumber( "width" ) ;
		        var height = getNumber( "height" ) ;
		        
		        styleObj.left = (width - 33) / 2  ;
		        styleObj.top = (height - 33) / 2  ;
		    } 
		    styleObj.visibility = onOff ? "visible" : "hidden" ; 
	    } 
    } catch( e ) {
    	
    }
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

// map image functions 

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
}

function getLevelDistCurrent() { 
    var zoomLevel = getZoomLevelCurr() ;
    var panInfo = getPanInfo();
    var distList = panInfo.distList[ zoomLevel ];     
    return distList;
}

function getLevelScaleRelative( zoomLevel ) {
    var panInfo = getPanInfo();
    var levelScale = panInfo.scaleList[ zoomLevel ];    
    var levelScaleMax = panInfo.scalePhysScr ;     
    var scale = levelScaleMax / levelScale ;    
    return scale;
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

function getMapLevel() {
    return getZoomLevelCurr();
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

function zoomIn() { // zoom in    
    var zoomLevelCurr = getZoomLevelCurr() ;
    
    if( zoomLevelCurr > 0 ) {  
    
        setMapLevel( zoomLevelCurr - 1 );
        
        // setMapLevelListPane_Loc( 0, 0 );
        
        return true;
    } else {
       var msg = "cannot zoom in more.";
       //showMessage( msg );
       return false;
    }
}

function zoomOut() { // zoom out 
    var zoomLevelCurr = getZoomLevelCurr() ;
    
    if( zoomLevelCurr < getZoomLevelMax() ) { 
    
        setMapLevel( zoomLevelCurr + 1 );
        
        // setMapLevelListPane_Loc( 0, 0 );
        
        return true;
    } else {
       var msg = "cannot zoom out more." ;
       //showMessage( msg );
       return false;
    }
} 

function setMapFullExtent() {
	fullExtent();
}

function fullExtent() {     
    setMapLevel( getZoomLevelMax() );    
    setMapCenter( initMapX , initMapY ) ;    
    return true;    
} 

function doSetMapSize( w, h ) {
    // resize map image size    
    setValue( "width", w );
    setValue( "height", h );
    
    var topPane = getTopPaneMapControl();
    if( topPane != null ) {
		var style = topPane.style;
		if( style != null ) {
			style.width = w;
			style.height = h;
		}
	}

    getPanInfoFromServer(); 
    mapRefresh();    
}

function getTopPaneMapControl() {
	return getObject( "topPaneMapControl" ); 
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

function toggleDebugOption() {
    var e = window.event;
            
    if( e != null && e.ctrlKey ) {  
        setValue( "debug" , -1 * getNumber( "debug" ) ) ; 
        window.status =  "debug=" + getValue( "debug" ) ; 
    }    
}

function getMapUrl() {  
    var urlHead = getGisEngine_MapUrlHead() ; 
    var url = urlHead + "../.." ;  
    url += "/servlet/MapServlet?" ;
    
    url += "mapNo=" + getValue( "mapNo" ) ;
    url += "&mapX=" + getValue( "mapX" ) ;
    url += "&mapY=" + getValue( "mapY" ) ; 
    
    url += "&width=" + getValue( "width" ) + "&height=" + getValue( "height" ) ; 
              
    url += "&zoomLevelCurr=" + getValue( "zoomLevelCurr" ) ;
    url += "&format=" + getValue( "format" ) ;
    url += "&debug=" + getValue( "debug" ) ;
     
    url += "&mapReqNo=" + ( mapReqNo ++ ) ; 
    
    if( false ) {      
        url += "&minX=" + getValue( "minX" ) ;
        url += "&minY=" + getValue( "minY" ) ;
        url += "&maxX=" + getValue( "maxX" ) ;
        url += "&maxY=" + getValue( "maxY" ) ;
    } 
    
    return url;
}

function getMapCurrentUrl() {
    var mapImage = getObject( "mapImage" );
    if( mapImage != null  ) { 
        return mapImage.src;
    } else {
        return null;
    }
}

function mapRefresh() { 
    loadMapImage( true );   
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

// end of map image functions

// map info functions

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
	return getMapMbr();
}
	
function getMapMbr() {

    var mbr = new Mbr() ;
    
    mbr.minX = getNumber( "minX" );    
    mbr.minY = getNumber( "minY" );
    mbr.maxX = getNumber( "maxX" );
    mbr.maxY = getNumber( "maxY" );
    
    return mbr;
}

function getMapNo() {
	return getNumber( "mapNo" );
}

function getMapWidth() {
	var width = getNumber( "width" ); 
    return width;
}

function getMapHeight() { 
    var height = getNumber( "height" ); 
    return height;
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
    
    if( false ) {
        //TODO getMbrMoveBy 
	    dx = dx*(width/256.0);
	    dy = dy*(height/256.0);
    }
        
    var zoomLevelCurr = getZoomLevelCurr();
    
    var scaleRelative = getLevelScaleRelative( zoomLevelCurr );
    
    var panInfo = getPanInfo();
    
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

// end of map info function

// map layer functions 

function doSearch( srchLayerName, srchColName, srchColValue ,
         srchLayerLineColor, srchLayerFillColor , srchLayerTextColor , srchLayerLineWidth ) { 
   // layer attribute search
    
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

// poi functions 

function doAddPoi( poi ) { 
    var val = doAdd_UserObject( "0", poi ); 
    return val ;
}

function doAddLoi( poi ) { 
    var val = doAdd_UserObject( "1", poi ); 
    return val ; 
}

function doAdd_UserObject( type, poi , listener ) { 
	
	var mapNo = getMapNo();
	poi.type = type;
	poi.mapNo = mapNo;
    
    var url = "./M010_MapModel.jsp" ;  
    var onSuccessCall = listener  ;
    
    callAjaxUrlParams( url , poi, onSuccessCall ); 
    
    return true;
}

function doAdd_UserObjectOld( type, poi ) { 
	
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
// end of poi functions  

function doMapClear() { 
   // remove searched layers and poi layers
    return doClearAll();
}

function doClear() { 
    // remove searched layers and poi layers
    return doClearAll();
}

function doClearAll() { 
    // remove searched layers and poi layers
    doClearSearch();
    doRemoveAllPoiList();
    
    return true;
}

function doClearSearch() { 
    // remove searched layers
    setValue( "clearSearch" , "true" ) ; 
    
    doSubmitTarget( "abc" ); 
    
    setValue( "clearSearch" , "" ) ;
    
    return true;    
}

function doRemoveAllPoiList() { 
    // remove poi layers
    setValue( "removeAllPoiList", "true" ) ;  
    
    doSubmitTarget( "abc" );
    
    setValue( "removeAllPoiList", "" ) ;  
    
    return true;    
} 

function doRemoveSessionLayer( removeLayerName, removeShapeId ) {
    // remove layer or shape
    setValue( "removeLayerName" , removeLayerName ) ; 
    setValue( "removeShapeId" , removeShapeId ) ;  
    
    doSubmitTarget( "abc" ); 
    
    setValue( "removeLayerName" , "" ) ; 
    setValue( "removeShapeId" , "" ) ;  
    
    return true;    
}

// end of map layer functions

function setTarget( target ) {
    var mainForm = getObject( "mainForm" ); 
    if( mainForm != null ) { 
    	mainForm.target = target;
    }
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
    var urlRedirect = getGisEngine_MapUrlHead() + url ;

    if( false ) { 
	    alert( "urlRedirect=" + urlRedirect );
    } 

    var parameters = {
           "a"            : ""
           
        , "mapNo" : getValue( "mapNo" )
           
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
        
        , "removeLayerName" : getValue( "removeLayerName" ) 
        , "removeShapeId" : getValue( "removeShapeId" ) 
        
        , "debug" : getValue( "debug" ) 
        , "b" : ""
        
    };

    callAjaxUrlParams( urlRedirect , parameters, onSuccessCall ); 
}

function callAjaxUrlParams( url , parameters, onSuccessCall ) { 
    new Ajax.Request(
        url ,
        {
            asynchronous: false ,
            method: 'POST',  
            encoding: 'UTF-8' ,
            "parameters" : parameters ,  
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

// end of common functions

// map history functions

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
        
    if( isSameHist ) { // if same date
        // do nothing !!! 
    } else if( hc == null  ) { // if history size is zero
        mapHistList.push( hn );
        mapHistId ++ ;
    } else {
        for(  ; mapHistId < mapHistList.length -1 ; ) {
            mapHistList.pop();
        }
        mapHistList.push( hn );    
        mapHistId ++ ;
    } 
}

function goBackward() {
    return goMapPrev();
}

function goForeward() {
    return goMapNext();
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

// end of map history functions

// map loading functions

function loadMapPage( htmlText ) {
    if( htmlText.indexOf( "<sucess/>" ) > -1 ) {
        $("mapDataTag").update( htmlText ) ; 
        loadMapImage();
    } else {
        var msg = "Map Loading failed. Try again, please!";
        showMessage( msg );
    }
}

function getPreLoadMapImage( mapUrl ) {  
	
    //var mapImgUrl = getValue( "mapImgUrl" ) ;
    var width = getNumber( "width" ) ;
    var height = getNumber( "height" ) ; 
    
    var mapImage = getObject( "mapImage" );
    
    mapImage.width = width;
    mapImage.height = height; 
    mapImage.src = mapUrl ; 
    
    return mapImage; 
}

function loadMapImage( history ) { 
    if( history ) {
        manageMapHistory();
    }

    var mapUrl = getMapUrl(); 

    var loadingLogo = getNumber( "loadingLogo" );
    
    if( loadingLogo > 0 ) {
        setLoadingLogoVisible( true );    
    }
    
    try {    
        var image = getPreLoadMapImage( mapUrl );
    } catch ( e ) {        
        setLoadingLogoVisible( false );
    }
}

// end of map loading functions 

// mouse drag functions

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

var mapPaneId = "mapPane" ; 
        
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
	    
	    if( e!= null ) {
	        sx = e.screenX ;
	        sy = e.screenY ;
	        //window.status = ( "down x = " +  sx + ", y = " + sy ); 
	    } 
    }  
    
    if( self.parent.goodMapMouseDown != null ) {
        self.parent.goodMapMouseDown( e );
    }
}

var mouseMoveX = -1 ;
var mouseMoveY = -1 ;

var preMouseMoveX = -1 ;
var preMouseMoveY = -1 ;

var mouseMoveMiliSec = -1; 

function getCurrTimeMiliSec() {
	var d = new Date();  
	var curr_hr = d.getHours();
	var curr_min = d.getMinutes(); 
	var curr_sec = d.getSeconds();  
	var curr_mili = d.getMilliseconds();
	var miliSec = (curr_hr*3600 + curr_min*60 + curr_sec)*1000 + curr_mili;
	//alert( miliSec );
	return miliSec;
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
        mouseMoveX = e.x;
    	mouseMoveY = e.y;
    	mouseMoveMiliSec = getCurrTimeMiliSec();
    	 
    	if( useToolTip > 0 ) { 
	    	if( true ) {
	    		setToolTipPaneVisible( 0 ); 
	    	}
	    	
	    	if( ! isToolTipShowing ) {  
	    		if( true ) {
	    			fireToolTipThread();
	    		}
	    	}
    	}
    }
    
    if( self.parent.goodMapMouseMove != null ) {
        self.parent.goodMapMouseMove( e );
    }
}

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

var mode = 0 ; // 0 : normal, 1 : distance

var clickPrev = null , clickCurr = null ;
var clickTimePrev = -1 ; clickTimeCurr = -1 ;
var clickNo = 0;
var lineClickNo = 0;
var lineObjNo = -1 ;
var distTotal = 0;
var distPrev = 0;

function getMapMode() {
	return mode;
}

function setMapMode( m ) {
    mode = m ; 
    
    if( true ) {
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

function doClearMeasureDistance() { 
	doRemoveSessionLayer( "MEASURE_DIST_POI", -1 );
	doRemoveSessionLayer( "MEASURE_DIST_LOI", -1 ); 
}

function doAdd_MeasureDist( e0, e1 ) {
    var id = lineClickNo ;
    
    var layerName = "MEASURE_DIST_POI" ;  
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
       
       label = labelHead + "Total: " + formatDist( distTotal );
   
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
        var layerName = "MEASURE_DIST_LOI" ;   
   
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
     
     if( e == null ) { // if event is null
        window.status = ( "wrong event" );
     } else if( true ) {
         var isLeftClick = Event.isLeftClick( e ) ; 
         var isRightClick = ( ! isLeftClick ) ;

         var x = e.x;
         var y = e.y;
              
	     ex = e.screenX ; 
	     ey = e.screenY;
	     
	     var dx = ex - sx , dy = ey - sy ;
	     
	     var distum = Math.abs( dx*dy ) ;
	     var mouseSense = ( distum >= 6 ) ;

	     if( isDblClick ) {
	    	 if( useDoubleClick > 0 ) { 
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
	    	 }
	     } else if( isRightClick ) { // right mouse button click
		     // do nothing ...
	     } else if( mouseSense ) { // pan mode
	         down = false; 
             drag = false;   
     	     setMapAreaMovedBy( -dx, -dy );
		     loadMapImage( true );
		     //window.status = ( "drag dx = " + dx + ", dy = " + dy );
	     } else if( mode == 1 ) { // distance mode 
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
	     }  
     } 
     ; 
     
     if( true ) {
        setMapCursor( mapCursorPrev );
     }    
     
     if( self.parent.goodMapMouseUp != null ) {
        self.parent.goodMapMouseUp( e );
     }
}

function mapMouseWheel() {
	if( useMouseWheel > 0 ) { 
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
	    
	    if( self.parent.goodMapMouseWheel != null ) {
	        self.parent.goodMapMouseWheel( e );
	    }
	}
}

function initMouseEvent() {
	
	var mapImageId = "mapImage";
	
    var mapImage = getObject( mapImageId );
    
    mapImage.onmousedown = mapMouseDown ;
    mapImage.onmousemove = mapMouseMove ;
    mapImage.onmouseup = mapMouseUp ;
    mapImage.onmousewheel = mapMouseWheel; 
}

function restoreMapImagePaneLocation() {
    var dragObj = getObject( mapPaneId );
    if( dragObj != null && dragObj.style != null && dragObj.style.top != 0 ) {
        dragObj.style.top = 0;
        dragObj.style.left = 0 ;
    }  
}

function showMapScale() {
	var mapScaleVal = getObject( "mapScaleVal" );
    
	if( mapScaleVal != null ) { 
		var mapScaleBarImg = getObject( "mapScaleBarImg" ); 
	    var levelDist = getLevelDistCurrent();  
	    var dist_Meter = levelDist*3/100; 
	    var dist_Meter_Format = formatNumber( dist_Meter, 2, false,true ) ;  
	    var text = "&nbsp;" + dist_Meter_Format + " m" + "&nbsp;" ; 
    	mapScaleVal.innerHTML = text ;
    }
}

function setZoomLevelListener( listener ) {
    alert( "here" );
    document.zoomLevelListener = listener ;
}

function mapImage_OnLoad() { // a function that call after map loading.     
     setLoadingLogoVisible( false );
     setZoomLogo( false );
     restoreMapImagePaneLocation();

     showMapScale();
     
     if( true ) {
    	 setToolTipPaneVisible( 0 );
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

// end of mouse drag event
  
// end of javascript file