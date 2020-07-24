<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="htmlAll" value="${ param.htmlAll != null }" scope="request" />

<c:if test="${ htmlAll }" >

    <jsp:include page="./C002_HtmlDocType.jsp"></jsp:include>
	
	<title> 제주 노선 검색 2 ${visitNo}  </title> 
	
	<jsp:include page="./C001_ComHeader.jsp"></jsp:include> 	

</c:if> 

<script>

// 검색 폼 만들기.

function getXmlStore( pageUrl, colInfo ) {

    var store = new Ext.data.Store({
         
        url: pageUrl , 
        reader: new Ext.data.XmlReader(
            {              
               record: 'ROW',
               id: 'RNO',
               totalRecords: 'CNT'
            }
            ,
            colInfo
           )
    });
    
    return store;
    
}

var xg = Ext.grid; 

Ext.namespace('Ext.bus'); 

// 지역 데이터.

Ext.bus.areaList = [
          ['0', "제주" ]
        , ['1', "서귀포" ] 
    ];
    
var store_AreaList = new Ext.data.SimpleStore({
        fields: [ "id", "area" ],
        data : Ext.bus.areaList 
    });
    
var comboBox_areaList = new Ext.form.ComboBox({
    fieldLabel: '지역',
    store: store_AreaList ,
    displayField: 'area',
    valueField: 'id', 
    typeAhead: true,
    mode: 'local',
    forceSelection: true ,
    value: 0,
    emptyText:'지역 선택',
    //width: 120,
    autoWidth: true,
    editable : false ,
    selectOnFocus: false,
    triggerAction: 'all' ,
    //listeners: { select : test } ,
    //transform : "area_select" , 
    //applyTo: 'local-states' ,
    a : ""                    
})  ;   
    
// 끝. 지역 데이터.

// 노선 콤보 박스.

var colInfo_Nosun = [
               {name: 'RNO', type: 'float'},
               {name: 'CNT', type: 'float'},
               {name: 'NOSUN_NM', type: 'string'},
               {name: 'NOSUN_DESC', type: 'string'}
           ] ;
               
var store_NosunList = getXmlStore( "./DS001_BusNosun.jsp", colInfo_Nosun );

var comboBox_NosunList = new Ext.form.ComboBox({
       fieldLabel: '노선',
       store: store_NosunList ,
       displayField: 'NOSUN_DESC', 
       valueField: 'NOSUN_NM',        
       typeAhead: true,
       mode: 'local',
       forceSelection: true ,
       //value: 0,
       emptyText:'노선번호 선택',
       //width: 120,
       autoWidth: true,
       editable: false,
       selectOnFocus: false ,
       triggerAction: 'all' ,  
       listeners: { change : load_DirComboBoxData } ,  // 노선을 선택하면 방향값 콤보박스를 로드한다.
       //transform : "area_select" ,  
       //applyTo: 'local-states' ,  
       a : ""                    
   })  ;
   
// 끝. 노선 콤보 박스.

// 방향 콤보 박스.

var colInfo_Dir = [
               {name: 'RNO', type: 'float'},
               {name: 'CNT', type: 'float'},
               {name: 'NOSUN_NM', type: 'string'},
               {name: 'DIR_ID', type: 'string'},
               {name: 'DIR_DESC', type: 'string'},
               {name: 'X_POS_CEN', type: 'float'},
               {name: 'Y_POS_CEN', type: 'float'},
               {name: "" }               
           ] ;
               
var store_DirList = getXmlStore( "./DS001_BusNosun.jsp", colInfo_Dir );
   
var comboBox_DirList = new Ext.form.ComboBox({
       fieldLabel: '방향',
       store: store_DirList ,
       displayField: 'DIR_DESC',
       valueField: 'DIR_ID', 
       typeAhead: true,
       mode: 'local',
       forceSelection: true ,
       emptyText:'노선방향 선택',
       editable: false,
       selectOnFocus: false,
       //width: 120,
       autoWidth: true,
       triggerAction: 'all' ,
       listeners: { select : showBusNosunLineOnMap_ComboBoxSelect } ,
       //transform : "area_select" ,
       //applyTo: 'local-states' ,
       a : ""                    
   })  ;
   
