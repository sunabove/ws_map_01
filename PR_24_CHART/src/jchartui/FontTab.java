//package com.techdigm.chart.editor;
package jchartui;

//import jcalc.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

import java.util.*;
//import com.techdigm.editor.DefaultFont;
import jcosmos.DefaultFont;

import javax.swing.event.*;
import java.beans.*;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class FontTab extends JPanel implements ActionListener{

	//-----------------------------------------------------
	//	DEFAULT 속성 정의
	//-----------------------------------------------------
	// 이 기본값들은 CellFormat의 기본값과 같아야 한다.
	// 이 기본값들은 Normal Font로 바꿀때 사용한다.
	final String	DEFAULT_FONTNAME		= DefaultFont.FONT_NAME;		// 폰트이름 기본값
	final int		DEFAULT_FONTSIZE		= DefaultFont.FONT_SIZE;		// 폰트사이즈 기본값
	final int		DEFAULT_FONTSTYLE		= DefaultFont.FONT_STYLE;		// 폰트스타일 기본값
	final byte		DEFAULT_UNDERLINE		= DefaultFont.UNDERLINE_NONE;	// 언더라인 기본값
	final Color		DEFAULT_FONTCOLOR		= Color.black;					// 폰트컬러 기본값
	final byte		DEFAULT_SCRIPT			= DefaultFont.SCRIPT_NONE;		// 스크립트 기본값
	final boolean	DEFAULT_STRIKETHROUGH	= false;						// 통과선 기본값

	String		fontName		= DEFAULT_FONTNAME;
	int			fontStyle		= DEFAULT_FONTSTYLE;
	boolean		fontBold		= false;
	boolean		fontItalic		= false;
	short		fontSize		= (short)DEFAULT_FONTSIZE;
	Color		fontColor		= DEFAULT_FONTCOLOR;
	byte		underline		= DEFAULT_UNDERLINE;
	boolean		strikeThrough	= DEFAULT_STRIKETHROUGH;
	byte		script			= DEFAULT_SCRIPT;

//	ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/FontTabBundle", JCalcResource.getLocale());
	ResourceBundle bundle = ResourceBundle.getBundle("jchartui/FontTabBundle", jchartui.JCalcResource.getLocale());

	String fontLstArray[]	= GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	String fontStyleLstArray[]	= (String[])bundle.getObject("styles");
//								{ "Regular",
//									"Italic",
//									"Bold",
//									"Bold Italic" };
	String fontSizeLstArray[]	= { "8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72" };
	String underLineCboArray[]	= (String[])bundle.getObject("underlines");
//								{ "None",
//									"Single",
//									"Double"};
	String backGroundArray[] = (String[])bundle.getObject("backgrounds");
//	{
//		"Auto",
//		"Transparent",
//		"Untransparent"
//	};

    JTextField textFieldFontName = new JTextField();

    JTextField textFieldFontStyle = new JTextField();

    JTextField textFieldFontSize = new JTextField();

    JComboBox comboBoxUnderline = new JComboBox(underLineCboArray);
    //JComboBox comboBoxColor = new JComboBox();
	ColorButton fontColorButton = new ColorButton(this);

    JComboBox comboBoxBackground = new JComboBox(backGroundArray);

    JPanel panelEffect = new JPanel();
    JPanel panelPreview = new JPanel();

    JCheckBox checkBoxAutoSize = new JCheckBox();


	JCheckBox checkBoxStrikeout = new JCheckBox();
    JCheckBox checkBoxSuperscript = new JCheckBox();
    JCheckBox checkBoxSubscript = new JCheckBox();

//	Border border1;
	TitledBorder border1;

//    Border border2;
	TitledBorder border2;

	JLabel labelUnderLine = new JLabel();
    JLabel labelColor = new JLabel();
    JLabel labelBackground = new JLabel();
    JLabel labelFontName = new JLabel();
    JLabel labelFontStyle = new JLabel();
    JLabel labelFontSize = new JLabel();

//	com.techdigm.chart.editor.FontPreviewPanel panelFontPreview = new com.techdigm.chart.editor.FontPreviewPanel();
	FontPreviewPanel panelFontPreview = new FontPreviewPanel();

    JScrollPane scrollPaneFontName = new JScrollPane();
    JList listFontName = new JList(fontLstArray);
    JScrollPane scrollPaneFontStyle = new JScrollPane();
    JList listFontStyle = new JList(fontStyleLstArray);
    JScrollPane scrollPaneFontSize = new JScrollPane();
    JList listFontSize = new JList(fontSizeLstArray);

	//메뉴 변경 ....
	private boolean nowChangeMenu = false;

    public FontTab() {
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

		this.listFontName.setSelectedValue(this.fontName, false);
		this.listFontStyle.setSelectedValue(this.fontStyleLstArray[this.fontStyle], true);
		this.listFontSize.setSelectedValue(String.valueOf(this.fontSize), true);
    }

    private void jbInit() throws Exception {
        this.setLayout(null);

		border1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)), bundle.getString("effect"));
        border2 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)), bundle.getString("preview"));

        textFieldFontName.setBounds(new Rectangle(12, 31, 166, 21));

        textFieldFontStyle.setBounds(new Rectangle(189, 31, 99, 21));

        textFieldFontSize.setBounds(new Rectangle(301, 31, 75, 21));

		comboBoxUnderline.setBounds(new Rectangle(13, 152, 157, 24));
        comboBoxUnderline.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comboBoxUnderline_actionPerformed(e);
            }
        });
