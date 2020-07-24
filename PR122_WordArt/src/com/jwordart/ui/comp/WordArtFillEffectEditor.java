package com.jwordart.ui.comp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.*;

import com.jwordart.model.fillEffect.GraphicEffect;
import com.jwordart.ui.renderer.FillColorComboBoxRenderer;
import com.jwordart.ui.resource.Resource_WordArt;
import com.jwordart.util.*;

import java.io.File;
import java.awt.geom.*;
import java.awt.image.*;

public class WordArtFillEffectEditor extends JDialog {

	private GraphicEffect graphicEffect;
	private GraphicEffect[] fourGradient = new GraphicEffect[4];
	private Image fillEffectImage = null;
	private Image selectedPictureImage = null;
	private boolean neverShown = true;

	private Color firstTileColor = Color.white;
	private Color secondTileColor = Color.black;

	private long lastActionEventTime = System.currentTimeMillis();

	private FillColorComboBoxRenderer firstTileColorChooserComboBoxRenderer = new FillColorComboBoxRenderer();
	private FillColorComboBoxRenderer secondTileColorChooserComboBoxRenderer = new FillColorComboBoxRenderer();
	private FillColorComboBoxRenderer firstGradientColorChooserComboBoxRenderer = new FillColorComboBoxRenderer();
	private FillColorComboBoxRenderer secondGradientColorChooserComboBoxRenderer = new FillColorComboBoxRenderer();

	private JRadioButton prePanelRadioButton = null, preGradientTypeRadioButton = null;

	private int gradientType = GraphicEffect.HORIZONTAL;

	private JPanel selectedGradientInstancePanel;
	private int selectedGradientIndex = -1;

	private JTiledToggleButton selectedTiledToggleButton = null;
	private int[][][] predefinedColorIndexes = { { { 23, 7, 141 }, { 250, 0, 206 } }, { { 35, 11, 91 }, { 238, 140, 0 } },
			{ { 39, 7, 141 }, { 181, 40, 254 } }, { { 128, 203, 246 }, { 247, 228, 248 } },
			{ { 17, 255, 185 }, { 77, 159, 251 } }, { { 240, 255, 5 }, { 250, 47, 0 } }, { { 223, 246, 218 }, { 54, 141, 7 } },
			{ { 245, 233, 209 }, { 234, 245, 209 } }, { { 244, 202, 152 }, { 189, 134, 3 } },
			{ { 176, 178, 4 }, { 243, 244, 164 } }, { { 141, 145, 149 }, { 255, 255, 255 } }, { { 2, 2, 202 }, { 5, 64, 255 } } };
	// End of data.

