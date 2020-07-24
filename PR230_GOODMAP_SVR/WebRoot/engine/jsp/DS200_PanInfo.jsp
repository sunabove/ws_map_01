<%@page language="java" contentType="text/xml; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="./M020_MapControl_Act.jsp" />

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
   ,
   width : ${ width }
   , 
   height : ${ height }
}
