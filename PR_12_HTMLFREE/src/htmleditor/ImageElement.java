
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
import java.net.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import javax.swing.*;
import java.util.*;
import jcosmos.*;

public class ImageElement extends HtmlElement implements HtmlComment, ModeInterface {

  // static memebers definition

  protected static ImageElement SEL_IMG_ELEM;  // 선택된 이미지 엘리먼트는 하나이다. 전체에서
  protected static ImageElement WEAK_SEL_IMG_ELEM; // 선택된 이미지 엘리먼트.. 약하게...도큐먼트 붙여넣기 후에....
						    // 선택된 것 처럼 보여주기 위해서..실제 선택된 것은 아니지만....

  protected static MouseEvent LAST_MOUSE_EVENT;
  protected static Shape XOR_AREA_SHAPE;

  // 마우스 토폴로지 값을 정의한다.

  protected static final int OUT_OF_AREA = -1, ON_THE_BOUNDARY = 8,
			     BOTTOM_BOUNDARY = 9,
			     LEFT_BOUNDARY = 10, RIGHT_BOUNDARY = 11,
			     INSIDE_AREA = 12;

  protected static final Color XOR_COLOR = Color.yellow;

  // 이미지 엘리먼트의 레이아웃(스타일)을 정의 한다.

  protected static final int BACK_TEXT = 0, RECT = 1, FRONT_TEXT = 2;

  protected static final int CORNER_RECT_WIDTH = 6;

  protected static final Stroke DRAGGED_STROKE =
	 new BasicStroke( 1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float [] { 5, 5 }, 0 );

  // end of static members definition

  protected int style = RECT;

  protected File src;  // image source

  protected URL linkUrl; // link image url

  private double x, y;
  private double w = AppRegistry.MINIMUM_IMAGE_WIDTH;
  private double h = AppRegistry.MINIMUM_IMAGE_HEIGHT;

  private Image img;

  private static int LAST_ID;

  private int id = LAST_ID ++ ;

  protected ImageElement() {
  }

  public ImageElement(HtmlDocument parentDoc, File src ) {

      init( parentDoc, src, new Point(0, 0) );

  }

  protected boolean isEditMode() {

      return mode.isMode( Mode.EDIT );

  }

  public ImageElement(HtmlDocument parentDoc, File src, Rectangle2D area ) {

     init( parentDoc, src, area, RECT, null );

  }

  public ImageElement(HtmlDocument parentDoc, File src, Rectangle2D area, int style, String href ) {

      init( parentDoc, src, area, style, href );

  }

  public ImageElement(HtmlDocument parentDoc, File src, URL linkUrl, Rectangle2D area, int style, String href ) {

      this.linkUrl = linkUrl;

      init( parentDoc, src, area, style, href );

  }

  public ImageElement(final HtmlDocument parentDoc, final File src, final Point2D loc) {

      init( parentDoc, src, loc );

  }

  public ImageElement(final HtmlDocument parentDoc, final URL url, final Point2D loc) {

      init( parentDoc, url, loc );

  }

  public ImageElement(final HtmlDocument parentDoc, final URL url, final Rectangle2D area) {

      init( parentDoc, url, area );

  }

  public Object clone(final HtmlDocument parentDoc) {

      final Rectangle2D area = this.getArea();

      final Rectangle2D cloneArea = new Rectangle2D.Double( x, y, w, h );

      ImageElement clone = new ImageElement( parentDoc, src, linkUrl, cloneArea, style, href );

//      clone.linkUrl = this.linkUrl;

      return clone;

  }

  public void init(final HtmlDocument parentDoc, final File src ) {

      init( parentDoc, src, new Point(0, 0) );

  }

  public void init(final HtmlDocument parentDoc, final File src, final Rectangle2D area ) {

     init( parentDoc, src, area, RECT, null );

  }

  public void init(final HtmlDocument parentDoc, final File src, final Rectangle2D area, final int style, final String href ) {

      init( parentDoc, src, new Point2D.Double(0, 0) );

      this.style = style;

      this.href = href;

      this.setArea( area );

  }

  public void init( final HtmlDocument parentDoc, File src, final Point2D loc) {

      this.parentDoc = parentDoc;

      try {

	src = cloneFileOnTheTempDir( src );

	Utility.debug( this, "" + src );

      } catch (Exception e) {

	Utility.debug( e );

      }

      this.src = src;

      this.img = createImage( src );

      setImageAreaBeautifully( loc, img );

  }

  public void init(HtmlDocument parentDoc, URL url, Point2D loc) {

      this.parentDoc = parentDoc;

      this.linkUrl = url;

      final Image img = getImage( url );

      setImageAreaBeautifully( loc, img );

  }

  public void init(HtmlDocument parentDoc, URL url, Rectangle2D area) {

      this.parentDoc = parentDoc;

      this.linkUrl = url;

      final Image img = getImage( url );

      setArea( area );

  }

  private void setImageAreaBeautifully(final Point2D loc, final Image img ) {

      double x = loc.getX();
      double y = loc.getY();

      final Component editor = getImageEditor();

      double w = img.getWidth( editor );
      double h = img.getHeight( editor );

      w = w == 0 ? 1 : w;
      h = h == 0 ? 1 : h;

      final Rectangle2D area = new Rectangle2D.Double( x, y, w, h );

      final Rectangle2D bounds = getBounds();

      Utility.debug( this, "AREA = " + area );
      Utility.debug( this, "BOUNDS = " + bounds );

      if( bounds.contains( area ) ) {

	this.setArea( x, y, w, h ) ;

      } else { // if( area.intersects( bounds ) ) {

	final double bx = bounds.getX();
	final double by = bounds.getY();
	final double bw = bounds.getWidth();
	final double bh = bounds.getHeight();

	final double whrate = (w + 0.0)/(h + 0.0);

	if( w > bw ) {

	  w = bw;
	  h = w/whrate;

	}

	if( h > bh ) {

	  h = bh;
	  w = whrate*h;

	}

//	if( x + w > bx + bw ) {
//
//	  x = bx + bw - w;
//
//	}
//
//	if( y + h > by + bh ) {
//
//	  y = by + bh - h;
//
//	}

	this.setArea( x, y, w, h );

      }

  }

