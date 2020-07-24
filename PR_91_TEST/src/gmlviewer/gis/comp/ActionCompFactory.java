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
   * 스윙 버튼을 액션화 한다.
   */

  public Component createActionedComponent(JButton comp, String command, String resDir, String iconResource) {

     comp.setBorder( BorderFactory.createRaisedBevelBorder() );

     comp.setIcon( Resource.getResourceImageIcon( resDir, iconResource ) );

     comp.setToolTipText( comp.getText() );

     return createActionedComponent( comp, command );

  }

  /**
   * 스윙 버튼을 액션화 한다.
   */

  public Component createActionedComponent(JButton comp, String command ) {

    // 버튼에 액션 리스너를 지정

    comp.setFont( FontManager.getDefaultFont() );

    comp.addActionListener( this );

    // 끝. 버튼 액션 리스너 지정

    // 컴포넌트 명령어 저장
    storeComponentCommand( comp, command );
    // 끝. 컴포넌트 명령어 저장

    return comp;

  }

  public Component createActionedComponent(JComboBox comp, String command) {

    // 체크 박스에 아이텀 리스너 지정

    comp.addItemListener( this );

    // 끝. 체크 박스 리스너 지정

    // 컴포넌트 명령어 저장
    storeComponentCommand( comp, command );
    // 끝. 컴포넌트 명령어 저장

    return comp;

  }

  /**
   * 컴포넌트의 명령어를 해쉬테이블에 저장한다.
   * 저장시에 정수형을 래핑하여 저장한다.
   */

  private void storeComponentCommand(Component comp, String command ) {

    // 지정된 명령어를 래핑하여 명령어 리스트에 저장한다.
    // 다시 꺼낼 때는 다시 정수형으로 변환해야 한다.

    commandList.put( comp, command );

    // 끝. 명령어 리스트에 저장하기

  }

  /**
   * 컴포넌트 액션이 발생하면 이 함수가 실행된다.
   */
  public void actionPerformed(ActionEvent e) {

    String command = getCommand( e.getSource() );

    listener.actionPerformed( command, e );

  }

  /**
   * 아이텀 이벤트가 발생하면 이 함수가 실행된다.
   */

   public void itemStateChanged(ItemEvent e) {

    String command = getCommand( e.getSource() );

    listener.actionPerformed( command, e );

  }


  // member
  private ActListener listener;

  // 명령어 해쉬테이블. 키는 컴포넌트
  private static Hashtable commandList = new Hashtable();

  private static String getCommand(Object comp) {

    return  (String) ( commandList.get( comp ) );

  }

}
