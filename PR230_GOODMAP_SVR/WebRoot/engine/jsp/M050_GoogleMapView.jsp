<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="width" value="${param.width != null ? param.width : 700 }" />
<c:set var="height" value="${param.height != null ? param.height : 700 }" />
<c:set var="level" value="${param.level != null ? param.level : 16 }" />
  

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title> Good Map B </title>
    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAAXu1C6tqZj3NyhysZcXrwnBSePoRgZ5P0Of9Yp9hdoOoSf7ZF_hRs_0O55GCFj3uZ3hHG45iOpy8Seg"
      type="text/javascript"></script>
    <script type="text/javascript">
    //<![CDATA[
    function load() {
      if (GBrowserIsCompatible()) {
        var gMap = new GMap2(document.getElementById("map"));
        
        gMap.setMapType(G_SATELLITE_MAP);
        //gMap.setMapType(G_HYBRID_MAP); 
        //gMap.addControl(new GMapTypeControl());
        //gMap.addControl(new GSmallMapControl());
        gMap.addControl(new GLargeMapControl());
        
        gMap.setCenter(new GLatLng( 33.5, 126.5  ), 15 );  
        
        if( false ) {
            var layerSrc = "http://220.90.136.132/PR_05_YGIS_MAPSVR/engine/jsp/test5.kmz" ;
            var geoXml = new GGeoXml( layerSrc );
            //gMap.setCenter(new GLatLng( 37.499, 127.038765 ), 9 );
            gMap.addOverlay(geoXml);
        }  
        
      }
    }
    //]]>
    </script>
  </head>
  <body onload="load()" onunload="GUnload()">
    <div id="map" style="width: ${ width }px; height: ${ height }px"></div>
  </body>
</html> 