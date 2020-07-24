// �ڹٽ�ũ��Ʈ�� String Ÿ�Կ� trim() �Լ��� �߰��� �ش�.
// �ٸ� ��ũ��Ʈ���� �߰��Ͽ����� ���� �� ���ɼ��� 1% ������ �ִ�.
// prototy.trim �� ������ trim() �Լ��� �߰����� �ʵ��� ���ָ� �� ���� ���̴�.

String.prototype.trim = function()
{

  var x=this;
  x=x.replace(/^\s*(.*)/, "$1");
  x=x.replace(/(.*?)\s*$/, "$1");

  return x;

} // ��. �ڹٽ�ũ��Ʈ String Ÿ�Կ� trim() �Լ� �߰� �ϱ�.

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
			// �ڹٽ�ũ��Ʈ���� parseDouble �� ����. ��..��.
		}
	}
}

function vmsGetInt( val ) {
	return parseInt( "" + vmsGetNumber( val ) );
}

var vmsContinueWarn = true;

function vmsGetObject( id )
{
    // HTML body �������� ��ü�� ��ȯ�Ѵ�.
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
					if( ! confirm( id + '�� �ش��ϴ� ��ü�� ã�� �� �����ϴ�.\n\n�����ڿ��� �����ϼ���!' ) ) {
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
    // body �������� ��ü�� value�� ��ȯ�Ѵ�.
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
    // body �������� ��ü�� value ���� �����Ѵ�.
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

// ��. ��ť��Ʈ ��ü ���� �Լ���.

// �޽��� ���� �Լ���.

function vmsMessageWarn( msg )
{
    // ��� �޽���.
        vmsMessage( 'Warn: ' + msg );
}

function vmsWarn( msg )
{
    // ��� �޽���.
        vmsMessageWarn( msg );
}

function vmsDebug( msg )
{
    // ���� �޽���.
        vmsMessage( 'DEBUG: ' + msg );
}

function vmsMessage( msg )
{
    // �޽��� �Լ�.
        alert( msg );
}

// ��. �޽��� ���� �Լ���.....

// �� ���� �Լ���.

function vmsFormStyleVisibility( id, vis_or_hid )
{
        // id�� �ش��ϴ� ���� ��Ÿ���� visibility ���� 'visible' �̳� 'hidden' ���� �����Ѵ�.

        var obj = vmsGetObject( id );
        if( obj != null ) {
                if( obj.style != null ) {
                        obj.style.visibility = vis_or_hid ;
                }
        }

}

function vmsFormVisible( id )
{
        // ���� ���̵��� �Ѵ�.

        vmsFormStyleVisibility( id, 'visible' );

}

function vmsFormHidden( id )
{
        // ���� �����.

        vmsFormStyleVisibility( id, 'hidden' );
}

function vmsDoNothing() {
    // �ƹ� ��ɵ� ���� �ʴ� �Լ�. �ڹٽ�ũ��Ʈ ������ �ʿ��� ��� ����� �� �ִ�.
}

function vmsFormLoadingStart()
{
        // �ε� �� ��Ÿ����
        vmsFormVisible( 'div_wait' );

} // �ε� �� ��Ÿ����

function vmsFormLoadingEnd()
{
        // �ε� �� �����
        vmsFormHidden( 'div_wait' );

} // �ε� �� �����

function vmsFormLoadStart()
{
        // �ε� �� ��Ÿ����
        vmsFormLoadingStart();
} // �ε� �� ��Ÿ����

function vmsFormLoadEnd()
{
        // �ε� �� �����
        vmsFormLoadingEnd();
} // �ε� �� �����

// ��. �� ���� �Լ���.

function vmsTranStart()
{
        // Ʈ������ ���۽� �ε��ΰ� ���̴� �Լ�.

        vmsFormVisible( 'div_wait' );

} // Ʈ������ ���۽� �ε��ΰ� ���̴� �Լ�.

function vmsTranEnd()
{
        // Ʈ������ ����� �ε��ΰ� ����� �Լ�.

        vmsFormHidden( 'div_wait' );

} // Ʈ������ ����� �ε��ΰ� ����� �Լ�.

// Ʈ������ ���� �Լ���.


// ��. Ʈ������ ���� �Լ���.

function vmsIsHtmlDocumentLoadingComplete()
{
    // HTML Document Loading �� �����ϴ� �Լ�
    // HTML ������ �ǵڿ� ���� �� �Ƶ� 'htmlDocumentLoadingComplete' �� �����Ͽ� �����Ѵ�.

        var obj = vmsGetObject( 'htmlDocumentLoadingComplete' );

        if( obj != null && obj.value == 'true' )
        {
                // HTML ���� �ٿ�ε� ���� �Ϸ�!
                return true;
        }
         else
         {
                vmsMessage( 'HTML ������ �ٿ�ε尡 ���������� �Ϸ���� �ʾҽ��ϴ�.\n��� �� �ٽ� �õ��Ͽ� �ֽʽÿ�!' );
                return false;
        }

} // ��. HTMl Document Loading ����.

function getCurrentTime() {

  // ���� �ð� ���ϱ�.
  var now=new Date();
  hour=now.getHours();
  min=now.getMinutes();
  sec=now.getSeconds();

  return hour*3600 + min*60 + sec ;
  // ��. ���� �ð� ���ϱ�.

}