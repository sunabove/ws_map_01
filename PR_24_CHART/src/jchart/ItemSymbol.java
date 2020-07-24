package jchart;

/**
 * Title:           ItemSymbol Ŭ����
 * Copyright:       Copyright (c) 2001
 * Company:         Techdigm Corp.
 * @author          withhim
 * @version 1.0
 * Description:     LegendItemSymbol �� DataLabelSymbol ��ü�� ���� ���� Ŭ������
 *                  LegendItemSymbol�� Selectable�� �ݸ� DataLabelSymbol�� Unselectable
 *                  �� ����� �ΰ�ü�� ����ι��� ���� ���� Ŭ������ ������ ���Ҵ�.
 */

import java.awt.*;

public class ItemSymbol extends ChartElement implements UnResizable{

    public ItemSymbol(SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style) {

        super(sheet, parent, shape, style );

    }

    public ChartElement cloneSelf(ChartElement parent, SpreadSheet sheet) {

        ShapeAndStyle sns = cloneShapeAndShapeStyle();

        ChartElement element = new ItemSymbol( sheet, parent, sns.getShape(), sns.getStyle() );

        return element;

    }



}