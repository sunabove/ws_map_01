package jchart;

/**
 * Title:           ItemSymbol 클래스
 * Copyright:       Copyright (c) 2001
 * Company:         Techdigm Corp.
 * @author          withhim
 * @version 1.0
 * Description:     LegendItemSymbol 과 DataLabelSymbol 개체를 위한 상위 클래스로
 *                  LegendItemSymbol은 Selectable한 반면 DataLabelSymbol은 Unselectable
 *                  한 관계로 두개체의 공통부문만 묶어 상위 클래스로 구현해 놓았다.
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