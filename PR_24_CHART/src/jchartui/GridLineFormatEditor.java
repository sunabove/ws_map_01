package jchartui;

/**
 * Title:           GridLineFormatEditor Ŭ����
 * Copyright:       Copyright (c) juney<p>
 * Company:         Techdigm corp.
 * @author          withhim
 * @version 1.0
 * Description:     ���ݼ� ���� ���̾�α� �ڽ��� �ش��ϴ� Ŭ����
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import jchart.*;

import java.util.ResourceBundle;
import java.util.Locale;

public class GridLineFormatEditor extends Editor {

	// ��ü�� ���� Panel
	JPanel contentPane;

	// ���� ���� Panel
	JTabbedPane tabPaneMain = new JTabbedPane();

	// �ϳ� �ϳ��� �ǿ� �ش��ϴ� Ŭ������
    PatternTab tabPattern;
    GridTabForValueAxis tabGrid;

	// Ȯ��, ��� ��ư
	JButton buttonOk = new JButton();
    JButton buttonCancel = new JButton();

	// text�� locale�� ��� ���� ����
	public Locale currentLocale;

    public GridLineFormatEditor() {

		// ��� ���õ� ���� ���� ����
		tabPattern = new PatternTab( PatternTab.LINE_MODE );

		// ValueAxis�� ���õ� ���� ���� ����
		tabGrid = new GridTabForValueAxis();

		try {
		  jbInit();
		}
		catch(Exception e) {
		  e.printStackTrace();
		}

		this.setResizable(false);
	//	this.setLocationRelativeTo(jcalc.JCalc.frame);
	}

  //Component initialization
    private void jbInit() throws Exception  {

		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/EditorDialogBundle", JCalcResource.getLocale());

		contentPane = (JPanel) this.getContentPane();

		// Layout�� ���Ѵ�.
		contentPane.setLayout(null);

		// Component���� Bound�� ���Ѵ�.
		this.setSize(new Dimension(447, 413));
		tabPaneMain.setBounds(new Rectangle(7, 6, 432, 342));
		buttonCancel.setBounds(new Rectangle(349, 360, 90, 20));
		buttonOk.setBounds(new Rectangle(247, 360, 90, 20));


	// Component���� ÷����Ų��.
	contentPane.add(tabPaneMain, null);

		tabPaneMain.add(tabPattern,  bundle.getString("patternTab") );
	tabPaneMain.add(tabGrid,  bundle.getString("scaleTab") );

		contentPane.add(buttonCancel, null);
	contentPane.add(buttonOk, null);

		// Component���� Text�� �����Ѵ�.
	    this.setTitle(bundle.getString("gridLine"));        // "���ݼ� ����"
		buttonOk.setText(bundle.getString("ok"));
	    buttonCancel.setText(bundle.getString("cancel"));


		// �����ʸ� ��Ͻ�Ų��.
		buttonOk.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		buttonOk_actionPerformed(e);
	    }
	});
		buttonCancel.addActionListener(new java.awt.event.ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		buttonCancel_actionPerformed(e);
	    }
	});


  }

	/**
	 * Ȯ�� ��ư�� ������ ��...
	 *
	 * ���̾�α� �ڽ����� �����ߴ� ������� ���õ� chartElement�� �����Ų��.
	 */
    void buttonOk_actionPerformed(ActionEvent e) {

		tabPattern.applyToChartElement();
		tabGrid.applyToChartElement();

		this.dispose();

		SpreadSheet.getCurrentSpreadSheet().repaint();

    }

	/**
	 * ��� ��ư�� ������ ��...
	 *
	 * ���̾�α� �ڽ��� show �Ǿ��� ���� ���·� dialog �ڽ��� �����Ѵ�.
	 */
    void buttonCancel_actionPerformed(ActionEvent e) {

		initializeDialog();

		this.dispose();

    }

	/**
	 * ���̾�α� �ڽ� ���� ��� component���� text�� �����Ѵ�.
	 * Scale�� �ٲ���� ���� ���Լ��� ȣ���Ѵ�.
	 */
	public void changeMenuText() {

		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/EditorDialogBundle", JCalcResource.getLocale());

	    this.setTitle(bundle.getString("gridLine"));        // "���ݼ� ����"

	    tabPaneMain.add(tabPattern, bundle.getString("patternTab"));
	tabPaneMain.add(tabGrid, bundle.getString("scaleTab"));

		buttonOk.setText(bundle.getString("ok"));
	    buttonCancel.setText(bundle.getString("cancel"));

		this.tabPattern.changeMenuText();
		this.tabGrid.changeMenuText();

	}

	/**
	 * ���̾�α� �ڽ��� �ʱ�ȭ �Ѵ�.
	 */
	public void initializeDialog() {

	    tabPattern.initializeDialogTab();
		tabGrid.initializeDialogTab();

	}

	/**
	 * ���̾�α� �ڽ��� ȭ�鿡 show �� �� �� ���̾�α� �ڽ��� chartElement�� ���� ��
	 * �õ� ChartElement�� set���ش�.
	 */
	public void show() {

	    ChartElement cse = getSelectedChartElementOnTheCurrentSpreadSheet();

		tabPattern.setChartElement( cse );
	    tabGrid.setChartElement( cse );

		this.initializeDialog();

		super.show();

	}

}