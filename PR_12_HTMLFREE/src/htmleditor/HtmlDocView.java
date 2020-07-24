
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
import java.awt.event.*;
import jcosmos.*;

public class HtmlDocView extends ImageView {

  private Vector rowViews = new Vector();
  private Vector imageViews = new Vector();

  private HtmlDocument doc;

  private int zindex = 0;

  public HtmlDocView(HtmlDocument doc) {

     super( doc );

     this.doc = doc;

  }

  public Vector getRowViewList() {

     return rowViews;

  }

  public HtmlDocument getHtmlDocument() {

    return doc;

  }

  public int getZindex() {

    return zindex;

  }

  public String tag(final int initZindex) {

//     Rectangle2D area = this.getArea();

     zindex = initZindex;

     String tag = "";

     // save background images
     tag += getImageTags( ImageElement.BACK_TEXT );
     // end of saving background images.

     // save text contents

     zindex ++;

     if( doc instanceof TableDocument ) {

	// ���̺� ��ť��Ʈ�� ��쿡�� ���� �±״� ������ �ʴ´�.

	// Do nothing!

     } else {

	// ���̺� ��ť��Ʈ�� �ƴ� ��쿡�� ���� �±׸� �����.

	Iterator it = rowViews.iterator();

	while( it.hasNext() ) {

	    HtmlView view = (HtmlView) it.next();

	    tag += view.tag( zindex ) + nl;

	}

	zindex ++;

     }

     // end of saving text contents

     // save rect images

     tag += getImageTags( ImageElement.RECT );

     this.zindex ++;

     // end of saving rect images

     // save foreground images

     tag += getImageTags( ImageElement.FRONT_TEXT );

     // end of saving foreground images.

     return tag;

  }

  public String getImageTags( final int style ) {

     String tag = "";

     ImageElement [] ies = this.doc.getImageElementsArray();

     for(int i = 0, len = ies.length; i < len ; i ++ ) {

	ImageElement ie = ies[ i ];

	if( ie.getStyle() == style) {
	   tag += ie.tag( ++ zindex);
//	   zindex ++;
	}

     }

     return tag;
  }

  final public StringElementAndMouseIndex getIndex(final int x, final int y, final MouseEvent e) {

      final RowView rowView = findNearestRowView( y );

      final Enumeration enumIt = rowView.elements(); // string elements enumIteartion

      while( enumIt.hasMoreElements() ) {

	 final Object obj = enumIt.nextElement();

	 if( obj instanceof StringView ) {

	    final StringView sv = (StringView) obj;

	    final int index = sv.getIndex( x );

	    if( index > -1 ) {

//               Utility.debug(this, "SELECTION INDEX = " + index );

	       return  new StringElementAndMouseIndex( sv.getStringElement(), index );

	    }

	 }

      }

      // �� ã������ ������ �ε����� �����Ѵ�.
      // ������ ����, ������ StingView�� ã�ƾ� �Ѵٴ� ���̴�.
      // EmptyStringView�� ã���� �� �ȴ�. �� ��......
      // EmptyStringview�� �̹����� ��ġ�� �κ��� ä��� ���� ��ü�̹Ƿ�,
      // x, y �� �ش��ϴ� �ε����� ������ ���� �ʴ�.

      final Vector svList = rowView.getHtmlViews();

      for(int i = svList.size() - 1; i > -1 ; i -- ) {

	  // �ε����� �������� Ž���Ѵ�.

	  final Object obj = svList.elementAt( i );

	  if( obj instanceof StringView ) {

	     Utility.debug(this ,"RETURN FINAL INDEX");

	     // ���ν� �巡��ÿ��� ��(one) ��ŭ �����Ͽ� �����Ѵ�.
	     // �׷��߸� ���� ������ ���õȴ�.
	     // ����� ����. �� �����ؾ� �ϴ� ���� ���߿� �� �� �ڼ��� ������� �Ѵ�.
	     // ��ư ������ �׷��� ���ش�. 2001/11/05/21/02 ��... ���ο� �ð��̴�.

	     final StringView sv = (StringView) obj;

	     final int index = sv.getEndIndex() + ( ( (e != null) && ( e.getID() == e.MOUSE_DRAGGED || e.getID() == e.MOUSE_RELEASED) ) ? 1 : 0 );

	     return new StringElementAndMouseIndex( sv.getStringElement(), index );

	  }

      }


      // �����ϴ� �ε����� ��Ʈ�� ������Ʈ�� ã�� ���ϸ� �� ���� �ѱ��.
      // ȣ���ϴ� �Լ������� �� ���� �Ѿ���� �����ϴ� ��Ʈ�� ������Ʈ�� �ε�����
      // ã�� ���� ��쿡 �ش��ϴ� ���̽��� ���ؼ� �˾Ƽ� ...ó���� �� �־�� �Ѵ�.
      return null;

  }

