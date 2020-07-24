//package com.techdigm.chart.editor;
package jchartui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

//import com.techdigm.chart.FillEffect;
import jchart.FillEffect;

import javax.swing.event.*;
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

public class FillEffectEditor extends JDialog {

	//Data
	public static FillEffectEditor editor;

    JTabbedPane mainTabPane = new JTabbedPane();
    GradientTab tabGradient = new GradientTab();
    TextureTab tabTexture = new TextureTab();

    JLabel labelSample = new JLabel();
    JButton buttonOk = new JButton();
    JButton buttonCancel = new JButton();
    FillEffectPatternTab tabPattern = new FillEffectPatternTab();
    PictureTab tabPicture = new PictureTab();

	static JPanel panelSample = new JPanel();


	//FillEffect정보.....
	private static FillEffect fillEffect = null;

	public boolean isOK = false;

	public Locale currentLocale;

    public FillEffectEditor() {
//		super(jcalc.JCalc.frame, true);

		//처음 시작시 locale정보를 저장한다....
//		this.currentLocale = jcalc.JCalcResource.getLocale();
		this.currentLocale = jchartui.JCalcResource.getLocale();

        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

		this.setResizable(false);
//		this.setLocationRelativeTo(jcalc.JCalc.frame);
    }

    private void jbInit() throws Exception {
//		ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/FillEffectEditorBundle", jcalc.JCalcResource.getLocale());
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/FillEffectEditorBundle", jchartui.JCalcResource.getLocale());


        this.getContentPane().setLayout(null);

		// 모든 Dialog의 크기는 440 x 370으로 한다.
		this.setSize(440, 370);


        panelSample.setBorder(BorderFactory.createLineBorder(Color.black));
        panelSample.setPreferredSize(new Dimension(56, 40));

	   // component들의 크기를 결정한다.
		mainTabPane.setBounds(new Rectangle(7, 6, 311, 322));
       	panelSample.setBounds(new Rectangle(328, 260, 70, 68));
		labelSample.setBounds(new Rectangle(328, 236, 48, 16));
		buttonOk.setBounds(new Rectangle(328, 32, 90, 20));
        buttonCancel.setBounds(new Rectangle(328, 58, 90, 20));

		// 	Tab의 이름 Text를 정한다.
		this.getContentPane().add(mainTabPane, null);
		this.getContentPane().add(panelSample, null);
        this.getContentPane().add(buttonOk, null);
        this.getContentPane().add(buttonCancel, null);
        this.getContentPane().add(labelSample, null);

		// 다이얼로그 박스 이름 Text 정하기
		this.setTitle(bundle.getString("fillEffect"));

		// 탭의 이름 Text 정하여 mainTab Panel에 넣는다.
		mainTabPane.add(tabGradient, bundle.getString("gradientTab") );
        mainTabPane.add(tabTexture,  bundle.getString("textureTab") );
        mainTabPane.add(tabPattern, bundle.getString("patternTab") );
        mainTabPane.add(tabPicture, bundle.getString("pictureTab") );

		// OK Button, Cancel Button, Sample Label 이름 Text 정하기
		this.buttonCancel.setText(bundle.getString("cancel"));
		this.buttonOk.setText(bundle.getString("ok"));
		this.labelSample.setText(bundle.getString("sample"));

		// 각각의 탭 내용의 Text 정하기
		this.tabGradient.changeMenuText();
		this.tabPattern.changeMenuText();
		this.tabPicture.changeMenuText();
		this.tabTexture.changeMenuText();

		//리스너 등록하기
		 mainTabPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                mainTabPane_stateChanged(e);
            }
        });

		buttonOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonOk_actionPerformed(e);
            }
        });

		buttonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonCancel_actionPerformed(e);
            }
        });

    }

	/**
	 * OK 버튼을 눌렀을 때
	 */
	void buttonOk_actionPerformed(ActionEvent e) {
		this.isOK = true;
		this.hide();

    }

	/**
	 * Cancel 버튼을 눌렀을 때
	 */
	void buttonCancel_actionPerformed(ActionEvent e) {
		this.isOK = false;
		this.fillEffect = null;
		this.hide();
    }

	/**
	 * 각 탭 전환시 각 탭에 있는 FillEffect를 얻어와서 Sample Panel에 그린다..
	 */
	void mainTabPane_stateChanged(ChangeEvent e) {

		int index = this.mainTabPane.getSelectedIndex();

		switch (index) {
			case 0:
	    	    this.fillEffect = this.tabGradient.getFillEffect();
		    	break;
		    case 1:
			    this.fillEffect = this.tabTexture.getFillEffect();
				break;
	    	case 2:
		    	this.fillEffect = this.tabPattern.getFillEffect();
				break;
	    	case 3:
		    	this.fillEffect = this.tabPicture.getFillEffect();
				break;
	    	default :
		    	break;
		}

		showCurrentFillEffect(this.fillEffect);

    }

	/**
	 * 현재 선택된 FillEffect를 samplePanel에 그린다...
	 */
	public static void showCurrentFillEffect(FillEffect fillEffect) {

		FillEffectEditor.fillEffect = fillEffect;

		GradientTab.showGradientOnPanel(panelSample, fillEffect);

	}

	public void setFillEffect(FillEffect fillEffect) {

		this.fillEffect = fillEffect;

	}

	public FillEffect getFillEffect() {
		return this.fillEffect;
	}


	public static FillEffect getNewFillEffect( FillEffect refFillEffect ) {
		if( editor == null ) {
		    editor = new FillEffectEditor();
		}
		if( refFillEffect != null ) {
		    editor.setFillEffect( refFillEffect.create() );
		}


		editor.show();

		// OK Button이 선택되어 지면 Pattern Tab에 fillEffect를 넘긴다.
		// Cancle Button이 선택되어 지면 아무일도 안하고 끝낸다.
		if (editor.isOK == true) {

			FillEffect fe = editor.getFillEffect();

			if( fe == null ) {

				return null;

			}

			return  fe.create();

		}else {

			return null;

		}
	}

	public void changeMenuText() {
//		ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/FillEffectEditorBundle", jcalc.JCalcResource.getLocale());
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/FillEffectEditorBundle", jchartui.JCalcResource.getLocale());

		// 다이얼로그 박스 이름 Text 바꾸기
		this.setTitle(bundle.getString("fillEffect"));

		// 탭의 이름 Text 바꾸기
		mainTabPane.add(tabGradient,  bundle.getString( "gradientTab" ) );
        mainTabPane.add(tabTexture,  bundle.getString( "textureTab" ) );
        mainTabPane.add(tabPattern,  bundle.getString( "patternTab" ) );
        mainTabPane.add(tabPicture,  bundle.getString( "pictureTab" ) );

		// OK Button, Cancel Button, Sample Label 이름 Text 바꾸기
		this.buttonCancel.setText(bundle.getString("cancel"));
		this.buttonOk.setText(bundle.getString("ok"));
		this.labelSample.setText(bundle.getString("sample"));

		// 각각의 탭 내용의 Text 바꾸기
		this.tabGradient.changeMenuText();
		this.tabPattern.changeMenuText();
		this.tabPicture.changeMenuText();
		this.tabTexture.changeMenuText();

	}

	public void show() {

		if (this.currentLocale != jchartui.JCalcResource.getLocale() ) {

			this.changeMenuText();

		}

		super.show();
	}

	public void paintComponent(Graphics g) {
		super.paintComponents(g);

		this.showCurrentFillEffect(this.getFillEffect());

		System.out.println("메인 FillEffect Panel 다시 그리기...");
	}
}










