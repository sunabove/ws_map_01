
/**
 * Title:        Test Publishing System<p>
 * Description:  Internet Based Test Publishing System V1.0<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package htmleditor;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import jcosmos.*;

public abstract class HtmlView implements CharacterUtility {

  boolean debugFlag = false; //Utility.debugFlag; // debugging flag

//  private RowView rowView;

  abstract public void paint(Graphics2D g2, int x, int y);

  abstract public String tag(int zindex);

  abstract public Rectangle2D getArea();

  private static final int [][] WORD_BOX_EDIT_BOUNDARY_PIXELS = {

      { 0, 0, 0, 1 },
      { 0, 0, 1, 0 },
      { 0, 1, 0, 0 },
      { 1, 0, 0, 0 }

  };

  private static final int [][] WORD_BOX_RESHAPE_BOUNDARY_PIXELS = {

      { 0, 0, 1, 0 },
      { 1, 0, 0, 0 },

  };

  private final static Paint WORD_BOX_EDIT_BOUNDARY_PAINT = Utility.createTexturePaint( WORD_BOX_EDIT_BOUNDARY_PIXELS, Color.black );

  private final static Paint WORD_BOX_RESHAPE_BOUNDARY_PAINT = Utility.createTexturePaint( WORD_BOX_RESHAPE_BOUNDARY_PIXELS, Color.black );

  protected final Paint getWordBoxBoundaryPaint(final boolean isEditMode ) {

    return isEditMode ? WORD_BOX_EDIT_BOUNDARY_PAINT : WORD_BOX_RESHAPE_BOUNDARY_PAINT;

  }

//  public static Graphics2D getGraphics2D() {
//
//    return HtmlElement.getGraphics2D();
//
//  }

}