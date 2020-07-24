//package com.techdigm.chart.editor;
package jchartui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
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

public class InteriorPanel extends JPanel  {

	Color subColor[][] = { { new Color(0  ,0    , 0), new Color(156,49   , 0) },
							{ new Color(49 ,49   , 0), new Color(0  ,49   , 0) },
							{ new Color(0  ,49  , 99), new Color(0  ,0  , 132) },
							{ new Color(49 ,49 , 156), new Color(49 ,49  , 49) },
							{ new Color(132,  0   ,0), new Color(255, 99   ,0) },
							{ new Color(132,132   ,0), new Color(0  ,132   ,0) },
							{ new Color(0  ,132, 132), new Color(0  ,  0, 255) },
							{ new Color(99 , 99, 156), new Color(132,132, 132) }
						  };

	PreviewPanelInPatternTab panelPreview;

	// 영역 자동, 없음 라디오 버튼을 생성한다.
	JRadioButton radioButtonAreaAuto = new JRadioButton();
	JRadioButton radioButtonAreaEmpty = new JRadioButton();

	// ColorPanel을 생성하고 그에 해당하는 Text를 담을 Label도 생성한다.
    JPanel panelAreaColor = new JPanel();
	JLabel labelAreaColor = new JLabel();
	ColorPanel mainAreaColor = new ColorPanel();
	ColorPanel subAreaColor = new ColorPanel(2, 8, subColor);

	// 채우기 효과 다이얼로그 박스를 불러올 Button을 하나 생성한다.
	JButton buttonFillEffect = new JButton();

	// 음수이면 반전 시킬 CheckBox을 생성한다.
    JCheckBox checkBoxNegativeReverse = new JCheckBox();

	public static final byte LINE_MODE = 1;
	public static final byte AREA_MODE = 2;

	private byte mode;


	public InteriorPanel() {

		this.mode = PatternTab.AREA_MODE;
		this.panelPreview = new PreviewPanelInPatternTab(mode);

        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

		this.radioButtonAreaAuto.setSelected(true);

    }

	public InteriorPanel(byte mode) {

		this.mode = mode;
		this.panelPreview = new PreviewPanelInPatternTab(mode);

        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

		this.radioButtonAreaAuto.setSelected(true);

    }


    private void jbInit() throws Exception {

		// Component들의 Layout을 정한다.
		this.setLayout(null);
		panelAreaColor.setLayout(null);

		// Component들을 mode에 맞게 addition한다.

//		if (this.mode == AREA_MODE) {
			this.add(radioButtonAreaAuto, null);
			this.add(radioButtonAreaEmpty, null);
			this.add(labelAreaColor, null);
			this.add(panelAreaColor, null);

			panelAreaColor.add(mainAreaColor, null);
			panelAreaColor.add(subAreaColor, null);
			panelAreaColor.add(buttonFillEffect, null);

			this.add(checkBoxNegativeReverse, null);

//		}

		// radioButton을 그룹화 한다.
		ButtonGroup groupArea = new ButtonGroup();
		groupArea.add(radioButtonAreaAuto);
		groupArea.add(radioButtonAreaEmpty);


		// Component들의 Bounds를 정한다.
		radioButtonAreaAuto.setBounds(new Rectangle(9, 21, 99, 19));
		radioButtonAreaEmpty.setBounds(new Rectangle(9, 40, 100, 17));
		mainAreaColor.setBounds(new Rectangle(0, 0, 163, 100));
        subAreaColor.setBounds(new Rectangle(0, 106, 161, 26));
		labelAreaColor.setBounds(new Rectangle(9, 58, 65, 18));
        panelAreaColor.setBounds(new Rectangle(5, 75, 165, 165));
        buttonFillEffect.setBounds(new Rectangle(9, 141, 143, 19));
        checkBoxNegativeReverse.setBounds(new Rectangle(9, 247, 131, 18));


		// Componemt들의 리스너를 등록한다.
        radioButtonAreaAuto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonAreaAuto_actionPerformed(e);
            }
        });
		radioButtonAreaEmpty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                radioButtonAreaEmpty_actionPerformed(e);
            }
        });
		mainAreaColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainAreaColor_actionPerformed(e);
            }
        });
		subAreaColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                subAreaColor_actionPerformed(e);
            }
        });
		buttonFillEffect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonFillEffect_actionPerformed(e);
            }
        });
		checkBoxNegativeReverse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBoxNegativeReverse_actionPerformed(e);
            }
        });

		// 단축키 지정
		radioButtonAreaAuto.setMnemonic('U');
		radioButtonAreaEmpty.setMnemonic('E');
		buttonFillEffect.setMnemonic('I');
		checkBoxNegativeReverse.setMnemonic('V');

		// Text 설정
		changeMenuText();

    }


	public void setPreviewPanelInPatternTab( PreviewPanelInPatternTab panelPreview) {

		this.panelPreview = panelPreview;

	}



	public void changeMenuText() {

	    ResourceBundle bundle = ResourceBundle.getBundle("jchartui/InteriorPanelBundle", JCalcResource.getLocale());

		radioButtonAreaAuto.setText(bundle.getString("areaAuto"));
        radioButtonAreaEmpty.setText(bundle.getString("areaNone"));
        labelAreaColor.setText(bundle.getString("areaColor"));
		buttonFillEffect.setText(bundle.getString("fillEffect"));
	    checkBoxNegativeReverse.setText(bundle.getString("invert"));

	}



	//==========================================================================
	// Area에 관련된 이벤트 처리...
	//==========================================================================
	//Area의 mainColorPanel에서 이벤트 발생....
    void mainAreaColor_actionPerformed(ActionEvent e) {
		//칼라를 얻어와서... 보기 영역의 배경색을 변경한다.
		Color color = this.mainAreaColor.getColor();
//		this.shapeStyle.setFillColor(color);
//		this.shapeStyle.setFillEffect(null);

		this.panelPreview.setBackColor(color);
		this.panelPreview.fillEffect = null;
		this.panelPreview.repaint();
    }

    void subAreaColor_actionPerformed(ActionEvent e) {
		//역시 mainColorPanel과 마찬 가지로... 작동한다.
		Color color = this.subAreaColor.getColor();
//		this.shapeStyle.setFillColor(color);
//		this.shapeStyle.setFillEffect(null);

		this.panelPreview.setBackColor(color);
		this.panelPreview.fillEffect = null;
		this.panelPreview.repaint();
    }

	void radioButtonAreaAuto_actionPerformed(ActionEvent e) {

	}

	void radioButtonAreaEmpty_actionPerformed(ActionEvent e) {

	}

	/**
	 * 채우기 효과를 선택했을 경우.....
	 * FillEffect 다이얼로그 박스를 띄운후 거기에서 결정된 FillEffect를 가져와
	 * PreviewArea Panel에 나타낸다.
	 */
	void buttonFillEffect_actionPerformed(ActionEvent e) {

		FillEffect refFillEffect = null;

		if( this.panelPreview.getFillEffect() != null ) {

		    refFillEffect = this.panelPreview.getFillEffect();

		}

		System.out.println("fillEffect = " + refFillEffect);

		FillEffect fillEffect = FillEffectEditor.getNewFillEffect( refFillEffect );

		System.out.println("fillEffect = " + fillEffect);

		if( fillEffect != null ) {

		    this.panelPreview.setBackColor(null);

		    this.panelPreview.setFillEffect(fillEffect);

			this.panelPreview.repaint();
		}
	}

	void checkBoxNegativeReverse_actionPerformed(ActionEvent e) {

	}


}
