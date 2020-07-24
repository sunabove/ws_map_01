package com.ynhenc.gis.ui.comp;

public interface StateEventSourceInterface {

  public void addWorkStateListener(StateListener listener);

  public void removeWorkStateListener(StateListener listener);

  public void fireWorkStateEvent(StateEvent e);

  public StateEvent createWorkStateEvent( Object ojb );

}
