
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

public class ImageView extends HtmlView {

  protected static ImageView SEL_IMG_VIEW ;
  protected static ImageView WEAK_SEL_IMG_VIEW ;

  private ImageElement ie;

  public ImageView(ImageElement ie) {

     this.ie = ie;

  }

  public String tag(int zindex) {

     return "";

  }

  public String getHref() {

     return ie.getHref();

  }

  public boolean contains(int x, int y) {

     Rectangle2D area = this.getArea();

     double lx = area.getX(), ly = area.getY();

     if( lx <= x && ly <= y && x <= lx + area.getWidth() && y <= ly + area.getHeight() ) {
	 return true;
     } else {
	 return false;
     }

  }

  public ImageElement getImageElement() {

     return this.ie;

  }

  public Rectangle2D getArea() {

     return this.ie.getArea();

  }

  public boolean isSelected() {

     return ie.isSelected();

  }

  public Shape [] getCornerRects() {

     return this.ie.getCornerRects();

  }

  public void paint(Graphics2D g2, int x, int y) {

     ImageElement ie = this.ie;

     paintImageView( g2, x, y );

     if( isSelected() ) {

	ImageView.SEL_IMG_VIEW = this;

     }

  }

  private void paintImageView(Graphics2D g2, int x, int y ) {

     ImageElement ie = this.ie;

     Rectangle2D area = this.getArea();

     int lx = (int) area.getX(), ly = (int) area.getY();

     int width = (int) area.getWidth(), height = (int) area.getHeight();

     Color org = g2.getColor();

     // Drawing image

     Image image = ie.getImage();

     Utility.debug(this, "VIEW IMG = " + image );

     if( image != null ) {

	g2.drawImage( image, lx, ly, width, height, ie.getImageEditor() );

     }

     // End of drawing image

     // Drawing hyper link symbolic

     if( ie.getHref() != null && ie.getHref().length() > 0 ) {

	  int margin = 2;
	  int hx = lx - margin, hy = ly - margin;
	  int hw = width + 2*margin, hh = height + 2*margin;

	  g2.setColor( Color.blue );

	  g2.fillRect( hx, hy, hw, margin );
	  g2.fillRect( hx, hy, margin, hh );
	  g2.fillRect( hx + hw - margin, hy, margin, hh );
	  g2.fillRect( hx, hy + hh - margin, hw, margin );

     }

     // End of drawing hyper link symbolic

     g2.setColor( org ); // unset color

     if( debugFlag ) {

	Utility.debug(this, "painting img x = " + lx + ", y = " + ly + ", w = " + width + ", h = " + height );

     }

  }

  protected void paintCornerRects(final Graphics2D g2) {

    final Shape [] rects = getCornerRects();

    for( int i = 0, len = rects.length; i < len; i ++ ) {

	final Shape rect = rects[i];

	if( rect == null ) {

	  continue;

	}

	g2.setColor( Color.white );
	g2.fill( rect );
	g2.setColor( Color.black );
	g2.draw( rect );

    }

  }

  // 이 함수는 최상위 뷰에서 선택된 모습을 하나만 나타내기 위해서,
  // 에디터가 페인트 될 시에 딱 한 번 씩 만 실행 된다.
  // 그러므로, 상대 좌표로 그래픽스를 이동한 다음에 ..... 선택된 모습을 그려져야 한다.

  public void paintSelected(final Graphics2D g2) {

    final Point2D absParLoc = ie.getAbsoluteParentLocation(); // absolute parent location

    g2.translate( absParLoc.getX(), absParLoc.getY() );

    final Color pc = g2.getColor(); // previous color


    if( ie.getStyle() == ImageElement.RECT ) {

      // 렉트 스타일이일 경우에만 경계선을 검정색으로 한 번 더 그려 준다.
      // BACK, FRONT 스타일의 경우에는 코너 렉탱글만 그려 준다.
      // MS Word 와 같은 방식이다.
      // 단, 차이나는 것은 MS Word에서는 XOR 방식으로 그려 주는 데...
      // 여기에서는 그렇게 그려 주지는 않는다. 해 볼까 ...말까....

      final Rectangle2D area = getArea();

      g2.setColor( Color.black );

      g2.draw( area );

    }

    // draws corner rects

    paintCornerRects( g2 );

    // end of drawing corner rects

    // graphics translation restoration
    g2.translate( - absParLoc.getX(), - absParLoc.getY() );

    g2.setColor( pc ); // unset color

  }

  public void setLocation(Point loc) {

     this.setLocation( loc.x, loc.y);

  }

  public void setLocation(double x, double y) {

     Rectangle2D area = this.getArea();

     if( area.getX() != x || area.getY() != y ) {

	this.ie.setLocation( x, y );

     }

  }

  public void setSize(double width, double height) {

     Rectangle2D area = this.getArea();

     if( area.getWidth() != width || area.getHeight() != height ) {

	this.ie.setSize( width, height );

     }

  }

  public Stroke getBorderWidthStroke(int width) {

     return new BasicStroke( width );

  }

}