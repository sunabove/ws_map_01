//package com.techdigm.chart.editor;
package jchartui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
//import jcalc.*;
import java.awt.event.*;

//import com.techdigm.chart.*;
import jchart.*;

import java.awt.geom.Rectangle2D;

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

public class PatternTabForAxis extends JPanel implements ActionListener {

	private String[] borderStyle = {
		"1번스타일",
		"2번스타일",
		"3번스타일",
		"4번스타일",
//		"5번스타일",
//		"6번스타일",
//		"7번스타일",
//		"8번스타일"
	};

	private String[] borderWeight = {
		"두께1",
		"두께2",
		"두께3",
		"두께4",
		"두께5",
	};

	private float[][] style = {
	    {},
		{20, 5},
		{5, 5},
		{10, 5, 5},
		{10, 2 },

	};

	Color subColor[][] = { { new Color(0  ,0    , 0), new Color(156,49   , 0) },
							{ new Color(49 ,49   , 0), new Color(0  ,49   , 0) },
							{ new Color(0  ,49  , 99), new Color(0  ,0  , 132) },
							{ new Color(49 ,49 , 156), new Color(49 ,49  , 49) },
							{ new Color(132,  0   ,0), new Color(255, 99   ,0) },
							{ new Color(132,132   ,0), new Color(0  ,132   ,0) },
							{ new Color(0  ,132, 132), new Color(0  ,  0, 255) },
							{ new Color(99 , 99, 156), new Color(132,132, 132) }
						  };

	//BorderColorPanel
    JPanel panelBorder = new JPanel();
    JPanel panelPreview = new JPanel();

	JRadioButton radioButtonBorderAuto = new JRadioButton();
    JRadioButton radioButtonBorderNone = new JRadioButton();
    JRadioButton radioButtonBorderCustom = new JRadioButton();
    JComboBox comboBoxBorderWeight = new JComboBox(this.borderWeight);
//    JComboBox comboBoxBorderColor = new JComboBox();
    ColorButton buttonBorderColor = new ColorButton(this);
	JComboBox comboBoxBorderStyle = new JComboBox(this.borderStyle);
    Border border1;
    TitledBorder titledBorder1;
    JLabel labelBorderStyle = new JLabel();
    JLabel labelBorderColor = new JLabel();
    JLabel labelBorderWeight = new JLabel();

	Border border2;
    TitledBorder titledBorder2;

	//미리보기 판넬....
	PreviewPanel panelPreviewArea;

    Border border4;
    TitledBorder titledBorder3;

	JCheckBox checkBoxBorderShadow = new JCheckBox();


    //AreaColorPanel
    JPanel panelMajorTickMark = new JPanel();

//			jcalc.ColorPanel mainAreaColor = new jcalc.ColorPanel();
//			jcalc.ColorPanel subAreaColor = new jcalc.ColorPanel(2, 8, subColor);




	public static final byte LINE_MODE = 1;
	public static final byte AREA_MODE = 2;

	private byte mode;
    JPanel panelMinorTickMark = new JPanel();
    JRadioButton radioButtonMinorTickMarkOutside = new JRadioButton();
    JRadioButton radioButtonMinorTickMarkNone = new JRadioButton();
    JRadioButton radioButtonMinorTickMarkInside = new JRadioButton();
    JRadioButton radioButtonMinorTickMarkCross = new JRadioButton();
    JRadioButton radioButtonMajorTickMarkOutside = new JRadioButton();
    JRadioButton radioButtonMajorTickMarkNone = new JRadioButton();
    JRadioButton radioButtonMajorTickMarkInside = new JRadioButton();
    JRadioButton radioButtonMajorTickMarkCross = new JRadioButton();
    JPanel panelTickLabelPosition = new JPanel();
    JRadioButton radioButtonTickLabelPositionLow = new JRadioButton();
    JRadioButton radioButtonTickLabelPositionNone = new JRadioButton();
    JRadioButton radioButtonTickLabelPositionNextToAxis = new JRadioButton();
    JRadioButton radioButtonTickLabelPositionHigh = new JRadioButton();


