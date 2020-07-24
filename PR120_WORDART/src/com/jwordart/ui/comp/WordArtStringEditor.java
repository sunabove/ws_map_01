/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      <p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.ui.comp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import com.jwordart.model.wordart.FontAndString;
import com.jwordart.ui.renderer.ComboBoxRenderer;
import com.jwordart.util.FontManager_WordArt;
import com.ynhenc.comm.util.WinUtil;

public class WordArtStringEditor extends JDialog {
	public static WordArtStringEditor wordArtStringEditor = new WordArtStringEditor();

	// Data.
	private FontAndString fontAndString;
	private String fontFamilyName = com.jwordart.util.FontManager_WordArt.fontFamilyNames[5];
	private int fontSize = 36;
	private int fontStyle = Font.PLAIN;
	private boolean isBoldic, isItalic;
	private Font font = new Font(fontFamilyName, fontStyle, fontSize);
	private boolean neverShown = true;

	// GUI components.
	JPanel contentPane;
	BorderLayout borderLayout1 = new BorderLayout();
	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();
	JPanel jPanel3 = new JPanel();
	JPanel jPanel4 = new JPanel();
	JButton veryfyButton = new JButton();
	FlowLayout flowLayout1 = new FlowLayout();
	JButton cancelButton = new JButton();

	JPanel jPanel5 = new JPanel();
	JPanel jPanel6 = new JPanel();
	JPanel jPanel8 = new JPanel();
	FlowLayout flowLayout2 = new FlowLayout();
	JLabel jLabel1 = new JLabel();
	FlowLayout flowLayout3 = new FlowLayout();
	JLabel jLabel2 = new JLabel();
	JLabel jLabel3 = new JLabel();
	JLabel jLabel4 = new JLabel();
	FlowLayout flowLayout4 = new FlowLayout();
	FlowLayout flowLayout5 = new FlowLayout();
	JComboBox fontFamilyNameComboBox = new JComboBox(FontManager_WordArt.iconFontNames);
	JComboBox fontSizeComboBox = new JComboBox(FontManager_WordArt.iconFontSizes);
	JToggleButton isBoldicButton = new JToggleButton();
	JToggleButton isItalicButton = new JToggleButton();
	JLabel jLabel5 = new JLabel();
	JLabel jLabel6 = new JLabel();
	JLabel jLabel7 = new JLabel();
	TitledBorder titledBorder1;
	Border border1;
	JScrollPane jScrollPane1 = new JScrollPane();
	JTextPane textArea = new JTextPane();

