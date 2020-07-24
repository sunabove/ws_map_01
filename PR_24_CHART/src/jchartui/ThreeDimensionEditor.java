//package com.techdigm.chart.editor;
package jchartui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import jchart.*;
import jcosmos.*;
import jcosmos.Utility;

import java.util.Locale;

//public class ThreeDimensionEditor extends JDialog {
public class ThreeDimensionEditor extends Editor {

  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();
  JPanel jPanel1 = new JPanel();
  JPanel jPanel2 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  GridLayout gridLayout1 = new GridLayout();
  JButton defaultValueBtn = new JButton();
  JButton applyBtn = new JButton();
  JButton closeBtn = new JButton();
  JButton verifyBtn = new JButton();
  JButton jButton5 = new JButton();
  Border border1;
  JPanel jPanel3 = new JPanel();
  JPanel jPanel4 = new JPanel();
  JPanel jPanel5 = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  BorderLayout borderLayout4 = new BorderLayout();
  BorderLayout borderLayout5 = new BorderLayout();
  Border border2;
  JPanel jPanel6 = new JPanel();
  JPanel jPanel7 = new JPanel();
  GridLayout gridLayout2 = new GridLayout();
  JButton eyeUpBtn = new JButton();
  JButton eyeDownBtn = new JButton();
  GridLayout gridLayout3 = new GridLayout();
  JToggleButton jToggleButton1 = new JToggleButton();
  JTextField phiTF = new JTextField();
  JToggleButton jToggleButton2 = new JToggleButton();
  JTextField thetaTF = new JTextField();
  Border border3;
  Border border4;
  JPanel jPanel8 = new JPanel();
  JPanel jPanel9 = new JPanel();
  BorderLayout borderLayout6 = new BorderLayout();
  GridLayout gridLayout4 = new GridLayout();
  JButton eyeLeftBtn = new JButton();
  JButton eyeRightBtn = new JButton();
  Border border5;
  Canvas3D canvas3D = new Canvas3D();
  TitledBorder titledBorder1;
  JPanel jPanel10 = new JPanel();
  JPanel jPanel11 = new JPanel();
  JPanel jPanel12 = new JPanel();
  GridLayout gridLayout5 = new GridLayout();
  Border border6;
  Border border7;
  GridLayout gridLayout6 = new GridLayout();
  Border border8;
  GridLayout gridLayout7 = new GridLayout();
  JCheckBox autoSizeCbx = new JCheckBox();
  JPanel jPanel13 = new JPanel();
  BorderLayout borderLayout7 = new BorderLayout();
  JToggleButton jToggleButton5 = new JToggleButton();
  JPanel jPanel14 = new JPanel();
  JLabel jLabel1 = new JLabel();
  BorderLayout borderLayout8 = new BorderLayout();
  JTextField itemAxisRatioTF = new JTextField();
  JLabel jLabel2 = new JLabel();
  Border border9;
  Border border10;

//  private DataSheet dataSheet;

  public Locale currentLocale;

  //Construct the frame
  public ThreeDimensionEditor() {
//  public ThreeDimensionEditor(DataSheet dataSheet) {
//	super(jcalc.JCalc.frame, true);
//	this.dataSheet = dataSheet;

	//맨처음 생성되면 현재 locale정보를 저장해 놓는다.
//	this.currentLocale = jcalc.JCalcResource.getLocale();
	this.currentLocale = jchartui.JCalcResource.getLocale();

    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    this.setResizable(false);

    visualizeThetaPhiAndRho();

//	this.setLocationRelativeTo(jcalc.JCalc.frame);

  }

