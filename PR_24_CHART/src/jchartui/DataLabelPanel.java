package jchartui;

/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author 1.0
 * @version 1.0
 */
import jchart.*;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

import java.util.ResourceBundle;

public class DataLabelPanel extends TabPanel {

	public static final byte CHART_MODE = 1;    // Chart 전체의 dataLabel에 해당하는 panel
	public static final byte DATA_MODE = 2;     // DataSeries, DataElement의 dataLabel에 해당하는 panel

	private byte mode;

	// mode가 DATA_MODE일 때 필요한 component
    JLabel labelDataLabel;
	LinePanel linePanel;

	// mode가 CHART_MODE일 때 필요한 component
	Border border1;
    TitledBorder titledBorder1;


    JRadioButton radioButtonDataLabelShowNone = new JRadioButton();
    JRadioButton radioButtonDataLabelShowValue = new JRadioButton();
    JRadioButton radioButtonDataLabelShowPercent = new JRadioButton();
    JRadioButton radioButtonDataLabelShowLabel = new JRadioButton();
    JRadioButton radioButtonDataLabelShowLabelAndPercent = new JRadioButton();
    JRadioButton radioButtonDataLabelShowBubbleSizes = new JRadioButton();

	JCheckBox checkBoxLegendKeyNextToLabel = new JCheckBox();