  public Rectangle2D getBounds() {

      final HtmlDocument parDoc = getParentDocument();

      if( parDoc == null ) {

	return null;

      }

      if( parDoc.isTopMostDocument() ) {

	final HtmlEditorPane editorPane = parDoc.getDocumentEditor();

	final JHtmlEditorScrollPane scrollPane = editorPane.getScroller();

	final Insets insets = parDoc.getInsets();

	return new Rectangle2D.Double(
		    insets.left,
		    insets.top,
		    editorPane.getWidth() - insets.left - insets.right,
		    scrollPane.getHeight()*2 - insets.top - insets.bottom
		 );

      }

      final Rectangle2D parDocArea = parDoc.getArea();

      return new Rectangle2D.Double( 0, 0, parDocArea.getWidth(), parDocArea.getHeight() );

  }

  public HtmlEditorPane getImageEditor() {

      return getParentDocument().getDocumentEditor();

  }

  private File cloneFileOnTheTempDir(File file) throws IOException {

     if( file == null ) {

	return null;

     }

     String fileName = file.getName();

     int dotIdx = fileName.lastIndexOf( '.' );

     if( dotIdx > -1 ) {

	 fileName = "" + Math.abs( (int)(System.currentTimeMillis() ) ) + fileName.substring( dotIdx );

     } else {

	 fileName = "" + Math.abs( (int)(System.currentTimeMillis() ) );

     }

     File tempDir = HtmlEditorPane.getTempDir( getImageEditor().getScroller() );

     File clone = new File( tempDir, fileName );

     new WebCopy( null ).copy( file, clone );

     return clone;

  }

  protected Image createImage(File src ) {

      if( this instanceof AudioElement ) {

	  return Utility.getResourceImage("audio_symbol.jpg");

      } else if( this instanceof VideoElement ) {

	  return Utility.getResourceImage("video_symbol.jpg");

      } else if( this instanceof ChartElement ) {

	  return this.getImage();

      } else if( this.isLinkImage() ) {

	  return this.getImage();

      } else {

	  return new ImageIcon( src.getAbsolutePath() ).getImage();

      }

  }

  public boolean isLinkImage() {

      Utility.debug( this, "LINK URL = " + linkUrl );

      return linkUrl != null;

  }

  public URL getLinkURL() {

      return linkUrl;

  }

  public void setLinkURL(URL linkUrl) {

     this.linkUrl = linkUrl;

     this.img = getImage( linkUrl );

  }

  public String getSourceName() {

      if( this.linkUrl != null ) {

	return "" + this.linkUrl;

      }

      return this.src.getName();

  }

  protected String getSrcLocation() {

      final String name = getSourceName();

      if( isLinkImage() ) {

	  return name;

      }

      final String base = ( AppRegistry.RSC_BASE_DIR_URL == null ) ? "" : AppRegistry.RSC_BASE_DIR_URL;

      final String src = base + "./" + name;

      return src;

  }

  public String getNameTag() {

      return "";

  }

  public String tag() {

      final String src = getSrcLocation();

      final Rectangle2D area = getArea();

      final int width = (int) area.getWidth(), height = (int) area.getHeight();

      final String nameTag = getNameTag();

      final String htmlTag = "<img " + nameTag +
			      "src = \"" + src + "\"" +
			      " border=\"0\"" +
			      " width = \"" + width +
			      "\" height = \"" + height +
			      "\"></img>";

      return hrefTag( htmlTag );

  }

  protected String hrefTag( final String htmlTag ) {

     final String href = getHref();
     final String target = getTarget();

     return StringView.hrefTag( htmlTag, href, target );

  }

  public String tag( final int zindex) {

     final Rectangle2D area = getArea();

     final int x = (int) area.getX(), y = (int) area.getY();

     String tag = "<div style=\"position:absolute; left:" + x + "px; top:" + y + "px; z-index:" + zindex + "\">" + tag() + "</div>" + nl;

     tag = HtmlElement.comment( tag, this );

     return tag;

  }

  public File getFile() {

     return this.src;

  }

  public void setStyle(int style) {

      this.style = style;

  }

  public int getStyle() {

      return this.style;

  }

  public boolean isRectStyle() {

      if( this.style == RECT ) {
	 return true;
      }

      return false;

  }

  public Shape [] getCornerRects() {

     if( this instanceof HtmlDocument ) {

	HtmlDocument doc = (HtmlDocument) this;

	if( doc.isTopMostDocument() ) {

	// 최상위 도큐먼트이면 어레이 크기 제로인 사각형들을 반환한다.

	  return new Rectangle [] {};

	}

     }

     Rectangle2D area = this.getArea();

     int margin = 0;

     int x = (int) area.getX();
     int y = (int) area.getY();

     int w = (int)(area.getWidth());
     int h = (int)(area.getHeight());

     int rw = CORNER_RECT_WIDTH, rh = rw;
     int mw = (w - rw)/2, mh = (h - rh)/2;
     int ew = w - rw, eh = h - rh;

     final Shape [] rects = new Shape[ 8 ];

     rects[0] = new Rectangle(      x - margin,      y - margin, rw, rh );
     rects[1] = new Rectangle(           x+ mw,      y - margin, rw, rh);
     rects[2] = new Rectangle( x + ew + margin,      y - margin, rw, rh );

     rects[3] = new Rectangle(      x - margin,          y + mh, rw, rh );
     rects[4] = new Rectangle( x + ew + margin,          y + mh, rw, rh);

     rects[5] = new Rectangle(      x - margin, y + eh + margin, rw, rh);
     rects[6] = new Rectangle(          x + mw, y + eh + margin, rw, rh );
     rects[7] = new Rectangle( x + ew + margin, y + eh + margin, rw, rh );

     return rects;

  }

