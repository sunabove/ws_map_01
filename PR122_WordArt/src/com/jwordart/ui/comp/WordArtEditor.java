package com.jwordart.ui.comp;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import com.jwordart.model.wordart.WordArt;
import com.jwordart.model.wordart.WordArtGroupManager;
import com.jwordart.ui.resource.Resource_WordArt;
import com.jwordart.util.WinUtil;

public class WordArtEditor extends JDialog {
	// Data.
	public static WordArtEditor wordArtEditor = new WordArtEditor();
	private boolean neverShown = true;
	private boolean popupMenuShow = false;
	private JCheckBoxMenuItem seletedAdjustPopupMenuItem; // 정렬 선택 메뉴 아이템
	private JCheckBoxMenuItem selectedCharacterIntervalJCheckBoxMenuItem; // 문자 간력 선택 메뉴 아템

	// GUI components.
	JPanel contentPane;
	JButton jButton1 = new JButton();
	JButton wordArtInsertButton = new JButton();
	BorderLayout borderLayout3 = new BorderLayout();
	JButton wordArtGalleryButton = new JButton();
	JButton wordArtRotationButton = new JButton();
	JButton wordArtStyleButton = new JButton();
	JButton wordArtKindButton = new JButton();
	JButton wordArtVerticalStringButton = new JButton();
	JButton wordArtSameHeightButton = new JButton();
	JButton wordArtCharacterIntervalButton = new JButton();
	JButton wordArtAdjustButton = new JButton();
	JButton wordArtInsertButton1 = new JButton();
	JButton wordArtStringEditButton = new JButton();
	JPanel jPanel9 = new JPanel();
	BorderLayout borderLayout6 = new BorderLayout();
	JPanel jPanel10 = new JPanel();
	JPanel jPanel11 = new JPanel();
	BorderLayout borderLayout7 = new BorderLayout();
	JPanel jPanel2 = new JPanel();
	BorderLayout borderLayout1 = new BorderLayout();
	JPanel jPanel5 = new JPanel();
	BorderLayout borderLayout4 = new BorderLayout();
	JLabel jLabel2 = new JLabel();
	JLabel jLabel3 = new JLabel();
	JPanel jPanel6 = new JPanel();
	BorderLayout borderLayout5 = new BorderLayout();
	JLabel jLabel4 = new JLabel();
	JPanel jPanel7 = new JPanel();
	BorderLayout borderLayout8 = new BorderLayout();
	JPanel jPanel8 = new JPanel();
	GridLayout gridLayout3 = new GridLayout();
	GridLayout gridLayout4 = new GridLayout();
	JPopupMenu wordCharacterIntervalPopupMenu = new JPopupMenu();
	JCheckBoxMenuItem veryNarrowGapCheckBoxMenuItem = new JCheckBoxMenuItem();
	JCheckBoxMenuItem narrowGapCheckBoxMenuItem = new JCheckBoxMenuItem();
	JCheckBoxMenuItem standardGapCheckBoxMenuItem = new JCheckBoxMenuItem();
	JCheckBoxMenuItem wideGapCheckBoxMenuItem = new JCheckBoxMenuItem();
	JCheckBoxMenuItem veryWideGapCheckBoxMenuItem = new JCheckBoxMenuItem();
	JCheckBoxMenuItem jCheckBoxMenuItem6 = new JCheckBoxMenuItem();
	JPopupMenu wordAdjustPopupMenu = new JPopupMenu();
	JCheckBoxMenuItem leftAdjustmentCheckBoxMenuItem = new JCheckBoxMenuItem();
	JCheckBoxMenuItem centerAdjustCheckBoxMenuItem = new JCheckBoxMenuItem();
	JCheckBoxMenuItem rightAdjustmentCheckBoxMenuItem = new JCheckBoxMenuItem();
	JCheckBoxMenuItem wordAdjustmentCheckBoxMenuItem = new JCheckBoxMenuItem();
	JCheckBoxMenuItem stringAdjustmentCheckBoxMenuItem = new JCheckBoxMenuItem();
	JCheckBoxMenuItem bothAdjustmentCheckBoxMenuItem = new JCheckBoxMenuItem();
	JPopupMenu wordArtEditorPopupMenu = new JPopupMenu();
	JMenuItem cutMenuItem = new JMenuItem();
	JMenuItem copyMenuItem = new JMenuItem();
	JMenuItem pasteMenuItem = new JMenuItem();
	JMenuItem stringEditMenuItem = new JMenuItem();
	JMenuItem toolHideMenuItem = new JMenuItem();
	JMenu jMenu1 = new JMenu();
	JMenuItem groupMenuItem = new JMenuItem();
	JMenuItem unGroupMenuItem = new JMenuItem();
	JMenuItem reGroupMenuItem = new JMenuItem();
	JMenu jMenu2 = new JMenu();
	JMenuItem atFirstMenuItem = new JMenuItem();
	JMenuItem atLastMenuItem = new JMenuItem();
	JMenuItem forewardMenuItem = new JMenuItem();
	JMenuItem backwardMenuItem = new JMenuItem();
	JMenuItem stringForewardMenuItem = new JMenuItem();
	JMenuItem stringBackwardMenuItem = new JMenuItem();
	JMenuItem jMenuItem15 = new JMenuItem();
	JMenuItem wordArtStyleMenuItem = new JMenuItem();

