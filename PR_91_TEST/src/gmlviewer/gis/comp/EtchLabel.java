package gmlviewer.gis.comp;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import gmlviewer.gis.util.*;

/**
 * ��ġ ���� ���̺�
 */

public class EtchLabel extends JLabel {

  /**
   * ������
   */
  public EtchLabel(String text) {

     super( text, JLabel.CENTER );

     // ���� ����
     setBorder( BorderFactory.createEtchedBorder() );
     // ��. ���� ����

     // ��Ʈ ����
     setFont( FontManager.getDefaultFont() );
     // ��. ��Ʈ ����

  }

}
