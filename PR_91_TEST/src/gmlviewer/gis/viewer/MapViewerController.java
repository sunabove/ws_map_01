package gmlviewer.gis.viewer;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import gmlviewer.gis.comp.*;
import gmlviewer.gis.*;

/**
 * ���̾� �� �����ϴ� ��ư��� �����Ǿ��� �г��̴�.
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
   * ��ư���� ���� ��, �׼��� ó���Ѵ�.
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
   * ���̾� ��� ��Ʈ�ѷ��� ������Ʈ�� �߻���Ų��.
   * �� �Լ��� �� �� �� �ҷ����� �Ѵ�. ��Ʈ�ѷ� �ϳ���.
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

  // ��ũ�� ���̾� ���, ������Ʈ���� ���̾� ����� �����Ѵ�.

  private MapViewer layerViewer;

}
