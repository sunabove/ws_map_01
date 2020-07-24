
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

  protected static ImageElement SEL_IMG_ELEM;  // ���õ� �̹��� ������Ʈ�� �ϳ��̴�. ��ü����
  protected static ImageElement WEAK_SEL_IMG_ELEM; // ���õ� �̹��� ������Ʈ.. ���ϰ�...��ť��Ʈ �ٿ��ֱ� �Ŀ�....
						    // ���õ� �� ó�� �����ֱ� ���ؼ�..���� ���õ� ���� �ƴ�����....

  protected static MouseEvent LAST_MOUSE_EVENT;
  protected static Shape XOR_AREA_SHAPE;

  // ���콺 �������� ���� �����Ѵ�.

  protected static final int OUT_OF_AREA = -1, ON_THE_BOUNDARY = 8,
			     BOTTOM_BOUNDARY = 9,
			     LEFT_BOUNDARY = 10, RIGHT_BOUNDARY = 11,
			     INSIDE_AREA = 12;

  protected static final Color XOR_COLOR = Color.yellow;

  // �̹��� ������Ʈ�� ���̾ƿ�(��Ÿ��)�� ���� �Ѵ�.

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

	// �ֻ��� ��ť��Ʈ�̸� ��� ũ�� ������ �簢������ ��ȯ�Ѵ�.

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

  // ���� �ڽ� �߰� ��� ���� ���� �ٿ������ ��ȯ�Ѵ�.

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

  // ������ǥ�� �������� �ϴ� �ٿ������ ���Ѵ�.

  final public Rectangle2D getAbsoluteBounds() {

    // ���� �ڽ� �߰� ��� ���� ���� �ٿ���� ������ ���� �����ش�.
    if( isAddWordBoxMode() ) {

	return getAbsoluteBoundsOfWordBoxToAdd();

    }

    final HtmlDocument parentDoc = getParentDocument();

//    Utility.debug( this, "PAR DOC = " + parentDoc );

    if( parentDoc != null && parentDoc.isTopMostDocument() ) {

      // �ֻ��� ��ť��Ʈ�̸� ������ Y ���� ���Ѵ밡 �ǵ��� �Ѵ�.

      final Rectangle2D parDocArea = parentDoc.getArea();

      final Insets parDocScanMargin = new Insets( 15, 15, 15, 15 );

      final Point2D parDocAbsLoc = parentDoc.getAbsoluteLocation();

      final double infiniteHeight = Double.MAX_VALUE/4;
      // ���� ���� �������� ���� ���׸� ������ �����ϱ� ���ؼ�
      // ���� ���� ���� ���Ѵ밪�� ����� �ϸ� ��´�.
      // �̷��� �����ص� �������� ���Ѵ븦 �����ϴ� ��, �ƹ��� ������ ����.
      // ����� �� ���� �ص� �ſ� ū ���̱� �����̴�.

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

  // x, y �� �� �� ��ǥ �̹Ƿ�, �з�Ʈ�� ���� ��ǥ�� ���� ��� ��ǥ��
  // �����Ͽ� ���� ���θ� �Ǻ��Ѵ�.

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

	// ����� ����Ǹ� ���� �ڽ��� �̹��� ��ü������ ���� ������� �ƹ��� ��ť��Ʈ���� �����Ѵ�.

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

     if( this instanceof HtmlDocument ) { // ��ť��Ʈ �̹��� ������Ʈ�̸� ���� ������� �䱸�Ѵ�.

	 HtmlDocument doc = (HtmlDocument) this;

	 doc.requestNewHtmlDocView();

     } else if( this instanceof ChartElement ) {

	 ChartElement chart = (ChartElement) this ;

	 ChartData cd = chart.getChartData();

	 cd.setSize( (int) w, (int) h );

	 parentDoc.requestNewHtmlDocView();

     } else if ( ( parentDoc != null ) && ( getStyle() == RECT ) ) {

	 // ����� ����Ǹ� ���� �ڽ��� �̹��� ��ü������ ���� ������� �ƹ��� ��ť��Ʈ���� �����Ѵ�.

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

	 WEAK_SEL_IMG_ELEM = null ; // ���콺�� Ŭ���Ǹ� ������ ���� ���õ� �̹��� ������Ʈ�� �η� �����Ѵ�.
				    // ���� ���õ� �̹��� ������Ʈ�� ��ť��Ʈ�� �ٿ��ֱ�ÿ� ���õ� ��ó�� ���̵��� �ϱ� ���ؼ� �ʿ��ϴ�.

	// ���� Ŭ�� ���� �� �̺�Ʈ ó��

	if( e.getClickCount() == 2 ) {

	    // Ÿ���� �̹��� ��Ÿ���� �������Ѵ�.

	    if ( this instanceof ChartElement) {

		  // ��Ʈ�� ���� Ŭ���ϸ�......�翬�� ��Ʈ�� �����Ѵ�.

		  editor.editChart( (ChartElement) this );

		  return true;   // �̺��� ó���� ���������� ����ȴ�.

	    } else if( this.isLinkImage() ) {

		  editor.editLinkImage( this );

		  return true;

	    }

	    return editor.editImageStyle( );

	    // �̹��� ��Ÿ�Ͽ��� �̺�Ʈ�� ó�� ���� ������ �׳�...
	    // �������� �̺�Ʈ�� ó���� ��� �Ѵ�.

	    // ���콺 �̺�Ʈ ó���� �Ϸ� �Ǿ� ��ť��Ʈ ������Ʈ������
	    // �� �̻� ���콺 ó���� �Ῡ ���� �ʴ´�.
	    // �� ���� �ѱ�� ��ť��Ʈ ������Ʈ������ ���콺 �̺�Ʈ ó����
	    // ���� �ʴ´ٴ� ��ȣ�� �޾� ���δ�.
	    // �׷��Ƿ�, ���� �ѱ��� ������ �ѱ� ���� �����Ͽ� �Ǵ��Ͽ��� �Ѵ�.

	}

	// ��. ���� Ŭ������ �� �̺�Ʈ ó��

	final boolean isRightButton = Utility.isRightMouseButton( e );

	if( ! isRightButton ) {

	    // ���� ���콺�� �������� ������ �˾� �޴��� �����.
	    editor.hideEditPopupMenu();

	}

	if( isRightButton ) { // ������ ���콺�� �������� ��.....

	    editor.showEditPopupMenu( e );

	    return true; // ���콺 �̺�Ʈ ó���� �����Ѵ�. ����������...
			 // �� ���� �ѱ�� ���� ��ü������ �� �̻� ���콺 �̺�Ʈ�� ó�� ���� �ʴ´�.

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

  // ���� ��ǥ �������� �巡�� ������ ��ȯ�Ѵ�.

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

      // ���� ��ǥ�� �������� ��ȯ �� �� �ִ� AffineTransform �� ���Ѵ�.

      final AffineTransform at = getResizeAffineTransform( rv.getX(), rv.getY(), rv.getWidth(), rv.getHeight() ); // resize affine transform

      draggedShape = at.createTransformedShape( shape );

    }

    final Rectangle2D absBounds = getAbsoluteBounds();

    if( absBounds == null ) {

    } else if( top == INSIDE_AREA || top == ON_THE_BOUNDARY ) { // move mode �� ��쿡....

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

    } else { // �������� ��� �� ��쿡.....

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

      // Do nothing! (������.....)

    }

    if( mode.isMode( Mode.ADD_TABLE ) ) {

      return getShapeOfTableToInsert( draggedShape );

    }

    return draggedShape;

  }

  final private void paintResizeMode(final Graphics2D g2, final int top, final MouseEvent from, final MouseEvent to ) {

//      Utility.debug( this, "RESIZING Painting ..." );

      if( mode.isMode( Mode.EDIT ) ) { // ������ �ÿ��� �������� ������ �׷� ���� �ʴ´�.

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

      // �巡�� ������ ���� ��ǥ ������ �����̴�.

      final Shape draggedShape = getDraggedShape( top, from, to );

      // ��. ���� ��ǥ ������ �巡�� ���� ���ϱ�......

      g2.draw( draggedShape );

      this.XOR_AREA_SHAPE = draggedShape; // set as current xor shape

      g2.setPaintMode(); // set paint mode

      g2.setStroke( ps ); // unset stroke

      g2.setColor( pc ); // unset color

   }

   // ���� ��ǥ�� �������� ��ȯ�� �� �ִ� AffineTransform�� �ѱ��.

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

      if( doc.isTableCell() ) { // ���̺� ���� �������� �� ���.....

	TableDocument tableDoc = (TableDocument) doc.getParentDocument();

	tableDoc.moveCell( top, doc, to );

	return true;

      }

    } else if( top == OUT_OF_AREA  ) {

      return false;

    }

    // ���� ��ǥ�� �Ѿ�� �巡�� ������ ��� ��ǥ�� ��ȯ�ؼ�...
    // ���� ���� ������ �ش�.

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
	  // ���� ��� ���콺�� ������ �� �� ���� ������Ʈ�� �����Ϳ� �߰��Ѵ�.
	  // ���� ���� ��ȯ�Ѵ�.

	  shapeElement.addMousePoint( e );

	  ShapeElement.addMouseShapeElementToEditor( editor );

	  ShapeElement.removeAllMousePoints();

	  mode.setMode( Mode.EDIT );

	}

      } else if( id == e.MOUSE_PRESSED ) {

	final ShapeElement shapeElement = ShapeElement.getMouseShapeElement();

	final int shapeType = shapeElement.getShapeType();

	final Point lastPoint = shapeElement.getLastPoint();

	if( e.getClickCount() == 2 ) { // ���콺�� ����Ŭ���ϸ� ���� ������Ʈ��
				       // �����Ϳ� �߰��ϰ�, ��带 ������ ���� ��ȯ�Ѵ�.

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

	  // ���� ������Ʈ�� ����Ʈ�� �ϳ��� ������ �� ���� �����ϰ�,
	  // ���콺�� ���� �� �� ���� �� ����Ʈ�� ��ǥ���� �����Ѵ�.

	  // ���콺 ����Ʈ �� �� �߰��ϱ�

	  shapeElement.addMousePoint( e );

	  shapeElement.addMousePoint( e );

	  shapeElement.reSetShape();

	  // ��. ���콺 ����Ʈ �� �� �߰��ϱ�

	} else if( lastPoint != null && shapeElement.isTwoPointsComposedShapeElement() ) {

	  // �� �� ���� ���� ���� ��Ʈ�� ��쿡��
	  // ���콺�� Ŭ���Ǹ� ������ ���� ��ǥ����
	  // ���� ���콺 ��ǥ���� ���߾� �����Ѵ�.

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

	    // ���� ��� ���콺�� ���� �� ������ ����Ʈ�� �߰��Ѵ�.
	    // Ŭ���� ��쿡 ����Ʈ�� �߰����� �ʾƵ� �ȴ�.
	    // Do nothing!

	  } else {

	    shapeElement.addMousePoint( e );

	    shapeElement.reSetShape();

	  }

	  final LinkedList pointList = shapeElement.getPointList();

	  if( pointList.size() > 1 ) { // ����Ʈ ������ �� �� �̻� �̰�,
				       // ���콺�� Ŭ������ ��,
				       // �� ���� ù ���� ���� ���ٰ� �Ǵ� �� ��쿡��
				       // ���� ������Ʈ�� �߰��ϰ�,
				       // ���� ���� ��ȯ�Ѵ�.

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

	     // ���� ��� ��쿡�� ���콺 ����ÿ� ù ���� �� ���� ����,
	     // ����Ʈ ������ 4 �� �̻��� ��쿡,
	     // ���� ������Ʈ�� �����Ϳ� �߰��Ѵ�.

	     // ���� ��� ���� ����� ����� ���ؼ�
	     // ������ ����Ʈ�� ù ���� ������ �־��ش�.

	     pointList.add( pointList.getFirst() );

	     // ��. ���� � ù ���� ������ �� ���� �ϱ�.

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

	// ���� ���� �����

	shapeElement.paint( g2 );

	// ��. ���� ���� �����

	// ���콺 ��ġ�� ���߾ ������ ����Ʈ ��ǥ ����

	lastPoint.setLocation( e.getX(), e.getY() );

	shapeElement.reSetShape();

	// ��. ������ ����Ʈ ��ǥ ����

	// ���ο� ���� �׸���

	shapeElement.paint( g2 );

	// ��. ���ο� ���� �׸���

	g2.setPaintMode();

      }

  }

  public boolean isVeryNear( final Point p, final Point q ) {

      return Math.abs( p.x - q.x ) < 4 && Math.abs( p.y - q.y ) < 4 ;

  }

}