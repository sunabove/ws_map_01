
/**
 * Title:        chartwizard<p>
 * Description:  JAVA CHART v1.0<p>
 * Copyright:    Copyright (c) juney<p>
 * Company:      JCosmos<p>
 * @author juney
 * @version 1.0
 */
package jchartui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import jcosmos.*;
import jchart.*;

public class ChartTypeEditor extends Editor {

  // Data

  private String chartTypeGroupName; // selected chart group name
  private int chartTypeSubIndex; // chart type sub index

  // End of data.

  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  Border border1;
  JPanel jPanel2 = new JPanel();
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JPanel nameEnglish = new JPanel();
  JPanel userDefined = new JPanel();
  Border border2;
  Border border3;
  BorderLayout borderLayout3 = new BorderLayout();
  JPanel jPanel3 = new JPanel();
  JPanel jPanel5 = new JPanel();
  Border border4;
  TitledBorder titledBorder1;
  BorderLayout borderLayout4 = new BorderLayout();
  Border border5;
  TitledBorder titledBorder2;
  TitledBorder titledBorder3;
  JScrollPane jScrollPane1 = new JScrollPane();
  JPanel jPanel6 = new JPanel();
  GridLayout gridLayout3 = new GridLayout(0, 1);
  JPanel jPanel20 = new JPanel();
  Border border6;
  Border border7;
  JPanel jPanel7 = new JPanel();
  Border border8;
  Border border9;
  TitledBorder titledBorder4;
  Border border10;
  BorderLayout borderLayout5 = new BorderLayout();
  JPanel jPanel8 = new JPanel();
  JButton jButton20 = new JButton();
  BorderLayout borderLayout6 = new BorderLayout();
  JTextArea jTextArea1 = new JTextArea();
  Border border11;
  Border border12;
  Border border13;
  Border border14;
  BorderLayout borderLayout7 = new BorderLayout();
  JToggleButton jToggleButton10 = new JToggleButton();
  JToggleButton jToggleButton11 = new JToggleButton();
  BorderLayout borderLayout8 = new BorderLayout();
  JToggleButton jToggleButton12 = new JToggleButton();
  JToggleButton jToggleButton13 = new JToggleButton();
  JPanel jPanel9 = new JPanel();
  BorderLayout borderLayout9 = new BorderLayout();
  JPanel jPanel11 = new JPanel();
  BorderLayout borderLayout10 = new BorderLayout();
  JScrollPane jScrollPane2 = new JScrollPane();
  Border border15;
  JPanel jPanel10 = new JPanel();
  JPanel jPanel12 = new JPanel();
  JPanel jPanel13 = new JPanel();
  GridLayout gridLayout2 = new GridLayout(0, 1);
  JButton jButton1118 = new JButton();
  JButton jButton1119 = new JButton();
  BorderLayout borderLayout1114 = new BorderLayout();
  JPanel jPanel1116 = new JPanel();
  JButton jButton11110 = new JButton();
  JButton jButton11111 = new JButton();
  BorderLayout borderLayout1115 = new BorderLayout();
  JPanel jPanel1117 = new JPanel();
  JButton jButton11112 = new JButton();
  JButton jButton11113 = new JButton();
  BorderLayout borderLayout1116 = new BorderLayout();
  JPanel jPanel1118 = new JPanel();
  JButton jButton11114 = new JButton();
  JButton jButton11115 = new JButton();
  BorderLayout borderLayout1117 = new BorderLayout();
  JPanel jPanel1119 = new JPanel();
  JButton jButton11116 = new JButton();
  JButton jButton11117 = new JButton();
  BorderLayout borderLayout1118 = new BorderLayout();
  JPanel jPanel11110 = new JPanel();
  JButton jButton11118 = new JButton();
  JButton jButton11119 = new JButton();
  BorderLayout borderLayout1119 = new BorderLayout();
  JPanel jPanel11111 = new JPanel();
  JButton jButton111110 = new JButton();
  JButton jButton111111 = new JButton();
  BorderLayout borderLayout11110 = new BorderLayout();
  JPanel jPanel11112 = new JPanel();
  JButton jButton111112 = new JButton();
  JButton jButton111113 = new JButton();
  BorderLayout borderLayout11111 = new BorderLayout();
  JPanel jPanel11113 = new JPanel();
  JButton jButton111114 = new JButton();
  JButton jButton111115 = new JButton();
  BorderLayout borderLayout11112 = new BorderLayout();
  JPanel jPanel11114 = new JPanel();
  JButton jButton111116 = new JButton();
  JButton jButton111117 = new JButton();
  BorderLayout borderLayout11113 = new BorderLayout();
  JPanel jPanel11115 = new JPanel();
  BorderLayout borderLayout11 = new BorderLayout();
  JToggleButton jToggleButton8 = new JToggleButton();
  Border border16;
  Border border17;
  JPanel jPanel15 = new JPanel();
  JButton jButton111118 = new JButton();
  JButton jButton111119 = new JButton();
  BorderLayout borderLayout11114 = new BorderLayout();
  JPanel jPanel11116 = new JPanel();
  JButton jButton1111110 = new JButton();
  JButton jButton1111111 = new JButton();
  BorderLayout borderLayout11115 = new BorderLayout();
  JPanel jPanel11117 = new JPanel();
  JButton jButton1111112 = new JButton();
  JButton jButton1111113 = new JButton();
  BorderLayout borderLayout11116 = new BorderLayout();
  JPanel jPanel11118 = new JPanel();
  JButton jButton1111114 = new JButton();
  JButton jButton1111115 = new JButton();
  BorderLayout borderLayout11117 = new BorderLayout();
  JPanel jPanel11119 = new JPanel();
  TitledBorder titledBorder5;
  BorderLayout borderLayout12 = new BorderLayout();
  JPanel jPanel14 = new JPanel();
  BorderLayout borderLayout13 = new BorderLayout();
  JPanel jPanel16 = new JPanel();
  JTextArea jTextArea2 = new JTextArea();
  Border border18;
  TitledBorder titledBorder6;
  JRadioButton jRadioButton2 = new JRadioButton();
  Border border19;
  Border border20;
  Border border21;
  Border border22;
  TitledBorder titledBorder7;
  Border border23;
  JRadioButton jRadioButton3 = new JRadioButton();
  BorderLayout borderLayout14 = new BorderLayout();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel18 = new JPanel();
  JButton jButton1 = new JButton();
  FlowLayout flowLayout1 = new FlowLayout();
  Border border24;
  Border border25;
  Border border26;
  JButton jButton7 = new JButton();
  JPanel jPanel17 = new JPanel();
  JButton okBtn = new JButton();
  FlowLayout flowLayout2 = new FlowLayout();
  JButton cancelBtn = new JButton();
  CardLayout cardLayout1 = new CardLayout();
  JButton jButton8 = new JButton();
  JButton jButton5 = new JButton();
  JPanel jPanel19 = new JPanel();
  JButton jButton6 = new JButton();
  FlowLayout flowLayout3 = new FlowLayout();
  JButton jButton9 = new JButton();
  JLabel jLabel1 = new JLabel();
  JPanel chartTypesPanel = new JPanel();
  GridLayout gridLayout1 = new GridLayout();
  CardLayout cardLayout2 = new CardLayout();
  JPanel chartPreViewPanel = new JPanel();
  JToggleButton verticalBarTypeBtn = new JToggleButton();
  JToggleButton horizontalBarTypeBtn = new JToggleButton();
  JToggleButton bendLineLineTypeBtn = new JToggleButton();
  JToggleButton circleTypeBtn = new JToggleButton();
  JToggleButton distributionTypeBtn = new JToggleButton();
  JToggleButton areaTypeBtn = new JToggleButton();
  JToggleButton donutsTypeBtn = new JToggleButton();
  JToggleButton radiationTypeBtn = new JToggleButton();
  JToggleButton surfaceTypeBtn = new JToggleButton();
  JToggleButton bubbleTypeBtn = new JToggleButton();
  JToggleButton stockTypeBtn = new JToggleButton();
  JToggleButton cylinderTypeBtn = new JToggleButton();
  JToggleButton pyramidTypeBtn = new JToggleButton();
  JToggleButton coneTypeBtn = new JToggleButton();
  JToggleButton chartTypeBtn [] = new JToggleButton[7];

