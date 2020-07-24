package gmlviewer.gis.comp;

import java.awt.*;
import javax.swing.*;

public class BorderPane extends JPanel {

  public BorderPane() {
      this( 0, 0 );
  }

  public BorderPane(int hgap, int vgap) {
      this( new BorderLayout( hgap, vgap ) );
  }

  private BorderPane(BorderLayout layout) {
      setLayout( layout );
  }

}
