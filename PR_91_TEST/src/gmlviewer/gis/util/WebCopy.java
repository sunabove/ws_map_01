/**
 * Title:        Test Publishing System<p>
 * Description:  Internet Based Test Publishing System V1.0<p>
 * Copyright:    Copyright (c) Suhng ByuhngMunn<p>
 * Company:      JCOSMOS DEVELOPMENT<p>
 * @author Suhng ByuhngMunn
 * @version 1.0
 */
package gmlviewer.gis.util;

import java.io.*;
import java.net.*;
import javax.swing.*;

public class WebCopy {

  private double src = -1, sink = -1;

  private ProgressInterface progressBar;

  public WebCopy(ProgressInterface progressBar) {
    this.progressBar = progressBar;
  }

  public void copy(String in, Socket out) throws IOException {

    URL url = getUrl(in);

    if (url != null) {
      copy(url.openStream(), out.getOutputStream());
    }
    else {
      copy(new FileInputStream(in), out.getOutputStream());
    }

  }

  public void copy(String in, String out) throws IOException {

    URL url = getUrl(in);

    if (url != null) {
      copy(url, new File(out));
    }
    else {
      copy(new File(in), new File(out));
    }

  }

  public void copy(String in, File out) throws IOException {

    URL url = getUrl(in);

    if (url != null) {
      copy(url, out);
    }
    else {
      copy(new File(in), out);
    }

  }

  private URL getUrl(String url) {

    try {
      URL u = new URL(url);

      src = u.openConnection().getContentLength();

      return u;
    }
    catch (Exception e) {
      return null;
    }

  }

  public void copy(URL in, File out) throws IOException {
    copy(in.openStream(), new FileOutputStream(out));
  }

  public void copy(File in, File out) throws IOException {
    copy(new FileInputStream(in), new FileOutputStream(out));
  }

  public void copy(URL in, OutputStream out) throws IOException {
    copy(in.openStream(), out);
  }

  public void copy(Socket in, Socket out) throws IOException {
    copy(in.getInputStream(), out.getOutputStream());
  }

  public void copy(InputStream in, OutputStream out) throws IOException {

    try {
      int n; // number of bytes read
      ProgressInterface progressBar = this.progressBar;
      byte[] bytes = new byte[1024];

      while ( (n = in.read(bytes)) > -1) {
        out.write(bytes, 0, n);
        sink += n;
        if (progressBar != null) {
          progressBar.setValue( (int) (100.0 * sink / src));
        }
      }

      if (progressBar != null) {
        progressBar.setValue( -1);
      }

    }
//    catch (IndexOutOfBoundsException e) {
//           System.out.println( "copy done." );
//    }
    catch (IOException e) {
      throw e;
    }

  }

}
