package gmlviewer.gis.file;

import java.io.*;
import gmlviewer.gis.comp.*;
import gmlviewer.gis.kernel.*;

public abstract class DataFileReader extends CommonLib
    implements StateEventSourceInterface {

  public DataFileReader(InputStream src) {
    this(src, -1);
  }

  public DataFileReader(InputStream src, long length) {
    this.in = src;
    this.length = length;
    this.position = 0;
    this.readBuffer = new byte[8];
    this.workStateEventSource = new StateEventSource();
  }

  protected int IntBig() throws IOException {
    int i =
        (in.read() << 24)
        + (in.read() << 16)
        + (in.read() << 8)
        + (in.read())
        ;
    this.position += 4;

    return i;
  }

  protected int IntLittle() throws IOException {
    int i =
        (in.read())
        + (in.read() << 8)
        + (in.read() << 16)
        + (in.read() << 24)
        ;

    this.position += 4;

    return i;
  }

  protected long LongBig() throws IOException {
    ReadFully(readBuffer, 8);

    return
        ( ( (long) readBuffer[0] << 56) +
         ( (long) (readBuffer[1] & 255) << 48) +
         ( (long) (readBuffer[2] & 255) << 40) +
         ( (long) (readBuffer[3] & 255) << 32) +
         ( (long) (readBuffer[4] & 255) << 24) +
         ( (readBuffer[5] & 255) << 16) +
         ( (readBuffer[6] & 255) << 8) +
         ( (readBuffer[7] & 255)))
        ;
  }

  protected long LongLittle() throws IOException {
    ReadFully(readBuffer, 8);

    return
        ( ( (long) readBuffer[7] << 56) +
         ( (long) (readBuffer[6] & 255) << 48) +
         ( (long) (readBuffer[5] & 255) << 40) +
         ( (long) (readBuffer[4] & 255) << 32) +
         ( (long) (readBuffer[3] & 255) << 24) +
         ( (readBuffer[2] & 255) << 16) +
         ( (readBuffer[1] & 255) << 8) +
         ( (readBuffer[0] & 255)))
        ;
  }

  protected double DoubleLittle() throws IOException {
    return Double.longBitsToDouble(LongLittle());
  }

  protected int readDWord() throws IOException {
    return IntLittle();
  }

  protected short readWord() throws IOException {
    return ShortLittle();
  }

  protected byte Byte() throws IOException {
    byte b = (byte)this.in.read();
    this.position++;
    return b;
  }

  protected short ShortBig() throws IOException {
    short s = (short) ( (this.in.read() << 8) + (this.in.read()) );
    this.position += 2;
    return s;
  }

  protected short ShortLittle() throws IOException {
    short s = (short) ( (this.in.read() ) + (this.in.read() << 8) );
    this.position += 2;
    return s;
  }

  private void ReadFully(byte[] b, int n) throws IOException {
      int offset = 0;
      do {
        offset += this.in.read(b, offset, n - offset);
      }
      while (offset < n);

      this.position += offset;
  }

  protected byte[] readBytes(int available) throws IOException {
    byte[] bytes = new byte[available];
    int readNum = 0;
    int n;
    try {
      for (; readNum < available; ) {
        n = in.read(bytes, readNum, available - readNum);
        readNum += n;
      }
      this.position += readNum;
    }
    catch (IndexOutOfBoundsException e) {
      debug(e);
    }
    return bytes;
  }

  public void debug( String message ) {
    if( debugFlag ) {
      debug.debug(this, message);
    }
  }

  public void debug( boolean force, String message ) {
    debug.debug(this, message);
  }


  public void debug( Exception e ) {
    if( debugFlag ) {
      debug.debug(this, e);
    }
  }

  public void message( String message ) {
    debug.debug( this, message );
  }

  public void setDebugFlag( boolean b ) {
    this.debugFlag = b;
  }

  // member
  protected boolean debugFlag = true;
  protected long position;
  protected long length;
  private byte[] readBuffer;
  private InputStream in;

  // abstract methods

  public void addWorkStateListener(StateListener listener) {
    workStateEventSource.addWorkStateListener(listener);
  }

  public void removeWorkStateListener(StateListener listener) {
    workStateEventSource.removeWorkStateListener(listener);
  }

  public void fireWorkStateEvent(StateEvent e) {
    workStateEventSource.fireWorkStateEvent(e);
  }

  private StateEventSource workStateEventSource;

}