	// Construct the frame
	public WordArtEditor() {
		this.enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		try {
			this.jbInit();
			// Code by sbmoon.
			this.selectedCharacterIntervalJCheckBoxMenuItem = this.standardGapCheckBoxMenuItem;
			this.seletedAdjustPopupMenuItem = this.centerAdjustCheckBoxMenuItem;
			// End of code by sbmoon.
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Component initialization
	private void jbInit() throws Exception {
		contentPane = (JPanel) this.getContentPane();
		contentPane.setPreferredSize(new Dimension(354, 40));
		contentPane.setLayout(borderLayout3);
		this.setResizable(false);
		this.setSize(new Dimension(341, 70));
		this.setTitle("WordArt");
		jButton1.setText("jButton1");
		wordArtInsertButton.setBorder(null);
		wordArtInsertButton.setPreferredSize(new Dimension(40, 28));
		wordArtInsertButton.setRequestFocusEnabled(false);
		wordArtInsertButton.setToolTipText("WordArt 삽입");
		wordArtInsertButton.setIcon(Resource_WordArt.getIcon("wordart", "wordart_insert.gif"));
		wordArtInsertButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.wordArtInsertButton_actionPerformed(e);
			}
		});
		wordArtGalleryButton.setBorder(null);
		wordArtGalleryButton.setPreferredSize(new Dimension(24, 28));
		wordArtGalleryButton.setRequestFocusEnabled(false);
		wordArtGalleryButton.setToolTipText("WordArt Gallery");
		wordArtGalleryButton.setIcon(Resource_WordArt.getIcon("wordart", "wordart_gallery.gif"));
		wordArtGalleryButton.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtEditor.this.wordArtGalleryButton_mousePressed(e);
			}
		});
		wordArtGalleryButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				WordArtEditor.this.wordArtGalleryButton_mouseMoved(e);
			}
		});
		wordArtGalleryButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.wordArtGalleryButton_actionPerformed(e);
			}
		});
		wordArtRotationButton.setBorder(null);
		wordArtRotationButton.setPreferredSize(new Dimension(22, 28));
		wordArtRotationButton.setRequestFocusEnabled(false);
		wordArtRotationButton.setToolTipText("자유 각도 회전");
		wordArtRotationButton.setIcon(Resource_WordArt.getIcon("wordart", "wordart_rotation.gif"));
		wordArtRotationButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				WordArtEditor.this.wordArtRotationButton_mouseMoved(e);
			}
		});
		wordArtRotationButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.wordArtRotationButton_actionPerformed(e);
			}
		});
		wordArtStyleButton.setBorder(null);
		wordArtStyleButton.setPreferredSize(new Dimension(22, 28));
		wordArtStyleButton.setRequestFocusEnabled(false);
		wordArtStyleButton.setToolTipText("WordArt 서식");
		wordArtStyleButton.setIcon(Resource_WordArt.getIcon("wordart", "wordart_style.gif"));
		wordArtStyleButton.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtEditor.this.wordArtStyleButton_mousePressed(e);
			}
		});
		wordArtStyleButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.wordArtStyleButton_actionPerformed(e);
			}
		});
		wordArtStyleButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				WordArtEditor.this.wordArtStyleButton_mouseMoved(e);
			}
		});
		wordArtKindButton.setBorder(null);
		wordArtKindButton.setPreferredSize(new Dimension(24, 28));
		wordArtKindButton.setRequestFocusEnabled(false);
		wordArtKindButton.setToolTipText("WordArt 종류");
		wordArtKindButton.setIcon(Resource_WordArt.getIcon("wordart", "wordart_kind.gif"));
		wordArtKindButton.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtEditor.this.wordArtKindButton_mousePressed(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				WordArtEditor.this.wordArtKindButton_mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				WordArtEditor.this.wordArtKindButton_mouseExited(e);
			}
		});
		wordArtKindButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.wordArtKindButton_actionPerformed(e);
			}
		});
		wordArtKindButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				WordArtEditor.this.wordArtKindButton_mouseMoved(e);
			}
		});
		wordArtVerticalStringButton.setBorder(null);
		wordArtVerticalStringButton.setPreferredSize(new Dimension(22, 28));
		wordArtVerticalStringButton.setRequestFocusEnabled(false);
		wordArtVerticalStringButton.setToolTipText("WordArt 수직 문자열");
		wordArtVerticalStringButton.setIcon(Resource_WordArt.getIcon("wordart", "wordart_verticalstring.gif"));
		wordArtVerticalStringButton.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtEditor.this.wordArtVerticalStringButton_mousePressed(e);
			}
		});
		wordArtVerticalStringButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.wordArtVerticalStringButton_actionPerformed(e);
			}
		});
		wordArtVerticalStringButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				WordArtEditor.this.wordArtVerticalStringButton_mouseMoved(e);
			}
		});
		wordArtSameHeightButton.setBorder(null);
		wordArtSameHeightButton.setPreferredSize(new Dimension(24, 28));
		wordArtSameHeightButton.setRequestFocusEnabled(false);
		wordArtSameHeightButton.setToolTipText("WordArt와 같은 문자 높이");
		wordArtSameHeightButton.setIcon(Resource_WordArt.getIcon("wordart", "wordart_samehight.gif"));
		wordArtSameHeightButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.wordArtSameHeightButton_actionPerformed(e);
			}
		});
		wordArtSameHeightButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				WordArtEditor.this.wordArtSameHeightButton_mouseMoved(e);
			}
		});
		wordArtCharacterIntervalButton.setBorder(null);
		wordArtCharacterIntervalButton.setPreferredSize(new Dimension(27, 28));
		wordArtCharacterIntervalButton.setRequestFocusEnabled(false);
		wordArtCharacterIntervalButton.setToolTipText("WordArt 글자 간격");
		wordArtCharacterIntervalButton.setIcon(Resource_WordArt.getIcon("wordart", "wordart_interval.gif"));
		wordArtCharacterIntervalButton.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtEditor.this.wordArtCharacterIntervalButton_mousePressed(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				WordArtEditor.this.wordArtCharacterIntervalButton_mouseEntered(e);
			}
		});
		wordArtCharacterIntervalButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.wordArtCharacterIntervalButton_actionPerformed(e);
			}
		});
		wordArtCharacterIntervalButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				WordArtEditor.this.wordArtCharacterIntervalButton_mouseMoved(e);
			}
		});
		wordArtAdjustButton.setBorder(null);
		wordArtAdjustButton.setPreferredSize(new Dimension(23, 28));
		wordArtAdjustButton.setRequestFocusEnabled(false);
		wordArtAdjustButton.setToolTipText("WordArt 정렬");
		wordArtAdjustButton.setIcon(Resource_WordArt.getIcon("wordart", "wordart_adjust.gif"));
		wordArtAdjustButton.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtEditor.this.wordArtAdjustButton_mousePressed(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				WordArtEditor.this.wordArtAdjustButton_mouseExited(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				WordArtEditor.this.wordArtAdjustButton_mouseEntered(e);
			}
		});
		wordArtAdjustButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.wordArtAdjustButton_actionPerformed(e);
			}
		});
		wordArtAdjustButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				WordArtEditor.this.wordArtAdjustButton_mouseMoved(e);
			}
		});
		wordArtInsertButton1.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.wordArtInsertButton1_actionPerformed(e);
			}
		});
		wordArtInsertButton1.setIcon(Resource_WordArt.getIcon("wordart", "wordart_insert.gif"));
		wordArtInsertButton1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtEditor.this.wordArtInsertButton1_mousePressed(e);
			}
		});
		wordArtInsertButton1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				WordArtEditor.this.wordArtInsertButton1_mouseMoved(e);
			}
		});
		wordArtInsertButton1.setToolTipText("WordArt 삽입");
		wordArtInsertButton1.setRequestFocusEnabled(false);
		wordArtInsertButton1.setBorder(null);
		wordArtInsertButton1.setMinimumSize(new Dimension(1, 1));
		wordArtInsertButton1.setPreferredSize(new Dimension(11, 10));
		wordArtStringEditButton.setBorder(null);
		wordArtStringEditButton.setPreferredSize(new Dimension(115, 10));
		wordArtStringEditButton.setRequestFocusEnabled(false);
		wordArtStringEditButton.setToolTipText("문자열 편집");
		wordArtStringEditButton.setMnemonic('X');
		wordArtStringEditButton.setText("문자열 편집(X)...");
		wordArtStringEditButton.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				WordArtEditor.this.wordArtStringEditButton_mouseMoved(e);
			}
		});
		wordArtStringEditButton.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				WordArtEditor.this.wordArtStringEditButton_mouseEntered(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				WordArtEditor.this.wordArtStringEditButton_mousePressed(e);
			}
		});
		wordArtStringEditButton.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.wordArtStringEditButton_actionPerformed(e);
			}
		});
		jPanel9.setBackground(Color.red);
		jPanel9.setPreferredSize(new Dimension(10, 30));
		jPanel9.setLayout(borderLayout6);
		jPanel10.setBackground(Color.red);
		jPanel10.setPreferredSize(new Dimension(135, 10));
		jPanel10.setLayout(borderLayout7);
		jPanel11.setBackground(Color.pink);
		jPanel11.setLayout(borderLayout1);
		jPanel5.setBackground(Color.pink);
		jPanel5.setLayout(gridLayout4);
		jPanel2.setBackground(Color.orange);
		jPanel2.setPreferredSize(new Dimension(100, 10));
		jPanel2.setLayout(borderLayout4);
		jLabel2.setPreferredSize(new Dimension(2, 22));
		jLabel2.setMinimumSize(new Dimension(1, 1));
		jLabel2.setBorder(BorderFactory.createEtchedBorder());
		jLabel2.setEnabled(false);
		jLabel3.setPreferredSize(new Dimension(2, 22));
		jLabel3.setMinimumSize(new Dimension(1, 1));
		jLabel3.setBorder(BorderFactory.createEtchedBorder());
		jLabel3.setEnabled(false);
		jPanel6.setBackground(Color.red);
		jPanel6.setPreferredSize(new Dimension(30, 10));
		jPanel6.setLayout(borderLayout5);
		jLabel4.setEnabled(false);
		jLabel4.setBorder(BorderFactory.createEtchedBorder());
		jLabel4.setMinimumSize(new Dimension(1, 1));
		jLabel4.setPreferredSize(new Dimension(2, 22));
		jPanel7.setLayout(borderLayout8);
		jPanel8.setLayout(gridLayout3);
		veryNarrowGapCheckBoxMenuItem.setMnemonic('I');
		veryNarrowGapCheckBoxMenuItem.setText("매우 좁게(I)");
		veryNarrowGapCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.veryNarrowGapCheckBoxMenuItem_actionPerformed(e);
			}
		});
		narrowGapCheckBoxMenuItem.setMnemonic('T');
		narrowGapCheckBoxMenuItem.setText("좁게(T)");
		narrowGapCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.narrowGapCheckBoxMenuItem_actionPerformed(e);
			}
		});
		standardGapCheckBoxMenuItem.setSelected(true);
		standardGapCheckBoxMenuItem.setMnemonic('N');
		standardGapCheckBoxMenuItem.setText("표준(N)");
		standardGapCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.standardGapCheckBoxMenuItem_actionPerformed(e);
			}
		});
		wideGapCheckBoxMenuItem.setMnemonic('L');
		wideGapCheckBoxMenuItem.setText("넓게(L)");
		wideGapCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.wideGapCheckBoxMenuItem_actionPerformed(e);
			}
		});
		veryWideGapCheckBoxMenuItem.setMnemonic('V');
		veryWideGapCheckBoxMenuItem.setText("매우 넓게(V)");
		veryWideGapCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.veryWideGapCheckBoxMenuItem_actionPerformed(e);
			}
		});
		jCheckBoxMenuItem6.setSelected(true);
		jCheckBoxMenuItem6.setMnemonic('K');
		jCheckBoxMenuItem6.setText("글자 장식 꼬리 쌍(K)");
		leftAdjustmentCheckBoxMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "left_adjust.gif"));
		leftAdjustmentCheckBoxMenuItem.setText("왼쪽 맞춤(L)");
		leftAdjustmentCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.leftAdjustmentCheckBoxMenuItem_actionPerformed(e);
			}
		});
		centerAdjustCheckBoxMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "center_adjust.gif"));
		centerAdjustCheckBoxMenuItem.setSelected(true);
		centerAdjustCheckBoxMenuItem.setText("가운데 맞춤(C)");
		centerAdjustCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.centerAdjustCheckBoxMenuItem_actionPerformed(e);
			}
		});
		rightAdjustmentCheckBoxMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "right_adjust.gif"));
		rightAdjustmentCheckBoxMenuItem.setText("오른쪽 맞춤(R)");
		rightAdjustmentCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.rightAdjustmentCheckBoxMenuItem_actionPerformed(e);
			}
		});
		wordAdjustmentCheckBoxMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "word_adjust.gif"));
		wordAdjustmentCheckBoxMenuItem.setText("Word 정렬(W)");
		wordAdjustmentCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.wordAdjustmentCheckBoxMenuItem_actionPerformed(e);
			}
		});
		stringAdjustmentCheckBoxMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "character_adjust.gif"));
		stringAdjustmentCheckBoxMenuItem.setText("문자 정렬(T)");
		stringAdjustmentCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.stringAdjustmentCheckBoxMenuItem_actionPerformed(e);
			}
		});
		bothAdjustmentCheckBoxMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "both_adjust.gif"));
		bothAdjustmentCheckBoxMenuItem.setText("양쪽 늘이기(S)");
		bothAdjustmentCheckBoxMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.bothAdjustmentCheckBoxMenuItem_actionPerformed(e);
			}
		});
		wordAdjustPopupMenu.setBackground(SystemColor.menu);
		wordCharacterIntervalPopupMenu.setBackground(SystemColor.menu);
		cutMenuItem.setBorder(BorderFactory.createRaisedBevelBorder());
		cutMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		cutMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "cut.gif"));
		cutMenuItem.setMnemonic('T');
		cutMenuItem.setText("잘라내기(T)");
		cutMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.cutMenuItem_actionPerformed(e);
			}
		});
		copyMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		copyMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "copy.gif"));
		copyMenuItem.setMnemonic('C');
		copyMenuItem.setText("복사(C)");
		copyMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.copyMenuItem_actionPerformed(e);
			}
		});
		pasteMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		pasteMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "paste.gif"));
		pasteMenuItem.setMnemonic('P');
		pasteMenuItem.setText("붙여넣기(P)");
		pasteMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.pasteMenuItem_actionPerformed(e);
			}
		});
		stringEditMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		stringEditMenuItem.setHorizontalTextPosition(SwingConstants.LEFT);
		stringEditMenuItem.setMnemonic('X');
		stringEditMenuItem.setText("문자열 편집(X)...");
		stringEditMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.stringEditMenuItem_actionPerformed(e);
			}
		});
		toolHideMenuItem.setPreferredSize(new Dimension(187, 23));
		toolHideMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		toolHideMenuItem.setHorizontalTextPosition(SwingConstants.LEFT);
		toolHideMenuItem.setMnemonic('R');
		toolHideMenuItem.setText("WordArt 도구 모음 숨기기(R)");
		toolHideMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.toolHideMenuItem_actionPerformed(e);
			}
		});
		jMenu1.setMnemonic('G');
		jMenu1.setText("그룹화(G)");
		jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				WordArtEditor.this.jMenu1_mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				WordArtEditor.this.jMenu1_mouseExited(e);
			}
		});
		groupMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		groupMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "group_en.gif"));
		groupMenuItem.setMnemonic('G');
		groupMenuItem.setText("그룹(G)");
		unGroupMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		unGroupMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "ungroup_en.gif"));
		unGroupMenuItem.setMnemonic('U');
		unGroupMenuItem.setText("그룹 해제(U)");
		reGroupMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		reGroupMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "regroup_en.gif"));
		reGroupMenuItem.setMnemonic('O');
		reGroupMenuItem.setText("재 그룹(O)");
		jMenu2.setText("정렬(R)");
		jMenu2.addMouseListener(new java.awt.event.MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				WordArtEditor.this.jMenu2_mouseEntered(e);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				WordArtEditor.this.jMenu2_mouseExited(e);
			}
		});
		atFirstMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		atFirstMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "at_first.gif"));
		atFirstMenuItem.setText("맨앞으로 가져오기 (T)");
		atLastMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		atLastMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "at_last.gif"));
		atLastMenuItem.setText("맨뒤로 보내기(K)");
		forewardMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		forewardMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "foreward.gif"));
		forewardMenuItem.setText("앞으로 가져오기(F)");
		backwardMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		backwardMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "backward.gif"));
		backwardMenuItem.setText("뒤로 보내기(B)");
		stringForewardMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		stringForewardMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "string_foreward.gif"));
		stringForewardMenuItem.setText("문자열 앞으로 가져오기(R)");
		stringBackwardMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		stringBackwardMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "string_backward.gif"));
		stringBackwardMenuItem.setText("문자열 뒤로 보내기(H)");
		jMenuItem15.setMnemonic('D');
		jMenuItem15.setText("기본 도형 설정(D)");
		wordArtStyleMenuItem.setHorizontalAlignment(SwingConstants.LEFT);
		wordArtStyleMenuItem.setIcon(Resource_WordArt.getIcon("wordart", "wordart_style.gif"));
		wordArtStyleMenuItem.setMnemonic('O');
		wordArtStyleMenuItem.setText("WordArt 서식(O)...");
		wordArtStyleMenuItem.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(ActionEvent e) {
				WordArtEditor.this.wordArtStyleMenuItem_actionPerformed(e);
			}
		});
		wordArtEditorPopupMenu.setBorder(BorderFactory.createEtchedBorder());
		wordArtEditorPopupMenu.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {

			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
			}

			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				WordArtEditor.this.wordArtEditorPopupMenu_popupMenuWillBecomeInvisible(e);
			}

			public void popupMenuCanceled(PopupMenuEvent e) {
				WordArtEditor.this.wordArtEditorPopupMenu_popupMenuCanceled(e);
			}
		});
		wordArtEditorPopupMenu.addComponentListener(new java.awt.event.ComponentAdapter() {

			@Override
			public void componentHidden(ComponentEvent e) {
				WordArtEditor.this.wordArtEditorPopupMenu_componentHidden(e);
			}
		});
		contentPane.add(jPanel9, BorderLayout.CENTER);
		jPanel9.add(jPanel10, BorderLayout.WEST);
		jPanel10.add(jPanel6, BorderLayout.WEST);
		jPanel6.add(wordArtInsertButton1, BorderLayout.CENTER);
		jPanel6.add(jLabel4, BorderLayout.EAST);
		jPanel10.add(jPanel7, BorderLayout.CENTER);
		jPanel7.add(wordArtStringEditButton, BorderLayout.CENTER);
		jPanel9.add(jPanel11, BorderLayout.CENTER);
		jPanel11.add(jPanel2, BorderLayout.WEST);
		jPanel2.add(jLabel2, BorderLayout.EAST);
		jPanel2.add(jLabel3, BorderLayout.WEST);
		jPanel2.add(jPanel8, BorderLayout.CENTER);
		jPanel8.add(wordArtGalleryButton, null);
		jPanel8.add(wordArtStyleButton, null);
		jPanel8.add(wordArtKindButton, null);
		jPanel8.add(wordArtRotationButton, null);
		jPanel11.add(jPanel5, BorderLayout.CENTER);
		jPanel5.add(wordArtSameHeightButton, null);
		jPanel5.add(wordArtVerticalStringButton, null);
		jPanel5.add(wordArtAdjustButton, null);
		jPanel5.add(wordArtCharacterIntervalButton, null);
		wordCharacterIntervalPopupMenu.add(veryNarrowGapCheckBoxMenuItem);
		wordCharacterIntervalPopupMenu.add(narrowGapCheckBoxMenuItem);
		wordCharacterIntervalPopupMenu.add(standardGapCheckBoxMenuItem);
		wordCharacterIntervalPopupMenu.add(wideGapCheckBoxMenuItem);
		wordCharacterIntervalPopupMenu.add(veryWideGapCheckBoxMenuItem);
		wordCharacterIntervalPopupMenu.addSeparator();
		wordCharacterIntervalPopupMenu.add(jCheckBoxMenuItem6);
		wordAdjustPopupMenu.add(leftAdjustmentCheckBoxMenuItem);
		wordAdjustPopupMenu.add(centerAdjustCheckBoxMenuItem);
		wordAdjustPopupMenu.add(rightAdjustmentCheckBoxMenuItem);
		wordAdjustPopupMenu.add(wordAdjustmentCheckBoxMenuItem);
		wordAdjustPopupMenu.add(stringAdjustmentCheckBoxMenuItem);
		wordAdjustPopupMenu.add(bothAdjustmentCheckBoxMenuItem);
		wordArtEditorPopupMenu.add(cutMenuItem);
		wordArtEditorPopupMenu.add(copyMenuItem);
		wordArtEditorPopupMenu.add(pasteMenuItem);
		wordArtEditorPopupMenu.addSeparator();
		wordArtEditorPopupMenu.add(stringEditMenuItem);
		wordArtEditorPopupMenu.add(toolHideMenuItem);
		wordArtEditorPopupMenu.addSeparator();
		wordArtEditorPopupMenu.add(jMenu1);
		wordArtEditorPopupMenu.add(jMenu2);
		wordArtEditorPopupMenu.addSeparator();
		wordArtEditorPopupMenu.add(jMenuItem15);
		wordArtEditorPopupMenu.add(wordArtStyleMenuItem);
		jMenu1.add(groupMenuItem);
		jMenu1.add(unGroupMenuItem);
		jMenu1.add(reGroupMenuItem);
		jMenu2.add(atFirstMenuItem);
		jMenu2.add(atLastMenuItem);
		jMenu2.add(forewardMenuItem);
		jMenu2.add(backwardMenuItem);
		jMenu2.add(stringForewardMenuItem);
		jMenu2.add(stringBackwardMenuItem);
	}

	// Overridden so we can exit when window is closed
	@Override
	protected void processWindowEvent(WindowEvent e) {
		super.processWindowEvent(e);
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			// System.exit(0);
		}
	}

	void wordArtInsertButton_actionPerformed(ActionEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtGalleryButton_actionPerformed(ActionEvent e) {
		this.draw3DRect(e, true);
		WordArtGroupManager.changeWordArtGallery();
	}

	void wordArtInsertButton1_actionPerformed(ActionEvent e) {
		this.draw3DRect(e, true);
		// Add new word art.
		new Thread() {
			@Override
			public void run() {
				com.jwordart.model.wordart.WordArtGroupManager
						.getNewWordArt(com.jwordart.model.wordart.WordArtGroupManager.currentWorkingManager
								.getTargetComponent());
			}
		}.start();
	}

	void wordArtStringEditButton_actionPerformed(ActionEvent e) {
		this.draw3DRect(e, true);
		com.jwordart.model.wordart.WordArtGroupManager.editWordArtString();
	}

	void wordArtStringEditButton_mouseEntered(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void draw3DRect(AWTEvent e, boolean b) {
		Component comp = (Component) e.getSource();
		// 디스에이블 된 버튼은 3차원 사각형을 그리지 않는다.
		if (!comp.isEnabled()) {
			return;
		}
		// 3차원 사각형을 그린다.
		Graphics2D g2 = (Graphics2D) comp.getGraphics().create();
		int w = comp.getWidth(), h = comp.getHeight();
		g2.draw3DRect(1, 1, w - 2, h - 2, b);
	}

	void wordArtStringEditButton_mouseMoved(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtGalleryButton_mouseMoved(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	// 그룹 매니저를 로테이션 모드로 전환한다.
	void wordArtRotationButton_actionPerformed(ActionEvent e) {
		this.draw3DRect(e, true);
		WordArtGroupManager manager = WordArtGroupManager.currentWorkingManager;
		if (manager != null) {
			if (manager.getWorkMode() == WordArtGroupManager.ROTATE_MODE) {
				manager.setWorkMode(WordArtGroupManager.WAIT_MODE);
			} else {
				manager.setWorkMode(WordArtGroupManager.ROTATE_MODE);
			}
			manager.repaint();
			manager.getTargetComponent().requestFocus();
		}
	}

	void wordArtRotationButton_mouseMoved(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtStyleButton_mouseMoved(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtKindButton_mouseMoved(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtVerticalStringButton_mouseMoved(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtSameHeightButton_mouseMoved(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtCharacterIntervalButton_mouseMoved(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtAdjustButton_mouseMoved(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtInsertButton1_mouseMoved(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtStyleButton_actionPerformed(ActionEvent e) {
		this.draw3DRect(e, true);
		WordArtGroupManager.changeWordArtStyle();
	}

	void wordArtKindButton_actionPerformed(ActionEvent e) {
		this.draw3DRect(e, true);
		if (this.popupMenuShow) {
			this.popupMenuShow = false;
		} else {
			this.popupMenuShow = true;
		}
		this.showWordArtMappingTypeSelector();
	}

	void wordArtVerticalStringButton_actionPerformed(ActionEvent e) {
		this.draw3DRect(e, true);
		WordArtGroupManager.toggleSeletecWordArtsVertical();
	}

	void wordArtSameHeightButton_actionPerformed(ActionEvent e) {
		this.draw3DRect(e, true);
		WordArtGroupManager.toggleSelectedWordArtsSameCharacterHeight();
	}

	void wordArtCharacterIntervalButton_actionPerformed(ActionEvent e) {
		this.draw3DRect(e, true);
		if (this.popupMenuShow) {
			this.popupMenuShow = false;
		} else {
			this.popupMenuShow = true;
		}
		if (this.popupMenuShow) {
			this.wordCharacterIntervalPopupMenu.show(this.wordArtAdjustButton, 0, this.wordArtAdjustButton.getHeight());
		}
	}

	void wordArtAdjustButton_actionPerformed(ActionEvent e) {
		this.draw3DRect(e, true);
		if (this.popupMenuShow) {
			this.popupMenuShow = false;
		} else {
			this.popupMenuShow = true;
		}
		if (this.popupMenuShow) {
			this.wordAdjustPopupMenu.show(this.wordArtAdjustButton, 0, this.wordArtAdjustButton.getHeight());
		}
	}

	void wordArtInsertButton1_mousePressed(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtStringEditButton_mousePressed(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtGalleryButton_mousePressed(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtStyleButton_mousePressed(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtKindButton_mousePressed(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtVerticalStringButton_mousePressed(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtCharacterIntervalButton_mousePressed(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	void wordArtAdjustButton_mousePressed(MouseEvent e) {
		this.draw3DRect(e, true);
	}

	public static void setEditingMode(boolean b) {
		if (b) {
			wordArtEditor.setVisible(true);
		} else {
			wordArtEditor.setVisible(false);
		}
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			if (neverShown) {
				this.reLocate();
				neverShown = false;
			}
			super.setVisible(true);
			super.toFront();
		} else {
			super.setVisible(b);
		}
	}

	public boolean isNeverShown() {
		return this.neverShown;
	}

	public void showWordArtEditorPopupMenu(MouseEvent e) {

		if (e == null) {
			return;
		}

		this.wordArtEditorPopupMenu.show((Component) e.getSource(), e.getX(), e.getY());

	}

	public void reLocate() {
		WinUtil.locateOnTheScreenCenter(this);
	}

	// Set string edit button enabled or disabled.
	public static void setStringEditButtonEnabledOrDisabled() {
		boolean b = true;
		WordArtGroupManager manager = WordArtGroupManager.currentWorkingManager;
		if (manager == null) {
			return;
		}
		if (manager.getSelectedWordArts().length == 1) {
			b = true;
		} else {
			b = false;
		}
		if (b) {
			wordArtEditor.wordArtStringEditButton.setEnabled(true);
		} else {
			wordArtEditor.wordArtStringEditButton.setEnabled(false);
		}
	}

	public static void showEditorOnFront() {
		if (!wordArtEditor.isVisible()) {
			wordArtEditor.setVisible(true);
		}
		// wordArtEditor.toFront();
		// WordArtGroupManager.currentWorkingManager.getTargetComponent().requestFocus();
	}

	public static void hideEditor() {
		if (wordArtEditor.isVisible()) {
			wordArtEditor.setVisible(false);
		}
	}

	void wordArtAdjustButton_mouseExited(MouseEvent e) {
	}

	void wordArtAdjustButton_mouseEntered(MouseEvent e) {
		if (this.popupMenuShow) {
			this.wordAdjustPopupMenu.show(this.wordArtAdjustButton, 0, this.wordArtAdjustButton.getHeight());
		}
	}

	void clickOnWordArtCharacterIntervalPopupMenu(AWTEvent e, double glyphGapRatio) {
		if (this.selectedCharacterIntervalJCheckBoxMenuItem != null) {
			this.selectedCharacterIntervalJCheckBoxMenuItem.setSelected(false);
		}
		this.selectedCharacterIntervalJCheckBoxMenuItem = (JCheckBoxMenuItem) e.getSource();
		WordArtGroupManager.setSelectedWordArtsGlyphGap(glyphGapRatio);
	}

	void veryNarrowGapCheckBoxMenuItem_actionPerformed(ActionEvent e) {
		this.clickOnWordArtCharacterIntervalPopupMenu(e, 0.8);
	}

	void narrowGapCheckBoxMenuItem_actionPerformed(ActionEvent e) {
		this.clickOnWordArtCharacterIntervalPopupMenu(e, 0.9);
	}

	void standardGapCheckBoxMenuItem_actionPerformed(ActionEvent e) {
		this.clickOnWordArtCharacterIntervalPopupMenu(e, 1.0);
	}

	void wideGapCheckBoxMenuItem_actionPerformed(ActionEvent e) {
		this.clickOnWordArtCharacterIntervalPopupMenu(e, 1.2);
	}

	void veryWideGapCheckBoxMenuItem_actionPerformed(ActionEvent e) {
		this.clickOnWordArtCharacterIntervalPopupMenu(e, 1.5);
	}

	void wordArtCharacterIntervalButton_mouseEntered(MouseEvent e) {
		if (this.popupMenuShow) {
			this.wordCharacterIntervalPopupMenu.show(this.wordArtCharacterIntervalButton, 0, this.wordArtCharacterIntervalButton
					.getHeight());
		}
	}

	void clickOnWordArtAdjustPopupMenu(AWTEvent e, int adjustmentType) {
		if (this.seletedAdjustPopupMenuItem != null) {
			this.seletedAdjustPopupMenuItem.setSelected(false);
		}
		this.seletedAdjustPopupMenuItem = (JCheckBoxMenuItem) e.getSource();
		WordArtGroupManager.changeAdjustmentType(adjustmentType);
	}

	void leftAdjustmentCheckBoxMenuItem_actionPerformed(ActionEvent e) {
		this.clickOnWordArtAdjustPopupMenu(e, WordArt.LEFT);
	}

	void centerAdjustCheckBoxMenuItem_actionPerformed(ActionEvent e) {
		this.clickOnWordArtAdjustPopupMenu(e, WordArt.CENTER);
	}

	void rightAdjustmentCheckBoxMenuItem_actionPerformed(ActionEvent e) {
		this.clickOnWordArtAdjustPopupMenu(e, WordArt.RIGHT);
	}

	void wordAdjustmentCheckBoxMenuItem_actionPerformed(ActionEvent e) {
		this.clickOnWordArtAdjustPopupMenu(e, WordArt.WORD);
	}

	void stringAdjustmentCheckBoxMenuItem_actionPerformed(ActionEvent e) {
		this.clickOnWordArtAdjustPopupMenu(e, WordArt.CHARACTER);
	}

	void bothAdjustmentCheckBoxMenuItem_actionPerformed(ActionEvent e) {
		this.clickOnWordArtAdjustPopupMenu(e, WordArt.BOTH);
	}

	void stringEditMenuItem_actionPerformed(ActionEvent e) {
		com.jwordart.model.wordart.WordArtGroupManager.editWordArtString();
	}

	void toolHideMenuItem_actionPerformed(ActionEvent e) {
		this.setVisible(false);
	}

	void wordArtStyleMenuItem_actionPerformed(ActionEvent e) {
		WordArtGroupManager.changeWordArtStyle();
	}

	public JPopupMenu getWordArtEditorPopupMenu() {
		return this.wordArtEditorPopupMenu;
	}

	void jMenu1_mouseEntered(MouseEvent e) {
		this.wordArtEditorPopupMenu.repaint();
	}

	void jMenu2_mouseEntered(MouseEvent e) {
		this.wordArtEditorPopupMenu.repaint();
	}

	void jMenu1_mouseExited(MouseEvent e) {
		this.wordArtEditorPopupMenu.repaint();
	}

	void jMenu2_mouseExited(MouseEvent e) {
		this.wordArtEditorPopupMenu.repaint();
	}

	void wordArtEditorPopupMenu_componentHidden(ComponentEvent e) {

	}

	void cutMenuItem_actionPerformed(ActionEvent e) {
		if (com.jwordart.model.wordart.WordArtGroupManager.currentWorkingManager != null) {
			com.jwordart.model.wordart.WordArtGroupManager.currentWorkingManager.cutSelectedWordArts();
		}
	}

	void copyMenuItem_actionPerformed(ActionEvent e) {
		if (com.jwordart.model.wordart.WordArtGroupManager.currentWorkingManager != null) {
			com.jwordart.model.wordart.WordArtGroupManager.currentWorkingManager.copySelectedWordArts();
		}
	}

	void pasteMenuItem_actionPerformed(ActionEvent e) {
		if (com.jwordart.model.wordart.WordArtGroupManager.currentWorkingManager != null) {
			com.jwordart.model.wordart.WordArtGroupManager.currentWorkingManager.pasteCopiedWordArts();
		}
	}

	void wordArtEditorPopupMenu_popupMenuCanceled(PopupMenuEvent e) {

	}

	void wordArtEditorPopupMenu_popupMenuWillBecomeInvisible(PopupMenuEvent e) {

		if (com.jwordart.model.wordart.WordArtGroupManager.currentWorkingManager != null) {
			new Thread() {
				@Override
				public void run() {

					com.jwordart.model.wordart.WordArtGroupManager.currentWorkingManager.repaint();
				}
			}.start();
		}
	}

	void wordArtKindButton_mouseEntered(MouseEvent e) {
		this.showWordArtMappingTypeSelector();
	}

	public void showWordArtMappingTypeSelector() {
		if (this.popupMenuShow) {
			this.wordAdjustPopupMenu.setVisible(false);
			this.wordCharacterIntervalPopupMenu.setVisible(false);
			WordArtMappingTypeSelector.showSelector(this.wordArtKindButton);
		}
	}

	void wordArtKindButton_mouseExited(MouseEvent e) {
		WordArtMappingTypeSelector.hideSelector();
	}
}