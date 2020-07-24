package gmlviewer.gis.kernel;

import java.io.*;
import gmlviewer.gis.util.DateUtil;
import gmlviewer.gis.util.ClassUtil;

public class Kernel implements DebugInterface {

  private Kernel() {
    this.initializeKernel();
  }

  private void initializeKernel() {
    java.util.Date now = DateUtil.getNowDate();
    String date_info = now.toString();
    String fileName = "kernel_out_" + date_info + ".txt";
    fileName.replaceAll( " ", "_" );
    File file = new File( fileName );
    try {
      OutputStream out = new FileOutputStream(file);
      PrintStream prnt = new PrintStream(out);
      System.setOut(prnt);
    }
    catch (FileNotFoundException ex) {
    }

  }

  public void debug( Exception e ) {
      debug.debug( this ,e );
  }

  public void debug( String msg ) {
      debug.debug( this, msg );
  }

  public void showStatus() {

    String msg = null;

    if( this.exceptionNo > 0 ) {
      msg = "Exception " + exceptionNo + " has occured!";
    }else {
      msg = "There are no exceptions occured.";
    }

    this.println( this, msg );

  }

  public boolean execute( UnitProcess process ) {

    Exception ex = null;

    boolean result = false;

    try {

      this.println( this, process.getStartMsg() );

      processDepth ++;

      if ( process.execute( ) == UnitProcess.PROCESS_SUCCESS ) {
	result = true;
      } else {
	result = false;
      }

    }
    catch( Exception e ) {
      ex = e;
      result = false;
    }

    if( ex != null ) {

      processException( process, ex );

      processDepth --;

      this.println( this, process.getFailMsg() );

      return result;

    } else {

      processDepth --;

      this.println( this, process.getDoneMsg() );

      return result;

    }

  }

  private void processException( UnitProcess porcess, Exception e ) {

    processException( e );

  }

  public void processException( Exception e ) {

    preProcessException( e );

    e.printStackTrace();

  }

  public void processException( Error e ) {

    preProcessException( e );

    e.printStackTrace();

  }

  private void preProcessException( Object e ) {
    this.exceptionNo ++;
  }

  public void println( Object obj, String msg ) {

    StringBuffer msgHeader = getMessageHeader( obj, this.processDepth, this.msgNo );

    System.out.println( msgHeader.toString() + msg );

    msgNo += 1;

  }

  private StringBuffer getMessageHeader( Object obj, int processDepth, int msgNo ) {

    Class klass = ( obj instanceof Class ) ? (Class) obj : obj.getClass();

    StringBuffer bfr = new StringBuffer();

    for( int i = 0, len = processDepth + 1 ; i < len ; i ++ ) {
      bfr.append( "* " );
    }

    bfr.append( msgNo + " [" + ClassUtil.getSimplifiedName( klass ) + "] " );

    return bfr;

  }

  public boolean isUnix() {
    return ( java.io.File.separatorChar == '/' ) ;
  }

  public boolean isWindow() {
    return ! isUnix();
  }

  // member

  private int exceptionNo = 0;
  private int msgNo = 0;
  private int processDepth = -1;

  // end of member

  // static member

  private final static Kernel KERNEL = new Kernel();

  // end of static member

  // static method

  public static Kernel getKernel() {
    return KERNEL;
  }

  // end of static method

}
