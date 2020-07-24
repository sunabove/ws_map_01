package jchartui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class ChartTitleFormEditor1 extends JFrame {
  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  BorderLayout borderLayout21 = new BorderLayout();
  JPanel jPanel5 = new JPanel();
  Border border1;
  TitledBorder titledBorder1;
  Border border2;
  TitledBorder titledBorder2;
  JPanel jPanel2 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  Border border3;
  Border border4;
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel3 = new JPanel();
  BorderLayout borderLayout4 = new BorderLayout();
  JPanel jPanel4 = new JPanel();
  JScrollPane jScrollPane2 = new JScrollPane();
  JToggleButton jToggleButton7 = new JToggleButton();
  BorderLayout borderLayout47 = new BorderLayout();
  BorderLayout borderLayout46 = new BorderLayout();
  BorderLayout borderLayout44 = new BorderLayout();
  GridLayout gridLayout7 = new GridLayout();
  JTextField jTextField7 = new JTextField();
  JTextField jTextField6 = new JTextField();
  JPanel jPanel55 = new JPanel();
  JPanel jPanel54 = new JPanel();
  JPanel jPanel53 = new JPanel();
  JPanel jPanel50 = new JPanel();
  JLabel jLabel29 = new JLabel();
  JLabel jLabel28 = new JLabel();
  JLabel jLabel27 = new JLabel();
  JLabel jLabel26 = new JLabel();
  JLabel jLabel25 = new JLabel();
  JLabel jLabel24 = new JLabel();
  JLabel jLabel23 = new JLabel();
  JLabel jLabel22 = new JLabel();
  JLabel jLabel21 = new JLabel();
  JLabel jLabel20 = new JLabel();
  Border border5;
  Border border6;
  Border border7;
  Border border8;
  JPanel jPanel6 = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  JButton jButton1 = new JButton();
  JButton jButton2 = new JButton();
  Border border9;
  GridLayout gridLayout2 = new GridLayout();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel8 = new JPanel();
  BorderLayout borderLayout5 = new BorderLayout();
  BorderLayout borderLayout6 = new BorderLayout();
  JToggleButton jToggleButton1 = new JToggleButton();
  JToggleButton jToggleButton2 = new JToggleButton();
  JComboBox jComboBox1 = new JComboBox();
  JComboBox jComboBox2 = new JComboBox();
  Border border10;

  //Construct the frame
  public ChartTitleFormEditor1() {
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
    border1 = BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134));
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"방향");
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134));
    titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"텍스트 맞춤");
    border3 = BorderFactory.createEmptyBorder(5,5,0,8);
    border4 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"텍스트 맞춤");
    border5 = BorderFactory.createEmptyBorder(0,0,195,0);
    border6 = BorderFactory.createEmptyBorder(0,3,0,0);
    border7 = BorderFactory.createEmptyBorder(0,2,100,60);
    border8 = BorderFactory.createCompoundBorder(titledBorder1,BorderFactory.createEmptyBorder(0,5,3,5));
    border9 = BorderFactory.createEmptyBorder(7,0,5,0);
    border10 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"텍스트 맞춤");
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(420, 363));
    this.setTitle("ChartTitleStyle");
    jPanel5.setLayout(borderLayout21);
    jPanel2.setLayout(borderLayout3);
    jPanel2.setBorder(border9);
    jPanel2.setPreferredSize(new Dimension(0, 33));
    contentPane.setBorder(border3);
    jPanel1.setLayout(borderLayout2);
    jPanel3.setLayout(borderLayout4);
    jPanel4.setLayout(gridLayout2);
    jPanel4.setBorder(border10);
    jPanel4.setPreferredSize(new Dimension(150, 0));
    jScrollPane2.setPreferredSize(new Dimension(50, 4));
    jScrollPane2.setBorder(BorderFactory.createLoweredBevelBorder());
    jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    jToggleButton7.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton7.setBorder(null);
    jToggleButton7.setMnemonic('D');
    jToggleButton7.setText("      도(D)   ");
    jToggleButton7.setRequestFocusEnabled(false);
    borderLayout44.setHgap(5);
    borderLayout44.setVgap(5);
    gridLayout7.setColumns(1);
    gridLayout7.setRows(10);
    jTextField6.setPreferredSize(new Dimension(25, 22));
    jPanel55.setBackground(Color.white);
    jPanel55.setPreferredSize(new Dimension(10, 150));
    jPanel55.setLayout(gridLayout7);
    jPanel54.setLayout(borderLayout47);
    jPanel53.setLayout(borderLayout46);
    jPanel53.setPreferredSize(new Dimension(0, 20));
    jPanel50.setLayout(borderLayout44);
    jPanel50.setBorder(border8);
    jPanel50.setPreferredSize(new Dimension(145, 0));
    jLabel29.setText(" 1");
    jLabel28.setText(" 2");
    jLabel27.setText(" 3");
    jLabel26.setText(" 4");
    jLabel25.setText(" 5");
    jLabel24.setText(" 6");
    jLabel23.setText(" 7");
    jLabel22.setText(" 8");
    jLabel21.setText(" 9");
    jLabel20.setText(" 0");
    jPanel1.setBorder(border5);
    jPanel1.setPreferredSize(new Dimension(110, 150));
    jPanel3.setBorder(border7);
    jPanel3.setPreferredSize(new Dimension(188, 0));
    jPanel5.setBorder(border6);
    borderLayout21.setVgap(5);
    jPanel6.setLayout(gridLayout1);
    jPanel6.setPreferredSize(new Dimension(150, 0));
    gridLayout1.setColumns(2);
    gridLayout1.setHgap(5);
    jButton1.setText("확인");
    jButton2.setText("취소");
    gridLayout2.setRows(2);
    gridLayout2.setVgap(3);
    jPanel7.setLayout(borderLayout5);
    jPanel8.setLayout(borderLayout6);

    jToggleButton1.setHorizontalTextPosition(SwingConstants.CENTER);
    jToggleButton1.setMargin(new Insets(2, 5, 2, 0));
    jToggleButton1.setPreferredSize(new Dimension(60, 27));
    jToggleButton1.setBorder(null);
    jToggleButton1.setMnemonic('H');
    jToggleButton1.setText("가로(H):");
    jToggleButton1.setRequestFocusEnabled(false);
    jToggleButton2.setHorizontalTextPosition(SwingConstants.CENTER);
    jToggleButton2.setMargin(new Insets(2, 5, 2, 0));
    jToggleButton2.setPreferredSize(new Dimension(60, 27));
    jToggleButton2.setBorder(null);
    jToggleButton2.setMnemonic('V');
    jToggleButton2.setText("세로(V):");
    jToggleButton2.setRequestFocusEnabled(false);
    contentPane.add(jTabbedPane1, BorderLayout.CENTER);
    contentPane.add(jPanel2, BorderLayout.SOUTH);
    jPanel2.add(jPanel6, BorderLayout.EAST);
    jPanel6.add(jButton1, null);
    jPanel6.add(jButton2, null);
    jTabbedPane1.add(jPanel5, "  맞춤  ");
    jPanel5.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jPanel4, BorderLayout.CENTER);
    jPanel4.add(jPanel7, null);
    jPanel7.add(jToggleButton1, BorderLayout.WEST);
    jPanel7.add(jComboBox1, BorderLayout.CENTER);
    jPanel4.add(jPanel8, null);
    jPanel8.add(jToggleButton2, BorderLayout.WEST);
    jPanel8.add(jComboBox2, BorderLayout.CENTER);
    jPanel5.add(jPanel3, BorderLayout.EAST);
    jPanel3.add(jPanel50, BorderLayout.CENTER);
    jPanel50.add(jPanel53, BorderLayout.SOUTH);
    jPanel53.add(jScrollPane2, BorderLayout.WEST);
    jPanel53.add(jToggleButton7, BorderLayout.EAST);
    jPanel50.add(jPanel54, BorderLayout.CENTER);
    jPanel54.add(jTextField7, BorderLayout.CENTER);
    jPanel50.add(jTextField6, BorderLayout.WEST);
    jScrollPane2.getViewport().add(jPanel55, null);
    jPanel55.add(jLabel20, null);
    jPanel55.add(jLabel29, null);
    jPanel55.add(jLabel28, null);
    jPanel55.add(jLabel27, null);
    jPanel55.add(jLabel26, null);
    jPanel55.add(jLabel25, null);
    jPanel55.add(jLabel24, null);
    jPanel55.add(jLabel23, null);
    jPanel55.add(jLabel22, null);
    jPanel55.add(jLabel21, null);
  }

  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      System.exit(0);
    }
  }
}