package gmlviewer.gis.util;

import java.io.*;
import java.net.*;
import java.util.*;

public class FileUtil {

  public static byte[] readBytes(File file) {

    ByteArrayOutputStream baos = new ByteArrayOutputStream();

    try {
      InputStream is = new BufferedInputStream(new FileInputStream(file));
      int count = 0;

      byte[] buf = new byte[16384];

      while ( (count = is.read(buf)) != -1) {
	if (count > 0) {
	  baos.write(buf, 0, count);
	}
      }

      is.close();

    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return baos.toByteArray();

  }

  public static char[] readChars(File file) throws FileNotFoundException {
      Reader reader = new BufferedReader( new FileReader(file) );
      return readChars( reader );
  }

  public static char[] readChars( Reader in ) {

    CharArrayWriter caw = new CharArrayWriter();
    try {
      int count = 0;
      char[] buf = new char[16384];
      while ( (count = in.read(buf)) != -1) {
	if (count > 0) {
	  caw.write(buf, 0, count);
	}
      }
      in.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    return caw.toCharArray();
  }

  public static String readString(File file) throws FileNotFoundException {

    return new String( readChars(file) );

  }

  public static void writeBytes(File file, byte[] data) {
    try {
      OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
      os.write(data);
      os.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void writeChars(File file, char[] data) {
    try {
      Writer os = new BufferedWriter(new FileWriter(file));
      os.write(data);
      os.close();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static int getFileLength(final File file) {

    if (file == null) {
      return 0;
    }

    try {

      final URL url = new URL("file:///" + file.getCanonicalPath());
      if (url == null) {
	return 0;
      }

      final URLConnection uc = url.openConnection();
      if (uc == null) {
	return 0;
      }

      return uc.getContentLength();
    }
    catch (IOException e) {
      return 0;
    }

  }

  public static void removeFilesUnderDir(final File dir) {

    if (!dir.exists()) {
      return;
    }

    File[] files = dir.listFiles();
    File file;

    for (int i = 0, len = files.length; i < len; i++) {
      file = files[i];
      if (file.isDirectory()) {
	removeFilesUnderDir(file);
	file.delete();
      }
      else {
	file.delete();
      }
    }
  }

  public static BufferedReader getBufferedReader( File file )
      throws FileNotFoundException
  {
    return new BufferedReader(new InputStreamReader( new FileInputStream(file)));
  }

  public static String getFileBodyName( File file ) {
    String filename = file.getName();
    int dotIdx = filename.indexOf( "." );
    if( dotIdx > -1 ) {
      return filename.substring( 0, dotIdx );
    } else {
      return filename;
    }
  }

  public static String getFileTailName( File file ) {
    String filename = file.getName();
    int dotIdx = filename.indexOf( "." );
    if( dotIdx > -1  ) {
      return filename.substring( dotIdx );
    } else {
      return "" ;
    }
  }


  public static boolean isImgFile(String name) {
    name = name.toUpperCase();
    String[] imgExt = IMG_EXT ;
    for (int i = 0, len = imgExt.length; i < len; i++) {
      if (name.endsWith(imgExt[i])) {
        return true;
      }
    }
    return false;
  }

  //static member
  final static String [] IMG_EXT =
            { "GIF", "JPG", "JPEG", "PNG", "BMP" };
  // end of static member

}
