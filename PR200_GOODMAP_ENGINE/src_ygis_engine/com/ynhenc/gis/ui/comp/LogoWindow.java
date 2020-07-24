package com.ynhenc.gis.ui.comp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.ynhenc.comm.util.*;

public class LogoWindow extends JWindow {

	/**
	 *
	 */
	private static final long serialVersionUID = -8630797037089163550L;

	public LogoWindow() {

		Image loadingImage = this.logoPainter.loadingImage.getBfrImage();

		int iw = loadingImage.getWidth(this);
		int ih = loadingImage.getHeight(this);

		this.setSize(iw, ih);

		WinUtil.locateOnTheScreenCenter(this);

		this.addFocusListener(new FocusAdapter() {

			@Override
			public void focusLost(FocusEvent e) {
			}

		});

	}

	@Override
	public void paint(Graphics g) {
		this.logoPainter.paint(this, -1);
	}

	private LogoPainter logoPainter = new LogoPainter();

}
