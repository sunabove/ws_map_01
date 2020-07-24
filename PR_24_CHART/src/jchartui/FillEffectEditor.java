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


	//FillEffect����.....
	private static FillEffect fillEffect = null;

	public boolean isOK = false;

	public Locale currentLocale;

    public FillEffectEditor() {
//		super(jcalc.JCalc.frame, true);

		//ó�� ���۽� locale������ �����Ѵ�....
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

		// ��� Dialog�� ũ��� 440 x 370���� �Ѵ�.
		this.setSize(440, 370);


        panelSample.setBorder(BorderFactory.createLineBorder(Color.black));
        panelSample.setPreferredSize(new Dimension(56, 40));

	   // component���� ũ�⸦ �����Ѵ�.
		mainTabPane.setBounds(new Rectangle(7, 6, 311, 322));
       	panelSample.setBounds(new Rectangle(328, 260, 70, 68));
		labelSample.setBounds(new Rectangle(328, 236, 48, 16));
		buttonOk.setBounds(new Rectangle(328, 32, 90, 20));
        buttonCancel.setBounds(new Rectangle(328, 58, 90, 20));

		// 	Tab�� �̸� Text�� ���Ѵ�.
		this.getContentPane().add(mainTabPane, null);
		this.getContentPane().add(panelSample, null);
        this.getContentPane().add(buttonOk, null);
        this.getContentPane().add(buttonCancel, null);
        this.getContentPane().add(labelSample, null);

		// ���̾�α� �ڽ� �̸� Text ���ϱ�
		this.setTitle(bundle.getString("fillEffect"));

		// ���� �̸� Text ���Ͽ� mainTab Panel�� �ִ´�.
		mainTabPane.add(tabGradient, bundle.getString("gradientTab") );
        mainTabPane.add(tabTexture,  bundle.getString("textureTab") );
        mainTabPane.add(tabPattern, bundle.getString("patternTab") );
        mainTabPane.add(tabPicture, bundle.getString("pictureTab") );

		// OK Button, Cancel Button, Sample Label �̸� Text ���ϱ�
		this.buttonCancel.setText(bundle.getString("cancel"));
		this.buttonOk.setText(bundle.getString("ok"));
		this.labelSample.setText(bundle.getString("sample"));

		// ������ �� ������ Text ���ϱ�
		this.tabGradient.changeMenuText();
		this.tabPattern.changeMenuText();
		this.tabPicture.changeMenuText();
		this.tabTexture.changeMenuText();

		//������ ����ϱ�
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
	 * OK ��ư�� ������ ��
	 */
	void buttonOk_actionPerformed(ActionEvent e) {
		this.isOK = true;
		this.hide();

    }

	/**
	 * Cancel ��ư�� ������ ��
	 */
	void buttonCancel_actionPerformed(ActionEvent e) {
		this.isOK = false;
		this.fillEffect = null;
		this.hide();
    }

	/**
	 * �� �� ��ȯ�� �� �ǿ� �ִ� FillEffect�� ���ͼ� Sample Panel�� �׸���..
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
	 * ���� ���õ� FillEffect�� samplePanel�� �׸���...
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

		// OK Button�� ���õǾ� ���� Pattern Tab�� fillEffect�� �ѱ��.
		// Cancle Button�� ���õǾ� ���� �ƹ��ϵ� ���ϰ� ������.
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

		// ���̾�α� �ڽ� �̸� Text �ٲٱ�
		this.setTitle(bundle.getString("fillEffect"));

		// ���� �̸� Text �ٲٱ�
		mainTabPane.add(tabGradient,  bundle.getString( "gradientTab" ) );
        mainTabPane.add(tabTexture,  bundle.getString( "textureTab" ) );
        mainTabPane.add(tabPattern,  bundle.getString( "patternTab" ) );
        mainTabPane.add(tabPicture,  bundle.getString( "pictureTab" ) );

		// OK Button, Cancel Button, Sample Label �̸� Text �ٲٱ�
		this.buttonCancel.setText(bundle.getString("cancel"));
		this.buttonOk.setText(bundle.getString("ok"));
		this.labelSample.setText(bundle.getString("sample"));

		// ������ �� ������ Text �ٲٱ�
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

		System.out.println("���� FillEffect Panel �ٽ� �׸���...");
	}
}










