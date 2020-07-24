package htmleditor;

/**
 * Title:        HTML FREE EDITOR
 * Description:  Html Free Editor v2.0
 * Copyright:    Copyright (c) 2001
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import jcosmos.*;

public class StyleEditorPane extends JPanel {

  private int styleValue = -1;

  private JDialog dialog;

  JTabbedPane layoutTabPane = new JTabbedPane();

  JPanel bottomPanel = new JPanel();

  FlowLayout bottomPanelLayout = new FlowLayout();
  JButton OkBtn = new JButton();
  JButton CancelBtn = new JButton();

  JPanel layoutPane = new JPanel();

  TitledBorder titledBorder = BorderFactory.createTitledBorder( " 스타일 " );

  BorderLayout layoutPaneLayout = new BorderLayout();

  JPanel buttonPanel = new JPanel();

  FlowLayout buttonPanelLayout = new FlowLayout();

  JToggleButton frontStyleBtn = new JToggleButton();
  JToggleButton backStyleBtn = new JToggleButton();
  JToggleButton rectStyleBtn = new JToggleButton();

  JLabel backLabel = new JLabel();
  JLabel forwardLabel = new JLabel();
  JLabel rectLabel = new JLabel();

  public StyleEditorPane() {
    try {
      jbInit();
      setFonts();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  void jbInit() throws Exception {

    this.setLayout( new BorderLayout() );

    bottomPanel.setLayout(bottomPanelLayout);

    OkBtn.setText("확인");
    OkBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	OkBtn_actionPerformed(e);
      }
    });

    CancelBtn.setSelected(true);
    CancelBtn.setText("취소");
    CancelBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	CancelBtn_actionPerformed(e);
      }
    });

    bottomPanelLayout.setAlignment(FlowLayout.RIGHT);

    layoutPane.setLayout(layoutPaneLayout);

    buttonPanel.setLayout(buttonPanelLayout);

    frontStyleBtn.setIcon( Utility.getResourceImageIcon("front_style.gif") );
    frontStyleBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	frontStyleBtn_actionPerformed(e);
      }
    });

    backStyleBtn.setIcon(Utility.getResourceImageIcon("back_style.gif"));
    backStyleBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	backStyleBtn_actionPerformed(e);
      }
    });

    rectStyleBtn.setIcon(Utility.getResourceImageIcon("rect_style.gif"));
    rectStyleBtn.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
	rectStyleBtn_actionPerformed(e);
      }
    });

    backLabel.setText("뒤로");
    backLabel.setHorizontalAlignment( SwingConstants.CENTER );
    backLabel.setHorizontalTextPosition( SwingConstants.CENTER );

    forwardLabel.setText("앞으로");
    forwardLabel.setHorizontalAlignment( SwingConstants.CENTER );
    forwardLabel.setHorizontalTextPosition( SwingConstants.CENTER );

    rectLabel.setText("사각형");
    rectLabel.setHorizontalAlignment( SwingConstants.CENTER );
    rectLabel.setHorizontalTextPosition( SwingConstants.CENTER );

    Border buttonBorder = BorderFactory.createEmptyBorder(2, 2, 2, 2);

    frontStyleBtn.setBorder( buttonBorder );
    backStyleBtn.setBorder( buttonBorder );
    rectStyleBtn.setBorder( buttonBorder );

    Border buttonPanelBorder = BorderFactory.createEmptyBorder( 10, 0, 10, 0 );
    buttonPanel.setBorder( buttonPanelBorder );
    buttonPanelLayout.setHgap( 45 );

    this.add(layoutTabPane, BorderLayout.CENTER);
    this.add(bottomPanel,  BorderLayout.SOUTH);

    bottomPanel.add(OkBtn, null);
    bottomPanel.add(CancelBtn, null);

    layoutTabPane.add(layoutPane,     "레이아웃");

    layoutPane.setBorder( titledBorder );

    layoutPane.add(buttonPanel, BorderLayout.CENTER);

    JPanel rectStylePane = new JPanel();

    rectStylePane.setLayout( new BorderLayout( 0, 10 ) );

    rectStylePane.add( rectStyleBtn, BorderLayout.CENTER );
    rectStylePane.add( rectLabel, BorderLayout.SOUTH );

    JPanel backStylePane = new JPanel();

    backStylePane.setLayout( new BorderLayout( 0, 10 ) );

    backStylePane.add( backStyleBtn, BorderLayout.CENTER );
    backStylePane.add( backLabel, BorderLayout.SOUTH );

    JPanel frontStylePane = new JPanel();

    frontStylePane.setLayout( new BorderLayout( 0, 10 ) );

    frontStylePane.add( frontStyleBtn, BorderLayout.CENTER );
    frontStylePane.add( forwardLabel, BorderLayout.SOUTH );

    buttonPanel.add(rectStylePane, null);
    buttonPanel.add(backStylePane, null);
    buttonPanel.add(frontStylePane, null);

  }

  private void setFonts() {

    Font font = FontManager.getFont( "Serif", Font.PLAIN, 14 );

    layoutTabPane.setFont( font );
    layoutPane.setFont( font );
    backLabel.setFont( font );
    rectLabel.setFont( font );
    forwardLabel.setFont( font );
    OkBtn.setFont( font );
    CancelBtn.setFont( font );
    titledBorder.setTitleFont( font );

  }

  void OkBtn_actionPerformed(ActionEvent e) {

    dialog.setVisible( false );

  }

  void CancelBtn_actionPerformed(ActionEvent e) {

    styleValue = -1;

    dialog.setVisible( false );

  }



  void rectStyleBtn_actionPerformed(ActionEvent e) {

    styleValue = ImageElement.RECT;

  }

  void backStyleBtn_actionPerformed(ActionEvent e) {

    styleValue = ImageElement.BACK_TEXT;

  }

  void frontStyleBtn_actionPerformed(ActionEvent e) {

    styleValue = ImageElement.FRONT_TEXT;

  }

  public static int getStyle(final Component comp, final String title, int refStyleValue) {

      final JDialog editorDia = new JDialog( Utility.getJFrame( comp ), title );

      editorDia.setSize( 400, 250 );

      final Container con = editorDia.getContentPane();

      con.setLayout( new BorderLayout() );

      final StyleEditorPane styleEditorPane = new StyleEditorPane();

      con.add( styleEditorPane, BorderLayout.CENTER );

      editorDia.setModal( true );

      styleEditorPane.dialog = editorDia;

      editorDia.setLocationRelativeTo( comp );

      editorDia.show();

      return styleEditorPane.styleValue;

  }

}