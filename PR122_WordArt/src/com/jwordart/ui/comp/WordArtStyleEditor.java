package com.jwordart.ui.comp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import com.jwordart.model.fillEffect.GraphicEffect;
import com.jwordart.model.wordart.WordArt;
import com.jwordart.model.wordart.WordArtGroupManager;
import com.jwordart.model.wordart.WordArtStyle;
import com.jwordart.ui.renderer.FillColorComboBoxRenderer;
import com.jwordart.ui.renderer.LineKindComboBoxRenderer;
import com.jwordart.ui.renderer.SteppedComboBoxUI;
import com.jwordart.util.UnitUtil_WordArt;
import com.jwordart.util.WinUtil;
import com.jwordart.util.WordArtUtil;

public class WordArtStyleEditor extends JDialog {
	// Data.
	public static WordArtStyleEditor wordArtStyleEditor = new WordArtStyleEditor();
	private boolean neverShown = true;
	private WordArtStyle wordArtStyle = new WordArtStyle();
	// 스크롤 바의 증감 여부를 알아내기 위해서 사용된다.
	private int preHeightScrollBarValue, preWidthScrollBarValue, preHeightRatioScrollBarValue, preWidthRatioScrollBarValue,
			preLocationXScrollBarValue, preLocationYScrollBarValue, preLineWidthScrollBarValue;
	// 가로 세로 크기의 증감 비율을 설정하기 위한 값.
	private double firstWidth = 100, firstHeight = 100;

	private Color chooserFillColor, chooserLineColor; // 채우기 및 라인 칼라 선택용 임시 색상
	// private boolean halfTransparentCheckBoxSelected = false;
	// End of data.

	// GUI Components.
	FillColorComboBoxRenderer fillColorComboBoxRenderer = new FillColorComboBoxRenderer();
	FillColorComboBoxRenderer lineColorComboBoxRenderer = new FillColorComboBoxRenderer();
	LineKindComboBoxRenderer lineKindComboBoxRenderer = new LineKindComboBoxRenderer();

	JPanel contentPane;
	BorderLayout borderLayout1 = new BorderLayout();
	JTabbedPane jTabbedPane1 = new JTabbedPane();
	JPanel colorAndLineTabPanel = new JPanel();
	JPanel fillPanel = new JPanel();
	BorderLayout borderLayout2 = new BorderLayout();
	JPanel arrowPanel = new JPanel();
	BorderLayout borderLayout5 = new BorderLayout();
	JPanel jPanel12 = new JPanel();
	BorderLayout borderLayout6 = new BorderLayout();
	JPanel jPanel13 = new JPanel();
	BorderLayout borderLayout7 = new BorderLayout();
	JPanel jPanel14 = new JPanel();
	JPanel jPanel15 = new JPanel();
	JLabel jLabel1 = new JLabel();
	BorderLayout borderLayout8 = new BorderLayout();
	JPanel jPanel19 = new JPanel();
	JPanel jPanel20 = new JPanel();
	JPanel jPanel21 = new JPanel();
	JPanel jPanel11 = new JPanel();
	JPanel jPanel22 = new JPanel();
	JLabel jLabel2 = new JLabel();
	BorderLayout borderLayout9 = new BorderLayout();
	JPanel colorPanel = new JPanel();
	BorderLayout borderLayout4 = new BorderLayout();
	JPanel jPanel110 = new JPanel();
	JPanel jPanel111 = new JPanel();
	JPanel jPanel112 = new JPanel();
	JPanel jPanel113 = new JPanel();
	JPanel jPanel114 = new JPanel();
	BorderLayout borderLayout12 = new BorderLayout();
	JPanel jPanel24 = new JPanel();
	BorderLayout borderLayout14 = new BorderLayout();
	JPanel jPanel26 = new JPanel();
	BorderLayout borderLayout15 = new BorderLayout();
	BorderLayout borderLayout16 = new BorderLayout();
	BorderLayout borderLayout17 = new BorderLayout();
	BorderLayout borderLayout18 = new BorderLayout();
	JLabel jLabel7 = new JLabel();
	JLabel jLabel8 = new JLabel();
	JPanel jPanel116 = new JPanel();
	JPanel jPanel117 = new JPanel();
	FlowLayout flowLayout1 = new FlowLayout();
	FlowLayout flowLayout2 = new FlowLayout();
	JPanel jPanel7 = new JPanel();
	JLabel jLabel10 = new JLabel();
	GridLayout gridLayout2 = new GridLayout();
	JPanel jPanel25 = new JPanel();
	JLabel jLabel9 = new JLabel();
	JLabel jLabel6 = new JLabel();
	JLabel jLabel5 = new JLabel();
	JComboBox jComboBox4 = new JComboBox();
	JComboBox lineKindComboBox = new JComboBox(new String[] { "a", "b", "c", "c", "e", "f", "g", "h" });
	JComboBox lineColorComboBox = new JComboBox(new String[] { "", "선 없음", "다른 색", "선 무뉘" });
	JPanel jPanel9 = new JPanel();
	BorderLayout borderLayout10 = new BorderLayout();
	JPanel jPanel10 = new JPanel();
	BorderLayout borderLayout11 = new BorderLayout();
	JCheckBox halfTransparentCheckBox = new JCheckBox();
	GridLayout gridLayout1 = new GridLayout();
	JPanel jPanel23 = new JPanel();
	JLabel jLabel4 = new JLabel();
	JLabel jLabel3 = new JLabel();
	JComboBox fillColorComboBox = new JComboBox(new String[] { "", "채우기 없음", "다른 색", "채우기 효과" });
	JPanel jPanel18 = new JPanel();
	JPanel jPanel17 = new JPanel();
	JPanel jPanel16 = new JPanel();
	JPanel jPanel27 = new JPanel();
	JTextField lineWidthTextField = new JTextField();
	JScrollBar lineWidthScrollBar = new JScrollBar();
	BorderLayout borderLayout13 = new BorderLayout();
	BorderLayout borderLayout19 = new BorderLayout();
	FlowLayout flowLayout3 = new FlowLayout();
	JButton verifyButton = new JButton();
	JButton cancelButton = new JButton();
	JLabel jLabel11 = new JLabel();
	BorderLayout borderLayout110 = new BorderLayout();
	BorderLayout borderLayout111 = new BorderLayout();
	BorderLayout borderLayout112 = new BorderLayout();
	BorderLayout borderLayout113 = new BorderLayout();
	BorderLayout borderLayout114 = new BorderLayout();
	BorderLayout borderLayout116 = new BorderLayout();
	JPanel jPanel118 = new JPanel();
	JPanel jPanel119 = new JPanel();
	FlowLayout flowLayout4 = new FlowLayout();
	JPanel jPanel115 = new JPanel();
	JPanel jPanel1110 = new JPanel();
	JPanel jPanel1111 = new JPanel();
	JPanel jPanel1112 = new JPanel();
	JPanel jPanel1113 = new JPanel();
	JPanel jPanel28 = new JPanel();
	GridLayout gridLayout3 = new GridLayout();
	JPanel jPanel210 = new JPanel();
	JPanel jPanel211 = new JPanel();
	JPanel jPanel212 = new JPanel();
	JLabel jLabel12 = new JLabel();
	JLabel jLabel13 = new JLabel();
	JLabel jLabel14 = new JLabel();
	JLabel jLabel15 = new JLabel();
	JLabel jLabel16 = new JLabel();
	JComboBox jComboBox5 = new JComboBox();
	JComboBox jComboBox6 = new JComboBox();
	JComboBox jComboBox7 = new JComboBox();
	JComboBox jComboBox8 = new JComboBox();
	GridLayout gridLayout5 = new GridLayout();
	GridLayout gridLayout6 = new GridLayout();
	JPanel jPanel120 = new JPanel();
	JPanel jPanel121 = new JPanel();
	JPanel jPanel122 = new JPanel();
	JPanel jPanel123 = new JPanel();
	JPanel jPanel124 = new JPanel();
	JPanel jPanel125 = new JPanel();
	JPanel jPanel126 = new JPanel();
	JPanel jPanel127 = new JPanel();
	JPanel jPanel128 = new JPanel();
	JPanel originalSizePanel = new JPanel();
	BorderLayout borderLayout115 = new BorderLayout();
	BorderLayout borderLayout118 = new BorderLayout();
	BorderLayout borderLayout119 = new BorderLayout();
	BorderLayout borderLayout1110 = new BorderLayout();
	BorderLayout borderLayout1111 = new BorderLayout();
	JTextField widthRatioTextField = new JTextField();
	BorderLayout borderLayout20 = new BorderLayout();
	BorderLayout borderLayout21 = new BorderLayout();
	BorderLayout borderLayout22 = new BorderLayout();
	BorderLayout borderLayout23 = new BorderLayout();
	BorderLayout borderLayout24 = new BorderLayout();
	BorderLayout borderLayout25 = new BorderLayout();
	BorderLayout borderLayout3 = new BorderLayout();
	JPanel jPanel1114 = new JPanel();
	FlowLayout flowLayout5 = new FlowLayout();
	JPanel jPanel1115 = new JPanel();
	JPanel jPanel1116 = new JPanel();
	JPanel jPanel29 = new JPanel();
	FlowLayout flowLayout6 = new FlowLayout();
	FlowLayout flowLayout7 = new FlowLayout();
	JPanel jPanel1117 = new JPanel();
	JPanel jPanel8 = new JPanel();
	JLabel jLabel19 = new JLabel();
	JLabel jLabel110 = new JLabel();
	JPanel sizeTabPane = new JPanel();
	JScrollBar widthRatioScrollBar = new JScrollBar();
	JPanel ratioPanel = new JPanel();
	JLabel jLabel21 = new JLabel();
	JLabel jLabel22 = new JLabel();
	JLabel jLabel23 = new JLabel();
	JLabel jLabel25 = new JLabel();
	JLabel jLabel26 = new JLabel();
	JLabel jLabel27 = new JLabel();
	JLabel jLabel28 = new JLabel();
	JPanel jPanel1120 = new JPanel();
	JPanel jPanel1121 = new JPanel();
	JPanel jPanel1122 = new JPanel();
	JPanel jPanel1123 = new JPanel();
	JPanel jPanel1124 = new JPanel();
	JPanel jPanel1125 = new JPanel();
	JPanel jPanel1126 = new JPanel();
	JPanel jPanel1127 = new JPanel();
	BorderLayout borderLayout120 = new BorderLayout();
	BorderLayout borderLayout121 = new BorderLayout();
	BorderLayout borderLayout122 = new BorderLayout();
	BorderLayout borderLayout123 = new BorderLayout();
	BorderLayout borderLayout124 = new BorderLayout();
	BorderLayout borderLayout125 = new BorderLayout();
	BorderLayout borderLayout126 = new BorderLayout();
	BorderLayout borderLayout127 = new BorderLayout();
	BorderLayout borderLayout128 = new BorderLayout();
	BorderLayout borderLayout129 = new BorderLayout();
	JPanel jPanel214 = new JPanel();
	JPanel sizeAndRotationPanel = new JPanel();
	JPanel jPanel215 = new JPanel();
	JPanel jPanel216 = new JPanel();
	JPanel jPanel217 = new JPanel();
	JPanel jPanel219 = new JPanel();
	JPanel jPanel220 = new JPanel();
	JPanel jPanel221 = new JPanel();
	JPanel jPanel222 = new JPanel();
	BorderLayout borderLayout1210 = new BorderLayout();
	JScrollBar rotationAngleScrollBar = new JScrollBar();
	JTextField rotationAngleTextField = new JTextField();
	JPanel jPanel2110 = new JPanel();
	JPanel jPanel1 = new JPanel();
	JLabel jLabel29 = new JLabel();
	BorderLayout borderLayout1211 = new BorderLayout();
	JScrollBar widthValueScrollBar = new JScrollBar();
	JTextField widthTextField = new JTextField();
	JPanel jPanel2111 = new JPanel();
	JLabel jLabel210 = new JLabel();
	BorderLayout borderLayout1212 = new BorderLayout();
	JScrollBar heightValueScrollBar = new JScrollBar();
	JTextField heightTextField = new JTextField();
	JPanel jPanel2112 = new JPanel();
	JLabel jLabel24 = new JLabel();
	BorderLayout borderLayout1213 = new BorderLayout();
	JScrollBar heightRatioScrollBar = new JScrollBar();
	JTextField heightRatioTextField = new JTextField();
	JPanel jPanel218 = new JPanel();
	JPanel jPanel2 = new JPanel();
	BorderLayout borderLayout26 = new BorderLayout();
	JPanel jPanel3 = new JPanel();
	JPanel jPanel4 = new JPanel();
	FlowLayout flowLayout8 = new FlowLayout();
	JCheckBox widthHeightRatioFixedCheckBox = new JCheckBox();
	JCheckBox jCheckBox3 = new JCheckBox();
	GridLayout gridLayout4 = new GridLayout();
	JLabel jLabel17 = new JLabel();
	JLabel jLabel18 = new JLabel();
	JPanel jPanel5 = new JPanel();
	JPanel jPanel6 = new JPanel();
	JButton jButton1 = new JButton();
	JPanel jPanel30 = new JPanel();
	BorderLayout borderLayout1214 = new BorderLayout();
	BorderLayout borderLayout1215 = new BorderLayout();
	BorderLayout borderLayout1223 = new BorderLayout();
	BorderLayout borderLayout1112 = new BorderLayout();
	BorderLayout borderLayout1113 = new BorderLayout();
	BorderLayout borderLayout1114 = new BorderLayout();
	BorderLayout borderLayout1115 = new BorderLayout();
	JLabel jLabel211 = new JLabel();
	BorderLayout borderLayout117 = new BorderLayout();
	JLabel jLabel212 = new JLabel();
	JTextField locationXTextField = new JTextField();
	JLabel jLabel213 = new JLabel();
	JPanel jPanel11213 = new JPanel();
	JLabel jLabel214 = new JLabel();
	JTextField locationYTextField = new JTextField();
	JLabel jLabel215 = new JLabel();
	JLabel jLabel216 = new JLabel();
	BorderLayout borderLayout27 = new BorderLayout();
	JPanel jPanel1118 = new JPanel();
	JPanel jPanel1119 = new JPanel();
	JPanel jPanel11110 = new JPanel();
	FlowLayout flowLayout11 = new FlowLayout();
	JPanel jPanel11111 = new JPanel();
	FlowLayout flowLayout12 = new FlowLayout();
	JLabel jLabel111 = new JLabel();
	BorderLayout borderLayout12111 = new BorderLayout();
	JPanel locationTabPane = new JPanel();
	BorderLayout borderLayout12113 = new BorderLayout();
	JPanel jPanel37 = new JPanel();
	JScrollBar locationXScrollBar = new JScrollBar();
	JScrollBar locationYScrollBar = new JScrollBar();
	JPanel jPanel129 = new JPanel();
	JPanel jPanel1210 = new JPanel();
	JPanel jPanel1211 = new JPanel();
	JPanel jPanel1212 = new JPanel();
	JPanel jPanel1213 = new JPanel();
	JPanel jPanel1214 = new JPanel();
	JPanel jPanel1215 = new JPanel();
	JPanel jPanel1216 = new JPanel();
	JPanel jPanel1217 = new JPanel();
	JPanel stringLocationPanel = new JPanel();
	JPanel jPanel2113 = new JPanel();
	JPanel jPanel2115 = new JPanel();
	BorderLayout borderLayout210 = new BorderLayout();
	BorderLayout borderLayout211 = new BorderLayout();
	BorderLayout borderLayout212 = new BorderLayout();
	BorderLayout borderLayout213 = new BorderLayout();
	BorderLayout borderLayout214 = new BorderLayout();
	JPanel locationPanel = new JPanel();
	JPanel jPanel223 = new JPanel();
	JPanel jPanel224 = new JPanel();
	JLabel jLabel114 = new JLabel();
	JPanel jPanel2116 = new JPanel();
	JPanel jPanel2119 = new JPanel();
	JPanel jPanel213 = new JPanel();
	GridLayout gridLayout8 = new GridLayout();
	BorderLayout borderLayout28 = new BorderLayout();
	JPanel jPanel31 = new JPanel();
	JCheckBox jCheckBox4 = new JCheckBox();
	FlowLayout flowLayout9 = new FlowLayout();
	JComboBox refereceFComboBox = new JComboBox(new String[] { "여백", "쪽", "단" });
	JComboBox referenceRComboBox = new JComboBox(new String[] { "여백", "쪽", "단락" });
	BorderLayout borderLayout29 = new BorderLayout();
	JPanel jPanel32 = new JPanel();
	JPanel jPanel33 = new JPanel();
	JPanel jPanel34 = new JPanel();
	JCheckBox jCheckBox5 = new JCheckBox();
	JCheckBox jCheckBox6 = new JCheckBox();
	FlowLayout flowLayout10 = new FlowLayout();
	JPanel jPanel35 = new JPanel();
	JPanel jPanel36 = new JPanel();