  final private RowView findNearestRowView(final int y) {

      final Vector rowViews = this.rowViews;

//      Utility.debug(this, "Y = " + y );

      final Enumeration enumIt = rowViews.elements();

      while( enumIt.hasMoreElements() ) {

	   final RowView rv = (RowView) enumIt.nextElement();

	   final Rectangle2D currArea = rv.getArea();

	   if( y < currArea.getY() + currArea.getHeight() + 1 ) {

		 return rv;

	   }

      }

      return (RowView) rowViews.lastElement();

//      Utility.debug(this, "ROWVIEW ID = " + rowViews.indexOf( rowView ) );

  }

  final public void addElement(final Object view) {

     if( view instanceof RowView ) {

	 rowViews.addElement( view );

     } else if( view instanceof ImageView ) {

	 imageViews.addElement( view );

     } else {

	 Utility.debug(this, "Warn: illegal view!" );

     }

  }

  // �ڵ� ũ�� ������
  // ����ڰ� ���콺�� ��� ������ �ּ� ������ �����ϴ� �������� �����Ѵ�.

  final public Rectangle2D getArea() {

    final Rectangle2D userSpecifiedArea = doc.getUserSpecifiedArea();

    final Dimension viewSize = getViewTotalSize();

    final double w = ( userSpecifiedArea.getWidth() > viewSize.width ) ? userSpecifiedArea.getWidth() : viewSize.width ;
    final double h = ( userSpecifiedArea.getHeight() > viewSize.height ) ? userSpecifiedArea.getHeight() : viewSize.height ;

    final Point2D docLoc = doc.getLocation();

    return new Rectangle2D.Double( docLoc.getX(), docLoc.getY(), w, h );

  }

  final protected Dimension getViewTotalSize() {

     final HtmlDocument doc = this.doc;

     double w = 0, h = 0;

     final Vector rowViews = this.rowViews;

     Enumeration enumIt = rowViews.elements();

     while( enumIt.hasMoreElements() ) {

	 final RowView rowView = (RowView) enumIt.nextElement();

	 final Rectangle2D rowArea = rowView.getArea();

	 final double rw = rowArea.getWidth();

	 w = ( w > rw ) ? w : rw;

	 h += ( rowArea.getHeight() + 1 );

     }

     final Insets scanInsets = doc.getScanMargin();

     w += ( scanInsets.left + scanInsets.right );
     h += ( scanInsets.top + scanInsets.bottom );

     // ���̺� ���� �ƴ� ��쿡��
     // ��ť��Ʈ �ϴܿ��� ���� Ű�� ĥ ��, ���ְ� ���̵��� �ϱ� ���ؼ�
     // �ϴ� ������ �����Ͽ� �ش�.

     if( ! doc.isTableCell() ) {

	  h += AppRegistry.DOCUMENT_ADDITIONAL_BOTTOM_INSET;

     }

     // ��. ��ť��Ʈ �ϴܿ��� ���ְ� ���̵��� �ϱ�. (���̺� ���� �ƴ� ��쿡��)

     double ph = h; // previous height

     final Vector imageViews = this.imageViews;

     enumIt = imageViews.elements();

     final double rightScanMargin = ( doc instanceof TableDocument ) ? 0 : doc.getScanMargin().right;

     while( enumIt.hasMoreElements() ) {

	 final ImageView iv = (ImageView) enumIt.nextElement();

	 final Rectangle2D ivArea = iv.getArea();

	 final double iw = ivArea.getX() + ivArea.getWidth() + rightScanMargin;

	 final double ih = ivArea.getY() + ivArea.getHeight();

	 w = ( w > iw ) ? w : iw;

	 h = ( h > ih ) ? h : ih;

     }

     // �̹��� ���� �ؽ�Ʈ ���� �Ѿ�� �ٽ� bottom inset ��ŭ ������ �ش�.
     // �ϴ� �μ¸�ŭ�� �����Ͽ��� �Ѵ�. ��.... ������ �� �������.
     // �� �Ǿ� �� �ٵ�....2001/11/07/15/11 ������ ��������, ��âȣ â�Ͽ� �Ｚ ȭ��� �ٵ��ϰ� �ִ�.
     // �����ϰ� �� ���� �ϴ� ��..
     // �� ���� �ڵ��� �� �̸�...

     if( h != ph && ( ! ( doc instanceof TableDocument ) ) ) {

	h +=  scanInsets.bottom;

     }

     return new Dimension( (int) w, (int) h );

  }

