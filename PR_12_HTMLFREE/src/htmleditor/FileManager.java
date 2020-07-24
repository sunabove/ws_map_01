package htmleditor;

/**
 * Title:        HTML FREE EDITOR
 * Description:  Html Free Editor v2.0
 * Copyright:    Copyright (c) 2001
 * Company:      JCOSMOS DEVELOPMENT
 * @author Suhng ByuhngMunn
 * @version 1.0
 */

import java.io.*;

public class FileManager {

  public static boolean isAudioFileExtension( final File file ) {

    return isFileExtension( file, AUDIO_EXTENSIONS );

  }

  public static boolean isAudioFileExtension( final String fileName ) {

    return isFileExtension( fileName, AUDIO_EXTENSIONS );

  }

  public static boolean isVideoFileExtension( final File file ) {

    return isFileExtension( file, VIDEO_EXTENSIONS );

  }

  public static boolean isVideoFileExtension( final String fileName ) {

    return isFileExtension( fileName, VIDEO_EXTENSIONS );

  }

  public static boolean isImageFileExtension( final File file ) {

    return isFileExtension( file, IMAGE_EXTENSIONS );

  }

  public static boolean isImageFileExtension( final String fileName ) {

    return isFileExtension( fileName, IMAGE_EXTENSIONS );

  }

  private static boolean isFileExtension( final File file, final String [] extensions ) {

    if( file == null ) {

      return false;

    }

    return isFileExtension( file.getName(), extensions );

  }

  private static boolean isFileExtension( final String fileName, final String [] extensions ) {

    if( fileName == null ) {

      return false;

    }

    final int dotIdx = fileName.lastIndexOf( '.' );

    if( dotIdx < 0 ) {

      return false;

    }

    final String extensionName = fileName.substring( dotIdx );

    for( int i = 0, len = extensions.length ; i < len; i ++ ) {

      if( extensionName.equalsIgnoreCase( extensions[i] ) ) {

	return true;

      }

    }

    return false;

  }

  public static final String [] VIDEO_EXTENSIONS = {
     ".asf", ".wm", ".wma", ".wmv",
      ".mpeg", ".mpg", ".m1v",
      ".asx", ".wax", ".m3u", ".wvx", ".wmx",
      ".avi", ".wmv",
  };

  public static final String [] IMAGE_EXTENSIONS = new String [] {
    ".gif", ".jpg", ".jpeg"
  };

  public static final String [] AUDIO_EXTENSIONS = {
    ".cda",
    ".mid", ".rmi", ".midi",
    ".mp3", "mp2",
    ".wav", ".snd", ".au", ".aif", ".aifc", ".aiff"
  };


}