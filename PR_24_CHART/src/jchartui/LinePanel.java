package jchartui;

/**
 * Title:           LinePanel 클래스
 * Copyright:       Copyright (c) 2001
 * Company:         Techdigm corp.
 * @author:         withhim
 * @version 1.0
 * Description:     Dialog 안에 들어가는 라인을 나타내는 클래스이다.
 */

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;


public class LinePanel extends JPanel{
    JPanel panelInnerLine = new JPanel();

    public LinePanel() {
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void jbInit() throws Exception {
        panelInnerLine.setBorder(BorderFactory.createEtchedBorder());
        panelInnerLine.setPreferredSize(new Dimension(400, 2));
        this.add(panelInnerLine, null);
    }
}