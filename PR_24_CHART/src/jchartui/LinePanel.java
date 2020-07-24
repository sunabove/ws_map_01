package jchartui;

/**
 * Title:           LinePanel Ŭ����
 * Copyright:       Copyright (c) 2001
 * Company:         Techdigm corp.
 * @author:         withhim
 * @version 1.0
 * Description:     Dialog �ȿ� ���� ������ ��Ÿ���� Ŭ�����̴�.
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