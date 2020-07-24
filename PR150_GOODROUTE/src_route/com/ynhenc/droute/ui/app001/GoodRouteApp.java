package com.ynhenc.droute.ui.app001;

import com.ynhenc.comm.GisComLib;
import com.ynhenc.droute.*;
import com.ynhenc.droute.io.XmlHandler;
import com.ynhenc.droute.render.PathFinderPanel;
import com.ynhenc.kml.KmlHandlerPath;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

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
public class GoodRouteApp extends SingleFrameApplication {
	private JTextField pathLenTextField;
	private JProgressBar jProgressBar1;
	private JPanel southPane;
	private PathFinderPanel pathFinder;
	private JPanel rightPane;
	private JPanel centerPane;
	private JPanel topPanel;
	private JSeparator jSeparator;
	private JButton saveButton;
	private JButton openButton;
	private JButton exitButton;
	private JTextField endX;
	private JTextArea pathInfoComfortTextArea;
	private JScrollPane jScrollPane3;
	private JTextArea pathInfoTimeTextArea;
	private JScrollPane jScrollPane2;
	private JTextArea pathInfoDistTextArea;
	private JScrollPane jScrollPane1;
	private JPanel jPanel5;
	private JButton swapOrgDestBtn;
	private JComboBox testDataComboBox;
	private JRadioButton drawLinkShapeBtn;
	private JPanel jPanel4;
	private JTextField endY;
	private JLabel jLabel4;
	private JLabel jLabel3;
	private JPanel jPanel3;
	private JTextField startY;
	private JLabel jLabel2;
	private JRadioButton srchTypeOptBtn;
	private JRadioButton srchTypeTimeBtn;
	private JRadioButton srchTypeDistBtn;
	private JLabel jLabel1;
	private JTextField startX;
	private JPanel jPanel2;
	private JPanel jPanel1;
	private ButtonGroup srchTypeBG;
	private JButton exportButton;
	private JButton newTopis;
	private JButton newButton;
	private JToolBar toolBar;
	private JPanel toolBarPanel;
	private JPanel contentPanel;

	@Action
	public void open() {
		this.openButtonPressed();
	}

	@Action
	public void save() {
		this.saveButtonPressed();
	}

	@Action
	public void exportToKml() {
		this.exportButtonPressed();
	}

	@Action
	public void newFile() {
		this.newButtonPressed(0);
	}

	@Action
	public void newTopis() {
		this.newButtonPressed(1);
	}

	@Action
	public void exitApp() {
		this.exitButtonPressed();
	}

	@Action
	public void ActStartX() {
		this.locButtonPressed(0);
	}

	@Action
	public void ActStartY() {
		this.locButtonPressed(1);
	}

	@Action
	public void ActEndX() {
		this.locButtonPressed(2);
	}

	@Action
	public void ActEndY() {
		this.locButtonPressed(3);
	}

	@Action
	public void actSwapOrgDestPosition() {
		this.swapOrgDestPositionBtn_Pressed();
	}

	private ActionMap getAppActionMap() {
		return Application.getInstance().getContext().getActionMap(this);
	}

