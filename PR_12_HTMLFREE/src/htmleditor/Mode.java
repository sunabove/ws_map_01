package htmleditor;

/**
 * Title:        HTML FREE EDITOR
 * Description:  Html Free Editor v2.0
 * Copyright:    Copyright (c) 2001
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

public class Mode {

  public static final int   EDIT = 0, RESHAPE = 1, ADD_WORD_BOX = 2,
			    ADD_TABLE = 3, ADD_SHAPE = 4;

  private static boolean FILE_OPEN;

  private static int mode = EDIT;

  public static void setFileOpen( boolean b ) {

    FILE_OPEN = b;

  }

  public static boolean isFileOpenning() {

    return FILE_OPEN;

  }

  final public static void setMode(final int m) {

    mode = m;

  }

  final public static int getMode() {

    return mode;

  }

  final public static boolean isMode(final int m) {

    return mode == m;

  }

}