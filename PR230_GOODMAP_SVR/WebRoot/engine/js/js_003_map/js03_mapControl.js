
// add by kbk
function getMapLoc2( x, y ) {
	var p = getMapLocation(x, y);
	var ret;
	
	ret = p.x + "," + p.y;
	
	return ret;
}

function getMapSize(){
	var msg = getNumber("width") + "," + getNumber("height");
	
	return msg;
}

function getMbr2() {

    var mbr = getNumber("minX") + "," + getNumber("minY") + "," + getNumber("maxX") + "," + getNumber("maxY");
    
    return mbr;
}

function getMapCenter2() {
	var p = getNumber("mapX") + "," + getNumber("mapY");
	return p;
}

function doGetLayerCoord2( srchLayerName  ) {
	var text = "" + doGetLayerCoord(srchLayerName);
	alert(text);
    return text;
    
}

function doAddPoi2(type, id, layerName, mapX, mapY, label, textColor, icon) { 
	
	var poi = {
	   "id" : id,
	   "layerName" : layerName ,
	   "label" : label ,
	   "icon" : icon ,
	   "mapX" : mapX ,
	   "mapY" : mapY ,
	   "textColor" : textColor ,
	   "a" : ""
	};

	doAddPoi(poi);
	/*
	setValue( "poiType", type );
    setValue( "poiLayerName", layerName );
    setValue( "poiId", id );
    
    setValue( "poiMapX", mapX );
    setValue( "poiMapY", mapY );
    
    setValue( "poiLabel", label );
    setValue( "poiTextColor", textColor ); 
    setValue( "poiIcon", icon ); 
    
    doSubmitTarget( "abc" );
    */
    return true;
}

function doAddLoi2 (type, id, layerName, mapX, mapY, mapX1, mapY1, lineColor, lineWidth) {
	
	var loi = {
	   "id" : id ,
	   "layerName" : layerName ,
	   "label" : "" ,
	   "icon" : "" ,
	   "mapX" : mapX ,
	   "mapY" : mapY ,
	   "mapX1" : mapX1 ,
	   "mapY1" : mapY1 ,
	   "textColor" : "" ,
	   "lineColor" : lineColor ,
	   "lineWidth" : lineWidth ,
	   "a" : ""
	};  
	
	doAddLoi(loi);
	/*
    setValue( "poiType", type );
    setValue( "poiLayerName", layerName );
    setValue( "poiId", id );
    
    setValue( "poiMapX", mapX );
    setValue( "poiMapY", mapY );
    setValue( "poiMapX1", mapX1 );
    setValue( "poiMapY1", mapY1 );
    
    setValue( "poiLineColor", lineColor );
    setValue( "poiLineWidth", lineWidth );
    
    doSubmitTarget( "abc" );
    */
    return true;
}

function doMapPrint2() {
	
	var myObject = new Object();
	var src = document.getElementById("mapImage").src;
	
	myObject.path = src; 
	
	window.showModalDialog("/PR230_GOODMAP_SVR/engine/jsp/test02/T001_Test.jsp", myObject, "dialogHeight : 700px; dialogWidth: 700px; dialogLeft : 200px;");
//	window.showModelessDialog("/PR230_GOODMAP_SVR/engine/jsp/test02/T001_Test.jsp", myObject, "dialogHeight : 700px; dialogWidth: 1000px; dialogLeft : 200px;");
}

function doGetImageSrc() {
	
	var src = document.getElementById("mapImage").src;
	return src;
}
// add by kbk

