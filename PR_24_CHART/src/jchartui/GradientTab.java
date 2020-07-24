//package com.techdigm.chart.editor;
package jchartui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

//import com.techdigm.chart.FillEffect;
import jchart.FillEffect;

import java.awt.geom.Rectangle2D;
import java.util.ResourceBundle;

/**
 * Title:
 * Description:  FillEffect에서 사용될 Graient Tab
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author      Byung-Chul   2001-05-15
 * @version 1.0
 */


public class GradientTab extends JPanel implements ActionListener{

	//Preset Color들...
	private int [][][] predefinedColorIndexes = {
      { {23,7,141}, {250, 0, 206} },
      { {35,11,91}, {238, 140, 0} },
      { {39,7,141}, {181, 40, 254} },
      { {128,203,246}, {247, 228, 248} },
      { {17, 255, 185}, {77, 159, 251} },
      { {240, 255, 5}, {250, 47, 0} },
      { {223, 246, 218}, {54, 141, 7} },
      { {245, 233, 209}, {234, 245, 209} },
      { {244, 202, 152}, {189, 134, 3} },
      { {176, 178, 4}, {243, 244, 164} },
      { {141, 145, 149}, {255, 255, 255} },
      { {2, 2, 202}, {5, 64, 255} }
	};

	/*String[]  presetString = {
		"이른 해질녁", "늦은 해질녁", "밤의 어둠", "새벽",
		"해양", "불" , "이끼", "양피지", "마호가니",
		"금색", "은색", "사파이어"
	};*/


    JPanel panelColors = new JPanel();
    JPanel panelShadingStyles = new JPanel();
    JPanel panelVariants = new JPanel();
    TitledBorder border1;
    Border border2;
    TitledBorder titledBorder1;
    Border border3;
    TitledBorder titledBorder2;
    JPanel panelGradient1 = new JPanel();
    Border border4;
    JPanel panelGradient2 = new JPanel();
    JPanel panelGradient3 = new JPanel();
    JPanel panelGradient4 = new JPanel();
    Border border5;
    JRadioButton radioButtonVertical = new JRadioButton();
    JRadioButton radioButtonHorizontal = new JRadioButton();
    JRadioButton radioButtonDiagonalUp = new JRadioButton();
    JRadioButton radioButtonDiagonalDown = new JRadioButton();
    JRadioButton radioButtonFromCorner = new JRadioButton();
    JRadioButton radioButtonFromCenter = new JRadioButton();
    JRadioButton radioButtonOneColor = new JRadioButton();
    JRadioButton radioButtonTwoColor = new JRadioButton();
    JRadioButton radioButtonPreset = new JRadioButton();
    JScrollBar scrollBarBright = new JScrollBar();
    JLabel labelDark = new JLabel();
    JLabel labelLight = new JLabel();

	ButtonGroup colorGroup = new ButtonGroup();
	ButtonGroup directionGroup = new ButtonGroup();

	//Gradient에 사용되어질 Color값.
	Color firstGradientColor = Color.white;
	Color secondGradientColor = Color.black;
	//그라디언트 Type
	int gradientType;

	//4개의 gradientPanel에 사용될 fillEffect
	private FillEffect [] fourGradient = new FillEffect[4];

	//4개의 gardient중에 어떤것이 선택되었는지 ....
	int selectedGradientIndex = -1;

	//최종 선택된 FillEffect.....
	private FillEffect fillEffect = null;

    ColorButton buttonOneColor;// = new ColorButton();
    ColorButton buttonTwoColor;// = new ColorButton();
    JComboBox comboBoxPreset;// = new JComboBox(presetString);

	private boolean nowChangeMenu = false;