function load_DirComboBoxData() {

    var nosun_nm = comboBox_NosunList.getValue();
    
    if( false ) {
        alert( "nosun_nm = " + nosun_nm );    
        return ; 
    }
    
    var comboBox = comboBox_DirList;
    comboBox.store.removeAll();
    
    comboBox.setValue( "" );
    
    var paramsObj_Dir =  { params: { "class" : "dir" , "nosun_nm" : nosun_nm } };
    
    var store = store_DirList ;
    
    var paramsObj = paramsObj_Dir ;

    store.load( paramsObj );
    
}
   
// 끝. 방향 콤보 박스.

var button_Srch = new  Ext.Button( {
        text : "검색"
        , listeners : { click : srch_Submit }
    } );  

var srchForm = new Ext.FormPanel({ 

    labelWidth: 40, // label settings here cascade unless overridden
    url: "" ,
    frame: true , 
    //title: '노선 검색',
    bodyStyle:'padding:5px 5px 0',    
    width: 250,
    //defaults: {width: 230},
    //plugins: [new Ext.ux.plugins.FitToParent()], // 자동 사이즈 조절.
    autoWidth: true,
    defaultType: 'textfield',

    items: [
        comboBox_areaList  
        , comboBox_NosunList
        , comboBox_DirList        
    ],

    buttons: [
        button_Srch      
    ]
    
});       

function createSrchForm() {

    // 노선 데이터 로딩.

    var nosun_Params =  {params: { "class" : "nosun" }};

    store_NosunList.load( nosun_Params );
    
    // 끝. 노선 데이터 로딩.
    
    // 검색 폼 렌더링.
    
    srchForm.render( "srchCond_FormPane" ); 
    
    // 끝. 검색 폼 렌더링. 
}

var recordBusNosun_Sel  = null; // 선택된 버스 노선 레코드 값.

function showBusNosunLineOnMap_ComboBoxSelect ( combo, record, index ) { // 리스너에서 자동으로 넘어오는 변수들.....

    recordBusNosun_Sel = record;
    
    if( true ) { return ; }
    
    var nosun_nm = comboBox_NosunList.getValue();
    var dir_id = comboBox_DirList.getValue() ;
    var zoomLevel = 6;
    
     if( dir_id > -1 ) {
        var map = getMap();
        if( map != null ) {            
            map.doMapClear(); //map.fullExtent();
            map.setMapLevel( zoomLevel ); 
    
		    if( record != null ) {         
		        var xPosCen = record.get( "X_POS_CEN" ); 
		        var yPosCen = record.get( "Y_POS_CEN" ); 
		        
		        map.setMapCenter( xPosCen , yPosCen ); 
		    }
		        
            showBusNosunLineOnMap( nosun_nm , dir_id );
        }
     } 
}

function srch_Submit() {

    var nosun_nm = comboBox_NosunList.getValue();
    
    //alert( "nosun_nm = " + nosun_nm ); 
    
    if( nosun_nm == '' || nosun_nm < 0 ) {
        messageBox( "노선을 선택하십시오!" , "노선 선택:" );        
        return;
    } 
    
    var dir_id = comboBox_DirList.getValue() ;
    
    if( dir_id == '' || dir_id == null || dir_id < 0 ) {
        messageBox( "방향을 선택하십시오!" , "방향 선택:" );
        return;
    }
    
    var map = getMap();
    
    if( map != null ) {      
          
        map.doMapClear(); //map.fullExtent();
        
        var zoomLevel = 6;
        
        map.setMapLevel( zoomLevel ); 
        
        var record = recordBusNosun_Sel ;

        if( record != null ) {          
            var xPosCen = record.get( "X_POS_CEN" ); 
            var yPosCen = record.get( "Y_POS_CEN" ); 
            
            map.setMapCenter( xPosCen , yPosCen ); 
        } 
        
        showBusNosunLineOnMap( nosun_nm , dir_id );
        
    } 
    
    show_SrchData_JrjOfNosunDir( nosun_nm , dir_id );
    
}

