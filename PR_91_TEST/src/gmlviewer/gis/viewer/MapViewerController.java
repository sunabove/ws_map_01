package gmlviewer.gis.viewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gmlviewer.gis.comp.*;
import gmlviewer.gis.*;

/**
 * 레이어 뷰어를 제어하는 버튼들로 구성되어진 패널이다.
 */
public class MapViewerController
    extends ControlPane {

  public MapViewerController( ) {

    this( null );

  }

  public MapViewerController( MapViewer mapViewer ) {
    this.setMapViewer( mapViewer );
  }

  /**
   * 버튼들을 누를 때, 액션을 처리한다.
   */
  public void actionPerformed(String command, Object event) {

    if (event instanceof ItemEvent) {

      event = ( (ItemEvent) event).getItem();

    }
    else if (event instanceof ActionEvent) {

      event = ( (ActionEvent) event).getActionCommand();

    }

    MapViewer mapViewer = this.layerViewer;

    if( mapViewer != null ) {
      mapViewer.interpret(command, event);
    }

  }

  public void setMapViewer( MapViewer mapViewer ) {
    this.layerViewer = mapViewer;
  }

  /**
   * 레이어 뷰어 콘트롤러의 컴포넌트를 발생시킨다.
   * 이 함수는 딱 한 번 불러져야 한다. 컨트롤러 하나당.
   */
  public Component[] createControlComponents() {

    ActionCompFactory actionFact = super.actionFact;

    return new Component[] {

        createActionComponent(GmlViewerRegistry.ZOOM_IN_TEXT, GmlViewerRegistry.GIS_RES_DIR,
                              GmlViewerRegistry.ZOOM_IN_ICON_FILE_NAME),

        createActionComponent(GmlViewerRegistry.ZOOM_OUT_TEXT,
                              GmlViewerRegistry.GIS_RES_DIR,
                              GmlViewerRegistry.ZOOM_OUT_ICON_FILE_NAME),

        createActionComponent(GmlViewerRegistry.SEL_EXTENT_TEXT,
                              GmlViewerRegistry.GIS_RES_DIR,
                              GmlViewerRegistry.SELECT_EXTENT_ICON_FILE_NAME),

        createActionComponent(GmlViewerRegistry.PAN_TEXT, GmlViewerRegistry.GIS_RES_DIR,
                              GmlViewerRegistry.PAN_ICON_FILE_NAME),

        createActionComponent(GmlViewerRegistry.FULL_EXTENT_TEXT,
                              GmlViewerRegistry.GIS_RES_DIR,
                              GmlViewerRegistry.FULL_EXTENT_ICON_FILE_NAME),

        createActionComponent(GmlViewerRegistry.SHOW_ATTRIBUTE_TEXT,
                              GmlViewerRegistry.GIS_RES_DIR,
                              GmlViewerRegistry.ATTRIBUTE_ICON_FILE_NAME),

    };

  }

  // 링크된 레이어 뷰어, 콤포넌트들이 레이어 뷰어을 조정한다.

  private MapViewer layerViewer;

}
