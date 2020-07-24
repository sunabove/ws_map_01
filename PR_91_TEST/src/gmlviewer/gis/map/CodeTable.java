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

    Code topCode = new Code( "전체보기", -1, -1 );

    makeCodeTable( topCode );


    return topCode;

  }

  public static Code code( int i , String name ) {
    return new Code( name, i/10, i%10 );
  }

  private void makeCodeTable( Code topCode ) {

        Code code;

        code = code( 10, "교육학원" );

        topCode.addSubCode( code );

        code.addSubCode( code( 11, "초등" ) );
        code.addSubCode( code( 12, "중등" ) );
        code.addSubCode( code( 13, "고등" ) );
        code.addSubCode( code( 14, "대학" ) );
        code.addSubCode( code( 15, "학원" ) );
        code.addSubCode( code(  16, "독서실" ) );
        code.addSubCode( code(  17, "체육관" ) );

        code = code(  20, "의료후생" ) ;

        topCode.addSubCode( code );

        code.addSubCode( code(  21, "대학병원" ) );
        code.addSubCode( code(  22, "일반병원" ) );
        code.addSubCode( code(  23, "보건소" ) );
        code.addSubCode( code(  24, "약국" ) );
        code.addSubCode( code(  25, "한의원" ) );
        code.addSubCode( code(  26, "한약방" ) );
        code.addSubCode( code(  27, "의원" ) );

        code = code(  30, "산업" );

        topCode.addSubCode( code );

        code.addSubCode( code(  31, "산업" ) );
        code.addSubCode( code(  32, "공장" ) );
        code.addSubCode( code(  33, "변전소" ) );

        code = code(  40, "숙박" ) ;

        topCode.addSubCode( code );

        code.addSubCode( code(  41, "호텔" ) );
        code.addSubCode( code(  42, "모텔" ) );
        code.addSubCode( code(  43, "여인숙" ) );
        code.addSubCode( code(  44, "민박" ) );
        code.addSubCode( code(  45, "목욕탕" ) );
        code.addSubCode( code(  46, "사우나" ) );

        code = code(  50, "금융" ) ;

        topCode.addSubCode( code );

        code.addSubCode( code(  51, "은행" ) );
        code.addSubCode( code(  52, "보험" ) );
        code.addSubCode( code(  53, "조합" ) );
        code.addSubCode( code(  54, "기타" ) );

        code = code(  60, "문화 종교"  );

        topCode.addSubCode( code );

        code.addSubCode( code(  61, "교회" ) );
        code.addSubCode( code(  62, "절" ) );
        code.addSubCode( code(  63, "원불교" ) );
        code.addSubCode( code(  64, "기도원" ) );
        code.addSubCode( code(  65, "문화체험" ) );
        code.addSubCode( code(  66, "박물관" ) );

        code = code(  70, "운수 창고" );

        topCode.addSubCode( code );

        code.addSubCode( code(  71, "역" ) );
        code.addSubCode( code(  72, "공항" ) );
        code.addSubCode( code(  73, "터미널" ) );
        code.addSubCode( code(  74, "버스정류장" ) );
        code.addSubCode( code(  75, "택시정류장" ) );
        code.addSubCode( code(  76, "카센타" ) );
        code.addSubCode( code(  77, "주차장" ) );
        code.addSubCode( code(  78, "주유소" ) );
        code.addSubCode( code(  79, "택배" ) );

        code = code(  80, "사회복지" );

        topCode.addSubCode( code );

        code.addSubCode( code(  81, "사회복지" ) );
        code.addSubCode( code(  82, "양로시설" ) );
        code.addSubCode( code(  83, "장애인시설" ) );

        code = code(  90, "아동복지" );

        topCode.addSubCode( code );

        code.addSubCode( code(  91, "탁아시설" ) );
        code.addSubCode( code(  92, "영아시설" ) );
        code.addSubCode( code(  93, "육아시설" ) );
        code.addSubCode( code(  94, "놀이방" ) );

        code = code(  100, "행정" );

        topCode.addSubCode( code );

        code.addSubCode( code(  101, "시청" ) );
        code.addSubCode( code(  102, "동사무소" ) );
        code.addSubCode( code(  103, "면사무소" ) );
        code.addSubCode( code(  104, "경찰서" ) );
        code.addSubCode( code(  105, "파출소" ) );
        code.addSubCode( code(  106, "소방서" ) );
        code.addSubCode( code(  107, "우체국" ) );
        code.addSubCode( code(  108, "세무서" ) );
        code.addSubCode( code(  109, "전화국" ) );
        code.addSubCode( code(  110, "병무청" ) );

        code = code(  0, "기타"  );

        topCode.addSubCode( code );

        code.addSubCode( code(  01, "주택" ) );
        code.addSubCode( code(  02, "아파트" ) );
        code.addSubCode( code(  03, "원룸" ) );
        code.addSubCode( code(  04, "기숙사" ) );
  }

}
