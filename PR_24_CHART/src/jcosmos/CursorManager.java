
/**
 * Title:        Escort Pro<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Juney<p>
 * Company:      Jcosmos<p>
 * @author Juney
 * @version 1.0
 */
package jcosmos;

import java.awt.*;

public class CursorManager {
  public static final int hand = Cursor.HAND_CURSOR;
  public static final int move = Cursor.MOVE_CURSOR;
  public static final int cross = Cursor.CROSSHAIR_CURSOR;
  public static final int custom = Cursor.CUSTOM_CURSOR;
  public static final int def = Cursor.DEFAULT_CURSOR;
  public static final int text = Cursor.TEXT_CURSOR;
  public static final int wait = Cursor.WAIT_CURSOR;

  public static final int north = Cursor.N_RESIZE_CURSOR;
  public static final int south = Cursor.S_RESIZE_CURSOR;
  public static final int east = Cursor.E_RESIZE_CURSOR;
  public static final int west = Cursor.W_RESIZE_CURSOR;

  public static final int ne = Cursor.NE_RESIZE_CURSOR;
  public static final int nw = Cursor.NW_RESIZE_CURSOR;
  public static final int se = Cursor.SE_RESIZE_CURSOR;
  public static final int sw = Cursor.SW_RESIZE_CURSOR;

  public static void setCursor(Component component, int type ) {
      component.setCursor( Cursor.getPredefinedCursor( type ) );
  }

  public static void setResizableCursor(Component component, int topology, int def) {
      int type = component.getCursor().getType();

      switch( topology ) {
        case -1:
           type = def;
           break;
        case 0:
           type = nw;
           break;
        case 1:
            type = north;
            break;
        case 2:
            type = ne;
            break;
        case 3:
            type = west;
            break;
        case 4:
            type = east;
            break;
        case 5:
            type = sw;
            break;
        case 6:
            type = south;
            break;
        case 7:
            type = se;
            break;
        case 8:
            type = def;
            break;
      }

      setCursor( component, type );
  }

}