	public PatternTabForAxis() {
		//
		this.mode = PatternTabForAxis.AREA_MODE;
		this.panelPreviewArea = new PreviewPanel(mode);

        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

//		this.radioButtonAreaAuto.setSelected(true);
//		this.radioButtonBorderAuto.setSelected(true);

    }

	public PatternTabForAxis(byte mode) {
		//
		this.mode = mode;
		this.panelPreviewArea = new PreviewPanel(mode);

        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

//		this.radioButtonAreaAuto.setSelected(true);
//		this.radioButtonBorderAuto.setSelected(true);

    }


    private void jbInit() throws Exception {
		//ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/PatternTabBundle", JCalcResource.getLocale());
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/PatternTabBundle", jchartui.JCalcResource.getLocale());

		this.setLayout(null);

		border1 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
        titledBorder1 = new TitledBorder(border1, bundle.getString("border"));
        border2 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
        titledBorder2 = new TitledBorder(border2, bundle.getString("sample"));

        border4 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
        titledBorder3 = new TitledBorder(border4, bundle.getString("area"));

        panelBorder.setBorder(titledBorder1);
        panelBorder.setBounds(new Rectangle(6, 6, 182, 206));
        panelBorder.setLayout(null);

		panelPreview.setBorder(titledBorder2);
        panelPreview.setBounds(new Rectangle(7, 216, 182, 64));
        panelPreview.setLayout(null);


		radioButtonBorderAuto.setText(bundle.getString("borderAuto"));
        radioButtonBorderAuto.setBounds(new Rectangle(10, 20, 104, 24));
        radioButtonBorderAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonBorderAuto_actionPerformed(e);
            }
        });
        radioButtonBorderNone.setText(bundle.getString("borderNone"));
        radioButtonBorderNone.setBounds(new Rectangle(10, 42, 97, 20));
        radioButtonBorderNone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonBorderNone_actionPerformed(e);
            }
        });
        radioButtonBorderCustom.setText(bundle.getString("borderCustom"));
        radioButtonBorderCustom.setBounds(new Rectangle(10, 61, 100, 21));
        radioButtonBorderCustom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonBorderCustom_actionPerformed(e);
            }
        });
        ButtonGroup groupBorder = new ButtonGroup();
        comboBoxBorderStyle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comboBoxBorderStyle_actionPerformed(e);
            }
        });

		this.buttonBorderColor.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
                buttonBorderColor_actionPerformed(e);
            }
        });
