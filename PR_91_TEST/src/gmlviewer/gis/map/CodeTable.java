package gmlviewer.gis.map;

import gmlviewer.gis.kernel.CommonLib;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class CodeTable extends CommonLib {

  public CodeTable() {
  }

  public Code getTopCode() {

    Code topCode = new Code( "��ü����", -1, -1 );

    makeCodeTable( topCode );


    return topCode;

  }

  public static Code code( int i , String name ) {
    return new Code( name, i/10, i%10 );
  }

  private void makeCodeTable( Code topCode ) {

        Code code;

        code = code( 10, "�����п�" );

        topCode.addSubCode( code );

        code.addSubCode( code( 11, "�ʵ�" ) );
        code.addSubCode( code( 12, "�ߵ�" ) );
        code.addSubCode( code( 13, "���" ) );
        code.addSubCode( code( 14, "����" ) );
        code.addSubCode( code( 15, "�п�" ) );
        code.addSubCode( code(  16, "������" ) );
        code.addSubCode( code(  17, "ü����" ) );

        code = code(  20, "�Ƿ��Ļ�" ) ;

        topCode.addSubCode( code );

        code.addSubCode( code(  21, "���к���" ) );
        code.addSubCode( code(  22, "�Ϲݺ���" ) );
        code.addSubCode( code(  23, "���Ǽ�" ) );
        code.addSubCode( code(  24, "�౹" ) );
        code.addSubCode( code(  25, "���ǿ�" ) );
        code.addSubCode( code(  26, "�Ѿ��" ) );
        code.addSubCode( code(  27, "�ǿ�" ) );

        code = code(  30, "���" );

        topCode.addSubCode( code );

        code.addSubCode( code(  31, "���" ) );
        code.addSubCode( code(  32, "����" ) );
        code.addSubCode( code(  33, "������" ) );

        code = code(  40, "����" ) ;

        topCode.addSubCode( code );

        code.addSubCode( code(  41, "ȣ��" ) );
        code.addSubCode( code(  42, "����" ) );
        code.addSubCode( code(  43, "���μ�" ) );
        code.addSubCode( code(  44, "�ι�" ) );
        code.addSubCode( code(  45, "�����" ) );
        code.addSubCode( code(  46, "��쳪" ) );

        code = code(  50, "����" ) ;

        topCode.addSubCode( code );

        code.addSubCode( code(  51, "����" ) );
        code.addSubCode( code(  52, "����" ) );
        code.addSubCode( code(  53, "����" ) );
        code.addSubCode( code(  54, "��Ÿ" ) );

        code = code(  60, "��ȭ ����"  );

        topCode.addSubCode( code );

        code.addSubCode( code(  61, "��ȸ" ) );
        code.addSubCode( code(  62, "��" ) );
        code.addSubCode( code(  63, "���ұ�" ) );
        code.addSubCode( code(  64, "�⵵��" ) );
        code.addSubCode( code(  65, "��ȭü��" ) );
        code.addSubCode( code(  66, "�ڹ���" ) );

        code = code(  70, "��� â��" );

        topCode.addSubCode( code );

        code.addSubCode( code(  71, "��" ) );
        code.addSubCode( code(  72, "����" ) );
        code.addSubCode( code(  73, "�͹̳�" ) );
        code.addSubCode( code(  74, "����������" ) );
        code.addSubCode( code(  75, "�ý�������" ) );
        code.addSubCode( code(  76, "ī��Ÿ" ) );
        code.addSubCode( code(  77, "������" ) );
        code.addSubCode( code(  78, "������" ) );
        code.addSubCode( code(  79, "�ù�" ) );

        code = code(  80, "��ȸ����" );

        topCode.addSubCode( code );

        code.addSubCode( code(  81, "��ȸ����" ) );
        code.addSubCode( code(  82, "��νü�" ) );
        code.addSubCode( code(  83, "����νü�" ) );

        code = code(  90, "�Ƶ�����" );

        topCode.addSubCode( code );

        code.addSubCode( code(  91, "Ź�ƽü�" ) );
        code.addSubCode( code(  92, "���ƽü�" ) );
        code.addSubCode( code(  93, "���ƽü�" ) );
        code.addSubCode( code(  94, "���̹�" ) );

        code = code(  100, "����" );

        topCode.addSubCode( code );

        code.addSubCode( code(  101, "��û" ) );
        code.addSubCode( code(  102, "���繫��" ) );
        code.addSubCode( code(  103, "��繫��" ) );
        code.addSubCode( code(  104, "������" ) );
        code.addSubCode( code(  105, "�����" ) );
        code.addSubCode( code(  106, "�ҹ漭" ) );
        code.addSubCode( code(  107, "��ü��" ) );
        code.addSubCode( code(  108, "������" ) );
        code.addSubCode( code(  109, "��ȭ��" ) );
        code.addSubCode( code(  110, "����û" ) );

        code = code(  0, "��Ÿ"  );

        topCode.addSubCode( code );

        code.addSubCode( code(  01, "����" ) );
        code.addSubCode( code(  02, "����Ʈ" ) );
        code.addSubCode( code(  03, "����" ) );
        code.addSubCode( code(  04, "�����" ) );
  }

}
