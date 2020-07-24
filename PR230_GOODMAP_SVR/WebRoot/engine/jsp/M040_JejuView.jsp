<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%> 

<jsp:include page="./C002_HtmlDocType.jsp"></jsp:include>

<html>
<head>
<title> 제주도 GBIS 데모 F V1.0.004 :  ${visitNo} </title> 

<jsp:include page="./C001_ComHeader.jsp"></jsp:include>

<script type="text/javascript">

var copyrightText = "<img src='../image/bg/y.gif' align='absmiddle' /> &nbsp; © 2009 Y&H E&C Co., Ltd. &nbsp; All rights reserved.";

function createMapUI() {
 
   var viewport = new Ext.Viewport({
        layout:'border',
        monitorResize : true ,
        items:[
            new Ext.BoxComponent({ // raw
                region:'north',
                el: 'north',
                collapsible: true,
                autoWidth: true, 
                //height: 32,
                a: ""
            })
            ,
             {
                region:'west',
                id:'west-panel',
                title:'메뉴',
                split:true,
                width: 270,
                minSize: 175,
                maxSize: 270, 
                collapsible: true,
                margins:'0 0 0 0',
                layout:'accordion',
                layoutConfig:{
                    animate:true
                },
                items: [
                    {
                        contentEl: 'west',
                        title:'노선검색',
                        border:false,
                        iconCls:'nav'
                    }
                    ,
                    {
                        title:'정류장검색',
                        html:'<p></p>',
                        border:false,
                        iconCls:'settings'
                    }                         
                ]
            }
            ,
            {
                region:'east',
                title: '버스정보',
                collapsible: true,
                split:true,
                width: 225,
                minSize: 175,
                maxSize: 280,
                layout:'fit',
                //layout:'accordion',
                margins:'0 0 0 0',
                items:
                    new Ext.TabPanel({
                        border:false,
                        activeTab:0,
                        tabPosition:'top',
                        items:
                            [
                                {
                                    html:'<p>없음</p>',
                                    title: '정류장정보',
                                    autoScroll:true
                                } 
                                ,
                                {
                                    html:'<p>없음</p>',
                                    title: '노선정보',
                                    autoScroll:true
                                }
                                /*,new Ext.grid.PropertyGrid(
                                   {
                                            title: '환경설정',
                                            closable: false,
                                            source: {
                                                "(name)": "Properties Grid",
                                                "grouping": false,
                                                "autoFitColumns": true,
                                                "productionQuality": false,
                                                "created": new Date(Date.parse('10/15/2006')),
                                                "tested": false,
                                                "version": .01,
                                                "borderWidth": 1
                                            }
                                   }
                                )*/
                            ]
                    })
             }
             ,
            {
                region:'south',
                contentEl: 'south',
                split: true ,
                height: 50,
                //minSize: 50,
                //maxSize: 50,
                autoWidth: true,                
                collapsible: true,
                title: copyrightText ,
                margins:'0 0 0 0'
            },
            new Ext.TabPanel({
                region:'center',
                deferredRender: true,
                activeTab:0,
                margins:'0 0 0 0',  
                items:
                    [
                        {
                            contentEl:'mapPane',
                            title: '지도',
                            closable: false,
                            margins:'0 0 0 0',
                            autoScroll: false,
                            autoWidth: false ,             
                            "a" : ""
                        }
                        /*,
                        {
                            contentEl:'center2',
                            title: '지도2',
                            margins:'0 0 0 0',
                            autoScroll:true
                        }*/
                    ]
             }
            )
         ]
    });
    
    /*
    Ext.get("hideit").on('click', function() {
       var w = Ext.getCmp('west-panel');
       w.collapsed ? w.expand() : w.collapse(); 
    });
    */
    
}

var bodyLoad = false;

function bodyOnLoad() {

    createMapUI(); 
    initSrchForm(); // 검색 폼 초기화. 
    
    bodyLoad = true;
    
    loadMapPage();  // 지도 초기화.  
    makeToolTipList(); 
    makeButtonList();
}

</script>

<script type="text/javascript">

function goodMapMouseMove( e ) {
    //alert( "mouse move" );
}

function goodMapMouseDown( e ) {
    //alert( "mouse down" );
}

function goodMapMouseUp( e ) {
    //alert( "mouse up" );
}

function goodMapMouseWheel( e ) {
    //alert( "mouse wheel" );
}

</script>

<jsp:include page="./M041_JejuBusNosun_SrchView.jsp"></jsp:include>

</head> 


<body onload="bodyOnLoad();" style="padding:0; margin:0; width:100%; height:100%;" >

  <div id="north" style="width:100%; " >
    <p class="title" >
        제주특별자치도 광역버스정보시스템
        <span style="color:gray; font-size:9pt;">BETA</span>
        <span style="color:gray; font-size:8pt; text-align:right;" >
            &nbsp;&nbsp; Powered by 
            <img src="../image/map/GoodMap_90x23.gif" align="middle"/>
            <!-- Powered by GoodMap™  -->
        </span>
    </p>
  </div>
  
  <div id="west" style="width:270; height: 100%;" >  
        <div id="srchCond_FormPane" style="width: 100%; ">      
        </div>     
        <div id="srchResult_GridPane" style="width: 100%; height: 100%; ">
        </div>
  </div>
  
  <div id="mapPane" style="padding:0; margin: 0; width:100%; height:100%; " >
        <jsp:include page="./M030_MapView.jsp"></jsp:include>       
  </div>
  
  <div id="south" style="width: 100%; height:0;">
  </div>
  
 </body>
</html>