  //Construct the frame
  public ChartTypeEditor() {
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
    // Code by sbmoon.

    for(int i = 0, len = chartTypeBtn.length; i < len; i ++ ) {

        chartTypeBtn[i] = new JToggleButton();
        chartTypeBtn[i].setBackground(Color.white);
        chartTypeBtn[i].setBorder(BorderFactory.createLoweredBevelBorder());

        final int fi = i;

        chartTypeBtn[i].addActionListener(

            new ActionListener() {

               int chartTypeSubIndex = fi;

               public void actionPerformed(ActionEvent e) {
                   ChartTypeEditor.this.chartTypeSubIndex = chartTypeSubIndex;
                   Utility.debug(this, "chart type sub index = " + chartTypeSubIndex );
               }
            }

        );

    }

    // End of code by sbmoon.

    contentPane = (JPanel) this.getContentPane();
    border1 = BorderFactory.createEmptyBorder(5,5,2,5);
    border2 = BorderFactory.createEmptyBorder(3,2,3,0);
    border3 = BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(Color.lightGray,Color.lightGray),border1);
    border4 = BorderFactory.createEmptyBorder();
    titledBorder1 = new TitledBorder("");
    border5 = BorderFactory.createEmptyBorder(2,6,5,6);
    titledBorder2 = new TitledBorder("");
    titledBorder3 = new TitledBorder("");
    border7 = BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134));
    border8 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(134, 134, 134),new Color(93, 93, 93));
    border9 = BorderFactory.createBevelBorder(BevelBorder.RAISED,Color.white,Color.white,new Color(178, 178, 178),new Color(124, 124, 124));
    titledBorder4 = new TitledBorder("");
    border10 = BorderFactory.createEmptyBorder(0,0,0,5);
    border11 = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(134, 134, 134),new Color(93, 93, 93)),BorderFactory.createEmptyBorder(0,0,0,30));
    border12 = BorderFactory.createEmptyBorder(10,0,0,0);
    border13 = BorderFactory.createEmptyBorder(5,0,0,0);
    border14 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,SystemColor.activeCaption,Color.white,SystemColor.activeCaption);
    border15 = BorderFactory.createEmptyBorder(0,5,5,5);
    border16 = BorderFactory.createEmptyBorder(0,10,0,0);
    border17 = BorderFactory.createEmptyBorder(1,0,0,0);
    titledBorder5 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(178, 178, 178)),"");
    border18 = BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134));
    titledBorder6 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"사용할 서식");
    border19 = BorderFactory.createEmptyBorder(3,0,0,0);
    titledBorder7 = new TitledBorder("");
    border23 = BorderFactory.createCompoundBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"사용할 서식"),BorderFactory.createEmptyBorder(0,6,0,0));
    border24 = BorderFactory.createEmptyBorder();
    border25 = BorderFactory.createEmptyBorder();
    border26 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(134, 134, 134),new Color(93, 93, 93));
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(420, 379));
    this.setTitle("차트 종류");
    jPanel1.setLayout(borderLayout2);
    jPanel1.setBorder(border1);
    jPanel1.setPreferredSize(new Dimension(0, 321));
    contentPane.setPreferredSize(new Dimension(0, 71));
    jPanel2.setBorder(border4);
    jPanel2.setPreferredSize(new Dimension(10, 45));
    jPanel2.setLayout(borderLayout14);
    nameEnglish.setLayout(borderLayout3);
    jPanel3.setPreferredSize(new Dimension(10, 20));
    jPanel3.setLayout(borderLayout7);
    jPanel5.setBorder(border5);
    jPanel5.setPreferredSize(new Dimension(385, 10));
    jPanel5.setLayout(borderLayout4);
    jTabbedPane1.setPreferredSize(new Dimension(385, 76));
    jTabbedPane1.setRequestFocusEnabled(false);
    userDefined.setRequestFocusEnabled(false);
    userDefined.setLayout(borderLayout8);
    jScrollPane1.setBorder(border26);
    jScrollPane1.setPreferredSize(new Dimension(155, 2));
    jPanel6.setBackground(Color.white);
    jPanel6.setMaximumSize(new Dimension(100, 32767));
    jPanel6.setMinimumSize(new Dimension(100, 252));
    jPanel6.setPreferredSize(new Dimension(100, 250));
    jPanel6.setLayout(gridLayout3);
    gridLayout3.setHgap(1);
    gridLayout3.setColumns(1);
    gridLayout3.setRows(0);
    jPanel20.setBorder(border12);
    jPanel20.setPreferredSize(new Dimension(10, 80));
    jPanel20.setLayout(borderLayout5);
    jPanel7.setLayout(cardLayout2);
    jPanel7.setBorder(border10);
    jPanel7.setMaximumSize(new Dimension(200, 200));
    jPanel7.setMinimumSize(new Dimension(200, 200));
    jPanel7.setPreferredSize(new Dimension(220, 83));
    jPanel8.setBorder(border13);
    jPanel8.setPreferredSize(new Dimension(10, 23));
    jPanel8.setLayout(borderLayout6);
    jButton20.setPreferredSize(new Dimension(216, 20));
    jButton20.setToolTipText("");
    jButton20.setHorizontalAlignment(SwingConstants.LEFT);
    jButton20.setMargin(new Insets(1, 5, 1, 5));
    jButton20.setMnemonic('V');
    jButton20.setText("누르고 있으면 보기가 나타납니다(V)");
    jTextArea1.setLineWrap(true);
    jTextArea1.setPreferredSize(new Dimension(216, 100));
    jTextArea1.setWrapStyleWord(true);
    jTextArea1.setTabSize(5);
    jTextArea1.setOpaque(false);
    jTextArea1.setBackground(Color.lightGray);
    jTextArea1.setBorder(border11);
    jTextArea1.setText("묶은 세로 막대형, 항목간의 값을 비교합니다.");
    jTextArea1.setEditable(false);
    jTextArea1.setRequestFocusEnabled(false);
    jToggleButton10.setBorder(null);
    jToggleButton10.setMnemonic('C');
    jToggleButton10.setText("  차트 종류(C):");
    jToggleButton10.setRequestFocusEnabled(false);
    jToggleButton11.setMargin(new Insets(0, 14, 0, 14));
    jToggleButton11.setPreferredSize(new Dimension(225, 19));
    jToggleButton11.setBorder(null);
    jToggleButton11.setText("하위 종류(T):");
    jToggleButton11.setRequestFocusEnabled(false);
    jToggleButton11.setHorizontalAlignment(SwingConstants.LEFT);