// 끝. 검색 폼 만들기.

</script>

<script>

// 검색 결과 그리드 만들기.

// Renderer

function rdr_nosun(val){
    return rdr_icon( val, val, "green" );
}

function rdr_gyns(val){
    return rdr_icon( val, "경유노선", "green" );
}

function rdr_mybus(val){
    return rdr_icon( val, "MyBus", "red" );
}

function rdr_dochak(val){
    return rdr_icon( val, "도착", "blue" );
}

// example of custom renderer function

var rowNo = 0;

function rdr_first_col(val){
    var color = "gray";
    
    if( rowNo%2 == 0 ){
        color = "black";
    }
    
    rowNo ++ ;
    
    return rdr_icon( val, val, color );
} 

function rdr_icon( val , text, color) {
    if( false ) { return val ; }
    return '<span style="cursor:hand; color:' + color + ';"><center>' + text + '</center></span>'; 
}

// End of Renderer. 

// 검색 데이터.

var srchResult_ColInfo = [
               {name: 'RNO', type: 'float'},
               {name: 'CNT', type: 'float'},
               {name: 'NOSUN_ID', type: 'string'},
               {name: 'NOSUN_NM', type: 'string'},
               {name: 'GIJUM', type: 'string'},
               {name: 'JONGJUM', type: 'string'},
               {name: 'DIR_ID', type: 'float'},
               {name: 'JRJ_ID', type: 'float'},
               {name: 'JRJ_NM', type: 'string'},
               {name: 'X_POS', type: 'float'},
               {name: 'Y_POS', type: 'float'},
               {name: 'SUNBUN', type: 'float'}
           ] ;

var srchResult_Store = getXmlStore( "./DS001_BusNosun.jsp", srchResult_ColInfo );

// 검색 결과 그리드.

var srchResult_Grid_Cm = new xg.ColumnModel(
        [
            new xg.RowNumberer(),
            //{header: "No", width: 35, renderer: rdr_first_col, sortable: false, dataIndex: "RNO"},
            {header: "정류장", width: 65, renderer: "", sortable: true, dataIndex: "JRJ_NM" },
            {header: "경유노선", width: 65, sortable: false, renderer: rdr_gyns, dataIndex: "JRJ_ID" , align: "center"},
            {header: "MyBus", width: 55, sortable: false, renderer: rdr_mybus, dataIndex: "NOSUN_ID" , align: "center"},
            {header: "도착", width: 45, sortable: false, renderer: rdr_dochak, dataIndex: "JRJ_ID" , align: "center"},
            {header: "순번", width: 35, renderer: "", sortable: false, dataIndex: "SUNBUN" , align: "center" },
            {header: "방향", width: 35, sortable: false,  dataIndex: "DIR_ID", align: "right" },
            {header: "JRJ_ID", width: 50, sortable: false,  dataIndex: "JRJ_ID", align: "right" },
            {header: "X", width: 45, sortable: false, dataIndex: "X_POS" , align: "right" },
            {header: "Y", width: 45, sortable: false, dataIndex: "Y_POS" , align: "right" },
            {header: "노선", width: 35, renderer: "", sortable: false, dataIndex: "NOSUN_NM" },
            {header: "기점", width: 45, sortable: false, dataIndex: "GIJUM" },
            {header: "종점", width: 45, sortable: false, dataIndex: "JONGJUM" , align: "center" }              
        ]
      ) ;
      
