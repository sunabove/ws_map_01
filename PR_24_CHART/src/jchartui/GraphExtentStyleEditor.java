
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

public class GraphExtentStyleEditor extends JDialog {
  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout9 = new BorderLayout();
  BorderLayout borderLayout8 = new BorderLayout();
  BorderLayout borderLayout7 = new BorderLayout();
  BorderLayout borderLayout6 = new BorderLayout();
  BorderLayout borderLayout5 = new BorderLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  BorderLayout borderLayout3 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  JPanel jPanel24 = new JPanel();
  JPanel jPanel23 = new JPanel();
  JPanel jPanel22 = new JPanel();
  JPanel jPanel21 = new JPanel();
  JPanel jPanel20 = new JPanel();
  JPanel jPanel19 = new JPanel();
  JPanel jPanel17 = new JPanel();
  JPanel jPanel16 = new JPanel();
  JPanel jPanel15 = new JPanel();
  JPanel jPanel14 = new JPanel();
  JPanel jPanel13 = new JPanel();
  JPanel jPanel11 = new JPanel();
  JPanel jPanel10 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JToggleButton jToggleButton9 = new JToggleButton();
  JToggleButton jToggleButton8 = new JToggleButton();
  JToggleButton jToggleButton7 = new JToggleButton();
  JToggleButton jToggleButton6 = new JToggleButton();
  JToggleButton jToggleButton5 = new JToggleButton();
  JToggleButton jToggleButton4 = new JToggleButton();
  JToggleButton jToggleButton3 = new JToggleButton();
  JToggleButton jToggleButton2 = new JToggleButton();
  JToggleButton jToggleButton1 = new JToggleButton();
  JRadioButton jRadioButton5 = new JRadioButton();
  JRadioButton jRadioButton4 = new JRadioButton();
  JRadioButton jRadioButton3 = new JRadioButton();
  JRadioButton jRadioButton2 = new JRadioButton();
  BorderLayout borderLayout20 = new BorderLayout();
  JRadioButton jRadioButton1 = new JRadioButton();
  JPanel jPanel9 = new JPanel();
  JPanel jPanel8 = new JPanel();
  JPanel jPanel7 = new JPanel();
  JPanel jPanel6 = new JPanel();
  BorderLayout borderLayout117 = new BorderLayout();
  BorderLayout borderLayout116 = new BorderLayout();
  BorderLayout borderLayout19 = new BorderLayout();
  BorderLayout borderLayout115 = new BorderLayout();
  JPanel jPanel3 = new JPanel();
  BorderLayout borderLayout18 = new BorderLayout();
  JPanel jPanel2 = new JPanel();
  BorderLayout borderLayout114 = new BorderLayout();
  BorderLayout borderLayout17 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout112 = new BorderLayout();
  BorderLayout borderLayout15 = new BorderLayout();
  BorderLayout borderLayout14 = new BorderLayout();
  BorderLayout borderLayout13 = new BorderLayout();
  BorderLayout borderLayout12 = new BorderLayout();
  BorderLayout borderLayout10 = new BorderLayout();
  JComboBox jComboBox9 = new JComboBox();
  JComboBox jComboBox8 = new JComboBox();
  JComboBox jComboBox6 = new JComboBox();
  JToggleButton jToggleButton60 = new JToggleButton();
  JToggleButton jToggleButton59 = new JToggleButton();
  JToggleButton jToggleButton58 = new JToggleButton();
  JToggleButton jToggleButton57 = new JToggleButton();
  JToggleButton jToggleButton56 = new JToggleButton();
  JToggleButton jToggleButton55 = new JToggleButton();
  JToggleButton jToggleButton54 = new JToggleButton();
  JToggleButton jToggleButton53 = new JToggleButton();
  JToggleButton jToggleButton52 = new JToggleButton();
  JToggleButton jToggleButton51 = new JToggleButton();
  JToggleButton jToggleButton50 = new JToggleButton();
  JToggleButton jToggleButton49 = new JToggleButton();
  JToggleButton jToggleButton48 = new JToggleButton();
  JToggleButton jToggleButton47 = new JToggleButton();
  JToggleButton jToggleButton46 = new JToggleButton();
  JToggleButton jToggleButton45 = new JToggleButton();
  JToggleButton jToggleButton44 = new JToggleButton();
  JToggleButton jToggleButton43 = new JToggleButton();
  JToggleButton jToggleButton42 = new JToggleButton();
  JToggleButton jToggleButton41 = new JToggleButton();
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JToggleButton jToggleButton40 = new JToggleButton();
  JPanel jPanel119 = new JPanel();
  JPanel jPanel118 = new JPanel();
  JPanel jPanel117 = new JPanel();
  JPanel jPanel116 = new JPanel();
  JPanel jPanel114 = new JPanel();
  JPanel jPanel111 = new JPanel();
  JToggleButton jToggleButton39 = new JToggleButton();
  JToggleButton jToggleButton38 = new JToggleButton();
  JToggleButton jToggleButton37 = new JToggleButton();
  JToggleButton jToggleButton36 = new JToggleButton();
  JToggleButton jToggleButton35 = new JToggleButton();
  JToggleButton jToggleButton34 = new JToggleButton();
  JToggleButton jToggleButton33 = new JToggleButton();
  JToggleButton jToggleButton32 = new JToggleButton();
  JToggleButton jToggleButton31 = new JToggleButton();
  JToggleButton jToggleButton30 = new JToggleButton();
  JToggleButton jToggleButton29 = new JToggleButton();
  JToggleButton jToggleButton28 = new JToggleButton();
  JToggleButton jToggleButton27 = new JToggleButton();
  JToggleButton jToggleButton26 = new JToggleButton();
  JToggleButton jToggleButton25 = new JToggleButton();
  JToggleButton jToggleButton24 = new JToggleButton();
  JToggleButton jToggleButton23 = new JToggleButton();
  JToggleButton jToggleButton22 = new JToggleButton();
  JToggleButton jToggleButton21 = new JToggleButton();
  JToggleButton jToggleButton20 = new JToggleButton();
  GridLayout gridLayout3 = new GridLayout();
  GridLayout gridLayout2 = new GridLayout();
  GridLayout gridLayout1 = new GridLayout();
  JButton jButton3 = new JButton();
  JButton jButton2 = new JButton();
  JButton jButton1 = new JButton();
  JToggleButton jToggleButton19 = new JToggleButton();
  JToggleButton jToggleButton18 = new JToggleButton();
  JToggleButton jToggleButton17 = new JToggleButton();
  JToggleButton jToggleButton16 = new JToggleButton();
  JToggleButton jToggleButton15 = new JToggleButton();
  JToggleButton jToggleButton14 = new JToggleButton();
  JToggleButton jToggleButton13 = new JToggleButton();
  JToggleButton jToggleButton12 = new JToggleButton();
  JToggleButton jToggleButton11 = new JToggleButton();
  JToggleButton jToggleButton10 = new JToggleButton();
  Border border1;
  Border border2;
  TitledBorder titledBorder1;
  Border border3;
  TitledBorder titledBorder2;
  Border border4;
  JPanel jPanel4 = new JPanel();
  BorderLayout borderLayout11 = new BorderLayout();
  Border border5;
  Border border6;
  Border border7;
  TitledBorder titledBorder3;
  Border border8;
  FlowLayout flowLayout1 = new FlowLayout();
  Border border9;
  Border border10;
  Border border11;
  Border border12;
  Border border13;

