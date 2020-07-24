package com.ynhenc.gis.ui.app.mapeditor;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import javax.swing.table.*;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

import com.ynhenc.comm.ArrayListString;
import com.ynhenc.comm.DebugInterface;
import com.ynhenc.comm.registry.ArrayListRegiItem;
import com.ynhenc.comm.registry.RegiItem;
import com.ynhenc.comm.util.*;
import com.ynhenc.gis.*;
import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.model.shape.Layer;
import com.ynhenc.gis.model.shape.OffSet;
import com.ynhenc.gis.model.style.*;
import com.ynhenc.gis.ui.comp.*;
import com.ynhenc.gis.ui.viewer_01.map_viewer.*;
import com.ynhenc.gis.ui.viewer_02.style_editor.JLayerStyleEditor;
import com.ynhenc.gis.ui.viewer_03.dbf_viewer.JDbfGridTable;

/**
 * This code was edited or generated using CloudGarden's Jigloo
 * SWT/Swing GUI Builder, which is free for non-commercial
 * use. If Jigloo is being used commercially (ie, by a corporation,
 * company or business for any purpose whatever) then you
 * should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details.
 * Use of Jigloo implies acceptance of these licensing terms.
 * A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
 * THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
 * LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
/**
 *
 */
public class YGisMapEditor_00_App extends SingleFrameApplication implements LayerFontStyleShower, DebugInterface {
	private JMenuBar menuBar;
	private JPanel topPanel;
	private JButton btn_002_mapFixZoomOut;
	private JButton btn_001_mapFixZoomIn;
	private JToolBar jToolBar_003_mapController;
	private JMenuItem jMenuItem_AboutGoodMapEditor;
	private JMenuItem jMenuItem_HelpDocument;
	private JTextField yPos_TextField;
	private JTextField xPos_TextField;
	private JProgressBar progressBar;
	private JLabel statusLabel;
	private JPanel statusPanel;
	private JMenuItem jMenuItem_LayerRemoveAll;
	private JMenuItem jMenuItem_LayerAdd;
	private JMenu menu_09_Help;
	private JButton jButton_OpenMultiLayer;
	private JPanel jPanel8;
	private JScrollPane jScrollPane_01;
	private YGisMapEditor_02_ProjectOptionPane jPanel_002_LayerOption;
	private JTabbedPane jTabbedPane_Map;
	private JComboBox jComboBox_MapCommand;
	private MapViewer mapViewer;
	private JTextField jTextField_Memory;
	private JSpinner jSpinner_LayerOffSetY;
	private JSpinner jSpinner_LayerOffSetX;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JScrollPane jScrollPane1;
	private JLabel jLabel1;
	private JSpinner jSpinner_MapLevelSelector;
	private JPanel jPanel2;
	private LevelScaleEditor jLevelScaleEditor;
	private JPanel jPanel_LevelScale;
	private JTextField scale_TextField;
	private JSpinner jSpinner_ZoomLevel_02;
	private JSpinner jSpinner_ZoomLevel_01;
	private ButtonGroup buttonGroup_02_FontAlignV;
	private ButtonGroup buttonGroup_01_FontAlignH;
	private JSeparator jSeparator3;
	private JToggleButton jButton_FontAlignBottom;
	private JToggleButton jButton_FontAlignMiddle;
	private JToggleButton jButton_FontAlignTop;
	private JSeparator jSeparator2;
	private JSeparator jSeparator1;
	private JComboBox_Color jComboBox_FontColor;
	private JToggleButton jButton_FontAlignRight;
	private JMenu jMenu_ProjectOpenRecent;
	private JToggleButton jButton_FontAlignCenter;
	private JToggleButton jButton_FontAlignLeft;
	private JPanel jPanel3;
	private JComboBox jComboBox_FontFamily;
	private JToggleButton jButton_FontUnderLine;
	private JToggleButton jButton_FontStyleItalic;
	private JToggleButton jButton_FontStyleBoldic;
	private JSpinner jSpinner_FontSize;
	private JToolBar jToolBar_002_Font;
	private JMenuItem jMenuItem7;
	private JMenuItem jMenuItem6;
	private JMenuItem jMenuItem5;
	private JMenuItem jMenuItem4;
	private JMenu menu_02_Edit;
	private JMenuItem jMenuItem_ProjectSave;
	private JMenuItem jMenuItem_ProjectOpen;
	private JMenuItem jMenuItem_ProjectNew;
	private JPanel jPanel_001_LayerStyler;
	private JTabbedPane jTabbedPane1;
	private JPanel jPanel_Left;
	private JPanel jPanel_Right;
	private JSplitPane jSplitPane1;
	private JMenu menu_03_Layer;
	private JMenuItem jMenuItem_Exit;
	private JMenu menu_01_File;
	private JButton btn_100_ProjectOpen;
	private JLayerStyleEditor jLayerStyleEditor;
	private JButton btn_003_mapPan;
	private JButton btn_011_mapSrchElem;
	private JButton btn_010_mapViewAttr;
	private JButton btn_009_mapSeleElem;
	private MapMetaDataPane mapMetaDataPane;
	private JDbfGridPane dbfGridPane;
	private JTextField selectedLayerTextField;
	private JButton btn_006_mapHistNext;
	private JButton btn_005_mapHistPrev;
	private JButton btn_ProjectNew;
	private JButton btn_103_MapPrint;
	private JButton btn_101_ProjectSave;
	private JButton btn_004_mapFullExtent;
	private JToolBar jToolBar_001_Project;
	private JPanel toolBarPanel;
	private JPanel contentPanel;

	@Action
	public void open() {
	}

	@Action
	public void save() {
	}

	@Action
	public void newFile() {
	}

	private ActionMap getAppActionMap() {
		return Application.getInstance().getContext().getActionMap(this);
	}

