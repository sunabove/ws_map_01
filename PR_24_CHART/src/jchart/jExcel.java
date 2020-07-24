
/**
 * Title:        java chart project<p>
 * Description:  jchart v1.0<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package jchart;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class jExcel extends JFrame {
  String buildNum = "002";
  JPanel contentPane;
  JMenuBar menuBar1 = new JMenuBar();
  JMenu menuFile = new JMenu();
  JMenuItem exit = new JMenuItem();
  JMenu menuHelp = new JMenu();
  JMenuItem menuHelpAbout = new JMenuItem();
  JLabel statusBar = new JLabel();
  BorderLayout borderLayout1 = new BorderLayout();
  JMenuItem newFile = new JMenuItem();
  JMenuItem open = new JMenuItem();
  JMenuItem save = new JMenuItem();
  JMenuItem saveAs = new JMenuItem();
  JScrollPane jScrollPane1 = new JScrollPane();
  SpreadSheet spreadSheet = new SpreadSheet( new SpreadSheetTableModel() );
  JMenu insert = new JMenu();
  JMenuItem chart = new JMenuItem();
  JMenuItem jMenuItem15 = new JMenuItem();
  JMenuItem jMenuItem14 = new JMenuItem();
  JPopupMenu jPopupMenu6 = new JPopupMenu();
  JMenu jMenu1 = new JMenu();
  JMenuItem copy = new JMenuItem();
  JMenuItem paste = new JMenuItem();

  //Construct the frame
  public jExcel() {
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
    contentPane.setLayout(borderLayout1);
    this.setSize(new Dimension(786, 709));
    this.setTitle("  jChart v1.0" + buildNum );
    statusBar.setToolTipText("");
    statusBar.setText("  jChart v1.0" + buildNum );
    menuFile.setText("File");
    exit.setText("Exit");
    exit.addMouseListener(new java.awt.event.MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        exit_mouseClicked(e);
      }
    });
    exit.addActionListener(new ActionListener()  {

      public void actionPerformed(ActionEvent e) {
        fileExit_actionPerformed(e);
      }
    });
    menuHelp.setText("Help");
    menuHelpAbout.setText("About");
    menuHelpAbout.addActionListener(new ActionListener()  {

      public void actionPerformed(ActionEvent e) {
        helpAbout_actionPerformed(e);
      }
    });
    newFile.setText("New");
    open.setText("Open");
    open.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        open_actionPerformed(e);
      }
    });
    save.setText("Save");
    save.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        save_actionPerformed(e);
      }
    });
    saveAs.setText("Save As");
    saveAs.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        saveAs_actionPerformed(e);
      }
    });
    insert.setText("Insert");
    chart.setText("Chart");
    chart.addActionListener(new java.awt.event.ActionListener() {

      public void actionPerformed(ActionEvent e) {
        chart_actionPerformed(e);
      }
    });
    spreadSheet.setBackground(new Color(125, 243, 98));
    spreadSheet.setMinimumSize(new Dimension(1, 1));
    spreadSheet.setPreferredSize(new Dimension(380, 700));
    jMenuItem15.setMnemonic('A');
    jMenuItem15.setText("지우기(A)");
    jMenuItem14.setMnemonic('O');
    jMenuItem14.setText("축 제목 서식(O)...");
    jMenu1.setText("Edit");
    copy.setText("Copy");
    copy.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        copy_actionPerformed(e);
      }
    });
    paste.setText("Paste");
    paste.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(ActionEvent e) {
        paste_actionPerformed(e);
      }
    });
    menuFile.add(newFile);
    menuFile.add(open);
    menuFile.add(save);
    menuFile.add(saveAs);
    menuFile.add(exit);
    menuHelp.add(menuHelpAbout);
    menuBar1.add(menuFile);
    menuBar1.add(jMenu1);
    menuBar1.add(insert);
    menuBar1.add(menuHelp);
    this.setJMenuBar(menuBar1);
    contentPane.add(statusBar, BorderLayout.SOUTH);
    contentPane.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(spreadSheet, null);
    insert.add(chart);
    jPopupMenu6.add(jMenuItem14);
    jPopupMenu6.addSeparator();
    jPopupMenu6.add(jMenuItem15);
    jMenu1.add(copy);
    jMenu1.add(paste);
  }

  //File | Exit action performed
  public void fileExit_actionPerformed(ActionEvent e) {
    System.exit(0);
  }

  //Help | About action performed
  public void helpAbout_actionPerformed(ActionEvent e) {
    jExcelAboutBox dlg = new jExcelAboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x, (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.show();
  }

  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      fileExit_actionPerformed(null);
    }
  }

  void exit_mouseClicked(MouseEvent e) {
     System.exit( 0 );
  }

  void open_actionPerformed(ActionEvent e) {
     this.spreadSheet.open( spreadSheet );
  }

  void save_actionPerformed(ActionEvent e) {
     this.spreadSheet.save( spreadSheet );
  }

  void saveAs_actionPerformed(ActionEvent e) {
     this.spreadSheet.saveAs( spreadSheet );
  }

  void chart_actionPerformed(ActionEvent e) {
    this.spreadSheet.addChart();
    this.spreadSheet.repaint();
  }

  void copy_actionPerformed(ActionEvent e) {

    this.spreadSheet.copySelectedChart();

  }

  void paste_actionPerformed(ActionEvent e) {

    this.spreadSheet.pasteCopiedChart();

  }
}