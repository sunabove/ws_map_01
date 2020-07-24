<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:if test="${ headerInclude == null || headerInclude == false }" >

<c:set var="headerInclude" value="${ true }" scope="request" />

<c:set var="visitNo" value="${visitNo != null ? visitNo + 1 : 0 }" scope="request" />

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="imagetoolbar" content="no">

<LINK REL="SHORTCUT ICON" HREF="../../favicon.ico">

<link rel="stylesheet" type="text/css" href="../js/js_005_ext-2.2/resources/css/ext-all.css" />
<link rel="stylesheet" type="text/css" href="../js/js_005_ext-2.2/examples/shared/examples.css" />
<link rel="stylesheet" type="text/css" href="../js/js_005_ext-2.2/examples/tabs/tabs-example.css" />

<link rel="stylesheet" type="text/css" href="../css/css_002_MapView.css">

<script type="text/javascript" src="../js/js_005_ext-2.2/adapter/prototype/prototype.js"></script>
<script type="text/javascript" src="../js/js_004_scriptaculous/scriptaculous.js"></script>
<script type="text/javascript" src="../js/js_005_ext-2.2/adapter/prototype/ext-prototype-adapter.js"></script>
<script type="text/javascript" src="../js/js_005_ext-2.2/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="../js/js_005_ext-2.2/ext-all.js"></script>

<script type="text/javascript">

// Ext Js 초기화 함수.

function initExtJsFrameWork() {

    Ext.QuickTips.init();
    
    Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
    
    // turn on validation errors beside the field globally
    Ext.form.Field.prototype.msgTarget = 'side';

}

//initExtJsFrameWork();

// 끝. Ext Js 초기화 함수.

// 자동 사이즈 조절기

Ext.namespace('Ext.ux.plugins');
Ext.ux.plugins.FitToParent = function(parent) {
    return {
        init: function(c) {
            c.on('render', function(c) {
                parent = Ext.get(parent || c.el.dom.parentNode);
            });
            c.monitorResize = true;
            c.doLayout = c.doLayout.createInterceptor(function(){
                var pos = this.getPosition(), size = parent.getViewSize();
                this.setSize(size.width - pos[0], size.height - pos[1]);
            }, c);
        }
    }
}

// 끝. 자동 사이즈 조절기.

</script>

<script type="text/javascript">

Ext.BLANK_IMAGE_URL = '../js/js_005_ext-2.2/resources/images/default/s.gif';

Ext.example = function(){
    var msgCt;

    function createBox(t, s){
        return ['<div class="msg">',
                '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
                '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', t, '</h3>', s, '</div></div></div>',
                '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
                '</div>'].join('');
    }
    return {
        msg : function( title, format , obj ){
            if(!msgCt){
                msgCt = Ext.DomHelper.insertFirst(document.body, {id:'msg-div'}, true);
            }
            msgCt.alignTo(document, 't-t');
            var s = String.format.apply(String, Array.prototype.slice.call(arguments, 1));
            var m = Ext.DomHelper.append(msgCt, {html:createBox(title, s)}, true); 
            //m.slideIn('t').pause(1).ghost("t", {remove:true});
        }, 

        init : function(){
            var t = Ext.get('exttheme');
            if(!t){ // run locally?
                return;
            }
            var theme = Cookies.get('exttheme') || 'aero';
            if(theme){
                t.dom.value = theme;
                Ext.getBody().addClass('x-'+theme);
            }
            t.on('change', function(){
                Cookies.set('exttheme', t.getValue());
                setTimeout(function(){
                    window.location.reload();
                }, 250);
            });

            var lb = Ext.get('lib-bar');
            if(lb){
                lb.show();
            }
        }
    };
}();

//Ext.onReady(Ext.example.init, Ext.example);

// 툴팁 함수.

function makeToolTip( target, message , title ) {
    new Ext.ToolTip({
        "target" : target ,
        "title" : title ,
        html: message ,
        trackMouse: true ,
        "a" : ""
    });  
}

// 끝. 툴팁 함수.

</script> 

<script type="text/javascript" src="../js/js_003_map/js01_mapCommon.js"></script>  

<script type="text/javascript" src="../js/js_006_proj/geocent.js"></script>
<script type="text/javascript" src="../js/js_006_proj/tmerc.js"></script>
<script type="text/javascript" src="../js/js_006_proj/cscs.js"></script>
<SCRIPT type="text/javascript" src="../js/js_006_proj/defs/GOOGLE_WGS84.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/js_006_proj/defs/TM128_katech_3param.js"></SCRIPT> 
<SCRIPT type="text/javascript" src="../js/js_006_proj/defs/TM128_katech_7param.js"></SCRIPT>
<SCRIPT type="text/javascript" src="../js/js_006_proj/defs/UTM_K.js"></SCRIPT>

<script type="text/javascript" >

// Define Coordinate system
var TM128_naver = new CS(csList.TM128_katech_3param);
var TM_Center = new CS(csList.KOREA_CENTER_TM_3param);

//var TM128_naver = new CS(csList.TM128_katech_7param);
var WGS84_google = new CS(csList.GOOGLE_WGS84);   

function getTmToWgs84_Old3( x_tm , y_tm ) {  
    var p2 = new PT( x_tm, y_tm ); //(X_east,Y_north)
    cs_transform(TM_Center, WGS84_google, p2);
    return p2;    
}

</script>

</c:if>

