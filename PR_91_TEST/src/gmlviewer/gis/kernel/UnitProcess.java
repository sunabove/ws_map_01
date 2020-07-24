package gmlviewer.gis.kernel;


public abstract class UnitProcess
    implements KernelInterface {

  public UnitProcess( ) {
    this( null, null, null );
  }

  public UnitProcess( String startMsg, String doneMsg, String failMsg ) {
    this.startMsg = startMsg;
    this.doneMsg = doneMsg;
    this.failMsg = failMsg;

    setProcessName();

  }

  private void setProcessName(){
    Class klass = getClass();

    String processName = klass.getName();

    int index = processName.lastIndexOf( '.' );

    if( ( index > -1 ) && ( index < processName.length() - 1 ) ) {

      processName = processName.substring( index + 1 );

    }

    this.processName = processName;

  }

  public String getStartMsg() {
    if( this.startMsg != null ) {
       return this.startMsg;
    }

    String startMsg = "Executing " + getProcessName() + " ......" ;

    this.startMsg = startMsg;

    return startMsg;

  }

  public String getDoneMsg() {
    if(  this.doneMsg != null ) {
      return this.doneMsg;
    }

    String doneMsg = "Done " + getProcessName() + " ......";

    this.doneMsg = doneMsg;

    return doneMsg;

  }

  public String getFailMsg() {

    if( this.failMsg != null ) {
      return this.failMsg;
    }

    String failMsg = "Failed " + getProcessName() + " ......";

    this.failMsg = failMsg;

    return failMsg;

  }

  public String getProcessName() {

    return this.processName;

  }

  protected void println( String msg ) {

    kernel.println( this, msg );

  }

  public boolean executeProcess( UnitProcess process ) {

    return kernel.execute( process );

  }

  public abstract int execute( ) throws Exception ;

  private String startMsg;
  private String doneMsg;
  private String failMsg;
  private String processName;

  public static final int PROCESS_SUCCESS = 0;
  public static final int PROCESS_FAIL = -1;
  public static final int EXCEPTION = -2;

}
