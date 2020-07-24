package htmleditor;

/**
 * Title:        java gis project
 * Description:  jgis server/client version 1.0
 * Copyright:    Copyright (c) 2001
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;
import javax.swing.*;

public class JBorderLayoutPanel extends JPanel {

  public JBorderLayoutPanel(int hgap, int vgap) {

      setLayout( new BorderLayout( hgap, vgap ) );

  }

  public JBorderLayoutPanel( ) {

      this( 0, 0 );

  }

}