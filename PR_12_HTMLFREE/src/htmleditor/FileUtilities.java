package htmleditor;

/**
 * Title:        Test Publishing System
 * Description:  Internet Based Test Publishing System V1.0
 * Copyright:    Copyright (c) Suhng ByuhngMunn
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.io.*;
import java.net.*;

public class FileUtilities {

    public static byte[] readBytes(File file)
    {
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	try
	{
	    InputStream is = new BufferedInputStream(new FileInputStream(file));
	    int count = 0;
	    byte[] buf = new byte[16384];
	    while ((count=is.read(buf)) != -1) {
		if (count > 0) baos.write(buf, 0, count);
	    }
	    is.close();
	}
	catch (Exception e) { e.printStackTrace(); }

	return baos.toByteArray();
    }

    public static char[] readChars(File file)
    {
	CharArrayWriter caw = new CharArrayWriter();
	try
	{
	    Reader in = new BufferedReader(new FileReader(file));
	    int count = 0;
	    char[] buf = new char[16384];
	    while ((count=in.read(buf)) != -1) {
		if (count > 0) caw.write(buf, 0, count);
	    }
	    in.close();
	}
	catch (Exception e) { e.printStackTrace(); }

	return caw.toCharArray();
    }

    public static String readString(File file) {

	return new String( readChars( file ) );

    }

    public static void writeBytes(File file, byte[] data) {
	try {
	    OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
	    os.write(data);
	    os.close();
	}
	catch (Exception e) { e.printStackTrace(); }
    }

    public static void writeChars(File file, char[] data) {
	try {
	    Writer os = new BufferedWriter(new FileWriter(file));
	    os.write(data);
	    os.close();
	}
	catch (Exception e) { e.printStackTrace(); }
    }

    public static int getFileLength( final File file ) {

      if( file == null ) {

	return 0;

      }

      try {

	final URL url = new URL( "file:///" + file.getCanonicalPath() );

	if( url == null ) {

	  return 0;

	}

	final URLConnection uc = url.openConnection();

	if( uc == null ) {

	  return 0;

	}

	return uc.getContentLength();

//	return ( new FileInputStream( file ).available() );

      } catch ( IOException e ) {

	return 0;

      }

    }

    public static void removeFilesUnderDir( final File dir ) {

      if( ! dir.exists() ) {

	return;

      }

      final File [] files = dir.listFiles();

      File file;

      for( int i = 0, len = files.length ; i < len; i ++ ) {

	file = files[ i ];

	if( file.isDirectory() ) {

	  removeFilesUnderDir( file );

	  file.delete();

	} else {

	  file.delete();

	}

      }

    }

}