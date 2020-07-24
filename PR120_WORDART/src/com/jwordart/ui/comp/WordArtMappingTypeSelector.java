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
import com.ynhenc.comm.util.SystemUtil;

public class WordArtMappingTypeSelector extends JWindow {
	// Data
	private static WordArtMappingTypeSelector selector = new WordArtMappingTypeSelector();
	private JToggleButton selectedMappingTypeButton = null;
	private int selectedMappingTypeNumber = 0;
	private boolean mouseOver = false;
	private String[] toolTips = { "���� ���ڿ�", "����", "�ﰢ�� ��", "�ﰢ�� �Ʒ�",
			"���ű�ǥ ���� �Ӹ� �κ�", "���Ÿ�ǥ ���� ���� �κ�", "�� ����", "�� �ٱ���", "���� ��ȣ(�)",
			"��ȣ �Ʒ�(�)", "��(�)", "����(�)", "��ȣ ��(����)", "��ȣ �Ʒ�(����)", "��(����)",
			"����(����)", "� ��", "� �Ʒ�", "���� ��", "���� �Ʒ�", "���� 1", "���� 2",
			"���� ���� 1", "���� ���� 2", "��Ǯ����", "��������", "�Ʒ����� ��Ǯ����", "�Ʒ����� ��������",
			"������ ��Ǯ����", "������ ��������", "�����Ǵ� ��Ǯ����", "�����Ǵ� ��Ǯ�Ǵ� �ٽ� ��������",
			"���������� �������", "�������� �������", "���� �������", "�Ʒ��� �������", "���� ����",
			"�Ʒ��� ����", "���� ��ܽ� �迭", "�Ʒ��� ��ܽ� �迭" };

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