  // 워드 박스 추가 모드 때의 절대 바운더리를 반환한다.

  final public Rectangle2D getAbsoluteBoundsOfWordBoxToAdd() {

    final HtmlDocument target = ( this instanceof HtmlDocument ) ?
				   (HtmlDocument) this : (HtmlDocument) parentDoc;

    final Rectangle2D targetArea = target.getArea();

    final Point2D targetAbsLoc = target.getAbsoluteLocation();

    final Insets targetScanMargin = target.getScanMargin();

    final double x = targetAbsLoc.getX() + targetScanMargin.left;
    final double y = targetAbsLoc.getY() + targetScanMargin.top;
    final double w = targetArea.getWidth() - targetScanMargin.left - targetScanMargin.right;
    final double h = ( target.isTopMostDocument() ) ?
		      Double.MAX_VALUE/4 :
		      targetArea.getHeight() - targetScanMargin.top - targetScanMargin.bottom;

    return new Rectangle2D.Double( x, y, w, h );

  }

  // 절대좌표를 기준으로 하는 바운더리를 구한다.

  final public Rectangle2D getAbsoluteBounds() {

    // 워드 박스 추가 모드 때의 절대 바운더리 영역은 따로 구해준다.
    if( isAddWordBoxMode() ) {

	return getAbsoluteBoundsOfWordBoxToAdd();

    }

    final HtmlDocument parentDoc = getParentDocument();

//    Utility.debug( this, "PAR DOC = " + parentDoc );

    if( parentDoc != null && parentDoc.isTopMostDocument() ) {

      // 최상위 도큐먼트이면 영역의 Y 값은 무한대가 되도록 한다.

      final Rectangle2D parDocArea = parentDoc.getArea();

      final Insets parDocScanMargin = new Insets( 15, 15, 15, 15 );

      final Point2D parDocAbsLoc = parentDoc.getAbsoluteLocation();

      final double infiniteHeight = Double.MAX_VALUE/4;
      // 라운딩 오프 현상으로 인한 버그를 사전에 차단하기 위해서
      // 실제 더블 값의 무한대값의 사분의 일만 잡는다.
      // 이렇게 보정해도 실제적인 무한대를 적용하는 데, 아무런 문제가 없다.
      // 사분의 일 값만 해도 매우 큰 값이기 때문이다.

      return new Rectangle2D.Double(

		    parDocAbsLoc.getX() + parDocScanMargin.left,
		    parDocAbsLoc.getY() + parDocScanMargin.top,
		    parDocArea.getWidth() - parDocScanMargin.left - parDocScanMargin.right,
		    infiniteHeight

		  );

    } else if( parentDoc != null ) {

      final Insets parDocScanMargin = parentDoc.getScanMargin();

      final Rectangle2D parDocArea = parentDoc.getArea();

      final Point2D parDocAbsLoc = parentDoc.getAbsoluteLocation();

      return new Rectangle2D.Double(

		    parDocAbsLoc.getX() + parDocScanMargin.left,
		    parDocAbsLoc.getY() + parDocScanMargin.top,
		    parDocArea.getWidth() - parDocScanMargin.left - parDocScanMargin.right,
		    parDocArea.getHeight() - parDocScanMargin.top - parDocScanMargin.bottom

		  );

    }

    return null;

  }

  public Rectangle2D getArea() {

      return new Rectangle2D.Double( x, y, w, h );

  }

  public Rectangle2D getAbsoluteArea() {

      Rectangle2D area = getArea();

      Point2D absLoc = getAbsoluteLocation();

      return new Rectangle2D.Double(
				     absLoc.getX(),
				     absLoc.getY(),
				     area.getWidth(),
				     area.getHeight()
				   );

  }

  public Point2D getLocation() {

      return new Point2D.Double( x, y );

  }

  public Point2D getAbsoluteParentLocation() {

      HtmlDocument parDoc = this.getParentDocument();

      return ( ( parDoc == null ) ? getAbsoluteLocation() : parDoc.getAbsoluteLocation() );

  }

  // x, y 는 절 대 좌표 이므로, 패런트의 절대 좌표로 부터 상대 좌표를
  // 보정하여 포함 여부를 판별한다.

  public boolean contains(double x, double y) {

      Point2D absParLoc = getAbsoluteParentLocation();

      return getArea().contains( x - absParLoc.getX(), y - absParLoc.getY() );

  }

  public boolean isSelected() {

    return HtmlDocument.isSelected( this );

  }

  private boolean isInserted() {

      return parentDoc.contains( this );

  }

  public Vector createViews(final Point scanPoint, final Insets margin) {

      final Vector views = new Vector();

      ImageView imageView;

      if( this instanceof ShapeElement ) {

	imageView = new ShapeView( (ShapeElement) this );

      } else {

	imageView = new ImageView( this );

      }

      views.addElement( imageView );

      return views;

  }

  public Image getImage(final URL url) {

    try {

	Object content = url.getContent();

	if( ("" + content).startsWith( "sun.net.www.http.KeepAliveStream" ) ) {

	    return Utility.getImageNotFound();

	}

	Utility.debug(this, " URL CONTENT = " + content );

	return Utility.getUrlImage( url );

    } catch (Exception e ) {

	Utility.debug( e );

	return null;

    }

  }

