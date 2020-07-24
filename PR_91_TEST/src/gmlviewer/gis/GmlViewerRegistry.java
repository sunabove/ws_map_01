package gmlviewer.gis;

import java.awt.*;
import java.util.*;
import java.io.*;
import gmlviewer.gis.model.*;
import gmlviewer.gis.comp.*;
import gmlviewer.gis.util.*;
import gmlviewer.gis.map.Code;

public class GmlViewerRegistry {

  public static Code CURR_CODE ;

  public static String H_BUILD_FG_COLOR = "#A0D7FF";

  public static boolean VIEWER_CONTROLLER_AUTO_PACK = false;

  public static boolean MOBILCOM_MBR_RECALCULATE =  false ;

  public static boolean SHOW_MAP_BOUNDARY = false;

  public static boolean SHOW_CENTER_LINE = false;

  public static boolean SHOW_TEXT_LAYER = true;

  public static boolean SHOW_GLOBAL_BOUNDARY = false;

  public static boolean MOBILCOM_READER_DEBUG = false;

  public static boolean SHOW_BG_RASTER_LAYER = true;

  public static final MBR DEFAULT_GLOBAL_MBR = new MBR( 0, 0, 1, 1 );

  public static final String
      APP_NAME = "GML Viewer POI v1.0" ,
      BUILD_NUM = "11" ,
      COPY_RIGHT = "(C)2006 이진석." ,
      ALL_RIGHTS_RESERVED = "All Rights Reserved." ,
      //SUPPORT_E_MAIL = "sbmoon@multics.co.kr" ,
      GEN_RES_DIR = "gen" ,
      SHAPE_STYLE_RES_DIR = "shape" ,
      GIS_RES_DIR = "gis" ,
      LOGO_FILE = "logo.gif" ,
      LOADING_LOGO_FILE = "loading.jpg" ,
      WINDOW_ICON = "window_icon.gif";

  public static int FRAME_WIDTH = 700;
  public static int FRAME_HEIGHT = 650;

  // 애플릿 파라미터 이름들

  public static final String LAYER_VIEWER_APPLET_PARAM_NAME = "VIEWER";
  public static final String PROJECT_NAME_APPLET_PARAM_NAME = "PROJECT";
  public static final String LAYER_NUMBER_APPLET_PARAM_NAME = "MAP_NUMBER";
  public static final String LAYER_APPLET_PARAM_NAME = "MAP_";
  public static final String LAYER_INFO_DELIMETER = ";";

  // 끝. 애플릿 파라미터 이름들

  // 사용자 인터페이스 텍스트 메시지들

  public static final String
      CANNOT_GO_FOREWARD_MORE_TEXT = "더 이상 앞으로 갈 수 없습니다.",
      CANNOT_GO_BACKWARD_MORE_TEXT = "더 이상 뒤로 갈 수 없습니다.",
      SHAPE_LAYER_ADD_TEXT = "Shape 레이어 추가",
      BBD_LAYER_ADD_TEXT = "BBD 레이어 추가",
      LAYER_DEL_TEXT = "레이어 삭제",
      ZOOM_IN_TEXT = "확대",
      ZOOM_OUT_TEXT = "축소",
      SEL_EXTENT_TEXT = "영역 선택",
      PAN_TEXT = "이동",
      PAN_DIR_TEXT = "8방향 이동" ,
      FULL_EXTENT_TEXT = "전체 보기" ,
      GO_FORWARD_TEXT = "앞으로" ,
      GO_BACKWARD_TEXT = "뒤로" ,
      CALC_DISTANCE_TEXT = "거리" ,
      CALC_AREA_TEXT = "면적" ,
      SHOW_ATTRIBUTE_TEXT = "POI " ,
      SET_ZOOM_LEVEL = "확대 레벨" ,
      LAYER_LOADING_TEXT = "레이어를 로딩하고 있습니다.";

  // 끝. 사용자 인터페애ㅣ스 텍스트 메시지들

  // 사용자 인터페이스 아이콘들

  public static final String
      ZOOM_IN_ICON_FILE_NAME = "zoom_in.gif" ,
      ZOOM_OUT_ICON_FILE_NAME = "zoom_out.gif" ,
      CALC_AREA_ICON_FILE_NAME = "calc_area.gif" ,
      CALC_DISTANCE_ICON_FILE_NAME = "calc_distance.gif" ,
      PAN_EIGHT_DIR_ICON_FILE_NAME = "pan_eight_dir.gif" ,
      FULL_EXTENT_ICON_FILE_NAME = "full_extent.gif" ,
      FORWARD_HISTORY_ICON_FILE_NAME = "forward_history.gif" ,
      BACKWARD_HISTORY_ICON_FILE_NAME = "backward_history.gif" ,
      PAN_ICON_FILE_NAME = "pan.gif" ,
      SELECT_EXTENT_ICON_FILE_NAME = "select_extent.gif" ,
      ATTRIBUTE_ICON_FILE_NAME = "attribute.gif";

  // 끝. 사용자 인터페이스 아이콘들

  // 프로젝트 매니저 메뉴바 텍스트들

  // 끝. 프로젝트 매니저 메뉴바 텍스트들

  // 로딩 로고를 출력하기 위한 메인 콤포넌트 설정( 로딩 로고는 한 번만 실행 되어야 하기 때문에)

  // 로딩 로고 최소 출력 유지 시간(단위 밀리세컨드)
  public static final long LOADING_LOGO_MIN_DURATION = 0;
  public static final Font LOADING_LOGO_TEXT_FONT = FontManager.getFont( "Times New Roman", Font.PLAIN, 22 );
  // 로딩 텍스트 칼라
  public static final Color LOADING_TEXT_COLOR = Color.orange;
  // 로딩 경계선 칼라
  public static final Color LOADING_BORDER_COLOR = Color.black;
  // 로딩 경계선 마진
  public static final Insets LOADING_BORDER_INSETS = new Insets( 1, 1, 1, 1 );

  public static String getAppFullName() {

    return APP_NAME + " build #" + BUILD_NUM;

  }

  // 레이어 채우기 칼라 툴팁
  public static final String LAYER_AREA_COLOR_TOOLTIP = "Layer Area Color";
  // 레이어 경계선 칼라 툴팁
  public static final String LAYER_BORDER_COLOR_TOOLTIP = "Layer Line Color";

  // 프로그레시바 바 크기
  public static final Dimension PROGRESS_BAR_SIZE = new Dimension( 150, 25 );

}
