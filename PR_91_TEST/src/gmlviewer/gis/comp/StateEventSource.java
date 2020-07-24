package gmlviewer.gis.comp;

import java.util.*;

public class StateEventSource implements StateEventSourceInterface {

  public StateEventSource() {
    this.listeners = new Vector();
  }

  public void addWorkStateListener(StateListener listener) {
     listeners.addElement( listener );
  }

  public void removeWorkStateListener(StateListener listener) {
     listeners.remove( listener );
  }

  public void fireWorkStateEvent(StateEvent e) {

     Enumeration it = listeners.elements();

     while( it.hasMoreElements() ) {
        ( (StateListener) it.nextElement() ).workStateChanged( e );
     }

  }

  public StateEvent createWorkStateEvent() {
     return null;
  }

  private Vector listeners ;

}
