/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.ui.comp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.jwordart.model.wordart.WordArtGroupManager;
import com.jwordart.ui.resource.Resource_WordArt;
import com.jwordart.util.SystemUtil;

public class WordArtMappingTypeSelector extends JWindow {
	// Data
	private static WordArtMappingTypeSelector selector = new WordArtMappingTypeSelector();
	private JToggleButton selectedMappingTypeButton = null;
	private int selectedMappingTypeNumber = 0;
	private boolean mouseOver = false;
	private String[] toolTips = { "보통 문자열", "중지", "삼각형 위", "삼각형 아래",
			"갈매기표 수장 머리 부분", "갈매리표 수장 꼬리 부분", "고리 안쪽", "고리 바깥쪽", "윗쪽 원호(곡선)",
			"원호 아래(곡선)", "원(곡선)", "단추(곡선)", "원호 위(유출)", "원호 아래(유출)", "원(유출)",
			"단추(유출)", "곡선 위", "곡선 아래", "원통 위", "원통 아래", "물결 1", "물결 2",
			"이중 물결 1", "이중 물결 2", "부풀리기", "움츠리기", "아래에서 부풀리기", "아래에서 움츠리기",
			"위에서 부풀리기", "위에서 움츠리기", "움츠렷다 부풀리기", "움츠렷다 부풀렷다 다시 움츠리기",
			"오른쪽으로 흐려지기", "왼쪽으로 흐려지기", "위로 흐려지기", "아래로 흐려지기", "위로 기울기",
			"아래로 기울기", "위로 계단식 배열", "아래로 계단식 배열" };

	// End of data.

	class MappingTypeToggleButton extends JToggleButton implements
			MouseListener {
		private int mappingTypeNumber = 0;
		private boolean mouseReady = false;

		public MappingTypeToggleButton(int typeNumber) {
			this.mappingTypeNumber = typeNumber;
			super.addMouseListener(this);
		}

		public void mousePressed(MouseEvent e) {
			if (selectedMappingTypeButton != null) {
				selectedMappingTypeButton.setSelected(false);
				selectedMappingTypeButton.repaint();
			}
			selectedMappingTypeButton = this;
		}

		public void mouseReleased(MouseEvent e) {
			selectedMappingTypeNumber = this.mappingTypeNumber;

			mouseOver = false;
			mouseReady = false;

			//WordArtMappingTypeSelector.this.setVisible(false);
			WordArtGroupManager
					.changeSelectedWordArtsMappingType(selectedMappingTypeNumber);
		}

		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
			mouseReady = true;
			mouseOver = true;
		}

		public void mouseExited(MouseEvent e) {
			mouseReady = false;
			mouseOver = false;
			//WordArtMappingTypeSelector.this.setVisible(false);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			Rectangle bounds = this.getBounds();
			if (this.isSelected()) {
				g.draw3DRect(0, 0, bounds.width - 1, bounds.height - 1, false);
			} else if (this.mouseReady) {
				g.draw3DRect(0, 0, bounds.width - 1, bounds.height - 1, true);
			}
		}
	};

	JPanel contentPane;
	GridLayout gridLayout1 = new GridLayout();
	JToggleButton[] mappingTypeButtons = new JToggleButton[40];
	Border border1;

	// Construct the frame
	public WordArtMappingTypeSelector() {
		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		this.enableEvents(AWTEvent.MOUSE_MOTION_EVENT_MASK);
		try {
			this.jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Component initialization
	private void jbInit() throws Exception {
		contentPane = (JPanel) this.getContentPane();
		contentPane.setBorder(BorderFactory.createRaisedBevelBorder());
		border1 = BorderFactory.createEmptyBorder();
		gridLayout1.setRows(5);
		gridLayout1.setColumns(8);
		contentPane.setLayout(gridLayout1);
		this.setSize(new Dimension(214, 141));
		String imageFileName;
		for (int i = 0; i < mappingTypeButtons.length; i++) {
			mappingTypeButtons[i] = new MappingTypeToggleButton(i);
			imageFileName = "mapping_";
			if ((i + 1) < 10) {
				imageFileName += "0";
			}
			imageFileName += "" + (i + 1) + ".gif";

			mappingTypeButtons[i].setIcon(Resource_WordArt.getIcon("wordart",
					imageFileName));
			mappingTypeButtons[i].setBorder(border1);
			mappingTypeButtons[i].setToolTipText(this.toolTips[i]);
			contentPane.add(mappingTypeButtons[i], null);
		}
	}

	@Override
	public void setVisible( final boolean b) {

		if (!b) {

			this.mouseOver = false;

			Thread thread = new Thread() {
				@Override
				public void run() {
					SystemUtil.waitForSeconds(0.7);
					if (!WordArtMappingTypeSelector.this.mouseOver) {
						WordArtMappingTypeSelector.super.setVisible( b );
					}
				}
			};

			//thread.start();

		}

		if ( b ) {
			//this.mouseOver = true;
		}

		super.setVisible(b);
	}

	@Override
	protected void processMouseMotionEvent(MouseEvent e) {
		super.processMouseMotionEvent(e);
		if (e.getID() == MouseEvent.MOUSE_EXITED) {
			mouseOver = false;
		} else {
			mouseOver = true;
		}
	}

	public static void showSelector(Component comp) {
		Point compLoc = comp.getLocationOnScreen();
		Rectangle compBounds = comp.getBounds();
		selector.setLocation(compLoc.x, compLoc.y + compBounds.width + 4);
		selector.setVisible(true);
	}

	public static void hideSelector() {
		//selector.setVisible(false);
	}

}