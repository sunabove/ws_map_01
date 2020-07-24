package gmlviewer.gis.viewer;

import java.awt.BorderLayout;

import javax.swing.*;
import javax.swing.table.*;
import gmlviewer.gis.model.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class JPropPane extends JPanel {

    BorderLayout borderLayout1 = new BorderLayout();

    JScrollPane mainScroller = new JScrollPane();

    JPropTable propTable = new JPropTable();

    public JPropPane() {
        try {
            jbInit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(borderLayout1);
        this.add(mainScroller, java.awt.BorderLayout.CENTER);
        mainScroller.getViewport().add(propTable);
//        mainScroller.setHorizontalScrollBarPolicy( mainScroller.HORIZONTAL_SCROLLBAR_ALWAYS );
    }

    public void setShapeAttribute( Shape shape ) {
      this.propTable.setShapeAttribute( shape );
    }

    public java.awt.Dimension getPreferredSize() {
      return this.propTable.getPreferredSize();
    }

}
