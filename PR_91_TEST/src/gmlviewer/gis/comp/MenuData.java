package gmlviewer.gis.comp;

public class MenuData {

  public MenuData(String menuText) {
     this( menuText, menuText.charAt( 0 ) );
  }

  public MenuData(String menuText, char mnemonic) {
     this.menuText = menuText;
     this.mnemonic = mnemonic;
  }

  public String menuText;
  public char mnemonic;

}
