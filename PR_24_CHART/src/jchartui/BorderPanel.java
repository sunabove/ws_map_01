//package com.techdigm.chart.editor;
package jchartui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
//import jcalc.*;
import java.awt.event.*;


import java.util.ResourceBundle;
import java.util.Locale;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class BorderPanel extends JPanel  implements ActionListener{

	PreviewPanelInPatternTab panelPreview;

	public static final byte LINE_MODE = 1;
	public static final byte AREA_MODE = 2;

	private byte mode;

	private String[] borderStyle = {
		"1����Ÿ��",
		"2����Ÿ��",
		"3����Ÿ��",
		"4����Ÿ��",
//		"5����Ÿ��",
//		"6����Ÿ��",
//		"7����Ÿ��",
//		"8����Ÿ��"
	};

	private String[] borderWeight = {
		"�β�1",
		"�β�2",
		"�β�3",
		"�β�4",
		"�β�5",
	};

	private float[][] style = {
	    {},
		{20, 5},
		{5, 5},
		{10, 5, 5},
		{10, 2 },

	};


	// �׵θ� �ڵ�, ����, ����� ���� RadioButton�� �����Ѵ�.
	JRadioButton radioButtonBorderAuto = new JRadioButton();
	JRadioButton radioButtonBorderNone = new JRadioButton();
    JRadioButton radioButtonBorderCustom = new JRadioButton();

    // ��Ÿ��, ��, ������ �ش��ϴ� comboBox�� �����.
	// �� comboBox�� ColorButton Inner Class�� ����Ѵ�.
    JComboBox comboBoxBorderWeight = new JComboBox(this.borderWeight);
	ColorButton buttonBorderColor = new ColorButton(this);
	JComboBox comboBoxBorderStyle = new JComboBox(this.borderStyle);

	// comboBox �տ� �ִ� Label�� �����.
    JLabel labelBorderStyle = new JLabel();
    JLabel labelBorderColor = new JLabel();
    JLabel labelBorderWeight = new JLabel();

	JCheckBox checkBoxBorderShadow = new JCheckBox();
	JCheckBox checkBoxBorderSmooth = new JCheckBox();


	public BorderPanel( byte mode ) {

		this.mode = mode;

        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

		this.radioButtonBorderAuto.setSelected(true);

    }

    private void jbInit() throws Exception {

		// Component���� Layout�� ���Ѵ�.
		this.setLayout(null);

		//Component Addition
		this.add(radioButtonBorderAuto, null);
        this.add(radioButtonBorderNone, null);
		this.add(radioButtonBorderCustom, null);
        this.add(labelBorderStyle, null);
        this.add(labelBorderColor, null);
        this.add(labelBorderWeight, null);
        this.add(buttonBorderColor, null);
        this.add(comboBoxBorderStyle, null);
        this.add(comboBoxBorderWeight, null);
		this.add(checkBoxBorderShadow, null);

		// radioButton�� �׷�ȭ �Ѵ�.
		ButtonGroup groupBorder = new ButtonGroup();
		groupBorder.add(radioButtonBorderAuto);
		groupBorder.add(radioButtonBorderNone);
		groupBorder.add(radioButtonBorderCustom);

		// mode�� ���� Component���� Enable�� �����Ѵ�.
		if (this.mode == AREA_MODE) this.add(checkBoxBorderShadow, null);
		if(this.mode == LINE_MODE) radioButtonBorderNone.setEnabled(false);


		// Component���� Bounds�� ���Ѵ�.
		radioButtonBorderAuto.setBounds(new Rectangle(10, 20, 104, 24));
        radioButtonBorderNone.setBounds(new Rectangle(10, 42, 97, 20));
		radioButtonBorderCustom.setBounds(new Rectangle(10, 61, 100, 21));
		comboBoxBorderStyle.setBounds(new Rectangle(72, 90, 102, 19));
		buttonBorderColor.setBounds(new Rectangle(72, 115, 102, 19));
		comboBoxBorderWeight.setBounds(new Rectangle(72, 147, 102, 19));
		checkBoxBorderShadow.setBounds(new Rectangle(10, 174, 95, 21));
		labelBorderStyle.setBounds(new Rectangle(10, 90, 57, 19));
		labelBorderWeight.setBounds(new Rectangle(10, 147, 57, 19));


		// Componemt���� �����ʸ� ����Ѵ�.
		 radioButtonBorderAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonBorderAuto_actionPerformed(e);
            }
        });
		radioButtonBorderNone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonBorderNone_actionPerformed(e);
            }
        });
		radioButtonBorderCustom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonBorderCustom_actionPerformed(e);
            }
        });
		comboBoxBorderStyle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comboBoxBorderStyle_actionPerformed(e);
            }
        });
		comboBoxBorderWeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comboBoxBorderWeight_actionPerformed(e);
            }
        });
        checkBoxBorderShadow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBoxBorderShadow_actionPerformed(e);
            }
        });
		buttonBorderColor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
                buttonBorderColor_actionPerformed(e);
            }
        });

		// ����Ű ����
		radioButtonBorderAuto.setMnemonic('A');
        radioButtonBorderNone.setMnemonic('N');
		checkBoxBorderShadow.setMnemonic('D');

		// Text ����
		changeMenuText();


    }

	public void setPreviewPanelInPatternTab( PreviewPanelInPatternTab panelPreview) {

		this.panelPreview = panelPreview;

	}

	public void changeMenuText() {

	    ResourceBundle bundle = ResourceBundle.getBundle("jchartui/BorderPanelBundle", JCalcResource.getLocale());

        radioButtonBorderAuto.setText(bundle.getString("borderAuto"));
        radioButtonBorderNone.setText(bundle.getString("borderNone"));
        radioButtonBorderCustom.setText(bundle.getString("borderCustom"));
        checkBoxBorderShadow.setText(bundle.getString("shadow"));
        labelBorderStyle.setText(bundle.getString("style"));
        labelBorderColor.setText(bundle.getString("borderColor"));
        labelBorderWeight.setText(bundle.getString("weight"));
		this.buttonBorderColor.autoColor.setText(bundle.getString("automatic"));

	}


	//==========================================================================
	// �̺�Ʈ ó��...
	//==========================================================================
	public void actionPerformed(ActionEvent ev) {
	    Color color = Color.black;
		if (ev.getSource() == this.buttonBorderColor.autoColor ) {
	        //??
		}
		else if (ev.getSource() == this.buttonBorderColor.mainColor) {
			color = this.buttonBorderColor.mainColor.getColor();
		}
		else if (ev.getSource() == this.buttonBorderColor.subColor) {
		    color = this.buttonBorderColor.subColor.getColor();
		}

		this.buttonBorderColor.setSelectedColor(color);
		panelPreview.setBorderColor(color);

		this.radioButtonBorderCustom.setSelected(true);

		panelPreview.repaint();

	}

	//Border
	void radioButtonBorderAuto_actionPerformed(ActionEvent e) {
		//�ڵ��϶�.. ???

		int index = 0;
		Color color = Color.black;
		// Dash Style ����..
		this.comboBoxBorderStyle.setSelectedIndex(index);
		this.panelPreview.setDashStyle(this.getDashStyle(index));
		// LineWidth ����..
		this.comboBoxBorderWeight.setSelectedIndex(index);
		this.panelPreview.setLineWidth(this.getLineWidth(index));
		//Border Color
		this.buttonBorderColor.setSelectedColor(color);
		this.panelPreview.setBorderColor(color);

		this.panelPreview.setEnableBorder(true);

		this.panelPreview.repaint();

		this.radioButtonBorderAuto.setSelected(true);
    }

    void radioButtonBorderNone_actionPerformed(ActionEvent e) {
		//Border������ ����....
		this.panelPreview.setEnableBorder(false);
		this.panelPreview.repaint();
    }

    void radioButtonBorderCustom_actionPerformed(ActionEvent e) {
		//���� �޺��ڽ��� ���õǾ� �ִ� Ÿ������ Border�� �����ϰ� �����Ų��.

		int index;
		Color color;
		// Dash Style ����..
		index = this.comboBoxBorderStyle.getSelectedIndex();
		this.panelPreview.setDashStyle(this.getDashStyle(index));
		// LineWidth ����..
		index = this.comboBoxBorderWeight.getSelectedIndex();
		this.panelPreview.setLineWidth(this.getLineWidth(index));
		//Border Color
		color = this.buttonBorderColor.getSelectedColor();
		this.panelPreview.setBorderColor(color);

		this.panelPreview.setEnableBorder(true);

		this.panelPreview.repaint();
    }


	float[] getDashStyle(int index) {
		float[] style;

		if (index == 0) style = null;
		else {
		    style = this.style[index];
		}
		return style;
    }

	float getLineWidth(int index) {
		return index * 2 +1;
	}

	void comboBoxBorderStyle_actionPerformed(ActionEvent e) {

		int index = this.comboBoxBorderStyle.getSelectedIndex();

		float[] style = this.getDashStyle(index);

		this.panelPreview.setDashStyle(style);

		this.radioButtonBorderCustom.setSelected(true);
		this.panelPreview.setEnableBorder(true);

		this.panelPreview.repaint();
    }

	void buttonBorderColor_actionPerformed(ActionEvent e) {
		buttonBorderColor.panelPopup.show(this.buttonBorderColor, 0,0);
    }


    void comboBoxBorderWeight_actionPerformed(ActionEvent e) {
		int index = this.comboBoxBorderWeight.getSelectedIndex()*2 +1;
		this.panelPreview.setLineWidth((float)index);
		this.radioButtonBorderCustom.setSelected(true);

		this.panelPreview.setEnableBorder(true);

		this.panelPreview.repaint();
    }

	void checkBoxBorderShadow_actionPerformed(ActionEvent e) {
		this.panelPreview.setShadow(this.checkBoxBorderShadow.isSelected());
		this.panelPreview.repaint();
    }



	//==========================================================================
	// ComboBox�� ����ؼ� button���� �ұ�??
	//==========================================================================
	public class ColorButton extends JButton{// implements ActionListener{
	    private Color selectedColor = Color.black;

		public ColorPanel mainColor;
		public ColorPanel subColor;
		public JMenuItem autoColor;

		private JPopupMenu panelPopup;

		//���� ��ü....
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
			    //�ڵ� ���� ��������.....�𸣰ڴ�..
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