  public Image getImage() {

     if( this.linkUrl != null && this.img == null ) {

	 Image urlImage = getImage( this.linkUrl );

	 if( urlImage == null ) {

	    return Utility.getImageNotFound();

	 }

	 this.img = urlImage;

     }

     return this.img;

  }

  public void setLocationOnly(double x, double y) {

     this.x = x;
     this.y = y;

  }

  public void setLocation(double x, double y) {

     if( this.x == x && this.y == y ) {

	return; // this means do nothing

     }

     this.x = x;
     this.y = y;

     if( ( parentDoc != null ) && ( getStyle() == RECT ) ) {

	// 사이즈가 변경되면 워드 박스나 이미지 객체에서는 뷰의 재생성을 아버지 도큐먼트에게 유구한다.

	parentDoc.requestNewHtmlDocView();

     }

  }

  public void setSize(double w, double h) {

//     Utility.debug(this, "SET SIZE : W = " + w + ", H = " + h );

     if( this instanceof HtmlDocument ) {

	final HtmlDocument doc = (HtmlDocument) this;

	final int minWidth = doc.getMinWidth();

	final int minHeight = doc.getMinHeight();

	w = ( w < minWidth ) ? minWidth : w;

	h = ( h < minHeight) ? minHeight : h;

     } else {

	w = (w < AppRegistry.MINIMUM_IMAGE_WIDTH ) ? AppRegistry.MINIMUM_IMAGE_WIDTH : w;

	h = (h < AppRegistry.MINIMUM_IMAGE_HEIGHT ) ? AppRegistry.MINIMUM_IMAGE_HEIGHT : h;

     }

     if( this.w == w && this.h == h ) {

	return;

     }

     this.w = w;
     this.h = h;

     if( this instanceof HtmlDocument ) { // 도큐먼트 이미지 엘리먼트이면 뷰의 재생성을 요구한다.

	 HtmlDocument doc = (HtmlDocument) this;

	 doc.requestNewHtmlDocView();

     } else if( this instanceof ChartElement ) {

	 ChartElement chart = (ChartElement) this ;

	 ChartData cd = chart.getChartData();

	 cd.setSize( (int) w, (int) h );

	 parentDoc.requestNewHtmlDocView();

     } else if ( ( parentDoc != null ) && ( getStyle() == RECT ) ) {

	 // 사이즈가 변경되면 워드 박스나 이미지 객체에서는 뷰의 재생성을 아버지 도큐먼트에게 유구한다.

	 parentDoc.requestNewHtmlDocView();

     }

  }

  public void setArea(Rectangle2D area) {

//     Utility.debug(this, "SET AREA = " + area );

     this.setArea( area.getX(), area.getY(), area.getWidth(), area.getHeight() );

  }

  public void setArea(double x, double y, double w, double h) {

     setLocation( x, y );

     setSize( w, h );

  }

  public String commentText() {

     int style = getStyle();

     if( this instanceof TableDocument ) {

       return HtmlLayer.TABLE;

     } else if( this instanceof HtmlDocument ) {

       return HtmlLayer.WORD_BOX;

     } if( style == this.RECT ) {

       return HtmlLayer.RECT_IMAGE;

     } else if( style == this.FRONT_TEXT ) {

       return HtmlLayer.FRONT_TEXT;

     } else {

       return HtmlLayer.BACK_TEXT;

     }

  }

  public boolean processImageKeyEvent(KeyEvent e) {

    if( ! isSelected() ) {

      return false;

    }

    int key = e.getKeyCode();

    boolean doDelete = false;

//    Utility.debug( this, "Processing key event ...MOUSE TOPOLOGY = " + MOUSE_TOPOLOGY );

    if( ( key == KeyEvent.VK_DELETE ) && this instanceof HtmlDocument ) {

       if( mode.isMode( Mode.RESHAPE ) ) {

	  doDelete = true;

       }

    } else if( key == KeyEvent.VK_DELETE ) {

      doDelete = true;

    }

    if( doDelete ) {

      parentDoc.deleteImageElement( this, null );

      parentDoc.requestNewHtmlDocView();

    }

    return doDelete;

  }

  protected static boolean isAddWordBoxMode() {

    return mode.isMode( Mode.ADD_WORD_BOX );

  }

  protected static boolean isReShapeMode() {

    return ( mode.isMode( Mode.RESHAPE ) || mode.isMode( Mode.ADD_TABLE ) || isAddWordBoxMode() );

  }