/*      comboBoxBorderColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comboBoxBorderColor_actionPerformed(e);
            }
        });
*/      comboBoxBorderWeight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comboBoxBorderWeighpanelTickLabelPosition_actionPerformed(e);
            }
        });
        checkBoxBorderShadow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBoxBorderShadow_actionPerformed(e);
            }
        });
        checkBoxBorderShadow.setText(bundle.getString("shadow"));
        checkBoxBorderShadow.setBounds(new Rectangle(10, 174, 95, 21));



        panelMajorTickMark.setBorder(titledBorder3);
        panelMajorTickMark.setBounds(new Rectangle(195, 7, 186, 60));
        panelMajorTickMark.setLayout(null);
        panelMinorTickMark.setLayout(null);
        panelMinorTickMark.setBounds(new Rectangle(194, 78, 186, 60));
        panelMinorTickMark.setBorder(titledBorder3);
        panelTickLabelPosition.setLayout(null);
        panelTickLabelPosition.setBounds(new Rectangle(201, 151, 178, 60));
        panelTickLabelPosition.setBorder(titledBorder3);
        radioButtonMinorTickMarkOutside.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonMinorTickMarkOutside_actionPerformed(e);
            }
        });
        radioButtonMinorTickMarkOutside.setBounds(new Rectangle(100, 9, 75, 18));
        radioButtonMinorTickMarkNone.setBounds(new Rectangle(10, 9, 75, 18));
        radioButtonMinorTickMarkInside.setBounds(new Rectangle(10, 32, 75, 18));
        radioButtonMinorTickMarkCross.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonMinorTickMarkCross_actionPerformed(e);
            }
        });
        radioButtonMinorTickMarkCross.setBounds(new Rectangle(100, 32, 75, 18));
        radioButtonMajorTickMarkOutside.setBounds(new Rectangle(99, 9, 75, 18));
        radioButtonMajorTickMarkOutside.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonMajorTickMarkOutside_actionPerformed(e);
            }
        });
        radioButtonMajorTickMarkNone.setBounds(new Rectangle(9, 9, 75, 18));
        radioButtonMajorTickMarkInside.setBounds(new Rectangle(9, 32, 75, 18));
        radioButtonMajorTickMarkCross.setBounds(new Rectangle(99, 32, 75, 18));
        radioButtonMajorTickMarkCross.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonMajorTickMarkCross_actionPerformed(e);
            }
        });
        panelTickLabelPosition.setBorder(titledBorder3);
        panelTickLabelPosition.setBounds(new Rectangle(194, 152, 186, 60));
        panelTickLabelPosition.setLayout(null);
        radioButtonTickLabelPositionLow.setBounds(new Rectangle(9, 33, 75, 18));
        radioButtonTickLabelPositionNone.setBounds(new Rectangle(9, 10, 75, 18));
        radioButtonTickLabelPositionNextToAxis.setBounds(new Rectangle(99, 33, 75, 18));
        radioButtonTickLabelPositionNextToAxis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonTickLabelPositionNextToAxis_actionPerformed(e);
            }
        });
        radioButtonTickLabelPositionHigh.setBounds(new Rectangle(98, 11, 75, 18));
        radioButtonTickLabelPositionHigh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonTickLabelPositionHigh_actionPerformed(e);
            }
        });
        groupBorder.add(radioButtonBorderAuto);
		groupBorder.add(radioButtonBorderNone);

		if(this.mode == LINE_MODE) radioButtonBorderNone.setEnabled(false);

		groupBorder.add(radioButtonBorderCustom);

		comboBoxBorderWeight.setBounds(new Rectangle(72, 147, 102, 19));
//        comboBoxBorderColor.setBounds(new Rectangle(58, 115, 102, 19));
        buttonBorderColor.setBounds(new Rectangle(72, 115, 102, 19));
		buttonBorderColor.autoColor.setText(bundle.getString("automatic"));

		comboBoxBorderStyle.setBounds(new Rectangle(72, 90, 102, 19));
        labelBorderStyle.setText(bundle.getString("style"));
        labelBorderStyle.setBounds(new Rectangle(10, 90, 57, 19));
        labelBorderColor.setText(bundle.getString("borderColor"));
        labelBorderColor.setBounds(new Rectangle(10, 119, 57, 19));
        labelBorderWeight.setText(bundle.getString("weight"));
        labelBorderWeight.setBounds(new Rectangle(10, 147, 57, 19));

        panelPreviewArea.setBounds(new Rectangle(12, 20, 145, 35));

		ButtonGroup groupArea = new ButtonGroup();

		panelPreview.add(panelPreviewArea, null);

        this.add(panelBorder, null);
        panelBorder.add(radioButtonBorderAuto, null);
        panelBorder.add(radioButtonBorderNone, null);
        panelBorder.add(radioButtonBorderCustom, null);
        panelBorder.add(labelBorderStyle, null);
        panelBorder.add(labelBorderColor, null);
        panelBorder.add(labelBorderWeight, null);
        //panelBorder.add(comboBoxBorderColor, null);
		panelBorder.add(buttonBorderColor, null);
        panelBorder.add(checkBoxBorderShadow, null);
        panelBorder.add(comboBoxBorderWeight, null);
        panelBorder.add(comboBoxBorderStyle, null);
		if (this.mode == AREA_MODE) panelBorder.add(checkBoxBorderShadow, null);

		this.add(panelPreview, null);
        this.add(panelMinorTickMark, null);
        this.add(panelMajorTickMark, null);
        this.add(panelTickLabelPosition, null);

		if (this.mode == AREA_MODE) {
//			this.add(panelMajorTickMark, null);
//			panelMajorTickMark.add(radioButtonAreaEmpty, null);
//			panelMajorTickMark.add(radioButtonAreaAuto, null);
//			panelMajorTickMark.add(labelAreaColor, null);
//			panelMajorTickMark.add(panelAreaColor, null);
//			panelAreaColor.add(mainAreaColor, null);
//			panelAreaColor.add(subAreaColor, null);
//			panelAreaColor.add(buttonFillEffect, null);
//			panelMajorTickMark.add(checkBoxNegativeReverse, null);

//			panelMajorTickMark.add(checkBoxNegativeReverse, null);
//			panelMajorTickMark.add(panelAreaColor, null);
//			panelAreaColor.add(mainAreaColor, null);
//			panelAreaColor.add(buttonFillEffect, null);
//			panelAreaColor.add(subAreaColor, null);
//			panelMajorTickMark.add(radioButtonAreaAuto, null);
//			panelMajorTickMark.add(labelAreaColor, null);
//			panelMajorTickMark.add(radioButtonAreaEmpty, null);
//			panelAreaColor.add(this.mainAreaColor);
		}
