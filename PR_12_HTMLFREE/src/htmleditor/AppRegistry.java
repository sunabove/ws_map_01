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

    public static final String APP_NAME = "���� �۾��� v2.0";

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

    public static final int IMAGE_HTML_VERICAL_CALIBRATION = 6; // �̹��� HTML ����� ���� ��ġ ���� ��, �ſ� �߿��� �����̴�.
    public static final boolean DEFAULT_CRYTPTION_OPTION = false;

    public static final File TEMP_DIR = new File( "C:" + FOLDER_DELIMITER + "TEMP" );

    public static final double MINIMUN_DOCUMENT_WIDTH = 70;
    public static final double MINIMUN_DOCUMENT_HEIGHT = 25;
    public static final int DEFAULT_DOCUMENT_BORDER_WIDTH = 1; // ��ť��Ʈ ���� �� �⺻ ��

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

    public static final String HTML_FREE_INIT_MSG = APP_NAME + "�� �ʱ�ȭ���Դϴ�.";
    public static final String CONNECTING_SERVER_MSG = "���� ���� ���Դϴ�.";
    public static final String UNZIPPING_HTML_FILE_MSG = "����� HTML ������ Ǯ�� �ֽ��ϴ�.";
    public static final String HTML_FILE_DOWLOAD_MSG = "HTML ������ �ٿ�ε� ���Դϴ�.";
    public static final String HTML_FILE_READING_MSG = "HTML ������ ���� ���Դϴ�.";
    public static final String HTML_FILE_OPEN_DONE_MSG = "HTML ������ �����Ͻ� �� �ֽ��ϴ�.";
    public static final String NEW_DOCUMENT_MSG = "������ ������� �ʾҽ��ϴ�. �� ������ �ۼ��Ͻðڽ��ϱ�?";
    public static final String BOARD_REGISTER_OK_MSG = "�Խ��� ��� ����";
    public static final String REFRESH_WEB_BROWSER_1_MSG = "��� ���� ��ư�� ������";
    public static final String REFRESH_WEB_BROWSER_2_MSG = "�Խù��� Ȯ���ϼ���.";
    public static final String BOARD_REGISTER_FAIL_MSG = "�Խ��� ��� ����";
    public static final String WRONG_URL_MSG = "�߸��� URL �Դϴ�.";
    public static final String SERVER_COMM_FAIL_MSG = "��� �����Դϴ�.";
    public static final String CANNOT_FIND_CASH_FILE_MSG = "ĳ�� ������ ã�� �� �����ϴ�.";
    public static final String CANNOT_READ_CASH_FILE_MSG = "ĳ�� ������ ���� �� �����ϴ�.";
    public static final String CANNOT_READ_HTML_FILE_FROM_SVR_MSG = "�����κ��� HTML ������ ���� �� �����ϴ�.";
    public static final String READING_ARGUMENT_FILE_MSG = "���� ������ �а� �ֽ��ϴ�.";
    public static final String PROCESSING_ARGUMENT_INFO_MSG = "���� ���ϸ� �м����Դϴ�.";
    public static final String RECEIVING_ENVIRONMENT_INFO_FROM_SVR_MSG = "ȯ�� ���� ������ �ް� �ֽ��ϴ�.";
    public static final String INITIALIZING_USER_INTERFACE = "����� ȯ���� �ʱ�ȭ ���Դϴ�.";
    public static final String INITIALIZING_EDITOR_INTERFACE = "�����͸� �ʱ�ȭ ���Դϴ�.";
    public static final String CANCEL_EDITING_FORCED_MSG = "�� ���⸦ ����ϰ� �����Ͻðڽ��ϱ�?.";
    public static final String INIT_APP_ERROR_MESSAGE = "�ʱ� ���� ȹ�� ����";

    public static String HTML_BODY_ADDITIONAL_TAG =
	    "onSelectstart=\"return false\" onContextmenu=\"return false\"";


    // ��ť��Ʈ ������ ���� Ű�� ĥ �� ���ְ� ���̵��� �ϱ� ���ؼ�...
    // ��ť��Ʈ�� ���̿� �߰��� ������ �־���� ��ť��Ʈ�� �ϴ� ����.
    public static final int DOCUMENT_ADDITIONAL_BOTTOM_INSET = 3;

    // �� ���� ���μ��� ���� ����

    public static final boolean TAB_KEY_PROCESSING = false;

    public static String RSC_BASE_DIR_URL;

    public static final int MIN_GLYPH_WIDTH = 10; // �ּ� �۸� ũ��, �ּ� ��ť��Ʈ �� �����ÿ� ����Ѵ�.

    public static boolean FORCED_NEW_DOCUMENT = false; // �� ���� �ۼ��� ���� ���� ���� ���� ���� �� ���� �� ���� ����

    public static final File CAPTURE_DIR = new File( TEMP_DIR, "CAP" );

    public static final File VOICE_RECORD_DIR = new File( TEMP_DIR, "VOICE" );

    public static final String SCREEN_CAPTURE_COMMAND = "C:\\Program Files\\htmlfree\\cap.exe";

    public static final String VOICE_RECORD_COMMAND = "C:\\Program Files\\htmlfree\\voice.exe";

    public static final String SCREEN_CAPUTER_FILE_NAME = "cap.jpg";

    public static final String VOICE_RECORD_FILE_NAME = "voice.mp3";

    // �޴� ��ư ���ü�

    public static boolean OPEN = true, NEW = true, SAVE = true, TABLE = true, WORD_BOX = true,
			  IMAGE = true, CHART = true, MULTI_MEDIA = true, LINK_IMAGE = true,
			  CAPTURE = true, RECORD = true, SHAPES = true, HELP = true;

    // ��. �޴� ��ư ���ü�

    // ���ø����̼� Ÿ�� ����

