package com.ynhenc.gis.ui.app.mapeditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.jdesktop.application.Application;

import com.ynhenc.gis.ui.resource.ResourceGetter;

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
public class YGisMapEditor_01_AboutBox extends JDialog implements
		ActionListener {

	String product = "Good Map Editor";
	private JLabel jLabel2;
	String version = "build# 2008.11.14.002";
	String copyright = "Copyright (c) 2008. Y&H E&C Co., Ltd.";
	String comments = "Á¦ÀÛÀÚ: \n\t¼ºº´¹®\n\t À¯ÁöÃ¶\n\t ±è¹®³â\n\t ±è¿íÇö \n\t±èµµÇü";

	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	JPanel insetsPanel2 = new JPanel();
	JPanel insetsPanel3 = new JPanel();
	JButton button1 = new JButton();
	JLabel imageLabel = new JLabel();
	JLabel label1 = new JLabel();
	JLabel label2 = new JLabel();
	JLabel label3 = new JLabel();
	JLabel label4 = new JLabel();
	BorderLayout borderLayout1 = new BorderLayout();
	BorderLayout borderLayout2 = new BorderLayout();
	private JEditorPane jEditorPane_History;
	private JScrollPane jScrollPane1;
	private JPanel jPanel2;
	private JPanel jPanel1;
	private JTabbedPane jTabbedPane1;
	private JLabel jLabel1;

	public YGisMapEditor_01_AboutBox(JFrame parent) {
		super(parent);
		try {
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			this.jbInit();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public YGisMapEditor_01_AboutBox() {
		this(null);
	}

	/**
	 * Component initialization.
	 *
	 * @throws java.lang.Exception
	 */
	private void jbInit() throws Exception {

		this.setTitle("About");
		panel1.setLayout(borderLayout1);
		panel2.setLayout(borderLayout2);
		insetsPanel2.setLayout(null);
		insetsPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		;
		label1.setText(product);
		label2.setText(version);
		label3.setText(copyright);
		label4.setText(comments);
		insetsPanel3.setLayout(null);
		insetsPanel3.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));
		button1.setText("OK");
		button1.addActionListener(this);
		imageLabel.setName("imageLabel");
		panel2.add(insetsPanel2, BorderLayout.WEST);
		insetsPanel2.setPreferredSize(new java.awt.Dimension(62, 217));
		insetsPanel2.add(imageLabel);
		imageLabel.setBounds(10, 82, 35, 28);
		{
			jLabel2 = new JLabel();
			insetsPanel2.add(jLabel2);
			jLabel2.setName("jLabel2");
			jLabel2.setBounds(10, 30, 42, 40);
		}
		panel1.setPreferredSize(new java.awt.Dimension(384, 205));
		{
			jTabbedPane1 = new JTabbedPane();
			this.getContentPane().add(jTabbedPane1, BorderLayout.CENTER);
			jTabbedPane1.addTab("GoodMap Editor", null, panel1, null);
			panel1.setName("°³¿ä");
			{
				jPanel2 = new JPanel();
				BorderLayout jPanel2Layout = new BorderLayout();
				jPanel2.setLayout(jPanel2Layout);
				jTabbedPane1.addTab("History", null, jPanel2, null);
				{
					jScrollPane1 = new JScrollPane();
					jPanel2.add(jScrollPane1, BorderLayout.CENTER);
					jScrollPane1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
					{
						jEditorPane_History = new JEditorPane();
						jScrollPane1.setViewportView(jEditorPane_History);
						jEditorPane_History.setName("jEditorPane_History");
						jEditorPane_History.setText( this.getHistoryText() );
					}
				}
			}
		}
		{
			jPanel1 = new JPanel();
			this.getContentPane().add(jPanel1, BorderLayout.SOUTH);
			jPanel1.setPreferredSize(new java.awt.Dimension(384, 29));
			jPanel1.add(button1);
		}
		insetsPanel3.add(label1);
		label1.setName("label1");
		label1.setBounds(13, 10, 174, 22);
		insetsPanel3.add(label2);
		label2.setBounds(13, 38, 121, 22);
		insetsPanel3.add(label4);
		label4.setBounds(13, 64, 284, 22);
		insetsPanel3.add(label3);
		label3.setBounds(13, 92, 284, 22);
		{
			jLabel1 = new JLabel();
			insetsPanel3.add(jLabel1);
			jLabel1.setBounds(13, 124, 280, 22);
			jLabel1.setName("jLabel1");
		}
		panel2.add(insetsPanel3, BorderLayout.CENTER);
		insetsPanel3.setPreferredSize(new java.awt.Dimension(319, 170));
		panel1.add(panel2, BorderLayout.NORTH);
		this.setResizable(true);
		this.setSize(841, 304);
		Application.getInstance().getContext().getResourceMap(this.getClass())
				.injectComponents(this.getContentPane());
	}

	String getHistoryText() {

		ResourceGetter getter = new ResourceGetter();

		return getter.getStreamAsStringBuffer( "text", "History.txt" ).toString() ;

	}

	/**
	 * Close the dialog on a button event.
	 *
	 * @param actionEvent
	 *            ActionEvent
	 */
	public void actionPerformed(ActionEvent actionEvent) {
		if (actionEvent.getSource() == button1) {
			this.dispose();
		}
	}
}