//        panelMinorTickMark.add(radioButtonMinorTickMarkCross, null);
//        panelMinorTickMark.add(radioButtonMinorTickMarkNone, null);
//        panelMinorTickMark.add(radioButtonMinorTickMarkOutside, null);
//        panelMinorTickMark.add(radioButtonMinorTickMarkInside, null);
//        panelMajorTickMark.add(jRadioButton4, null);
//        panelMajorTickMark.add(jRadioButton1, null);
//        panelMajorTickMark.add(jRadioButton2, null);
//        panelMajorTickMark.add(jRadioButton3, null);
//        panelMajorTickMark.add(radioButtonMajorTickMarkCross, null);
//        panelMajorTickMark.add(radioButtonMajorTickMarkNone, null);
//        panelMajorTickMark.add(radioButtonMajorTickMarkOutside, null);
//        panelMajorTickMark.add(radioButtonMajorTickMarkInside, null);
//        this.add(panelTickLabelPosition, null);
//        panelTickLabelPosition.add(radioButtonTickLabelPositionLow, null);
//        panelTickLabelPosition.add(radioButtonTickLabelPositionNone, null);
//        panelTickLabelPosition.add(radioButtonTickLabelPositionNextToAxis, null);
//        panelTickLabelPosition.add(radioButtonTickLabelPositionHigh, null);
    }

	//==========================================================================
	// Border에 관련된 이벤트 처리...
	//==========================================================================
	//Border
	void radioButtonBorderAuto_actionPerformed(ActionEvent e) {
		//자동일때.. ???

		int index = 0;
		Color color = Color.black;
		// Dash Style 설정..
		this.comboBoxBorderStyle.setSelectedIndex(index);
		this.panelPreviewArea.setDashStyle(this.getDashStyle(index));
		// LineWidth 설정..
		this.comboBoxBorderWeight.setSelectedIndex(index);
		this.panelPreviewArea.setLineWidth(this.getLineWidth(index));
		//Border Color
		this.buttonBorderColor.setSelectedColor(color);
		this.panelPreviewArea.setBorderColor(color);

		this.panelPreviewArea.setEnableBorder(true);

		this.panelPreviewArea.repaint();

		this.radioButtonBorderAuto.setSelected(true);
    }

    void radioButtonBorderNone_actionPerformed(ActionEvent e) {
		//Border없음을 선택....
		this.panelPreviewArea.setEnableBorder(false);
		this.panelPreviewArea.repaint();
    }

    void radioButtonBorderCustom_actionPerformed(ActionEvent e) {
		//현재 콤보박스에 선택되어 있는 타입으로 Border를 생성하고 적용시킨다.

		int index;
		Color color;
		// Dash Style 설정..
		index = this.comboBoxBorderStyle.getSelectedIndex();
		this.panelPreviewArea.setDashStyle(this.getDashStyle(index));
		// LineWidth 설정..
		index = this.comboBoxBorderWeight.getSelectedIndex();
		this.panelPreviewArea.setLineWidth(this.getLineWidth(index));
		//Border Color
		color = this.buttonBorderColor.getSelectedColor();
		this.panelPreviewArea.setBorderColor(color);

		this.panelPreviewArea.setEnableBorder(true);

		this.panelPreviewArea.repaint();
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

		this.panelPreviewArea.setDashStyle(style);

		this.radioButtonBorderCustom.setSelected(true);
		this.panelPreviewArea.setEnableBorder(true);

		this.panelPreviewArea.repaint();
    }

	void buttonBorderColor_actionPerformed(ActionEvent e) {
		buttonBorderColor.panelPopup.show(this.buttonBorderColor, 0,0);
    }


    void comboBoxBorderWeighpanelTickLabelPosition_actionPerformed(ActionEvent e) {
		int index = this.comboBoxBorderWeight.getSelectedIndex()*2 +1;
		this.panelPreviewArea.setLineWidth((float)index);
		this.radioButtonBorderCustom.setSelected(true);

		this.panelPreviewArea.setEnableBorder(true);

		this.panelPreviewArea.repaint();
    }

	void checkBoxBorderShadow_actionPerformed(ActionEvent e) {
		this.panelPreviewArea.setShadow(this.checkBoxBorderShadow.isSelected());
		this.panelPreviewArea.repaint();
    }
