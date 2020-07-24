
/**
 * Title:        Test Publishing System<p>
 * Description:  Internet Based Test Publishing System V1.0<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package htmleditor;

import java.util.*;
import java.awt.*;
import java.awt.geom.*;
import jcosmos.*;

public class RowView extends HtmlView {

   private Vector htmlViews = new Vector();

   private Point loc = new Point(0, 0 );

   public String tag(int zindex) {

      String tag = "";

      Iterator it = this.htmlViews.iterator();

      while( it.hasNext() ) {
	 HtmlView view = (HtmlView) it.next();

	 tag += view.tag(zindex);
      }

      return tag;

   }

   public int getMaxDescentOfStringViews() {

       Iterator it = this.htmlViews.iterator();

       int descent = 0;

       while( it.hasNext() ) {
	   Object o = it.next();
	   if( o instanceof StringView ) {
	       StringView sv = (StringView) o;
	       int ld = sv.getDescent() ; // local descent
	       descent = (descent > ld ) ? descent : ld;
	   }
       }

       return descent;

   }

   public void paint(Graphics2D g2, int x, int y) {

       this.loc.x = x;
       this.loc.y = y;

       int size = this.htmlViews.size();

       int descent = this.getMaxDescentOfStringViews();

       HtmlView sv;
       Rectangle2D svSize;

       y += this.getHeight(); // 스트링 뷰는 베이스 라인을 기준으로 그린다.

       for(int i = 0; i < size; i ++ ) {

	  sv = (HtmlView) this.htmlViews.get( i );
	  svSize = sv.getArea();

	  if( sv instanceof StringView ) {

	     ((StringView)sv).paint( g2, x, y, descent);

	  } else {         // in case of empty view

	     sv.paint( g2, x, y);

	  }

	  x += (svSize.getWidth() + 1 );

       }

       if( debugFlag ) {
	  //Utility.message(this, "paint x = " + x + ", y = " + y);
	  Color org = g2.getColor();
	  g2.setColor(Color.red);
	  g2.draw( getArea() );
	  g2.setColor( org );
       }

   }


  public Enumeration elements() {

     return this.htmlViews.elements();

  }

  public Vector getHtmlViews() {

     return htmlViews;

  }

  public void addElement(HtmlView htmlView) {

    this.htmlViews.addElement( htmlView );

//    htmlView.setRowView( this );

  }

   public Rectangle2D getArea() {

       int size = this.htmlViews.size();

       HtmlView first = (HtmlView) this.htmlViews.firstElement();
       HtmlView last = (HtmlView) this.htmlViews.lastElement();

       if( first == null || last == null ) {

	  return new Rectangle2D.Double(loc.x, loc.y, 0, 0);

       } else {

	  Rectangle2D firstArea = first.getArea();
	  Rectangle2D lastArea = last.getArea();

	  double w = lastArea.getX() + lastArea.getWidth() - firstArea.getX();
	  double h = getHeight();

	  return new Rectangle2D.Double(loc.x, loc.y, w, h);

       }

   }

   public double getHeight() {

      int size = this.htmlViews.size();

      double h = 0;

      HtmlView view;

      double vh ; // view height;

      for(int i = 0; i < size; i ++ ) {

	   view = (HtmlView) this.htmlViews.get( i );

	   vh = view.getArea().getHeight();

	   h = ( h > vh ) ? h : vh;

      }

      return h;

   }

}