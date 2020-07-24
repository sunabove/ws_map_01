package htmleditor;

/**
 * Title:        Test Publishing System
 * Description:  Internet Based Test Publishing System V1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import jcosmos.*;
import java.io.*;

public class AppRegistry {

    public static final String APP_NAME = "매직 글쓰기 v2.0";

//    public static final String APP_NAME = "Html Free Editor v2.0 beta 3 build 6970";

    public static boolean DEBUG_FLAG = true;

    public static boolean EDITOR_GRAPHIC_DEBUG_FLAG = false;

    public static final Color HTML_LINK_COLOR = Color.blue;
    public static final boolean NEW_WINDOW_HTML_LINK = true;
    public static final String DEFAULT_URL = "http://www.";
    public static final String DEFAULT_LINK_TARGET = "_hfnw";

    public static final int FONT_SIZE_FACTOR = 64;

    public static final String HTML_FREE_EDITOR_TITLE = "Document created by Html Free Editor v2.0";

    public static final int MINIMUM_IMAGE_WIDTH = 10;
    public static final int MINIMUM_IMAGE_HEIGHT = 10;

    public static Insets EDITOR_MARGIN = new Insets( 30, 30, 30, 30 );

    public static final Color DEFAULT_BORDER_LINE_COLOR = Color.black;
    public static final String FOLDER_DELIMITER = "\\";

    public static final int IMAGE_HTML_VERICAL_CALIBRATION = 6; // 이미지 HTML 저장시 수직 위치 보정 값, 매우 중요한 팩터이다.
    public static final boolean DEFAULT_CRYTPTION_OPTION = false;

    public static final File TEMP_DIR = new File( "C:" + FOLDER_DELIMITER + "TEMP" );

    public static final double MINIMUN_DOCUMENT_WIDTH = 70;
    public static final double MINIMUN_DOCUMENT_HEIGHT = 25;
    public static final int DEFAULT_DOCUMENT_BORDER_WIDTH = 1; // 도큐먼트 보더 폭 기본 값

    public static HtmlFreeEditorApplet HTML_APPLET;
    public static Class MAIN_APP_CLASS;

    public static JFrame MAIN_APP_JFRAME;

    public static boolean APPLET_MODE = false;

    public static final Image WINDOW_ICON_IMAGE = Utility.getResourceImage( "insert_image.gif" );
    public static final Image NEW_LINE_IMG = Utility.getResourceImage( "newline.gif" );
    public static final String LOADING_MESSAGE = "Loading Now, Please Wait!";
    public static final Color LOADING_MESSAGE_COLOR = Color.black;

    public static final Image STARTUP_LOGO_IMAGE = Utility.getResourceImage( "startup_logo.jpg" );
    public static final Dimension LOGO_MESSAGE_TEXT_SIZE = new Dimension( 350, 30 );
    public static final Font LOGO_MESSAGE_FONT = FontManager.getFont( "Arial", Font.PLAIN, 12 );

    public static final String TAB_STRING = "     ";

    public static final String HTML_FREE_ZIP_FILE_EXTENSION = "hfz"; // html free zip file extension
//    public static String CHARSET = "UNICODE-1-1-UTF-8"; // HTML DOCUMENT CHARSET
    public static String CHARSET = "KSC5601"; // HTML DOCUMENT CHARSET
//    public static String CHARSET = "utf-8"; // HTML DOCUMENT CHARSET

    public static final String TEMP_HTML_FILE_NAME = "index.html";
    public static String DEFAULT_FONT_NAME = "Arial";
    public static int DEFAULT_FONT_SIZE = 12;
    public static int DEFAULT_TABLE_CELL_FONT_SIZE = 12;

    public static final String HTML_FREE_INIT_MSG = APP_NAME + "을 초기화중입니다.";
    public static final String CONNECTING_SERVER_MSG = "서버 접속 중입니다.";
    public static final String UNZIPPING_HTML_FILE_MSG = "압축된 HTML 파일을 풀고 있습니다.";
    public static final String HTML_FILE_DOWLOAD_MSG = "HTML 파일을 다운로드 중입니다.";
    public static final String HTML_FILE_READING_MSG = "HTML 파일을 여는 중입니다.";
    public static final String HTML_FILE_OPEN_DONE_MSG = "HTML 파일을 편집하실 수 있습니다.";
    public static final String NEW_DOCUMENT_MSG = "파일이 저장되지 않았습니다. 새 문서를 작성하시겠습니까?";
    public static final String BOARD_REGISTER_OK_MSG = "게시판 등록 성공";
    public static final String REFRESH_WEB_BROWSER_1_MSG = "목록 보기 버튼을 눌러서";
    public static final String REFRESH_WEB_BROWSER_2_MSG = "게시물을 확인하세요.";
    public static final String BOARD_REGISTER_FAIL_MSG = "게시판 등록 실패";
    public static final String WRONG_URL_MSG = "잘못된 URL 입니다.";
    public static final String SERVER_COMM_FAIL_MSG = "통신 에러입니다.";
    public static final String CANNOT_FIND_CASH_FILE_MSG = "캐시 파일을 찾을 수 없읍니다.";
    public static final String CANNOT_READ_CASH_FILE_MSG = "캐시 파일을 읽을 수 없읍니다.";
    public static final String CANNOT_READ_HTML_FILE_FROM_SVR_MSG = "서버로부터 HTML 파일을 읽을 수 없습니다.";
    public static final String READING_ARGUMENT_FILE_MSG = "정보 파일을 읽고 있습니다.";
    public static final String PROCESSING_ARGUMENT_INFO_MSG = "정보 파일를 분석중입니다.";
    public static final String RECEIVING_ENVIRONMENT_INFO_FROM_SVR_MSG = "환경 변수 정보를 받고 있습니다.";
    public static final String INITIALIZING_USER_INTERFACE = "사용자 환경을 초기화 중입니다.";
    public static final String INITIALIZING_EDITOR_INTERFACE = "에디터를 초기화 중입니다.";
    public static final String CANCEL_EDITING_FORCED_MSG = "글 쓰기를 취소하고 종료하시겠습니까?.";
    public static final String INIT_APP_ERROR_MESSAGE = "초기 정보 획득 실패";

    public static String HTML_BODY_ADDITIONAL_TAG =
	    "onSelectstart=\"return false\" onContextmenu=\"return false\"";


    // 도큐먼트 끝에서 엔터 키를 칠 때 멋있게 보이도록 하기 위해서...
    // 도큐먼트의 높이에 추가로 보정해 주어야할 도큐먼트의 하단 마진.
    public static final int DOCUMENT_ADDITIONAL_BOTTOM_INSET = 3;

    // 탭 문자 프로세싱 여부 설정

    public static final boolean TAB_KEY_PROCESSING = false;

    public static String RSC_BASE_DIR_URL;

    public static final int MIN_GLYPH_WIDTH = 10; // 최소 글립 크기, 최소 도큐먼트 폭 설정시에 사용한다.

    public static boolean FORCED_NEW_DOCUMENT = false; // 새 문서 작성시 기존 파일 저장 여부 물어 볼 것인 지 여부 설정

    public static final File CAPTURE_DIR = new File( TEMP_DIR, "CAP" );

    public static final File VOICE_RECORD_DIR = new File( TEMP_DIR, "VOICE" );

    public static final String SCREEN_CAPTURE_COMMAND = "C:\\Program Files\\htmlfree\\cap.exe";

    public static final String VOICE_RECORD_COMMAND = "C:\\Program Files\\htmlfree\\voice.exe";

    public static final String SCREEN_CAPUTER_FILE_NAME = "cap.jpg";

    public static final String VOICE_RECORD_FILE_NAME = "voice.mp3";

    // 메뉴 버튼 가시성

    public static boolean OPEN = true, NEW = true, SAVE = true, TABLE = true, WORD_BOX = true,
			  IMAGE = true, CHART = true, MULTI_MEDIA = true, LINK_IMAGE = true,
			  CAPTURE = true, RECORD = true, SHAPES = true, HELP = true;

    // 끝. 메뉴 버튼 가시성

    // 어플리케이션 타입 변수

//    public static final String EDITOR_APP_TYPE = "EDITOR"; // 에디터 어플리케이션

    public static final String BOARD_APP_TYPE = "BOARD";   // 게시판 어플리케이션

    // 끝. 어플리케이션 타입 변수

    // 주요 폰트 설정

    // 에디터 팝업 메뉴 폰트
    public static final Font EDITOR_POPUP_MENU_FONT = FontManager.getFont( "Serif", Font.PLAIN, 14 );

    // 보드 북쪽 패널 폰트
    public static final Font BOARD_NORTH_PANE_FONT = FontManager.getFont( "Arial", Font.PLAIN, 13 );

    // 보드 남쪽 패널 폰트
    public static final Font BOARD_SOUTH_PANE_FONT = FontManager.getFont( "Arial", Font.PLAIN, 12 );

    // 옵션 페인 폰트

    public static final Font OPTIONPANE_FONT = FontManager.getFont( "Arial", Font.PLAIN, 13 );

    // 게시판 등록 성공 메뉴

    public static final Font BOARD_REGI_SUCCESS_PANE_FONT = FontManager.getFont( "Arial", Font.PLAIN, 13 );

    // 끝. 주표 폰트 설정

    // 보드 모드 아규먼트 설정

    public static final String STANDARD_BOARD_MODE_TEXT = "STANDARD";
    public static final String MODIFY_BOARD_MODE_TEXT = "MODIFY";
    public static final String REPLY_BOARD_MODE_TEXT = "REPLY";

    // 끝. 보드 모드 아규먼트 설정

    // 보드 어플리케이션 버전 설정

    public static final String BOARD_APP_VERSION = "099";

    // 끝. 보드 어플리케이션 버전 설정

    // 헬프 사이트 설정( 어플리케이션 일 때만 의미 있음 )

    public static String HELP_SITE;

    // 끝. 헬프 사이트 설정

    // 보드 어플리케이션의 아규먼트들을 넘기는 파일

    public static final File BOARD_ARGS_FILE = new File( "c:\\temp\\htmlfree_init.sock" );

    // 최소 로고 페인트 시간

    public static final long MIN_LOGO_SHOW_TIME = 2500;

    // 종목 코드 찾기 윈도우 크기

    public static final Dimension STOCK_CODE_FIND_WINDOW_SIZE = new Dimension( 400, 300 );

    public static final Font STOCK_CODE_FIND_WINDOW_BIG_FONT = FontManager.getFont( "Arial", Font.PLAIN, 15 );
    public static final Font STOCK_CODE_FIND_WINDOW_SMALL_FONT = FontManager.getFont( "Arial", Font.PLAIN, 12 );

    public static final Font CHART_DEFAULT_FONT = FontManager.getFont( "Arial", Font.PLAIN, 12 );

    public static void initUiManager() {

      UIManager.put( "Button.font", AppRegistry.OPTIONPANE_FONT );
      UIManager.put( "Label.font", AppRegistry.OPTIONPANE_FONT );

    }

    public static void systemExit( int i ) {

      removeTempFiles();

      System.exit( i );

    }

    public static void removeTempFiles() {

      final File boardArgsFile = BOARD_ARGS_FILE;

      if( boardArgsFile.exists() ) {

	boardArgsFile.delete();

      }

    }

    static {

      initUiManager();

    }

}
