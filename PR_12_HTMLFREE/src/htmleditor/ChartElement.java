package htmleditor;

/**
 * Title:        Test Publishing System
 * Description:  Internet Based Test Publishing System V1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.*;
import jcosmos.*;
import com.sun.image.codec.jpeg.*;

public class ChartElement extends ImageElement {

  private ChartData data;

  private File chartFile;

  private int chartId = Math.abs( (int)(System.currentTimeMillis() ) );

  public ChartElement(HtmlDocument doc, File src, ChartData data ) {

      this( doc, src, new Point(0, 0), data );

  }

  public ChartElement(HtmlDocument doc, File src, Point loc, ChartData data) {

      this.data = data;

      super.init( doc, src, loc);

  }

  public ChartElement(HtmlDocument doc, File src, Rectangle area, ChartData data ) {

     this( doc, src, area, RECT, null, data );

  }

  public ChartElement(HtmlDocument doc, File src, Rectangle area, int style, String href, ChartData data ) {

      this.data = data;

      super.init( doc, src, area, style, href );

      Dimension size = data.getSize();

      super.setSize( size.width, size.height );

  }

  public Object clone(HtmlDocument doc) {

      Rectangle2D area = this.getArea();

      Rectangle cloneArea = new Rectangle( (int) area.getX(), (int) area.getY(), (int) area.getWidth(), (int) area.getHeight() );

      return new ChartElement( doc, src, cloneArea, style, href, data.cloneChartData() );

  }

  private File createChartFile() throws Exception {

     File tmpDir = HtmlEditorPane.getTempDir( getImageEditor().getScroller() );

     File file = new File( tmpDir, "chart" + chartId + ".jpg" );

     OutputStream out = new FileOutputStream( file );

     JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder( out );

     try {

	encoder.encode( (BufferedImage) getImage() );

     } catch (IOException e) {

	Utility.debug( e );

	return null;

     }

     return file;

  }

  public String getSourceName() {

      return getFile().getName();

  }

  public File getFile() {

     if( chartFile == null ) {

	 try {

	    this.chartFile = createChartFile();

	 } catch (Exception e) {

	    Utility.debug( e );

	 }
     }

     return chartFile;

  }

  public Image getImage() {

     Dimension size = data.getSize();

     BufferedImage image = new BufferedImage( size.width, size.height, BufferedImage.TYPE_INT_RGB );

     ChartUtilities.paint( image.getGraphics(), data );

     return image;

  }

  public ChartData getChartData() {

     return data;

  }

  public void setChartData(ChartData data) {

    this.data = data;

  }

  public String getNameTag() {

      return data.getNameTag();

  }

}