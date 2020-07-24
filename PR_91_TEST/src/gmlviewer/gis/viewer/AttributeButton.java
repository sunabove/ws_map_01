package gmlviewer.gis.viewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import gmlviewer.gis.model.*;
import gmlviewer.gis.comp.*;
import gmlviewer.gis.*;

public class AttributeButton
    extends JButton implements SenseComp {

  public AttributeButton(final Lyr layer,
                         final Component layerViewer) {

    super(" ");

    setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

    this.layer = layer;

    this.layerViewer = layerViewer;

    new SenseMouseListener(this);

    addMouseListener(new MouseAdapter() {

      public void mouseClicked(MouseEvent e) {
        whenMouseClicked(e);
      }

    });

  }

  private void whenMouseClicked(MouseEvent e) {
    final Component comp = this;

    int x = e.getX(), y = e.getY();

    Rectangle fillColorArea = getFillColorArea();

    if (fillColorArea.contains(x, y)) {

      Color color = ColorManager.getColorUsingColorChoooser(comp,
          "Filling Color", layer.getFillColor());

      layer.setFillColor(color);
      layerViewer.repaint();
      comp.repaint();
    }
    else {
      Color color = ColorManager.getColorUsingColorChoooser(comp, "Line Color",
          layer.getLineColor());

      layer.setLineColor(color);
      layerViewer.repaint();
      comp.repaint();
    }

  }

  public void paint(Graphics g) {

    super.paint(g);

    Graphics2D g2 = (Graphics2D) g;

    Color fillColor = layer.getFillColor();
    Color lineColor = layer.getLineColor();

    Rectangle lineColorArea = getLineColorArea();
    Rectangle fillColorArea = getFillColorArea();

    Color fg = getForeground();

    g2.setColor(lineColor);
    g2.fill(lineColorArea);
    g2.setColor(fg);
    g2.draw(lineColorArea);

    g2.setColor(fillColor);
    g2.fill(fillColorArea);
    g2.setColor(fg);
    g2.draw(fillColorArea);

  }

  public String[] getToolTipTexts() {

    return new String[] {
        GmlViewerRegistry.LAYER_AREA_COLOR_TOOLTIP, GmlViewerRegistry.LAYER_BORDER_COLOR_TOOLTIP};

  }

  public java.awt.Shape[] getSensitiveShapes() {

    return new java.awt.Shape[] {
        getFillColorArea(), getLineColorArea()};

  }

  public Rectangle getFillColorArea() {

    Lyr layer = this.layer;

    if (layer.getLyrType() == LyrType.LINE) { // poly line type
      return new Rectangle(0, 0, 0, 0);
    }

    Dimension size = getSize();
    Insets insets = getInsets();

    int x = insets.left;
    int y = insets.top;
    int w = size.width - insets.left - insets.right;
    int h = size.height - insets.top - insets.bottom;

    return new Rectangle( (int) (x + w / 4.0), (int) (y + h / 4.0),
                         (int) (w / 2.0), (int) (h / 2.0));

  }

  private Rectangle getLineColorArea() {

    Dimension size = getSize();
    Insets insets = getInsets();

    int x = insets.left;
    int y = insets.top;
    int w = size.width - insets.left - insets.right;
    int h = size.height - insets.top - insets.bottom;

    return new Rectangle(x, y, w, h);

  }

  private Lyr layer;

  private Component layerViewer;

}
