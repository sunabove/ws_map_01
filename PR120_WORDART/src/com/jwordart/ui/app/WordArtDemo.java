/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p> 
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.ui.app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.jwordart.model.wordart.WordArtGroupManager;

public class WordArtDemo extends JFrame {
	JPanel contentPane;
	JMenuBar menuBar1 = new JMenuBar();
	JMenu menuFile = new JMenu();
	JMenuItem menuFileExit = new JMenuItem();
	JMenu menuHelp = new JMenu();
	JMenuItem menuHelpAbout = new JMenuItem();
	JLabel statusBar = new JLabel();
	BorderLayout borderLayout1 = new BorderLayout();
	JMenu jMenu1 = new JMenu();
	JMenu jMenu2 = new JMenu();
	JMenuItem jMenuItem1 = new JMenuItem();
	JScrollPane jScrollPane1 = new JScrollPane();
	JPanel textArea = new JPanel() {
		@Override
		public void update(Graphics g) {
			paint(g);
		}

		@Override
		public void paint(Graphics g) {

			Dimension size = this.getSize();

			g.setClip(0, 0, size.width, size.height);

			super.paint(g);

			WordArtGroupManager manager = WordArtGroupManager.currentWorkingManager;
			if (manager != null) {
				manager.repaintWithoutUpdate();
			}

		}
	};

	// Construct the frame
	public WordArtDemo() {
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Component initialization
	private void jbInit() throws Exception {
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(borderLayout1);
		this.setSize(new Dimension(742, 770));
		this.setTitle("WordArtDemo");
		statusBar.setText(" ");
		menuFile.setText("ȭ��");
		menuFileExit.setToolTipText("");
		menuFileExit.setText("����");
		menuFileExit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				fileExit_actionPerformed(e);
			}
		});
		menuHelp.setText("����");
		menuHelpAbout.setText("Java Word Art ����");
		menuHelpAbout.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				helpAbout_actionPerformed(e);
			}
		});
		jMenu1.setText("����");
		jMenu2.setText("�׸�");
		jMenuItem1.setMnemonic('W');
		jMenuItem1.setText("�����Ʈ(W)");
		jMenuItem1.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				jMenuItem1_actionPerformed(e);
			}
		});
		textArea.setBackground(Color.white);
		textArea.addKeyListener(new java.awt.event.KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				textArea_keyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				textArea_keyReleased(e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
				textArea_keyTyped(e);
			}
		});
		menuFile.add(menuFileExit);
		menuHelp.add(menuHelpAbout);
		menuBar1.add(menuFile);
		menuBar1.add(jMenu1);
		menuBar1.add(menuHelp);
		this.setJMenuBar(menuBar1);
		contentPane.add(statusBar, BorderLayout.SOUTH);
		contentPane.add(jScrollPane1, BorderLayout.CENTER);
		jScrollPane1.getViewport().add(textArea, null);
		jMenu1.add(jMenu2);
		jMenu2.add(jMenuItem1);
	}

	// File | Exit action performed
	public void fileExit_actionPerformed(ActionEvent e) {
		System.exit(0);
	}

	// Help | About action performed
	public void helpAbout_actionPerformed(ActionEvent e) {
		WordArtDemo_AboutBox dlg = new WordArtDemo_AboutBox(this);
		Dimension dlgSize = dlg.getPreferredSize();
		Dimension frmSize = getSize();
		Point loc = getLocation();
		dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
				(frmSize.height - dlgSize.height) / 2 + loc.y);
		dlg.setModal(true);
		dlg.setVisible(true);
	}

	// Overridden so we can exit when window is closed
	@Override
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			fileExit_actionPerformed(null);
		}
	}

	void jMenuItem1_actionPerformed(ActionEvent e) {
		new Thread() {
			@Override
			public void run() {
				com.jwordart.model.wordart.WordArtGroupManager
						.getNewWordArt(textArea);
			}
		}.start();
	}

	void textArea_keyPressed(KeyEvent e) {
	}

	void textArea_keyReleased(KeyEvent e) {

	}

	void textArea_keyTyped(KeyEvent e) {

	}
}