package com.jwordart.util;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class WinUtil {

	public static void locateOnTheScreenCenter(Window comp) {

		Dimension frameSize = comp.getSize();

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		if (frameSize.height > screenSize.height) {
			frameSize.height = screenSize.height;
		}
		if (frameSize.width > screenSize.width) {
			frameSize.width = screenSize.width;
		}

		comp.setLocation((screenSize.width - frameSize.width) / 2,
				(screenSize.height - frameSize.height) / 2);

	}

	public static boolean isRightMouseButton(MouseEvent anEvent) {

		return ((anEvent.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK);

	}

	public static JFrame getJFrame(Component comp) {
		return (JFrame) getAncestorOfClass(JFrame.class, comp);
	}

	public static JSplitPane getJSplitPane( Component comp ) {
		return (JSplitPane) getAncestorOfClass( JSplitPane.class, comp );
	}

	public static Component getAncestorOfClass(Class klass, Component comp) {
		return SwingUtilities.getAncestorOfClass(klass, comp);
	}

}
