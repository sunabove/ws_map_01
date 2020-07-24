package jchartui;

/**
 * Title:        java chart project
 * Description:  jchart v1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.util.*;
import jcosmos.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EditorManager {

  private static Hashtable editors = new Hashtable();

  public static void show(Class klass, AWTEvent event ) {
      Editor editor = (Editor) editors.get( klass );

      if( editor == null ) {
          try {
             Utility.debug( EditorManager.class, "creating an instance ...");
             Object obj = klass.newInstance();
             editors.put( klass, obj );
             editor = (Editor) obj;
          } catch (IllegalAccessException e) {
             Utility.debug( e );
             return;
          } catch (InstantiationException e ) {
             Utility.debug( e );
             return;
          }
      }

      Component comp = (Component) event.getSource();

      JPopupMenu popUp = null ;

      while( popUp == null ) {
          if( comp instanceof JPopupMenu ) {
             popUp = (JPopupMenu) comp;
          } else {
             comp = comp.getParent();

             if( comp == null ) {
                break;
             }
          }
      }

      if( popUp != null && popUp.isVisible() ) {
         popUp.setVisible( false );
      }

      editor.show();

  }

}