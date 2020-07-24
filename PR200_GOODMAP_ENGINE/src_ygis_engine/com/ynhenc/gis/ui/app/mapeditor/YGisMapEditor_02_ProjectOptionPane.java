package com.ynhenc.gis.ui.app.mapeditor;

import java.awt.*;
import java.awt.event.*;

import org.jdesktop.application.*;

import com.ynhenc.gis.model.map.*;
import com.ynhenc.gis.projection.*;
import com.ynhenc.gis.ui.viewer_01.map_viewer.*;

import javax.swing.*;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class YGisMapEditor_02_ProjectOptionPane extends javax.swing.JPanel {
	private JCheckBox jCheckBox_CrossLine;
	private JLabel coordTypeLabel;
	private JCheckBox jCheckBox_AntiAliasing;
	// private JLabel jLabel1;
	private JComboBox jComboBox_CoordConv;
	private JCheckBox jCheckBox_Logo;
	private JCheckBox jCheckBox_RenderingTime;
	private JCheckBox jCheckBox_GlobalMbr;
	private JCheckBox jCheckBox_displayShapeAndIconLabelTogether;

	/**
	 * Auto-generated main method to display this JPanel inside a new JFrame.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new YGisMapEditor_02_ProjectOptionPane());
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}

	public YGisMapEditor_02_ProjectOptionPane() {
		super();
		this.initGUI();
	}

	private void initGUI() {
		try {
			this.setPreferredSize(new java.awt.Dimension(379, 290));
			this.setLayout(null);
			{
				this.jCheckBox_CrossLine = new JCheckBox();
				this.add(this.jCheckBox_CrossLine);
				this.jCheckBox_CrossLine.setBounds(32, 52, 85, 19);
				this.jCheckBox_CrossLine.setName("CrossLine");
				this.jCheckBox_CrossLine.addActionListener(this.getProjecOptionListener());
			}
			{
				this.jCheckBox_AntiAliasing = new JCheckBox();
				this.add(this.jCheckBox_AntiAliasing);
				this.jCheckBox_AntiAliasing.setBounds(32, 90, 167, 19);
				this.jCheckBox_AntiAliasing.setName("AntiAliasing");
				this.jCheckBox_AntiAliasing.addActionListener(this.getProjecOptionListener());
			}
			{
				this.jCheckBox_GlobalMbr = new JCheckBox();
				this.add(this.jCheckBox_GlobalMbr);
				this.jCheckBox_GlobalMbr.setName("GlobalMbr");
				this.jCheckBox_GlobalMbr.setBounds(32, 131, 135, 19);
				this.jCheckBox_GlobalMbr.addActionListener(this.getProjecOptionListener());
			}
			{
				this.jCheckBox_Logo = new JCheckBox();
				this.add(this.jCheckBox_Logo);
				this.jCheckBox_Logo.setName("Logo");
				jCheckBox_Logo.setBounds(32, 217, 85, 19);
				this.jCheckBox_Logo.addActionListener(this.getProjecOptionListener());
			}
			{
				ComboBoxModel jComboBox1Model = new DefaultComboBoxModel(new CoordinateConversion[] { new DirectConversion(),
						new WgsToUtm() });
				this.jComboBox_CoordConv = new JComboBox();
				this.add(this.jComboBox_CoordConv);
				this.jComboBox_CoordConv.setModel(jComboBox1Model);
				this.jComboBox_CoordConv.setBounds(124, 15, 120, 22);
				this.jComboBox_CoordConv.addActionListener(this.getProjecOptionListener());
			}
			{
				jCheckBox_RenderingTime = new JCheckBox();
				this.add(jCheckBox_RenderingTime);
				jCheckBox_RenderingTime.setName("RenderingTime");
				jCheckBox_RenderingTime.setBounds(32, 176, 141, 19);
				jCheckBox_RenderingTime.addActionListener(projecOptionListener);
			}
			{
				this.jCheckBox_displayShapeAndIconLabelTogether = new JCheckBox();
				this.add(this.jCheckBox_displayShapeAndIconLabelTogether);
				this.jCheckBox_displayShapeAndIconLabelTogether.setName("ShapeAndIconLabelTogether");
				jCheckBox_displayShapeAndIconLabelTogether.setBounds(32, 254, 227, 19);
				this.jCheckBox_displayShapeAndIconLabelTogether.addActionListener(this.getProjecOptionListener());
			}
			{
				coordTypeLabel = new JLabel();
				this.add(coordTypeLabel);
				coordTypeLabel.setBounds(32, 19, 36, 15);
				coordTypeLabel.setName("coordTypeLabel");
			}

			Application.getInstance().getContext().getResourceMap(this.getClass()).injectComponents(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ProjectOptionPaneListener getProjecOptionListener() {
		return this.projecOptionListener;
	}

	public void synchronizeToGisProject(MapViewer mapViewer) {
		if (mapViewer != null) {
			this.getProjecOptionListener().setMapViewer(mapViewer);
			GisProject gisProject = mapViewer.getGisProject();
			GisProjectOption option = (gisProject != null) ? gisProject.getGisProjectOption() : null;
			if (option != null) {
				this.jCheckBox_CrossLine.setSelected(option.isCrossLine_Show());
				this.jCheckBox_AntiAliasing.setSelected(option.isGraphics_HighQuality());
				this.jCheckBox_GlobalMbr.setSelected(option.isGlobalMbr_Show());
				this.jCheckBox_Logo.setSelected(option.isLogo_Show());
				this.jComboBox_CoordConv.setSelectedItem(option.getCoordinateConversion());
				this.jCheckBox_RenderingTime.setSelected(option.isRenderingTime_Show());
				this.jCheckBox_displayShapeAndIconLabelTogether.setSelected(option.isDisplayShapeAndIconLabelTogether());
			}
		}
	}

	private ProjectOptionPaneListener projecOptionListener = new ProjectOptionPaneListener();

}
