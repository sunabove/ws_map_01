package jchart;

/**
 * Title:           DataLabelSymbol Ŭ����
 * Copyright:       Copyright (c) 2001
 * Company:         Techdigm Corp.
 * @author          withhim
 * @version 1.0
 * Description:     LegendItemSymbol�� ���������� �� ������ ��Ұ� ���� DataSereis��
 *                  ������ ǥ���ϱ� ���� Symbol�� DataLabel�� ���� ��Ÿ����. DataLabel��
 *                  DataLabelType�� DATA_LABEL_SHOW_LABEL�϶� ǥ�ð� �����ϴ�.
 */

import java.awt.*;

public class DataLabelSymbol extends ItemSymbol implements UnSelectable{

    public DataLabelSymbol(SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style) {

        super(sheet, parent, shape, style );

    }




}