//        comboBoxColor.setBounds(new Rectangle(192, 152, 110, 23));
        comboBoxBackground.setBounds(new Rectangle(316, 152, 101, 23));

		fontColorButton.setBounds(new Rectangle(192, 152, 110, 23));
		fontColorButton.addActionListener(this);
		fontColorButton.autoColor.setText(bundle.getString("automatic"));

		panelEffect.setBorder(border1);
        panelEffect.setBounds(new Rectangle(14, 184, 159, 86));
        panelEffect.setLayout(null);

		panelPreview.setBorder(border2);
        panelPreview.setBounds(new Rectangle(193, 183, 224, 87));
        panelPreview.setLayout(null);

		checkBoxAutoSize.setText(bundle.getString("autoScale"));//"자동 크기 조정(T)");
        checkBoxAutoSize.setBounds(new Rectangle(16, 276, 124, 22));
        checkBoxAutoSize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBoxAutoSize_actionPerformed(e);
            }
        });


		checkBoxStrikeout.setText(bundle.getString("strikethrough"));//"Strikeout");
        checkBoxStrikeout.setBounds(new Rectangle(10, 18, 117, 22));
        checkBoxStrikeout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBoxStrikeout_actionPerformed(e);
            }
        });
        checkBoxSuperscript.setText(bundle.getString("superscript"));//"Super script");
        checkBoxSuperscript.setBounds(new Rectangle(10, 39, 117, 22));
        checkBoxSuperscript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBoxSuperscript_actionPerformed(e);
            }
        });
        checkBoxSubscript.setText(bundle.getString("subscript"));//"Sub script");
        checkBoxSubscript.setBounds(new Rectangle(10, 58, 117, 22));
        checkBoxSubscript.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBoxSubscript_actionPerformed(e);
            }
        });

		labelUnderLine.setText(bundle.getString("underline"));//"Underline");
        labelUnderLine.setBounds(new Rectangle(13, 130, 77, 20));
        labelColor.setText(bundle.getString("color"));//"Color");
        labelColor.setBounds(new Rectangle(193, 129, 91, 19));
        labelBackground.setText(bundle.getString("background"));//"Background");
        labelBackground.setBounds(new Rectangle(317, 129, 94, 22));
        labelFontName.setText(bundle.getString("font"));//"Font");
        labelFontName.setBounds(new Rectangle(12, 9, 80, 20));
        labelFontStyle.setText(bundle.getString("fontStyle"));//"Style");
        labelFontStyle.setBounds(new Rectangle(193, 7, 80, 20));
        labelFontSize.setText(bundle.getString("size"));//"Size");
        labelFontSize.setBounds(new Rectangle(314, 6, 80, 20));

		panelFontPreview.setBounds(new Rectangle(11, 20, 199, 56));
		panelFontPreview.string = bundle.getString("sampleText");

        scrollPaneFontName.setBounds(new Rectangle(11, 53, 166, 74));
        listFontName.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listFontName.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                listFontName_valueChanged(e);
            }
        });
        scrollPaneFontStyle.setBounds(new Rectangle(190, 53, 98, 74));
        listFontStyle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listFontStyle.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                listFontStyle_valueChanged(e);
            }
        });
        scrollPaneFontSize.setBounds(new Rectangle(300, 53, 77, 74));
        listFontSize.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listFontSize.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                listFontSize_valueChanged(e);
            }
        });
        this.add(textFieldFontName, null);
        this.add(comboBoxUnderline, null);