  public boolean processImageMouseEvent( final int top, MouseEvent e ) {

//    Utility.debug( this, "PROCESS IMAGE MOUSE EVENT TOP = " + top );

    final int x = e.getX();
    final int y = e.getY();

    final int id = e.getID();

    final HtmlEditorPane editor = (HtmlEditorPane) e.getSource();

    if( mode.isMode( Mode.ADD_SHAPE ) ) {

      processAddShapeMouseEvent( e );

    } else if( id == MouseEvent.MOUSE_PRESSED ) {

	 WEAK_SEL_IMG_ELEM = null ; // 마우스가 클릭되면 무조건 약한 선택된 이미지 엘리먼트를 널로 설정한다.
				    // 약한 선택된 이미지 엘리먼트는 도큐먼트의 붙여넣기시에 선택된 것처럼 보이도록 하기 위해서 필요하다.

	// 더블 클릭 했을 때 이벤트 처리

	if( e.getClickCount() == 2 ) {

	    // 타겟의 이미지 스타일을 에디팅한다.

	    if ( this instanceof ChartElement) {

		  // 차트를 더블 클릭하면......당연히 차트를 편집한다.

		  editor.editChart( (ChartElement) this );

		  return true;   // 이벤츠 처리가 정상적으로 종료된다.

	    } else if( this.isLinkImage() ) {

		  editor.editLinkImage( this );

		  return true;

	    }

	    return editor.editImageStyle( );

	    // 이미지 스타일에서 이벤트를 처리 하지 않으면 그냥...
	    // 하위에서 이벤트를 처리해 줘야 한다.

	    // 마우스 이벤트 처리가 완료 되어 도큐먼트 엘리먼트에서는
	    // 더 이상 마우스 처리를 햐여 주지 않는다.
	    // 참 값을 넘기면 도큐먼트 엘리먼트에서는 마우스 이벤트 처리를
	    // 하지 않는다는 신호로 받아 들인다.
	    // 그러므로, 참을 넘길지 거짓을 넘길 지는 주의하여 판단하여야 한다.

	}

	// 끝. 더블 클릭했을 때 이벤트 처리

	final boolean isRightButton = Utility.isRightMouseButton( e );

	if( ! isRightButton ) {

	    // 왼쪽 마우스가 눌러지면 에디터 팝업 메뉴를 감춘다.
	    editor.hideEditPopupMenu();

	}

	if( isRightButton ) { // 오른쪽 마우스가 눌러졋을 때.....

	    editor.showEditPopupMenu( e );

	    return true; // 마우스 이벤트 처리를 종료한다. 정상적으로...
			 // 참 값을 넘기면 하위 객체에서는 더 이상 마우스 이벤트를 처리 하지 않는다.

	}

	return false;

    } else if( isReShapeMode() && id == MouseEvent.MOUSE_DRAGGED ) {

	paintResizeMode( (Graphics2D) editor.getGraphics(), top,
			 LAST_MOUSE_EVENT, e );

	return false;

    } else if( isReShapeMode() && id == MouseEvent.MOUSE_RELEASED ) {

	Shape xorShape = this.XOR_AREA_SHAPE;

	if( xorShape != null ) {

	  final Graphics2D g2 = (Graphics2D) editor.getGraphics();

	  final Stroke ps = g2.getStroke(); // previous stroke

	  g2.setXORMode( XOR_COLOR );

	  g2.setStroke( getDraggedStroke() );

	  g2.draw( xorShape );

	  g2.setStroke( ps );

	  g2.setPaintMode();

	  this.XOR_AREA_SHAPE = null;

	}

	if ( hasReSetArea( top, LAST_MOUSE_EVENT, e ) ) {

	  parentDoc.requestNewHtmlDocView();

	}

	return false;

    }

    return false;

  }

  public Rectangle2D getResizeValue(double tx, double ty, final int topology) {

	    Rectangle2D area = getArea();

	    double w = area.getWidth(), h = area.getHeight();

	    switch( topology ) {

		case 0:  // north west topology
			w = w - tx;
			h = h - ty;
			break;

		case 1:  // north topology
			h = h - ty;
			tx = 0;
			break;

		case 2:  // north east topology
			w = w + tx;
			h = h - ty;
			tx = 0;
			break;

		case 3:  // west topology
			w = w - tx;
			ty = 0;
			break;

		case 4:  // ease topology
			w = w + tx;
			tx = 0;
			ty = 0;
			break;

		case 5: // south west topology
			w = w - tx;
			h = h + ty;
			ty = 0;
			break;

		case 6:  // south topology
			h = h + ty;
			tx = 0;
			ty = 0;
			break;

		case 7:
			w = w + tx;
			h = h + ty;
			tx = 0;
			ty = 0;
			break;

		case 8: // if on the boundary
			// Do nothing!
			break;

		case 9:
			// Do nothing!
			break;

	   }

	   return new Rectangle2D.Double(tx, ty, w, h);

  }

  final public static Stroke getDraggedStroke() {

    return DRAGGED_STROKE;

  }

  final public Shape getDraggedShapeOfTableCell(final int top, HtmlDocument cellDoc, final MouseEvent to ) {

    final HtmlEditorPane editor = cellDoc.getDocumentEditor();

    final Rectangle visRect = editor.getVisibleRect();

    final double vx = visRect.x;

    final double vy = visRect.y;

    final double vw = visRect.width;

    final double vh = visRect.height;

    final int x = to.getX();

    final int y = to.getY();

    if( top == BOTTOM_BOUNDARY ) {

      return new Line2D.Double( vx, y, vx + vw, y );

    } else if( top == LEFT_BOUNDARY || top == RIGHT_BOUNDARY ) {

      return new Line2D.Double( x, vy, x, vy + vh );

    } else {

      Utility.debug(this, "UnExpected Table Cell Dragging Topology!!" );

    }

    return null;

  }

  // 절대 좌표 기준으로 드래깅 도형을 반환한다.

