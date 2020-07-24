package gmlviewer.gis.viewer;

import java.io.*;
import java.net.*;
import java.util.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.print.*;
import javax.swing.*;

import gmlviewer.gis.*;
import gmlviewer.gis.comp.*;
import gmlviewer.gis.file.gml.*;
import gmlviewer.gis.file.shape.*;
import gmlviewer.gis.kernel.*;
import gmlviewer.gis.model.*;
import gmlviewer.gis.model.Area;
import gmlviewer.gis.model.Shape;
import gmlviewer.gis.rsc.*;
import gmlviewer.gis.style.*;
import gmlviewer.gis.util.*;
import gmlviewer.gis.util.FileSystem;
import javax.swing.border.EtchedBorder;
import javax.swing.border.Border;

public class MapViewer
    extends JPanel implements MouseListener, MouseMotionListener,
    StateListener
    , Printable, DebugInterface {

  public MapViewer() {

    // set options
    this.showAllLyr = true;
    this.showTextLayer = GmlViewerRegistry.SHOW_TEXT_LAYER;
    this.showMapUnitBoundary = GmlViewerRegistry.SHOW_MAP_BOUNDARY;
    this.showCenterLine = GmlViewerRegistry.SHOW_CENTER_LINE;
    this.showGlobalBox = GmlViewerRegistry.SHOW_GLOBAL_BOUNDARY;
    // end of setting options

    this.gisProject = new GisProject();

    this.isPainting = false;

    this.mode = this.SHOW_ATTRIBUTE;

    MBR mbr = new MBR(0, 0, 1, 1);
    Dimension size = this.getSize();
    this.proj = Project.getProject(mbr, size);

    this.clearClickedPoints = false;
    this.xorLineColor = Color.yellow;
    this.calcShapeFillColor = Color.red;
    this.calcShapeLineColor = Color.black;

    this.calcLayer = new Lyr("길이 면적 계산 레이어", LyrType.AREA, new Integer(0));

    Style calcStyle = LineStyle.getStyle(this.calcShapeLineColor);
    this.calcLayer.setStyle(calcStyle);

    this.historyList = new Vector();

    this.progressBar = new RealProgressBar(this,
                                           GmlViewerRegistry.PROGRESS_BAR_SIZE);

    setFont(FontManager.getFont("Serif", Font.PLAIN, 12));

    addMouseMotionListener(this);
    addMouseListener(this);
    addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        whenComponentResized(e);
      }
    });

    addKeyListener(new KeyAdapter() {
      public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        if (e.isShiftDown() && (c == 'p' || c == 'P')) {
          print();
        }
        else {
          //System.out.println( "Pressed key = " + c );
        }
      }
    });

  }

  private void whenComponentResized(ComponentEvent e) {
    Dimension size = this.getSize();
    Project proj = this.proj;
    if (proj != null) {
      this.proj = Project.getProject(proj.getMBR(), size);
      this.repaint();
    }
  }

  /**
   * 상태 메시지 출력 콤포넌트 설정 하기
   */
  public void setStatusLabel(JLabel statusLabel) {

    this.statusLabel = statusLabel;

  }

  public void setMbrBar(JTextField mbrBar) {

    this.mbrBar = mbrBar;

  }

  /**
   * 로고 그리기
   */

  public void paintLogo(Graphics2D g2) {

    Image logoImage = this.logoImage;

    Dimension size = getSize();

    int iw = logoImage.getWidth(this);

    g2.setPaintMode();

    g2.drawImage(logoImage, 10, 10, this);

//     Utilities.debug(this, "DONE PAINTING LOGO." );

  }

  private void addViewParamToHistoryList() {

    //ViewParam viewParam = new ViewParam(viewBoxClone, paintScale);

    Vector historyList = this.historyList;

    historyNum += 1;

    for (int i = historyNum; i < historyList.size(); ) {

      historyList.removeElementAt(i);

    }

    //historyList.addElement(viewParam);

  }

  private void debug(String msg) {
    debug.debug(this, msg);
  }

  private MBR getTopLevellMBR() {

    return this.getGlobalMBR(this.proj);

  }

  private MBR getGlobalMBR(Project proj) {

    GisProject gisProject = this.getGisProject();

    MBR mbr = null;

    Lyr[] lyrs = gisProject.getLayers(null, null);
    Lyr lyr;

    for (int i = 0, len = lyrs.length; i < len; i++) {
      lyr = lyrs[i];

      if (lyr != null) {
        mbr = mbr.union(lyr.getMBR(), mbr);
      }

    }

    return mbr;

  }

  private void setViewAllBox() {

    debug("Setting View All Box .....");

    Project proj = this.proj;

    MBR gmbr = this.getTopLevellMBR();

    if (gmbr == null) {
      gmbr = GmlViewerRegistry.DEFAULT_GLOBAL_MBR;
    }

    debug("VIEW ALL MBR = " + gmbr);

    Dimension size = getSize();

    this.proj = Project.getProject(gmbr, size.width, size.height);

    debug("Done Setting View All Box.");

  }

  public void paintLayers(Graphics2D g, Project proj, Dimension size) {

    boolean showTextLayer = this.showTextLayer;
    boolean showMapUnitBoundary = this.showMapUnitBoundary;
    boolean showCenterLine = this.showCenterLine;
    boolean showGlobalBox = this.showGlobalBox;

    // preserve original color
    Color orgColor = g.getColor();
    // end of preservation original color

    if (showCenterLine) {
      paintCrossLine(g, size, Color.red);
    }

    // paint normal layer

    if (true) {

      LyrPainter painter = new LyrPainter(showTextLayer);

      PaintInfo pinfo = new PaintInfo();

      Lyr[] lyrs = this.getLayersToPaint(proj, pinfo);

      double tx = 0, ty = 0;

      int lyrNoToPainted = painter.paintLayers(lyrs, proj, g, pinfo, tx, ty);

    }

    // end of painting normal layer

    // paint calculation layer
    paintCalcLayer(g, proj);
    // end of painting calculation layer

    // paint selected shape layer
    paintSelShapeLayer(g, proj);
    // end of painting selected shape layer

    if (showGlobalBox) {
      //paintGlobalBox(g, paintedMBR);
    }

    if (showCenterLine) {
      paintCrossLine(g, size, Color.red);
    }

    // restore original color
    g.setColor(orgColor);
    // end of restoration original color

  }

  private Lyr[] getLayersToPaint(Project proj, PaintInfo paintInfo) {
    return this.gisProject.getLayers(proj, paintInfo);
  }

  public void addLyr() {

    Lyr lyr = this.openLayerFile();

    if (lyr != null) {

      GisProject gisProject = this.getGisProject();

      Lyr bgLyr = gisProject.getBgLyr();

      if (bgLyr == null) {
        Style style = FillStyle.getStyle(Color.black, Color.lightGray);
        lyr.setStyle(style);
        gisProject.setBgLyr(lyr);
      }
      else {
        Color fgColor = HtmlUtil.fromHtmlColor(GmlViewerRegistry.
                                               H_BUILD_FG_COLOR);

        if (fgColor == null) {
          fgColor = Color.cyan;
        }

        Style style = FillStyle.getStyle(Color.black, fgColor);
        lyr.setStyle(style);
        gisProject.setSrchLyr(lyr);
      }

      if (true) {
        // 레이어 갯수가 1일때 전체 보기를 한다.

        Lyr[] lyrs = gisProject.getLayers(null, null);

        if (lyrs != null && lyrs.length < 2) {
          this.viewAll();
        }
      }
    }
  }

  public void removeLyr() {

    GisProject gisProject = this.getGisProject();

    if (gisProject != null) {

      if (gisProject.getSrchLyr() != null) {
        gisProject.setSrchLyr(null);
      }
      else if (gisProject.getBgLyr() != null) {
        gisProject.setBgLyr(null);
      }

      if (this.selShpLayer != null) {
        this.selShpLayer = null;
      }

      if (true) {
        this.hideAttributeWindow();
      }

    }

    this.setStatus("");

  }

  private JFileChooser getFileChooser() {
    if (this.fileChooser != null) {
      return this.fileChooser;
    }
    else {
      JFileChooser fileChooser = new JFileChooser();
      fileChooser.setFileFilter(new SimpleFileFilter(new String[] {".gml",
          ".xml"}, "GML, XML"));
      this.fileChooser = fileChooser;
      return fileChooser;
    }
  }

  public Lyr openLayerFile() {

    File file = null;

    FileSystem fs = new FileSystem();

    file = fs.selectAFile(this, this.getFileChooser());

    if (file != null) {

      String fileTailName = FileUtil.getFileTailName(file);

      if (fileTailName.equalsIgnoreCase(".gml") ||
          fileTailName.equalsIgnoreCase(".xml")) {

        GmlParser gmlParser = new GmlParser();

        try {
          return gmlParser.getLyr(file);
        }
        catch (Exception ex1) {

          String message = file.getName() + " 파일을 파싱하는 중 에러가 발생하였습니다.";
          message += "\r\n에러 메시지 : " + ex1.getMessage();

          JOptionPane.showMessageDialog(this, message, "GML 파싱 에러",
                                        JOptionPane.WARNING_MESSAGE);

          ex1.printStackTrace();

          this.debug("" + ex1);

          return null;
        }

      }
      else {

        String url = "file:///" + file.getAbsoluteFile();
        try {
          return openEsriShapeUrl(url, file.getName());
        }
        catch (Exception ex) {

          ex.printStackTrace();

          this.debug("" + ex);
          return null;
        }

      }

    }
    else {

      return null;

    }

  }

  public Lyr openEsriShapeUrl(String urlText, String layerName) throws
      MalformedURLException, IOException {

    debug.debug(this, "ESRI SHP URL = " + urlText);

    URL url = new URL(urlText);

    paintStatus(layerName + "를 로딩중입니다!");

    return openEsriShapeInputStream(url.openStream(), layerName);

  }

  private Lyr openEsriShapeInputStream(InputStream in, String lyrName) throws
      IOException {

    ShapeFileReader reader = new ShapeFileReader(in);

    reader.addWorkStateListener(this);

    reader.readLayer();

    Lyr layer = reader.getLayer(lyrName);

    layer.setSelected(true);

    return layer;

  }

  public String toString() {

    return "size = " + getSize() + ", project = " + this.proj;

  }

  public void paintGlobalBox(Graphics2D g2, MBR mbr) {

    //MBR mbr = this.getGlobalMBROfLayersToPaint();

    if (mbr != null) {
      // top left graphical coordinate
      Pnt topLeftGraphical = getGraphicalPoint(mbr.minx(), mbr.maxy());
      // bottom right graphical coordiante
      Pnt bottomRightGraphical = getGraphicalPoint(mbr.maxx(), mbr.miny());

      Color c = g2.getColor(); // old color
      g2.setColor(Color.blue);

      Rectangle2D rect =
          new Rectangle2D.Double(topLeftGraphical.x, topLeftGraphical.y,
                                 bottomRightGraphical.x - topLeftGraphical.x -
                                 1,
                                 bottomRightGraphical.y - topLeftGraphical.y -
                                 1);

      g2.draw(rect);
      g2.setColor(c); // restore old color
    }
  }

  /**
   * converts mouse coordinate to geographical coordinate
   */

  public Pnt getSpatialPoint(int x, int y) {

    return proj.toSpatial(x, y);

  }

  /**
   * converts spatial coordiante to graphical coordinate
   */

  public Pnt getGraphicalPoint(double x, double y) {

    return proj.toGraphics(x, y);

  }

  private boolean setStatus(Pnt geoPoint) {

    int mode = this.mode;

    String status;

    if (mode == DISTANCE_CALC || mode == AREA_CALC) {
      Shape calcShape = this.calcShape;

      if (calcShape != null) {
        status = (calcShape instanceof Area) ? "AREA : " + calcShape.getArea() :
            "DISTANCE : " + calcShape.getLength();
      }
      else {
        status = "Digitizing ...";
      }
    }
    else {
      if (geoPoint != null) {
        status = geoPoint.toString();
      }
      else {
        status = "";
      }
    }

    this.setStatus(status);

    return true;

  }

  public void mouseMoved(MouseEvent e) {

    int x = e.getX(), y = e.getY();

    Pnt geoPoint = getSpatialPoint(x, y);

    if (setStatus(geoPoint)) {
      paintStatus();
    }

    int mode = this.mode;

    if (mode == DISTANCE_CALC || mode == AREA_CALC) {
      doDistanceCalcModeMouseMoved(e);
      if (clearClickedPoints) {
        // Do nothing!
      }
      else {
        lastMoveME = e;
      }
    }
    else if (mode == this.SHOW_ATTRIBUTE) {
      doShowAttributeModeMouseEvent(e);
    }
    else {
      lastMoveME = e;
    }

    //debug( "Moving mouse over canvas" );

  }

  public void mousePressed(MouseEvent e) {

    super.requestFocus(true);

    int mode = this.mode;

    setCursor();

    int x = e.getX(), y = e.getY();
    Pnt geoPoint = getSpatialPoint(x, y);

    if (setStatus(geoPoint)) {
      paintStatus();
    }

    if (mode == DISTANCE_CALC || mode == AREA_CALC) {
      doShowAttributeModeMouseEventThread(e);
    }

    lastPressME = e;

    lastMoveME = null;

  }

  public void mouseReleased(MouseEvent e) {

    int mode = this.mode;

    if (mode == PAN) {
      if (this.lastPressME != null) {
        pan(e, this.lastPressME);
      }
    }
    else if (mode == SEL_EXTENT) {
      doExtentSelectModeMouseRelased(e);
    }
    else if (mode == SHOW_ATTRIBUTE) {
      doShowAttributeModeMouseEventThread(e);
    }

    lastPressME = lastDragME = null;

  }

  private void doShowAttributeModeMouseEvent(MouseEvent e) {

    Shape selShape = this.selShape;

    if ( selShape != null ) {
      java.awt.Shape as = selShape.getShape(this.proj);
      if (!as.contains(e.getX(), e.getY())) {
        this.selShpLayer = null;
        this.selShape = null;
        this.hideAttributeWindow();
        this.repaint();
        return;
      }
    }

  }

  private void doShowAttributeModeMouseEventThread(MouseEvent e) {

    debug.debug(this, "Showing attribute ......");

    int x = e.getX(), y = e.getY();

    GisProject gisProject = this.getGisProject();

    Lyr layer = gisProject.getSrchLyr();

    if (layer == null) {
      layer = gisProject.getBgLyr();
    }

    if (layer != null) {

      Graphics2D g2 = (Graphics2D) (this.getGraphics());

      Project proj = this.proj;

      Lyr srchLyr = layer.getShapesIncludes(x, y, proj);

      Style srchLyrStyle = FillStyle.getStyle(Color.black, Color.red);

      srchLyr.setStyle(srchLyrStyle);

      this.selShpLayer = srchLyr;

      this.repaint();

      if (false) {
        this.paintSelShapeLayer(g2, proj);
      }

      Shape shapes[] = srchLyr.getSpatialShapes();

      if (shapes != null && shapes.length > 0) {
        showAttribute(shapes[0]);
      }

    }

    debug("End of Showing attribute ......");

  }

  private Shape selShape;

  public void showAttribute(Shape shape) {

    if (shape != null || this.selShape == shape) {
      // 검색된 도형이 없거나 , 검색된 도형이 이전 도형과 같을 때....
      this.selShape = shape;
    }
    else {
      this.hideAttributeWindow();
    }

    if (shape instanceof GmlArea) {
      GmlArea gmlArea = (GmlArea) shape;

      if (gmlArea.getGmlObject() == null || !gmlArea.getGmlObject().isOx()) {
        // 검색 대상이 아니면.....
        this.hideAttributeWindow();
        return;
      }
    }

    Pnt center = shape.getCenter();

    if (shape != null && center != null) {

      JWindow aw = this.getAttributeWindow();

      if (aw != null) {

        if (this.propPane != null) {
          this.propPane.setShapeAttribute(shape);
        }

        Pnt winLoc = this.proj.toGraphics(center);

        //aw.setLocationRelativeTo( this );

        java.awt.Point thisLoc = this.getLocationOnScreen();

        int locX = thisLoc.x + (int) winLoc.x;
        int locY = thisLoc.y + (int) winLoc.y;

        if (aw.isVisible() || aw.isShowing()) {
          // 이미 속성 윈도우가 떠 있으면 잠시 감추었다가 다시 뛰운다.
          aw.setVisible(false);
          this.repaint();
          aw.setLocation(locX, locY);
          aw.setVisible(true);

        }
        else {
          aw.setLocation(locX, locY);
          aw.setVisible(true);
        }

      }

    }

  }

  public void hideAttributeWindow() {

    JWindow aw = this.getAttributeWindow();

    if (aw != null) {
      if (aw.isShowing() || aw.isVisible()) {
        aw.setVisible(false);
      }
    }

  }

  // do distance calculation when mouse pressed

  private void doDistanceCalcModeMousePressed(MouseEvent e) {

    Graphics2D g2 = (Graphics2D) getGraphics();
    Project proj = this.proj;

    if (clearClickedPoints) {
      clearClickedPoints();
      repaint();
    }
    else {
      g2.setXORMode(xorLineColor);
      calcLayer.paintNonTextOnly(g2, proj);
      calcLayer.paintTextOnly(g2, proj);
      g2.setPaintMode();
    }

    int modifier = e.getModifiers();

    int x = e.getX(), y = e.getY(); // x and y coordinate

    Shape calcShape = this.calcShape;

    Pnt[] points = calcShape.getGeoPoints();

    int len = points.length;

    Pnt sxy = this.proj.toSpatial(x, y);

    Pnt sq = new Pnt(sxy.x, sxy.y);

    this.calcShape.addGeoPoint(sq);

    if (len == 0) {

      this.calcShape.addGeoPoint(sq);

    }

    g2.setXORMode(xorLineColor);

    calcLayer.paintNonTextOnly(g2, proj);
    calcLayer.paintTextOnly(g2, proj);

    g2.setPaintMode();

    if (WinUtil.isRightMouseButton(e)) {

      clearClickedPoints = true;

    }

  }

  private void doDistanceCalcModeMouseMoved(MouseEvent e) {

    if (clearClickedPoints) {

      return;

    }

    Shape calcShape = this.calcShape;

    Pnt[] points = calcShape.getGeoPoints();

    int len = points.length;

    Project proj = this.proj;

    if (len > 0) {
      Graphics2D g2 = (Graphics2D) getGraphics();
      g2.setXORMode(xorLineColor);

      // clear previous move line
      calcLayer.paintNonTextOnly(g2, proj);
      calcLayer.paintTextOnly(g2, proj);
      // end of clearing previous move line

      // set new coordinate of last spatial point

      // x and y coordinate

      int x = e.getX(), y = e.getY();
      Pnt q = this.proj.toSpatial(x, y);

      calcShape.setShape(null); // to reconstruct shape by setting null

      // end of setting new coordinate of last spatial point

      // draws current move line

      calcLayer.paintNonTextOnly(g2, proj);
      calcLayer.paintTextOnly(g2, proj);

      g2.setPaintMode();

      // end of drawing current move line

    }

  }

  private void doDistanceCalcModeMouseExited(MouseEvent e) {
    // Do nothing!
  }

  private void doExtentSelectModeMouseRelased(MouseEvent e) {

    this.showTextLayer = true;
    selectExtent(null);
    setViewBox(lastPressME, e);

  }

  public void mouseClicked(MouseEvent e) {
  }

  public void mouseDragged(MouseEvent e) {

    int mode = this.mode;

    if (mode == PAN) {
//      MouseEvent pre = (lastDragME == null) ? lastPressME : lastDragME;
//      pan(e, pre);
    }
    else if (mode == SEL_EXTENT) {
      selectExtent(e);
    }

    lastDragME = e;

  }

  private void setViewBox(MouseEvent e, MouseEvent e2) {

    Pnt from = getSpatialPoint(e.getX(), e.getY());
    Pnt to = getSpatialPoint(e2.getX(), e2.getY());

    MBR mbr = new MBR(from, to);
    Dimension size = this.getSize();

    this.proj = Project.getProject(mbr, size);

    repaint();

    //addViewParamToHistoryList(viewBox, paintScale);

  }

  public void selectExtent() {
    this.mode = SEL_EXTENT;
  }

  private void selectExtent(MouseEvent e) {

    Graphics2D g2 = (Graphics2D) getGraphics();

    g2.setXORMode(Color.yellow);

    int lx = lastPressME.getX(), ly = lastPressME.getY(); // last x and y

    // clear old selection rectangle

    if (lastDragME != null) {

      int x = lastDragME.getX(), y = lastDragME.getY();

      int tx = (lx < x) ? lx : x;
      int ty = (ly < y) ? ly : y;

      g2.draw3DRect(tx, ty, Math.abs(x - lx), Math.abs(y - ly), true);

    }

    // end of clearance old rectangle

    // draw new selection rectangle
    if (e != null) {
      int x = e.getX(), y = e.getY();

      int tx = (lx < x) ? lx : x;
      int ty = (ly < y) ? ly : y;

      g2.draw3DRect(tx, ty, Math.abs(x - lx), Math.abs(y - ly), true);
    }

    // end of drawing new selection rectangle

    g2.setPaintMode();

  }

  private void pan(MouseEvent now, MouseEvent pre) {

    int dx = now.getX() - pre.getX();
    int dy = now.getY() - pre.getY();

    pan( -dx, -dy);

  }

  public void mouseExited(MouseEvent e) {

    CursorManager.setCursor(this, CursorManager.def);

    int mode = this.mode;
    if (mode == DISTANCE_CALC || mode == AREA_CALC) {
      doDistanceCalcModeMouseExited(e);
    }

    lastMoveME = null;

  }

  public void mouseEntered(MouseEvent e) {
    setCursor();
  }

  public void setCursor() {

    int mode = this.mode;

    int type = CursorManager.cross;

    if (mode == PAN) {
      type = CursorManager.move;
    }
    else if (mode == ZOOM_IN) {
      type = CursorManager.hand;
    }
    else if (mode == ZOOM_OUT) {
      type = CursorManager.hand;
    }
    else if (mode == SEL_EXTENT) {
      type = CursorManager.cross;
    }
    else if (mode == SHOW_ATTRIBUTE) {
      type = CursorManager.hand;
    }

    CursorManager.setCursor(this, type);

  }

  public boolean setViewBoxZoomIn() {

    Project proj = this.proj;

    MBR mbr = proj.getMBR();

    Dimension size = getSize();

    double percent = this.zoomInPercent;

    Pnt cenS = mbr.getCenter();
    double cenSx = cenS.x;
    double cenSy = cenS.y;

    double sw = mbr.getWidth(); // spatial width
    double sh = mbr.getHeight(); // spatial height

    double wv = 0, hv = 0; // width variant and height variant

    if (sw < sh) {
      wv = (sw / 2) * percent;
      hv = (wv * size.height) / size.width;
    }
    else {
      hv = (sh / 2) * percent;
      wv = (hv * size.width) / size.height;
    }

    if (mbr.minx() + wv >= cenSx || mbr.miny() + hv >= cenSy) {
      return false;
    }

    MBR newMBR = new MBR(mbr.minx() + wv, mbr.miny() + hv,
                         mbr.maxx() - wv, mbr.maxy() - hv
        );

    this.proj = Project.getProject(newMBR, size);

    return true;

  }

  public boolean zoomIn() {

    mode = ZOOM_IN;
    boolean zoomIn = setViewBoxZoomIn();

    if (zoomIn) {

      this.showTextLayer = true;

      setCursor();
      repaint();
      addViewParamToHistoryList();
      return true;
    }
    else {
      return false;
    }

  }

  private boolean setViewBoxZoomOut() {

    Project proj = this.proj;
    MBR mbr = proj.getMBR();

    double percent = this.zoomOutPercent;

    double sw = mbr.getWidth(); // spatial width
    double sh = mbr.getHeight(); // spatial height

    Dimension size = getSize();

    double wv = 0, hv = 0; // width variant and height variant

    if (sw < sh) {

      wv = (sw / 2) * percent;

      hv = (wv * size.height) / size.width;

    }
    else {

      hv = (sh / 2) * percent;

      wv = (hv * size.width) / size.height;

    }

    MBR newMBR = new MBR(mbr.minx() - wv, mbr.miny() - hv,
                         mbr.maxx() + wv, mbr.maxy() + hv
        );

    this.proj = Project.getProject(newMBR, size);

    return true;

  }

  /**
   * 축소
   */

  public boolean zoomOut() {

    mode = ZOOM_OUT;
    boolean zoomOut = setViewBoxZoomOut();

    if (zoomOut) {
      setCursor();
      repaint();
      addViewParamToHistoryList();
      return true;
    }
    else {
      return false;
    }

  }

  /**
   * 전체 영역 표시
   */

  public void viewAll() {

    // 전체 보기에서는 텍스트를 보여 주지 않는다.

    //this.showTextLayer = false;

    setViewAllBox();
    repaint();
    addViewParamToHistoryList();

  }

  public void setZoomLevel(int level) {

    level--;
    setViewAllBox();
    for (int i = 0; i < level; i++) {
      setViewBoxZoomIn();
    }

    setCursor();
    repaint();

    addViewParamToHistoryList();

  }

  private void paintCrossLine(Graphics2D g2, Dimension size, Color color) {

    int w = size.width;
    int h = size.height;
    int x = w / 2;
    int y = h / 2;

    int gap = 2;

    g2.setColor(color);

    g2.drawLine(0, y, x - gap, y);
    g2.drawLine(x + gap, y, w, y);

    g2.drawLine(x, 0, x, y - gap);
    g2.drawLine(x, y + gap, x, h);

  }

  /**
   * 이동
   */

  public void pan() {

    mode = PAN;
    setCursor();

  }

  public void pan(int dx, int dy) {

    mode = PAN;

    Project proj = this.proj;

    double scale = proj.getScale();
    double sx = dx / scale; // spatial x
    double sy = -dy / scale; // spatial y

    MBR mbr = proj.getMBR();

    MBR newMBR = new MBR(mbr.minx() + sx, mbr.miny() + sy,
                         mbr.maxx() + sx, mbr.maxy() + sy
        );

    Dimension size = getSize();

    this.proj = Project.getProject(newMBR, size);

    repaint();

  }

  public void pan(String dir) {

    Dimension size = getSize();

    if (size == null) {
      return;
    }

    int dx = size.width / 4, dy = size.height / 4;

    if (dir.equalsIgnoreCase("NW")) {
      dx = -dx;
      dy = -dy;
    }
    else if (dir.equalsIgnoreCase("N")) {
      dx = 0;
      dy = -dy;
    }
    else if (dir.equalsIgnoreCase("NE")) {
      dy = -dy;
    }
    else if (dir.equalsIgnoreCase("W")) {
      dx = -dx;
      dy = 0;
    }
    else if (dir.equalsIgnoreCase("E")) {
      dy = 0;
    }
    else if (dir.equalsIgnoreCase("SW")) {
      dx = -dx;
    }
    else if (dir.equalsIgnoreCase("S")) {
      dx = 0;
    }
    else if (dir.equalsIgnoreCase("SE")) {
      // Do nothing
    }

    pan(dx, dy);

    addViewParamToHistoryList();

  }

  private void init() {

    logoImage = Resource.getResourceImage(GmlViewerRegistry.GEN_RES_DIR,
                                          GmlViewerRegistry.LOGO_FILE);

    Lyr calcLayer = this.calcLayer;

    calcLayer.setLineColor(calcShapeLineColor);

    calcLayer.setFillColor(calcShapeFillColor);

    calcLayer.setSelected(true);

    inited = true;

  }

  public void paint(Graphics g) {

    if (!isPainting) {

      Dimension size = getSize();

      this.isPainting = true;

      try {
        // 컴포넌트 초기화
        if (!inited) {
          super.paint(g);
          init();
        }
        // 끝. 컴포넌트 초기화

        Graphics2D g2 = (Graphics2D) g;

        this.paintViewer(g2, this.proj, size);

      }
      catch (Exception ex) {
        debug.debug(ex);
      }
      catch (Error ex) {
        debug.debug(ex);
      }

      this.isPainting = false;

    }

  }

  private void paintViewer(Graphics2D g2, Project proj, Dimension size) {

    // 그래픽 클립 영역 설정, 전체 크기와 같이 설정한다.

    java.awt.Shape orgClip = g2.getClip();

    g2.setClip(0, 0, size.width, size.height);

    // 끝. 그래픽 클립 영역 설정

    // 바탕 그리기

    g2.setColor(Color.white);
    g2.fillRect(0, 0, size.width, size.height);

    // 끝. 바탕 그리기

    if (false) {
      paintLogo(g2);
    }

    paintLayers(g2, proj, size);

    paintWorkState(g2);

    paintStatus();

    if( false ) {

      paintLogo(g2);
    }

    // 그래픽 클립 영역 복원

    g2.setClip(orgClip);

    // 끝. 그래픽 클립 영역 복원

  }

  private void paintCalcLayer(Graphics2D g2, Project proj) {

    Lyr calcLayer = this.calcLayer;

    g2.setXORMode(this.xorLineColor);

    calcLayer.paintNonTextOnly(g2, proj);
    calcLayer.paintTextOnly(g2, proj);

    g2.setPaintMode();

  }

  private void paintSelShapeLayer(Graphics2D g2, Project proj) {

    Lyr layer = this.selShpLayer;

    if (layer != null) {
      layer.paintNonTextOnly(g2, proj);
      layer.paintTextOnly(g2, proj);
    }

  }

  public void calculateDistance() {

    this.mode = DISTANCE_CALC;

    clearClickedPoints();

    repaint();

  }

  public void calculateArea() {

    this.mode = AREA_CALC;

    clearClickedPoints();

    repaint();

  }

  public void showAttrribute() {

    this.mode = SHOW_ATTRIBUTE;

    clearClickedPoints();

    //this.selShpLayer = null;

    repaint();

  }

  private void clearClickedPoints() {

    int mode = this.mode;

    Lyr calcLayer = this.calcLayer;

    if (this.calcShape != null) {
      calcLayer.removeSpatialShape(this.calcShape);
    }

    if (mode == DISTANCE_CALC) {
      Shape calcShape = new Line("", -1, new Pnt[] {});
      this.calcShape = calcShape;
      calcLayer.addSpatialShape(calcShape);
      clearClickedPoints = false;
    }
    else if (mode == AREA_CALC) {
      Shape calcShape = new Area( -1, new Pnt[] {});
      this.calcShape = calcShape;
      calcLayer.addSpatialShape(calcShape);
      clearClickedPoints = false;
    }

  }

  public void paintStatus() {

    paintStatus(this.status);

  }

  public void paintStatus(String status) {

    if (status == null) {
      status = GmlViewerRegistry.COPY_RIGHT + " " +
          GmlViewerRegistry.ALL_RIGHTS_RESERVED;
      this.status = status;
    }
    else {
      status = status;
    }

    JLabel statusLabel = this.statusLabel;
    if (statusLabel != null) {
      statusLabel.setText(this.status);
    }

    JTextField mbrBar = this.mbrBar;
    if (mbrBar != null) {
      Project proj = this.proj;
      if (proj != null && proj.getMBR() != null) {
        mbrBar.setText("" + proj.getMBR());
      }
    }

  }

  public void goForward() {

    if (increaseHistoryNum()) {
      goCurrentHistoryViewBox();
    }
    else {
      paintStatus(GmlViewerRegistry.CANNOT_GO_FOREWARD_MORE_TEXT);
    }

  }

  public void goBackward() {

    if (decreaseHistoryNum()) {
      goCurrentHistoryViewBox();
    }
    else {
      paintStatus(GmlViewerRegistry.CANNOT_GO_BACKWARD_MORE_TEXT);
    }

  }

  private void goCurrentHistoryViewBox() {
    repaint();
  }

  private boolean increaseHistoryNum() {

    int historyListSize = historyList.size();
    boolean increasible = (historyNum < historyListSize - 1);
    if (increasible) {
      historyNum += 1;
    }
    return increasible;
  }

  private boolean decreaseHistoryNum() {

    int historyListSize = historyList.size();
    boolean decreasible = (historyNum > 0);
    if (decreasible) {
      historyNum -= 1;
    }
    return decreasible;
  }

  public void workStateChanged(StateEvent e) {
    lastWorkStateEvent = e;
    paintWorkState( (Graphics2D) getGraphics());
  }

  private void setStatus(String status) {
    this.status = GmlViewerRegistry.APP_NAME + " " + status;
  }

  public void paintWorkState(Graphics2D g2) {

    StateEvent lastWorkStateEvent = this.lastWorkStateEvent;
    JProgressBar progressBar = this.progressBar;

    if (progressBar != null) {

      if (lastWorkStateEvent == null) {
        progressBar.setValue(progressBar.getMinimum());
      }
      else {

        int workPercent = (int) (100 * lastWorkStateEvent.current /
                                 lastWorkStateEvent.total);

        String status = workPercent + " %";
        progressBar.setValue(workPercent);
        progressBar.setString(status);

        //Util.debug(this, "WORK PCT = " + status );

        if (status == null) {
          status = GmlViewerRegistry.APP_NAME + " " +
              GmlViewerRegistry.COPY_RIGHT + " " +
              GmlViewerRegistry.ALL_RIGHTS_RESERVED;
          this.status = status;
        }

        if (workPercent >= 100) {
          this.lastWorkStateEvent = null;
          progressBar.setValue(progressBar.getMinimum());
        }
      }
    }
  }

  /**
   * 버튼들을 누를 때, 액션을 처리한다.
   */
  public void interpret(String command, Object event) {

    if (command == null) {

    }
    else if (command.equals(GmlViewerRegistry.ZOOM_IN_TEXT)) {
      zoomIn();
    }
    else if (command.equals(GmlViewerRegistry.ZOOM_OUT_TEXT)) {
      zoomOut();
    }
    else if (command.equals(GmlViewerRegistry.SEL_EXTENT_TEXT)) {
      selectExtent();
    }
    else if (command.equals(GmlViewerRegistry.PAN_TEXT)) {
      pan();
    }
    else if (command.equals(GmlViewerRegistry.FULL_EXTENT_TEXT)) {
      viewAll();
    }
    else if (command.equals(GmlViewerRegistry.GO_FORWARD_TEXT)) {
      goForward();
    }
    else if (command.equals(GmlViewerRegistry.GO_BACKWARD_TEXT)) {
      goBackward();
    }
    else if (command.equals(GmlViewerRegistry.CALC_DISTANCE_TEXT)) {
      calculateDistance();
    }
    else if (command.equals(GmlViewerRegistry.CALC_AREA_TEXT)) {
      calculateArea();
    }
    else if (command.equals(GmlViewerRegistry.SET_ZOOM_LEVEL)) {
      int zoomLevel = new Integer("" + event).intValue();
      setZoomLevel(zoomLevel);
    }
    else if (command.equals(GmlViewerRegistry.PAN_DIR_TEXT)) {
      String panDir = "" + event;
      if (panDir != null && panDir.length() > 0) {
        pan(panDir);
      }
    }
    else if (command.equals(GmlViewerRegistry.SHOW_ATTRIBUTE_TEXT)) {
      this.showAttrribute();
    }

  }

  public void print() {
    PrinterJob printJob = PrinterJob.getPrinterJob();
    printJob.setPrintable(this);
    if (printJob.printDialog()) {
      try {
        printJob.print();
      }
      catch (PrinterException pe) {
        System.out.println("Error printing: " + pe);
      }
    }
  }

  public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
    if (pageIndex > 0) {
      return (NO_SUCH_PAGE);
    }
    else {
      Graphics2D g2 = (Graphics2D) g;

      double x = pageFormat.getImageableX();
      double y = pageFormat.getImageableY();
      double w = pageFormat.getImageableWidth();
      double h = pageFormat.getImageableHeight();

      Project proj = this.proj;

      MBR mbr = proj.getMBR();

      Project printProj = Project.getProject(mbr, (int) w, (int) h);
      Dimension printSize = new Dimension( (int) w, (int) h);

      g2.translate(x, y);
      this.paintViewer(g2, printProj, printSize);
      g2.translate( -x, -y);

      return (PAGE_EXISTS);
    }
  }

  public void showTextLyr(boolean b) {
    this.showTextLayer = b;
  }

  public void showMapUnitBoundary(boolean b) {
    this.showMapUnitBoundary = b;
  }

  public void showAllLyr(boolean b) {
    this.showAllLyr = b;
  }

  public void showCenterLine(boolean b) {
    this.showCenterLine = b;
  }

  public void showGlobalBox(boolean b) {
    this.showGlobalBox = b;
  }

  public void setGisProject(GisProject gisProject) {
    this.gisProject = gisProject;
  }

  public void setAttributeWindow(JWindow attributeWindow) {
    this.attributeWindow = attributeWindow;
  }

  public GisProject getGisProject() {
    return this.gisProject;
  }

  public JWindow getAttributeWindow() {

    if (this.attributeWindow == null) {

      JFrame owner = WinUtil.getJFrame(this);

      JWindow attributeWindow = new JWindow(owner);

      attributeWindow.setSize(280, 100);

      JPropPane propPane = new JPropPane();

      Border border = BorderFactory.createCompoundBorder(BorderFactory.
          createEtchedBorder(EtchedBorder.RAISED, Color.white,
                             new Color(148, 145, 140)),
          BorderFactory.createEmptyBorder(2, 2, 2, 2));

      JPanel pane = (JPanel) attributeWindow.getContentPane();

      pane.setBorder(border);

      pane.add(propPane, BorderLayout.CENTER);

      this.attributeWindow = attributeWindow;
      this.propPane = propPane;

      return this.attributeWindow;

    }
    else {

      return this.attributeWindow;

    }

  }

  public BufferedImage getImage() {
    Dimension size = this.getSize();
    BufferedImage img = new BufferedImage(size.width, size.height,
                                          BufferedImage.TYPE_INT_RGB);
    Graphics2D g = (Graphics2D) (img.getGraphics());
    GraphicsUtil.setHighQuality(g);
    Project proj = this.proj;
    this.paintViewer(g, proj, size);
    return img;
  }

  private JWindow attributeWindow;

  private JPropPane propPane;

  private boolean isPainting;
  // options
  private boolean showAllLyr;
  private boolean showTextLayer = true;
  private boolean showMapUnitBoundary;
  private boolean showCenterLine;
  private boolean showGlobalBox;
  // end of options

  private JProgressBar progressBar;

  private StateEvent lastWorkStateEvent;

  private int historyNum;
  private Vector historyList;
  private Image logoImage;
  private String status;
  private JLabel statusLabel;
  private JTextField mbrBar;

  private boolean clearClickedPoints;
  private Project proj;

  // distance and area calculation layer
  private Lyr calcLayer;
  private Lyr selShpLayer;
  private Shape calcShape;

  private boolean inited;

  private Color calcShapeFillColor;
  private Color calcShapeLineColor;

  private Color xorLineColor; // xor drawing line color

  private double zoomInPercent = 0.25;
  private double zoomOutPercent = 0.25;

  private int mode;

  private MouseEvent lastPressME; // last pressed mouse event
  private MouseEvent lastDragME; // last dragged mouse event
  private MouseEvent lastMoveME; // last moved mouse event

  private GisProject gisProject; // GIS project

  private static JFileChooser fileChooser;

  // 레이어 뷰어 명령어들
  private static final int ZOOM_IN = 0,
  ZOOM_OUT = 1,
  SEL_EXTENT = 2,
  PAN = 3,
  GO_FORWARD = 4,
  GO_BACKWARD = 5,
  //FULL_EXTENT = 6,
  DISTANCE_CALC = 7,
  AREA_CALC = 8,
  SET_ZOOM_LEVEL = 9,
  PAN_DIR = 10,
  SHOW_ATTRIBUTE = 11
      ;
  // 끝. 레이어 뷰어 명령어들

}