	// Construct the frame
	public WordArtStringEditor() {
		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			this.jbInit();
			// Code by sbmoon.
			this.setFontInformation();
			// End of Code by sbmoon.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Code by sbmoon.

	private void setFontInformation() {
		ComboBoxRenderer fontNameRenderer = new ComboBoxRenderer(SwingConstants.LEFT, SwingConstants.CENTER);
		fontFamilyNameComboBox.setRenderer(fontNameRenderer);
		fontFamilyNameComboBox.setMaximumRowCount(6);
		ComboBoxRenderer fontSizeRenderer = new ComboBoxRenderer(SwingConstants.CENTER, SwingConstants.CENTER);
		fontSizeComboBox.setRenderer(fontSizeRenderer);
		fontSizeComboBox.setMaximumRowCount(6);
	}

	// End of code by sbmoon.

	// Component initialization
	private void jbInit() throws Exception {
		contentPane = (JPanel) this.getContentPane();

		titledBorder1 = new TitledBorder("");
		border1 = BorderFactory.createCompoundBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white,
				Color.white, Color.black, Color.black), BorderFactory.createEmptyBorder(0, 0, 4, 4));
		contentPane.setLayout(borderLayout1);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setSize(new Dimension(499, 300));
		this.setTitle("WordArt 문자열 편집");
		this.setModal(true);
		this.addWindowListener(new java.awt.event.WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				WordArtStringEditor.this.this_windowClosing(e);
			}
		});
		jPanel1.setPreferredSize(new Dimension(7, 10));
		jPanel3.setPreferredSize(new Dimension(7, 10));
		jPanel4.setPreferredSize(new Dimension(30, 35));
		jPanel4.setLayout(flowLayout1);
		jPanel2.setBackground(SystemColor.scrollbar);
		jPanel2.setMinimumSize(new Dimension(10, 50));
		jPanel2.setPreferredSize(new Dimension(10, 62));
		jPanel2.setLayout(flowLayout2);
		veryfyButton.setPreferredSize(new Dimension(83, 24));
		veryfyButton.setText("확인");
		veryfyButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStringEditor.this.veryfyButton_actionPerformed(e);
			}
		});
		flowLayout1.setAlignment(FlowLayout.RIGHT);
		flowLayout1.setHgap(7);
		cancelButton.setPreferredSize(new Dimension(83, 24));
		cancelButton.setText("취소");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStringEditor.this.cancelButton_actionPerformed(e);
			}
		});
		jPanel5.setBackground(SystemColor.scrollbar);
		jPanel5.setMinimumSize(new Dimension(10, 50));
		jPanel5.setPreferredSize(new Dimension(477, 17));
		jPanel5.setLayout(flowLayout3);
		jPanel8.setBackground(SystemColor.scrollbar);
		jPanel8.setPreferredSize(new Dimension(477, 17));
		jPanel8.setLayout(flowLayout4);
		jPanel6.setBackground(SystemColor.scrollbar);
		jPanel6.setPreferredSize(new Dimension(477, 24));
		jPanel6.setLayout(flowLayout5);
		flowLayout2.setHgap(0);
		flowLayout2.setVgap(0);
		jLabel1.setDisplayedMnemonic('F');
		jLabel1.setText("글꼴(F):");
		flowLayout3.setAlignment(FlowLayout.LEFT);
		flowLayout3.setHgap(0);
		flowLayout3.setVgap(0);
		jLabel2.setText("크기(S):");
		jLabel2.setDisplayedMnemonic('S');
		jLabel3.setPreferredSize(new Dimension(174, 0));
		jLabel3.setDisplayedMnemonic('F');
		jLabel4.setText("문자열(T):");
		jLabel4.setDisplayedMnemonic('T');
		flowLayout4.setAlignment(FlowLayout.LEFT);
		flowLayout4.setHgap(0);
		flowLayout4.setVgap(0);
		flowLayout5.setAlignment(FlowLayout.LEFT);
		flowLayout5.setHgap(0);
		flowLayout5.setVgap(0);
		isBoldicButton.setHorizontalTextPosition(SwingConstants.LEFT);
		isBoldicButton.setMargin(new Insets(2, 7, 2, 7));
		isBoldicButton.setPreferredSize(new Dimension(33, 24));
		isBoldicButton.setAlignmentY((float) 0.0);
		isBoldicButton.setVerticalTextPosition(SwingConstants.TOP);
		isBoldicButton.setText("가");
		isBoldicButton.setVerticalAlignment(SwingConstants.TOP);
		isBoldicButton.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtStringEditor.this.isBoldicButton_mousePressed(e);
			}
		});
		isItalicButton.setHorizontalTextPosition(SwingConstants.LEFT);
		isItalicButton.setMargin(new Insets(2, 7, 2, 7));
		isItalicButton.setPreferredSize(new Dimension(32, 24));
		isItalicButton.setAlignmentY((float) 0.0);
		isItalicButton.setVerticalTextPosition(SwingConstants.TOP);
		isItalicButton.setText("가");
		isItalicButton.setVerticalAlignment(SwingConstants.TOP);
		isItalicButton.setFont(new java.awt.Font("Dialog", 2, 12));
		isItalicButton.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtStringEditor.this.isItalicButton_mousePressed(e);
			}
		});
		fontFamilyNameComboBox.setAlignmentY((float) 0.0);
		fontFamilyNameComboBox.setMinimumSize(new Dimension(126, 22));
		fontFamilyNameComboBox.setPreferredSize(new Dimension(207, 24));
		fontFamilyNameComboBox.setRenderer(null);
		fontFamilyNameComboBox.setSelectedIndex(FontManager_WordArt.defaultFontIndex);
		fontFamilyNameComboBox.addItemListener(new java.awt.event.ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				WordArtStringEditor.this.fontFamilyNameComboBox_itemStateChanged(e);
			}
		});
		fontFamilyNameComboBox.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStringEditor.this.fontFamilyNameComboBox_actionPerformed(e);
			}
		});
		fontSizeComboBox.setAlignmentY((float) 0.0);
		fontSizeComboBox.setMinimumSize(new Dimension(124, 22));
		fontSizeComboBox.setPreferredSize(new Dimension(70, 24));
		fontSizeComboBox.setSelectedIndex(FontManager_WordArt.defaultSizeIndex);
		fontSizeComboBox.addItemListener(new java.awt.event.ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				WordArtStringEditor.this.fontSizeComboBox_itemStateChanged(e);
			}
		});
		jLabel5.setPreferredSize(new Dimension(9, 0));
		jLabel6.setPreferredSize(new Dimension(15, 0));
		jLabel7.setPreferredSize(new Dimension(10, 0));
		textArea.setBorder(BorderFactory.createLoweredBevelBorder());
		textArea.setSelectionColor(SystemColor.activeCaption);
		textArea.setText("문자열을 입력하십시오.");
		textArea.setFont(new java.awt.Font("Dialog", 0, 36));
		contentPane.add(jPanel1, BorderLayout.WEST);
		contentPane.add(jPanel2, BorderLayout.NORTH);
		jPanel2.add(jPanel5, null);
		jPanel5.add(jLabel1, null);
		jPanel5.add(jLabel3, null);
		jPanel5.add(jLabel2, null);
		jPanel2.add(jPanel6, null);
		jPanel6.add(fontFamilyNameComboBox, null);
		jPanel6.add(jLabel5, null);
		jPanel6.add(fontSizeComboBox, null);
		jPanel6.add(jLabel6, null);
		jPanel6.add(isBoldicButton, null);
		jPanel6.add(jLabel7, null);
		jPanel6.add(isItalicButton, null);
		jPanel2.add(jPanel8, null);
		jPanel8.add(jLabel4, null);
		contentPane.add(jPanel3, BorderLayout.EAST);
		contentPane.add(jPanel4, BorderLayout.SOUTH);
		jPanel4.add(veryfyButton, null);
		jPanel4.add(cancelButton, null);
		contentPane.add(jScrollPane1, BorderLayout.CENTER);
		jScrollPane1.getViewport().add(textArea, null);
	}

	void this_windowClosing(WindowEvent e) {
		this.cancelButton_actionPerformed(null);
	}

	void veryfyButton_actionPerformed(ActionEvent e) {
		this.fontAndString = new FontAndString(this.font, textArea.getText());
		this.setVisible(false);
	}

	void cancelButton_actionPerformed(ActionEvent e) {
		this.fontAndString = null;
		this.setVisible(false);
	}

	public static FontAndString getFontAndString() {
		if (WordArtStringEditor.wordArtStringEditor == null) {
			wordArtStringEditor = new WordArtStringEditor();
		}
		wordArtStringEditor.setVisible(true);
		return wordArtStringEditor.fontAndString;
	}

	void fontFamilyNameComboBox_actionPerformed(ActionEvent e) {
	}

	void fontFamilyNameComboBox_itemStateChanged(ItemEvent e) {
		fontFamilyName = e.getItem().toString();

		this.redrawTextArea();
	}

	void fontSizeComboBox_itemStateChanged(ItemEvent e) {
		fontSize = Integer.valueOf(e.getItem().toString()).intValue();

		this.redrawTextArea();
	}

	void setWordArtFontStyle() {
		if (this.isBoldic && this.isItalic) {
			this.fontStyle = Font.BOLD | Font.ITALIC;
		} else if (this.isBoldic) {
			this.fontStyle = Font.BOLD;
		} else if (this.isItalic) {
			this.fontStyle = Font.ITALIC;
		} else {
			this.fontStyle = Font.PLAIN;
		}
	}

	public void setText(String str) {
		this.textArea.setText(str);
	}

	public Font getWordArtFont() {
		this.setWordArtFontStyle();
		this.font = new Font(fontFamilyName, fontStyle, fontSize);
		return this.font;
	}

	public void setWordArtFont(Font font) {
		this.font = font;
		this.textArea.setFont(font);
		this.repaint();
	}

	void redrawTextArea() {
		if (this.neverShown) { // 적어도 한 번 show 할 때 까지는 그리지 않는다.
			return;
		}
		Font tFont = this.getWordArtFont();
		if (tFont == null) {
			return;
		}
		textArea.setFont(tFont);
		textArea.setText(textArea.getText());
		textArea.repaint();
	}

	@Override
	public void setVisible(boolean b) {

		if (b) {
			if (neverShown) {
				this.reLocate();
				neverShown = false;
			}
		}

		super.setVisible(b);
	}

	public void reLocate() {
		WinUtil.locateOnTheScreenCenter(this);
	}

	void isBoldicButton_mousePressed(MouseEvent e) {
		this.isBoldic = !this.isBoldic;
		this.redrawTextArea();
	}

	void isItalicButton_mousePressed(MouseEvent e) {
		this.isItalic = !this.isItalic;
		this.redrawTextArea();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Font font = this.font;
		if (font != null && font.isBold()) {
			this.isBoldic = true;
		} else {
			this.isBoldic = false;
		}
		if (font != null && font.isItalic()) {
			this.isItalic = true;
		} else {
			this.isItalic = false;
		}

		this.isBoldicButton.setSelected(this.isBoldic);
		this.isBoldicButton.repaint();
		this.isItalicButton.setSelected(this.isItalic);
		this.isItalicButton.repaint();
	}
}