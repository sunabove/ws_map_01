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
      COPY_RIGHT = "(C)2006 ������." ,
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

  // ���ø� �Ķ���� �̸���

  public static final String LAYER_VIEWER_APPLET_PARAM_NAME = "VIEWER";
  public static final String PROJECT_NAME_APPLET_PARAM_NAME = "PROJECT";
  public static final String LAYER_NUMBER_APPLET_PARAM_NAME = "MAP_NUMBER";
  public static final String LAYER_APPLET_PARAM_NAME = "MAP_";
  public static final String LAYER_INFO_DELIMETER = ";";

  // ��. ���ø� �Ķ���� �̸���

  // ����� �������̽� �ؽ�Ʈ �޽�����

  public static final String
      CANNOT_GO_FOREWARD_MORE_TEXT = "�� �̻� ������ �� �� �����ϴ�.",
      CANNOT_GO_BACKWARD_MORE_TEXT = "�� �̻� �ڷ� �� �� �����ϴ�.",
      SHAPE_LAYER_ADD_TEXT = "Shape ���̾� �߰�",
      BBD_LAYER_ADD_TEXT = "BBD ���̾� �߰�",
      LAYER_DEL_TEXT = "���̾� ����",
      ZOOM_IN_TEXT = "Ȯ��",
      ZOOM_OUT_TEXT = "���",
      SEL_EXTENT_TEXT = "���� ����",
      PAN_TEXT = "�̵�",
      PAN_DIR_TEXT = "8���� �̵�" ,
      FULL_EXTENT_TEXT = "��ü ����" ,
      GO_FORWARD_TEXT = "������" ,
      GO_BACKWARD_TEXT = "�ڷ�" ,
      CALC_DISTANCE_TEXT = "�Ÿ�" ,
      CALC_AREA_TEXT = "����" ,
      SHOW_ATTRIBUTE_TEXT = "POI " ,
      SET_ZOOM_LEVEL = "Ȯ�� ����" ,
      LAYER_LOADING_TEXT = "���̾ �ε��ϰ� �ֽ��ϴ�.";

  // ��. ����� ������֤ӽ� �ؽ�Ʈ �޽�����

  // ����� �������̽� �����ܵ�

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

  // ��. ����� �������̽� �����ܵ�

  // ������Ʈ �Ŵ��� �޴��� �ؽ�Ʈ��

  // ��. ������Ʈ �Ŵ��� �޴��� �ؽ�Ʈ��

  // �ε� �ΰ� ����ϱ� ���� ���� ������Ʈ ����( �ε� �ΰ�� �� ���� ���� �Ǿ�� �ϱ� ������)

  // �ε� �ΰ� �ּ� ��� ���� �ð�(���� �и�������)
  public static final long LOADING_LOGO_MIN_DURATION = 0;
  public static final Font LOADING_LOGO_TEXT_FONT = FontManager.getFont( "Times New Roman", Font.PLAIN, 22 );
  // �ε� �ؽ�Ʈ Į��
  public static final Color LOADING_TEXT_COLOR = Color.orange;
  // �ε� ��輱 Į��
  public static final Color LOADING_BORDER_COLOR = Color.black;
  // �ε� ��輱 ����
  public static final Insets LOADING_BORDER_INSETS = new Insets( 1, 1, 1, 1 );

  public static String getAppFullName() {

    return APP_NAME + " build #" + BUILD_NUM;

  }

  // ���̾� ä��� Į�� ����
  public static final String LAYER_AREA_COLOR_TOOLTIP = "Layer Area Color";
  // ���̾� ��輱 Į�� ����
  public static final String LAYER_BORDER_COLOR_TOOLTIP = "Layer Line Color";

  // ���α׷��ù� �� ũ��
  public static final Dimension PROGRESS_BAR_SIZE = new Dimension( 150, 25 );

}
