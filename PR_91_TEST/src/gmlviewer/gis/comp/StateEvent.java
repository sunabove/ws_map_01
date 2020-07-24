package gmlviewer.gis.comp;

public class StateEvent {

  public StateEvent(double current, double total, String message) {

     this.current = current;
     this.total = total;
     this.message = message;

  }

  public double current;
  public double total;
  public String message;

}
