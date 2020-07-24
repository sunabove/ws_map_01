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
	 * 제어용 콤포넌트
	 */
	public Component[] getControlComponents() {
		if (controlComponents == null) {
			controlComponents = createControlComponents();
		}
		return controlComponents;
	}

	/**
	 * 컴포넌트를 패키징 한다.
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
	 * 페인트 함수를 오버라이딩함.
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

	// 사용자 인터페이스 초기화 여부를 나타냄
	private boolean inited;

	// 제어용 콤포넌트들
	private Component[] controlComponents;
	protected ActionCompFactory actionFact;

	/**
	 * 제어용 콤포넌트를 반환하는 함수. 아들 클래스들은 반드시 정의하여야 한다.
	 */

	public abstract Component[] createControlComponents();

	private void jbInit() throws Exception {
	}

}
