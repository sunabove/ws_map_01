package gmlviewer.gis.viewer;

import java.util.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import gmlviewer.gis.*;
import gmlviewer.gis.kernel.*;
import gmlviewer.gis.map.*;

public class TreeLyrSelector
    extends JPanel implements KernelInterface {
  BorderLayout borderLayout1 = new BorderLayout();
  JScrollPane scrollPane = new JScrollPane();

  CodeTable codeTable = new CodeTable();

  DefaultMutableTreeNode top = new DefaultMutableTreeNode(codeTable.getTopCode());

  JTree tree = new JTree(top);

  public TreeLyrSelector() {
    try {
      jbInit();
      multicsInit();
    }
    catch (Exception exception) {
      exception.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    this.add(scrollPane, java.awt.BorderLayout.CENTER);

    scrollPane.getViewport().add(tree);

    this.initCodeTable();

  }

  private void multicsInit() throws Exception {

    JTree tree = this.tree;

    tree.setCellRenderer(new CodeCellRenderer());
    //tree.setEditable(true);

    tree.addTreeSelectionListener(new TreeSelectionListener() {
      public void valueChanged(TreeSelectionEvent e) {
        whenTreeNodeSelected(e);
      }
    });

    tree.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        whenMouseClicked(e);
      }
    });
  }

  private void debug(String msg) {
    debug.debug(this, msg);
  }

  private Code preSelCode = null;

  private MapViewer mapViewer;

  private void whenMouseClicked(MouseEvent e) {

    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
        tree.getLastSelectedPathComponent();

    if (node != null) {
      Object obj = node.getUserObject();

      if (obj instanceof Code) {
        Code code = (Code) obj;

        code.setSelected(true);

        GmlViewerRegistry.CURR_CODE = code;

        if (preSelCode != null) {

          if (code != preSelCode) {

            preSelCode.setSelected(false);

            if (this.mapViewer != null) {
              this.mapViewer.repaint();
            }

          }

        }
        else {

          if (this.mapViewer != null) {
            this.mapViewer.repaint();
          }

        }

        preSelCode = code;

        tree.repaint();

      }
    }

  }

  private void whenMouseClickedOld(MouseEvent e) {

    int clickCount = e.getClickCount();
    boolean isCtrlDown = e.isControlDown();

    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
        tree.getLastSelectedPathComponent();

    if (node != null) {
      Object obj = node.getUserObject();

      if (obj instanceof Code) {
        Code code = (Code) obj;

        boolean isSel = code.isSelected();
        if (node.isLeaf()) {
          code.setSelected(!isSel);
          tree.repaint();
        }
        else if (clickCount == 2 || isCtrlDown) {
          code.setSelected(!isSel);
          tree.repaint();
        }

      }
    }

  }

  private void whenTreeNodeSelected(TreeSelectionEvent e) {

    DefaultMutableTreeNode node = (DefaultMutableTreeNode)
        tree.getLastSelectedPathComponent();

    if (node != null) {
      Object obj = node.getUserObject();

      if (node.isLeaf()) {
        debug("SEL LEAF CODE = " + obj);
      }
      else {
        debug("SEL BRANCH CODE = " + obj);
      }
    }
  }

  public void initCodeTable() {

    top.removeAllChildren();

    Code code = codeTable.getTopCode();

    ArrayList subCodes = code.getSubCodes();

    if (subCodes != null) {

      Code subCode;
      for (int i = 0, len = subCodes.size(); i < len; i++) {
        subCode = (Code) subCodes.get(i);
        if (subCode != null) {
          makeTree(top, subCode);
        }
      }
    }

    this.tree.validate();

  }

  public void makeTree(DefaultMutableTreeNode parent, Code code) {

    DefaultMutableTreeNode node = new DefaultMutableTreeNode(code);

    parent.add(node);

    ArrayList subCodes = code.getSubCodes();

    if (subCodes != null) {

      Code subCode;
      for (int i = 0, len = subCodes.size(); i < len; i++) {
        subCode = (Code) subCodes.get(i);
        if (subCode != null) {
          makeTree(node, subCode);
        }
      }
    }

  }

  public void test() {
    DefaultTreeCellRenderer renderer =
        (DefaultTreeCellRenderer) tree.getCellRenderer();
    renderer.setClosedIcon(new ImageIcon("door.closed.gif"));
    renderer.setOpenIcon(new ImageIcon("door.open.gif"));
    renderer.setLeafIcon(new ImageIcon("world.gif"));

  }

  private class CodeCellRenderer
      extends DefaultTreeCellRenderer {
    //ImageIcon tutorialIcon = new ImageIcon("images/middle.gif");

    private Code code;

    public CodeCellRenderer() {
      this.code = null;
    }

    private void setCompStyle(Code code) {
      boolean isSel = code.isSelected();
      Color bg = isSel ? Color.red : Color.gray;
      //super.setBackground(bg);
      super.setBackgroundSelectionColor(bg);
      super.setBackgroundNonSelectionColor(bg);
      //System.out.println("RENDER OBJ = " + code);

    }

    public Component getTreeCellRendererComponent(
        JTree tree,
        Object value,
        boolean sel,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus) {

      super.getTreeCellRendererComponent(
          tree, value, sel,
          expanded, leaf, row,
          hasFocus);
      DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
      Object obj = node.getUserObject();
      if (obj instanceof Code) {
        Code code = (Code) obj;
        if (this.code == null) {
          this.code = code;
        }
        setCompStyle(code);
      }
      else {
        //System.out.println("" + value.getClass());
      }
      return this;
    }

  }

  public MapViewer getMapViewer() {
    return mapViewer;
  }

  public void setMapViewer(MapViewer mapViewer) {
    this.mapViewer = mapViewer;
  }

}