  public Shape getDraggedShape( final int top, final MouseEvent from, final MouseEvent to ) {

    if( this instanceof HtmlDocument ) {

      HtmlDocument doc = (HtmlDocument) this;

      if( doc.isTableCell() ) {

	return getDraggedShapeOfTableCell( top, doc, to );

      }

    }

    Shape draggedShape;

    if( isAddWordBoxMode() || mode.isMode( Mode.ADD_TABLE ) ) {

      draggedShape = getMouseRectangle( from, to );

//      Utility.debug( this, "MOUSE SHAPE = " + draggedShape );

    } else {

      final Point p = getMouseVector( from, to );

      final Rectangle2D rv = getResizeValue( p.x, p.y, top );  // resize value

      final Shape shape = getArea();

      // 절대 좌표를 기준으로 변환 할 수 있는 AffineTransform 을 구한다.

      final AffineTransform at = getResizeAffineTransform( rv.getX(), rv.getY(), rv.getWidth(), rv.getHeight() ); // resize affine transform

      draggedShape = at.createTransformedShape( shape );

    }

    final Rectangle2D absBounds = getAbsoluteBounds();

    if( absBounds == null ) {

    } else if( top == INSIDE_AREA || top == ON_THE_BOUNDARY ) { // move mode 일 경우에....

      final Area intDraggedShapeArea = new Area( absBounds ); // intersected dragged shape area

      intDraggedShapeArea.intersect( new Area( draggedShape) );

      Rectangle2D draggedShapeRect = draggedShape.getBounds2D(); // dragged shape rect

//      Rectangle2D draggedShapeRect = new Rectangle2D.Double( 0, 0, 1, 1 );
//
//      Rectangle2D.intersect( draggedShape.getBounds2D(), absBounds, draggedShapeRect );

      Utility.debug( this, "DRAGGED SHAPE = " + draggedShapeRect );

      double x = draggedShapeRect.getX();
      double y = draggedShapeRect.getY();
      double w = draggedShapeRect.getWidth();
      double h = draggedShapeRect.getHeight();

      double overWidth = x + w - ( absBounds.getX() + absBounds.getWidth() );

      if( x < absBounds.getX() ) {

	x = absBounds.getX();

      } else if( overWidth > 0 ) {

	x -= overWidth;

      }

      double overHeight = y + h - ( absBounds.getY() + absBounds.getHeight() );

      if( y < absBounds.getY() ) {

	y = absBounds.getY();

      } else if( overHeight > 0 ) {

	y -= overHeight;

      }

      draggedShape = new Rectangle2D.Double( x, y, w, h );

//      Utility.debug(this, "CHECKING OVER WIDTH AND HEIGHT......" );

    } else { // 리사이즈 모드 일 경우에.....

      final Area absBoundsArea = new Area( absBounds );

      absBoundsArea.intersect( new Area( draggedShape) );

      draggedShape = absBoundsArea;

    }

    final HtmlDocument doc = this instanceof HtmlDocument ?
				(HtmlDocument) this :
				getParentDocument();

    if( isAddWordBoxMode() || mode.isMode(Mode.ADD_TABLE) || doc.isTopMostDocument() ) {

      // Do nothing!

    } else if( top == INSIDE_AREA || top == ON_THE_BOUNDARY ) {

      // Do nothing! (지금은.....)

    }

    if( mode.isMode( Mode.ADD_TABLE ) ) {

      return getShapeOfTableToInsert( draggedShape );

    }

    return draggedShape;

  }

  final private void paintResizeMode(final Graphics2D g2, final int top, final MouseEvent from, final MouseEvent to ) {

//      Utility.debug( this, "RESIZING Painting ..." );

      if( mode.isMode( Mode.EDIT ) ) { // 에디팅 시에는 리쉐이핑 윤곽을 그려 주지 않는다.

	 return;

      } else if( top < 0 || top > INSIDE_AREA ) {

	 return;

      }

      final Color pc = g2.getColor(); // previous color

      final Stroke ps = g2.getStroke(); // previos stroke

      g2.setXORMode( XOR_COLOR );

      g2.setColor( Color.black );

      g2.setStroke( getDraggedStroke() );

      Shape pxs = this.XOR_AREA_SHAPE;  // previous xor Shape

      if( pxs != null ) {

	  g2.draw( pxs );

      }

      // 드래깅 쉐잎은 절대 좌표 기준의 도형이다.

      final Shape draggedShape = getDraggedShape( top, from, to );

      // 끝. 절대 좌표 기준의 드래깅 도형 구하기......

      g2.draw( draggedShape );

      this.XOR_AREA_SHAPE = draggedShape; // set as current xor shape

      g2.setPaintMode(); // set paint mode

      g2.setStroke( ps ); // unset stroke

      g2.setColor( pc ); // unset color

   }

   // 절대 좌표를 기준으로 변환할 수 있는 AffineTransform을 넘긴다.

   public AffineTransform getResizeAffineTransform(final double dx, final double dy, final double w, final double h) {

      final Point2D absParLoc = getAbsoluteParentLocation();

      final Point2D loc = getLocation();

      final Point2D newLoc = new Point2D.Double( loc.getX() + dx, loc.getY() + dy );

      final AffineTransform at = AffineTransform.getTranslateInstance( absParLoc.getX() + newLoc.getX(), absParLoc.getY() + newLoc.getY() );

      final Rectangle2D area = getArea();

      at.scale( w/area.getWidth(), h/area.getHeight() );

      at.translate( - loc.getX(), - loc.getY() );

      return at;

  }

  final private Rectangle getMouseRectangle(final MouseEvent m0, final MouseEvent m1 ) {

     if( m0 == null || m1 == null ) {

	return null;

     }

//     Utility.debug( this, "FROM MOUSE X = " + m0.getX() + ", Y = " + m0.getY() );
//     Utility.debug( this, "TO   MOUSE X = " + m1.getX() + ", Y = " + m1.getY() );

     final int x0 = m0.getX(), y0 = m0.getY();
     final int x1 = m1.getX(), y1 = m1.getY();

     return new Rectangle( (x0 < x1) ? x0 : x1, (y0 < y1) ? y0 : y1, Math.abs( x0 - x1 ), Math.abs( y0 - y1 ) );

//     Rectangle rect = new Rectangle( (x0 < x1) ? x0 : x1, (y0 < y1) ? y0 : y1, Math.abs( x0 - x1 ), Math.abs( y0 - y1 ) );
//
//     Utility.debug( this, "MOUSE RECT = " + rect );

//     return rect;

  }

  protected boolean isTableCell() {

    return ( getParentDocument() instanceof TableDocument ) ;

  }

