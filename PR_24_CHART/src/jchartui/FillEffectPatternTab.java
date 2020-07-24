//package com.techdigm.chart.editor;
package jchartui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;

//import com.techdigm.chart.Utility;
//import com.techdigm.chart.FillEffect;
import jcosmos.Utility;
import jchart.FillEffect;


//import com.techdigm.chart.JTiledButton;
import java.awt.event.*;
import java.util.ResourceBundle;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class FillEffectPatternTab extends JPanel implements ActionListener{
    JPanel panelPatterns = new JPanel();
    GridLayout gridLayout1 = new GridLayout();

    Border border1;
    JTextField textFieldPatternInfo = new JTextField();
    JLabel labelForeground = new JLabel();
    JLabel labelBackground = new JLabel();



	Color foreColor = Color.black;
	Color backColor = Color.white;

    ColorButton buttonForeColor;// = new ColorButton();
    ColorButton buttonBackColor;// = new ColorButton();

    JTiledButton tileTB_01 = new JTiledButton();
    JTiledButton tileTB_02 = new JTiledButton();
    JTiledButton tileTB_03 = new JTiledButton();
    JTiledButton tileTB_04 = new JTiledButton();
    JTiledButton tileTB_05 = new JTiledButton();
    JTiledButton tileTB_06 = new JTiledButton();
    JTiledButton tileTB_07 = new JTiledButton();
    JTiledButton tileTB_08 = new JTiledButton();
    JTiledButton tileTB_09 = new JTiledButton();
    JTiledButton tileTB_10 = new JTiledButton();
    JTiledButton tileTB_11 = new JTiledButton();
    JTiledButton tileTB_12 = new JTiledButton();
    JTiledButton tileTB_13 = new JTiledButton();
    JTiledButton tileTB_14 = new JTiledButton();
    JTiledButton tileTB_15 = new JTiledButton();
    JTiledButton tileTB_16 = new JTiledButton();
    JTiledButton tileTB_17 = new JTiledButton();
    JTiledButton tileTB_18 = new JTiledButton();
    JTiledButton tileTB_19 = new JTiledButton();
    JTiledButton tileTB_20 = new JTiledButton();
    JTiledButton tileTB_21 = new JTiledButton();
    JTiledButton tileTB_22 = new JTiledButton();
    JTiledButton tileTB_23 = new JTiledButton();
    JTiledButton tileTB_24 = new JTiledButton();
    JTiledButton tileTB_25 = new JTiledButton();
    JTiledButton tileTB_26 = new JTiledButton();
    JTiledButton tileTB_27 = new JTiledButton();
    JTiledButton tileTB_28 = new JTiledButton();
    JTiledButton tileTB_29 = new JTiledButton();
    JTiledButton tileTB_30 = new JTiledButton();
    JTiledButton tileTB_31 = new JTiledButton();
    JTiledButton tileTB_32 = new JTiledButton();
    JTiledButton tileTB_33 = new JTiledButton();
    JTiledButton tileTB_34 = new JTiledButton();
    JTiledButton tileTB_35 = new JTiledButton();
    JTiledButton tileTB_36 = new JTiledButton();
    JTiledButton tileTB_37 = new JTiledButton();
    JTiledButton tileTB_38 = new JTiledButton();
    JTiledButton tileTB_39 = new JTiledButton();
    JTiledButton tileTB_40 = new JTiledButton();
    JTiledButton tileTB_41 = new JTiledButton();
    JTiledButton tileTB_42 = new JTiledButton();
    JTiledButton tileTB_43 = new JTiledButton();
    JTiledButton tileTB_44 = new JTiledButton();
    JTiledButton tileTB_45 = new JTiledButton();
    JTiledButton tileTB_46 = new JTiledButton();
    JTiledButton tileTB_47 = new JTiledButton();
    JTiledButton tileTB_48 = new JTiledButton();


	//FillEffect에서 사용될 Image....
	private Image fillEffectImage = null;
	//이 탭에서 사용되어지는 FillEffect
	private FillEffect fillEffect = null;
	//선택된 tile버튼을 저장...
	static JTiledButton selectedButton = null;
    JLabel labelPattern = new JLabel();

//	ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/FillEffectPatternTabBundle", jcalc.JCalcResource.getLocale());
	ResourceBundle bundle = ResourceBundle.getBundle("jchartui/FillEffectPatternTabBundle", jchartui.JCalcResource.getLocale());
	String[]  patternString = (String[])bundle.getObject("patternString");

    public FillEffectPatternTab() {
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(142, 142, 142),new Color(99, 99, 99));
        this.setLayout(null);
        panelPatterns.setBounds(new Rectangle(20, 30, 274, 162));
        panelPatterns.setLayout(gridLayout1);
        gridLayout1.setRows(6);
        gridLayout1.setColumns(8);
        gridLayout1.setHgap(2);
        gridLayout1.setVgap(2);


        textFieldPatternInfo.setBackground(UIManager.getColor("Button.background"));
        textFieldPatternInfo.setBorder(border1);
        textFieldPatternInfo.setEditable(false);
        textFieldPatternInfo.setBounds(new Rectangle(20, 203, 271, 25));
        labelForeground.setText(bundle.getString("foreground"));
        labelForeground.setBounds(new Rectangle(20, 239, 78, 20));
        labelBackground.setText(bundle.getString("background"));
        labelBackground.setBounds(new Rectangle(183, 239, 87, 20));

		buttonForeColor = new ColorButton(this);
		buttonForeColor.setSelectedColor(Color.black);
		buttonBackColor = new ColorButton(this);
		buttonBackColor.setSelectedColor(Color.white);
        buttonForeColor.setBounds(new Rectangle(19, 262, 108, 22));
		buttonForeColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonForeColor_actionPerformed(e);
            }
        });

		buttonBackColor.setBounds(new Rectangle(183, 262, 108, 22));
		buttonBackColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonBackColor_actionPerformed(e);
            }
        });



		tileTB_01.setIcon(new ImageIcon(Utility.getResource("tile_01.gif")));
		tileTB_02.setIcon(new ImageIcon(Utility.getResource("tile_02.gif")));
		tileTB_03.setIcon(new ImageIcon(Utility.getResource("tile_03.gif")));
		tileTB_04.setIcon(new ImageIcon(Utility.getResource("tile_04.gif")));
		tileTB_05.setIcon(new ImageIcon(Utility.getResource("tile_05.gif")));
		tileTB_06.setIcon(new ImageIcon(Utility.getResource("tile_06.gif")));
		tileTB_07.setIcon(new ImageIcon(Utility.getResource("tile_07.gif")));
		tileTB_08.setIcon(new ImageIcon(Utility.getResource("tile_08.gif")));
		tileTB_09.setIcon(new ImageIcon(Utility.getResource("tile_09.gif")));
		tileTB_10.setIcon(new ImageIcon(Utility.getResource("tile_10.gif")));
		tileTB_11.setIcon(new ImageIcon(Utility.getResource("tile_11.gif")));
		tileTB_12.setIcon(new ImageIcon(Utility.getResource("tile_12.gif")));
		tileTB_13.setIcon(new ImageIcon(Utility.getResource("tile_13.gif")));
		tileTB_14.setIcon(new ImageIcon(Utility.getResource("tile_14.gif")));
		tileTB_15.setIcon(new ImageIcon(Utility.getResource("tile_15.gif")));
		tileTB_16.setIcon(new ImageIcon(Utility.getResource("tile_16.gif")));
		tileTB_17.setIcon(new ImageIcon(Utility.getResource("tile_17.gif")));
		tileTB_18.setIcon(new ImageIcon(Utility.getResource("tile_18.gif")));
		tileTB_19.setIcon(new ImageIcon(Utility.getResource("tile_19.gif")));
		tileTB_20.setIcon(new ImageIcon(Utility.getResource("tile_20.gif")));
		tileTB_21.setIcon(new ImageIcon(Utility.getResource("tile_21.gif")));
		tileTB_22.setIcon(new ImageIcon(Utility.getResource("tile_22.gif")));
		tileTB_23.setIcon(new ImageIcon(Utility.getResource("tile_23.gif")));
		tileTB_24.setIcon(new ImageIcon(Utility.getResource("tile_24.gif")));
		tileTB_25.setIcon(new ImageIcon(Utility.getResource("tile_25.gif")));
		tileTB_26.setIcon(new ImageIcon(Utility.getResource("tile_26.gif")));
		tileTB_27.setIcon(new ImageIcon(Utility.getResource("tile_27.gif")));
		tileTB_28.setIcon(new ImageIcon(Utility.getResource("tile_28.gif")));
		tileTB_29.setIcon(new ImageIcon(Utility.getResource("tile_29.gif")));
		tileTB_30.setIcon(new ImageIcon(Utility.getResource("tile_30.gif")));
		tileTB_31.setIcon(new ImageIcon(Utility.getResource("tile_31.gif")));
		tileTB_32.setIcon(new ImageIcon(Utility.getResource("tile_32.gif")));
		tileTB_33.setIcon(new ImageIcon(Utility.getResource("tile_33.gif")));
		tileTB_34.setIcon(new ImageIcon(Utility.getResource("tile_34.gif")));
		tileTB_35.setIcon(new ImageIcon(Utility.getResource("tile_35.gif")));
		tileTB_36.setIcon(new ImageIcon(Utility.getResource("tile_36.gif")));
		tileTB_37.setIcon(new ImageIcon(Utility.getResource("tile_37.gif")));
		tileTB_38.setIcon(new ImageIcon(Utility.getResource("tile_38.gif")));
		tileTB_39.setIcon(new ImageIcon(Utility.getResource("tile_39.gif")));
		tileTB_40.setIcon(new ImageIcon(Utility.getResource("tile_40.gif")));
		tileTB_41.setIcon(new ImageIcon(Utility.getResource("tile_41.gif")));
		tileTB_42.setIcon(new ImageIcon(Utility.getResource("tile_42.gif")));
		tileTB_43.setIcon(new ImageIcon(Utility.getResource("tile_43.gif")));
		tileTB_44.setIcon(new ImageIcon(Utility.getResource("tile_44.gif")));
		tileTB_45.setIcon(new ImageIcon(Utility.getResource("tile_45.gif")));
		tileTB_46.setIcon(new ImageIcon(Utility.getResource("tile_46.gif")));
		tileTB_47.setIcon(new ImageIcon(Utility.getResource("tile_47.gif")));
		tileTB_48.setIcon(new ImageIcon(Utility.getResource("tile_48.gif")));


        tileTB_01.setBorder(border1);
        tileTB_01.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_01_mouseClicked(e);
            }
        });
        tileTB_02.setBorder(border1);
	    tileTB_02.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_02_mouseClicked(e);
            }
        });
        tileTB_03.setBorder(border1);
        tileTB_03.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_03_mouseClicked(e);
            }
        });
        tileTB_04.setBorder(border1);
        tileTB_04.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_04_mouseClicked(e);
            }
        });
        tileTB_05.setBorder(border1);
        tileTB_05.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_05_mouseClicked(e);
            }
        });
        tileTB_06.setBorder(border1);
        tileTB_06.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_06_mouseClicked(e);
            }
        });
        tileTB_07.setBorder(border1);
        tileTB_07.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_07_mouseClicked(e);
            }
        });
        tileTB_08.setBorder(border1);
        tileTB_08.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_08_mouseClicked(e);
            }
        });
        tileTB_09.setBorder(border1);
        tileTB_09.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_09_mouseClicked(e);
            }
        });
        tileTB_10.setBorder(border1);
        tileTB_10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_10_mouseClicked(e);
            }
        });
        tileTB_11.setBorder(border1);
        tileTB_11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_11_mouseClicked(e);
            }
        });
        tileTB_12.setBorder(border1);
        tileTB_12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_12_mouseClicked(e);
            }
        });
        tileTB_13.setBorder(border1);
        tileTB_13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_13_mouseClicked(e);
            }
        });
        tileTB_14.setBorder(border1);
        tileTB_14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_14_mouseClicked(e);
            }
        });
        tileTB_15.setBorder(border1);
        tileTB_15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_15_mouseClicked(e);
            }
        });
        tileTB_16.setBorder(border1);
        tileTB_16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_16_mouseClicked(e);
            }
        });
        tileTB_17.setBorder(border1);
        tileTB_17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_17_mouseClicked(e);
            }
        });
        tileTB_18.setBorder(border1);
        tileTB_18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_18_mouseClicked(e);
            }
        });
        tileTB_19.setBorder(border1);
        tileTB_19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_19_mouseClicked(e);
            }
        });
        tileTB_20.setBorder(border1);
        tileTB_20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_20_mouseClicked(e);
            }
        });
        tileTB_21.setBorder(border1);
        tileTB_21.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_21_mouseClicked(e);
            }
        });
        tileTB_22.setBorder(border1);
        tileTB_22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_22_mouseClicked(e);
            }
        });
        tileTB_23.setBorder(border1);
        tileTB_23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_23_mouseClicked(e);
            }
        });
        tileTB_24.setBorder(border1);
        tileTB_24.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_24_mouseClicked(e);
            }
        });
        tileTB_25.setBorder(border1);
        tileTB_25.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_25_mouseClicked(e);
            }
        });
        tileTB_26.setBorder(border1);
        tileTB_26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_26_mouseClicked(e);
            }
        });
        tileTB_27.setBorder(border1);
        tileTB_27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_27_mouseClicked(e);
            }
        });
        tileTB_28.setBorder(border1);
        tileTB_28.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_28_mouseClicked(e);
            }
        });
        tileTB_29.setBorder(border1);
        tileTB_29.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_29_mouseClicked(e);
            }
        });
        tileTB_30.setBorder(border1);
        tileTB_30.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_30_mouseClicked(e);
            }
        });
        tileTB_31.setBorder(border1);
        tileTB_31.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_31_mouseClicked(e);
            }
        });
        tileTB_32.setBorder(border1);
        tileTB_32.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_32_mouseClicked(e);
            }
        });
        tileTB_33.setBorder(border1);
        tileTB_33.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_33_mouseClicked(e);
            }
        });
        tileTB_34.setBorder(border1);
        tileTB_34.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_34_mouseClicked(e);
            }
        });
        tileTB_35.setBorder(border1);
        tileTB_35.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_35_mouseClicked(e);
            }
        });
        tileTB_36.setBorder(border1);
        tileTB_36.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_36_mouseClicked(e);
            }
        });
        tileTB_37.setBorder(border1);
        tileTB_37.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_37_mouseClicked(e);
            }
        });
        tileTB_38.setBorder(border1);
        tileTB_38.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_38_mouseClicked(e);
            }
        });
        tileTB_39.setBorder(border1);
        tileTB_39.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_39_mouseClicked(e);
            }
        });
        tileTB_40.setBorder(border1);
        tileTB_40.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_40_mouseClicked(e);
            }
        });
        tileTB_41.setBorder(border1);
        tileTB_41.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_41_mouseClicked(e);
            }
        });
        tileTB_42.setBorder(border1);
        tileTB_42.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_42_mouseClicked(e);
            }
        });
        tileTB_43.setBorder(border1);
        tileTB_43.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_43_mouseClicked(e);
            }
        });
        tileTB_44.setBorder(border1);
        tileTB_44.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_44_mouseClicked(e);
            }
        });
        tileTB_45.setBorder(border1);
        tileTB_45.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_45_mouseClicked(e);
            }
        });
        tileTB_46.setBorder(border1);
        tileTB_46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_46_mouseClicked(e);
            }
        });
        tileTB_47.setBorder(border1);
        tileTB_47.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_47_mouseClicked(e);
            }
        });
        tileTB_48.setBorder(border1);
        tileTB_48.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tileTB_48_mouseClicked(e);
            }
        });

        labelPattern.setText(bundle.getString("pattern"));
        labelPattern.setBounds(new Rectangle(21, 8, 60, 18));
        this.add(panelPatterns, null);
        panelPatterns.add(tileTB_01, null);
        panelPatterns.add(tileTB_02, null);
        panelPatterns.add(tileTB_03, null);
        panelPatterns.add(tileTB_04, null);
        panelPatterns.add(tileTB_05, null);
        panelPatterns.add(tileTB_06, null);
        panelPatterns.add(tileTB_07, null);
        panelPatterns.add(tileTB_08, null);
        panelPatterns.add(tileTB_09, null);
        panelPatterns.add(tileTB_10, null);
        panelPatterns.add(tileTB_11, null);
        panelPatterns.add(tileTB_12, null);
        panelPatterns.add(tileTB_13, null);
        panelPatterns.add(tileTB_14, null);
        panelPatterns.add(tileTB_15, null);
        panelPatterns.add(tileTB_16, null);
        panelPatterns.add(tileTB_17, null);
        panelPatterns.add(tileTB_18, null);
        panelPatterns.add(tileTB_19, null);
        panelPatterns.add(tileTB_20, null);
        panelPatterns.add(tileTB_21, null);
        panelPatterns.add(tileTB_22, null);
        panelPatterns.add(tileTB_23, null);
        panelPatterns.add(tileTB_24, null);
        panelPatterns.add(tileTB_25, null);
        panelPatterns.add(tileTB_26, null);
        panelPatterns.add(tileTB_27, null);
        panelPatterns.add(tileTB_28, null);
        panelPatterns.add(tileTB_29, null);
        panelPatterns.add(tileTB_30, null);
        panelPatterns.add(tileTB_31, null);
        panelPatterns.add(tileTB_32, null);
        panelPatterns.add(tileTB_33, null);
        panelPatterns.add(tileTB_34, null);
        panelPatterns.add(tileTB_35, null);
        panelPatterns.add(tileTB_36, null);
        panelPatterns.add(tileTB_37, null);
        panelPatterns.add(tileTB_38, null);
        panelPatterns.add(tileTB_39, null);
        panelPatterns.add(tileTB_40, null);
        panelPatterns.add(tileTB_41, null);
        panelPatterns.add(tileTB_42, null);
        panelPatterns.add(tileTB_43, null);
        panelPatterns.add(tileTB_44, null);
        panelPatterns.add(tileTB_45, null);
        panelPatterns.add(tileTB_46, null);
        panelPatterns.add(tileTB_47, null);
        panelPatterns.add(tileTB_48, null);

        this.add(textFieldPatternInfo, null);
        this.add(buttonBackColor, null);
        this.add(labelBackground, null);
        this.add(buttonForeColor, null);
        this.add(labelForeground, null);
        this.add(labelPattern, null);
    }////implements MouseListener, ActionListener{



	//Fore/Back ground Color 지정/반환
    public void setForeColor(Color color ) {
	    this.foreColor = color;
    }
	public void setSecondColor(Color color) {
		this.backColor = color;
	}
	public Color getFirstColor() {
		return this.foreColor;
	}
	public Color getBackColor() {
		return this.backColor;
	}

    void tileTB_01_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[0]);
		this.selectTexture(tileTB_01);
    }

    void tileTB_02_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[1]);
		this.selectTexture(tileTB_02);
    }


    void tileTB_03_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[2]);
		this.selectTexture(tileTB_03);
    }

    void tileTB_04_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[3]);
		this.selectTexture(tileTB_04);
    }

    void tileTB_05_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[4]);
		this.selectTexture(tileTB_05);
    }

    void tileTB_06_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[5]);
		this.selectTexture(tileTB_06);
    }

    void tileTB_07_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[6]);
		this.selectTexture(tileTB_07);
    }

    void tileTB_08_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[7]);
		this.selectTexture(tileTB_08);
    }

    void tileTB_09_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[8]);
		this.selectTexture(tileTB_09);
    }

    void tileTB_10_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[9]);
		this.selectTexture(tileTB_10);
    }

    void tileTB_11_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[10]);
		this.selectTexture(tileTB_11);
    }

    void tileTB_12_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[11]);
		this.selectTexture(tileTB_12);
    }

    void tileTB_13_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[12]);
		this.selectTexture(tileTB_13);
    }

    void tileTB_14_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[13]);
		this.selectTexture(tileTB_14);
    }

    void tileTB_15_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[14]);
		this.selectTexture(tileTB_15);
    }

    void tileTB_16_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[15]);
		this.selectTexture(tileTB_16);
    }

    void tileTB_17_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[16]);
		this.selectTexture(tileTB_17);
    }

    void tileTB_18_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[17]);
		this.selectTexture(tileTB_18);
    }

    void tileTB_19_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[18]);
		this.selectTexture(tileTB_19);
    }

    void tileTB_20_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[19]);
		this.selectTexture(tileTB_20);
    }

    void tileTB_21_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[20]);
		this.selectTexture(tileTB_21);
    }

    void tileTB_22_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[21]);
		this.selectTexture(tileTB_22);
    }

    void tileTB_23_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[22]);
		this.selectTexture(tileTB_23);
    }

    void tileTB_24_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[23]);
		this.selectTexture(tileTB_24);
    }

    void tileTB_25_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[24]);
		this.selectTexture(tileTB_25);
    }

    void tileTB_26_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[25]);
		this.selectTexture(tileTB_26);
    }

    void tileTB_27_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[26]);
		this.selectTexture(tileTB_27);
    }

    void tileTB_28_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[27]);
		this.selectTexture(tileTB_28);
    }

    void tileTB_29_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[28]);
		this.selectTexture(tileTB_29);
    }

    void tileTB_30_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[29]);
		this.selectTexture(tileTB_30);
    }

    void tileTB_31_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[30]);
		this.selectTexture(tileTB_31);
    }

    void tileTB_32_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[31]);
		this.selectTexture(tileTB_32);
    }

    void tileTB_33_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[32]);
		this.selectTexture(tileTB_33);
    }

    void tileTB_34_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[33]);
		this.selectTexture(tileTB_34);
    }

    void tileTB_35_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[34]);
		this.selectTexture(tileTB_35);
    }

    void tileTB_36_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[35]);
		this.selectTexture(tileTB_36);
    }

    void tileTB_37_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[36]);
		this.selectTexture(tileTB_37);
    }

    void tileTB_38_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[37]);
		this.selectTexture(tileTB_38);
    }

    void tileTB_39_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[38]);
		this.selectTexture(tileTB_39);
    }

    void tileTB_40_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[39]);
		this.selectTexture(tileTB_40);
    }

    void tileTB_41_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[40]);
		this.selectTexture(tileTB_41);
    }

    void tileTB_42_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[41]);
		this.selectTexture(tileTB_42);
    }

    void tileTB_43_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[42]);
		this.selectTexture(tileTB_43);
    }

    void tileTB_44_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[43]);
		this.selectTexture(tileTB_44);
    }

    void tileTB_45_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[44]);
		this.selectTexture(tileTB_45);
    }

    void tileTB_46_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[45]);
		this.selectTexture(tileTB_46);
    }

    void tileTB_47_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[46]);
		this.selectTexture(tileTB_47);
    }

    void tileTB_48_mouseClicked(MouseEvent e) {
		this.textFieldPatternInfo.setText(this.patternString[47]);
		this.selectTexture(tileTB_48);
    }

	//현재 선택된 Pattern의 이미지를 얻어서 설정...
	void selectTexture(JTiledButton button) {
		this.selectedButton = button;
		Icon icon = null;
		if (this.selectedButton == null) {
			this.fillEffectImage = null;
//			System.out.println("button이 널이라네.... 웃기네..");
		}
		else icon = button.getIcon();

		if (icon == null) this.fillEffectImage = null;
		else {
		    this.fillEffectImage = ((ImageIcon)icon).getImage();
//		    System.out.println("정상......");
		}

		FillEffectEditor.showCurrentFillEffect(this.getFillEffect());
	}

	//Color Button의 팝업을 보여 준다..
	void buttonForeColor_actionPerformed(ActionEvent e) {
	    this.buttonForeColor.panelPopup.show(this.buttonForeColor, 0,0);
	}
	void buttonBackColor_actionPerformed(ActionEvent e) {
		this.buttonBackColor.panelPopup.show(this.buttonBackColor, 0,0);
	}

	//각 color Button내의 이벤트 처리...
	public void actionPerformed(ActionEvent e) {
	    Color color;
		if (e.getSource() == this.buttonForeColor.mainColorPanel) {
			color = this.buttonForeColor.mainColorPanel.getColor();
			this.buttonForeColor.setSelectedColor(color);
			this.foreColor = color;
			JTiledButton.firstTileColor = color;

			resetTiledImages();

			this.selectTexture(this.selectedButton);
	    }
		else if (e.getSource() == this.buttonForeColor.subColorPanel) {
			color = this.buttonForeColor.subColorPanel.getColor();
			this.buttonForeColor.setSelectedColor(color);
			this.foreColor = color;
		    JTiledButton.firstTileColor = color;
			resetTiledImages();

			this.selectTexture(this.selectedButton);
		}
		else if (e.getSource() == this.buttonBackColor.mainColorPanel) {
			color = this.buttonBackColor.mainColorPanel.getColor();
			this.buttonBackColor.setSelectedColor(color);
			this.backColor = color;
		    JTiledButton.secondTileColor = color;
			resetTiledImages();

			this.selectTexture(this.selectedButton);
		}
		else if (e.getSource() == this.buttonBackColor.subColorPanel) {
			color = this.buttonForeColor.subColorPanel.getColor();
			this.buttonBackColor.setSelectedColor(color);
			this.backColor = color;
		    JTiledButton.secondTileColor = color;
			resetTiledImages();

			this.selectTexture(this.selectedButton);
		}

	}

	public void resetTiledImages() {

     this.tileTB_01.setIcon( null );
     this.tileTB_02.setIcon( null );
     this.tileTB_03.setIcon( null );
     this.tileTB_04.setIcon( null );
     this.tileTB_05.setIcon( null );
     this.tileTB_06.setIcon( null );
     this.tileTB_07.setIcon( null );
     this.tileTB_08.setIcon( null );
     this.tileTB_09.setIcon( null );
     this.tileTB_10.setIcon( null );
     this.tileTB_11.setIcon( null );
     this.tileTB_12.setIcon( null );
     this.tileTB_13.setIcon( null );
     this.tileTB_14.setIcon( null );
     this.tileTB_15.setIcon( null );
     this.tileTB_16.setIcon( null );
     this.tileTB_17.setIcon( null );
     this.tileTB_18.setIcon( null );
     this.tileTB_19.setIcon( null );
     this.tileTB_20.setIcon( null );
     this.tileTB_21.setIcon( null );
     this.tileTB_22.setIcon( null );
     this.tileTB_23.setIcon( null );
     this.tileTB_24.setIcon( null );
     this.tileTB_25.setIcon( null );
     this.tileTB_26.setIcon( null );
     this.tileTB_27.setIcon( null );
     this.tileTB_28.setIcon( null );
     this.tileTB_29.setIcon( null );
     this.tileTB_30.setIcon( null );
     this.tileTB_31.setIcon( null );
     this.tileTB_32.setIcon( null );
     this.tileTB_33.setIcon( null );
     this.tileTB_34.setIcon( null );
     this.tileTB_35.setIcon( null );
     this.tileTB_36.setIcon( null );
     this.tileTB_37.setIcon( null );
     this.tileTB_38.setIcon( null );
     this.tileTB_39.setIcon( null );
     this.tileTB_40.setIcon( null );
     this.tileTB_41.setIcon( null );
     this.tileTB_42.setIcon( null );
     this.tileTB_43.setIcon( null );
     this.tileTB_44.setIcon( null );
     this.tileTB_45.setIcon( null );
     this.tileTB_46.setIcon( null );
     this.tileTB_47.setIcon( null );
     this.tileTB_48.setIcon( null );


     new Thread() {
        public void run() {
           panelPatterns.repaint();
        }
     }.start();

  }

    //FillEffect를 설정하고 반환...
	public void setFillEffect (FillEffect fillEffect) {
		this.fillEffect = fillEffect;
	}

	public FillEffect getFillEffect() {
		//FillEffect에 저장할 이미지가 없으면 null을 반환...
		if (this.fillEffectImage == null) return null;

		if (this.fillEffect == null) fillEffect = new FillEffect();

		this.fillEffect.setImage(this.fillEffectImage);

		//반환 할때... 복사본을 반환하도록...
		// 다이얼로그가 항상 같은 놈이기 때문에... 적용받았던 다른 쪽에 영향을 끼칠 수 있다..
		return this.fillEffect.create();
	}


	public void changeMenuText() {
//		this.bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/FillEffectPatternTabBundle", jcalc.JCalcResource.getLocale());
		this.bundle = ResourceBundle.getBundle("jchartui/FillEffectPatternTabBundle", jchartui.JCalcResource.getLocale());
		this.patternString = (String[])bundle.getObject("patternString");

		this.labelBackground.setText(bundle.getString("background"));
		this.labelForeground.setText(bundle.getString("foreground"));

		this.labelPattern.setText(bundle.getString("pattern"));
	}

	//==========================================================================
	// ComboBox를 대신해서 button으로 할까??
	//==========================================================================
	public class ColorButton extends JButton{// implements ActionListener{
	    private Color selectedColor = Color.black;

		public ColorPanel mainColorPanel;
		public ColorPanel subColorPanel;
//		public JMenuItem autoColorButton;

		private JPopupMenu panelPopup;

		//상위 객체....
		private ActionListener parentObj;

		Color subColor[][] = { { new Color(0  ,0    , 0), new Color(156,49   , 0) },
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

		    mainColorPanel = new ColorPanel();
			subColorPanel = new ColorPanel(2, 8, subColor);
//			autoColorButton = new JMenuItem("");

			panelPopup = new JPopupMenu();

//			panelPopup.add(autoColorButton);
//			panelPopup.addSeparator();
			panelPopup.add(mainColorPanel);
			panelPopup.add(subColorPanel);

			mainColorPanel.addActionListener(parentObj);
			subColorPanel.addActionListener(parentObj);
//			autoColor.addActionListener(parentObj);

		}

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