	// Construct the frame
	public WordArtStyleEditor() {
		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			this.jbInit();
			// Code by sbmoon.
			// set combo box renderers.
			// TODO fillColorComboBox 참조

			this.fillColorComboBox.setRenderer(this.fillColorComboBoxRenderer);
			this.lineColorComboBox.setRenderer(this.lineColorComboBoxRenderer);
			this.lineKindComboBox.setRenderer(this.lineKindComboBoxRenderer);
			this.lineKindComboBox.setUI(new SteppedComboBoxUI(30, 0));
			// End of setting combo box renderes.
			// set pre values to detect increasing or decreasing.
			this.preHeightScrollBarValue = this.heightRatioScrollBar.getValue();
			this.preWidthScrollBarValue = this.widthRatioScrollBar.getValue();
			this.preHeightRatioScrollBarValue = this.heightRatioScrollBar.getValue();
			this.preWidthRatioScrollBarValue = this.widthRatioScrollBar.getValue();
			this.preLocationXScrollBarValue = this.locationXScrollBar.getValue();
			this.preLocationYScrollBarValue = this.locationYScrollBar.getValue();
			this.preLineWidthScrollBarValue = this.lineWidthScrollBar.getValue();
			// end of setting pre values.
			// End of code by sbmoon.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Component initialization
	private void jbInit() throws Exception {
		contentPane = (JPanel) this.getContentPane();
		contentPane.setLayout(borderLayout1);
		this.setResizable(false);
		this.setSize(new Dimension(419, 349));
		this.setTitle("WordArt 서식");
		this.setModal(true);
		colorAndLineTabPanel.setLayout(borderLayout2);
		fillPanel.setBackground(Color.red);
		fillPanel.setMinimumSize(new Dimension(365, 50));
		fillPanel.setPreferredSize(new Dimension(10, 61));
		fillPanel.setLayout(borderLayout5);
		arrowPanel.setBackground(Color.orange);
		arrowPanel.setPreferredSize(new Dimension(10, 116));
		arrowPanel.setLayout(borderLayout19);
		jPanel12.setBackground(Color.pink);
		jPanel12.setPreferredSize(new Dimension(10, 18));
		jPanel12.setLayout(borderLayout6);
		jPanel13.setBackground(Color.pink);
		jPanel13.setPreferredSize(new Dimension(10, 15));
		jPanel13.setLayout(borderLayout7);
		jPanel14.setBackground(SystemColor.menu);
		jPanel14.setPreferredSize(new Dimension(5, 10));
		jLabel1.setBorder(BorderFactory.createEtchedBorder());
		jLabel1.setMinimumSize(new Dimension(0, 0));
		jLabel1.setPreferredSize(new Dimension(350, 2));
		jPanel15.setLayout(borderLayout8);
		jPanel19.setPreferredSize(new Dimension(10, 35));
		jPanel19.setLayout(flowLayout3);
		jPanel21.setMinimumSize(new Dimension(1, 10));
		jPanel21.setPreferredSize(new Dimension(6, 10));
		jPanel20.setMinimumSize(new Dimension(1, 10));
		jPanel20.setPreferredSize(new Dimension(6, 10));
		jPanel11.setBackground(SystemColor.menu);
		jPanel11.setPreferredSize(new Dimension(50, 10));
		jPanel11.setLayout(borderLayout9);
		jPanel22.setLayout(flowLayout2);
		jLabel2.setBackground(SystemColor.menu);
		jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel2.setText("채우기");
		jLabel2.setVerticalAlignment(SwingConstants.BOTTOM);
		jLabel2.setVerticalTextPosition(SwingConstants.BOTTOM);
		colorPanel.setLayout(borderLayout4);
		jPanel110.setLayout(borderLayout15);
		jPanel111.setBackground(SystemColor.menu);
		jPanel111.setPreferredSize(new Dimension(5, 10));
		jPanel112.setBackground(Color.pink);
		jPanel112.setPreferredSize(new Dimension(10, 15));
		jPanel112.setLayout(borderLayout16);
		jPanel113.setBackground(Color.pink);
		jPanel113.setPreferredSize(new Dimension(10, 18));
		jPanel113.setLayout(borderLayout17);
		jPanel114.setBackground(SystemColor.menu);
		jPanel114.setMinimumSize(new Dimension(1, 18));
		jPanel114.setPreferredSize(new Dimension(30, 10));
		jPanel114.setLayout(borderLayout14);
		jPanel24.setBackground(Color.red);
		jPanel24.setMinimumSize(new Dimension(365, 50));
		jPanel24.setPreferredSize(new Dimension(10, 65));
		jPanel24.setLayout(borderLayout18);
		jPanel26.setLayout(flowLayout1);
		jLabel7.setBackground(SystemColor.menu);
		jLabel7.setPreferredSize(new Dimension(10, 18));
		jLabel7.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel7.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel7.setText("선");
		jLabel8.setBorder(BorderFactory.createEtchedBorder());
		jLabel8.setMinimumSize(new Dimension(0, 0));
		jLabel8.setPreferredSize(new Dimension(375, 2));
		jPanel116.setBackground(SystemColor.menu);
		jPanel116.setPreferredSize(new Dimension(10, 16));
		jPanel117.setBackground(Color.pink);
		jPanel117.setPreferredSize(new Dimension(504, 100));
		jPanel117.setLayout(borderLayout12);
		jLabel10.setBackground(SystemColor.menu);
		jLabel10.setForeground(Color.gray);
		jLabel10.setPreferredSize(new Dimension(43, 18));
		jLabel10.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel10.setText("유형(S):          ");
		gridLayout2.setRows(2);
		gridLayout2.setColumns(4);
		gridLayout2.setVgap(7);
		jPanel25.setBackground(SystemColor.menu);
		jPanel25.setLayout(gridLayout2);
		jLabel9.setText("종류(D):    ");
		jLabel9.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel9.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel6.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel6.setText("색(O):        ");
		jLabel5.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel5.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel5.setText("두께(W):         ");
		jPanel9.setLayout(borderLayout10);
		halfTransparentCheckBox.setAlignmentX((float) 0.5);
		halfTransparentCheckBox.setMnemonic('T');
		halfTransparentCheckBox.setText("반투명(T)");
		halfTransparentCheckBox.setHorizontalAlignment(SwingConstants.CENTER);
		halfTransparentCheckBox.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStyleEditor.this.halfTransparentCheckBox_actionPerformed(e);
			}
		});
		gridLayout1.setColumns(4);
		jPanel23.setBackground(SystemColor.menu);
		jPanel23.setLayout(gridLayout1);
		jLabel4.setBackground(SystemColor.menu);
		jLabel4.setText(" ");
		jLabel3.setBackground(SystemColor.menu);
		jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel3.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel3.setText("색(C):        ");
		jPanel18.setBackground(SystemColor.menu);
		jPanel18.setPreferredSize(new Dimension(10, 12));
		jPanel17.setBackground(SystemColor.menu);
		jPanel17.setPreferredSize(new Dimension(10, 12));
		jPanel16.setBackground(Color.pink);
		jPanel16.setLayout(borderLayout11);
		lineWidthTextField.setText("1 pt");
		lineWidthTextField.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				WordArtStyleEditor.this.lineWidthTextField_mouseExited(e);
			}
		});
		lineWidthTextField.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStyleEditor.this.lineWidthTextField_actionPerformed(e);
			}
		});
		jPanel27.setLayout(borderLayout13);
		lineWidthScrollBar.setMaximum(10000);
		lineWidthScrollBar.setMinimum(-100000);
		lineWidthScrollBar.setVisibleAmount(0);
		lineWidthScrollBar.setPreferredSize(new Dimension(20, 48));
		lineWidthScrollBar.addAdjustmentListener(new java.awt.event.AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent e) {
				WordArtStyleEditor.this.lineWidthScrollBar_adjustmentValueChanged(e);
			}
		});
		jComboBox4.setEnabled(false);
		verifyButton.setPreferredSize(new Dimension(68, 24));
		verifyButton.setSelected(true);
		verifyButton.setText("확인");
		verifyButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStyleEditor.this.verifyButton_actionPerformed(e);
			}
		});
		cancelButton.setPreferredSize(new Dimension(68, 24));
		cancelButton.setText("취소");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStyleEditor.this.cancelButton_actionPerformed(e);
			}
		});
		flowLayout3.setAlignment(FlowLayout.RIGHT);
		flowLayout3.setHgap(10);
		jLabel11.setText("끝 유형(E):    ");
		jLabel11.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel11.setPreferredSize(new Dimension(43, 18));
		jLabel11.setBackground(SystemColor.menu);
		jLabel11.setEnabled(false);
		jPanel118.setLayout(borderLayout116);
		jPanel118.setPreferredSize(new Dimension(504, 100));
		jPanel118.setBackground(Color.pink);
		jPanel119.setBackground(SystemColor.menu);
		jPanel119.setPreferredSize(new Dimension(10, 50));
		jPanel115.setBackground(SystemColor.menu);
		jPanel115.setMinimumSize(new Dimension(1, 18));
		jPanel115.setPreferredSize(new Dimension(50, 10));
		jPanel115.setLayout(borderLayout114);
		jPanel1110.setBackground(Color.pink);
		jPanel1110.setPreferredSize(new Dimension(10, 18));
		jPanel1110.setLayout(borderLayout111);
		jPanel1111.setBackground(Color.pink);
		jPanel1111.setPreferredSize(new Dimension(10, 15));
		jPanel1111.setLayout(borderLayout112);
		jPanel1112.setBackground(SystemColor.menu);
		jPanel1112.setPreferredSize(new Dimension(5, 10));
		jPanel1113.setLayout(borderLayout113);
		gridLayout3.setRows(2);
		gridLayout3.setColumns(4);
		gridLayout3.setVgap(7);
		jPanel210.setLayout(flowLayout4);
		jPanel211.setBackground(SystemColor.menu);
		jPanel211.setLayout(gridLayout3);
		jPanel212.setBackground(Color.red);
		jPanel212.setMinimumSize(new Dimension(365, 50));
		jPanel212.setPreferredSize(new Dimension(10, 65));
		jPanel212.setLayout(borderLayout110);
		jLabel12.setText("시작 크기(I):");
		jLabel12.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel12.setEnabled(false);
		jLabel12.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel13.setBorder(BorderFactory.createEtchedBorder());
		jLabel13.setMinimumSize(new Dimension(0, 0));
		jLabel13.setPreferredSize(new Dimension(375, 2));
		jLabel14.setBackground(SystemColor.menu);
		jLabel14.setPreferredSize(new Dimension(36, 18));
		jLabel14.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel14.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel14.setText("화살표");
		jLabel15.setEnabled(false);
		jLabel15.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel15.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel15.setText("시작 유형(B):");
		jLabel16.setEnabled(false);
		jLabel16.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel16.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel16.setText("끝 크기(Z):    ");
		jComboBox5.setEnabled(false);
		jComboBox8.setEnabled(false);
		jComboBox7.setEnabled(false);
		jComboBox6.setEnabled(false);
		jPanel210.setPreferredSize(new Dimension(350, 12));
		gridLayout5.setVgap(7);
		gridLayout5.setColumns(4);
		gridLayout6.setRows(2);
		gridLayout6.setColumns(4);
		gridLayout6.setVgap(7);
		jPanel120.setBackground(SystemColor.menu);
		jPanel121.setBackground(SystemColor.menu);
		jPanel122.setLayout(borderLayout128);
		jPanel122.setBackground(Color.pink);
		jPanel123.setLayout(borderLayout21);
		jPanel124.setBackground(SystemColor.menu);
		jPanel124.setPreferredSize(new Dimension(5, 10));
		jPanel125.setBackground(Color.pink);
		jPanel125.setPreferredSize(new Dimension(10, 15));
		jPanel125.setLayout(borderLayout22);
		jPanel126.setBackground(Color.pink);
		jPanel126.setPreferredSize(new Dimension(10, 18));
		jPanel126.setLayout(borderLayout23);
		jPanel127.setBackground(SystemColor.menu);
		jPanel127.setPreferredSize(new Dimension(70, 10));
		jPanel127.setLayout(borderLayout20);
		originalSizePanel.setBackground(Color.orange);
		originalSizePanel.setPreferredSize(new Dimension(10, 76));
		originalSizePanel.setLayout(borderLayout120);
		widthRatioTextField.setText("100 %");
		widthRatioTextField.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStyleEditor.this.widthRatioTextField_actionPerformed(e);
			}
		});
		jPanel1114.setLayout(borderLayout118);
		jPanel1115.setBackground(SystemColor.menu);
		jPanel1115.setPreferredSize(new Dimension(5, 10));
		jPanel1116.setBackground(Color.pink);
		jPanel1116.setPreferredSize(new Dimension(10, 15));
		jPanel1116.setLayout(borderLayout119);
		jPanel29.setLayout(borderLayout129);
		jPanel1117.setBackground(Color.pink);
		jPanel1117.setPreferredSize(new Dimension(10, 18));
		jPanel1117.setLayout(borderLayout1110);
		jLabel19.setBackground(SystemColor.menu);
		jLabel19.setMaximumSize(new Dimension(200, 18));
		jLabel19.setPreferredSize(new Dimension(120, 18));
		jLabel19.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel19.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel19.setText("원래 크기");
		jLabel110.setBorder(BorderFactory.createEtchedBorder());
		jLabel110.setMinimumSize(new Dimension(0, 0));
		jLabel110.setPreferredSize(new Dimension(375, 2));
		sizeTabPane.setLayout(borderLayout3);
		widthRatioScrollBar.setMaximum(1000);
		widthRatioScrollBar.setMinimum(-1000);
		widthRatioScrollBar.setVisibleAmount(0);
		widthRatioScrollBar.setPreferredSize(new Dimension(20, 48));
		widthRatioScrollBar.addAdjustmentListener(new java.awt.event.AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent e) {
				WordArtStyleEditor.this.widthRatioScrollBar_adjustmentValueChanged(e);
			}
		});
		ratioPanel.setLayout(borderLayout25);
		jLabel21.setBorder(BorderFactory.createEtchedBorder());
		jLabel21.setMinimumSize(new Dimension(0, 0));
		jLabel21.setPreferredSize(new Dimension(375, 2));
		jLabel22.setBackground(SystemColor.menu);
		jLabel22.setMaximumSize(new Dimension(100, 18));
		jLabel22.setPreferredSize(new Dimension(20, 18));
		jLabel22.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel22.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel22.setText("비율");
		jLabel23.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel23.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel23.setText("높이(H):        ");
		jLabel25.setText(" ");
		jLabel26.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel26.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel26.setText("높이(E):       ");
		jLabel27.setBackground(SystemColor.menu);
		jLabel27.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel27.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel27.setText("크기 및 회전");
		jLabel27.setVerticalAlignment(SwingConstants.BOTTOM);
		jLabel27.setVerticalTextPosition(SwingConstants.BOTTOM);
		jLabel28.setBorder(BorderFactory.createEtchedBorder());
		jLabel28.setMinimumSize(new Dimension(0, 0));
		jLabel28.setPreferredSize(new Dimension(350, 2));
		jPanel1120.setBackground(Color.pink);
		jPanel1120.setPreferredSize(new Dimension(504, 100));
		jPanel1120.setLayout(borderLayout127);
		jPanel1121.setBackground(Color.red);
		jPanel1121.setPreferredSize(new Dimension(10, 50));
		jPanel1121.setLayout(borderLayout26);
		jPanel1122.setBackground(SystemColor.menu);
		jPanel1122.setMinimumSize(new Dimension(1, 18));
		jPanel1122.setPreferredSize(new Dimension(60, 10));
		jPanel1122.setLayout(borderLayout115);
		jPanel1123.setBackground(SystemColor.menu);
		jPanel1123.setMinimumSize(new Dimension(1, 18));
		jPanel1123.setPreferredSize(new Dimension(30, 10));
		jPanel1123.setLayout(borderLayout125);
		jPanel1124.setBackground(Color.pink);
		jPanel1124.setPreferredSize(new Dimension(10, 18));
		jPanel1124.setLayout(borderLayout122);
		jPanel1125.setBackground(Color.pink);
		jPanel1125.setPreferredSize(new Dimension(10, 15));
		jPanel1125.setLayout(borderLayout123);
		jPanel1126.setBackground(SystemColor.menu);
		jPanel1126.setPreferredSize(new Dimension(5, 10));
		jPanel1127.setLayout(borderLayout124);
		jPanel214.setLayout(borderLayout126);
		sizeAndRotationPanel.setBackground(Color.red);
		sizeAndRotationPanel.setMinimumSize(new Dimension(365, 50));
		sizeAndRotationPanel.setPreferredSize(new Dimension(10, 93));
		sizeAndRotationPanel.setLayout(borderLayout24);
		jPanel215.setLayout(flowLayout7);
		jPanel216.setBackground(Color.red);
		jPanel216.setMinimumSize(new Dimension(365, 50));
		jPanel216.setPreferredSize(new Dimension(10, 65));
		jPanel216.setLayout(borderLayout1111);
		jPanel217.setBackground(SystemColor.menu);
		jPanel217.setPreferredSize(new Dimension(272, 40));
		jPanel217.setLayout(gridLayout5);
		jPanel219.setLayout(flowLayout5);
		jPanel219.setPreferredSize(new Dimension(320, 12));
		jPanel220.setBackground(Color.red);
		jPanel220.setMinimumSize(new Dimension(365, 50));
		jPanel220.setPreferredSize(new Dimension(10, 65));
		jPanel220.setLayout(borderLayout121);
		jPanel221.setBackground(SystemColor.menu);
		jPanel221.setLayout(gridLayout6);
		jPanel222.setLayout(flowLayout6);
		rotationAngleScrollBar.setPreferredSize(new Dimension(20, 48));
		rotationAngleScrollBar.addAdjustmentListener(new java.awt.event.AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent e) {
				WordArtStyleEditor.this.rotationAngleScrollBar_adjustmentValueChanged(e);
			}
		});
		rotationAngleScrollBar.setMaximum(360);
		rotationAngleScrollBar.setMinimum(-360);
		rotationAngleScrollBar.setVisibleAmount(0);
		rotationAngleTextField.setText("0 degree");
		rotationAngleTextField.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStyleEditor.this.rotationAngleTextField_actionPerformed(e);
			}
		});
		jPanel2110.setLayout(borderLayout1210);
		ratioPanel.setPreferredSize(new Dimension(10, 107));
		jPanel1.setBackground(SystemColor.menu);
		jPanel1.setLayout(gridLayout4);
		jLabel29.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel29.setText("회전 각도(T):");
		widthValueScrollBar.setMaximum(1000000);
		widthValueScrollBar.setMinimum(-1000000);
		widthValueScrollBar.setVisibleAmount(0);
		widthValueScrollBar.setPreferredSize(new Dimension(20, 48));
		widthValueScrollBar.addAdjustmentListener(new java.awt.event.AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent e) {
				WordArtStyleEditor.this.widthValueScrollBar_adjustmentValueChanged(e);
			}
		});
		widthTextField.setText("13.6 cm");
		widthTextField.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				WordArtStyleEditor.this.widthTextField_mouseExited(e);
			}
		});
		widthTextField.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStyleEditor.this.widthTextField_actionPerformed(e);
			}
		});
		jPanel2111.setLayout(borderLayout1211);
		jLabel210.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel210.setText("너비(D):        ");
		heightValueScrollBar.setMaximum(1000000);
		heightValueScrollBar.setMinimum(-1000000);
		heightValueScrollBar.setVisibleAmount(0);
		heightValueScrollBar.setPreferredSize(new Dimension(20, 48));
		heightValueScrollBar.addAdjustmentListener(new java.awt.event.AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent e) {
				WordArtStyleEditor.this.heightValueScrollBar_adjustmentValueChanged(e);
			}
		});
		heightTextField.setText("1.27 cm");
		heightTextField.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				WordArtStyleEditor.this.heightTextField_mouseExited(e);
			}
		});
		heightTextField.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStyleEditor.this.heightTextField_actionPerformed(e);
			}
		});
		jPanel2112.setLayout(borderLayout1212);
		jLabel24.setText("너비(W):       ");
		jLabel24.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel24.setHorizontalAlignment(SwingConstants.CENTER);
		heightRatioScrollBar.setPreferredSize(new Dimension(20, 48));
		heightRatioScrollBar.addAdjustmentListener(new java.awt.event.AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent e) {
				WordArtStyleEditor.this.heightRatioScrollBar_adjustmentValueChanged(e);
			}
		});
		heightRatioScrollBar.setMaximum(1000);
		heightRatioScrollBar.setMinimum(-1000);
		heightRatioScrollBar.setVisibleAmount(0);
		heightRatioTextField.setText("100 %");
		heightRatioTextField.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStyleEditor.this.heightRatioTextField_actionPerformed(e);
			}
		});
		jPanel218.setLayout(borderLayout1213);
		jPanel2.setBackground(SystemColor.menu);
		jPanel2.setMinimumSize(new Dimension(10, 1));
		jPanel2.setPreferredSize(new Dimension(10, 5));
		jPanel3.setBackground(SystemColor.menu);
		jPanel4.setBackground(SystemColor.menu);
		jPanel4.setLayout(flowLayout8);
		widthHeightRatioFixedCheckBox.setPreferredSize(new Dimension(300, 15));
		widthHeightRatioFixedCheckBox.setText("가로 세로 비율 고정(A)");
		jCheckBox3.setPreferredSize(new Dimension(356, 15));
		jCheckBox3.setMaximumSize(new Dimension(356, 26));
		jCheckBox3.setEnabled(false);
		jCheckBox3.setText("원래 크기에 비례하여(R)");
		flowLayout8.setAlignment(FlowLayout.LEFT);
		flowLayout8.setVgap(3);
		gridLayout4.setRows(2);
		gridLayout4.setColumns(2);
		jLabel17.setBackground(SystemColor.menu);
		jLabel17.setEnabled(false);
		jLabel17.setText("     높이:");
		jLabel18.setEnabled(false);
		jLabel18.setText("   너비:");
		jButton1.setEnabled(false);
		jButton1.setPreferredSize(new Dimension(150, 24));
		jButton1.setText("원래 상태로(S)");
		jPanel30.setBackground(SystemColor.menu);
		jPanel30.setMinimumSize(new Dimension(10, 1));
		jPanel30.setPreferredSize(new Dimension(10, 4));
		jPanel215.setPreferredSize(new Dimension(355, 12));
		jLabel211.setText("       기준(F):");
		jLabel211.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel212.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel212.setText("세로(V):      ");
		locationXTextField.setText("0.63 cm");
		locationXTextField.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				WordArtStyleEditor.this.locationXTextField_mouseExited(e);
			}
		});
		locationXTextField.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStyleEditor.this.locationXTextField_actionPerformed(e);
			}
		});
		jLabel213.setBorder(BorderFactory.createEtchedBorder());
		jLabel213.setMinimumSize(new Dimension(0, 0));
		jLabel213.setPreferredSize(new Dimension(350, 2));
		jPanel11213.setBackground(SystemColor.menu);
		jPanel11213.setMinimumSize(new Dimension(1, 18));
		jPanel11213.setPreferredSize(new Dimension(50, 10));
		jPanel11213.setLayout(borderLayout117);
		jLabel214.setBackground(SystemColor.menu);
		jLabel214.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel214.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel214.setText("위치");
		jLabel214.setVerticalAlignment(SwingConstants.BOTTOM);
		jLabel214.setVerticalTextPosition(SwingConstants.BOTTOM);
		locationYTextField.setText("5.71 cm");
		locationYTextField.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseExited(MouseEvent e) {
				WordArtStyleEditor.this.locationYTextField_mouseExited(e);
			}
		});
		locationYTextField.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStyleEditor.this.locationYTextField_actionPerformed(e);
			}
		});
		jLabel215.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel215.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel215.setText("가로(H):      ");
		jLabel216.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel216.setText("       기준(R):");
		jPanel1118.setBackground(Color.pink);
		jPanel1118.setPreferredSize(new Dimension(10, 18));
		jPanel1118.setLayout(borderLayout1113);
		jPanel1119.setBackground(Color.pink);
		jPanel1119.setPreferredSize(new Dimension(10, 15));
		jPanel1119.setLayout(borderLayout1114);
		jPanel11110.setBackground(SystemColor.menu);
		jPanel11110.setPreferredSize(new Dimension(5, 10));
		jPanel11111.setLayout(borderLayout1115);
		jLabel111.setBackground(SystemColor.menu);
		jLabel111.setMaximumSize(new Dimension(200, 18));
		jLabel111.setPreferredSize(new Dimension(50, 18));
		jLabel111.setHorizontalAlignment(SwingConstants.CENTER);
		jLabel111.setHorizontalTextPosition(SwingConstants.CENTER);
		jLabel111.setText("           ");
		locationTabPane.setLayout(borderLayout27);
		jPanel37.setBackground(SystemColor.menu);
		jPanel37.setLayout(borderLayout28);
		locationXScrollBar.setMaximum(1000000);
		locationXScrollBar.setMinimum(-1000000);
		locationXScrollBar.setVisibleAmount(0);
		locationXScrollBar.setPreferredSize(new Dimension(20, 48));
		locationXScrollBar.addAdjustmentListener(new java.awt.event.AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent e) {
				WordArtStyleEditor.this.locationXScrollBar_adjustmentValueChanged(e);
			}
		});
		locationYScrollBar.setPreferredSize(new Dimension(20, 48));
		locationYScrollBar.addAdjustmentListener(new java.awt.event.AdjustmentListener() {

			public void adjustmentValueChanged(AdjustmentEvent e) {
				WordArtStyleEditor.this.locationYScrollBar_adjustmentValueChanged(e);
			}
		});
		locationYScrollBar.setMaximum(1000000);
		locationYScrollBar.setMinimum(-1000000);
		locationYScrollBar.setVisibleAmount(0);
		jPanel1210.setBackground(SystemColor.menu);
		jPanel1210.setPreferredSize(new Dimension(40, 10));
		jPanel1210.setLayout(borderLayout214);
		jPanel1211.setBackground(Color.pink);
		jPanel1211.setPreferredSize(new Dimension(10, 18));
		jPanel1211.setLayout(borderLayout211);
		jPanel1212.setBackground(Color.pink);
		jPanel1212.setPreferredSize(new Dimension(10, 15));
		jPanel1212.setLayout(borderLayout212);
		jPanel1213.setBackground(SystemColor.menu);
		jPanel1213.setPreferredSize(new Dimension(5, 10));
		jPanel1214.setLayout(borderLayout213);
		jPanel1215.setLayout(borderLayout1215);
		jPanel1215.setBackground(Color.pink);
		jPanel1216.setBackground(SystemColor.menu);
		jPanel1217.setBackground(Color.red);
		jPanel1217.setPreferredSize(new Dimension(10, 90));
		jPanel1217.setLayout(borderLayout29);
		stringLocationPanel.setBackground(Color.orange);
		stringLocationPanel.setPreferredSize(new Dimension(10, 96));
		stringLocationPanel.setLayout(borderLayout1223);
		jPanel2113.setLayout(borderLayout12111);
		jPanel2115.setLayout(borderLayout12113);
		locationPanel.setBackground(Color.red);
		locationPanel.setMinimumSize(new Dimension(365, 50));
		locationPanel.setPreferredSize(new Dimension(10, 160));
		locationPanel.setLayout(borderLayout210);
		jPanel223.setLayout(flowLayout11);
		jPanel224.setBackground(SystemColor.menu);
		jPanel224.setLayout(gridLayout8);
		jLabel114.setBorder(BorderFactory.createEtchedBorder());
		jLabel114.setMinimumSize(new Dimension(0, 0));
		jLabel114.setPreferredSize(new Dimension(375, 2));
		jPanel2116.setLayout(flowLayout12);
		jPanel2116.setPreferredSize(new Dimension(320, 12));
		jPanel2119.setBackground(Color.red);
		jPanel2119.setMinimumSize(new Dimension(365, 50));
		jPanel2119.setPreferredSize(new Dimension(10, 65));
		jPanel2119.setLayout(borderLayout1112);
		jPanel213.setLayout(borderLayout1214);
		gridLayout8.setRows(2);
		gridLayout8.setColumns(4);
		gridLayout8.setVgap(7);
		jPanel31.setBackground(SystemColor.menu);
		jPanel31.setPreferredSize(new Dimension(10, 40));
		jPanel31.setLayout(flowLayout9);
		jCheckBox4.setSelected(true);
		jCheckBox4.setEnabled(false);
		jCheckBox4.setText("문자열 위에 위치(T)");
		flowLayout9.setAlignment(FlowLayout.LEFT);
		flowLayout9.setHgap(15);
		jPanel34.setBackground(SystemColor.menu);
		jPanel34.setLayout(flowLayout10);
		jCheckBox5.setPreferredSize(new Dimension(271, 26));
		jCheckBox5.setSelected(true);
		jCheckBox5.setMnemonic('M');
		jCheckBox5.setText("문자열과 함께 개체 이동(M)");
		jCheckBox6.setMnemonic('L');
		jCheckBox6.setText("틀 위치 고정(L)");
		flowLayout10.setAlignment(FlowLayout.LEFT);
		jPanel36.setMinimumSize(new Dimension(10, 1));
		jPanel36.setPreferredSize(new Dimension(10, 3));
		lineColorComboBox.setMaximumRowCount(10);
		lineColorComboBox.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStyleEditor.this.lineColorComboBox_actionPerformed(e);
			}
		});
		lineKindComboBox.setMaximumRowCount(10);
		lineKindComboBox.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStyleEditor.this.lineKindComboBox_actionPerformed(e);
			}
		});
		fillColorComboBox.setMaximumRowCount(10);
		fillColorComboBox.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtStyleEditor.this.fillColorComboBox_actionPerformed(e);
			}
		});
		contentPane.add(jTabbedPane1, BorderLayout.CENTER);
		jTabbedPane1.add(colorAndLineTabPanel, "  색과 선  ");
		colorAndLineTabPanel.add(fillPanel, BorderLayout.NORTH);
		fillPanel.add(jPanel12, BorderLayout.CENTER);
		jPanel12.add(jPanel13, BorderLayout.NORTH);
		jPanel13.add(jPanel14, BorderLayout.EAST);
		jPanel13.add(jPanel15, BorderLayout.CENTER);
		jPanel15.add(jPanel11, BorderLayout.WEST);
		jPanel11.add(jLabel2, BorderLayout.CENTER);
		jPanel15.add(jPanel22, BorderLayout.CENTER);
		jPanel22.add(jLabel1, null);
		jPanel12.add(jPanel9, BorderLayout.CENTER);
		jPanel9.add(jPanel10, BorderLayout.EAST);
		jPanel9.add(jPanel16, BorderLayout.CENTER);
		jPanel16.add(jPanel17, BorderLayout.NORTH);
		jPanel16.add(jPanel18, BorderLayout.SOUTH);
		jPanel16.add(jPanel23, BorderLayout.CENTER);
		jPanel23.add(jLabel3, null);
		jPanel23.add(fillColorComboBox, null);
		jPanel23.add(halfTransparentCheckBox, null);
		jPanel23.add(jLabel4, null);
		colorAndLineTabPanel.add(arrowPanel, BorderLayout.SOUTH);
		arrowPanel.add(jPanel212, BorderLayout.CENTER);
		jPanel212.add(jPanel1110, BorderLayout.CENTER);
		jPanel1110.add(jPanel1111, BorderLayout.NORTH);
		jPanel1111.add(jPanel1112, BorderLayout.EAST);
		jPanel1111.add(jPanel1113, BorderLayout.CENTER);
		jPanel1113.add(jPanel115, BorderLayout.WEST);
		jPanel115.add(jLabel14, BorderLayout.CENTER);
		jPanel1113.add(jPanel210, BorderLayout.CENTER);
		jPanel210.add(jLabel13, null);
		jPanel1110.add(jPanel118, BorderLayout.CENTER);
		jPanel118.add(jPanel119, BorderLayout.SOUTH);
		jPanel118.add(jPanel28, BorderLayout.EAST);
		jPanel118.add(jPanel211, BorderLayout.CENTER);
		jPanel211.add(jLabel15, null);
		jPanel211.add(jComboBox7, null);
		jPanel211.add(jLabel11, null);
		jPanel211.add(jComboBox5, null);
		jPanel211.add(jLabel12, null);
		jPanel211.add(jComboBox6, null);
		jPanel211.add(jLabel16, null);
		jPanel211.add(jComboBox8, null);
		colorAndLineTabPanel.add(colorPanel, BorderLayout.CENTER);
		colorPanel.add(jPanel24, BorderLayout.CENTER);
		jPanel24.add(jPanel113, BorderLayout.CENTER);
		jPanel113.add(jPanel112, BorderLayout.NORTH);
		jPanel112.add(jPanel111, BorderLayout.EAST);
		jPanel112.add(jPanel110, BorderLayout.CENTER);
		jPanel110.add(jPanel114, BorderLayout.WEST);
		jPanel114.add(jLabel7, BorderLayout.CENTER);
		jPanel110.add(jPanel26, BorderLayout.CENTER);
		jPanel26.add(jLabel8, null);
		jPanel113.add(jPanel117, BorderLayout.CENTER);
		jPanel117.add(jPanel116, BorderLayout.SOUTH);
		jPanel117.add(jPanel7, BorderLayout.EAST);
		jPanel117.add(jPanel25, BorderLayout.CENTER);
		jPanel25.add(jLabel6, null);
		jPanel25.add(lineColorComboBox, null);
		jPanel25.add(jLabel10, null);
		jPanel25.add(jComboBox4, null);
		jPanel25.add(jLabel9, null);
		jPanel25.add(lineKindComboBox, null);
		jPanel25.add(jLabel5, null);
		jPanel25.add(jPanel27, null);
		jPanel27.add(lineWidthTextField, BorderLayout.CENTER);
		jPanel27.add(lineWidthScrollBar, BorderLayout.EAST);
		jTabbedPane1.add(sizeTabPane, "  크기  ");
		sizeTabPane.add(sizeAndRotationPanel, BorderLayout.NORTH);
		sizeAndRotationPanel.add(jPanel126, BorderLayout.CENTER);
		jPanel126.add(jPanel125, BorderLayout.NORTH);
		jPanel125.add(jPanel124, BorderLayout.EAST);
		jPanel125.add(jPanel123, BorderLayout.CENTER);
		jPanel123.add(jPanel127, BorderLayout.WEST);
		jPanel127.add(jLabel27, BorderLayout.CENTER);
		jPanel123.add(jPanel222, BorderLayout.CENTER);
		jPanel222.add(jLabel28, null);
		jPanel126.add(jPanel29, BorderLayout.CENTER);
		jPanel29.add(jPanel128, BorderLayout.EAST);
		jPanel29.add(jPanel122, BorderLayout.CENTER);
		jPanel122.add(jPanel121, BorderLayout.NORTH);
		jPanel122.add(jPanel120, BorderLayout.SOUTH);
		jPanel122.add(jPanel221, BorderLayout.CENTER);
		jPanel221.add(jLabel26, null);
		jPanel221.add(jPanel2112, null);
		jPanel2112.add(heightTextField, BorderLayout.CENTER);
		jPanel2112.add(heightValueScrollBar, BorderLayout.EAST);
		jPanel221.add(jLabel210, null);
		jPanel221.add(jPanel2111, null);
		jPanel2111.add(widthTextField, BorderLayout.CENTER);
		jPanel2111.add(widthValueScrollBar, BorderLayout.EAST);
		jPanel221.add(jLabel29, null);
		jPanel221.add(jPanel2110, null);
		jPanel2110.add(rotationAngleTextField, BorderLayout.CENTER);
		jPanel2110.add(rotationAngleScrollBar, BorderLayout.EAST);
		jPanel221.add(jLabel25, null);
		sizeTabPane.add(originalSizePanel, BorderLayout.SOUTH);
		originalSizePanel.add(jPanel216, BorderLayout.CENTER);
		jPanel216.add(jPanel1117, BorderLayout.CENTER);
		jPanel1117.add(jPanel1116, BorderLayout.NORTH);
		jPanel1116.add(jPanel1115, BorderLayout.EAST);
		jPanel1116.add(jPanel1114, BorderLayout.CENTER);
		jPanel1114.add(jPanel1122, BorderLayout.WEST);
		jPanel1122.add(jLabel19, BorderLayout.CENTER);
		jPanel1114.add(jPanel219, BorderLayout.CENTER);
		jPanel219.add(jLabel110, null);
		jPanel1117.add(jPanel1, BorderLayout.CENTER);
		jPanel1.add(jLabel17, null);
		jPanel1.add(jLabel18, null);
		jPanel1.add(jPanel5, null);
		jPanel1.add(jPanel6, null);
		jPanel6.add(jButton1, null);
		sizeTabPane.add(ratioPanel, BorderLayout.CENTER);
		ratioPanel.add(jPanel220, BorderLayout.CENTER);
		jPanel220.add(jPanel1124, BorderLayout.CENTER);
		jPanel1124.add(jPanel1125, BorderLayout.NORTH);
		jPanel1125.add(jPanel1126, BorderLayout.EAST);
		jPanel1125.add(jPanel1127, BorderLayout.CENTER);
		jPanel1127.add(jPanel1123, BorderLayout.WEST);
		jPanel1123.add(jLabel22, BorderLayout.CENTER);
		jPanel1127.add(jPanel215, BorderLayout.CENTER);
		jPanel215.add(jLabel21, null);
		jPanel1124.add(jPanel1120, BorderLayout.CENTER);
		jPanel1120.add(jPanel1121, BorderLayout.SOUTH);
		jPanel1121.add(jPanel3, BorderLayout.WEST);
		jPanel1121.add(jPanel4, BorderLayout.CENTER);
		jPanel4.add(widthHeightRatioFixedCheckBox, null);
		jPanel4.add(jCheckBox3, null);
		jPanel1121.add(jPanel30, BorderLayout.NORTH);
		jPanel1121.add(jPanel36, BorderLayout.SOUTH);
		jPanel1120.add(jPanel8, BorderLayout.EAST);
		jPanel1120.add(jPanel217, BorderLayout.CENTER);
		jPanel217.add(jLabel23, null);
		jPanel217.add(jPanel218, null);
		jPanel218.add(heightRatioTextField, BorderLayout.CENTER);
		jPanel218.add(heightRatioScrollBar, BorderLayout.EAST);
		jPanel217.add(jLabel24, null);
		jPanel217.add(jPanel214, null);
		jPanel214.add(widthRatioTextField, BorderLayout.CENTER);
		jPanel214.add(widthRatioScrollBar, BorderLayout.EAST);
		jPanel1120.add(jPanel2, BorderLayout.NORTH);
		jTabbedPane1.add(locationTabPane, "  위치  ");
		locationTabPane.add(locationPanel, BorderLayout.NORTH);
		locationPanel.add(jPanel1211, BorderLayout.CENTER);
		jPanel1211.add(jPanel1212, BorderLayout.NORTH);
		jPanel1212.add(jPanel1213, BorderLayout.EAST);
		jPanel1212.add(jPanel1214, BorderLayout.CENTER);
		jPanel1214.add(jPanel1210, BorderLayout.WEST);
		jPanel1210.add(jLabel214, BorderLayout.CENTER);
		jPanel1214.add(jPanel223, BorderLayout.CENTER);
		jPanel223.add(jLabel213, null);
		jPanel1211.add(jPanel213, BorderLayout.CENTER);
		jPanel213.add(jPanel129, BorderLayout.EAST);
		jPanel213.add(jPanel1215, BorderLayout.CENTER);
		jPanel1215.add(jPanel1216, BorderLayout.NORTH);
		jPanel1215.add(jPanel1217, BorderLayout.SOUTH);
		jPanel1217.add(jPanel32, BorderLayout.WEST);
		jPanel1217.add(jPanel33, BorderLayout.NORTH);
		jPanel1217.add(jPanel34, BorderLayout.CENTER);
		jPanel34.add(jCheckBox5, null);
		jPanel34.add(jCheckBox6, null);
		jPanel1215.add(jPanel224, BorderLayout.CENTER);
		jPanel224.add(jLabel215, null);
		jPanel224.add(jPanel2113, null);
		jPanel2113.add(locationXTextField, BorderLayout.CENTER);
		jPanel2113.add(locationXScrollBar, BorderLayout.EAST);
		jPanel224.add(jLabel211, null);
		jPanel224.add(refereceFComboBox, null);
		jPanel224.add(jLabel212, null);
		jPanel224.add(jPanel2115, null);
		jPanel2115.add(locationYTextField, BorderLayout.CENTER);
		jPanel2115.add(locationYScrollBar, BorderLayout.EAST);
		jPanel224.add(jLabel216, null);
		jPanel224.add(referenceRComboBox, null);
		locationTabPane.add(stringLocationPanel, BorderLayout.SOUTH);
		stringLocationPanel.add(jPanel2119, BorderLayout.CENTER);
		jPanel2119.add(jPanel1118, BorderLayout.CENTER);
		jPanel1118.add(jPanel1119, BorderLayout.NORTH);
		jPanel1119.add(jPanel11110, BorderLayout.EAST);
		jPanel1119.add(jPanel11111, BorderLayout.CENTER);
		jPanel11111.add(jPanel11213, BorderLayout.WEST);
		jPanel11213.add(jLabel111, BorderLayout.CENTER);
		jPanel11111.add(jPanel2116, BorderLayout.CENTER);
		jPanel2116.add(jLabel114, null);
		jPanel1118.add(jPanel37, BorderLayout.CENTER);
		jPanel37.add(jPanel31, BorderLayout.NORTH);
		jPanel31.add(jCheckBox4, null);
		locationTabPane.add(jPanel35, BorderLayout.CENTER);
		contentPane.add(jPanel19, BorderLayout.SOUTH);
		jPanel19.add(verifyButton, null);
		jPanel19.add(cancelButton, null);
		contentPane.add(jPanel20, BorderLayout.WEST);
		contentPane.add(jPanel21, BorderLayout.EAST);
	}

	// Overridden so we can exit when window is closed
	@Override
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			// System.exit(0);
		}
	}

	private void setWordArtStyleAccordingToCurrentGroupManager() {
		try {
			WordArtGroupManager manager = WordArtGroupManager.currentWorkingManager;
			if (manager == null) {
				return;
			}
			WordArt[] was = manager.getSelectedWordArts();
			if (was != null && was.length > 0) {
				this.wordArtStyle = was[0].getWordArtStyle().create();
			} else {
				this.wordArtStyle = new WordArtStyle();
			}
			this.setGUIComponentsValueAccordingToWordArtStyle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setGUIComponentsValueAccordingToWordArtStyle() {
		this.widthTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(this.wordArtStyle.getDimensionWidth()) + " cm");
		this.heightTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(this.wordArtStyle.getDimensionHeight()) + " cm");
		this.rotationAngleTextField.setText(" " + this.wordArtStyle.getRotationDegreeAngle() + " degree");
		this.rotationAngleScrollBar.setValue((int) -this.wordArtStyle.getRotationDegreeAngle()); // 스크롤바의
		// 특성상
		// 부호가
		// 역이어야
		// 한다.
		this.locationXTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(this.wordArtStyle.getLocationX()) + " cm");
		this.locationYTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(this.wordArtStyle.getLocationY()) + " cm");
		this.widthRatioTextField.setText(" 100%");
		this.heightRatioTextField.setText(" 100%");
		this.firstWidth = this.wordArtStyle.getDimensionWidth();
		this.firstHeight = this.wordArtStyle.getDimensionHeight();
		this.setLineWidthTextField();
		Color fillColor = this.wordArtStyle.getFillColor(), lineColor = this.wordArtStyle.getLineColor();
		if (fillColor != null) {
			this.fillColorComboBoxRenderer.changeColor(fillColor);
			this.halfTransparentCheckBox.setEnabled(true);
			// this.halfTransparentCheckBoxSelected = false;
			this.halfTransparentCheckBox.setSelected(false);
		} else {
			GraphicEffect fe = null;
			if (this.wordArtStyle != null) {
				fe = this.wordArtStyle.getFillEffect();
				if (fe != null) {
					this.fillColorComboBoxRenderer.changeFillEffect(fe);
					if (fe.isHalfTransparent()) {
						this.halfTransparentCheckBox.setEnabled(true);
						this.halfTransparentCheckBox.setSelected(true);
						// this.halfTransparentCheckBoxSelected = true;
						this.chooserFillColor = fe.getFirstGradientColor();
					} else {
						this.halfTransparentCheckBox.setEnabled(false);
						this.halfTransparentCheckBox.setSelected(false);
						// this.halfTransparentCheckBoxSelected = false;
					}
				}
			}
		}
		if (lineColor != null) {
			this.lineColorComboBoxRenderer.changeColor(lineColor);
		} else {
			GraphicEffect lineTexture = null;
			if (this.wordArtStyle != null) {
				lineTexture = this.wordArtStyle.getFillEffect_LineTexture();
				if (lineTexture != null) {
					this.lineColorComboBoxRenderer.changeFillEffect(lineTexture);
				} else {
					this.lineColorComboBoxRenderer.changeColor(null);
					this.lineColorComboBoxRenderer.changeFillEffect(null);
				}
			} else {
				this.lineColorComboBoxRenderer.changeColor(null);
				this.lineColorComboBoxRenderer.changeFillEffect(null);
			}
		}
	}

	@Override
	public void setVisible(boolean b) {

		if (b) {

			if (neverShown) {
				this.reLocate();
				neverShown = false;
			}
			this.setWordArtStyleAccordingToCurrentGroupManager();

		}
		super.setVisible(b);
	}

	public void reLocate() {
		WinUtil.locateOnTheScreenCenter(this);
	}

	public static WordArtStyle getNewWordArtStyle() {
		wordArtStyleEditor.setVisible(true);
		return wordArtStyleEditor.wordArtStyle;
	}

	void cancelButton_actionPerformed(ActionEvent e) {
		this.wordArtStyle = null;
		this.setVisible(false);
		;
	}

	void verifyButton_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	void fillColorComboBox_actionPerformed(ActionEvent ae) {
		int index = this.fillColorComboBox.getSelectedIndex();
		if (index == 1) { // 채우기 없음을 선택하면, 채우기 칼라를 널로 변경한다.
			this.setFillColor(null);
			this.halfTransparentCheckBox.setSelected(false);
			this.halfTransparentCheckBox.setEnabled(false);
		} else if (index == 2) { // 다른 색을 선택하면, 채우기 칼라를 변경한다.
			Component comp = WordArtGroupManager.currentWorkingManager.getTargetComponent();
			if (chooserFillColor == null) {
				chooserFillColor = this.wordArtStyle.getFillColor();
			}

			if (chooserFillColor == null) {
				chooserFillColor = Color.black;
			}

			Color c = JColorChooser.showDialog(comp, "색", chooserFillColor);

			if (c == null) {
				return;
			} else {
				chooserFillColor = c;
			}
			if (!this.halfTransparentCheckBox.isEnabled()) {
				this.halfTransparentCheckBox.setEnabled(true);
			}
			if (this.halfTransparentCheckBox.isSelected()) {
				this.halfTransparentCheckBox_actionPerformed(null);
			} else {
				this.setFillColor(c);
			}
		} else if (index == 3) { // 채우기 효과를 설정하는 경우
			GraphicEffect refFillEffect = null;
			if (this.wordArtStyle != null) {
				refFillEffect = this.wordArtStyle.getFillEffect();
			} else {
				refFillEffect = new GraphicEffect();
			}
			GraphicEffect graphicEffect = WordArtFillEffectEditor.getNewFillEffect(refFillEffect);
			if (graphicEffect != null) {
				this.setFillEffect(graphicEffect);
				this.halfTransparentCheckBox.setSelected(false);
				this.halfTransparentCheckBox.setEnabled(false);
			}
		}
		// this.fillColorComboBox.setSelectedIndex(0);
	}

	public void setFillEffect(GraphicEffect fe) {
		this.wordArtStyle.setFillEffect(fe);
		this.fillColorComboBoxRenderer.changeFillEffect(fe);
		this.fillColorComboBox.setSelectedIndex(0);
	}

	private boolean isHeightIncreased(AdjustmentEvent e) {
		boolean value = false;
		if (this.preHeightScrollBarValue > e.getValue()) { // 스크롤 바의 특성상 판별 부호가
			// 역으로 되어야 한다.
			value = true;
		}
		this.preHeightScrollBarValue = e.getValue();
		return value;
	}

	private void increaseHeight(AdjustmentEvent e) {
		if (this.isHeightIncreased(e)) { // 높이 값을 증가 시키는 경우
			this.wordArtStyle.increaseDimensionHeight(UnitUtil_WordArt.convertCMToPixel(0.1));
		} else { // 높이 값을 감소 시키는 경우
			this.wordArtStyle.increaseDimensionHeight(UnitUtil_WordArt.convertCMToPixel(-0.1));
		}
		this.heightTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionHeight()) + "cm");
		this.heightRatioTextField.setText(" " + this.getHeightRatioPercent() + "%");
	}

	private boolean isWidthIncreased(AdjustmentEvent e) {
		boolean value = false;
		if (this.preWidthScrollBarValue > e.getValue()) { // 스크롤 바의 특성상 판별 부호가
			// 역으로 되어야 한다.
			value = true;
		}
		this.preWidthScrollBarValue = e.getValue();
		return value;
	}

	private void increaseWidth(AdjustmentEvent e) {
		if (this.isWidthIncreased(e)) { // 폭 값을 증가 시키는 경우
			this.wordArtStyle.increaseDimensionWidth(UnitUtil_WordArt.convertCMToPixel(0.1));
		} else { // 폭 값을 감소 시키는 경우
			this.wordArtStyle.increaseDimensionWidth(UnitUtil_WordArt.convertCMToPixel(-0.1));
		}
		this.widthTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionWidth()) + "cm");
		this.widthRatioTextField.setText(" " + this.getWidthRatioPercent() + "%");
	}

	void heightValueScrollBar_adjustmentValueChanged(AdjustmentEvent e) {
		this.increaseHeight(e);
		if (this.widthHeightRatioFixedCheckBox.isSelected()) {
			double fixedRatio = this.getHeightRatioPercent() / 100.0; // 퍼센티지를
			// 실 값으로
			// 변환
			double width = this.firstWidth * fixedRatio;
			this.wordArtStyle.setDimension(width, this.wordArtStyle.getDimensionHeight());
			this.widthTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionWidth()) + "cm");
			// 폭 비율을 높이 비율과 일치시킨다.
			this.widthRatioTextField.setText(" " + this.getHeightRatioPercent() + "%");
		}
	}

	void widthValueScrollBar_adjustmentValueChanged(AdjustmentEvent e) {
		this.increaseWidth(e);
		if (this.widthHeightRatioFixedCheckBox.isSelected()) {
			double fixedRatio = this.getWidthRatioPercent() / 100.0; // 퍼센티지를
			// 실 값으로
			// 변환
			double height = this.firstHeight * fixedRatio;
			this.wordArtStyle.setDimension(this.wordArtStyle.getDimensionWidth(), height);
			this.heightTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionHeight()) + "cm");
			// 높이 비율을 폭 비율과 일치시킨다.
			fixedRatio = (int) fixedRatio * 100;
			this.heightRatioTextField.setText(" " + this.getWidthRatioPercent() + "%");
		}
	}

	void rotationAngleScrollBar_adjustmentValueChanged(AdjustmentEvent e) {
		this.wordArtStyle.setRotationDegreeAngle(-e.getValue()); // 스크롤 바의
		// 특성상 부호가
		// 역으로 되어야
		// 한다.
		this.rotationAngleTextField.setText(" " + this.wordArtStyle.getRotationDegreeAngle() + " degree");
	}

	void rotationAngleTextField_actionPerformed(ActionEvent e) {
		String angleStr = this.rotationAngleTextField.getText();
		int degreeIndex = angleStr.indexOf("degree");
		if (degreeIndex > -1) {
			angleStr = angleStr.substring(0, degreeIndex);
		}
		angleStr = angleStr.trim();
		try {
			double degree = Double.valueOf(angleStr).doubleValue();
			if (degree > 360) {
				degree = degree % 360;
			} else if (degree < -360) {
				degree = degree % -360;
			}
			this.wordArtStyle.setRotationDegreeAngle(degree);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		this.rotationAngleTextField.setText(" " + this.wordArtStyle.getRotationDegreeAngle() + " degree");
		this.rotationAngleScrollBar.setValue((int) -this.wordArtStyle.getRotationDegreeAngle()); // 스크롤
		// 바의
		// 특성상
		// 부호가
		// 역으로
		// 되어야
		// 한다.
	}

	private boolean isLocationXIncreased(AdjustmentEvent e) {
		boolean value = false;
		if (this.preLocationXScrollBarValue > e.getValue()) { // 스크롤 바의 특성상 판별
			// 부호가 역으로 되어야
			// 한다.
			value = true;
		}
		this.preLocationXScrollBarValue = e.getValue();
		return value;
	}

	private void increaseLocationX(AdjustmentEvent e) {
		if (this.isLocationXIncreased(e)) { // 위치 X 값을 증가 시키는 경우
			this.wordArtStyle.increaseLocationX(UnitUtil_WordArt.convertCMToPixel(0.5));
		} else { // 위치 X 값을 감소 시키는 경우
			this.wordArtStyle.increaseLocationX(UnitUtil_WordArt.convertCMToPixel(-0.5));
		}
	}

	void locationXScrollBar_adjustmentValueChanged(AdjustmentEvent e) {
		this.increaseLocationX(e);
		double locX = UnitUtil_WordArt.convertPixelToCM(this.wordArtStyle.getLocationX());
		locX *= 2.0;
		locX = Math.rint(locX);
		locX /= 2.0;
		int x = (int) locX, xx = (int) ((locX - x) * 10);
		float xxx = x + xx / 10.0f;
		this.locationXTextField.setText(" " + xxx + " cm");
	}

	private boolean isLocationYIncreased(AdjustmentEvent e) {
		boolean value = false;
		if (this.preLocationYScrollBarValue > e.getValue()) { // 스크롤 바의 특성상 판별
			// 부호가 역으로 되어야
			// 한다.
			value = true;
		}
		this.preLocationYScrollBarValue = e.getValue();
		return value;
	}

	private void increaseLocationY(AdjustmentEvent e) {
		if (this.isLocationYIncreased(e)) { // 위치 X 값을 증가 시키는 경우
			this.wordArtStyle.increaseLocationY(UnitUtil_WordArt.convertCMToPixel(0.5));
		} else { // 위치 X 값을 감소 시키는 경우
			this.wordArtStyle.increaseLocationY(UnitUtil_WordArt.convertCMToPixel(-0.5));
		}
	}

	void locationYScrollBar_adjustmentValueChanged(AdjustmentEvent e) {
		this.increaseLocationY(e);
		double locY = UnitUtil_WordArt.convertPixelToCM(this.wordArtStyle.getLocationY());
		locY *= 2.0;
		locY = Math.rint(locY);
		locY /= 2.0;
		int y = (int) locY, yy = (int) ((locY - y) * 10);
		float yyy = y + yy / 10.0f;
		this.locationYTextField.setText(" " + yyy + " cm");
	}

	void locationXTextField_actionPerformed(ActionEvent e) {
		String locX = this.locationXTextField.getText();
		int cmIndex = locX.indexOf("cm");
		if (cmIndex > -1) {
			locX = locX.substring(0, cmIndex);
		}
		locX = locX.trim();
		try {
			double xCm = Double.valueOf(locX).doubleValue(), xPxl = UnitUtil_WordArt.convertCMToPixel(xCm);
			this.wordArtStyle.setLocationX(xPxl);
			this.locationXTextField.setText(" " + xCm + " cm");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void locationYTextField_actionPerformed(ActionEvent e) {
		String locY = this.locationYTextField.getText();
		int cmIndex = locY.indexOf("cm");
		if (cmIndex > -1) {
			locY = locY.substring(0, cmIndex);
		}
		locY = locY.trim();
		try {
			double yCm = Double.valueOf(locY).doubleValue(), yPxl = UnitUtil_WordArt.convertCMToPixel(yCm);
			this.wordArtStyle.setLocationY(yPxl);
			this.locationYTextField.setText(" " + yCm + " cm");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void locationXTextField_mouseExited(MouseEvent e) {
		this.locationXTextField_actionPerformed(null);
	}

	void locationYTextField_mouseExited(MouseEvent e) {
		this.locationYTextField_actionPerformed(null);
	}

	private double getWidthRatioPercent() {
		double value = this.wordArtStyle.getDimensionWidth() / this.firstWidth;
		value = Math.rint(value * 100);
		return value;
	}

	private double getHeightRatioPercent() {
		double value = this.wordArtStyle.getDimensionHeight() / this.firstHeight;
		value = Math.rint(value * 100);
		return value;
	}

	void heightTextField_actionPerformed(ActionEvent e) {
		String heightStr = this.heightTextField.getText();
		int cmIndex = heightStr.indexOf("cm");
		if (cmIndex > -1) {
			heightStr = heightStr.substring(0, cmIndex);
		}
		heightStr = heightStr.trim();
		try {
			double h = Double.valueOf(heightStr).doubleValue();
			h = UnitUtil_WordArt.convertCMToPixel(h); // 단위 변환
			this.wordArtStyle.setDimension(this.wordArtStyle.getDimensionWidth(), h);
			if (this.widthHeightRatioFixedCheckBox.isSelected()) {
				double fixedRatio = this.getHeightRatioPercent() / 100.0; // 퍼센티지를
				// 실
				// 값으로
				// 변환
				double width = this.firstWidth * fixedRatio;
				this.wordArtStyle.setDimension(width, h);
				this.widthTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionWidth()) + "cm");
				this.heightTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionHeight()) + "cm");
				// 폭 비율을 높이 비율과 일치시킨다.
				this.widthRatioTextField.setText(" " + this.getHeightRatioPercent() + "%");
				this.heightRatioTextField.setText(" " + this.getHeight() + "%");
			} else {
				this.heightTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionHeight()) + "cm");
				this.heightRatioTextField.setText(" " + this.getHeight() + "%");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void widthTextField_actionPerformed(ActionEvent e) {
		String widthStr = this.widthTextField.getText();
		int cmIndex = widthStr.indexOf("cm");
		if (cmIndex > -1) {
			widthStr = widthStr.substring(0, cmIndex);
		}
		widthStr = widthStr.trim();
		try {
			double w = Double.valueOf(widthStr).doubleValue();
			w = UnitUtil_WordArt.convertCMToPixel(w); // 단위 변환
			this.wordArtStyle.setDimension(w, this.wordArtStyle.getDimensionHeight());
			if (this.widthHeightRatioFixedCheckBox.isSelected()) {
				double fixedRatio = this.getWidthRatioPercent() / 100.0; // 퍼센티지를
				// 실
				// 값으로
				// 변환
				double height = this.firstHeight * fixedRatio;
				this.wordArtStyle.setDimension(w, height);
				this.widthTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionWidth()) + "cm");
				this.heightTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionHeight()) + "cm");
				// 폭 비율을 높이 비율과 일치시킨다.
				this.widthRatioTextField.setText(" " + this.getWidthRatioPercent() + "%");
				this.heightRatioTextField.setText(" " + this.getWidthRatioPercent() + "%");
			} else {
				this.widthTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionWidth()) + "cm");
				this.widthRatioTextField.setText(" " + this.getWidthRatioPercent() + "%");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void heightTextField_mouseExited(MouseEvent e) {
		this.heightTextField_actionPerformed(null);
	}

	void widthTextField_mouseExited(MouseEvent e) {
		this.widthTextField_actionPerformed(null);
	}

	private boolean isHeightRatioIncreased(AdjustmentEvent e) {
		boolean value = false;
		if (this.preHeightRatioScrollBarValue > e.getValue()) { // 스크롤 바의 특성상 판별
			// 부호가 역으로 되어야
			// 한다.
			value = true;
		}
		this.preHeightRatioScrollBarValue = e.getValue();
		return value;
	}

	private void increaseHeightRatio(AdjustmentEvent e) {
		double heightRatio = this.wordArtStyle.getDimensionHeight() / this.firstHeight;
		if (this.isHeightRatioIncreased(e)) { // 높이 값을 증가 시키는 경우
			heightRatio += 0.01;
		} else { // 높이 값을 감소 시키는 경우
			heightRatio -= 0.01;
		}
		double w, h;

		h = this.firstHeight * heightRatio;

		if (this.widthHeightRatioFixedCheckBox.isSelected()) {
			w = this.firstWidth * heightRatio;
			this.wordArtStyle.setDimension(w, h);
			this.widthTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionWidth()) + "cm");
			this.widthRatioTextField.setText(" " + this.getHeightRatioPercent() + "%");
		} else {
			w = this.wordArtStyle.getDimensionWidth();
			this.wordArtStyle.setDimension(w, h);
		}

		this.heightTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionHeight()) + "cm");
		this.heightRatioTextField.setText(" " + this.getHeightRatioPercent() + "%");
	}

	void heightRatioScrollBar_adjustmentValueChanged(AdjustmentEvent e) {
		this.increaseHeightRatio(e);
	}

	private boolean isWidthRatioIncreased(AdjustmentEvent e) {
		boolean value = false;
		if (this.preWidthRatioScrollBarValue > e.getValue()) { // 스크롤 바의 특성상 판별
			// 부호가 역으로 되어야
			// 한다.
			value = true;
		}
		this.preWidthRatioScrollBarValue = e.getValue();
		return value;
	}

	private void increaseWidthRatio(AdjustmentEvent e) {
		double widthRatio = this.wordArtStyle.getDimensionWidth() / this.firstWidth;
		if (this.isWidthRatioIncreased(e)) { // 높이 값을 증가 시키는 경우
			widthRatio += 0.01;
		} else { // 높이 값을 감소 시키는 경우
			widthRatio -= 0.01;
		}
		double w, h;
		w = this.firstWidth * widthRatio;

		if (this.widthHeightRatioFixedCheckBox.isSelected()) {
			h = this.firstHeight * widthRatio;
			this.wordArtStyle.setDimension(w, h);
			this.heightTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionHeight()) + "cm");
			this.heightRatioTextField.setText(" " + this.getWidthRatioPercent() + "%");
		} else {
			h = this.wordArtStyle.getDimensionHeight();
			this.wordArtStyle.setDimension(w, h);
		}

		this.widthTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionWidth()) + "cm");
		this.widthRatioTextField.setText(" " + this.getWidthRatioPercent() + "%");
	}

	void widthRatioScrollBar_adjustmentValueChanged(AdjustmentEvent e) {
		this.increaseWidthRatio(e);
	}

	void heightRatioTextField_actionPerformed(ActionEvent e) {
		String hRatioStr = this.heightRatioTextField.getText();
		int pctIndex = hRatioStr.indexOf("%");
		if (pctIndex > -1) {
			hRatioStr = hRatioStr.substring(0, pctIndex);
		}
		hRatioStr = hRatioStr.trim();
		try {
			double heightRatio = Double.valueOf(hRatioStr).doubleValue() / 100.0; // 퍼센트를
			// 실
			// 값으로
			// 변환
			double w, h;
			h = this.firstHeight * heightRatio;

			if (this.widthHeightRatioFixedCheckBox.isSelected()) {
				w = this.firstWidth * heightRatio;
				this.wordArtStyle.setDimension(w, h);
				this.widthTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionWidth()) + "cm");
				this.widthRatioTextField.setText(" " + this.getHeightRatioPercent() + "%");
			} else {
				w = this.wordArtStyle.getDimensionWidth();
				this.wordArtStyle.setDimension(w, h);
			}

			this.heightTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionHeight()) + "cm");
			this.heightRatioTextField.setText(" " + this.getHeightRatioPercent() + "%");
		} catch (Exception ex) {
		}
	}

	void widthRatioTextField_actionPerformed(ActionEvent e) {
		String wRatioStr = this.widthRatioTextField.getText();
		int pctIndex = wRatioStr.indexOf("%");
		if (pctIndex > -1) {
			wRatioStr = wRatioStr.substring(0, pctIndex);
		}
		wRatioStr = wRatioStr.trim();
		try {
			double widthRatio = Double.valueOf(wRatioStr).doubleValue() / 100.0; // 퍼센트를
			// 실
			// 값으로
			// 변환
			double w, h;
			w = this.firstWidth * widthRatio;

			if (this.widthHeightRatioFixedCheckBox.isSelected()) {
				h = this.firstHeight * widthRatio;
				this.wordArtStyle.setDimension(w, h);
				this.heightTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionHeight()) + "cm");
				this.heightRatioTextField.setText(" " + this.getWidthRatioPercent() + "%");
			} else {
				h = this.wordArtStyle.getDimensionHeight();
				this.wordArtStyle.setDimension(w, h);
			}

			this.widthTextField.setText(" " + UnitUtil_WordArt.convertPixelToCM(wordArtStyle.getDimensionWidth()) + "cm");
			this.widthRatioTextField.setText(" " + this.getWidthRatioPercent() + "%");
		} catch (Exception ex) {
		}
	}

	void lineKindComboBox_actionPerformed(ActionEvent e) {
		int i = this.lineKindComboBox.getSelectedIndex();
		this.lineKindComboBoxRenderer.setSelectedIndex(i);
		this.wordArtStyle.setLineStroke(this.lineKindComboBoxRenderer.getStroke(i));
	}

	private boolean isLineWidthIncreased(AdjustmentEvent e) {
		boolean value = false;
		if (this.preLineWidthScrollBarValue > e.getValue()) { // 스크롤바의 특성상 부호가
			// 역이어야 한다.
			value = true;
		} else {
			value = false;
		}
		this.preLineWidthScrollBarValue = e.getValue();
		return value;
	}

	private void increaseLineWidth(AdjustmentEvent e) {
		double lw = this.wordArtStyle.getLineWidth();
		double dlw = UnitUtil_WordArt.convertPointToPixel(0.25);
		if (this.isLineWidthIncreased(e)) {
			lw += dlw;
		} else {
			lw -= dlw;
		}
		this.wordArtStyle.setLineWidth(lw);
		this.setLineWidthTextField();
	}

	private void setLineWidthTextField() {
		double lw = this.wordArtStyle.getLineWidth();
		double lwPnt = UnitUtil_WordArt.convertPixelToPoint(lw);
		lwPnt = ((int) (lwPnt * 100 + 0.5)) / 100.0;
		this.lineWidthTextField.setText(" " + lwPnt + " pt");
	}

	void lineWidthScrollBar_adjustmentValueChanged(AdjustmentEvent e) {
		this.increaseLineWidth(e);
	}

	void lineWidthTextField_actionPerformed(ActionEvent e) {
		String lwStr = this.lineWidthTextField.getText();
		int ptIndex = lwStr.indexOf("pt");
		if (ptIndex > -1) {
			lwStr = lwStr.substring(0, ptIndex);
		}
		lwStr = lwStr.trim();
		try {
			double lwPnt = Double.valueOf(lwStr).doubleValue();
			this.wordArtStyle.setLineWidth(UnitUtil_WordArt.convertPointToPixel(lwPnt));
			this.setLineWidthTextField();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void lineWidthTextField_mouseExited(MouseEvent e) {
		this.lineWidthTextField_actionPerformed(null);
	}

	// 선 칼라를 바꾼다.
	void lineColorComboBox_actionPerformed(ActionEvent e) {
		int index = this.lineColorComboBox.getSelectedIndex();
		if (index == 1) { // 선 없음을 선택하면, 선 칼라를 널로 설정한다.
			this.setLineColor(null);
		} else if (index == 2) { // 다른 색을 선택하면, 선 칼라를 변경한다.
			Component comp = WordArtGroupManager.currentWorkingManager.getTargetComponent();
			if (chooserLineColor == null) {
				chooserLineColor = this.wordArtStyle.getLineColor();
				if (chooserLineColor == null) {
					chooserLineColor = Color.black;
				}
			}

			Color c = JColorChooser.showDialog(comp, "색", chooserLineColor);

			if (c == null) {
				return;
			} else {
				chooserLineColor = c;
			}
			this.setLineColor(c);
		} else if (index == 3) { // 선 무늬를 설정하는 경우
			GraphicEffect refLineTexture = null;
			if (this.wordArtStyle != null) {
				refLineTexture = this.wordArtStyle.getFillEffect_LineTexture();
			}
			GraphicEffect lineTexure = WordArtLineTextureEditor.getNewLineTexture(refLineTexture);
			if (lineTexure != null) {
				this.wordArtStyle.setFillEffect_LineTexture(lineTexure);
				this.lineColorComboBoxRenderer.changeFillEffect(lineTexure);
			}
		}
		this.lineColorComboBox.setSelectedIndex(0);
	}

	public void setFillColor(Color c) {
		this.chooserFillColor = c;
		this.fillColorComboBoxRenderer.changeColor(c);
		this.wordArtStyle.setFillColor(c);
		this.fillColorComboBox.setSelectedIndex(0);
	}

	public void setLineColor(Color c) {
		this.lineColorComboBoxRenderer.changeColor(c);
		this.wordArtStyle.setLineColor(c);
	}

	// 반투명 색 설정
	void halfTransparentCheckBox_actionPerformed(ActionEvent e) {

		boolean selected = this.halfTransparentCheckBox.isSelected();

		if (selected) {
			Color c = this.chooserFillColor;

			if (c == null) {
				c = Color.black;
			}

			GraphicEffect fe = new GraphicEffect();
			fe.setHalfTransparent(c);

			this.setFillEffect(fe);
		} else {
			GraphicEffect fe = this.fillColorComboBoxRenderer.getGraphicEffect();
			Color c = this.chooserFillColor;
			if (fe != null && fe.getFirstGradientColor() != null) {
				c = fe.getFirstGradientColor();
			}
			if (c == null) {
				c = Color.black;
			}
			this.setFillColor(c);
		}

		this.fillColorComboBox.repaint();
	}
}