/*
	//==========================================================================
	// Area에 관련된 이벤트 처리...
	//==========================================================================
	//Area의 mainColorPanel에서 이벤트 발생....
    void mainAreaColor_actionPerformed(ActionEvent e) {
		//칼라를 얻어와서... 보기 영역의 배경색을 변경한다.
		Color color = this.mainAreaColor.getColor();
//		this.shapeStyle.setFillColor(color);
//		this.shapeStyle.setFillEffect(null);

		this.panelPreviewArea.setBackColor(color);
		this.panelPreviewArea.fillEffect = null;
		this.panelPreviewArea.repaint();
    }

    void subAreaColor_actionPerformed(ActionEvent e) {
		//역시 mainColorPanel과 마찬 가지로... 작동한다.
		Color color = this.subAreaColor.getColor();
//		this.shapeStyle.setFillColor(color);
//		this.shapeStyle.setFillEffect(null);

		this.panelPreviewArea.setBackColor(color);
		this.panelPreviewArea.fillEffect = null;
		this.panelPreviewArea.repaint();
    }



	//채우기 효과 선택했을 경우.....
	void buttonFillEffecpanelTickLabelPosition_actionPerformed(ActionEvent e) {
		FillEffect refFillEffect = null;

//		if( this.panelPreviewArea.getFillEffect() != null ) {
		    refFillEffect = this.panelPreviewArea.getFillEffect();
//		}

		FillEffect fillEffect = FillEffectEditor2.getNewFillEffect( refFillEffect );

		if( fillEffect != null ) {

//		    this.shapeStyle.setFillEffect( fillEffect );
		    this.panelPreviewArea.setBackColor(null);
		    this.panelPreviewArea.setFillEffect(fillEffect);

			this.panelPreviewArea.repaint();
		}
	}

*/


	//==========================================================================
	// 자체 Action Event 처리..
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
		this.panelPreviewArea.setBorderColor(color);

		this.radioButtonBorderCustom.setSelected(true);

		this.panelPreviewArea.repaint();

	}

	public ShapeStyle getShapeStyle(ShapeStyle shapeStyle) {
		Color color;
		FillEffect fillEffect = this.panelPreviewArea.getFillEffect();

		if (shapeStyle == null ) shapeStyle = new ShapeStyle();

		if ( this.mode == PatternTabForAxis.AREA_MODE) {
			color = this.panelPreviewArea.getBackColor();
			if (color == null) {
//				System.out.println("FillColor 가 널......");
				if (fillEffect==null) shapeStyle.setFillEffect(fillEffect);
				else shapeStyle.setFillEffect(fillEffect.create());
			}
			else {
//				System.out.println("FillColor 가 널 아님.........");
				shapeStyle.setFillColor(color);
			}

//			Color fillColor = shapeStyle.getFillColor();
//			if (fillColor == null ) System.out.println("FillColor지정후.. FillColor가 널...");
//			else System.out.println("FillColor지정후 .. fillColor가 있다...");
		}

		//테두리 정보를 shapeStyle에 적용 시킨다..
		//채우기 색상정보는 계속해서 shapeStyle에 저장되어 있기 때문에 테두리 정보만 수집해서 저장한다.
		color = this.buttonBorderColor.getSelectedColor();
		float lineWidth = this.getLineWidth(this.comboBoxBorderWeight.getSelectedIndex());

		shapeStyle.setLineColor(color);
		shapeStyle.setLineWidth((double)lineWidth);

		//Stroke정보...
		BasicStroke stroke;
		float[] dash = this.getDashStyle(this.comboBoxBorderStyle.getSelectedIndex());
			if ( dash == null )    stroke = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
			else stroke = new BasicStroke(lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dash, 0);
		shapeStyle.setLineStroke(stroke);

	    return shapeStyle;//.create();
	}

	public void changeMenuText() {
	    //ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/PatternTabBundle", JCalcResource.getLocale());
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/PatternTabForAxisBundle", jchartui.JCalcResource.getLocale());
        titledBorder1.setTitle(bundle.getString("border"));
        titledBorder2.setTitle(bundle.getString("sample"));

        titledBorder3.setTitle(bundle.getString("area"));

		radioButtonBorderAuto.setText(bundle.getString("borderAuto"));
        radioButtonBorderNone.setText(bundle.getString("borderNone"));
        radioButtonBorderCustom.setText(bundle.getString("borderCustom"));
        checkBoxBorderShadow.setText(bundle.getString("shadow"));
//        radioButtonAreaEmpty.setText(bundle.getString("areaNone"));
//        buttonFillEffect.setText(bundle.getString("fillEffect"));
//        radioButtonAreaAuto.setText(bundle.getString("areaAuto"));
//        labelAreaColor.setText(bundle.getString("areaColor"));
//        checkBoxNegativeReverse.setText(bundle.getString("invert"));
        labelBorderStyle.setText(bundle.getString("style"));
        labelBorderColor.setText(bundle.getString("borderColor"));
        labelBorderWeight.setText(bundle.getString("weight"));

		this.buttonBorderColor.autoColor.setText(bundle.getString("automatic"));
	}

	//==========================================================================
	// Preview 용으로 사용될 Panel...
	//==========================================================================
	public class PreviewPanel extends JPanel {
	    private Color backColor = Color.white;
		private Color borderColor = Color.black;
		private float lineWidth = 1;
		private float[] dashArray = null;
		private boolean enableShadow = false;
		private boolean enableborder = true;

		FillEffect fillEffect=null;

		private byte mode;

		public PreviewPanel(byte mode) {
		    this.mode = mode;
		}

		public void setBackColor(Color color) {
			this.backColor = color;
		}
		public Color getBackColor() {
			return this.backColor;
		}

		public void setBorderColor(Color color) {
		    this.borderColor = color;
		}
		public Color getBorderColor() {
			return this.borderColor;
		}

		public void setLineWidth(float width) {
			this.lineWidth = width;
		}
		public float getLineWidth() {
		    return this.lineWidth;
		}

		public void setDashStyle(float[] style) {
			this.dashArray = style;
		}
		public float[] getDashStyle() {
			return this.dashArray;
		}

		public void setShadow(boolean isEnable) {
		    this.enableShadow = isEnable;
		}

		public void setEnableBorder(boolean isEnable) {
		    this.enableborder = isEnable;
		}

		public void setFillEffect(FillEffect fillEffect) {
			this.fillEffect = fillEffect;
		}
		public FillEffect getFillEffect() {
			return this.fillEffect;
		}


		public void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
		    int x;

			BasicStroke stroke;
			//dashArray가 널인지 확인... null인면 실선.. null이 아니면 점선...
			if ( dashArray == null )    stroke = new BasicStroke(this.lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
			else stroke = new BasicStroke(this.lineWidth, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, this.dashArray, 0);

			if (this.mode == PatternTabForAxis.AREA_MODE ) drawAreaMode(g2d, stroke);
			else                                    drawLineMode(g2d, stroke);



		}


		private void drawAreaMode(Graphics2D g2d, Stroke stroke) {
			int x = (int)(this.lineWidth/2);
			//그림자가 있는지 없는지 확인해서 그림자를 그린다..
			if (enableShadow) {
				//배경색으로 먼저 색칠을 한다... 왜냐면.. 그림자 때문에 오른쪽 위 모퉁이 문제..
				g2d.setColor(this.getBackground());
				g2d.fillRect(0,0, this.getWidth(), this.getHeight());
			    //그림자...
				g2d.setColor(Color.black);
				g2d.fillRect(4,4, this.getWidth()-4, this.getHeight()-4);

				if (this.fillEffect == null) {
					//실제 그리기...
//					System.out.println("Paint..... 채우기 효과 없음...");
					g2d.setColor(this.backColor);
					g2d.fillRect(0,0, this.getWidth()-4, this.getHeight()-4);
				}else {
//					System.out.println("Paint..... 채우기 효과 적용.....");

					this.drawFillEffect(g2d, this.getWidth()-4, this.getHeight()-4, this.fillEffect);

				    //this.fillEffect = fillEffect;
				}

				if (this.enableborder) {
					//Border그리기
					g2d.setColor(this.borderColor);
					g2d.setStroke(stroke);
					g2d.drawRect(x,x, (int)(this.getWidth()-4-this.lineWidth), (int)(this.getHeight()-4-this.lineWidth));
				}
			}
			else {
				//실제 그리기...
				if (this.fillEffect == null) {
					//실제 그리기...
//					System.out.println("Paint..... 채우기 효과 없음...");
					g2d.setColor(this.backColor);
					g2d.fillRect(0,0, this.getWidth(), this.getHeight());
				}else {
//					System.out.println("Paint..... 채우기 효과 적용.....");

					this.drawFillEffect(g2d, this.getWidth(), this.getHeight(), this.fillEffect);

				    //this.fillEffect = fillEffect;
				}
				if (this.enableborder) {
					//Border그리기
					g2d.setColor(this.borderColor);
					g2d.setStroke(stroke);
					g2d.drawRect(x,x, (int)(this.getWidth()-this.lineWidth), (int)(this.getHeight()-this.lineWidth));
				}
			}
		}

		private void drawLineMode(Graphics2D g2d, Stroke stroke) {
		    int middle = (int)(this.getHeight()/2);

			//배경색으로 먼저 색칠을 한다...
			g2d.setColor(this.getBackground());
			g2d.fillRect(0,0, this.getWidth(), this.getHeight());

			g2d.setColor(this.borderColor);
			g2d.setStroke(stroke);
			g2d.drawLine(5, middle, this.getWidth()-5, middle);

		}

		private void drawFillEffect(Graphics2D g2d, double w, double h, FillEffect fillEffect) {
//		    double  w = this.getWidth(),
//			h = this.getHeight();

			fillEffect.setBounds(w, h);

			Paint gp = fillEffect.getGradientPaint();
			TexturePaint tp = fillEffect.getTexturePaint();

			Rectangle2D rect = new Rectangle2D.Double( 1, 1, w -2, h -2 );
			if( gp != null ) {
//				System.out.println("Gradient 로 그리기.....");
				g2d.setPaint( gp );
				g2d.fill( rect );
			} else if( tp != null ) {
//				System.out.println("Texture 로 그리기.....");
				g2d.setPaint( tp );
				g2d.fill( rect );
			} else {

			}
		}
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


    void radioButtonMinorTickMarkOutside_actionPerformed(ActionEvent e) {

    }
    void radioButtonMinorTickMarkCross_actionPerformed(ActionEvent e) {

    }
    void radioButtonMajorTickMarkOutside_actionPerformed(ActionEvent e) {

    }
    void radioButtonTickLabelPositionNextToAxis_actionPerformed(ActionEvent e) {

    }

    void radioButtonTickLabelPositionHigh_actionPerformed(ActionEvent e) {

    }
    void radioButtonMajorTickMarkCross_actionPerformed(ActionEvent e) {

    }


}
