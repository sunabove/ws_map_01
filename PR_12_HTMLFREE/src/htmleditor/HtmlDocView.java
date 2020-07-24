
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

	// 테이블 도큐먼트인 경우에는 문자 태그는 만들지 않는다.

	// Do nothing!

     } else {

	// 테이블 도큐먼트가 아닌 경우에만 문자 태그를 만든다.

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

      // 못 찾았으면 마지막 인덱스를 리턴한다.
      // 주의할 점은, 마지막 StingView를 찾아야 한다는 것이다.
      // EmptyStringView를 찾으면 안 된다. 안 되......
      // EmptyStringview는 이미지와 겹치는 부분을 채우기 위한 객체이므로,
      // x, y 에 해당하는 인덱스를 가지고 있지 않다.

      final Vector svList = rowView.getHtmlViews();

      for(int i = svList.size() - 1; i > -1 ; i -- ) {

	  // 인덱스의 역순으로 탐색한다.

	  final Object obj = svList.elementAt( i );

	  if( obj instanceof StringView ) {

	     Utility.debug(this ,"RETURN FINAL INDEX");

	     // 마두스 드래깅시에만 일(one) 만큼 보정하여 리턴한다.
	     // 그래야만 줄의 끝까지 선택된다.
	     // 디버그 했음. 왜 보정해야 하는 지는 나중에 좀 더 자세히 살펴봐야 한다.
	     // 암튼 지금은 그렇게 해준다. 2001/11/05/21/02 음... 괴로운 시간이다.

	     final StringView sv = (StringView) obj;

	     final int index = sv.getEndIndex() + ( ( (e != null) && ( e.getID() == e.MOUSE_DRAGGED || e.getID() == e.MOUSE_RELEASED) ) ? 1 : 0 );

	     return new StringElementAndMouseIndex( sv.getStringElement(), index );

	  }

      }


      // 만족하는 인덱스의 스트링 엘리먼트를 찾지 못하면 널 값을 넘긴다.
      // 호출하는 함수에서는 널 값이 넘어오면 만족하는 스트링 엘리먼트와 인덱스를
      // 찾지 못한 경우에 해당하는 케이스에 대해서 알아서 ...처리를 해 주어야 한다.
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

  // 자동 크기 설정과
  // 사용자가 마우스로 끌어서 설정한 최소 영역을 보장하는 영역값을 리턴한다.

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

     // 테이블 셀이 아닌 경우에만
     // 도큐먼트 하단에서 엔터 키를 칠 때, 멋있게 보이도록 하기 위해서
     // 하단 마진을 보정하여 준다.

     if( ! doc.isTableCell() ) {

	  h += AppRegistry.DOCUMENT_ADDITIONAL_BOTTOM_INSET;

     }

     // 끝. 도큐먼트 하단에서 멋있게 보이도록 하기. (테이블 셀이 아닌 경우에만)

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

     // 이미지 폭이 텍스트 폭을 넘어서면 다시 bottom inset 만큼 보정해 준다.
     // 하단 인셋만큼만 보정하여야 한다. 흑.... 개념이 넘 어려웠음.
     // 잘 되야 할 텐데....2001/11/07/15/11 조훈현 마샤오춘, 이창호 창하오 삼성 화재배 바둑하고 있다.
     // 빨랑하고 좀 숴야 하는 데..
     // 이 분의 코딩은 왜 이리...

     if( h != ph && ( ! ( doc instanceof TableDocument ) ) ) {

	h +=  scanInsets.bottom;

     }

     return new Dimension( (int) w, (int) h );

  }

  public void paint(final Graphics2D g2) {

     final Point2D loc = doc.getLocation();

     // 도큐 먼트 위치 만큼 이동
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

     if( true ) { // 무조건 전체 영역을 배경색으로 채운다.

	// 영역은 아버지 다큐먼트로 부터의 좌표 부터 시작 한다.

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

     // 워드 박스의 경계선 그리기 ( 선택되지 않은 경우나 최상위 도큐 먼트일 경우에만 그린다. )

      if ( doc.isTableCell() ) {

	// 영역은 아버지 다큐먼트로 부터의 좌표 부터 시작 한다.

	final Rectangle2D docArea = doc.getArea();

	final Stroke ps = g2.getStroke(); // previous stroke

	final int thick = doc.getBorderWidth(); // border width

	g2.setStroke( getBorderWidthStroke( 1 ) ); // set border width stroke

	g2.setColor( doc.getBorderColor() ); // set border line color

	// 최상위 도큐먼트일 경우 보더 위스 만큼 보정하여 아주 멋있게 보이도록 한다. 흑..넘 멋있다.

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

	// 영역은 아버지 다큐먼트로 부터의 좌표 부터 시작 한다.

	final Rectangle2D docArea = isTopMostDocument ? doc.getTopMostDocumentArea() : doc.getArea();

	final Stroke ps = g2.getStroke(); // previous stroke

	final int borderWidth = doc.getBorderWidth(); // border width

	g2.setStroke( getBorderWidthStroke( borderWidth ) ); // set border width stroke

	g2.setColor( doc.getBorderColor() ); // set border line color

	// 최상위 도큐먼트일 경우 보더 위스 만큼 보정하여 아주 멋있게 보이도록 한다. 흑..넘 멋있다.
	final int calW = isTopMostDocument ? (borderWidth/2) : 0 ;

	final int bx = calW;  // border x
	final int by = calW;   // border y
	final int bw = (int) docArea.getWidth() - 2*calW; // border width
	final int bh = (int) docArea.getHeight() - 2*calW; // border height

	g2.drawRect( bx, by, bw , bh );

	g2.setStroke( ps ); // unset stroke

     }

     // 끝. 워드 박스의 경계선 그리기 ( 선택되지 않은 경우만 )

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

     // 도큐먼트 위치 만큼 이동한 것을 복원

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

  // 이 함수는 최상위 뷰에서 선택된 모습을 하나만 나타내기 위해서,
  // 에디터가 페인트 될 시에 딱 한 번 씩 만 실행 된다.
  // 그러므로, 상대 좌표로 그래픽스를 이동한 다음에 ..... 선택된 모습을 그려져야 한다.

  public void paintSelected(final Graphics2D g2) {

    final HtmlDocument doc = this.doc;

    final Point2D absParLoc = doc.getAbsoluteParentLocation(); // absolute parent location

    g2.translate( absParLoc.getX(), absParLoc.getY() );

    final Color pc = g2.getColor(); // previous color

    final Stroke ps = g2.getStroke(); // previous storke

    // draws image view boundary

    if( AppRegistry.EDITOR_GRAPHIC_DEBUG_FLAG && doc.isTopMostDocument() ) { // 최상위 도큐먼트 일 경우에 디버그 용으로 그림. 나중에 없애 줘야 함.

	g2.setColor( Color.red );

	g2.draw( doc.getDocumentInsideArea() );

    }

    if( this == WEAK_SEL_IMG_VIEW ) { // 약한 선택된 이미지 뷰일 경우에.....

	// 최상위 도큐먼트가 아니면...
	// document view 일 경우에만 테두리를 멋있게 그려준다.

	g2.setStroke( ImageElement.getDraggedStroke() );

	g2.setColor( doc.getBorderColor() );

	g2.draw( doc.getDocumentBoundary() );

    } else if( doc.isTableCell() || doc instanceof TableDocument ) {

	final Rectangle2D area = getArea();

	g2.setStroke( getBorderWidthStroke( doc.getBorderWidth() ) );

	g2.setColor( doc.getBorderColor() );

	g2.draw( area );

    } else if( ! doc.isTopMostDocument() ) {

	// 최상위 도큐먼트가 아니면...
	// document view 일 경우에만 테두리를 멋있게 그려준다.

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

      // 아버지 도큐먼트인 테이블 도큐먼트의 선택된 외관을 대신 그려 준다.

      doc.getParentDocument().getHtmlDocView().paintSelected( g2 );

    }

  }

}
