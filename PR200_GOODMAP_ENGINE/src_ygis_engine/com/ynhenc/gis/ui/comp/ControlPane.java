package com.ynhenc.gis.ui.comp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.ynhenc.comm.util.*;


public abstract class ControlPane extends JPanel implements ActListener {

	public ControlPane() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private void initUi() {

		this.actionFact = new ActionCompFactory(this);

		setBorder(BorderFactory.createEtchedBorder());

		LayoutManager layout = new FlowLayout(FlowLayout.CENTER, 1, 1);

		this.setLayout(layout);

		Component controlBtns[] = getControlComponents();

		for (int i = 0, len = controlBtns.length; i < len; i++) {
			add(controlBtns[i]);
		}

		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				packComponents();
			}
		});

	}

	/**
	 * ����� ������Ʈ
	 */
	public Component[] getControlComponents() {
		if (controlComponents == null) {
			controlComponents = createControlComponents();
		}
		return controlComponents;
	}

	/**
	 * ������Ʈ�� ��Ű¡ �Ѵ�.
	 */
	public void packComponents() {
		
		if( false ) {
			return ;
		}

		Dimension size = getPreferredSize();
		Component comps[] = getComponents();
		int w = 0, h = 0;
		for (int i = 0, len = comps.length; i < len; i++) {
			Rectangle bounds = comps[i].getBounds();
			int lw = bounds.x + bounds.width;
			int lh = bounds.y + bounds.height;
			w = (lw > w) ? lw : w;
			h = (lh > h) ? lh : h;
		}

		// calibrate by insets

		Insets insets = getInsets();
		w += insets.right + insets.left;
		h += insets.bottom + insets.top;

		// end of calibrating by insets

		if (w < 1 || h < 1) {
			return;
		}

		if (size.width != w || size.height != h) {
			setPreferredSize(new Dimension(w, h));
			setSize(w, h);
			Container con = getParent();
			con.validate();
		}

	}

	/**
	 * ����Ʈ �Լ��� �������̵���.
	 */
	public void paint(Graphics g) {

		if (!inited) {
			super.paint(g);
			initUi();
			packComponents();
			inited = true;
			super.paint(g);
		} else {
			super.paint(g);
		}
	}

	protected Component createActionComponent(String command, String resDir,
			String iconResource) {
		return actionFact.createActionedComponent(new JButton(command),
				command, resDir, iconResource);
	}

	// ����� �������̽� �ʱ�ȭ ���θ� ��Ÿ��
	private boolean inited;

	// ����� ������Ʈ��
	private Component[] controlComponents;
	protected ActionCompFactory actionFact;

	/**
	 * ����� ������Ʈ�� ��ȯ�ϴ� �Լ�. �Ƶ� Ŭ�������� �ݵ�� �����Ͽ��� �Ѵ�.
	 */

	public abstract Component[] createControlComponents();

	private void jbInit() throws Exception {
	}

}
