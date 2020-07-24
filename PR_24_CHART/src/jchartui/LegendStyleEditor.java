package jchartui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class LegendStyleEditor extends Editor {
  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel jPanel24 = new JPanel();
  JPanel jPanel23 = new JPanel();
  JPanel jPanel15 = new JPanel();
  BorderLayout borderLayout20 = new BorderLayout();
  JPanel jPanel8 = new JPanel();
  BorderLayout borderLayout18 = new BorderLayout();
  BorderLayout borderLayout17 = new BorderLayout();
  JButton jButton2 = new JButton();
  JButton jButton1 = new JButton();
  JPanel jPanel4 = new JPanel();
  BorderLayout borderLayout19 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  Border border1;
  Border border2;
  TitledBorder titledBorder1;
  GridLayout gridLayout5 = new GridLayout();
  JRadioButton jRadioButton16 = new JRadioButton();
  JRadioButton jRadioButton17 = new JRadioButton();
  JRadioButton jRadioButton18 = new JRadioButton();
  JRadioButton jRadioButton19 = new JRadioButton();
  JRadioButton jRadioButton20 = new JRadioButton();
  Border border3;
  Border border4;
  Border border5;

  //Construct the frame
  public LegendStyleEditor() {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  //Component initialization
  private void jbInit() throws Exception  {
    contentPane = (JPanel) this.getContentPane();
    border1 = BorderFactory.createEmptyBorder(0,2,153,0);
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134));
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"배치");
    border3 = BorderFactory.createCompoundBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"배치"),BorderFactory.createEmptyBorder(0,5,0,0));
    border4 = BorderFactory.createEmptyBorder(5,5,5,8);
    border5 = BorderFactory.createEmptyBorder(7,0,0,0);
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(419, 365));
    this.setTitle("범례 서식");
    jPanel24.setLayout(borderLayout20);
    jPanel23.setMinimumSize(new Dimension(50, 10));
    jPanel23.setPreferredSize(new Dimension(80, 0));
    jPanel23.setLayout(borderLayout18);
    jPanel15.setPreferredSize(new Dimension(160, 0));
    jPanel15.setLayout(borderLayout17);
    jPanel8.setLayout(borderLayout4);
    jPanel8.setBorder(border5);
    jPanel8.setPreferredSize(new Dimension(0, 28));
    borderLayout17.setHgap(5);
    jButton2.setText("취소");
    jButton1.setText("확인");
    jPanel4.setLayout(borderLayout19);
    jPanel2.setBorder(border3);
    jPanel2.setPreferredSize(new Dimension(106, 10));
    jPanel2.setLayout(gridLayout5);
    jPanel4.setBorder(border1);
    gridLayout5.setRows(5);
    jRadioButton16.setMnemonic('B');
    jRadioButton16.setText("아래쪽(B)");
    jRadioButton17.setMnemonic('C');
    jRadioButton17.setText("모서리(C)");
    jRadioButton18.setMnemonic('T');
    jRadioButton18.setText("위쪽(T)");
    jRadioButton19.setMnemonic('R');
    jRadioButton19.setText("오른쪽(R)");
    jRadioButton20.setMnemonic('L');
    jRadioButton20.setText("왼쪽(L)");
    contentPane.setBorder(border4);
    jTabbedPane1.setRequestFocusEnabled(false);
    contentPane.add(jTabbedPane1, BorderLayout.CENTER);
    jTabbedPane1.add(jPanel4, "   배치   ");
    jPanel4.add(jPanel2, BorderLayout.WEST);
    jPanel2.add(jRadioButton16, null);
    jPanel2.add(jRadioButton17, null);
    jPanel2.add(jRadioButton18, null);
    jPanel2.add(jRadioButton19, null);
    jPanel2.add(jRadioButton20, null);
    contentPane.add(jPanel8, BorderLayout.SOUTH);
    jPanel8.add(jPanel15, BorderLayout.EAST);
    jPanel15.add(jPanel23, BorderLayout.WEST);
    jPanel23.add(jButton1, BorderLayout.CENTER);
    jPanel15.add(jPanel24, BorderLayout.CENTER);
    jPanel24.add(jButton2, BorderLayout.CENTER);
  }


}