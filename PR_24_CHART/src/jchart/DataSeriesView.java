package jchart;

/**
 * Title:        java chart project
 * Description:  jchart v1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;

public class DataSeriesView extends ChartElement implements ChildSelectable, UnMovable, UnResizable, MultipleChild, ParentSelectable {

  private ChartType chartType;
  private DataSeries dataSeries;
  private boolean initedDataElementViews;

  public ChartElement cloneSelf(ChartElement parent, SpreadSheet sheet) {

     ShapeAndStyle sns = cloneShapeAndShapeStyle();

     DataSeriesView element = new DataSeriesView( sheet, parent, sns.getShape(), sns.getStyle(), chartType, dataSeries );

     element.initedDataElementViews = this.initedDataElementViews;

     return element;

  }

  public DataSeriesView(SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style,
			ChartType chartType, DataSeries dataSeries) {
      super(sheet, parent, shape, style );

      this.chartType = chartType;

      setDataSeries(dataSeries);

      // adds data element views

      DataElement des [] = dataSeries.getDataElements();

      if( chartType.isLinearChartType() ) {

	 for(int i = 0, len = des.length; i < len; i ++ ) {
	     DataElementView dev = new LinearDataElementView( sheet, this, null, null, chartType, des[ i ] );
	     addChild( dev );
	 }

      } else {

	 for(int i = 0, len = des.length; i < len; i ++ ) {
	     DataElementView dev = new DataElementView( sheet, this, null, null, chartType, des[ i ] );
	     addChild( dev );
	 }

      }

      // end of adding data element views

  }

    public DataSeries getDataSeries() {

	return dataSeries;

    }

    public void setDataSeries(DataSeries dataSeries) {

	    this.dataSeries = dataSeries;
		dataSeries.setDataSeriesView(this);
	}

  public DataElementView [] getDataElementViews () {
      ChartElement [] childs = getChilds();

      int len = childs.length;

      DataElementView [] des = new DataElementView[ len ];

      for(int i = 0; i < len; i ++ ) {
	  des[ i ] = (DataElementView) childs[ i ];
      }

      return des;

  }

  public void paint(Graphics2D g2) {

     if( ! initedDataElementViews ) {

	 setShapeAndStyle();

     }

     super.paint( g2 );

  }

  public void paint3DSelfOnly(float tx, float ty, Graphics2D g2, float proX, float proY ) {

     if( ! initedDataElementViews ) {

	 setShapeAndStyle();

     }

     super.paint3DSelfOnly( tx, ty, g2, proX, proY );

  }

  public boolean isInitedDataElementViews() {

      return initedDataElementViews;

  }

  public void setShapeAndStyle() {

     chartType.setShapeAndStyle( this );

     initedDataElementViews = true;

  }

//  public String toString() {
//
//     return "";
//
//  }

	/**
	 * 현재 담고 있는 Datalabel의 Text의 종류를 나타낸다.
	 * 담고 있는 DataLabel이 DataElementView에 따라 다르면 DATA_LABEL_CANNOT_SHOW를 리턴한다.
	 *
	 * @see     DataElementView Class의 getDataLabelType()
	 */
	public byte getDataLabelType() {

		DataElementView [] dev = getDataElementViews();

		byte dataLabelType = dev[0].getDataLabelType();

		for ( int i = 1; i < dev.length; i++ ) {

			if (dataLabelType != dev[i].getDataLabelType()) {

			dataLabelType = dev[i].getDataLabelType();

			}

		}

		if ( dataLabelType != dev[0].getDataLabelType() ) {

			dataLabelType = AppRegistry.DATA_LABEL_CANNOT_SHOW;

		}

		return dataLabelType;

	}


	/**
	 * 선택된 DataSeriesView에 원하는 dataLabelType, showDataLabelSymbol을 가지는
	 * DataLabelview를 생성하는 함수로 DataSeriesView에 속해 있는 모든 DataElementView
	 * 의 applyDataLabelView 함수를 호출한다.
	 *
	 * @param   dataLabelType : DataLabel에 나타나는 Text의 종류를 나타내는 변수
	 * @param   showDataLabelSymbol : DataLabel 옆에 범례 표식 표시여부를 나타내는 변수
	 * @see     DataElementView Class의 applyDataLabelView( byte, boolean )
	 */
	public void applyDataLabelView( byte dataLabelType, boolean showDataLabelSymbol) {

		DataElementView [] dev = getDataElementViews();

		for ( int i = 0; i < dev.length; i++ ) {

			dev[i].applyDataLabelView( dataLabelType, showDataLabelSymbol);

	    }

	}

}