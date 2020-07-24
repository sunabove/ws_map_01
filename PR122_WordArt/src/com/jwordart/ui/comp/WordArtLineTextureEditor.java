/**
 * Title:        Java Word Art<p>
 * Description:  Word Art JAVA version<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package com.jwordart.ui.comp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;

import com.jwordart.model.fillEffect.GraphicEffect;
import com.jwordart.ui.renderer.FillColorComboBoxRenderer;
import com.jwordart.ui.resource.Resource_WordArt;
import com.jwordart.util.SystemUtil;
import com.jwordart.util.WordArtUtil;

import java.io.File;
import java.awt.geom.*;
import java.awt.image.*;

public class WordArtLineTextureEditor extends JDialog {
	// Data

	private GraphicEffect graphicEffect = new GraphicEffect();
	private Image fillEffectImage = null;
	private boolean neverShown = true;

	private Color firstTileColor = Color.white;
	private Color secondTileColor = Color.black;

	private long lastActionEventTime = System.currentTimeMillis();

	private FillColorComboBoxRenderer firstTileColorChooserComboBoxRenderer = new FillColorComboBoxRenderer();
	private FillColorComboBoxRenderer secondTileColorChooserComboBoxRenderer = new FillColorComboBoxRenderer();

	// End of data.

	JPanel contentPane;
	BorderLayout borderLayout1 = new BorderLayout();
	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();
	JPanel jPanel3 = new JPanel();
	JTabbedPane jTabbedPane1 = new JTabbedPane();
	JPanel jPanel5 = new JPanel();
	BorderLayout borderLayout2 = new BorderLayout();
	JPanel jPanel7 = new JPanel();
	JPanel jPanel8 = new JPanel();
	JPanel jPanel6 = new JPanel();
	BorderLayout borderLayout3 = new BorderLayout();
	JPanel jPanel9 = new JPanel();
	JPanel jSbmoonPanel = new JPanel();
	JPanel jPanel11 = new JPanel();
	JLabel jLabel1 = new JLabel();
	FlowLayout flowLayout2 = new FlowLayout();
	JButton veryfyButton = new JButton();
	JButton cancelButton = new JButton();
	JLabel jLabel2 = new JLabel();
	BorderLayout borderLayout4 = new BorderLayout();
	Border border1;
	TitledBorder titledBorder1;
	Border border2;
	TitledBorder titledBorder2;
	Border border3;
	TitledBorder titledBorder3;
	Border border4;
	TitledBorder titledBorder4;
	Border border5;
	Border border6;
	Border border7;
	GridLayout gridLayout2 = new GridLayout();
	Border border8;
	Border border9;
	JPanel muniPanel = new JPanel();
	Border border10;
	Border border11;
	Border border12;
	BorderLayout borderLayout10 = new BorderLayout();
	Border border13;
	JPanel selectedFillEffectViewer = new JPanel() {
		@Override
		public void update(Graphics g) {
			this.paint(g);
		}

		@Override
		public void paint(Graphics g) {
			super.paint(g);
			new Thread() {
				@Override
				public void run() {
					SystemUtil.waitForSeconds(0.5);
					WordArtLineTextureEditor.this.showSelectedFillEffect();
				}
			}.start();
		}
	};
	Border border14;
	Border border15;
	Border border16;
	BorderLayout borderLayout13 = new BorderLayout();
	JPanel jPanel26 = new JPanel();
	JPanel jPanel111 = new JPanel();
	JButton jButton7 = new JButton();
	FlowLayout flowLayout7 = new FlowLayout();
	JPanel tileTBsPanel = new JPanel();
	BorderLayout borderLayout14 = new BorderLayout();
	JPanel jPanel29 = new JPanel();
	JTextField imageFileNameTF1 = new JTextField();
	FlowLayout flowLayout8 = new FlowLayout();
	Border border17;
	JPanel jPanel30 = new JPanel();
	Border border18;
	Border border19;
	GridLayout gridLayout4 = new GridLayout();
	JComboBox secondTileColorComboBox = new JComboBox(new String[] { "", " �ٸ� �� " });
	JComboBox firstTileColorComboBox = new JComboBox(new String[] { "", " �ٸ� �� " });
	JButton jButton8 = new JButton();
	JButton jButton9 = new JButton();
	Border border20;
	GridLayout gridLayout5 = new GridLayout();
	Border border21;

	class JTiledToggleButton extends JToggleButton {
		ImageIcon nonTiledImageIcon = null;
		ImageIcon orgNonTiledImageIcon = null;
		Image processedTileImage = null;
		boolean neverShown = true;

		public JTiledToggleButton() {
			this.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					lastActionEventTime = System.currentTimeMillis();
				}
			});
		}

		@Override
		public void setIcon(Icon icon) {
			if (nonTiledImageIcon == null) {
				// nonTiledImageIcon = (ImageIcon) icon;
				if (orgNonTiledImageIcon == null) {
					orgNonTiledImageIcon = (ImageIcon) icon;
					neverShown = true;
				}
			}
			if (icon == null) {
				nonTiledImageIcon = null;
				neverShown = true;
			}
			if (icon != null) {
				super.setIcon(icon);
			}
		}

		public void setNonTiledImageIcon(ImageIcon icon) {
			this.nonTiledImageIcon = icon;
		}

		@Override
		public void paint(Graphics g) {

			// �� Ÿ�ϵ� �̹��� �������� ������ ���.
			if (nonTiledImageIcon == null) {
				if (orgNonTiledImageIcon != null) {
					Frame f = new Frame();
					f.addNotify();
					int ow = orgNonTiledImageIcon.getImage().getWidth(f), oh = orgNonTiledImageIcon.getImage().getHeight(f);
					BufferedImage image = WordArtLineTextureEditor.this.craeteTexturePaintedTiledImage(ow, oh, orgNonTiledImageIcon);
					processedTileImage = image;
					this.nonTiledImageIcon = orgNonTiledImageIcon;
				} else {
					super.paint(g);
					return;
				}
			}
			// ��. �� Ÿ�ϵ� �̹��� ������ ����.

			int w = super.getWidth(), h = super.getHeight();

			if (w > 0 && h > 0) {
			} else {
				super.paint(g);
				return;
			}

			// Ÿ�ϵ� �̹��� ������ ����.
			if (this.neverShown) {
				BufferedImage tiledImage = WordArtLineTextureEditor.this.craeteTexturePaintedTiledImage(w, h, nonTiledImageIcon);
				super.setIcon(new ImageIcon(tiledImage));
				this.neverShown = false;
			}
			// ��. Ÿ�ϵ� �̹��� ������ ����.

			if (super.isSelected() && this.processedTileImage != null) {
				WordArtLineTextureEditor.this.fillEffectImage = this.processedTileImage;
				WordArtLineTextureEditor.this.showSelectedFillEffect();
			}

			super.paint(g);
		}
	};

	JToggleButton tileTB_48 = new JTiledToggleButton();
	JToggleButton tileTB_47 = new JTiledToggleButton();
	JToggleButton tileTB_46 = new JTiledToggleButton();
	JToggleButton tileTB_45 = new JTiledToggleButton();
	JToggleButton tileTB_44 = new JTiledToggleButton();
	JToggleButton tileTB_43 = new JTiledToggleButton();
	JToggleButton tileTB_42 = new JTiledToggleButton();
	JToggleButton tileTB_41 = new JTiledToggleButton();
	JToggleButton tileTB_40 = new JTiledToggleButton();
	JToggleButton tileTB_39 = new JTiledToggleButton();
	JToggleButton tileTB_38 = new JTiledToggleButton();
	JToggleButton tileTB_37 = new JTiledToggleButton();
	JToggleButton tileTB_36 = new JTiledToggleButton();
	JToggleButton tileTB_35 = new JTiledToggleButton();
	JToggleButton tileTB_34 = new JTiledToggleButton();
	JToggleButton tileTB_33 = new JTiledToggleButton();
	JToggleButton tileTB_32 = new JTiledToggleButton();
	JToggleButton tileTB_31 = new JTiledToggleButton();
	JToggleButton tileTB_30 = new JTiledToggleButton();
	JToggleButton tileTB_29 = new JTiledToggleButton();
	JToggleButton tileTB_28 = new JTiledToggleButton();
	JToggleButton tileTB_27 = new JTiledToggleButton();
	JToggleButton tileTB_26 = new JTiledToggleButton();
	JToggleButton tileTB_25 = new JTiledToggleButton();
	JToggleButton tileTB_24 = new JTiledToggleButton();
	JToggleButton tileTB_23 = new JTiledToggleButton();
	JToggleButton tileTB_22 = new JTiledToggleButton();
	JToggleButton tileTB_21 = new JTiledToggleButton();
	JToggleButton tileTB_20 = new JTiledToggleButton();
	JToggleButton tileTB_19 = new JTiledToggleButton();
	JToggleButton tileTB_18 = new JTiledToggleButton();
	JToggleButton tileTB_17 = new JTiledToggleButton();
	JToggleButton tileTB_16 = new JTiledToggleButton();
	JToggleButton tileTB_15 = new JTiledToggleButton();
	JToggleButton tileTB_14 = new JTiledToggleButton();
	JToggleButton tileTB_13 = new JTiledToggleButton();
	JToggleButton tileTB_12 = new JTiledToggleButton();
	JToggleButton tileTB_11 = new JTiledToggleButton();
	JToggleButton tileTB_10 = new JTiledToggleButton();
	JToggleButton tileTB_09 = new JTiledToggleButton();
	JToggleButton tileTB_08 = new JTiledToggleButton();
	JToggleButton tileTB_07 = new JTiledToggleButton();
	JToggleButton tileTB_06 = new JTiledToggleButton();
	JToggleButton tileTB_05 = new JTiledToggleButton();
	JToggleButton tileTB_04 = new JTiledToggleButton();
	JToggleButton tileTB_03 = new JTiledToggleButton();
	JToggleButton tileTB_02 = new JTiledToggleButton();
	JToggleButton tileTB_01 = new JTiledToggleButton();

	// Construct the frame
	protected WordArtLineTextureEditor() {
		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			this.jbInit();
			// Code by sbmoon.
			this.firstTileColorComboBox.setRenderer(this.firstTileColorChooserComboBoxRenderer);
			this.secondTileColorComboBox.setRenderer(this.secondTileColorChooserComboBoxRenderer);
			this.secondTileColorChooserComboBoxRenderer.changeColor(this.firstTileColor);
			// End of code by sbmoon.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Color getFirstTileColor() {
		return this.firstTileColor;
	}

	public Color getSecondTileColor() {
		return this.secondTileColor;
	}

	BufferedImage craeteTexturePaintedTiledImage(int w, int h, ImageIcon imageIcon) {
		// ���� ���۵� �̹��� ����
		Image refImage = imageIcon.getImage();
		Frame f = new Frame();
		f.addNotify();
		int rw = refImage.getWidth(f), rh = refImage.getHeight(f);
		BufferedImage refBImage = new BufferedImage(rw, rh, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) refBImage.getGraphics();
		g2.drawImage(refImage, 0, 0, f);
		// ��. ���� ���۵� �̹��� ����

		// ���� �̹��� ��������
		BufferedImage processedImage = new BufferedImage(rw, rh, BufferedImage.TYPE_INT_RGB);
		g2 = (Graphics2D) processedImage.getGraphics();
		int srcPixel, dstPixel, whitePixel = Color.white.getRGB(), firstTileColorPixel = firstTileColor.getRGB(), secondTileColorPixel = secondTileColor
				.getRGB();

		for (int y = 0; y < refBImage.getHeight(); y++) {
			for (int x = 0; x < refBImage.getWidth(); x++) {
				srcPixel = refBImage.getRGB(x, y);
				if (srcPixel == whitePixel) {
					dstPixel = firstTileColorPixel;
				} else {
					dstPixel = secondTileColorPixel;
				}
				processedImage.setRGB(x, y, dstPixel);
			}
		}
		// ��. ���� �̹��� ��������

		// Ÿ�ϵ� ���۵� �̹��� ����
		BufferedImage tiledImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Rectangle2D tr = new Rectangle2D.Double(0, 0, rw, rh);
		TexturePaint tp = new TexturePaint(processedImage, tr);
		Rectangle2D rect = new Rectangle2D.Double(0, 0, w, h);
		g2 = (Graphics2D) tiledImage.getGraphics();
		g2.setPaint(tp);
		g2.fill(rect);
		// ��. Ÿ�ϵ� ���۵� �̹��� ����

		return tiledImage;
	}

	// Component initialization
	private void jbInit() throws Exception {
		contentPane = (JPanel) this.getContentPane();
		border1 = new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(148, 145, 140));
		titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "��");
		border2 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
		titledBorder2 = new TitledBorder(border2, "����");
		border3 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
		titledBorder3 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "����");
		border4 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
		titledBorder4 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "����(S)");
		border5 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "��");
		border6 = BorderFactory.createCompoundBorder(titledBorder3, BorderFactory.createEmptyBorder(0, 5, 0, 0));
		border7 = BorderFactory.createEmptyBorder(3, 0, 0, 0);
		border8 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
		border9 = BorderFactory.createCompoundBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(
				148, 145, 140)), "����(S) "), BorderFactory.createEmptyBorder(4, 6, 6, 6));
		border10 = BorderFactory.createEmptyBorder();
		border11 = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white, new Color(148, 145, 140),
				new Color(103, 101, 98));
		border12 = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white, Color.black, Color.black);
		border13 = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		border14 = BorderFactory.createEmptyBorder(5, 8, 5, 5);
		border15 = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white, Color.black, Color.black);
		border16 = BorderFactory.createEmptyBorder(0, 8, 0, 5);
		border17 = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		border18 = BorderFactory.createEmptyBorder(0, 5, 0, 0);
		border19 = BorderFactory.createEmptyBorder(0, 0, 15, 5);
		border20 = BorderFactory.createEmptyBorder(5, 10, 0, 10);
		border21 = BorderFactory.createBevelBorder(BevelBorder.LOWERED, Color.white, Color.white, Color.black, Color.black);
		contentPane.setLayout(borderLayout1);
		this.setResizable(false);
		this.setSize(new Dimension(399, 357));
		this.setTitle("ä��� ȿ��");
		this.setModal(true);
		jPanel1.setBackground(SystemColor.menu);
		jPanel1.setPreferredSize(new Dimension(7, 10));
		jPanel2.setBackground(SystemColor.menu);
		jPanel2.setPreferredSize(new Dimension(10, 29));
		jPanel3.setBackground(Color.red);
		jPanel3.setDoubleBuffered(false);
		jPanel3.setPreferredSize(new Dimension(97, 10));
		jPanel3.setLayout(borderLayout2);
		jPanel5.setBackground(SystemColor.menu);
		jPanel5.setPreferredSize(new Dimension(10, 25));
		jPanel7.setBackground(SystemColor.menu);
		jPanel7.setPreferredSize(new Dimension(5, 10));
		jPanel8.setBackground(SystemColor.menu);
		jPanel8.setPreferredSize(new Dimension(20, 10));
		jPanel6.setLayout(borderLayout3);
		jPanel9.setBackground(SystemColor.menu);
		jPanel9.setPreferredSize(new Dimension(10, 190));
		jPanel9.setLayout(flowLayout2);
		jSbmoonPanel.setBackground(Color.black);
		jSbmoonPanel.setBorder(border13);
		jSbmoonPanel.setDoubleBuffered(false);
		jSbmoonPanel.setPreferredSize(new Dimension(10, 60));
		jSbmoonPanel.setLayout(borderLayout10);
		jLabel1.setHorizontalAlignment(SwingConstants.LEFT);
		jLabel1.setHorizontalTextPosition(SwingConstants.LEFT);
		jLabel1.setText("����:");
		jPanel11.setLayout(borderLayout4);
		veryfyButton.setBorder(BorderFactory.createRaisedBevelBorder());
		veryfyButton.setMaximumSize(new Dimension(1000, 1000));
		veryfyButton.setPreferredSize(new Dimension(70, 22));
		veryfyButton.setText("Ȯ��");
		veryfyButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtLineTextureEditor.this.veryfyButton_actionPerformed(e);
			}
		});
		cancelButton.setBorder(BorderFactory.createRaisedBevelBorder());
		cancelButton.setPreferredSize(new Dimension(70, 22));
		cancelButton.setSelected(true);
		cancelButton.setText("���");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtLineTextureEditor.this.cancelButton_actionPerformed(e);
			}
		});
		flowLayout2.setAlignment(FlowLayout.LEFT);
		flowLayout2.setHgap(0);
		flowLayout2.setVgap(0);
		jLabel2.setBackground(Color.cyan);
		jLabel2.setPreferredSize(new Dimension(20, 5));
		jLabel2.setText(" ");
		muniPanel.setBackground(Color.red);
		muniPanel.setLayout(borderLayout13);
		selectedFillEffectViewer.setDoubleBuffered(false);
		jPanel6.setDoubleBuffered(false);
		jTabbedPane1.setDoubleBuffered(true);
		jPanel26.setBackground(SystemColor.menu);
		jPanel26.setBorder(border19);
		jPanel26.setPreferredSize(new Dimension(10, 102));
		jPanel26.setLayout(borderLayout14);
		jPanel111.setLayout(flowLayout7);
		jPanel111.setPreferredSize(new Dimension(10, 30));
		jPanel111.setBackground(SystemColor.menu);
		jButton7.setText("����(T):");
		jButton7.setMnemonic('T');
		jButton7.setBorder(border10);
		flowLayout7.setAlignment(FlowLayout.LEFT);
		jPanel29.setBackground(SystemColor.menu);
		jPanel29.setBorder(border17);
		jPanel29.setPreferredSize(new Dimension(10, 35));
		jPanel29.setLayout(flowLayout8);
		imageFileNameTF1.setHorizontalAlignment(SwingConstants.CENTER);
		imageFileNameTF1.setPreferredSize(new Dimension(275, 22));
		imageFileNameTF1.setDisabledTextColor(Color.black);
		imageFileNameTF1.setBorder(border11);
		imageFileNameTF1.setBackground(SystemColor.menu);
		imageFileNameTF1.setEnabled(false);
		jPanel30.setBackground(SystemColor.menu);
		jPanel30.setBorder(border18);
		jPanel30.setPreferredSize(new Dimension(156, 51));
		jPanel30.setLayout(gridLayout4);
		gridLayout4.setRows(2);
		gridLayout4.setColumns(2);
		gridLayout4.setHgap(5);
		gridLayout4.setVgap(2);
		jButton8.setBorder(border10);
		jButton8.setHorizontalAlignment(SwingConstants.LEFT);
		jButton8.setHorizontalTextPosition(SwingConstants.LEFT);
		jButton8.setMnemonic('F');
		jButton8.setText("���̻�(F):");
		jButton9.setText("������(B):");
		jButton9.setMnemonic('B');
		jButton9.setBorder(border10);
		jButton9.setHorizontalAlignment(SwingConstants.LEFT);
		jButton9.setHorizontalTextPosition(SwingConstants.LEFT);
		tileTBsPanel.setBorder(border20);
		tileTBsPanel.setLayout(gridLayout5);
		gridLayout5.setRows(6);
		gridLayout5.setColumns(8);
		gridLayout5.setHgap(2);
		gridLayout5.setVgap(2);
		tileTB_48.setIcon(Resource_WordArt.getIcon("wordart", "tile_48.gif"));
		tileTB_48.setBorder(border21);
		tileTB_47.setBorder(border21);
		tileTB_47.setIcon(Resource_WordArt.getIcon("wordart", "tile_47.gif"));
		tileTB_46.setBorder(border21);
		tileTB_46.setIcon(Resource_WordArt.getIcon("wordart", "tile_46.gif"));
		tileTB_45.setBorder(border21);
		tileTB_45.setIcon(Resource_WordArt.getIcon("wordart", "tile_45.gif"));
		tileTB_44.setBorder(border21);
		tileTB_44.setIcon(Resource_WordArt.getIcon("wordart", "tile_44.gif"));
		tileTB_43.setBorder(border21);
		tileTB_43.setIcon(Resource_WordArt.getIcon("wordart", "tile_43.gif"));
		tileTB_42.setBorder(border21);
		tileTB_42.setIcon(Resource_WordArt.getIcon("wordart", "tile_42.gif"));
		tileTB_41.setBorder(border21);
		tileTB_41.setIcon(Resource_WordArt.getIcon("wordart", "tile_41.gif"));
		tileTB_40.setBorder(border21);
		tileTB_40.setIcon(Resource_WordArt.getIcon("wordart", "tile_40.gif"));
		tileTB_39.setBorder(border21);
		tileTB_39.setIcon(Resource_WordArt.getIcon("wordart", "tile_39.gif"));
		tileTB_38.setBorder(border21);
		tileTB_38.setIcon(Resource_WordArt.getIcon("wordart", "tile_38.gif"));
		tileTB_37.setBorder(border21);
		tileTB_37.setIcon(Resource_WordArt.getIcon("wordart", "tile_37.gif"));
		tileTB_36.setBorder(border21);
		tileTB_36.setIcon(Resource_WordArt.getIcon("wordart", "tile_36.gif"));
		tileTB_35.setBorder(border21);
		tileTB_35.setIcon(Resource_WordArt.getIcon("wordart", "tile_35.gif"));
		tileTB_34.setBorder(border21);
		tileTB_34.setIcon(Resource_WordArt.getIcon("wordart", "tile_34.gif"));
		tileTB_33.setBorder(border21);
		tileTB_33.setIcon(Resource_WordArt.getIcon("wordart", "tile_33.gif"));
		tileTB_32.setBorder(border21);
		tileTB_32.setIcon(Resource_WordArt.getIcon("wordart", "tile_32.gif"));
		tileTB_31.setBorder(border21);
		tileTB_31.setIcon(Resource_WordArt.getIcon("wordart", "tile_31.gif"));
		tileTB_30.setBorder(border21);
		tileTB_30.setIcon(Resource_WordArt.getIcon("wordart", "tile_30.gif"));
		tileTB_29.setBorder(border21);
		tileTB_29.setIcon(Resource_WordArt.getIcon("wordart", "tile_29.gif"));
		tileTB_28.setBorder(border21);
		tileTB_28.setIcon(Resource_WordArt.getIcon("wordart", "tile_28.gif"));
		tileTB_27.setBorder(border21);
		tileTB_27.setIcon(Resource_WordArt.getIcon("wordart", "tile_27.gif"));
		tileTB_26.setBorder(border21);
		tileTB_26.setIcon(Resource_WordArt.getIcon("wordart", "tile_26.gif"));
		tileTB_25.setBorder(border21);
		tileTB_25.setIcon(Resource_WordArt.getIcon("wordart", "tile_25.gif"));
		tileTB_24.setBorder(border21);
		tileTB_24.setIcon(Resource_WordArt.getIcon("wordart", "tile_24.gif"));
		tileTB_23.setBorder(border21);
		tileTB_23.setIcon(Resource_WordArt.getIcon("wordart", "tile_23.gif"));
		tileTB_22.setBorder(border21);
		tileTB_22.setIcon(Resource_WordArt.getIcon("wordart", "tile_22.gif"));
		tileTB_21.setBorder(border21);
		tileTB_21.setIcon(Resource_WordArt.getIcon("wordart", "tile_21.gif"));
		tileTB_20.setBorder(border21);
		tileTB_20.setIcon(Resource_WordArt.getIcon("wordart", "tile_20.gif"));
		tileTB_19.setBorder(border21);
		tileTB_19.setIcon(Resource_WordArt.getIcon("wordart", "tile_19.gif"));
		tileTB_18.setBorder(border21);
		tileTB_18.setIcon(Resource_WordArt.getIcon("wordart", "tile_18.gif"));
		tileTB_17.setBorder(border21);
		tileTB_17.setIcon(Resource_WordArt.getIcon("wordart", "tile_17.gif"));
		tileTB_16.setBorder(border21);
		tileTB_16.setIcon(Resource_WordArt.getIcon("wordart", "tile_16.gif"));
		tileTB_15.setBorder(border21);
		tileTB_15.setIcon(Resource_WordArt.getIcon("wordart", "tile_15.gif"));
		tileTB_14.setBorder(border21);
		tileTB_14.setIcon(Resource_WordArt.getIcon("wordart", "tile_14.gif"));
		tileTB_13.setBorder(border21);
		tileTB_13.setIcon(Resource_WordArt.getIcon("wordart", "tile_13.gif"));
		tileTB_12.setBorder(border21);
		tileTB_12.setIcon(Resource_WordArt.getIcon("wordart", "tile_12.gif"));
		tileTB_11.setBorder(border21);
		tileTB_11.setIcon(Resource_WordArt.getIcon("wordart", "tile_11.gif"));
		tileTB_10.setBorder(border21);
		tileTB_10.setIcon(Resource_WordArt.getIcon("wordart", "tile_10.gif"));
		tileTB_09.setBorder(border21);
		tileTB_09.setIcon(Resource_WordArt.getIcon("wordart", "tile_09.gif"));
		tileTB_08.setBorder(border21);
		tileTB_08.setIcon(Resource_WordArt.getIcon("wordart", "tile_08.gif"));
		tileTB_07.setBorder(border21);
		tileTB_07.setIcon(Resource_WordArt.getIcon("wordart", "tile_07.gif"));
		tileTB_06.setBorder(border21);
		tileTB_06.setIcon(Resource_WordArt.getIcon("wordart", "tile_06.gif"));
		tileTB_05.setBorder(border21);
		tileTB_05.setIcon(Resource_WordArt.getIcon("wordart", "tile_05.gif"));
		tileTB_04.setBorder(border21);
		tileTB_04.setIcon(Resource_WordArt.getIcon("wordart", "tile_04.gif"));
		tileTB_03.setBorder(border21);
		tileTB_03.setIcon(Resource_WordArt.getIcon("wordart", "tile_03.gif"));
		tileTB_02.setBorder(border21);
		tileTB_02.setIcon(Resource_WordArt.getIcon("wordart", "tile_02.gif"));
		tileTB_01.setBorder(border21);
		tileTB_01.setIcon(Resource_WordArt.getIcon("wordart", "tile_01.gif"));
		firstTileColorComboBox.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtLineTextureEditor.this.firstTileColorComboBox_actionPerformed(e);
			}
		});
		secondTileColorComboBox.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtLineTextureEditor.this.secondTileColorComboBox_actionPerformed(e);
			}
		});
		contentPane.add(jPanel1, BorderLayout.WEST);
		contentPane.add(jPanel2, BorderLayout.SOUTH);
		contentPane.add(jPanel3, BorderLayout.EAST);
		jPanel3.add(jPanel5, BorderLayout.NORTH);
		jPanel3.add(jPanel7, BorderLayout.WEST);
		jPanel3.add(jPanel8, BorderLayout.EAST);
		jPanel3.add(jPanel6, BorderLayout.CENTER);
		jPanel6.add(jPanel9, BorderLayout.NORTH);
		jPanel9.add(veryfyButton, null);
		jPanel9.add(jLabel2, null);
		jPanel9.add(cancelButton, null);
		jPanel6.add(jSbmoonPanel, BorderLayout.SOUTH);
		jSbmoonPanel.add(selectedFillEffectViewer, BorderLayout.CENTER);
		jPanel6.add(jPanel11, BorderLayout.CENTER);
		jPanel11.add(jLabel1, BorderLayout.CENTER);
		contentPane.add(jTabbedPane1, BorderLayout.CENTER);
		jTabbedPane1.add(muniPanel, " ���� ");
		muniPanel.add(jPanel26, BorderLayout.SOUTH);
		jPanel26.add(jPanel29, BorderLayout.NORTH);
		jPanel29.add(imageFileNameTF1, null);
		jPanel26.add(jPanel30, BorderLayout.CENTER);
		jPanel30.add(jButton8, null);
		jPanel30.add(jButton9, null);
		jPanel30.add(firstTileColorComboBox, null);
		jPanel30.add(secondTileColorComboBox, null);
		muniPanel.add(jPanel111, BorderLayout.NORTH);
		jPanel111.add(jButton7, null);
		muniPanel.add(tileTBsPanel, BorderLayout.CENTER);
		tileTBsPanel.add(tileTB_01, null);
		tileTBsPanel.add(tileTB_02, null);
		tileTBsPanel.add(tileTB_03, null);
		tileTBsPanel.add(tileTB_04, null);
		tileTBsPanel.add(tileTB_05, null);
		tileTBsPanel.add(tileTB_06, null);
		tileTBsPanel.add(tileTB_07, null);
		tileTBsPanel.add(tileTB_08, null);
		tileTBsPanel.add(tileTB_09, null);
		tileTBsPanel.add(tileTB_10, null);
		tileTBsPanel.add(tileTB_11, null);
		tileTBsPanel.add(tileTB_12, null);
		tileTBsPanel.add(tileTB_13, null);
		tileTBsPanel.add(tileTB_14, null);
		tileTBsPanel.add(tileTB_15, null);
		tileTBsPanel.add(tileTB_16, null);
		tileTBsPanel.add(tileTB_17, null);
		tileTBsPanel.add(tileTB_18, null);
		tileTBsPanel.add(tileTB_19, null);
		tileTBsPanel.add(tileTB_20, null);
		tileTBsPanel.add(tileTB_21, null);
		tileTBsPanel.add(tileTB_22, null);
		tileTBsPanel.add(tileTB_23, null);
		tileTBsPanel.add(tileTB_24, null);
		tileTBsPanel.add(tileTB_25, null);
		tileTBsPanel.add(tileTB_26, null);
		tileTBsPanel.add(tileTB_27, null);
		tileTBsPanel.add(tileTB_28, null);
		tileTBsPanel.add(tileTB_29, null);
		tileTBsPanel.add(tileTB_30, null);
		tileTBsPanel.add(tileTB_31, null);
		tileTBsPanel.add(tileTB_32, null);
		tileTBsPanel.add(tileTB_33, null);
		tileTBsPanel.add(tileTB_34, null);
		tileTBsPanel.add(tileTB_35, null);
		tileTBsPanel.add(tileTB_36, null);
		tileTBsPanel.add(tileTB_37, null);
		tileTBsPanel.add(tileTB_38, null);
		tileTBsPanel.add(tileTB_39, null);
		tileTBsPanel.add(tileTB_40, null);
		tileTBsPanel.add(tileTB_41, null);
		tileTBsPanel.add(tileTB_42, null);
		tileTBsPanel.add(tileTB_43, null);
		tileTBsPanel.add(tileTB_44, null);
		tileTBsPanel.add(tileTB_45, null);
		tileTBsPanel.add(tileTB_46, null);
		tileTBsPanel.add(tileTB_47, null);
		tileTBsPanel.add(tileTB_48, null);
	}

	// Overridden so we can exit when window is closed
	@Override
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			// System.exit(0);
		}
	}

	void textureTB_01_actionPerformed(ActionEvent e) {
	}

	public void showSelectedFillEffect() {
		if (neverShown) {
			neverShown = false;
			return;
		}

		if (this.fillEffectImage != null) {
			BufferedImage bfrImage = Resource_WordArt.convertToBufferedImage(this.fillEffectImage);
			this.graphicEffect.setBfrImage(bfrImage);
		}

		GraphicEffect graphicEffect = this.graphicEffect;
		if (graphicEffect == null) {
			return;
		}

		long current = System.currentTimeMillis();

		// ���콺�� ���� �ӵ��� ���Ͻ�Ų��. 0.2�� �̻�����.
		if (Math.abs(current - lastActionEventTime) < 200) {
			return;
		}

		double w = this.selectedFillEffectViewer.getWidth(), h = this.selectedFillEffectViewer.getHeight();

		graphicEffect.setBounds(w, h);

		Paint gp = graphicEffect.getGradientPaint();
		TexturePaint tp = graphicEffect.getTexturePaint();

		Graphics2D g2 = (Graphics2D) this.selectedFillEffectViewer.getGraphics();
		Rectangle2D rect = new Rectangle2D.Double(1, 1, w - 2, h - 2);
		if (gp != null) {

			g2.setPaint(gp);
			g2.fill(rect);
		} else if (tp != null) {

			g2.setPaint(tp);
			g2.fill(rect);
		}

		this.graphicEffect = graphicEffect;
	}

	File getChoosedImageFile(String title) {
		JFileChooser fileChooser = new JFileChooser();
		FileFilter fileFilter = new FileFilter() {
			@Override
			public String getDescription() {
				return "Image Files";
			}

			@Override
			public boolean accept(File f) {
				if (f == null) {
					return false;
				} else if (f.isDirectory()) {
					return true;
				} else {
					return f.getName().toLowerCase().endsWith("gif") || f.getName().toLowerCase().endsWith("jpg")
							|| f.getName().toLowerCase().endsWith("jpeg");
				}
			}
		};
		fileChooser.setFileFilter(fileFilter);
		fileChooser.setCurrentDirectory(new File("."));
		fileChooser.showDialog(this, title);
		File selectedFile = fileChooser.getSelectedFile();
		return selectedFile;
	}

	void selectTexture(MouseEvent e) {
		JToggleButton tb = (JToggleButton) e.getSource();
		Icon icon = tb.getIcon();
		if (icon == null) {
			return;
		}
		ImageIcon imageIcon = (ImageIcon) icon;
		Image image = imageIcon.getImage();
		this.fillEffectImage = image;
		this.showSelectedFillEffect();
		this.lastActionEventTime = System.currentTimeMillis();
	}

	void textureTB_01_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_02_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_03_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_04_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_05_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_06_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_07_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_08_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_09_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_10_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_11_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_12_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_13_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_14_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_15_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_16_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_17_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_18_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_19_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_20_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_21_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_22_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_23_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_24_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void textureTB_25_mousePressed(MouseEvent e) {
		this.selectTexture(e);
	}

	void firstTileColorComboBox_actionPerformed(ActionEvent e) {
		int index = this.firstTileColorComboBox.getSelectedIndex();
		boolean changeTiledImages = false;
		if (index == 1) {
			Color refColor = this.firstTileColor;
			if (refColor == null) {
				refColor = Color.white;
			}
			Color color = JColorChooser.showDialog(this, "��", refColor);
			if (color != null) {
				this.firstTileColorChooserComboBoxRenderer.changeColor(color);
				this.firstTileColor = color;
				// this.firstGradientColor = null;
				// this.secondGradientColor = null;
				changeTiledImages = true;
			}
		}
		this.firstTileColorComboBox.setSelectedIndex(0);
		if (changeTiledImages) {
			this.resetTiledImages();
		}
	}

	void secondTileColorComboBox_actionPerformed(ActionEvent e) {
		int index = this.secondTileColorComboBox.getSelectedIndex();
		boolean changeTiledImages = false;
		if (index == 1) {
			Color refColor = this.secondTileColor;
			if (refColor == null) {
				refColor = Color.white;
			}
			Color color = JColorChooser.showDialog(this, "��", refColor);
			if (color != null) {
				this.secondTileColorChooserComboBoxRenderer.changeColor(color);
				this.secondTileColor = color;
				// this.firstGradientColor = null;
				// this.secondGradientColor = null;
				changeTiledImages = true;
			}
		}
		this.secondTileColorComboBox.setSelectedIndex(0);
		if (changeTiledImages) {
			this.resetTiledImages();
		}
	}

	public void resetTiledImages() {
		this.tileTB_01.setIcon(null);
		this.tileTB_02.setIcon(null);
		this.tileTB_03.setIcon(null);
		this.tileTB_04.setIcon(null);
		this.tileTB_05.setIcon(null);
		this.tileTB_06.setIcon(null);
		this.tileTB_07.setIcon(null);
		this.tileTB_08.setIcon(null);
		this.tileTB_09.setIcon(null);
		this.tileTB_10.setIcon(null);
		this.tileTB_11.setIcon(null);
		this.tileTB_12.setIcon(null);
		this.tileTB_13.setIcon(null);
		this.tileTB_14.setIcon(null);
		this.tileTB_15.setIcon(null);
		this.tileTB_16.setIcon(null);
		this.tileTB_17.setIcon(null);
		this.tileTB_18.setIcon(null);
		this.tileTB_19.setIcon(null);
		this.tileTB_20.setIcon(null);
		this.tileTB_21.setIcon(null);
		this.tileTB_22.setIcon(null);
		this.tileTB_23.setIcon(null);
		this.tileTB_24.setIcon(null);
		this.tileTB_25.setIcon(null);
		this.tileTB_26.setIcon(null);
		this.tileTB_27.setIcon(null);
		this.tileTB_28.setIcon(null);
		this.tileTB_29.setIcon(null);
		this.tileTB_30.setIcon(null);
		this.tileTB_31.setIcon(null);
		this.tileTB_32.setIcon(null);
		this.tileTB_33.setIcon(null);
		this.tileTB_34.setIcon(null);
		this.tileTB_35.setIcon(null);
		this.tileTB_36.setIcon(null);
		this.tileTB_37.setIcon(null);
		this.tileTB_38.setIcon(null);
		this.tileTB_39.setIcon(null);
		this.tileTB_40.setIcon(null);
		this.tileTB_41.setIcon(null);
		this.tileTB_42.setIcon(null);
		this.tileTB_43.setIcon(null);
		this.tileTB_44.setIcon(null);
		this.tileTB_45.setIcon(null);
		this.tileTB_46.setIcon(null);
		this.tileTB_47.setIcon(null);
		this.tileTB_48.setIcon(null);

		new Thread() {
			@Override
			public void run() {
				tileTBsPanel.repaint();
			}
		}.start();
	}

	@Override
	public void setVisible(boolean b) {

		if (b) {
			if (this.graphicEffect == null) {
				this.graphicEffect = new GraphicEffect();
			}
			if (this.firstTileColor != null) {
				this.firstTileColorChooserComboBoxRenderer.changeColor(this.firstTileColor);
				// this.firstTileColorComboBox.repaint();
			}
			if (this.secondTileColor != null) {
				this.secondTileColorChooserComboBoxRenderer.changeColor(this.secondTileColor);
				// this.secondTileColorComboBox.repaint();
			}
		}
		super.setVisible(b);
	}

	public GraphicEffect getGrahicEffect() {
		return this.graphicEffect;
	}

	void veryfyButton_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	void cancelButton_actionPerformed(ActionEvent e) {
		this.graphicEffect = null;
		this.setVisible(false);
	}

	public void setGraphicEffect(GraphicEffect graphicEffect) {
		this.graphicEffect = graphicEffect;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		this.showSelectedFillEffect();
	}

	public static GraphicEffect getNewLineTexture(GraphicEffect refFillEffect) {

		if (EDITOR == null) {
			EDITOR = new WordArtLineTextureEditor();
		}
		if (refFillEffect != null) {
			EDITOR.setGraphicEffect(refFillEffect.create());
		} else {
			EDITOR.setGraphicEffect(new GraphicEffect());
		}

		EDITOR.setVisible(true);

		return EDITOR.getGrahicEffect().create();
	}

	public static WordArtLineTextureEditor EDITOR = new WordArtLineTextureEditor();

}