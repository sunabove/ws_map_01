package jchart;

/**
 * Title:           DataLabelSymbol 클래스
 * Copyright:       Copyright (c) 2001
 * Company:         Techdigm Corp.
 * @author          withhim
 * @version 1.0
 * Description:     LegendItemSymbol과 마찬가지로 한 데이터 요소가 속한 DataSereis를
 *                  색으로 표시하기 위한 Symbol로 DataLabel과 같이 나타난다. DataLabel의
 *                  DataLabelType이 DATA_LABEL_SHOW_LABEL일때 표시가 가능하다.
 */

import java.awt.*;

public class DataLabelSymbol extends ItemSymbol implements UnSelectable{

    public DataLabelSymbol(SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style) {

        super(sheet, parent, shape, style );

    }




}