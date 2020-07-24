package gmlviewer.gis.viewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gmlviewer.gis.model.*;
import gmlviewer.gis.comp.*;
import gmlviewer.gis.map.*;

/**
 * JLayerList �� ���� ������Ʈ.
 * �ϳ��� ���̾��� ����� ���� ��� ���θ� �����Ѵ�.
 */

public class LayerListItem extends BorderPane implements ItemListener {


  public LayerListItem( Lyr layer, Component layerViewer ) {

     this.layer = layer;
     this.layerViewer = layerViewer;

     // ���̾� �¿��� üũ�ڽ�
     JCheckBox onOffCB = this.createCheckGBox( layer );
     add( onOffCB, BorderLayout.CENTER );
     onOffCB.setToolTipText( layer.getName() );
     onOffCB.addItemListener( this );
     // ��. ���̾� �¿��� üũ�ڽ�

     // �Ӽ� ��ư
     AttributeButton attBtn = new AttributeButton( layer, layerViewer );
     add( attBtn, BorderLayout.EAST );
     // ��. �Ӽ� ��ư

  }

  private JCheckBox createCheckGBox( final Lyr layer ) {
     return new JCheckBox( layer.getName(), layer.isSelected() ) {
       public void paint( Graphics g) {
         if( isSelected() != layer.isSelected() ) {
           super.paint( g );
           super.setSelected( layer.isSelected() );
         } else {
           super.paint(g);
         }
       }
     };
  }

  public void itemStateChanged(ItemEvent e) {

     Object source = e.getSource();
     if( source instanceof JCheckBox ) {
       JCheckBox onOffCB = (JCheckBox) source;
       layer.setSelected(!layer.isSelected());
       layerViewer.repaint();
     }
  }

  private Lyr layer;
  private Component layerViewer;

}
