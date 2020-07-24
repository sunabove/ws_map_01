package gmlviewer.gis.comp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import gmlviewer.gis.rsc.*;
import gmlviewer.gis.util.*;

public class ActionCompFactory implements ActionListener, ItemListener {

  public ActionCompFactory( ActListener listener ) {

    this.listener = listener;

  }

  /**
   * ���� ��ư�� �׼�ȭ �Ѵ�.
   */

  public Component createActionedComponent(JButton comp, String command, String resDir, String iconResource) {

     comp.setBorder( BorderFactory.createRaisedBevelBorder() );

     comp.setIcon( Resource.getResourceImageIcon( resDir, iconResource ) );

     comp.setToolTipText( comp.getText() );

     return createActionedComponent( comp, command );

  }

  /**
   * ���� ��ư�� �׼�ȭ �Ѵ�.
   */

  public Component createActionedComponent(JButton comp, String command ) {

    // ��ư�� �׼� �����ʸ� ����

    comp.setFont( FontManager.getDefaultFont() );

    comp.addActionListener( this );

    // ��. ��ư �׼� ������ ����

    // ������Ʈ ��ɾ� ����
    storeComponentCommand( comp, command );
    // ��. ������Ʈ ��ɾ� ����

    return comp;

  }

  public Component createActionedComponent(JComboBox comp, String command) {

    // üũ �ڽ��� ������ ������ ����

    comp.addItemListener( this );

    // ��. üũ �ڽ� ������ ����

    // ������Ʈ ��ɾ� ����
    storeComponentCommand( comp, command );
    // ��. ������Ʈ ��ɾ� ����

    return comp;

  }

  /**
   * ������Ʈ�� ��ɾ �ؽ����̺� �����Ѵ�.
   * ����ÿ� �������� �����Ͽ� �����Ѵ�.
   */

  private void storeComponentCommand(Component comp, String command ) {

    // ������ ��ɾ �����Ͽ� ��ɾ� ����Ʈ�� �����Ѵ�.
    // �ٽ� ���� ���� �ٽ� ���������� ��ȯ�ؾ� �Ѵ�.

    commandList.put( comp, command );

    // ��. ��ɾ� ����Ʈ�� �����ϱ�

  }

  /**
   * ������Ʈ �׼��� �߻��ϸ� �� �Լ��� ����ȴ�.
   */
  public void actionPerformed(ActionEvent e) {

    String command = getCommand( e.getSource() );

    listener.actionPerformed( command, e );

  }

  /**
   * ������ �̺�Ʈ�� �߻��ϸ� �� �Լ��� ����ȴ�.
   */

   public void itemStateChanged(ItemEvent e) {

    String command = getCommand( e.getSource() );

    listener.actionPerformed( command, e );

  }


  // member
  private ActListener listener;

  // ��ɾ� �ؽ����̺�. Ű�� ������Ʈ
  private static Hashtable commandList = new Hashtable();

  private static String getCommand(Object comp) {

    return  (String) ( commandList.get( comp ) );

  }

}