//        this.add(comboBoxColor, null);
		this.add(fontColorButton, null);

        this.add(comboBoxBackground, null);
        this.add(panelEffect, null);
		panelEffect.add(checkBoxSubscript, null);
        panelEffect.add(checkBoxStrikeout, null);
        panelEffect.add(checkBoxSuperscript, null);

		this.add(checkBoxAutoSize, null);
        this.add(panelPreview, null);
        panelPreview.add(panelFontPreview, null);

		this.add(labelUnderLine, null);
        this.add(labelColor, null);
        this.add(labelBackground, null);
        this.add(labelFontName, null);
        this.add(labelFontStyle, null);
        this.add(labelFontSize, null);
        this.add(textFieldFontSize, null);
        this.add(textFieldFontStyle, null);
        this.add(scrollPaneFontName, null);
        this.add(scrollPaneFontStyle, null);
        this.add(scrollPaneFontSize, null);
        scrollPaneFontSize.getViewport().add(listFontSize, null);
        scrollPaneFontStyle.getViewport().add(listFontStyle, null);
        scrollPaneFontName.getViewport().add(listFontName, null);
    }

	//첨자...
    void checkBoxSuperscript_actionPerformed(ActionEvent e) {
		//자신이 선택되었는지 확인.....
		if (this.checkBoxSuperscript.isSelected()){
			//자신이 선택되어 있는 상황에서 다른쪽이 선태되어 있으면
			//다른쪽 선택을 해제한다..
			if (this.checkBoxSubscript.isSelected()) {
				this.checkBoxSubscript.setSelected(false);
			}
			this.script = FontPreviewPanel.SCRIPT_SUPER;
		}
		else {
		    if (!this.checkBoxSubscript.isSelected()) this.script = FontPreviewPanel.SCRIPT_NONE;
			else this.script = FontPreviewPanel.SCRIPT_SUB;
		}

		this.panelFontPreview.setScript(this.script);
		this.panelFontPreview.repaint();
    }

	//첨자...
    void checkBoxSubscript_actionPerformed(ActionEvent e) {
		//자신이 선택되었는지 확인.....
		if (this.checkBoxSubscript.isSelected()){
			//자신이 선택되어 있는 상황에서 다른쪽이 선태되어 있으면
			//다른쪽 선택을 해제한다..
			if (this.checkBoxSuperscript.isSelected()) {
				this.checkBoxSuperscript.setSelected(false);
			}
			this.script = FontPreviewPanel.SCRIPT_SUB;
		}
		else {
		    if (!this.checkBoxSuperscript.isSelected()) this.script = FontPreviewPanel.SCRIPT_NONE;
			else this.script = FontPreviewPanel.SCRIPT_SUPER;
		}

		this.panelFontPreview.setScript(this.script);
		this.panelFontPreview.repaint();
    }

	//취소선...
    void checkBoxStrikeout_actionPerformed(ActionEvent e) {
		boolean isSelected = this.checkBoxStrikeout.isSelected();
		this.panelFontPreview.setStrikeThrough(isSelected);

		this.strikeThrough = isSelected;

		this.panelFontPreview.repaint();
    }

	//밑줄....
    void comboBoxUnderline_actionPerformed(ActionEvent e) {
		int index = this.comboBoxUnderline.getSelectedIndex();
		this.panelFontPreview.setUnderLine((byte)index);

		this.underline = (byte)index;

		this.panelFontPreview.repaint();
    }

	void listFontName_valueChanged(ListSelectionEvent e) {
		if (this.nowChangeMenu) return;

		this.fontName = (String)this.listFontName.getSelectedValue();
		this.textFieldFontName.setText(this.fontName);
		this.panelFontPreview.setFontName(this.fontName);

		this.panelFontPreview.repaint();
    }

    void listFontStyle_valueChanged(ListSelectionEvent e) {
		if (this.nowChangeMenu) return;

		// 폰트스타일이 변경되었을 때
		String[] style = (String[])bundle.getObject("styles");

		String currentStyle = (String)this.listFontStyle.getSelectedValue();
		if (currentStyle.equals(style[0]) ) {
			this.fontBold = false;
			this.fontItalic = false;
			this.fontStyle = Font.PLAIN;
			panelFontPreview.setFontStyle(Font.PLAIN);
		}
		if (currentStyle.equals(style[1]) ) {
			this.fontBold = false;
			this.fontItalic = true;
			this.fontStyle = Font.ITALIC;
			panelFontPreview.setFontStyle(Font.ITALIC);
		}
		if (currentStyle.equals(style[2]) ) {
			this.fontBold = true;
			this.fontItalic = false;
			this.fontStyle = Font.BOLD;
			panelFontPreview.setFontStyle(Font.BOLD);
		}
		if (currentStyle.equals(style[3]) ) {
			this.fontBold = true;
			this.fontItalic = true;
			this.fontStyle = Font.BOLD + Font.ITALIC;
			panelFontPreview.setFontStyle(Font.BOLD + Font.ITALIC);
		}

		this.textFieldFontStyle.setText(currentStyle);

		this.panelFontPreview.repaint();
    }

    void listFontSize_valueChanged(ListSelectionEvent e) {
		if (this.nowChangeMenu) return;

		int index = this.listFontSize.getSelectedIndex();
		this.fontSize = (short) (Integer.parseInt(this.fontSizeLstArray[index]));
		this.textFieldFontSize.setText((String)this.listFontSize.getSelectedValue());

		this.panelFontPreview.setFontSize(this.fontSize);

		this.panelFontPreview.repaint();
    }

	/**
	 * 자동 크기 조절 event 처리 함수
	 */
	void checkBoxAutoSize_actionPerformed(ActionEvent e) {


    }


	//현재 탭에서 선택된 폰트를 반환....
	public Font getSelectedFont() {
	    Font font;
		System.out.println("Name : "+ this.fontName + " Style : " + this.fontStyle + " Size : " + this.fontSize);
		font = new Font(this.fontName, this.fontStyle, this.fontSize);
		return font;
	}

	//현재 탭에서 선택된 폰트 칼라 반환...
	public Color getFontColor() {
		return this.fontColorButton.getSelectedColor();//this.panelFontPreview.fontColor;
	}

	//밑줄....


	public void actionPerformed(ActionEvent ev) {
		Color color= Color.pink;
		if (ev.getSource() == this.fontColorButton) {
		    this.fontColorButton.panelPopup.show(this.fontColorButton, 0,0);
		}
		else if (ev.getSource() == this.fontColorButton.autoColor) {
		    color = Color.black;
	    }
		else if (ev.getSource() == this.fontColorButton.mainColor) {
			color = this.fontColorButton.mainColor.getColor();
		}

		else if (ev.getSource() == this.fontColorButton.subColor) {
			color = this.fontColorButton.subColor.getColor();
		}


		this.fontColorButton.setSelectedColor(color);
		this.fontColorButton.repaint();

		this.panelFontPreview.setFontColor(color);

	}

	public void changeMenuText() {
		this.nowChangeMenu = true;

	    this.bundle = ResourceBundle.getBundle("jchartui/FontTabBundle", JCalcResource.getLocale());

		border1.setTitle(bundle.getString("effect"));
        border2.setTitle(bundle.getString("preview"));

		checkBoxAutoSize.setText(bundle.getString("autoScale"));//"자동 크기 조정(T)");
		checkBoxStrikeout.setText(bundle.getString("strikethrough"));//"Strikeout");
        checkBoxSuperscript.setText(bundle.getString("superscript"));//"Super script");
        checkBoxSubscript.setText(bundle.getString("subscript"));//"Sub script");
		labelUnderLine.setText(bundle.getString("underline"));//"Underline");
        labelColor.setText(bundle.getString("color"));//"Color");
        labelBackground.setText(bundle.getString("background"));//"Background");
        labelFontName.setText(bundle.getString("font"));//"Font");
        labelFontStyle.setText(bundle.getString("fontStyle"));//"Style");
        labelFontSize.setText(bundle.getString("size"));//"Size");

		JComboBox temp = new JComboBox((String[])bundle.getObject("underlines"));
		this.comboBoxUnderline.setModel(temp.getModel());
		temp = new JComboBox((String[])bundle.getObject("backgrounds"));
		this.comboBoxBackground.setModel(temp.getModel());

		JList listTemp = new JList((String[])bundle.getObject("styles"));
		this.listFontStyle.setModel(listTemp.getModel());


		this.panelFontPreview.string = bundle.getString("sampleText");

		this.fontColorButton.autoColor.setText(bundle.getString("automatic"));

		this.nowChangeMenu = false;
	}


	//==========================================================================
	// ComboBox를 대신해서 button으로 할까??
	//==========================================================================
	public class ColorButton extends JButton{// implements ActionListener{
	    private Color selectedColor = Color.black;

		public ColorPanel mainColor;
		public ColorPanel subColor;
		public JMenuItem autoColor;

		private JPopupMenu panelPopup;

		//상위 객체....
		private ActionListener parentObj;

		Color subBorderColor[][] = { { new Color(0  ,0    , 0), new Color(156,49   , 0) },
							{ new Color(49 ,49   , 0), new Color(0  ,49   , 0) },
							{ new Color(0  ,49  , 99), new Color(0  ,0  , 132) },
							{ new Color(49 ,49 , 156), new Color(49 ,49  , 49) },
							{ new Color(132,  0   ,0), new Color(255, 99   ,0) },
							{ new Color(132,132   ,0), new Color(0  ,132   ,0) },
							{ new Color(0  ,132, 132), new Color(0  ,  0, 255) },
							{ new Color(99 , 99, 156), new Color(132,132, 132) }
						  };

		public ColorButton(ActionListener obj) {
			this.parentObj = obj;

		    mainColor = new ColorPanel();
			subColor = new ColorPanel(2, 8, subBorderColor);
			autoColor = new JMenuItem("");

			panelPopup = new JPopupMenu();

			panelPopup.add(autoColor);
			panelPopup.addSeparator();
			panelPopup.add(mainColor);
			panelPopup.add(subColor);

			mainColor.addActionListener(parentObj);
			subColor.addActionListener(parentObj);
			autoColor.addActionListener(parentObj);

		}

		/*
		public void actionPerformed(ActionEvent ev) {
		    if (ev.getSource() == mainColor) {
				this.selectedColor = mainColor.getColor();
		    }
			else if (ev.getSource() == subColor) {
			    this.selectedColor = subColor.getColor();
			}
			else if (ev.getSource() == autoColor) {
			    //자동 색은 뭔색인지.....모르겠다..
				this.selectedColor = Color.darkGray;
			}
		}
		*/
		public Color getSelectedColor() {
			return this.selectedColor;
		}

		public void setSelectedColor(Color color) {
		    this.selectedColor = color;
			this.setBackground(color);
			this.repaint();
		}

		public void paintComponent(Graphics g) {
		    g.setColor(this.getSelectedColor());
			g.fillRect(0,0, this.getWidth(), this.getHeight());
		}
	}





}