package gmlviewer.gis.comp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MenuFactory {

  public static JMenu createJMenu(MenuData data) {
     return createJMenu( data.menuText, data.mnemonic );
  }

  private static JMenu createJMenu(String text, char mnemonic) {
      JMenu menu = new JMenu( text );
      menu.setMnemonic( mnemonic );
      return menu;
  }

}