//    horizontalBarTypeBtn.setRequestFocusEnabled(false);
    jToggleButton12.setHorizontalAlignment(SwingConstants.LEFT);
    jToggleButton12.setRequestFocusEnabled(false);
    jToggleButton12.setText("보기:");
    jToggleButton12.setBorder(null);
    jToggleButton12.setPreferredSize(new Dimension(225, 19));
    jToggleButton12.setMargin(new Insets(0, 14, 0, 14));
    jToggleButton13.setRequestFocusEnabled(false);
    jToggleButton13.setHorizontalAlignment(SwingConstants.LEFT);
    jToggleButton13.setText("  차트 종류(C):");
    jToggleButton13.setMnemonic('C');
    jToggleButton13.setBorder(null);
    jPanel9.setLayout(borderLayout9);
    jPanel9.setPreferredSize(new Dimension(10, 23));
    jPanel11.setBorder(border15);
    jPanel11.setPreferredSize(new Dimension(100, 200));
    jPanel11.setLayout(borderLayout10);
    jScrollPane2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    jScrollPane2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    jScrollPane2.setBorder(BorderFactory.createLoweredBevelBorder());
    jScrollPane2.setPreferredSize(new Dimension(50, 4));
    jPanel10.setBorder(border16);
    jPanel10.setPreferredSize(new Dimension(230, 10));
    jPanel10.setLayout(borderLayout11);
    jPanel12.setBorder(border17);
    jPanel12.setPreferredSize(new Dimension(220, 82));
    jPanel12.setLayout(borderLayout12);
    jPanel13.setLayout(gridLayout2);
    jPanel13.setBackground(Color.white);
    jPanel13.setPreferredSize(new Dimension(100, 240));
    gridLayout2.setColumns(1);
    gridLayout2.setHgap(1);
    gridLayout2.setRows(0);
    jButton1118.setBackground(Color.white);
    jButton1118.setBorder(null);
    jButton1118.setPreferredSize(new Dimension(60, 19));
    jButton1118.setRequestFocusEnabled(false);
    jButton1118.setHorizontalAlignment(SwingConstants.LEFT);
    jButton1118.setText(" 3차원 쪼개진 원형");
    jButton1118.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton1118_actionPerformed(e);
      }
    });
    jButton1119.setBackground(Color.white);
    jButton1119.setBorder(null);
    jButton1119.setRequestFocusEnabled(false);
    jButton1119.setIcon(new ImageIcon(jcosmos.Utility.getResource("20.gif")));
    jPanel1116.setLayout(borderLayout1114);
    jPanel1116.setPreferredSize(new Dimension(120, 18));
    jButton11110.setBackground(Color.white);
    jButton11110.setBorder(null);
    jButton11110.setPreferredSize(new Dimension(60, 19));
    jButton11110.setRequestFocusEnabled(false);
    jButton11110.setHorizontalAlignment(SwingConstants.LEFT);
    jButton11110.setText(" 파란색 원형");
    jButton11110.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton11110_actionPerformed(e);
      }
    });
    jButton11111.setBackground(Color.white);
    jButton11111.setBorder(null);
    jButton11111.setRequestFocusEnabled(false);
    jButton11111.setIcon(new ImageIcon(jcosmos.Utility.getResource("33.gif")));
    jPanel1117.setLayout(borderLayout1115);
    jPanel1117.setPreferredSize(new Dimension(120, 18));
    jButton11112.setBackground(Color.white);
    jButton11112.setBorder(null);
    jButton11112.setPreferredSize(new Dimension(60, 19));
    jButton11112.setRequestFocusEnabled(false);
    jButton11112.setHorizontalAlignment(SwingConstants.LEFT);
    jButton11112.setText(" 튜브형");
    jButton11112.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton11112_actionPerformed(e);
      }
    });
    jButton11113.setBackground(Color.white);
    jButton11113.setBorder(null);
    jButton11113.setRequestFocusEnabled(false);
    jButton11113.setIcon(new ImageIcon(jcosmos.Utility.getResource("32.gif")));
    jPanel1118.setLayout(borderLayout1116);
    jPanel1118.setPreferredSize(new Dimension(120, 18));
    jButton11114.setBackground(Color.white);
    jButton11114.setBorder(null);
    jButton11114.setPreferredSize(new Dimension(60, 19));
    jButton11114.setRequestFocusEnabled(false);
    jButton11114.setHorizontalAlignment(SwingConstants.LEFT);
    jButton11114.setText(" 컬러 누적 막대형");
    jButton11114.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton11114_actionPerformed(e);
      }
    });
    jButton11115.setBackground(Color.white);
    jButton11115.setBorder(null);
    jButton11115.setRequestFocusEnabled(false);
    jButton11115.setIcon(new ImageIcon(jcosmos.Utility.getResource("31.gif")));
    jPanel1119.setLayout(borderLayout1117);
    jPanel1119.setPreferredSize(new Dimension(120, 18));
    jButton11116.setBackground(Color.white);
    jButton11116.setBorder(null);
    jButton11116.setPreferredSize(new Dimension(60, 19));
    jButton11116.setRequestFocusEnabled(false);
    jButton11116.setHorizontalAlignment(SwingConstants.LEFT);
    jButton11116.setText(" 컬라 꺽은 선형");
    jButton11116.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton11116_actionPerformed(e);
      }
    });
    jButton11117.setBackground(Color.white);
    jButton11117.setBorder(null);
    jButton11117.setRequestFocusEnabled(false);
    jButton11117.setIcon(new ImageIcon(jcosmos.Utility.getResource("30.gif")));
    jPanel11110.setLayout(borderLayout1118);
    jPanel11110.setPreferredSize(new Dimension(120, 18));
    jButton11118.setBackground(Color.white);
    jButton11118.setBorder(null);
    jButton11118.setPreferredSize(new Dimension(60, 19));
    jButton11118.setRequestFocusEnabled(false);
    jButton11118.setHorizontalAlignment(SwingConstants.LEFT);
    jButton11118.setText(" 질감 표시 가로 막대형");
    jButton11118.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton11118_actionPerformed(e);
      }
    });
    jButton11119.setBackground(Color.white);
    jButton11119.setBorder(null);
    jButton11119.setRequestFocusEnabled(false);
    jButton11119.setIcon(new ImageIcon(jcosmos.Utility.getResource("29.gif")));
    jPanel11111.setLayout(borderLayout1119);
    jPanel11111.setPreferredSize(new Dimension(120, 18));
    jButton111110.setBackground(Color.white);
    jButton111110.setBorder(null);
    jButton111110.setPreferredSize(new Dimension(60, 19));
    jButton111110.setRequestFocusEnabled(false);
    jButton111110.setHorizontalAlignment(SwingConstants.LEFT);
    jButton111110.setText(" 입체 세로 막대형");
    jButton111110.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton111110_actionPerformed(e);
      }
    });
    jButton111111.setBackground(Color.white);
    jButton111111.setBorder(null);
    jButton111111.setRequestFocusEnabled(false);
    jButton111111.setIcon(new ImageIcon(jcosmos.Utility.getResource("28.gif")));
    jPanel11112.setLayout(borderLayout11110);
    jPanel11112.setPreferredSize(new Dimension(120, 18));
    jButton111112.setBackground(Color.white);
    jButton111112.setBorder(null);
    jButton111112.setPreferredSize(new Dimension(60, 19));
    jButton111112.setRequestFocusEnabled(false);
    jButton111112.setHorizontalAlignment(SwingConstants.LEFT);
    jButton111112.setText(" 이중 축 혼합형");
    jButton111112.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton111112_actionPerformed(e);
      }
    });
    jButton111113.setBackground(Color.white);
    jButton111113.setBorder(null);
    jButton111113.setRequestFocusEnabled(false);
    jButton111113.setIcon(new ImageIcon(jcosmos.Utility.getResource("27.gif")));
    jPanel11113.setLayout(borderLayout11111);
    jPanel11113.setPreferredSize(new Dimension(120, 18));
    jButton111114.setBackground(Color.white);
    jButton111114.setBorder(null);
    jButton111114.setPreferredSize(new Dimension(60, 19));
    jButton111114.setRequestFocusEnabled(false);
    jButton111114.setHorizontalAlignment(SwingConstants.LEFT);
    jButton111114.setText(" 이중 축 꺽은선형");
    jButton111114.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton111114_actionPerformed(e);
      }
    });
    jButton111115.setBackground(Color.white);
    jButton111115.setBorder(null);
    jButton111115.setRequestFocusEnabled(false);
    jButton111115.setIcon(new ImageIcon(jcosmos.Utility.getResource("26.gif")));
    jPanel11114.setLayout(borderLayout11112);
    jPanel11114.setPreferredSize(new Dimension(120, 18));
    jButton111116.setBackground(Color.white);
    jButton111116.setBorder(null);
    jButton111116.setPreferredSize(new Dimension(60, 19));
    jButton111116.setRequestFocusEnabled(false);
    jButton111116.setHorizontalAlignment(SwingConstants.LEFT);
    jButton111116.setText(" 원뿔형");
    jButton111116.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton111116_actionPerformed(e);
      }
    });
    jButton111117.setBackground(Color.white);
    jButton111117.setBorder(null);
    jButton111117.setRequestFocusEnabled(false);
    jButton111117.setIcon(new ImageIcon(jcosmos.Utility.getResource("cone.gif")));
    jPanel11115.setLayout(borderLayout11113);
    jPanel11115.setPreferredSize(new Dimension(120, 18));
    jToggleButton8.setIcon(new ImageIcon(jcosmos.Utility.getResource("19.gif")));
    jToggleButton8.setHorizontalTextPosition(SwingConstants.CENTER);
    jToggleButton8.setMargin(new Insets(2, 2, 2, 2));
    jToggleButton8.setPreferredSize(new Dimension(205, 200));
    jToggleButton8.setBackground(Color.white);
    jToggleButton8.setBorder(BorderFactory.createEtchedBorder());
    jPanel15.setPreferredSize(new Dimension(10, 24));
    jButton111118.setIcon(new ImageIcon(jcosmos.Utility.getResource("24.gif")));
    jButton111118.setRequestFocusEnabled(false);
    jButton111118.setBorder(null);
    jButton111118.setBackground(Color.white);
    jButton111119.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton111119_actionPerformed(e);
      }
    });
    jButton111119.setText(" 블럭 영역형");
    jButton111119.setHorizontalAlignment(SwingConstants.LEFT);
    jButton111119.setRequestFocusEnabled(false);
    jButton111119.setPreferredSize(new Dimension(60, 19));
    jButton111119.setBorder(null);
    jButton111119.setBackground(Color.white);
    jPanel11116.setPreferredSize(new Dimension(120, 18));
    jPanel11116.setLayout(borderLayout11114);
    jButton1111110.setIcon(new ImageIcon(jcosmos.Utility.getResource("23.gif")));
    jButton1111110.setRequestFocusEnabled(false);
    jButton1111110.setBorder(null);
    jButton1111110.setBackground(Color.white);
    jButton1111111.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton1111111_actionPerformed(e);
      }
    });
    jButton1111111.setText(" 부드러운 꺽은선형");
    jButton1111111.setHorizontalAlignment(SwingConstants.LEFT);
    jButton1111111.setRequestFocusEnabled(false);
    jButton1111111.setPreferredSize(new Dimension(60, 19));
    jButton1111111.setBorder(null);
    jButton1111111.setBackground(Color.white);
    jPanel11117.setPreferredSize(new Dimension(120, 18));
    jPanel11117.setLayout(borderLayout11115);
    jButton1111112.setIcon(new ImageIcon(jcosmos.Utility.getResource("22.gif")));
    jButton1111112.setRequestFocusEnabled(false);
    jButton1111112.setBorder(null);
    jButton1111112.setBackground(Color.white);
    jButton1111113.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton1111113_actionPerformed(e);
      }
    });
    jButton1111113.setText(" 부동 가로 막대형");
    jButton1111113.setHorizontalAlignment(SwingConstants.LEFT);
    jButton1111113.setRequestFocusEnabled(false);
    jButton1111113.setPreferredSize(new Dimension(60, 19));
    jButton1111113.setBorder(null);
    jButton1111113.setBackground(Color.white);
    jPanel11118.setPreferredSize(new Dimension(120, 18));
    jPanel11118.setLayout(borderLayout11116);
    jButton1111114.setIcon(new ImageIcon(jcosmos.Utility.getResource("21.gif")));
    jButton1111114.setRequestFocusEnabled(false);
    jButton1111114.setBorder(null);
    jButton1111114.setBackground(Color.white);
    jButton1111115.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        jButton1111115_actionPerformed(e);
      }
    });
    jButton1111115.setText(" 로그 단위 꺽은선형");
    jButton1111115.setHorizontalAlignment(SwingConstants.LEFT);
    jButton1111115.setRequestFocusEnabled(false);
    jButton1111115.setPreferredSize(new Dimension(60, 19));
    jButton1111115.setBorder(null);
    jButton1111115.setBackground(Color.white);
    jPanel11119.setPreferredSize(new Dimension(120, 18));
    jPanel11119.setLayout(borderLayout11117);
    jPanel14.setBorder(border23);
    jPanel14.setPreferredSize(new Dimension(161, 49));
    jPanel14.setLayout(borderLayout13);
    jTextArea2.setLineWrap(true);
    jTextArea2.setPreferredSize(new Dimension(216, 49));
    jTextArea2.setWrapStyleWord(true);
    jTextArea2.setTabSize(5);
    jTextArea2.setMaximumSize(new Dimension(100, 100));
    jTextArea2.setOpaque(false);
    jTextArea2.setBackground(Color.lightGray);
    jTextArea2.setBorder(BorderFactory.createLoweredBevelBorder());
    jTextArea2.setMinimumSize(new Dimension(200, 38));
    jTextArea2.setText("3차원 쪼개진 원형차트, 스크린 프리젠테이션에 적합한 형식입니다.");
    jTextArea2.setEditable(false);
    jTextArea2.setRequestFocusEnabled(false);
    borderLayout12.setHgap(5);
    jRadioButton2.setMargin(new Insets(0, 0, 0, 0));
    jRadioButton2.setPreferredSize(new Dimension(103, 15));
    jRadioButton2.setMnemonic('B');
    jRadioButton2.setVerticalTextPosition(SwingConstants.BOTTOM);
    jRadioButton2.setText("제공된 서식(B)");
    jRadioButton2.setRequestFocusEnabled(false);
    jRadioButton2.setVerticalAlignment(SwingConstants.BOTTOM);
    jPanel16.setBorder(border19);
    borderLayout13.setHgap(2);
    borderLayout13.setVgap(2);
    jRadioButton3.setVerticalAlignment(SwingConstants.BOTTOM);
    jRadioButton3.setRequestFocusEnabled(false);
    jRadioButton3.setText("사용자 정의(U)");
    jRadioButton3.setVerticalTextPosition(SwingConstants.BOTTOM);
    jRadioButton3.setMnemonic('U');
    jRadioButton3.setPreferredSize(new Dimension(103, 15));
    jRadioButton3.setMargin(new Insets(0, 0, 0, 0));
    jPanel4.setLayout(flowLayout1);
    jPanel4.setBorder(border17);
    jPanel4.setPreferredSize(new Dimension(40, 40));
    jButton1.setPreferredSize(new Dimension(30, 21));
    jButton1.setIcon(new ImageIcon(jcosmos.Utility.getResource("01.gif")));
    jButton7.setMinimumSize(new Dimension(1, 1));
    jButton7.setPreferredSize(new Dimension(150, 21));
    jButton7.setMnemonic('E');
    jButton7.setText("기본 차트로 설정(E)");
    jPanel17.setBorder(border4);
    jPanel17.setMinimumSize(new Dimension(1, 1));
    jPanel17.setPreferredSize(new Dimension(320, 21));
    jPanel17.setLayout(flowLayout2);
    okBtn.setMinimumSize(new Dimension(1, 1));
    okBtn.setPreferredSize(new Dimension(74, 21));
    okBtn.setText("확인");
    okBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        okBtn_actionPerformed(e);
      }
    });
    flowLayout2.setHgap(4);
    cancelBtn.setMinimumSize(new Dimension(1, 1));
    cancelBtn.setPreferredSize(new Dimension(74, 21));
    cancelBtn.setMargin(new Insets(2, 10, 2, 10));
    cancelBtn.setMnemonic('F');
    cancelBtn.setText("취소");
    cancelBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        cancelBtn_actionPerformed(e);
      }
    });
    jPanel18.setLayout(cardLayout1);
    jButton8.setText("취소");
    jButton8.setPreferredSize(new Dimension(70, 21));
    jButton8.setMinimumSize(new Dimension(1, 1));
    jButton5.setText("<뒤로(<)");
    jButton5.setMargin(new Insets(2, 10, 2, 10));
    jButton5.setHorizontalAlignment(SwingConstants.LEFT);
    jButton5.setPreferredSize(new Dimension(74, 21));
    jButton5.setMinimumSize(new Dimension(1, 1));
    jPanel19.setLayout(flowLayout3);
    jPanel19.setPreferredSize(new Dimension(320, 21));
    jPanel19.setMinimumSize(new Dimension(1, 1));
    jPanel19.setBorder(border4);
    jButton6.setMinimumSize(new Dimension(1, 1));
    jButton6.setPreferredSize(new Dimension(74, 21));
    jButton6.setText("다음>");
    flowLayout3.setHgap(4);
    jButton9.setMinimumSize(new Dimension(1, 1));
    jButton9.setPreferredSize(new Dimension(74, 21));
    jButton9.setMargin(new Insets(2, 10, 2, 10));
    jButton9.setMnemonic('F');
    jButton9.setText("마침(F)");
    jLabel1.setEnabled(false);
    jLabel1.setPreferredSize(new Dimension(30, 18));
    jLabel1.setText(" ");
    chartTypesPanel.setLayout(gridLayout1);
    gridLayout1.setRows(3);
    gridLayout1.setColumns(3);
    gridLayout1.setHgap(1);
    gridLayout1.setVgap(1);
    verticalBarTypeBtn.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        verticalBarTypeBtn_actionPerformed(e);
      }
    });
    verticalBarTypeBtn.setText(" 세로 막대형");
    verticalBarTypeBtn.setHorizontalAlignment(SwingConstants.LEFT);
    verticalBarTypeBtn.setPreferredSize(new Dimension(60, 19));
    verticalBarTypeBtn.setBorder(null);
    verticalBarTypeBtn.setBackground(Color.white);
    verticalBarTypeBtn.setIcon(new ImageIcon(jcosmos.Utility.getResource("02.gif")));
    horizontalBarTypeBtn.setBackground(Color.white);
    horizontalBarTypeBtn.setBorder(null);
    horizontalBarTypeBtn.setIcon(new ImageIcon(jcosmos.Utility.getResource("03.gif")));
    horizontalBarTypeBtn.setToolTipText("");
    horizontalBarTypeBtn.setHorizontalAlignment(SwingConstants.LEFT);
    horizontalBarTypeBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        horizontalBarTypeBtn_actionPerformed(e);
      }
    });
    horizontalBarTypeBtn.setText(" 가로 막대형");
    bendLineLineTypeBtn.setBackground(Color.white);
    bendLineLineTypeBtn.setBorder(null);
    bendLineLineTypeBtn.setPreferredSize(new Dimension(60, 19));
    bendLineLineTypeBtn.setIcon(new ImageIcon(jcosmos.Utility.getResource("04.gif")));
    bendLineLineTypeBtn.setHorizontalAlignment(SwingConstants.LEFT);
    bendLineLineTypeBtn.setText(" 꺾은선형");
    bendLineLineTypeBtn.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        bendLineLineTypeBtn_actionPerformed(e);
      }
    });
    circleTypeBtn.setBackground(Color.white);
    circleTypeBtn.setBorder(null);
    circleTypeBtn.setPreferredSize(new Dimension(60, 19));
    circleTypeBtn.setIcon(new ImageIcon(jcosmos.Utility.getResource("05.gif")));
    circleTypeBtn.setHorizontalAlignment(SwingConstants.LEFT);
    circleTypeBtn.setText(" 원형");
    circleTypeBtn.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        circleTypeBtn_actionPerformed(e);
      }
    });
    distributionTypeBtn.setBackground(Color.white);
    distributionTypeBtn.setBorder(null);
    distributionTypeBtn.setPreferredSize(new Dimension(60, 19));
    distributionTypeBtn.setIcon(new ImageIcon(jcosmos.Utility.getResource("06.gif")));
    distributionTypeBtn.setHorizontalAlignment(SwingConstants.LEFT);
    distributionTypeBtn.setText(" 분산형");
    distributionTypeBtn.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        distributionTypeBtn_actionPerformed(e);
      }
    });
    areaTypeBtn.setBackground(Color.white);
    areaTypeBtn.setBorder(null);
    areaTypeBtn.setPreferredSize(new Dimension(60, 19));
    areaTypeBtn.setIcon(new ImageIcon(jcosmos.Utility.getResource("07.gif")));
    areaTypeBtn.setHorizontalAlignment(SwingConstants.LEFT);
    areaTypeBtn.setText(" 영역형");
    areaTypeBtn.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        areaTypeBtn_actionPerformed(e);
      }
    });
    donutsTypeBtn.setBackground(Color.white);
    donutsTypeBtn.setBorder(null);
    donutsTypeBtn.setPreferredSize(new Dimension(60, 19));
    donutsTypeBtn.setIcon(new ImageIcon(jcosmos.Utility.getResource("08.gif")));
    donutsTypeBtn.setHorizontalAlignment(SwingConstants.LEFT);
    donutsTypeBtn.setText(" 도넛형");
    donutsTypeBtn.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        donutsTypeBtn_actionPerformed(e);
      }
    });
    radiationTypeBtn.setBackground(Color.white);
    radiationTypeBtn.setBorder(null);
    radiationTypeBtn.setPreferredSize(new Dimension(60, 19));
    radiationTypeBtn.setIcon(new ImageIcon(jcosmos.Utility.getResource("09.gif")));
    radiationTypeBtn.setHorizontalAlignment(SwingConstants.LEFT);
    radiationTypeBtn.setText(" 방사형");
    radiationTypeBtn.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        radiationTypeBtn_actionPerformed(e);
      }
    });
    surfaceTypeBtn.setBackground(Color.white);
    surfaceTypeBtn.setBorder(null);
    surfaceTypeBtn.setPreferredSize(new Dimension(60, 19));
    surfaceTypeBtn.setIcon(new ImageIcon(jcosmos.Utility.getResource("10.gif")));
    surfaceTypeBtn.setHorizontalAlignment(SwingConstants.LEFT);
    surfaceTypeBtn.setText(" 표면형");
    surfaceTypeBtn.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        surfaceTypeBtn_actionPerformed(e);
      }
    });
    bubbleTypeBtn.setBackground(Color.white);
    bubbleTypeBtn.setBorder(null);
    bubbleTypeBtn.setPreferredSize(new Dimension(60, 19));
    bubbleTypeBtn.setIcon(new ImageIcon(jcosmos.Utility.getResource("11.gif")));
    bubbleTypeBtn.setHorizontalAlignment(SwingConstants.LEFT);
    bubbleTypeBtn.setText(" 거품형");
    bubbleTypeBtn.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        bubbleTypeBtn_actionPerformed(e);
      }
    });

    stockTypeBtn.setBackground(Color.white);
    stockTypeBtn.setBorder(null);
    stockTypeBtn.setPreferredSize(new Dimension(60, 19));
    stockTypeBtn.setIcon(new ImageIcon(jcosmos.Utility.getResource("02.gif")));
    stockTypeBtn.setActionCommand("주식형");
    stockTypeBtn.setHorizontalAlignment(SwingConstants.LEFT);
    stockTypeBtn.setText(" 주식형");
    stockTypeBtn.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        stockTypeBtn_actionPerformed(e);
      }
    });
    cylinderTypeBtn.setBackground(Color.white);
    cylinderTypeBtn.setBorder(null);
    cylinderTypeBtn.setPreferredSize(new Dimension(60, 19));
    cylinderTypeBtn.setIcon(new ImageIcon(jcosmos.Utility.getResource("cylinder.gif")));
    cylinderTypeBtn.setActionCommand("원통형");
    cylinderTypeBtn.setHorizontalAlignment(SwingConstants.LEFT);
    cylinderTypeBtn.setText(" 원통형");
    cylinderTypeBtn.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        cylinderTypeBtn_actionPerformed(e);
      }
    });
    pyramidTypeBtn.setBackground(Color.white);
    pyramidTypeBtn.setBorder(null);
    pyramidTypeBtn.setPreferredSize(new Dimension(60, 19));
    pyramidTypeBtn.setIcon(new ImageIcon(jcosmos.Utility.getResource("pyramid.gif")));
    pyramidTypeBtn.setActionCommand("피라미드형");
    pyramidTypeBtn.setHorizontalAlignment(SwingConstants.LEFT);
    pyramidTypeBtn.setText(" 피라미드형");
    pyramidTypeBtn.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        pyramidTypeBtn_actionPerformed(e);
      }
    });
    coneTypeBtn.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        coneTypeBtn_actionPerformed(e);
      }
    });
    coneTypeBtn.setText(" 원뿔형");
    coneTypeBtn.setHorizontalAlignment(SwingConstants.LEFT);
    coneTypeBtn.setActionCommand("피라미드형");
    coneTypeBtn.setIcon(new ImageIcon(jcosmos.Utility.getResource("cone.gif")));
    coneTypeBtn.setPreferredSize(new Dimension(60, 19));
    coneTypeBtn.setBorder(null);
    coneTypeBtn.setBackground(Color.white);
    contentPane.add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jTabbedPane1, BorderLayout.CENTER);
    jTabbedPane1.add(nameEnglish, " 표준 종류 ");
    nameEnglish.add(jPanel3, BorderLayout.NORTH);
    jPanel3.add(jToggleButton10, BorderLayout.WEST);
    jPanel3.add(jToggleButton11, BorderLayout.EAST);
    nameEnglish.add(jPanel5, BorderLayout.CENTER);
    jPanel5.add(jScrollPane1, BorderLayout.WEST);
    jPanel5.add(jPanel20, BorderLayout.SOUTH);
    jPanel20.add(jPanel8, BorderLayout.SOUTH);
    jPanel8.add(jButton20, BorderLayout.EAST);
    jPanel20.add(jTextArea1, BorderLayout.EAST);
    jPanel5.add(jPanel7, BorderLayout.EAST);
    jPanel7.add(chartTypesPanel, "jPanel21");

    for(int i = 0, len = chartTypeBtn.length; i < len; i ++ ) {
       chartTypesPanel.add(chartTypeBtn[ i ], null);
    }
    jPanel7.add(chartPreViewPanel, "jPanel22");
    jTabbedPane1.add(userDefined, " 사용자 정의 종류 ");
    userDefined.add(jPanel9, BorderLayout.NORTH);
    jPanel9.add(jToggleButton13, BorderLayout.WEST);
    jPanel9.add(jToggleButton12, BorderLayout.EAST);
    userDefined.add(jPanel11, BorderLayout.CENTER);
    jPanel11.add(jScrollPane2, BorderLayout.CENTER);
    jScrollPane2.getViewport().add(jPanel13, null);
    jPanel13.add(jPanel1116, null);
    jPanel1116.add(jButton1119, BorderLayout.WEST);
    jPanel1116.add(jButton1118, BorderLayout.CENTER);
    jPanel13.add(jPanel11119, null);
    jPanel11119.add(jButton1111114, BorderLayout.WEST);
    jPanel11119.add(jButton1111115, BorderLayout.CENTER);
    jPanel13.add(jPanel11118, null);
    jPanel11118.add(jButton1111112, BorderLayout.WEST);
    jPanel11118.add(jButton1111113, BorderLayout.CENTER);
    jPanel13.add(jPanel11117, null);
    jPanel11117.add(jButton1111110, BorderLayout.WEST);
    jPanel11117.add(jButton1111111, BorderLayout.CENTER);
    jPanel13.add(jPanel11116, null);
    jPanel11116.add(jButton111118, BorderLayout.WEST);
    jPanel11116.add(jButton111119, BorderLayout.CENTER);
    jPanel13.add(jPanel11115, null);
    jPanel11115.add(jButton111117, BorderLayout.WEST);
    jPanel11115.add(jButton111116, BorderLayout.CENTER);
    jPanel13.add(jPanel11114, null);
    jPanel11114.add(jButton111115, BorderLayout.WEST);
    jPanel11114.add(jButton111114, BorderLayout.CENTER);
    jPanel13.add(jPanel11113, null);
    jPanel11113.add(jButton111113, BorderLayout.WEST);
    jPanel11113.add(jButton111112, BorderLayout.CENTER);
    jPanel13.add(jPanel11112, null);
    jPanel11112.add(jButton111111, BorderLayout.WEST);
    jPanel11112.add(jButton111110, BorderLayout.CENTER);
    jPanel13.add(jPanel11111, null);
    jPanel11111.add(jButton11119, BorderLayout.WEST);
    jPanel11111.add(jButton11118, BorderLayout.CENTER);
    jPanel13.add(jPanel11110, null);
    jPanel11110.add(jButton11117, BorderLayout.WEST);
    jPanel11110.add(jButton11116, BorderLayout.CENTER);
    jPanel13.add(jPanel1119, null);
    jPanel1119.add(jButton11115, BorderLayout.WEST);
    jPanel1119.add(jButton11114, BorderLayout.CENTER);
    jPanel13.add(jPanel1118, null);
    jPanel1118.add(jButton11113, BorderLayout.WEST);
    jPanel1118.add(jButton11112, BorderLayout.CENTER);
    jPanel13.add(jPanel1117, null);
    jPanel1117.add(jButton11111, BorderLayout.WEST);
    jPanel1117.add(jButton11110, BorderLayout.CENTER);
    jPanel11.add(jPanel10, BorderLayout.EAST);
    jPanel10.add(jToggleButton8, BorderLayout.CENTER);
    jPanel11.add(jPanel12, BorderLayout.SOUTH);
    jPanel12.add(jPanel14, BorderLayout.WEST);
    jPanel14.add(jRadioButton2, BorderLayout.SOUTH);
    jPanel14.add(jRadioButton3, BorderLayout.NORTH);
    jPanel12.add(jPanel15, BorderLayout.SOUTH);
    jPanel12.add(jPanel16, BorderLayout.CENTER);
    jPanel16.add(jTextArea2, null);
    jScrollPane1.getViewport().add(jPanel6, null);
    jPanel6.add(verticalBarTypeBtn, null);
    jPanel6.add(horizontalBarTypeBtn, null);
    jPanel6.add(bendLineLineTypeBtn, null);
    jPanel6.add(circleTypeBtn, null);
    jPanel6.add(distributionTypeBtn, null);
    jPanel6.add(areaTypeBtn, null);
    jPanel6.add(donutsTypeBtn, null);
    jPanel6.add(radiationTypeBtn, null);
    jPanel6.add(surfaceTypeBtn, null);
    jPanel6.add(bubbleTypeBtn, null);
    jPanel6.add(stockTypeBtn, null);
    jPanel6.add(cylinderTypeBtn, null);
    jPanel6.add(coneTypeBtn, null);
    jPanel6.add(pyramidTypeBtn, null);

    contentPane.add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jPanel4, BorderLayout.WEST);
    jPanel4.add(jButton1, null);
    jPanel2.add(jPanel18, BorderLayout.CENTER);
    jPanel18.add(jPanel17, "jPanel17");
    jPanel17.add(jButton7, null);
    jPanel17.add(jLabel1, null);
    jPanel17.add(okBtn, null);
    jPanel17.add(cancelBtn, null);
    jPanel18.add(jPanel19, "jPanel19");
    jPanel19.add(jButton8, null);
    jPanel19.add(jButton5, null);
    jPanel19.add(jButton6, null);
    jPanel19.add(jButton9, null);
  }

  void jButton1118_actionPerformed(ActionEvent e) {

  }

  void jButton11110_actionPerformed(ActionEvent e) {

  }

  void jButton11112_actionPerformed(ActionEvent e) {

  }

  void jButton11114_actionPerformed(ActionEvent e) {

  }

  void jButton11116_actionPerformed(ActionEvent e) {

  }

  void jButton11118_actionPerformed(ActionEvent e) {

  }

  void jButton111110_actionPerformed(ActionEvent e) {

  }

  void jButton111112_actionPerformed(ActionEvent e) {

  }

  void jButton111114_actionPerformed(ActionEvent e) {

  }

  void jButton111116_actionPerformed(ActionEvent e) {

  }

  void jButton111119_actionPerformed(ActionEvent e) {

  }

  void jButton1111111_actionPerformed(ActionEvent e) {

  }

  void jButton1111113_actionPerformed(ActionEvent e) {

  }

  void jButton1111115_actionPerformed(ActionEvent e) {

  }

  void selectChartTypeGroup(String chartTypeGroupName) {
      this.chartTypeGroupName = chartTypeGroupName;

      String idx, rsc; // index, resource
      ImageIcon ii;

      for(int i = 0; i < 7; i ++ ) {
          if( i < 10 ) {
              idx = "0" + (i + 1);
          } else {
              idx = "" + (i + 1);
          }

          rsc = chartTypeGroupName + "_" + idx + ".gif";

          Utility.debug(this, "icon resource = " + rsc );

          try {
            ii = new ImageIcon( jcosmos.Utility.getResource(rsc) );

            if( ! chartTypeBtn[i].isVisible() ) {
                chartTypeBtn[i].setVisible( true );
            }
            chartTypeBtn[i].setIcon( ii );
          } catch ( NullPointerException e) {
            chartTypeBtn[i].setVisible( false );
          }
      }
  }

  void verticalBarTypeBtn_actionPerformed(ActionEvent e) {
      selectChartTypeGroup( "verticalbar" );
  }

  void bendLineLineTypeBtn_actionPerformed(ActionEvent e) {
      selectChartTypeGroup( "bendline" );
  }
  void circleTypeBtn_actionPerformed(ActionEvent e) {
      selectChartTypeGroup( "circle" );
  }
  void distributionTypeBtn_actionPerformed(ActionEvent e) {
      selectChartTypeGroup( "distribution" );
  }
  void areaTypeBtn_actionPerformed(ActionEvent e) {
      selectChartTypeGroup( "area" );
  }
  void donutsTypeBtn_actionPerformed(ActionEvent e) {
      selectChartTypeGroup( "donut" );
  }
  void radiationTypeBtn_actionPerformed(ActionEvent e) {
       selectChartTypeGroup( "radiation" );
  }
  void surfaceTypeBtn_actionPerformed(ActionEvent e) {
      selectChartTypeGroup( "surface" );
  }
  void bubbleTypeBtn_actionPerformed(ActionEvent e) {
      selectChartTypeGroup( "bubble" );
  }
  void stockTypeBtn_actionPerformed(ActionEvent e) {
      selectChartTypeGroup( "stock" );
  }
  void cylinderTypeBtn_actionPerformed(ActionEvent e) {
      selectChartTypeGroup( "cylinder" );
  }
  void pyramidTypeBtn_actionPerformed(ActionEvent e) {
      selectChartTypeGroup( "pyramid" );
  }

  void horizontalBarTypeBtn_actionPerformed(ActionEvent e) {
      selectChartTypeGroup( "horizontalbar" );
  }
  void coneTypeBtn_actionPerformed(ActionEvent e) {
      selectChartTypeGroup( "cone" );
  }

  void cancelBtn_actionPerformed(ActionEvent e) {
      setVisible( false );
  }

  public void paint(Graphics g) {
      super.paint( g );
      if( chartTypeGroupName == null ) {
          verticalBarTypeBtn.setSelected( true );
          selectChartTypeGroup( "verticalbar" );
      }
  }

  void okBtn_actionPerformed(ActionEvent e) {

      ChartType ct = ChartType.getChartType( chartTypeGroupName, chartTypeSubIndex );

      setVisible( false );

      SpreadSheet.applyChartType( ct );
  }

}