    public GradientTab() {
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

		this.initItems();
		//제일처음 Horizontal선택...
//		this.radioButtonHorizontal.setSelected(true);
		this.gradientType = FillEffect.HORIZONTAL;
    }
    private void jbInit() throws Exception {
		//ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/GradientTabBundle", jcalc.JCalcResource.getLocale());
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/GradientTabBundle", jchartui.JCalcResource.getLocale());

		comboBoxPreset = new JComboBox((String[])bundle.getObject("presetString"));

        border1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142)),bundle.getString("color"));//"Colors");
        border2 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
        titledBorder1 = new TitledBorder(border2, bundle.getString("shadingStyle"));//"Shading Styles");
        border3 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));
        titledBorder2 = new TitledBorder(border3, bundle.getString("variants"));//"Variants");
        border4 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(142, 142, 142),new Color(99, 99, 99));
        border5 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(142, 142, 142),new Color(99, 99, 99));

		this.setLayout(null);
        panelColors.setBorder(border1);
        panelColors.setBounds(new Rectangle(8, 5, 286, 108));
        panelColors.setLayout(null);

		panelShadingStyles.setBorder(titledBorder1);
        panelShadingStyles.setBounds(new Rectangle(6, 115, 138, 150));
        panelShadingStyles.setLayout(null);

		panelVariants.setBorder(titledBorder2);
        panelVariants.setBounds(new Rectangle(147, 115, 146, 150));
        panelVariants.setLayout(null);

		panelGradient1.setBorder(border5);
        panelGradient1.setBounds(new Rectangle(11, 31, 59, 52));
        panelGradient1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                panelGradient1_mousePressed(e);
            }
        });
        panelGradient2.setBorder(border5);
        panelGradient2.setBounds(new Rectangle(73, 31, 59, 52));
        panelGradient2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                panelGradient2_mousePressed(e);
            }
        });
        panelGradient3.setBorder(border5);
        panelGradient3.setBounds(new Rectangle(11, 87, 59, 52));
        panelGradient3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                panelGradient3_mousePressed(e);
            }
        });
        panelGradient4.setBorder(border5);
        panelGradient4.setBounds(new Rectangle(73, 87, 59, 52));
        panelGradient4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                panelGradient4_mousePressed(e);
            }
        });

        radioButtonHorizontal.setText(bundle.getString("horizontal"));//"Horizontal");
        radioButtonHorizontal.setBounds(new Rectangle(12, 23, 109, 19));
        radioButtonHorizontal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonHorizontal_actionPerformed(e);
            }
        });
		radioButtonVertical.setText(bundle.getString("vertical"));//"Vertical");
        radioButtonVertical.setBounds(new Rectangle(12, 43, 110, 19));
        radioButtonVertical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonVertical_actionPerformed(e);
            }
        });
        radioButtonDiagonalUp.setText(bundle.getString("diagonalUp"));//"Diagonal up");
        radioButtonDiagonalUp.setBounds(new Rectangle(12, 63, 109, 19));
        radioButtonDiagonalUp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonDiagonalUp_actionPerformed(e);
            }
        });
        radioButtonDiagonalDown.setText(bundle.getString("diagonalDown"));//"Diagonal down");
        radioButtonDiagonalDown.setBounds(new Rectangle(12, 82, 109, 19));
        radioButtonDiagonalDown.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonDiagonalDown_actionPerformed(e);
            }
        });
        radioButtonFromCorner.setText(bundle.getString("fromCorner"));//"From Corner");
        radioButtonFromCorner.setBounds(new Rectangle(12, 102, 107, 19));
        radioButtonFromCorner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonFromCorner_actionPerformed(e);
            }
        });
        radioButtonFromCenter.setText(bundle.getString("fromCenter"));//"From Center");
        radioButtonFromCenter.setBounds(new Rectangle(12, 122, 105, 19));
        radioButtonFromCenter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonFromCenter_actionPerformed(e);
            }
        });
        radioButtonOneColor.setText(bundle.getString("oneColor"));//"One Color");
        radioButtonOneColor.setBounds(new Rectangle(11, 26, 104, 18));
        radioButtonOneColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonOneColor_actionPerformed(e);
            }
        });
        radioButtonTwoColor.setText(bundle.getString("twoColor"));//"Two Color");
        radioButtonTwoColor.setBounds(new Rectangle(11, 48, 104, 18));
        radioButtonTwoColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonTwoColor_actionPerformed(e);
            }
        });
        radioButtonPreset.setText(bundle.getString("preset"));//"Preset");
        radioButtonPreset.setBounds(new Rectangle(11, 73, 104, 18));
        radioButtonPreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonPreset_actionPerformed(e);
            }
        });
        scrollBarBright.setOrientation(JScrollBar.HORIZONTAL);
        scrollBarBright.setMaximum(255);
        scrollBarBright.setBounds(new Rectangle(141, 63, 126, 19));
        scrollBarBright.addAdjustmentListener(new java.awt.event.AdjustmentListener() {
            public void adjustmentValueChanged(AdjustmentEvent e) {
                scrollBarBright_adjustmentValueChanged(e);
            }
        });
        labelDark.setText(bundle.getString("dark"));//"Dark");
        labelDark.setBounds(new Rectangle(145, 83, 39, 16));
        labelLight.setText(bundle.getString("light"));//"Light");
        labelLight.setBounds(new Rectangle(234, 83, 39, 16));

		buttonOneColor = new ColorButton(this);
		buttonOneColor.setSelectedColor(this.firstGradientColor);
        buttonOneColor.setBounds(new Rectangle(166, 26, 101, 18));
		buttonOneColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonOneColor_actionPerformed(e);
            }
        });

		buttonTwoColor = new ColorButton(this);
		buttonTwoColor.setSelectedColor(this.secondGradientColor);
        buttonTwoColor.setBounds(new Rectangle(166, 48, 101, 18));
		buttonTwoColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonTwoColor_actionPerformed(e);
            }
        });

        comboBoxPreset.setBounds(new Rectangle(150, 48, 117, 21));
        comboBoxPreset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                comboBoxPreset_actionPerformed(e);
            }
        });

		//RadioButton Group설정..
		this.colorGroup.add(radioButtonOneColor);
		this.colorGroup.add(radioButtonTwoColor);
		this.colorGroup.add(radioButtonPreset);

		this.directionGroup.add(this.radioButtonHorizontal);
		this.directionGroup.add(this.radioButtonVertical);
		this.directionGroup.add(this.radioButtonDiagonalUp);
		this.directionGroup.add(this.radioButtonDiagonalDown);
		this.directionGroup.add(this.radioButtonFromCorner);
		this.directionGroup.add(this.radioButtonFromCenter);

		this.add(panelColors, null);
        panelColors.add(radioButtonTwoColor, null);
        panelColors.add(radioButtonOneColor, null);
        panelColors.add(labelDark, null);
        panelColors.add(labelLight, null);
        panelColors.add(buttonOneColor, null);
        panelColors.add(radioButtonPreset, null);
        panelColors.add(comboBoxPreset, null);
        panelColors.add(buttonTwoColor, null);
        panelColors.add(scrollBarBright, null);
        this.add(panelShadingStyles, null);
        panelShadingStyles.add(radioButtonHorizontal, null);
        panelShadingStyles.add(radioButtonFromCenter, null);
        panelShadingStyles.add(radioButtonVertical, null);
        panelShadingStyles.add(radioButtonDiagonalUp, null);
        panelShadingStyles.add(radioButtonDiagonalDown, null);
        panelShadingStyles.add(radioButtonFromCorner, null);
        this.add(panelVariants, null);
        panelVariants.add(panelGradient1, null);
        panelVariants.add(panelGradient2, null);
        panelVariants.add(panelGradient4, null);
        panelVariants.add(panelGradient3, null);
    }

    void radioButtonOneColor_actionPerformed(ActionEvent e) {
		if (this.selectedGradientIndex == -1 ) {
			this.radioButtonHorizontal.setSelected(true);
			this.selectedGradientIndex = 0;
		}

		this.initItems();

		this.drawGradient();
    }

	void buttonOneColor_actionPerformed(ActionEvent e) {
		this.buttonOneColor.panelPopup.show(this.buttonOneColor, 0, 0);
	}


    void radioButtonTwoColor_actionPerformed(ActionEvent e) {
		if (this.selectedGradientIndex == -1 ) {
			this.radioButtonHorizontal.setSelected(true);
			this.selectedGradientIndex = 0;
		}

		this.initItems();

		this.drawGradient();
    }

	void buttonTwoColor_actionPerformed(ActionEvent e) {
		this.buttonTwoColor.panelPopup.show(this.buttonTwoColor, 0, 0);
	}

    void radioButtonPreset_actionPerformed(ActionEvent e) {
		if (this.selectedGradientIndex == -1 ) {
			this.radioButtonHorizontal.setSelected(true);
			this.selectedGradientIndex = 0;
		}
		this.initItems();

		this.drawGradient();
    }

	//Gradient Type 설정을 변경.....
	//Type을 변경하고 GradientPanel 4개를 다시 그린다...
    void radioButtonHorizontal_actionPerformed(ActionEvent e) {
		this.gradientType = FillEffect.HORIZONTAL;
		this.drawGradient();

		if (this.selectedGradientIndex == -1 ) {
			this.radioButtonOneColor.setSelected(true);
			this.selectedGradientIndex = 0;
		}
//		this.repaint();
    }

    void radioButtonVertical_actionPerformed(ActionEvent e) {
		this.gradientType = FillEffect.VERTICAL;
		this.drawGradient();

		if (this.selectedGradientIndex == -1 ) {
			this.radioButtonOneColor.setSelected(true);
			this.selectedGradientIndex = 0;
		}
//		this.repaint();
    }

    void radioButtonDiagonalUp_actionPerformed(ActionEvent e) {
		this.gradientType = FillEffect.LEFT_DIAGONAL;
		this.drawGradient();

		if (this.selectedGradientIndex == -1 ) {
			this.radioButtonOneColor.setSelected(true);
			this.selectedGradientIndex = 0;
		}
//		this.repaint();
    }

    void radioButtonDiagonalDown_actionPerformed(ActionEvent e) {
		this.gradientType = FillEffect.RIGHT_DIAGONAL;
		this.drawGradient();

		if (this.selectedGradientIndex == -1 ) {
			this.radioButtonOneColor.setSelected(true);
			this.selectedGradientIndex = 0;
		}
//		this.repaint();
    }

    void radioButtonFromCorner_actionPerformed(ActionEvent e) {
		this.gradientType = FillEffect.FROM_CORNER;
		this.drawGradient();

		if (this.selectedGradientIndex == -1 ) {
			this.radioButtonOneColor.setSelected(true);
			this.selectedGradientIndex = 0;
		}
//		this.repaint();
    }

    void radioButtonFromCenter_actionPerformed(ActionEvent e) {
		this.gradientType = FillEffect.FROM_CENTER;
		this.drawGradient();

		if (this.selectedGradientIndex == -1 ) {
			this.radioButtonOneColor.setSelected(true);
			this.selectedGradientIndex = 0;
		}
//		this.repaint();
    }

	//밝기 조절 스크롤 바......
    void scrollBarBright_adjustmentValueChanged(AdjustmentEvent e) {
		this.changeSecondGradientColor( e.getValue() );
//		this.showFourGradientInstance();
    }

	 void comboBoxPreset_actionPerformed(ActionEvent e) {
		if (this.nowChangeMenu) return;

		int index = this.comboBoxPreset.getSelectedIndex();
//		Utility.debug(this, "predefined index = " + index );
		int [][] ci = this.predefinedColorIndexes[index]; // Color index
		Color   firstColor = new Color( ci[0][0], ci[0][1], ci[0][2] ),
			    secondColor = new Color( ci[1][0], ci[1][1], ci[1][2] );

		this.buttonOneColor.setSelectedColor(firstColor);
		this.buttonTwoColor.setSelectedColor(secondColor);

		this.firstGradientColor = firstColor;
		this.secondGradientColor = secondColor;

		//this.repaint();
		this.drawGradient();
    }

	//4개의 샘플중에 선택......
    void panelGradient1_mousePressed(MouseEvent e) {
//		System.out.println("여기는 1번 판넬...");
		if (this.selectedGradientIndex == -1) {
		    this.radioButtonOneColor.setSelected(true);
			this.radioButtonHorizontal.setSelected(true);
			this.initItems();
		}

		this.selectedGradientIndex = 0;
		this.setFillEffect(this.fourGradient[0]);
		FillEffectEditor.showCurrentFillEffect(this.getFillEffect());
    }

    void panelGradient2_mousePressed(MouseEvent e) {
//		System.out.println("여기는 2번 판넬...");
		if (this.selectedGradientIndex == -1) {
		    this.radioButtonOneColor.setSelected(true);
			this.radioButtonHorizontal.setSelected(true);
			this.initItems();
		}

		this.selectedGradientIndex = 1;
		this.setFillEffect(this.fourGradient[1]);
		FillEffectEditor.showCurrentFillEffect(this.getFillEffect());
    }

    void panelGradient3_mousePressed(MouseEvent e) {
//		System.out.println("여기는 3번 판넬...");
		if (this.selectedGradientIndex == -1) {
		    this.radioButtonOneColor.setSelected(true);
			this.radioButtonHorizontal.setSelected(true);
			this.initItems();
		}

		this.selectedGradientIndex = 2;
		this.setFillEffect(this.fourGradient[2]);
		FillEffectEditor.showCurrentFillEffect(this.getFillEffect());
    }

    void panelGradient4_mousePressed(MouseEvent e) {
//		System.out.println("여기는 4번 판넬...");
		if (this.selectedGradientIndex == -1) {
		    this.radioButtonOneColor.setSelected(true);
			this.radioButtonHorizontal.setSelected(true);
			this.initItems();
		}

		this.selectedGradientIndex = 3;
		this.setFillEffect(this.fourGradient[3]);
		FillEffectEditor.showCurrentFillEffect(this.getFillEffect());
    }


	public void actionPerformed(ActionEvent e) {
	    Color color;
		if (e.getSource() == this.buttonOneColor.mainColorPanel) {
			color = this.buttonOneColor.mainColorPanel.getColor();
			this.firstGradientColor = color;
			this.buttonOneColor.setSelectedColor(color);
	    }
		else if (e.getSource() == this.buttonOneColor.subColorPanel) {
			color = this.buttonOneColor.subColorPanel.getColor();
			this.firstGradientColor = color;
			this.buttonOneColor.setSelectedColor(color);
	    }
		else if (e.getSource() == this.buttonTwoColor.mainColorPanel) {
			color = this.buttonTwoColor.mainColorPanel.getColor();
			this.secondGradientColor = color;
			this.buttonTwoColor.setSelectedColor(color);
		}
		else if (e.getSource() == this.buttonTwoColor.subColorPanel) {
			color = this.buttonTwoColor.subColorPanel.getColor();
			this.secondGradientColor = color;
			this.buttonTwoColor.setSelectedColor(color);
		}

		//this.repaint();


	}



	//그라디언트 첫번째 색 지정..
	public void setFirstColor(Color color) {
		this.firstGradientColor = color;
	}
	//그라디언트 두번째 색 지정..
	public void setSecondColor(Color color) {
		this.secondGradientColor = color;
	}
	//그라디언트 첫번째 색 반환..
	public Color getFirstGradientColor() {
		return this.firstGradientColor;
	}
	//그라디언트 두번째 색 반환..
	public Color getSecondGradientColor() {
	    return this.secondGradientColor;
	}

	//스크롤바에 의한 밝기 조절...
	public void changeSecondGradientColor(int r) {
//		Utility.debug(this, "brightness = " + r );
		if( r < 0 ) { r = 0; }
		if( r > 255 ) { r = 255; }
		Color color = new Color(r, r, r);

		this.secondGradientColor = color;

		this.drawGradient();
//		this.secondGradientColorChooserComboBoxRenderer.changeColor( color );
//		this.secondGradientColorChooserComboBox.repaint();
	}

	public void update(Graphics g) {
		this.paintComponent(g);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawGradient();

		System.out.println("그라디언트 다시 그리기...");
	}

	public void drawGradient() {
		//그라디언트 종류.....
		int gradientType = this.gradientType;

		//첫번째 색, 두번째 색을 확인한다.
		Color firstGradientColor = this.getFirstGradientColor(),
			secondGradientColor = this.getSecondGradientColor();
		if( firstGradientColor == null ) { firstGradientColor = Color.white ; }
		if( secondGradientColor == null ) { secondGradientColor = Color.black ; }

		FillEffect fe = new FillEffect();
		fe.setFirstGradientColor( firstGradientColor );
		fe.setSecondGradientColor( secondGradientColor );
		fe.setCyclic( false );
		fe.setSymmetric( false );
		fe.setType( gradientType );
		this.fourGradient[0] = fe.create();
		fe.setSymmetric( true );
		fe.setCyclic( true );
		this.fourGradient[2] = fe.create();
		fe.setFirstGradientColor( secondGradientColor );
		fe.setSecondGradientColor( firstGradientColor );
		this.fourGradient[3] = fe.create();
		fe.setSymmetric( false );
		fe.setCyclic( false );
		this.fourGradient[1] = fe.create();

		if( gradientType == FillEffect.FROM_CORNER ) {
			this.fourGradient[0].setDirection( FillEffect.DOWN );
			this.fourGradient[1].setDirection( FillEffect.UP );
			this.fourGradient[2].setDirection( FillEffect.UP );
			this.fourGradient[3].setDirection( FillEffect.DOWN );
		} else if( gradientType == FillEffect.FROM_CENTER ) {
			this.fourGradient[2].setType( FillEffect.ROUND );
			this.fourGradient[3].setType( FillEffect.ROUND );
		}

		//각 판넬에 해당 그라디언트를 그린다..
		this.showGradientOnPanel( this.panelGradient1, this.fourGradient[0] );
		this.showGradientOnPanel( this.panelGradient2, this.fourGradient[1] );
		this.showGradientOnPanel( this.panelGradient3, this.fourGradient[2] );
		this.showGradientOnPanel( this.panelGradient4, this.fourGradient[3] );

		//
		if (this.selectedGradientIndex != -1 ) {
			this.setFillEffect(this.fourGradient[this.selectedGradientIndex]);
			FillEffectEditor.showCurrentFillEffect(this.getFillEffect());
		}

		/*
		if( this.selectedGradientIndex < 0 ) {
		    this.selectedGradientIndex = 0;
		}
		if( this.selectedGradientIndex >= 0 && this.selectedGradientIndex < this.fourGradient.length ) {
		    this.fillEffect = this.fourGradient[ this.selectedGradientIndex ];
		}
		this.showSelectedFillEffect();
		*/
	}

	public static void showGradientOnPanel(JPanel panel, FillEffect fe) {
		Rectangle2D rect = new Rectangle2D.Double(2, 2, panel.getBounds().getWidth()-4, panel.getBounds().getHeight()-4 );
		Graphics2D g2 = (Graphics2D) panel.getGraphics();
		Paint gp;
		TexturePaint tp;

		if (fe != null) {
			fe.setBounds( rect );
			gp = fe.getGradientPaint();
		    tp = fe.getTexturePaint();
			if( gp != null ) {
				g2.setPaint( gp );
				g2.fill( rect );
			 } else if( tp != null ) {
				g2.setPaint( tp );
				g2.fill( rect );
			 }


		}
		else {
			panel.setBackground(panel.getBackground());
			panel.repaint();
		}
	}


	public void initItems() {
//		this.buttonOneColor.setSelectedColor(this.firstGradientColor);
//		this.buttonTwoColor.setSelectedColor(this.secondGradientColor);

		//칼라 선택 영역의 radioButton선택여부에따라 각 컨트롤 표시..
		if (this.radioButtonOneColor.isSelected() ) {
			//oneColor가 선택 되어 있으면..
			this.buttonOneColor.setVisible(true);
			this.buttonTwoColor.setVisible(false);
			this.scrollBarBright.setVisible(true);
			this.labelDark.setVisible(true);
			this.labelLight.setVisible(true);
			this.comboBoxPreset.setVisible(false);
		}
		else if(this.radioButtonTwoColor.isSelected() ) {
			//twoColor 선택 되어 있으면..
			this.buttonOneColor.setVisible(true);
			this.buttonTwoColor.setVisible(true);
			this.scrollBarBright.setVisible(false);
			this.labelDark.setVisible(false);
			this.labelLight.setVisible(false);
			this.comboBoxPreset.setVisible(false);
		}
		else if(this.radioButtonPreset.isSelected() ) {
			//미리정의된..이 선택되어 있으면
			this.buttonOneColor.setVisible(false);
			this.buttonTwoColor.setVisible(false);
			this.scrollBarBright.setVisible(false);
			this.labelDark.setVisible(false);
			this.labelLight.setVisible(false);
			this.comboBoxPreset.setVisible(true);
		}
		else {
			//아무것도 선택되어 있지 않으면...

			this.buttonOneColor.setVisible(false);
			this.buttonTwoColor.setVisible(false);
			this.scrollBarBright.setVisible(false);
			this.labelDark.setVisible(false);
			this.labelLight.setVisible(false);
			this.comboBoxPreset.setVisible(false);
		}


	}


	//FillEffect를 설정하고 반환...
	public void setFillEffect (FillEffect fillEffect) {
		this.fillEffect = fillEffect;
	}

	public FillEffect getFillEffect() {

		if (this.fillEffect == null) return null;

		return this.fillEffect.create();
	}


	public void changeMenuText() {
		this.nowChangeMenu = true;

//	    ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/GradientTabBundle", jcalc.JCalcResource.getLocale());

		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/GradientTabBundle", jchartui.JCalcResource.getLocale());
		border1.setTitle(bundle.getString("color"));
        titledBorder1.setTitle(bundle.getString("shadingStyle"));
        titledBorder2.setTitle(bundle.getString("variants"));

		JComboBox temp = new JComboBox((String[])bundle.getObject("presetString"));
		this.comboBoxPreset.setModel(temp.getModel());

		radioButtonHorizontal.setText(bundle.getString("horizontal"));
    	radioButtonVertical.setText(bundle.getString("vertical"));
        radioButtonDiagonalUp.setText(bundle.getString("diagonalUp"));
        radioButtonDiagonalDown.setText(bundle.getString("diagonalDown"));
        radioButtonFromCorner.setText(bundle.getString("fromCorner"));
        radioButtonFromCenter.setText(bundle.getString("fromCenter"));
        radioButtonOneColor.setText(bundle.getString("oneColor"));
        radioButtonTwoColor.setText(bundle.getString("twoColor"));
        radioButtonPreset.setText(bundle.getString("preset"));
        labelDark.setText(bundle.getString("dark"));
        labelLight.setText(bundle.getString("light"));

		this.nowChangeMenu = false;
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