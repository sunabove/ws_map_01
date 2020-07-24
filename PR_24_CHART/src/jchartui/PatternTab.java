package jchartui;


import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;

import jchart.*;
import jcosmos.Utility;

import java.awt.geom.Rectangle2D;

import java.util.ResourceBundle;
import java.util.Locale;
/**
 * Title:
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class PatternTab extends TabPanel {

	// �׵θ��� ������ �ش��ϴ� Panel
    BorderPanel panelBorder;
    InteriorPanel panelInterior;
	//�̸����� Panel�� �׸� ���� Panel.
	PreviewPanelInPatternTab panelPreview;
	JPanel panelPreviewArea = new JPanel();

	//�� ����, ���� ������ ���� Panel
	TickMarkPanel panelMajorTickMark;
	TickMarkPanel panelMinorTickMark;

	// ���ݷ��̺��� ��ġ�� �����ϴ� radio ��ư�� ���� Panel
	TickLabelPositionPanel panelTickLabelPosition;

	// Panel���� Border ��ü��
	Border border1;
    TitledBorder titledBorder1, titledBorder2, titledBorder3,titledBorder4
				,titledBorder5;



	public static final byte LINE_MODE = 1;
	public static final byte AREA_MODE = 2;
	public static final byte AXIS_MODE = 3;

	private byte mode;


	public PatternTab(byte mode) {

		this.mode = mode;

		this.panelBorder = new BorderPanel( mode );
		this.panelPreview = new PreviewPanelInPatternTab( mode );
		panelBorder.setPreviewPanelInPatternTab( panelPreview );

		switch ( mode ) {

		case LINE_MODE :

			this.panelBorder = new BorderPanel( mode );
			this.panelPreview = new PreviewPanelInPatternTab( mode );
		    panelBorder.setPreviewPanelInPatternTab( panelPreview );
		    break;

		case AREA_MODE :

			this.panelBorder = new BorderPanel( mode );
			this.panelPreview = new PreviewPanelInPatternTab( mode );
		    panelBorder.setPreviewPanelInPatternTab( panelPreview );
			this.panelInterior = new InteriorPanel( mode );
			panelInterior.setPreviewPanelInPatternTab( panelPreview );
			break;

		case AXIS_MODE :

			this.panelMajorTickMark = new TickMarkPanel( AppRegistry.MAJOR_TYPE );
			this.panelMinorTickMark = new TickMarkPanel( AppRegistry.MINOR_TYPE );
			this.panelTickLabelPosition = new TickLabelPositionPanel();
			break;

		}

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
		panelBorder.setLayout(null);
		panelPreviewArea.setLayout(null);

		ResourceBundle bundle = ResourceBundle.getBundle("jchartui/PatternTabBundle", JCalcResource.getLocale());


		switch ( mode ) {
		case LINE_MODE :

		    // Component���� Border�� ���Ѵ�.
			border1 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));

	        titledBorder1 = new TitledBorder(border1, bundle.getString("border"));
            titledBorder2 = new TitledBorder(border1, bundle.getString("sample"));
		    panelBorder.setBorder(titledBorder1);
		    panelPreviewArea.setBorder(titledBorder2);

			this.add(panelBorder, null);
			this.add(panelPreviewArea, null);
		    panelPreviewArea.add(panelPreview, null);

			// Component���� ũ��� ��ġ�� ���Ѵ�.
            panelBorder.setBounds(new Rectangle(6, 6, 182, 206));
	        panelPreviewArea.setBounds(new Rectangle(6, 216, 182, 64));
            panelPreview.setBounds(new Rectangle(12, 20, 145, 35));

			break;

		case AREA_MODE :

		    // Component���� Border�� ���Ѵ�.
			border1 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));

	        titledBorder1 = new TitledBorder(border1, bundle.getString("border"));
            titledBorder2 = new TitledBorder(border1, bundle.getString("sample"));
			titledBorder3 = new TitledBorder(border1, bundle.getString("area"));

		    panelBorder.setBorder(titledBorder1);
		    panelPreviewArea.setBorder(titledBorder2);
			panelInterior.setBorder(titledBorder3);

			this.add(panelBorder, null);
			this.add(panelPreviewArea, null);
			this.add(panelInterior, null);
		    panelPreviewArea.add(panelPreview, null);

			// Component���� ũ��� ��ġ�� ���Ѵ�.
            panelBorder.setBounds(new Rectangle(6, 6, 182, 206));
	        panelPreviewArea.setBounds(new Rectangle(6, 216, 182, 64));
            panelPreview.setBounds(new Rectangle(12, 20, 145, 35));
			panelInterior.setBounds(new Rectangle(195, 6, 178, 274));

			break;

		case AXIS_MODE :

		    // Component���� Border�� ���Ѵ�.
			border1 = BorderFactory.createEtchedBorder(Color.white,new Color(142, 142, 142));

	        titledBorder1 = new TitledBorder(border1, bundle.getString("border"));
            titledBorder2 = new TitledBorder(border1, bundle.getString("sample"));
			titledBorder3 = new TitledBorder(border1, bundle.getString("majorTickMark"));
		    titledBorder4 = new TitledBorder(border1, bundle.getString("minorTickMark"));
			titledBorder5 = new TitledBorder(border1, bundle.getString("tickLabel"));

		    panelBorder.setBorder(titledBorder1);
		    panelPreviewArea.setBorder(titledBorder2);
			panelMajorTickMark.setBorder(titledBorder3);
		    panelMinorTickMark.setBorder(titledBorder4);
		    panelTickLabelPosition.setBorder(titledBorder5);

			this.add(panelBorder, null);
			this.add(panelPreviewArea, null);
		    panelPreviewArea.add(panelPreview, null);

			// Component���� ũ��� ��ġ�� ���Ѵ�.
            panelBorder.setBounds(new Rectangle(6, 6, 182, 206));
	        panelPreviewArea.setBounds(new Rectangle(6, 216, 182, 64));
            panelPreview.setBounds(new Rectangle(12, 20, 145, 35));
			panelMajorTickMark.setBounds(new Rectangle(194, 6, 200, 75));
			panelMinorTickMark.setBounds(new Rectangle(194, 86, 200, 75));
			panelTickLabelPosition.setBounds(new Rectangle(194, 166, 200, 75));

            this.add(panelMajorTickMark, null);
			this.add(panelMinorTickMark, null);
			this.add(panelTickLabelPosition, null);

			break;
		}


    }

	public ShapeStyle getShapeStyle(ShapeStyle shapeStyle) {

		if( panelPreview == null )

			return null;

		return panelPreview.getShapeStyle(shapeStyle);

	}


	public void changeMenuText() {

	    ResourceBundle bundle = ResourceBundle.getBundle("jchartui/PatternTabBundle", JCalcResource.getLocale());

        titledBorder1.setTitle(bundle.getString("border"));
        titledBorder2.setTitle(bundle.getString("sample"));

		if ( mode == AXIS_MODE ) {

            titledBorder3.setTitle( bundle.getString("majorTickMark") );
		    titledBorder4.setTitle( bundle.getString("minorTickMark") );
			titledBorder5.setTitle( bundle.getString("tickLabel") );

		}else{

			titledBorder3.setTitle(bundle.getString("area"));

		}

	}


	/**
	 * Dialog�� ȭ�鿡 Show�ɶ� ���õ� ChartElement�� ������ PatternTab�� �ʱ�ȭ
	 * ��Ű�� �Լ��̴�.
	 */
	public void initializeDialogTab() {

//		ShapeStyle style = this.chartElement.getShapeStyle();

	}

	/**
	 * PreviewPanel�� ��Ÿ�� ShapeStyle�� �ش� ���̾�α� �ڽ��� OK ��ư�� ������ ��
	 * ���� ���õ� ChartElement�� �����Ű�� �Լ��̴�.
	 */
	public void applyToChartElement () {

		Utility.debug(chartElement, "ChartElement");

		ShapeStyle styleFromChartElement = chartElement.getShapeStyle();



		if ( styleFromChartElement == null) {

			if ( chartElement instanceof DataSeriesView ) {

			    DataElementView [] dev = ( (DataSeriesView) chartElement ).getDataElementViews();

				ShapeStyle styleFromPreviewPanel = this.getShapeStyle(dev[0].getShapeStyle() );

				for (int i = 0; i < dev.length; i ++ ) {

				    dev[i].changeShapeStyle( styleFromPreviewPanel );

				}


		    }else {

				return;

			}
		} else {

		    ShapeStyle styleFromPreviewPanel = this.getShapeStyle(styleFromChartElement);

		    chartElement.changeShapeStyle( styleFromPreviewPanel );

		}

		if (this.mode == this.AXIS_MODE) {

		}

	}






}