  //Construct the frame
  public GraphExtentStyleEditor() {
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
    border1 = BorderFactory.createEmptyBorder(5,8,5,10);
    border2 = BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134));
    titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"�׵θ�");
    border3 = BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134));
    titledBorder2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"����");
    border4 = BorderFactory.createEmptyBorder(0,2,6,45);
    border5 = BorderFactory.createCompoundBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"�׵θ�"),BorderFactory.createEmptyBorder(0,5,0,3));
    border6 = BorderFactory.createCompoundBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134)),"����"),BorderFactory.createEmptyBorder(0,6,10,8));
    border7 = BorderFactory.createEtchedBorder(Color.white,new Color(134, 134, 134));
    titledBorder3 = new TitledBorder(border7,"����");
    border8 = BorderFactory.createEmptyBorder(3,0,0,0);
    border9 = BorderFactory.createEmptyBorder();
    border10 = BorderFactory.createEmptyBorder(1,0,1,0);
    border11 = BorderFactory.createEmptyBorder(1,0,1,0);
    border12 = BorderFactory.createEmptyBorder(1,0,1,0);
    border13 = BorderFactory.createEmptyBorder(3,5,5,0);
    borderLayout5.setVgap(1);
    contentPane = (JPanel) this.getContentPane();
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(435, 369));
    this.setTitle("�׸� ���� ����");
    jPanel24.setLayout(borderLayout20);
    jPanel23.setMinimumSize(new Dimension(50, 10));
    jPanel23.setPreferredSize(new Dimension(80, 0));
    jPanel23.setLayout(borderLayout18);
    jPanel22.setPreferredSize(new Dimension(147, 2));
    jPanel22.setLayout(flowLayout1);
    jPanel21.setPreferredSize(new Dimension(0, 36));
    jPanel21.setLayout(gridLayout3);
    jPanel20.setBorder(border9);
    jPanel20.setPreferredSize(new Dimension(0, 90));
    jPanel20.setLayout(gridLayout2);
    jPanel19.setLayout(borderLayout15);
    jPanel17.setBorder(border8);
    jPanel17.setPreferredSize(new Dimension(0, 36));
    jPanel17.setLayout(borderLayout13);
    jPanel16.setPreferredSize(new Dimension(0, 60));
    jPanel16.setLayout(borderLayout14);
    jPanel15.setLayout(borderLayout17);
    jPanel15.setPreferredSize(new Dimension(160, 0));
    jPanel14.setLayout(gridLayout1);
    jPanel14.setBorder(border13);
    jPanel14.setPreferredSize(new Dimension(11, 80));
    jPanel13.setPreferredSize(new Dimension(0, 55));
    jPanel13.setLayout(borderLayout10);
    jPanel11.setPreferredSize(new Dimension(0, 57));
    jPanel11.setLayout(borderLayout9);
    jPanel10.setLayout(borderLayout12);
    jLabel1.setBorder(BorderFactory.createEtchedBorder());
    jLabel1.setPreferredSize(new Dimension(145, 2));
    jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
    jLabel1.setVerticalAlignment(SwingConstants.TOP);
    jToggleButton9.setRequestFocusEnabled(false);
    jToggleButton9.setText("��Ÿ��(S):   ");
    jToggleButton9.setMnemonic('S');
    jToggleButton9.setBorder(null);
    jToggleButton9.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton8.setRequestFocusEnabled(false);
    jToggleButton8.setText("��(C):           ");
    jToggleButton8.setMnemonic('C');
    jToggleButton8.setBorder(null);
    jToggleButton8.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton7.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-06.gif")));
    jToggleButton7.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton7.setRequestFocusEnabled(false);
    jToggleButton6.setRequestFocusEnabled(false);
    jToggleButton6.setText("�β�(W):      ");
    jToggleButton6.setMnemonic('W');
    jToggleButton6.setBorder(null);
    jToggleButton6.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton5.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-04.gif")));
    jToggleButton5.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton5.setRequestFocusEnabled(false);
    jToggleButton4.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-02.gif")));
    jToggleButton4.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton4.setRequestFocusEnabled(false);
    jToggleButton3.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-39.gif")));
    jToggleButton3.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton3.setRequestFocusEnabled(false);
    jToggleButton2.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-01.gif")));
    jToggleButton2.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton2.setRequestFocusEnabled(false);
    jToggleButton1.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton1.setPreferredSize(new Dimension(65, 25));
    jToggleButton1.setBorder(null);
    jToggleButton1.setMnemonic('O');
    jToggleButton1.setVerticalTextPosition(SwingConstants.BOTTOM);
    jToggleButton1.setText("��(O):");
    jToggleButton1.setRequestFocusEnabled(false);
    jToggleButton1.setHorizontalAlignment(SwingConstants.LEFT);
    jToggleButton1.setVerticalAlignment(SwingConstants.BOTTOM);
    jRadioButton5.setMargin(new Insets(0, 0, 0, 0));
    jRadioButton5.setMnemonic('E');
    jRadioButton5.setText("����(E)");
    jRadioButton5.setRequestFocusEnabled(false);
    jRadioButton4.setMargin(new Insets(0, 0, 0, 0));
    jRadioButton4.setMnemonic('U');
    jRadioButton4.setText("�ڵ�(U)");
    jRadioButton4.setRequestFocusEnabled(false);
    jRadioButton3.setMargin(new Insets(0, 0, 0, 0));
    jRadioButton3.setMnemonic('N');
    jRadioButton3.setText("����(N)");
    jRadioButton3.setRequestFocusEnabled(false);
    jRadioButton2.setMargin(new Insets(0, 0, 0, 0));
    jRadioButton2.setText("����� ����");
    jRadioButton2.setRequestFocusEnabled(false);
    jRadioButton1.setMargin(new Insets(0, 0, 0, 0));
    jRadioButton1.setMnemonic('A');
    jRadioButton1.setText("�ڵ�(A)");
    jRadioButton1.setRequestFocusEnabled(false);
    jPanel9.setBorder(titledBorder3);
    jPanel9.setPreferredSize(new Dimension(12, 47));
    jPanel9.setLayout(borderLayout7);
    jPanel8.setLayout(borderLayout6);
    jPanel7.setLayout(borderLayout8);
    jPanel6.setPreferredSize(new Dimension(180, 0));
    jPanel6.setLayout(borderLayout5);
    jPanel3.setLayout(borderLayout4);
    jPanel2.setLayout(borderLayout3);
    jPanel2.setPreferredSize(new Dimension(0, 20));
    borderLayout17.setHgap(5);
    jPanel1.setLayout(borderLayout2);
    jPanel1.setPreferredSize(new Dimension(0, 305));
    jToggleButton60.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-48.gif")));
    jToggleButton60.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton60.setRequestFocusEnabled(false);
    jToggleButton59.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-44.gif")));
    jToggleButton59.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton59.setRequestFocusEnabled(false);
    jToggleButton58.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-47.gif")));
    jToggleButton58.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton58.setRequestFocusEnabled(false);
    jToggleButton57.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-42.gif")));
    jToggleButton57.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton57.setRequestFocusEnabled(false);
    jToggleButton56.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-51.gif")));
    jToggleButton56.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton56.setRequestFocusEnabled(false);
    jToggleButton55.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-46.gif")));
    jToggleButton55.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton55.setRequestFocusEnabled(false);
    jToggleButton54.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-50.gif")));
    jToggleButton54.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton54.setRequestFocusEnabled(false);
    jToggleButton53.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-43.gif")));
    jToggleButton53.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton53.setRequestFocusEnabled(false);
    jToggleButton52.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-52.gif")));
    jToggleButton52.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton52.setRequestFocusEnabled(false);
    jToggleButton51.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-45.gif")));
    jToggleButton51.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton51.setRequestFocusEnabled(false);
    jToggleButton50.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-53.gif")));
    jToggleButton50.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton50.setRequestFocusEnabled(false);
    jToggleButton49.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-49.gif")));
    jToggleButton49.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton49.setRequestFocusEnabled(false);
    jToggleButton48.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-54.gif")));
    jToggleButton48.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton48.setRequestFocusEnabled(false);
    jToggleButton47.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-55.gif")));
    jToggleButton47.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton47.setRequestFocusEnabled(false);
    jToggleButton46.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-56.gif")));
    jToggleButton46.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton46.setRequestFocusEnabled(false);
    jToggleButton45.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-41.gif")));
    jToggleButton45.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton45.setRequestFocusEnabled(false);
    jToggleButton44.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-32.gif")));
    jToggleButton44.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton44.setRequestFocusEnabled(false);
    jToggleButton43.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-31.gif")));
    jToggleButton43.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton43.setRequestFocusEnabled(false);
    jToggleButton42.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-30.gif")));
    jToggleButton42.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton42.setRequestFocusEnabled(false);
    jToggleButton41.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-21.gif")));
    jToggleButton41.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton41.setRequestFocusEnabled(false);
    jTabbedPane1.setPreferredSize(new Dimension(241, 74));
    jTabbedPane1.setRequestFocusEnabled(false);
    jToggleButton40.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-16.gif")));
    jToggleButton40.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton40.setRequestFocusEnabled(false);
    jPanel119.setBorder(border10);
    jPanel119.setPreferredSize(new Dimension(0, 29));
    jPanel119.setLayout(borderLayout117);
    jPanel118.setLayout(borderLayout116);
    jPanel117.setBorder(border11);
    jPanel117.setPreferredSize(new Dimension(0, 29));
    jPanel117.setLayout(borderLayout115);
    jPanel116.setLayout(borderLayout114);
    jPanel114.setLayout(borderLayout112);
    jPanel111.setBorder(border12);
    jPanel111.setPreferredSize(new Dimension(0, 29));
    jPanel111.setLayout(borderLayout19);
    jToggleButton39.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-33.gif")));
    jToggleButton39.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton39.setRequestFocusEnabled(false);
    jToggleButton38.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-34.gif")));
    jToggleButton38.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton38.setRequestFocusEnabled(false);
    jToggleButton37.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-28.gif")));
    jToggleButton37.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton37.setRequestFocusEnabled(false);
    jToggleButton36.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-24.gif")));
    jToggleButton36.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton36.setRequestFocusEnabled(false);
    jToggleButton35.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-09.gif")));
    jToggleButton35.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton35.setRequestFocusEnabled(false);
    jToggleButton34.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-29.gif")));
    jToggleButton34.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton34.setRequestFocusEnabled(false);
    jToggleButton33.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-27.gif")));
    jToggleButton33.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton33.setRequestFocusEnabled(false);
    jToggleButton32.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-26.gif")));
    jToggleButton32.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton32.setRequestFocusEnabled(false);
    jToggleButton31.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-25.gif")));
    jToggleButton31.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton31.setRequestFocusEnabled(false);
    jToggleButton30.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-19.gif")));
    jToggleButton30.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton30.setRequestFocusEnabled(false);
    jToggleButton29.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-36.gif")));
    jToggleButton29.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton29.setRequestFocusEnabled(false);
    jToggleButton28.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-23.gif")));
    jToggleButton28.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton28.setRequestFocusEnabled(false);
    jToggleButton27.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-22.gif")));
    jToggleButton27.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton27.setRequestFocusEnabled(false);
    jToggleButton26.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-20.gif")));
    jToggleButton26.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton26.setRequestFocusEnabled(false);
    jToggleButton25.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-12.gif")));
    jToggleButton25.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton25.setRequestFocusEnabled(false);
    jToggleButton24.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-35.gif")));
    jToggleButton24.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton24.setRequestFocusEnabled(false);
    jToggleButton23.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-40.gif")));
    jToggleButton23.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton23.setRequestFocusEnabled(false);
    jToggleButton22.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-37.gif")));
    jToggleButton22.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton22.setRequestFocusEnabled(false);
    jToggleButton21.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-13.gif")));
    jToggleButton21.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton21.setRequestFocusEnabled(false);
    jToggleButton20.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-18.gif")));
    jToggleButton20.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton20.setRequestFocusEnabled(false);
    gridLayout3.setColumns(8);
    gridLayout3.setHgap(3);
    gridLayout3.setRows(2);
    gridLayout3.setVgap(3);
    gridLayout2.setColumns(8);
    gridLayout2.setHgap(3);
    gridLayout2.setRows(5);
    gridLayout2.setVgap(3);
    gridLayout1.setColumns(1);
    gridLayout1.setRows(3);
    gridLayout1.setVgap(2);
    jButton3.setMaximumSize(new Dimension(115, 50));
    jButton3.setPreferredSize(new Dimension(115, 18));
    jButton3.setMargin(new Insets(2, 10, 2, 10));
    jButton3.setText("ä��� ȿ��(I)..");
    jButton2.setText("���");
    jButton1.setText("Ȯ��");
    jToggleButton19.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-15.gif")));
    jToggleButton19.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton19.setRequestFocusEnabled(false);
    jToggleButton18.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-17.gif")));
    jToggleButton18.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton18.setRequestFocusEnabled(false);
    jToggleButton17.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-14.gif")));
    jToggleButton17.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton17.setRequestFocusEnabled(false);
    jToggleButton16.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-10.gif")));
    jToggleButton16.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton16.setRequestFocusEnabled(false);
    jToggleButton15.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-38.gif")));
    jToggleButton15.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton15.setRequestFocusEnabled(false);
    jToggleButton14.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-11.gif")));
    jToggleButton14.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton14.setRequestFocusEnabled(false);
    jToggleButton13.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-08.gif")));
    jToggleButton13.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton13.setRequestFocusEnabled(false);
    jToggleButton12.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-05.gif")));
    jToggleButton12.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton12.setRequestFocusEnabled(false);
    jToggleButton11.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-07.gif")));
    jToggleButton11.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton11.setRequestFocusEnabled(false);
    jToggleButton10.setIcon(new ImageIcon(jcosmos.Utility.getResource("chartextent1-03.gif")));
    jToggleButton10.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton10.setRequestFocusEnabled(false);
    contentPane.setBorder(border1);
    jPanel8.setBorder(border5);
    jPanel10.setBorder(border6);
    jPanel10.setMinimumSize(new Dimension(182, 200));
    jPanel10.setPreferredSize(new Dimension(185, 250));
    jPanel3.setBorder(border4);
    jPanel4.setLayout(borderLayout11);
    jPanel4.setPreferredSize(new Dimension(0, 60));
    borderLayout4.setHgap(3);
    flowLayout1.setVgap(2);
    contentPane.add(jPanel2, BorderLayout.SOUTH);
    jPanel2.add(jPanel15, BorderLayout.EAST);
    jPanel15.add(jPanel23, BorderLayout.WEST);
    jPanel23.add(jButton1, BorderLayout.CENTER);
    jPanel15.add(jPanel24, BorderLayout.CENTER);
    jPanel24.add(jButton2, BorderLayout.CENTER);
    contentPane.add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jTabbedPane1, BorderLayout.CENTER);
    jTabbedPane1.add(jPanel3, "   ����   ");
    jPanel3.add(jPanel6, BorderLayout.WEST);
    jPanel6.add(jPanel8, BorderLayout.CENTER);
    jPanel8.add(jPanel13, BorderLayout.NORTH);
    jPanel13.add(jPanel11, BorderLayout.NORTH);
    jPanel11.add(jRadioButton1, BorderLayout.NORTH);
    jPanel11.add(jRadioButton2, BorderLayout.SOUTH);
    jPanel11.add(jRadioButton3, BorderLayout.CENTER);
    jPanel8.add(jPanel14, BorderLayout.CENTER);
    jPanel14.add(jPanel119, null);
    jPanel119.add(jToggleButton9, BorderLayout.WEST);
    jPanel119.add(jPanel118, BorderLayout.CENTER);
    jPanel118.add(jComboBox9, BorderLayout.CENTER);
    jPanel14.add(jPanel117, null);
    jPanel117.add(jToggleButton8, BorderLayout.WEST);
    jPanel117.add(jPanel116, BorderLayout.CENTER);
    jPanel116.add(jComboBox8, BorderLayout.CENTER);
    jPanel14.add(jPanel111, null);
    jPanel111.add(jToggleButton6, BorderLayout.WEST);
    jPanel111.add(jPanel114, BorderLayout.CENTER);
    jPanel114.add(jComboBox6, BorderLayout.CENTER);
    jPanel8.add(jPanel4, BorderLayout.SOUTH);
    jPanel6.add(jPanel9, BorderLayout.SOUTH);
    jPanel3.add(jPanel7, BorderLayout.CENTER);
    jPanel7.add(jPanel10, BorderLayout.CENTER);
    jPanel10.add(jPanel16, BorderLayout.NORTH);
    jPanel16.add(jRadioButton4, BorderLayout.NORTH);
    jPanel16.add(jRadioButton5, BorderLayout.CENTER);
    jPanel16.add(jToggleButton1, BorderLayout.SOUTH);
    jPanel10.add(jPanel17, BorderLayout.SOUTH);
    jPanel17.add(jButton3, BorderLayout.NORTH);
    jPanel10.add(jPanel19, BorderLayout.CENTER);
    jPanel19.add(jPanel20, BorderLayout.NORTH);
    jPanel20.add(jToggleButton2, null);
    jPanel20.add(jToggleButton4, null);
    jPanel20.add(jToggleButton10, null);
    jPanel20.add(jToggleButton5, null);
    jPanel20.add(jToggleButton12, null);
    jPanel20.add(jToggleButton7, null);
    jPanel20.add(jToggleButton11, null);
    jPanel20.add(jToggleButton13, null);
    jPanel20.add(jToggleButton35, null);
    jPanel20.add(jToggleButton16, null);
    jPanel20.add(jToggleButton14, null);
    jPanel20.add(jToggleButton25, null);
    jPanel20.add(jToggleButton21, null);
    jPanel20.add(jToggleButton17, null);
    jPanel20.add(jToggleButton19, null);
    jPanel20.add(jToggleButton40, null);
    jPanel20.add(jToggleButton18, null);
    jPanel20.add(jToggleButton20, null);
    jPanel20.add(jToggleButton30, null);
    jPanel20.add(jToggleButton26, null);
    jPanel20.add(jToggleButton41, null);
    jPanel20.add(jToggleButton27, null);
    jPanel20.add(jToggleButton28, null);
    jPanel20.add(jToggleButton36, null);
    jPanel20.add(jToggleButton31, null);
    jPanel20.add(jToggleButton32, null);
    jPanel20.add(jToggleButton33, null);
    jPanel20.add(jToggleButton37, null);
    jPanel20.add(jToggleButton34, null);
    jPanel20.add(jToggleButton42, null);
    jPanel20.add(jToggleButton43, null);
    jPanel20.add(jToggleButton44, null);
    jPanel20.add(jToggleButton39, null);
    jPanel20.add(jToggleButton38, null);
    jPanel20.add(jToggleButton24, null);
    jPanel20.add(jToggleButton29, null);
    jPanel20.add(jToggleButton22, null);
    jPanel20.add(jToggleButton15, null);
    jPanel20.add(jToggleButton3, null);
    jPanel20.add(jToggleButton23, null);
    jPanel19.add(jPanel21, BorderLayout.SOUTH);
    jPanel21.add(jToggleButton45, null);
    jPanel21.add(jToggleButton57, null);
    jPanel21.add(jToggleButton53, null);
    jPanel21.add(jToggleButton59, null);
    jPanel21.add(jToggleButton51, null);
    jPanel21.add(jToggleButton55, null);
    jPanel21.add(jToggleButton58, null);
    jPanel21.add(jToggleButton60, null);
    jPanel21.add(jToggleButton49, null);
    jPanel21.add(jToggleButton54, null);
    jPanel21.add(jToggleButton56, null);
    jPanel21.add(jToggleButton52, null);
    jPanel21.add(jToggleButton50, null);
    jPanel21.add(jToggleButton48, null);
    jPanel21.add(jToggleButton47, null);
    jPanel21.add(jToggleButton46, null);
    jPanel19.add(jPanel22, BorderLayout.WEST);
    jPanel22.add(jLabel1, null);
  }


}