var srchResult_Grid = new Ext.grid.GridPanel( {

    store:  srchResult_Store ,
    
    cm: srchResult_Grid_Cm , 
        
    viewConfig: {
        forceFit: false ,
        //autoFill: true,
        a: ""
    },
    stripeRows: false,
    sm: new Ext.grid.RowSelectionModel({singleSelect:true}),    
    //autoExpandColumn: 'company',
    height:550,
    width: 500,
    //autoHeight: true,
    frame: true,   
    title: "검색 결과",
    iconCls: 'icon-grid',
    listeners: { click : showBusJrjOnMap },
    //plugins: [new Ext.ux.plugins.FitToParent()], // 자동 사이즈 조절.
    footer: true,
    a: ""
});

function showBusNosunLineOnMap( nosunNm, dirId ) {    

   var localDebug = false;
   
   if( localDebug ) {               
	    alert( "nosunNm = " + nosunNm );
	    alert( "dirId = " + dirId );    
    }

    var url = "./M042_JejuBusNosun_ShowAct.jsp";
    
    var params = { "class" : "vertex" ,
                   "nosun_nm" : nosunNm ,
                   "dir_id" : dirId,
                   "a" : ""
                  };
                  
    var map = getMap(); 
    
    if( map != null ) { 
        map.callAjaxUrlParams( url, params, "" ); 
        map.mapRefresh();     
    }
    
}

function showBusJrjOnMap() {

    var grid = srchResult_Grid;
    
    var record = grid.getSelectionModel().getSelected();
    
    if( record != null ) {
    
        var nosunNm = record.get( "NOSUN_NM" );
        var dirId = record.get( "DIR_ID" );
        var jrjId = record.get( "JRJ_ID" );
        var jrjNm = record.get( "JRJ_NM" );
        var xPos = record.get( "X_POS" );
        var yPos = record.get( "Y_POS" );
        
        var layerName = "bus_poi_added"
        var id = jrjId ;   //var id = record.id;         
        var iconName = "BusStopHa_Red_High_30x27.png";  //var iconName = "BusStop_High.gif"; 
        var textColor = "FF0000" ;
        var zoomLevel = 1 ;
        //alert( record.id + " : " + jrjId + " : " + xPos + " : " + yPos );    
        
        if( getMap != null ) {
        
	        var map = getMap();
	        
	        if( map != null ) {
	        
	            var poi = {
	                           "id" : id ,
	                           "layerName" : layerName ,
	                           "label" : jrjNm ,
	                           "icon" : iconName ,
	                           "mapX" : xPos ,
	                           "mapY" : yPos ,
	                           "textColor" : textColor ,
	                           "a" : ""
	                       }; 
	                       
	            map.doAddPoi( poi );
	            map.setMapLevel( zoomLevel ); 
	            map.setMapCenter( xPos, yPos );
	            map.mapRefresh(); 
	        }
	        
        }
    }
}

function show_SrchData_JrjOfNosunDir( nosun_nm , dir_id ) { // 노선과 방향에 해당하는 정류장 데이터 검색.

    var grid = srchResult_Grid; 
    
    var store = grid.store;
    
    var params = { params: 
                        { 
                            "class" : "jrj" ,
                            "nosun_nm": nosun_nm ,
                            "dir_id" : dir_id ,
                            "a" : ""
                        } 
                  } ;
    
    store.load( params );
    
    //store.load( );
    
    grid.render( "srchResult_GridPane" ); 
    
    grid.getSelectionModel().selectFirstRow(); 
       
    var gridTitle = "검색 결과 :";
    
    var totalCount = grid.store.getTotalCount();
    
    if( totalCount > 0 ) {
        gridTitle = "검색 결과 : 총 " + totalCount + "건" ;    
    }
    
    grid.setTitle( gridTitle );
    
}

function initSrchForm() {
    createSrchForm();            
    show_SrchData_JrjOfNosunDir( "", "" );
}

// 끝. 검색 결과 그리드 만들기.

</script>

<c:if test="${ htmlAll }" >
	
	<body onload="" class="bodyZero" >
	
	    <div id="srchCond_FormPane" >        
	    </div> 
	    
	    <div id="srchResult_GridPane" class="sizeFull" >       
	    </div>
	    
	    <script>
	    
	        initSrchForm();
	            
	    </script>
	
	</body> 

</c:if>
