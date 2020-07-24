/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.ui.renderer;

import java.awt.*;
import javax.swing.plaf.basic.*;

public class SteppedComboBoxUI extends BasicComboBoxUI {
	int w, h;

	public SteppedComboBoxUI(int w, int h) {
		this.w = w;
		this.h = h;
	}

	@Override
	protected ComboPopup createPopup() {

		BasicComboPopup popup = new BasicComboPopup(comboBox) {

			@Override
			public void setVisible(boolean b) {

				if (b) {
					Dimension popupSize = comboBox.getSize();
					popupSize.setSize(popupSize.getWidth() + w, popupSize
							.getHeight()
							+ h);
					popupSize.setSize(popupSize.width,
							getPopupHeightForRowCount(comboBox
									.getMaximumRowCount()));
					Rectangle popupBounds = computePopupBounds(0, comboBox
							.getBounds().height, popupSize.width,
							popupSize.height);
					scroller.setMaximumSize(popupBounds.getSize());
					scroller.setPreferredSize(popupBounds.getSize());
					scroller.setMinimumSize(popupBounds.getSize());
					list.invalidate();
					int selectedIndex = comboBox.getSelectedIndex();
					if (selectedIndex == -1) {
						list.clearSelection();
					} else {
						list.setSelectedIndex(selectedIndex);
					}
					list.ensureIndexIsVisible(list.getSelectedIndex());
					setLightWeightPopupEnabled(comboBox
							.isLightWeightPopupEnabled());

					// super.show(comboBox, popupBounds.x, popupBounds.y);
					
					super.setVisible( b );
					
				} else {  
					super.setVisible(b);
				}
			}
		};

		popup.getAccessibleContext().setAccessibleParent(comboBox);
		return popup;
	}
}
