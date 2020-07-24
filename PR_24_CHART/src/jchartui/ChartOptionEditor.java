package jchartui;

/**
 * Title:           ChartOption Ŭ����
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

public class ChartOptionEditor extends Editor {

	// ��ü�� ���� Panel
	JPanel contentPane;

	// ���� ���� Panel
	JTabbedPane tabPaneMain = new JTabbedPane();

	// �ϳ� �ϳ��� �ǿ� �ش��ϴ� Ŭ������� ���� Ŭ������ �����Ǿ� �ִ�.
    TitleTab tabTitle;
    AxisTab tabAxis;
	GridTab tabGrid;
	LegendTab tabLegend;
	DataLabelTab tabDataLabel;
	DataTableTab tabDataTable;


	// Ȯ��, ��� ��ư
	JButton buttonOk = new JButton();
    JButton buttonCancel = new JButton();

	// text�� locale�� ��� ���� ����
	public Locale currentLocale;

    public ChartOptionEditor() {

		// Tab Class�� ����
		tabTitle = new TitleTab();
		tabAxis = new AxisTab();
		tabGrid = new GridTab();
		tabLegend = new LegendTab();
		tabDataLabel = new DataLabelTab();
		tabDataTable = new DataTableTab();

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
		this.setSize(new Dimension(480, 300));
		tabPaneMain.setBounds(new Rectangle(7, 7, 463, 227));
		buttonCancel.setBounds(new Rectangle(303, 245, 75, 20));
		buttonOk.setBounds(new Rectangle(197, 245, 75, 20));


	// Component���� ÷����Ų��.
	contentPane.add(tabPaneMain, null);

	    tabPaneMain.add( tabTitle, bundle.getString("titleTab") );
		tabPaneMain.add( tabAxis, bundle.getString("axisTab") );
		tabPaneMain.add( tabGrid, bundle.getString("gridTab") );
		tabPaneMain.add( tabLegend, bundle.getString("legendTab") );
		tabPaneMain.add( tabDataLabel, bundle.getString("dataLabelTab") );
		tabPaneMain.add( tabDataTable, bundle.getString("dataTableTab") );

	contentPane.add(buttonCancel, null);
	contentPane.add(buttonOk, null);

		// Component���� Text�� �����Ѵ�.
	    this.setTitle(bundle.getString("chartOption"));        // "��Ʈ �ɼ�"
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

		tabTitle.applyToChartElement();
		tabAxis.applyToChartElement();
		tabGrid.applyToChartElement();
		tabLegend.applyToChartElement();
		tabDataLabel.applyToChartElement();
		tabDataTable.applyToChartElement();


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

	    this.setTitle(bundle.getString("chartOption"));        // "��Ʈ �ɼ�"

	    tabPaneMain.add(tabTitle, bundle.getString("titleTab") );
		tabPaneMain.add(tabAxis, bundle.getString("axisTab") );
		tabPaneMain.add(tabGrid, bundle.getString("gridTab") );
		tabPaneMain.add(tabLegend, bundle.getString("legendTab") );
		tabPaneMain.add(tabDataLabel, bundle.getString("dataLabelTab") );
		tabPaneMain.add(tabDataTable, bundle.getString("dataTableTab") );

		buttonOk.setText(bundle.getString("ok"));
	    buttonCancel.setText(bundle.getString("cancel"));

		tabTitle.changeMenuText();
		tabAxis.changeMenuText();
		tabGrid.changeMenuText();
		tabLegend.changeMenuText();
		tabDataLabel.changeMenuText();
		tabDataTable.changeMenuText();

	}

	/**
	 * ���̾�α� �ڽ��� �ʱ�ȭ �Ѵ�.
	 */
	public void initializeDialog() {

	    tabTitle.initializeDialogTab();
//		tabAxis.initializeDialogTab();
		tabGrid.initializeDialogTab();
	    tabLegend.initializeDialogTab();
		tabDataLabel.initializeDialogTab();
		tabDataTable.initializeDialogTab();

	}

	/**
	 * ���̾�α� �ڽ��� ȭ�鿡 show �� �� �� ���̾�α� �ڽ��� chartElement�� ���� ��
	 * �õ� ChartElement�� set���ش�.
	 */
	public void show() {

		ChartElement cse = getSelectedChartElementOnTheCurrentSpreadSheet();

		tabTitle.setChartElement( cse );
		tabAxis.setChartElement( cse );
		tabGrid.setChartElement( cse );
		tabLegend.setChartElement( cse );
		tabDataLabel.setChartElement( cse );
		tabDataTable.setChartElement( cse);

		this.initializeDialog();

		super.show();

	}

	/**
	 * TitleTab Ŭ����
	 */
	public class TitleTab extends TabPanel {

		// textField�� �̸��� ��Ÿ���� Label ��
		JLabel labelChartTitle = new JLabel();
		JLabel labelItemAxisTitle = new JLabel();
		JLabel labelValueAxisTitle = new JLabel();
		JLabel labelSecondItemAxisTitle = new JLabel();
		JLabel labelSecondValueAxisTitle = new JLabel();

		JTextField textFieldChartTitle = new JTextField();          // ��Ʈ ������ �Է��ϴ� TextField
		JTextField textFieldItemAxisTitle = new JTextField();       // X(�׸�)�� ������ �Է��ϴ� TextField
		JTextField textFieldValueAxisTitle = new JTextField();     // Y(��)�� ������ �Է��ϴ� TextField
		JTextField textFieldSecondItemAxisTitle = new JTextField(); // ���� X(�׸�) �� ������ �Է��ϴ� TextField
		JTextField textFieldSecondValueAxisTitle = new JTextField();// ���� Y(��) �� ������ �Է��ϴ� TextField

		public TitleTab() {
			try {
				jbInit();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		private void jbInit() throws Exception {

			// Component���� Layout�� ���Ѵ�.
			this.setLayout(null);

			// Component���� Addition�Ѵ�.
			this.add(labelChartTitle, null);
			this.add(textFieldChartTitle, null);
			this.add(labelItemAxisTitle, null);
			this.add(textFieldItemAxisTitle, null);
			this.add(labelValueAxisTitle, null);
			this.add(textFieldValueAxisTitle, null);
			this.add(labelSecondItemAxisTitle, null);
			this.add(textFieldSecondItemAxisTitle, null);
			this.add(labelSecondValueAxisTitle, null);
			this.add(textFieldSecondValueAxisTitle, null);

		    // Component���� Bound�� ���Ѵ�.
			labelChartTitle.setBounds(new Rectangle(5, 3, 150, 20));
			labelItemAxisTitle.setBounds(new Rectangle(5, 41, 150, 20));
			labelValueAxisTitle.setBounds(new Rectangle(5, 79, 150, 20));
			labelSecondItemAxisTitle.setBounds(new Rectangle(5, 117, 150, 20));
			labelSecondValueAxisTitle.setBounds(new Rectangle(5, 155, 150, 20));
			textFieldChartTitle.setBounds(new Rectangle(20, 23, 160, 18));
			textFieldItemAxisTitle.setBounds(new Rectangle(20, 61, 160, 18));
			textFieldValueAxisTitle.setBounds(new Rectangle(20, 99, 160, 18));
			textFieldSecondItemAxisTitle.setBounds(new Rectangle(20, 137, 160, 18));
			textFieldSecondValueAxisTitle.setBounds(new Rectangle(20, 175, 160, 18));

			// Component���� text�� ���Ѵ�.
			changeMenuText();

		}

		public Chart getChart() {

			if ( chartElement instanceof GraphExtent ) {

				return chartElement.getChart();

			}

//			if( ! ( super.chartElement instanceof Chart ) ) {
//
//				return null;
//
//			}

			return ( Chart) chartElement;

		}

		public ChartOption getChartOption() {

			return  getChart().getChartOption();

		}

		/**
		 * �ش� Tab�� ���õ� ��Ʈ�� ���缭 �ʱ�ȭ ��Ų��.
		 */
		public void initializeDialogTab() {

			ChartOption chartOption = getChartOption();

			//exception handling -- ���õ� chart�� null�϶� �׳� �����Ѵ�.
			if( chartOption == null ) {
				return;
			}

		    textFieldChartTitle.setText( chartOption.getChartTitle() );
			textFieldItemAxisTitle.setText( chartOption.getXAxisTitle() );
		    this.textFieldValueAxisTitle.setText( chartOption.getYAxisTitle() );


		}

		/**
		 * ��Ʈ ����, ������ ���� ���� ���õ� Chart�� ���� ��Ų��.
		 */
		public void applyToChartElement() {

			Chart chart = getChart();

			chart.applyChartTitle( textFieldChartTitle.getText() );
			chart.applyXAxisTitle( textFieldItemAxisTitle.getText() );
			chart.applyYAxisTitle( textFieldValueAxisTitle.getText() );

		}


		/**
		 * TabPanel ���� ��� component���� text�� �����Ѵ�.
		 * Scale�� �ٲ���� ���� ���Լ��� ȣ���Ѵ�.
		 */
		public void changeMenuText() {

			ResourceBundle bundle = ResourceBundle.getBundle("jchartui/ChartOptionEditorBundle", JCalcResource.getLocale());

		    labelChartTitle.setText( bundle.getString("chartTitle") );
			labelItemAxisTitle.setText( bundle.getString("xAxisTitle") );
			labelValueAxisTitle.setText( bundle.getString("yAxisTitle") );
			labelSecondItemAxisTitle.setText( bundle.getString("secondXTitle") );
			labelSecondValueAxisTitle.setText( bundle.getString("secondYTitle") );


		}


	}

	public class AxisTab extends TabPanel{

		JLabel labelPrimaryAxis = new JLabel();
		LinePanel line = new LinePanel();

		JCheckBox checkBoxItemAxis = new JCheckBox();
		JRadioButton radioButtonAutomatic = new JRadioButton();
		JRadioButton radioButtonItemScale = new JRadioButton();
		JRadioButton radioButtonTimeScale = new JRadioButton();
		JCheckBox checkBoxValueAxis = new JCheckBox();


		public AxisTab() {
			try {
				jbInit();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		private void jbInit() throws Exception {

			this.setLayout(null);


			this.add(checkBoxItemAxis, null);
			this.add(radioButtonAutomatic, null);
			this.add(radioButtonItemScale, null);
			this.add(radioButtonTimeScale, null);
			this.add(checkBoxValueAxis, null);
			this.add(labelPrimaryAxis, null);
			this.add(line, null);


			labelPrimaryAxis.setBounds(new Rectangle(5, 3, 72, 20));
			line.setBounds(new Rectangle(81, 8, 125, 12));
			checkBoxItemAxis.setBounds(new Rectangle(15, 23, 120, 20));
			radioButtonAutomatic.setBounds(new Rectangle(25, 43, 120, 18));
			radioButtonItemScale.setBounds(new Rectangle(25, 61, 120, 18));
			radioButtonTimeScale.setBounds(new Rectangle(25, 79, 120, 18));
			checkBoxValueAxis.setBounds(new Rectangle(15, 97, 120, 20));

			// Component���� text�� ���Ѵ�.
			changeMenuText();

		}

		/**
		 * �ش� Tab�� ���õ� ��Ʈ�� ���缭 �ʱ�ȭ ��Ų��.
		 */
		public void initializeDialogTab() {


		}

		public void applyToChartElement() {

		}


		/**
		 * TabPanel ���� ��� component���� text�� �����Ѵ�.
		 * Scale�� �ٲ���� ���� ���Լ��� ȣ���Ѵ�.
		 */
		public void changeMenuText() {

			ResourceBundle bundle = ResourceBundle.getBundle("jchartui/ChartOptionEditorBundle", JCalcResource.getLocale());

		    labelPrimaryAxis.setText( bundle.getString("primaryAxis") );
			checkBoxItemAxis.setText( bundle.getString("checkXAxis") );
			radioButtonAutomatic.setText( bundle.getString("auto") );
			radioButtonItemScale.setText( bundle.getString("category") );
			radioButtonTimeScale.setText( bundle.getString("timeScale") );
		    checkBoxValueAxis.setText( bundle.getString("checkYAxis") );

		}

	}

	public class GridTab extends TabPanel{

		LinePanel line1 = new LinePanel();
		JCheckBox checkBoxItemHasMajorGridlines = new JCheckBox();
		JCheckBox checkBoxItemHasMinorGridlines = new JCheckBox();
		JLabel labelItemAxis1 = new JLabel();

		JLabel labelValueAxis = new JLabel();
		LinePanel line2 = new LinePanel();
		JCheckBox checkBoxValueHasMajorGridlines = new JCheckBox();
		JCheckBox checkBoxValueHasMinorGridlines = new JCheckBox();


		public GridTab() {
			try {
				jbInit();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		private void jbInit() throws Exception {

			this.setLayout(null);

			this.add(checkBoxItemHasMajorGridlines, null);
			this.add(labelItemAxis1, null);
			this.add(line1, null);
			this.add(checkBoxItemHasMinorGridlines, null);
			this.add(line2, null);
			this.add(checkBoxValueHasMajorGridlines, null);
			this.add(labelValueAxis, null);
			this.add(checkBoxValueHasMinorGridlines, null);

			labelItemAxis1.setBounds(new Rectangle(5, 3, 72, 20));
			line1.setBounds(new Rectangle(81, 8, 125, 12));
			checkBoxItemHasMajorGridlines.setBounds(new Rectangle(15, 23, 120, 20));
			checkBoxItemHasMinorGridlines.setBounds(new Rectangle(15, 43, 120, 20));
			labelValueAxis.setBounds(new Rectangle(5, 65, 72, 20));
			line2.setBounds(new Rectangle(81, 70, 125, 12));
			checkBoxValueHasMajorGridlines.setBounds(new Rectangle(15, 85, 120, 20));
			checkBoxValueHasMinorGridlines.setBounds(new Rectangle(15, 105, 120, 20));

			// Component���� text�� ���Ѵ�.
			changeMenuText();

		}

		public Chart getChart() {

			if ( chartElement instanceof GraphExtent ) {

				return chartElement.getChart();

			}

//			if( ! ( super.chartElement instanceof Chart ) ) {
//
//				return null;
//
//			}

			return ( Chart) chartElement;

		}


		/**
		 * ���ݼ��� ���õ� ��Ʈ�� AxisOption�� ���� ���缭 �ʱ�ȭ ��Ų��.
		 */
		public void initializeDialogTab() {

			Chart chart = getChart();

			if ( chart == null ) {
				return;
			}

			ItemAxisOption iao = chart.getItemAxisOption();
			ValueAxisOption vao = chart.getValueAxisOption();

			checkBoxItemHasMajorGridlines.setSelected( iao.getHasMajorGridLineGroup() );
		    checkBoxItemHasMinorGridlines.setSelected( iao.getHasMinorGridLineGroup() );
			checkBoxValueHasMajorGridlines.setSelected( vao.getHasMajorGridLineGroup() );
			checkBoxValueHasMajorGridlines.setSelected( vao.getHasMinorGridLineGroup() );

		}

		public void applyToChartElement() {

			Chart chart = getChart();

			if ( chart == null ) {
				return;
			}

		    ValueAxis valueAxis = ( ValueAxis ) chart.getChartElement(ValueAxis.class);
			ItemAxis itemAxis = ( ItemAxis ) chart.getChartElement(ItemAxis.class);

			itemAxis.setHasMajorGridLineGroup( this.checkBoxItemHasMajorGridlines.isSelected() );
			itemAxis.setHasMinorGridLineGroup( this.checkBoxItemHasMinorGridlines.isSelected() );
			valueAxis.setHasMajorGridLineGroup( this.checkBoxValueHasMajorGridlines.isSelected() );
		    valueAxis.setHasMinorGridLineGroup( this.checkBoxValueHasMinorGridlines.isSelected() );

		}


		/**
		 * TabPanel ���� ��� component���� text�� �����Ѵ�.
		 * Scale�� �ٲ���� ���� ���Լ��� ȣ���Ѵ�.
		 */
		public void changeMenuText() {

			ResourceBundle bundle = ResourceBundle.getBundle("jchartui/ChartOptionEditorBundle", JCalcResource.getLocale());

		    labelItemAxis1.setText( bundle.getString("gridXAxis") );
			checkBoxItemHasMajorGridlines.setText( bundle.getString("xMajorGrid") );
			checkBoxItemHasMinorGridlines.setText( bundle.getString("xMinorGrid") );
			labelValueAxis.setText( bundle.getString("gridYAxis") );
			checkBoxValueHasMajorGridlines.setText( bundle.getString("yMajorGrid") );
		    checkBoxValueHasMinorGridlines.setText( bundle.getString("yMinorGrid") );

		}



	}

	public class LegendTab extends TabPanel {

		PlacementTab placementTab;

		public LegendTab() {

			placementTab = new PlacementTab( );

			try {
				jbInit();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		private void jbInit() throws Exception {

			// Component���� Layout�� ���Ѵ�.
			this.setLayout(null);

		    // Component���� Addition�Ѵ�.
			this.add( placementTab, null);

			// Component���� Bound�� ���Ѵ�.
			placementTab.setBounds(new Rectangle(0, 0, 207, 207));
			// Component���� text�� ���Ѵ�.
			changeMenuText();

		}

		/**
		 * �ش� Tab�� ���õ� ��Ʈ�� ���缭 �ʱ�ȭ ��Ų��.
		 */
		public void initializeDialogTab() {

			placementTab.initializeDialogTab();


		}


		public void applyToChartElement() {

			placementTab.applyToChartElement();

		}

		/**
		 * TabPanel ���� ��� component���� text�� �����Ѵ�.
		 * Scale�� �ٲ���� ���� ���Լ��� ȣ���Ѵ�.
		 */
	    public void changeMenuText() {

		    placementTab.changeMenuText();

		}

		public void setChartElement( ChartElement chartElement ) {

			super.setChartElement( chartElement );

			placementTab.setChartElement( chartElement );

		}


	}

	public class DataLabelTab extends TabPanel {

		DataLabelPanel dataLabelPanel;

		public DataLabelTab() {

			dataLabelPanel = new DataLabelPanel( dataLabelPanel.CHART_MODE );

			try {
				jbInit();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		private void jbInit() throws Exception {

			// Component���� Layout�� ���Ѵ�.
			this.setLayout(null);

		    // Component���� Addition�Ѵ�.
			this.add( dataLabelPanel, null);

			// Component���� Bound�� ���Ѵ�.
			dataLabelPanel.setBounds(new Rectangle(0, 0, 207, 207));

			// Component���� text�� ���Ѵ�.
			changeMenuText();

		}

		/**
		 * �ش� Tab�� ���õ� ��Ʈ�� ���缭 �ʱ�ȭ ��Ų��.
		 */
		public void initializeDialogTab() {

		    dataLabelPanel.initializeDialogTab();


		}


		public void applyToChartElement() {

			dataLabelPanel.applyToChartElement();

		}

		public void setChartElement( ChartElement chartElement ) {

			super.setChartElement( chartElement );

			dataLabelPanel.setChartElement( chartElement );

		}

		/**
		 * TabPanel ���� ��� component���� text�� �����Ѵ�.
		 * Scale�� �ٲ���� ���� ���Լ��� ȣ���Ѵ�.
		 */
	    public void changeMenuText() {

		    dataLabelPanel.changeMenuText();

		}




	}


	/**
	 * DataTabelTab Ŭ����
	 */
	public class DataTableTab extends TabPanel {

		// ������ ���̺� ǥ�� ���θ� ���³��� checkBox
		JCheckBox checkBoxHasDataTable = new JCheckBox();

		// ���� ǥ�� ǥ�� ���θ� ��Ÿ���� checkBox
		JCheckBox checkBoxShowLegend = new JCheckBox();


		public DataTableTab() {
			try {
				jbInit();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		private void jbInit() throws Exception {

			// Component���� Layout�� ���Ѵ�.
			this.setLayout(null);

			// Component���� Addition�Ѵ�.
			this.add(checkBoxShowLegend, null);
			this.add(checkBoxHasDataTable, null);

			// Component���� Bound�� ���Ѵ�.
			checkBoxShowLegend.setBounds(new Rectangle(10, 23, 150, 20));
			checkBoxHasDataTable.setBounds(new Rectangle(10, 3, 150, 20));

			// Component���� text�� ���Ѵ�.
			changeMenuText();

			// �����ʸ� ����Ѵ�.
			checkBoxHasDataTable.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(ActionEvent e) {
					checkBoxHasDataTable_actionPerformed(e);
				}
			});

		}

		public Chart getChart() {

			if ( chartElement instanceof GraphExtent ) {

				return chartElement.getChart();

			}

//			if( ! ( super.chartElement instanceof Chart ) ) {
//
//				return null;
//
//			}

			return ( Chart) chartElement;
		}





		/**
		 * �ش� Tab�� ���õ� ��Ʈ�� ���缭 �ʱ�ȭ ��Ų��.
		 */
		public void initializeDialogTab() {



		}

		public void applyToChartElement() {

			getChart().applyDataTable( checkBoxHasDataTable.isSelected(), checkBoxShowLegend.isSelected() );

		}

		/**
		 * TabPanel ���� ��� component���� text�� �����Ѵ�.
		 * Scale�� �ٲ���� ���� ���Լ��� ȣ���Ѵ�.
		 */
	    public void changeMenuText() {

			ResourceBundle bundle = ResourceBundle.getBundle("jchartui/ChartOptionEditorBundle", JCalcResource.getLocale());

		    checkBoxHasDataTable.setText( bundle.getString("hasDataTable") );
			checkBoxShowLegend.setText( bundle.getString("showLegend") );


		}

		/**
		 * checkBoxHasDataTable �� �����Ͽ��� ��쿡�� checkBoxShowLegend��
		 * ��� �����ϰ� ���ش�.
		 */
		void checkBoxHasDataTable_actionPerformed(ActionEvent e) {

			if ( checkBoxHasDataTable.isSelected() ) {
				checkBoxShowLegend.setEnabled( true );
			}else {
				checkBoxShowLegend.setEnabled( false );
			}

		}

	}
}