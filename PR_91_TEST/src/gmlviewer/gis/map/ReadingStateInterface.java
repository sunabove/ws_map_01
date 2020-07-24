package gmlviewer.gis.map;

public interface ReadingStateInterface {
  public static final ReadingState
      READ_FAIL = new ReadingState( -1 ),
      UNREAD = new ReadingState( 0 ),
      READING = new ReadingState( 1 ),
      READ_DONE = new ReadingState( 2 )
      ;

  public static class ReadingState {
    protected ReadingState( int state ) {
      this.state = state;
    }
    private int state;
  }

}