    public DataLabelPanel( byte mode ) {

		this.mode = mode;

        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/DataLabelTabBundle", JCalcResource.getLocale());

		// Component들의 Layout을 정한다.
        this.setLayout(null);

		if ( mode == CHART_MODE ) {

		    labelDataLabel = new JLabel();
			linePanel = new LinePanel();

            this.add(labelDataLabel);
			this.add(linePanel);
			labelDataLabel.setBounds(new Rectangle(3, 3, 101, 20));
		    linePanel.setBounds(new Rectangle(113, 8, 117, 12));


		} else {

		    titledBorder1 = new TitledBorder(border1, bundle.getString("dataLabel"));
			this.setBorder(titledBorder1);

		}


		// Component들을 Addition한다.
        this.add(checkBoxLegendKeyNextToLabel, null);
        this.add(radioButtonDataLabelShowLabelAndPercent, null);
        this.add(radioButtonDataLabelShowLabel, null);
        this.add(radioButtonDataLabelShowNone, null);
        this.add(radioButtonDataLabelShowValue, null);
        this.add(radioButtonDataLabelShowPercent, null);
        this.add(radioButtonDataLabelShowBubbleSizes, null);

		// Component들의 Bound를 정한다.
        radioButtonDataLabelShowNone.setBounds(new Rectangle(13, 23, 150, 18));
        radioButtonDataLabelShowValue.setBounds(new Rectangle(13, 41, 150, 18));
		radioButtonDataLabelShowPercent.setBounds(new Rectangle(13, 59, 150, 18));
        radioButtonDataLabelShowLabel.setBounds(new Rectangle(13, 77, 150, 18));
		radioButtonDataLabelShowLabelAndPercent.setBounds(new Rectangle(13, 95, 150, 18));
        radioButtonDataLabelShowBubbleSizes.setBounds(new Rectangle(13, 113, 150, 18));
		checkBoxLegendKeyNextToLabel.setBounds(new Rectangle(13, 141, 193, 20));



		// radioButton을 그룹화 한다.
		ButtonGroup showGroup = new ButtonGroup();
		showGroup.add(radioButtonDataLabelShowNone);
		showGroup.add(radioButtonDataLabelShowValue);
		showGroup.add(radioButtonDataLabelShowPercent);
		showGroup.add(radioButtonDataLabelShowLabel);
		showGroup.add(radioButtonDataLabelShowLabelAndPercent);
		showGroup.add(radioButtonDataLabelShowBubbleSizes);


		// Component들의 Text를 지정한다.
		this.changeMenuText();

		// 단축키를 지정한다.
		radioButtonDataLabelShowNone.setMnemonic('N');
		radioButtonDataLabelShowValue.setMnemonic('V');
        radioButtonDataLabelShowPercent.setMnemonic('P');
		radioButtonDataLabelShowLabel.setMnemonic('A');
        radioButtonDataLabelShowLabelAndPercent.setMnemonic('B');
        radioButtonDataLabelShowBubbleSizes.setMnemonic('L');
		checkBoxLegendKeyNextToLabel.setMnemonic('K');


		// checkBoxLegendKeyNextToLabel를 숨긴다.
		checkBoxLegendKeyNextToLabel.setEnabled( false );

		// 리스너를 등록한다.
		radioButtonDataLabelShowNone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                checkBoxLegendKeyNextToLabel.setEnabled( false );
            }
        });
        radioButtonDataLabelShowValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCheckBoxLegendKeyNextToLabel();
            }
        });
        radioButtonDataLabelShowPercent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCheckBoxLegendKeyNextToLabel();
            }
        });
        radioButtonDataLabelShowLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCheckBoxLegendKeyNextToLabel();
            }
        });
		radioButtonDataLabelShowLabelAndPercent.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCheckBoxLegendKeyNextToLabel();
            }
        });
        radioButtonDataLabelShowBubbleSizes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showCheckBoxLegendKeyNextToLabel();
            }
        });
    }


	/**
	 * DataLabelTab의 초기값을 설정하는 함수이다.
	 */
	public void initializeDialogTab() {

		byte dataLabelType  = AppRegistry.DATA_LABEL_SHOW_NONE;

		if ( chartElement instanceof DataSeriesView ) {

			DataSeriesView dsv = (DataSeriesView) chartElement;

			dataLabelType = dsv.getDataLabelType();

		} else if ( chartElement instanceof DataElementView ) {

			DataElementView dev = (DataElementView) chartElement;

		    dataLabelType = dev.getDataLabelType();

		} else {

		    Chart chart = chartElement.getChart();

			dataLabelType = chart.getDataLabelType();

		}

		switch (dataLabelType) {

		// DataSeriesView의 DataLabelType이 DataElement에 따라 다르면
		// DataLabelType을 나타내는 모든 radioButton을 unSelected 시킨다.
		case AppRegistry.DATA_LABEL_CANNOT_SHOW :
			    radioButtonDataLabelShowValue.setSelected(false);
				radioButtonDataLabelShowPercent.setSelected(false);
			    radioButtonDataLabelShowLabel.setSelected(false);
				radioButtonDataLabelShowLabelAndPercent.setSelected(false);
				radioButtonDataLabelShowBubbleSizes.setSelected(false);
		    break;
		case AppRegistry.DATA_LABEL_SHOW_NONE :
			    radioButtonDataLabelShowNone.setSelected(true);
			break;
		case AppRegistry.DATA_LABEL_SHOW_VALUE :
			    radioButtonDataLabelShowValue.setSelected(true);
			break;
		case AppRegistry.DATA_LABEL_SHOW_PERCENT  :
			    radioButtonDataLabelShowPercent.setSelected(true);
			break;
		case AppRegistry.DATA_LABEL_SHOW_LABEL  :
			    radioButtonDataLabelShowLabel.setSelected(true);
			break;
		case AppRegistry.DATA_LABEL_SHOW_LABEL_AND_PERCENT :
			    radioButtonDataLabelShowLabelAndPercent.setSelected(true);
			break;
		case AppRegistry.DATA_LABEL_SHOW_BUBBLE_SIZES :
			    radioButtonDataLabelShowBubbleSizes.setSelected(true);
			break;

		}
	}


	public void changeMenuText() {

	    ResourceBundle bundle = ResourceBundle.getBundle("jchartui/DataLabelTabBundle", JCalcResource.getLocale());

		if (mode == CHART_MODE) {
			labelDataLabel.setText(bundle.getString("dataLabel") );
		}else {
			titledBorder1.setTitle(bundle.getString("dataLabel"));
		}
		radioButtonDataLabelShowNone.setText(bundle.getString("none"));
        radioButtonDataLabelShowValue.setText(bundle.getString("showValue"));
        radioButtonDataLabelShowLabelAndPercent.setText(bundle.getString("showLabelAndPercent"));
        radioButtonDataLabelShowBubbleSizes.setText(bundle.getString("showBubbleSizes"));
        radioButtonDataLabelShowPercent.setText(bundle.getString("showPercent"));
        radioButtonDataLabelShowLabel.setText(bundle.getString("showLabel"));
        checkBoxLegendKeyNextToLabel.setText(bundle.getString("legendKeyNextToLabel"));

	}

	public void applyToChartElement() {

		byte dataLabelType  = AppRegistry.DATA_LABEL_SHOW_NONE;

		if (radioButtonDataLabelShowNone.isSelected() ) {

		    dataLabelType = AppRegistry.DATA_LABEL_SHOW_NONE;

		} else if (radioButtonDataLabelShowValue.isSelected() ) {

			dataLabelType = AppRegistry.DATA_LABEL_SHOW_VALUE;

		} else if (radioButtonDataLabelShowPercent.isSelected() ) {

			dataLabelType = AppRegistry.DATA_LABEL_SHOW_PERCENT;

		} else if (radioButtonDataLabelShowLabel.isSelected() ) {

		    dataLabelType = AppRegistry.DATA_LABEL_SHOW_LABEL;

		} else if (radioButtonDataLabelShowLabelAndPercent.isSelected() ) {

	        dataLabelType = AppRegistry.DATA_LABEL_SHOW_LABEL_AND_PERCENT;

		} else {

		    dataLabelType = AppRegistry.DATA_LABEL_SHOW_BUBBLE_SIZES;

		}

		boolean showDataLabelSymbol = checkBoxLegendKeyNextToLabel.isSelected();

		if ( chartElement instanceof DataSeriesView ) {

			DataSeriesView dsv = (DataSeriesView) chartElement;

			dsv.applyDataLabelView( dataLabelType, showDataLabelSymbol);

		} else if ( chartElement instanceof DataElementView ) {

			DataElementView dev = (DataElementView) chartElement;

		    dev.applyDataLabelView( dataLabelType, showDataLabelSymbol);

		} else {

		    Chart chart = (Chart) chartElement;

			chart.applyDataLabelView( dataLabelType, showDataLabelSymbol );

		}




	}

	/**
	 * CheckBoxLegendKeyNextToLabel을 화면에 show하는 함수로 DataLable이 존재할때
	 * DataLabelSymbol이 화면에 show할 수 있다.
	 */
	void showCheckBoxLegendKeyNextToLabel() {

		if ( checkBoxLegendKeyNextToLabel.isEnabled() ) {
			return;
		}

		checkBoxLegendKeyNextToLabel.setEnabled( true );

    }

    void radioButtonDataLabelShowLabel_actionPerformed(ActionEvent e) {

    }


}