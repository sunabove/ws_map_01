package gmlviewer.gis.viewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gmlviewer.gis.model.*;
import gmlviewer.gis.comp.*;
import gmlviewer.gis.map.*;

/**
 * JLayerList 에 들어가는 컴포넌트.
 * 하나의 레이어의 전경색 배경색 출력 여부를 제어한다.
 */

public class LayerListItem extends BorderPane implements ItemListener {


  public LayerListItem( Lyr layer, Component layerViewer ) {

     this.layer = layer;
     this.layerViewer = layerViewer;

     // 레이어 온오프 체크박스
     JCheckBox onOffCB = this.createCheckGBox( layer );
     add( onOffCB, BorderLayout.CENTER );
     onOffCB.setToolTipText( layer.getName() );
     onOffCB.addItemListener( this );
     // 끝. 레이어 온오프 체크박스

     // 속성 버튼
     AttributeButton attBtn = new AttributeButton( layer, layerViewer );
     add( attBtn, BorderLayout.EAST );
     // 끝. 속성 버튼

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
