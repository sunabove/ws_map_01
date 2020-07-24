/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.ui.comp;

import javax.swing.UIManager;
import java.awt.*;

public class WordArtFillEffectEditorApplication {
	boolean packFrame = false;

	// Construct the application
	public WordArtFillEffectEditorApplication() {
		WordArtFillEffectEditor frame = new WordArtFillEffectEditor();
		// Validate frames that have preset sizes
		// Pack frames that have useful preferred size info, e.g. from their
		// layout
		if (packFrame) {
			frame.pack();
		} else {
			frame.validate();
		}
		// Center the window
		Component comp = null;
		frame.setLocationRelativeTo(comp);
		frame.setVisible(true);
	}

	// Main method
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		new WordArtFillEffectEditorApplication();
	}
}