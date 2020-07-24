package jchartui;

/**
 * Title:           GridLineFormatEditor 클래스
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

public class GridLineFormatEditor extends Editor {

	// 전체를 담을 Panel
	JPanel contentPane;

	// 탭을 담을 Panel
	JTabbedPane tabPaneMain = new JTabbedPane();

	// 하나 하나의 탭에 해당하는 클래스들
    PatternTab tabPattern;
    GridTabForValueAxis tabGrid;

	// 확인, 취소 버튼
	JButton buttonOk = new JButton();
    JButton buttonCancel = new JButton();

	// text의 locale을 담고 있을 변수
	public Locale currentLocale;

    public GridLineFormatEditor() {

		// 축과 관련된 무늬 탭을 생성
		tabPattern = new PatternTab( PatternTab.LINE_MODE );

		// ValueAxis과 관련된 눈금 탭을 생성
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

		// Layout을 정한다.
		contentPane.setLayout(null);

		// Component들의 Bound를 정한다.
		this.setSize(new Dimension(447, 413));
		tabPaneMain.setBounds(new Rectangle(7, 6, 432, 342));
		buttonCancel.setBounds(new Rectangle(349, 360, 90, 20));
		buttonOk.setBounds(new Rectangle(247, 360, 90, 20));


	// Component들을 첨가시킨다.
	contentPane.add(tabPaneMain, null);

		tabPaneMain.add(tabPattern,  bundle.getString("patternTab") );
	tabPaneMain.add(tabGrid,  bundle.getString("scaleTab") );

		contentPane.add(buttonCancel, null);
	contentPane.add(buttonOk, null);

		// Component들의 Text를 설정한다.
	    this.setTitle(bundle.getString("gridLine"));        // "눈금선 서식"
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

		tabPattern.applyToChartElement();
		tabGrid.applyToChartElement();

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

	    this.setTitle(bundle.getString("gridLine"));        // "눈금선 서식"

	    tabPaneMain.add(tabPattern, bundle.getString("patternTab"));
	tabPaneMain.add(tabGrid, bundle.getString("scaleTab"));

		buttonOk.setText(bundle.getString("ok"));
	    buttonCancel.setText(bundle.getString("cancel"));

		this.tabPattern.changeMenuText();
		this.tabGrid.changeMenuText();

	}

	/**
	 * 다이얼로그 박스를 초기화 한다.
	 */
	public void initializeDialog() {

	    tabPattern.initializeDialogTab();
		tabGrid.initializeDialogTab();

	}

	/**
	 * 다이얼로그 박스를 화면에 show 할 때 이 다이얼로그 박스의 chartElement를 현재 선
	 * 택된 ChartElement로 set해준다.
	 */
	public void show() {

	    ChartElement cse = getSelectedChartElementOnTheCurrentSpreadSheet();

		tabPattern.setChartElement( cse );
	    tabGrid.setChartElement( cse );

		this.initializeDialog();

		super.show();

	}

}