  public void paint(final Graphics2D g2) {

     final Point2D loc = doc.getLocation();

     // ��ť ��Ʈ ��ġ ��ŭ �̵�
     g2.translate( loc.getX(), loc.getY() );

     final Insets scanMargin = doc.getScanMargin();

     final int x = scanMargin.left;  // local x
     int y = scanMargin.top;  // local y

//     Utility.debug(this, "PAINTING HTML DOC VIEW....." );

     final HtmlDocument doc = this.doc;

     final boolean isTopMostDocument = doc.isTopMostDocument();

     if( isTopMostDocument ) {  // if top most document (word box )

	ImageView.SEL_IMG_VIEW = null; // unset selected image view
	ImageView.WEAK_SEL_IMG_VIEW = null; // unset weak selected image view

     }

     if( true ) { // ������ ��ü ������ �������� ä���.

	// ������ �ƹ��� ��ť��Ʈ�� ������ ��ǥ ���� ���� �Ѵ�.

	final Rectangle2D docArea = isTopMostDocument ? doc.getTopMostDocumentArea() : doc.getArea();

	g2.setColor( doc.getFillColor() );

	g2.fillRect( 0, 0, (int) docArea.getWidth(), (int) docArea.getHeight() );

     }

     // paint background images

     paintImages( g2, ImageElement.BACK_TEXT );

     // end of painting background images

     // paint string views

     final Vector rowViews = this.rowViews;

     final int size = rowViews.size();

//     Utility.debug(this, "PAINTING HTML DOC VIEW ROW VIEW SIZE = " + size );

     HtmlView view;

     for(int i = 0; i < size; i ++ ) {

	view = (HtmlView) rowViews.get( i );

	view.paint( g2, x, y);

	y += (view.getArea().getHeight() + 1 );

     }

     // end of painting string views

     // paint rect style and front style images

     paintImages( g2, ImageElement.RECT );

     paintImages( g2, ImageElement.FRONT_TEXT );

     // end of painting rect sytle and front style images

     if( isSelected() ) {

	ImageView.SEL_IMG_VIEW = this;

     } else if( doc == ImageElement.WEAK_SEL_IMG_ELEM ) {

	ImageView.WEAK_SEL_IMG_VIEW = this;

     }

     // End of drawing corner rectangles

     // ���� �ڽ��� ��輱 �׸��� ( ���õ��� ���� ��쳪 �ֻ��� ��ť ��Ʈ�� ��쿡�� �׸���. )

      if ( doc.isTableCell() ) {

	// ������ �ƹ��� ��ť��Ʈ�� ������ ��ǥ ���� ���� �Ѵ�.

	final Rectangle2D docArea = doc.getArea();

	final Stroke ps = g2.getStroke(); // previous stroke

	final int thick = doc.getBorderWidth(); // border width

	g2.setStroke( getBorderWidthStroke( 1 ) ); // set border width stroke

	g2.setColor( doc.getBorderColor() ); // set border line color

	// �ֻ��� ��ť��Ʈ�� ��� ���� ���� ��ŭ �����Ͽ� ���� ���ְ� ���̵��� �Ѵ�. ��..�� ���ִ�.

	final int bx = 0;  // border x
	final int by = 0;   // border y
	final int bw = (int) docArea.getWidth(); // border width
	final int bh = (int) docArea.getHeight(); // border height

	Shape boundaryArea;

	if( thick > 1 ) {

	  Area outerArea = new Area( new Rectangle( bx, by, bw, bh ) );
	  Area insideArea = new Area( new Rectangle( bx + thick, by + thick, bw - 2*thick + 1, bh - 2*thick + 1 ) );
	  outerArea.subtract( insideArea );

	  boundaryArea = outerArea;

	  g2.fill( boundaryArea );

	} else {

	  boundaryArea = new Rectangle( bx, by, bw, bh );

	  g2.draw( boundaryArea );

	}

	g2.setStroke( ps ); // unset stroke

     } else if( ( ! isSelected() ) || isTopMostDocument ) {

	// ������ �ƹ��� ��ť��Ʈ�� ������ ��ǥ ���� ���� �Ѵ�.

	final Rectangle2D docArea = isTopMostDocument ? doc.getTopMostDocumentArea() : doc.getArea();

	final Stroke ps = g2.getStroke(); // previous stroke

	final int borderWidth = doc.getBorderWidth(); // border width

	g2.setStroke( getBorderWidthStroke( borderWidth ) ); // set border width stroke

	g2.setColor( doc.getBorderColor() ); // set border line color

	// �ֻ��� ��ť��Ʈ�� ��� ���� ���� ��ŭ �����Ͽ� ���� ���ְ� ���̵��� �Ѵ�. ��..�� ���ִ�.
	final int calW = isTopMostDocument ? (borderWidth/2) : 0 ;

	final int bx = calW;  // border x
	final int by = calW;   // border y
	final int bw = (int) docArea.getWidth() - 2*calW; // border width
	final int bh = (int) docArea.getHeight() - 2*calW; // border height

	g2.drawRect( bx, by, bw , bh );

	g2.setStroke( ps ); // unset stroke

     }

     // ��. ���� �ڽ��� ��輱 �׸��� ( ���õ��� ���� ��츸 )

     if( isTopMostDocument ) {  // if top most document html view

	ImageView siv = ImageView.SEL_IMG_VIEW; // selected image view

	if( siv != null ) {

	    siv.paintSelected( g2 );

	}

	siv = ImageView.WEAK_SEL_IMG_VIEW;

	if( siv != null ) {

	    siv.paintSelected( g2 );

	}

     }

     // ��ť��Ʈ ��ġ ��ŭ �̵��� ���� ����

     g2.translate( - loc.getX(), - loc.getY() );

//     Utility.debug(this, "DONE PAINTING HTML DOC VIEW....." + doc.getArea() );

  }

