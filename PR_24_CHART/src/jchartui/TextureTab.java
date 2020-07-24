//package com.techdigm.chart.editor;
package jchartui;


import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

//import com.techdigm.chart.Utility;
import jcosmos.Utility;

import java.io.File;
import javax.swing.filechooser.FileFilter;

//import com.techdigm.chart.FillEffect;
import jchart.FillEffect;


import java.util.ResourceBundle;

/**
 * Title:
 * Description:     FillEffectEditor에서 사용될 TextureTab
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class TextureTab extends JPanel {

    JScrollPane scrollTexture = new JScrollPane();
    JPanel panelTexture = new JPanel();
    GridLayout gridLayout1 = new GridLayout();
    JToggleButton textureTB_25 = new JToggleButton();
    JToggleButton textureTB_24 = new JToggleButton();
    JToggleButton textureTB_23 = new JToggleButton();
    JToggleButton textureTB_22 = new JToggleButton();
    JToggleButton textureTB_21 = new JToggleButton();
    JToggleButton textureTB_20 = new JToggleButton();
    JToggleButton textureTB_09 = new JToggleButton();
    JToggleButton textureTB_08 = new JToggleButton();
    JToggleButton textureTB_07 = new JToggleButton();
    JToggleButton textureTB_06 = new JToggleButton();
    JToggleButton textureTB_05 = new JToggleButton();
    JToggleButton textureTB_04 = new JToggleButton();
    JToggleButton textureTB_03 = new JToggleButton();
    JToggleButton textureTB_02 = new JToggleButton();
    JToggleButton textureTB_01 = new JToggleButton();
    JToggleButton textureTB_19 = new JToggleButton();
    JToggleButton textureTB_18 = new JToggleButton();
    JToggleButton textureTB_17 = new JToggleButton();
    JToggleButton textureTB_16 = new JToggleButton();
    JToggleButton textureTB_15 = new JToggleButton();
    JToggleButton textureTB_14 = new JToggleButton();
    JToggleButton textureTB_13 = new JToggleButton();
    JToggleButton textureTB_12 = new JToggleButton();
    JToggleButton textureTB_11 = new JToggleButton();
    JToggleButton textureTB_10 = new JToggleButton();
    JLabel labelTexture = new JLabel();
    JTextField textFieldTextureInfo = new JTextField();
    Border border1;
    JButton buttonMoreTexture = new JButton();

	//FillEffect에 사용될 Image...
	private Image fillEffectImage = null;
	private FillEffect fillEffect = null;

	ResourceBundle bundle = ResourceBundle.getBundle("jchartui/TextureTabBundle", jchartui.JCalcResource.getLocale());
	String[] textureString = (String[])bundle.getObject("textureString");

    public TextureTab() {
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void jbInit() throws Exception {


        border1 = BorderFactory.createBevelBorder(BevelBorder.LOWERED,Color.white,Color.white,new Color(142, 142, 142),new Color(99, 99, 99));

        labelTexture.setText(bundle.getString("texture"));
        labelTexture.setBounds(new Rectangle(11, 13, 60, 15));
        this.setLayout(null);
        scrollTexture.setBounds(new Rectangle(12, 31, 274, 162));

        panelTexture.setLayout(gridLayout1);
        gridLayout1.setRows(7);
        gridLayout1.setColumns(4);
        gridLayout1.setHgap(2);
        gridLayout1.setVgap(2);

        textureTB_01.setPreferredSize(new Dimension(56, 40));
        textureTB_01.setBorder(border1);
        textureTB_01.setMinimumSize(new Dimension(56, 40));
        textureTB_01.setMaximumSize(new Dimension(56, 40));
		textureTB_01.setIcon(new ImageIcon(Utility.getResource("texture_01.gif")));
        textureTB_01.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_01_actionPerformed(e);
            }
        });
        textureTB_02.setPreferredSize(new Dimension(56, 40));
        textureTB_02.setBorder(border1);
		textureTB_02.setIcon(new ImageIcon(Utility.getResource("texture_02.gif")));
        textureTB_02.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_02_actionPerformed(e);
            }
        });
        textureTB_03.setPreferredSize(new Dimension(56, 40));
        textureTB_03.setBorder(border1);
		textureTB_03.setIcon(new ImageIcon(Utility.getResource("texture_03.gif")));
        textureTB_03.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_03_actionPerformed(e);
            }
        });
        textureTB_04.setPreferredSize(new Dimension(56, 40));
        textureTB_04.setBorder(border1);
		textureTB_04.setIcon(new ImageIcon(Utility.getResource("texture_04.gif")));
        textureTB_04.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_04_actionPerformed(e);
            }
        });
        textureTB_05.setPreferredSize(new Dimension(56, 40));
        textureTB_05.setBorder(border1);
		textureTB_05.setIcon(new ImageIcon(Utility.getResource("texture_05.gif")));
        textureTB_05.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_05_actionPerformed(e);
            }
        });
        textureTB_06.setPreferredSize(new Dimension(56, 40));
        textureTB_06.setBorder(border1);
		textureTB_06.setIcon(new ImageIcon(Utility.getResource("texture_06.gif")));
        textureTB_06.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_06_actionPerformed(e);
            }
        });
        textureTB_07.setPreferredSize(new Dimension(56, 40));
        textureTB_07.setBorder(border1);
		textureTB_07.setIcon(new ImageIcon(Utility.getResource("texture_07.gif")));
        textureTB_07.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_07_actionPerformed(e);
            }
        });
        textureTB_08.setPreferredSize(new Dimension(56, 40));
        textureTB_08.setBorder(border1);
		textureTB_08.setIcon(new ImageIcon(Utility.getResource("texture_08.gif")));
        textureTB_08.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_08_actionPerformed(e);
            }
        });
        textureTB_09.setPreferredSize(new Dimension(56, 40));
        textureTB_09.setBorder(border1);
		textureTB_09.setIcon(new ImageIcon(Utility.getResource("texture_09.gif")));
        textureTB_09.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_09_actionPerformed(e);
            }
        });
        textureTB_10.setPreferredSize(new Dimension(56, 40));
        textureTB_10.setBorder(border1);
		textureTB_10.setIcon(new ImageIcon(Utility.getResource("texture_10.gif")));
        textureTB_10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_10_actionPerformed(e);
            }
        });
        textureTB_11.setPreferredSize(new Dimension(56, 40));
        textureTB_11.setBorder(border1);
		textureTB_11.setIcon(new ImageIcon(Utility.getResource("texture_11.gif")));
        textureTB_11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_11_actionPerformed(e);
            }
        });
        textureTB_12.setPreferredSize(new Dimension(56, 40));
        textureTB_12.setBorder(border1);
		textureTB_12.setIcon(new ImageIcon(Utility.getResource("texture_12.gif")));
        textureTB_12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_12_actionPerformed(e);
            }
        });
        textureTB_13.setPreferredSize(new Dimension(56, 40));
        textureTB_13.setBorder(border1);
		textureTB_13.setIcon(new ImageIcon(Utility.getResource("texture_13.gif")));
        textureTB_13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_13_actionPerformed(e);
            }
        });
        textureTB_14.setPreferredSize(new Dimension(56, 40));
        textureTB_14.setBorder(border1);
		textureTB_14.setIcon(new ImageIcon(Utility.getResource("texture_14.gif")));
        textureTB_14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_14_actionPerformed(e);
            }
        });
        textureTB_15.setPreferredSize(new Dimension(56, 40));
        textureTB_15.setBorder(border1);
		textureTB_15.setIcon(new ImageIcon(Utility.getResource("texture_15.gif")));
        textureTB_15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_15_actionPerformed(e);
            }
        });
        textureTB_16.setPreferredSize(new Dimension(56, 40));
        textureTB_16.setBorder(border1);
		textureTB_16.setIcon(new ImageIcon(Utility.getResource("texture_16.gif")));
        textureTB_16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_16_actionPerformed(e);
            }
        });
        textureTB_17.setPreferredSize(new Dimension(56, 40));
        textureTB_17.setBorder(border1);
		textureTB_17.setIcon(new ImageIcon(Utility.getResource("texture_17.gif")));
        textureTB_17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_17_actionPerformed(e);
            }
        });
        textureTB_18.setPreferredSize(new Dimension(56, 40));
        textureTB_18.setBorder(border1);
		textureTB_18.setIcon(new ImageIcon(Utility.getResource("texture_18.gif")));
        textureTB_18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_18_actionPerformed(e);
            }
        });
        textureTB_19.setPreferredSize(new Dimension(56, 40));
        textureTB_19.setBorder(border1);
		textureTB_19.setIcon(new ImageIcon(Utility.getResource("texture_19.gif")));
        textureTB_19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_19_actionPerformed(e);
            }
        });
        textureTB_20.setPreferredSize(new Dimension(56, 40));
        textureTB_20.setBorder(border1);
		textureTB_20.setIcon(new ImageIcon(Utility.getResource("texture_20.gif")));
        textureTB_20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_20_actionPerformed(e);
            }
        });
        textureTB_21.setPreferredSize(new Dimension(56, 40));
        textureTB_21.setBorder(border1);
		textureTB_21.setIcon(new ImageIcon(Utility.getResource("texture_21.gif")));
        textureTB_21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_21_actionPerformed(e);
            }
        });
        textureTB_22.setPreferredSize(new Dimension(56, 40));
        textureTB_22.setBorder(border1);
		textureTB_22.setIcon(new ImageIcon(Utility.getResource("texture_22.gif")));
        textureTB_22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_22_actionPerformed(e);
            }
        });
        textureTB_23.setPreferredSize(new Dimension(56, 40));
        textureTB_23.setBorder(border1);
		textureTB_23.setIcon(new ImageIcon(Utility.getResource("texture_23.gif")));
        textureTB_23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_23_actionPerformed(e);
            }
        });
        textureTB_24.setPreferredSize(new Dimension(56, 40));
        textureTB_24.setBorder(border1);
		textureTB_24.setIcon(new ImageIcon(Utility.getResource("texture_24.gif")));
        textureTB_24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_24_actionPerformed(e);
            }
        });
        textureTB_25.setPreferredSize(new Dimension(56, 40));
        textureTB_25.setBorder(border1);
        textureTB_25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textureTB_25_actionPerformed(e);
            }
        });
        textFieldTextureInfo.setBackground(UIManager.getColor("MenuItem.background"));
        textFieldTextureInfo.setBorder(border1);
        textFieldTextureInfo.setBounds(new Rectangle(12, 203, 274, 25));
        buttonMoreTexture.setMargin(new Insets(2, 2, 2, 2));
        buttonMoreTexture.setText(bundle.getString("otherTexture"));
        buttonMoreTexture.setBounds(new Rectangle(184, 236, 102, 20));
        buttonMoreTexture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                buttonMoreTexture_actionPerformed(e);
            }
        });
        this.add(scrollTexture, null);
        this.add(labelTexture, null);
        this.add(textFieldTextureInfo, null);
        this.add(buttonMoreTexture, null);
        scrollTexture.getViewport().add(panelTexture, null);
        panelTexture.add(textureTB_01, null);
        panelTexture.add(textureTB_02, null);
        panelTexture.add(textureTB_03, null);
        panelTexture.add(textureTB_04, null);
        panelTexture.add(textureTB_05, null);
        panelTexture.add(textureTB_06, null);
        panelTexture.add(textureTB_07, null);
        panelTexture.add(textureTB_08, null);
        panelTexture.add(textureTB_09, null);
        panelTexture.add(textureTB_10, null);
        panelTexture.add(textureTB_11, null);
        panelTexture.add(textureTB_12, null);
        panelTexture.add(textureTB_13, null);
        panelTexture.add(textureTB_14, null);
        panelTexture.add(textureTB_15, null);
        panelTexture.add(textureTB_16, null);
        panelTexture.add(textureTB_17, null);
        panelTexture.add(textureTB_18, null);
        panelTexture.add(textureTB_19, null);
        panelTexture.add(textureTB_20, null);
        panelTexture.add(textureTB_21, null);
        panelTexture.add(textureTB_22, null);
        panelTexture.add(textureTB_23, null);
        panelTexture.add(textureTB_24, null);
        panelTexture.add(textureTB_25, null);


    }

	//More Texture 선택....
    void buttonMoreTexture_actionPerformed(ActionEvent e) {
		File file = getChoosedImageFile(bundle.getString("selecteTexture"));
		if( file != null ) {
			String fileName = file.getName();
			int index = fileName.lastIndexOf(".");
			fileName = fileName.substring(0, index );
			this.textFieldTextureInfo.setText( fileName );
			Utility.debug(this, file.getAbsolutePath() );
			ImageIcon icon = new ImageIcon( file.getAbsolutePath() );
			this.textureTB_25.setIcon( icon );
			java.awt.geom.Rectangle2D bounds = this.textureTB_25.getBounds();
			Point p = new Point( (int) bounds.getX(), (int) bounds.getY() );
			this.scrollTexture.getViewport().setViewPosition( p );

			// 선택된 이미지를 샘플 화면에 보여준다.
			this.fillEffectImage = icon.getImage();

			//this.showSelectedFillEffect();
		}
    }

    void textureTB_01_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[0]);
		this.selectTexture(textureTB_01.getIcon());
    }

    void textureTB_02_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[1]);
		this.selectTexture(textureTB_02.getIcon());
    }

    void textureTB_03_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[2]);
		this.selectTexture(textureTB_03.getIcon());
    }

    void textureTB_04_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[3]);
		this.selectTexture(textureTB_04.getIcon());
    }

    void textureTB_05_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[4]);
		this.selectTexture(textureTB_05.getIcon());
    }

    void textureTB_06_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[5]);
		this.selectTexture(textureTB_06.getIcon());
    }

    void textureTB_07_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[6]);
		this.selectTexture(textureTB_07.getIcon());
    }

    void textureTB_08_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[7]);
		this.selectTexture(textureTB_08.getIcon());
    }

    void textureTB_09_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[8]);
		this.selectTexture(textureTB_09.getIcon());
    }

    void textureTB_10_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[9]);
		this.selectTexture(textureTB_10.getIcon());
    }

    void textureTB_11_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[10]);
		this.selectTexture(textureTB_11.getIcon());
    }

    void textureTB_12_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[11]);
		this.selectTexture(textureTB_12.getIcon());
    }

    void textureTB_13_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[12]);
		this.selectTexture(textureTB_13.getIcon());
    }

    void textureTB_14_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[13]);
		this.selectTexture(textureTB_14.getIcon());
    }

    void textureTB_15_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[14]);
		this.selectTexture(textureTB_15.getIcon());
    }

    void textureTB_16_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[15]);
		this.selectTexture(textureTB_16.getIcon());
    }

    void textureTB_17_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[16]);
		this.selectTexture(textureTB_17.getIcon());
    }

    void textureTB_18_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[17]);
		this.selectTexture(textureTB_18.getIcon());
    }

    void textureTB_19_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[18]);
		this.selectTexture(textureTB_19.getIcon());
    }

    void textureTB_20_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[19]);
		this.selectTexture(textureTB_20.getIcon());
    }

    void textureTB_21_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[20]);
		this.selectTexture(textureTB_21.getIcon());
    }

    void textureTB_22_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[21]);
		this.selectTexture(textureTB_22.getIcon());
    }

    void textureTB_23_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[22]);
		this.selectTexture(textureTB_23.getIcon());
    }

    void textureTB_24_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[23]);
		this.selectTexture(textureTB_24.getIcon());
    }

    void textureTB_25_actionPerformed(ActionEvent e) {
		this.textFieldTextureInfo.setText(this.textureString[24]);
		this.selectTexture(textureTB_25.getIcon());
    }

	void selectTexture(Icon icon) {
		if (icon == null) this.fillEffectImage = null;
		else {
		    this.fillEffectImage = ((ImageIcon)icon).getImage();
			FillEffectEditor.showCurrentFillEffect(this.getFillEffect());
		}
	}

	void selectTexture(ActionEvent e, String textureName) {
		 JToggleButton tb = (JToggleButton) e.getSource();
		 if( textureName != null ) {
			this.textFieldTextureInfo.setText( textureName );
		 }
		 Icon icon = tb.getIcon();
		 if( icon == null ) {
			return;
		 }
		 ImageIcon imageIcon = (ImageIcon) icon;
		 Image image = imageIcon.getImage();
		 this.fillEffectImage = image;
	//     this.showSelectedFillEffect();
	//     this.lastActionEventTime = System.currentTimeMillis();
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

	//FillEffect를 설정하고 반환...
	public void setFillEffect (FillEffect fillEffect) {
		this.fillEffect = fillEffect;
	}

	public FillEffect getFillEffect() {
		//FillEffect에 저장할 이미지가 없으면 null을 반환...
		if (this.fillEffectImage == null) return null;

		if (this.fillEffect == null) fillEffect = new FillEffect();


		this.fillEffect.setImage(this.fillEffectImage);

		return this.fillEffect.create();
	}


	public void changeMenuText() {
//		this.bundle = ResourceBundle.getBundle("com.techdigm.chart.editor/TextureTabBundle", jcalc.JCalcResource.getLocale());
		this.bundle = ResourceBundle.getBundle("jchartui/TextureTabBundle", jchartui.JCalcResource.getLocale());
		this.textureString = (String[])bundle.getObject("textureString");

		labelTexture.setText(bundle.getString("texture"));
        buttonMoreTexture.setText(bundle.getString("otherTexture"));
	}
}













