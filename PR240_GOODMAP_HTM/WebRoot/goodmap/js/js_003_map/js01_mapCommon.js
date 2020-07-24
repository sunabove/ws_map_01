// 시작. 공통 함수들.

Number.prototype.toRad = function() {  // convert degrees to radians
  return this * Math.PI / 180.0 ;
}

Number.prototype.toDeg = function() {  // convert radians to degrees
  return ( this / Math.PI ) * 180.0 ;
}

function setValue( id, val ) {
    var obj = getObject( id );
    
    if( obj != null ) {
        obj.value = val;    
    }             
}

function getCurrentTimeMillis(){ // 현재 시각 구하는 함수.
   var d = new Date();
   
   var s = 0;
   s += d.getHours()*3600*1000;
   s += d.getMinutes()*60*1000;
   s += d.getSeconds()*1000;
   s += d.getMilliseconds();
   return(s);
} 

function formatNumber(num, decimalNum, bolLeadingZero, bolParens)
/* IN - num:            the number to be formatted
        decimalNum:     the number of decimals after the digit
        bolLeadingZero: true / false to use leading zero
        bolParens:      true / false to use parenthesis for - num

   RETVAL - formatted number
*/
{
    var tmpNum = num;

    // Return the right number of decimal places
    tmpNum *= Math.pow(10,decimalNum);
    tmpNum = Math.floor(tmpNum);
    tmpNum /= Math.pow(10,decimalNum);

    var tmpStr = new String(tmpNum);

    // See if we need to hack off a leading zero or not
    if (!bolLeadingZero && num < 1 && num > -1 && num !=0)
        if (num > 0)
            tmpStr = tmpStr.substring(1,tmpStr.length);
        else
            // Take out the minus sign out (start at 2)
            tmpStr = "-" + tmpStr.substring(2,tmpStr.length);                        


    // See if we need to put parenthesis around the number
    if (bolParens && num < 0)
        tmpStr = "(" + tmpStr.substring(1,tmpStr.length) + ")";


    return addCommas( tmpStr );
}

function addCommas(nStr)
{
    nStr += '';
    x = nStr.split('.');
    x1 = x[0];
    x2 = x.length > 1 ? '.' + x[1] : '';
    var rgx = /(\d+)(\d{3})/;
    while (rgx.test(x1)) {
        x1 = x1.replace(rgx, '$1' + ',' + '$2');
    }
    return x1 + x2;
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

// 메시지 함수들.

function messageBox_Old( message, listener ) {

    var title = "" ;    
     Ext.MessageBox.alert( title, message, listener );
     //Ext.MessageBox.alert('Status', 'Changes saved successfully.', showResult);
}

function messageBox( message, title, icon, listener ) {
    //Ext.Msg.alert( title, message ); 
    messageBoxShow( message, title, icon, listener , Ext.MessageBox.INFO );
}

function messageBox_Info( message, title, icon, listener ) {
    messageBoxShow( message, title, icon, listener , Ext.MessageBox.INFO );
}

function messageBox_Quest( message, title, icon, listener ) {
    messageBoxShow( message, title, icon, listener , Ext.MessageBox.QUESTION );
}

function messageBox_Warn( message, title, icon, listener ) {
    messageBoxShow( message, title, icon, listener , Ext.MessageBox.WARNING );
}

function messageBox_Error( message, title, icon, listener ) {
    messageBoxShow( message, title, icon, listener , Ext.MessageBox.ERROR );
}

function messageBoxShow( message, title, icon, listener , icon_Def ) {

    var info = Ext.MessageBox.INFO;
    var question = Ext.MessageBox.QUESTION;
    var warning = Ext.MessageBox.WARNING;
    var error = Ext.MessageBox.ERROR;
    
    var srcElement = window.event.srcElement ;
    //srcElement = "";
    
    icon = ( icon == null || icon == "" ) ? icon_Def : icon ;
    icon = ( icon == null || icon == "" ) ? error : icon ;
     
    Ext.MessageBox.show({
       "title" : title ,
       msg: message  ,
       buttons: Ext.MessageBox.OK ,
       animEl: srcElement ,
       //animEl: 'mb9',
       fn: listener ,
       "icon" : icon , 
       a : "" 
   });     
    
}

// 끝. 메시지 함수들.

// 끝. 공통 함수들.

// 공간 함수들. 

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

// 끝. 공간 함수들.