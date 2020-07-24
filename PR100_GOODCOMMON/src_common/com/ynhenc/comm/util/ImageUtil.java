package com.ynhenc.comm.util;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

import com.ynhenc.comm.util.*;


public class ImageUtil {

  public ImageUtil() {
  }

  public static BufferedImage createImage( int w, int h ) {

    return new BufferedImage( w, h, BufferedImage.TYPE_INT_RGB );

  }

  public static void write( BufferedImage image, String format, OutputStream out ) throws IOException {

    ImageIO.write( image, format, out );

  }

}