  //Component initialization
  private void jbInit() throws Exception  {
    contentPane = (JPanel) this.getContentPane();
    border1 = BorderFactory.createEmptyBorder(7,5,5,5);
    border2 = BorderFactory.createEmptyBorder(5,5,0,5);
    border3 = BorderFactory.createEmptyBorder(5,0,0,4);
    border4 = BorderFactory.createEmptyBorder(0,0,5,0);
    border5 = BorderFactory.createEmptyBorder(0,4,8,0);
    titledBorder1 = new TitledBorder("");
    border6 = BorderFactory.createEmptyBorder(0,0,5,100);
    border7 = BorderFactory.createEmptyBorder(0,7,5,0);
    border8 = BorderFactory.createEmptyBorder(0,0,0,115);
    border9 = BorderFactory.createEmptyBorder(0,0,0,5);
    border10 = BorderFactory.createEmptyBorder(3,0,0,0);
    contentPane.setLayout(borderLayout1);
//    this.setSize(new Dimension(807, 429));
    this.setSize(new Dimension(407, 229));
    this.setTitle("ThreeD");
    jPanel1.setLayout(gridLayout1);
    jPanel2.setLayout(borderLayout3);
    jPanel1.setBorder(border1);
    jPanel1.setPreferredSize(new Dimension(0, 33));
    gridLayout1.setColumns(5);
    gridLayout1.setHgap(5);
    defaultValueBtn.setMargin(new Insets(2, 0, 2, 0));
    defaultValueBtn.setMnemonic('D');
    defaultValueBtn.setText("기본값(D)");
    applyBtn.setMnemonic('A');
    applyBtn.setText("적용(A)");
    applyBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	applyBtn_actionPerformed(e);
      }
    });
    closeBtn.setText("닫기");
    closeBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	closeBtn_actionPerformed(e);
      }
    });
    verifyBtn.setText("확인");
    verifyBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	verifyBtn_actionPerformed(e);
      }
    });
    jButton5.setBorder(null);
    jPanel3.setLayout(borderLayout2);
    jPanel4.setLayout(borderLayout4);
    jPanel5.setLayout(borderLayout5);
    jPanel3.setBorder(border9);
    jPanel3.setPreferredSize(new Dimension(80, 0));
    jPanel4.setBorder(border7);
    jPanel4.setPreferredSize(new Dimension(188, 0));
    jPanel2.setBorder(border2);
    jPanel6.setLayout(gridLayout2);
    jPanel6.setBorder(border4);
    jPanel6.setPreferredSize(new Dimension(0, 65));
    eyeUpBtn.setRequestFocusEnabled(false);
    eyeUpBtn.setIcon(new ImageIcon(Utility.getResource("3d-01.gif")));
    eyeUpBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	eyeUpBtn_actionPerformed(e);
      }
    });
    eyeDownBtn.setRequestFocusEnabled(false);
    eyeDownBtn.setIcon(new ImageIcon(Utility.getResource("3d-02.gif")));
    eyeDownBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	eyeDownBtn_actionPerformed(e);
      }
    });
    gridLayout2.setRows(2);
    gridLayout2.setColumns(1);
    gridLayout2.setVgap(5);
    jPanel7.setLayout(gridLayout3);
    gridLayout3.setRows(5);
    gridLayout3.setColumns(1);
    gridLayout3.setVgap(3);
    jToggleButton1.setBorder(null);
    jToggleButton1.setMnemonic('E');
    jToggleButton1.setText("상하 회전(E):");
    jToggleButton1.setRequestFocusEnabled(false);
    jToggleButton1.setHorizontalAlignment(SwingConstants.LEFT);
    jToggleButton2.setBorder(null);
    jToggleButton2.setMnemonic('R');
    jToggleButton2.setText("좌우 회전(R):");
    jToggleButton2.setRequestFocusEnabled(false);
    jToggleButton2.setHorizontalAlignment(SwingConstants.LEFT);
    jPanel7.setBorder(border3);
    jPanel9.setLayout(borderLayout6);
    jPanel8.setLayout(gridLayout4);
    jPanel8.setPreferredSize(new Dimension(0, 45));
    gridLayout4.setColumns(2);
    gridLayout4.setHgap(5);
    eyeLeftBtn.setRequestFocusEnabled(false);
    eyeLeftBtn.setIcon(new ImageIcon(Utility.getResource("3d-05.gif")));
    eyeLeftBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	eyeLeftBtn_actionPerformed(e);
      }
    });
    eyeRightBtn.setRequestFocusEnabled(false);
    eyeRightBtn.setIcon(new ImageIcon(Utility.getResource("3d-06.gif")));
    eyeRightBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	eyeRightBtn_actionPerformed(e);
      }
    });
    jPanel5.setBorder(border5);
    jPanel5.setPreferredSize(new Dimension(8, 75));
    canvas3D.setBackground(Color.white);
    canvas3D.setBorder(BorderFactory.createLineBorder(Color.black));
    canvas3D.setRequestFocusEnabled(false);
    borderLayout5.setVgap(8);
    phiTF.setMinimumSize(new Dimension(4, 15));
    phiTF.setPreferredSize(new Dimension(4, 15));
    jPanel10.setLayout(gridLayout5);
    jPanel12.setLayout(gridLayout6);
    jPanel11.setLayout(gridLayout7);
    jPanel10.setBorder(border6);
    jPanel10.setPreferredSize(new Dimension(0, 65));
    jPanel11.setPreferredSize(new Dimension(0, 55));
    gridLayout5.setRows(2);
    gridLayout5.setColumns(1);
    gridLayout5.setVgap(5);
    gridLayout6.setRows(2);
    jPanel12.setBorder(border8);
    jPanel12.setPreferredSize(new Dimension(171, 65));
    gridLayout7.setRows(3);
    autoSizeCbx.setText("자동 크기 조정(S)");
    jPanel13.setLayout(borderLayout7);
    jToggleButton5.setMargin(new Insets(0, 0, 0, 0));
    jToggleButton5.setBorder(null);
    jToggleButton5.setMnemonic('I');
    jToggleButton5.setText("높이(I):");
    jToggleButton5.setRequestFocusEnabled(false);
    jLabel1.setMinimumSize(new Dimension(20, 18));
    jLabel1.setPreferredSize(new Dimension(40, 18));
    jLabel1.setRequestFocusEnabled(false);
    jLabel1.setText(" 항목 축 길이의");
    jPanel14.setLayout(borderLayout8);
    jPanel14.setMinimumSize(new Dimension(50, 0));
    jPanel14.setPreferredSize(new Dimension(60, 0));
    jLabel2.setText("%");
    contentPane.setBorder(border10);
    contentPane.add(jPanel1, BorderLayout.SOUTH);
    jPanel1.add(defaultValueBtn, null);
    jPanel1.add(jButton5, null);
    jPanel1.add(verifyBtn, null);
    jPanel1.add(closeBtn, null);
    jPanel1.add(applyBtn, null);
    contentPane.add(jPanel2, BorderLayout.CENTER);
    jPanel2.add(jPanel3, BorderLayout.WEST);
    jPanel3.add(jPanel6, BorderLayout.NORTH);
    jPanel6.add(eyeUpBtn, null);
    jPanel6.add(eyeDownBtn, null);
    jPanel3.add(jPanel7, BorderLayout.CENTER);
    jPanel7.add(jToggleButton1, null);
    jPanel7.add(phiTF, null);
    jPanel7.add(jToggleButton2, null);
    jPanel7.add(thetaTF, null);
    jPanel2.add(jPanel4, BorderLayout.EAST);
    jPanel4.add(jPanel10, BorderLayout.NORTH);
    jPanel4.add(jPanel11, BorderLayout.SOUTH);
    jPanel11.add(autoSizeCbx, null);
    jPanel11.add(jPanel13, null);
    jPanel13.add(jToggleButton5, BorderLayout.WEST);
    jPanel13.add(jPanel14, BorderLayout.EAST);
    jPanel14.add(itemAxisRatioTF, BorderLayout.CENTER);
    jPanel14.add(jLabel2, BorderLayout.EAST);
    jPanel13.add(jLabel1, BorderLayout.CENTER);
    jPanel4.add(jPanel12, BorderLayout.CENTER);
    jPanel2.add(jPanel5, BorderLayout.CENTER);
    jPanel5.add(jPanel8, BorderLayout.SOUTH);
    jPanel8.add(eyeLeftBtn, null);
    jPanel8.add(eyeRightBtn, null);
    jPanel5.add(jPanel9, BorderLayout.CENTER);
    jPanel9.add(canvas3D, BorderLayout.CENTER);

  }

  void visualizeThetaPhiAndRho() {

      final Canvas3D canvas3D = this.canvas3D;

      final int thetaAngle = canvas3D.getThetaAngle();

      final int phiAngle = canvas3D.getPhiAngle();

      thetaTF.setText( "" + thetaAngle );
      phiTF.setText( "" + phiAngle );

  }

  void eyeUpBtn_actionPerformed(ActionEvent e) {

      canvas3D.setEyeUp();

      visualizeThetaPhiAndRho();

  }

  void eyeDownBtn_actionPerformed(ActionEvent e) {

      canvas3D.setEyeDown();

      visualizeThetaPhiAndRho();

  }

  void eyeLeftBtn_actionPerformed(ActionEvent e) {

      canvas3D.setEyeLeft();

      visualizeThetaPhiAndRho();

  }

  void eyeRightBtn_actionPerformed(ActionEvent e) {

      canvas3D.setEyeRight();

      visualizeThetaPhiAndRho();

  }

  public void show() {

      Utility.debug( this, "SHOWING THREE DIM EDITOR" );

      ChartElement sce = super.getSelectedChartElementOnTheCurrentSpreadSheet();

      if( sce == null ) {

	  super.show();

	  return;

      }

      final PictureExtent pe = sce.getChart().getPictureExtent();

      double theta = pe.getThetaRadian();
      double phi   = pe.getPhiRadian();

      double thetaAngle = Unit.convertRadianToDegree( theta );
      double phiAngle = Unit.convertRadianToDegree( phi );

      final Canvas3D canvas3D = this.canvas3D;

      canvas3D.setChartType( sce.getChartType() );

      canvas3D.setThetaAndPhiAngle( (int) thetaAngle, (int) phiAngle );

      super.show();

  }

  void closeBtn_actionPerformed(ActionEvent e) {

    setVisible( false );

  }

  void verifyBtn_actionPerformed(ActionEvent e) {

    setVisible( false );

    applyCanvas3DAngleToPictureExtent();

  }

  void applyBtn_actionPerformed(ActionEvent e) {

     applyCanvas3DAngleToPictureExtent();

  }

  private void applyCanvas3DAngleToPictureExtent() {

     ChartElement sce = super.getSelectedChartElementOnTheCurrentSpreadSheet();

      if( sce == null ) {

	  return;

      }

      final Chart chart = sce.getChart();

      final PictureExtent pe = chart.getPictureExtent();

      Canvas3D canvas3D = this.canvas3D;

      int thetaAngle = canvas3D.getThetaAngle();

      int phiAngle = canvas3D.getPhiAngle();

      double theta = Unit.convertDegreeToRadian( thetaAngle );

      double phi = Unit.convertDegreeToRadian( phiAngle );

      pe.setThetaAndPhiRadian( theta, phi );

      GraphExtent ge = (GraphExtent) chart.getChartElement( GraphExtent.class );

      ge.justifyThreeDimFrame( pe );

      SpreadSheet.getCurrentSpreadSheet().repaint();

  }

}