//package com.techdigm.chart.editor;
package jchartui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

//import com.techdigm.chart.Utility;
//import com.techdigm.chart.FillEffect;
import jcosmos.Utility;
import jchart.FillEffect;

import java.io.File;
import javax.swing.filechooser.FileFilter;

import java.util.ResourceBundle;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class PictureTab extends JPanel {
    JPanel panelPicture = new JPanel();
    JTextField textFieldPictureInfo = new JTextField();
    JButton buttonOpenPicture = new JButton();
    Border border1;
    JLabel labelPicture = new JLabel();

	private Image selectedPictureImage = null;
	private FillEffect fillEffect = null;

    public PictureTab() {
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void jbInit() throws Exception {
		//ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/PictureTabBundle", jcalc.JCalcResource.getLocale());
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/PictureTabBundle", jchartui.JCalcResource.getLocale());

        border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(142, 142, 142),new Color(99, 99, 99));
        this.setLayout(null);
        panelPicture.setBorder(border1);
        panelPicture.setBounds(new Rectangle(19, 30, 265, 133));
        textFieldPictureInfo.setBackground(UIManager.getColor("Button.background"));
        textFieldPictureInfo.setBorder(border1);
        textFieldPictureInfo.setEditable(false);
        textFieldPictureInfo.setBounds(new Rectangle(19, 172, 265, 22));
        buttonOpenPicture.setMargin(new Insets(2, 2, 2, 2));
        buttonOpenPicture.setText(bundle.getString("otherPicture"));
        buttonOpenPicture.setBounds(new Rectangle(168, 203, 116, 21));
        buttonOpenPicture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonOpenPicture_actionPerformed(e);
            }
        });
        labelPicture.setText(bundle.getString("picture"));
        labelPicture.setBounds(new Rectangle(21, 8, 71, 19));
        this.add(labelPicture, null);
        this.add(panelPicture, null);
        this.add(textFieldPictureInfo, null);
        this.add(buttonOpenPicture, null);
    }

	public void paintComponent(Graphics g) {
	    super.paintComponent(g);

		this.showSelectedPictureImage();
	}

    void buttonOpenPicture_actionPerformed(ActionEvent e) {
		//ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/PictureTabBundle", jcalc.JCalcResource.getLocale());
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/PictureTabBundle", jchartui.JCalcResource.getLocale());
		File file = getChoosedImageFile(bundle.getString("selectPicture"));

		if( file != null ) {
			// �̹��� ȭ���� �����Ѵ�.
			String fileName = file.getName();
			int index = fileName.lastIndexOf(".");
			fileName = fileName.substring(0, index );
			this.textFieldPictureInfo.setText( fileName );
//			Utility.debug(this, file.getAbsolutePath() );
			ImageIcon icon = new ImageIcon( file.getAbsolutePath() );
			// ��. �̹��� ȭ�� ����.

			// �̹��� ȭ���� ȭ�鿡 �����ش�.
			Image image = icon.getImage();
			this.selectedPictureImage = image;
			this.showSelectedPictureImage();
			// ��. �̹��� �� �׸���.
			// ���õ� �� ���� �̹����� �̹����� ���� ȭ�鿡 �����ش�.

			FillEffectEditor.showCurrentFillEffect(this.getFillEffect());

//			this.repaint();
//			this.showSelectedPictureImage();
//			this.showSelectedFillEffect();
			// ��. ���� ȭ�鿡 �����ֱ�.
		}
    }

	File getChoosedImageFile(String title) {
		JFileChooser fileChooser = new JFileChooser();
		FileFilter fileFilter = new FileFilter() {
		    public String getDescription() {  return "Image Files"; }

			public boolean accept(File f) {
				if( f == null ) { return false; }
				else if( f.isDirectory() ) { return true; }
				else { return f.getName().toLowerCase().endsWith("gif")
					|| f.getName().toLowerCase().endsWith("jpg")
					|| f.getName().toLowerCase().endsWith("jpeg");
				}
			}
		};
		fileChooser.setFileFilter( fileFilter );
		fileChooser.setCurrentDirectory( new File(".") );
		fileChooser.showDialog(this, title );
		File selectedFile = fileChooser.getSelectedFile();
		return selectedFile;
	}

	void showSelectedPictureImage() {
		Utility.debug(this, "Showing selected picture image.......");

		Image image = this.selectedPictureImage;

		if( image == null ) {
		    return;
		} else {
			// this.firstGradientColor = null;
			// this.secondGradientColor = null;
//			this.fillEffect.setFirstGradientColor( null );
//			this.fillEffect.setSecondGradientColor( null );
		}

		Frame f = new Frame();
		f.addNotify();
		// �׸� �̹����� ��ġ�� ũ�⸦ �����Ѵ�.
		double iw = image.getWidth(f), ih = image.getHeight(f);
		Insets insets = this.panelPicture.getInsets();
		double cw = this.panelPicture.getWidth() - insets.left - insets.right - 10,
		ch = this.panelPicture.getHeight() - insets.top - insets.bottom  - 10;
		Graphics g = this.panelPicture.getGraphics();
		double gx = 0, gy = 0, gw = 0, gh = 0;

		if( iw > ih ) {
			// �׸��� ���� ���� ���� ũ��,
			// �׷����� ���̸� ���� �ʿ� ���� �����.
			gh = ch;
			gw = iw*( gh/ih);
		} else {
			// �׸��� ũ�Ⱑ ���̺��� ũ��,
			// �׷����� ���� ���� �ʿ� ���� �����.
			gw = ch;
			gh = ih*( gw / iw );
		}

		gx = (cw - gw) / 2 + insets.left + 5;
		gy = 0 + insets.top + 5;
		g.drawImage( image, (int)gx, (int)gy, (int)gw, (int)gh, f);
		// ��. �̹��� ũ��� ��ġ ����
		// ��. �̹��� �� ��Ÿ����.
		Utility.debug(this, "Done showing selected picture image." );
  }


  //FillEffect�� �����ϰ� ��ȯ...
	public void setFillEffect (FillEffect fillEffect) {
		this.fillEffect = fillEffect;
	}

	public FillEffect getFillEffect() {
		//FillEffect�� ������ �̹����� ������ null�� ��ȯ...
		if (this.selectedPictureImage == null) return null;

		if (this.fillEffect == null) fillEffect = new FillEffect();

		this.fillEffect.setImage(this.selectedPictureImage);

		return this.fillEffect.create();
	}


	public void changeMenuText(){
		//ResourceBundle bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/PictureTabBundle", jcalc.JCalcResource.getLocale());
		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/PictureTabBundle", jchartui.JCalcResource.getLocale());
		this.labelPicture.setText(bundle.getString("picture"));
		this.buttonOpenPicture.setText(bundle.getString("otherPicture"));
	}
}