//    public static final String EDITOR_APP_TYPE = "EDITOR"; // ������ ���ø����̼�

    public static final String BOARD_APP_TYPE = "BOARD";   // �Խ��� ���ø����̼�

    // ��. ���ø����̼� Ÿ�� ����

    // �ֿ� ��Ʈ ����

    // ������ �˾� �޴� ��Ʈ
    public static final Font EDITOR_POPUP_MENU_FONT = FontManager.getFont( "Serif", Font.PLAIN, 14 );

    // ���� ���� �г� ��Ʈ
    public static final Font BOARD_NORTH_PANE_FONT = FontManager.getFont( "Arial", Font.PLAIN, 13 );

    // ���� ���� �г� ��Ʈ
    public static final Font BOARD_SOUTH_PANE_FONT = FontManager.getFont( "Arial", Font.PLAIN, 12 );

    // �ɼ� ���� ��Ʈ

    public static final Font OPTIONPANE_FONT = FontManager.getFont( "Arial", Font.PLAIN, 13 );

    // �Խ��� ��� ���� �޴�

    public static final Font BOARD_REGI_SUCCESS_PANE_FONT = FontManager.getFont( "Arial", Font.PLAIN, 13 );

    // ��. ��ǥ ��Ʈ ����

    // ���� ��� �ƱԸ�Ʈ ����

    public static final String STANDARD_BOARD_MODE_TEXT = "STANDARD";
    public static final String MODIFY_BOARD_MODE_TEXT = "MODIFY";
    public static final String REPLY_BOARD_MODE_TEXT = "REPLY";

    // ��. ���� ��� �ƱԸ�Ʈ ����

    // ���� ���ø����̼� ���� ����

    public static final String BOARD_APP_VERSION = "099";

    // ��. ���� ���ø����̼� ���� ����

    // ���� ����Ʈ ����( ���ø����̼� �� ���� �ǹ� ���� )

    public static String HELP_SITE;

    // ��. ���� ����Ʈ ����

    // ���� ���ø����̼��� �ƱԸ�Ʈ���� �ѱ�� ����

    public static final File BOARD_ARGS_FILE = new File( "c:\\temp\\htmlfree_init.sock" );

    // �ּ� �ΰ� ����Ʈ �ð�

    public static final long MIN_LOGO_SHOW_TIME = 2500;

    // ���� �ڵ� ã�� ������ ũ��

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