  final public int [] getInitialRowCol(Rectangle2D rect) {

     final double w = rect.getWidth();
     final double h = rect.getHeight();

     final int rowNum = (int)( h / AppRegistry.MINIMUN_DOCUMENT_HEIGHT  + 1 );

     int colNum = (int)( w / AppRegistry.MINIMUN_DOCUMENT_WIDTH );

     colNum = colNum < 1 ? 1 : colNum;

     return new int [] { rowNum, colNum };

  }

  final private Shape getShapeOfTableToInsert( final Shape draggedShape ) {

     if( draggedShape == null ) {

	 return null;

     }

     final Rectangle mouseRect = draggedShape.getBounds();

     final int w = mouseRect.width;
     final int h = mouseRect.height;

     final int rowCol [] = getInitialRowCol( mouseRect );

     final int rowNum = rowCol[ 0 ];
     final int colNum = rowCol[ 1 ];

     final int mcw = w/colNum; // minimum cell width
     final int mch = h/rowNum;   // minimun cell height

     final int tw = mcw*colNum; // talbe width
     final int th = mch*rowNum; // talbe height

     GeneralPath gp = new GeneralPath();

     final int x = mouseRect.x;
     final int y = mouseRect.y;

     gp.moveTo( x, y );

     for(int i = 0; i < rowNum + 1 ; i ++ ) {

	 gp.moveTo( x, y + i*mch );
	 gp.lineTo( x + tw, y + i*mch );

     }

     for(int i = 0; i < colNum + 1 ; i ++ ) {

	 gp.moveTo( x + i*mcw , y);
	 gp.lineTo( x + i*mcw, y + th );

     }

     return gp;

  }

  final private Point getMouseVector(final MouseEvent from, final MouseEvent to ) {

     return new Point( to.getX() - from.getX(), to.getY() - from.getY() );

  }

  protected boolean hasReSetArea( final int top, final MouseEvent from, final MouseEvent to) {

    if( parentDoc == null ) {

      return false ;

    }

    if( isAddWordBoxMode() ) {

      return false;

    }

    if( this instanceof HtmlDocument ) {

      if( top == OUT_OF_AREA || top == INSIDE_AREA ) {

	return false;

      }

      HtmlDocument doc = (HtmlDocument) this;

      if( doc.isTableCell() ) { // 테이블 셀을 리쉐이핑 한 경우.....

	TableDocument tableDoc = (TableDocument) doc.getParentDocument();

	tableDoc.moveCell( top, doc, to );

	return true;

      }

    } else if( top == OUT_OF_AREA  ) {

      return false;

    }

    // 절대 좌표로 넘어온 드래깅 쉐잎을 상대 좌표로 변환해서...
    // 영역 값을 설정해 준다.

    final Point2D absParLoc = getAbsoluteParentLocation();

    final AffineTransform at = AffineTransform.getTranslateInstance( - absParLoc.getX(), - absParLoc.getY() );

    final Shape draggedShape = at.createTransformedShape( getDraggedShape( top, from, to ) );

    setArea( draggedShape.getBounds2D() );

    return true;

  }

  protected Shape getDocumentInsideArea() {

     Rectangle2D docArea = getArea();

     HtmlDocument doc = (HtmlDocument) this;

     Insets scanMargin = null;

     if( doc.isTopMostDocument() ) {

	 scanMargin = doc.getScanMargin();

     } else {

	 int cornerRectWidth = ImageElement.CORNER_RECT_WIDTH;

	 scanMargin = new Insets( cornerRectWidth, cornerRectWidth, cornerRectWidth, cornerRectWidth );

     }

     return  new Rectangle2D.Double(
		    docArea.getX() + scanMargin.left,
		    docArea.getY() + scanMargin.top,
		    docArea.getWidth() - scanMargin.left - scanMargin.right,
		    docArea.getHeight() - scanMargin.top - scanMargin.bottom
	  );

  }

  public Shape getDocumentBoundary() {

      Rectangle2D docArea = getArea();

      Area boundaryArea = new Area( docArea );

      boundaryArea.subtract( new Area( getDocumentInsideArea() ) );

      return boundaryArea;

  }

  public Point2D getAbsoluteLocation() {

      if( parentDoc == null ) {

	  return getLocation();

      }

      Point2D pal = parentDoc.getAbsoluteLocation(); // parent absolute location

      Point2D loc = getLocation();

      return new Point2D.Double( pal.getX() + loc.getX(), pal.getY() + loc.getY() );

  }

  public String toString() {

      return getClass().getName() + " ID = " + id;

  }

