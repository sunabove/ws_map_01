
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
import jcosmos.*;

public class EmptyStringView extends HtmlView {

  private Rectangle2D area = new Rectangle2D.Double(0, 0, 1, 1);

  public EmptyStringView(double width, double height) {
      area.setRect(0, 0, width, height);
  }

  public String tag(int zindex) {
      return "";
  }

  public Rectangle2D getArea() {
     return area;
  }

  public void paint(Graphics2D g2, int x, int y ) {  // x, y means bottom line
     if( debugFlag ) {
        Utility.debug(this, "painting text x = " + x + ", y = " + y );
     }

     Rectangle2D area = this.area;

     area.setRect( x, y - area.getHeight(), area.getWidth(), area.getHeight() );

     // Drawing debugging boundary

     if( debugFlag ) {
        g2.draw( area );
     }

     // End of drawing debuggin boundary
  }
}