  final public void paintImages(final Graphics2D g2, final int style ) {

     final Vector imageViews = this.imageViews;

     final int size = imageViews.size();

//     for(int i = size -1; i > -1; i -- ) {
     for(int i = 0; i < size; i ++ ) {

	final HtmlView view = (HtmlView) imageViews.get( i );
	final ImageElement ie = ((ImageView) view).getImageElement();

	if( ie.getStyle() != style ) {

	    continue;

	}

	if( view instanceof HtmlDocView ) {

	    final HtmlDocView hdv = (HtmlDocView) view; // html document view

	    hdv.paint( g2 );

	} else {

	    view.paint( g2, 0, 0);

	}

     }

  }

  // �� �Լ��� �ֻ��� �信�� ���õ� ����� �ϳ��� ��Ÿ���� ���ؼ�,
  // �����Ͱ� ����Ʈ �� �ÿ� �� �� �� �� �� ���� �ȴ�.
  // �׷��Ƿ�, ��� ��ǥ�� �׷��Ƚ��� �̵��� ������ ..... ���õ� ����� �׷����� �Ѵ�.

  public void paintSelected(final Graphics2D g2) {

    final HtmlDocument doc = this.doc;

    final Point2D absParLoc = doc.getAbsoluteParentLocation(); // absolute parent location

    g2.translate( absParLoc.getX(), absParLoc.getY() );

    final Color pc = g2.getColor(); // previous color

    final Stroke ps = g2.getStroke(); // previous storke

    // draws image view boundary

    if( AppRegistry.EDITOR_GRAPHIC_DEBUG_FLAG && doc.isTopMostDocument() ) { // �ֻ��� ��ť��Ʈ �� ��쿡 ����� ������ �׸�. ���߿� ���� ��� ��.

	g2.setColor( Color.red );

	g2.draw( doc.getDocumentInsideArea() );

    }

    if( this == WEAK_SEL_IMG_VIEW ) { // ���� ���õ� �̹��� ���� ��쿡.....

	// �ֻ��� ��ť��Ʈ�� �ƴϸ�...
	// document view �� ��쿡�� �׵θ��� ���ְ� �׷��ش�.

	g2.setStroke( ImageElement.getDraggedStroke() );

	g2.setColor( doc.getBorderColor() );

	g2.draw( doc.getDocumentBoundary() );

    } else if( doc.isTableCell() || doc instanceof TableDocument ) {

	final Rectangle2D area = getArea();

	g2.setStroke( getBorderWidthStroke( doc.getBorderWidth() ) );

	g2.setColor( doc.getBorderColor() );

	g2.draw( area );

    } else if( ! doc.isTopMostDocument() ) {

	// �ֻ��� ��ť��Ʈ�� �ƴϸ�...
	// document view �� ��쿡�� �׵θ��� ���ְ� �׷��ش�.

	final Paint pp = g2.getPaint(); // previous paint

	final boolean isEditMode = doc.isEditMode();

	g2.setPaint( getWordBoxBoundaryPaint( isEditMode  ) );

	g2.fill( doc.getDocumentBoundary() );

	g2.setPaint( pp ); // unset graphics paint

	g2.setStroke( getBorderWidthStroke( doc.getBorderWidth() ) );

	g2.setColor( doc.getBorderColor() );

	g2.draw( doc.getDocumentInsideArea() );

    }

    g2.setStroke( ps ); // unset stroke

    // draws corner rects

    if( ! doc.isTableCell() ) {

	paintCornerRects( g2 );

    }

    // end of drawing corner rects

    // graphics translation restoration
    g2.translate( - absParLoc.getX(), - absParLoc.getY() );

    g2.setColor( pc ); // unset color

    if( doc.isTableCell() ) {

      // �ƹ��� ��ť��Ʈ�� ���̺� ��ť��Ʈ�� ���õ� �ܰ��� ��� �׷� �ش�.

      doc.getParentDocument().getHtmlDocView().paintSelected( g2 );

    }

  }

}
