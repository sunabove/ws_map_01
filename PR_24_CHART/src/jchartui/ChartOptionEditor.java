package jchartui;

/**
 * Title:           ChartOption 클래스
 * Copyright:       Copyright (c) juney<p>
 * Company:         Techdigm corp.
 * @author          withhim
 * @version 1.0
 * Description:     눈금선 서식 다이얼로그 박스에 해당하는 클래스
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import jchart.*;

import java.util.ResourceBundle;
import java.util.Locale;

public class ChartOptionEditor extends Editor {

	// 전체를 담을 Panel
	JPanel contentPane;

	// 탭을 담을 Panel
	JTabbedPane tabPaneMain = new JTabbedPane();

	// 하나 하나의 탭에 해당하는 클래스들로 내부 클래스로 구현되어 있다.
    TitleTab tabTitle;
    AxisTab tabAxis;
	GridTab tabGrid;
	LegendTab tabLegend;
	DataLabelTab tabDataLabel;
	DataTableTab tabDataTable;


	// 확인, 취소 버튼
	JButton buttonOk = new JButton();
    JButton buttonCancel = new JButton();

	// text의 locale을 담고 있을 변수
	public Locale currentLocale;

    public ChartOptionEditor() {

		// Tab Class들 생성
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

		// Layout을 정한다.
		contentPane.setLayout(null);

		// Component들의 Bound를 정한다.
		this.setSize(new Dimension(480, 300));
		tabPaneMain.setBounds(new Rectangle(7, 7, 463, 227));
		buttonCancel.setBounds(new Rectangle(303, 245, 75, 20));
		buttonOk.setBounds(new Rectangle(197, 245, 75, 20));


	// Component들을 첨가시킨다.
	contentPane.add(tabPaneMain, null);

	    tabPaneMain.add( tabTitle, bundle.getString("titleTab") );
		tabPaneMain.add( tabAxis, bundle.getString("axisTab") );
		tabPaneMain.add( tabGrid, bundle.getString("gridTab") );
		tabPaneMain.add( tabLegend, bundle.getString("legendTab") );
		tabPaneMain.add( tabDataLabel, bundle.getString("dataLabelTab") );
		tabPaneMain.add( tabDataTable, bundle.getString("dataTableTab") );

	contentPane.add(buttonCancel, null);
	contentPane.add(buttonOk, null);

		// Component들의 Text를 설정한다.
	    this.setTitle(bundle.getString("chartOption"));        // "차트 옵션"
		buttonOk.setText(bundle.getString("ok"));
	    buttonCancel.setText(bundle.getString("cancel"));


		// 리스너를 등록시킨다.
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
	 * 확인 버튼을 눌렀을 때...
	 *
	 * 다이얼로그 박스에서 적용했던 내용들을 선택된 chartElement에 적용시킨다.
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
	 * 취소 버튼을 눌렀을 때...
	 *
	 * 다이얼로그 박스가 show 되었을 때의 상태로 dialog 박스를 설정한다.
	 */
    void buttonCancel_actionPerformed(ActionEvent e) {

		initializeDialog();

		this.dispose();

    }

	/**
	 * 다이얼로그 박스 안의 모든 component들의 text를 설정한다.
	 * Scale이 바뀌었을 때도 이함수를 호출한다.
	 */
	public void changeMenuText() {

		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/EditorDialogBundle", JCalcResource.getLocale());

	    this.setTitle(bundle.getString("chartOption"));        // "차트 옵션"

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
	 * 다이얼로그 박스를 초기화 한다.
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
	 * 다이얼로그 박스를 화면에 show 할 때 이 다이얼로그 박스의 chartElement를 현재 선
	 * 택된 ChartElement로 set해준다.
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
	 * TitleTab 클래스
	 */
	public class TitleTab extends TabPanel {

		// textField의 이름을 나타내는 Label 들
		JLabel labelChartTitle = new JLabel();
		JLabel labelItemAxisTitle = new JLabel();
		JLabel labelValueAxisTitle = new JLabel();
		JLabel labelSecondItemAxisTitle = new JLabel();
		JLabel labelSecondValueAxisTitle = new JLabel();

		JTextField textFieldChartTitle = new JTextField();          // 차트 제목을 입력하는 TextField
		JTextField textFieldItemAxisTitle = new JTextField();       // X(항목)축 제목을 입력하는 TextField
		JTextField textFieldValueAxisTitle = new JTextField();     // Y(값)축 제목을 입력하는 TextField
		JTextField textFieldSecondItemAxisTitle = new JTextField(); // 보조 X(항목) 축 제목을 입력하는 TextField
		JTextField textFieldSecondValueAxisTitle = new JTextField();// 보조 Y(값) 축 제목을 입력하는 TextField

		public TitleTab() {
			try {
				jbInit();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}

		private void jbInit() throws Exception {

			// Component들의 Layout을 정한다.
			this.setLayout(null);

			// Component들을 Addition한다.
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

		    // Component들의 Bound를 정한다.
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

			// Component들의 text를 정한다.
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
		 * 해당 Tab을 선택된 차트에 맞춰서 초기화 시킨다.
		 */
		public void initializeDialogTab() {

			ChartOption chartOption = getChartOption();

			//exception handling -- 선택된 chart가 null일때 그냥 리턴한다.
			if( chartOption == null ) {
				return;
			}

		    textFieldChartTitle.setText( chartOption.getChartTitle() );
			textFieldItemAxisTitle.setText( chartOption.getXAxisTitle() );
		    this.textFieldValueAxisTitle.setText( chartOption.getYAxisTitle() );


		}

		/**
		 * 차트 제목, 축제목 등을 현재 선택된 Chart에 적용 시킨다.
		 */
		public void applyToChartElement() {

			Chart chart = getChart();

			chart.applyChartTitle( textFieldChartTitle.getText() );
			chart.applyXAxisTitle( textFieldItemAxisTitle.getText() );
			chart.applyYAxisTitle( textFieldValueAxisTitle.getText() );

		}


		/**
		 * TabPanel 안의 모든 component들의 text를 설정한다.
		 * Scale이 바뀌었을 때도 이함수를 호출한다.
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

			// Component들의 text를 정한다.
			changeMenuText();

		}

		/**
		 * 해당 Tab을 선택된 차트에 맞춰서 초기화 시킨다.
		 */
		public void initializeDialogTab() {


		}

		public void applyToChartElement() {

		}


		/**
		 * TabPanel 안의 모든 component들의 text를 설정한다.
		 * Scale이 바뀌었을 때도 이함수를 호출한다.
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

			// Component들의 text를 정한다.
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
		 * 눈금선을 선택된 차트의 AxisOption의 값에 맞춰서 초기화 시킨다.
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
		 * TabPanel 안의 모든 component들의 text를 설정한다.
		 * Scale이 바뀌었을 때도 이함수를 호출한다.
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

			// Component들의 Layout을 정한다.
			this.setLayout(null);

		    // Component들을 Addition한다.
			this.add( placementTab, null);

			// Component들의 Bound를 정한다.
			placementTab.setBounds(new Rectangle(0, 0, 207, 207));
			// Component들의 text를 정한다.
			changeMenuText();

		}

		/**
		 * 해당 Tab을 선택된 차트에 맞춰서 초기화 시킨다.
		 */
		public void initializeDialogTab() {

			placementTab.initializeDialogTab();


		}


		public void applyToChartElement() {

			placementTab.applyToChartElement();

		}

		/**
		 * TabPanel 안의 모든 component들의 text를 설정한다.
		 * Scale이 바뀌었을 때도 이함수를 호출한다.
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

			// Component들의 Layout을 정한다.
			this.setLayout(null);

		    // Component들을 Addition한다.
			this.add( dataLabelPanel, null);

			// Component들의 Bound를 정한다.
			dataLabelPanel.setBounds(new Rectangle(0, 0, 207, 207));

			// Component들의 text를 정한다.
			changeMenuText();

		}

		/**
		 * 해당 Tab을 선택된 차트에 맞춰서 초기화 시킨다.
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
		 * TabPanel 안의 모든 component들의 text를 설정한다.
		 * Scale이 바뀌었을 때도 이함수를 호출한다.
		 */
	    public void changeMenuText() {

		    dataLabelPanel.changeMenuText();

		}




	}


	/**
	 * DataTabelTab 클래스
	 */
	public class DataTableTab extends TabPanel {

		// 데이터 테이블 표시 여부를 나태내는 checkBox
		JCheckBox checkBoxHasDataTable = new JCheckBox();

		// 범례 표식 표시 여부를 나타내는 checkBox
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

			// Component들의 Layout을 정한다.
			this.setLayout(null);

			// Component들을 Addition한다.
			this.add(checkBoxShowLegend, null);
			this.add(checkBoxHasDataTable, null);

			// Component들의 Bound를 정한다.
			checkBoxShowLegend.setBounds(new Rectangle(10, 23, 150, 20));
			checkBoxHasDataTable.setBounds(new Rectangle(10, 3, 150, 20));

			// Component들의 text를 정한다.
			changeMenuText();

			// 리스너를 등록한다.
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
		 * 해당 Tab을 선택된 차트에 맞춰서 초기화 시킨다.
		 */
		public void initializeDialogTab() {



		}

		public void applyToChartElement() {

			getChart().applyDataTable( checkBoxHasDataTable.isSelected(), checkBoxShowLegend.isSelected() );

		}

		/**
		 * TabPanel 안의 모든 component들의 text를 설정한다.
		 * Scale이 바뀌었을 때도 이함수를 호출한다.
		 */
	    public void changeMenuText() {

			ResourceBundle bundle = ResourceBundle.getBundle("jchartui/ChartOptionEditorBundle", JCalcResource.getLocale());

		    checkBoxHasDataTable.setText( bundle.getString("hasDataTable") );
			checkBoxShowLegend.setText( bundle.getString("showLegend") );


		}

		/**
		 * checkBoxHasDataTable 를 선택하였을 경우에만 checkBoxShowLegend을
		 * 사용 가능하게 해준다.
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