	@Override
	protected void startup() {
		{
			topPanel = new JPanel();
			BorderLayout panelLayout = new BorderLayout();
			topPanel.setLayout(panelLayout);
			topPanel.setPreferredSize(new java.awt.Dimension(979, 626));
			{
				contentPanel = new JPanel();
				BorderLayout contentPanelLayout = new BorderLayout();
				contentPanel.setLayout(contentPanelLayout);
				topPanel.add(contentPanel, BorderLayout.CENTER);
				contentPanel.setPreferredSize(new java.awt.Dimension(824, 468));
				{
					centerPane = new JPanel();
					BorderLayout centerPaneLayout = new BorderLayout();
					centerPane.setLayout(centerPaneLayout);
					contentPanel.add(centerPane, BorderLayout.CENTER);
					centerPane.setPreferredSize(new java.awt.Dimension(710, 589));
					{
						PathFinderPanel pane = this.getMainPane();
						try {
							pane.exportToKml();
							this.pack();
							this.pack();
							this.pack();
							this.pack();
							this.pack();
						} catch (Exception e) {

						}
					}
					{
						pathFinder = new PathFinderPanel();
						pathFinder.setPreferredSize(new java.awt.Dimension(861, 595));
						centerPane.add(pathFinder, BorderLayout.CENTER);
						centerPane.add(this.getJPanel5(), BorderLayout.SOUTH);
					}
				}
				{
					rightPane = new JPanel();
					contentPanel.add(rightPane, BorderLayout.EAST);
					rightPane.setPreferredSize(new java.awt.Dimension(161, 562));
					rightPane.setLayout(null);
				}
			}
			{
				toolBarPanel = new JPanel();
				topPanel.add(toolBarPanel, BorderLayout.NORTH);
				BorderLayout jPanel1Layout = new BorderLayout();
				toolBarPanel.setLayout(jPanel1Layout);
				{
					toolBar = new JToolBar();
					toolBarPanel.add(toolBar, BorderLayout.CENTER);
					{
						newButton = new JButton();
						toolBar.add(newButton);
						newButton.setAction(this.getAppActionMap().get("newFile"));
						newButton.setName("newButton");
						newButton.setFocusable(false);
					}
					{
						newTopis = new JButton();
						toolBar.add(newTopis);
						newTopis.setAction(this.getAppActionMap().get("newTopis"));
						newTopis.setName("newTopis");
					}
					{
						openButton = new JButton();
						toolBar.add(openButton);
						openButton.setAction(this.getAppActionMap().get("open"));
						openButton.setName("openButton");
						openButton.setFocusable(false);
					}
					{
						saveButton = new JButton();
						toolBar.add(saveButton);
						saveButton.setAction(this.getAppActionMap().get("save"));
						saveButton.setName("saveButton");
						saveButton.setFocusable(false);
					}
					{
						exportButton = new JButton();
						toolBar.add(exportButton);
						exportButton.setAction(this.getAppActionMap().get("exportToKml"));
						exportButton.setName("exportButton");
					}
					{
						exitButton = new JButton();
						toolBar.add(exitButton);
						exitButton.setAction(this.getAppActionMap().get("exitApp"));
						exitButton.setName("exitApp");
						exitButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent evt) {
								System.out.println("newTopis.actionPerformed, event=" + evt);
								GoodRouteApp.this.exitButtonPressed();
							}
						});
					}
				}
				{
					jSeparator = new JSeparator();
					toolBarPanel.add(jSeparator, BorderLayout.SOUTH);
				}
			}
			{
				southPane = new JPanel();
				FlowLayout southPaneLayout = new FlowLayout();
				topPanel.add(southPane, BorderLayout.SOUTH);
				southPane.setPreferredSize(new java.awt.Dimension(979, 33));
				southPane.setLayout(southPaneLayout);
				southPaneLayout.setAlignment(FlowLayout.LEFT);
				southPaneLayout.setAlignment(FlowLayout.LEFT);
				southPaneLayout.setHgap(2);
				southPaneLayout.setVgap(2);
				southPaneLayout.setAlignOnBaseline(true);
				{
					pathLenTextField = new JTextField();
					southPane.add(pathLenTextField);
					pathLenTextField.setName("pathLenTextField");
					pathLenTextField.setPreferredSize(new java.awt.Dimension(323, 27));
				}
				{
					jProgressBar1 = new JProgressBar();
					southPane.add(jProgressBar1);
					jProgressBar1.setPreferredSize(new java.awt.Dimension(224, 27));
				}
			}
			{
				this.getJPanel1();
			}
		}
		this.show(topPanel);
		this.pack();

		this.initUserInterface();
	}

	private void initUserInterface() {
		PathFinderPanel mainPane = this.getMainPane();
		if (mainPane != null) {
			JTextArea[] pathInfoCompList = { this.getPathInfoDistTextArea(), this.getPathInfoTimeTextArea(),
					this.getPathInfoComfortTextArea() };
			mainPane.setPathInfoComp(pathInfoCompList);
			JTextField pathLenTextField = this.pathLenTextField;
			mainPane.setPathLenComp(pathLenTextField);
		}
		this.setTestData(0);
	}

	private PathFinderPanel getMainPane() {
		return this.pathFinder;
	}

	private void newButtonPressed(int initMode) {
		PathFinderPanel pane = this.getMainPane();
		if (pane != null) {
			pane.initPanel(initMode);
			pane.repaint();
		}
	}

	private void openButtonPressed() {
		PathFinderPanel pane = this.getMainPane();
		if (pane != null) {
			XmlHandler xmlHandler = new XmlHandler();
			xmlHandler.open(pane);
			pane.repaint();
		}
	}

	private void saveButtonPressed() {
		PathFinderPanel pane = this.getMainPane();
		if (pane != null) {
			XmlHandler xmlHandler = new XmlHandler();
			xmlHandler.write(pane);
			pane.repaint();
		}
	}

	private void exportButtonPressed() {
		PathFinderPanel pane = this.getMainPane();
		if (pane != null) {
			try {
				pane.exportToKml();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void exitButtonPressed() {
		GoodRouteApp.this.shutdown();
		System.exit(0);
	}

	private void swapOrgDestPositionBtn_Pressed() {

		String sx = startX.getText();
		String sy = startY.getText();
		String ex = endX.getText();
		String ey = endY.getText();

		this.startX.setText(ex);
		this.startY.setText(ey);
		this.endX.setText(sx);
		this.endY.setText(sy);

		this.locButtonPressed(1);
	}

	private void locButtonPressed_Impl(int btnId) {
		System.out.println("btnId = " + btnId);
		PathFinderPanel pane = this.getMainPane();
		if (pane != null) {
			pane.setOrgDesLocation(startX.getText(), startY.getText(), endX.getText(), endY.getText());
			pane.repaint();
		}
	}

	private void locButtonPressed(final int btnId) {
		Runnable runnable = new Runnable() {
			public void run() {
				GoodRouteApp.this.locButtonPressed_Impl(btnId);
			}
		};

		GisComLib comLib = new GisComLib();

		comLib.invokeLater(runnable);
	}

	private void pack() {
	}

	public static void main(String[] args) {
		launch(GoodRouteApp.class, args);
	}

	private void jMenuItem8ActionPerformed(ActionEvent evt) {
		System.out.println("jMenuItem8.actionPerformed, event=" + evt);
		// TODO add your code for jMenuItem8.actionPerformed
	}

	public ButtonGroup getSrchTypeBG() {
		if (srchTypeBG == null) {
			srchTypeBG = new ButtonGroup();
		}
		return srchTypeBG;
	}

	private void srchTypeDistBtnActionPerformed(ActionEvent evt) {
		System.out.println("srchTypeDistBtn.actionPerformed, event=" + evt);
		this.setSrchType(evt);
	}

	private void srchTypeTimeBtnActionPerformed(ActionEvent evt) {
		System.out.println("srchTypeTimeBtn.actionPerformed, event=" + evt);
		this.setSrchType(evt);
	}

	private void setSrchType(final ActionEvent evt) {
		Runnable runnable = new Runnable() {
			public void run() {
				GoodRouteApp.this.setSrchType_Impl(evt);
			}
		};

		GisComLib comLib = new GisComLib();

		comLib.invokeLater(runnable);
	}

	private void setSrchType_Impl(ActionEvent evt) {

		PathFinderPanel pane = this.getMainPane();
		if (pane != null) {
			int srchType = 0;
			Object src = evt.getSource();
			if (src == this.srchTypeTimeBtn) {
				srchType = 1;
			} else if (src == this.srchTypeOptBtn) {
				srchType = 2;
			}

			SrchOption srchOption = new SrchOption(srchType);

			boolean btnIsSelected = false;

			if (src instanceof JRadioButton) {
				JRadioButton btn = (JRadioButton) src;
				btnIsSelected = btn.isSelected();
			}

			if (btnIsSelected) {
				MouseEvent e = null;
				pane.searchPathAgain(e, srchOption);
			} else {
				pane.setPath(srchOption, null);
			}

			pane.repaint();
		}

	}

	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			jPanel1 = new JPanel();
			FlowLayout jPanel1Layout1 = new FlowLayout();
			jPanel1Layout1.setAlignment(FlowLayout.LEFT);
			jPanel1.setLayout(jPanel1Layout1);
			jPanel1.setBounds(0, 4, 161, 116);
			jPanel1.setBorder(BorderFactory.createTitledBorder("\uac80\uc0c9\uc635\uc158"));
			{
				srchTypeDistBtn = new JRadioButton();
				jPanel1.add(srchTypeDistBtn);
				srchTypeDistBtn.setBounds(14, 12, 101, 23);
				srchTypeDistBtn.setName("srchTypeDistBtn");
				// this.getSrchTypeBG().add(srchTypeDistBtn);
				srchTypeDistBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						GoodRouteApp.this.srchTypeDistBtnActionPerformed(evt);
					}
				});
			}
			{
				srchTypeTimeBtn = new JRadioButton();
				jPanel1.add(srchTypeTimeBtn);
				srchTypeTimeBtn.setBounds(14, 35, 101, 23);
				srchTypeTimeBtn.setName("srchTypeTimeBtn");
				srchTypeTimeBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						GoodRouteApp.this.srchTypeTimeBtnActionPerformed(evt);
					}
				});
				// this.getSrchTypeBG().add(srchTypeTimeBtn);
			}
			{
				srchTypeOptBtn = new JRadioButton();
				jPanel1.add(srchTypeOptBtn);
				srchTypeOptBtn.setBounds(14, 58, 101, 23);
				srchTypeOptBtn.setName("srchTypeOptBtn");
				srchTypeOptBtn.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent evt) {
						GoodRouteApp.this.srchTypeDistBtnActionPerformed(evt);
					}
				});
				// this.getSrchTypeBG().add(srchTypeOptBtn);
			}
			rightPane.add(this.getJPanel1());
			rightPane.add(this.getSwapOrgDestBtn());
			rightPane.add(this.getTestDataComboBox());
			rightPane.add(this.getJPanel4());
			rightPane.add(this.getJPanel2());
			rightPane.add(this.getJPanel3());
		}
		return jPanel1;
	}

	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			jPanel2 = new JPanel();
			jPanel2.setBounds(0, 153, 161, 77);
			jPanel2.setBorder(BorderFactory.createTitledBorder("\ucd9c\ubc1c\uc9c0"));
			jPanel2.setLayout(null);
			jPanel2.add(this.getStartX());
			jPanel2.add(this.getJLabel1());
			jPanel2.add(this.getJLabel2());
			jPanel2.add(this.getStartY());
		}
		return jPanel2;
	}

	private JTextField getStartX() {
		if (startX == null) {
			startX = new JTextField();
			startX.setBounds(26, 19, 123, 21);
			startX.setAction(this.getAppActionMap().get("ActStartX"));
			startX.setName("startX");
		}
		return startX;
	}

	private JLabel getJLabel1() {
		if (jLabel1 == null) {
			jLabel1 = new JLabel();
			jLabel1.setBounds(11, 22, 10, 15);
			jLabel1.setName("jLabel1");
		}
		return jLabel1;
	}

	private JLabel getJLabel2() {
		if (jLabel2 == null) {
			jLabel2 = new JLabel();
			jLabel2.setBounds(13, 47, 10, 15);
			jLabel2.setName("jLabel2");
		}
		return jLabel2;
	}

	private JTextField getStartY() {
		if (startY == null) {
			startY = new JTextField();
			startY.setBounds(26, 44, 123, 21);
			startY.setName("startY");
			startY.setAction(this.getAppActionMap().get("ActStartY"));
		}
		return startY;
	}

	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			jPanel3 = new JPanel();
			jPanel3.setBounds(0, 230, 161, 73);
			jPanel3.setBorder(BorderFactory.createTitledBorder("\ubaa9\uc801\uc9c0"));
			jPanel3.setLayout(null);
			jPanel3.add(this.getJLabel3());
			jPanel3.add(this.getEndX());
			jPanel3.add(this.getJLabel4());
			jPanel3.add(this.getEndY());
		}
		return jPanel3;
	}

	private JLabel getJLabel3() {
		if (jLabel3 == null) {
			jLabel3 = new JLabel();
			jLabel3.setBounds(13, 19, 10, 15);
			jLabel3.setName("jLabel3");
		}
		return jLabel3;
	}

	private JTextField getEndX() {
		if (endX == null) {
			endX = new JTextField();
			endX.setBounds(28, 16, 121, 21);
			endX.setAction(this.getAppActionMap().get("ActEndX"));
			endX.setName("endX");
		}
		return endX;
	}

	private JLabel getJLabel4() {
		if (jLabel4 == null) {
			jLabel4 = new JLabel();
			jLabel4.setBounds(13, 44, 10, 15);
			jLabel4.setName("jLabel4");
		}
		return jLabel4;
	}

	private JTextField getEndY() {
		if (endY == null) {
			endY = new JTextField();
			endY.setBounds(28, 41, 121, 21);
			endY.setAction(this.getAppActionMap().get("ActEndY"));
			endY.setName("endY");
		}
		return endY;
	}

	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(null);
			jPanel4.setBounds(0, 331, 161, 66);
			jPanel4.setBorder(BorderFactory.createTitledBorder("\ud45c\ucd9c\uc635\uc158"));
			jPanel4.add(this.getDrawLinkShapeBtn());
		}
		return jPanel4;
	}

	private JRadioButton getDrawLinkShapeBtn() {
		if (drawLinkShapeBtn == null) {
			drawLinkShapeBtn = new JRadioButton();
			drawLinkShapeBtn.setBounds(6, 19, 117, 23);
			drawLinkShapeBtn.setName("drawLinkShapeBtn");
			drawLinkShapeBtn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					System.out.println("drawLinkShapeBtn.actionPerformed, event=" + evt);
					GoodRouteApp.this.setDrawLinkShape(drawLinkShapeBtn.isSelected());
				}
			});
		}
		return drawLinkShapeBtn;
	}

	private void setDrawLinkShape(boolean drawLinkShape) {
		PathFinderPanel pane = this.getMainPane();
		if (pane != null) {
			pane.setDrawLinkShape(drawLinkShape);
			pane.repaint();
		}
	}

	private JComboBox getTestDataComboBox() {
		if (testDataComboBox == null) {
			ComboBoxModel testDataComboBoxModel = new DefaultComboBoxModel(RouteTestData.getTestDataList());
			testDataComboBox = new JComboBox();
			testDataComboBox.setModel(testDataComboBoxModel);
			testDataComboBox.setBounds(5, 120, 156, 23);
			testDataComboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					System.out.println("testDataComboBox.actionPerformed ");
					GoodRouteApp.this.setTestData(testDataComboBox.getSelectedIndex());
				}
			});
		}
		return testDataComboBox;
	}

	private void setTestData(int index) {
		RouteTestData data = RouteTestData.getTestData(index);
		this.startX.setText("" + data.getStart().getX());
		this.startY.setText("" + data.getStart().getY());
		this.endX.setText("" + data.getEnd().getX());
		this.endY.setText("" + data.getEnd().getY());

		this.locButtonPressed(0);
	}

	private JButton getSwapOrgDestBtn() {
		if (swapOrgDestBtn == null) {
			swapOrgDestBtn = new JButton();
			swapOrgDestBtn.setBounds(5, 303, 151, 28);
			swapOrgDestBtn.setName("swapOrgDestBtn");
			swapOrgDestBtn.setAction(this.getAppActionMap().get("actSwapOrgDestPosition"));
		}
		return swapOrgDestBtn;
	}

	private JPanel getJPanel5() {
		if (jPanel5 == null) {
			jPanel5 = new JPanel();
			jPanel5.setPreferredSize(new java.awt.Dimension(818, 74));
			jPanel5.setLayout(null);
			jPanel5.setBorder(BorderFactory.createEtchedBorder(BevelBorder.LOWERED));
			jPanel5.add(this.getJScrollPane1());
			jPanel5.add(this.getJScrollPane2());
			jPanel5.add(this.getJScrollPane3());
		}
		return jPanel5;
	}

	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setBounds(2, 2, 279, 70);
			jScrollPane1.setBorder(BorderFactory.createTitledBorder(null, "\uac70\ub9ac\uacbd\ub85c", TitledBorder.LEADING,
					TitledBorder.DEFAULT_POSITION));
			jScrollPane1.setViewportView(this.getPathInfoDistTextArea());
		}
		return jScrollPane1;
	}

	private JTextArea getPathInfoDistTextArea() {
		if (pathInfoDistTextArea == null) {
			pathInfoDistTextArea = new JTextArea();
			pathInfoDistTextArea.setName("pathInfoDistTextArea");
			pathInfoDistTextArea.setPreferredSize(new java.awt.Dimension(121, 40));
			pathInfoDistTextArea.setName("pathInfoDistTextArea");
		}
		return pathInfoDistTextArea;
	}

	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setBorder(BorderFactory.createTitledBorder("\uc2dc\uac04\uacbd\ub85c"));
			jScrollPane2.setBounds(281, 2, 276, 70);
			jScrollPane2.setViewportView(this.getPathInfoTimeTextArea());
		}
		return jScrollPane2;
	}

	private JTextArea getPathInfoTimeTextArea() {
		if (pathInfoTimeTextArea == null) {
			pathInfoTimeTextArea = new JTextArea();
			pathInfoTimeTextArea.setPreferredSize(new java.awt.Dimension(121, 40));
			pathInfoTimeTextArea.setName("pathInfoDistTextArea");
		}
		return pathInfoTimeTextArea;
	}

	private JScrollPane getJScrollPane3() {
		if (jScrollPane3 == null) {
			jScrollPane3 = new JScrollPane();
			jScrollPane3.setBorder(BorderFactory.createTitledBorder("\ud3b8\ub9ac\uacbd\ub85c"));
			jScrollPane3.setBounds(557, 2, 259, 70);
			jScrollPane3.setViewportView(this.getPathInfoComfortTextArea());
		}
		return jScrollPane3;
	}

	private JTextArea getPathInfoComfortTextArea() {
		if (pathInfoComfortTextArea == null) {
			pathInfoComfortTextArea = new JTextArea();
			pathInfoComfortTextArea.setPreferredSize(new java.awt.Dimension(121, 40));
			pathInfoComfortTextArea.setName("pathInfoDistTextArea");
		}
		return pathInfoComfortTextArea;
	}

}