	JPanel contentPane;
	BorderLayout borderLayout1 = new BorderLayout();
	JPanel jPanel1 = new JPanel();
	JPanel jPanel2 = new JPanel();
	JPanel jPanel3 = new JPanel();
	JTabbedPane jTabbedPane1 = new JTabbedPane();
	JPanel variancePanel = new JPanel();
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
	BorderLayout borderLayout5 = new BorderLayout();
	JPanel jPanel12 = new JPanel();
	JPanel jPanel13 = new JPanel();
	JPanel jPanel14 = new JPanel();
	Border border1;
	TitledBorder titledBorder1;
	Border border2;
	TitledBorder titledBorder2;
	BorderLayout borderLayout6 = new BorderLayout();
	JPanel jPanel15 = new JPanel();
	JPanel jPanel16 = new JPanel();
	JPanel fourGradientInstacePanel = new JPanel();
	Border border3;
	TitledBorder titledBorder3;
	Border border4;
	TitledBorder titledBorder4;
	Border border5;
	JRadioButton centerTypeButton = new JRadioButton();
	JRadioButton cornerTypeButton = new JRadioButton();
	JRadioButton leftDiagonalTypeButton = new JRadioButton();
	JRadioButton rightDiagonalTypeButton = new JRadioButton();
	JRadioButton verticalTypeButton = new JRadioButton();
	GridLayout gridLayout1 = new GridLayout();
	JRadioButton horizontalTypeButton = new JRadioButton();
	Border border6;
	BorderLayout borderLayout7 = new BorderLayout();
	JPanel jPanel18 = new JPanel();
	JPanel colorChoicePanel = new JPanel();
	JRadioButton oneColorRadioButton = new JRadioButton();
	JRadioButton twoColorRadioButton = new JRadioButton();
	JRadioButton preDefinedColorRadioButton = new JRadioButton();
	FlowLayout flowLayout1 = new FlowLayout();
	Border border7;
	GridLayout gridLayout2 = new GridLayout();
	JPanel threeGradientPanel = new JPanel();
	JPanel fourGradientPanel = new JPanel();
	JPanel twoGradientPanel = new JPanel();
	JPanel oneGradientPanel = new JPanel();
	Border border8;
	Border border9;
	CardLayout colorChoiceLayout = new CardLayout();
	JPanel oneColorPanle = new JPanel();
	JPanel twoColorPanel = new JPanel();
	JPanel preDefinedColorPanel = new JPanel();
	JPanel jilGamPanel = new JPanel();
	JPanel muniPanel = new JPanel();
	JPanel grimPanel = new JPanel();
	BorderLayout borderLayout8 = new BorderLayout();
	JPanel jPanel4 = new JPanel();
	JPanel jPanel19 = new JPanel();
	JPanel jPanel24 = new JPanel();
	FlowLayout flowLayout3 = new FlowLayout();
	JButton jButton3 = new JButton();
	Border border10;
	JTextField textureFileNameTF = new JTextField();
	FlowLayout flowLayout4 = new FlowLayout();
	JButton jButton4 = new JButton();
	Border border11;
	BorderLayout borderLayout9 = new BorderLayout();
	JScrollPane textureImagesScrollPane = new JScrollPane();
	JPanel jPanel25 = new JPanel();
	JToggleButton textureTB_01 = new JToggleButton();
	Border border12;
	GridLayout gridLayout3 = new GridLayout();
	JToggleButton textureTB_25 = new JToggleButton();
	JToggleButton textureTB_24 = new JToggleButton();
	JToggleButton textureTB_23 = new JToggleButton();
	JToggleButton textureTB_22 = new JToggleButton();
	JToggleButton textureTB_21 = new JToggleButton();
	JToggleButton textureTB_20 = new JToggleButton();
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
	JToggleButton textureTB_09 = new JToggleButton();
	JToggleButton textureTB_08 = new JToggleButton();
	JToggleButton textureTB_07 = new JToggleButton();
	JToggleButton textureTB_06 = new JToggleButton();
	JToggleButton textureTB_05 = new JToggleButton();
	JToggleButton textureTB_04 = new JToggleButton();
	JToggleButton textureTB_03 = new JToggleButton();
	JToggleButton textureTB_02 = new JToggleButton();
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
					WordArtFillEffectEditor.this.showSelectedFillEffect();
				}
			}.start();
		}
	};
	BorderLayout borderLayout11 = new BorderLayout();
	JButton jButton5 = new JButton();
	JButton jButton6 = new JButton();
	FlowLayout flowLayout5 = new FlowLayout();
	FlowLayout flowLayout6 = new FlowLayout();
	JTextField imageFileNameTF = new JTextField();
	JPanel jPanel10 = new JPanel();
	JPanel jPanel27 = new JPanel();
	BorderLayout borderLayout12 = new BorderLayout();
	JPanel jPanel110 = new JPanel();
	JPanel imageViewerPanel = new JPanel() {
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			new Thread() {
				@Override
				public void run() {
					SystemUtil.waitForSeconds(0.5);
					WordArtFillEffectEditor.this.showSelectedPictureImage();
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
	JTextField muniFileNameTF = new JTextField();
	FlowLayout flowLayout8 = new FlowLayout();
	Border border17;
	JPanel jPanel30 = new JPanel();
	Border border18;
	Border border19;
	GridLayout gridLayout4 = new GridLayout();
	JComboBox secondTileColorComboBox = new JComboBox(new String[] { "", " 다른 색 " });
	JComboBox firstTileColorComboBox = new JComboBox(new String[] { "", " 다른 색 " });
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
					WordArtFillEffectEditor.this.selectedTiledToggleButton = JTiledToggleButton.this;
					JTiledToggleButton.this.paint(JTiledToggleButton.this.getGraphics());
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

			// 넌 타일드 이미지 아이콘을 변경할 경우.
			if (nonTiledImageIcon == null) {
				if (orgNonTiledImageIcon != null) {
					Frame f = new Frame();
					f.addNotify();
					int ow = orgNonTiledImageIcon.getImage().getWidth(f), oh = orgNonTiledImageIcon.getImage().getHeight(f);
					BufferedImage image = WordArtFillEffectEditor.this.craeteTexturePaintedTiledImage(ow, oh,
							orgNonTiledImageIcon);
					processedTileImage = image;
					this.nonTiledImageIcon = orgNonTiledImageIcon;
				} else {
					super.paint(g);
					return;
				}
			}
			// 끝. 넌 타일드 이미지 아이콘 변경.

			int w = super.getWidth(), h = super.getHeight();

			if (w > 0 && h > 0) {
			} else {
				super.paint(g);
				return;
			}

			// 타일드 이미지 아이콘 세팅.
			if (this.neverShown) {
				BufferedImage tiledImage = WordArtFillEffectEditor.this.craeteTexturePaintedTiledImage(w, h, nonTiledImageIcon);
				super.setIcon(new ImageIcon(tiledImage));
				this.neverShown = false;
			}
			// 끝. 타일드 이미지 아이콘 세팅.

			if (selectedTiledToggleButton == this && this.processedTileImage != null) {
				WordArtFillEffectEditor.this.fillEffectImage = this.processedTileImage;
				WordArtFillEffectEditor.this.showSelectedFillEffect();
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
	BorderLayout borderLayout15 = new BorderLayout();
	JPanel jPanel28 = new JPanel();
	JPanel jPanel31 = new JPanel();
	JPanel jPanel32 = new JPanel();
	BorderLayout borderLayout16 = new BorderLayout();
	BorderLayout borderLayout17 = new BorderLayout();
	JLabel jLabel3 = new JLabel();
	JComboBox firstGradientColorChooserComboBox = new JComboBox(new String[] { "", "다른 색" });
	JScrollBar secondGradientColorBrightnessScrollBar = new JScrollBar();
	JLabel jLabel4 = new JLabel();
	BorderLayout borderLayout18 = new BorderLayout();
	JPanel jPanel33 = new JPanel();
	JPanel jPanel34 = new JPanel();
	BorderLayout borderLayout19 = new BorderLayout();
	BorderLayout borderLayout20 = new BorderLayout();
	JLabel jLabel5 = new JLabel();
	JComboBox anotherFirstGradientColorChooserComboBox = new JComboBox(new String[] { "", "다른 색" });
	JLabel jLabel6 = new JLabel();
	JComboBox secondGradientColorChooserComboBox = new JComboBox(new String[] { "", "다른 색" });
	JPanel jPanel17 = new JPanel();
	BorderLayout borderLayout21 = new BorderLayout();
	BorderLayout borderLayout22 = new BorderLayout();
	JLabel jLabel7 = new JLabel();
	JComboBox preDefinedColorComboBox = new JComboBox(new String[] { "이른 해질녁", "늦은 해질녁", "밤의 어둠", "새벽", "해양", "불", "이끼", "양피지",
			"마호가니", "금색", "은색", "사파이어" });

	// Construct the frame
	protected WordArtFillEffectEditor() {

		this(new GraphicEffect());
	}

	protected WordArtFillEffectEditor(GraphicEffect graphicEffect) {

		this.graphicEffect = graphicEffect;

		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			this.jbInit();

			this.firstTileColorComboBox.setRenderer(this.firstTileColorChooserComboBoxRenderer);
			this.secondTileColorComboBox.setRenderer(this.secondTileColorChooserComboBoxRenderer);
			this.secondTileColorChooserComboBoxRenderer.changeColor(this.firstTileColor);
			this.firstGradientColorChooserComboBox.setRenderer(this.firstGradientColorChooserComboBoxRenderer);
			this.anotherFirstGradientColorChooserComboBox.setRenderer(this.firstGradientColorChooserComboBoxRenderer);
			this.secondGradientColorChooserComboBox.setRenderer(this.secondGradientColorChooserComboBoxRenderer);
			this.secondGradientColorChooserComboBoxRenderer.changeColor(Color.black);
			this.prePanelRadioButton = this.oneColorRadioButton;
			this.preGradientTypeRadioButton = this.horizontalTypeButton;
			this.selectedGradientInstancePanel = this.oneGradientPanel;

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

	public static BufferedImage getImageChangedColor(BufferedImage image, Color firstColor, Color secondColor) {

		int w = image.getWidth(), h = image.getHeight();

		BufferedImage processedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		int srcPixel, dstPixel, whitePixel = Color.white.getRGB(), firstColorPixel = firstColor.getRGB(), secondColorPixel = secondColor
				.getRGB();

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				srcPixel = image.getRGB(x, y);
				if (srcPixel == whitePixel) {
					dstPixel = firstColorPixel;
				} else {
					dstPixel = secondColorPixel;
				}
				processedImage.setRGB(x, y, dstPixel);
			}
		}

		return processedImage;
	}

	BufferedImage craeteTexturePaintedTiledImage(int w, int h, ImageIcon imageIcon) {
		// 기준 버퍼드 이미지 생성
		Image refImage = imageIcon.getImage();
		Frame f = new Frame();
		f.addNotify();
		int rw = refImage.getWidth(f), rh = refImage.getHeight(f);
		BufferedImage refBImage = new BufferedImage(rw, rh, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) refBImage.getGraphics();
		g2.drawImage(refImage, 0, 0, f);
		// 끝. 기준 버퍼드 이미지 생성

		BufferedImage processedImage = WordArtFillEffectEditor.getImageChangedColor(refBImage, this.firstTileColor,
				this.secondTileColor);

		// 타일드 버퍼드 이미지 생성
		BufferedImage tiledImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Rectangle2D tr = new Rectangle2D.Double(0, 0, rw, rh);
		TexturePaint tp = new TexturePaint(processedImage, tr);
		Rectangle2D rect = new Rectangle2D.Double(0, 0, w, h);
		g2 = (Graphics2D) tiledImage.getGraphics();
		g2.setPaint(tp);
		g2.fill(rect);
		// 끝. 타일드 버퍼드 이미지 생성

		return tiledImage;
	}

	// Component initialization
	private void jbInit() throws Exception {
		contentPane = (JPanel) this.getContentPane();
		border1 = new EtchedBorder(EtchedBorder.RAISED, Color.white, new Color(148, 145, 140));
		titledBorder1 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "색");
		border2 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
		titledBorder2 = new TitledBorder(border2, "유형");
		border3 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
		titledBorder3 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "유형");
		border4 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
		titledBorder4 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "적용(S)");
		border5 = new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140)), "색");
		border6 = BorderFactory.createCompoundBorder(titledBorder3, BorderFactory.createEmptyBorder(0, 5, 0, 0));
		border7 = BorderFactory.createEmptyBorder(3, 0, 0, 0);
		border8 = BorderFactory.createEtchedBorder(Color.white, new Color(148, 145, 140));
		border9 = BorderFactory.createCompoundBorder(new TitledBorder(BorderFactory.createEtchedBorder(Color.white, new Color(
				148, 145, 140)), "적용(S) "), BorderFactory.createEmptyBorder(4, 6, 6, 6));
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
		this.setTitle("채우기 효과");
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
		jLabel1.setText("예제:");
		jPanel11.setLayout(borderLayout4);
		veryfyButton.setBorder(BorderFactory.createRaisedBevelBorder());
		veryfyButton.setMaximumSize(new Dimension(1000, 1000));
		veryfyButton.setPreferredSize(new Dimension(70, 22));
		veryfyButton.setText("확인");
		veryfyButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.veryfyButton_actionPerformed(e);
			}
		});
		cancelButton.setBorder(BorderFactory.createRaisedBevelBorder());
		cancelButton.setPreferredSize(new Dimension(70, 22));
		cancelButton.setSelected(true);
		cancelButton.setText("취소");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.cancelButton_actionPerformed(e);
			}
		});
		flowLayout2.setAlignment(FlowLayout.LEFT);
		flowLayout2.setHgap(0);
		flowLayout2.setVgap(0);
		jLabel2.setBackground(Color.cyan);
		jLabel2.setPreferredSize(new Dimension(20, 5));
		jLabel2.setText(" ");
		variancePanel.setLayout(borderLayout5);
		jPanel12.setBackground(SystemColor.menu);
		jPanel12.setBorder(border5);
		jPanel12.setPreferredSize(new Dimension(10, 128));
		jPanel12.setLayout(borderLayout7);
		jPanel13.setBackground(SystemColor.menu);
		jPanel13.setPreferredSize(new Dimension(10, 145));
		jPanel13.setLayout(borderLayout6);
		jPanel14.setBackground(SystemColor.menu);
		jPanel15.setBackground(SystemColor.menu);
		jPanel15.setBorder(border6);
		jPanel15.setPreferredSize(new Dimension(142, 10));
		jPanel15.setLayout(gridLayout1);
		jPanel16.setBackground(SystemColor.menu);
		jPanel16.setPreferredSize(new Dimension(146, 10));
		fourGradientInstacePanel.setBackground(SystemColor.menu);
		fourGradientInstacePanel.setBorder(border9);
		fourGradientInstacePanel.setPreferredSize(new Dimension(142, 10));
		fourGradientInstacePanel.addComponentListener(new java.awt.event.ComponentAdapter() {

			@Override
			public void componentShown(ComponentEvent e) {
				WordArtFillEffectEditor.this.fourGradientInstacePanel_componentShown(e);
			}
		});
		fourGradientInstacePanel.setLayout(gridLayout2);
		centerTypeButton.setMnemonic('M');
		centerTypeButton.setText("가운데에서(M)");
		centerTypeButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.centerTypeButton_actionPerformed(e);
			}
		});
		cornerTypeButton.setToolTipText("");
		cornerTypeButton.setMnemonic('F');
		cornerTypeButton.setText("모서리에서(F)");
		cornerTypeButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.cornerTypeButton_actionPerformed(e);
			}
		});
		leftDiagonalTypeButton.setMnemonic('D');
		leftDiagonalTypeButton.setText("왼쪽 대각선(D)");
		leftDiagonalTypeButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.leftDiagonalTypeButton_actionPerformed(e);
			}
		});
		rightDiagonalTypeButton.setMnemonic('U');
		rightDiagonalTypeButton.setText("오른쪽 대각선(U)");
		rightDiagonalTypeButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.rightDiagonalTypeButton_actionPerformed(e);
			}
		});
		verticalTypeButton.setMnemonic('V');
		verticalTypeButton.setText("수직(V)");
		verticalTypeButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.verticalTypeButton_actionPerformed(e);
			}
		});
		gridLayout1.setRows(6);
		gridLayout1.setColumns(1);
		gridLayout1.setHgap(10);
		gridLayout1.setVgap(3);
		horizontalTypeButton.setSelected(true);
		horizontalTypeButton.setMnemonic('Z');
		horizontalTypeButton.setText("수평(Z)");
		horizontalTypeButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.horizontalTypeButton_actionPerformed(e);
			}
		});
		jPanel18.setBackground(SystemColor.menu);
		jPanel18.setBorder(border7);
		jPanel18.setPreferredSize(new Dimension(140, 10));
		jPanel18.setLayout(flowLayout1);
		colorChoicePanel.setBackground(Color.orange);
		colorChoicePanel.setPreferredSize(new Dimension(140, 10));
		colorChoicePanel.setLayout(colorChoiceLayout);
		oneColorRadioButton.setSelected(true);
		oneColorRadioButton.setMnemonic('O');
		oneColorRadioButton.setText("한 색(O)");
		oneColorRadioButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.oneColorRadioButton_actionPerformed(e);
			}
		});
		twoColorRadioButton.setMnemonic('T');
		twoColorRadioButton.setText("두 색(T)");
		twoColorRadioButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.twoColorRadioButton_actionPerformed(e);
			}
		});
		preDefinedColorRadioButton.setText("미리 설정된 색(R)");
		preDefinedColorRadioButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.preDefinedColorRadioButton_actionPerformed(e);
			}
		});
		flowLayout1.setAlignment(FlowLayout.LEFT);
		flowLayout1.setHgap(8);
		flowLayout1.setVgap(0);
		gridLayout2.setRows(2);
		gridLayout2.setColumns(2);
		gridLayout2.setHgap(3);
		gridLayout2.setVgap(3);
		oneGradientPanel.setBorder(border8);
		oneGradientPanel.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.oneGradientPanel_mousePressed(e);
			}
		});
		twoGradientPanel.setBorder(BorderFactory.createEtchedBorder());
		twoGradientPanel.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.twoGradientPanel_mousePressed(e);
			}
		});
		threeGradientPanel.setBorder(BorderFactory.createEtchedBorder());
		threeGradientPanel.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.threeGradientPanel_mousePressed(e);
			}
		});
		fourGradientPanel.setBorder(BorderFactory.createEtchedBorder());
		fourGradientPanel.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.fourGradientPanel_mousePressed(e);
			}
		});
		oneColorPanle.setBackground(Color.red);
		oneColorPanle.setLayout(borderLayout15);
		twoColorPanel.setBackground(Color.orange);
		twoColorPanel.setLayout(borderLayout18);
		preDefinedColorPanel.setBackground(SystemColor.menu);
		preDefinedColorPanel.setLayout(borderLayout21);
		jilGamPanel.setBackground(Color.red);
		jilGamPanel.setLayout(borderLayout8);
		muniPanel.setBackground(Color.red);
		muniPanel.setLayout(borderLayout13);
		grimPanel.setBackground(Color.red);
		grimPanel.setLayout(borderLayout11);
		jPanel4.setBackground(SystemColor.menu);
		jPanel4.setPreferredSize(new Dimension(10, 30));
		jPanel4.setLayout(flowLayout3);
		jPanel19.setBackground(SystemColor.menu);
		jPanel19.setPreferredSize(new Dimension(10, 100));
		jPanel19.setLayout(flowLayout4);
		flowLayout3.setAlignment(FlowLayout.LEFT);
		jButton3.setBorder(border10);
		jButton3.setMnemonic('T');
		jButton3.setText("질감(T):");
		textureFileNameTF.setBackground(SystemColor.menu);
		textureFileNameTF.setEnabled(false);
		textureFileNameTF.setBorder(border11);
		textureFileNameTF.setPreferredSize(new Dimension(275, 22));
		textureFileNameTF.setDisabledTextColor(Color.black);
		textureFileNameTF.setHorizontalAlignment(SwingConstants.CENTER);
		jButton4.setPreferredSize(new Dimension(111, 22));
		jButton4.setMnemonic('O');
		jButton4.setText("다른 질감(O)...");
		jButton4.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.jButton4_actionPerformed(e);
			}
		});
		flowLayout4.setAlignment(FlowLayout.RIGHT);
		jPanel24.setLayout(borderLayout9);
		jPanel25.setLayout(gridLayout3);
		textureTB_01.setIcon(Resource_WordArt.getIcon("wordart", "texture_01.gif"));
		textureTB_01.setPreferredSize(new Dimension(56, 40));
		textureTB_01.setBorder(border12);
		textureTB_01.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_01_mousePressed(e);
			}
		});
		textureTB_01.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.textureTB_01_actionPerformed(e);
			}
		});
		textureTB_25.setBorder(border12);
		textureTB_25.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_25_mousePressed(e);
			}
		});
		textureTB_25.setPreferredSize(new Dimension(56, 40));
		textureTB_25.setIcon(Resource_WordArt.getIcon("wordart", "texture_24.gif"));
		textureTB_24.setBorder(border12);
		textureTB_24.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_24_mousePressed(e);
			}
		});
		textureTB_24.setPreferredSize(new Dimension(56, 40));
		textureTB_24.setIcon(Resource_WordArt.getIcon("wordart", "texture_24.gif"));
		textureTB_23.setBorder(border12);
		textureTB_23.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_23_mousePressed(e);
			}
		});
		textureTB_23.setPreferredSize(new Dimension(56, 40));
		textureTB_23.setIcon(Resource_WordArt.getIcon("wordart", "texture_23.gif"));
		textureTB_22.setBorder(border12);
		textureTB_22.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_22_mousePressed(e);
			}
		});
		textureTB_22.setPreferredSize(new Dimension(56, 40));
		textureTB_22.setIcon(Resource_WordArt.getIcon("wordart", "texture_22.gif"));
		textureTB_21.setBorder(border12);
		textureTB_21.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_21_mousePressed(e);
			}
		});
		textureTB_21.setPreferredSize(new Dimension(56, 40));
		textureTB_21.setIcon(Resource_WordArt.getIcon("wordart", "texture_21.gif"));
		textureTB_20.setBorder(border12);
		textureTB_20.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_20_mousePressed(e);
			}
		});
		textureTB_20.setPreferredSize(new Dimension(56, 40));
		textureTB_20.setIcon(Resource_WordArt.getIcon("wordart", "texture_20.gif"));
		textureTB_19.setBorder(border12);
		textureTB_19.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_19_mousePressed(e);
			}
		});
		textureTB_19.setPreferredSize(new Dimension(56, 40));
		textureTB_19.setIcon(Resource_WordArt.getIcon("wordart", "texture_19.gif"));
		textureTB_18.setBorder(border12);
		textureTB_18.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_18_mousePressed(e);
			}
		});
		textureTB_18.setPreferredSize(new Dimension(56, 40));
		textureTB_18.setIcon(Resource_WordArt.getIcon("wordart", "texture_18.gif"));
		textureTB_17.setBorder(border12);
		textureTB_17.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_17_mousePressed(e);
			}
		});
		textureTB_17.setPreferredSize(new Dimension(56, 40));
		textureTB_17.setIcon(Resource_WordArt.getIcon("wordart", "texture_17.gif"));
		textureTB_16.setBorder(border12);
		textureTB_16.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_16_mousePressed(e);
			}
		});
		textureTB_16.setPreferredSize(new Dimension(56, 40));
		textureTB_16.setIcon(Resource_WordArt.getIcon("wordart", "texture_16.gif"));
		textureTB_15.setBorder(border12);
		textureTB_15.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_15_mousePressed(e);
			}
		});
		textureTB_15.setPreferredSize(new Dimension(56, 40));
		textureTB_15.setIcon(Resource_WordArt.getIcon("wordart", "texture_15.gif"));
		textureTB_14.setBorder(border12);
		textureTB_14.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_14_mousePressed(e);
			}
		});
		textureTB_14.setPreferredSize(new Dimension(56, 40));
		textureTB_14.setIcon(Resource_WordArt.getIcon("wordart", "texture_14.gif"));
		textureTB_13.setBorder(border12);
		textureTB_13.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_13_mousePressed(e);
			}
		});
		textureTB_13.setPreferredSize(new Dimension(56, 40));
		textureTB_13.setIcon(Resource_WordArt.getIcon("wordart", "texture_13.gif"));
		gridLayout3.setRows(7);
		gridLayout3.setColumns(4);
		gridLayout3.setHgap(2);
		gridLayout3.setVgap(2);
		textureTB_12.setIcon(Resource_WordArt.getIcon("wordart", "texture_12.gif"));
		textureTB_12.setPreferredSize(new Dimension(56, 40));
		textureTB_12.setBorder(border12);
		textureTB_12.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_12_mousePressed(e);
			}
		});
		textureTB_11.setIcon(Resource_WordArt.getIcon("wordart", "texture_11.gif"));
		textureTB_11.setPreferredSize(new Dimension(56, 40));
		textureTB_11.setBorder(border12);
		textureTB_11.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_11_mousePressed(e);
			}
		});
		textureTB_10.setIcon(Resource_WordArt.getIcon("wordart", "texture_10.gif"));
		textureTB_10.setPreferredSize(new Dimension(56, 40));
		textureTB_10.setBorder(border12);
		textureTB_10.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_10_mousePressed(e);
			}
		});
		textureTB_09.setIcon(Resource_WordArt.getIcon("wordart", "texture_09.gif"));
		textureTB_09.setPreferredSize(new Dimension(56, 40));
		textureTB_09.setBorder(border12);
		textureTB_09.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_09_mousePressed(e);
			}
		});
		textureTB_08.setIcon(Resource_WordArt.getIcon("wordart", "texture_08.gif"));
		textureTB_08.setPreferredSize(new Dimension(56, 40));
		textureTB_08.setBorder(border12);
		textureTB_08.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_08_mousePressed(e);
			}
		});
		textureTB_07.setIcon(Resource_WordArt.getIcon("wordart", "texture_07.gif"));
		textureTB_07.setPreferredSize(new Dimension(56, 40));
		textureTB_07.setBorder(border12);
		textureTB_07.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_07_mousePressed(e);
			}
		});
		textureTB_06.setIcon(Resource_WordArt.getIcon("wordart", "texture_06.gif"));
		textureTB_06.setPreferredSize(new Dimension(56, 40));
		textureTB_06.setBorder(border12);
		textureTB_06.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_06_mousePressed(e);
			}
		});
		textureTB_05.setIcon(Resource_WordArt.getIcon("wordart", "texture_05.gif"));
		textureTB_05.setPreferredSize(new Dimension(56, 40));
		textureTB_05.setBorder(border12);
		textureTB_05.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_05_mousePressed(e);
			}
		});
		textureTB_04.setIcon(Resource_WordArt.getIcon("wordart", "texture_04.gif"));
		textureTB_04.setPreferredSize(new Dimension(56, 40));
		textureTB_04.setBorder(border12);
		textureTB_04.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_04_mousePressed(e);
			}
		});
		textureTB_03.setBorder(border12);
		textureTB_03.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_03_mousePressed(e);
			}
		});
		textureTB_03.setPreferredSize(new Dimension(56, 40));
		textureTB_03.setIcon(Resource_WordArt.getIcon("wordart", "texture_03.gif"));
		textureTB_02.setBorder(border12);
		textureTB_02.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.textureTB_02_mousePressed(e);
			}
		});
		textureTB_02.setPreferredSize(new Dimension(56, 40));
		textureTB_02.setIcon(Resource_WordArt.getIcon("wordart", "texture_02.gif"));
		textureImagesScrollPane.setBorder(null);
		jButton5.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.jButton5_actionPerformed(e);
			}
		});
		jButton5.setText("그림 선택(L)...");
		jButton5.setMnemonic('L');
		jButton5.setPreferredSize(new Dimension(111, 22));
		jButton6.setText("그림:");
		jButton6.setMnemonic('T');
		jButton6.setBorder(border10);
		flowLayout5.setAlignment(FlowLayout.RIGHT);
		flowLayout6.setAlignment(FlowLayout.LEFT);
		imageFileNameTF.setBackground(SystemColor.menu);
		imageFileNameTF.setEnabled(false);
		imageFileNameTF.setBorder(border11);
		imageFileNameTF.setPreferredSize(new Dimension(275, 22));
		imageFileNameTF.setDisabledTextColor(Color.black);
		imageFileNameTF.setHorizontalAlignment(SwingConstants.CENTER);
		jPanel10.setBackground(SystemColor.menu);
		jPanel10.setPreferredSize(new Dimension(10, 30));
		jPanel10.setLayout(flowLayout6);
		jPanel27.setLayout(borderLayout12);
		jPanel110.setBackground(SystemColor.menu);
		jPanel110.setPreferredSize(new Dimension(10, 130));
		jPanel110.setLayout(flowLayout5);
		jPanel27.setBorder(border14);
		imageViewerPanel.setBorder(border15);
		jPanel24.setBorder(border16);
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
		jButton7.setText("무늬(T):");
		jButton7.setMnemonic('T');
		jButton7.setBorder(border10);
		flowLayout7.setAlignment(FlowLayout.LEFT);
		jPanel29.setBackground(SystemColor.menu);
		jPanel29.setBorder(border17);
		jPanel29.setPreferredSize(new Dimension(10, 35));
		jPanel29.setLayout(flowLayout8);
		muniFileNameTF.setHorizontalAlignment(SwingConstants.CENTER);
		muniFileNameTF.setPreferredSize(new Dimension(275, 22));
		muniFileNameTF.setDisabledTextColor(Color.black);
		muniFileNameTF.setBorder(border11);
		muniFileNameTF.setBackground(SystemColor.menu);
		muniFileNameTF.setEnabled(false);
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
		jButton8.setText("무늬색(F):");
		jButton9.setText("바탕색(B):");
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
		tileTB_48.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_48_mousePressed(e);
			}
		});
		tileTB_47.setBorder(border21);
		tileTB_47.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_47_mousePressed(e);
			}
		});
		tileTB_47.setIcon(Resource_WordArt.getIcon("wordart", "tile_47.gif"));
		tileTB_46.setBorder(border21);
		tileTB_46.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_46_mousePressed(e);
			}
		});
		tileTB_46.setIcon(Resource_WordArt.getIcon("wordart", "tile_46.gif"));
		tileTB_45.setBorder(border21);
		tileTB_45.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_45_mousePressed(e);
			}
		});
		tileTB_45.setIcon(Resource_WordArt.getIcon("wordart", "tile_45.gif"));
		tileTB_44.setBorder(border21);
		tileTB_44.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_44_mousePressed(e);
			}
		});
		tileTB_44.setIcon(Resource_WordArt.getIcon("wordart", "tile_44.gif"));
		tileTB_43.setBorder(border21);
		tileTB_43.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_43_mousePressed(e);
			}
		});
		tileTB_43.setIcon(Resource_WordArt.getIcon("wordart", "tile_43.gif"));
		tileTB_42.setBorder(border21);
		tileTB_42.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_42_mousePressed(e);
			}
		});
		tileTB_42.setIcon(Resource_WordArt.getIcon("wordart", "tile_42.gif"));
		tileTB_41.setBorder(border21);
		tileTB_41.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_41_mousePressed(e);
			}
		});
		tileTB_41.setIcon(Resource_WordArt.getIcon("wordart", "tile_41.gif"));
		tileTB_40.setBorder(border21);
		tileTB_40.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_40_mousePressed(e);
			}
		});
		tileTB_40.setIcon(Resource_WordArt.getIcon("wordart", "tile_40.gif"));
		tileTB_39.setBorder(border21);
		tileTB_39.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_39_mousePressed(e);
			}
		});
		tileTB_39.setIcon(Resource_WordArt.getIcon("wordart", "tile_39.gif"));
		tileTB_38.setBorder(border21);
		tileTB_38.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_38_mousePressed(e);
			}
		});
		tileTB_38.setIcon(Resource_WordArt.getIcon("wordart", "tile_38.gif"));
		tileTB_37.setBorder(border21);
		tileTB_37.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_37_mousePressed(e);
			}
		});
		tileTB_37.setIcon(Resource_WordArt.getIcon("wordart", "tile_37.gif"));
		tileTB_36.setBorder(border21);
		tileTB_36.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_36_mousePressed(e);
			}
		});
		tileTB_36.setIcon(Resource_WordArt.getIcon("wordart", "tile_36.gif"));
		tileTB_35.setBorder(border21);
		tileTB_35.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_35_mousePressed(e);
			}
		});
		tileTB_35.setIcon(Resource_WordArt.getIcon("wordart", "tile_35.gif"));
		tileTB_34.setBorder(border21);
		tileTB_34.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_34_mousePressed(e);
			}
		});
		tileTB_34.setIcon(Resource_WordArt.getIcon("wordart", "tile_34.gif"));
		tileTB_33.setBorder(border21);
		tileTB_33.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_33_mousePressed(e);
			}
		});
		tileTB_33.setIcon(Resource_WordArt.getIcon("wordart", "tile_33.gif"));
		tileTB_32.setBorder(border21);
		tileTB_32.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_32_mousePressed(e);
			}
		});
		tileTB_32.setIcon(Resource_WordArt.getIcon("wordart", "tile_32.gif"));
		tileTB_31.setBorder(border21);
		tileTB_31.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_31_mousePressed(e);
			}
		});
		tileTB_31.setIcon(Resource_WordArt.getIcon("wordart", "tile_31.gif"));
		tileTB_30.setBorder(border21);
		tileTB_30.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_30_mousePressed(e);
			}
		});
		tileTB_30.setIcon(Resource_WordArt.getIcon("wordart", "tile_30.gif"));
		tileTB_29.setBorder(border21);
		tileTB_29.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_29_mousePressed(e);
			}
		});
		tileTB_29.setIcon(Resource_WordArt.getIcon("wordart", "tile_29.gif"));
		tileTB_28.setBorder(border21);
		tileTB_28.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_28_mousePressed(e);
			}
		});
		tileTB_28.setIcon(Resource_WordArt.getIcon("wordart", "tile_28.gif"));
		tileTB_27.setBorder(border21);
		tileTB_27.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_27_mousePressed(e);
			}
		});
		tileTB_27.setIcon(Resource_WordArt.getIcon("wordart", "tile_27.gif"));
		tileTB_26.setBorder(border21);
		tileTB_26.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_26_mousePressed(e);
			}
		});
		tileTB_26.setIcon(Resource_WordArt.getIcon("wordart", "tile_26.gif"));
		tileTB_25.setBorder(border21);
		tileTB_25.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_25_mousePressed(e);
			}
		});
		tileTB_25.setIcon(Resource_WordArt.getIcon("wordart", "tile_25.gif"));
		tileTB_24.setBorder(border21);
		tileTB_24.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_24_mousePressed(e);
			}
		});
		tileTB_24.setIcon(Resource_WordArt.getIcon("wordart", "tile_24.gif"));
		tileTB_23.setBorder(border21);
		tileTB_23.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_23_mousePressed(e);
			}
		});
		tileTB_23.setIcon(Resource_WordArt.getIcon("wordart", "tile_23.gif"));
		tileTB_22.setBorder(border21);
		tileTB_22.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_22_mousePressed(e);
			}
		});
		tileTB_22.setIcon(Resource_WordArt.getIcon("wordart", "tile_22.gif"));
		tileTB_21.setBorder(border21);
		tileTB_21.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_21_mousePressed(e);
			}
		});
		tileTB_21.setIcon(Resource_WordArt.getIcon("wordart", "tile_21.gif"));
		tileTB_20.setBorder(border21);
		tileTB_20.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_20_mousePressed(e);
			}
		});
		tileTB_20.setIcon(Resource_WordArt.getIcon("wordart", "tile_20.gif"));
		tileTB_19.setBorder(border21);
		tileTB_19.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_19_mousePressed(e);
			}
		});
		tileTB_19.setIcon(Resource_WordArt.getIcon("wordart", "tile_19.gif"));
		tileTB_18.setBorder(border21);
		tileTB_18.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_18_mousePressed(e);
			}
		});
		tileTB_18.setIcon(Resource_WordArt.getIcon("wordart", "tile_18.gif"));
		tileTB_17.setBorder(border21);
		tileTB_17.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_17_mousePressed(e);
			}
		});
		tileTB_17.setIcon(Resource_WordArt.getIcon("wordart", "tile_17.gif"));
		tileTB_16.setBorder(border21);
		tileTB_16.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_16_mousePressed(e);
			}
		});
		tileTB_16.setIcon(Resource_WordArt.getIcon("wordart", "tile_16.gif"));
		tileTB_15.setBorder(border21);
		tileTB_15.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_15_mousePressed(e);
			}
		});
		tileTB_15.setIcon(Resource_WordArt.getIcon("wordart", "tile_15.gif"));
		tileTB_14.setBorder(border21);
		tileTB_14.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_14_mousePressed(e);
			}
		});
		tileTB_14.setIcon(Resource_WordArt.getIcon("wordart", "tile_14.gif"));
		tileTB_13.setBorder(border21);
		tileTB_13.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_13_mousePressed(e);
			}
		});
		tileTB_13.setIcon(Resource_WordArt.getIcon("wordart", "tile_13.gif"));
		tileTB_12.setBorder(border21);
		tileTB_12.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_12_mousePressed(e);
			}
		});
		tileTB_12.setIcon(Resource_WordArt.getIcon("wordart", "tile_12.gif"));
		tileTB_11.setBorder(border21);
		tileTB_11.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_11_mousePressed(e);
			}
		});
		tileTB_11.setIcon(Resource_WordArt.getIcon("wordart", "tile_11.gif"));
		tileTB_10.setBorder(border21);
		tileTB_10.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_10_mousePressed(e);
			}
		});
		tileTB_10.setIcon(Resource_WordArt.getIcon("wordart", "tile_10.gif"));
		tileTB_09.setBorder(border21);
		tileTB_09.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_09_mousePressed(e);
			}
		});
		tileTB_09.setIcon(Resource_WordArt.getIcon("wordart", "tile_09.gif"));
		tileTB_08.setBorder(border21);
		tileTB_08.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_08_mousePressed(e);
			}
		});
		tileTB_08.setIcon(Resource_WordArt.getIcon("wordart", "tile_08.gif"));
		tileTB_07.setBorder(border21);
		tileTB_07.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_07_mousePressed(e);
			}
		});
		tileTB_07.setIcon(Resource_WordArt.getIcon("wordart", "tile_07.gif"));
		tileTB_06.setBorder(border21);
		tileTB_06.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_06_mousePressed(e);
			}
		});
		tileTB_06.setIcon(Resource_WordArt.getIcon("wordart", "tile_06.gif"));
		tileTB_05.setBorder(border21);
		tileTB_05.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_05_mousePressed(e);
			}
		});
		tileTB_05.setIcon(Resource_WordArt.getIcon("wordart", "tile_05.gif"));
		tileTB_04.setBorder(border21);
		tileTB_04.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_04_mousePressed(e);
			}
		});
		tileTB_04.setIcon(Resource_WordArt.getIcon("wordart", "tile_04.gif"));
		tileTB_03.setBorder(border21);
		tileTB_03.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_03_mousePressed(e);
			}
		});
		tileTB_03.setIcon(Resource_WordArt.getIcon("wordart", "tile_03.gif"));
		tileTB_02.setBorder(border21);
		tileTB_02.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_02_mousePressed(e);
			}
		});
		tileTB_02.setIcon(Resource_WordArt.getIcon("wordart", "tile_02.gif"));
		tileTB_01.setBorder(border21);
		tileTB_01.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtFillEffectEditor.this.tileTB_01_mousePressed(e);
			}
		});
		tileTB_01.setIcon(Resource_WordArt.getIcon("wordart", "tile_01.gif"));
		firstTileColorComboBox.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.firstTileColorComboBox_actionPerformed(e);
			}
		});
		secondTileColorComboBox.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.secondTileColorComboBox_actionPerformed(e);
			}
		});
		jPanel28.setBackground(SystemColor.menu);
		jPanel28.setPreferredSize(new Dimension(10, 45));
		jPanel28.setLayout(borderLayout16);
		jPanel31.setBackground(SystemColor.menu);
		jPanel31.setMinimumSize(new Dimension(10, 10));
		jPanel31.setPreferredSize(new Dimension(10, 45));
		jPanel31.setLayout(borderLayout17);
		jLabel3.setBackground(SystemColor.menu);
		jLabel3.setHorizontalAlignment(SwingConstants.LEFT);
		jLabel3.setText("색 1:");
		secondGradientColorBrightnessScrollBar.setOrientation(JScrollBar.HORIZONTAL);
		secondGradientColorBrightnessScrollBar.setMaximum(270);
		secondGradientColorBrightnessScrollBar.setPreferredSize(new Dimension(48, 20));
		secondGradientColorBrightnessScrollBar.addAdjustmentListener(new java.awt.event.AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent e) {
				WordArtFillEffectEditor.this.secondGradientColorBrightnessScrollBar_adjustmentValueChanged(e);
			}
		});
		jLabel4.setBackground(SystemColor.menu);
		jLabel4.setText("어둡게                         밝게");
		firstGradientColorChooserComboBox.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.firstGradientColorChooserComboBox_actionPerformed(e);
			}
		});
		jPanel33.setBackground(SystemColor.menu);
		jPanel33.setPreferredSize(new Dimension(10, 50));
		jPanel33.setLayout(borderLayout19);
		jPanel34.setBackground(SystemColor.menu);
		jPanel34.setPreferredSize(new Dimension(10, 50));
		jPanel34.setLayout(borderLayout20);
		jLabel5.setPreferredSize(new Dimension(41, 24));
		jLabel5.setText("색 1:");
		anotherFirstGradientColorChooserComboBox.setPreferredSize(new Dimension(126, 20));
		anotherFirstGradientColorChooserComboBox.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.anotherFirstGradientColorChooserComboBox_actionPerformed(e);
			}
		});
		jLabel6.setText("색 2:");
		jLabel6.setPreferredSize(new Dimension(41, 24));
		secondGradientColorChooserComboBox.setPreferredSize(new Dimension(126, 20));
		secondGradientColorChooserComboBox.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtFillEffectEditor.this.secondGradientColorChooserComboBox_actionPerformed(e);
			}
		});
		jPanel17.setBackground(SystemColor.menu);
		jPanel17.setPreferredSize(new Dimension(10, 50));
		jPanel17.setLayout(borderLayout22);
		jLabel7.setBackground(SystemColor.menu);
		jLabel7.setPreferredSize(new Dimension(41, 24));
		jLabel7.setText("미리 지정한 색:");
		preDefinedColorComboBox.setMaximumRowCount(4);
		preDefinedColorComboBox.addItemListener(new java.awt.event.ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				WordArtFillEffectEditor.this.preDefinedColorComboBox_itemStateChanged(e);
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
		jTabbedPane1.add(variancePanel, " 변화 ");
		variancePanel.add(jPanel12, BorderLayout.NORTH);
		jPanel12.add(jPanel18, BorderLayout.WEST);
		jPanel18.add(oneColorRadioButton, null);
		jPanel18.add(twoColorRadioButton, null);
		jPanel18.add(preDefinedColorRadioButton, null);
		jPanel12.add(colorChoicePanel, BorderLayout.EAST);
		colorChoicePanel.add(oneColorPanle, "oneColorPanel");
		oneColorPanle.add(jPanel28, BorderLayout.NORTH);
		jPanel28.add(jLabel3, BorderLayout.NORTH);
		jPanel28.add(firstGradientColorChooserComboBox, BorderLayout.CENTER);
		oneColorPanle.add(jPanel31, BorderLayout.SOUTH);
		jPanel31.add(secondGradientColorBrightnessScrollBar, BorderLayout.NORTH);
		jPanel31.add(jLabel4, BorderLayout.SOUTH);
		oneColorPanle.add(jPanel32, BorderLayout.CENTER);
		colorChoicePanel.add(twoColorPanel, "twoColorPanel");
		twoColorPanel.add(jPanel33, BorderLayout.NORTH);
		jPanel33.add(jLabel5, BorderLayout.NORTH);
		jPanel33.add(anotherFirstGradientColorChooserComboBox, BorderLayout.CENTER);
		twoColorPanel.add(jPanel34, BorderLayout.SOUTH);
		jPanel34.add(jLabel6, BorderLayout.NORTH);
		jPanel34.add(secondGradientColorChooserComboBox, BorderLayout.CENTER);
		colorChoicePanel.add(preDefinedColorPanel, "preDefinedColorPanel");
		preDefinedColorPanel.add(jPanel17, BorderLayout.NORTH);
		jPanel17.add(jLabel7, BorderLayout.NORTH);
		jPanel17.add(preDefinedColorComboBox, BorderLayout.CENTER);
		variancePanel.add(jPanel13, BorderLayout.SOUTH);
		jPanel13.add(jPanel15, BorderLayout.WEST);
		jPanel15.add(horizontalTypeButton, null);
		jPanel15.add(verticalTypeButton, null);
		jPanel15.add(rightDiagonalTypeButton, null);
		jPanel15.add(leftDiagonalTypeButton, null);
		jPanel15.add(cornerTypeButton, null);
		jPanel15.add(centerTypeButton, null);
		jPanel13.add(jPanel16, BorderLayout.CENTER);
		jPanel13.add(fourGradientInstacePanel, BorderLayout.EAST);
		fourGradientInstacePanel.add(oneGradientPanel, null);
		fourGradientInstacePanel.add(twoGradientPanel, null);
		fourGradientInstacePanel.add(threeGradientPanel, null);
		fourGradientInstacePanel.add(fourGradientPanel, null);
		variancePanel.add(jPanel14, BorderLayout.CENTER);
		jTabbedPane1.add(jilGamPanel, " 질감 ");
		jilGamPanel.add(jPanel4, BorderLayout.NORTH);
		jPanel4.add(jButton3, null);
		jilGamPanel.add(jPanel19, BorderLayout.SOUTH);
		jPanel19.add(textureFileNameTF, null);
		jPanel19.add(jButton4, null);
		jilGamPanel.add(jPanel24, BorderLayout.CENTER);
		jPanel24.add(textureImagesScrollPane, BorderLayout.CENTER);
		textureImagesScrollPane.getViewport().add(jPanel25, null);
		jPanel25.add(textureTB_01, null);
		jPanel25.add(textureTB_02, null);
		jPanel25.add(textureTB_03, null);
		jPanel25.add(textureTB_04, null);
		jPanel25.add(textureTB_05, null);
		jPanel25.add(textureTB_06, null);
		jPanel25.add(textureTB_07, null);
		jPanel25.add(textureTB_08, null);
		jPanel25.add(textureTB_09, null);
		jPanel25.add(textureTB_10, null);
		jPanel25.add(textureTB_11, null);
		jPanel25.add(textureTB_12, null);
		jPanel25.add(textureTB_13, null);
		jPanel25.add(textureTB_14, null);
		jPanel25.add(textureTB_15, null);
		jPanel25.add(textureTB_16, null);
		jPanel25.add(textureTB_17, null);
		jPanel25.add(textureTB_18, null);
		jPanel25.add(textureTB_19, null);
		jPanel25.add(textureTB_20, null);
		jPanel25.add(textureTB_21, null);
		jPanel25.add(textureTB_22, null);
		jPanel25.add(textureTB_23, null);
		jPanel25.add(textureTB_24, null);
		jPanel25.add(textureTB_25, null);
		jTabbedPane1.add(muniPanel, " 무늬 ");
		muniPanel.add(jPanel26, BorderLayout.SOUTH);
		jPanel26.add(jPanel29, BorderLayout.NORTH);
		jPanel29.add(muniFileNameTF, null);
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
		jTabbedPane1.add(grimPanel, " 그림 ");
		grimPanel.add(jPanel10, BorderLayout.NORTH);
		jPanel10.add(jButton6, null);
		grimPanel.add(jPanel27, BorderLayout.CENTER);
		jPanel27.add(imageViewerPanel, BorderLayout.CENTER);
		grimPanel.add(jPanel110, BorderLayout.SOUTH);
		jPanel110.add(imageFileNameTF, null);
		jPanel110.add(jButton5, null);
	}

	void oneColorRadioButton_actionPerformed(ActionEvent e) {
		this.colorChoiceLayout.show(colorChoicePanel, "oneColorPanel");
		this.toggleRadioButton(e);
		this.changeSecondGradientColor(this.secondGradientColorBrightnessScrollBar.getValue());
		this.showFourGradientInstance();
	}

	void twoColorRadioButton_actionPerformed(ActionEvent e) {
		this.colorChoiceLayout.show(colorChoicePanel, "twoColorPanel");
		this.toggleRadioButton(e);
		this.showFourGradientInstance();
	}

	void preDefinedColorRadioButton_actionPerformed(ActionEvent e) {
		this.colorChoiceLayout.show(colorChoicePanel, "preDefinedColorPanel");
		this.toggleRadioButton(e);
	}

	void toggleRadioButton(ActionEvent e) {
		if (this.prePanelRadioButton != null) {
			this.prePanelRadioButton.setSelected(false);
		}
		this.prePanelRadioButton = (JRadioButton) e.getSource();
		this.prePanelRadioButton.setSelected(true);
	}

	void textureTB_01_actionPerformed(ActionEvent e) {
	}

	// 질감에서 다른 이미지의 질감을 선택할 경우.
	void jButton4_actionPerformed(ActionEvent e) {
		File file = this.getChoosedImageFile(" 질감 선택 ");
		if (file != null) {
			String fileName = file.getName();
			int index = fileName.lastIndexOf(".");
			fileName = fileName.substring(0, index);
			this.textureFileNameTF.setText(fileName);

			ImageIcon icon = new ImageIcon(file.getAbsolutePath());
			this.textureTB_25.setIcon(icon);
			java.awt.geom.Rectangle2D bounds = this.textureTB_25.getBounds();
			Point p = new Point((int) bounds.getX(), (int) bounds.getY());
			this.textureImagesScrollPane.getViewport().setViewPosition(p);

			// 선택된 이미지를 샘플 화면에 보여준다.
			this.fillEffectImage = icon.getImage();
			this.showSelectedFillEffect();
		}
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

		// 마우스의 감별 속도를 저하시킨다. 0.2초 이상으로.
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

	// 그림 선택 시에 그림을 보여주고 , 채우기 효과를 설정한다.
	void jButton5_actionPerformed(ActionEvent e) {
		File file = this.getChoosedImageFile(" 그림 선택 ");
		if (file != null) {
			// 이미지 화일을 선택한다.
			String fileName = file.getName();
			int index = fileName.lastIndexOf(".");
			fileName = fileName.substring(0, index);
			this.imageFileNameTF.setText(fileName);

			ImageIcon icon = new ImageIcon(file.getAbsolutePath());
			// 끝. 이미지 화일 선택.

			// 이미지 화일을 화면에 보여준다.
			Image image = icon.getImage();
			this.selectedPictureImage = image;
			this.showSelectedPictureImage();
			// 끝. 이미지 뷰어에 그리기.
			// 선택된 필 이펙 이미지를 이미지를 샘플 화면에 보여준다.
			this.fillEffectImage = image;
			this.showSelectedFillEffect();
			// 끝. 샘플 화면에 보여주기.
		}
	}

	void showSelectedPictureImage() {

		Image image = this.selectedPictureImage;

		if (image == null) {
			return;
		} else {
			// this.firstGradientColor = null;
			// this.secondGradientColor = null;
			this.graphicEffect.setFirstGradientColor(null);
			this.graphicEffect.setSecondGradientColor(null);
		}

		Frame f = new Frame();
		f.addNotify();
		// 그릴 이미지의 위치와 크기를 결정한다.
		double iw = image.getWidth(f), ih = image.getHeight(f);
		Insets insets = this.imageViewerPanel.getInsets();
		double cw = this.imageViewerPanel.getWidth() - insets.left - insets.right - 10, ch = this.imageViewerPanel.getHeight()
				- insets.top - insets.bottom - 10;
		Graphics g = this.imageViewerPanel.getGraphics();
		double gx = 0, gy = 0, gw = 0, gh = 0;

		if (iw > ih) {
			// 그림의 폭이 높이 보다 크면,
			// 그랙픽의 높이를 적은 쪽에 먼저 맞춘다.
			gh = ch;
			gw = iw * (gh / ih);
		} else {
			// 그림의 크기가 높이보다 크면,
			// 그래픽의 폭을 적은 쪽에 먼저 맞춘다.
			gw = ch;
			gh = ih * (gw / iw);
		}

		gx = (cw - gw) / 2 + insets.left + 5;
		gy = 0 + insets.top + 5;
		g.drawImage(image, (int) gx, (int) gy, (int) gw, (int) gh, f);
		// 끝. 이미지 크기와 위치 결정
		// 끝. 이미지 뷰어에 나타내기.

	}

	void selectTexture(MouseEvent e, String textureName) {
		JToggleButton tb = (JToggleButton) e.getSource();
		if (textureName != null) {
			this.textureFileNameTF.setText(textureName);
		}
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
		this.selectTexture(e, "인쇄용지");
	}

	void textureTB_02_mousePressed(MouseEvent e) {
		this.selectTexture(e, "재생지");
	}

	void textureTB_03_mousePressed(MouseEvent e) {
		this.selectTexture(e, "양피지");
	}

	void textureTB_04_mousePressed(MouseEvent e) {
		this.selectTexture(e, "편지지");
	}

	void textureTB_05_mousePressed(MouseEvent e) {
		this.selectTexture(e, "초록색 대리석");
	}

	void textureTB_06_mousePressed(MouseEvent e) {
		this.selectTexture(e, "흰색 대리석");
	}

	void textureTB_07_mousePressed(MouseEvent e) {
		this.selectTexture(e, "갈색 대리석");
	}

	void textureTB_08_mousePressed(MouseEvent e) {
		this.selectTexture(e, "화강암");
	}

	void textureTB_09_mousePressed(MouseEvent e) {
		this.selectTexture(e, "파랑색 박엽지");
	}

	void textureTB_10_mousePressed(MouseEvent e) {
		this.selectTexture(e, "분홍색 박엽지");
	}

	void textureTB_11_mousePressed(MouseEvent e) {
		this.selectTexture(e, "자주색 편물");
	}

	void textureTB_12_mousePressed(MouseEvent e) {
		this.selectTexture(e, "꽃다발");
	}

	void textureTB_13_mousePressed(MouseEvent e) {
		this.selectTexture(e, "파피루스");
	}

	void textureTB_14_mousePressed(MouseEvent e) {
		this.selectTexture(e, "캔버스");
	}

	void textureTB_15_mousePressed(MouseEvent e) {
		this.selectTexture(e, "데님");
	}

	void textureTB_16_mousePressed(MouseEvent e) {
		this.selectTexture(e, "짜서 만든 돗자리");
	}

	void textureTB_17_mousePressed(MouseEvent e) {
		this.selectTexture(e, "작은 물방울");
	}

	void textureTB_18_mousePressed(MouseEvent e) {
		this.selectTexture(e, "종이 가방");
	}

	void textureTB_19_mousePressed(MouseEvent e) {
		this.selectTexture(e, "화석어");
	}

	void textureTB_20_mousePressed(MouseEvent e) {
		this.selectTexture(e, "모래");
	}

	void textureTB_21_mousePressed(MouseEvent e) {
		this.selectTexture(e, "코르크");
	}

	void textureTB_22_mousePressed(MouseEvent e) {
		this.selectTexture(e, "월넛");
	}

	void textureTB_23_mousePressed(MouseEvent e) {
		this.selectTexture(e, "오크");
	}

	void textureTB_24_mousePressed(MouseEvent e) {
		this.selectTexture(e, "일반 목재");
	}

	void textureTB_25_mousePressed(MouseEvent e) {
		this.selectTexture(e, null);
	}

	void firstTileColorComboBox_actionPerformed(ActionEvent e) {
		int index = this.firstTileColorComboBox.getSelectedIndex();
		boolean changeTiledImages = false;
		if (index == 1) {
			Color refColor = this.firstTileColor;
			if (refColor == null) {
				refColor = Color.white;
			}
			Color color = JColorChooser.showDialog(this, "색", refColor);
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
			Color color = JColorChooser.showDialog(this, "색", refColor);
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
			this.setFillEffectDataAccordingToCurrentFillEffect();
		}

		super.setVisible(b);
	}

	public void setFillEffectDataAccordingToCurrentFillEffect() {
		if (this.firstTileColor != null) {
			this.firstTileColorChooserComboBoxRenderer.changeColor(this.firstTileColor);
			// this.firstTileColorComboBox.repaint();
		}
		if (this.secondTileColor != null) {
			this.secondTileColorChooserComboBoxRenderer.changeColor(this.secondTileColor);
			// this.secondTileColorComboBox.repaint();
		}

		if (this.graphicEffect == null) {
			return;
		}
		if (this.graphicEffect.getGradientPaint() == null) {
			return;
		}
		Color firstGradientColor = this.graphicEffect.getFirstGradientColor();
		Color secondGradientColor = this.graphicEffect.getSecondGradientColor();
		if (firstGradientColor != null) {
			this.firstGradientColorChooserComboBoxRenderer.changeColor(firstGradientColor);
		}
		if (secondGradientColor != null) {
			this.secondGradientColorChooserComboBoxRenderer.changeColor(secondGradientColor);
		}
		this.repaint();
	}

	public GraphicEffect getGraphicEffect() {
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
		this.setFillEffectDataAccordingToCurrentFillEffect();
	}

	void firstGradientColorChooserComboBox_actionPerformed(ActionEvent e) {
		int index = this.firstGradientColorChooserComboBox.getSelectedIndex();
		if (index == 1) {
			Color refColor = this.firstGradientColorChooserComboBoxRenderer.getFillColor();
			if (refColor == null) {
				refColor = Color.white;
			}
			Color color = JColorChooser.showDialog(this, "색", refColor);
			if (color != null) {
				this.firstGradientColorChooserComboBoxRenderer.changeColor(color);
			}
		}
		this.firstGradientColorChooserComboBox.setSelectedIndex(0);
	}

	void anotherFirstGradientColorChooserComboBox_actionPerformed(ActionEvent e) {
		int index = this.anotherFirstGradientColorChooserComboBox.getSelectedIndex();
		if (index == 1) {
			Color refColor = this.firstGradientColorChooserComboBoxRenderer.getFillColor();
			if (refColor == null) {
				refColor = Color.white;
			}
			Color color = JColorChooser.showDialog(this, "색", refColor);
			if (color != null) {
				this.firstGradientColorChooserComboBoxRenderer.changeColor(color);
			}
		}
		this.anotherFirstGradientColorChooserComboBox.setSelectedIndex(0);
	}

	void secondGradientColorChooserComboBox_actionPerformed(ActionEvent e) {
		int index = this.secondGradientColorChooserComboBox.getSelectedIndex();
		if (index == 1) {
			Color refColor = this.secondGradientColorChooserComboBoxRenderer.getFillColor();
			if (refColor == null) {
				refColor = Color.white;
			}
			Color color = JColorChooser.showDialog(this, "색", refColor);
			if (color != null) {
				this.secondGradientColorChooserComboBoxRenderer.changeColor(color);
			}
		}
		this.secondGradientColorChooserComboBox.setSelectedIndex(0);
	}

	void secondGradientColorBrightnessScrollBar_adjustmentValueChanged(AdjustmentEvent e) {
		this.changeSecondGradientColor(e.getValue());
		this.showFourGradientInstance();
	}

	public void changeSecondGradientColor(int r) {

		if (r < 0) {
			r = 0;
		}
		if (r > 255) {
			r = 255;
		}
		Color color = new Color(r, r, r);
		this.secondGradientColorChooserComboBoxRenderer.changeColor(color);
		this.secondGradientColorChooserComboBox.repaint();
	}

	public Color getFirstGradientColor() {

		if (this.firstGradientColorChooserComboBoxRenderer == null) {
			return Color.white;
		} else {
			return this.firstGradientColorChooserComboBoxRenderer.getFillColor();
		}
	}

	public Color getSecondGradientColor() {
		if (this.secondGradientColorChooserComboBoxRenderer == null) {
			return Color.black;
		} else {
			return this.secondGradientColorChooserComboBoxRenderer.getFillColor();
		}
	}

	void horizontalTypeButton_actionPerformed(ActionEvent e) {
		this.changeGradientType(e, GraphicEffect.HORIZONTAL);
	}

	void verticalTypeButton_actionPerformed(ActionEvent e) {
		this.changeGradientType(e, GraphicEffect.VERTICAL);
	}

	void rightDiagonalTypeButton_actionPerformed(ActionEvent e) {
		this.changeGradientType(e, GraphicEffect.RIGHT_DIAGONAL);
	}

	void leftDiagonalTypeButton_actionPerformed(ActionEvent e) {
		this.changeGradientType(e, GraphicEffect.LEFT_DIAGONAL);
	}

	void cornerTypeButton_actionPerformed(ActionEvent e) {
		this.changeGradientType(e, GraphicEffect.FROM_CORNER);
	}

	void centerTypeButton_actionPerformed(ActionEvent e) {
		this.changeGradientType(e, GraphicEffect.FROM_CENTER);
	}

	public void changeGradientType(ActionEvent e, int type) {
		this.gradientType = type;
		if (this.preGradientTypeRadioButton != null) {
			this.preGradientTypeRadioButton.setSelected(false);
		}
		this.preGradientTypeRadioButton = (JRadioButton) e.getSource();
		this.preGradientTypeRadioButton.setSelected(true);
		this.showFourGradientInstance();
	}

	public void showFourGradientInstance() {
		if (this.selectedGradientInstancePanel == null || !this.selectedGradientInstancePanel.isShowing()
				|| !this.selectedGradientInstancePanel.isVisible()) {
			return;
		}
		int gradientType = this.gradientType;
		Color firstGradientColor = this.getFirstGradientColor(), secondGradientColor = this.getSecondGradientColor();
		if (firstGradientColor == null) {
			firstGradientColor = Color.white;
		}
		if (secondGradientColor == null) {
			secondGradientColor = Color.black;
		}

		GraphicEffect fe = new GraphicEffect();
		fe.setFirstGradientColor(firstGradientColor);
		fe.setSecondGradientColor(secondGradientColor);
		fe.setCyclic(false);
		fe.setSymmetric(false);
		fe.setType(gradientType);
		this.fourGradient[0] = fe.create();
		fe.setSymmetric(true);
		fe.setCyclic(true);
		this.fourGradient[2] = fe.create();
		fe.setFirstGradientColor(secondGradientColor);
		fe.setSecondGradientColor(firstGradientColor);
		this.fourGradient[3] = fe.create();
		fe.setSymmetric(false);
		fe.setCyclic(false);
		this.fourGradient[1] = fe.create();

		if (gradientType == GraphicEffect.FROM_CORNER) {
			this.fourGradient[0].setDirection(GraphicEffect.DOWN);
			this.fourGradient[1].setDirection(GraphicEffect.UP);
			this.fourGradient[2].setDirection(GraphicEffect.UP);
			this.fourGradient[3].setDirection(GraphicEffect.DOWN);
		} else if (gradientType == GraphicEffect.FROM_CENTER) {
			this.fourGradient[2].setType(GraphicEffect.ROUND);
			this.fourGradient[3].setType(GraphicEffect.ROUND);
		}
		this.showGradientOnPanel(this.oneGradientPanel, this.fourGradient[0]);
		this.showGradientOnPanel(this.twoGradientPanel, this.fourGradient[1]);
		this.showGradientOnPanel(this.threeGradientPanel, this.fourGradient[2]);
		this.showGradientOnPanel(this.fourGradientPanel, this.fourGradient[3]);

		if (this.selectedGradientIndex < 0) {
			this.selectedGradientIndex = 0;
		}
		if (this.selectedGradientIndex >= 0 && this.selectedGradientIndex < this.fourGradient.length) {
			this.graphicEffect = this.fourGradient[this.selectedGradientIndex];
		}
		this.showSelectedFillEffect();
	}

	public void showGradientOnPanel(JPanel panel, GraphicEffect fe) {
		Rectangle2D rect = new Rectangle2D.Double(0, 0, panel.getBounds().getWidth(), panel.getBounds().getHeight());
		fe.setBounds(rect);
		Graphics2D g2 = (Graphics2D) panel.getGraphics();
		g2.setPaint(fe.getGradientPaint());
		g2.fill(rect);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		this.colorChoicePanel.validate();
		this.firstGradientColorChooserComboBox.validate();
		this.secondGradientColorChooserComboBox.validate();
		this.drawSelectedGradientPanelMark();
		this.showFourGradientInstance();
		this.showSelectedFillEffect();
	}

	public void drawSelectedGradientPanelMark() {
		if (this.selectedGradientInstancePanel == null || !this.selectedGradientInstancePanel.isShowing()
				|| !this.selectedGradientInstancePanel.isVisible()) {
			return;
		}
		Graphics2D g2 = (Graphics2D) this.fourGradientInstacePanel.getGraphics();
		Rectangle bounds = this.selectedGradientInstancePanel.getBounds();
		Rectangle2D rect = new Rectangle2D.Double(bounds.x - 1, bounds.y - 1, bounds.width + 2, bounds.height + 2);
		g2.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 2, 2 }, 0));
		g2.draw(rect);
	}

	void oneGradientPanel_mousePressed(MouseEvent e) {
		this.showSelectedGradientInstance(e, 0);
	}

	void twoGradientPanel_mousePressed(MouseEvent e) {
		this.showSelectedGradientInstance(e, 1);
	}

	void threeGradientPanel_mousePressed(MouseEvent e) {
		this.showSelectedGradientInstance(e, 2);
	}

	void fourGradientPanel_mousePressed(MouseEvent e) {
		this.showSelectedGradientInstance(e, 3);
	}

	public void showSelectedGradientInstance(MouseEvent e, int index) {
		if (index > this.fourGradient.length - 1 || index < 0) {

			return;
		}
		this.selectedGradientInstancePanel = (JPanel) e.getSource();
		this.selectedGradientIndex = index;
		this.paint(super.getGraphics());
		this.graphicEffect = this.fourGradient[index].create();
		this.fillEffectImage = null;
		this.showSelectedFillEffect();
	}

	void fourGradientInstacePanel_componentShown(ComponentEvent e) {
		this.showFourGradientInstance();
	}

	void tileTB_01_mousePressed(MouseEvent e) {
		this.setMuniFileName("5%");
	}

	void tileTB_02_mousePressed(MouseEvent e) {
		this.setMuniFileName("50%");
	}

	void tileTB_03_mousePressed(MouseEvent e) {
		this.setMuniFileName("밝은 역방향 사선");
	}

	void tileTB_04_mousePressed(MouseEvent e) {
		this.setMuniFileName("밝은 수직선");
	}

	void tileTB_05_mousePressed(MouseEvent e) {
		this.setMuniFileName("수평 역방향 사선");
	}

	void tileTB_06_mousePressed(MouseEvent e) {
		this.setMuniFileName("지그 재그");
	}

	void tileTB_07_mousePressed(MouseEvent e) {
		this.setMuniFileName("양방향 사선");
	}

	void tileTB_08_mousePressed(MouseEvent e) {
		this.setMuniFileName("좁은 괘선");
	}

	void tileTB_09_mousePressed(MouseEvent e) {
		this.setMuniFileName("10%");
	}

	void tileTB_10_mousePressed(MouseEvent e) {
		this.setMuniFileName("60%");
	}

	void tileTB_11_mousePressed(MouseEvent e) {
		this.setMuniFileName("밝은 정방향 사선");
	}

	void tileTB_12_mousePressed(MouseEvent e) {
		this.setMuniFileName("밝은 수평선");
	}

	void tileTB_13_mousePressed(MouseEvent e) {
		this.setMuniFileName("수평 정방향 사선");
	}

	void tileTB_14_mousePressed(MouseEvent e) {
		this.setMuniFileName("물결");
	}

	void tileTB_15_mousePressed(MouseEvent e) {
		this.setMuniFileName("점선 괘선");
	}

	void tileTB_16_mousePressed(MouseEvent e) {
		this.setMuniFileName("넓은 괘선");
	}

	void tileTB_17_mousePressed(MouseEvent e) {
		this.setMuniFileName("20%");
	}

	void tileTB_18_mousePressed(MouseEvent e) {
		this.setMuniFileName("70%");
	}

	void tileTB_19_mousePressed(MouseEvent e) {
		this.setMuniFileName("어두운 역방향 사선");
	}

	void tileTB_20_mousePressed(MouseEvent e) {
		this.setMuniFileName("좁은 수직선");
	}

	void tileTB_21_mousePressed(MouseEvent e) {
		this.setMuniFileName("점선 수평선");
	}

	void tileTB_22_mousePressed(MouseEvent e) {
		this.setMuniFileName("벽돌 사선 무늬");
	}

	void tileTB_23_mousePressed(MouseEvent e) {
		this.setMuniFileName("점선 다이아몬드");
	}

	void tileTB_24_mousePressed(MouseEvent e) {
		this.setMuniFileName("좁은 체크 보드");
	}

	void tileTB_25_mousePressed(MouseEvent e) {
		this.setMuniFileName("25%");
	}

	void tileTB_26_mousePressed(MouseEvent e) {
		this.setMuniFileName("75%");
	}

	void tileTB_27_mousePressed(MouseEvent e) {
		this.setMuniFileName("어두운 정방향 사선");
	}

	void tileTB_28_mousePressed(MouseEvent e) {
		this.setMuniFileName("좁은 수평선");
	}

	void tileTB_29_mousePressed(MouseEvent e) {
		this.setMuniFileName("점선 수직선");
	}

	void tileTB_30_mousePressed(MouseEvent e) {
		this.setMuniFileName("수평 벽돌 무늬");
	}

	void tileTB_31_mousePressed(MouseEvent e) {
		this.setMuniFileName("지붕 널");
	}

	void tileTB_32_mousePressed(MouseEvent e) {
		this.setMuniFileName("넓은 체크 보드");
	}

	void tileTB_33_mousePressed(MouseEvent e) {
		this.setMuniFileName("30%");
	}

	void tileTB_34_mousePressed(MouseEvent e) {
		this.setMuniFileName("80%");
	}

	void tileTB_35_mousePressed(MouseEvent e) {
		this.setMuniFileName("넓은 역방향 사선");
	}

	void tileTB_36_mousePressed(MouseEvent e) {
		this.setMuniFileName("어두운 수직선");
	}

	void tileTB_37_mousePressed(MouseEvent e) {
		this.setMuniFileName("작은 색종이 조각");
	}

	void tileTB_38_mousePressed(MouseEvent e) {
		this.setMuniFileName("평직");
	}

	void tileTB_39_mousePressed(MouseEvent e) {
		this.setMuniFileName("격자 울타리");
	}

	void tileTB_40_mousePressed(MouseEvent e) {
		this.setMuniFileName("체크 무늬");
	}

	void tileTB_41_mousePressed(MouseEvent e) {
		this.setMuniFileName("40%");
	}

	void tileTB_42_mousePressed(MouseEvent e) {
		this.setMuniFileName("90%");
	}

	void tileTB_43_mousePressed(MouseEvent e) {
		this.setMuniFileName("넓은 정방향 사선");
	}

	void tileTB_44_mousePressed(MouseEvent e) {
		this.setMuniFileName("어두운 수평선");
	}

	void tileTB_45_mousePressed(MouseEvent e) {
		this.setMuniFileName("색종이 조각");
	}

	void tileTB_46_mousePressed(MouseEvent e) {
		this.setMuniFileName("격자 무늬");
	}

	void tileTB_47_mousePressed(MouseEvent e) {
		this.setMuniFileName("타원 무늬");
	}

	void tileTB_48_mousePressed(MouseEvent e) {
		this.setMuniFileName("다이아 몬드");
	}

	public void setMuniFileName(String name) {
		if (name == null) {
			name = "";
		}
		this.muniFileNameTF.setText(name);
	}

	void preDefinedColorComboBox_itemStateChanged(ItemEvent e) {
		int index = this.preDefinedColorComboBox.getSelectedIndex();

		int[][] ci = this.predefinedColorIndexes[index]; // Color index
		Color firstColor = new Color(ci[0][0], ci[0][1], ci[0][2]), secondColor = new Color(ci[1][0], ci[1][1], ci[1][2]);
		this.firstGradientColorChooserComboBoxRenderer.changeColor(firstColor);
		this.secondGradientColorChooserComboBoxRenderer.changeColor(secondColor);
		this.showFourGradientInstance();
	}

	public static GraphicEffect getNewFillEffect(GraphicEffect refFillEffect) {
		if (EDITOR == null) {
			EDITOR = new WordArtFillEffectEditor();
		}
		if (refFillEffect != null) {
			EDITOR.setGraphicEffect(refFillEffect.create());
		}
		EDITOR.setModal(true);
		EDITOR.setVisible(true);
		GraphicEffect fe = EDITOR.getGraphicEffect();
		if (fe != null) {
			return fe.create();
		} else {
			return null;
		}
	}

	// Data
	public static WordArtFillEffectEditor EDITOR = new WordArtFillEffectEditor();

}