<%@ page contentType="text/html; charset=utf-8" %>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
  
<script>

function call_it( poi ) {
   var obj = get_obj( "poi_name" );
   obj.value = poi;
   alert( 'multics poi = ' + poi );
   main_form_submit( "" );
}

function get_obj(id){
	 return document.getElementById( id ); 
}

function main_form_submit(a){
	var obj = get_obj( 'MAIN_FORM' );
	if( obj != null ) { 
		obj.submit();
	} else {
		alert( "submit is skipped!" );
	}
}

function change_form_method() {
        var obj = get_obj( 'MAIN_FORM' );
        
        if( obj != null ) {
           var method = obj.method ;
           if( method == 'post' || method == 'POST' ) {
              obj.method = 'get';
           } else {
              obj.method = 'post';
           }
           alert( obj.method );           
        }
}

function showData( a ) {
  window.status = a;
}

</script>

</head>

<body>

<form name="MAIN_FORM" target = "_new" method="post" action="http://221.150.168.64/multics_gis/index.jsp">

<% 
  String [] data = { "왕남초등학교", "정토사", "남한산", "ì±ë¨:ë¨íì°" } ; 
  String poi;
%>

<% for( int i = 0, len = data.length ; i < len; i ++ ) { 
    poi = data[i];   
  %>

<p onClick="call_it( '<%=poi%>' );" 
  onMouseMove= "showData( '<%=poi%>' );" 
> 
  <%= poi %>
</p>

<% } %>
 
POI NAME <input type=text name=poi_name size="20"  >
<input type=submit name=go >

</form>

</body>
</html>