  public void processAddShapeMouseEvent( final MouseEvent e ) {

      final HtmlEditorPane editor = (HtmlEditorPane) e.getSource();

      final int id = e.getID();

      if( id == e.MOUSE_RELEASED ) {

	final ShapeElement shapeElement = ShapeElement.getMouseShapeElement();

	final int shapeType = shapeElement.getShapeType();

	if( shapeType == ShapeElement.FREE_LINE ) {
	  // 자유 곡선은 마우스가 릴리즈 될 때 쉐잎 엘리먼트를 에디터에 추가한다.
	  // 에딧 모드로 변환한다.

	  shapeElement.addMousePoint( e );

	  ShapeElement.addMouseShapeElementToEditor( editor );

	  ShapeElement.removeAllMousePoints();

	  mode.setMode( Mode.EDIT );

	}

      } else if( id == e.MOUSE_PRESSED ) {

	final ShapeElement shapeElement = ShapeElement.getMouseShapeElement();

	final int shapeType = shapeElement.getShapeType();

	final Point lastPoint = shapeElement.getLastPoint();

	if( e.getClickCount() == 2 ) { // 마우스를 더블클릭하면 쉐잎 엘리먼트를
				       // 에디터에 추가하고, 모드를 에디터 모드로 전환한다.

	  if( shapeElement.isTwoPointsComposedShapeElement() ) {

	    // Do nothing!

	  } else {

	    lastPoint.setLocation( e.getX(), e.getY() );

	    shapeElement.reSetShape();

	  }

	  ShapeElement.addMouseShapeElementToEditor( editor );

	  ShapeElement.removeAllMousePoints();

	  mode.setMode( Mode.EDIT );

	} else if( lastPoint == null ) {

	  // 쉐잎 엘리먼트에 포인트가 하나도 없으면 두 개를 삽입하고,
	  // 마우스가 움질 일 때 마직 말 포인트의 좌표값을 변경한다.

	  // 마우스 포인트 두 개 추가하기

	  shapeElement.addMousePoint( e );

	  shapeElement.addMousePoint( e );

	  shapeElement.reSetShape();

	  // 끝. 마우스 포인트 두 개 추가하기

	} else if( lastPoint != null && shapeElement.isTwoPointsComposedShapeElement() ) {

	  // 두 점 구성 쉐잎 엘리 먼트의 경우에는
	  // 마우스가 클릭되면 마지막 점의 좌표값을
	  // 현재 마우스 좌표값에 맞추어 변경한다.

	  lastPoint.setLocation( e.getX(), e.getY() );

	  shapeElement.reSetShape();

	  ShapeElement.addMouseShapeElementToEditor( editor );

	  ShapeElement.removeAllMousePoints();

	  mode.setMode( Mode.EDIT );

	} else if( lastPoint != null ) {

	  if( shapeElement.getPointList().size() < 3 ) {

	    lastPoint.setLocation( e.getX(), e.getY() );

	  }

	  if( shapeType == ShapeElement.FREE_LINE ) {

	    // 자유 곡선은 마우스가 무브 될 때에만 포인트를 추가한다.
	    // 클릭할 경우에 포인트를 추가하지 않아도 된다.
	    // Do nothing!

	  } else {

	    shapeElement.addMousePoint( e );

	    shapeElement.reSetShape();

	  }

	  final LinkedList pointList = shapeElement.getPointList();

	  if( pointList.size() > 1 ) { // 포인트 갯수가 두 개 이상 이고,
				       // 마우스를 클릭했을 때,
				       // 끝 점과 첫 점이 거의 같다고 판달 될 경우에는
				       // 쉐잎 엘리먼트를 추가하고,
				       // 에딧 모드로 전환한다.

	    final Point first = (Point) pointList.getFirst();
	    final Point last  = (Point) pointList.getLast();

	    if( isVeryNear( first, last ) ) {

	      last.setLocation( first );

	      shapeElement.reSetShape();

	      ShapeElement.addMouseShapeElementToEditor( editor );

	      ShapeElement.removeAllMousePoints();

	      mode.setMode( Mode.EDIT );

	    }

	  }

	}

      } else if( id == e.MOUSE_MOVED || id == e.MOUSE_DRAGGED ) {

	final Graphics2D g2 = (Graphics2D) editor.getGraphics();

	final ShapeElement shapeElement = ShapeElement.getMouseShapeElement();

	final Point lastPoint = shapeElement.getLastPoint();

	final int shapeType = shapeElement.getShapeType();

//	Utility.debug( this, "POINT LIST SIZE = " + shapeElement.getPointList().size() );
//	Utility.debug(this, "LAST POINT = " + lastPoint );

//	Utility.debug( this, "SHAPE TYPE = " + shapeType );

	if( lastPoint == null ) {

	  return;

	} else if( shapeType == ShapeElement.FREE_LINE && id == e.MOUSE_DRAGGED ) {

	  shapeElement.addMousePoint( e );

	  final LinkedList pointList = shapeElement.getPointList();

	  final Rectangle2D shapeArea = shapeElement.getArea();

	  if( shapeArea != null &&
	      shapeArea.getWidth() > 10 &&
	      shapeArea.getHeight() > 10 &&
	      pointList.size() > 4 &&
	      isVeryNear( (Point) pointList.getFirst(), (Point) pointList.getLast() )
	  ) {

	     // 자유 곡선인 경우에는 마우스 무브시에 첫 점과 끝 점이 같고,
	     // 포인트 갯수가 4 개 이상일 경우에,
	     // 쉐잎 엘리먼트를 에디터에 추가한다.

	     // 자유 곡선을 닫힌 곡선으로 만들기 위해서
	     // 마지막 포인트를 첫 점의 값으로 넣어준다.

	     pointList.add( pointList.getFirst() );

	     // 끝. 자유 곡선 첫 점과 마지막 점 같게 하기.

	     shapeElement.reSetShape();

	     ShapeElement.addMouseShapeElementToEditor( editor );

	     ShapeElement.removeAllMousePoints();

	     mode.setMode( Mode.EDIT );

	     editor.repaint();

	     return;

	  }

	  shapeElement.reSetShape();

	  shapeElement.paint( g2 );

	  return;

	}

	g2.setXORMode( XOR_COLOR );

	// 이전 쉐잎 지우기

	shapeElement.paint( g2 );

	// 끝. 이전 쉐잎 지우기

	// 마우스 위치에 맞추어서 마지막 포인트 좌표 변경

	lastPoint.setLocation( e.getX(), e.getY() );

	shapeElement.reSetShape();

	// 끝. 마지막 포인트 좌표 변경

	// 새로운 쉐잎 그리기

	shapeElement.paint( g2 );

	// 끝. 새로운 쉐잎 그리기

	g2.setPaintMode();

      }

  }

  public boolean isVeryNear( final Point p, final Point q ) {

      return Math.abs( p.x - q.x ) < 4 && Math.abs( p.y - q.y ) < 4 ;

  }

}