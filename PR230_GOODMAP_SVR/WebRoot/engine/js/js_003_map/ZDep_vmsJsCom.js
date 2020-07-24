// 자바스크립트의 String 타입에 trim() 함수를 추가해 준다.
// 다른 스크립트에서 추가하였으면 에러 날 가능성도 1% 정도는 있다.
// prototy.trim 이 있으면 trim() 함수를 추가하지 않도록 해주면 더 좋을 것이다.

String.prototype.trim = function()
{

  var x=this;
  x=x.replace(/^\s*(.*)/, "$1");
  x=x.replace(/(.*?)\s*$/, "$1");

  return x;

} // 끝. 자바스크립트 String 타입에 trim() 함수 추가 하기.

function __ShowEmbedObject(__ELEMENT_ID) 
{
      document.write( __ELEMENT_ID.innerHTML );
      __ELEMENT_ID.id = "";
}

function vmsGetNumber( val )
{
	if( val == null ) {
		return 0;
	} else {
		val = ("" + val).trim() ;
		if( val.length < 1 ) {
			return 0;
		} else {
			return parseFloat( val );
			// 자바스크립트에는 parseDouble 이 없다. ㅠ..ㅠ.
		}
	}
}

function vmsGetInt( val ) {
	return parseInt( "" + vmsGetNumber( val ) );
}

var vmsContinueWarn = true;

function vmsGetObject( id )
{
    // HTML body 문서내의 객체를 반환한다.
        try
        {
            //var obj = document.getElementById( id );

			var obj ;

			if( document.getElementById ) // this is the way the standards work
				obj = document.getElementById( id );
			else if( document.all ) // this is the way old msie versions work
				obj = document.all[ id ];
			else if( document.layers ) // this is the way nn4 works
				obj = document.layers[ id ];

			if( obj == null )
			{
				if( vmsContinueWarn ) {
					if( ! confirm( id + '에 해당하는 객체를 찾을 수 없습니다.\n\n관리자에게 문의하세요!' ) ) {
						vmsContinueWarn = false;
					}
				}
				return null;
			}

			return obj;
        }
        catch ( exception )
        {
                return null;
        }

}

function vmsGetValue( id )
{
    // body 문서내의 객체의 value를 반환한다.
        try
        {
                var obj = vmsGetObject( id );
                if( obj != null ) {
                        return obj.value;
                } else {
                        return '';
                }
        }
        catch ( exception )
        {
                return '';
        }

}

function vmsSetValue( id , val )
{
    // body 문서내의 객체의 value 값을 설정한다.
        try
        {
                var obj = vmsGetObject( id );

                if( obj != null ) {
                        obj.value = val;
                        return true;
                } else {
                        vmsMessage( 'Cannot find object[' + id + ']' );
                        return false;
                }
        }
        catch ( exception )
        {
                vmsMessageWarn( msg );
                return false;
        }

}

// 끝. 다큐먼트 객체 관련 함수들.

// 메시지 관련 함수들.

function vmsMessageWarn( msg )
{
    // 경고 메시지.
        vmsMessage( 'Warn: ' + msg );
}

function vmsWarn( msg )
{
    // 경고 메시지.
        vmsMessageWarn( msg );
}

function vmsDebug( msg )
{
    // 디비거 메시지.
        vmsMessage( 'DEBUG: ' + msg );
}

function vmsMessage( msg )
{
    // 메시지 함수.
        alert( msg );
}

// 끝. 메시지 관련 함수들.....

// 폼 관련 함수들.

function vmsFormStyleVisibility( id, vis_or_hid )
{
        // id에 해당하는 폼의 스타일의 visibility 값을 'visible' 이나 'hidden' 으로 설정한다.

        var obj = vmsGetObject( id );
        if( obj != null ) {
                if( obj.style != null ) {
                        obj.style.visibility = vis_or_hid ;
                }
        }

}

function vmsFormVisible( id )
{
        // 폼을 보이도록 한다.

        vmsFormStyleVisibility( id, 'visible' );

}

function vmsFormHidden( id )
{
        // 폼을 숨긴다.

        vmsFormStyleVisibility( id, 'hidden' );
}

function vmsDoNothing() {
    // 아무 기능도 하지 않는 함수. 자바스크립트 문법상 필요할 경우 사용할 수 있다.
}

function vmsFormLoadingStart()
{
        // 로딩 폼 나타내기
        vmsFormVisible( 'div_wait' );

} // 로딩 폼 나타내기

function vmsFormLoadingEnd()
{
        // 로딩 폼 숨기기
        vmsFormHidden( 'div_wait' );

} // 로딩 폼 숨기기

function vmsFormLoadStart()
{
        // 로딩 폼 나타내기
        vmsFormLoadingStart();
} // 로딩 폼 나타내기

function vmsFormLoadEnd()
{
        // 로딩 폼 숨기기
        vmsFormLoadingEnd();
} // 로딩 폼 숨기기

// 끝. 폼 관련 함수들.

function vmsTranStart()
{
        // 트랜젝션 시작시 로딩로고 보이는 함수.

        vmsFormVisible( 'div_wait' );

} // 트랜젝션 시작시 로딩로고 보이는 함수.

function vmsTranEnd()
{
        // 트랜젝션 종료시 로딩로고 숨기는 함수.

        vmsFormHidden( 'div_wait' );

} // 트랜젝션 종료시 로딩로고 숨기는 함수.

// 트랙젝션 관련 함수들.


// 끝. 트랜젝션 관련 함수들.

function vmsIsHtmlDocumentLoadingComplete()
{
    // HTML Document Loading 을 검증하는 함수
    // HTML 문서의 맨뒤에 히든 폼 아디 'htmlDocumentLoadingComplete' 을 전송하여 검증한다.

        var obj = vmsGetObject( 'htmlDocumentLoadingComplete' );

        if( obj != null && obj.value == 'true' )
        {
                // HTML 문서 다운로드 정상 완료!
                return true;
        }
         else
         {
                vmsMessage( 'HTML 문서의 다운로드가 정상적으로 완료되지 않았습니다.\n잠시 후 다시 시도하여 주십시오!' );
                return false;
        }

} // 끝. HTMl Document Loading 검증.

function getCurrentTime() {

  // 현재 시각 구하기.
  var now=new Date();
  hour=now.getHours();
  min=now.getMinutes();
  sec=now.getSeconds();

  return hour*3600 + min*60 + sec ;
  // 끝. 현재 시각 구하기.

}