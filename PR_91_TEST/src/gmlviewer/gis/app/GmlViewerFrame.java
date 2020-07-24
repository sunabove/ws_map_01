package gmlviewer.gis.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.BorderLayout;
import gmlviewer.gis.comp.BorderPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import gmlviewer.gis.viewer.MapViewer;
import gmlviewer.gis.viewer.MapViewerController;
import gmlviewer.gis.viewer.TreeLyrSelector;
import gmlviewer.gis.GmlViewerRegistry;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class GmlViewerFrame
    extends JFrame {
  JPanel contentPane;
  BorderLayout borderLayout1 = new BorderLayout();
  JMenuBar jMenuBar1 = new JMenuBar();
  JMenu jMenuFile = new JMenu();
  JMenuItem jMenuFileExit = new JMenuItem();
  JMenu jMenuHelp = new JMenu();
  JMenuItem jMenuHelpAbout = new JMenuItem();
  JLabel statusBar = new JLabel();
  JSplitPane mainSplitter = new JSplitPane();
  JPanel mainLeftPane = new BorderPane();

  JPanel mainRightPane = new BorderPane();
  BorderPane mapViewerPane = new BorderPane();
  TreeLyrSelector lyrSelector = new TreeLyrSelector();;
  /*
  JScrollPane treeSelectorScroller = new JScrollPane();
  JTree treeSelector = new JTree();
  */
  MapViewer mapViewer = new MapViewer();
  MapViewerController mapController = new MapViewerController( mapViewer );

  Border mainRightPaneBorder1 = BorderFactory.createEtchedBorder(EtchedBorder.RAISED,
      Color.white, new Color(148, 145, 140));
  Border mainRightPaneBorder2 = new TitledBorder(mainRightPaneBorder1, "레이어 선택");
  Border mainRightPaneBorder3 = BorderFactory.createCompoundBorder(mainRightPaneBorder2,
      BorderFactory.createEmptyBorder(2, 2, 2, 2));
  JMenuItem jMenuNewProject = new JMenuItem();
  JMenuItem jMenuOpenProject = new JMenuItem();
  JMenuItem jMenuSaveProject = new JMenuItem();
  JMenu jMenuLayer = new JMenu();
  JMenuItem jMenuAddLayer = new JMenuItem();
  JMenuItem jMenuRemoveLayer = new JMenuItem();

  public GmlViewerFrame() {
    try {
      setDefaultCloseOperation(EXIT_ON_CLOSE);
      jbInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Component initialization.
   *
   * @throws java.lang.Exception
   */
  private void jbInit() throws Exception {

    contentPane = (JPanel) getContentPane();
    contentPane.setLayout(borderLayout1);
    setSize(new Dimension( GmlViewerRegistry.FRAME_WIDTH, GmlViewerRegistry.FRAME_HEIGHT ));
    setTitle( GmlViewerRegistry.APP_NAME );
    statusBar.setText(" ");
    jMenuFile.setText("File");
    jMenuFileExit.setText("Exit");
    jMenuFileExit.addActionListener(new
                                    GmlViewerFrame_jMenuFileExit_ActionAdapter(this));
    jMenuHelp.setText("Help");
    jMenuHelpAbout.setText("About");
    jMenuHelpAbout.addActionListener(new
                                     GmlViewerFrame_jMenuHelpAbout_ActionAdapter(this));
    mainSplitter.setLeftComponent(mainLeftPane);
    mainSplitter.setRightComponent(mainRightPane);
    jMenuRemoveLayer.addActionListener(new
        GmlViewerFrame_jMenuRemoveLayer_actionAdapter(this));

    //mapViewerPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    //mapViewerPane.setBottomComponent(mapController);
    //mapViewerPane.setTopComponent(mapViewer);

    mapViewerPane.add(mapViewer, BorderLayout.CENTER );
    mapViewerPane.add(mapController, BorderLayout.SOUTH );

    mainRightPane.setBorder( mainRightPaneBorder3 );
    jMenuNewProject.setText("New Project");
    jMenuOpenProject.setText("Open Project");
    jMenuSaveProject.setText("Save Project");
    jMenuLayer.setText("Layer");
    jMenuAddLayer.setText("Add Layer");
    jMenuAddLayer.addActionListener(new
                                    GmlViewerFrame_jMenuAddLayer_actionAdapter(this));
    jMenuRemoveLayer.setText("Remove Layer");
    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(jMenuLayer);
//    jMenuFile.add(jMenuNewProject);
//    jMenuFile.add(jMenuOpenProject);
//    jMenuFile.add(jMenuSaveProject);
    jMenuFile.add(jMenuFileExit);
    jMenuBar1.add(jMenuHelp);
    jMenuHelp.add(jMenuHelpAbout);
    setJMenuBar(jMenuBar1);
    contentPane.add(statusBar, BorderLayout.SOUTH);
    contentPane.add(mainSplitter, java.awt.BorderLayout.CENTER);
    mainLeftPane.add(mapViewerPane, java.awt.BorderLayout.CENTER);
    mainRightPane.add( this.lyrSelector, java.awt.BorderLayout.CENTER);
    //treeSelectorScroller.getViewport().add(treeSelector);
    jMenuLayer.add(jMenuAddLayer);
    jMenuLayer.add(jMenuRemoveLayer);

    mainSplitter.setDividerLocation(488);
    //mapViewerPane.setDividerLocation(400);

    this.lyrSelector.setMapViewer( this.mapViewer );

    this.mapViewer.setStatusLabel( this.statusBar );

  }

  /**
   * File | Exit action performed.
   *
   * @param actionEvent ActionEvent
   */
  void jMenuFileExit_actionPerformed(ActionEvent actionEvent) {
    System.exit(0);
  }

  /**
   * Help | About action performed.
   *
   * @param actionEvent ActionEvent
   */
  void jMenuHelpAbout_actionPerformed(ActionEvent actionEvent) {
    GmlViewerFrame_AboutBox dlg = new GmlViewerFrame_AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                    (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.pack();
    dlg.setVisible(true);
  }

  public void jMenuAddLayer_actionPerformed(ActionEvent e) {
    MapViewer mapViewer = this.mapViewer;
    if( mapViewer != null ) {
      mapViewer.addLyr();
      mapViewer.repaint();
    }
  }

  public void jMenuRemoveLayer_actionPerformed(ActionEvent e) {

    MapViewer mapViewer = this.mapViewer;
    if( mapViewer != null ) {
      mapViewer.removeLyr();
      mapViewer.repaint();
    }

  }
}

class GmlViewerFrame_jMenuRemoveLayer_actionAdapter
    implements ActionListener {
  private GmlViewerFrame adaptee;
  GmlViewerFrame_jMenuRemoveLayer_actionAdapter(GmlViewerFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuRemoveLayer_actionPerformed(e);
  }
}

class GmlViewerFrame_jMenuAddLayer_actionAdapter
    implements ActionListener {
  private GmlViewerFrame adaptee;
  GmlViewerFrame_jMenuAddLayer_actionAdapter(GmlViewerFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuAddLayer_actionPerformed(e);
  }
}

class GmlViewerFrame_jMenuFileExit_ActionAdapter
    implements ActionListener {
  GmlViewerFrame adaptee;

  GmlViewerFrame_jMenuFileExit_ActionAdapter(GmlViewerFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent) {
    adaptee.jMenuFileExit_actionPerformed(actionEvent);
  }
}

class GmlViewerFrame_jMenuHelpAbout_ActionAdapter
    implements ActionListener {
  GmlViewerFrame adaptee;

  GmlViewerFrame_jMenuHelpAbout_ActionAdapter(GmlViewerFrame adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent actionEvent) {
    adaptee.jMenuHelpAbout_actionPerformed(actionEvent);
  }
}