	@Override
	protected void startup() {
		{
			this.getMainFrame().setSize(1297, 546);
		}
		{
			this.topPanel = new JPanel();
			BorderLayout panelLayout = new BorderLayout();
			this.topPanel.setLayout(panelLayout);
			this.topPanel.setPreferredSize(new java.awt.Dimension(811, 378));
			{
				this.contentPanel = new JPanel();
				BorderLayout contentPanelLayout = new BorderLayout();
				this.contentPanel.setLayout(contentPanelLayout);
				this.topPanel.add(this.contentPanel, BorderLayout.CENTER);
				this.contentPanel.setPreferredSize(new java.awt.Dimension(436, 271));
				{
					this.jSplitPane1 = new JSplitPane();
					this.contentPanel.add(this.jSplitPane1, BorderLayout.CENTER);
					{
						this.jPanel_Right = new JPanel();
						BorderLayout jPanel1Layout1 = new BorderLayout();
						this.jPanel_Right.setLayout(jPanel1Layout1);
						this.jSplitPane1.add(this.jPanel_Right, JSplitPane.RIGHT);
						this.jPanel_Right.setPreferredSize(new java.awt.Dimension(564, 459));
						this.mapViewer = new MapViewer();
						{
							jTabbedPane_Map = new JTabbedPane();
							jPanel_Right.add(jTabbedPane_Map, BorderLayout.CENTER);
							jTabbedPane_Map.addTab("지도", null, mapViewer, null);
							jTabbedPane_Map.addTab("DBF", null, this.getDbfGridPane(), null);
							mapViewer.setName("mapViewer");
							mapViewer.setPreferredSize(new java.awt.Dimension(884, 417));
						}
					}
					{
						this.jPanel_Left = new JPanel();
						BorderLayout jPanel2Layout = new BorderLayout();
						this.jPanel_Left.setLayout(jPanel2Layout);
						this.jSplitPane1.add(this.jPanel_Left, JSplitPane.LEFT);
						this.jPanel_Left.setPreferredSize(new java.awt.Dimension(386, 429));
						{
							this.jTabbedPane1 = new JTabbedPane();
							this.jPanel_Left.add(this.jTabbedPane1, BorderLayout.CENTER);
							{
								this.jPanel_001_LayerStyler = new JPanel();
								BorderLayout jPanel3Layout = new BorderLayout();
								this.jPanel_001_LayerStyler.setLayout(jPanel3Layout);
								jTabbedPane1.addTab("지도", null, this.getMapMetaDataPane(), null);
								this.jTabbedPane1.addTab("레이어", null, this.jPanel_001_LayerStyler, null);
								{
									this.jScrollPane_01 = new JScrollPane();
									this.jPanel_001_LayerStyler.add(this.jScrollPane_01, BorderLayout.CENTER);
									this.jScrollPane_01.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
									this.jScrollPane_01.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
									{
										this.jLayerStyleEditor = new JLayerStyleEditor();
										this.jScrollPane_01.setViewportView(this.jLayerStyleEditor);
									}
								}
								{
									this.jPanel8 = new JPanel();
									FlowLayout jPanel8Layout = new FlowLayout();
									jPanel8Layout.setHgap(1);
									jPanel8Layout.setVgap(2);
									this.jPanel8.setLayout(jPanel8Layout);
									this.jPanel_001_LayerStyler.add(this.jPanel8, BorderLayout.NORTH);
									this.jPanel8.setPreferredSize(new java.awt.Dimension(215, 28));
									{
										this.jButton_OpenMultiLayer = new JButton();
										this.jPanel8.add(this.jButton_OpenMultiLayer);
										this.jPanel8.add(this.getJSpinner_ZoomLevel_02());
										this.jButton_OpenMultiLayer.setName("jButton_OpenMultiLayer");
										this.jButton_OpenMultiLayer.setPreferredSize(new java.awt.Dimension(133, 25));
										this.jButton_OpenMultiLayer.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent evt) {
												System.out.println("jButton_OpenMultiLayer.actionPerformed, event=" + evt);

												YGisMapEditor_00_App.this.getMapViewerActive().layer_00_OpenMultipleFile();
											}
										});
									}
								}
							}
							{
								this.jPanel_002_LayerOption = new YGisMapEditor_02_ProjectOptionPane();

								this.jTabbedPane1.addTab("축척", null, this.getJPanel_LevelScale(), null);
								this.jTabbedPane1.addTab("옵션", null, this.jPanel_002_LayerOption, null);
								this.jPanel_002_LayerOption.addComponentListener(new ComponentAdapter() {
									@Override
									public void componentShown(ComponentEvent e) {
										YGisMapEditor_00_App.this.jPanel_002_LayerOption.synchronizeToGisProject(YGisMapEditor_00_App.this.getMapViewerActive());
									}
								});
							}
						}
					}
				}
			}
			{
				this.toolBarPanel = new JPanel();
				this.topPanel.add(this.toolBarPanel, BorderLayout.NORTH);
				FlowLayout jPanel1Layout = new FlowLayout();
				jPanel1Layout.setAlignment(FlowLayout.LEFT);
				jPanel1Layout.setHgap(0);
				jPanel1Layout.setVgap(1);
				this.toolBarPanel.setLayout(jPanel1Layout);
				this.toolBarPanel.setPreferredSize(new java.awt.Dimension(1152, 37));
				{
					this.jToolBar_001_Project = new JToolBar();
					this.toolBarPanel.add(this.jToolBar_001_Project);
					this.jToolBar_001_Project.setPreferredSize(new java.awt.Dimension(125, 30));
					{
						this.btn_ProjectNew = new JButton();
						this.jToolBar_001_Project.add(this.btn_ProjectNew);
						this.btn_ProjectNew.setName("btn_ProjectNew");
						this.btn_ProjectNew.setPreferredSize(new java.awt.Dimension(26, 24));
						this.btn_ProjectNew.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								MapViewer mapViewer = YGisMapEditor_00_App.this.getMapViewerActive();
								if (mapViewer != null) {
									mapViewer.gisProject_00_Create();
									mapViewer.repaint();
								}
							}
						});
					}
					{
						this.btn_100_ProjectOpen = new JButton();
						this.jToolBar_001_Project.add(this.btn_100_ProjectOpen);
						this.btn_100_ProjectOpen.setName("btn_100_ProjectOpen");
						this.btn_100_ProjectOpen.setPreferredSize(new java.awt.Dimension(26, 19));
						this.btn_100_ProjectOpen.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.out.println("btn_001_mapFixZoomIn.actionPerformed, event=" + evt);
								YGisMapEditor_00_App.this.listener_01_ProjectOpen();
							}
						});
					}
					{
						this.btn_101_ProjectSave = new JButton();
						this.jToolBar_001_Project.add(this.btn_101_ProjectSave);
						this.btn_101_ProjectSave.setName("btn_101_ProjectSave");
						this.btn_101_ProjectSave.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {

								YGisMapEditor_00_App.this.getMapViewerActive().gisProject_02_Save();
							}
						});
					}
					{
						this.btn_103_MapPrint = new JButton();
						this.jToolBar_001_Project.add(this.btn_103_MapPrint);
						this.btn_103_MapPrint.setPreferredSize(new java.awt.Dimension(27, 25));
						this.btn_103_MapPrint.setName("btn_103_MapPrint");
						this.btn_103_MapPrint.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.out.println("btn_001_mapFixZoomIn.actionPerformed, event=" + evt);
								YGisMapEditor_00_App.this.getMapViewerActive().print();
							}
						});
					}
				}
				{
					this.jToolBar_002_Font = new JToolBar();
					this.toolBarPanel.add(this.jToolBar_002_Font);
					this.jToolBar_002_Font.setPreferredSize(new java.awt.Dimension(658, 25));
					{
						this.jPanel3 = new JPanel();
						this.jToolBar_002_Font.add(this.jPanel3);
						FlowLayout jPanel3Layout = new FlowLayout();
						jPanel3Layout.setVgap(1);
						jPanel3Layout.setHgap(1);
						jPanel3Layout.setAlignment(FlowLayout.LEFT);
						this.jPanel3.setLayout(jPanel3Layout);
						this.jPanel3.setPreferredSize(new java.awt.Dimension(768, 23));
						this.jPanel3.setSize(150, 23);
						{
							ComboBoxModel jComboBox2Model = new DefaultComboBoxModel(FontManager.getAvailableFontFamilyNames());
							this.jComboBox_FontFamily = new JComboBox();
							jPanel3.add(this.getSelectedLayerTextField());
							this.jPanel3.add(this.jComboBox_FontFamily);
							this.jComboBox_FontFamily.setModel(jComboBox2Model);
							this.jComboBox_FontFamily.setPreferredSize(new java.awt.Dimension(111, 22));
							this.jComboBox_FontFamily.setName("jComboBox_FontFamily");
							this.jComboBox_FontFamily.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									YGisMapEditor_00_App.this
											.when_FontFamilySelected(YGisMapEditor_00_App.this.jComboBox_FontFamily);
								}
							});
						}
						{
							this.jSpinner_FontSize = new JSpinner();
							this.jPanel3.add(this.jSpinner_FontSize);

							this.jSpinner_FontSize.setPreferredSize(new java.awt.Dimension(48, 22));
							this.jSpinner_FontSize.setSize(40, 23);
							this.jSpinner_FontSize.setName("jSpinner_FontSize");
							this.jSpinner_FontSize.setValue(8);
							this.jSpinner_FontSize.addChangeListener(new ChangeListener() {
								public void stateChanged(ChangeEvent evt) {
									YGisMapEditor_00_App.this.when_FontSizeSelected(evt);
								}
							});
						}
						{
							this.jButton_FontStyleBoldic = new JToggleButton();
							this.jPanel3.add(this.jButton_FontStyleBoldic);
							this.jButton_FontStyleBoldic.setName("jButton_FontStyleBoldic");
							this.jButton_FontStyleBoldic.setPreferredSize(new java.awt.Dimension(32, 22));
							this.jButton_FontStyleBoldic.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									YGisMapEditor_00_App.this.when_FontStyleSelected(evt);
								}
							});
						}
						{
							this.jButton_FontStyleItalic = new JToggleButton();
							this.jPanel3.add(this.jButton_FontStyleItalic);
							this.jButton_FontStyleItalic.setName("jButton_FontStyleItalic");
							this.jButton_FontStyleItalic.setPreferredSize(new java.awt.Dimension(30, 22));
							this.jButton_FontStyleItalic.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									YGisMapEditor_00_App.this.when_FontStyleSelected(evt);
								}
							});
						}
						{
							this.jButton_FontUnderLine = new JToggleButton();
							this.jPanel3.add(this.jButton_FontUnderLine);
							this.jButton_FontUnderLine.setName("jButton_FontUnderLine");
							this.jButton_FontUnderLine.setPreferredSize(new java.awt.Dimension(31, 22));
							this.jButton_FontUnderLine.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									YGisMapEditor_00_App.this.when_FontStyleSelected(evt);
								}
							});
						}
						{
							this.jSeparator1 = new JSeparator();
							this.jPanel3.add(this.jSeparator1);
							this.jSeparator1.setBounds(51, 40, -1, 14);
							this.jSeparator1.setOrientation(SwingConstants.VERTICAL);
							this.jSeparator1.setPreferredSize(new java.awt.Dimension(2, 14));
						}
						{
							this.jButton_FontAlignLeft = new JToggleButton();
							this.jPanel3.add(this.jButton_FontAlignLeft);
							this.jButton_FontAlignLeft.setPreferredSize(new java.awt.Dimension(31, 22));
							this.jButton_FontAlignLeft.setName("jButton_FontAlignLeft");
							this.jButton_FontAlignLeft.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									YGisMapEditor_00_App.this.when_FontAlignSelected(0);
								}
							});
						}
						{
							this.jButton_FontAlignCenter = new JToggleButton();
							this.jPanel3.add(this.jButton_FontAlignCenter);
							this.jButton_FontAlignCenter.setPreferredSize(new java.awt.Dimension(31, 22));
							this.jButton_FontAlignCenter.setName("jButton_FontAlignCenter");
							this.jButton_FontAlignCenter.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									YGisMapEditor_00_App.this.when_FontAlignSelected(1);
								}
							});
						}
						{
							this.jButton_FontAlignRight = new JToggleButton();
							this.jPanel3.add(this.jButton_FontAlignRight);
							this.jButton_FontAlignRight.setPreferredSize(new java.awt.Dimension(31, 22));
							this.jButton_FontAlignRight.setName("jButton_FontAlignRight");
							this.jButton_FontAlignRight.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									YGisMapEditor_00_App.this.when_FontAlignSelected(2);
								}
							});
						}
						{
							this.jSeparator2 = new JSeparator();
							this.jPanel3.add(this.jSeparator2);
							this.jSeparator2.setOrientation(SwingConstants.VERTICAL);
							this.jSeparator2.setPreferredSize(new java.awt.Dimension(2, 14));
							this.jSeparator2.setBounds(51, 40, -1, 14);
						}
						{
							this.jButton_FontAlignBottom = new JToggleButton();
							this.jPanel3.add(this.jButton_FontAlignBottom);
							this.jButton_FontAlignBottom.setPreferredSize(new java.awt.Dimension(31, 22));
							this.jButton_FontAlignBottom.setName("jButton_FontAlignBottom");
							this.jButton_FontAlignBottom.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									YGisMapEditor_00_App.this.when_FontAlignSelected(3);
								}
							});
						}
						{
							this.jButton_FontAlignMiddle = new JToggleButton();
							this.jPanel3.add(this.jButton_FontAlignMiddle);
							this.jButton_FontAlignMiddle.setPreferredSize(new java.awt.Dimension(31, 22));
							this.jButton_FontAlignMiddle.setName("jButton_FontAlignMiddle");
							this.jButton_FontAlignMiddle.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									YGisMapEditor_00_App.this.when_FontAlignSelected(4);
								}
							});
						}
						{
							this.jButton_FontAlignTop = new JToggleButton();
							this.jPanel3.add(this.jButton_FontAlignTop);
							this.jButton_FontAlignTop.setPreferredSize(new java.awt.Dimension(31, 22));
							this.jButton_FontAlignTop.setName("jButton_FontAlignTop");
							this.jButton_FontAlignTop.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									YGisMapEditor_00_App.this.when_FontAlignSelected(5);
								}
							});
						}
						{
							this.jSeparator3 = new JSeparator();
							this.jPanel3.add(this.jSeparator3);
							this.jSeparator3.setOrientation(SwingConstants.VERTICAL);
							this.jSeparator3.setPreferredSize(new java.awt.Dimension(2, 14));
							this.jSeparator3.setBounds(51, 40, -1, 14);
						}
						{
							this.jComboBox_FontColor = new JComboBox_Color();
							this.jPanel3.add(this.jComboBox_FontColor);
							this.jPanel3.add(this.getJLabel3());
							this.jPanel3.add(this.getJSpinner_LayerOffSetX());
							this.jPanel3.add(this.getJSpinner_LayerOffSetY());
							this.jComboBox_FontColor.setPreferredSize(new java.awt.Dimension(61, 22));
							this.jComboBox_FontColor.setSize(40, 23);
							this.jComboBox_FontColor.setName("jComboBox_FontColor");
							this.jComboBox_FontColor.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									YGisMapEditor_00_App.this
											.when_FontColorSelected(YGisMapEditor_00_App.this.jComboBox_FontColor);
								}
							});
						}
					}
				}
				{
					this.jToolBar_003_mapController = new JToolBar();
					this.toolBarPanel.add(this.jToolBar_003_mapController);
					this.jToolBar_003_mapController.setPreferredSize(new java.awt.Dimension(486, 30));
					{
						this.btn_001_mapFixZoomIn = new JButton();
						this.jToolBar_003_mapController.add(this.getJLabel2());
						this.jToolBar_003_mapController.add(this.getJSpinner_ZoomLevel_01());
						this.jToolBar_003_mapController.add(this.btn_001_mapFixZoomIn);
						this.btn_001_mapFixZoomIn.setName("btn_001_mapFixZoomIn");
						this.btn_001_mapFixZoomIn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.out.println("btn_001_mapFixZoomIn.actionPerformed, event=" + evt);
								YGisMapEditor_00_App.this.getMapViewerActive().setZoomIn();
							}
						});
					}
					{
						this.btn_002_mapFixZoomOut = new JButton();
						this.jToolBar_003_mapController.add(this.btn_002_mapFixZoomOut);
						this.btn_002_mapFixZoomOut.setName("btn_002_mapFixZoomOut");
						this.btn_002_mapFixZoomOut.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.out.println("btn_001_mapFixZoomIn.actionPerformed, event=" + evt);
								YGisMapEditor_00_App.this.getMapViewerActive().setZoomOut();
							}
						});
					}
					{
						this.btn_003_mapPan = new JButton();
						this.jToolBar_003_mapController.add(this.btn_003_mapPan);
						this.btn_003_mapPan.setName("btn_003_mapPan");
						this.btn_003_mapPan.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.out.println("btn_001_mapFixZoomIn.actionPerformed, event=" + evt);
								YGisMapEditor_00_App.this.getMapViewerActive().setPan();
							}
						});
					}
					{
						this.btn_004_mapFullExtent = new JButton();
						this.jToolBar_003_mapController.add(this.btn_004_mapFullExtent);
						this.btn_004_mapFullExtent.setName("btn_004_mapFullExtent");
						{
							ComboBoxModel jComboBox1Model =
								new DefaultComboBoxModel(
										new String[] { "지도 이동", "거리 측정", "면적 측정" , "최단경로" });
							this.jComboBox_MapCommand = new JComboBox();
							this.jToolBar_003_mapController.add(this.jComboBox_MapCommand);
							this.jComboBox_MapCommand.setModel(jComboBox1Model);
							this.jComboBox_MapCommand.setPreferredSize(new java.awt.Dimension(100, 28));
							this.jComboBox_MapCommand.setSize(100, 28);
							this.jComboBox_MapCommand.addActionListener(new ActionListener() {
								public void actionPerformed(ActionEvent evt) {
									 YGisMapEditor_00_App.this.setMapViewerMode( YGisMapEditor_00_App.this.jComboBox_MapCommand );
								}
							});
						}
						this.btn_004_mapFullExtent.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.out.println("btn_001_mapFixZoomIn.actionPerformed, event=" + evt);
								YGisMapEditor_00_App.this.getMapViewerActive().setViewFullExtent();
							}
						});
					}
					{
						this.btn_005_mapHistPrev = new JButton();
						this.jToolBar_003_mapController.add(this.btn_005_mapHistPrev);
						this.btn_005_mapHistPrev.setName("btn_005_mapHistPrev");
						this.btn_005_mapHistPrev.setEnabled(false);
						this.btn_005_mapHistPrev.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.out.println("btn_001_mapFixZoomIn.actionPerformed, event=" + evt);
								YGisMapEditor_00_App.this.getMapViewerActive().goViewPrev();
							}
						});
					}
					{
						this.btn_006_mapHistNext = new JButton();
						this.jToolBar_003_mapController.add(this.btn_006_mapHistNext);
						this.btn_006_mapHistNext.setName("btn_006_mapHistNext");
						this.btn_006_mapHistNext.setEnabled(false);
						this.btn_006_mapHistNext.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.out.println("btn_001_mapFixZoomIn.actionPerformed, event=" + evt);
								YGisMapEditor_00_App.this.getMapViewerActive().goViewNext();
							}
						});
					}
					{
						this.btn_009_mapSeleElem = new JButton();
						this.jToolBar_003_mapController.add(this.btn_009_mapSeleElem);
						this.btn_009_mapSeleElem.setName("btn_009_mapSeleElem");
						this.btn_009_mapSeleElem.setEnabled(false);
						this.btn_009_mapSeleElem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.out.println("btn_001_mapFixZoomIn.actionPerformed, event=" + evt);
							}
						});
					}
					{
						this.btn_010_mapViewAttr = new JButton();
						this.jToolBar_003_mapController.add(this.btn_010_mapViewAttr);
						this.btn_010_mapViewAttr.setName("btn_010_mapViewAttr");
						this.btn_010_mapViewAttr.setEnabled(false);
						this.btn_010_mapViewAttr.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.out.println("btn_001_mapFixZoomIn.actionPerformed, event=" + evt);
							}
						});
					}
					{
						this.btn_011_mapSrchElem = new JButton();
						this.jToolBar_003_mapController.add(this.btn_011_mapSrchElem);
						this.btn_011_mapSrchElem.setName("btn_011_mapSrchElem");
						this.btn_011_mapSrchElem.setEnabled(false);
						this.btn_011_mapSrchElem.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.out.println("btn_001_mapFixZoomIn.actionPerformed, event=" + evt);
							}
						});
					}
				}
			}
			{
				this.statusPanel = new JPanel();
				FlowLayout statusPanelLayout1 = new FlowLayout();
				statusPanelLayout1.setAlignment(FlowLayout.LEFT);
				FlowLayout statusPanelLayout = new FlowLayout();
				this.topPanel.add(this.statusPanel, BorderLayout.SOUTH);
				this.statusPanel.setPreferredSize(new java.awt.Dimension(811, 35));
				this.statusPanel.setLayout(statusPanelLayout1);
				{
					this.statusLabel = new JLabel();
					this.statusPanel.add(this.statusLabel);
					this.statusLabel.setName("statusLabel");
					this.statusLabel.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
					this.statusLabel.setPreferredSize(new java.awt.Dimension(278, 29));
				}
				{
					this.xPos_TextField = new JTextField();
					this.statusPanel.add(this.xPos_TextField);
					this.xPos_TextField.setPreferredSize(new java.awt.Dimension(130, 29));
					this.xPos_TextField.setEnabled(false);
				}
				{
					this.yPos_TextField = new JTextField();
					this.statusPanel.add(this.yPos_TextField);
					this.statusPanel.add(this.getScale_TextField());
					this.yPos_TextField.setPreferredSize(new java.awt.Dimension(130, 29));
					this.yPos_TextField.setEnabled(false);
				}
				{
					this.progressBar = new JProgressBar();
					this.statusPanel.add(this.progressBar);
					this.progressBar.setPreferredSize(new java.awt.Dimension(206, 29));
					this.progressBar.setDoubleBuffered(true);
					{
						this.jTextField_Memory = new JTextField();
						this.statusPanel.add(this.jTextField_Memory);
						this.jTextField_Memory.setName("jTextField_Memory");
						this.jTextField_Memory.setEnabled(false);
						this.jTextField_Memory.setPreferredSize(new java.awt.Dimension(281, 29));
					}
				}
			}
		}
		this.menuBar = new JMenuBar();
		{
			this.menu_01_File = new JMenu();
			this.menuBar.add(this.menu_01_File);
			this.menu_01_File.setName("menu_01_File");
			{
				this.jMenuItem_ProjectNew = new JMenuItem();
				this.menu_01_File.add(this.jMenuItem_ProjectNew);
				this.jMenuItem_ProjectNew.setAction(this.getAppActionMap().get("newFile"));
				this.jMenuItem_ProjectNew.setName("jMenuItem_ProjectNew");
				this.jMenuItem_ProjectNew.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						MapViewer mapViewer = YGisMapEditor_00_App.this.getMapViewerActive();
						if (mapViewer != null) {
							mapViewer.gisProject_00_Create();
							mapViewer.repaint();
						}
					}
				});
			}
			{
				this.jMenuItem_ProjectOpen = new JMenuItem();
				this.menu_01_File.add(this.jMenuItem_ProjectOpen);
				this.jMenuItem_ProjectOpen.setAction(this.getAppActionMap().get("open"));
				this.jMenuItem_ProjectOpen.setName("jMenuItem_ProjectOpen");
				{
					this.jMenu_ProjectOpenRecent = new JMenu();
					this.menu_01_File.add(this.jMenu_ProjectOpenRecent);
					this.jMenu_ProjectOpenRecent.setName("jMenu_ProjectOpenRecent");
				}
				this.jMenuItem_ProjectOpen.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						YGisMapEditor_00_App.this.listener_01_ProjectOpen();
					}
				});
			}
			{
				this.jMenuItem_ProjectSave = new JMenuItem();
				this.menu_01_File.add(this.jMenuItem_ProjectSave);
				this.jMenuItem_ProjectSave.setAction(this.getAppActionMap().get("save"));
				this.jMenuItem_ProjectSave.setName("jMenuItem_ProjectSave");
				this.jMenuItem_ProjectSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						System.out.println("jMenuItem_ProjectSave.actionPerformed, event=" + evt);

						// jMenuItem_ProjectSave.actionPerformed
						YGisMapEditor_00_App.this.getMapViewerActive().gisProject_02_Save();
					}
				});
			}
			{
				this.jMenuItem_Exit = new JMenuItem();
				this.menu_01_File.add(this.jMenuItem_Exit);
				this.jMenuItem_Exit.setName("jMenuItem_Exit");
				this.jMenuItem_Exit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						if (true) {
							YGisMapEditor_00_App.this.getMainFrame().setVisible(false);

							YGisMapEditor_00_App.this.shutdown();

							System.exit(0);
						} else {
							System.exit(0);
						}
					}
				});
			}
		}
		{
			this.menu_02_Edit = new JMenu();
			this.menuBar.add(this.menu_02_Edit);
			this.menu_02_Edit.setName("menu_02_Edit");
			this.menu_02_Edit.setEnabled(false);
			{
				this.jMenuItem4 = new JMenuItem();
				this.menu_02_Edit.add(this.jMenuItem4);
				this.jMenuItem4.setAction(this.getAppActionMap().get("copy"));
			}
			{
				this.jMenuItem5 = new JMenuItem();
				this.menu_02_Edit.add(this.jMenuItem5);
				this.jMenuItem5.setAction(this.getAppActionMap().get("cut"));
			}
			{
				this.jMenuItem6 = new JMenuItem();
				this.menu_02_Edit.add(this.jMenuItem6);
				this.jMenuItem6.setAction(this.getAppActionMap().get("paste"));
			}
			{
				this.jMenuItem7 = new JMenuItem();
				this.menu_02_Edit.add(this.jMenuItem7);
				this.jMenuItem7.setAction(this.getAppActionMap().get("delete"));
			}
		}
		{
			this.menu_03_Layer = new JMenu();
			this.menuBar.add(this.menu_03_Layer);
			this.menu_03_Layer.setName("menu_03_Layer");
			{
				this.jMenuItem_LayerAdd = new JMenuItem();
				this.menu_03_Layer.add(this.jMenuItem_LayerAdd);
				this.jMenuItem_LayerAdd.setName("jMenuItem_LayerAdd");
				this.jMenuItem_LayerAdd.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						MapViewer mapViewer = YGisMapEditor_00_App.this.getMapViewerActive();
						if (mapViewer != null) {
							mapViewer.layer_00_OpenMultipleFile();
						}
					}
				});
			}
			{
				this.jMenuItem_LayerRemoveAll = new JMenuItem();
				this.menu_03_Layer.add(this.jMenuItem_LayerRemoveAll);
				this.jMenuItem_LayerRemoveAll.setName("jMenuItem_LayerRemoveAll");
				this.jMenuItem_LayerRemoveAll.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						MapViewer mapViewer = YGisMapEditor_00_App.this.getMapViewerActive();
						if (mapViewer != null) {
							mapViewer.removeAllLayerList();
							mapViewer.repaint();
						}
					}
				});
			}
		}
		{
			this.menu_09_Help = new JMenu();
			this.menuBar.add(this.menu_09_Help);
			this.menu_09_Help.setName("menu_09_Help");
			{
				this.jMenuItem_HelpDocument = new JMenuItem();
				this.menu_09_Help.add(this.jMenuItem_HelpDocument);
				this.jMenuItem_HelpDocument.setName("jMenuItem_HelpDocument");
				this.jMenuItem_HelpDocument.setEnabled(false);
			}
			{
				this.jMenuItem_AboutGoodMapEditor = new JMenuItem();
				this.menu_09_Help.add(this.jMenuItem_AboutGoodMapEditor);
				this.jMenuItem_AboutGoodMapEditor.setName("jMenuItem_AboutGoodMapEditor");
				this.jMenuItem_AboutGoodMapEditor.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						System.out.println("jMenuItem_AboutGoodMapEditor.actionPerformed, event=" + evt);

						JFrame frame = YGisMapEditor_00_App.this.getMainFrame();
						YGisMapEditor_01_AboutBox dlg = new YGisMapEditor_01_AboutBox(frame);

						// dlg.setLocationRelativeTo( frame );
						dlg.setModal(true);
						// dlg.pack();
						dlg.setLocationByPlatform(true);
						dlg.setVisible(true);
					}
				});
			}
		}
		this.getMainFrame().setJMenuBar(this.menuBar);

		this.show(this.topPanel);

		this.initComponent();

	}

	private void listener_01_ProjectOpen() {
		this.getMapViewerActive().gisProject_01_OpenFile();

		this.init_jMenu_ProjectOpenRecent();
	}

	private void listener_01_ProjectOpen_Recent(JMenuItem menuItem) {
		String projectFilePath = menuItem.getText();
		this.getMapViewerActive().gisProject_01_OpenFile_RecentProject(projectFilePath);
	}

	private MapViewer getMapViewerActive() {
		// return null;
		return this.mapViewer;
	}

	private void when_FontFamilySelected(JComboBox comboBox) {
		Layer layer = this.layerSelected;
		if (layer != null) {
			String fontFamily_Selected = "" + comboBox.getSelectedItem();
			Style_01_Text textStyle = layer.getTextStyle();
			if (textStyle != null) {
				textStyle.setFontFamily(fontFamily_Selected);

				MapViewer mapViewer = this.getMapViewerActive();

				if (mapViewer != null) {
					mapViewer.repaint_MapViewer_After_Creating_Map_Again();
				}
			}
		}
	}

	private void when_FontSizeSelected(ChangeEvent evt) {
		Layer layer = this.layerSelected;
		if (layer != null) {
			Integer fontSize_Selected = Integer.parseInt("" + this.jSpinner_FontSize.getValue());

			if (fontSize_Selected < 5) {
				fontSize_Selected = 5;
				this.jSpinner_FontSize.setValue(fontSize_Selected);
			}

			Style_01_Text textStyle = layer.getTextStyle();
			if (textStyle != null && fontSize_Selected > 0) {
				textStyle.setFontSize(fontSize_Selected);

				MapViewer mapViewer = this.getMapViewerActive();

				if (mapViewer != null) {
					mapViewer.repaint_MapViewer_After_Creating_Map_Again();
				}
			}
		}
	}

	private void when_FontColorSelected(JComboBox_Color comboBox_Color) {

		Layer layer = this.layerSelected;
		if (layer != null) {
			Object obj = comboBox_Color.getSelectedItem();
			Color fontColor_Selected = null;

			if (obj instanceof Color) {
				fontColor_Selected = (Color) obj;
			}

			if (fontColor_Selected == null) {
				String title = "폰트 색상 선택";
				Color initialColor = Color.black;
				if (false) {
					Style_01_Text textStyle = layer.getTextStyle();
					if (textStyle != null) {
						initialColor = textStyle.getFontColor();
					}
				}
				fontColor_Selected = ColorManager.getColorUsingColorChoooser(comboBox_Color, title, initialColor);

				if (fontColor_Selected != null) {
					comboBox_Color.setSelectedColor(fontColor_Selected);
				}
			}

			Style_01_Text textStyle = layer.getTextStyle();

			if (textStyle != null && fontColor_Selected != null) {
				textStyle.setFontColor(fontColor_Selected);

				final MapViewer mapViewer = this.getMapViewerActive();

				if (mapViewer != null) {
					mapViewer.repaint_MapViewer_After_Creating_Map_Again();
				}
			}
		}

	}

	private void when_FontStyleSelected(ActionEvent evt) {

		Layer layer = this.layerSelected;

		final Style_01_Text textStyle = (layer != null) ? layer.getTextStyle() : null;

		if (textStyle != null) {

			int fontStyle = Font.PLAIN;

			if (this.jButton_FontStyleBoldic.isSelected()) {
				// 볼딕체 토글
				fontStyle = fontStyle | Font.BOLD;
			}

			if (this.jButton_FontStyleItalic.isSelected()) {
				// 이탤릭 토글
				fontStyle = fontStyle | Font.ITALIC;
			}

			textStyle.setFontStyle(fontStyle);

			if (true) {
				// 밑줄 토글.
				textStyle.setUnderLine(this.jButton_FontUnderLine.isSelected());
			}

			final MapViewer mapViewer = this.getMapViewerActive();

			if (mapViewer != null) {
				mapViewer.repaint_MapViewer_After_Creating_Map_Again();
			}

		}

	}

	private void when_FontAlignSelected(int align) {

		Layer layer = this.layerSelected;

		Style_01_Text textStyle = (layer != null) ? layer.getTextStyle() : null;

		if (textStyle != null) {

			if (align == 0) {
				textStyle.setAlignH(AlignH.LEFT);
			} else if (align == 1) {
				textStyle.setAlignH(AlignH.CENTER);
			} else if (align == 2) {
				textStyle.setAlignH(AlignH.RIGHT);
			} else if (align == 3) {
				textStyle.setAlignV(AlignV.BOTTOM);
			} else if (align == 4) {
				textStyle.setAlignV(AlignV.MIDDLE);
			} else if (align == 5) {
				textStyle.setAlignV(AlignV.TOP);
			}

			final MapViewer mapViewer = this.getMapViewerActive();

			if (mapViewer != null) {
				mapViewer.repaint_MapViewer_After_Creating_Map_Again();
			}

		}

	}

	public void showLayerFontSyle(Layer layer) {

		MapViewer mapViewer = this.getMapViewerActive();

		if( true ) {
			this.getMapMetaDataPane().validate();
			this.getMapMetaDataPane().repaint( 0 );
		}

		if (layer != null && this.layerSelected != layer ) {
			this.debug.println(this, "LAYER NAME = " + layer.getName());
			this.statusLabel.setText("선택 레이어 = " + layer.getName());
			this.selectedLayerTextField.setText( layer.getName());
			this.layerSelected = layer;
			Style_01_Text textStyle = layer.getTextStyle();

			if (textStyle != null) {
				if (true) {
					// 폰트 패밀리 동기화
					String fontFamily = textStyle.getFontFamily();
					JComboBox jComboBox_FontFamily = this.jComboBox_FontFamily;
					for (int i = 0, iLen = jComboBox_FontFamily.getItemCount(); i < iLen; i++) {
						if (fontFamily.equalsIgnoreCase("" + jComboBox_FontFamily.getItemAt(i))) {
							jComboBox_FontFamily.setSelectedIndex(i);
						}
					}

				}

				if (true) {
					// 폰트 사이즈 동기화
					int fontSize = textStyle.getFontSize();
					JSpinner jComboBox_FontSize = this.jSpinner_FontSize;
					jComboBox_FontSize.setValue(fontSize);
				}

				if (true) {
					// 폰트 스타일 동기화
					int fontStyle = textStyle.getFontStyle();

					if (fontStyle > -1) {
						this.jButton_FontStyleBoldic.setSelected((fontStyle & Font.BOLD) == Font.BOLD);
						this.jButton_FontStyleItalic.setSelected((fontStyle & Font.ITALIC) == Font.ITALIC);
						this.jButton_FontUnderLine.setSelected(textStyle.isUnderLine());
					}
				}

				if (true) {
					// 폰트 정렬 동기화.
					AlignH alignH = textStyle.getAlignH();

					this.jButton_FontAlignLeft.setSelected(false);
					this.jButton_FontAlignCenter.setSelected(false);
					this.jButton_FontAlignRight.setSelected(false);

					if (alignH == null) {
						this.jButton_FontAlignLeft.setSelected(true);
					} else if (alignH.equals(AlignH.LEFT)) {
						this.jButton_FontAlignLeft.setSelected(true);
					} else if (alignH.equals(AlignH.CENTER)) {
						this.jButton_FontAlignCenter.setSelected(true);
					} else if (alignH.equals(AlignH.RIGHT)) {
						this.jButton_FontAlignRight.setSelected(true);
					}

					AlignV alignV = textStyle.getAlignV();

					this.jButton_FontAlignTop.setSelected(false);
					this.jButton_FontAlignMiddle.setSelected(false);
					this.jButton_FontAlignBottom.setSelected(false);

					if (alignV == null) {
						this.jButton_FontAlignMiddle.setSelected(true);
					} else if (alignV.equals(AlignV.TOP)) {
						this.jButton_FontAlignTop.setSelected(true);
					} else if (alignV.equals(AlignV.MIDDLE)) {
						this.jButton_FontAlignMiddle.setSelected(true);
					} else if (alignV.equals(AlignV.BOTTOM)) {
						this.jButton_FontAlignBottom.setSelected(true);
					}
				}

				if (true) {
					// 오프셋 동기화
					OffSet offSet = textStyle.getOffSet();

					if (offSet != null) {
						this.jSpinner_LayerOffSetX.setValue((int) offSet.getX());
						this.jSpinner_LayerOffSetY.setValue((int) offSet.getY());
					}
				}

			}
		}

	}

	private ButtonGroup getButtonGroup_01_FontAlignH() {
		if (this.buttonGroup_01_FontAlignH == null) {
			this.buttonGroup_01_FontAlignH = new ButtonGroup();
			this.buttonGroup_01_FontAlignH.add(this.jButton_FontAlignLeft);
			this.buttonGroup_01_FontAlignH.add(this.jButton_FontAlignCenter);
			this.buttonGroup_01_FontAlignH.add(this.jButton_FontAlignRight);
		}
		return this.buttonGroup_01_FontAlignH;
	}

	private ButtonGroup getButtonGroup_02_FontAlignV() {
		if (this.buttonGroup_02_FontAlignV == null) {
			this.buttonGroup_02_FontAlignV = new ButtonGroup();
			this.buttonGroup_02_FontAlignV.add(this.jButton_FontAlignTop);
			this.buttonGroup_02_FontAlignV.add(this.jButton_FontAlignMiddle);
			this.buttonGroup_02_FontAlignV.add(this.jButton_FontAlignBottom);
		}
		return this.buttonGroup_02_FontAlignV;
	}

	private JSpinner getJSpinner_ZoomLevel_01() {
		if (this.jSpinner_ZoomLevel_01 == null) {
			this.jSpinner_ZoomLevel_01 = new JSpinner();
			this.jSpinner_ZoomLevel_01.setValue(9);
			this.jSpinner_ZoomLevel_01.setSize(30, 28);
			this.jSpinner_ZoomLevel_01.setPreferredSize(new java.awt.Dimension(34, 28));
			this.jSpinner_ZoomLevel_01.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent evt) {
					YGisMapEditor_00_App.this.jSpinner_ZoomLevel_01StateChanged(YGisMapEditor_00_App.this.jSpinner_ZoomLevel_01);
				}
			});
		}
		return this.jSpinner_ZoomLevel_01;
	}

	private void jSpinner_ZoomLevel_01StateChanged(JSpinner spinner) {

		Object obj = spinner.getValue();

		int zoomLevel = (int) (Double.parseDouble("" + obj));

		MapViewer mapViewer = this.getMapViewerActive();
		if (mapViewer != null) {
			GisProject gisProject = mapViewer.getGisProject();
			ZoomLevelList zoomLevelList = gisProject.getZoomLevelList();

			if (zoomLevel > zoomLevelList.getZoomLevelMax()) {
				zoomLevel = zoomLevelList.getZoomLevelMax();
				spinner.setValue(zoomLevel);
			}

			if (zoomLevel < 0) {
				zoomLevel = 0;
				spinner.setValue(zoomLevel);
			}

			if (zoomLevel != zoomLevelList.getZoomLevelCurr()) {
				mapViewer.setZoomLevel(zoomLevel);
			}
		}
	}

	private JSpinner getJSpinner_ZoomLevel_02() {
		if (this.jSpinner_ZoomLevel_02 == null) {
			this.jSpinner_ZoomLevel_02 = new JSpinner();
			this.jSpinner_ZoomLevel_02.setValue(9);
			this.jSpinner_ZoomLevel_02.setPreferredSize(new java.awt.Dimension(43, 28));
			this.jSpinner_ZoomLevel_02.setSize(30, 28);
			this.jSpinner_ZoomLevel_02.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent evt) {
					YGisMapEditor_00_App.this.jSpinner_ZoomLevel_01StateChanged(YGisMapEditor_00_App.this.jSpinner_ZoomLevel_02);
				}
			});
		}
		return this.jSpinner_ZoomLevel_02;
	}

	private JTextField getScale_TextField() {
		if (this.scale_TextField == null) {
			this.scale_TextField = new JTextField();
			this.scale_TextField.setEnabled(false);
			this.scale_TextField.setPreferredSize(new java.awt.Dimension(130, 29));
			this.scale_TextField.setSize(130, 29);
		}
		return this.scale_TextField;
	}

	private void initComponent() {
		MapViewer mapViewer = this.getMapViewerActive();
		if (mapViewer != null) {
			mapViewer.setLayerFontStyleShower(this);
			mapViewer.setJLayerStyleEditor(this.jLayerStyleEditor);
			mapViewer.setProgressBar(this.progressBar);
			mapViewer.setStatusLabel(this.statusLabel);
			mapViewer.setXPos_TextField(this.xPos_TextField);
			mapViewer.setYPos_TextField(this.yPos_TextField);
			mapViewer.setScale_TextField(this.getScale_TextField());
			mapViewer.setJDbfViewer(this.getDbfGridPane());
			mapViewer.setAddZoomLevelSpinner(this.getJSpinner_ZoomLevel_01());
			mapViewer.setAddZoomLevelSpinner(this.getJSpinner_ZoomLevel_02());
			mapViewer.setLevelScaleEditor(this.getJLevelScaleEditor());
			mapViewer.setMemory_TextField(this.jTextField_Memory);
			this.getJLevelScaleEditor().setMapViewer(mapViewer);
			this.getMapMetaDataPane().setMapViewer(mapViewer);
			this.getButtonGroup_01_FontAlignH();
			this.getButtonGroup_02_FontAlignV();
			this.init_jMenu_ProjectOpenRecent();
		}

	}

	private void init_jMenu_ProjectOpenRecent() {

		JMenu menu = this.jMenu_ProjectOpenRecent;

		if (menu != null) {
			menu.removeAll();

			GisRegistry gisRegistry = GisRegistry.getGisRegistry();
			ArrayListRegiItem regiItems = gisRegistry.getWindowRegistry_GisProjectFilePathList();
			for ( RegiItem regiItem : regiItems) {
				final JMenuItem menuItem = new JMenuItem(regiItem.getValue());
				menuItem.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						YGisMapEditor_00_App.this.listener_01_ProjectOpen_Recent(menuItem);
					}
				});
				menu.add(menuItem);
			}
		}

	}

	public static void main(String[] args) {

		System.out.println( GisRegistry.getGisRegistry().getAppFullName() );
		System.out.println( "java.version: " + System.getProperty( "java.version" ) );

		launch(YGisMapEditor_00_App.class, args);

	}

	private JPanel getJPanel_LevelScale() {
		if (this.jPanel_LevelScale == null) {
			this.jPanel_LevelScale = new JPanel();
			BorderLayout jPanel1Layout = new BorderLayout();
			this.jPanel_LevelScale.setLayout(jPanel1Layout);
			this.jPanel_LevelScale.setName("jPanel_LevelScale");
			this.jPanel_LevelScale.add(this.getJPanel2(), BorderLayout.NORTH);
			this.jPanel_LevelScale.add(this.getJScrollPane1(), BorderLayout.CENTER);
		}
		return this.jPanel_LevelScale;
	}

	private LevelScaleEditor getJLevelScaleEditor() {
		if (this.jLevelScaleEditor == null) {
			this.jLevelScaleEditor = new LevelScaleEditor();
			// jLevelScaleEditor.setPreferredSize(new java.awt.Dimension(377,
			// 265));
		}
		return this.jLevelScaleEditor;
	}

	private JPanel getJPanel2() {
		if (this.jPanel2 == null) {
			this.jPanel2 = new JPanel();
			this.jPanel2.setLayout(null);
			this.jPanel2.setPreferredSize(new java.awt.Dimension(381, 42));
			this.jPanel2.add(this.getJLabel1());
			this.jPanel2.add(this.getJSpinner_MapLevelSelector());
		}
		return this.jPanel2;
	}

	private JSpinner getJSpinner_MapLevelSelector() {
		if (this.jSpinner_MapLevelSelector == null) {

			this.jSpinner_MapLevelSelector = new JSpinner() {
				@Override
				public void paint(Graphics g) {
					YGisMapEditor_00_App.this.synchToGisProjectZoomLevelMax(this);
					super.paint(g);
				}
			};
			this.jSpinner_MapLevelSelector.setName("jSpinner_MapLevelSelector");
			this.jSpinner_MapLevelSelector.setBounds(179, 8, 51, 24);
			this.jSpinner_MapLevelSelector.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent evt) {
					YGisMapEditor_00_App.this.setMaxZoomLevel(YGisMapEditor_00_App.this.jSpinner_MapLevelSelector);
				}
			});
			this.getJSpinner_MapLevelSelector().getEditor().setPreferredSize(new java.awt.Dimension(32, 20));
		}
		return this.jSpinner_MapLevelSelector;
	}

	private void setMaxZoomLevel(JSpinner spinner) {

		int zoomLevelMaxNew = (int) (Double.parseDouble("" + spinner.getValue()));

		this.debug.println(this, "NEW MAX ZOOM LEVEL = " + zoomLevelMaxNew);

		MapViewer mapViewer = this.getMapViewerActive();

		if (mapViewer != null) {
			GisProject gisProject = mapViewer.getGisProject();
			ZoomLevelList zoomLevelList = gisProject.getZoomLevelList();
			if (zoomLevelList != null) {

				if (zoomLevelMaxNew > 4) {

					int zoomLevelCurr = zoomLevelList.getZoomLevelCurr();

					if (zoomLevelCurr > zoomLevelMaxNew) {
						zoomLevelList.setZoomLevelCurr(zoomLevelMaxNew);
					}

					zoomLevelList.setZoomLevelMax(zoomLevelMaxNew);

					mapViewer.repaint();

					LevelScaleEditor jLevelScaleEditor = this.getJLevelScaleEditor();

					if (jLevelScaleEditor != null) {
						jLevelScaleEditor.repaint();
					}

				} else {
					spinner.setValue(zoomLevelList.getZoomLevelMax());
				}
			}
		}
	}

	private void synchToGisProjectZoomLevelMax(JSpinner jSpinner) {

		MapViewer mapViewer = this.getMapViewerActive();

		if (mapViewer != null) {

			GisProject gisProject = mapViewer.getGisProject();

			if (gisProject != null) {
				int zoomLevelMax = gisProject.getZoomLevelList().getZoomLevelMax();
				jSpinner.setValue(zoomLevelMax);
			}

		}
	}

	private JLabel getJLabel1() {
		if (this.jLabel1 == null) {
			this.jLabel1 = new JLabel();
			this.jLabel1.setName("jLabel1");
			this.jLabel1.setBounds(107, 12, 60, 15);
		}
		return this.jLabel1;
	}

	private JScrollPane getJScrollPane1() {
		if (this.jScrollPane1 == null) {
			this.jScrollPane1 = new JScrollPane();
			this.jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			this.jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			this.jScrollPane1.setViewportView(this.getJLevelScaleEditor());
		}
		return this.jScrollPane1;
	}

	private JLabel getJLabel2() {
		if (this.jLabel2 == null) {
			this.jLabel2 = new JLabel();
			this.jLabel2.setName("jLabel2");
		}
		return this.jLabel2;
	}

	private JLabel getJLabel3() {
		if (this.jLabel3 == null) {
			this.jLabel3 = new JLabel();
			this.jLabel3.setName("jLabel3");
		}
		return this.jLabel3;
	}

	private JSpinner getJSpinner_LayerOffSetX() {
		if (this.jSpinner_LayerOffSetX == null) {
			SpinnerListModel jSpinner2Model = new SpinnerListModel(new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
					14, 15, 16, 17, 18, 19, 20 });
			this.jSpinner_LayerOffSetX = new JSpinner();
			this.jSpinner_LayerOffSetX.setModel(jSpinner2Model);
			this.jSpinner_LayerOffSetX.setPreferredSize(new java.awt.Dimension(38, 24));
			this.jSpinner_LayerOffSetX.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent evt) {
					YGisMapEditor_00_App.this.when_LayerOffSet_Changed(YGisMapEditor_00_App.this.jSpinner_LayerOffSetX, 0);
				}
			});
		}
		return this.jSpinner_LayerOffSetX;
	}

	private void when_LayerOffSet_Changed(JSpinner spinner, int xy) {
		Layer layer = this.layerSelected;
		Style_01_Text textStyle = (layer != null) ? layer.getTextStyle() : null;
		if (textStyle != null) {
			int offSetValue = -1;
			try {
				offSetValue = (int) (Double.parseDouble(("" + spinner.getValue()).trim()));
			} catch (NumberFormatException e) {
				this.debug.println(this, e);
			}

			if (offSetValue > -1) {
				OffSet offSet = textStyle.getOffSet();
				if (offSet != null) {
					if (xy == 0) {
						offSet.setX(offSetValue);
					} else {
						offSet.setY(offSetValue);
					}

					MapViewer mapViewer = this.getMapViewerActive();
					if (mapViewer != null) {
						mapViewer.createMapImageAgain();
						mapViewer.repaint();
					}
				}

			}
		}
	}

	private JSpinner getJSpinner_LayerOffSetY() {
		if (this.jSpinner_LayerOffSetY == null) {
			SpinnerListModel jSpinner1Model = new SpinnerListModel(new Integer[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
					14, 15, 16, 17, 18, 19, 20 });
			this.jSpinner_LayerOffSetY = new JSpinner();
			this.jSpinner_LayerOffSetY.setModel(jSpinner1Model);
			this.jSpinner_LayerOffSetY.setPreferredSize(new java.awt.Dimension(38, 24));
			this.jSpinner_LayerOffSetY.addChangeListener(new ChangeListener() {
				public void stateChanged(ChangeEvent evt) {
					YGisMapEditor_00_App.this.when_LayerOffSet_Changed(YGisMapEditor_00_App.this.jSpinner_LayerOffSetY, 1);
				}
			});
		}
		return this.jSpinner_LayerOffSetY;
	}

	private void setMapViewerMode( JComboBox comboBox ) {
		int index = comboBox.getSelectedIndex();
		if( index > - 1 ) {
			MapViewer mapViewer = this.getMapViewerActive();
			if( mapViewer != null ) {
				mapViewer.getMapViewerListener().setMode( index );
			}
		}
	}

	private JTextField getSelectedLayerTextField() {
		if(selectedLayerTextField == null) {
			selectedLayerTextField = new JTextField();
			selectedLayerTextField.setPreferredSize(new java.awt.Dimension(98, 23));
		}
		return selectedLayerTextField;
	}

	private JDbfGridPane getDbfGridPane() {
		if(dbfGridPane == null) {
			dbfGridPane = new JDbfGridPane();
			dbfGridPane.setName("dbfGridPane");
		}
		return dbfGridPane;
	}

	private MapMetaDataPane getMapMetaDataPane() {
		if(mapMetaDataPane == null) {
			mapMetaDataPane = new MapMetaDataPane();
		}
		return mapMetaDataPane;
	}

	private Layer layerSelected;

}
