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
import java.awt.event.*;

public class GridLineGroup extends ChartElement implements ChildSelectable, UnMovable {

	private byte gridLineType;




    public GridLineGroup(SpreadSheet sheet, ChartElement parent, Shape shape, ShapeStyle style, byte gridLineType) {
        super(sheet, parent, shape, style );

	    this.gridLineType = gridLineType;
    }

    public ChartElement cloneSelf(ChartElement parent, SpreadSheet sheet) {

        ShapeAndStyle sns = cloneShapeAndShapeStyle();

        GridLineGroup element = new GridLineGroup( sheet, parent, sns.getShape(), sns.getStyle(), this.gridLineType );

        return element;

    }

	public byte getGridLineType() {

		return this.gridLineType;

    }


}