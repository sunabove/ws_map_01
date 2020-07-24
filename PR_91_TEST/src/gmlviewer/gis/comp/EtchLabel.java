package gmlviewer.gis.comp;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import gmlviewer.gis.util.*;

/**
 * 에치 보더 레이블
 */

public class EtchLabel extends JLabel {

  /**
   * 생성자
   */
  public EtchLabel(String text) {

     super( text, JLabel.CENTER );

     // 보더 설정
     setBorder( BorderFactory.createEtchedBorder() );
     // 끝. 보더 설정

     // 폰트 설정
     setFont( FontManager.getDefaultFont() );
     // 끝. 폰트 설정

  }

}
